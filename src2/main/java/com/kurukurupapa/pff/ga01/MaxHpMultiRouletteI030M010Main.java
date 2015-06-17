package com.kurukurupapa.pff.ga01;

import org.apache.log4j.Logger;

import com.kurukurupapa.pff.ga01.domain.FitnessForHp;
import com.kurukurupapa.pff.ga01.service.MultiRouletteMainServiceImpl;

/**
 * コマンドラインインターフェース用メインクラスのテンプレートです。
 *
 * 次の条件で、複数の母集団を動作させ、比較します。<br>
 * ・ルーレット戦略。<br>
 * ・適応度：HPのみで決定。<br>
 * ・母集団の数：10個<br>
 * ・母集団内の個体数：30体<br>
 * ・突然変異率：10%<br>
 */
public class MaxHpMultiRouletteI030M010Main {

    /**
     * ロガー
     */
    private Logger logger;

    /**
     * メインメソッドです。
     *
     * @param args
     *            実行時引数
     */
    public static void main(String[] args) {
        MaxHpMultiRouletteI030M010Main main = new MaxHpMultiRouletteI030M010Main();
        main.run(args);
    }

    /**
     * コンストラクタ
     */
    public MaxHpMultiRouletteI030M010Main() {
        logger = Logger.getLogger(MaxHpMultiRouletteI030M010Main.class);
    }

    /**
     * 処理を実行します。
     *
     * @param args
     *            実行時引数
     */
    public void run(String[] args) {
        logger.trace("処理開始");

        MultiRouletteMainServiceImpl mainService = new MultiRouletteMainServiceImpl();
        mainService.setFitness(new FitnessForHp());
        mainService.setNumTimes(10);
        mainService.setNumIndividuals(30);
        mainService.setMutationRate(0.1f);
        mainService.run(args);

        logger.trace("処理終了");
    }

}
