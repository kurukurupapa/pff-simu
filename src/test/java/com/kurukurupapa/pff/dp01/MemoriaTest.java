package com.kurukurupapa.pff.dp01;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.JobSkill;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.domain.PremiumSkill;
import com.kurukurupapa.pff.domain.WeaponType;
import com.kurukurupapa.pff.domain.WeaponTypeFactory;
import com.kurukurupapa.pff.test.BaseTestCase;

public class MemoriaTest extends BaseTestCase {
	/** 1バトルあたりのターン数 */
	private static final int TURN = 10;
	/** 1ターンあたりのチャージ */
	private static final int CHARGE_PER_TURN = 5 * 5 / 2;
	/** 1バトルあたりのチャージ */
	private static final int CHARGE_PER_BATTLE = CHARGE_PER_TURN * TURN;
	/** 力メメントの物理倍率 */
	public static final float MEMENT_CHIKARA_PHYSICAL_RATE = 1.2f;
	/** 知恵メメントの黒魔法倍率 */
	public static final float MEMENT_CHIE_BLACK_RATE = 1.2f;
	/** 祈りメメントの白魔法倍率 */
	public static final float MEMENT_INORI_WHITE_RATE = 1.25f;

	/** ティナの名前 */
	private static final String TINA_NAME = "ティナ";
	/** ティナのHP */
	private static final int TINA_HP = 402;
	/** ティナの力 */
	private static int TINA_POWER = 79;
	/** ティナの素早さ */
	private static final int TINA_SPEED = 16;
	/** ティナの知性 */
	private static int TINA_INTELLIGENCE = 125;
	/** ティナの運 */
	private static final int TINA_LUCK = 18;
	/** ティナの物理攻撃特性 */
	private static float TINA_PHYSICAL_ATTACK = 1.0f;
	/** ティナの白魔法攻撃特性 */
	private static final float TINA_WHITE_MAGIC_ATTACK = 1.0f;
	/** ティナの黒魔法攻撃特性 */
	private static final float TINA_BLACK_MAGIC_ATTACK = 2.0f;
	/** ティナの召喚攻撃特性 */
	private static final float TINA_SUMMON_MAGIC_ATTACK = 4.0f;
	/** ティナの武器種別 */
	private static final WeaponType TINA_WEAPON_TYPE = new WeaponType("剣");

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;
	private static Memoria mYariThunderMemoria;
	private static Memoria mYumiWindMemoria;
	private static Memoria mYumiWindBoost1Memoria;
	private static Memoria mYumiWindBoost2Memoria;
	private static Memoria mBlackMagicFireMemoria;
	private static Memoria mBlackMagicFireBoost1Memoria;
	private static Memoria mBlackMagicFireBoost2Memoria;
	private static Memoria mSummonIceMemoria;
	private static Memoria mSummonIceBoost1Memoria;
	private static Memoria mSummonIceBoost2Memoria;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readMasterFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readMasterFile();

		ItemData yariThunderWeapon = mItemDataSet.find("サンダーランス");
		mYariThunderMemoria = new Memoria(mMemoriaDataSet.find("カイン"));
		mYariThunderMemoria.setWeapon(yariThunderWeapon);

		ItemData yumiWind = mItemDataSet.find("疾風の弓矢");
		ItemData ringWind = mItemDataSet.find("風の指輪");
		mYumiWindMemoria = new Memoria(mMemoriaDataSet.find("トレイ"));
		mYumiWindMemoria.setWeapon(yumiWind);
		mYumiWindBoost1Memoria = mYumiWindMemoria.clone();
		mYumiWindBoost1Memoria.addAccessory(ringWind);
		mYumiWindBoost2Memoria = mYumiWindBoost1Memoria.clone();
		mYumiWindBoost2Memoria.addAccessory(ringWind);

		ItemData rodFire = mItemDataSet.find("炎のロッド");
		ItemData magicFire = mItemDataSet.find("ファイラ");
		ItemData ringFire = mItemDataSet.find("炎の指輪");
		mBlackMagicFireMemoria = new Memoria(mMemoriaDataSet.find("ヴァニラ"));
		mBlackMagicFireMemoria.addAccessory(magicFire);
		mBlackMagicFireBoost1Memoria = mBlackMagicFireMemoria.clone();
		mBlackMagicFireBoost1Memoria.addAccessory(ringFire);
		mBlackMagicFireBoost2Memoria = mBlackMagicFireBoost1Memoria.clone();
		mBlackMagicFireBoost2Memoria.setWeapon(rodFire);

		ItemData rodIce = mItemDataSet.find("氷のロッド");
		ItemData summonIce = mItemDataSet.find("シヴァ");
		ItemData ringIce = mItemDataSet.find("氷結の指輪");
		mSummonIceMemoria = new Memoria(mMemoriaDataSet.find("ヴァニラ"));
		mSummonIceMemoria.addAccessory(summonIce);
		mSummonIceBoost1Memoria = mSummonIceMemoria.clone();
		mSummonIceBoost1Memoria.addAccessory(ringIce);
		mSummonIceBoost2Memoria = mSummonIceBoost1Memoria.clone();
		mSummonIceBoost2Memoria.setWeapon(rodIce);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testGetPower_アイテム0() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find("アーロン"));

		// テスト実行
		int actual = sut.getPower();

		// 検証
		assertThat(actual, is(129));
	}

	@Test
	public void testGetPower_アイテム1() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find("アーロン"));
		sut.addAccessory(mItemDataSet.find("リストバンド"));

		// テスト実行
		int actual = sut.getPower();

		// 検証
		assertThat(actual, is((int) Math.floor(129 * 1.13f)));
	}

	@Test
	public void testGetPhysicalAttackDamage_幸運0() {
		// 準備
		int power = 100;
		int luck = 0;
		float physicalAttack = 1.0f;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, power, 1, 1,
				luck, physicalAttack, 1.0f, 1.0f, 1.0f,
				WeaponTypeFactory.create("剣"), new JobSkill(""),
				new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = (int) sut.getPhysicalAttackDamage(new ArrayList<Attr>(),
				new ArrayList<Attr>(), 0);

		// 検証
		assertThat(
				actual,
				is((int) (power * physicalAttack * MEMENT_CHIKARA_PHYSICAL_RATE)));
	}

	@Test
	public void testGetPhysicalAttackDamage_幸運250() {
		// 準備
		int power = 100;
		int luck = 250;
		float physicalAttack = 1.0f;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, power, 1, 1,
				luck, physicalAttack, 1.0f, 1.0f, 1.0f,
				WeaponTypeFactory.create("剣"), new JobSkill(""),
				new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = (int) sut.getPhysicalAttackDamage(new ArrayList<Attr>(),
				new ArrayList<Attr>(), 0);

		// 検証
		assertThat(actual, is((int) (power * physicalAttack
				* (1f + luck / 500f) * MEMENT_CHIKARA_PHYSICAL_RATE)));
	}

	@Test
	public void testGetPhysicalAttackDamage_幸運500() {
		// 準備
		int power = 100;
		int luck = 500;
		float physicalAttack = 1.0f;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, power, 1, 1,
				luck, physicalAttack, 1.0f, 1.0f, 1.0f,
				WeaponTypeFactory.create("剣"), new JobSkill(""),
				new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = (int) sut.getPhysicalAttackDamage(new ArrayList<Attr>(),
				new ArrayList<Attr>(), 0);

		// 検証
		assertThat(
				actual,
				is((int) (power * physicalAttack * 2 * MEMENT_CHIKARA_PHYSICAL_RATE)));
	}

	@Test
	public void testGetPhysicalAttackDamage_幸運1000() {
		// 準備
		int power = 100;
		int luck = 1000;
		float physicalAttack = 1.0f;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, power, 1, 1,
				luck, physicalAttack, 1.0f, 1.0f, 1.0f,
				WeaponTypeFactory.create("剣"), new JobSkill(""),
				new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = (int) sut.getPhysicalAttackDamage(new ArrayList<Attr>(),
				new ArrayList<Attr>(), 0);

		// 検証
		assertThat(
				actual,
				is((int) (power * physicalAttack * 2 * MEMENT_CHIKARA_PHYSICAL_RATE)));
	}

	@Test
	public void testGetAttackDamageForBlackMagic_アイテム0() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));

		// テスト実行
		int actual = (int) sut.getAttackDamageForBlackMagic(null,
				new ArrayList<Attr>(), new ArrayList<Attr>(), 0);

		// 検証
		assertThat(actual, is(0));
	}

	@Test
	public void testGetAttackDamageForBlackMagic_黒魔法1() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));
		sut.addAccessory(mItemDataSet.find("ファイラ"));

		// テスト実行
		int actual = (int) sut.getAttackDamageForBlackMagic(
				sut.mAccessoryDataArr[0], new ArrayList<Attr>(),
				new ArrayList<Attr>(), 0);

		// 検証
		assertThat(actual, is((int) ((TINA_INTELLIGENCE + 50)
				* TINA_BLACK_MAGIC_ATTACK * 1.2)));
	}

	@Test
	public void testGetAttackDamageForSummonMagic_アイテム0() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));

		// テスト実行
		int actual = (int) sut.getAttackDamageForSummonMagic(null,
				new ArrayList<Attr>(), new ArrayList<Attr>());

		// 検証
		assertThat(actual, is(0));
	}

	@Test
	public void testGetAttackDamageForSummonMagic_召喚魔法1() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));
		sut.addAccessory(mItemDataSet.find("シヴァ"));

		// テスト実行
		int actual = (int) sut.getAttackDamageForSummonMagic(
				sut.mAccessoryDataArr[0], new ArrayList<Attr>(),
				new ArrayList<Attr>());

		// 検証
		assertThat(actual, is((int) (80 * TINA_SUMMON_MAGIC_ATTACK)));
	}

	@Test
	public void testGetAttackDamage_アイテム0() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));

		// テスト実行
		int actual = sut.getAttackDamage(TURN, CHARGE_PER_BATTLE);

		// 検証
		assertThat(actual,
				is((int) (TINA_POWER * TINA_PHYSICAL_ATTACK
						* (1f + TINA_LUCK / 500f)
						* MEMENT_CHIKARA_PHYSICAL_RATE * TURN)));
	}

	@Test
	public void testGetAttackDamage_黒魔法1() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));
		sut.addAccessory(mItemDataSet.find("ファイラ"));

		// テスト実行
		int actual = sut.getAttackDamage(TURN, CHARGE_PER_BATTLE);

		// 検証
		final int MAGIC_CHARGE = 49;
		final float MAGIC_TIMES = (float) CHARGE_PER_BATTLE / MAGIC_CHARGE;
		assertThat(
				actual,
				is((int) (TINA_POWER * (1f + TINA_LUCK / 500f)
						* MEMENT_CHIKARA_PHYSICAL_RATE * (TURN - MAGIC_TIMES) + (TINA_INTELLIGENCE + 50f)
						* TINA_BLACK_MAGIC_ATTACK
						* MEMENT_CHIE_BLACK_RATE
						* MAGIC_TIMES)));
	}

	@Test
	public void testGetAttackDamage_召喚魔法1() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));
		sut.addAccessory(mItemDataSet.find("シヴァ"));

		// テスト実行
		int actual = sut.getAttackDamage(TURN, CHARGE_PER_BATTLE);

		// 検証
		final int MAGIC_CHARGE = 64;
		final float MAGIC_TIMES = (float) CHARGE_PER_BATTLE / MAGIC_CHARGE;
		assertThat(actual, is((int) (TINA_POWER * (1f + TINA_LUCK / 500f)
				* MEMENT_CHIKARA_PHYSICAL_RATE * (TURN - MAGIC_TIMES) + 80f
				* TINA_SUMMON_MAGIC_ATTACK * MAGIC_TIMES)));
	}

	@Test
	public void testGetAttackDamage_黒魔法召喚魔法() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find(TINA_NAME));
		sut.addAccessory(mItemDataSet.find("ファイラ"));
		sut.addAccessory(mItemDataSet.find("シヴァ"));

		// テスト実行
		int actual = sut.getAttackDamage(TURN, CHARGE_PER_BATTLE);

		// 検証
		final int MAGIC_CHARGE = 49;
		final float MAGIC_TIMES = (float) CHARGE_PER_BATTLE / MAGIC_CHARGE;
		assertThat(
				actual,
				is((int) (TINA_POWER * (1f + TINA_LUCK / 500f)
						* MEMENT_CHIKARA_PHYSICAL_RATE * (TURN - MAGIC_TIMES) + (TINA_INTELLIGENCE + 50)
						* TINA_BLACK_MAGIC_ATTACK
						* MEMENT_CHIE_BLACK_RATE
						* MAGIC_TIMES)));
	}

	@Test
	public void testGetAttrRateForPhysical_弱点なし耐性なし() {
		// 準備
		Memoria sut = mYariThunderMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(1.0f));
	}

	@Test
	public void testGetAttrRateForPhysical_弱点あり() {
		// 準備
		Memoria sut = mYariThunderMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.THUNDER);
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(2.0f));
	}

	@Test
	public void testGetAttrRateForPhysical_弱点あり短距離飛行() {
		// 準備
		Memoria sut = mYariThunderMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.FLIGHT);
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(2.0f));
	}

	@Test
	public void testGetAttrRateForPhysical_弱点あり長距離飛行() {
		// 準備
		Memoria sut = mYumiWindMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.FLIGHT);
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(2.5f));
	}

	@Test
	public void testGetAttrRateForPhysical_弱点ありブースト1() {
		// 準備
		Memoria sut = mYumiWindBoost1Memoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.WIND);
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(2.5f));
	}

	@Test
	public void testGetAttrRateForPhysical_弱点ありブースト2() {
		// 準備
		Memoria sut = mYumiWindBoost2Memoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.WIND);
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(3.0f));
	}

	@Test
	public void testGetAttrRateForPhysical_弱点あり混在() {
		// 準備
		Memoria sut = mYumiWindBoost2Memoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.WIND);
		weakList.add(Attr.FLIGHT);
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(4.0f));
	}

	@Test
	public void testGetAttrRateForPhysical_耐性あり() {
		// 準備
		Memoria sut = mYariThunderMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		resistanceList.add(Attr.THUNDER);
		// テスト実行
		float actual = sut.getAttrRateForPhysical(weakList, resistanceList);
		// 検証
		assertThat(actual, is(0.8f));
	}

	@Test
	public void testGetAttrRateForBlackMagic_弱点なし耐性なし() {
		// 準備
		Memoria sut = mBlackMagicFireMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		// テスト実行
		float actual = sut.getAttrRateForBlackMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(1.0f));
	}

	@Test
	public void testGetAttrRateForBlackMagic_弱点あり() {
		// 準備
		Memoria sut = mBlackMagicFireMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.FIRE);
		// テスト実行
		float actual = sut.getAttrRateForBlackMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(2.0f));
	}

	@Test
	public void testGetAttrRateForBlackMagic_弱点ありブースト1() {
		// 準備
		Memoria sut = mBlackMagicFireBoost1Memoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.FIRE);
		// テスト実行
		float actual = sut.getAttrRateForBlackMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(2.5f));
	}

	@Test
	public void testGetAttrRateForBlackMagic_弱点ありブースト2() {
		// 準備
		Memoria sut = mBlackMagicFireBoost2Memoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.FIRE);
		// テスト実行
		float actual = sut.getAttrRateForBlackMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(3.0f));
	}

	@Test
	public void testGetAttrRateForBlackMagic_耐性あり() {
		// 準備
		Memoria sut = mBlackMagicFireMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		resistanceList.add(Attr.FIRE);
		// テスト実行
		float actual = sut.getAttrRateForBlackMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(0.8f));
	}

	@Test
	public void testGetAttrRateForSummonMagic_弱点なし耐性なし() {
		// 準備
		Memoria sut = mSummonIceMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		// テスト実行
		float actual = sut.getAttrRateForSummonMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(1.0f));
	}

	@Test
	public void testGetAttrRateForSummonMagic_弱点あり() {
		// 準備
		Memoria sut = mSummonIceMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.ICE);
		// テスト実行
		float actual = sut.getAttrRateForSummonMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(2.0f));
	}

	@Test
	public void testGetAttrRateForSummonMagic_弱点ありブースト1() {
		// 準備
		Memoria sut = mSummonIceBoost1Memoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.ICE);
		// テスト実行
		float actual = sut.getAttrRateForSummonMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		// ブーストが影響しないこと
		assertThat(actual, is(2.0f));
	}

	@Test
	public void testGetAttrRateForSummonMagic_弱点ありブースト2() {
		// 準備
		Memoria sut = mSummonIceBoost2Memoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		weakList.add(Attr.ICE);
		// テスト実行
		float actual = sut.getAttrRateForSummonMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		// ブーストが影響しないこと
		assertThat(actual, is(2.0f));
	}

	@Test
	public void testGetAttrRateForSummonMagic_耐性あり() {
		// 準備
		Memoria sut = mSummonIceMemoria;
		List<Attr> weakList = new ArrayList<Attr>();
		List<Attr> resistanceList = new ArrayList<Attr>();
		resistanceList.add(Attr.ICE);
		// テスト実行
		float actual = sut.getAttrRateForSummonMagic(sut.mAccessoryDataArr[0],
				weakList, resistanceList);
		// 検証
		assertThat(actual, is(0.8f));
	}

	@Test
	public void testGetPhysicalDefenceDamage_物理防御力0素早さ0() {
		// 準備
		int speed = 0;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, 1, speed, 1, 1,
				1f, 1f, 1f, 1f, WeaponTypeFactory.create("剣"),
				new JobSkill(""), new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = sut.getPhysicalDefenceDamage(TURN, 100);

		// 検証
		assertThat(actual, is(0));
	}

	@Test
	public void testGetPhysicalDefenceDamage_物理防御特性40() {
		// 準備
		int speed = 0;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, 1, speed, 1, 1,
				1f, 1f, 1f, 1f, WeaponTypeFactory.create("剣"),
				new JobSkill(""), new PremiumSkill(""), mItemDataSet));
		sut.addAccessory(mItemDataSet.find("エスカッション(レア4)"));

		// テスト実行
		int actual = sut.getPhysicalDefenceDamage(TURN, 100);

		// 検証
		assertThat(actual, is(40 * TURN));
	}

	@Test
	public void testGetPhysicalDefenceDamage_素早さ250() {
		// 準備
		int speed = 250;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, 1, speed, 1, 1,
				1f, 1f, 1f, 1f, WeaponTypeFactory.create("剣"),
				new JobSkill(""), new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = sut.getPhysicalDefenceDamage(TURN, 100);

		// 検証
		assertThat(actual, is((int) (100 * speed / 500f) * TURN));
	}

	@Test
	public void testGetPhysicalDefenceDamage_素早さ500() {
		// 準備
		int speed = 500;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, 1, speed, 1, 1,
				1f, 1f, 1f, 1f, WeaponTypeFactory.create("剣"),
				new JobSkill(""), new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = sut.getPhysicalDefenceDamage(TURN, 100);

		// 検証
		assertThat(actual, is(100 * TURN));
	}

	@Test
	public void testGetPhysicalDefenceDamage_素早さ1000() {
		// 準備
		int speed = 1000;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, 1, speed, 1, 1,
				1f, 1f, 1f, 1f, WeaponTypeFactory.create("剣"),
				new JobSkill(""), new PremiumSkill(""), mItemDataSet));

		// テスト実行
		int actual = sut.getPhysicalDefenceDamage(TURN, 100);

		// 検証
		assertThat(actual, is(100 * TURN));
	}

	@Test
	public void testGetPhysicalDefenceDamage_アクセサリ素早さアップ() {
		// 準備
		int speed = 0;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, 1, speed, 1, 1,
				1f, 1f, 1f, 1f, WeaponTypeFactory.create("剣"),
				new JobSkill(""), new PremiumSkill(""), mItemDataSet));
		sut.addAccessory(mItemDataSet.find("ルフェインブーツ"));

		// テスト実行
		int actual = sut.getPhysicalDefenceDamage(TURN, 100);

		// 検証
		assertThat(actual, is((int) (100 * 35 / 500f) * TURN));
	}

	@Test
	public void testGetPhysicalDefenceDamage_物理防御特性40素早さ250() {
		// 準備
		int speed = 250;
		Memoria sut = new Memoria(new MemoriaData("メモリア", 1000, 1, speed, 1, 1,
				1f, 1f, 1f, 1f, WeaponTypeFactory.create("剣"),
				new JobSkill(""), new PremiumSkill(""), mItemDataSet));
		sut.addAccessory(mItemDataSet.find("エスカッション(レア4)"));

		// テスト実行
		int actual = sut.getPhysicalDefenceDamage(TURN, 100);

		// 検証
		assertThat(actual, is((int) ( //
				(40 * TURN / 2f) // 200
				+ (100 * TURN / 2f) // 500
				)));
	}

	@Test
	public void testGetRecovery_アイテム0() {
		// 準備
		Memoria sut = new Memoria(mMemoriaDataSet.find("ティナ"));

		// テスト実行
		int actual = sut.getRecovery(TURN, CHARGE_PER_BATTLE);

		// 検証
		assertThat(actual, is(705));
	}

	@Test
	public void testGetRecovery_アイテム1() {
		// 準備
		ItemData item1 = mItemDataSet.find("ケアル");
		Memoria sut = new Memoria(mMemoriaDataSet.find("ティナ"), null, item1,
				null);

		// テスト実行
		int actual = sut.getRecovery(TURN, CHARGE_PER_BATTLE);

		// 検証
		assertThat(actual, is(1283));
	}

	@Test
	public void testGetRecovery_重複アイテム() {
		// 準備
		ItemData item1 = mItemDataSet.find("ケアル");
		ItemData item2 = mItemDataSet.find("ケアルラ");
		Memoria sut = new Memoria(mMemoriaDataSet.find("ティナ"), null, item1,
				item2);

		int expected = new Memoria(mMemoriaDataSet.find("ティナ"), null, item1,
				null).getRecovery(TURN, CHARGE_PER_BATTLE);

		// テスト実行
		int actual = sut.getRecovery(TURN, CHARGE_PER_BATTLE);

		// 検証
		assertThat(actual, is(expected));
	}

}
