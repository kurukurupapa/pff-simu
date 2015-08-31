package com.kurukurupapa.pff.partyfinder2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.test.BaseTestCase;
import com.kurukurupapa.pff.test.SlowTests;

/**
 * 開発中止。詳細はPartyFinder2bクラスのコメント参照。
 */
@Ignore
public class PartyFinder2bTest extends BaseTestCase {

	private ItemDataSet mItemDataSet;
	private MemoriaDataSet mMemoriaDataSet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();

		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile(true);
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Test
	public void testRun1_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2b pf;
		for (int i = 0; i < 8; i++) {
			pf = new PartyFinder2b(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	@Category(SlowTests.class)
	public void testRun2_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		PartyFinder2b pf;
		pf = new PartyFinder2b(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		pf.run(2);

		// 検証
		String expected = readExpectedFile();
		assertPartyAsMultiLine(expected, pf.getParty());
	}

}
