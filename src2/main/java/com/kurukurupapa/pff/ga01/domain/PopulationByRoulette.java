package com.kurukurupapa.pff.ga01.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * 母集団クラス
 *
 * パーティ（個体）の集まりを表します。
 */
public class PopulationByRoulette extends Population {

    /**
     * コンストラクタ
     *
     * @param name
     * @param memoriaDataSet
     * @param itemDataSet
     * @param numIndividuals
     *            母集団内の個体数です。
     * @param mutationRate
     *            突然変異率。0.0～1.0で設定します。
     * @param partyFitness
     */
    public PopulationByRoulette(String name, MemoriaDataSet memoriaDataSet,
            ItemDataSet itemDataSet, int numIndividuals, float mutationRate,
            Fitness fitness) {
        super(name, memoriaDataSet, itemDataSet, numIndividuals, mutationRate,
                fitness);
    }

    /**
     * 次の世代を生成します。（ルーレット戦略）
     */
    public void next() {
        // 合計評価
        int totalFitness = 0;
        for (Party e : mPartyList) {
            totalFitness += e.getFitness();
        }

        // 次の世代を作成、評価
        // int num = mNumIndividuals;
        int num = 2;
        List<Party> childList = new ArrayList<Party>();
        while (childList.size() < num) {
            Party parent1 = getPartyByRoulette(totalFitness);
            Party parent2 = getPartyByRoulette(totalFitness);
            Party child = new Party(mMemoriaDataSet, mItemDataSet, parent1,
                    parent2, mMutationRate);
            child.calcFitness(mFitness);
            childList.add(child);
        }

        // 淘汰
        for (int i = 0; i < num; i++) {
            mPartyList.remove(mPartyList.size() - 1);
        }

        // 次の世代の保存
        mPartyList.addAll(childList);

        // 評価の高い順にソート
        sortPartyList();

        super.next();
    }

    private Party getPartyByRoulette(int totalValue) {
        Party party = null;
        int value = RandomUtils.nextInt(0, totalValue);
        for (int i = 0; i < mPartyList.size(); i++) {
            party = mPartyList.get(i);
            if (value < party.getFitness()) {
                break;
            }
            value -= party.getFitness();
        }
        return party;
    }

}
