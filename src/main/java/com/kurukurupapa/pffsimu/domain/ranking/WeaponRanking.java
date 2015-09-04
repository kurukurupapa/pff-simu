package com.kurukurupapa.pffsimu.domain.ranking;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.fitness.WeaponFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * 武器順位付けクラス
 * 
 * 武器を評価し、順位をつけます。 これにより、不要な武器が判断しやすくなると思います。
 */
public class WeaponRanking extends ItemRanking {
	/** ロガー */
	private Logger mLogger = Logger.getLogger(WeaponRanking.class);

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator,
			Party party, int memoriaIndex) {
		super.setParams(memoriaDataSet, itemDataSet, fitnessCalculator, party,
				memoriaIndex);

		// メモリアには、武器を設定できる空きが存在すること。
		if (mParty != null) {
			Validate.validState(mMemoriaIndex < mParty.getMemoriaList().size());
			Validate.validState(mParty.getMemoria(mMemoriaIndex)
					.getRemainWeaponSlot() >= 1);
		}
	}

	@Override
	protected void runWithoutParty() {
		// 武器の一覧
		List<ItemData> weapons = mItemDataSet.getWeaponList();

		// アクセサリの評価
		mLogger.info("武器数=" + weapons.size() //
				+ ",メモリア数=" + mMemoriaDataSet.size() //
		);
		mFitnessList = new ArrayList<ItemFitness>();
		// int count = 0;
		for (ItemData weapon : weapons) {
			WeaponFitness maxFitness = new WeaponFitness();
			maxFitness.setup(weapon);

			// 全てのメモリアに対して、当該武器を評価し、
			// 最大評価値を当該武器の評価とします。
			for (MemoriaData memoriaData : mMemoriaDataSet) {
				// NGな組み合わせをスキップ
				if (!weapon.isValid(memoriaData)) {
					// mLogger.debug("NG組み合わせ=" + weapon + "+" + memoriaData);
					continue;
				}

				Memoria memoria = new Memoria(memoriaData);
				WeaponFitness fitness = calcWeaponFitness(weapon, memoria);
				if (fitness.getFitness() > maxFitness.getFitness()) {
					maxFitness = fitness;
				}
				// mLogger.debug(fitness);
			}

			mFitnessList.add(maxFitness);
			// count++;
			// mLogger.debug("武器ループカウント=" + count + "/" + weapons.size());
		}
	}

	@Override
	protected void runWithParty() {
		Validate.validState(mParty != null);

		// 武器の一覧
		// パーティで使用中のアイテムは、ランキング対象から除外します。
		// TODO 同一名称の武器が複数件ある場合の考慮が不足しています。
		ItemDataSet itemDataSet = mItemDataSet.clone();
		for (Memoria e : mParty.getMemoriaList()) {
			if (e.getWeapon() != null) {
				itemDataSet.removeWeapon(e.getWeapon());
			}
		}
		List<ItemData> weapons = itemDataSet.getWeaponList();

		// 対象メモリア
		Memoria memoria = mParty.getMemoria(mMemoriaIndex);

		// アクセサリの評価
		mLogger.info("武器数=" + weapons.size() + ",対象メモリア=" + memoria);
		mFitnessList = new ArrayList<ItemFitness>();
		for (ItemData weapon : weapons) {
			// NGな組み合わせをスキップ
			if (!memoria.validWeaponData(weapon)) {
				// mLogger.debug("NG組み合わせ=" + weapon + "+" + memoria.getName());
				continue;
			}

			// 適応度計算
			WeaponFitness fitness = calcWeaponFitness(weapon, memoria);
			mFitnessList.add(fitness);
		}
	}

	private WeaponFitness calcWeaponFitness(ItemData weapon, Memoria memoria) {
		WeaponFitness fitness = new WeaponFitness();
		fitness.setup(weapon, mFitnessCalculator);
		fitness.calc(memoria);
		return fitness;
	}

}
