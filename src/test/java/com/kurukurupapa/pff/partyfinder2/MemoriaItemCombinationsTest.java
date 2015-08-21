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
		// setup後の要素数は、最大で、計算回数と一致する。
		// 同一メモリア/アイテムが複数個存在する場合は、要素数が少なくなる。
		assertTrue(sut.mCalcCount * 0.9 > sut.size());
	}

}
