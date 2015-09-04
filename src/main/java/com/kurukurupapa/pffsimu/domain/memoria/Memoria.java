package com.kurukurupapa.pffsimu.domain.memoria;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.Mement;
import com.kurukurupapa.pffsimu.domain.item.BlackMagicItemDataEx;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.MagicType;
import com.kurukurupapa.pffsimu.domain.item.SummonMagicItemDataEx;
import com.kurukurupapa.pffsimu.domain.item.WhiteMagicItemDataEx;

public class Memoria implements Cloneable {
	/** 武器スロット数 */
	public static final int MAX_WEAPON = 1;
	/** アクセサリスロット数 */
	public static final int MAX_ACCESSORIES = 2;

	protected MemoriaData mMemoriaData;
	protected ItemData mWeaponData;
	protected ItemData[] mAccessoryDataArr;
	protected LeaderSkill mLeaderSkill;
	protected boolean mJobSkillFlag;

	public Memoria(MemoriaData memoriaData) {
		mMemoriaData = memoriaData;
		mAccessoryDataArr = new ItemData[] {};
		// ジョブスキルフラグは、既存処理との互換性のため、デフォルト有効とする。
		if (memoriaData.getJobSkill() != null) {
			mJobSkillFlag = true;
		}
	}

	public Memoria(MemoriaData memoriaData, ItemData weapon,
			ItemData magicAccessory1, ItemData magicAccessory2) {
		this(memoriaData);
		if (weapon != null) {
			setWeapon(weapon);
		}
		if (magicAccessory1 != null) {
			addAccessory(magicAccessory1);
		}
		if (magicAccessory2 != null) {
			addAccessory(magicAccessory2);
		}
	}

	public Memoria(Memoria memoria) {
		mMemoriaData = memoria.mMemoriaData;
		mWeaponData = memoria.mWeaponData;
		mAccessoryDataArr = memoria.mAccessoryDataArr.clone();
		mLeaderSkill = memoria.mLeaderSkill;
		mJobSkillFlag = memoria.mJobSkillFlag;
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
		if (mJobSkillFlag) {
			sb.append("+" + getJobSkill());
		}
		if (mLeaderSkill != null) {
			sb.append("+" + mLeaderSkill.getName());
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Memoria)) {
			return false;
		}
		Memoria other = (Memoria) obj;
		// メモリアデータの確認
		if (!getName().equals(other.getName())) {
			return false;
		}
		// 武器の確認
		if (!ItemData.equals(mWeaponData, other.mWeaponData)) {
			return false;
		}
		// アクセサリの確認
		if (!ItemData.equals(mAccessoryDataArr, other.mAccessoryDataArr)) {
			return false;
		}
		// リーダースキルの確認
		if (!LeaderSkill.equals(mLeaderSkill, other.mLeaderSkill)) {
			return false;
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
		Validate.notNull(weaponData);
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

	public int getNumAccessories() {
		return mAccessoryDataArr.length;
	}

	public int getRemainAccessorySlot() {
		return MAX_ACCESSORIES - mAccessoryDataArr.length;
	}

	public void addAccessory(ItemData accessoryData) {
		Validate.notNull(accessoryData);
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

	public LeaderSkill getLeaderSkill() {
		return mLeaderSkill;
	}

	public void setLeaderSkill(LeaderSkill leaderSkill) {
		mLeaderSkill = leaderSkill;
	}

	public void clearLeaderSkill() {
		mLeaderSkill = null;
	}

	public MemoriaData getMemoriaData() {
		return mMemoriaData;
	}

	public JobSkill getJobSkill() {
		if (mJobSkillFlag) {
			return mMemoriaData.getJobSkill();
		}
		return null;
	}

	public void setJobSkillFlag(boolean flag) {
		mJobSkillFlag = flag;
	}

	public void enableJobSkill() {
		setJobSkillFlag(true);
	}

	public void disableJobSkill() {
		setJobSkillFlag(false);
	}

	private ItemData[] getAllItemData() {
		List<ItemData> itemDataList = new ArrayList<ItemData>();
		if (mWeaponData != null) {
			itemDataList.add(mWeaponData);
		}
		for (ItemData e : mAccessoryDataArr) {
			itemDataList.add(e);
		}
		if (mLeaderSkill != null) {
			itemDataList.add(mLeaderSkill.getItemData());
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
	public int getPower() {
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
				new ArrayList<Attr>(), 0, 0);
	}

	/**
	 * 物理と魔法での与ダメージを取得します。
	 *
	 * @return 与ダメージ
	 */
	public int getAttackDamage(int turn, int charge, List<Attr> weakList,
			List<Attr> resistanceList) {
		return getAttackDamage(turn, charge, weakList, resistanceList, 0, 0);
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
			List<Attr> resistanceList, int physicalResistance,
			int magicResistance) {
		float damage = 0f;
		float tmp = 0f;

		// 全て物理攻撃の場合
		float physical = getPhysicalAttackDamage(weakList, resistanceList,
				physicalResistance);
		tmp = physical * turn;
		damage = Math.max(damage, tmp);

		// 黒魔法攻撃を含める場合
		for (ItemData e : mAccessoryDataArr) {
			BlackMagicItemDataEx ex = e.getBlackMagicEx();
			if (ex != null) {
				float black = getAttackDamageForBlackMagic(e, weakList,
						resistanceList, magicResistance);
				float magicTimes = (float) charge / ex.getMagicCharge();
				float physicalTimes = turn - magicTimes;
				tmp = black * magicTimes + physical * physicalTimes;
				damage = Math.max(damage, tmp);
			}
		}

		// 召喚魔法攻撃を含める場合
		for (ItemData e : mAccessoryDataArr) {
			SummonMagicItemDataEx ex = e.getSummonMagicEx();
			if (ex != null) {
				float summon = getAttackDamageForSummonMagic(e, weakList,
						resistanceList);
				float magicTimes = (float) charge / ex.getMagicCharge();
				float physicalTimes = turn - magicTimes;
				tmp = summon * magicTimes + physical * physicalTimes;
				damage = Math.max(damage, tmp);
			}
		}

		// ジョブスキル
		// フレア
		// ブレイクゲージ200%以上の時に知恵メメントで攻撃すると初回のみフレアを発動
		// TODO ひとまず、ターン数やブレイクに関わらず、1回発動することとします。
		// TODO 1ターンとして数えます。
		if (mJobSkillFlag && getMemoriaData().getJobSkill().isFurea()) {
			damage += getMemoriaData().getJobSkill().getFureaAttackDamage(
					getIntelligence(),
					getMemoriaData().getMagicAttack(MagicType.BLACK));
			// turn--;
		}
		// ホーリー
		// ブレイクゲージ200%以上の時に祈りメメントで攻撃すると初回のみホーリーを発動
		// TODO ひとまず、ターン数やブレイクに関わらず、1回発動することとします。
		// TODO 1ターンとして数えます。
		if (mJobSkillFlag && getJobSkill().isHoly()) {
			float holy = getJobSkill().getHolyAttackDamage(getIntelligence(),
					getMemoriaData().getMagicAttack(MagicType.WHITE));

			// リーダースキル 白魔法効果
			// ※回復魔法に効果あり
			if (mLeaderSkill != null) {
				holy = mLeaderSkill.calcWhiteMagic(holy);
			}

			damage += holy;
			// turn--;
		}

		// プレミアムスキル
		if (getMemoriaData().getPremiumSkill().isExist()) {
			damage += getMemoriaData().getPremiumSkill().getAttackDamage(turn);
		}

		return (int) damage;
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
	protected float getPhysicalAttackDamage(List<Attr> weakList,
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
				* Mement.CHIKARA_PHYSICAL_RATE;
		if (damage < 1f) {
			damage = 1f;
		}

		// ジョブスキル
		if (mJobSkillFlag) {
			damage = getJobSkill().calcPhysicalAttackDamage(damage);
		}

		// 敵の弱点・属性
		damage *= getAttrRateForPhysical(weakList, resistanceList);

		return damage;
	}

	protected float getAttackDamageForBlackMagic(ItemData blackMagic,
			List<Attr> weakList, List<Attr> resistanceList, int magicResistance) {
		if (blackMagic == null) {
			return 0;
		}
		return blackMagic.getBlackMagicEx().getAttackDamage(getIntelligence(),
				getMemoriaData().getMagicAttack(MagicType.BLACK),
				magicResistance)
				* getAttrRateForBlackMagic(blackMagic, weakList, resistanceList);
	}

	protected float getAttackDamageForSummonMagic(ItemData summonMagic,
			List<Attr> weakList, List<Attr> resistanceList) {
		if (summonMagic == null) {
			return 0;
		}
		return summonMagic.getSummonMagicEx().getAttackDamage(
				getMemoriaData().getMagicAttack(MagicType.SUMMON))
				* getAttrRateForSummonMagic(summonMagic, weakList,
						resistanceList);
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
		float inori = (getHp() / 50.0f + getIntelligence() / 2.0f) * turn;

		// 回復魔法
		float white = 0;
		for (ItemData e : mAccessoryDataArr) {
			WhiteMagicItemDataEx ex = e.getWhiteMagicEx();
			if (ex != null) {
				float recovery = ex.getRecovery(getIntelligence(),
						getMemoriaData().getMagicAttack(MagicType.WHITE));

				// ジョブスキル
				if (mJobSkillFlag
						&& getMemoriaData().getJobSkill().isRecoveryMagic()) {
					recovery = getMemoriaData().getJobSkill()
							.calcRecoveryMagicDamage(recovery);
				}

				float magicTimes = (float) charge / ex.getMagicCharge();
				float tmp = recovery * magicTimes;
				white = Math.max(white, tmp);
			}
		}

		// リーダースキル 白魔法効果
		// ※回復魔法に効果あり
		if (mLeaderSkill != null) {
			white = mLeaderSkill.calcWhiteMagic(white);
		}

		// 合計
		float recovery = inori + white;

		// リーダースキル 回復効果
		// ※祈りメメントと回復魔法の両方に効果あり
		if (mLeaderSkill != null) {
			recovery = mLeaderSkill.calcRecovery(recovery);
		}

		return (int) recovery;
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
		float rementRate = (Mement.CHIKARA_REMENT_RATE * chikaraTurn + Mement.CHIE_REMENT_RATE
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
