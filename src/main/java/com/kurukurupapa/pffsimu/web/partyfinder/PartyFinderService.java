package com.kurukurupapa.pffsimu.web.partyfinder;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.Dp01;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.partyfinder.PartyFinder;
import com.kurukurupapa.pff.partyfinder2.PartyFinder2d;

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
