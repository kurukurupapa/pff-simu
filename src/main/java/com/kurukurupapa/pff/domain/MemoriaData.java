package com.kurukurupapa.pff.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * メモリアデータ
 */
public class MemoriaData {
	/** 名前 */
	private String mName;
	/** HP */
	private int mHp;
	/** 力 */
	private int mPower;
	/** 素早さ */
	private int mSpeed;
	/** 知性 */
	private int mIntelligence;
	/** 運 */
	private int mLuck;
	/** 物理攻撃特性 */
	private float mPhysicalAttack;
	/** 白魔法攻撃特性 */
	private float mWhiteMagicAttack;
	/** 黒魔法攻撃特性 */
	private float mBlackMagicAttack;
	/** 召喚攻撃特性 */
	private float mSummonMagicAttack;
	/** 武器リスト */
	private ItemData[] mWeaponDataArr;
	/** 魔法、アクセサリリスト */
	private ItemData[] mAccessoryDataArr;
	/** 武器種別 */
	private WeaponType mWeaponType;
	/** ジョブスキル */
	private JobSkill mJobSkill;
	/** プレミアムスキル */
	private PremiumSkill mPremiumSkill;

	/**
	 * コンストラクタ
	 */
	public MemoriaData(String name, int hp, int power, int speed,
			int intelligence, int luck, float physicalAttack,
			float whiteMagicAttack, float blackMagicAttack, float summonAttack,
			WeaponType weaponType, JobSkill jobSkill,
			PremiumSkill premiumSkill, ItemDataSet itemDataSet) {

		init(name, hp, power, speed, intelligence, luck, physicalAttack,
				whiteMagicAttack, blackMagicAttack, summonAttack, null, null,
				weaponType, jobSkill, premiumSkill);

		// 装備可能な武器リストを作成します。
		List<ItemData> weaponDataList = new ArrayList<ItemData>();
		for (ItemData e : itemDataSet.getWeaponList()) {
			if (e.getEx().isValid(this)) {
				weaponDataList.add(e);
			}
		}

		// 装備可能な魔法/アクセサリリストを作成します。
		List<ItemData> accessoryDataList = new ArrayList<ItemData>();
		for (ItemData e : itemDataSet.getMagicAccessoryList()) {
			if (e.isValidMemoria(name)) {
				accessoryDataList.add(e);
			}
		}

		mWeaponDataArr = weaponDataList.toArray(new ItemData[] {});
		mAccessoryDataArr = accessoryDataList.toArray(new ItemData[] {});
	}

	/**
	 * コンストラクタ
	 */
	public MemoriaData(String name, int hp, int power, int speed,
			int intelligence, int luck, float physicalAttack,
			float whiteMagicAttack, float blackMagicAttack, float summonAttack,
			ItemData[] weaponDataArr, ItemData[] accessoryDataArr,
			WeaponType weaponType, JobSkill jobSkill, PremiumSkill premiumSkill) {
		init(name, hp, power, speed, intelligence, luck, physicalAttack,
				whiteMagicAttack, blackMagicAttack, summonAttack,
				weaponDataArr, accessoryDataArr, weaponType, jobSkill,
				premiumSkill);
	}

	private void init(String name, int hp, int power, int speed,
			int intelligence, int luck, float physicalAttack,
			float whiteMagicAttack, float blackMagicAttack, float summonAttack,
			ItemData[] weaponDataArr, ItemData[] accessoryDataArr,
			WeaponType weaponType, JobSkill jobSkill, PremiumSkill premiumSkill) {
		mName = name;
		mHp = hp;
		mPower = power;
		mSpeed = speed;
		mIntelligence = intelligence;
		mLuck = luck;
		mPhysicalAttack = physicalAttack;
		mWhiteMagicAttack = whiteMagicAttack;
		mBlackMagicAttack = blackMagicAttack;
		mSummonMagicAttack = summonAttack;
		mWeaponDataArr = weaponDataArr;
		mAccessoryDataArr = accessoryDataArr;
		mWeaponType = weaponType;
		mJobSkill = jobSkill;
		mPremiumSkill = premiumSkill;
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getName() {
		return mName;
	}

	public int getHp() {
		return mHp;
	}

	public int getPower() {
		return mPower;
	}

	public int getSpeed() {
		return mSpeed;
	}

	public int getIntelligence() {
		return mIntelligence;
	}

	public int getLuck() {
		return mLuck;
	}

	public float getPhysicalAttack() {
		return mPhysicalAttack;
	}

	public float getMagicAttack(MagicType magicType) {
		float value;
		switch (magicType) {
		case WHITE:
			value = mWhiteMagicAttack;
			break;
		case BLACK:
			value = mBlackMagicAttack;
			break;
		case SUMMON:
			value = mSummonMagicAttack;
			break;
		default:
			value = 0.0f;
			break;
		}
		return value;
	}

	public float getWhiteMagicAttack() {
		return mWhiteMagicAttack;
	}

	public float getBlackMagicAttack() {
		return mBlackMagicAttack;
	}

	public float getSummonMagicAttack() {
		return mSummonMagicAttack;
	}

	public int getNumWeapons() {
		return mWeaponDataArr.length;
	}

	public ItemData getWeaponData(int index) {
		return mWeaponDataArr[index];
	}

	public boolean validWeaponData(ItemData weaponData) {
		for (ItemData e : mWeaponDataArr) {
			if (e.getName().equals(weaponData.getName())) {
				return true;
			}
		}
		return false;
	}

	public int getNumAccessories() {
		return mAccessoryDataArr.length;
	}

	public ItemData getAccessoryData(int index) {
		return mAccessoryDataArr[index];
	}

	public boolean validAccessoryData(ItemData accessoryData) {
		for (ItemData e : mAccessoryDataArr) {
			if (e.getName().equals(accessoryData.getName())) {
				return true;
			}
		}
		return false;
	}

	public WeaponType getWeaponType() {
		return mWeaponType;
	}

	public JobSkill getJobSkill() {
		return mJobSkill;
	}

	public PremiumSkill getPremiumSkill() {
		return mPremiumSkill;
	}

}
