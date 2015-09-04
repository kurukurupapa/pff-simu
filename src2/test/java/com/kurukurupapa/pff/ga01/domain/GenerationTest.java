package com.kurukurupapa.pff.ga01.domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

public class GenerationTest {
    private static final int MAX_GENERATION = 100;
    private static final int MAX_MEMORIA = 4;
    private static final int DEBUG_INTERVAL = 1;

    private static PopulationByRoulette mPopulation;
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
    public void testHpCriteria() {
        // 準備
        Fitness partyFitness = new FitnessForHp();
        mPopulation = new PopulationByRoulette(null, mMemoriaDataSet,
                mItemDataSet, 20, 0.01f, partyFitness);

        // HPの高い順にソート
        List<MemoriaData> list = mMemoriaDataSet.copyMemoriaDataList();
        Collections.sort(list, new Comparator<MemoriaData>() {
            @Override
            public int compare(MemoriaData o1, MemoriaData o2) {
                if (o1.getHp() == o2.getHp()) {
                    return 0;
                } else if (o1.getHp() > o2.getHp()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        int maxTotalHp = 0;
        for (int i = 0; i < Math.min(MAX_MEMORIA, list.size()); i++) {
            maxTotalHp += list.get(i).getHp();
        }

        // テスト実行
        Party party = null;
        mPopulation.init();
        System.out.println(mPopulation.getShortString());
        while (mPopulation.getCount() < MAX_GENERATION) {
            party = mPopulation.getMaxFitnessParty();
            if (party.getHp() == maxTotalHp) {
                break;
            }
            mPopulation.next();
            if (mPopulation.getCount() % DEBUG_INTERVAL == 0) {
                System.out.println(mPopulation.getShortString());
            }
        }
        System.out.println(mPopulation.toString());

        // 検証
        assertThat(party.getHp() >= maxTotalHp * 0.9, is(true));
    }

    @Test
    public void testPowerCriteria() {
        // 準備
        Fitness partyFitness = new FitnessForPower();
        mPopulation = new PopulationByRoulette(null, mMemoriaDataSet,
                mItemDataSet, 20, 0.01f, partyFitness);

        // 力の高い順にソート
        List<MemoriaData> list = mMemoriaDataSet.copyMemoriaDataList();
        Collections.sort(list, new Comparator<MemoriaData>() {
            @Override
            public int compare(MemoriaData o1, MemoriaData o2) {
                if (o1.getPower() == o2.getPower()) {
                    return 0;
                } else if (o1.getPower() > o2.getPower()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        int maxTotalPower = 0;
        for (int i = 0; i < Math.min(MAX_MEMORIA, list.size()); i++) {
            maxTotalPower += list.get(i).getPower();
        }

        // テスト実行
        Party party = null;
        mPopulation.init();
        System.out.println(mPopulation.getShortString());
        while (mPopulation.getCount() < MAX_GENERATION) {
            party = mPopulation.getMaxFitnessParty();
            if (party.getPower() == maxTotalPower) {
                break;
            }
            mPopulation.next();
            if (mPopulation.getCount() % DEBUG_INTERVAL == 0) {
                System.out.println(mPopulation.getShortString());
            }
        }
        System.out.println(mPopulation.toString());

        // 検証
        assertThat(party.getPower() >= maxTotalPower * 0.9, is(true));
    }

    @Test
    public void testAttackCriteria() {
        // 準備
        Fitness partyFitness = new FitnessForAttack();
        mPopulation = new PopulationByRoulette(null, mMemoriaDataSet,
                mItemDataSet, 20, 0.01f, partyFitness);

        // テスト実行
        mPopulation.init();
        System.out.println(mPopulation.getShortString());
        while (mPopulation.getCount() < MAX_GENERATION) {
            mPopulation.next();
            if (mPopulation.getCount() % DEBUG_INTERVAL == 0) {
                System.out.println(mPopulation.getShortString());
            }
        }
        System.out.println(mPopulation.toString());
        ;

        // 検証
    }

    @Test
    public void testBattleCriteria() {
        // 準備
        Fitness partyFitness = new FitnessForBattle();
        mPopulation = new PopulationByRoulette(null, mMemoriaDataSet,
                mItemDataSet, 20, 0.01f, partyFitness);

        // テスト実行
        mPopulation.init();
        System.out.println(mPopulation.getShortString());
        while (mPopulation.getCount() < MAX_GENERATION) {
            mPopulation.next();
            if (mPopulation.getCount() % DEBUG_INTERVAL == 0) {
                System.out.println(mPopulation.getShortString());
            }
        }
        System.out.println(mPopulation.toString());

        // 検証
    }

    @Test
    public void testInoriRecoveryCriteria() {
        // 準備
        Fitness partyFitness = new FitnessForInoriRecovery();
        mPopulation = new PopulationByRoulette(null, mMemoriaDataSet,
                mItemDataSet, 20, 0.01f, partyFitness);

        // テスト実行
        mPopulation.init();
        System.out.println(mPopulation.getShortString());
        while (mPopulation.getCount() < MAX_GENERATION) {
            mPopulation.next();
            if (mPopulation.getCount() % DEBUG_INTERVAL == 0) {
                System.out.println(mPopulation.getShortString());
            }
        }
        System.out.println(mPopulation.toString());

        // 検証
    }

}
