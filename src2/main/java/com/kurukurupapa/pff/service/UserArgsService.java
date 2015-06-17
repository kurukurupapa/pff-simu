package com.kurukurupapa.pff.service;

import com.kurukurupapa.pff.ga01.domain.UserArgs;

/**
 * 実行時引数解析サービスです。
 */
public interface UserArgsService {

    /**
     * 実行時引数を解析します。
     *
     * @param args
     *            実行時引数
     * @return 解析成功の場合true。失敗の場合false。
     */
    boolean parse(String[] args);

    /**
     * ヘルプを標準出力します。
     */
    void printHelp();

    /**
     * 実行時引数を取得します。
     *
     * @return 実行時引数
     */
    UserArgs getUserArgs();

}
