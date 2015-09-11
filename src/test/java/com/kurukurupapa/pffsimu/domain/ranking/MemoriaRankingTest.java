package com.kurukurupapa.pffsimu.domain.ranking;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.ranking.impl1.MemoriaRanking1;
import com.kurukurupapa.pffsimu.domain.ranking.impl2.MemoriaRanking2;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class MemoriaRankingTest extends BaseTestCase {
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
	public void testRun1_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		MemoriaRanking1 sut1 = new MemoriaRanking1();
		sut1.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		sut1.run();
		List<MemoriaFitness> actual1 = sut1.getFitnesses();

		// テスト実行
		MemoriaRanking2 sut2 = new MemoriaRanking2();
		sut2.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		sut2.run();
		List<MemoriaFitness> actual2 = sut2.getFitnesses();

		// 検証
		assertMemoriaFitnesses(actual1, actual2);
	}

	/**
	 * 結果判定
	 *
	 * @param arg1
	 *            古いアルゴリズムでの結果
	 * @param arg2
	 *            新しいアルゴリズムでの結果
	 */
	private void assertMemoriaFitnesses(List<MemoriaFitness> arg1,
			List<MemoriaFitness> arg2) {
		assertEquals(arg1.size(), arg2.size());

		for (int i = 0; i < arg1.size(); i++) {
			// 新しいアルゴリズムの結果が、古いほうよりも悪くなっていたら、何かしらの問題がある。
			if (arg1.get(i).getValue() > arg2.get(i).getValue()) {
				assertEquals(toString(arg1), toString(arg2));
			}
		}
	}

	private String toString(List<MemoriaFitness> memoriaFitnesses) {
		return StringUtils.join(memoriaFitnesses, "\n") + "\n";
	}
}
