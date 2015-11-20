package com.kurukurupapa.pffsimu.web.ranking;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.ranking.MemoriaRanking;

/**
 * ランキング機能 メモリアランキングサービスクラス
 */
@Service
public class MemoriaRankingService {
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private FitnessCalculator fitnessCalculator;
	private Party party;
	private MemoriaRankingForm form;
	private MemoriaRanking memoriaRanking;

	public MemoriaRankingService() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile(true);
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

	public void setup(MemoriaRankingForm form) {
		this.form = form;
		setup(form.getFitnessCalculator(), new Party());
	}

	public void run() {
		memoriaRanking = new MemoriaRanking();
		memoriaRanking.setParams(memoriaDataSet, itemDataSet, fitnessCalculator, party);
		if (form != null) {
			memoriaRanking.setLeaderSkillFlag(form.isLeaderSkillFlag());
			memoriaRanking.setPremiumSkillFlag(form.isPremiumSkillFlag());
			memoriaRanking.setJobSkillFlag(form.isJobSkillFlag());
		}
		memoriaRanking.run();
	}

	public List<MemoriaFitness> getRanking() {
		return memoriaRanking.getFitnesses();
	}

}
