package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

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
	private List<AccessoryFitness> mFitnessList;

	public void setParams(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
	}

	public void run() {
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
			maxFitness.setup(accessory);

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

					AccessoryFitness fitness = new AccessoryFitness();
					fitness.setup(accessory);
					fitness.calc(memoria);
					if (fitness.getFitness() > maxFitness.getFitness()) {
						maxFitness = fitness;
					}
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

					AccessoryFitness fitness = new AccessoryFitness();
					fitness.setup(accessory);
					fitness.calc(memoria);
					if (fitness.getFitness() > maxFitness.getFitness()) {
						maxFitness = fitness;
					}
					mLogger.debug(fitness);
				}
			}

			mFitnessList.add(maxFitness);
			count++;
			mLogger.debug("アクセサリループカウント=" + count + "/" + accessories.size());
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

	public List<AccessoryFitness> getFitnesses() {
		return mFitnessList;
	}
}
