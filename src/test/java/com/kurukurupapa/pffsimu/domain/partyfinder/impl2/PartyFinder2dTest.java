package com.kurukurupapa.pffsimu.domain.partyfinder.impl2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
import com.kurukurupapa.pffsimu.domain.partyfinder.impl2.PartyFinder2d;
import com.kurukurupapa.pffsimu.test.BaseTestCase;
import com.kurukurupapa.pffsimu.test.SlowTests;

public class PartyFinder2dTest extends BaseTestCase {

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
		List<Party> actual = new ArrayList<Party>();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.add(pf.getParty());
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		assertParty(readExpectedFile(), actual);
	}

	@Test
	public void testRun1_Attack() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForAttack();

		// テスト実行
		List<Party> actual = new ArrayList<Party>();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.add(pf.getParty());
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		assertParty(readExpectedFile(), actual);
	}

	@Test
	public void testRun1_Recovery() {
		// 準備
		FitnessCalculator fitnessCalculator = FitnessCalculatorFactory
				.createForRecovery();

		// テスト実行
		List<Party> actual = new ArrayList<Party>();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.add(pf.getParty());
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		assertParty(readExpectedFile(), actual);
	}

	@Test
	public void testRun1_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		List<Party> actual = new ArrayList<Party>();
		PartyFinder2d pf;
		for (int i = 0; i < 8; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.add(pf.getParty());
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		assertParty(readExpectedFile(), actual);
	}

	@Test
	public void testRun1_Battle_ThunderWeak() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		fitnessCalculator.addEnemyWeak(Attr.THUNDER);

		// テスト実行
		List<Party> actual = new ArrayList<Party>();
		PartyFinder2d pf;
		for (int i = 0; i < 3; i++) {
			pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet,
					fitnessCalculator);
			pf.run(1);
			actual.add(pf.getParty());
			mMemoriaDataSet.remove(pf.getParty().getMemoria(0).getName());
		}

		// 検証
		assertParty(readExpectedFile(), actual);
	}

	@Test
	public void testRun2_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		PartyFinder2d pf;
		pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		pf.run(2);

		// 検証
		String expected = readExpectedFile();
		assertPartyAsMultiLine(expected, pf.getParty());
	}

	@Test
	@Category(SlowTests.class)
	public void testRun4_Battle() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();

		// テスト実行
		PartyFinder2d pf;
		pf = new PartyFinder2d(mMemoriaDataSet, mItemDataSet, fitnessCalculator);
		// pf.mDebug = true;
		pf.run(4);

		// 検証
		String expected = readExpectedFile();
		assertPartyAsMultiLine(expected, pf.getParty());
	}

	@Test
	public void testRun_排他ジョブスキル() {
		// 準備
		FitnessCalculator fitnessCalculator = new FitnessCalculator();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(mItemDataSet);
		memoriaDataSet.add(mMemoriaDataSet.find("アーロン"));
		memoriaDataSet.add(mMemoriaDataSet.find("トレイ"));

		// テスト実行
		PartyFinder2d pf = new PartyFinder2d(memoriaDataSet, mItemDataSet,
				fitnessCalculator);
		// pf.mDebug = true;
		pf.run(2);
		Party actual = pf.getParty();

		// 検証
		assertEquals(2, actual.getMemoriaList().size());
		if (actual.getMemoria(0).getJobSkill() == null) {
			assertTrue(actual.toString(),
					actual.getMemoria(1).getJobSkill() != null);
		} else {
			assertTrue(actual.toString(),
					actual.getMemoria(1).getJobSkill() == null);
		}
	}

}
