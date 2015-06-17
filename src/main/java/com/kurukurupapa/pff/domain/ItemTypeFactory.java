package com.kurukurupapa.pff.domain;

/**
 * アイテム種別ファクトリークラス
 */
public class ItemTypeFactory {

    public static ItemType create(String name) {
        name = name.trim();
        if (name.equals("武器")) {
            return ItemType.WEAPON;
        } else if (name.equals("魔法")) {
            return ItemType.MAGIC;
        } else if (name.equals("アクセサリ")) {
            return ItemType.ACCESSORY;
        } else {
            throw new AppException("不正な引数です。name=" + name);
        }
    }

}
