package com.kurukurupapa.pff.domain;

/**
 * 白魔法アイテムデータ拡張クラス
 */
public class WhiteMagicItemDataEx extends MagicItemDataEx {

	public WhiteMagicItemDataEx(ItemData itemData, ItemType itemType,
			ItemType2 itemType2, Attr attr, int magicCharge, int magicEffect) {
		super(itemData, itemType, itemType2, attr, magicCharge, magicEffect);
	}

	/**
	 * 白魔法回復量を取得します。
	 *
	 * @return 回復量
	 */
	public float getRecovery(int intelligence, float magicAttack) {
		// 回復量＝(知性＋効果値)×魔法倍率
		// http://wikiwiki.jp/pictlogicaff/?%C0%EF%C6%AE%BE%F0%CA%F3#measure
		// ※祈りメメントで白魔法 1.25を前提とします。
		return (intelligence + mMagicEffect) * magicAttack
				* Mement.INORI_WHITE_RATE;
	}

}
