package com.kurukurupapa.pff.partyfinder2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.FitnessCalculatorFactory;
import com.kurukurupapa.pff.test.BaseTestCase;
import com.kurukurupapa.pff.test.SlowTests;

public class PartyFinder2dTest extends BaseTestCase {

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
		mItemDataSet.readTestFile(true);
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Test
	public void testRun1_Hp() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty().toString() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	public void testRun1_Attack() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	public void testRun1_Recovery() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForRecovery();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty().toString() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	public void testRun1_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2d pf;
		for (int i = 0; i < 8; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
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
		StringBuilder actual = new StringBuilder();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	@Category(SlowTests.class)
	public void testRun2_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		PartyFinder2d pf;
		pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		pf.run(2);

		// 検証
		String expected = readExpectedFile();
		assertParty(expected, pf.getParty());
	}

	@Test
	@Category(SlowTests.class)
	public void testRun4_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		PartyFinder2d pf;
		pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		pf.run(4);

		// 検証
		String expected = readExpectedFile();
		assertParty(expected, pf.getParty());
	}

	@Test
	public void testRun4_Battle_不具合対応1() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.addWeaponData(mItemDataSet.find("ダンシングダガー"));
		itemDataSet.addWeaponData(mItemDataSet.find("おろち"));
		itemDataSet.addWeaponData(mItemDataSet.find("黄忠の長弓(レア5)"));
		itemDataSet.addWeaponData(mItemDataSet.find("青紅の剣(レア5)"));
		itemDataSet.addMagicData(mItemDataSet.find("ファイアRF+3"));
		itemDataSet.addAccessoryData(mItemDataSet.find("エクサイヤリング+1"));
		itemDataSet.addAccessoryData(mItemDataSet.find("マーシャルネイ"));
		itemDataSet.addAccessoryData(mItemDataSet.find("マーシャルネイ"));
		itemDataSet.addAccessoryData(mItemDataSet.find("赤兎馬のたてがみ(レア5)"));
		itemDataSet.addAccessoryData(mItemDataSet.find("パワーリスト"));
		itemDataSet.addAccessoryData(mItemDataSet.find("クリスタルの小手"));
		itemDataSet.addAccessoryData(mItemDataSet.find("エクサバックラー+2"));
		itemDataSet.addAccessoryData(mItemDataSet.find("ケアル"));
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("パンネロ"));
		memoriaDataSet.add(mMemoriaDataSet.find("元帥シド"));
		memoriaDataSet.add(mMemoriaDataSet.find("トレイ"));
		memoriaDataSet.add(mMemoriaDataSet.find("ライトニング(No.119)"));

		// テスト実行
		PartyFinder2d pf;
		pf = new PartyFinder2d(memoriaDataSet, itemDataSet, fitnessCalculator);
		pf.run(4);

		// 検証
		assertEquals(
				"37319,トレイ+黄忠の長弓(レア5)+赤兎馬のたてがみ(レア5)+パワーリスト+元帥シドLS,パンネロ+ダンシングダガー+ファイアRF+3+エクサイヤリング+1+元帥シドLS,元帥シド+おろち+マーシャルネイ+マーシャルネイ+元帥シドLS,ライトニング(No.119)+青紅の剣(レア5)+クリスタルの小手+エクサバックラー+2+元帥シドLS",
				pf.getParty().toString());
	}

}
