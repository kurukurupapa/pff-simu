package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.ranking.AccessoryRanking;

/**
 * ランキング機能 アクセサリランキングサービスクラス
 */
@Service
public class AccessoryRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private FitnessCalculator fitnessCalculator;
	private Party party;
	private int memoriaIndex;
	private AccessoryRanking accessoryRanking;

	public AccessoryRankingService() {
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
		accessoryRanking = new AccessoryRanking();
		accessoryRanking.setParams(memoriaDataSet, itemDataSet,
				fitnessCalculator, party, memoriaIndex);
		accessoryRanking.run();
	}

	public List<ItemFitness> getFitnesses() {
		return accessoryRanking.getFitnesses();
	}

}
