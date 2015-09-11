package com.kurukurupapa.pffsimu.domain.ranking.impl1;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.test.BaseTestCase;
import com.kurukurupapa.pffsimu.test.SlowTests;

/**
 * ユーザデータでメモリアのランキングを確認します。
 */
@Category(SlowTests.class)
public class MemoriaRanking1UserDataTest extends BaseTestCase {
	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private MemoriaRanking1 sut;

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
		sut = new MemoriaRanking1();
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
