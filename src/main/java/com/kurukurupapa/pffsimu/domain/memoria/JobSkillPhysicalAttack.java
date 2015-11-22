package com.kurukurupapa.pffsimu.domain.memoria;

import org.apache.commons.lang3.Validate;

/**
 * ジョブスキル（物理攻撃系）
 */
public class JobSkillPhysicalAttack extends JobSkill {

	private float mAttackRate;

	public JobSkillPhysicalAttack(String name, JobSkillCondition condition, float attackRate) {
		super(name, condition);
		mAttackRate = attackRate;

		Validate.isTrue(attackRate >= 1.0f);
	}

	/**
	 * 物理攻撃に効果があるか判定します。
	 *
	 * @return 効果がある場合true
	 */
	@Override
	public boolean isPhysicalAttack() {
		return true;
	}

	/**
	 * 物理攻撃に対する効果を適用します。
	 *
	 * @param damage
	 *            ジョブスキル適用前の物理攻撃値
	 * @return 物理攻撃値
	 */
	@Override
	public float calcPhysicalAttackDamage(float physicalDamage) {
		return physicalDamage * mAttackRate;
	}
}
