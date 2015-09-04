package com.kurukurupapa.pff.apachega;

import java.util.ArrayList;

import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * パーティ母集団クラス
 */
public class PartyPopulation extends ElitisticListPopulation {

    /** エリート選択における次世代への生き残り率 */
    // private static final double ELITISM_RATE = 0.1;
    private static final double ELITISM_RATE = 0.9;

    /**
     * コンストラクタ
     *
     * @param memoriaDataSet
     * @param itemDataSet
     * @param fitness
     */
    public PartyPopulation(int populationLimit, MemoriaDataSet memoriaDataSet,
            ItemDataSet itemDataSet, Fitness fitness) {
        super(populationLimit, ELITISM_RATE);

        ArrayList<Chromosome> initPopulation = new ArrayList<Chromosome>(
                populationLimit);
        for (int i = populationLimit - 1; i >= 0; i--) {
            initPopulation.add(new PartyChromosome(memoriaDataSet, itemDataSet,
                    fitness));
        }
        addChromosomes(initPopulation);
    }
}
