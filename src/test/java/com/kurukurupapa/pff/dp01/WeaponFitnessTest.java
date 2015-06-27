package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class WeaponFitnessTest {
	/** 1バトルあたりのターン数 */
	private static final int TURN = 10;

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private WeaponFitness sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readMasterFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readMasterFile();
	}

	@Before
	public void setUp() throws Exception {
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
		assertEquals("4548,烈風,アーロン+烈風", actual);
	}

}
