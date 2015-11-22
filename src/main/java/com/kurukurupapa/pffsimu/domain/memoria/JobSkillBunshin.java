package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * ジョブスキル（分身）
 */
public class JobSkillBunshin extends JobSkill {

	protected JobSkillBunshin() {
		super("分身", JobSkillCondition.FIRST_ATTACK);
	}

	@Override
	public float calcKaihiRate(float kaihiRate) {
		// 100%回避
		return 1f;
	}

}
