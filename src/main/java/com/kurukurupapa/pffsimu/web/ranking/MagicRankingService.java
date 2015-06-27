package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.MagicFitness;
import com.kurukurupapa.pff.dp01.MagicRanking;

/**
 * ランキング機能 魔法ランキングサービスクラス
 */
@Service
public class MagicRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private MagicRanking magicRanking;

	public MagicRankingService() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();
	}

	public void run() {
		magicRanking = new MagicRanking();
		magicRanking.setParams(memoriaDataSet, itemDataSet);
		magicRanking.run();
	}

	public List<MagicFitness> getRanking() {
		return magicRanking.getFitnesses();
	}

}
