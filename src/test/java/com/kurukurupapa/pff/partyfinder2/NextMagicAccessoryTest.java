package com.kurukurupapa.pff.partyfinder2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.AccessoryRanking;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.MagicRanking;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.test.BaseTestCase;

public class NextMagicAccessoryTest extends BaseTestCase {

	private static MemoriaDataSet mMemoriaDataSet;
	private static ItemDataSet mItemDataSet;
	private static FitnessCalculator mFitnessCalculator;
	private static List<ItemFitness> mMagicAccessoryFitnesses;

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

		// 魔法ランキング
		MagicRanking magicRanking = new MagicRanking();
		magicRanking.setParams(mMemoriaDataSet, mItemDataSet,
				mFitnessCalculator);
		magicRanking.run();
		List<ItemFitness> magicFitnesses = magicRanking.getFitnesses();

		// アクセサリランキング
		AccessoryRanking accessoryRanking = new AccessoryRanking();
		accessoryRanking.setParams(mMemoriaDataSet, mItemDataSet,
				mFitnessCalculator);
		accessoryRanking.run();
		List<ItemFitness> accessoryFitnesses = accessoryRanking.getFitnesses();

		// 魔法とアクセサリをマージする
		mMagicAccessoryFitnesses = new ArrayList<ItemFitness>();
		mMagicAccessoryFitnesses.addAll(magicFitnesses);
		mMagicAccessoryFitnesses.addAll(accessoryFitnesses);
		Collections.sort(mMagicAccessoryFitnesses,
				new Comparator<ItemFitness>() {
					@Override
					public int compare(ItemFitness arg0, ItemFitness arg1) {
						// 降順
						return arg1.getFitness() - arg0.getFitness();
					}
				});

		System.out.println("魔法/アクセサリランキング="
				+ StringUtils.join(mMagicAccessoryFitnesses, ","));
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

		sut = new NextMagicAccessory(0, 0, mMagicAccessoryFitnesses,
				mFitnessCalculator);
		assertEquals("ケアル", sut.next(currentParty, maxParty).getName());
		assertEquals("ケアルラ", sut.next(currentParty, maxParty).getName());

		// テスト実行
		sut.reset();
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
		assertEquals("ケアル", actual.getName());
	}

	@Test
	public void testNext_現アイテムなし_次アイテムあり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		MemoriaData memoriaData = mMemoriaDataSet.find("アーロン");
		currentParty.add(memoriaData);
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 0, mMagicAccessoryFitnesses,
				mFitnessCalculator);
		assertEquals("ケアル", sut.next(currentParty, maxParty).getName());
		assertEquals("ケアルラ", sut.next(currentParty, maxParty).getName());

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);
		// 検証
		assertEquals("パワーリスト", actual.getName());

		// テスト実行
		ItemData actual2 = sut.next(currentParty, maxParty);
		// 検証
		assertEquals("赤兎馬のたてがみ(レア5)", actual2.getName());
	}

	@Test
	public void testNext_現アイテムあり_適応度向上可能性あり() {
		// 準備
		Party currentParty = new Party();
		Party maxParty = null;

		currentParty.add(new Memoria(mMemoriaDataSet.find("アーロン"), null,
				mItemDataSet.find("赤兎馬のたてがみ(レア5)"), null));
		currentParty.calcFitness(mFitnessCalculator);

		sut = new NextMagicAccessory(0, 0, mMagicAccessoryFitnesses,
				mFitnessCalculator);
		assertEquals("ケアル", sut.next(currentParty, maxParty).getName());
		assertEquals("ケアルラ", sut.next(currentParty, maxParty).getName());

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
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

		sut = new NextMagicAccessory(0, 0, mMagicAccessoryFitnesses,
				mFitnessCalculator);
		assertEquals("ケアル", sut.next(currentParty, maxParty).getName());
		assertEquals("ケアルラ", sut.next(currentParty, maxParty).getName());

		// テスト実行
		ItemData actual = sut.next(currentParty, maxParty);

		// 検証
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

		sut = new NextMagicAccessory(0, 0, mMagicAccessoryFitnesses,
				mFitnessCalculator);
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

		sut = new NextMagicAccessory(0, 1, mMagicAccessoryFitnesses,
				mFitnessCalculator);
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
