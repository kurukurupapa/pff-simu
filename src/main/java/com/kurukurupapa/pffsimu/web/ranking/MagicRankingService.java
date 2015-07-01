package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.Fitness;
import com.kurukurupapa.pff.dp01.MagicFitness;
import com.kurukurupapa.pff.dp01.MagicRanking;
import com.kurukurupapa.pff.dp01.Party;

/**
 * ランキング機能 魔法ランキングサービスクラス
 */
@Service
public class MagicRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private Fitness fitness;
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

	public void setup(Fitness fitness, Party party, int memoriaIndex) {
		this.fitness = fitness;
		this.party = party;
		this.memoriaIndex = memoriaIndex;
	}

	public void run() {
		magicRanking = new MagicRanking();
		magicRanking.setParams(memoriaDataSet, itemDataSet, fitness, party,
				memoriaIndex);
		magicRanking.run();
	}

	public List<MagicFitness> getRanking() {
		return magicRanking.getFitnesses();
	}

}
