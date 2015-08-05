package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.test.BaseTestCase;

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
		String actual = sut.toString();

		// 検証
		assertEquals("[6764,烈風,アーロン+烈風]", actual);
	}

}
