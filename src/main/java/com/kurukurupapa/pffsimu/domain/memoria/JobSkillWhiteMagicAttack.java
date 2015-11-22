package com.kurukurupapa.pffsimu.domain.memoria;

import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pffsimu.domain.Mement;

/**
 * ジョブスキル（白魔法攻撃系）
 */
public class JobSkillWhiteMagicAttack extends JobSkill {

	private int mAdditionalIntelligence;

	public JobSkillWhiteMagicAttack(String name, JobSkillCondition condition, int additionalIntelligence) {
		super(name, condition);
		mAdditionalIntelligence = additionalIntelligence;

		Validate.isTrue(mAdditionalIntelligence > 0);
	}

	@Override
	public boolean isWhiteMagicAttack() {
		return true;
	}

	@Override
	public float getWhiteMagicAttackDamage(int intelligence, float magicAttack) {
		return (intelligence + mAdditionalIntelligence) * magicAttack * Mement.INORI_WHITE_RATE;
	}
}
