package com.kurukurupapa.pff.dp01;

import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pff.domain.ItemData;

/**
 * 武器適応度クラス
 */
public class MagicFitness {
	private ItemData mMagic;
	private Memoria mMemoria;
	private int mFitness;
	private FitnessValue mBeforeValue;
	private FitnessValue mAfterValue;

	/**
	 * コンストラクタ
	 */
	public MagicFitness() {
	}

	/**
	 * 準備
	 * 
	 * @param magic
	 *            対象魔法
	 */
	public void setup(ItemData magic) {
		mMagic = magic;
	}

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
		mMemoria.addAccessory(mMagic);

		// 当該魔法の適応度
		Fitness fitness = createFitness();
		mFitness = calcDifference(fitness, memoria, mMemoria);
	}

	private Fitness createFitness() {
		// 当クラスにおける適応度としては、さまざまな敵に対する最大の適応度のみを調べたい。
		// そのため、当該魔法の属性を弱点とする敵を想定して、適応度を評価します。
		FitnessForBattle fitness = new FitnessForBattle();
		if (!mMagic.getAttr().isNone()) {
			fitness.addEnemyWeak(mMagic.getAttr());
		}
		return fitness;
	}

	private int calcDifference(Fitness fitness, Memoria before, Memoria after) {
		mBeforeValue = fitness.calc(new Party(before));
		mAfterValue = fitness.calc(new Party(after));
		return mAfterValue.getValue() - mBeforeValue.getValue();
	}

	public int getFitness() {
		return mFitness;
	}

	public ItemData getMagic() {
		return mMagic;
	}

	public Memoria getMemoria() {
		return mMemoria;
	}

	public int getHp() {
		return mAfterValue.getHp() - mBeforeValue.getHp();
	}

	public int getAttackDamage() {
		return mAfterValue.getAttackDamage() - mBeforeValue.getAttackDamage();
	}

	public int getDefenceDamage() {
		return mAfterValue.getDefenceDamage() - mBeforeValue.getDefenceDamage();
	}

	public int getRecovery() {
		return mAfterValue.getRecovery() - mBeforeValue.getRecovery();
	}

	@Override
	public String toString() {
		return mFitness + "," + mMagic + "," + mMemoria;
	}
}
