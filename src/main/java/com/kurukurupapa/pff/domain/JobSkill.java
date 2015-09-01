package com.kurukurupapa.pff.domain;

import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;

/**
 * ジョブスキルクラス
 */
public class JobSkill {
	private String mName;

	/**
	 * 引数のジョブスキルと当オブジェクトが排他的な発動条件であるか判定する。
	 * 
	 * @param arg1
	 *            ジョブスキル
	 * @param arg2
	 *            ジョブスキル
	 * @return 排他的な場合true
	 */
	public static boolean isExclusiveCondition(JobSkill arg1, JobSkill arg2) {
		if (arg1 == null) {
			return false;
		}
		return arg1.isExclusiveCondition(arg2);
	}

	/**
	 * 引数のパーティに含まれるジョブスキルが妥当であるか判定します。 パーティにジョブスキルが含まれていない場合も妥当とします。
	 * 
	 * @param party
	 *            パーティ
	 * @return 妥当な場合true
	 */
	public static boolean validOrNone(Party party) {
		int size = party.getMemoriaList().size();
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				JobSkill jobSkill1 = party.getMemoria(i).getJobSkill();
				JobSkill jobSkill2 = party.getMemoria(j).getJobSkill();
				if (JobSkill.isExclusiveCondition(jobSkill1, jobSkill2)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 引数のパーティに対して、引数のメモリアのジョブスキルが妥当であるか判定します。
	 * パーティやメモリアにジョブスキルが含まれていない場合も妥当とします。
	 * 
	 * @param party
	 *            パーティ
	 * @param memoria
	 *            メモリア
	 * @return 妥当な場合true
	 */
	public static boolean validOrNone(Party party, Memoria memoria) {
		JobSkill jobSkill = memoria.getJobSkill();
		for (Memoria e : party.getMemoriaList()) {
			if (JobSkill.isExclusiveCondition(jobSkill, e.getJobSkill())) {
				return false;
			}
		}
		return true;
	}

	public JobSkill(String name) {
		mName = name.trim();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof JobSkill)) {
			return false;
		}
		JobSkill other = (JobSkill) obj;
		return mName.equals(other.mName);
	}

	@Override
	public String toString() {
		return mName;
	}

	/**
	 * 排他的な発動条件であるか判定する。
	 * 
	 * @return 排他的な場合true
	 */
	public boolean isExclusiveCondition() {
		return isFirstAttackCondition() || isLastAttackCondition();
	}

	/**
	 * 引数のジョブスキルと当オブジェクトが排他的な発動条件であるか判定する。
	 * 
	 * @param other
	 *            ジョブスキル
	 * @return 排他的な場合true
	 */
	public boolean isExclusiveCondition(JobSkill other) {
		if (other == null) {
			return false;
		}
		if (isFirstAttackCondition() && other.isFirstAttackCondition()) {
			return true;
		}
		if (isLastAttackCondition() && other.isLastAttackCondition()) {
			return true;
		}
		return false;
	}

	public boolean isFirstAttackCondition() {
		return "居合い抜き".equals(mName) || "分身".equals(mName)
				|| "乱れ撃ち".equals(mName) || "スロット".equals(mName);
	}

	public boolean isLastAttackCondition() {
		// TODO 未実装
		return false;
	}

	public boolean isFurea() {
		return "フレア".equals(mName);
	}

	public boolean isHoly() {
		return "ホーリー".equals(mName);
	}

	/**
	 * 物理攻撃に効果があるか判定します。
	 * 
	 * @return 効果がある場合true
	 */
	public boolean isPhysicalAttack() {
		return "居合い抜き".equals(mName) || "乱れ撃ち".equals(mName)
				|| "ジャンプ".equals(mName);
	}

	/**
	 * 物理攻撃に対する効果を適用します。
	 * 
	 * @param damage
	 *            ジョブスキル適用前の物理攻撃値
	 * @return 物理攻撃値
	 */
	public float calcPhysicalAttackDamage(float physicalDamage) {
		float damage = physicalDamage;
		if (mName.equals("居合い抜き")) {
			// 居合い抜き
			// 自身が一番初めの攻撃を行うと与ダメージ50%アップの居合い抜きを発動
			damage *= 1.5f;
		} else if (mName.equals("乱れ撃ち")) {
			// 乱れ撃ち
			// 自身が一番初めの攻撃を行うと2～4回の連続攻撃を発動。1回あたりの与ダメージは通常の60%。
			// ※平均3回攻撃と考えます。
			damage *= 3 * 0.60f;
		} else if (mName.equals("ジャンプ")) {
			// ジャンプ
			// ブレイク時に攻撃すると与ダメージ20%アップのジャンプを発動
			// TODO とりあえず、1バトルの半分のターンをブレイク中と考える。
			damage *= 1.1f;
		}
		return damage;
	}

	/**
	 * 回復魔法に効果があるか判定します。
	 * 
	 * @return 効果がある場合true
	 */
	public boolean isRecoveryMagic() {
		return "オーラ".equals(mName);
	}

	/**
	 * 回復魔法に対する効果を適用します。
	 * 
	 * @param recoveryDamage
	 *            ジョブスキル適用前の回復値
	 * @return 回復値
	 */
	public float calcRecoveryMagicDamage(float recoveryDamage) {
		if (mName.equals("オーラ")) {
			// オーラ
			// 自身の白魔法アビリティ効果が上昇
			// 回復量1.8倍 他の白魔法にも効果あり
			return recoveryDamage * 1.8f;
		}
		return recoveryDamage;
	}

	public float getFureaAttackDamage(int intelligence, float magicAttack) {
		return (intelligence + 100f) * magicAttack * Mement.CHIE_BLACK_RATE;
	}

	public float getHolyAttackDamage(int intelligence, float magicAttack) {
		return (intelligence + 100f) * magicAttack * Mement.INORI_WHITE_RATE;
	}

}
