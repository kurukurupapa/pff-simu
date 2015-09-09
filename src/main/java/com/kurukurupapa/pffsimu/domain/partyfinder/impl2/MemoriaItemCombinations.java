package com.kurukurupapa.pffsimu.domain.partyfinder.impl2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.MemoriaFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.item.ItemDataSet;
import com.kurukurupapa.pffsimu.domain.memoria.JobSkill;
import com.kurukurupapa.pffsimu.domain.memoria.LeaderSkill;
import com.kurukurupapa.pffsimu.domain.memoria.LeaderSkillFactory;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaDataSet;

/**
 * 各メモリア・アイテム全組み合わせクラス
 * <p>
 * 各メモリアについて、武器・魔法・アクセサリの組み合わせを作成し、適応度順で保持します。
 * </p>
 * <p>
 * パフォーマンスを考慮し、各種組み合わせは、適宜枝刈りします。
 * </p>
 */
public class MemoriaItemCombinations {
	private static Logger mLogger = Logger
			.getLogger(MemoriaItemCombinations.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private FakePartySet mPartySet;
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
		mPartySet = new FakePartySet();

		mCalcCount = 0;
		mMCount = 0;
		mWCount = 0;
		mMaCount1 = 0;
		mMaCount2 = 0;

		// 各メモリアについて、武器・魔法・アクセサリ・リーダースキルの全パターンを洗い出します。
		List<MemoriaData> memorias = mMemoriaDataSet.getMemoriaDataList();
		for (MemoriaData m1 : memorias) {
			// リーダースキルなし
			setupParties(m1, null);

			// リーダースキルあり
			// TODO 同一リーダースキルを持ったメモリアが複数存在する場合を考慮していません。
			for (MemoriaData m2 : memorias) {
				LeaderSkill leaderSkill = LeaderSkillFactory.get(m2);
				if (leaderSkill != null) {
					setupParties(m1, leaderSkill);
				}
			}

			mMCount++;
		}
		mPartyArray = mPartySet.toArray(new FakeParty[] {});

		mLogger.debug("メモリア数=" + memorias.size() + ",武器数="
				+ mItemDataSet.getWeaponList().size() + ",魔法/アクセサリ数="
				+ mItemDataSet.getMagicAccessoryList().size() + ",計算回数="
				+ mCalcCount + "(M:" + mMCount + ",W:" + mWCount + ",A1:"
				+ mMaCount1 + ",A2:" + mMaCount2 + "),結果要素数="
				+ mPartyArray.length);
	}

	public void setup(LeaderSkill leaderSkill) {
		mPartySet = new FakePartySet();

		mCalcCount = 0;
		mMCount = 0;
		mWCount = 0;
		mMaCount1 = 0;
		mMaCount2 = 0;

		// 各メモリアについて、武器・魔法・アクセサリの全パターンを洗い出します。
		// TODO 同一リーダースキルを持ったメモリアが複数存在する場合を考慮していません。
		List<MemoriaData> memorias = mMemoriaDataSet.getMemoriaDataList();
		for (MemoriaData m1 : memorias) {
			setupParties(m1, leaderSkill);
			mMCount++;
		}
		mPartyArray = mPartySet.toArray(new FakeParty[] {});

		mLogger.debug("メモリア数=" + memorias.size() + ",武器数="
				+ mItemDataSet.getWeaponList().size() + ",魔法/アクセサリ数="
				+ mItemDataSet.getMagicAccessoryList().size() + ",LS="
				+ leaderSkill + ",計算回数=" + mCalcCount + "(M:" + mMCount + ",W:"
				+ mWCount + ",A1:" + mMaCount1 + ",A2:" + mMaCount2
				+ "),結果要素数=" + mPartyArray.length);
	}

	private void setupParties(MemoriaData memoriaData, LeaderSkill leaderSkill) {
		JobSkill jobSkill = memoriaData.getJobSkill();
		if (jobSkill == null || jobSkill.isExclusiveCondition()) {
			// ジョブスキルなし
			setupParties(memoriaData, leaderSkill, false);
		}
		if (jobSkill != null) {
			// ジョブスキルあり
			setupParties(memoriaData, leaderSkill, true);
		}
	}

	private void setupParties(MemoriaData memoriaData, LeaderSkill leaderSkill,
			boolean jobSkillFlag) {
		List<ItemData> wlist = mItemDataSet.getWeaponList();
		List<ItemData> malist = mItemDataSet.getMagicAccessoryList();

		// 引数のリーダースキルが自分のリーダースキルか判定
		boolean selfLeaderSkillFlag = isSelfLeaderSkill(memoriaData,
				leaderSkill);

		// 武器ループ
		for (ItemData w : wlist) {
			if (!w.isValid(memoriaData)) {
				continue;
			}

			// 魔法/アクセサリ1ループ
			FakePartySet partySet = new FakePartySet();
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

					// 自分のリーダースキルが発動可能か判定
					// ※ただし、自メモリアの条件に関する判定のみ。パーティ全体に関する判定は別途行う必要あり。
					if (selfLeaderSkillFlag) {
						Memoria memoria = new Memoria(memoriaData, w, ma1, ma2);
						if (!leaderSkill.validCondition(memoria)) {
							continue;
						}
					}

					// 適応度計算
					partySet.add(calcParty(memoriaData, w, ma1, ma2,
							leaderSkill, jobSkillFlag));

					mMaCount2++;
				}
				mMaCount1++;
			}

			// 魔法/アクセサリ組み合わせについて、枝刈りを行い、
			// 必要な組み合わせのパーティのみ保持する。
			addPartySet(partySet);

			mWCount++;
		}

		// TODO 武器も魔法/アクセサリと同様の考え方で、枝刈り可能。ただし、武器の数は少ないため優先度低。
	}

	private boolean isSelfLeaderSkill(MemoriaData memoriaData,
			LeaderSkill leaderSkill) {
		if (leaderSkill == null) {
			return false;
		}
		return LeaderSkill.equals(LeaderSkillFactory.get(memoriaData),
				leaderSkill);
	}

	private FakeParty calcParty(MemoriaData memoriaData, ItemData weapon,
			ItemData magicAccessory1, ItemData magicAccessory2,
			LeaderSkill leaderSkill, boolean jobSkillFlag) {
		Memoria memoria = new Memoria(memoriaData, weapon, magicAccessory1,
				magicAccessory2);
		memoria.setJobSkillFlag(jobSkillFlag);
		FakeParty party = new FakeParty(memoria);
		party.setLeaderSkill(leaderSkill);
		party.calcFitness(mFitnessCalculator);
		mCalcCount++;
		// mLogger.debug(mPartySet.size() + "," + party);
		return party;
	}

	private void addPartySet(TreeSet<FakeParty> partySet) {
		// リーダースキルなし、または 魔法/アクセサリ構成によらずリーダースキル適用可能であれば、
		// 魔法/アクセサリ組み合わせの枝刈り可能。

		// 適用度の降順でループ
		Iterator<FakeParty> ite = partySet.iterator();
		HashMap<ItemData, Integer> countMap = new HashMap<ItemData, Integer>();
		int twoOverCount = 0;
		while (ite.hasNext()) {
			FakeParty party = ite.next();
			ItemData[] accessories = party.getMemoria(0).getAccessories();
			boolean needFlag = true;

			// 1つの魔法/アクセサリにつき、適用度の高い最大7つの組み合わせがあればよい。
			// ※メモリア4体の魔法/アクセサリ数（8個）－当該魔法/アクセサリ（1個）＝7
			for (ItemData accessory : accessories) {
				Integer count = countMap.get(accessory);
				count = count == null ? 1 : count + 1;
				countMap.put(accessory, count);
				if (count == 2) {
					twoOverCount++;
				}
				if (count > 7) {
					needFlag = false;
				}
			}

			// 魔法/アクセサリ間の依存関係がない魔法/アクセサリが、適用度の高い最大7件あればよい。
			// ※メモリア3体の魔法/アクセサリ数（6件）＋当該メモリアの魔法/アクセサリ組み合わせ（1件）＝7
			if (twoOverCount > 7) {
				break;
			}

			// 当該ループにおける魔法/アクセサリの組み合わせが必要な場合のみ、パーティを保持する。
			if (needFlag) {
				mPartySet.add(party);
			}
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
