package com.kurukurupapa.pff.service;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Component;

import com.kurukurupapa.pff.ga01.domain.UserArgs;

/**
 * 実行時引数を解析します。
 */
@Component
public class UserArgsServiceImpl implements UserArgsService {

    /**
     * 実行時引数のオプション定義
     */
    private Options options;

    /**
     * 実行時引数
     */
    private UserArgs userArgs;

    /**
     * コンストラクタ
     */
    public UserArgsServiceImpl() {
        options = new Options();
        options.addOption("h", "help", false, "ヘルプを表示します。");
        options.addOption("a", "option-without-arg", false, "引数なしオプションです。");
        options.addOption("b", "option-with-arg", true, "引数ありオプションです。");

        userArgs = new UserArgs();
    }

    @Override
    public boolean parse(String[] args) {
        // 引数なしは、ヘルプオプションが指定された扱いにします。
        if (args.length == 0) {
            userArgs.setHelp(true);
            return true;
        }

        // Apache Commons CLI による解析
        CommandLine cl = null;
        try {
            BasicParser parser = new BasicParser();
            cl = parser.parse(options, args);
        } catch (ParseException e) {
            return false;
        }

        // ヘルプオプションがある場合は、解析を終了します。
        userArgs.setHelp(cl.hasOption("help"));
        if (userArgs.isHelp()) {
            return true;
        }

        // 必須引数を解析
        if (cl.getArgs().length != 1) {
            return false;
        }

        // オプション、必須引数を取得
        userArgs.setOptionWithoutArg(cl.hasOption("option-without-arg"));
        userArgs.setOptionWithArg(cl.getOptionValue("option-with-arg"));
        userArgs.setInFile(cl.getArgs()[0]);

        return true;
    }

    @Override
    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(
                "Usage: java jp.co.javaapptemplate.cli.Main [オプション] <入力ファイル>",
                options);
    }

    @Override
    public UserArgs getUserArgs() {
        return this.userArgs;
    }

}
