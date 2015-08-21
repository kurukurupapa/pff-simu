package com.kurukurupapa.pff.partyfinder2;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.AppException;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.Party;

/**
 * 最適パーティ計算クラス
 * <p>
 * メモリア1体に対するアイテムの組み合わせを全探索で計算し、パーティ構成（全探索したメモリアの組み合わせ）を優先順位をつけて探索します。
 * </p>
 * <p>
 * ループの回し方は、基本的に（メモリア1 × 武器 × 魔法/アクセサリ1 × 魔法/アクセサリ2） + （メモリア2 × 武器 × 魔法/アクセサリ1 ×
 * 魔法/アクセサリ2） ＋ ・・・ で、さらに工夫しています。
 * </p>
 */
public class PartyFinder2d {
	// パーティの適用度は、各メモリアの適応度の合計とは、多少異なる場合がある。
	// メモリア適応度合計から、最大何倍になるかを定義しておく。
	private static final float PARTY_FACTOR = 1.05f;

	private static Logger mLogger = Logger.getLogger(PartyFinder2d.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private FitnessCalculator mFitnessCalculator;
	private MemoriaItemCombinations mCombinations;
	private Party mBestParty;
	private long mCalcCount;
	private long mMCount1;
	private long mMCount2;
	private long mMCount3;
	private long mMCount4;

	public PartyFinder2d(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, FitnessCalculator fitnessCalculator) {
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

		int mcount = mMemoriaDataSet.size();
		int wcount = mItemDataSet.getWeaponList().size();
		int macount = mItemDataSet.getMagicAccessoryList().size();
		mLogger.debug("メモリア数=" + mcount + ",武器数=" + wcount + ",魔法/アクセサリ数="
				+ macount);

		// 準備
		mBestParty = null;
		mCalcCount = 0;
		mMCount1 = 0;
		mMCount2 = 0;
		mMCount3 = 0;
		mMCount4 = 0;

		// 各メモリアの各アイテム全組み合わせ求めます。
		// 適応度の降順でソートされる前提です。
		mCombinations = new MemoriaItemCombinations(mMemoriaDataSet,
				mItemDataSet, mFitnessCalculator);
		mCombinations.setup();
		// mLogger.debug("mCombinations=\n" + mCombinations.toDebugStr());

		if (maxMemorias == 1) {
			calc1();
		} else if (maxMemorias == 2) {
			calc2();
		} else if (maxMemorias == 4) {
			calc4();
		} else {
			throw new AppException("未実装");
		}

		if (mBestParty == null) {
			mBestParty = new Party();
		}

		mLogger.debug("妥当性チェック回数=" + mCalcCount + "(M1:" + mMCount1 + ",M2:"
				+ mMCount2 + ",M3:" + mMCount3 + ",M4:" + mMCount4 + ")");
	}

	private void calc1() {
		final int num = 1;

		// 全組み合わせのうち、適応度の高い組み合わせのみを使って、妥当なパーティが作れるとよい。
		// 組み合わせの上位1つから始めて、1件ずつ増やしていく。
		Party maxParty = null;
		for (int m1 = num - 1; m1 < mCombinations.size() - (num - 1); m1++) {
			// mLogger.debug("ループカウント=" + i + "/" + mCombinations.size()
			// + "(M1:" + mMCount1 + ",M2:" + mMCount2 + ",M3:" + mMCount3
			// + ",M4:" + mMCount4 + ")");

			// ループ終了判定
			if (!isProcess(maxParty, m1)) {
				break;
			}

			// 妥当性チェック＆最大適応度パーティ判定
			maxParty = calcMaxValidParty(maxParty, m1);

			mMCount1++;
			// mLogger.debug("maxParty=" + maxParty);
		}

		mBestParty = maxParty;
	}

	/**
	 * メモリア2体のパーティ計算
	 */
	private void calc2() {
		final int num = 2;

		// 全組み合わせのうち、適応度の高い組み合わせのみを使って、妥当なパーティが作れるとよい。
		// 組み合わせの上位2つから始めて、1件ずつ増やしていく。
		Party maxParty = null;
		for (int i = num - 1; i < mCombinations.size() - (num - 1); i++) {
			// mLogger.debug("ループカウント=" + i + "/" + mCombinations.size()
			// + "(M1:" + mMCount1 + ",M2:" + mMCount2 + ",M3:" + mMCount3
			// + ",M4:" + mMCount4 + ")");

			// メモリア2
			int m2 = i;
			// ループ終了判定
			if (!isProcess(maxParty, 0, m2)) {
				break;
			}

			// メモリア1ループ
			for (int m1 = 0; m1 < m2; m1++) {
				// ループ終了判定
				if (!isProcess(maxParty, m1, m2)) {
					break;
				}

				// 妥当性チェック＆最大適応度パーティ判定
				maxParty = calcMaxValidParty(maxParty, m1, m2);

				mMCount1++;
			}
			mMCount2++;
			// mLogger.debug("maxParty=" + maxParty);
		}

		mBestParty = maxParty;
	}

	/**
	 * メモリア4体のパーティ計算
	 */
	private void calc4() {
		final int num = 4;

		// 全組み合わせのうち、適応度の高い組み合わせのみを使って、妥当なパーティが作れるとよい。
		// 組み合わせの上位4つから始めて、1件ずつ増やしていく。
		Party maxParty = null;
		for (int i = num - 1; i < mCombinations.size() - (num - 1); i++) {
			// mLogger.debug("ループカウント=" + i + "/" + mCombinations.size()
			// + "(M1:" + mMCount1 + ",M2:" + mMCount2 + ",M3:" + mMCount3
			// + ",M4:" + mMCount4 + ")");

			// メモリア4
			int m4 = i;
			// ループ終了判定
			if (!isProcess(maxParty, 0, 0, 0, m4)) {
				break;
			}

			// メモリア1ループ
			for (int m1 = 0; m1 < m4 - 2; m1++) {
				// ループ終了判定
				if (!isProcess(maxParty, m1, m1, m1, m4)) {
					break;
				}

				// メモリア2ループ
				for (int m2 = m1 + 1; m2 < m4 - 1; m2++) {
					// ループ終了判定
					if (!isProcess(maxParty, m1, m2, m2, m4)) {
						break;
					}

					// メモリア3ループ
					for (int m3 = m2 + 1; m3 < m4; m3++) {
						// ループ終了判定
						if (!isProcess(maxParty, m1, m2, m3, m4)) {
							break;
						}

						// 妥当性チェック＆最大適応度パーティ判定
						maxParty = calcMaxValidParty(maxParty, m1, m2, m3, m4);

						mMCount3++;
					}
					mMCount2++;
				}
				mMCount1++;
			}
			mMCount4++;
			// mLogger.debug("maxParty=" + maxParty);
		}

		mBestParty = maxParty;
	}

	/**
	 * 今回パーティの処理が必要であるか判定します。
	 * 
	 * @param maxParty
	 *            前回までの最大適応度パーティ。null可能。
	 * @param indexes
	 *            今回パーティのメモリアのインデックス配列。
	 * @return 今回パーティ処理必要の場合true。
	 */
	private boolean isProcess(Party maxParty, int[] indexes) {
		// まだ最大適応度パーティ候補が見つかっていなければ、処理必要。
		if (maxParty == null) {
			return true;
		}

		// 最大適応度パーティ候補が見つかっている場合、
		// 当該パーティの適応度が候補の適応度より高ければ、当該パーティの処理必要。
		int value = 0;
		for (int i : indexes) {
			value += mCombinations.get(i).getValue();
		}
		return maxParty.getFitness() < value * PARTY_FACTOR;
	}

	private boolean isProcess(Party maxParty, int m1) {
		return isProcess(maxParty, new int[] { m1 });
	}

	private boolean isProcess(Party maxParty, int m1, int m2) {
		return isProcess(maxParty, new int[] { m1, m2 });
	}

	private boolean isProcess(Party maxParty, int m1, int m2, int m3, int m4) {
		return isProcess(maxParty, new int[] { m1, m2, m3, m4 });
	}

	/**
	 * 引数のメモリアからパーティを作成し、妥当であれば、前回までの最大適応度パーティと比較し、適応度が大きいパーティを返却します。
	 * 
	 * @param maxParty
	 *            前回までの最大適応度パーティ。null可能。
	 * @param indexes
	 *            今回パーティのメモリアのインデックス配列
	 * @return 最大適応度パーティ。nullの場合あり。
	 */
	private Party calcMaxValidParty(Party maxParty, int[] indexes) {
		FakeParty2 party = new FakeParty2();
		for (int i : indexes) {
			party.add(mCombinations.get(i).getMemoria());
		}
		if (party.valid()) {
			party.calcFitness(mFitnessCalculator);
			maxParty = max(maxParty, party);
			// mLogger.debug("有効なパーティ=" + party);
		}
		mCalcCount++;
		return maxParty;
	}

	private Party calcMaxValidParty(Party maxParty, int m1) {
		return calcMaxValidParty(maxParty, new int[] { m1 });
	}

	private Party calcMaxValidParty(Party maxParty, int m1, int m2) {
		return calcMaxValidParty(maxParty, new int[] { m1, m2 });
	}

	private Party calcMaxValidParty(Party maxParty, int m1, int m2, int m3,
			int m4) {
		return calcMaxValidParty(maxParty, new int[] { m1, m2, m3, m4 });
	}

	private Party max(Party p1, Party p2) {
		if (p1 == null || p1.getFitness() < p2.getFitness()) {
			return p2;
		} else {
			return p1;
		}
	}

	public Party getParty() {
		return mBestParty;
	}

}
