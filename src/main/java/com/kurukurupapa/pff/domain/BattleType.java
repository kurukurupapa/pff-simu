package com.kurukurupapa.pff.domain;

/**
 * バトル形式enum
 */
public enum BattleType {
	NORMAL("通常バトル"), //
	EXA_BATTLIA("エクサバトリア"), //
	;

	private String mText;

	private BattleType(String text) {
		mText = text;
	}

	/**
	 * enum定数名を取得します。（Thymeleaf用です。）
	 * 
	 * @return 文字列
	 */
	public String getName() {
		return name();
	}

	public String getText() {
		return mText;
	}
}
