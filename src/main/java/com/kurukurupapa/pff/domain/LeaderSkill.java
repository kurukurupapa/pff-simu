package com.kurukurupapa.pff.domain;

/**
 * リーダースキルクラス
 */
public enum LeaderSkill {
	/** アーロンLS HP小（18%）アップ */
	LS029("アーロン", 18, Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	/** トレイLS 力小（10%）アップ */
	LS052("トレイ", 0, Unit.PERCENT, 10, Unit.PERCENT, 0, Unit.PERCENT, 0,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	/** アーシェLS 知性微小（6%）アップ */
	LS117("アーシェ", 0, Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 6,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	/** 元帥シド 「無属性武器攻撃」が【大】アップ（無属性（武器系） 37%） */
	LS187("元帥シド", 0, Unit.PERCENT, 37, Unit.PERCENT, 0, Unit.PERCENT, 0,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	//
	;

	private String mMemoriaName;
	private ItemData mItemData;

	public static LeaderSkill parse(String memoriaName) {
		LeaderSkill leaderSkill = null;
		for (LeaderSkill e : values()) {
			if (memoriaName.indexOf(e.mMemoriaName) >= 0) {
				leaderSkill = e;
				break;
			}
		}
		return leaderSkill;
	}

	private LeaderSkill(String name, int hp, Unit hpUnit, int power,
			Unit powerUnit, int speed, Unit speedUnit, int intelligence,
			Unit intelligenceUnit, int luck, Unit luckUnit,
			int physicalDefence, int magicDefence, Attr attr) {
		mMemoriaName = name;
		mItemData = new ItemData("", name + "LS", hp, hpUnit, power, powerUnit,
				speed, speedUnit, intelligence, intelligenceUnit, luck,
				luckUnit, physicalDefence, magicDefence, attr, null, 1);
	}

	public ItemData getItemData() {
		return mItemData;
	}

}
