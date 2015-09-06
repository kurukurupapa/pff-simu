package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * リーダースキル回復効果クラス
 */
public class LeaderSkillRecoveryEffect extends LeaderSkillEffect {

	private int mEffect;

	/**
	 * コンストラクタ
	 *
	 * @param effect
	 *            回復効果（%）
	 */
	public LeaderSkillRecoveryEffect(int effect) {
		mEffect = effect;
	}

	/**
	 * 回復効果を適用します。
	 *
	 * @param recovery
	 *            リーダースキル適用前の回復値
	 * @return 回復値
	 */
	public float calc(float recovery) {
		return recovery * (1f + mEffect / 100f);
	}

}
