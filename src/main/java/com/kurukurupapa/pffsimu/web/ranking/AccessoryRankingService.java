package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.AccessoryFitness;
import com.kurukurupapa.pff.dp01.AccessoryRanking;

/**
 * ランキング機能 アクセサリランキングサービスクラス
 */
@Service
public class AccessoryRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private AccessoryRanking accessoryRanking;

	public AccessoryRankingService() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();
	}

	public void run() {
		accessoryRanking = new AccessoryRanking();
		accessoryRanking.setParams(memoriaDataSet, itemDataSet);
		accessoryRanking.run();
	}

	public List<AccessoryFitness> getFitnesses() {
		return accessoryRanking.getFitnesses();
	}

}
