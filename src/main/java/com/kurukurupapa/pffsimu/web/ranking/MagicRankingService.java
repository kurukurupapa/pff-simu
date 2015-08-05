package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.MagicRanking;
import com.kurukurupapa.pff.dp01.Party;

/**
 * ランキング機能 魔法ランキングサービスクラス
 */
@Service
public class MagicRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private FitnessCalculator fitnessCalculator;
	private Party party;
	private int memoriaIndex;
	private MagicRanking magicRanking;

	public MagicRankingService() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();
	}

	public void setup() {
		setup(null, null, 0);
	}

	public void setup(FitnessCalculator fitnessCalculator, Party party,
			int memoriaIndex) {
		this.fitnessCalculator = fitnessCalculator;
		this.party = party;
		this.memoriaIndex = memoriaIndex;
	}

	public void run() {
		magicRanking = new MagicRanking();
		magicRanking.setParams(memoriaDataSet, itemDataSet, fitnessCalculator,
				party, memoriaIndex);
		magicRanking.run();
	}

	public List<ItemFitness> getRanking() {
		return magicRanking.getFitnesses();
	}

}
