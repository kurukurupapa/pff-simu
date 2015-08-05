package com.kurukurupapa.pff.partyfinder2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.AppException;
import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;

/**
 * 最適パーティ計算クラス
 * 
 * 分岐限定法のような考え方で計算してみます。
 * 
 * 分岐限定法
 * https://ja.wikipedia.org/wiki/%E5%88%86%E6%9E%9D%E9%99%90%E5%AE%9A%E6%B3%95
 */
public class PartyFinder2a {
	private Logger mLogger;
	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private Party mBestParty;

	public PartyFinder2a(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator) {
		mLogger = Logger.getLogger(PartyFinder2a.class);

		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitnessCalculator = fitnessCalculator;
	}

	public void run() {
		run(Party.MAX_MEMORIAS);
	}

	public void run(int maxMemorias) {
		Validate.validState(1 <= maxMemorias
				&& maxMemorias <= Party.MAX_MEMORIAS);

		if (maxMemorias == 1) {
			run1();
		} else if (maxMemorias == 2) {
			run2();
		} else {
			throw new AppException("未実装");
		}
	}

	public void run1() {
		Party maxParty = null;
		long partyCount = 0;

		// メモリアループ
		for (MemoriaData mdata : mMemoriaDataSet.getMemoriaDataList()) {

			// 当該メモリアが装備可能な武器/魔法/アクセサリリスト
			List<ItemData> wlist = getWeapons(mdata);
			List<ItemData> malist = getMagicsAccessories(mdata);

			// 武器ループ
			for (ItemData wdata : wlist) {
				// スロット1ループ
				for (int i = 0; i < malist.size(); i++) {
					// スロット2ループ
					for (int j = i + 1; j < malist.size(); j++) {

						// 適応度計算
						Memoria memoria = new Memoria(mdata);
						memoria.setWeapon(wdata);
						memoria.addAccessory(malist.get(i));
						memoria.addAccessory(malist.get(j));
						Party party = new Party(memoria);
						party.calcFitness(mFitnessCalculator);
						partyCount++;

						// 最大適応度のパーティを残す
						maxParty = max(maxParty, party);
					}
				}
			}
		}

		mBestParty = maxParty;
		mLogger.debug("計算カウント=" + partyCount);
	}

	/**
	 * メモリア2体のパーティ計算
	 * 
	 * 単純に全探索します。
	 */
	public void run2() {
		int mcount = mMemoriaDataSet.size();
		int wcount = mItemDataSet.getWeaponList().size();
		int macount = mItemDataSet.getMagicAccessoryList().size();
		mLogger.debug("メモリア数=" + mcount + ",武器数=" + wcount + ",魔法/アクセサリ数="
				+ macount);
		mLogger.debug("計算量="
				+ ((long) mcount * (mcount - 1) * wcount * (wcount - 1)
						* macount * (macount - 1) * (macount - 2) * (macount - 3)));

		// 準備
		List<MemoriaData> memorias = mMemoriaDataSet.getMemoriaDataList();
		Party maxParty = null;
		int m1Count = 0;
		long partyCount = 0;

		// メモリアループ1
		for (MemoriaData mdata1 : memorias) {
			mLogger.debug("ループカウント=" + m1Count + "/" + mcount);
			// 当該メモリアで装備可能なアイテム
			List<ItemData> wlist1 = getWeapons(mdata1);
			List<ItemData> malist1 = getMagicsAccessories(mdata1);
			// メモリアループ2
			for (MemoriaData mdata2 : memorias) {
				if (mdata1 == mdata2) {
					continue;
				}
				// 当該メモリアで装備可能なアイテム
				List<ItemData> wlist2 = getWeapons(mdata2);
				List<ItemData> malist2 = getMagicsAccessories(mdata2);
				// 武器ループ1
				for (ItemData wdata1 : wlist1) {
					// 武器ループ2
					for (ItemData wdata2 : wlist2) {
						if (wdata1 == wdata2) {
							continue;
						}
						// スロット1-1ループ
						for (int s11 = 0; s11 < malist1.size() - 1; s11++) {
							ItemData idata11 = malist1.get(s11);
							// スロット1-2ループ
							for (int s12 = s11 + 1; s12 < malist1.size(); s12++) {
								ItemData idata12 = malist1.get(s12);
								// スロット2-1ループ
								for (int s21 = 0; s21 < malist2.size() - 1; s21++) {
									ItemData idata21 = malist2.get(s21);
									if (idata21 == idata11
											|| idata21 == idata12) {
										continue;
									}
									// スロット2-2ループ
									for (int s22 = s21 + 1; s22 < malist2
											.size(); s22++) {
										ItemData idata22 = malist2.get(s22);
										if (idata22 == idata11
												|| idata22 == idata12) {
											continue;
										}

										// 適応度計算
										Memoria memoria1 = new Memoria(mdata1);
										memoria1.setWeapon(wdata1);
										memoria1.addAccessory(idata11);
										memoria1.addAccessory(idata12);
										Memoria memoria2 = new Memoria(mdata2);
										memoria2.setWeapon(wdata2);
										memoria2.addAccessory(idata21);
										memoria2.addAccessory(idata22);
										Party party = new Party();
										party.add(memoria1);
										party.add(memoria2);
										party.calcFitness(mFitnessCalculator);

										// 最大適応度のパーティを残す
										maxParty = max(maxParty, party);

										partyCount++;
									}
								}
							}
						}
					}
				}
			}
			m1Count++;
		}
		mBestParty = maxParty;

		mLogger.debug("計算カウント=" + partyCount);
	}

	private List<ItemData> getWeapons(MemoriaData memoria) {
		List<ItemData> list = new ArrayList<ItemData>();
		for (ItemData e : mItemDataSet.getWeaponList()) {
			if (e.isValid(memoria)) {
				for (int i = 0; i < e.getNumber(); i++) {
					list.add(e);
				}
			}
		}
		return list;
	}

	private List<ItemData> getMagicsAccessories(MemoriaData memoria) {
		List<ItemData> result = new ArrayList<ItemData>();
		for (ItemData e : mItemDataSet.getMagicAccessoryList()) {
			if (e.isValid(memoria)) {
				for (int i = 0; i < e.getNumber(); i++) {
					result.add(e);
				}
			}
		}
		return result;
	}

	public Party getParty() {
		return mBestParty;
	}

	private Party max(Party p1, Party p2) {
		if (p1 == null || p1.getFitness() < p2.getFitness()) {
			return p2;
		} else {
			return p1;
		}
	}

}
