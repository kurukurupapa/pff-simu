package com.kurukurupapa.pffsimu.domain.partyfinder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl1.Dp01;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl2.PartyFinder2d;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class Compare extends BaseTestCase {
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
		MemoriaDataSet memoriaDataSet;

		// テスト実行
		memoriaDataSet = new MemoriaDataSet(mMemoriaDataSet);
		List<Party> result1 = new ArrayList<Party>();
		for (int i = 0; i < 8; i++) {
			Dp01 sut = new Dp01(memoriaDataSet, mItemDataSet, fitnessCalculator);
			sut.run(1);
			result1.add(sut.getParty());
			memoriaDataSet.remove(sut.getParty().getMemoria(0).getName());
		}

		// テスト実行
		memoriaDataSet = new MemoriaDataSet(mMemoriaDataSet);
		List<Party> result2 = new ArrayList<Party>();
		for (int i = 0; i < 8; i++) {
			PartyFinder2d sut = new PartyFinder2d(memoriaDataSet, mItemDataSet,
					fitnessCalculator);
			sut.run(1);
			result2.add(sut.getParty());
			memoriaDataSet.remove(sut.getParty().getMemoria(0).getName());
		}

		// 検証
		assertParties(result1, result2);
	}

	@Test
	public void testRun2_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		Dp01 sut1 = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		sut1.run(2);
		Party result1 = sut1.getParty();

		// テスト実行
		PartyFinder2d sut2 = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
				fitnessCalculator);
		sut2.run(2);
		Party result2 = sut2.getParty();

		// 検証
		assertParty(result1, result2);
	}

	@Test
	public void testRun4_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		Dp01 sut1 = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		sut1.run(4);
		Party result1 = sut1.getParty();

		// テスト実行
		PartyFinder2d sut2 = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
				fitnessCalculator);
		sut2.run(4);
		Party result2 = sut2.getParty();

		// 検証
		assertParty(result1, result2);
	}

	private void assertParties(List<Party> arg1, List<Party> arg2) {
		String str1 = StringUtils.join(arg1, "\n");
		String str2 = StringUtils.join(arg2, "\n");
		assertEquals(str1, str2);
	}

	private void assertParty(Party arg1, Party arg2) {
		if (arg1.getFitness() != arg2.getFitness()) {
			assertEquals(sortMultiLine(arg1.toMultiLineString()),
					sortMultiLine(arg2.toMultiLineString()));
		}
	}
}
