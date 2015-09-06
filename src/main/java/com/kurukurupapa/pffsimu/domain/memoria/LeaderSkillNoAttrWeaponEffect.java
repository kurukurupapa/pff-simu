package com.kurukurupapa.pffsimu.domain.memoria;

import com.kurukurupapa.pffsimu.domain.item.ItemData;

/**
 * リーダースキル無属性武器攻撃効果クラス
 */
public class LeaderSkillNoAttrWeaponEffect extends LeaderSkillEffect {
	private int mEffect;

	/**
	 * コンストラクタ
	 *
	 * @param effect
	 *            無属性武器攻撃効果（%）
	 */
	public LeaderSkillNoAttrWeaponEffect(int effect) {
		mEffect = effect;
	}

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
		ItemData weapon = memoria.getWeapon();
		if (weapon != null && weapon.getAttr().isNone()) {
			return damage * (1f + mEffect / 100f);
		}
		return damage;
	}
}
