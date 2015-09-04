package com.kurukurupapa.pffsimu.domain.ranking;

import java.util.ArrayList;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * 魔法/アクセサリ順位付けクラス
 * 
 * 魔法順位付けクラスとアクセサリ順位付けクラスを統合したクラスです。
 */
public class MagicAccessoryRanking extends ItemRanking {
	private MagicRanking mMagicRanking = new MagicRanking();
	private AccessoryRanking mAccessoryRanking = new AccessoryRanking();

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator,
			Party party, int memoriaIndex) {
		super.setParams(memoriaDataSet, itemDataSet, fitnessCalculator, party,
				memoriaIndex);
		mMagicRanking.setParams(memoriaDataSet, itemDataSet, fitnessCalculator,
				party, memoriaIndex);
		mAccessoryRanking.setParams(memoriaDataSet, itemDataSet,
				fitnessCalculator, party, memoriaIndex);
	}

	protected void runWithoutParty() {
		// 魔法
		mMagicRanking.runWithoutParty();
		// アクセサリ
		mAccessoryRanking.runWithoutParty();
		// マージ
		mFitnessList = new ArrayList<ItemFitness>();
		mFitnessList.addAll(mMagicRanking.mFitnessList);
		mFitnessList.addAll(mAccessoryRanking.mFitnessList);
	}

	protected void runWithParty() {
		// 魔法
		mMagicRanking.runWithParty();
		// アクセサリ
		mAccessoryRanking.runWithParty();
		// マージ
		mFitnessList = new ArrayList<ItemFitness>();
		mFitnessList.addAll(mMagicRanking.mFitnessList);
		mFitnessList.addAll(mAccessoryRanking.mFitnessList);
	}

}
