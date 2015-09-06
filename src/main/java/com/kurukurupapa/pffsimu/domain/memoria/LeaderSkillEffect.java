package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * リーダースキル効果クラス
 */
public class LeaderSkillEffect {

	/**
	 * 物理攻撃効果を適用します。
	 *
	 * @param damage
	 *            リーダースキル適用前のダメージ
	 * @param memoria
	 *            メモリア
	 * @return ダメージ
	 */
	public float calcPhysicalAttackDamage(float damage, Memoria memoria) {
		return damage;
	}

	/**
	 * 回復効果を適用します。
	 *
	 * @param recovery
	 *            リーダースキル適用前の回復値
	 * @return 回復値
	 */
	public float calcRecovery(float recovery) {
		return recovery;
	}

	/**
	 * 白魔法効果を適用します。
	 *
	 * @param recovery
	 *            リーダースキル適用前の回復値
	 * @return 回復値
	 */
	public float calcWhiteMagic(float recovery) {
		return recovery;
	}

}
