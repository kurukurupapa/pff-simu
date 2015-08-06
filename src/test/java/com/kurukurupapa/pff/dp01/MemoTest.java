package com.kurukurupapa.pff.dp01;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.test.BaseTestCase;

public class MemoTest extends BaseTestCase {

	private static ItemDataSet mItemDataSet;
	private static MemoriaDataSet mMemoriaDataSet;
	private Memo sut;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mItemDataSet = new ItemDataSet();
		mItemDataSet.read();
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
		assertThat(actual.toString(), is("0,ヴァニラ"));
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
		assertThat(actual.toString(), is("0,アーロン"));
	}

}
