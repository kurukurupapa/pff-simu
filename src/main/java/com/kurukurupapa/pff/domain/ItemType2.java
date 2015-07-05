package com.kurukurupapa.pff.domain;

import java.util.Arrays;
import java.util.List;

/**
 * アイテム種別2クラス
 */
public class ItemType2 {
	private static final List<String> BOOST_TYPE_LIST = Arrays
			.asList(new String[] { "杖", "ロッド", "波動", "指輪" });

	private String mType;

	public ItemType2() {
	}

	public ItemType2(String type) {
		mType = type.trim();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		ItemType2 other = (ItemType2) obj;
		return mType.equals(other.mType);
	}

	@Override
	public String toString() {
		return mType;
	}

	public boolean isBoost() {
		return BOOST_TYPE_LIST.contains(mType);
	}

	public String getText() {
		return mType;
	}

}
