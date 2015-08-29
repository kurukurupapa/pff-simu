package com.kurukurupapa.pff.partyfinder2;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.LeaderSkill;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;

/**
 * 疑似パーティクラス
 * 
 * 重複するメモリアやアイテム、発動しないかもしれないリーダースキルなどを設定したあと、妥当性チェックができるパーティクラスです。
 */
public class FakeParty extends Party implements Cloneable {

	public FakeParty() {
		super();
	}

	public FakeParty(Memoria memoria) {
		super(memoria);
	}

	public FakeParty(FakeParty other) {
		super(other);
	}

	@Override
	public FakeParty clone() {
		return new FakeParty(this);
	}

	@Override
	public void calcFitness(FitnessCalculator fitnessCalculator) {
		mFitnessValue = fitnessCalculator.calc(this);
	}

	/**
	 * 妥当性をチェックします。
	 * 
	 * @return 妥当な場合true。
	 */
	public boolean valid() {
		// メモリア・アイテム
		if (!validMemoriaItem()) {
			return false;
		}
		// リーダースキル
		if (!LeaderSkill.validOrNone(this)) {
			return false;
		}
		return true;
	}

	/**
	 * 引数のメモリアが追加可能か判定します。
	 * 
	 * @param memoria
	 *            メモリア
	 * @return 追加可能な場合true
	 */
	public boolean valid(Memoria memoria) {
		// メモリア・アイテム重複チェック
		for (int i = 0; i < mMemoriaList.size(); i++) {
			Memoria tmp = mMemoriaList.get(i);
			if (!validMemoriaItem(memoria, tmp)) {
				return false;
			}
		}
		// リーダースキル
		if (!LeaderSkill.validOrNone(this, memoria)) {
			return false;
		}
		return true;
	}

	private boolean validMemoriaItem() {
		// メモリア・アイテム重複チェック
		for (int m1 = 0; m1 < mMemoriaList.size() - 1; m1++) {
			for (int m2 = m1 + 1; m2 < mMemoriaList.size(); m2++) {
				Memoria m1obj = mMemoriaList.get(m1);
				Memoria m2obj = mMemoriaList.get(m2);
				if (!validMemoriaItem(m1obj, m2obj)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean validMemoriaItem(Memoria memoria1, Memoria memoria2) {
		// 同一メモリアオブジェクト
		if (memoria1.getMemoriaData() == memoria2.getMemoriaData()) {
			return false;
		}
		// 武器
		if (memoria1.getWeapon() == memoria2.getWeapon()) {
			return false;
		}
		// アクセサリ
		ItemData[] m1ma = memoria1.getAccessories();
		ItemData[] m2ma = memoria2.getAccessories();
		if (m1ma[0] == m2ma[0] || m1ma[0] == m2ma[1] || m1ma[1] == m2ma[0]
				|| m1ma[1] == m2ma[1]) {
			return false;
		}
		return true;
	}

}
