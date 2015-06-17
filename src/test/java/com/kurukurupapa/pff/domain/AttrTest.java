package com.kurukurupapa.pff.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AttrTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIsNoneTrue() {
        // 準備
        Attr sut = Attr.NONE;
        // テスト実行
        boolean actual = sut.isNone();
        // 検証
        assertThat(actual, is(true));
    }

    @Test
    public void testIsNoneFalse() {
        // 準備
        Attr sut = Attr.FIRE;
        // テスト実行
        boolean actual = sut.isNone();
        // 検証
        assertThat(actual, is(false));
    }

}
