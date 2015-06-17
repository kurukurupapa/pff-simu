package com.kurukurupapa.pff.domain;

import java.util.List;

/**
 * 召喚魔法アイテムデータ拡張クラス
 */
public class SummonMagicItemDataEx extends MagicItemDataEx {

    public SummonMagicItemDataEx(ItemData itemData, ItemType itemType,
            ItemType2 itemType2, Attr attr, int magicCharge, int magicEffect) {
        super(itemData, itemType, itemType2, attr, magicCharge, magicEffect);
    }

    /**
     * 召喚魔法ダメージを取得します。
     *
     * @return ダメージ
     */
    public float getAttackDamage(float magicAttack) {
        // 召喚ダメージ＝効果値×召喚倍率
        // http://wikiwiki.jp/pictlogicaff/?%C0%EF%C6%AE%BE%F0%CA%F3#measure
        return mMagicEffect * magicAttack;
    }

    public float getWeakAttrValue(List<Attr> weakList) {
        return mAttr.isAttrWithoutFlight(weakList) ? 1 : 0;
    }

    public float getResistanceAttrRate(List<Attr> resistanceList) {
        return mAttr.isAttrWithoutFlight(resistanceList) ? 0.8f : 1;
    }

}
