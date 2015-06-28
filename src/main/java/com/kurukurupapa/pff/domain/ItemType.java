package com.kurukurupapa.pff.domain;

/**
 * アイテム種別enumクラス
 */
public enum ItemType {
	/** 武器 */
	WEAPON("武器"),
	/** 魔法 */
	MAGIC("魔法"),
	/** アクセサリ */
	ACCESSORY("アクセサリ"),
	//
	;

	private String mText;

	private ItemType(String text) {
		mText = text;
	}

	public String getText() {
		return mText;
	}

	public static ItemType parse(String text) {
		for (ItemType e : values()) {
			if (e.getText().equals(text)) {
				return e;
			}
		}
		throw new AppException("不正な引数です。text=" + text);
	}
}
