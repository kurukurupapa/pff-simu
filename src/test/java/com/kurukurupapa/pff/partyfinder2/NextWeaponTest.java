package com.kurukurupapa.pff.partyfinder2;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.ranking.WeaponRanking;
import com.kurukurupapa.pff.test.BaseTestCase;

public class NextWeaponTest extends BaseTestCase {

	private static MemoriaDataSet mMemoriaDataSet;
	private static ItemDataSet mItemDataSet;
	private static FitnessCalculator mFitnessCalculator;
	private static List<ItemFitness> mWeaponFitnesses;

	private NextWeapon sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();

		// 適応度計算オブジェクト
		mFitnessCalculator = new FitnessCalculator();

		// 武器ランキング
		WeaponRanking weaponRanking = new WeaponRanking();
		weaponRanking.setParams(mMemoriaDataSet, mItemDataSet,
				mFitnessCalculator);
		weaponRanking.run();
		mWeaponFitnesses = weaponRanking.getFitnesses();

		System.out
				.println("武器ランキング=" + StringUtils.join(mWeaponFitnesses, ","));
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testReset() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("ライトニング(No.119)");
		currentParty.add(memoriaData);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextWeapon(0, mWeaponFitnesses, mFitnessCalculator);
		assertEquals("青紅の剣(レア5)", sut.next(currentParty, maxParty).getName());
		assertEquals("閃光のウォーソード", sut.next(currentParty, maxParty).getName());

		// テスト実行
		sut.reset();
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals("青紅の剣(レア5)", actual.getName());
	}

	@Test
	public void testNext_現武器なし_次武器あり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("ライトニング(No.119)");
		currentParty.add(memoriaData);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextWeapon(0, mWeaponFitnesses, mFitnessCalculator);

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);
		// 検証
		assertEquals("青紅の剣(レア5)", actual.getName());

		// テスト実行
		ItemData actual2 = sut.next(currentParty, maxParty);
		// 検証
		assertEquals("閃光のウォーソード", actual2.getName());
	}

	@Test
	public void testNext_現武器あり_適応度向上可能性あり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("ライトニング(No.119)");
		ItemData weapon = mItemDataSet.find("ディバインソード");
		Memoria memoria = new Memoria(memoriaData);
		memoria.setWeapon(weapon);
		currentParty.add(memoria);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextWeapon(0, mWeaponFitnesses, mFitnessCalculator);

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals("青紅の剣(レア5)", actual.getName());
	}

	@Test
	public void testNext_現武器あり_適応度向上可能性なし() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("ライトニング(No.119)");
		ItemData weapon = mItemDataSet.find("青紅の剣(レア5)");
		Memoria memoria = new Memoria(memoriaData);
		memoria.setWeapon(weapon);
		currentParty.add(memoria);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextWeapon(0, mWeaponFitnesses, mFitnessCalculator);

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals(null, actual);
	}

	@Test
	public void testNext_武器リスト終端() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("ライトニング(No.119)");
		currentParty.add(memoriaData);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextWeapon(0, mWeaponFitnesses, mFitnessCalculator);
		for (int i = 0; i < 3; i++) {
			sut.next(currentParty, maxParty);
		}

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals(null, actual);
	}

	@Test
	public void testNext_現武器スキップ() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		currentParty.add(new Memoria(mMemoriaDataSet.find("マキナ"), mItemDataSet
				.find("閃光のウォーソード"), null, null));
		currentParty.add(new Memoria(mMemoriaDataSet.find("ライトニング(No.119)"),
				null, null, null));
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextWeapon(1, mWeaponFitnesses, mFitnessCalculator);
		assertEquals("青紅の剣(レア5)", sut.next(currentParty, maxParty).getName());

		// テスト実行
		// ※閃光のウォーソードがスキップされる想定
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals("ディバインソード", actual.getName());
	}

}
