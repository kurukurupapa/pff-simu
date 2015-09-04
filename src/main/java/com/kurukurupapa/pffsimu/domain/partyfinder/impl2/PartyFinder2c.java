package com.kurukurupapa.pffsimu.domain.partyfinder.impl2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pffsimu.domain.AppException;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * 最適パーティ計算クラス
 * <p>
 * 全探索で計算してみます。
 * </p>
 * <p>
 * ループの回し方は、（メモリア1 × （武器 ＋ 魔法/アクセサリ1 ＋ 魔法/アクセサリ2）） × （メモリア2 × （武器 ＋ 魔法/アクセサリ1 ＋
 * 魔法/アクセサリ2）） × ・・・ です。
 * ただし、これだと魔法とアクセサリの相互関係や、アクセサリの組み合わせによるリーダースキル発動を考慮できませんでした。 →ＮＧ！
 * </p>
 */
public class PartyFinder2c {
	private Logger mLogger;
	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private Party mBestParty;

	public PartyFinder2c(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator) {
		mLogger = Logger.getLogger(PartyFinder2c.class);

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

		if (maxMemorias == 1) {
			run1();
		} else if (maxMemorias == 2) {
			run2();
		} else {
			throw new AppException("未実装");
		}
	}

	public void run1() {
		Party maxParty = null;
		long calcCount = 0;
		long mCount = 0;
		long wCount = 0;
		long maCount = 0;

		// メモリアループ
		for (MemoriaData mdata : mMemoriaDataSet.getMemoriaDataList()) {
			// 当該メモリアが装備可能な武器/魔法/アクセサリリスト
			List<ItemData> wlist = getWeapons(mdata);
			List<ItemData> malist = getMagicsAccessories(mdata);

			// TODO 指輪の考慮

			// 武器ループ
			Party wmax = null;
			for (ItemData wdata : wlist) {
				// 適応度計算
				Party party = new Party(new Memoria(mdata, wdata, null, null));
				party.calcFitness(mFitnessCalculator);
				calcCount++;
				// 最大適応度のパーティを残す
				wmax = max(wmax, party);

				wCount++;
			}
			ItemData wdata = wmax.getMemoria(0).getWeapon();

			// 魔法/アクセサリ1ループ
			Party mamax1 = null;
			for (ItemData madata1 : malist) {
				// 適応度計算
				Party party = new Party(
						new Memoria(mdata, wdata, madata1, null));
				party.calcFitness(mFitnessCalculator);
				calcCount++;
				mLogger.debug(party);
				// 最大適応度のパーティを残す
				mamax1 = max(mamax1, party);

				maCount++;
			}
			ItemData madata1 = mamax1.getMemoria(0).getAccessories()[0];

			// 魔法/アクセサリ2ループ
			Party mamax2 = null;
			for (ItemData madata2 : malist) {
				if (madata1 == madata2) {
					continue;
				}

				// 適応度計算
				Party party = new Party(new Memoria(mdata, wdata, madata1,
						madata2));
				party.calcFitness(mFitnessCalculator);
				calcCount++;
				// 最大適応度のパーティを残す
				mamax2 = max(mamax2, party);

				maCount++;
			}
			ItemData madata2 = mamax2.getMemoria(0).getAccessories()[1];

			// 適応度計算
			Memoria memoria = new Memoria(mdata, wdata, madata1, madata2);
			Party party = new Party(memoria);
			party.calcFitness(mFitnessCalculator);
			calcCount++;
			mLogger.debug(party);
			// 最大適応度のパーティを残す
			maxParty = max(maxParty, party);

			mCount++;
		}

		mBestParty = maxParty;
		mLogger.debug("計算カウント=" + calcCount + "(" + mCount + "," + wCount + ","
				+ maCount + ")");
	}

	/**
	 * メモリア2体のパーティ計算
	 * 
	 * 単純に全探索します。
	 */
	public void run2() {
		int mcount = mMemoriaDataSet.size();
		int wcount = mItemDataSet.getWeaponList().size();
		int macount = mItemDataSet.getMagicAccessoryList().size();
		mLogger.debug("メモリア数=" + mcount + ",武器数=" + wcount + ",魔法/アクセサリ数="
				+ macount);
		mLogger.debug("計算量="
				+ ((long) mcount * (mcount - 1) * wcount * (wcount - 1)
						* macount * (macount - 1) * (macount - 2) * (macount - 3)));

		// 準備
		List<MemoriaData> memorias = mMemoriaDataSet.getMemoriaDataList();
		Party maxParty = null;
		long calcCount = 0;
		long mCount = 0;
		long wCount = 0;
		long maCount1 = 0;
		long maCount2 = 0;

		// メモリアループ1
		for (int m1 = 0; m1 < memorias.size(); m1++) {
			MemoriaData mdata1 = memorias.get(m1);
			mLogger.debug("ループカウント=" + m1 + "/" + mcount);
			// 当該メモリアで装備可能なアイテム
			List<ItemData> wlist1 = getWeapons(mdata1);
			List<ItemData> malist1 = getMagicsAccessories(mdata1);

			// メモリアループ2
			for (int m2 = m1 + 1; m2 < memorias.size(); m2++) {
				MemoriaData mdata2 = memorias.get(m2);
				// 当該メモリアで装備可能なアイテム
				List<ItemData> wlist2 = getWeapons(mdata2);
				List<ItemData> malist2 = getMagicsAccessories(mdata2);

				// 武器ループ
				Party wmax = null;
				// 武器ループ1
				for (ItemData wdata1 : wlist1) {
					// 武器ループ2
					for (ItemData wdata2 : wlist2) {
						if (wdata1 == wdata2) {
							continue;
						}

						// 適応度計算
						Memoria memoria1 = new Memoria(mdata1, wdata1, null,
								null);
						Memoria memoria2 = new Memoria(mdata2, wdata2, null,
								null);
						Party party = new Party();
						party.add(memoria1);
						party.add(memoria2);
						party.calcFitness(mFitnessCalculator);
						calcCount++;

						// 最大適応度のパーティを残す
						wmax = max(wmax, party);

						wCount++;
					}
					wCount++;
				}

				// 魔法/アクセサリループ
				Party mamax = null;
				// スロット1-1ループ
				for (int s11 = 0; s11 < malist1.size() - 1; s11++) {
					ItemData idata11 = malist1.get(s11);
					// スロット1-2ループ
					for (int s12 = s11 + 1; s12 < malist1.size(); s12++) {
						ItemData idata12 = malist1.get(s12);
						// スロット2-1ループ
						for (int s21 = 0; s21 < malist2.size() - 1; s21++) {
							ItemData idata21 = malist2.get(s21);
							if (idata21 == idata11 || idata21 == idata12) {
								continue;
							}
							// スロット2-2ループ
							for (int s22 = s21 + 1; s22 < malist2.size(); s22++) {
								ItemData idata22 = malist2.get(s22);
								if (idata22 == idata11 || idata22 == idata12) {
									continue;
								}

								// 適応度計算
								Memoria memoria1 = new Memoria(mdata1, null,
										idata11, idata12);
								Memoria memoria2 = new Memoria(mdata2, null,
										idata21, idata22);
								Party party = new Party();
								party.add(memoria1);
								party.add(memoria2);
								party.calcFitness(mFitnessCalculator);
								calcCount++;

								// 最大適応度のパーティを残す
								mamax = max(mamax, party);

								maCount2++;
							}
							maCount1++;
						}
						maCount2++;
					}
					maCount1++;
				}

				// 適応度計算
				Memoria memoria1 = new Memoria(mdata1, wmax.getMemoria(0)
						.getWeapon(), mamax.getMemoria(0).getAccessories()[0],
						mamax.getMemoria(0).getAccessories()[1]);
				Memoria memoria2 = new Memoria(mdata2, wmax.getMemoria(1)
						.getWeapon(), mamax.getMemoria(1).getAccessories()[0],
						mamax.getMemoria(1).getAccessories()[1]);
				Party party = new Party();
				party.add(memoria1);
				party.add(memoria2);
				party.calcFitness(mFitnessCalculator);
				calcCount++;

				// 最大適応度のパーティを残す
				maxParty = max(maxParty, party);

				mCount++;
			}
			mCount++;
		}
		mBestParty = maxParty;

		mLogger.debug("計算カウント=" + calcCount + "(" + mCount + "," + wCount + ","
				+ maCount1 + "," + maCount2 + ")");
	}

	private List<ItemData> getWeapons(MemoriaData memoria) {
		List<ItemData> list = new ArrayList<ItemData>();
		for (ItemData e : mItemDataSet.getWeaponList()) {
			if (e.isValid(memoria)) {
				for (int i = 0; i < e.getNumber(); i++) {
					list.add(e);
				}
			}
		}
		return list;
	}

	private List<ItemData> getMagicsAccessories(MemoriaData memoria) {
		List<ItemData> result = new ArrayList<ItemData>();
		for (ItemData e : mItemDataSet.getMagicAccessoryList()) {
			if (e.isValid(memoria)) {
				for (int i = 0; i < e.getNumber(); i++) {
					result.add(e);
				}
			}
		}
		return result;
	}

	public Party getParty() {
		return mBestParty;
	}

	private Party max(Party p1, Party p2) {
		if (p1 == null || p1.getFitness() < p2.getFitness()) {
			return p2;
		} else {
			return p1;
		}
	}

}
