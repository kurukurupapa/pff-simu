package com.kurukurupapa.pff.domain;

import java.util.List;

/**
 * アクセサリアイテムデータ拡張クラス
 */
public class AccessoryItemDataEx extends ItemDataEx {

	/**
	 * コンストラクタ
	 *
	 * @param itemData
	 *            アイテムデータ
	 * @param itemType
	 *            アイテム種別
	 * @param itemType2
	 *            アイテム種別2
	 * @param attr
	 *            属性
	 */
	public AccessoryItemDataEx(ItemData itemData, ItemType itemType,
			ItemType2 itemType2, Attr attr) {
		super(itemData, itemType, itemType2, attr);
	}

	@Override
	public float getBoostValueForPhysical(List<Attr> weakList) {
		// アクセサリでは指輪のみブーストします。
		// 属性があれば、指輪であると判断します。
		boolean attr = mAttr.isAttrWithoutFlight(weakList);
		if (attr) {
			return 0.5f;
		} else {
			return 0.0f;
		}
	}

	@Override
	public float getBoostValueForBlackMagic(List<Attr> weakList) {
		boolean attr = mAttr.isAttrWithoutFlight(weakList);
		boolean boost = mItemType2.isBoost();
		return attr && boost ? 0.5f : 0;
	}

}
