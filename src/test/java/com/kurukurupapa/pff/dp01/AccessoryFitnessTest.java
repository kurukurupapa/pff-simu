package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class AccessoryFitnessTest {
	/** 1バトルあたりのターン数 */
	private static final int TURN = 10;

	/** ロガー */
	private static Logger mLogger = Logger
			.getLogger(AccessoryFitnessTest.class);

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private AccessoryFitness sut;

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
		sut = new AccessoryFitness();
	}

	@Test
	public void testCalc_アクセサリ1件() {
		// 準備
		sut.setup(mItemDataSet.find("タフネスリング"));
		Memoria memoria = new Memoria(mMemoriaDataSet.find("アーロン"));

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		int beforeHp = 911;
		int afterHp = (int) (911 * 1.1);
		int upHp = afterHp - beforeHp;
		int upRecovery = ((int) (afterHp / 50) - (int) (beforeHp / 50)) * TURN;
		assertEquals((upHp + upRecovery) + ",タフネスリング,アーロン+タフネスリング", actual);
	}

	@Test
	public void testCalc_アクセサリ素早さ() {
		// 準備
		sut.setup(mItemDataSet.find("ルフェインブーツ"));
		Memoria memoria = new Memoria(mMemoriaDataSet.find("アーロン"));

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		assertEquals("90,ルフェインブーツ,アーロン+ルフェインブーツ", actual);
	}

	@Test
	public void testCalc_武器なし指輪() {
		// 準備
		sut.setup(mItemDataSet.find("風の指輪"));
		Memoria memoria = new Memoria(mMemoriaDataSet.find("アーロン"));

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		assertEquals("0,風の指輪,アーロン+風の指輪", actual);
	}

	@Test
	public void testCalc_武器あり指輪() {
		// 準備
		sut.setup(mItemDataSet.find("風の指輪"));
		ItemData weapon = mItemDataSet.find("烈風");
		Memoria memoria = new Memoria(mMemoriaDataSet.find("アーロン"));
		memoria.setWeapon(weapon);

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		assertEquals("1520,風の指輪,アーロン+烈風+風の指輪", actual);
	}

	@Test
	public void testCalc_武器あり指輪2() {
		// 準備
		sut.setup(mItemDataSet.find("炎の指輪"));
		ItemData weapon = mItemDataSet.find("燃える戦杖");
		Memoria memoria = new Memoria(mMemoriaDataSet.find("ユウナ(No.48)"));
		memoria.setWeapon(weapon);

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		assertEquals("520,炎の指輪,ユウナ(No.48)+燃える戦杖+炎の指輪", actual);
	}

}
