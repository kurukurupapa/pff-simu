package com.kurukurupapa.pff.dp01;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * 最適パーティ計算クラス
 * 
 * 動的計画法で計算します。
 */
public class Dp01 {
	/** ロガー */
	private Logger mLogger;

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private Party mParty;
	private Memo mMemo;
	private Memo mWeaponMemo;
	private Memo mAccessoryMemo;

	public Dp01(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet,
			FitnessCalculator fitnessCalculator) {
		mLogger = Logger.getLogger(Dp01.class);

		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitnessCalculator = fitnessCalculator;
		mMemo = new Memo();
	}

	public Party getParty() {
		return mParty;
	}

	public List<Party> getTopParty(int num) {
		return mMemo.getTopParty(num);
	}

	public void run() {
		run(Party.MAX_MEMORIAS);
	}

	public void run(int maxMemorias) {
		mLogger.trace("run(" + maxMemorias + ")");
		Validate.validState(maxMemorias <= Party.MAX_MEMORIAS);

		mParty = calcMemoria(0, maxMemorias);
	}

	private Party calcAccessory(Party party) {
		mAccessoryMemo = new Memo();
		int[] maxAccessories = createArr(party.size(), Memoria.MAX_ACCESSORIES);
		// int[] maxAccessories = new int[party.size()];
		// for (int i =0; i < party.size(); i++) {
		// maxAccessories[i] = party.getMemoria(i).getRemainAccessorySlot();
		// }
		return calcAccessory(party, 0, maxAccessories);
	}

	private Party calcAccessory(Party baseParty, int accessoryIndex,
			int[] maxAccessories) {
		mLogger.trace("calcAccessory(accessoryIndex=" + accessoryIndex
				+ ",maxAccessories=[" + toStr(maxAccessories) + "])");

		// 処理対象のアクセサリが残っていなければ、処理不要とします。
		if (accessoryIndex >= mItemDataSet.getNumAssignableAccessoris()) {
			return baseParty;
		}

		// アクセサリスロットに空きがなければ、処理不要とします。
		if (sumArr(maxAccessories) <= 0) {
			return baseParty;
		}

		// メモ取得
		Party party = mAccessoryMemo.get(accessoryIndex, maxAccessories);
		if (party != null) {
			return party;
		}

		// 準備
		ItemData accessoryData = mItemDataSet
				.getMagicAccessoryDataWithAssginableIndex(accessoryIndex);

		// 当該アクセサリを使わない場合
		party = calcAccessory(baseParty, accessoryIndex + 1, maxAccessories);
		printParty(party);

		// 当該アクセサリを使う場合
		Party tmp;
		for (int i = 0; i < baseParty.size(); i++) {
			if (maxAccessories[i] <= 0) {
				continue;
			}
			int[] newMaxAccessories = maxAccessories.clone();
			newMaxAccessories[i]--;
			tmp = calcAccessory(baseParty, accessoryIndex + 1,
					newMaxAccessories);
			if (tmp.size() <= i
					|| !tmp.getMemoria(i).validAccessoryData(accessoryData)) {
				continue;
			}
			tmp = tmp.clone();
			tmp.getMemoria(i).addAccessory(accessoryData);
			tmp.calcFitness(mFitnessCalculator);
			printParty(tmp);
			party = getFitnessParty(party, tmp);
		}

		mAccessoryMemo.put(accessoryIndex, maxAccessories, party);
		return party;
	}

	private Party calcWeapon(Party party) {
		mWeaponMemo = new Memo();
		int[] maxWeapons = createArr(party.size(), Memoria.MAX_WEAPON);
		// int[] maxWeapons = new int[party.size()];
		// for (int i =0; i < party.size(); i++) {
		// maxWeapons[i] = party.getMemoria(i).getRemainWeaponSlot();
		// }
		return calcWeapon(party, 0, maxWeapons);
	}

	private Party calcWeapon(Party baseParty, int weaponIndex, int[] maxWeapons) {
		mLogger.trace("calcWeapon(weaponIndex=" + weaponIndex + ",maxWeapons=["
				+ toStr(maxWeapons) + "])");

		// 処理対象の武器が残っていなければ、処理不要とします。
		if (weaponIndex >= mItemDataSet.getNumAssignableWeapons()) {
			return baseParty;
		}

		// 武器スロットに空きがなければ、処理不要とします。
		if (sumArr(maxWeapons) <= 0) {
			return baseParty;
		}

		// メモ取得
		Party party = mWeaponMemo.get(weaponIndex, maxWeapons);
		if (party != null) {
			return party;
		}

		// 準備
		ItemData weaponData = mItemDataSet
				.getWeaponDataWithAssginableIndex(weaponIndex);

		// 当該武器を使わない場合
		party = calcWeapon(baseParty, weaponIndex + 1, maxWeapons);
		printParty(party);

		// 当該武器を使う場合
		Party tmp;
		for (int i = 0; i < baseParty.size(); i++) {
			if (maxWeapons[i] <= 0) {
				continue;
			}
			int[] newMaxWeapons = maxWeapons.clone();
			newMaxWeapons[i]--;
			tmp = calcWeapon(baseParty, weaponIndex + 1, newMaxWeapons);
			if (tmp.size() <= i
					|| !tmp.getMemoria(i).validWeaponData(weaponData)) {
				continue;
			}
			tmp = tmp.clone();
			tmp.getMemoria(i).setWeapon(weaponData);
			tmp.calcFitness(mFitnessCalculator);
			printParty(tmp);
			party = getFitnessParty(party, tmp);
		}

		mWeaponMemo.put(weaponIndex, maxWeapons, party);
		return party;
	}

	private Party calcMemoria(int memoriaIndex, int maxMemoria) {
		mLogger.trace("calcMemoria(memoriaIndex=" + memoriaIndex
				+ ",maxMemoria=" + maxMemoria + ")");

		// 処理対象のメモリアが残っていなければ終了
		if (memoriaIndex >= mMemoriaDataSet.size()) {
			return new Party();
		}
		// パーティに参加可能な空きがなければ終了
		if (maxMemoria <= 0) {
			return new Party();
		}

		// メモ取得
		Party party = mMemo.get(memoriaIndex, maxMemoria);
		if (party != null) {
			return party;
		}

		// 当該インデックスのメモリアが参加しない場合
		party = calcMemoria(memoriaIndex + 1, maxMemoria);
		printParty(party);

		// 当該インデックスのメモリアが参加する場合
		// 武器・アクセサリ類を一旦クリアして計算しなおします。
		Party tmp = calcMemoria(memoriaIndex + 1, maxMemoria - 1);
		tmp = tmp.clone();
		tmp.add(mMemoriaDataSet.get(memoriaIndex));
		tmp.clearWeaponAccessories();
		tmp.calcFitness(mFitnessCalculator);
		tmp = calcWeapon(tmp);
		tmp = calcAccessory(tmp);
		printParty(tmp);
		party = getFitnessParty(party, tmp);

		mMemo.put(memoriaIndex, maxMemoria, party);
		// mLogger.info("calcMemoria(memoriaIndex=" + memoriaIndex
		// + ",maxMemoria=" + maxMemoria + ") end");
		return party;
	}

	private Party getFitnessParty(Party party1, Party party2) {
		if (party1 == null) {
			return party2;
		}
		return party1.getFitness() < party2.getFitness() ? party2 : party1;
	}

	private void printParty(Party party) {
		if (party.size() >= Party.MAX_MEMORIAS) {
			// mLogger.debug(party);
		}
	}

	private String toStr(int[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	private int[] createArr(int size, int value) {
		int[] arr = new int[size];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = value;
		}
		return arr;
	}

	private int sumArr(int[] arr) {
		int value = 0;
		for (int e : arr) {
			value += e;
		}
		return value;
	}

}
