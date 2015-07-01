package com.kurukurupapa.pffsimu.web.partymaker;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.kurukurupapa.pff.dp01.AccessoryFitness;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.MemoriaFitnessValue;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.dp01.WeaponFitness;

/**
 * パーティメーカー機能用 セッションヘルパークラス
 */
public class SessionHelper {
	private static final String KEY_PARTY = Party.class.getName();
	private static final String KEY_CONDITION = ConditionForm.class.getName();
	private static final String KEY_TARGET = TargetForm.class.getName();
	private static final String KEY_MEMORIA_RANKING = MemoriaFitnessValue.class
			.getName();
	private static final String KEY_WEAPON_RANKING = WeaponFitness.class
			.getName();
	private static final String KEY_MAGIC_ACCESSORY_RANKING = ItemFitness.class
			.getName();

	private HttpSession session;

	public SessionHelper(HttpSession session) {
		this.session = session;
	}

	public Party getParty() {
		return (Party) session.getAttribute(KEY_PARTY);
	}

	public void setParty(Party party) {
		session.setAttribute(KEY_PARTY, party);
	}

	public ConditionForm getCondition() {
		return (ConditionForm) session.getAttribute(KEY_CONDITION);
	}

	public void setCondition(ConditionForm form) {
		session.setAttribute(KEY_CONDITION, form);
	}

	public TargetForm getTarget() {
		return (TargetForm) session.getAttribute(KEY_TARGET);
	}

	public void setTarget(TargetForm form) {
		session.setAttribute(KEY_TARGET, form);
	}

	public List<MemoriaFitnessValue> getMemoriaRanking() {
		return (List<MemoriaFitnessValue>) session
				.getAttribute(KEY_MEMORIA_RANKING);
	}

	public void setMemoriaRanking(List<MemoriaFitnessValue> ranking) {
		session.setAttribute(KEY_MEMORIA_RANKING, ranking);
	}

	public List<WeaponFitness> getWeaponRanking() {
		return (List<WeaponFitness>) session.getAttribute(KEY_WEAPON_RANKING);
	}

	public void setWeaponRanking(List<WeaponFitness> ranking) {
		session.setAttribute(KEY_WEAPON_RANKING, ranking);
	}

	public List<ItemFitness> getMagicAccessoryRanking() {
		return (List<ItemFitness>) session
				.getAttribute(KEY_MAGIC_ACCESSORY_RANKING);
	}

	public void setMagicAccessoryRanking(List<ItemFitness> ranking) {
		session.setAttribute(KEY_MAGIC_ACCESSORY_RANKING, ranking);
	}

}
