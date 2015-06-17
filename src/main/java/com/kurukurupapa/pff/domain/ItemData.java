package com.kurukurupapa.pff.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * アイテムデータクラス
 */
public class ItemData {

	/** アイテム種別 */
	private ItemType mItemType;
	/** アイテム種別2 */
	private ItemType2 mItemType2;

	/** 名前 */
	private String mName;

	/** HP単位 */
	private Unit mHpUnit;
	/** HP数値 */
	private int mHp;
	/** 力単位 */
	private Unit mPowerUnit;
	/** 力数値 */
	private int mPower;
	/** 素早さ単位 */
	private Unit mSpeedUnit;
	/** 素早さ数値 */
	private int mSpeed;
	/** 知性単位 */
	private Unit mIntelligenceUnit;
	/** 知性数値 */
	private int mIntelligence;
	/** 運単位 */
	private Unit mLuckUnit;
	/** 運数値 */
	private int mLuck;

	/** 物理防御特性 */
	private int mPhysicalDefence;
	/** 魔法防御特性 */
	private int mMagicDefence;

	/** 属性 */
	protected Attr mAttr;

	/** 専用アイテムにおける対象メモリア名 */
	private String mMemoriaName;

	/** 個数 */
	private int mNumber;

	/** アイテムデータ拡張 */
	private ItemDataEx mEx;

	/**
	 * 武器用コンストラクタ
	 *
	 * @param type
	 *            武器種別
	 * @param name
	 *            名前
	 * @param hp
	 *            HP
	 * @param power
	 *            力
	 * @param speed
	 *            素早さ
	 * @param intelligence
	 *            知性
	 * @param luck
	 *            幸運
	 * @param attr
	 *            属性
	 * @param number
	 *            個数
	 */
	public ItemData(String type, String name, int hp, int power, int speed,
			int intelligence, int luck, Attr attr, int number) {
		this(ItemType.WEAPON, new ItemType2(type), name, hp, Unit.VALUE, power,
				Unit.VALUE, speed, Unit.VALUE, intelligence, Unit.VALUE, luck,
				Unit.VALUE, 0, 0, MagicType.NONE, 0, 0, attr, null, number);
	}

	/**
	 * 魔法用コンストラクタ
	 *
	 * @param name
	 *            名前
	 * @param magicType
	 *            魔法種別
	 * @param charge
	 *            魔法チャージ
	 * @param effect
	 *            魔法効果値
	 * @param attr
	 *            属性
	 * @param number
	 *            個数
	 */
	public ItemData(String name, MagicType magicType, int charge, int effect,
			Attr attr, int number) {
		this(ItemType.MAGIC, new ItemType2(magicType.toString()), name, 0,
				Unit.VALUE, 0, Unit.VALUE, 0, Unit.VALUE, 0, Unit.VALUE, 0,
				Unit.VALUE, 0, 0, magicType, charge, effect, attr, null, number);
	}

	/**
	 * アクセサリ用コンストラクタ
	 *
	 * @param name
	 *            名前
	 * @param hp
	 *            HP
	 * @param power
	 *            力
	 * @param speed
	 *            素早さ
	 * @param intelligence
	 *            知性
	 * @param luck
	 *            幸運
	 * @param physicalDefence
	 *            物理防御特性
	 * @param magicDefence
	 *            魔法防御特性
	 * @param attr
	 *            属性
	 * @param number
	 *            個数
	 */
	public ItemData(String itemType2, String name, int hp, int power,
			int speed, int intelligence, int luck, int physicalDefence,
			int magicDefence, Attr attr, int number) {
		this(ItemType.ACCESSORY, new ItemType2(itemType2), name, hp,
				Unit.VALUE, power, Unit.VALUE, speed, Unit.VALUE, intelligence,
				Unit.VALUE, luck, Unit.VALUE, physicalDefence, magicDefence,
				MagicType.NONE, 0, 0, attr, null, number);
	}

	/**
	 * アクセサリ用コンストラクタ
	 *
	 * @param name
	 *            名前
	 * @param hp
	 *            HP
	 * @param hpUnit
	 *            HP単位
	 * @param power
	 *            力
	 * @param powerUnit
	 *            力単位
	 * @param speed
	 *            素早さ
	 * @param speedUnit
	 *            素早さ単位
	 * @param intelligence
	 *            知性
	 * @param intelligenceUnit
	 *            知性単位
	 * @param luck
	 *            幸運
	 * @param luckUnit
	 *            幸運単位
	 * @param physicalDefence
	 *            物理防御特性
	 * @param magicDefence
	 *            魔法防御特性
	 * @param attr
	 *            属性
	 * @param number
	 *            個数
	 */
	public ItemData(String itemType2, String name, int hp, Unit hpUnit,
			int power, Unit powerUnit, int speed, Unit speedUnit,
			int intelligence, Unit intelligenceUnit, int luck, Unit luckUnit,
			int physicalDefence, int magicDefence, Attr attr, String memoria,
			int number) {
		this(ItemType.ACCESSORY, new ItemType2(itemType2), name, hp, hpUnit,
				power, powerUnit, speed, speedUnit, intelligence,
				intelligenceUnit, luck, luckUnit, physicalDefence,
				magicDefence, MagicType.NONE, 0, 0, attr, memoria, number);
	}

	/**
	 * 汎用コンストラクタ
	 *
	 * @param itemType
	 *            アイテム種別
	 * @param name
	 *            名前
	 * @param hp
	 *            HP
	 * @param hpUnit
	 *            HP単位
	 * @param power
	 *            力
	 * @param powerUnit
	 *            力単位
	 * @param speed
	 *            素早さ
	 * @param speedUnit
	 *            素早さ単位
	 * @param intelligence
	 *            知性
	 * @param intelligenceUnit
	 *            知性単位
	 * @param luck
	 *            幸運
	 * @param luckUnit
	 *            幸運単位
	 * @param physicalDefence
	 *            物理防御特性
	 * @param magicDefence
	 *            魔法防御特性
	 * @param magicType
	 *            魔法種別
	 * @param magicCharge
	 *            魔法チャージ
	 * @param magicEffect
	 *            魔法効果値
	 * @param attr
	 *            属性
	 * @param weaponType
	 *            武器種別
	 * @param memoria
	 *            専用アイテムの場合、対象メモリア名を設定します。
	 * @param number
	 *            個数
	 */
	public ItemData(ItemType itemType, ItemType2 itemType2, String name,
			int hp, Unit hpUnit, int power, Unit powerUnit, int speed,
			Unit speedUnit, int intelligence, Unit intelligenceUnit, int luck,
			Unit luckUnit, int physicalDefence, int magicDefence,
			MagicType magicType, int magicCharge, int magicEffect, Attr attr,
			String memoria, int number) {
		mItemType = itemType;
		mItemType2 = itemType2;
		mName = name;
		mHp = hp;
		mHpUnit = hpUnit;
		mPower = power;
		mPowerUnit = powerUnit;
		mSpeed = speed;
		mSpeedUnit = speedUnit;
		mIntelligence = intelligence;
		mIntelligenceUnit = intelligenceUnit;
		mLuck = luck;
		mLuckUnit = luckUnit;
		mPhysicalDefence = physicalDefence;
		mMagicDefence = magicDefence;
		mAttr = attr;
		mMemoriaName = memoria;
		mNumber = number;

		mEx = ItemDataExFactory.create(this, itemType, itemType2, attr,
				magicCharge, magicEffect);
	}

	@Override
	public String toString() {
		return mName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ItemData)) {
			return false;
		}
		ItemData other = (ItemData) obj;
		if (!mName.equals(other.mName)) {
			return false;
		}
		return true;
	}

	public ItemType getItemType() {
		return mItemType;
	}

	/**
	 * アイテム名を取得します。
	 *
	 * @return アイテム名
	 */
	public String getName() {
		return mName;
	}

	/**
	 * 追加HPを取得します。
	 *
	 * @param memoriaData
	 *            メモリアデータ
	 * @return
	 */
	public int getHp(MemoriaData memoriaData) {
		return calc(mHpUnit, mHp, memoriaData.getHp());
	}

	public int getPower(MemoriaData memoriaData) {
		return calc(mPowerUnit, mPower, memoriaData.getPower());
	}

	public int getSpeed(MemoriaData memoriaData) {
		return calc(mSpeedUnit, mSpeed, memoriaData.getSpeed());
	}

	public int getIntelligence(MemoriaData memoriaData) {
		return calc(mIntelligenceUnit, mIntelligence,
				memoriaData.getIntelligence());
	}

	public int getLuck(MemoriaData memoriaData) {
		return calc(mLuckUnit, mLuck, memoriaData.getLuck());
	}

	public int getPhysicalDefence() {
		return mPhysicalDefence;
	}

	public int getMagicDefence() {
		return mMagicDefence;
	}

	public Attr getAttr() {
		return mAttr;
	}

	public ItemDataEx getEx() {
		return mEx;
	}

	public WeaponItemDataEx getWeaponEx() {
		if (mEx instanceof WeaponItemDataEx) {
			return (WeaponItemDataEx) mEx;
		}
		return null;
	}

	public MagicItemDataEx getMagicEx() {
		if (mEx instanceof MagicItemDataEx) {
			return (MagicItemDataEx) mEx;
		}
		return null;
	}

	public WhiteMagicItemDataEx getWhiteMagicEx() {
		if (mEx instanceof WhiteMagicItemDataEx) {
			return (WhiteMagicItemDataEx) mEx;
		}
		return null;
	}

	public BlackMagicItemDataEx getBlackMagicEx() {
		if (mEx instanceof BlackMagicItemDataEx) {
			return (BlackMagicItemDataEx) mEx;
		}
		return null;
	}

	public SummonMagicItemDataEx getSummonMagicEx() {
		if (mEx instanceof SummonMagicItemDataEx) {
			return (SummonMagicItemDataEx) mEx;
		}
		return null;
	}

	public AccessoryItemDataEx getAccessoryEx() {
		if (mEx instanceof AccessoryItemDataEx) {
			return (AccessoryItemDataEx) mEx;
		}
		return null;
	}

	/**
	 * アイテムの個数を取得します。
	 *
	 * @return 個数
	 */
	public int getNumber() {
		return mNumber;
	}

	/**
	 * 指定メモリアに対して装備可能か判定します。
	 *
	 * @param memoria
	 *            メモリア
	 * @return 装備可能な場合true
	 */
	public boolean isValid(MemoriaData memoria) {
		if (!isValidMemoria(memoria.getName())) {
			return false;
		}
		if (mItemType == ItemType.WEAPON) {
			if (!mEx.isValid(memoria)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 指定メモリアに対して装備可能か判定します。
	 *
	 * @param name
	 *            メモリア名
	 * @return 装備可能な場合true
	 */
	public boolean isValidMemoria(String name) {
		if (StringUtils.isEmpty(mMemoriaName)) {
			return true;
		} else {
			return mMemoriaName.equals(name);
		}
	}

	private int calc(Unit unit, int effectValue, int memoriaValue) {
		int result = 0;
		switch (unit) {
		case VALUE:
			result = effectValue;
			break;
		case PERCENT:
			result = memoriaValue * effectValue / 100;
			break;
		}
		return result;
	}

}
