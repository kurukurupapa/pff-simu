package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.MemoriaFitness;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.ranking.MemoriaRanking;

/**
 * ランキング機能 メモリアランキングサービスクラス
 */
@Service
public class MemoriaRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private FitnessCalculator fitnessCalculator;
	private Party party;
	private MemoriaRanking memoriaRanking;

	public MemoriaRankingService() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();
	}

	public void setup(FitnessCalculator fitnessCalculator) {
		setup(fitnessCalculator, new Party());
	}

	public void setup(FitnessCalculator fitnessCalculator, Party party) {
		this.fitnessCalculator = fitnessCalculator;
		this.party = party;
	}

	public void run() {
		memoriaRanking = new MemoriaRanking();
		memoriaRanking.setParams(memoriaDataSet, itemDataSet,
				fitnessCalculator, party);
		memoriaRanking.run();
	}

	public List<MemoriaFitness> getRanking() {
		return memoriaRanking.getFitnesses();
	}

}
