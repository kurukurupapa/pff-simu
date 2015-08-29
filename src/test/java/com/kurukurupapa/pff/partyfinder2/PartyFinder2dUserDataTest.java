package com.kurukurupapa.pff.partyfinder2;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.FitnessCalculatorFactory;
import com.kurukurupapa.pff.test.BaseTestCase;
import com.kurukurupapa.pff.test.SlowTests;

@Category(SlowTests.class)
public class PartyFinder2dUserDataTest extends BaseTestCase {

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
		mItemDataSet.readUserFile(true);
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readUserFile();
	}

	@Test
	public void testRun_Attack() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();

		// テスト実行
		PartyFinder2d dp = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
				fitnessCalculator);
		dp.mDebug = true;
		dp.run();

		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Recovery() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForRecovery();

		// テスト実行
		PartyFinder2d dp = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
				fitnessCalculator);
		dp.run();

		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_Default() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		PartyFinder2d pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
				fitnessCalculator);
		// pf.mDebug = true;
		pf.run();

		// 検証
		String expected = readExpectedFile();
		assertPartyAsMultiLine(expected, pf.getParty());
	}

	@Test
	public void testRun_Battle_ThunderWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.THUNDER);

		// テスト実行
		PartyFinder2d dp = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
				fitness);
		dp.run();

		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

}
