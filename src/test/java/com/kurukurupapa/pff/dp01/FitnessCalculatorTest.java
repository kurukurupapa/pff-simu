package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.test.BaseTestCase;

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
		assertEquals("[10666,[5878,アーロン+烈風],[4788,アーシェ+オブリージュ]]",
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
		assertEquals("[5811,アーロン+烈風]", actual.toString());
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
