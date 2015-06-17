package com.kurukurupapa.pff.ga01.domain;

/**
 * 実行時引数を保持します。
 */
public class UserArgs {

    /**
     * ヘルプオプション
     */
    private boolean help;

    /**
     * 引数なしオプション
     */
    private boolean optionWithoutArg;

    /**
     * 引数ありオプション
     */
    private String optionWithArg;

    /**
     * 入力ファイルパス
     */
    private String inFile;

    /**
     * ヘルプオプションを取得します。
     *
     * @return ヘルプオプション
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * ヘルプオプションを設定します。
     *
     * @param flag
     *            ヘルプオプション
     */
    public void setHelp(boolean flag) {
        this.help = flag;
    }

    /**
     * XXXXXオプションを取得します。
     *
     * @return XXXXXオプション
     */
    public boolean isOptionWithoutArg() {
        return optionWithoutArg;
    }

    /**
     * XXXXXオプションを設定します。
     *
     * @param arg
     *            XXXXXオプション
     */
    public void setOptionWithoutArg(boolean arg) {
        this.optionWithoutArg = arg;
    }

    /**
     * XXXXXオプションを取得します。
     *
     * @return XXXXXオプション
     */
    public String getOptionWithArg() {
        return optionWithArg;
    }

    /**
     * XXXXXオプションを設定します。
     *
     * @param arg
     *            XXXXXオプション
     */
    public void setOptionWithArg(String arg) {
        this.optionWithArg = arg;
    }

    /**
     * 入力ファイルパスを取得します。
     *
     * @return 入力ファイルパス
     */
    public String getInFile() {
        return inFile;
    }

    /**
     * 入力ファイルパスを設定します。
     *
     * @param path
     *            入力ファイルパス
     */
    public void setInFile(String path) {
        this.inFile = path;
    }

}
