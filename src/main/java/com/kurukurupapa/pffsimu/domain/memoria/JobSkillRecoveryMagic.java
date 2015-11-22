package com.kurukurupapa.pffsimu.domain.memoria;

import org.apache.commons.lang3.Validate;

/**
 * ジョブスキル（回復魔法系）
 */
public class JobSkillRecoveryMagic extends JobSkill {

	private float mRecoveryRate;

	public JobSkillRecoveryMagic(String name, JobSkillCondition condition, float recoveryRate) {
		super(name, condition);
		mRecoveryRate = recoveryRate;

		Validate.isTrue(recoveryRate >= 1.0f);
	}

	/**
	 * 回復魔法に効果があるか判定します。
	 *
	 * @return 効果がある場合true
	 */
	@Override
	public boolean isRecoveryMagic() {
		return true;
	}

	/**
	 * 回復魔法に対する効果を適用します。
	 *
	 * @param recoveryDamage
	 *            ジョブスキル適用前の回復値
	 * @return 回復値
	 */
	@Override
	public float calcRecoveryMagicDamage(float recoveryDamage) {
		return recoveryDamage * mRecoveryRate;
	}
}
