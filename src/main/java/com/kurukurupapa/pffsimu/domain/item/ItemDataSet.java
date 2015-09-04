package com.kurukurupapa.pffsimu.domain.item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.kurukurupapa.pffsimu.domain.AppException;
import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.Unit;

/**
 * アイテムデータセットクラス
 */
public class ItemDataSet {
	/** アイテムファイルのカラム数 */
	private static final int NUM_COLUMNS = 15;
	/** 区切り文字 */
	private static final String SEP = ",";

	private List<ItemData> mWeaponList;
	private List<ItemData> mMagicAccessoryList;

	/**
	 * コンストラクタ
	 */
	public ItemDataSet() {
		mWeaponList = new ArrayList<ItemData>();
		mMagicAccessoryList = new ArrayList<ItemData>();
	}

	public ItemDataSet(ItemDataSet other) {
		this();
		mWeaponList = new ArrayList<ItemData>(other.mWeaponList);
		mMagicAccessoryList = new ArrayList<ItemData>(other.mMagicAccessoryList);
	}

	@Override
	public ItemDataSet clone() {
		return new ItemDataSet(this);
	}

	public void read() {
		readUserFile();
	}

	public void readTestData() {
		readWeapon();
		readAccessory();
	}

	private void readWeapon() {
		mWeaponList.clear();

		// 武器 剣
		mWeaponList.add(new ItemData(//
				"剣", "閃光のウォーソード", //
				0, 130, 0, 0, 0, //
				Attr.THUNDER, 1));
		mWeaponList.add(new ItemData(//
				"剣", "ディバインソード", //
				0, 110, 20, 0, 0, //
				Attr.HOLINESS, 1));
		// 武器 槍
		// 武器 弓
		mWeaponList.add(new ItemData(//
				"弓", "ディバインアロー", //
				0, 90, 20, 0, 0, //
				Attr.HOLINESS, 1));
		// 武器 杖
		// 武器 楽器
		// 武器 格闘
		// 武器 銃
		// 武器 騎士剣
		mWeaponList.add(new ItemData(//
				"騎士剣", "フォースイーター", //
				0, 100, 0, 20, 0, //
				Attr.NONE, 2));
		mWeaponList.add(new ItemData(//
				"騎士剣", "オブリージュ", //
				0, 130, 0, 30, 0, //
				Attr.HOLINESS, 1));
		// 武器 短剣
		// 武器 刀
		mWeaponList.add(new ItemData(//
				"刀", "風林火山", //
				0, 115, 0, 0, 0, //
				Attr.NONE, 1));
		mWeaponList.add(new ItemData(//
				"刀", "烈風", //
				0, 121, 0, 0, 0, //
				Attr.WIND, 1));
		// 武器 斧
		// 武器 ロッド
		mWeaponList.add(new ItemData(//
				"ロッド", "星屑のロッド", //
				0, 70, 0, 52, 0, //
				Attr.NONE, 1));
		mWeaponList.add(new ItemData(//
				"ロッド", "光の棒", //
				0, 60, 0, 64, 0, //
				Attr.HOLINESS, 1));
		// 武器 カード
		// 武器 投擲
		// 武器 ブラスターエッジ
		// 武器 波動
		// 武器 その他
	}

	private void readAccessory() {
		mMagicAccessoryList.clear();

		//
		// 白魔法
		//
		mMagicAccessoryList.add(new ItemData(//
				"ケアル",//
				MagicType.WHITE, 35, 10, Attr.NONE, 1));
		mMagicAccessoryList.add(new ItemData(//
				"ケアルラ",//
				MagicType.WHITE, 49, 50, Attr.NONE, 1));
		//
		// 黒魔法
		//
		mMagicAccessoryList.add(new ItemData(//
				"ファイラ",//
				MagicType.BLACK, 49, 50, Attr.FIRE, 1));
		mMagicAccessoryList.add(new ItemData(//
				"ブリザラ",//
				MagicType.BLACK, 49, 50, Attr.COLD, 1));
		mMagicAccessoryList.add(new ItemData(//
				"エアロラ",//
				MagicType.BLACK, 49, 50, Attr.WIND, 1));
		mMagicAccessoryList.add(new ItemData(
				//
				ItemType.MAGIC, new ItemType2("黒魔法"),
				"ファイアRF+3",//
				0, Unit.VALUE, 0, Unit.VALUE, 20, Unit.VALUE, 0, Unit.VALUE, 0,
				Unit.VALUE,//
				0, 0,//
				MagicType.BLACK, 60, 26 * 2, Attr.WIND, null, 1));
		//
		// 召喚魔法
		//
		// TODO シルフは全与ダメージの合計数値分味方HP回復
		mMagicAccessoryList.add(new ItemData(//
				"シルフ",//
				MagicType.SUMMON, 110, 60, Attr.COLD, 1));
		mMagicAccessoryList.add(new ItemData(//
				"タイタン",//
				MagicType.SUMMON, 114, 200, Attr.COLD, 1));
		mMagicAccessoryList.add(new ItemData(//
				"シヴァ",//
				MagicType.SUMMON, 64, 80, Attr.COLD, 1));

		//
		// アクセサリ
		//
		mMagicAccessoryList.add(new ItemData(
				//
				"",
				"タフネスリング", //
				10, Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
				Unit.PERCENT, 0, Unit.PERCENT, //
				0, 0, Attr.NONE, null, 1));
		mMagicAccessoryList.add(new ItemData(
				//
				"",
				"ハチマキ", //
				0, Unit.PERCENT, 10, Unit.PERCENT, 0, Unit.PERCENT, 0,
				Unit.PERCENT, 0, Unit.PERCENT, //
				0, 0, Attr.NONE, null, 1));
		mMagicAccessoryList.add(new ItemData(
				//
				"",
				"リストバンド", //
				0, Unit.PERCENT, 13, Unit.PERCENT, 0, Unit.PERCENT, 0,
				Unit.PERCENT, 0, Unit.PERCENT, //
				0, 0, Attr.NONE, null, 1));
		mMagicAccessoryList.add(new ItemData(//
				"", "チャクラバンド", //
				30, 0, 0, 0, 0, //
				0, 10, Attr.NONE, 2));
		mMagicAccessoryList.add(new ItemData(//
				"", "EXコア100%", //
				50, 0, 0, 0, 0, //
				20, 0, Attr.NONE, 4));
		mMagicAccessoryList.add(new ItemData(//
				"", "ルフェインローブ", //
				0, 0, 0, 35, 0, //
				0, 0, Attr.NONE, 1));
		mMagicAccessoryList.add(new ItemData(//
				"", "ルフェインブーツ", //
				0, 0, 35, 0, 0, //
				0, 0, Attr.NONE, 1));
		mMagicAccessoryList.add(new ItemData(//
				"", "SPの腕輪", //
				0, 0, 0, 25, 0, //
				30, 0, Attr.NONE, 2));
		mMagicAccessoryList.add(new ItemData(
				//
				"",
				"マーシャルネイ", //
				0, Unit.PERCENT, 35, Unit.PERCENT, 0, Unit.PERCENT, -25,
				Unit.PERCENT, 0, Unit.PERCENT, //
				0, 0, //
				Attr.NONE, "元帥シド", 2));
	}

	public void readTestFile() {
		// readWeapon();
		// readAccessory();
		readTestFile(false);
	}

	/**
	 * テスト用アイテムファイルを読み込みます。
	 * 
	 * @param flatNumber
	 *            アイテム個数分アイテムオブジェクトを生成する場合true
	 */
	public void readTestFile(boolean flatNumber) {
		readFile("/itemTest.txt", flatNumber);
	}

	public void readUserFile() {
		readFile("/itemUser.txt", false);
	}

	public void readUserFile(boolean flatNumber) {
		readFile("/itemUser.txt", flatNumber);
	}

	public void readMasterFile() {
		readFile("/itemMaster.txt", false);
	}

	public void readMasterFile(boolean flatNumber) {
		readFile("/itemMaster.txt", flatNumber);
	}

	/**
	 * アイテムファイルを読み込みます。
	 * 
	 * @param path
	 *            ファイルパス
	 * @param flatNumber
	 *            アイテム個数分アイテムオブジェクトを生成する場合true
	 */
	private void readFile(String path, boolean flatNumber) {
		mWeaponList.clear();
		mMagicAccessoryList.clear();
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
				parseLine(line, flatNumber);
			}
		} catch (RuntimeException e) {
			throw new AppException("アイテムファイルの読み込みに失敗しました。ファイル=" + path + ",行="
					+ count + ",内容=" + line, e);
		} catch (IOException e) {
			throw new AppException("アイテムファイルの読み込みに失敗しました。ファイル=" + path + ",行="
					+ count + ",内容=" + line, e);
		} finally {
			try {
				r.close();
			} catch (IOException e) {
				throw new AppException("アイテムファイルのクローズに失敗しました。ファイル=" + path, e);
			}
		}
	}

	/**
	 * アイテムファイル1行分を解析し、アイテムオブジェクトを作成します。
	 * 
	 * @param line
	 *            アイテムファイルの1行
	 * @param flatNumber
	 *            アイテム個数分アイテムオブジェクトを生成する場合true
	 */
	private void parseLine(String line, boolean flatNumber) {
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
		ItemType itemType = ItemType.parse(columns[0]);
		ItemType2 itemType2 = new ItemType2(columns[1]);
		MagicType magicType = null;
		int magicCharge = 0;
		int magicEffect = 0;
		if (itemType == ItemType.MAGIC) {
			magicType = MagicType.parse(columns[1]);
			magicCharge = toInt(columns[10]);
			magicEffect = toInt(columns[11]);
		}
		int number = toInt(columns[14]);
		int numAddition = 1;
		if (flatNumber) {
			numAddition = number;
			number = 1;
		}
		for (int i = 0; i < numAddition; i++) {
			ItemData itemData = new ItemData(itemType, itemType2,
					toName(columns[2]), toInt(columns[3]), toUnit(columns[3]),
					toInt(columns[4]), toUnit(columns[4]), toInt(columns[5]),
					toUnit(columns[5]), toInt(columns[6]), toUnit(columns[6]),
					toInt(columns[7]), toUnit(columns[7]),
					toDefenceInt(columns[8]), toDefenceInt(columns[9]),
					magicType, magicCharge, magicEffect,
					Attr.parse(columns[12]), toStr(columns[13]), number);
			switch (itemType) {
			case WEAPON:
				addWeaponData(itemData);
				break;
			case MAGIC:
				addMagicData(itemData);
				break;
			case ACCESSORY:
				addAccessoryData(itemData);
				break;
			}
		}
	}

	private static String toName(String column) {
		return column.trim();
	}

	private static String toStr(String column) {
		return column.trim();
	}

	private static int toInt(String column) {
		int i = column.indexOf("%");
		if (i < 0) {
			return Integer.parseInt(column.trim());
		} else {
			return Integer.parseInt(column.substring(0, i).trim());
		}
	}

	private static int toDefenceInt(String column) {
		if (column.trim().length() <= 0) {
			// 設定なしの場合、通常値0とします。
			return 0;
		} else {
			return Integer.parseInt(column.trim());
		}
	}

	private static Unit toUnit(String column) {
		if (column.indexOf("%") < 0) {
			return Unit.VALUE;
		} else {
			return Unit.PERCENT;
		}
	}

	private static float toAttackFloat(String column) {
		if (column.trim().length() <= 0) {
			// 設定なしの場合、通常値1.0とします。
			return 1.0f;
		} else {
			return Float.parseFloat(column.trim());
		}
	}

	public void addWeaponData(ItemData weaponData) {
		mWeaponList.add(weaponData);
	}

	public void addMagicData(ItemData magicData) {
		mMagicAccessoryList.add(magicData);
	}

	public void addAccessoryData(ItemData accessoryData) {
		mMagicAccessoryList.add(accessoryData);
	}

	public void remove(String name) {
		removeWeapon(name);
		removeMagicOrAccessory(name);
	}

	public void removeWeapon(ItemData weapon) {
		removeWeapon(weapon.getName());
	}

	public void removeWeapon(String name) {
		for (ItemData e : mWeaponList) {
			if (e.getName().equals(name)) {
				mWeaponList.remove(e);
				break;
			}
		}
	}

	public void removeMagicOrAccessory(ItemData[] magicAccessoryArr) {
		for (ItemData e : magicAccessoryArr) {
			removeMagicOrAccessory(e);
		}
	}

	public void removeMagicOrAccessory(ItemData magicOrAccessory) {
		removeMagicOrAccessory(magicOrAccessory.getName());
	}

	public void removeMagicOrAccessory(String name) {
		for (ItemData e : mMagicAccessoryList) {
			if (e.getName().equals(name)) {
				mMagicAccessoryList.remove(e);
				break;
			}
		}
	}

	public List<ItemData> makeAllItemDataList() {
		List<ItemData> list = new ArrayList<ItemData>();
		list.addAll(mWeaponList);
		list.addAll(mMagicAccessoryList);
		return list;
	}

	public List<ItemData> getWeaponList() {
		return mWeaponList;
	}

	/**
	 * 魔法とアクセサリを取得します。（重複を含まない）
	 */
	public List<ItemData> getMagicAccessoryList() {
		return mMagicAccessoryList;
	}

	/**
	 * 魔法を取得します。（重複を含まない）
	 */
	public List<ItemData> makeMagicList() {
		List<ItemData> list = new ArrayList<ItemData>();
		for (ItemData e : mMagicAccessoryList) {
			if (e.getItemType().equals(ItemType.MAGIC)) {
				list.add(e);
			}
		}
		return list;
	}

	/**
	 * アクセサリを取得します。（重複を含まない）
	 */
	public List<ItemData> makeAccessoryList() {
		List<ItemData> list = new ArrayList<ItemData>();
		for (ItemData e : mMagicAccessoryList) {
			if (e.getItemType().equals(ItemType.ACCESSORY)) {
				list.add(e);
			}
		}
		return list;
	}

	public ItemData find(String name) {
		ItemData itemData = findWeapon(name);
		if (itemData == null) {
			itemData = findMagicOrAccessory(name);
		}
		return itemData;
	}

	public ItemData findWeapon(String name) {
		ItemData itemData = null;
		for (ItemData e : mWeaponList) {
			if (e.getName().equals(name)) {
				itemData = e;
				break;
			}
		}
		return itemData;
	}

	public ItemData findMagicOrAccessory(String name) {
		ItemData itemData = null;
		for (ItemData e : mMagicAccessoryList) {
			if (e.getName().equals(name)) {
				itemData = e;
				break;
			}
		}
		return itemData;
	}

	/**
	 * 武器種の数を取得します。
	 */
	public int getNumWeaponKinds() {
		return mWeaponList.size();
	}

	/**
	 * メモリアへ割り当て可能な武器の数を取得します。
	 */
	public int getNumAssignableWeapons() {
		// TODO 毎回計算するのは非効率
		int count = 0;
		for (ItemData e : mWeaponList) {
			count += e.getNumber();
		}
		return count;
	}

	/**
	 * 武器を取得します。
	 *
	 * @param index
	 *            武器種のインデックスです。
	 */
	public ItemData getWeaponDataWithKindIndex(int index) {
		return mWeaponList.get(index);
	}

	/**
	 * 武器を取得します。
	 *
	 * @param index
	 *            割り当て可能な武器のインデックスです。
	 */
	public ItemData getWeaponDataWithAssginableIndex(int index) {
		// TODO 毎回計算するのは非効率
		int count = 0;
		for (int i = 0; i < mWeaponList.size(); i++) {
			ItemData itemData = mWeaponList.get(i);
			count += itemData.getNumber();
			if (count > index) {
				return itemData;
			}
		}
		return null;
	}

	/**
	 * メモリアへ割り当て可能なアクセサリの数を取得します。（重複を含む）
	 */
	public int getNumAssignableAccessoris() {
		// TODO 毎回計算するのは非効率
		int count = 0;
		for (ItemData e : mMagicAccessoryList) {
			count += e.getNumber();
		}
		return count;
	}

	/**
	 * 魔法とアクセサリを取得します。（重複を含む）
	 *
	 * @param index
	 *            割り当て可能なアクセサリのインデックスです。
	 */
	public ItemData getMagicAccessoryDataWithAssginableIndex(int index) {
		// TODO 毎回計算するのは非効率
		int count = 0;
		for (int i = 0; i < mMagicAccessoryList.size(); i++) {
			ItemData itemData = mMagicAccessoryList.get(i);
			count += itemData.getNumber();
			if (count > index) {
				return itemData;
			}
		}
		return null;
	}

}
