package com.kurukurupapa.pff.ga01.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class PartyTest {

    private static MemoriaDataSet mMemoriaDataSet;
    private static ItemDataSet mItemDataSet;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // データ読み込み
        mItemDataSet = new ItemDataSet();
        mItemDataSet.read();
        mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
        mMemoriaDataSet.readTestFile();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testPartyMemoriaDataSetItemDataSet() {
        // 準備

        // テスト実行
        Party sut = new Party(mMemoriaDataSet, mItemDataSet);
        Gene[] sutGeneArr = sut.getGeneArr();

        // 検証

        // 遺伝子の数を確認
        int geneCount = 0;
        for (MemoriaData e : mMemoriaDataSet) {
            geneCount = 1 + e.getNumWeapons() + e.getNumAccessories();
        }
        assertThat(sutGeneArr.length, is(geneCount));

        // 遺伝子にランダム値が設定されていること
        int valueCount = 0;
        for (Gene e : sutGeneArr) {
            assertNotNull(e);
            if (0.0 < e.getRate() && e.getRate() < 1.0) {
                valueCount++;
            }
        }
        assertTrue(valueCount >= 1);
    }

    @Test
    public void testPartyMemoriaDataSetItemDataSetPartyPartyInt_01() {
        // 準備
        Party parent1 = new Party(mMemoriaDataSet, mItemDataSet);
        Party parent2 = new Party(mMemoriaDataSet, mItemDataSet);
        int num = parent1.getNumGenes();

        // テスト実行
        Party sut = new Party(mMemoriaDataSet, mItemDataSet, parent1, parent2,
                0);
        Gene[] sutGeneArr = sut.getGeneArr();

        // 検証
        Gene[] parent2GeneArr = parent2.getGeneArr();
        int count = 0;
        for (int i = 0; i < num; i++) {
            if (sutGeneArr[i].getRate() != parent2GeneArr[i].getRate()) {
                count++;
            }
        }
        assertTrue(count <= 1);
    }

    @Test
    public void testPartyMemoriaDataSetItemDataSetPartyPartyInt_02() {
        // 準備
        Party parent1 = new Party(mMemoriaDataSet, mItemDataSet);
        Party parent2 = new Party(mMemoriaDataSet, mItemDataSet);
        int num = parent1.getNumGenes();

        // テスト実行
        Party sut = new Party(mMemoriaDataSet, mItemDataSet, parent1, parent2,
                num);
        Gene[] sutGeneArr = sut.getGeneArr();

        // 検証
        Gene[] parent1GeneArr = parent1.getGeneArr();
        int count = 0;
        for (int i = 0; i < num; i++) {
            if (sutGeneArr[i].getRate() != parent1GeneArr[i].getRate()) {
                count++;
            }
        }
        assertTrue(count <= 1);
    }

}
