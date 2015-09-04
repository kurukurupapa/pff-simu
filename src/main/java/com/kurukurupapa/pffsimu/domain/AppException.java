package com.kurukurupapa.pffsimu.domain;

/**
 * 当アプリケーション用例外クラス
 */
public class AppException extends RuntimeException {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String msg) {
		super(msg);
	}

	public AppException(String msg, Exception e) {
		super(msg, e);
	}

}
