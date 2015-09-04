package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class Memo {
	private static class MemoKey {
		private int mMemoriaIndex;
		private int mMaxMemoria;
		private int mWeaponIndex;
		private int[] mMaxWeapons;
		private int mAccessoryIndex;
		private int[] mMaxAccessories;

		public MemoKey(int memoriaIndex, int maxMemoria, int weaponIndex,
				int[] maxWeapons, int accessoryIndex, int[] maxAccessories) {
			mMemoriaIndex = memoriaIndex;
			mMaxMemoria = maxMemoria;
			mWeaponIndex = weaponIndex;
			mMaxWeapons = maxWeapons.clone();
			mAccessoryIndex = accessoryIndex;
			mMaxAccessories = maxAccessories.clone();
		}

		@Override
		public String toString() {
			return mMemoriaIndex + "," + mMaxMemoria + "," + mWeaponIndex
					+ ",[" + toStr(mMaxWeapons) + "]," + mAccessoryIndex + ",["
					+ toStr(mMaxAccessories) + "]";
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

		@Override
		public boolean equals(Object obj) {
			MemoKey memoKey = (MemoKey) obj;
			if (mMemoriaIndex != memoKey.mMemoriaIndex
					|| mMaxMemoria != memoKey.mMaxMemoria
					|| mWeaponIndex != memoKey.mWeaponIndex
					|| mMaxWeapons.length != memoKey.mMaxWeapons.length
					|| mAccessoryIndex != memoKey.mAccessoryIndex
					|| mMaxAccessories.length != memoKey.mMaxAccessories.length) {
				return false;
			}
			for (int i = 0; i < mMaxWeapons.length; i++) {
				if (mMaxWeapons[i] != memoKey.mMaxWeapons[i]) {
					return false;
				}
			}
			for (int i = 0; i < mMaxAccessories.length; i++) {
				if (mMaxAccessories[i] != memoKey.mMaxAccessories[i]) {
					return false;
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			return 0;
		}
	}

	/** ロガー */
	private static Logger mLogger = Logger.getLogger(Memo.class);
	/** ハッシュマップ */
	private HashMap<MemoKey, Party> mHashMap;

	public Memo() {
		mHashMap = new HashMap<MemoKey, Party>();
	}

	/**
	 * パーティオブジェクトのコピーを取得します。
	 */
	public Party get(int memoriaIndex, int maxMemoria) {
		return get(memoriaIndex, maxMemoria, -1, new int[] {});
	}

	/**
	 * パーティオブジェクトのコピーを取得します。
	 */
	public Party get(int memoriaIndex, int maxMemoria, int weaponIndex,
			int[] maxWeapons) {
		return get(memoriaIndex, maxMemoria, weaponIndex, maxWeapons, -1,
				new int[] {});
	}

	/**
	 * パーティオブジェクトのコピーを取得します。
	 */
	public Party get(int memoriaIndex, int maxMemoria, int weaponIndex,
			int[] maxWeapons, int accessoryIndex, int[] maxAccessories) {
		MemoKey memoKey = new MemoKey(memoriaIndex, maxMemoria, weaponIndex,
				maxWeapons, accessoryIndex, maxAccessories);
		Party party = mHashMap.get(memoKey);
		if (party != null) {
			// mLogger.debug("get(" + memoKey + "), Hit");
			party = new Party(party);
		}
		return party;
	}

	public Party get(int itemIndex, int[] maxItems) {
		return get(-1, -1, -1, new int[] {}, itemIndex, maxItems);
	}

	/**
	 * パーティオブジェクトをコピーして保存します。
	 */
	public void put(int memoriaIndex, int maxMemoria, Party party) {
		put(memoriaIndex, maxMemoria, -1, new int[] {}, party);
	}

	/**
	 * パーティオブジェクトをコピーして保存します。
	 */
	public void put(int memoriaIndex, int maxMemoria, int weaponIndex,
			int[] maxWeapons, Party party) {
		put(memoriaIndex, maxMemoria, weaponIndex, maxWeapons, -1,
				new int[] {}, party);
	}

	/**
	 * パーティオブジェクトをコピーして保存します。
	 */
	public void put(int memoriaIndex, int maxMemoria, int weaponIndex,
			int[] maxWeapons, int accessoryIndex, int[] maxAccessories,
			Party party) {
		Party newParty = new Party(party);
		MemoKey memoKey = new MemoKey(memoriaIndex, maxMemoria, weaponIndex,
				maxWeapons, accessoryIndex, maxAccessories);
		// mLogger.debug("put(" + memoKey + ",[" + newParty + "])");
		mHashMap.put(memoKey, newParty);
	}

	public void put(int accessoryIndex, int[] maxAccessories, Party party) {
		put(-1, -1, -1, new int[] {}, accessoryIndex, maxAccessories, party);
	}

	/**
	 * 適応度降順のパーティリストを取得します。
	 *
	 * @param num
	 * @return
	 */
	public List<Party> getTopParty(int num) {
		List<Party> list = new ArrayList<Party>();
		List<Party> tmp = new ArrayList<Party>(mHashMap.values());
		Collections.sort(tmp, new Comparator<Party>() {
			@Override
			public int compare(Party o1, Party o2) {
				// 降順
				return o2.getFitness() - o1.getFitness();
			}
		});
		Party oldParty = null;
		for (int i = 0; i < tmp.size(); i++) {
			Party currentParty = tmp.get(i);
			if (!currentParty.equals(oldParty)) {
				list.add(currentParty);
				oldParty = currentParty;
				if (list.size() >= num) {
					break;
				}
			}
		}
		return list;
	}

}
