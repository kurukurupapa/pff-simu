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

public class WeaponRankingTest {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private WeaponRanking sut;

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
		sut = new WeaponRanking();
	}

	@Test
	public void testCalc() {
		// 準備

		// テスト実行
		sut.setParams(mMemoriaDataSet, mItemDataSet);
		sut.run();
		List<WeaponFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals("" //
				// 2015/06/27
				+ "8076,ブリザードロングボウ,トレイ+ブリザードロングボウ\n" //
				+ "7908,ディバインアロー,トレイ+ディバインアロー\n" //
				+ "6798,烈風,アーロン+烈風\n" //
				+ "6798,五月雨,アーロン+五月雨\n" //
				+ "6798,おろち,アーロン+おろち\n" //
				+ "5380,閃光のウォーソード,ライトニング(No.119)+閃光のウォーソード\n" //
				+ "5194,ディバインソード,ライトニング(No.119)+ディバインソード\n" //
				+ "5076,オブリージュ,セシル+オブリージュ\n" //
				+ "5076,ネクロフォリア,セシル+ネクロフォリア\n" //
				+ "3180,黄忠の長弓(レア5),トレイ+黄忠の長弓(レア5)\n" //
				+ "2907,セレーネボウ,トレイ+セレーネボウ\n" //
				+ "2586,青紅の剣(レア5),ライトニング(No.119)+青紅の剣(レア5)\n" //
				+ "2280,燃える戦杖,ユウナ(No.48)+燃える戦杖\n" //
				+ "1843,ダンシングダガー,パンネロ+ダンシングダガー\n" //
				+ "1768,イノセントロッド,ヴァニラ+イノセントロッド\n" //
				+ "1512,フォースイーター,アーシェ+フォースイーター\n" //
				+ "1181,鉄壁のグリモア(レア3),デシ+鉄壁のグリモア(レア3)\n" //
				+ "1062,チャームステッキ,ユウナ(No.48)+チャームステッキ\n" //
		, actualStr);
	}

	private String toString(List<WeaponFitness> weaponFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (WeaponFitness e : weaponFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
