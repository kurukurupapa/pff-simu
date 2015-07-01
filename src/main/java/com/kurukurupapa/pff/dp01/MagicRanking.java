package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * 魔法順位付けクラス
 * 
 * 魔法を評価し、順位をつけます。 これにより、不要な魔法が判断しやすくなると思います。
 */
public class MagicRanking {
	/** ロガー */
	private Logger mLogger = Logger.getLogger(MagicRanking.class);

	private MemoriaDataSet mMemoriaDataSet;
	private ItemDataSet mItemDataSet;
	private Fitness mFitness;
	private Party mParty;
	private int mMemoriaIndex;
	private List<MagicFitness> mFitnessList;

	public void setParams(MemoriaDataSet memoriaDataSet, ItemDataSet itemDataSet) {
		setParams(memoriaDataSet, itemDataSet, null, null, 0);
	}

	public void setParams(MemoriaDataSet memoriaDataSet,
			ItemDataSet itemDataSet, Fitness fitness, Party party,
			int memoriaIndex) {
		mMemoriaDataSet = memoriaDataSet;
		mItemDataSet = itemDataSet;
		mFitness = fitness;
		mParty = party;
		mMemoriaIndex = memoriaIndex;

		// メモリアには、魔法を設定できる空きスロットが存在すること。
		if (mParty != null) {
			Validate.validState(mMemoriaIndex < mParty.getMemoriaList().size());
			Validate.validState(mParty.getMemoria(mMemoriaIndex)
					.getRemainAccessorySlot() >= 1);
		}
	}

	public void run() {
		if (mParty == null) {
			runWithoutParty();
		} else {
			runWithParty();
		}

		// 評価値の降順でソート
		Collections.sort(mFitnessList, new Comparator<MagicFitness>() {
			@Override
			public int compare(MagicFitness arg0, MagicFitness arg1) {
				// 降順
				return arg1.getFitness() - arg0.getFitness();
			}
		});
	}

	private void runWithoutParty() {
		// 魔法の一覧
		List<ItemData> magics = mItemDataSet.makeMagicList();

		// 魔法の評価
		mLogger.info("魔法数=" + magics.size() //
				+ ",メモリア数=" + mMemoriaDataSet.size() //
		);
		mFitnessList = new ArrayList<MagicFitness>();
		int count = 0;
		for (ItemData magic : magics) {
			MagicFitness maxFitness = new MagicFitness();
			maxFitness.setup(magic, mFitness);

			// 全てのメモリアに対して、当該魔法を評価し、
			// 最大評価値を当該魔法の評価とします。
			for (MemoriaData memoriaData : mMemoriaDataSet) {
				// NGな組み合わせをスキップ
				if (!magic.isValid(memoriaData)) {
					mLogger.debug("NG組み合わせ=" + magic + "+" + memoriaData);
					continue;
				}

				Memoria memoria = new Memoria(memoriaData);
				MagicFitness fitness = calcMagicFitness(magic, memoria);
				if (fitness.getFitness() > maxFitness.getFitness()) {
					maxFitness = fitness;
				}
				mLogger.debug(fitness);
			}

			mFitnessList.add(maxFitness);
			count++;
			mLogger.debug("魔法ループカウント=" + count + "/" + magics.size());
		}
	}

	private void runWithParty() {
		Validate.validState(mParty != null);

		// 魔法の一覧
		// パーティで使用中のアイテムは、ランキング対象から除外します。
		// TODO 同一名称の魔法が複数件ある場合の考慮が不足しています。
		ItemDataSet itemDataSet = mItemDataSet.clone();
		for (Memoria e : mParty.getMemoriaList()) {
			itemDataSet.removeMagicOrAccessory(e.getAccessories());
		}
		List<ItemData> magics = itemDataSet.makeMagicList();

		// 対象メモリア
		Memoria memoria = mParty.getMemoria(mMemoriaIndex);

		// 魔法の評価
		mLogger.info("魔法数=" + magics.size() + ",対象メモリア=" + memoria);
		mFitnessList = new ArrayList<MagicFitness>();
		for (ItemData magic : magics) {
			// NGな組み合わせをスキップ
			if (!memoria.validAccessoryData(magic)) {
				mLogger.debug("NG組み合わせ=" + magic + "+" + memoria.getName());
				continue;
			}

			MagicFitness fitness = calcMagicFitness(magic, memoria);
			mFitnessList.add(fitness);
		}
	}

	private MagicFitness calcMagicFitness(ItemData magic, Memoria memoria) {
		MagicFitness fitness = new MagicFitness();
		fitness.setup(magic, mFitness);
		fitness.calc(memoria);
		return fitness;
	}

	public List<MagicFitness> getFitnesses() {
		return mFitnessList;
	}
}
