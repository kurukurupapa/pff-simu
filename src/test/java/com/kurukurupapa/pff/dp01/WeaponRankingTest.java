package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class WeaponRankingTest {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private WeaponRanking sut;

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
		sut = new WeaponRanking();
	}

	@Test
	public void testCalc_パーティなし() {
		// 準備

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet);
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals("" //
				+ "8028,ブリザードロングボウ,トレイ+ブリザードロングボウ\n" //
				+ "7694,ディバインアロー,トレイ+ディバインアロー\n" //
				+ "6764,烈風,アーロン+烈風\n" //
				+ "6764,五月雨,アーロン+五月雨\n" //
				+ "6764,おろち,アーロン+おろち\n" //
				+ "5120,閃光のウォーソード,ライトニング(No.119)+閃光のウォーソード\n" //
				+ "4998,オブリージュ,セシル+オブリージュ\n" //
				+ "4998,ネクロフォリア,セシル+ネクロフォリア\n" //
				+ "4782,ディバインソード,ライトニング(No.119)+ディバインソード\n" //
				+ "3122,黄忠の長弓(レア5),トレイ+黄忠の長弓(レア5)\n" //
				+ "2854,セレーネボウ,トレイ+セレーネボウ\n" //
				+ "2278,青紅の剣(レア5),ライトニング(No.119)+青紅の剣(レア5)\n" //
				+ "2175,燃える戦杖,ユウナ(No.48)+燃える戦杖\n" //
				+ "1718,イノセントロッド,ヴァニラ+イノセントロッド\n" //
				+ "1627,ダンシングダガー,パンネロ+ダンシングダガー\n" //
				+ "1440,フォースイーター,アーシェ+フォースイーター\n" //
				+ "1047,鉄壁のグリモア(レア3),デシ+鉄壁のグリモア(レア3)\n" //
				+ "961,チャームステッキ,ユウナ(No.48)+チャームステッキ\n" //
		, actualStr);
	}

	@Test
	public void testCalc_パーティあり() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		Party party = new Party();
		Memoria memoria = new Memoria(mMemoriaDataSet.find("元帥シド"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		memoria.addAccessory(mItemDataSet.find("マーシャルネイ"));
		party.add(memoria);
		memoria = new Memoria(mMemoriaDataSet.find("パンネロ"));
		memoria.setWeapon(mItemDataSet.find("ダンシングダガー"));
		memoria.addAccessory(mItemDataSet.find("ファイアRF+3"));
		party.add(memoria);

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitnessCalculator, party,
				0);
		sut.run();
		List<ItemFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals("" //
				+ "2022,烈風,元帥シド+烈風+マーシャルネイ+マーシャルネイ\n" //
				+ "2022,五月雨,元帥シド+五月雨+マーシャルネイ+マーシャルネイ\n" //
				+ "2022,おろち,元帥シド+おろち+マーシャルネイ+マーシャルネイ\n" //
		, actualStr);
	}

	private String toString(List<ItemFitness> weaponFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (ItemFitness e : weaponFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
