package com.kurukurupapa.pffsimu.domain.fitness;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.fitness.WeaponFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class WeaponFitnessTest extends BaseTestCase {
	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private WeaponFitness sut;

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
		sut = new WeaponFitness();
	}

	@Test
	public void testCalc_武器1件() {
		// 準備
		sut.setup(mItemDataSet.find("烈風"));
		Memoria memoria = new Memoria(mMemoriaDataSet.find("アーロン"));

		// テスト実行
		sut.calc(memoria);

		// 検証
		assertEquals("烈風", sut.getItem().getName());
		assertTrue(sut.getFitness() > 0);
		assertEquals("アーロン", sut.getMemoria().getMemoriaData().getName());
		assertEquals("烈風", sut.getMemoria().getWeapon().getName());
		assertEquals(0, sut.getMemoria().getNumAccessories());
	}

}
