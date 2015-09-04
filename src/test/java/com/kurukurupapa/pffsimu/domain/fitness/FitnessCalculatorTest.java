package com.kurukurupapa.pffsimu.domain.fitness;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessValue;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class FitnessCalculatorTest extends BaseTestCase {

	private static MemoriaDataSet mMemoriaDataSet;
	private static ItemDataSet mItemDataSet;

	private FitnessCalculator sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// ユーザデータ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		sut = new FitnessCalculator();
	}

	@Test
	public void testCalcParty() {
		// 準備
		MemoriaData memoriaData1 = mMemoriaDataSet.find("アーロン");
		Memoria memoria1 = new Memoria(memoriaData1);
		ItemData weaponData1 = mItemDataSet.find("烈風");
		memoria1.setWeapon(weaponData1);
		MemoriaData memoriaData2 = mMemoriaDataSet.find("アーシェ");
		Memoria memoria2 = new Memoria(memoriaData2);
		ItemData weaponData2 = mItemDataSet.find("オブリージュ");
		memoria2.setWeapon(weaponData2);
		Party party = new Party();
		party.add(memoria1);
		party.add(memoria2);

		// テスト実行
		FitnessValue actual = sut.calc(party);

		// 検証
		assertEquals("[10687,[5889,アーロン+烈風+居合い抜き],[4798,アーシェ+オブリージュ+フレア]]",
				actual.toString());
	}

	@Test
	public void testCalcMemoria() {
		// 準備
		MemoriaData memoriaData = mMemoriaDataSet.find("アーロン");
		Memoria memoria = new Memoria(memoriaData);
		ItemData weaponData = mItemDataSet.find("烈風");
		memoria.setWeapon(weaponData);

		// テスト実行
		MemoriaFitness actual = sut.calc(memoria);

		// 検証
		assertEquals("[5889,アーロン+烈風+居合い抜き]", actual.toString());
	}

	@Test
	public void testCalcMemoria_弱点あり() {
		// 準備
		MemoriaData memoriaData = mMemoriaDataSet.find("アーロン");
		Memoria memoria = new Memoria(memoriaData);
		ItemData weaponData = mItemDataSet.find("烈風");
		memoria.setWeapon(weaponData);

		// 敵弱点なしの場合
		MemoriaFitness baseFitness = sut.calc(memoria);

		// テスト実行
		sut.addEnemyWeak(Attr.WIND);
		MemoriaFitness actualFitness = sut.calc(memoria);

		// 検証
		assertTrue(actualFitness.getValue() > baseFitness.getValue());
	}
}
