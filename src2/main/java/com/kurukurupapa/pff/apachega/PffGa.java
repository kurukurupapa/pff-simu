package com.kurukurupapa.pff.apachega;

import org.apache.commons.math3.exception.OutOfRangeException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.CrossoverPolicy;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.SelectionPolicy;
import org.apache.commons.math3.genetics.StoppingCondition;

/**
 * メモリアGAクラス
 *
 * メモリア用の遺伝的アルゴリズムです。
 */
public class PffGa extends GeneticAlgorithm {

    /**
     * コンストラクタ
     *
     * @param crossoverPolicy
     * @param crossoverRate
     * @param mutationPolicy
     * @param mutationRate
     * @param selectionPolicy
     * @throws OutOfRangeException
     */
    public PffGa(CrossoverPolicy crossoverPolicy, double crossoverRate,
            MutationPolicy mutationPolicy, double mutationRate,
            SelectionPolicy selectionPolicy) throws OutOfRangeException {
        super(crossoverPolicy, crossoverRate, mutationPolicy, mutationRate,
                selectionPolicy);
    }

    @Override
    public Population nextGeneration(final Population current) {
        Population nextGeneration = super.nextGeneration(current);

        // ログ出力
        System.out.println("第" + getGenerationsEvolved() + "世代,"
                + nextGeneration.getFittestChromosome());

        return nextGeneration;
    }

    @Override
    public Population evolve(Population initial, StoppingCondition condition) {
        print(initial);
        Population result = super.evolve(initial, condition);
        print(result);
        return result;
    }

    private void print(Population population) {
        StringBuilder sb = new StringBuilder();
        sb.append("第" + getGenerationsEvolved() + "世代");
        sb.append("," + population.getPopulationSize() + "個体");
        System.out.println(sb.toString());
        for (Chromosome e : population) {
            System.out.println(e);
        }
    }
}
