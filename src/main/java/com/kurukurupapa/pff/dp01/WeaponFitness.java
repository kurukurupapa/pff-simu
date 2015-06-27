package com.kurukurupapa.pff.dp01;

import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pff.domain.ItemData;

/**
 * 武器適応度クラス
 */
public class WeaponFitness {
	private ItemData mWeapon;
	private Memoria mMemoria;
	private int mFitness;
	private FitnessValue mBeforeValue;
	private FitnessValue mAfterValue;

	/**
	 * コンストラクタ
	 */
	public WeaponFitness() {
	}

	/**
	 * 準備
	 * 
	 * @param weapon
	 *            対象武器
	 */
	public void setup(ItemData weapon) {
		mWeapon = weapon;
	}

	/**
	 * 適応度を計算します。
	 * 
	 * @param memoria
	 *            武器装備前のメモリア
	 */
	public void calc(Memoria memoria) {
		// 当該武器を装備するため、メモリアは武器未装備であること。
		Validate.validState(memoria.getRemainWeaponSlot() >= 1);

		// 武器装備後のメモリア
		mMemoria = memoria.clone();
		mMemoria.setWeapon(mWeapon);

		// 当該武器の適応度
		Fitness fitness = createFitness();
		mFitness = calcDifference(fitness, memoria, mMemoria);
	}

	private Fitness createFitness() {
		// 当クラスにおける適応度としては、さまざまな敵に対する最大の適応度のみを調べたい。
		// そのため、属性付きの武器の場合、その属性を弱点とする敵を想定して、適応度を評価します。
		FitnessForBattle fitness = new FitnessForBattle();
		if (!mWeapon.getAttr().isNone()) {
			fitness.addEnemyWeak(mWeapon.getAttr());
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

	public ItemData getWeapon() {
		return mWeapon;
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
		return mFitness + "," + mWeapon + "," + mMemoria;
	}
}
