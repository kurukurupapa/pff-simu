package com.kurukurupapa.pffsimu.web.partyfinder;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.partyfinder.PartyFinder;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl1.Dp01;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl2.PartyFinder2d;

/**
 * パーティ検討機能 サービスクラス
 */
@Service
public class PartyFinderService {

	private PartyFinderForm form;
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private FitnessCalculator fitness;
	private PartyFinder partyFinder;

	public void set(PartyFinderForm form) {
		this.form = form;
	}

	public void run() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.readUserFile();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();

		// パーティ洗い出し
		fitness = new FitnessCalculator();
		fitness.setBattleType(form.getBattleTypeObj());
		for (String e : form.getEnemyWeakPoints()) {
			fitness.addEnemyWeak(Attr.parse(e));
		}
		for (String e : form.getEnemyStrongPoints()) {
			fitness.addEnemyResistance(Attr.parse(e));
		}
		fitness.setEnemyPhysicalResistance(form.getPhysicalResistance());
		fitness.setEnemyMagicResistance(form.getMagicResistance());
		switch (form.getAlgorithmId()) {
		case ALGORITHM1:
			partyFinder = new Dp01(memoriaDataSet, itemDataSet, fitness);
			break;
		case ALGORITHM2:
			partyFinder = new PartyFinder2d(memoriaDataSet, itemDataSet,
					fitness);
			break;
		}
		partyFinder.run();
	}

	public Party getParty() {
		return partyFinder.getParty();
	}

}
