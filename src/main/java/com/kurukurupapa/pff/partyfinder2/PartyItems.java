package com.kurukurupapa.pff.partyfinder2;

import java.util.ArrayList;
import java.util.List;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;

/**
 * パーティアイテムクラス
 */
public class PartyItems {
	private ItemDataSet mItemDataSet;

	public PartyItems(ItemDataSet itemDataSet) {
		mItemDataSet = itemDataSet;
	}

	public List<ItemData> getWeapons(MemoriaData memoria) {
		List<ItemData> list = new ArrayList<ItemData>();
		for (ItemData e : mItemDataSet.getWeaponList()) {
			if (e.isValid(memoria)) {
				for (int i = 0; i < e.getNumber(); i++) {
					list.add(e);
				}
			}
		}
		return list;
	}

	public List<ItemData> getMagicsAccessories(MemoriaData memoria) {
		List<ItemData> result = new ArrayList<ItemData>();
		for (ItemData e : mItemDataSet.getMagicAccessoryList()) {
			if (e.isValid(memoria)) {
				for (int i = 0; i < e.getNumber(); i++) {
					result.add(e);
				}
			}
		}
		return result;
	}

}
