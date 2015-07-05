package com.kurukurupapa.pff.dp01;

import com.kurukurupapa.pff.domain.ItemData;

/**
 * アイテム適応度クラス
 */
public abstract class ItemFitness {
	protected ItemData mItem;
	protected Memoria mMemoria;
	protected Fitness mFitness;
	protected FitnessValue mBeforeValue;
	protected FitnessValue mAfterValue;

	/**
	 * コンストラクタ
	 */
	public ItemFitness() {
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
	 * @param fitness
	 *            適応度条件。nullの場合、対象アイテムの評価が最大となる適応度条件を設定します。
	 */
	public void setup(ItemData item, Fitness fitness) {
		mItem = item;
		mFitness = fitness;
	}

	/**
	 * 適応度を計算します。
	 * 
	 * @param memoria
	 *            アイテム装備前のメモリア
	 */
	public abstract void calc(Memoria memoria);

	protected Fitness createFitness() {
		if (mFitness != null) {
			return mFitness;
		} else {
			// 当クラスにおける適応度としては、さまざまな敵に対する最大の適応度のみを調べたい。
			// そのため、属性付きのアイテムの場合、その属性を弱点とする敵を想定して、適応度を評価します。
			FitnessForBattle fitness = new FitnessForBattle();
			if (!mItem.getAttr().isNone()) {
				fitness.addEnemyWeak(mItem.getAttr());
			}
			return fitness;
		}
	}

	protected void calcDifference(Fitness fitness, Memoria before, Memoria after) {
		mBeforeValue = fitness.calc(new Party(before));
		mAfterValue = fitness.calc(new Party(after));
	}

	public int getFitness() {
		if (mBeforeValue != null && mAfterValue != null) {
			return mAfterValue.getValue() - mBeforeValue.getValue();
		} else {
			return 0;
		}
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
		return mAfterValue.getPhysicalDefenceDamage() - mBeforeValue.getPhysicalDefenceDamage();
	}

	public int getMagicDefenceDamage() {
		return mAfterValue.getMagicDefenceDamage() - mBeforeValue.getMagicDefenceDamage();
	}

	public int getRecovery() {
		return mAfterValue.getRecovery() - mBeforeValue.getRecovery();
	}

	@Override
	public String toString() {
		return getFitness() + "," + mItem + "," + mMemoria;
	}
}
