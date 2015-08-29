package com.kurukurupapa.pff.partyfinder2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.test.BaseTestCase;

public class MemoriaItemCombinationsTest extends BaseTestCase {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile(true);
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testSetup() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		MemoriaItemCombinations sut = new MemoriaItemCombinations(
				mMemoriaDataSet, mItemDataSet, fitnessCalculator);

		// テスト実行
		sut.setup();

		// 検証
		// setup後の要素数は、計算回数以下である。
		// ※同一メモリア/アイテムが複数個存在する場合でも、それだけを理由にして、要素数は少なくすることはない。
		assertTrue(sut.mCalcCount >= sut.size());

		// 要素数の想定
		final int numMemorias = 12; // メモリア数
		final int numLeaderSkills = 5; // リーダースキル数（リーダースキルなしも1件と数える）
		final float numWeapons = 2.5f; // 1メモリアあたりの平均的な武器数
		final int numMagicAccessories = 42; // 魔法/アクセサリ数（専用アイテム除く）
		int maxSize = (int) (numMemorias * numLeaderSkills * numWeapons
				* numMagicAccessories * 7);
		assertTrue(sut.size() <= maxSize);

		// 内容確認
		assertEquals(readExpectedFile(), sut.toDebugStr());
	}

}
