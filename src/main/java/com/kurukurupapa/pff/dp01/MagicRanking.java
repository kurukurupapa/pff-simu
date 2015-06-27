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
 * 魔法順位付けクラス
 * 
 * 魔法を評価し、順位をつけます。 これにより、不要な魔法が判断しやすくなると思います。
 */
public class MagicRanking {
	/** ロガー */
	private Logger mLogger = Logger.getLogger(MagicRanking.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private List<MagicFitness> mFitnessList;

	public void setParams(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
	}

	public void run() {
		// 魔法の一覧
		List<ItemData> magics = mItemDataSet.makeMagicList();

		// アクセサリの評価
		mLogger.info("魔法数=" + magics.size() //
				+ ",メモリア数=" + mMemoriaDataSet.size() //
		);
		mFitnessList = new ArrayList<MagicFitness>();
		int count = 0;
		for (ItemData magic : magics) {
			MagicFitness maxFitness = new MagicFitness();
			maxFitness.setup(magic);

			// 全てのメモリアに対して、当該魔法を評価し、
			// 最大評価値を当該魔法の評価とします。
			for (MemoriaData memoriaData : mMemoriaDataSet) {
				// NGな組み合わせをスキップ
				if (!magic.isValid(memoriaData)) {
					mLogger.debug("NG組み合わせ=" + magic + "+" + memoriaData);
					continue;
				}

				Memoria memoria = new Memoria(memoriaData);
				MagicFitness fitness = new MagicFitness();
				fitness.setup(magic);
				fitness.calc(memoria);
				if (fitness.getFitness() > maxFitness.getFitness()) {
					maxFitness = fitness;
				}
				mLogger.debug(fitness);
			}

			mFitnessList.add(maxFitness);
			count++;
			mLogger.debug("魔法ループカウント=" + count + "/" + magics.size());
		}

		// 評価値の降順でソート
		Collections.sort(mFitnessList, new Comparator<MagicFitness>() {
			@Override
			public int compare(MagicFitness arg0, MagicFitness arg1) {
				// 降順
				return arg1.getFitness() - arg0.getFitness();
			}
		});
	}

	public List<MagicFitness> getFitnesses() {
		return mFitnessList;
	}
}
