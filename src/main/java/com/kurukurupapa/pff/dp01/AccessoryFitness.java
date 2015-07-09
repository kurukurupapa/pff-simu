package com.kurukurupapa.pff.dp01;

import org.apache.commons.lang3.Validate;

/**
 * アクセサリ適応度クラス
 */
public class AccessoryFitness extends ItemFitness {

	/**
	 * 適応度を計算します。
	 * 
	 * @param memoria
	 *            アクセサリ装備前のメモリア
	 */
	public void calc(Memoria memoria) {
		// 当該アクセサリを付与するため、メモリアには空きスロットが必要。
		Validate.validState(memoria.getRemainAccessorySlot() >= 1);

		// アクセサリ装備後のメモリア
		mMemoria = memoria.clone();
		mMemoria.addAccessory(mItem);

		// 当該アイテムの適応度
		FitnessCalculator fitnessCalculator = createFitnessCalculator();
		calcDifference(fitnessCalculator, memoria, mMemoria);
	}

}
