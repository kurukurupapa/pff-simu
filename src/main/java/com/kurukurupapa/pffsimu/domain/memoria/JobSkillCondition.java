package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * ジョブスキル発動条件クラス
 */
public class JobSkillCondition {
	// 条件なし
	public static final JobSkillCondition NONE = new JobSkillCondition();

	public static final JobSkillCondition FIRST_ATTACK = new JobSkillCondition();
	public static final JobSkillCondition LAST_ATTACK = new JobSkillCondition();

	// ブレイク時に一度だけ
	public static final JobSkillCondition BREAK_ONE = new JobSkillCondition();

	static {
		FIRST_ATTACK.mExclusive = true;
		FIRST_ATTACK.mFirstAttack = true;

		LAST_ATTACK.mExclusive = true;
		LAST_ATTACK.mLastAttack = true;

		BREAK_ONE.mBreakOne = true;
	}

	private boolean mExclusive;
	private boolean mFirstAttack;
	private boolean mLastAttack;
	private boolean mBreakOne;

	private JobSkillCondition() {
	}

	/**
	 * 排他的な発動条件であるか判定する。
	 *
	 * @return 排他的な場合true
	 */
	public boolean isExclusiveCondition() {
		return mExclusive;
	}

	public boolean isFirstAttack() {
		return mFirstAttack;
	}

	public boolean isLastAttack() {
		return mLastAttack;
	}

	public boolean isBreakOne() {
		return mBreakOne;
	}
}
