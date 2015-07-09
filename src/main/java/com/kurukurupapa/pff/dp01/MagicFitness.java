package com.kurukurupapa.pff.dp01;

import org.apache.commons.lang3.Validate;

/**
 * 武器適応度クラス
 */
public class MagicFitness extends ItemFitness {

	/**
	 * 適応度を計算します。
	 * 
	 * @param memoria
	 *            魔法装備前のメモリア
	 */
	public void calc(Memoria memoria) {
		// 当該魔法を装備するため、メモリアのスロットに空きがあること。
		Validate.validState(memoria.getRemainAccessorySlot() >= 1);

		// 魔法装備後のメモリア
		mMemoria = memoria.clone();
		mMemoria.addAccessory(mItem);

		// 当該魔法の適応度
		FitnessCalculator fitnessCalculator = createFitnessCalculator();
		calcDifference(fitnessCalculator, memoria, mMemoria);
	}

}
