package com.kurukurupapa.pffsimu.domain.ranking;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.ranking.WeaponRanking;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class WeaponRankingTest extends BaseTestCase {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private WeaponRanking sut;

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
		sut = new WeaponRanking();
	}

	@Test
	public void testCalc_デフォルト設定() {
		// 準備

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet);
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals(readExpectedFile(), actualStr);
	}

	@Test
	public void testCalc_適応度計算オブジェクトあり() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		fitnessCalculator.addEnemyWeak(Attr.THUNDER);

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals(readExpectedFile(), actualStr);
	}

	@Test
	public void testCalc_パーティあり() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		Party party = new Party();
		Memoria memoria = new Memoria(mMemoriaDataSet.find("元帥シド"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		party.add(memoria);
		memoria = new Memoria(mMemoriaDataSet.find("パンネロ"));
		memoria.setWeapon(mItemDataSet.find("ダンシングダガー"));
		memoria.addAccessory(mItemDataSet.find("ファイアRF+3"));
		party.add(memoria);

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator, party,
				0);
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();

		// 検証
		assertEquals(3, actual.size());
		assertEquals("烈風", actual.get(0).getItem().getName());
		assertEquals("五月雨", actual.get(1).getItem().getName());
		assertEquals("おろち", actual.get(2).getItem().getName());
		for (ItemFitness e : actual) {
			assertEquals("元帥シド", e.getMemoria().getName());
			assertEquals("マーシャルネイ",
					e.getMemoria().getAccessories()[0].toString());
			assertEquals("マーシャルネイ",
					e.getMemoria().getAccessories()[1].toString());
		}
	}

	private String toString(List<ItemFitness> weaponFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (ItemFitness e : weaponFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
