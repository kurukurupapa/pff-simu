package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.dp01.WeaponRanking;

/**
 * ランキング機能 武器ランキングサービスクラス
 */
@Service
public class WeaponRankingService {
	private Logger mLogger = Logger.getLogger(WeaponRankingService.class);

	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private FitnessCalculator fitnessCalculator;
	private Party party;
	private int memoriaIndex;
	private WeaponRanking weaponRanking;

	public WeaponRankingService() {
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
		weaponRanking = new WeaponRanking();
		weaponRanking.setParams(memoriaDataSet, itemDataSet, fitnessCalculator,
				party, memoriaIndex);
		weaponRanking.run();

		mLogger.info("武器ランキング=" + weaponRanking.getFitnesses());
	}

	public List<ItemFitness> getRanking() {
		return weaponRanking.getFitnesses();
	}

}
