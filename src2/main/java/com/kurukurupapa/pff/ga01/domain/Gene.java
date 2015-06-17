package com.kurukurupapa.pff.ga01.domain;

import org.apache.commons.lang3.RandomUtils;

/**
 * 遺伝子クラス
 */
public class Gene implements Comparable<Gene> {
    /** 最小値 */
    private static final float MIN = 0.0f;
    /** 最大値 */
    private static final float MAX = 1.0f;

    /** 度合い */
    private float mRate;

    /**
     * コンストラクタ
     */
    public Gene() {
    }

    @Override
    public String toString() {
        return String.format("%.1f", mRate);
    }

    @Override
    public int compareTo(Gene o) {
        return Float.compare(mRate, o.mRate);
    }

    /**
     * ランダムにします。
     */
    public void randomize() {
        mRate = RandomUtils.nextFloat(MIN, MAX);
    }

    /**
     * 反転します。
     */
    public void reverse() {
        mRate = MAX - mRate;
    }

    /**
     * 度合いを取得する。
     *
     * @return 度合い
     */
    public float getRate() {
        return mRate;
    }

    /**
     * 度合いを設定する。
     *
     * @param gene
     *            遺伝子オブジェクト
     */
    public void setRate(Gene gene) {
        mRate = gene.getRate();
    }

    /**
     * 度合いを設定する。
     *
     * @param rate
     *            度合い
     */
    public void setRate(float rate) {
        mRate = rate;
    }

}
