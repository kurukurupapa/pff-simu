package com.kurukurupapa.pff.partyfinder2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.test.BaseTestCase;

public class NextMagicAccessoryTest extends BaseTestCase {

	private static MemoriaDataSet mMemoriaDataSet;
	private static ItemDataSet mItemDataSet;
	private static FitnessCalculator mFitnessCalculator;
	private static List<ItemFitness> mItemFitnesses;

	private NextMagicAccessory sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile(true);
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();

		// 適応度計算オブジェクト
		mFitnessCalculator = new FitnessCalculator();

		// 魔法/アクセサリランキング
		mItemFitnesses = new ArrayList<ItemFitness>();
		ItemFitness itemFitness;

		itemFitness = Mockito.mock(ItemFitness.class);
		Mockito.when(itemFitness.getFitness()).thenReturn(1203);
		Mockito.when(itemFitness.getItem())
				.thenReturn(mItemDataSet.find("ケアル"));
		mItemFitnesses.add(itemFitness);

		itemFitness = Mockito.mock(ItemFitness.class);
		Mockito.when(itemFitness.getFitness()).thenReturn(1108);
		Mockito.when(itemFitness.getItem()).thenReturn(
				mItemDataSet.find("ケアルラ"));
		mItemFitnesses.add(itemFitness);

		itemFitness = Mockito.mock(ItemFitness.class);
		Mockito.when(itemFitness.getFitness()).thenReturn(915);
		Mockito.when(itemFitness.getItem()).thenReturn(
				mItemDataSet.find("パワーリスト"));
		mItemFitnesses.add(itemFitness);

		itemFitness = Mockito.mock(ItemFitness.class);
		Mockito.when(itemFitness.getFitness()).thenReturn(713);
		Mockito.when(itemFitness.getItem()).thenReturn(
				mItemDataSet.find("赤兎馬のたてがみ(レア5)"));
		mItemFitnesses.add(itemFitness);

		itemFitness = Mockito.mock(ItemFitness.class);
		Mockito.when(itemFitness.getFitness()).thenReturn(700);
		Mockito.when(itemFitness.getItem()).thenReturn(
				mItemDataSet.find("ファイアRF+3"));
		mItemFitnesses.add(itemFitness);

		System.out.println("魔法/アクセサリランキング="
				+ StringUtils.join(mItemFitnesses, ","));
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

		MemoriaData memoriaData = mMemoriaDataSet.find("アーロン");
		currentParty.add(memoriaData);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 0, mItemFitnesses, mFitnessCalculator);
		sut.next(currentParty, maxParty);
		sut.next(currentParty, maxParty);

		// テスト実行
		sut.reset();
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		// 1番目のアイテムが返却されること。
		assertEquals(mItemFitnesses.get(0).getItem(), actual);
	}

	@Test
	public void testNext_現アイテムなし_次アイテムあり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("アーロン");
		currentParty.add(memoriaData);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 0, mItemFitnesses, mFitnessCalculator);
		sut.next(currentParty, maxParty);
		sut.next(currentParty, maxParty);

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);
		// 検証
		assertEquals(mItemFitnesses.get(2).getItem(), actual);

		// テスト実行
		ItemData actual2 = sut.next(currentParty, maxParty);
		// 検証
		assertEquals(mItemFitnesses.get(3).getItem(), actual2);
	}

	@Test
	public void testNext_現アイテムあり_適応度向上可能性あり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		currentParty.add(new Memoria(mMemoriaDataSet.find("アーロン"), null,
				mItemDataSet.find("赤兎馬のたてがみ(レア5)"), null));
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 0, mItemFitnesses, mFitnessCalculator);
		assertEquals("ケアル", sut.next(currentParty, maxParty).getName());
		assertEquals("ケアルラ", sut.next(currentParty, maxParty).getName());

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		// 現在のアイテムよりも適応度が向上する可能性があるアイテムが返却されること。
		assertEquals("パワーリスト", actual.getName());
	}

	@Test
	public void testNext_現アイテムあり_適応度向上可能性なし() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		currentParty.add(new Memoria(mMemoriaDataSet.find("トレイ"), mItemDataSet
				.find("ディバインアロー"), mItemDataSet.find("パワーリスト"), null));
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 0, mItemFitnesses, mFitnessCalculator);
		assertEquals("ケアル", sut.next(currentParty, maxParty).getName());
		assertEquals("ケアルラ", sut.next(currentParty, maxParty).getName());

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		// 現在のアイテムよりも適応度が向上するアイテムがない場合、nullが返却されること。
		assertEquals(null, actual);
	}

	@Test
	public void testNext_アイテムリスト終端() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("アーロン");
		currentParty.add(memoriaData);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 0, mItemFitnesses, mFitnessCalculator);
		for (int i = 0; i < 37; i++) {
			sut.next(currentParty, maxParty);
		}

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals(null, actual);
	}

	@Test
	public void testNext_現アイテムスキップ() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		currentParty.add(new Memoria(mMemoriaDataSet.find("アーロン"), null,
				mItemDataSet.find("赤兎馬のたてがみ(レア5)"), null));
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 1, mItemFitnesses, mFitnessCalculator);
		assertEquals("ケアル", sut.next(currentParty, maxParty).getName());
		assertEquals("ケアルラ", sut.next(currentParty, maxParty).getName());
		assertEquals("パワーリスト", sut.next(currentParty, maxParty).getName());

		// テスト実行
		// ※赤兎馬のたてがみ(レア5)がスキップされる想定
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals("ファイアRF+3", actual.getName());
	}

}
