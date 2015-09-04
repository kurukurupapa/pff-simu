package com.kurukurupapa.pff.ga01.domain;

import java.util.ArrayList;
import java.util.List;

import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;

/**
 * メモリアクラス
 */
public class Memoria extends MemoriaGenes {
    private static final int MAX_ACCESSORY = 2;

    // 武器
    private ItemData mWeaponData;
    // 魔法、アクセサリ
    private List<ItemData> mAccessoryDataList;

    /**
     * コンストラクタ
     *
     * @param memoriaGenes
     */
    public Memoria(MemoriaData memoriaData, ItemDataSet itemDataSet) {
        super(memoriaData, itemDataSet);
        mAccessoryDataList = new ArrayList<ItemData>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        if (mWeaponData != null) {
            sb.append("+" + mWeaponData.getName());
        }
        for (ItemData e : mAccessoryDataList) {
            sb.append("+" + e.getName());
        }
        return sb.toString();
    }

    public boolean hasWeaponData() {
        return mWeaponData != null;
    }

    public void setWeaponData(ItemData weaponData) {
        mWeaponData = weaponData;
    }

    public boolean hasMaxAccessoryData() {
        return mAccessoryDataList.size() >= MAX_ACCESSORY;
    }

    public void addAccessoryData(ItemData accessoryData) {
        mAccessoryDataList.add(accessoryData);
    }

    private ItemData[] getAllItemData() {
        List<ItemData> itemDataList = new ArrayList<ItemData>();
        if (mWeaponData != null) {
            itemDataList.add(mWeaponData);
        }
        for (ItemData e : mAccessoryDataList) {
            itemDataList.add(e);
        }
        return itemDataList.toArray(new ItemData[] {});
    }

    public String getName() {
        return getMemoriaData().getName();
    }

    public int getHp() {
        int value = 0;
        value += getMemoriaData().getHp();
        for (ItemData e : getAllItemData()) {
            value += e.getHp(getMemoriaData());
        }
        return value;
    }

    public int getPower() {
        int value = 0;
        value += getMemoriaData().getPower();
        for (ItemData e : getAllItemData()) {
            value += e.getPower(getMemoriaData());
        }
        return value;
    }

    public int getIntelligence() {
        int value = 0;
        value += getMemoriaData().getIntelligence();
        for (ItemData e : getAllItemData()) {
            value += e.getIntelligence(getMemoriaData());
        }
        return value;
    }

    public float getPhysicalAttackDamage() {
        // 物理与ダメージ＝(力×攻撃力補正－物理防御力)×倍率
        return getPower() * getMemoriaData().getPhysicalAttack();
    }

    public float getMagicAttackDamage() {
        // TODO 魔法ダメージ＝(知性＋効果値)×倍率－魔法防御力
        return getIntelligence()
                * Math.max(getMemoriaData().getWhiteMagicAttack(),
                        getMemoriaData().getBlackMagicAttack());
    }

    public int getPhysicalDefenceDamage() {
        // 物理被ダメージ＝(力－物理防御力)×倍率
        int value = 0;
        if (mWeaponData != null) {
            value += mWeaponData.getPhysicalDefence();
        }
        for (ItemData e : mAccessoryDataList) {
            value += e.getPhysicalDefence();
        }
        return value;
    }

    public int getMagicDefenceDamage() {
        // 魔法被ダメージ＝(効果値×メメント効果－魔法防御力×倍率)×魔法効果
        int value = 0;
        if (mWeaponData != null) {
            value += mWeaponData.getMagicDefence();
        }
        for (ItemData e : mAccessoryDataList) {
            value += e.getMagicDefence();
        }
        return value;
    }

    public int getInoriRecovery() {
        // 祈りメメントの回復量
        return getHp() / 50 + getIntelligence() / 2;
    }
}
