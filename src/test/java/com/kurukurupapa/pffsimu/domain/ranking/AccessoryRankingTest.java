package com.kurukurupapa.pffsimu.domain.ranking;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.ranking.AccessoryRanking;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class AccessoryRankingTest extends BaseTestCase {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private AccessoryRanking sut;

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
		sut = new AccessoryRanking();
	}

	@Test
	public void testCalc_パーティなし() {
		// 準備

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet);
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actualStr);
	}

	@Test
	public void testCalc_パーティあり() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		Party party = new Party();
		Memoria memoria = new Memoria(mMemoriaDataSet.find("元帥シド"));
		memoria.setWeapon(mItemDataSet.find("おろち"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		party.add(memoria);
		memoria = new Memoria(mMemoriaDataSet.find("パンネロ"));
		memoria.setWeapon(mItemDataSet.find("ダンシングダガー"));
		memoria.addAccessory(mItemDataSet.find("ファイアRF+3"));
		party.add(memoria);

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator, party,
				1);
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertThat(actual.size(),
				is(mItemDataSet.makeAccessoryList().size() - 1));
		assertTrue(actualStr.indexOf("マーシャルネイ") < 0);
	}

	private String toString(List<ItemFitness> accessoryFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (ItemFitness e : accessoryFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
