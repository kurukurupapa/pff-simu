package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class AccessoryRankingTest {

	/** ロガー */
	private static Logger mLogger = Logger
			.getLogger(AccessoryRankingTest.class);

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
	public void testCalc() {
		// 準備

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet);
		sut.run();
		List<AccessoryFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals("" //
				// 2015/06/15
				+ "2270,水の指輪,アーロン+五月雨+水の指輪\n" //
				+ "1640,雷の指輪,マキナ+閃光のウォーソード+雷の指輪\n" //
				+ "1580,闇の指輪,セシル+ネクロフォリア+闇の指輪\n" //
				+ "1127,宿炎の指輪,パンネロ+ファイアRF+3+宿炎の指輪\n" //
				+ "893,パワーリスト,トレイ+ディバインアロー+パワーリスト\n" //
				+ "880,赤兎馬のたてがみ(レア5),トレイ+ディバインアロー+赤兎馬のたてがみ(レア5)\n" //
				+ "680,マーシャルネイ,元帥シド+ファイアRF+3+マーシャルネイ\n" //
				+ "628,SPの腕輪,ヴァニラ+ケアル+SPの腕輪\n" //
				+ "607,クリスタルの小手,トレイ+ディバインアロー+クリスタルの小手\n" //
				+ "550,炎の指輪,ユウナ(No.48)+燃える戦杖+炎の指輪\n" //
				+ "480,冷気の指輪,パンネロ+ブリザラ+冷気の指輪\n" //
				+ "476,ルフェインローブ,ヴァニラ+ケアル+ルフェインローブ\n" //
				+ "451,疾風のかんざし,トレイ+黄忠の長弓(レア5)+疾風のかんざし\n" //
				+ "446,エクサバックラー,アーロン+プロテアバングル+エクサバックラー\n" //
				+ "411,リストバンド,トレイ+ディバインアロー+リストバンド\n" //
				+ "394,エスカッション(レア4),アーロン+烈風+エスカッション(レア4)\n" //
				+ "394,プラチナアーマー,アーロン+烈風+プラチナアーマー\n" //
				+ "355,巨人の小手,トレイ+ディバインアロー+巨人の小手\n" //
				+ "347,司祭のローブ,ヴァニラ+ケアル+司祭のローブ\n" //
				+ "308,ハチマキ,トレイ+ディバインアロー+ハチマキ\n" //
				+ "300,クロスヘルム,ユウナ(No.48)+チャームステッキ+クロスヘルム\n" //
				+ "257,EXコア100%,アーロン+烈風+EXコア100%\n" //
				+ "204,ルフェインブーツ,マキナ+青紅の剣(レア5)+ルフェインブーツ\n" //
				+ "140,チャクラバンド,ユウナ(No.48)+ケアルバングル+チャクラバンド\n" //
				+ "111,タフネスリング,アーロン+烈風+タフネスリング\n" //
				+ "100,バルキーコート,ユウナ(No.48)+ケアルバングル+バルキーコート\n" //
				+ "90,赤兎馬のたてがみ(レア3),ユウナ(No.48)+ケアルバングル+赤兎馬のたてがみ(レア3)\n" //
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
