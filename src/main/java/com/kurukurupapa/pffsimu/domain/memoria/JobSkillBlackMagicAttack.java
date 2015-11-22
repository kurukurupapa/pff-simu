package com.kurukurupapa.pffsimu.domain.memoria;

import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pffsimu.domain.Mement;

/**
 * ジョブスキル（黒魔法攻撃系）
 */
public class JobSkillBlackMagicAttack extends JobSkill {

	private int mAdditionalIntelligence;

	public JobSkillBlackMagicAttack(String name, JobSkillCondition condition, int additionalIntelligence) {
		super(name, condition);
		mAdditionalIntelligence = additionalIntelligence;

		Validate.isTrue(mAdditionalIntelligence > 0);
	}

	@Override
	public boolean isBlackMagicAttack() {
		return true;
	}

	@Override
	public float getBlackMagicAttackDamage(int intelligence, float magicAttack) {
		return (intelligence + mAdditionalIntelligence) * magicAttack * Mement.CHIE_BLACK_RATE;
	}
}
