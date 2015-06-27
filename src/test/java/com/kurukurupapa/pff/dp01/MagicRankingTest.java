package com.kurukurupapa.pff.dp01;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

public class MagicRankingTest {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	private MagicRanking sut;

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
		sut = new MagicRanking();
	}

	@Test
	public void testCalc() {
		// 準備
		sut.setParams(mMemoriaDataSet, mItemDataSet);

		// テスト実行
		sut.run();
		List<MagicFitness> actual = sut.getFitnesses();
		String actualStr = toString(actual);

		// 検証
		assertEquals("" //
				// 2015/06/27
				+ "1845,ファイアRF+3,パンネロ+ファイアRF+3\n" //
				+ "1696,ファイラ,パンネロ+ファイラ\n" //
				+ "1696,ブリザラ,パンネロ+ブリザラ\n" //
				+ "1696,ウォタラ,パンネロ+ウォタラ\n" //
				+ "1696,エアロラ,パンネロ+エアロラ\n" //
				+ "1502,タイタン,ティナ+タイタン\n" //
				+ "1502,ディアボロス,ティナ+ディアボロス\n" //
				+ "732,ケアル,ヴァニラ+ケアル\n" //
				+ "667,プロテアバングル,元帥シド+プロテアバングル\n" //
				+ "658,ケアルラ,ヴァニラ+ケアルラ\n" //
				+ "542,シヴァ,ティナ+シヴァ\n" //
				+ "382,シルフ,ティナ+シルフ\n" //
				+ "263,ケアルバングル,ヴァニラ+ケアルバングル\n" //
		, actualStr);
	}

	private String toString(List<MagicFitness> magicFitnessList) {
		StringBuilder sb = new StringBuilder();
		for (MagicFitness e : magicFitnessList) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
