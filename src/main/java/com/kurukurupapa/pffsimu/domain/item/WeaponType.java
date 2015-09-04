package com.kurukurupapa.pffsimu.domain.item;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.kurukurupapa.pffsimu.domain.Attr;

/**
 * 武器種別クラス
 */
public class WeaponType {
    private static final String SWORD = "剣";
    private static final String YARI = "槍";
    private static final String YUMI = "弓";
    private static final String WAND = "杖";
    // 楽器 省略
    // 格闘 省略
    private static final String JU = "銃";
    // 騎士剣 省略
    // 短剣 省略
    // 刀 省略
    // 斧 省略
    private static final String ROD = "ロッド";
    private static final String CARD = "カード";
    private static final String SHURIKEN = "手裏剣";
    private static final String EJJI = "ブラスターエッジ";
    private static final String WAVE = "波動";

    /** 飛行（短距離）属性の武器リスト */
    private static final String[] SHORT_FLIGHT_ARR = new String[] { YARI, CARD };
    /** 飛行（長距離）属性の武器リスト */
    private static final String[] LONG_FLIGHT_ARR = new String[] { YUMI,
            SHURIKEN, EJJI, JU };
    /** ブースト武器リスト */
    private static final String[] BOOST_ARR = new String[] { WAND, ROD, WAVE };

    /** 種別名 */
    private String mType;

    /**
     * コンストラクタ
     *
     * @param type
     *            種別名
     */
    public WeaponType(String type) {
        mType = type;
    }

    @Override
    public boolean equals(Object obj) {
        WeaponType other = (WeaponType) obj;
        return mType.equals(other.mType);
    }

    @Override
    public String toString() {
        return mType;
    }

    /**
     * 飛行（短距離）を判定します。
     *
     * @param weakList
     *            敵の弱点属性一覧
     *
     * @return 飛行の場合true
     */
    public boolean isShortFlight(List<Attr> weakList) {
        // 敵の弱点に飛行が含まれるか確認します。
        if (!weakList.contains(Attr.FLIGHT)) {
            return false;
        }
        return ArrayUtils.contains(SHORT_FLIGHT_ARR, mType);
    }

    /**
     * 飛行（長距離）を判定します。
     *
     * @param weakList
     *            敵の弱点属性一覧
     * @return 飛行の場合true
     */
    public boolean isLongFlight(List<Attr> weakList) {
        // 敵の弱点に飛行が含まれるか確認します。
        if (!weakList.contains(Attr.FLIGHT)) {
            return false;
        }
        return ArrayUtils.contains(LONG_FLIGHT_ARR, mType);
    }

    /**
     * 黒魔法攻撃におけるブーストを判定します。
     *
     * @return ブースト対象の場合true
     */
    public boolean isBoostForBlackMagic() {
        return ArrayUtils.contains(BOOST_ARR, mType);
    }
}
