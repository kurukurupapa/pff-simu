package com.kurukurupapa.pff.ga01;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kurukurupapa.pff.ga01.service.RouletteMainService;

/**
 * コマンドラインインターフェース用メインクラスのテンプレートです。
 */
public class RouletteMain {

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
        RouletteMain main = new RouletteMain();
        main.run(args);
    }

    /**
     * コンストラクタ
     */
    public RouletteMain() {
        logger = Logger.getLogger(RouletteMain.class);
    }

    /**
     * 処理を実行します。
     *
     * @param args
     *            実行時引数
     */
    public void run(String[] args) {
        logger.trace("処理開始");

        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "/applicationContext.xml");
        RouletteMainService mainService = appContext
                .getBean(RouletteMainService.class);
        mainService.run(args);

        logger.trace("処理終了");
    }

}
