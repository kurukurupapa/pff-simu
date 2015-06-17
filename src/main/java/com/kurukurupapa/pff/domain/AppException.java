package com.kurukurupapa.pff.domain;


public class AppException extends RuntimeException {

    public AppException(String msg) {
        super(msg);
    }

    public AppException(String msg, Exception e) {
        super(msg, e);
    }

}
