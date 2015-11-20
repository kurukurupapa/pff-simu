package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.ranking.WeaponRanking;

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
		itemDataSet.readUserFile(true);
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
