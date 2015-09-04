package com.kurukurupapa.pff.ga01.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * 集団クラス
 *
 * パーティ（個体）の集まりを表します。
 */
public abstract class Population {

    /** 集団名 */
    private String mName;
    /** メモリアデータ */
    protected MemoriaDataSet mMemoriaDataSet;
    /** アイテムデータ */
    protected ItemDataSet mItemDataSet;
    /** 個体数 */
    protected int mNumIndividuals = 20;
    /** 突然変異率 */
    protected float mMutationRate;
    /** 適応度 */
    protected Fitness mFitness;

    /**
     * パーティリスト<br>
     * 評価の高い順にソートしておきます。
     */
    protected List<Party> mPartyList;

    /** 世代カウント */
    protected int mCount;

    /**
     * コンストラクタ
     *
     * @param name
     * @param memoriaDataSet
     * @param itemDataSet
     * @param numIndividuals
     *            母集団内の個体数
     * @param mutationRate
     *            突然変異率。0.0～1.0で設定します。
     * @param partyFitness
     */
    public Population(String name, MemoriaDataSet memoriaDataSet,
            ItemDataSet itemDataSet, int numIndividuals, float mutationRate,
            Fitness fitness) {
        mName = name;
        mMemoriaDataSet = memoriaDataSet;
        mItemDataSet = itemDataSet;
        mNumIndividuals = numIndividuals;
        mMutationRate = mutationRate;
        mFitness = fitness;
        mPartyList = new ArrayList<Party>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mName);
        sb.append(",第" + mCount + "世代");
        sb.append("," + mPartyList.size() + "個体");
        sb.append(System.lineSeparator());
        int count = 1;
        for (Party e : mPartyList) {
            sb.append(String.format("%2d", count++) + "," + e.toString()
                    + System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * 最大評価のパーティ（個体）情報を文字列で取得します。
     *
     * @return 文字列
     */
    public String getShortString() {
        StringBuilder sb = new StringBuilder();
        sb.append("第" + mCount + "世代,");
        sb.append(mPartyList.size() + "個体,");
        sb.append(getMaxFitnessParty().toString());
        return sb.toString();
    }

    /**
     * 第一世代のパーティ（個体）をランダムに生成します。
     */
    public void init() {
        mCount = 1;
        mPartyList.clear();

        // パーティ生成、評価
        for (int i = 0; i < mNumIndividuals; i++) {
            Party party = new Party(mMemoriaDataSet, mItemDataSet);
            party.calcFitness(mFitness);
            mPartyList.add(party);
        }

        // 評価の高い順にソート
        sortPartyList();
    }

    public String getName() {
        return mName;
    }

    /**
     * 次の世代を生成します。
     */
    public void next() {
        // カウントアップ
        mCount++;
    }

    /**
     * パーティリストを評価の高い順にソートします。
     */
    protected void sortPartyList() {
        Collections.sort(mPartyList, new Comparator<Party>() {
            @Override
            public int compare(Party o1, Party o2) {
                if (o1.getFitness() == o2.getFitness()) {
                    return 0;
                } else if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    /**
     * 世代を取得します。
     *
     * @return
     */
    public int getCount() {
        return mCount;
    }

    /**
     * 最高評価を取得します。
     *
     * @return
     */
    public Party getMaxFitnessParty() {
        return mPartyList.get(0);
    }

    public int getMaxFitness() {
        return getMaxFitnessParty().getFitness();
    }

    public int getAverageFitness() {
        int value = 0;
        for (Party e : mPartyList) {
            value += e.getFitness();
        }
        value /= mPartyList.size();
        return value;
    }

}
