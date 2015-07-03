package com.kurukurupapa.pff.dp01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class AccessoryRankingTest {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private AccessoryRanking sut;

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
		sut = new AccessoryRanking();
	}

	@Test
	public void testCalc_パーティなし() {
		// 準備

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet);
		sut.run();
		List<AccessoryFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals("" //
				+ "2270,水の指輪,アーロン+五月雨+水の指輪\n" //
				+ "2270,風の指輪,アーロン+烈風+風の指輪\n" //
				+ "2270,闇の指輪,アーロン+おろち+闇の指輪\n" //
				+ "1640,雷の指輪,マキナ+閃光のウォーソード+雷の指輪\n" //
				+ "1156,宿炎の指輪,パンネロ+ファイアRF+3+宿炎の指輪\n" //
				+ "975,マーシャルネイ,元帥シド+ファイアRF+3+マーシャルネイ\n" //
				+ "915,パワーリスト,トレイ+ディバインアロー+パワーリスト\n" //
				+ "899,赤兎馬のたてがみ(レア5),トレイ+ディバインアロー+赤兎馬のたてがみ(レア5)\n" //
				+ "713,クリスタルの小手,トレイ+ディバインアロー+クリスタルの小手\n" //
				+ "629,SPの腕輪,ヴァニラ+ケアル+SPの腕輪\n" //
				+ "565,エクサバックラー+2,アーロン+ケアル+エクサバックラー+2\n" //
				+ "550,炎の指輪,ユウナ(No.48)+燃える戦杖+炎の指輪\n" //
				+ "533,疾風のかんざし,トレイ+黄忠の長弓(レア5)+疾風のかんざし\n" //
				+ "480,冷気の指輪,パンネロ+ブリザラ+冷気の指輪\n" //
				+ "476,ルフェインローブ,ヴァニラ+ケアル+ルフェインローブ\n" //
				+ "421,リストバンド,トレイ+ディバインアロー+リストバンド\n" //
				+ "420,エクサイヤリング+1,マキナ+青紅の剣(レア5)+エクサイヤリング+1\n" //
				+ "412,ルフェインブーツ,マキナ+青紅の剣(レア5)+ルフェインブーツ\n" //
				+ "394,エスカッション(レア4),アーロン+烈風+エスカッション(レア4)\n" //
				+ "394,プラチナアーマー,アーロン+烈風+プラチナアーマー\n" //
				+ "369,巨人の小手,元帥シド+烈風+巨人の小手\n" //
				+ "300,クロスヘルム,ユウナ(No.48)+チャームステッキ+クロスヘルム\n" //
				+ "257,EXコア100%,アーロン+烈風+EXコア100%\n" //
				+ "111,タフネスリング,アーロン+烈風+タフネスリング\n" //
				+ "100,バルキーコート,ユウナ(No.48)+ケアルバングル+バルキーコート\n" //
		, actualStr);
	}

	@Test
	public void testCalc_パーティあり() {
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
		memoria.addAccessory(mItemDataSet.find("ファイアRF+3"));
		party.add(memoria);

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet, fitness, party, 1);
		sut.run();
		List<AccessoryFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertThat(actual.size(),
				is(mItemDataSet.makeAccessoryList().size() - 1));
		assertTrue(actualStr.indexOf("マーシャルネイ") < 0);
	}

	private String toString(List<AccessoryFitness> accessoryFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (AccessoryFitness e : accessoryFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
