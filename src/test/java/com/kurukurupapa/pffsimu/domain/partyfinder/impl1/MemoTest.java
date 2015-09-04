package com.kurukurupapa.pffsimu.domain.partyfinder.impl1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;
import com.kurukurupapa.pffsimu.domain.party.Party;
import com.kurukurupapa.pffsimu.domain.partyfinder.impl1.Memo;
import com.kurukurupapa.pffsimu.test.BaseTestCase;

public class MemoTest extends BaseTestCase {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;
	private Memo sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mItemDataSet = new ItemDataSet();
		mItemDataSet.readTestFile();
		mMemoriaDataSet = new MemoriaDataSet(mItemDataSet);
		mMemoriaDataSet.readTestFile();
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		sut = new Memo();
	}

	@Test
	public void testGet() {
		// 準備
		Party party = new Party();
		Memoria memoria = new Memoria(mMemoriaDataSet.find("ヴァニラ"));
		party.add(memoria);
		sut.put(0, 0, party);

		// テスト実行
		Party actual = sut.get(0, 0);

		// 検証
		assertEquals(1, actual.getMemoriaList().size());
		assertEquals("ヴァニラ", actual.getMemoria(0).getName());
	}

	@Test
	public void testGet_Clone() {
		// 準備
		Party party = new Party();
		Memoria memoria = new Memoria(mMemoriaDataSet.find("アーロン"));
		party.add(memoria);
		sut.put(0, 0, party);

		// この変更が影響をあたえないこと
		memoria.setWeapon(mItemDataSet.find("烈風"));
		party.add(new Memoria(mMemoriaDataSet.get(0)));
		Party tmp = sut.get(0, 0);
		tmp.getMemoria(0).setWeapon(mItemDataSet.find("烈風"));
		tmp.add(new Memoria(mMemoriaDataSet.get(0)));

		// テスト実行
		Party actual = sut.get(0, 0);

		// 検証
		assertEquals(1, actual.getMemoriaList().size());
		assertEquals("アーロン", actual.getMemoria(0).getName());
		assertEquals(null, actual.getMemoria(0).getWeapon());
	}

}
