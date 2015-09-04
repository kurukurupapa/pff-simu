package com.kurukurupapa.pff.apachega;

import org.apache.commons.math3.genetics.BinaryMutation;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.OnePointCrossover;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * Apache Commons Mathの遺伝的アルゴリズムを使用して、ピクトロジカファイナルファンタジーの最強パーティを見つけ出します。
 */
public class ApacheGaMain {
    /** 母集団内の個体数 */
    // private static final int POPULATION_SIZE = 20;
    private static final int POPULATION_SIZE = 50;
    /** 処理を行う世代数 */
    private static final int MAX_GENERATION = 100;
    /** 一点交叉率 */
    private static final double CROSSOVER_RATE = 0.7;
    /** 突然変異率 */
    // private static final double MUTATION_RATE = 0.01;
    private static final double MUTATION_RATE = 0.1;
    /** トーナメント方式選択におけるサイズ */
    private static final int TOURNAMENT_ARITY = 2;

    /** ロガー */
    private Logger logger;
    /** メモリアデータ集合 */
    private MemoriaDataSet mMemoriaDataSet;
    /** アイテムデータ集合 */
    private ItemDataSet mItemDataSet;
    /** 適応度 */
    private Fitness mFitness;
    /** 母集団 */
    private ElitisticListPopulation mPopulation;
    /** メモリアGA */
    private GeneticAlgorithm mGa;
    /** グラフデータ */
    private XYSeriesCollection mXYSeriesCollection;
    /** 最大適応度グラフデータ */
    private XYSeries mMaxXYSeries;

    /**
     * メインメソッドです。
     *
     * @param args
     *            実行時引数
     */
    public static void main(String[] args) {
        ApacheGaMain apacheGaMain = new ApacheGaMain();
        apacheGaMain.run(args);
    }

    /**
     * コンストラクタ
     */
    public ApacheGaMain() {
        logger = Logger.getLogger(ApacheGaMain.class);
    }

    /**
     * 処理を実行します。
     *
     * @param args
     *            実行時引数
     */
    public void run(String[] args) {
        logger.trace("処理開始");

        readData();
        initGa();
        initGraph();
        runGa();
        showGraph();

        logger.trace("処理終了");
    }

    /**
     * 各種データを読み込みます。
     */
    private void readData() {
        mItemDataSet = new ItemDataSet();
        mItemDataSet.read();
        mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
        mMemoriaDataSet.read();
        mFitness = new Fitness(mMemoriaDataSet, mItemDataSet);
    }

    /**
     * 遺伝的アルゴリズム関連オブジェクトを初期化します。
     */
    private void initGa() {
        // // 乱数生成はメルセンヌツイスター
        // GeneticAlgorithm.setRandomGenerator(new MersenneTwister());

        // 母集団を生成
        mPopulation = new PartyPopulation(POPULATION_SIZE, mMemoriaDataSet,
                mItemDataSet, mFitness);

        // 毎世代で 一点交叉を交差率0.7で行い， 突然変異を変異確率0.01で行い， トーナメント選択（サイズ2）を行う．
        mGa = new PffGa(new OnePointCrossover<PartyChromosome>(),
                CROSSOVER_RATE, new BinaryMutation(), MUTATION_RATE,
                new TournamentSelection(TOURNAMENT_ARITY)) {
            @Override
            public Population nextGeneration(final Population current) {
                Population nextGeneration = super.nextGeneration(current);

                // グラフデータ登録
                PartyChromosome partyChromosome = (PartyChromosome) nextGeneration
                        .getFittestChromosome();
                mMaxXYSeries.add(getGenerationsEvolved(),
                        partyChromosome.toNumber());

                return nextGeneration;
            }
        };
    }

    private void initGraph() {
        mXYSeriesCollection = new XYSeriesCollection();
        mMaxXYSeries = new XYSeries("Max fitness");
        mXYSeriesCollection.addSeries(mMaxXYSeries);
    }

    private void runGa() {
        // 世代数がmax_generationに達したら終了．
        Population evolvedPopulation = mGa.evolve(mPopulation,
                new FixedGenerationCount(MAX_GENERATION));

        System.out.print(evolvedPopulation.getFittestChromosome());
    }

    private void showGraph() {
        JFreeChart chart = ChartFactory.createScatterPlot("Sample Line Chart",
                "X", "Y", mXYSeriesCollection, PlotOrientation.VERTICAL, true,
                false, false);
        ChartFrame frame = new ChartFrame("Sample Line Chart", chart);
        frame.pack();
        frame.setVisible(true);
    }

}
