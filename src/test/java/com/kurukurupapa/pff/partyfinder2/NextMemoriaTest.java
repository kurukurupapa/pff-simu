package com.kurukurupapa.pff.partyfinder2;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.MemoriaFitness;
import com.kurukurupapa.pff.dp01.MemoriaRanking;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.test.BaseTestCase;

public class NextMemoriaTest extends BaseTestCase {

	private static MemoriaDataSet mMemoriaDataSet;
	private static ItemDataSet mItemDataSet;
	private static FitnessCalculator mFitnessCalculator;
	private static List<MemoriaFitness> mMemoriaFitnesses;

	private NextMemoria sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();

		// 適応度計算オブジェクト
		mFitnessCalculator = new FitnessCalculator();

		// メモリアランキング
		MemoriaRanking ranking = new MemoriaRanking();
		ranking.setParams(mMemoriaDataSet, mItemDataSet, mFitnessCalculator);
		ranking.run();
		mMemoriaFitnesses = ranking.getFitnesses();
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

		sut = new NextMemoria(mFitnessCalculator, 0, mMemoriaFitnesses);
		MemoriaData m1 = sut.next(currentParty, maxParty);
		sut.next(currentParty, maxParty);

		// テスト実行
		sut.reset();
		MemoriaData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals(m1, actual);
	}

	@Test
	public void testNext_現在パーティなし_次メモリアあり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;
		sut = new NextMemoria(mFitnessCalculator, 0, mMemoriaFitnesses);

		// テスト実行
		MemoriaData actual = sut.next(currentParty, maxParty);
		// 検証
		assertNotNull(actual);

		// テスト実行
		MemoriaData actual2 = sut.next(currentParty, maxParty);
		// 検証
		assertNotNull(actual2);
		assertNotEquals(actual, actual2);
	}

	@Test
	public void testNext_現在パーティあり_適応度向上可能性あり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("ライトニング(No.119)");
		ItemData weapon = mItemDataSet.find("青紅の剣(レア5)");
		ItemData magic1 = mItemDataSet.find("赤兎馬のたてがみ(レア5)");
		ItemData magic2 = mItemDataSet.find("クリスタルの小手");
		Memoria memoria = new Memoria(memoriaData);
		memoria.setWeapon(weapon);
		memoria.addAccessory(magic1);
		memoria.addAccessory(magic2);
		currentParty.add(memoria);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMemoria(mFitnessCalculator, 0, mMemoriaFitnesses);

		// テスト実行
		MemoriaData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals("パンネロ", actual.getName());
	}

	@Test
	public void testNext_現在パーティあり_適応度向上可能性なし() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("パンネロ");
		ItemData weapon = mItemDataSet.find("ダンシングダガー");
		ItemData magic1 = mItemDataSet.find("ファイアRF+3");
		ItemData magic2 = mItemDataSet.find("ケアル");
		Memoria memoria = new Memoria(memoriaData);
		memoria.setWeapon(weapon);
		memoria.addAccessory(magic1);
		memoria.addAccessory(magic2);
		currentParty.add(memoria);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMemoria(mFitnessCalculator, 0, mMemoriaFitnesses);

		// テスト実行
		MemoriaData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals(null, actual);
	}

	@Test
	public void testNext_メモリアリスト終端() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		sut = new NextMemoria(mFitnessCalculator, 0, mMemoriaFitnesses);
		for (int i = 0; i < mMemoriaDataSet.size(); i++) {
			sut.next(currentParty, maxParty);
		}

		// テスト実行
		MemoriaData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals(null, actual);
	}

	@Test
	public void testNext_現在パーティ内メモリアスキップ() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("ライトニング(No.119)");
		Memoria memoria = new Memoria(memoriaData);
		currentParty.add(memoria);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMemoria(mFitnessCalculator, 0, mMemoriaFitnesses);
		assertEquals("パンネロ", sut.next(currentParty, maxParty).getName());
		assertEquals("元帥シド", sut.next(currentParty, maxParty).getName());
		assertEquals("トレイ", sut.next(currentParty, maxParty).getName());

		// テスト実行
		// ※ライトニング(No.119)がスキップされる想定
		MemoriaData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals("アーロン", actual.getName());
	}

}
