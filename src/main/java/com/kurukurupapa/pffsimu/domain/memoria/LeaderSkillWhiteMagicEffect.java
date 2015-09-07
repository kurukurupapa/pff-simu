package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * リーダースキル白魔法効果クラス
 */
public class LeaderSkillWhiteMagicEffect extends LeaderSkillEffect {

	private int mEffect;

	/**
	 * コンストラクタ
	 *
	 * @param effect
	 *            白魔法効果（%）
	 */
	public LeaderSkillWhiteMagicEffect(int effect) {
		mEffect = effect;
	}

	/**
	 * 白魔法効果を適用します。
	 *
	 * @param recovery
	 *            リーダースキル適用前の回復値
	 * @return 回復値
	 */
	@Override
	public float calcWhiteMagic(float recovery) {
		return recovery * (1f + mEffect / 100f);
	}
}
