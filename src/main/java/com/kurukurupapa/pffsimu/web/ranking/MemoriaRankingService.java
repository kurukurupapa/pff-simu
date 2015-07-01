package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.Fitness;
import com.kurukurupapa.pff.dp01.MemoriaFitnessValue;
import com.kurukurupapa.pff.dp01.MemoriaRanking;
import com.kurukurupapa.pff.dp01.Party;

/**
 * ランキング機能 メモリアランキングサービスクラス
 */
@Service
public class MemoriaRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private Fitness fitness;
	private Party party;
	private MemoriaRanking memoriaRanking;

	public MemoriaRankingService() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();
	}

	public void setup(Fitness fitness) {
		setup(fitness, new Party());
	}

	public void setup(Fitness fitness, Party party) {
		this.fitness = fitness;
		this.party = party;
	}

	public void run() {
		memoriaRanking = new MemoriaRanking();
		memoriaRanking.setParams(memoriaDataSet, itemDataSet, fitness, party);
		memoriaRanking.run();
	}

	public List<MemoriaFitnessValue> getRanking() {
		return memoriaRanking.getFitnesses();
	}

}
