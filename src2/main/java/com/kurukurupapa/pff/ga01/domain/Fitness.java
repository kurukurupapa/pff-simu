package com.kurukurupapa.pff.ga01.domain;

/**
 * 適応度クラス
 *
 * 各個体の環境に対する適合の度合いを計算します。
 */
public abstract class Fitness {

    /**
     * 適応度を計算します。
     *
     * @param party
     *            パーティオブジェクト
     * @return 適応度
     */
    public abstract int calc(Party party);

}
