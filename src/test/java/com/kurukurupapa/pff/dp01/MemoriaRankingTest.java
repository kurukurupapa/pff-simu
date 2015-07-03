package com.kurukurupapa.pff.dp01;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class MemoriaRankingTest {
	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private MemoriaRanking sut;

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
		sut = new MemoriaRanking();
	}

	@Test
	public void testCalc_Battle() {
		// 準備
		Fitness fitness = new FitnessForBattle();
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitness);

		// テスト実行
		sut.run();
		List<MemoriaFitnessValue> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals("" //
				+ "9136,パンネロ+ダンシングダガー+ファイアRF+3+ケアル\n" //
				+ "8574,トレイ+黄忠の長弓(レア5)+赤兎馬のたてがみ(レア5)+パワーリスト\n" //
				+ "8403,ライトニング(No.119)+青紅の剣(レア5)+赤兎馬のたてがみ(レア5)+クリスタルの小手\n" //
				+ "8123,元帥シド+おろち+マーシャルネイ+マーシャルネイ\n" //
				+ "7317,アーロン+おろち+赤兎馬のたてがみ(レア5)+エクサバックラー+2+アーロンLS\n" //
				+ "5940,アーシェ+ネクロフォリア+エクサバックラー+2+ケアル+アーシェLS\n" //
				+ "5855,マキナ+青紅の剣(レア5)+クリスタルの小手+エクサバックラー+2\n" //
				+ "5804,ティナ+青紅の剣(レア5)+エクサバックラー+2+ディアボロス\n" //
				+ "5230,セシル+ネクロフォリア+赤兎馬のたてがみ(レア5)+エクサバックラー+2\n" //
				+ "4873,ヴァニラ+イノセントロッド+ファイアRF+3+ケアル\n" //
				+ "3910,デシ+鉄壁のグリモア(レア3)+赤兎馬のたてがみ(レア5)+パワーリスト\n" //
				+ "3824,ユウナ(No.48)+燃える戦杖+ディアボロス+ケアルラ\n" //
		, actualStr);
	}

	@Test
	public void testCalc_Battle_パーティあり() {
		// 準備
		Fitness fitness = new FitnessForBattle();

		Party party = new Party();
		Memoria memoria = new Memoria(mMemoriaDataSet.find("元帥シド"));
		memoria.setWeapon(mItemDataSet.find("おろち"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		party.add(memoria);
		memoria = new Memoria(mMemoriaDataSet.find("パンネロ"));
		memoria.setWeapon(mItemDataSet.find("ダンシングダガー"));
		memoria.addAccessory(mItemDataSet.find("クリスタルの小手"));
		memoria.addAccessory(mItemDataSet.find("ファイアRF+3"));
		party.add(memoria);

		sut.setParams(mMemoriaDataSet, mItemDataSet, fitness, party);

		// テスト実行
		sut.run();
		List<MemoriaFitnessValue> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertThat(actual.size(), is(mMemoriaDataSet.size() - 2));
		assertTrue(actualStr.indexOf("元帥シド") < 0);
		assertTrue(actualStr.indexOf("マーシャルネイ") < 0);
		assertTrue(actualStr.indexOf("パンネロ") < 0);
		assertTrue(actualStr.indexOf("クリスタルの小手") < 0);
		assertTrue(actualStr.indexOf("ファイアRF+3") < 0);
	}

	private String toString(List<MemoriaFitnessValue> memoriaFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (MemoriaFitnessValue e : memoriaFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
