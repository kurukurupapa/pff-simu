package com.kurukurupapa.pffsimu.domain.partyfinder.impl1;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculatorFactory;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl1.Dp01;
import com.kurukurupapa.pffsimu.test.BaseTestCase;
import com.kurukurupapa.pffsimu.test.SlowTests;

@Category(SlowTests.class)
public class Dp01UserDataTest extends BaseTestCase {
	/** ロガー */
	private static Logger mLogger;

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mLogger = Logger.getLogger(Dp01Test.class);

		// データ読み込み
		mItemDataSet = new ItemDataSet();
		mItemDataSet.read();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readUserFile();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testRun_Hp() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		dp.run();
		for (Party e : dp.getTopParty(10)) {
			mLogger.debug(e);
		}
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Attack() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Recovery() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForRecovery();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_Default() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		dp.run();
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_ColdWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.COLD);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_EarthWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.EARTH);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_FireWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.FIRE);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_HolinessWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.HOLINESS);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_IceWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.ICE);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_ThunderWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.THUNDER);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_WaterWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.WATER);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_WindWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.WIND);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_DarknessWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.DARKNESS);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_FlightWeak() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.addEnemyWeak(Attr.FLIGHT);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_PhysicalResistance() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.setEnemyPhysicalResistance(200);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		mLogger.debug(dp.getParty());
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_MagicResistance() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.setEnemyMagicResistance(200);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_物理防御重視() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.setWeight(1, 1, 2, 1, 1);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

	@Test
	public void testRun_Battle_魔法防御重視() {
		// 準備
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.setWeight(1, 1, 1, 2, 1);
		// テスト実行
		Dp01 dp = new Dp01(mMemoriaDataSet, mItemDataSet, fitness);
		dp.run();
		// 検証
		assertPartyAsMultiLine(readExpectedFile(), dp.getParty());
	}

}
