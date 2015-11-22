package com.kurukurupapa.pffsimu.domain.memoria;

import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * ジョブスキルクラス
 */
public abstract class JobSkill {
	private String mName;
	private JobSkillCondition mCondition;

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

	protected JobSkill(String name, JobSkillCondition condition) {
		mName = name.trim();
		mCondition = condition;
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

	protected String getName() {
		return mName;
	}

	/**
	 * 排他的な発動条件であるか判定する。
	 *
	 * @return 排他的な場合true
	 */
	public boolean isExclusiveCondition() {
		return mCondition.isExclusiveCondition();
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
		return mCondition.isFirstAttack();
	}

	public boolean isLastAttackCondition() {
		return mCondition.isLastAttack();
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
		return false;
	}

	/**
	 * 物理攻撃に対する効果を適用します。
	 *
	 * @param damage
	 *            ジョブスキル適用前の物理攻撃値
	 * @return 物理攻撃値
	 */
	public float calcPhysicalAttackDamage(float physicalDamage) {
		return physicalDamage;
	}

	/**
	 * 回復魔法に効果があるか判定します。
	 *
	 * @return 効果がある場合true
	 */
	public boolean isRecoveryMagic() {
		return false;
	}

	/**
	 * 回復魔法に対する効果を適用します。
	 *
	 * @param recoveryDamage
	 *            ジョブスキル適用前の回復値
	 * @return 回復値
	 */
	public float calcRecoveryMagicDamage(float recoveryDamage) {
		return recoveryDamage;
	}

	public boolean isBlackMagicAttack() {
		return false;
	}

	public boolean isWhiteMagicAttack() {
		return false;
	}

	public float getBlackMagicAttackDamage(int intelligence, float magicAttack) {
		return 0;
	}

	public float getWhiteMagicAttackDamage(int intelligence, float magicAttack) {
		return 0;
	}

	/**
	 * 回避率を適用します。
	 *
	 * @param kaihiRate
	 *            ジョブスキル適用前の回避率
	 * @return
	 */
	public float calcKaihiRate(float kaihiRate) {
		return kaihiRate;
	}

	/**
	 * 力を適用します。
	 *
	 * @param value
	 *            ジョブスキル適用前の力
	 * @return
	 */
	public float calcPower(int value) {
		return value;
	}

	public boolean isBreakOne() {
		return mCondition.isBreakOne();
	}

}
