package com.kurukurupapa.pff.ga01.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * 染色体クラス
 *
 * 複数の遺伝子の集まりを表します。
 */
public abstract class PartyChromosome {
    /** メモリアデータ集合 */
    private MemoriaDataSet mMemoriaDataSet;
    /** アイテムデータ集合 */
    private ItemDataSet mItemDataSet;
    /** メモリア遺伝子リスト */
    private List<Memoria> mMemoriaList;
    /** メモリア遺伝子リスト（高優先度順） */
    private List<Memoria> mSortedMemoriaList;
    /** 遺伝子の配列表現 */
    private Gene[] mGeneArr;

    /**
     * コンストラクタ
     *
     * ランダムな遺伝子を持つ染色体を作成します。
     */
    public PartyChromosome(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
        init(memoriaDataSet, itemDataSet);
        randomize();
    }

    /**
     * コンストラクタ
     *
     * 2つの親から染色体を作成します。
     *
     * @param mutationRate
     *            突然変異率。0.0～1.0で設定します。
     */
    public PartyChromosome(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet,
            PartyChromosome parent1, PartyChromosome parent2, float mutationRate) {
        this(memoriaDataSet, itemDataSet, parent1, parent2, mutationRate,
                RandomUtils.nextInt(0, parent1.getNumGenes() + 1));
    }

    /**
     * コンストラクタ
     *
     * 2つの親から染色体を作成します。
     *
     * @param mutationRate
     *            突然変異率。0.0～1.0で設定します。
     */
    public PartyChromosome(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet,
            PartyChromosome parent1, PartyChromosome parent2, float mutationRate,
            int position) {
        Validate.validState(0.0f <= mutationRate && mutationRate <= 1.0f);
        Validate.validState(0 <= position
                && position <= parent1.getNumGenes() + 1);

        // System.out.println("親DNA1=" + parent1.toString());
        // System.out.println("親DNA2=" + parent2.toString());

        // 初期化
        init(memoriaDataSet, itemDataSet);

        // 一点交叉
        Gene[][] geneArr = new Gene[][] { parent1.getGeneArr(),
                parent2.getGeneArr() };
        Validate.validState(geneArr[0].length == geneArr[1].length);
        int length = geneArr[0].length;
        for (int i = 0; i < length; i++) {
            setGene(i, i < position ? geneArr[0][i] : geneArr[1][i]);
        }

        // 突然変異（mutation）
        // ※Wikipedia：突然変異の確率は0.1%～1%、高くても数%である。
        if (isMutation(RandomUtils.nextFloat(0, 1), mutationRate)) {
            // 突然変異（ランダム）
            // children[i].setGene(RandomUtils.nextInt(0, length),
            // Gene.createRandom());
            // 突然変異（反転）
            reverseGene(RandomUtils.nextInt(0, length));
        }

        // System.out.println("子DNA=" + children[0].toString());
    }

    protected static boolean isMutation(float random, float mutationRate) {
        return random <= mutationRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MemoriaGenes genes : mMemoriaList) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append("[" + genes.toString() + "]");
        }
        return sb.toString();
    }

    /**
     * 初期化します。
     *
     * @param memoriaDataSet
     * @param itemDataSet
     */
    private void init(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
        mMemoriaDataSet = memoriaDataSet;
        mItemDataSet = itemDataSet;
        mMemoriaList = new ArrayList<Memoria>();
        for (MemoriaData memoriaData : memoriaDataSet) {
            mMemoriaList.add(new Memoria(memoriaData, itemDataSet));
        }
    }

    protected MemoriaDataSet getMemoriaDataSet() {
        return mMemoriaDataSet;
    }

    protected ItemDataSet getItemDataSet() {
        return mItemDataSet;
    }

    private void randomize() {
        for (MemoriaGenes e : mMemoriaList) {
            e.randomize();
        }
    }

    private Gene getGene(int index) {
        return getGeneArr()[index];
    }

    private void setGene(int index, Gene gene) {
        getGene(index).setRate(gene);
    }

    private void reverseGene(int index) {
        getGene(index).reverse();
    }

    protected int getNumGenes() {
        return getGeneArr().length;
    }

    protected Gene[] getGeneArr() {
        if (mGeneArr == null) {
            List<Gene> list = new ArrayList<Gene>();
            for (MemoriaGenes e : mMemoriaList) {
                CollectionUtils.addAll(list, e.getGenes());
            }
            mGeneArr = list.toArray(new Gene[] {});
        }
        return mGeneArr;
    }

    /**
     * 優先の高い順にソートしたメモリア遺伝子リストを取得します。
     *
     * @return
     */
    protected List<Memoria> getSortedMemoriaList() {
        if (mSortedMemoriaList == null) {
            mSortedMemoriaList = new ArrayList<Memoria>(mMemoriaList);
            Collections.sort(mSortedMemoriaList, new Comparator<Memoria>() {
                @Override
                public int compare(Memoria o1, Memoria o2) {
                    return -1 * o1.getUseRate().compareTo(o2.getUseRate());
                }
            });
        }
        return mSortedMemoriaList;
    }

}
