package com.kurukurupapa.pff.partyfinder2;

import java.util.List;

import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.MemoriaFitness;
import com.kurukurupapa.pff.dp01.Party;

/**
 * 次メモリアクラス
 */
public class NextMemoria {

	private FitnessCalculator mFitnessCalculator;

	/**
	 * 当オブジェクトで対象とするパーティ内のメモリア位置
	 */
	private int mPosition;

	/**
	 * メモリア適応度結果リスト<br>
	 * 適応度の高い順に並んでいます。
	 */
	private List<MemoriaFitness> mMemoriaFitnesses;

	/**
	 * メモリアリスト内の現在探索インデックス
	 */
	private int mIndex = -1;

	public NextMemoria(FitnessCalculator fitnessCalculator, int position,
			List<MemoriaFitness> memoriaFitnesses) {
		mFitnessCalculator = fitnessCalculator;
		mPosition = position;
		mMemoriaFitnesses = memoriaFitnesses;
	}

	public void reset() {
		mIndex = -1;
	}

	public MemoriaData next(Party currentParty, Party maxParty) {
		MemoriaData memoria = null;

		// 変更対象メモリアのみの適応度を算出
		int beforeMemoriaFitness = 0;
		if (currentParty != null) {
			// 引数のパーティの適応度
			int beforePartyFitness = currentParty.getFitness();

			// 変更対象メモリア以外の適応度
			int beforeBaseFitness;
			if (currentParty.size() > mPosition) {
				Party tmp = currentParty.clone();
				tmp.remove(mPosition);
				tmp.calcFitness(mFitnessCalculator);
				beforeBaseFitness = tmp.getFitness();
			} else {
				beforeBaseFitness = beforePartyFitness;
			}

			// 変更対象メモリアのみの適応度
			beforeMemoriaFitness = beforePartyFitness - beforeBaseFitness;
		}

		// パーティの適応度を上げる可能性のあるメモリアを探索
		while (true) {
			mIndex++;
			if (mIndex >= mMemoriaFitnesses.size()) {
				// 対象メモリアなし
				break;
			}

			// 対象メモリア
			MemoriaFitness memoriaFitness = mMemoriaFitnesses.get(mIndex);
			MemoriaData tmp = memoriaFitness.getMemoria().getMemoriaData();

			// 既にパーティに含まれている場合、次のメモリアを探索
			if (currentParty.contains(tmp)) {
				continue;
			}

			// 適応度の確認
			int afterMemoriaFitness = memoriaFitness.getValue();
			if (afterMemoriaFitness > beforeMemoriaFitness) {
				// 適応度が上がる可能性があれば、当該メモリアを返却対象とします。
				memoria = tmp;
			} else {
				// 適応度が上がる可能性がなければ、
				// 以降のメモリアも上がる可能性がないため、次メモリアなし（null）を返却対象とします。
				memoria = null;
			}

			// 探索終了
			break;
		}

		return memoria;
	}

}
