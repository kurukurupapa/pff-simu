package com.kurukurupapa.pff.domain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * メモリアデータ集合クラス
 */
public class MemoriaDataSet implements Iterable<MemoriaData> {
	/** メモリアファイルのカラム数 */
	private static final int NUM_COLUMNS = 13;
	/** 区切り文字 */
	private static final String SEP = ",";

	private ItemDataSet mItemDataSet;
	private List<MemoriaData> mMemoriaDataList;

	public MemoriaDataSet(ItemDataSet itemDataSet) {
		mItemDataSet = itemDataSet;
		mMemoriaDataList = new ArrayList<MemoriaData>();
	}

	public MemoriaDataSet(MemoriaDataSet other) {
		this(other.mItemDataSet);
		mMemoriaDataList = other.copyMemoriaDataList();
	}

	@Override
	public MemoriaDataSet clone() {
		return new MemoriaDataSet(this);
	}

	@Override
	public Iterator<MemoriaData> iterator() {
		return mMemoriaDataList.iterator();
	}

	public void readTestFile() {
		// readFile("/memoriaUser_201503.txt");
		readFile("/memoriaTest.txt");
	}

	public void read() {
		readUserFile();
	}

	public void readUserFile() {
		readFile("/memoriaUser.txt");
	}

	public void readMasterFile() {
		readFile("/memoriaMaster.txt");
	}

	private void readFile(String path) {
		mMemoriaDataList.clear();
		BufferedReader r = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream(path)));
		String line = null;
		int count = 0;
		try {
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				count++;
				parseLine(line);
			}
		} catch (RuntimeException e) {
			throw new AppException("メモリアファイルの読み込みに失敗しました。ファイル=" + path + ",行="
					+ count + ",内容=" + line, e);
		} catch (IOException e) {
			throw new AppException("メモリアファイルの読み込みに失敗しました。ファイル=" + path + ",行="
					+ count + ",内容=" + line, e);
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				throw new AppException("メモリアファイルのクローズに失敗しました。ファイル=" + path, e);
			}
		}
	}

	private void parseLine(String line) {
		// コメント行や空行の扱い
		line = line.replaceFirst("#.*", "").trim();
		if (line.replaceAll(SEP, "").trim().length() <= 0) {
			return;
		}

		// カラム数チェック
		String[] columns = line.split(SEP, -1);
		if (columns.length != NUM_COLUMNS) {
			throw new AppException("カラム数が不正です。カラム数=" + columns.length);
		}

		// 追加
		add(new MemoriaData(toName(columns[0]), toInt(columns[1]),
				toInt(columns[2]), toInt(columns[3]), toInt(columns[4]),
				toInt(columns[5]), toAttackFloat(columns[6]),
				toAttackFloat(columns[7]), toAttackFloat(columns[8]),
				toAttackFloat(columns[9]), toWeaponType(columns[10]),
				toJobSkill(columns[11]), toPremiumSkill(columns[12]),
				mItemDataSet));
	}

	private static String toName(String column) {
		return column.trim();
	}

	private static int toInt(String column) {
		return Integer.parseInt(column);
	}

	private static float toAttackFloat(String column) {
		if (column.trim().length() <= 0) {
			// 設定なしの場合、通常値1.0とします。
			return 1.0f;
		} else {
			return Float.parseFloat(column);
		}
	}

	private static WeaponType toWeaponType(String column) {
		return new WeaponType(column.trim());
	}

	private static JobSkill toJobSkill(String column) {
		return new JobSkill(column.trim());
	}

	private static PremiumSkill toPremiumSkill(String column) {
		return new PremiumSkill(column.trim());
	}

	public void add(MemoriaData memoriaData) {
		mMemoriaDataList.add(memoriaData);
	}

	public void remove(String name) {
		int index = findIndex(name);
		mMemoriaDataList.remove(index);
	}

	public List<MemoriaData> copyMemoriaDataList() {
		return new ArrayList<MemoriaData>(mMemoriaDataList);
	}

	public int size() {
		return mMemoriaDataList.size();
	}

	public MemoriaData get(int index) {
		return mMemoriaDataList.get(index);
	}

	public MemoriaData find(String name) {
		for (MemoriaData e : mMemoriaDataList) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}

	public int findIndex(String name) {
		for (int i = 0; i < mMemoriaDataList.size(); i++) {
			if (mMemoriaDataList.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

}
