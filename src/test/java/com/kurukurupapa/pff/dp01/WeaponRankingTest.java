package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.test.BaseTestCase;

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
		String actualStr = toString(actual);

		// 検証
		assertEquals(readExpectedFile(), actualStr);
	}

	private String toString(List<ItemFitness> weaponFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (ItemFitness e : weaponFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
