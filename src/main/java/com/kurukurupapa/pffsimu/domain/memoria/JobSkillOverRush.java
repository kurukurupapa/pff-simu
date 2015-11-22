package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * ジョブスキル（オーバーラッシュ）
 */
public class JobSkillOverRush extends JobSkill {

	public JobSkillOverRush() {
		super("オーバーラッシュ", JobSkillCondition.BREAK_ONE);
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
		return physicalDamage * 2.8f + 5000;
	}

}
