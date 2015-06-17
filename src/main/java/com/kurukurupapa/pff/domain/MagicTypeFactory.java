package com.kurukurupapa.pff.domain;

/**
 * 魔法種別ファクトリークラス
 */
public class MagicTypeFactory {

    public static MagicType create(String name) {
        name = name.trim();
        if (name.equals("白魔法")) {
            return MagicType.WHITE;
        } else if (name.equals("黒魔法")) {
            return MagicType.BLACK;
        } else if (name.equals("召喚魔法")) {
            return MagicType.SUMMON;
        } else {
            throw new AppException("不正な引数です。name=" + name);
        }
    }

}
