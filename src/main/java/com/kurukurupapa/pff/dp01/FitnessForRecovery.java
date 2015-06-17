package com.kurukurupapa.pff.dp01;

/**
 * 適応度クラス
 */
public class FitnessForRecovery extends Fitness {
    /** 1バトルあたりのターン数 */
    private static final int TURN = 10;
    /** 1ターンあたりのチャージ */
    private static final int CHARGE_PER_TURN = 5 * 5 / 2;
    /** 1バトルあたりのチャージ */
    private static final int CHARGE_PER_BATTLE = CHARGE_PER_TURN * TURN;

    @Override
    protected int calc(Memoria memoria) {
        return memoria.getRecovery(TURN, CHARGE_PER_BATTLE);
    }

}
