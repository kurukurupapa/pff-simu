package com.kurukurupapa.pff.dp01;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.test.BaseTestCase;

/**
 * ユーザデータでメモリアのランキングを確認します。
 */
public class MemoriaRankingTest2 extends BaseTestCase {
	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private MemoriaRanking sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// ユーザデータ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readUserFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readUserFile();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		sut = new MemoriaRanking();
	}

	@Test
	public void testCalc() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator);

		// テスト実行
		sut.run();
		List<MemoriaFitness> actual = sut.getFitnesses();

		// 検証
		assertMemoriaFitness(readExpectedFile(), actual);
	}

}
