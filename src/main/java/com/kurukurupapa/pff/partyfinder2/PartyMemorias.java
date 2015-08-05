package com.kurukurupapa.pff.partyfinder2;

import java.util.List;

import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;
import com.kurukurupapa.pff.dp01.FitnessCalculator;
import com.kurukurupapa.pff.dp01.MemoriaFitness;
import com.kurukurupapa.pff.dp01.MemoriaRanking;
import com.kurukurupapa.pff.dp01.Party;

/**
 * パーティメモリア集合クラス
 */
public class PartyMemorias {
	private MemoriaDataSet mMemoriaDataSet;
	private FitnessCalculator mFitnessCalculator;
	private int mIndexes[] = new int[4];

	/**
	 * メモリア適応度結果リスト<br>
	 * 適応度の高い順に並んでいます。
	 */
	private List<MemoriaFitness> mMemoriaFitnesses;

	public PartyMemorias(MemoriaDataSet memoriaDataSet) {
		mMemoriaDataSet = memoriaDataSet;
	}

	public void setup(ItemDataSet itemDataSet,
			FitnessCalculator fitnessCalculator) {
		mFitnessCalculator = fitnessCalculator;


		// インデックス
		mIndexes[0] = -1;
		mIndexes[1] = -1;
		mIndexes[2] = -1;
		mIndexes[3] = -1;
	}

	public void initNextMemoriaIndex(int position) {
		mIndexes[position] = -1;
	}

	/**
	 * 次に計算を試みるべきメモリアを取得します。
	 * 
	 * @param position
	 *            変更対象のパーティ内メモリア位置。ゼロ始まり。
	 * @param party
	 *            計算途中における最大適応度のパーティ
	 * @return メモリア
	 */
	public MemoriaData getNextMemoria(int position, Party party) {
		MemoriaData result = null;

		// 変更対象メモリアのみの適応度
		int beforeMemoriaFitness = 0;
		if (party != null) {
			// 引数のパーティの適応度
			int beforePartyFitness = party.getFitness();

			// 変更対象メモリア以外の適応度
			Party tmp = party.clone();
			tmp.remove(position);
			tmp.calcFitness(mFitnessCalculator);
			int beforeBaseFitness = tmp.getFitness();

			// 変更対象メモリアのみの適応度
			beforeMemoriaFitness = beforePartyFitness - beforeBaseFitness;
		}

		// パーティの適応度を上げる可能性のあるメモリアを探索
		while (true) {
			mIndexes[position]++;
			if (mIndexes[position] >= mMemoriaFitnesses.size()) {
				// 対象メモリアなし
				break;
			}

			// 
			for (int i = 0; i < position - 1; i++) {
				if (mIndexes[position] == mIndexes[position]) {
					break;
				}
			}

			MemoriaFitness memoriaFitness = mMemoriaFitnesses
					.get(mIndexes[position]);
			int afterMemoriaFitness = memoriaFitness.getValue();
			if (afterMemoriaFitness > beforeMemoriaFitness) {
				result = memoriaFitness.getMemoria().getMemoriaData();
				break;
			}
		}

		return result;
	}
}
