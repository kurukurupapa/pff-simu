package com.kurukurupapa.pff.cli;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kurukurupapa.pff.service.MainService;

/**
 * コマンドラインインターフェース用メインクラスのテンプレートです。
 */
public class Main {

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
        Main main = new Main();
        main.run(args);
    }

    /**
     * コンストラクタ
     */
    public Main() {
        logger = Logger.getLogger(Main.class);
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
        MainService mainService = appContext.getBean(MainService.class);
        mainService.run(args);

        logger.trace("処理終了");
    }

}
