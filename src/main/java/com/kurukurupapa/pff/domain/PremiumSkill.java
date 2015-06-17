package com.kurukurupapa.pff.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * プレミアムスキルクラス
 */
public class PremiumSkill {
	private String mName;

	public PremiumSkill(String name) {
		mName = name.trim();
	}

	@Override
	public boolean equals(Object obj) {
		PremiumSkill other = (PremiumSkill) obj;
		return mName.equals(other.mName);
	}

	@Override
	public String toString() {
		return mName;
	}

	public boolean isExist() {
		return StringUtils.isNotEmpty(mName);
	}

	public int getAttackDamage(int turn) {
		int damage = 0;
		if (mName.equals("生殺与奪")) {
			// パンネロ
			// 中速（6ターンごと）、知恵メメント、効果値3200
			int count = turn / 6;
			damage = 3200 * count;
		} else if (mName.equals("ブレイズラッシュ")) {
			// ライトニング（No.119）
			// 中速（6ターンごと）、力メメント、効果値2100
			// TODO 雷弱点付与
			int count = turn / 6;
			damage = 2100 * count;
		} else if (mName.equals("スパークブロウ")) {
			// ライトニング（No.39）
			// 中速（6ターンごと）、知恵メメント、効果値2100
			// TODO 雷属性
			int count = turn / 6;
			damage = 2100 * count;
		}
		return damage;
	}

}
