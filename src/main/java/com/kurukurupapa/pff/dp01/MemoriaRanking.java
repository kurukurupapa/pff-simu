package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.LeaderSkill;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * メモリア順位付けクラス
 * 
 * メモリアを評価し、順位をつけます。 これにより、不要なメモリアを判断しやすくなると思います。
 */
public class MemoriaRanking {
	/** ロガー */
	private Logger mLogger = Logger.getLogger(MemoriaRanking.class);

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
		mFitnessList = new ArrayList<MemoriaFitness>();
		Dp01 dp;
		for (MemoriaData e : memoriaDataSet) {
			// 当該メモリアの最大評価を計算
			MemoriaDataSet tmpMemoriaDataSet = new MemoriaDataSet(itemDataSet);
			tmpMemoriaDataSet.add(e);
			dp = new Dp01(tmpMemoriaDataSet, itemDataSet, mFitnessCalculator);
			dp.run(1);
			FitnessValue max = dp.getParty().getFitnessObj();

			// リーダースキルを考慮する
			for (LeaderSkill e2 : LeaderSkill.values()) {
				// TODO 自分自身のリーダースキルは省いた方が良いかも。
				Party party = dp.getParty().clone();
				party.getMemoria(0).addLeaderSkill(e2.getItemData());
				party.calcFitness(mFitnessCalculator);
				if (max.getValue() < party.getFitness()) {
					max = party.getFitnessObj();
				}
			}

			mFitnessList.add(max.getMemoriaFitnesses().get(0));
		}

		// 評価値の降順でソート
		Collections.sort(mFitnessList, new Comparator<MemoriaFitness>() {
			@Override
			public int compare(MemoriaFitness arg0, MemoriaFitness arg1) {
				// 降順
				return arg1.getValue() - arg0.getValue();
			}
		});

		mLogger.trace("End");
	}

	public List<MemoriaFitness> getFitnesses() {
		return mFitnessList;
	}

}
