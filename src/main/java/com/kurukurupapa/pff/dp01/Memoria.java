package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.BlackMagicItemDataEx;
import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.MagicType;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.SummonMagicItemDataEx;
import com.kurukurupapa.pff.domain.WhiteMagicItemDataEx;

public class Memoria implements Cloneable {
	/** 武器スロット数 */
	public static final int MAX_WEAPON = 1;
	/** アクセサリスロット数 */
	public static final int MAX_ACCESSORIES = 2;
	/** 力メメントの物理倍率 */
	public static final float MEMENT_CHIKARA_PHYSICAL_RATE = 1.2f;
	/** 知恵メメントの黒魔法倍率 */
	public static final float MEMENT_CHIE_BLACK_RATE = 1.2f;
	/** 祈りメメントの白魔法倍率 */
	public static final float MEMENT_INORI_WHITE_RATE = 1.25f;
	/** 力メメントのリメントゲージ上昇率（メモリアの攻撃1回あたりの増加比率）（%） */
	public static final float MEMENT_CHIKARA_REMENT_RATE = 4.4f;
	/** 知恵メメントのリメントゲージ上昇率（メモリアの攻撃1回あたりの増加比率）（%） */
	public static final float MEMENT_CHIE_REMENT_RATE = 11.0f;
	/** 力メメントのブレイクゲージ上昇率（パズル1マスあたりのブレイクゲージの上昇率）（%） */
	public static final float MEMENT_CHIKARA_BREAK_RATE = 0.40f;
	/** 知恵メメントのブレイクゲージ上昇率（パズル1マスあたりのブレイクゲージの上昇率）（%） */
	public static final float MEMENT_CHIE_BREAK_RATE = 4.65f;

	protected MemoriaData mMemoriaData;
	protected ItemData mWeaponData;
	protected ItemData[] mAccessoryDataArr;
	protected ItemData[] mLeaderSkillArr;

	public Memoria(MemoriaData memoriaData) {
		mMemoriaData = memoriaData;
		mAccessoryDataArr = new ItemData[] {};
		mLeaderSkillArr = new ItemData[] {};
	}

	public Memoria(Memoria memoria) {
		mMemoriaData = memoria.mMemoriaData;
		mWeaponData = memoria.mWeaponData;
		mAccessoryDataArr = memoria.mAccessoryDataArr.clone();
		mLeaderSkillArr = memoria.mLeaderSkillArr.clone();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mMemoriaData.getName());
		if (mWeaponData != null) {
			sb.append("+" + mWeaponData.getName());
		}
		for (ItemData e : mAccessoryDataArr) {
			sb.append("+" + e.getName());
		}
		for (ItemData e : mLeaderSkillArr) {
			sb.append("+" + e.getName());
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Memoria)) {
			return false;
		}
		Memoria other = (Memoria) obj;
		if (!getName().equals(other.getName())) {
			return false;
		}
		// 武器の確認
		if (mWeaponData == null) {
			if (other.mWeaponData != null) {
				return false;
			}
		} else {
			if (!mWeaponData.equals(other.mWeaponData)) {
				return false;
			}
		}
		// アクセサリの確認
		if (mAccessoryDataArr.length != other.mAccessoryDataArr.length) {
			return false;
		}
		for (int i = 0; i < mAccessoryDataArr.length; i++) {
			if (!mAccessoryDataArr[i].equals(other.mAccessoryDataArr[i])) {
				return false;
			}
		}
		// リーダースキルの確認
		if (mLeaderSkillArr.length != other.mLeaderSkillArr.length) {
			return false;
		}
		for (int i = 0; i < mLeaderSkillArr.length; i++) {
			if (!mLeaderSkillArr[i].equals(other.mLeaderSkillArr[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Memoria clone() {
		return new Memoria(this);
	}

	public boolean isSame(Memoria memoria) {
		return mMemoriaData.getName().equals(memoria.mMemoriaData.getName());
	}

	public boolean isWeapon() {
		return mWeaponData != null;
	}

	public ItemData getWeapon() {
		return mWeaponData;
	}

	public int getRemainWeaponSlot() {
		return mWeaponData == null ? 1 : 0;
	}

	public boolean validWeaponData(ItemData weaponData) {
		return mMemoriaData.validWeaponData(weaponData);
	}

	public void setWeapon(ItemData weaponData) {
		if (!validWeaponData(weaponData)) {
			throw new RuntimeException("メモリア[" + getName() + "]に武器["
					+ weaponData.getName() + "]を設定できません。");
		}
		mWeaponData = weaponData;
	}

	public void clearWeapon() {
		mWeaponData = null;
	}

	public ItemData[] getAccessories() {
		return mAccessoryDataArr;
	}

	public boolean validAccessoryData(ItemData accessoryData) {
		return mMemoriaData.validAccessoryData(accessoryData);
	}

	public int getRemainAccessorySlot() {
		return MAX_ACCESSORIES - mAccessoryDataArr.length;
	}

	public void addAccessory(ItemData accessoryData) {
		if (!validAccessoryData(accessoryData)) {
			throw new RuntimeException("メモリア[" + getName() + "]にアクセサリ["
					+ accessoryData.getName() + "]を設定できません。");
		}
		if (mAccessoryDataArr.length >= MAX_ACCESSORIES) {
			throw new RuntimeException("メモリア[" + getName() + "]にアクセサリ["
					+ accessoryData.getName() + "]を設定できません。");
		}
		mAccessoryDataArr = ArrayUtils.add(mAccessoryDataArr, accessoryData);
	}

	public void removeAccessory(int index) {
		mAccessoryDataArr = ArrayUtils.remove(mAccessoryDataArr, index);
	}

	public void clearAccessories() {
		mAccessoryDataArr = new ItemData[] {};
	}

	public void addLeaderSkill(ItemData leaderSkill) {
		mLeaderSkillArr = ArrayUtils.add(mLeaderSkillArr, leaderSkill);
	}

	public void clearLeaderSkill() {
		mLeaderSkillArr = new ItemData[] {};
	}

	private MemoriaData getMemoriaData() {
		return mMemoriaData;
	}

	private ItemData[] getAllItemData() {
		List<ItemData> itemDataList = new ArrayList<ItemData>();
		if (mWeaponData != null) {
			itemDataList.add(mWeaponData);
		}
		for (ItemData e : mAccessoryDataArr) {
			itemDataList.add(e);
		}
		for (ItemData e : mLeaderSkillArr) {
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

	/**
	 * 補正後の力を取得します。
	 *
	 * @return 力
	 */
	protected int getPower() {
		int value = 0;
		value += getMemoriaData().getPower();
		for (ItemData e : getAllItemData()) {
			value += e.getPower(getMemoriaData());
		}
		return value;
	}

	/**
	 * 補正後の素早さを取得します。
	 *
	 * @return 素早さ
	 */
	protected int getSpeed() {
		int value = 0;
		value += getMemoriaData().getSpeed();
		for (ItemData e : getAllItemData()) {
			value += e.getSpeed(getMemoriaData());
		}
		return value;
	}

	/**
	 * 補正後の知性を取得します。
	 *
	 * @return 知性
	 */
	protected int getIntelligence() {
		int value = 0;
		value += getMemoriaData().getIntelligence();
		for (ItemData e : getAllItemData()) {
			value += e.getIntelligence(getMemoriaData());
		}
		return value;
	}

	/**
	 * 物理防御特性を取得します。
	 *
	 * @return 物理防御特性
	 */
	public int getPhysicalDefence() {
		int value = 0;
		for (ItemData e : getAllItemData()) {
			value += e.getPhysicalDefence();
		}
		return value;
	}

	/**
	 * 物理と魔法での与ダメージを取得します。
	 *
	 * @return 与ダメージ
	 */
	public int getAttackDamage(int turn, int charge) {
		return getAttackDamage(turn, charge, new ArrayList<Attr>(),
				new ArrayList<Attr>(), 0);
	}

	/**
	 * 物理と魔法での与ダメージを取得します。
	 *
	 * @return 与ダメージ
	 */
	public int getAttackDamage(int turn, int charge, List<Attr> weakList,
			List<Attr> resistanceList) {
		return getAttackDamage(turn, charge, weakList, resistanceList, 0);
	}

	/**
	 * 物理と魔法での与ダメージを取得します。
	 *
	 * @param turn
	 *            ターン数
	 * @param charge
	 *            1ターンあたりのチャージ数
	 * @param weakList
	 *            敵の弱点
	 * @param resistanceList
	 *            敵の耐性
	 * @param physicalResistance
	 *            敵の物理防御（デフォルト0）
	 * @return 与ダメージ
	 */
	public int getAttackDamage(int turn, int charge, List<Attr> weakList,
			List<Attr> resistanceList, int physicalResistance) {
		int damage = 0;
		int tmp = 0;

		// 全て物理攻撃の場合
		int physical = getPhysicalAttackDamage(weakList, resistanceList,
				physicalResistance);
		tmp = physical * turn;
		damage = Math.max(damage, tmp);

		// 黒魔法攻撃を含める場合
		for (ItemData e : mAccessoryDataArr) {
			BlackMagicItemDataEx ex = e.getBlackMagicEx();
			if (ex != null) {
				int black = getAttackDamageForBlackMagic(e, weakList,
						resistanceList);
				int magicTimes = (int) (charge / ex.getMagicCharge());
				int physicalTimes = turn - magicTimes;
				tmp = black * magicTimes + physical * physicalTimes;
				damage = Math.max(damage, tmp);
			}
		}

		// 召喚魔法攻撃を含める場合
		for (ItemData e : mAccessoryDataArr) {
			SummonMagicItemDataEx ex = e.getSummonMagicEx();
			if (ex != null) {
				int summon = getAttackDamageForSummonMagic(e, weakList,
						resistanceList);
				int magicTimes = (int) (charge / ex.getMagicCharge());
				int physicalTimes = turn - magicTimes;
				tmp = summon * magicTimes + physical * physicalTimes;
				damage = Math.max(damage, tmp);
			}
		}

		// ジョブスキル
		// フレア
		// ブレイクゲージ200%以上の時に知恵メメントで攻撃すると初回のみフレアを発動
		// TODO ひとまず、ターン数やブレイクに関わらず、1回発動することとします。
		if (getMemoriaData().getJobSkill().isFurea()) {
			damage += getMemoriaData().getJobSkill().getFureaAttackDamage(
					getIntelligence(),
					getMemoriaData().getMagicAttack(MagicType.BLACK));
		}

		// プレミアムスキル
		if (getMemoriaData().getPremiumSkill().isExist()) {
			damage += getMemoriaData().getPremiumSkill().getAttackDamage(turn);
		}

		return damage;
	}

	/**
	 * 物理与ダメージを取得します。
	 *
	 * @param weakList
	 *            敵の弱点
	 * @param resistanceList
	 *            敵の耐性
	 * @param physicalResistance
	 *            敵の物理防御
	 * @return 物理与ダメージ
	 */
	protected int getPhysicalAttackDamage(List<Attr> weakList,
			List<Attr> resistanceList, int physicalResistance) {
		float damage = 0f;

		// クリティカル攻撃発動率＝幸運÷500
		// クリティカル発動時は、物理与ダメージ2倍。
		float criticalRate = Math
				.min(getMemoriaData().getLuck() / 500.0f, 1.0f);

		// 物理与ダメージ＝(力×攻撃力補正－物理防御力)×倍率
		// http://wikiwiki.jp/pictlogicaff/?%C0%EF%C6%AE%BE%F0%CA%F3#measure
		// ※力メメントで物理倍率 1.20を前提とします。
		damage = (getPower() * getMemoriaData().getPhysicalAttack()
				* (1.0f + criticalRate) - physicalResistance)
				* MEMENT_CHIKARA_PHYSICAL_RATE;
		if (damage < 1f) {
			damage = 1f;
		}

		// ジョブスキル
		// 居合い抜き
		// 自身が一番初めの攻撃を行うと与ダメージ50%アップの居合い抜きを発動
		if (getMemoriaData().getJobSkill().isIainuki()) {
			damage *= 1.5f;
		}
		// 乱れ撃ち
		// 自身が一番初めの攻撃を行うと2～4回の連続攻撃を発動。1回あたりの与ダメージは通常の60%。
		// ※平均3回攻撃と考えます。
		if (getMemoriaData().getJobSkill().isMidareuchi()) {
			damage *= 3 * 0.60f;
		}

		// 敵の弱点・属性
		damage *= getAttrRateForPhysical(weakList, resistanceList);

		return (int) damage;
	}

	protected int getAttackDamageForBlackMagic(ItemData blackMagic,
			List<Attr> weakList, List<Attr> resistanceList) {
		if (blackMagic == null) {
			return 0;
		}
		return (int) (blackMagic.getBlackMagicEx().getAttackDamage(
				getIntelligence(),
				getMemoriaData().getMagicAttack(MagicType.BLACK)) * getAttrRateForBlackMagic(
				blackMagic, weakList, resistanceList));
	}

	protected int getAttackDamageForSummonMagic(ItemData summonMagic,
			List<Attr> weakList, List<Attr> resistanceList) {
		if (summonMagic == null) {
			return 0;
		}
		return (int) (summonMagic.getSummonMagicEx().getAttackDamage(
				getMemoriaData().getMagicAttack(MagicType.SUMMON)) * getAttrRateForSummonMagic(
				summonMagic, weakList, resistanceList));
	}

	/**
	 * 物理攻撃における弱点/耐性倍率を取得します。
	 *
	 * @param weakList
	 * @param resistanceList
	 * @return
	 */
	protected float getAttrRateForPhysical(List<Attr> weakList,
			List<Attr> resistanceList) {
		return getWeakAttrRateForPhysical(weakList)
				* getResistanceAttrRateForPhysical(resistanceList);
	}

	/**
	 * 物理攻撃における弱点倍率を取得します。
	 *
	 * @param weakList
	 * @return
	 */
	protected float getWeakAttrRateForPhysical(List<Attr> weakList) {
		float weak = 1.0f;

		// 武器の敵弱点係数を取得します。
		float weaponValue = 0f;
		if (mWeaponData != null) {
			weaponValue = mWeaponData.getWeaponEx().getWeakAttrValue(weakList);
		}

		// 武器属性が有効な場合にブースト係数を取得します。
		if (weaponValue > 0f) {
			float boostValue = 0f;
			for (ItemData e : getAllItemData()) {
				boostValue += e.getEx().getBoostValueForPhysical(weakList);
			}
			// 倍率を計算します。
			weak += weaponValue + boostValue;
		}

		return weak;
	}

	/**
	 * 物理攻撃における敵の耐性倍率を取得します。
	 *
	 * @param resistanceList
	 * @return
	 */
	protected float getResistanceAttrRateForPhysical(List<Attr> resistanceList) {
		float resistance = 1.0f;
		if (mWeaponData != null) {
			resistance = mWeaponData.getWeaponEx().getResistanceAttrRate(
					resistanceList);
		}
		return resistance;
	}

	/**
	 * 黒魔法攻撃における弱点/耐性倍率を取得します。
	 *
	 * @param weakList
	 * @param resistanceList
	 * @return
	 */
	protected float getAttrRateForBlackMagic(ItemData blackMagic,
			List<Attr> weakList, List<Attr> resistanceList) {
		return getWeakAttrRateForBlackMagic(blackMagic, weakList)
				* getResistanceAttrRateForBlackMagic(blackMagic, resistanceList);
	}

	/**
	 * 黒魔法攻撃における弱点倍率を取得します。
	 *
	 * @param weakList
	 * @return
	 */
	protected float getWeakAttrRateForBlackMagic(ItemData blackMagic,
			List<Attr> weakList) {
		float weak = 1.0f;

		// 黒魔法の敵弱点係数を取得します。
		float baseValue = 0f;
		baseValue = blackMagic.getBlackMagicEx().getWeakAttrValue(weakList);

		// 黒魔法の属性が有効な場合にブースト係数を取得します。
		if (baseValue > 0f) {
			float boostValue = 0f;
			for (ItemData e : getAllItemData()) {
				boostValue += e.getEx().getBoostValueForBlackMagic(weakList);
			}
			// 倍率を計算します。
			weak += baseValue + boostValue;
		}

		return weak;
	}

	/**
	 * 黒魔法攻撃における敵の耐性倍率を取得します。
	 *
	 * @param resistanceList
	 * @return
	 */
	protected float getResistanceAttrRateForBlackMagic(ItemData blackMagic,
			List<Attr> resistanceList) {
		return blackMagic.getBlackMagicEx().getResistanceAttrRate(
				resistanceList);
	}

	/**
	 * 召喚魔法攻撃における弱点/耐性倍率を取得します。
	 *
	 * @param weakList
	 * @param resistanceList
	 * @return
	 */
	protected float getAttrRateForSummonMagic(ItemData summonMagic,
			List<Attr> weakList, List<Attr> resistanceList) {
		return getWeakAttrRateForSummonMagic(summonMagic, weakList)
				* getResistanceAttrRateForSummonMagic(summonMagic,
						resistanceList);
	}

	/**
	 * 召喚魔法攻撃における弱点倍率を取得します。
	 *
	 * @param weakList
	 * @return
	 */
	protected float getWeakAttrRateForSummonMagic(ItemData summonMagic,
			List<Attr> weakList) {
		float weak = 1.0f;
		// 召喚魔法の敵弱点係数を取得します。
		weak += summonMagic.getSummonMagicEx().getWeakAttrValue(weakList);
		return weak;
	}

	/**
	 * 召喚魔法攻撃における敵の耐性倍率を取得します。
	 *
	 * @param resistanceList
	 * @return
	 */
	protected float getResistanceAttrRateForSummonMagic(ItemData summonMagic,
			List<Attr> resistanceList) {
		return summonMagic.getSummonMagicEx().getResistanceAttrRate(
				resistanceList);
	}

	/**
	 * 物理防御ダメージを取得します。
	 *
	 * @param turn
	 *            ターン数
	 * @param enemyPower
	 *            敵の力
	 * @return 防御ダメージ
	 */
	public int getPhysicalDefenceDamage(int turn, int enemyPower) {
		// 回避率はおよそ「素早さ÷500」
		float kaihiRate = Math.min(getSpeed() / 500f, 1f);
		float kaihiTurn = kaihiRate * turn;
		float kaihiDamage = enemyPower * kaihiTurn;

		// 物理被ダメージ＝(力－物理防御力)×倍率
		// 力＝モンスターの力
		// 物理防御力＝メモリアの物理防御力＋装備アイテムの物理防御力＋魔法効果
		// 倍率＝メメント効果×ジョブスキル効果
		// →防御できる物理ダメージは、物理防御力（物理防御特性）の値と考える。
		float defenceTurn = turn - kaihiTurn;
		float defenceDamage = getPhysicalDefence() * defenceTurn;

		return (int) (kaihiDamage + defenceDamage);
	}

	/**
	 * 魔法防御ダメージを取得します。
	 *
	 * @param turn
	 *            ターン数
	 * @return 防御ダメージ
	 */
	public int getMagicDefenceDamage(int turn) {
		// 魔法被ダメージ＝(効果値×メメント効果－魔法防御力×倍率)×魔法効果
		int value = 0;
		for (ItemData e : getAllItemData()) {
			value += e.getMagicDefence();
		}
		return value * turn;
	}

	/**
	 * 回復量を取得します。
	 *
	 * @param turn
	 *            ターン数
	 * @param charge
	 *            チャージ量
	 * @return 回復量
	 */
	public int getRecovery(int turn, int charge) {

		// 祈りメメントの回復量
		// http://wikiwiki.jp/pictlogicaff/?%C0%EF%C6%AE%BE%F0%CA%F3#memento
		int inori = (int) ((getHp() / 50.0 + getIntelligence() / 2.0)) * turn;

		// 回復魔法
		int white = 0;
		for (ItemData e : mAccessoryDataArr) {
			WhiteMagicItemDataEx ex = e.getWhiteMagicEx();
			if (ex != null) {
				int recovery = ex.getRecovery(getIntelligence(),
						getMemoriaData().getMagicAttack(MagicType.WHITE));
				int magicTimes = (int) (charge / ex.getMagicCharge());
				int tmp = recovery * magicTimes;
				white = Math.max(white, tmp);
			}
		}

		return inori + white;
	}

	/**
	 * 祈りメメント回復量を取得します。
	 *
	 * @return 回復量
	 */
	protected int getInoriRecovery() {
		// 祈りメメントの回復量
		// http://wikiwiki.jp/pictlogicaff/?%C0%EF%C6%AE%BE%F0%CA%F3#memento
		return getHp() / 50 + getIntelligence() / 2;
	}

	/**
	 * リメントゲージ100%（ブレイク）までのターン数を取得します。
	 *
	 * @param chikaraTurn
	 *            力メメントのターン数
	 * @param chieTurn
	 *            知恵メメントのターン数
	 * @return ターン数
	 */
	protected float getRementTurn(int chikaraTurn, int chieTurn) {
		// 1ターンあたりのリメントゲージ上昇率（%）
		float rementRate = (MEMENT_CHIKARA_REMENT_RATE * chikaraTurn + MEMENT_CHIE_REMENT_RATE
				* chieTurn)
				/ (chikaraTurn + chieTurn);
		// 100%までのターン数
		return (float) Math.ceil(100.0f / rementRate);
	}

	/**
	 * ブレイク時間（秒）を取得します。
	 *
	 * @return 時間（秒）
	 */
	protected float geBreakTime() {
		// ブレイク時間(秒)＝35秒＋素早さ×0.025秒
		// http://wikiwiki.jp/pictlogicaff/?%C0%EF%C6%AE%BE%F0%CA%F3#status
		return 35 + getMemoriaData().getSpeed() * 0.025f;
	}

}
