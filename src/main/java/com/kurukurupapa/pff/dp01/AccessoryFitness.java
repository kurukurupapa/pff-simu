package com.kurukurupapa.pff.dp01;

import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pff.domain.ItemData;

/**
 * アクセサリ適応度クラス
 */
public class AccessoryFitness {
	private ItemData mAccessory;
	private Memoria mMemoria;
	private int mFitness;

	/**
	 * コンストラクタ
	 */
	public AccessoryFitness() {
	}

	/**
	 * 準備
	 * 
	 * @param accessory
	 *            対象アクセサリ
	 */
	public void setup(ItemData accessory) {
		mAccessory = accessory;
	}

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
		mMemoria.addAccessory(mAccessory);

		// 当該アイテムの適応度
		Fitness fitness = createFitness();
		mFitness = calcDifference(fitness, memoria, mMemoria);
	}

	private Fitness createFitness() {
		// 当クラスにおける適応度としては、さまざまな敵に対する最大の適応度のみを調べたい。
		// そのため、属性付きのアクセサリの場合、その属性を弱点とする敵を想定して、適応度を評価します。
		FitnessForBattle fitness = new FitnessForBattle();
		if (!mAccessory.getAttr().isNone()) {
			fitness.addEnemyWeak(mAccessory.getAttr());
		}
		return fitness;
	}

	private int calcDifference(Fitness fitness, Memoria before, Memoria after) {
		FitnessValue beforeValue = fitness.calc(new Party(before));
		FitnessValue afterValue = fitness.calc(new Party(after));
		return afterValue.getValue() - beforeValue.getValue();
	}

	public int getFitness() {
		return mFitness;
	}

	public ItemData getAccessory() {
		return mAccessory;
	}

	public Memoria getMemoria() {
		return mMemoria;
	}

	@Override
	public String toString() {
		return mFitness + "," + mAccessory + "," + mMemoria;
	}
}
