package com.kurukurupapa.pff.dp01;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.test.BaseTestCase;

public class Dp01Test extends BaseTestCase {
	private ItemDataSet mItemDataSet;
	private MemoriaDataSet mMemoriaDataSet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Test
	public void testRun1_HpItem0() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();

		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(),
				fitnessCalculator);
		dp.run(1);
		// 検証
		assertThat(dp.getParty().toString(), is("911,アーロン"));
	}

	@Test
	public void testRun1_Hp() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
			dp.run(1);
			actual.append(dp.getParty().toString() + "\n");
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertEquals("" //
				+ "1174,アーロン+赤兎馬のたてがみ(レア5)+SPの腕輪+アーロンLS\n" //
				+ "960,元帥シド+赤兎馬のたてがみ(レア5)+バルキーコート\n" //
				+ "889,マキナ+青紅の剣(レア5)+赤兎馬のたてがみ(レア5)+バルキーコート\n" //
		, actual.toString());
	}

	@Test
	public void testRun2_HpItem0() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();

		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(),
				fitnessCalculator);
		dp.run(2);
		// 検証
		assertEquals("" //
				+ "1691,アーロン,元帥シド" //
		, dp.getParty().toString());
	}

	@Test
	public void testRun4_HpItem0() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();

		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(),
				fitnessCalculator);
		dp.run(4);
		// 検証
		assertEquals("" //
				+ "2925,アーロン,元帥シド,マキナ,ライトニング(No.119)" //
		, dp.getParty().toString());
	}

	@Test
	public void testRun1_AttackItem0() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();
		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, new ItemDataSet(), fitnessCalculator);
			dp.run(1);
			if (i > 0) {
				actual += ",";
			}
			actual += dp.getParty();
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}
		// 検証
		assertThat(actual, is("" //
				// + "2670,トレイ,2320,アーロン,1870,元帥シド"
				// 2015/06/13
				+ "4920,パンネロ,3720,ライトニング(No.119),3050,トレイ"
		//
				));
	}

	/**
	 * 総合的な攻撃力の高いメモリアをみつけるテストです。
	 */
	@Test
	public void testRun1_Attack() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
			dp.run(1);
			actual += dp.getParty() + "\n";
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertThat(actual, is("" //
				+ "7650,トレイ+黄忠の長弓(レア5)+赤兎馬のたてがみ(レア5)+パワーリスト\n"
				+ "7220,パンネロ+ダンシングダガー+赤兎馬のたてがみ(レア5)+パワーリスト\n"
				+ "6450,ライトニング(No.119)+青紅の剣(レア5)+赤兎馬のたてがみ(レア5)+パワーリスト\n"
		// + "6390,トレイ+黄忠の長弓(レア5)+赤兎馬のたてがみ(レア5)+宿炎の指輪\n"
		// + "6090,トレイ+黄忠の長弓(レア5)+宿炎の指輪+リストバンド\n"
		// + "5400,トレイ+ディバインアロー+宿炎の指輪+リストバンド\n"
		// + "5400,アーロン+五月雨+赤兎馬のたてがみ(レア5)+宿炎の指輪\n"
		// + "5400,アーロン+烈風+赤兎馬のたてがみ(レア5)+宿炎の指輪\n"
		// + "5140,アーロン+烈風+宿炎の指輪+リストバンド\n"
		// + "4620,元帥シド+五月雨+マーシャルネイ+マーシャルネイ\n"
		// + "4620,元帥シド+烈風+マーシャルネイ+マーシャルネイ\n"
		//
				));
	}

	@Test
	public void testRun1_RecoveryItem0() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForRecovery();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(),
				fitnessCalculator);
		dp.run(1);
		// 検証
		assertThat(dp.getParty().toString(), is("" //
				// +"720,元帥シド"
				// 2015/06/13
				+ "820,パンネロ"
		//
				));
	}

	@Test
	public void testRun1_Recovery() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForRecovery();

		// テスト実行
		StringBuilder sb = new StringBuilder();
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
			dp.run(1);
			sb.append(dp.getParty().toString() + "\n");
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertEquals("" //
				+ "2861,ユウナ(No.48)+燃える戦杖+ケアルラ+ケアル\n"
				+ "2180,ヴァニラ+イノセントロッド+ケアルラ+ケアル\n"
				+ "1462,アーシェ+ネクロフォリア+ルフェインローブ+ケアルラ\n"
		//
				, sb.toString());
	}

	@Test
	public void testRun1_BattleItem0() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(),
				fitnessCalculator);
		dp.run(1);
		// 検証
		assertEquals("" //
				+ "6300,パンネロ" //
		, dp.getParty().toString());
	}

	@Test
	public void testRun1_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 8; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
			dp.run(1);
			actual += dp.getParty() + "\n";
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	public void testRun1_Battle_ThunderWeak() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		fitnessCalculator.addEnemyWeak(Attr.THUNDER);

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
			dp.run(1);
			actual += dp.getParty() + "\n";
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertEquals("" //
				+ "12930,ライトニング(No.119)+閃光のウォーソード+パワーリスト+雷の指輪\n" //
				+ "10471,マキナ+閃光のウォーソード+パワーリスト+雷の指輪\n" //
				+ "9378,ティナ+閃光のウォーソード+パワーリスト+雷の指輪\n" //
		, actual);
	}

	@Test
	public void testRun1_Battle_ThunderWeakPhysicalResistance() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		fitnessCalculator.addEnemyWeak(Attr.THUNDER);
		fitnessCalculator.setEnemyPhysicalResistance(200);

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
			dp.run(1);
			actual += dp.getParty() + "\n";
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertEquals("" //
				+ "7220,ライトニング(No.119)+閃光のウォーソード+赤兎馬のたてがみ(レア5)+パワーリスト\n"
				+ "7216,パンネロ+ダンシングダガー+ファイアRF+3+ケアル\n"
				+ "6713,元帥シド+おろち+マーシャルネイ+マーシャルネイ+元帥シドLS\n", actual);
	}

	@Test
	public void testRun2_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		Dp01 dp;
		dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		dp.run(2);
		Party actual = dp.getParty();

		// 検証
		assertParty(readExpectedFile(), actual);
	}

	@Test
	public void testRun_武器装備() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(mItemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("元帥シド"));
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addWeaponData(mItemDataSet.find("烈風"));
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitnessCalculator);
		dp.run(1);
		// 検証
		assertTrue(dp.getParty().toString().indexOf("元帥シド+烈風") >= 0);
	}

	@Test
	public void testRun_魔法装備() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForBattle();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(mItemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("アーシェ"));
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addMagicData(mItemDataSet.find("ファイラ"));

		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitnessCalculator);
		dp.run(1);
		String actual = dp.getParty().toString();

		// 検証
		assertTrue(actual, actual.indexOf("アーシェ+ファイラ") >= 0);
	}

	@Test
	public void testRun_アクセサリ装備() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(mItemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("元帥シド"));
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addAccessoryData(mItemDataSet.find("リストバンド"));
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitnessCalculator);
		dp.run(1);
		// 検証
		assertTrue(dp.getParty().toString().indexOf("元帥シド+リストバンド") >= 0);
	}

	@Test
	public void testRun_アクセサリ個数上限チェック() {
		// 準備
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addAccessoryData(mItemDataSet.find("タフネスリング"));
		itemDataSet.addAccessoryData(mItemDataSet.find("リストバンド"));
		itemDataSet.addAccessoryData(mItemDataSet.find("EXコア100%"));
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("アーロン"));
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitnessCalculator);
		dp.run();
		// 検証
		assertThat(dp.getParty().toString(),
				is("1174,アーロン+EXコア100%+EXコア100%+アーロンLS"));
	}

	@Test
	public void testRun_アクセサリ2重使用チェック() {
		// 準備
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addAccessoryData(mItemDataSet.find("タフネスリング"));
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("元帥シド"));
		memoriaDataSet.add(mMemoriaDataSet.find("アーロン"));
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitnessCalculator);
		dp.run(2);
		// 検証
		// アイテムが一方のメモリアのみに使用されること。
		assertTrue(dp.getParty().toString().indexOf("アーロン+タフネスリング") >= 0);
		assertTrue(dp.getParty().toString().indexOf("元帥シド+タフネスリング") < 0);
	}

}
