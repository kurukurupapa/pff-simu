package com.kurukurupapa.pffsimu.domain.item;

import com.kurukurupapa.pffsimu.domain.AppException;


/**
 * 魔法種別enumクラス
 */
public enum MagicType {
	/** 魔法なし */
	NONE("魔法なし"),
	/** 白魔法 */
	WHITE("白魔法"),
	/** 黒魔法 */
	BLACK("黒魔法"),
	/** 召喚魔法 */
	SUMMON("召喚魔法"),
	//
	;

	private String mText;

	public static MagicType parse(String text) {
		for (MagicType e : values()) {
			if (e.getText().equals(text.trim())) {
				return e;
			}
		}
		throw new AppException("不正な引数です。値=" + text);
	}

	private MagicType(String text) {
		mText = text;
	}

	public String getText() {
		return mText;
	}

}
