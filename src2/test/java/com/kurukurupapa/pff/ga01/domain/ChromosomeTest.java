package com.kurukurupapa.pff.ga01.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.ga01.domain.PartyChromosome;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

public class ChromosomeTest {

    private class TestingChromosome extends PartyChromosome {
        public TestingChromosome(MemoriaDataSet memoriaDataSet,
                ItemDataSet itemDataSet) {
            super(memoriaDataSet, itemDataSet);
        }
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testIsMutation() {
        // 準備

        // テスト実行＆検証
        assertThat(PartyChromosome.isMutation(0.00f, 0.01f), is(true));
        assertThat(PartyChromosome.isMutation(0.01f, 0.01f), is(true));
        assertThat(PartyChromosome.isMutation(0.02f, 0.01f), is(false));
        assertThat(PartyChromosome.isMutation(1.00f, 0.01f), is(false));
    }

}
