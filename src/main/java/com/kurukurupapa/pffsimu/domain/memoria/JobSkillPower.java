package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * ジョブスキル（力上昇系）
 */
public class JobSkillPower extends JobSkill {

	private float mPowerRate;

	public JobSkillPower(String name, JobSkillCondition condition, float powerRate) {
		super(name, condition);
		mPowerRate = powerRate;
	}

	@Override
	public float calcPower(int value) {
		return value * mPowerRate;
	}

}
