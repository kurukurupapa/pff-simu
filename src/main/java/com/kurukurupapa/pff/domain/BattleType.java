package com.kurukurupapa.pff.domain;

/**
 * バトル形式enum
 */
public enum BattleType {
	NORMAL("バランス"), //
	ATTACK("攻撃重視"), //
	RECOVERY("回復重視"), //
	HP_DEFENCE_RECOVERY("HP・防御・回復重視"), //
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
