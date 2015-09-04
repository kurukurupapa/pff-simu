package com.kurukurupapa.pff.ga01.domain;

import java.util.HashMap;
import java.util.Map;

import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;

/**
 * アイテム割り当てクラス
 */
public class ItemAssign {

    private ItemDataSet mItemDataSet;
    private Map<String, Integer> mCountMap;

    /**
     * ファクトリーメソッド
     */
    public static ItemAssign create(ItemDataSet itemDataSet) {
        ItemAssign itemAssign = new ItemAssign(itemDataSet);
        return itemAssign;
    }

    /**
     * コンストラクタ
     *
     * @param itemDataSet
     */
    private ItemAssign(ItemDataSet itemDataSet) {
        mItemDataSet = itemDataSet;
        mCountMap = new HashMap<String, Integer>();
        for (ItemData e : itemDataSet.makeAllItemDataList()) {
            mCountMap.put(e.getName(), 0);
        }
    }

    /**
     * 引数のアイテムが割り当て可能か判定する。
     *
     * @param data
     * @return
     */
    public boolean isAssign(ItemData data) {
        ItemData itemData = mItemDataSet.find(data.getName());
        Integer count = mCountMap.get(data.getName());
        return count < itemData.getNumber();
    }

    /**
     * 引数のアイテムを割り当てたことをカウントする。
     *
     * @param data
     */
    public void assign(ItemData data) {
        mCountMap.put(data.getName(), mCountMap.get(data.getName()) + 1);
    }
}
