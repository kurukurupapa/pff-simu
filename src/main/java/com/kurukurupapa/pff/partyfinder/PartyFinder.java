package com.kurukurupapa.pff.partyfinder;

import com.kurukurupapa.pff.dp01.Party;

/**
 * 最適パーティ計算クラス
 */
public interface PartyFinder {
	public void run();

	public void run(int maxMemorias);

	public Party getParty();
}
