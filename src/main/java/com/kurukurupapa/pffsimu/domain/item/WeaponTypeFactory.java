package com.kurukurupapa.pffsimu.domain.item;

import com.kurukurupapa.pffsimu.domain.AppException;

/**
 * 武器種別ファクトリークラス
 */
public class WeaponTypeFactory {

    private static final String[] mNames = new String[] { "剣", "槍", "弓", "杖",
            "楽器", "格闘", "銃", "騎士剣", "短剣", "刀", "斧", "ロッド", "カード", "投擲",
            "ブラスターエッジ", "波動", };

    public static WeaponType create(String name) {
        name = name.trim();
        for (String e : mNames) {
            if (e.equals(name)) {
                return new WeaponType(name);
            }
        }
        throw new AppException("不正な引数です。name=" + name);
    }

}
