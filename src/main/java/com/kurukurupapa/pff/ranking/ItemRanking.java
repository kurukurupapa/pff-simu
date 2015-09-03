package com.kurukurupapa.pff.ranking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.Party;

/**
 * アイテム順位付け抽象クラス
 * 
 * アイテムを評価し、順位をつけます。 これにより、不要なアイテムが判断しやすくなると思います。
 */
public abstract class ItemRanking {

	protected MemoriaDataSet mMemoriaDataSet;
	protected ItemDataSet mItemDataSet;
	protected FitnessCalculator mFitnessCalculator;
	protected Party mParty;
	protected int mMemoriaIndex;
	protected List<ItemFitness> mFitnessList = new ArrayList<ItemFitness>();

	public void setParams(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
		setParams(memoriaDataSet, itemDataSet, null, null, 0);
	}

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator) {
		setParams(memoriaDataSet, itemDataSet, fitnessCalculator, null, 0);
	}

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator,
			Party party, int memoriaIndex) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitnessCalculator = fitnessCalculator;
		mParty = party;
		mMemoriaIndex = memoriaIndex;
	}

	public void run() {
		if (mParty == null) {
			runWithoutParty();
		} else {
			runWithParty();
		}

		// 評価値の降順でソート
		Collections.sort(mFitnessList, new Comparator<ItemFitness>() {
			@Override
			public int compare(ItemFitness arg0, ItemFitness arg1) {
				// 降順
				return arg1.getFitness() - arg0.getFitness();
			}
		});
	}

	protected abstract void runWithoutParty();

	protected abstract void runWithParty();

	public List<ItemFitness> getFitnesses() {
		return mFitnessList;
	}
}
