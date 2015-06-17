package com.kurukurupapa.pff.ga01.domain;

/**
 * 適応度クラス
 */
public class FitnessForHp extends Fitness {

    public int calc(Party party) {
        return party.getHp();
    }

}
