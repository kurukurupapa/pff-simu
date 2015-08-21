package com.kurukurupapa.pff.partyfinder2;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.LeaderSkill;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.MemoriaFitness;

/**
 * 各メモリア・アイテム全組み合わせクラス
 * 
 * 各メモリアについて、武器・魔法・アクセサリの全組み合わせを作成し、適応度順で保持します。
 */
public class MemoriaItemCombinations {
	private static Logger mLogger = Logger
			.getLogger(MemoriaItemCombinations.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private TreeSet<FakeParty> mPartySet;
	private FakeParty[] mPartyArray;

	protected long mCalcCount;
	private long mMCount;
	private long mWCount;
	private long mMaCount1;
	private long mMaCount2;

	public MemoriaItemCombinations(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitnessCalculator = fitnessCalculator;
	}

	public void setup() {
		mPartySet = new TreeSet<FakeParty>(new Comparator<FakeParty>() {
			/**
			 * 比較メソッド
			 * 
			 * 適応度が同じ要素があったとしても、パーティを構成する際に、妥当性が変わるので、両方の要素を残したい。
			 * そのため、なるべく返却を0以外にします。
			 */
			@Override
			public int compare(FakeParty arg0, FakeParty arg1) {
				int result;
				// 適応度の降順
				result = arg1.getFitness() - arg0.getFitness();
				// 文字列表現の昇順
				if (result == 0) {
					result = arg0.toString().compareTo(arg1.toString());
				}
				return result;
			}
		});

		mCalcCount = 0;
		mMCount = 0;
		mWCount = 0;
		mMaCount1 = 0;
		mMaCount2 = 0;

		// 各メモリアについて、武器・魔法・アクセサリ・リーダースキルの全パターンを洗い出します。
		// TODO ジョブスキル
		List<MemoriaData> memorias = mMemoriaDataSet.getMemoriaDataList();
		for (MemoriaData m1 : memorias) {
			// リーダースキルなし
			setupParties(m1, null);

			// リーダースキルあり
			for (MemoriaData m2 : memorias) {
				LeaderSkill leaderSkill = LeaderSkill.parse(m2);
				if (leaderSkill != null) {
					setupParties(m1, leaderSkill);
				}
			}

			mMCount++;
		}

		mPartyArray = mPartySet.toArray(new FakeParty[] {});

		mLogger.debug("計算回数=" + mCalcCount + "(M:" + mMCount + ",W:" + mWCount
				+ ",A1:" + mMaCount1 + ",A2:" + mMaCount2 + "),要素数="
				+ mPartyArray.length);
	}

	private void setupParties(MemoriaData memoriaData, LeaderSkill leaderSkill) {
		List<ItemData> wlist = mItemDataSet.getWeaponList();
		List<ItemData> malist = mItemDataSet.getMagicAccessoryList();

		// 武器ループ
		for (ItemData w : wlist) {
			if (!w.isValid(memoriaData)) {
				continue;
			}

			// 魔法/アクセサリ1ループ
			for (int i = 0; i < malist.size() - 1; i++) {
				ItemData ma1 = malist.get(i);
				if (!ma1.isValid(memoriaData)) {
					continue;
				}
				// 魔法/アクセサリ2ループ
				for (int j = i + 1; j < malist.size(); j++) {
					ItemData ma2 = malist.get(j);
					if (!ma2.isValid(memoriaData)) {
						continue;
					}

					// 適応度計算
					Memoria memoria = new Memoria(memoriaData, w, ma1, ma2);
					FakeParty party = new FakeParty(memoria);
					party.setLeaderSkill(leaderSkill);
					party.calcFitness(mFitnessCalculator);
					mPartySet.add(party);
					mCalcCount++;
					// mLogger.debug(mPartySet.size() + "," + party);

					mMaCount2++;
				}
				mMaCount1++;
			}
			mWCount++;
		}
	}

	public int size() {
		return mPartyArray.length;
	}

	public MemoriaFitness get(int index) {
		if (index < 0 || mPartyArray.length <= index) {
			return null;
		}
		return mPartyArray[index].getFitnessObj().getMemoriaFitnesses().get(0);
	}

	public String toDebugStr() {
		StringBuilder sb = new StringBuilder();
		for (FakeParty e : mPartyArray) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
