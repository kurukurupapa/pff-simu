package com.kurukurupapa.pff.domain;

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
