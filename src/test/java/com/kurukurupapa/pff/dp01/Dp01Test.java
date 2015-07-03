package com.kurukurupapa.pff.dp01;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class Dp01Test {
	/** ロガー */
	private static Logger mLogger;

	private ItemDataSet mItemDataSet;
	private MemoriaDataSet mMemoriaDataSet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mLogger = Logger.getLogger(Dp01Test.class);
	}

	@Before
	public void setUp() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Test
	public void testRun1_HpItem0() {
		// 準備
		Fitness fitness = new FitnessForHp();

		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(), fitness);
		dp.run(1);
		mLogger.debug(dp.getParty());
		// 検証
		assertThat(dp.getParty().toString(), is("911,アーロン"));
	}

	@Test
	public void testRun1_Hp() {
		// 準備
		Fitness fitness = new FitnessForHp();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
			dp.run(1);
			actual.append(dp.getParty().toString() + "\n");
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		// assertThat(dp.getParty().toString(),
		// is("1052,アーロン+EXコア100%+タフネスリング"));
		// assertThat(dp2.getParty().toString(),
		// is("886,元帥シド+EXコア100%+タフネスリング"));
		// assertThat(dp3.getParty().toString(),
		// is("824,マキナ+EXコア100%+タフネスリング"));
		assertThat(actual.toString(), is("" //
				+ "1174,アーロン+赤兎馬のたてがみ(レア5)+SPの腕輪+アーロンLS\n"
				// + "1165,アーロン+SPの腕輪+タフネスリング+アーロンLS\n"
				+ "960,元帥シド+赤兎馬のたてがみ(レア5)+バルキーコート\n"
				// + "925,元帥シド+赤兎馬のたてがみ(レア4)+バルキーコート\n"
				// + "916,元帥シド+バルキーコート+タフネスリング\n"
				+ "889,マキナ+青紅の剣(レア5)+赤兎馬のたてがみ(レア5)+バルキーコート\n"
				// + "874,マキナ+青紅の剣(レア3)+赤兎馬のたてがみ(レア4)+バルキーコート\n"
				// + "854,マキナ+バルキーコート+タフネスリング\n")
				+ ""));
	}

	@Test
	public void testRun2_HpItem0() {
		// 準備
		Fitness fitness = new FitnessForHp();

		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(), fitness);
		dp.run(2);
		mLogger.debug(dp.getParty());
		// 検証
		assertThat(dp.getParty().toString(), is("1691,アーロン,元帥シド"));
	}

	@Test
	public void testRun4_HpItem0() {
		// 準備
		Fitness fitness = new FitnessForHp();

		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(), fitness);
		dp.run(4);
		mLogger.debug(dp.getParty());
		// 検証
		assertThat(dp.getParty().toString(),
				is("2925,マキナ,ライトニング(No.119),アーロン,元帥シド"));
	}

	@Test
	public void testRun1_AttackItem0() {
		// 準備
		Fitness fitness = new FitnessForAttack();
		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, new ItemDataSet(), fitness);
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
		Fitness fitness = new FitnessForAttack();

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
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
		Fitness fitness = new FitnessForRecovery();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(), fitness);
		dp.run(1);
		mLogger.debug(dp.getParty());
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
		Fitness fitness = new FitnessForRecovery();

		// テスト実行
		StringBuilder sb = new StringBuilder();
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
			dp.run(1);
			sb.append(dp.getParty().toString() + "\n");
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertThat(sb.toString(), is("" //
		// + "2128,ヴァニラ+光の棒+ルフェインローブ+ケアルラ\n"
		// + "1452,アーシェ+オブリージュ+ルフェインローブ+ケアルラ\n"
		// + "1404,ティナ+ルフェインローブ+ケアルラ\n"
		// 2015/06/15
				+ "2180,ヴァニラ+イノセントロッド+ケアルラ+ケアル\n"
				+ "1973,ユウナ(No.48)+燃える戦杖+ケアルラ+ケアル\n"
				+ "1462,アーシェ+ネクロフォリア+ルフェインローブ+ケアルラ\n"
		//
				));
	}

	@Test
	public void testRun1_BattleItem0() {
		// 準備
		Fitness fitness = new FitnessForBattle();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, new ItemDataSet(), fitness);
		dp.run(1);
		mLogger.debug(dp.getParty());
		// 検証
		assertEquals("" //
				+ "6300,パンネロ" //
		, dp.getParty().toString());
	}

	@Test
	public void testRun1_Battle() {
		// 準備
		Fitness fitness = new FitnessForBattle();

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 8; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
			dp.run(1);
			actual += dp.getParty() + "\n";
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertEquals("" //
				+ "9136,パンネロ+ダンシングダガー+ファイアRF+3+ケアル\n" //
				+ "8574,トレイ+黄忠の長弓(レア5)+赤兎馬のたてがみ(レア5)+パワーリスト\n" //
				+ "8403,ライトニング(No.119)+青紅の剣(レア5)+赤兎馬のたてがみ(レア5)+クリスタルの小手\n" //
				+ "8123,元帥シド+おろち+マーシャルネイ+マーシャルネイ\n" //
				+ "7317,アーロン+おろち+赤兎馬のたてがみ(レア5)+エクサバックラー+2+アーロンLS\n" //
				+ "5940,アーシェ+ネクロフォリア+エクサバックラー+2+ケアル+アーシェLS\n" //
				+ "5855,マキナ+青紅の剣(レア5)+クリスタルの小手+エクサバックラー+2\n" //
				+ "5804,ティナ+青紅の剣(レア5)+エクサバックラー+2+ディアボロス\n" //
		, actual);
	}

	@Test
	public void testRun1_Battle_ThunderWeak() {
		// 準備
		FitnessForBattle fitness = new FitnessForBattle();
		fitness.addEnemyWeak(Attr.THUNDER);

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
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
		FitnessForBattle fitness = new FitnessForBattle();
		fitness.addEnemyWeak(Attr.THUNDER);
		fitness.setEnemyPhysicalResistance(200);

		// テスト実行
		String actual = "";
		Dp01 dp;
		for (int i = 0; i < 3; i++) {
			dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
			dp.run(1);
			actual += dp.getParty() + "\n";
			mMemoriaDataSet.remove(dp.getParty().getMemoria(0).getName());
		}

		// 検証
		assertEquals("" //
				+ "7220,ライトニング(No.119)+閃光のウォーソード+赤兎馬のたてがみ(レア5)+パワーリスト\n"
				+ "7216,パンネロ+ダンシングダガー+ファイアRF+3+ケアル\n"
				+ "5723,元帥シド+おろち+マーシャルネイ+マーシャルネイ\n", actual);
	}

	@Test
	public void testRun_武器装備() {
		// 準備
		Fitness fitness = new FitnessForAttack();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(mItemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("元帥シド"));
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addWeaponData(mItemDataSet.find("烈風"));
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitness);
		dp.run(1);
		mLogger.debug(dp.getParty());
		// 検証
		assertThat(dp.getParty().toString(), is("3480,元帥シド+烈風"));
	}

	@Test
	public void testRun_魔法装備() {
		// 準備
		Fitness fitness = new FitnessForAttack();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(mItemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("元帥シド"));
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addMagicData(mItemDataSet.find("ファイラ"));
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitness);
		dp.run(1);
		mLogger.debug(dp.getParty());
		// 検証
		assertThat(dp.getParty().toString(), is("1992,元帥シド+ファイラ"));
	}

	@Test
	public void testRun_アクセサリ装備() {
		// 準備
		Fitness fitness = new FitnessForAttack();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(mItemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("元帥シド"));
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addAccessoryData(mItemDataSet.find("リストバンド"));
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitness);
		dp.run(1);
		mLogger.debug(dp.getParty());
		// 検証
		assertThat(dp.getParty().toString(), is("2230,元帥シド+リストバンド"));
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
		Fitness fitness = new FitnessForHp();
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
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
		Fitness fitness = new FitnessForHp();
		// テスト実行
		Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitness);
		dp.run(2);
		mLogger.debug(dp.getParty());
		// 検証
		assertThat(dp.getParty().toString(), is("1782,アーロン+タフネスリング,元帥シド"));
	}

}
