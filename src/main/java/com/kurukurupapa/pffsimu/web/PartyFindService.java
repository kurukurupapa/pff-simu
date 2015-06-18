package com.kurukurupapa.pffsimu.web;

import org.springframework.stereotype.Service;

import com.kurukurupapa.pff.domain.AttrFactory;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.Dp01;
import com.kurukurupapa.pff.dp01.FitnessForBattle;
import com.kurukurupapa.pff.dp01.FitnessForExaBattlia;
import com.kurukurupapa.pff.dp01.Party;

@Service
public class PartyFindService {

	private PartyFindForm form;
	private ItemDataSet itemDataSet;
	private MemoriaDataSet memoriaDataSet;
	private FitnessForBattle fitness;
	private Dp01 dp;

	public void set(PartyFindForm form) {
		this.form = form;
	}

	public void run() {
		// データ読み込み
		itemDataSet = new ItemDataSet();
		itemDataSet.read();
		memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.readUserFile();

		// パーティ洗い出し
		if (form.getBattleType().equals(PartyFindForm.BATTLE_TYPE_EXA_BATTLIA)) {
			fitness = new FitnessForExaBattlia();
		} else {
			fitness = new FitnessForBattle();
		}
		for (String e : form.getEnemyWeakPoints()) {
			fitness.addEnemyWeak(AttrFactory.create(e));
		}
		for (String e : form.getEnemyStrongPoints()) {
			fitness.addEnemyResistance(AttrFactory.create(e));
		}
		fitness.setEnemyPhysicalResistance(form.getPhysicalResistance());
		dp = new Dp01(memoriaDataSet, itemDataSet, fitness);
		dp.run();
	}

	public Party getParty() {
		return dp.getParty();
	}

}
