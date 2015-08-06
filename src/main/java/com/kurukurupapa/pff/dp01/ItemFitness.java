package com.kurukurupapa.pff.dp01;

import com.kurukurupapa.pff.domain.ItemData;

/**
 * アイテム適応度クラス
 */
public abstract class ItemFitness {
	protected ItemData mItem;
	protected Memoria mMemoria;
	protected FitnessCalculator mFitnessCalculator;
	protected FitnessValue mBeforeValue;
	protected FitnessValue mAfterValue;

	/**
	 * コンストラクタ
	 */
	public ItemFitness() {
		mBeforeValue = new FitnessValue();
		mAfterValue = new FitnessValue();
	}

	/**
	 * 準備
	 * 
	 * @param item
	 *            対象アイテム
	 */
	public void setup(ItemData item) {
		setup(item, null);
	}

	/**
	 * 準備
	 * 
	 * @param item
	 *            対象アイテム
	 * @param fitnessCalculator
	 *            適応度計算。nullの場合、対象アイテムの評価が最大となる適応度条件を設定します。
	 */
	public void setup(ItemData item, FitnessCalculator fitnessCalculator) {
		mItem = item;
		mFitnessCalculator = fitnessCalculator;
	}

	/**
	 * 適応度を計算します。
	 * 
	 * @param memoria
	 *            アイテム装備前のメモリア
	 */
	public abstract void calc(Memoria memoria);

	protected FitnessCalculator createFitnessCalculator() {
		if (mFitnessCalculator != null) {
			return mFitnessCalculator;
		} else {
			// 当クラスにおける適応度としては、さまざまな敵に対する最大の適応度のみを調べたい。
			// そのため、属性付きのアイテムの場合、その属性を弱点とする敵を想定して、適応度を評価します。
			FitnessCalculator fitnessCalculator = new FitnessCalculator();
			if (!mItem.getAttr().isNone()) {
				fitnessCalculator.addEnemyWeak(mItem.getAttr());
			}
			return fitnessCalculator;
		}
	}

	protected void calcDifference(FitnessCalculator fitnessCalculator,
			Memoria before, Memoria after) {
		mBeforeValue = fitnessCalculator.calc(new Party(before));
		mAfterValue = fitnessCalculator.calc(new Party(after));
	}

	public int getFitness() {
		return mAfterValue.getValue() - mBeforeValue.getValue();
	}

	public ItemData getItem() {
		return mItem;
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

	public int getPhysicalDefenceDamage() {
		return mAfterValue.getPhysicalDefenceDamage()
				- mBeforeValue.getPhysicalDefenceDamage();
	}

	public int getMagicDefenceDamage() {
		return mAfterValue.getMagicDefenceDamage()
				- mBeforeValue.getMagicDefenceDamage();
	}

	public int getRecovery() {
		return mAfterValue.getRecovery() - mBeforeValue.getRecovery();
	}

	@Override
	public String toString() {
		return "[" + getFitness() + "," + mItem + "," + mMemoria + "]";
	}

	public String toSimpeString() {
		return getFitness() + "," + mItem + "," + mMemoria;
	}
}
