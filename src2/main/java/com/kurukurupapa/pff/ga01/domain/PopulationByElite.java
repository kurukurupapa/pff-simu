package com.kurukurupapa.pff.ga01.domain;

import org.apache.commons.lang3.RandomUtils;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * 集団クラス
 *
 * パーティ（個体）の集まりを表します。
 */
public class PopulationByElite extends Population {

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
    public PopulationByElite(String name, MemoriaDataSet memoriaDataSet,
            ItemDataSet itemDataSet, int numIndividuals, float mutationRate,
            Fitness fitness) {
        super(name, memoriaDataSet, itemDataSet, numIndividuals, mutationRate,
                fitness);
    }

    /**
     * 次の世代を生成します。（エリート戦略）
     */
    public void next() {
        // 低評価のパーティ（個体）を淘汰
        for (int i = 0; i < 2; i++) {
            mPartyList.remove(mPartyList.size() - 1);
        }

        // 最も適応度の高いパーティ（個体）からパーティ（個体）を生成
        Party parent1 = mPartyList.get(0);
        Party parent2 = mPartyList.get(1);
        int position = RandomUtils.nextInt(0, parent1.getNumGenes() + 1);
        Party child1 = new Party(mMemoriaDataSet, mItemDataSet, parent1,
                parent2, mMutationRate, position);
        Party child2 = new Party(mMemoriaDataSet, mItemDataSet, parent2,
                parent1, mMutationRate, position);
        mPartyList.add(child1);
        mPartyList.add(child2);
        // 評価
        child1.calcFitness(mFitness);
        child2.calcFitness(mFitness);

        // 評価の高い順にソート
        sortPartyList();

        //
        super.next();
    }

}
