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
public class FakeParty2 extends Party {

	public FakeParty2() {
		super();
	}

	public FakeParty2(Memoria memoria) {
		super(memoria);
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
		if (!LeaderSkill.validStructue(this)) {
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
				// 同一メモリアオブジェクト
				if (m1obj.getMemoriaData() == m2obj.getMemoriaData()) {
					return false;
				}
				// 武器
				if (m1obj.getWeapon() == m2obj.getWeapon()) {
					return false;
				}
				// アクセサリ
				ItemData[] m1ma = m1obj.getAccessories();
				ItemData[] m2ma = m2obj.getAccessories();
				if (m1ma[0] == m2ma[0] || m1ma[0] == m2ma[1]
						|| m1ma[1] == m2ma[0] || m1ma[1] == m2ma[1]) {
					return false;
				}
			}
		}
		return true;
	}

}
