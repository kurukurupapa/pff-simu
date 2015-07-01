package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * アクセサリ順位付けクラス
 * 
 * アクセサリを評価し、順位をつけます。 これにより、不要なアクセサリが判断しやすくなると思います。
 */
public class AccessoryRanking {
	/** ロガー */
	private Logger mLogger = Logger.getLogger(AccessoryRanking.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private Fitness mFitness;
	private Party mParty;
	private int mMemoriaIndex;
	private List<AccessoryFitness> mFitnessList;

	public void setParams(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
		setParams(memoriaDataSet, itemDataSet, null, null, 0);
	}

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, Fitness fitness, Party party,
			int memoriaIndex) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitness = fitness;
		mParty = party;
		mMemoriaIndex = memoriaIndex;

		// メモリアには、アクセサリを設定できる空きスロットが存在すること。
		if (mParty != null) {
			Validate.validState(mMemoriaIndex < mParty.getMemoriaList().size());
			Validate.validState(mParty.getMemoria(mMemoriaIndex)
					.getRemainAccessorySlot() >= 1);
		}
	}

	public void run() {
		if (mParty == null) {
			runWithoutParty();
		} else {
			runWithParty();
		}

		// 評価値の降順でソート
		Collections.sort(mFitnessList, new Comparator<AccessoryFitness>() {
			@Override
			public int compare(AccessoryFitness arg0, AccessoryFitness arg1) {
				// 降順
				return arg1.getFitness() - arg0.getFitness();
			}
		});
	}

	private void runWithoutParty() {
		// 武器の一覧
		List<ItemData> weapons = mItemDataSet.getWeaponList();
		// 魔法の一覧
		List<ItemData> magics = mItemDataSet.makeMagicList();
		// アクセサリの一覧
		List<ItemData> accessories = mItemDataSet.makeAccessoryList();

		// アクセサリの評価
		mLogger.info("アクセサリ数=" + accessories.size() //
				+ ",メモリア数=" + mMemoriaDataSet.size() //
				+ ",武器数+魔法数=" + (weapons.size() + magics.size()));
		mLogger.info("計算回数="
				+ (accessories.size() * mMemoriaDataSet.size() * (weapons
						.size() + magics.size())));
		mFitnessList = new ArrayList<AccessoryFitness>();
		int count = 0;
		for (ItemData accessory : accessories) {
			AccessoryFitness maxFitness = new AccessoryFitness();
			maxFitness.setup(accessory, mFitness);

			// 全てのメモリア、武器の組み合わせで当該アクセサリを評価し、
			// 最大評価値を当該アクセサリの評価とします。
			for (MemoriaData memoriaData : mMemoriaDataSet) {
				// NGな組み合わせをスキップ
				if (!accessory.isValid(memoriaData)) {
					mLogger.debug("NG組み合わせ=" + accessory + "+" + memoriaData);
					continue;
				}
				for (ItemData weapon : weapons) {
					// NGな組み合わせをスキップ
					if (!weapon.isValid(memoriaData)) {
						mLogger.debug("NG組み合わせ=" + memoriaData + "+" + weapon);
						continue;
					}

					Memoria memoria = new Memoria(memoriaData);
					memoria.setWeapon(weapon);

					AccessoryFitness fitness = calcAccessoryFitness(accessory,
							memoria);
					maxFitness = getMaxAccessoryFitness(fitness, maxFitness);
					mLogger.debug(fitness);
				}
				for (ItemData magic : magics) {
					// NGな組み合わせをスキップ
					if (!magic.isValid(memoriaData)) {
						mLogger.debug("NG組み合わせ=" + memoriaData + "+" + magic);
						continue;
					}

					Memoria memoria = new Memoria(memoriaData);
					memoria.addAccessory(magic);

					AccessoryFitness fitness = calcAccessoryFitness(accessory,
							memoria);
					maxFitness = getMaxAccessoryFitness(fitness, maxFitness);
					mLogger.debug(fitness);
				}
			}

			mFitnessList.add(maxFitness);
			count++;
			mLogger.debug("アクセサリループカウント=" + count + "/" + accessories.size());
		}
	}

	private void runWithParty() {
		Validate.validState(mParty != null);

		// アクセサリの一覧
		// パーティで使用中のアイテムは、ランキング対象から除外します。
		// TODO 同一名称のアクセサリが複数件ある場合の考慮が不足しています。
		ItemDataSet itemDataSet = mItemDataSet.clone();
		for (Memoria e : mParty.getMemoriaList()) {
			itemDataSet.removeMagicOrAccessory(e.getAccessories());
		}
		List<ItemData> accessories = itemDataSet.makeAccessoryList();

		// 対象メモリア
		Memoria memoria = mParty.getMemoria(mMemoriaIndex);

		// アクセサリの評価
		mLogger.info("アクセサリ数=" + accessories.size() + ",対象メモリア=" + memoria);
		mFitnessList = new ArrayList<AccessoryFitness>();
		for (ItemData accessory : accessories) {
			// NGな組み合わせをスキップ
			if (!memoria.validAccessoryData(accessory)) {
				mLogger.debug("NG組み合わせ=" + accessory + "+" + memoria.getName());
				continue;
			}

			AccessoryFitness fitness = calcAccessoryFitness(accessory, memoria);
			mFitnessList.add(fitness);
		}
	}

	private AccessoryFitness calcAccessoryFitness(ItemData accessory,
			Memoria memoria) {
		AccessoryFitness fitness = new AccessoryFitness();
		fitness.setup(accessory, mFitness);
		fitness.calc(memoria);
		return fitness;
	}

	private AccessoryFitness getMaxAccessoryFitness(AccessoryFitness arg1,
			AccessoryFitness arg2) {
		if (arg1.getFitness() > arg2.getFitness()) {
			return arg1;
		} else {
			return arg2;
		}
	}

	public List<AccessoryFitness> getFitnesses() {
		return mFitnessList;
	}
}
