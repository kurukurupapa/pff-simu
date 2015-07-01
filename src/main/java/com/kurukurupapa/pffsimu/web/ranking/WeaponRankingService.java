package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.Fitness;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.dp01.WeaponFitness;
import com.kurukurupapa.pff.dp01.WeaponRanking;

/**
 * ランキング機能 武器ランキングサービスクラス
 */
@Service
public class WeaponRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private Fitness fitness;
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

	public void setup(Fitness fitness, Party party, int memoriaIndex) {
		this.fitness = fitness;
		this.party = party;
		this.memoriaIndex = memoriaIndex;
	}

	public void run() {
		weaponRanking = new WeaponRanking();
		weaponRanking.setParams(memoriaDataSet, itemDataSet, fitness, party,
				memoriaIndex);
		weaponRanking.run();
	}

	public List<WeaponFitness> getRanking() {
		return weaponRanking.getFitnesses();
	}

}
