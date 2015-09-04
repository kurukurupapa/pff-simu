package com.kurukurupapa.pffsimu.domain.item;

import com.kurukurupapa.pffsimu.domain.Attr;

/**
 * 魔法アイテムデータ拡張クラス
 */
public abstract class MagicItemDataEx extends ItemDataEx {
    /** 魔法種別 */
    protected MagicType mMagicType;
    /** 魔法チャージ */
    protected int mMagicCharge;
    /** 魔法効果値 */
    protected int mMagicEffect;

    /**
     * コンストラクタ
     *
     * @param itemData
     */
    protected MagicItemDataEx(ItemData itemData, ItemType itemType,
            ItemType2 itemType2, Attr attr, int magicCharge, int magicEffect) {
        super(itemData, itemType, itemType2, attr);
        mMagicType = MagicType.parse(itemType2.toString());
        mMagicCharge = magicCharge;
        mMagicEffect = magicEffect;
    }

    /**
     * 魔法チャージを取得します。
     *
     * @return 魔法チャージ
     */
    public int getMagicCharge() {
        return mMagicCharge;
    }

    /**
     * 魔法効果値を取得します。
     *
     * @return 魔法効果値
     */
    public int getMagicEffect() {
        return mMagicEffect;
    }

}
