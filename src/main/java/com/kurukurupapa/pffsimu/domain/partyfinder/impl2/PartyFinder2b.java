package com.kurukurupapa.pffsimu.domain.partyfinder.impl2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pffsimu.domain.AppException;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.ranking.AccessoryRanking;
import com.kurukurupapa.pffsimu.domain.ranking.MagicRanking;
import com.kurukurupapa.pffsimu.domain.ranking.MemoriaRanking;
import com.kurukurupapa.pffsimu.domain.ranking.WeaponRanking;

/**
 * 最適パーティ計算クラス
 * <p>
 * 分岐限定法のような考え方で計算してみます。
 * </p>
 * <p>
 * ループの回し方は、（メモリア1 × 武器 × 魔法/アクセサリ1 × 魔法/アクセサリ2） × （メモリア2 × 武器 × 魔法/アクセサリ1 ×
 * 魔法/アクセサリ2） × ・・・です。
 * </p>
 * <p>
 * PartyFinder2aと比べるとよくなりましたが、メモリア4体のパーティを処理することはできませんでした。 一旦、開発中止とします。
 * </p>
 * <p>
 * 参考：<br/>
 * 分岐限定法<br/>
 * https://ja.wikipedia.org/wiki/%E5%88%86%E6%9E%9D%E9%99%90%E5%AE%9A%E6%B3%95
 * </p>
 */
public class PartyFinder2b {
	protected Logger mLogger;

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private Party mBestParty;
	private List<MemoriaFitness> mMemoriaFitnesses;
	private List<ItemFitness> mWeaponFitnesses;
	private List<ItemFitness> mMagicFitnesses;
	private List<ItemFitness> mAccessoryFitnesses;
	private List<ItemFitness> mMagicAccessoryFitnesses;
	private long mCalcCount;
	private long mMCount;
	private long mWCount;
	private long mMaCount1;
	private long mMaCount2;

	public PartyFinder2b(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator) {
		mLogger = Logger.getLogger(PartyFinder2b.class);

		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitnessCalculator = fitnessCalculator;
	}

	public void run() {
		run(Party.MAX_MEMORIAS);
	}

	public void run(int maxMemorias) {
		Validate.validState(1 <= maxMemorias
				&& maxMemorias <= Party.MAX_MEMORIAS);

		// メモリアランキング
		MemoriaRanking memoriaRanking = new MemoriaRanking();
		memoriaRanking.setParams(mMemoriaDataSet, mItemDataSet,
				mFitnessCalculator);
		memoriaRanking.run();
		mMemoriaFitnesses = memoriaRanking.getFitnesses();

		// 武器ランキング
		WeaponRanking weaponRanking = new WeaponRanking();
		weaponRanking.setParams(mMemoriaDataSet, mItemDataSet,
				mFitnessCalculator);
		weaponRanking.run();
		mWeaponFitnesses = weaponRanking.getFitnesses();

		// 魔法ランキング
		MagicRanking magicRanking = new MagicRanking();
		magicRanking.setParams(mMemoriaDataSet, mItemDataSet,
				mFitnessCalculator);
		magicRanking.run();
		mMagicFitnesses = magicRanking.getFitnesses();

		// アクセサリランキング
		AccessoryRanking accessoryRanking = new AccessoryRanking();
		accessoryRanking.setParams(mMemoriaDataSet, mItemDataSet,
				mFitnessCalculator);
		accessoryRanking.run();
		mAccessoryFitnesses = accessoryRanking.getFitnesses();

		// 魔法とアクセサリをマージする
		mMagicAccessoryFitnesses = new ArrayList<ItemFitness>();
		mMagicAccessoryFitnesses.addAll(mMagicFitnesses);
		mMagicAccessoryFitnesses.addAll(mAccessoryFitnesses);
		Collections.sort(mMagicAccessoryFitnesses,
				new Comparator<ItemFitness>() {
					@Override
					public int compare(ItemFitness arg0, ItemFitness arg1) {
						// 降順
						return arg1.getFitness() - arg0.getFitness();
					}
				});

		mLogger.debug("メモリアランキング=" + StringUtils.join(mMemoriaFitnesses, ","));
		mLogger.debug("武器ランキング=" + StringUtils.join(mWeaponFitnesses, ","));
		mLogger.debug("魔法/アクセサリランキング="
				+ StringUtils.join(mMagicAccessoryFitnesses, ","));

		mCalcCount = 0;
		mMCount = 0;
		mWCount = 0;
		mMaCount1 = 0;
		mMaCount2 = 0;
		if (maxMemorias == 1) {
			run1();
		} else if (maxMemorias == 2) {
			run2();
		} else {
			throw new AppException("未実装");
		}
		mLogger.debug("計算カウント=" + mCalcCount + "(" + mMCount + "," + mWCount
				+ "," + mMaCount1 + "," + mMaCount2 + ")");
	}

	/**
	 * メモリア1体での最高適応度のパーティを計算します。
	 * 
	 * 計算量が減るように工夫します。
	 */
	private void run1() {
		int mcount = mMemoriaDataSet.size();
		int wcount = mItemDataSet.getWeaponList().size();
		int macount = mItemDataSet.getMagicAccessoryList().size();
		mLogger.debug("メモリア数=" + mcount + ",武器数=" + wcount + ",魔法/アクセサリ数="
				+ macount + ",想定計算量="
				+ ((long) mcount * wcount * macount * (macount - 1)));

		Party currentParty = new Party();
		mBestParty = calcMemoriaWeaponMagicAccessory(1, 0,
				NextMemoria.INIT_INDEX, currentParty);
	}

	/**
	 * メモリア2体での最高適応度のパーティを計算します。
	 * 
	 * 計算量が減るように工夫します。
	 */
	private void run2() {
		int mcount = mMemoriaDataSet.size();
		int wcount = mItemDataSet.getWeaponList().size();
		int macount = mItemDataSet.getMagicAccessoryList().size();
		mLogger.debug("メモリア数="
				+ mcount
				+ ",武器数="
				+ wcount
				+ ",魔法/アクセサリ数="
				+ macount
				+ ",想定計算量="
				+ ((long) mcount * (mcount - 1) * wcount * (wcount - 1)
						* macount * (macount - 1) * (macount - 2) * (macount - 3)));

		Party currentParty = new Party();
		mBestParty = calcMemoriaWeaponMagicAccessory(2, 0,
				NextMemoria.INIT_INDEX, currentParty);
	}

	private Party calcMemoriaWeaponMagicAccessory(int maxMemorias,
			int memoriaPosition, int prevMemoriaIndex, Party currentParty) {
		Validate.validState(currentParty.size() == memoriaPosition);

		Party maxParty = currentParty.clone();

		NextMemoria nextMemoria = new NextMemoria(mFitnessCalculator,
				memoriaPosition, prevMemoriaIndex, mMemoriaFitnesses);
		while (true) {
			MemoriaData mdata = nextMemoria.next(currentParty, maxParty);
			if (mdata == null) {
				// 次のメモリアがなければ探索終了
				break;
			}

			// 上記メモリアを現在パーティに反映
			Memoria memoria = new Memoria(mdata);
			currentParty.add(memoria);

			// 当該メモリアに対して、最大適応度の武器/魔法/アクセサリを計算します。
			Party tmpParty = calcWeaponMagicAccessory(maxMemorias,
					memoriaPosition, nextMemoria.getIndex(), currentParty);

			// 最大適応度のパーティを残す
			maxParty = max(maxParty, tmpParty);

			// 後片付け
			currentParty.remove(memoriaPosition);

			mMCount++;
		}

		return maxParty;
	}

	private Party calcWeaponMagicAccessory(int maxMemorias,
			int memoriaPosition, int prevMemoriaIndex, Party currentParty) {
		Validate.validState(currentParty.size() == memoriaPosition + 1);
		Validate.validState(currentParty.getMemoria(memoriaPosition).isWeapon() == false);
		Validate.validState(currentParty.getMemoria(memoriaPosition)
				.getNumAccessories() == 0);

		Party maxParty = currentParty.clone();
		NextWeapon nextWeapon = new NextWeapon(memoriaPosition,
				mWeaponFitnesses, mFitnessCalculator);

		// mLogger.debug("武器ループ Start");
		while (true) {
			ItemData weapon = nextWeapon.next(currentParty, maxParty);
			if (weapon == null) {
				break;
			}

			// 上記武器を現在パーティに反映
			currentParty.getMemoria(memoriaPosition).setWeapon(weapon);

			// 魔法/アクセサリの計算
			Party tmpParty = calcMagicAccessory(maxMemorias, memoriaPosition,
					prevMemoriaIndex, currentParty);
			maxParty = max(maxParty, tmpParty);

			// 後片付け
			currentParty.getMemoria(memoriaPosition).clearWeapon();

			mWCount++;
		}
		// mLogger.debug("武器ループ End");

		return maxParty;
	}

	/**
	 * 現在パーティに対して、最も適応度の高い魔法/アクセサリを計算します。
	 * 
	 * @param currentParty
	 *            現在パーティ
	 * @return 現在パーティに対して、魔法/アクセサリを追加したパーティを返却します。
	 */
	private Party calcMagicAccessory(int maxMemorias, int memoriaPosition,
			int prevMemoriaIndex, Party currentParty) {
		return calcMagicAccessory(maxMemorias, memoriaPosition,
				prevMemoriaIndex, 0, NextMagicAccessory.INIT_INDEX,
				currentParty);
	}

	/**
	 * 現在パーティに対して、最も適応度の高い魔法/アクセサリを計算します。
	 * 
	 * @param currentParty
	 *            現在パーティ
	 * @return 現在パーティに対して、魔法/アクセサリを追加したパーティを返却します。
	 */
	private Party calcMagicAccessory(int maxMemorias, int memoriaPosition,
			int prevMemoriaIndex, int magicAccessoryPosition,
			int prevMagicAccessoryIndex, Party currentParty) {
		Validate.validState(currentParty.size() == memoriaPosition + 1);
		Validate.validState(currentParty.getMemoria(memoriaPosition).isWeapon());
		Validate.validState(currentParty.getMemoria(memoriaPosition)
				.getNumAccessories() == magicAccessoryPosition);

		Party maxParty = currentParty.clone();
		NextMagicAccessory nextMagicAccessory = new NextMagicAccessory(
				memoriaPosition, magicAccessoryPosition,
				prevMagicAccessoryIndex, mMagicAccessoryFitnesses,
				mFitnessCalculator);

		// mLogger.debug("スロット" + (magicAccessoryPosition + 1) + "ループ Start");
		while (true) {
			ItemData magicAccessory = nextMagicAccessory.next(currentParty,
					maxParty);
			if (magicAccessory == null) {
				break;
			}

			// 上記アイテムを現在パーティに反映
			currentParty.getMemoria(memoriaPosition).addAccessory(
					magicAccessory);

			if (magicAccessoryPosition + 1 < Memoria.MAX_ACCESSORIES) {
				// 後続の魔法/アクセサリの計算
				Party tmpParty = calcMagicAccessory(maxMemorias,
						memoriaPosition, prevMemoriaIndex,
						magicAccessoryPosition + 1,
						nextMagicAccessory.getIndex(), currentParty);
				// 最大適応度のパーティを残す
				maxParty = max(maxParty, tmpParty);

			} else if (memoriaPosition + 1 < maxMemorias) {
				// 後続のメモリアの計算
				Party tmpParty = calcMemoriaWeaponMagicAccessory(maxMemorias,
						memoriaPosition + 1, prevMemoriaIndex, currentParty);
				// 最大適応度のパーティを残す
				maxParty = max(maxParty, tmpParty);

			} else {
				// 適応度計算
				currentParty.calcFitness(mFitnessCalculator);
				mCalcCount++;
				// mLogger.debug(currentParty);
				// 最大適応度のパーティを残す
				maxParty = max(maxParty, currentParty);
			}

			// 後片付け
			currentParty.getMemoria(memoriaPosition).removeAccessory(
					magicAccessoryPosition);

			if (magicAccessoryPosition == 0) {
				mMaCount1++;
			} else {
				mMaCount2++;
			}
		}
		// mLogger.debug("スロット" + (magicAccessoryPosition + 1) + "ループ結果="
		// + maxParty);
		// mLogger.debug("スロット" + (magicAccessoryPosition + 1) + "ループ End");

		return maxParty;
	}

	public Party getParty() {
		return mBestParty;
	}

	private Party max(Party maxParty, Party currentParty) {
		if (maxParty == null
				|| maxParty.getFitness() < currentParty.getFitness()) {
			return currentParty.clone();
		} else {
			return maxParty;
		}
	}

}
