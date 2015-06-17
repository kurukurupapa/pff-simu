package com.kurukurupapa.pff.service;

import org.springframework.stereotype.Component;

/**
 * XXXXXの実装クラスです。
 */
@Component
public class HelloServiceImpl implements HelloService {

    @Override
    public void run() {
        System.out.println("Hello World!");
    }

}
