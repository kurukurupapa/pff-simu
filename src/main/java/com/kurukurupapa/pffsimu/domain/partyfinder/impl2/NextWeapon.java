package com.kurukurupapa.pffsimu.domain.partyfinder.impl2;

import java.util.List;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.ItemFitness;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;
import com.kurukurupapa.pffsimu.domain.party.Party;

public class NextWeapon {
	private int mPosition;
	private List<ItemFitness> mWeaponFitnesses;
	private int mIndex = -1;
	private FitnessCalculator mFitnessCalculator;

	public NextWeapon(int position, List<ItemFitness> weaponFitnesses,
			FitnessCalculator fitnessCalculator) {
		mPosition = position;
		mWeaponFitnesses = weaponFitnesses;
		mFitnessCalculator = fitnessCalculator;
	}

	public void reset() {
		mIndex = -1;
	}

	public ItemData next(Party currentParty, Party maxParty) {
		ItemData item = null;

		// 対象メモリア
		Memoria currentMemoria = currentParty.getMemoria(mPosition);
		MemoriaData currentMemoriaData = currentMemoria.getMemoriaData();

		// 変更対象アイテムのみの適応度を算出
		int beforeFitness = -1;
		if (currentParty != null && currentMemoria.isWeapon()) {
			// 引数のパーティの適応度
			int beforePartyFitness = currentParty.getFitness();

			// 変更対象アイテム以外の適応度
			int beforeBaseFitness;
			Party tmp = currentParty.clone();
			tmp.getMemoria(mPosition).clearWeapon();
			tmp.calcFitness(mFitnessCalculator);
			beforeBaseFitness = tmp.getFitness();

			// 変更対象アイテムのみの適応度
			beforeFitness = beforePartyFitness - beforeBaseFitness;
		}

		// パーティの適応度を上げる可能性のあるアイテムを探索
		while (true) {
			mIndex++;
			if (mIndex >= mWeaponFitnesses.size()) {
				// 対象なし
				break;
			}

			// 対象アイテム
			ItemFitness itemFitness = mWeaponFitnesses.get(mIndex);
			ItemData tmp = itemFitness.getItem();

			// 対象メモリアに装備できなければ、次のアイテムを探索
			if (!tmp.isValid(currentMemoriaData)) {
				continue;
			}

			// 既にパーティに含まれている場合、次のアイテムを探索
			if (currentParty.contains(tmp)) {
				continue;
			}

			// 適応度の確認
			int afterFitness = itemFitness.getFitness();
			if (afterFitness > beforeFitness) {
				// 適応度が上がる可能性があれば、当該アイテムを返却対象とします。
				item = tmp;
			} else {
				// 適応度が上がる可能性がなければ、
				// 以降のアイテムも上がる可能性がないため、次アイテムなし（null）を返却対象とします。
				item = null;
			}

			// 探索終了
			break;
		}

		return item;
	}
}
