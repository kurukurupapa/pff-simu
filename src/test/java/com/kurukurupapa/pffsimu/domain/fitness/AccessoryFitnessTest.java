package com.kurukurupapa.pffsimu.domain.fitness;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.fitness.AccessoryFitness;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class AccessoryFitnessTest extends BaseTestCase {
	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private AccessoryFitness sut;

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
		sut = new AccessoryFitness();
	}

	@Test
	public void testCalc_アクセサリ1件() {
		// 準備
		ItemData itemData = mItemDataSet.find("タフネスリング");
		sut.setup(itemData);
		MemoriaData memoriaData = mMemoriaDataSet.find("アーロン");
		Memoria memoria = new Memoria(memoriaData);

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		FitnessCalculator calculator = new FitnessCalculator();
		MemoriaFitness before = calculator.calc(new Memoria(memoriaData, null,
				null, null));
		MemoriaFitness after = calculator.calc(new Memoria(memoriaData, null,
				itemData, null));
		int value = after.getValue() - before.getValue();
		assertEquals("[" + value + ",タフネスリング,アーロン+タフネスリング+居合い抜き]", actual);
	}

	@Test
	public void testCalc_アクセサリ素早さ() {
		// 準備
		MemoriaData mdata = mMemoriaDataSet.find("アーロン");
		ItemData accessory = mItemDataSet.find("ルフェインブーツ");
		sut.setup(accessory);
		Memoria memoria = new Memoria(mdata);

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		FitnessCalculator calculator = new FitnessCalculator();
		calculator.addEnemyWeak(Attr.WIND);
		MemoriaFitness before = calculator.calc(new Memoria(mdata, null, null,
				null));
		MemoriaFitness after = calculator.calc(new Memoria(mdata, null,
				accessory, null));
		int value = after.getValue() - before.getValue();
		assertEquals("[" + value + ",ルフェインブーツ,アーロン+ルフェインブーツ+居合い抜き]", actual);
	}

	@Test
	public void testCalc_武器なし指輪() {
		// 準備
		MemoriaData mdata = mMemoriaDataSet.find("アーロン");
		ItemData accessory = mItemDataSet.find("風の指輪");
		sut.setup(accessory);
		Memoria memoria = new Memoria(mdata);

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		assertEquals("[0,風の指輪,アーロン+風の指輪+居合い抜き]", actual);
	}

	@Test
	public void testCalc_武器あり指輪() {
		// 準備
		MemoriaData mdata = mMemoriaDataSet.find("アーロン");
		ItemData weapon = mItemDataSet.find("烈風");
		ItemData accessory = mItemDataSet.find("風の指輪");
		sut.setup(accessory);
		Memoria memoria = new Memoria(mdata);
		memoria.setWeapon(weapon);

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		FitnessCalculator calculator = new FitnessCalculator();
		calculator.addEnemyWeak(Attr.WIND);
		MemoriaFitness before = calculator.calc(new Memoria(mdata, weapon,
				null, null));
		MemoriaFitness after = calculator.calc(new Memoria(mdata, weapon,
				accessory, null));
		int value = after.getValue() - before.getValue();
		assertEquals("[" + value + ",風の指輪,アーロン+烈風+風の指輪+居合い抜き]", actual);
	}

	@Test
	public void testCalc_武器あり指輪2() {
		// 準備
		MemoriaData mdata = mMemoriaDataSet.find("ユウナ(No.48)");
		ItemData weapon = mItemDataSet.find("燃える戦杖");
		ItemData accessory = mItemDataSet.find("炎の指輪");
		sut.setup(accessory);
		Memoria memoria = new Memoria(mdata, weapon, null, null);

		// テスト実行
		sut.calc(memoria);
		String actual = sut.toString();

		// 検証
		FitnessCalculator calculator = new FitnessCalculator();
		calculator.addEnemyWeak(Attr.FIRE);
		MemoriaFitness before = calculator.calc(new Memoria(mdata, weapon,
				null, null));
		MemoriaFitness after = calculator.calc(new Memoria(mdata, weapon,
				accessory, null));
		int value = after.getValue() - before.getValue();
		assertEquals("[" + value + ",炎の指輪,ユウナ(No.48)+燃える戦杖+炎の指輪+オーラ]", actual);
	}

}
