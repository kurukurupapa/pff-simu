package com.kurukurupapa.pff.apachega;

import java.util.List;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * 適応度クラス
 */
public class Fitness {
    /** パーティ参加メモリアの最大数 */
    private static final int MAX_PARTY_MEMORIAS = 4;

    /** メモリアデータ集合 */
    private MemoriaDataSet mMemoriaDataSet;
    /** アイテムデータ集合 */
    private ItemDataSet mItemDataSet;

    /**
     * コンストラクタ
     *
     * @param memoriaDataSet
     * @param itemDataSet
     */
    public Fitness(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
        mMemoriaDataSet = memoriaDataSet;
        mItemDataSet = itemDataSet;
    }

    /**
     * 適応度を計算します。
     *
     * @param representation
     * @return
     */
    public double calc(List<Integer> representation) {
        double value = 0.0;
        int memoriaCount = 0;
        for (int i = 0; i < representation.size(); i++) {
            int gene = representation.get(i);
            MemoriaData memoriaData = mMemoriaDataSet.get(i);
            if (gene > 0) {
                memoriaCount++;
                value += memoriaData.getHp();
            }
        }
        if (memoriaCount > MAX_PARTY_MEMORIAS) {
            value = 0.0;
        }
        return value;
    }

}
