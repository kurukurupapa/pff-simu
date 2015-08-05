package com.kurukurupapa.pff.dp01;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.test.BaseTestCase;

public class MemoriaRankingTest extends BaseTestCase {
	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private MemoriaRanking sut;

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
		sut = new MemoriaRanking();
	}

	@Test
	public void testCalc_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator);

		// テスト実行
		sut.run();
		List<MemoriaFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals(readExpectedFile(), actualStr);
	}

	@Test
	public void testCalc_Battle_パーティあり() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();

		Party party = new Party();
		Memoria memoria = new Memoria(mMemoriaDataSet.find("元帥シド"));
		memoria.setWeapon(mItemDataSet.find("おろち"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		party.add(memoria);
		memoria = new Memoria(mMemoriaDataSet.find("パンネロ"));
		memoria.setWeapon(mItemDataSet.find("ダンシングダガー"));
		memoria.addAccessory(mItemDataSet.find("クリスタルの小手"));
		memoria.addAccessory(mItemDataSet.find("ファイアRF+3"));
		party.add(memoria);

		sut.setParams(mMemoriaDataSet, mItemDataSet, fitness, party);

		// テスト実行
		sut.run();
		List<MemoriaFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertThat(actual.size(), is(mMemoriaDataSet.size() - 2));
		assertTrue(actualStr.indexOf("元帥シド") < 0);
		assertTrue(actualStr.indexOf("マーシャルネイ") < 0);
		assertTrue(actualStr.indexOf("パンネロ") < 0);
		assertTrue(actualStr.indexOf("クリスタルの小手") < 0);
		assertTrue(actualStr.indexOf("ファイアRF+3") < 0);
	}

	private String toString(List<MemoriaFitness> memoriaFitnesses) {
		return StringUtils.join(memoriaFitnesses, "\n") + "\n";
	}

}
