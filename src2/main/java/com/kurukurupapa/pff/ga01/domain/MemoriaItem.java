package com.kurukurupapa.pff.ga01.domain;

import com.kurukurupapa.pffsimu.domain.item.ItemData;

/**
 * メモリアアイテムクラス
 *
 * メモリアのアイテム候補1つ分を表します。
 */
public class MemoriaItem {
    public static final int GROUP_WEAPON = 1;
    public static final int GROUP_ACCESSORY = 2;

    private Memoria mMemoria;
    private int mGroup;
    private int mIndex;

    /**
     * ファクトリーメソッド
     *
     * @param memoria
     * @param index
     * @return
     */
    public static MemoriaItem createWeaponStatus(Memoria memoria, int index) {
        return new MemoriaItem(memoria, GROUP_WEAPON, index);
    }

    /**
     * ファクトリーメソッド
     *
     * @param memoria
     * @param index
     * @return
     */
    public static MemoriaItem createAccessoryStatus(Memoria memoria, int index) {
        return new MemoriaItem(memoria, GROUP_ACCESSORY, index);
    }

    /**
     * コンストラクタ
     *
     * @param memoria
     * @param group
     * @param index
     */
    private MemoriaItem(Memoria memoria, int group, int index) {
        mMemoria = memoria;
        mGroup = group;
        mIndex = index;
    }

    public Gene getGene() {
        Gene gene = null;
        switch (mGroup) {
        case GROUP_WEAPON:
            gene = mMemoria.getWeaponRateArr()[mIndex];
            break;
        case GROUP_ACCESSORY:
            gene = mMemoria.getAccessoryRateArr()[mIndex];
            break;
        }
        return gene;
    }

    public Memoria getMemoria() {
        return mMemoria;
    }

    public ItemData getItemData() {
        ItemData itemData = null;
        switch (mGroup) {
        case GROUP_WEAPON:
            itemData = mMemoria.getMemoriaData().getWeaponData(mIndex);
            break;
        case GROUP_ACCESSORY:
            itemData = mMemoria.getMemoriaData().getAccessoryData(mIndex);
            break;
        }
        return itemData;
    }

}
