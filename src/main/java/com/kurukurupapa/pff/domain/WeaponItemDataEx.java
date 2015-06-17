package com.kurukurupapa.pff.domain;

import java.util.List;

/**
 * 武器アイテムデータ
 */
public class WeaponItemDataEx extends ItemDataEx {
    /** 武器種別 */
    private WeaponType mWeaponType;

    /**
     * コンストラクタ
     *
     * @param itemData
     */
    public WeaponItemDataEx(ItemData itemData, ItemType itemType,
            ItemType2 itemType2, Attr attr) {
        super(itemData, itemType, itemType2, attr);
        mWeaponType = new WeaponType(itemType2.toString());
    }

    @Override
    public boolean isValid(MemoriaData memoriaData) {
        return mWeaponType.equals(memoriaData.getWeaponType());
    }

    /**
     * 物理攻撃における敵弱点係数を取得します。
     *
     * @param weakList
     *            敵弱点一覧
     * @return 係数
     */
    public float getWeakAttrValue(List<Attr> weakList) {
        float value = 0f;

        boolean attr = mAttr.isAttrWithoutFlight(weakList);
        boolean sf = mWeaponType.isShortFlight(weakList);
        boolean lf = mWeaponType.isLongFlight(weakList);
        if (attr) {
            value += 1.0f;
        }
        if (sf) {
            value += 1.0f;
        }
        if (lf) {
            value += 1.5f;
        }
        // 重複時の補正
        if (attr && (sf || lf)) {
            value -= 0.5f;
        }

        return value;
    }

    /**
     * 物理攻撃における敵耐性倍率を取得します。
     *
     * @param resistanceList
     *            敵耐性一覧
     * @return 係数
     */
    public float getResistanceAttrRate(List<Attr> resistanceList) {
        if (mAttr.isAttrWithoutFlight(resistanceList)) {
            return 0.8f;
        } else {
            return 1.0f;
        }
    }

    @Override
    public float getBoostValueForBlackMagic(List<Attr> weakList) {
        boolean attr = mAttr.isAttrWithoutFlight(weakList);
        boolean boost = mWeaponType.isBoostForBlackMagic();
        return attr && boost ? 0.5f : 0;
    }

}
