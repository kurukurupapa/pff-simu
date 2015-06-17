package com.kurukurupapa.pff.apachega;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.BinaryChromosome;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * メモリア遺伝子クラス
 *
 * メモリア1体の遺伝子を表します。
 */
public class PartyChromosome extends BinaryChromosome {
    /** メモリアデータ集合 */
    private MemoriaDataSet mMemoriaDataSet;
    /** アイテムデータ集合 */
    private ItemDataSet mItemDataSet;
    /** 適応度 */
    private Fitness mFitness;

    /**
     * コンストラクタ
     *
     * ランダムな遺伝子でインスタンス生成します。
     *
     * @param mMemoriaDataSet
     * @param mItemDataSet
     */
    public PartyChromosome(MemoriaDataSet memoriaDataSet,
            ItemDataSet itemDataSet, Fitness fitness) {

        // this(BinaryChromosome.randomBinaryRepresentation(memoriaDataSet
        // .getNumMemoria()), memoriaDataSet, itemDataSet, fitness);

        this(createRandomRepresentation(memoriaDataSet, itemDataSet),
                memoriaDataSet, itemDataSet, fitness);
    }

    /**
     * コンストラクタ
     *
     * @param randomBinaryRepresentation
     * @param mMemoriaDataSet
     * @param mItemDataSet
     */
    public PartyChromosome(List<Integer> representation,
            MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet,
            Fitness fitness) {
        super(representation);
        mMemoriaDataSet = memoriaDataSet;
        mItemDataSet = itemDataSet;
        mFitness = fitness;
    }

    private static List<Integer> createRandomRepresentation(
            MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
        List<Integer> representation = new ArrayList<Integer>();
        for (int i = 0; i < memoriaDataSet.size(); i++) {
            representation.add(0);
        }
        return representation;
    }

    @Override
    public double fitness() {
        return mFitness.calc(getRepresentation());
    }

    @Override
    public AbstractListChromosome<Integer> newFixedLengthChromosome(
            List<Integer> representation) {
        return new PartyChromosome(representation, mMemoriaDataSet,
                mItemDataSet, mFitness);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("f=" + getFitness());
        for (int i = 0; i < getLength(); i++) {
            int gene = getRepresentation().get(i);
            MemoriaData memoriaData = mMemoriaDataSet.get(i);
            if (gene > 0) {
                sb.append(",");
                sb.append(memoriaData.getName());
            }
        }
        return sb.toString();
    }

    public long toNumber() {
        long value = 0;
        List<Integer> representation = getRepresentation();
        for (int i = 0; i < representation.size(); i++) {
            int v = representation.get(i);
            value = value * 2 + v;
        }
        return value;
    }

}
