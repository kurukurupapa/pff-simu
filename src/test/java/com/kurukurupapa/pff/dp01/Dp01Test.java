package com.kurukurupapa.pff.dp01;

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
		Party actual = dp.getParty();
		// 検証
		// ひとまず簡単な検証のみ
		assertTrue(actual.getFitness() > 0);
		assertEquals(1, actual.getMemoriaList().size());
		assertEquals(null, actual.getMemoria(0).getWeapon());
		assertEquals(0, actual.getMemoria(0).getNumAccessories());
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
				+ "1174,アーロン+赤兎馬のたてがみ(レア5)+SPの腕輪+居合い抜き+アーロンLS\n" //
				+ "960,元帥シド+赤兎馬のたてがみ(レア5)+バルキーコート+算術\n" //
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
		Party actual = dp.getParty();
		// 検証
		assertTrue(actual.getFitness() > 0);
		assertEquals(2, actual.getMemoriaList().size());
		assertEquals(0, actual.getMemoria(0).getNumAccessories());
		assertEquals(0, actual.getMemoria(1).getNumAccessories());
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
		Party actual = dp.getParty();
		// 検証
		assertTrue(actual.getFitness() > 0);
		assertEquals(4, actual.getMemoriaList().size());
		assertEquals(0, actual.getMemoria(0).getNumAccessories());
		assertEquals(0, actual.getMemoria(1).getNumAccessories());
		assertEquals(0, actual.getMemoria(2).getNumAccessories());
		assertEquals(0, actual.getMemoria(3).getNumAccessories());
	}

	@Test
	public void testRun1_AttackItem0() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();

		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(),
				fitnessCalculator);
		dp.run(1);
		Party actual = dp.getParty();

		// 検証
		// ひとまず簡単な検証のみ
		assertTrue(actual.getFitness() > 0);
		assertEquals(1, actual.getMemoriaList().size());
		assertEquals(null, actual.getMemoria(0).getWeapon());
		assertEquals(0, actual.getMemoria(0).getNumAccessories());
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
		assertEquals("" //
				+ "9441,パンネロ+ダンシングダガー+パワーリスト+ファイアRF+3+フレア+パンネロLS\n" //
				+ "7855,ライトニング(No.119)+青紅の剣(レア5)+赤兎馬のたてがみ(レア5)+パワーリスト+奥義\n" //
				+ "7658,トレイ+黄忠の長弓(レア5)+赤兎馬のたてがみ(レア5)+パワーリスト+乱れ撃ち\n" //
		, actual);
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
		Party actual = dp.getParty();
		// 検証
		// ひとまず簡単な検証のみ
		assertTrue(actual.getFitness() > 0);
		assertEquals(1, actual.getMemoriaList().size());
		assertEquals(null, actual.getMemoria(0).getWeapon());
		assertEquals(0, actual.getMemoria(0).getNumAccessories());
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
				+ "3158,ユウナ(No.48)+燃える戦杖+ケアルラ+ケアル+オーラ\n" //
				+ "2359,ヴァニラ+イノセントロッド+ケアルラ+ケアル+フレア\n" //
				+ "1680,パンネロ+ケアルラ+ケアル+フレア+パンネロLS\n" //
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
		Party actual = dp.getParty();

		// 検証
		// ひとまず簡単な検証のみ
		assertTrue(actual.getFitness() > 0);
		assertEquals(1, actual.getMemoriaList().size());
		assertEquals(null, actual.getMemoria(0).getWeapon());
		assertEquals(0, actual.getMemoria(0).getNumAccessories());
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
				+ "14340,ライトニング(No.119)+閃光のウォーソード+パワーリスト+雷の指輪+奥義\n" //
				+ "11660,パンネロ+ダンシングダガー+ファイアRF+3+ケアル+フレア+パンネロLS\n" //
				+ "10476,マキナ+閃光のウォーソード+パワーリスト+雷の指輪\n" //
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
				+ "9813,パンネロ+ダンシングダガー+エアロラ+ケアル+フレア+パンネロLS\n" //
				+ "8628,ライトニング(No.119)+閃光のウォーソード+赤兎馬のたてがみ(レア5)+パワーリスト+奥義\n" //
				+ "6728,元帥シド+おろち+マーシャルネイ+マーシャルネイ+算術+元帥シドLS\n" //
		, actual);
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
		assertPartyAsMultiLine(readExpectedFile(), actual);
	}

	@Test
	public void testRun4_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		Dp01 dp;
		dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		dp.run(4);
		Party actual = dp.getParty();

		// 検証
		assertPartyAsMultiLine(readExpectedFile(), actual);
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
		assertEquals(2, dp.getParty().getMemoria(0).getNumAccessories());
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
