package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * 魔法順位付けクラス
 * 
 * 魔法を評価し、順位をつけます。 これにより、不要な魔法が判断しやすくなると思います。
 */
public class MagicRanking extends ItemRanking {
	/** ロガー */
	public Logger mLogger = Logger.getLogger(MagicRanking.class);

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator,
			Party party, int memoriaIndex) {
		super.setParams(memoriaDataSet, itemDataSet, fitnessCalculator, party,
				memoriaIndex);

		// メモリアには、魔法を設定できる空きスロットが存在すること。
		if (mParty != null) {
			Validate.validState(mMemoriaIndex < mParty.getMemoriaList().size());
			Validate.validState(mParty.getMemoria(mMemoriaIndex)
					.getRemainAccessorySlot() >= 1);
		}
	}

	protected void runWithoutParty() {
		// 魔法の一覧
		List<ItemData> magics = mItemDataSet.makeMagicList();

		// 魔法の評価
		mLogger.info("魔法数=" + magics.size() //
				+ ",メモリア数=" + mMemoriaDataSet.size() //
		);
		mFitnessList = new ArrayList<ItemFitness>();
		// int count = 0;
		for (ItemData magic : magics) {
			MagicFitness maxFitness = new MagicFitness();
			maxFitness.setup(magic, mFitnessCalculator);

			// 全てのメモリアに対して、当該魔法を評価し、
			// 最大評価値を当該魔法の評価とします。
			for (MemoriaData memoriaData : mMemoriaDataSet) {
				// NGな組み合わせをスキップ
				if (!magic.isValid(memoriaData)) {
					// mLogger.debug("NG組み合わせ=" + magic + "+" + memoriaData);
					continue;
				}

				Memoria memoria = new Memoria(memoriaData);
				MagicFitness fitness = calcMagicFitness(magic, memoria);
				if (fitness.getFitness() > maxFitness.getFitness()) {
					maxFitness = fitness;
				}
				// mLogger.debug(fitness);
			}

			mFitnessList.add(maxFitness);
			// count++;
			// mLogger.debug("魔法ループカウント=" + count + "/" + magics.size());
		}
	}

	protected void runWithParty() {
		Validate.validState(mParty != null);

		// 魔法の一覧
		// パーティで使用中のアイテムは、ランキング対象から除外します。
		// TODO 同一名称の魔法が複数件ある場合の考慮が不足しています。
		ItemDataSet itemDataSet = mItemDataSet.clone();
		for (Memoria e : mParty.getMemoriaList()) {
			itemDataSet.removeMagicOrAccessory(e.getAccessories());
		}
		List<ItemData> magics = itemDataSet.makeMagicList();

		// 対象メモリア
		Memoria memoria = mParty.getMemoria(mMemoriaIndex);

		// 魔法の評価
		mLogger.info("魔法数=" + magics.size() + ",対象メモリア=" + memoria);
		mFitnessList = new ArrayList<ItemFitness>();
		for (ItemData magic : magics) {
			// NGな組み合わせをスキップ
			if (!memoria.validAccessoryData(magic)) {
				// mLogger.debug("NG組み合わせ=" + magic + "+" + memoria.getName());
				continue;
			}

			MagicFitness fitness = calcMagicFitness(magic, memoria);
			mFitnessList.add(fitness);
		}
	}

	private MagicFitness calcMagicFitness(ItemData magic, Memoria memoria) {
		MagicFitness fitness = new MagicFitness();
		fitness.setup(magic, mFitnessCalculator);
		fitness.calc(memoria);
		return fitness;
	}

}
