package com.kurukurupapa.pff.partyfinder2;

import java.util.List;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.ItemFitness;
import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;

public class NextMagicAccessory {
	public static final int INIT_INDEX = -1;

	private int mMemoriaPosition;
	private int mSlotPosition;
	private List<ItemFitness> mMagicAccessoryFitnesses;
	private FitnessCalculator mFitnessCalculator;
	private int mIndex = INIT_INDEX;

	public NextMagicAccessory(int memoriaPosition, int slotPosition,
			List<ItemFitness> magicAccessoryFitnesses,
			FitnessCalculator fitnessCalculator) {
		mMemoriaPosition = memoriaPosition;
		mSlotPosition = slotPosition;
		mMagicAccessoryFitnesses = magicAccessoryFitnesses;
		mFitnessCalculator = fitnessCalculator;
	}

	public NextMagicAccessory(int memoriaPosition, int slotPosition, int index,
			List<ItemFitness> magicAccessoryFitnesses,
			FitnessCalculator fitnessCalculator) {
		this(memoriaPosition, slotPosition, magicAccessoryFitnesses,
				fitnessCalculator);
		mIndex = index;
	}

	public void reset() {
		mIndex = INIT_INDEX;
	}

	public ItemData next(Party currentParty, Party maxParty) {
		ItemData item = null;

		// 対象メモリア
		Memoria currentMemoria = currentParty.getMemoria(mMemoriaPosition);
		MemoriaData currentMemoriaData = currentMemoria.getMemoriaData();

		// 変更対象アイテムのみの適応度を算出
		int beforeFitness = 0;
		if (currentParty != null) {
			// 引数のパーティの適応度
			int beforePartyFitness = currentParty.getFitness();

			// 変更対象アイテム以外の適応度
			int beforeBaseFitness;
			if (currentParty.size() > mMemoriaPosition
					&& currentParty.getMemoria(mMemoriaPosition)
							.getNumAccessories() > mSlotPosition) {
				Party tmp = currentParty.clone();
				tmp.getMemoria(mMemoriaPosition).removeAccessory(mSlotPosition);
				tmp.calcFitness(mFitnessCalculator);
				beforeBaseFitness = tmp.getFitness();
			} else {
				beforeBaseFitness = beforePartyFitness;
			}

			// 変更対象アイテムのみの適応度
			beforeFitness = beforePartyFitness - beforeBaseFitness;
		}

		// パーティの適応度を上げる可能性のあるアイテムを探索
		while (true) {
			mIndex++;
			if (mIndex >= mMagicAccessoryFitnesses.size()) {
				// 対象なし
				break;
			}

			// 対象アイテム
			ItemFitness itemFitness = mMagicAccessoryFitnesses.get(mIndex);
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

	public int getIndex() {
		return mIndex;
	}
}
