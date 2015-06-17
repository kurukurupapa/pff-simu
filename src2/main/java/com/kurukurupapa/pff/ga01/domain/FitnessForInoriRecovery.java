package com.kurukurupapa.pff.ga01.domain;

/**
 * 適応度クラス
 */
public class FitnessForInoriRecovery extends Fitness {

    public int calc(Party party) {
        int value = 0;
        value += party.getInoriRecovery();
        return value;
    }

}
