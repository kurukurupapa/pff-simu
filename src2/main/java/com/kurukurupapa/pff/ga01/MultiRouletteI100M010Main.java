package com.kurukurupapa.pff.ga01;

import org.apache.log4j.Logger;

import com.kurukurupapa.pff.ga01.service.MultiRouletteMainServiceImpl;

/**
 * コマンドラインインターフェース用メインクラスのテンプレートです。
 *
 * 次の条件で、複数の母集団を動作させ、比較します。<br>
 * ・ルーレット戦略。<br>
 * ・母集団の数：10個<br>
 * ・母集団内の個体数：100体<br>
 * ・突然変異率：10%<br>
 */
public class MultiRouletteI100M010Main {

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
        MultiRouletteI100M010Main main = new MultiRouletteI100M010Main();
        main.run(args);
    }

    /**
     * コンストラクタ
     */
    public MultiRouletteI100M010Main() {
        logger = Logger.getLogger(MultiRouletteI100M010Main.class);
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
        mainService.setNumTimes(10);
        mainService.setNumIndividuals(100);
        mainService.setMutationRate(0.1f);
        mainService.run(args);

        logger.trace("処理終了");
    }

}
