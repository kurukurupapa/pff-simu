package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * 武器順位付けクラス
 * 
 * 武器を評価し、順位をつけます。 これにより、不要な武器が判断しやすくなると思います。
 */
public class WeaponRanking {
	/** ロガー */
	private Logger mLogger = Logger.getLogger(WeaponRanking.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private List<WeaponFitness> mFitnessList;

	public void setParams(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
	}

	public void run() {
		// 武器の一覧
		List<ItemData> weapons = mItemDataSet.getWeaponList();

		// アクセサリの評価
		mLogger.info("武器数=" + weapons.size() //
				+ ",メモリア数=" + mMemoriaDataSet.size() //
		);
		mFitnessList = new ArrayList<WeaponFitness>();
		int count = 0;
		for (ItemData weapon : weapons) {
			WeaponFitness maxFitness = new WeaponFitness();
			maxFitness.setup(weapon);

			// 全てのメモリアに対して、当該武器を評価し、
			// 最大評価値を当該武器の評価とします。
			for (MemoriaData memoriaData : mMemoriaDataSet) {
				// NGな組み合わせをスキップ
				if (!weapon.isValid(memoriaData)) {
					mLogger.debug("NG組み合わせ=" + weapon + "+" + memoriaData);
					continue;
				}

				Memoria memoria = new Memoria(memoriaData);
				WeaponFitness fitness = new WeaponFitness();
				fitness.setup(weapon);
				fitness.calc(memoria);
				if (fitness.getFitness() > maxFitness.getFitness()) {
					maxFitness = fitness;
				}
				mLogger.debug(fitness);
			}

			mFitnessList.add(maxFitness);
			count++;
			mLogger.debug("武器ループカウント=" + count + "/" + weapons.size());
		}

		// 評価値の降順でソート
		Collections.sort(mFitnessList, new Comparator<WeaponFitness>() {
			@Override
			public int compare(WeaponFitness arg0, WeaponFitness arg1) {
				// 降順
				return arg1.getFitness() - arg0.getFitness();
			}
		});
	}

	public List<WeaponFitness> getFitnesses() {
		return mFitnessList;
	}
}
