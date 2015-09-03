package com.kurukurupapa.pff.partyfinder2;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.AppException;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.FitnessValue;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.MemoriaFitness;
import com.kurukurupapa.pff.dp01.Party;
import com.kurukurupapa.pff.partyfinder.PartyFinder;

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
public class PartyFinder2d implements PartyFinder {
	/** 処理タイムアウト時間 */
	private static final long TIMEOUT = 3 * 60 * 1000;

	private static Logger mLogger = Logger.getLogger(PartyFinder2d.class);

	public boolean mDebug;

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
	private long mStart;
	private long mEnd;
	private long mDebugCombinationsLoopTime;

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
		mLogger.info("計算開始 メモリア数=" + mcount + ",武器数=" + wcount + ",魔法/アクセサリ数="
				+ macount);

		// 準備
		mBestParty = null;
		mCalcCount = 0;
		mMCount1 = 0;
		mMCount2 = 0;
		mMCount3 = 0;
		mMCount4 = 0;
		mStart = System.currentTimeMillis();

		// 各メモリアの各アイテム全組み合わせ求めます。
		// 適応度の降順でソートされる前提です。
		mCombinations = new MemoriaItemCombinations(mMemoriaDataSet,
				mItemDataSet, mFitnessCalculator);
		mCombinations.setup();
		debug("mCombinations=\n" + mCombinations.toDebugStr());

		if (maxMemorias == 1) {
			calc1();
		} else if (maxMemorias == 2) {
			calc2();
		} else if (maxMemorias == 3) {
			calc3();
		} else if (maxMemorias == 4) {
			calc4();
		} else {
			throw new AppException("未実装");
		}
		if (mBestParty == null) {
			mBestParty = new Party();
		}

		mEnd = System.currentTimeMillis();
		long minutes = (mEnd - mStart) / 1000 / 60;
		long seconds = (mEnd - mStart) / 1000 % 60;
		long millSeconds = (mEnd - mStart) % 1000;
		mLogger.info("計算終了 メモリア数=" + mcount + ",武器数=" + wcount + ",魔法/アクセサリ数="
				+ macount + ",mCombinations計算回数=" + mCombinations.mCalcCount
				+ ",当クラス計算回数=" + mCalcCount + "(M1:" + mMCount1 + ",M2:"
				+ mMCount2 + ",M3:" + mMCount3 + ",M4:" + mMCount4 + ")"
				+ ",処理時間=" + minutes + "分" + seconds + "秒" + millSeconds
				+ "ミリ秒");
	}

	private void calc1() {
		final int num = 1;

		// 全組み合わせのうち、適応度の高い組み合わせのみを使って、妥当なパーティが作れるとよい。
		// 組み合わせの上位1つから始めて、1件ずつ増やしていく。
		Party maxParty = null;
		for (int m1 = num - 1; m1 < mCombinations.size() - (num - 1); m1++) {
			debugCombinationsLoop(m1, maxParty);

			// メモリア1ループ終了判定
			if (!isProcess(maxParty, m1)) {
				debug("メモリア1ループ終了 m1=" + m1);
				break;
			}

			// 妥当性チェック＆最大適応度パーティ判定
			maxParty = calcMaxValidParty(maxParty, m1);

			mMCount1++;
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
		for (int m2 = num - 1; m2 < mCombinations.size() - (num - 1); m2++) {
			debugCombinationsLoop(m2, maxParty);
			// メモリア2ループ終了判定
			if (!isProcess(maxParty, 0, m2)) {
				debug("メモリア2ループ終了 m2=" + m2);
				break;
			}

			// メモリア1ループ
			for (int m1 = 0; m1 < m2; m1++) {
				if (!isProcess(maxParty, m1, m2)) {
					break;
				}

				// 妥当性チェック＆最大適応度パーティ判定
				maxParty = calcMaxValidParty(maxParty, m1, m2);

				mMCount1++;
			}
			mMCount2++;
		}

		mBestParty = maxParty;
	}

	/**
	 * メモリア3体のパーティ計算
	 */
	private void calc3() {
		final int num = 3;

		// 全組み合わせのうち、適応度の高い組み合わせのみを使って、妥当なパーティが作れるとよい。
		// 組み合わせの上位3つから始めて、1件ずつ増やしていく。
		Party maxParty = null;
		for (int m3 = num - 1; m3 < mCombinations.size() - (num - 1); m3++) {
			debugCombinationsLoop(m3, maxParty);
			// メモリア3ループ終了判定
			if (!isProcess(maxParty, 0, 0, 0, m3)) {
				debug("メモリア3ループ終了 m3=" + m3);
				break;
			}

			// メモリア1ループ
			for (int m1 = 0; m1 < m3 - 1; m1++) {
				if (!isProcess(maxParty, m1, m1, m1, m3)) {
					break;
				}

				// メモリア2ループ
				for (int m2 = m1 + 1; m2 < m3; m2++) {
					if (!isProcess(maxParty, m1, m2, m2, m3)) {
						break;
					}

					// 妥当性チェック＆最大適応度パーティ判定
					maxParty = calcMaxValidParty(maxParty, m1, m2, m3);

					mMCount2++;
				}
				mMCount1++;
			}
			mMCount3++;
		}

		mBestParty = maxParty;
	}

	/**
	 * メモリア4体のパーティ計算
	 * 
	 * 動的計画法に似た方法で、計算量を削減してみました。
	 */
	private void calc4() {
		final int num = 4;

		// メモデータ
		// 適応度の降順になる。
		FakePartySet memo2 = new FakePartySet();
		FakePartySet memo3 = new FakePartySet();

		// 全組み合わせのうち、適応度の高い組み合わせのみを使って、妥当なパーティが作れるとよい。
		// 組み合わせの上位4つから始めて、1件ずつ増やしていく。
		Party maxParty = null;
		for (int m4 = num - 1; m4 < mCombinations.size() - (num - 1); m4++) {
			debugCombinationsLoop(m4, maxParty, memo2, memo3);
			checkTimeout();
			// メモリア4ループ終了判定
			if (!isProcess(maxParty, 0, 1, 2, m4)) {
				debug("メモリア4ループ終了 m4=" + m4);
				break;
			}

			// メモリア2体までの暫定パーティ作成
			int m2 = m4 - 2;
			for (int m1 = 0; m1 < m2; m1++) {
				if (!isProcess(maxParty, m1, m2, m2, m4)) {
					debug("メモリア2体ループ終了 m1=" + m1 + ",m2=" + m2 + ",m4=" + m4);
					break;
				}

				// 妥当性チェック
				FakeParty party = validSubParty(m1, m2);
				if (party != null) {
					memo2.add(party);
				}
			}

			// メモリア3体までの暫定パーティ作成
			int m3 = m4 - 1;
			for (FakeParty e : memo2) {
				if (!isProcess(maxParty, e, m3, m4)) {
					debug("メモリア3体ループ終了 party=" + e + ",m4=" + m4);
					break;
				}

				// 妥当性チェック
				FakeParty party = validSubParty(e, m3);
				if (party != null) {
					memo3.add(party);
				}
			}

			// メモリア4体のパーティ
			for (FakeParty e : memo3) {
				if (!isProcess(maxParty, e, m4)) {
					debug("メモリア4体ループ終了 party=" + e + ",m4=" + m4);
					break;
				}

				// 妥当性チェック＆最大適応度パーティ判定
				maxParty = calcMaxValidParty(maxParty, e, m4);

				// TODO maxPartyがnull以外、または値が変わったら、memo2,memo3をクリーニングしては？
			}

			mMCount4++;
		}

		mBestParty = maxParty;
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

	private boolean isProcess(Party maxParty, FakeParty party, int m4) {
		List<MemoriaFitness> fitnesses = party.getFitnessObj()
				.getMemoriaFitnesses();
		Validate.isTrue(fitnesses.size() == 3);

		return isProcess(maxParty, new MemoriaFitness[] { fitnesses.get(0),
				fitnesses.get(1), fitnesses.get(2), mCombinations.get(m4) });
	}

	private boolean isProcess(Party maxParty, FakeParty party, int m3, int m4) {
		List<MemoriaFitness> fitnesses = party.getFitnessObj()
				.getMemoriaFitnesses();
		Validate.isTrue(fitnesses.size() == 2);

		return isProcess(maxParty,
				new MemoriaFitness[] { fitnesses.get(0), fitnesses.get(1),
						mCombinations.get(m3), mCombinations.get(m4) });
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
		Validate.inclusiveBetween(1, 4, indexes.length);

		List<MemoriaFitness> list = new ArrayList<MemoriaFitness>();
		for (int i : indexes) {
			list.add(mCombinations.get(i));
		}
		return isProcess(maxParty, list.toArray(new MemoriaFitness[] {}));
	}

	/**
	 * 今回パーティの処理が必要であるか判定します。
	 * 
	 * @param maxParty
	 *            前回までの最大適応度パーティ。null可能。
	 * @param memoriaFitnesses
	 *            今回パーティのメモリア適応度結果。
	 * @return 今回パーティ処理必要の場合true。
	 */
	private boolean isProcess(Party maxParty, MemoriaFitness[] memoriaFitnesses) {
		Validate.inclusiveBetween(1, 4, memoriaFitnesses.length);

		// まだ最大適応度パーティ候補が見つかっていなければ、処理必要。
		if (maxParty == null) {
			// debug("isProcess result=true,maxParty=null");
			return true;
		}

		// 最大適応度パーティ候補が見つかっている場合、
		// 当該パーティの適応度が候補の適応度より高ければ、当該パーティの処理必要。
		FitnessValue fitnessValue = new FitnessValue();
		for (MemoriaFitness e : memoriaFitnesses) {
			fitnessValue.add(e);
		}
		boolean result = maxParty.getFitness() < fitnessValue.getValue();

		if (!result) {
			debug("isProcess result=" + result + ",maxParty=" + maxParty
					+ ",fitnessValue=" + fitnessValue);
		}
		return result;
	}

	private FakeParty validSubParty(int m1, int m2) {
		return validSubParty(
				new Memoria[] { mCombinations.get(m1).getMemoria() },
				mCombinations.get(m2).getMemoria());
	}

	private FakeParty validSubParty(FakeParty party, int m3) {
		Validate.isTrue(party.getMemoriaList().size() == 2);

		return validSubParty(
				new Memoria[] { party.getMemoria(0), party.getMemoria(1) },
				mCombinations.get(m3).getMemoria());
	}

	/**
	 * ある暫定的なパーティに対して、メモリアを追加し、新たな暫定的なパーティを作成する。
	 * 暫定的なパーティでは、パーティ構成として妥当でないものが含まれている可能性あり。
	 * たとえば、メンバーに存在しないメモリアのリーダースキルが含まれている可能性がある。
	 * 
	 * @param memorias
	 *            基本となるパーティを表すメモリア配列
	 * @param memoria
	 *            追加するメモリア
	 * @return パーティ
	 */
	private FakeParty validSubParty(Memoria[] memorias, Memoria memoria) {
		Validate.inclusiveBetween(1, 3, memorias.length);

		FakeParty tmp = new FakeParty();
		for (Memoria e : memorias) {
			tmp.add(e);
		}
		if (tmp.valid(memoria)) {
			tmp.add(memoria);
			tmp.calcFitness(mFitnessCalculator);
			return tmp;
		}
		return null;
	}

	private Party calcMaxValidParty(Party maxParty, int m1) {
		return calcMaxValidParty(maxParty, new int[] { m1 });
	}

	private Party calcMaxValidParty(Party maxParty, int m1, int m2) {
		return calcMaxValidParty(maxParty, new int[] { m1, m2 });
	}

	private Party calcMaxValidParty(Party maxParty, int m1, int m2, int m3) {
		return calcMaxValidParty(maxParty, new int[] { m1, m2, m3 });
	}

	private Party calcMaxValidParty(Party maxParty, FakeParty party, int m4) {
		List<MemoriaFitness> fitnesses = party.getFitnessObj()
				.getMemoriaFitnesses();
		Validate.isTrue(fitnesses.size() == 3);

		List<MemoriaFitness> list = new ArrayList<MemoriaFitness>();
		for (MemoriaFitness e : fitnesses) {
			list.add(e);
		}
		list.add(mCombinations.get(m4));
		return calcMaxValidParty(maxParty,
				list.toArray(new MemoriaFitness[] {}));
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
		Validate.inclusiveBetween(1, 4, indexes.length);

		List<MemoriaFitness> list = new ArrayList<MemoriaFitness>();
		for (int i : indexes) {
			list.add(mCombinations.get(i));
		}
		return calcMaxValidParty(maxParty,
				list.toArray(new MemoriaFitness[] {}));
	}

	/**
	 * 引数のメモリアからパーティを作成し、妥当であれば、前回までの最大適応度パーティと比較し、適応度が大きいパーティを返却します。
	 * 
	 * @param maxParty
	 *            前回までの最大適応度パーティ。null可能。
	 * @param memoriaFitnesses
	 *            今回パーティのメモリア適応度結果
	 * @return 最大適応度パーティ。nullの場合あり。
	 */
	private Party calcMaxValidParty(Party maxParty,
			MemoriaFitness[] memoriaFitnesses) {
		Validate.inclusiveBetween(1, 4, memoriaFitnesses.length);

		FakeParty party = new FakeParty();
		for (MemoriaFitness e : memoriaFitnesses) {
			party.add(e.getMemoria());
		}

		if (party.valid()) {
			party.calcFitness(mFitnessCalculator);
			maxParty = Party.max(maxParty, party);
			debug("有効パーティ=" + party);
		} else {
			// debug("無効パーティ=" + party);
		}

		mCalcCount++;
		return maxParty;
	}

	public Party getParty() {
		return mBestParty;
	}

	private void checkTimeout() {
		if (System.currentTimeMillis() - mStart > TIMEOUT) {
			throw new AppException("妥当な時間で処理が終わりませんでした。");
		}
	}

	public void debugCombinationsLoop(int index, Party maxParty) {
		debugCombinationsLoop(index, maxParty, null, null);
	}

	public void debugCombinationsLoop(int index, Party maxParty,
			FakePartySet memo2, FakePartySet memo3) {
		long time = System.currentTimeMillis();
		if (time - mDebugCombinationsLoopTime > 10 * 1000) {
			mDebugCombinationsLoopTime = time;

			StringBuilder sb = new StringBuilder();
			sb.append("ループカウント=" + index + "/" + mCombinations.size() + "(M1:"
					+ mMCount1 + ",M2:" + mMCount2 + ",M3:" + mMCount3 + ",M4:"
					+ mMCount4 + ")");
			if (memo2 != null) {
				sb.append(",memo2=" + memo2.size() + "件");
			}
			if (memo3 != null) {
				sb.append(",memo3=" + memo3.size() + "件");
			}
			sb.append(",maxPartyFitness="
					+ (maxParty == null ? "null" : maxParty.getFitness()));
			mLogger.debug(sb.toString());
		}
	}

	public void debugFakeParty(String msg, int[] indexes) {
		if (mDebug) {
			FitnessValue fitnessValue = new FitnessValue();
			for (int i : indexes) {
				fitnessValue.add(mCombinations.get(i));
			}
			mLogger.debug(msg + fitnessValue);
		}
	}

	public void debug(String msg) {
		if (mDebug) {
			mLogger.debug(msg);
		}
	}
}
