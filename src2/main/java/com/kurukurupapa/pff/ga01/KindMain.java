package com.kurukurupapa.pff.ga01;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kurukurupapa.pff.ga01.service.KindMainService;

/**
 * コマンドラインインターフェース用メインクラスのテンプレートです。
 */
public class KindMain {

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
        KindMain main = new KindMain();
        main.run(args);
    }

    /**
     * コンストラクタ
     */
    public KindMain() {
        logger = Logger.getLogger(KindMain.class);
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
        KindMainService mainService = appContext.getBean(KindMainService.class);
        mainService.run(args);

        logger.trace("処理終了");
    }

}
