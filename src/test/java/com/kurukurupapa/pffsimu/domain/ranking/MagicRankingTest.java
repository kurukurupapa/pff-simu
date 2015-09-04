package com.kurukurupapa.pffsimu.domain.ranking;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.ranking.MagicRanking;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class MagicRankingTest extends BaseTestCase {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private MagicRanking sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		sut = new MagicRanking();
	}

	@Test
	public void testCalc() {
		// 準備
		sut.setParams(mMemoriaDataSet, mItemDataSet);

		// テスト実行
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals(readExpectedFile(), actualStr);
	}

	private String toString(List<ItemFitness> magicFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (ItemFitness e : magicFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
