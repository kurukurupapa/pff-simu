package com.kurukurupapa.pff.ga01.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * パーティクラス（個体：Individual）
 */
public class Party extends PartyChromosome {
    private static final int MAX_MEMORIA = 4;

    /** パーティ参加対象のメモリアリスト */
    private List<Memoria> mMemoriaList;
    /** 武器リスト */
    private List<MemoriaItem> mSortedWeaponStatusList;
    /** アクセサリリスト */
    private List<MemoriaItem> mSortedAccessoryStatusList;
    /** 適応度 */
    private int mFitness;

    /**
     * コンストラクタ
     *
     * ランダムな遺伝子を持つパーティステータスを作成します。
     */
    public Party(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
        super(memoriaDataSet, itemDataSet);
        init();
    }

    /**
     * コンストラクタ
     *
     * 2つの親からパーティステータスを作成します。
     *
     * @param mutationRate
     *            突然変異率。0.0～1.0で設定します。
     */
    public Party(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet,
            PartyChromosome parent1, PartyChromosome parent2, float mutationRate) {
        super(memoriaDataSet, itemDataSet, parent1, parent2, mutationRate);
        init();
    }

    /**
     * コンストラクタ
     *
     * 2つの親からパーティステータスを作成します。
     *
     * @param mutationRate
     *            突然変異率。0.0～1.0で設定します。
     */
    public Party(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet,
            PartyChromosome parent1, PartyChromosome parent2, float mutationRate,
            int position) {
        super(memoriaDataSet, itemDataSet, parent1, parent2, mutationRate,
                position);
        init();
    }

    /**
     * 初期化します。
     */
    private void init() {
        mMemoriaList = new ArrayList<Memoria>();
        int numMemoria = Math.min(MAX_MEMORIA, getMemoriaDataSet()
                .size());

        // メモリアの決定
        List<Memoria> sortedMemoriaList = getSortedMemoriaList();
        for (int i = 0; i < numMemoria; i++) {
            mMemoriaList.add(sortedMemoriaList.get(i));
        }

        // アイテム決定の準備
        ItemAssign itemAssign = ItemAssign.create(getItemDataSet());

        // 武器の決定
        List<MemoriaItem> weaponStatusList = getSortedWeaponStatusList();
        for (MemoriaItem e : weaponStatusList) {
            Memoria memoria = e.getMemoria();
            // 当該メモリアに対して、まだ武器を割り当てていないことを確認。
            if (!memoria.hasWeaponData()) {
                // 割り当てようとしている武器が、他に割り当てていないことを確認。
                if (itemAssign.isAssign(e.getItemData())) {
                    memoria.setWeaponData(e.getItemData());
                    itemAssign.assign(e.getItemData());
                }
            }
        }

        // アクセサリの決定
        List<MemoriaItem> accessoryStatusList = getSortedAccessoryStatusList();
        for (MemoriaItem e : accessoryStatusList) {
            Memoria memoria = e.getMemoria();
            // 当該メモリアに対して、まだアイテムを割り当て可能であることを確認。
            if (!memoria.hasMaxAccessoryData()) {
                // 割り当てようとしているアイテムが、他に割り当てていないことを確認。
                if (itemAssign.isAssign(e.getItemData())) {
                    memoria.addAccessoryData(e.getItemData());
                    itemAssign.assign(e.getItemData());
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mFitness);
        for (Memoria e : mMemoriaList) {
            sb.append(",");
            sb.append(e.toString());
        }
        return sb.toString();
    }

    /**
     * 優先の高い順にソートした武器ステータスリストを取得します。
     *
     * @return
     */
    private List<MemoriaItem> getSortedWeaponStatusList() {
        if (mSortedWeaponStatusList == null) {
            mSortedWeaponStatusList = new ArrayList<MemoriaItem>();
            for (Memoria e : mMemoriaList) {
                for (int i = 0; i < e.getMemoriaData().getNumWeapons(); i++) {
                    mSortedWeaponStatusList.add(MemoriaItem.createWeaponStatus(
                            e, i));
                }
            }
            Collections.sort(mSortedWeaponStatusList,
                    new Comparator<MemoriaItem>() {
                        @Override
                        public int compare(MemoriaItem o1, MemoriaItem o2) {
                            return -1 * o1.getGene().compareTo(o2.getGene());
                        }
                    });
        }
        return mSortedWeaponStatusList;
    }

    /**
     * 優先の高い順にソートしたアクセサリステータスリストを取得します。
     *
     * @return
     */
    private List<MemoriaItem> getSortedAccessoryStatusList() {
        if (mSortedAccessoryStatusList == null) {
            mSortedAccessoryStatusList = new ArrayList<MemoriaItem>();
            for (Memoria e : mMemoriaList) {
                for (int i = 0; i < e.getMemoriaData().getNumAccessories(); i++) {
                    mSortedAccessoryStatusList.add(MemoriaItem
                            .createAccessoryStatus(e, i));
                }
            }
            Collections.sort(mSortedAccessoryStatusList,
                    new Comparator<MemoriaItem>() {
                        @Override
                        public int compare(MemoriaItem o1, MemoriaItem o2) {
                            return -1 * o1.getGene().compareTo(o2.getGene());
                        }
                    });
        }
        return mSortedAccessoryStatusList;
    }

    /**
     * パーティの適応度を計算します。
     *
     * @param fitness
     */
    public void calcFitness(Fitness fitness) {
        mFitness = fitness.calc(this);
    }

    /**
     * パーティの適応度を取得します。
     *
     * @return
     */
    public int getFitness() {
        return mFitness;
    }

    public int getHp() {
        int value = 0;
        for (Memoria e : mMemoriaList) {
            value += e.getHp();
        }
        return value;
    }

    public int getPower() {
        int value = 0;
        for (Memoria e : mMemoriaList) {
            value += e.getPower();
        }
        return value;
    }

    public int getPhysicalAttackDamage() {
        int value = 0;
        for (Memoria e : mMemoriaList) {
            value += e.getPhysicalAttackDamage();
        }
        return value;
    }

    public int getPhysicalDefenceDamage() {
        int value = 0;
        for (Memoria e : mMemoriaList) {
            value += e.getPhysicalDefenceDamage();
        }
        return value;
    }

    public int getMagicAttackDamage() {
        int value = 0;
        for (Memoria e : mMemoriaList) {
            value += e.getMagicAttackDamage();
        }
        return value;
    }

    public int getMagicDefenceDamage() {
        int value = 0;
        for (Memoria e : mMemoriaList) {
            value += e.getMagicDefenceDamage();
        }
        return value;
    }

    public int getInoriRecovery() {
        int value = 0;
        for (Memoria e : mMemoriaList) {
            value += e.getInoriRecovery();
        }
        return value;
    }

}
