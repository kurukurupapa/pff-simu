package com.kurukurupapa.pff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kurukurupapa.pff.ga01.domain.UserArgs;

/**
 * メインサービスの実装クラスです。
 */
@Component
public class MainServiceImpl implements MainService {

    /**
     * 実行時引数解析サービス
     */
    @Autowired
    private UserArgsService userArgsService;

    /**
     * XXXXXサービス
     */
    @Autowired
    private HelloService helloService;

    @Override
    public void run(String[] args) {
        if (!setUp(args)) {
            return;
        }

        runMain();
        tearDown();
    }

    /**
     * 前処理です。
     *
     * @param args
     *            実行時引数
     * @return 処理を継続する場合true。中止する場合false。
     */
    protected boolean setUp(String[] args) {
        if (!userArgsService.parse(args)) {
            userArgsService.printHelp();
            throw new RuntimeException("引数の解析に失敗しました。");
        }

        UserArgs userArgs = userArgsService.getUserArgs();
        if (userArgs.isHelp()) {
            userArgsService.printHelp();
            return false;
        }

        return true;
    }

    /**
     * 主処理です。
     */
    protected void runMain() {
        helloService.run();
    }

    /**
     * 後処理です。
     * <p>
     * 各種リソースの後始末や、処理結果などの出力を行います。
     * </p>
     */
    protected void tearDown() {
        print("後処理です。");
    }

    /**
     * メッセージを出力します。
     *
     * @param msg
     *            メッセージ
     */
    private void print(String msg) {
        System.out.println(msg);
    }

}
