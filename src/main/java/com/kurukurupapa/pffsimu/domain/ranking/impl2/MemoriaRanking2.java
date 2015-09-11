package com.kurukurupapa.pffsimu.domain.ranking.impl2;

import java.util.List;

import org.apache.log4j.Logger;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitnessSet;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl2.MemoriaItemCombinations;

/**
 * メモリア順位付けクラス
 * <p>
 * メモリアを評価し、順位をつけます。 これにより、不要なメモリアを判断しやすくなると思います。 内部処理に、PartyFinder2dを使用しています。
 * </p>
 */
public class MemoriaRanking2 {
	/** ロガー */
	private Logger mLogger = Logger.getLogger(MemoriaRanking2.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private Party mParty;
	private List<MemoriaFitness> mFitnessList;

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator) {
		setParams(memoriaDataSet, itemDataSet, fitnessCalculator, new Party());
	}

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator,
			Party party) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitnessCalculator = fitnessCalculator;
		mParty = party;
	}

	public void run() {
		mLogger.trace("Start");
		mLogger.debug("メモリア数=" + mMemoriaDataSet.size());

		// メモリアとアイテムの一覧
		MemoriaDataSet memoriaDataSet = mMemoriaDataSet.clone();
		ItemDataSet itemDataSet = mItemDataSet.clone();
		if (mParty != null) {
			for (Memoria e : mParty.getMemoriaList()) {
				memoriaDataSet.remove(e.getName());
				itemDataSet.removeWeapon(e.getWeapon());
				itemDataSet.removeMagicOrAccessory(e.getAccessories());
			}
		}

		// 各メモリアの評価
		MemoriaFitnessSet set = MemoriaFitnessSet.createAsFitnessDesc();
		for (MemoriaData e : memoriaDataSet) {
			// 当該メモリアの最大評価を計算
			// リーダースキルの考慮あり
			MemoriaDataSet tmpMemoriaDataSet = new MemoriaDataSet(itemDataSet);
			tmpMemoriaDataSet.add(e);
			MemoriaItemCombinations combinations = new MemoriaItemCombinations(
					tmpMemoriaDataSet, itemDataSet, mFitnessCalculator);
			combinations.setup();
			combinations.get(0);
			set.add(combinations.get(0));
		}

		// 型変換
		mFitnessList = set.toList();

		mLogger.info("メモリア数=" + mMemoriaDataSet.size() + ",結果件数="
				+ mFitnessList.size());
		mLogger.trace("End");
	}

	public List<MemoriaFitness> getFitnesses() {
		return mFitnessList;
	}

}
