package com.kurukurupapa.pff.dp01;

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
		mItemDataSet.readUserFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readUserFile();
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
				// 2015/06/28
				+ "2270,水の指輪,アーロン+五月雨+水の指輪\n" //
				+ "2270,闇の指輪,アーロン+おろち+闇の指輪\n" //
				+ "1640,雷の指輪,マキナ+閃光のウォーソード+雷の指輪\n" //
				+ "1266,マーシャルネイ,元帥シド+ファイアRF+3+マーシャルネイ\n" //
				+ "1215,宿炎の指輪,パンネロ+ファイアRF+3+宿炎の指輪\n" //
				+ "960,パワーリスト,トレイ+ディバインアロー+パワーリスト\n" //
				+ "937,赤兎馬のたてがみ(レア5),トレイ+ディバインアロー+赤兎馬のたてがみ(レア5)\n" //
				+ "933,クリスタルの小手,トレイ+黄忠の長弓(レア5)+クリスタルの小手\n" //
				+ "828,ルフェインブーツ,マキナ+青紅の剣(レア5)+ルフェインブーツ\n" //
				+ "753,エクサイヤリング+1,マキナ+青紅の剣(レア5)+エクサイヤリング+1\n" //
				+ "697,疾風のかんざし,トレイ+黄忠の長弓(レア5)+疾風のかんざし\n" //
				+ "628,SPの腕輪,ヴァニラ+ケアル+SPの腕輪\n" //
				+ "565,エクサバックラー+2,アーロン+ケアル+エクサバックラー+2\n" //
				+ "550,炎の指輪,ユウナ(No.48)+燃える戦杖+炎の指輪\n" //
				+ "480,冷気の指輪,パンネロ+ブリザラ+冷気の指輪\n" //
				+ "476,ルフェインローブ,ヴァニラ+ケアル+ルフェインローブ\n" //
				+ "448,巨人の小手,元帥シド+ファイアRF+3+巨人の小手\n" //
				+ "442,リストバンド,元帥シド+ファイアRF+3+リストバンド\n" //
				+ "394,エスカッション(レア4),アーロン+烈風+エスカッション(レア4)\n" //
				+ "394,プラチナアーマー,アーロン+烈風+プラチナアーマー\n" //
				+ "350,ハチマキ,元帥シド+ファイアRF+3+ハチマキ\n" //
				+ "347,司祭のローブ,ヴァニラ+ケアル+司祭のローブ\n" //
				+ "300,クロスヘルム,ユウナ(No.48)+チャームステッキ+クロスヘルム\n" //
				+ "257,EXコア100%,アーロン+烈風+EXコア100%\n" //
				+ "140,チャクラバンド,ユウナ(No.48)+ケアルバングル+チャクラバンド\n" //
				+ "111,タフネスリング,アーロン+烈風+タフネスリング\n" //
				+ "100,バルキーコート,ユウナ(No.48)+ケアルバングル+バルキーコート\n" //
				+ "90,赤兎馬のたてがみ(レア3),ユウナ(No.48)+ケアルバングル+赤兎馬のたてがみ(レア3)\n" //
		, actualStr);
	}

	@Test
	public void testCalc_評価条件とパーティあり() {
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
		assertEquals("" //
				// 2015/07/01
				+"659,クリスタルの小手,パンネロ+ダンシングダガー+ファイアRF+3+クリスタルの小手\n" //
				+"633,エクサイヤリング+1,パンネロ+ダンシングダガー+ファイアRF+3+エクサイヤリング+1\n" //
				+"623,赤兎馬のたてがみ(レア5),パンネロ+ダンシングダガー+ファイアRF+3+赤兎馬のたてがみ(レア5)\n" //
				+"588,パワーリスト,パンネロ+ダンシングダガー+ファイアRF+3+パワーリスト\n" //
				+"577,宿炎の指輪,パンネロ+ダンシングダガー+ファイアRF+3+宿炎の指輪\n" //
				+"566,SPの腕輪,パンネロ+ダンシングダガー+ファイアRF+3+SPの腕輪\n" //
				+"518,エクサバックラー+2,パンネロ+ダンシングダガー+ファイアRF+3+エクサバックラー+2\n" //
				+"515,ルフェインブーツ,パンネロ+ダンシングダガー+ファイアRF+3+ルフェインブーツ\n" //
				+"486,疾風のかんざし,パンネロ+ダンシングダガー+ファイアRF+3+疾風のかんざし\n" //
				+"432,ルフェインローブ,パンネロ+ダンシングダガー+ファイアRF+3+ルフェインローブ\n" //
				+"342,エスカッション(レア4),パンネロ+ダンシングダガー+ファイアRF+3+エスカッション(レア4)\n" //
				+"342,プラチナアーマー,パンネロ+ダンシングダガー+ファイアRF+3+プラチナアーマー\n" //
				+"316,司祭のローブ,パンネロ+ダンシングダガー+ファイアRF+3+司祭のローブ\n" //
				+"300,クロスヘルム,パンネロ+ダンシングダガー+ファイアRF+3+クロスヘルム\n" //
				+"231,EXコア100%,パンネロ+ダンシングダガー+ファイアRF+3+EXコア100%\n" //
				+"183,巨人の小手,パンネロ+ダンシングダガー+ファイアRF+3+巨人の小手\n" //
				+"162,リストバンド,パンネロ+ダンシングダガー+ファイアRF+3+リストバンド\n" //
				+"140,チャクラバンド,パンネロ+ダンシングダガー+ファイアRF+3+チャクラバンド\n" //
				+"135,ハチマキ,パンネロ+ダンシングダガー+ファイアRF+3+ハチマキ\n" //
				+"100,バルキーコート,パンネロ+ダンシングダガー+ファイアRF+3+バルキーコート\n" //
				+"90,赤兎馬のたてがみ(レア3),パンネロ+ダンシングダガー+ファイアRF+3+赤兎馬のたてがみ(レア3)\n" //
				+"48,タフネスリング,パンネロ+ダンシングダガー+ファイアRF+3+タフネスリング\n" //
				+"0,炎の指輪,パンネロ+ダンシングダガー+ファイアRF+3+炎の指輪\n" //
				+"0,冷気の指輪,パンネロ+ダンシングダガー+ファイアRF+3+冷気の指輪\n" //
				+"0,雷の指輪,パンネロ+ダンシングダガー+ファイアRF+3+雷の指輪\n" //
				+"0,水の指輪,パンネロ+ダンシングダガー+ファイアRF+3+水の指輪\n" //
				+"0,闇の指輪,パンネロ+ダンシングダガー+ファイアRF+3+闇の指輪\n" //
		, actualStr);
	}

	private String toString(List<AccessoryFitness> accessoryFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (AccessoryFitness e : accessoryFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
