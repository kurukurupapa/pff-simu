package com.kurukurupapa.pffsimu.domain;



/**
 * パラメータ値の単位enum
 */
public enum Unit {
	/** 値 */
	VALUE,
	/** パーセンテージ */
	PERCENT,
	//
	;

	public String getText(int number) {
		String text;
		switch (this) {
		case VALUE:
			text = Integer.toString(number);
			break;
		case PERCENT:
			text = number + "%";
			break;
		default:
			throw new AppException("想定外の単位です。単位=" + this);
		}
		return text;
	}
}
