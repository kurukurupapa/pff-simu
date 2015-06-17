package com.kurukurupapa.pff.cli;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.cli.Main;

/**
 * Mainのテストクラスです。
 */
public class MainTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        // 準備

        // テスト実行
        Main.main(new String[] {});

        // 検証
        // 例外が発生しなければOK
    }

}
