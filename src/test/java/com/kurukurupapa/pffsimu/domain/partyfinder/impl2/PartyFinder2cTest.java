package com.kurukurupapa.pffsimu.domain.partyfinder.impl2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculatorFactory;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl2.PartyFinder2c;
import com.kurukurupapa.pffsimu.test.BaseTestCase;
import com.kurukurupapa.pffsimu.test.SlowTests;

@Ignore
public class PartyFinder2cTest extends BaseTestCase {

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
	public void testRun1_Hp() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForHp();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2c pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2c(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty().toString() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	public void testRun1_Attack() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2c pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2c(mMemoriaDataSet, mItemDataSet,
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
	public void testRun1_Recovery() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForRecovery();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2c pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2c(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.append(pf.getParty().toString() + "\n");
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual.toString());
	}

	@Test
	public void testRun1_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2c pf;
		for (int i = 0; i < 8; i++) {
			pf = new PartyFinder2c(mMemoriaDataSet, mItemDataSet,
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
	public void testRun1_Battle_ThunderWeak() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		fitnessCalculator.addEnemyWeak(Attr.THUNDER);

		// テスト実行
		StringBuilder actual = new StringBuilder();
		PartyFinder2c pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2c(mMemoriaDataSet, mItemDataSet,
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
	@Ignore
	public void testRun2_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		PartyFinder2c pf;
		pf = new PartyFinder2c(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		pf.run2();
		String actual = pf.getParty().toString();

		// 検証
		String expected = readExpectedFile();
		assertEquals(expected, actual + "\n");
	}

}
