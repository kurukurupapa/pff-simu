package com.kurukurupapa.pffsimu.domain.partyfinder;

import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * 最適パーティ計算クラス
 */
public interface PartyFinder {
	public void run();

	public void run(int maxMemorias);

	public Party getParty();
}
