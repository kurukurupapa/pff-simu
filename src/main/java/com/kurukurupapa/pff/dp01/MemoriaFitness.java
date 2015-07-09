package com.kurukurupapa.pff.dp01;

/**
 * メモリア適応度結果クラス
 */
public class MemoriaFitness {
	/** メモリア */
	private Memoria mMemoria;
	/** HP */
	private int mHp;
	/** 物理/魔法与ダメージ */
	private int mAttackDamage;
	/** 物理被ダメージ */
	private int mPhysicalDefenceDamage;
	/** 魔法被ダメージ */
	private int mMagicDefenceDamage;
	/** 回復量 */
	private int mRecovery;
	/** 評価値 */
	private int mValue;

	public MemoriaFitness(Memoria memoria) {
		this.mMemoria = memoria;
	}

	@Override
	public String toString() {
		return mValue + "," + mMemoria;
	}

	public Memoria getMemoria() {
		return mMemoria;
	}

	public void setMemoria(Memoria memoria) {
		this.mMemoria = memoria;
	}

	public int getHp() {
		return mHp;
	}

	public void setHp(int mHp) {
		this.mHp = mHp;
	}

	public int getAttackDamage() {
		return mAttackDamage;
	}

	public void setAttackDamage(int attackDamage) {
		this.mAttackDamage = attackDamage;
	}

	public int getPhysicalDefenceDamage() {
		return mPhysicalDefenceDamage;
	}

	public void setPhysicalDefenceDamage(int physicalDefenceDamage) {
		this.mPhysicalDefenceDamage = physicalDefenceDamage;
	}

	public int getMagicDefenceDamage() {
		return mMagicDefenceDamage;
	}

	public void setMagicDefenceDamage(int magicDefenceDamage) {
		this.mMagicDefenceDamage = magicDefenceDamage;
	}

	public int getDefenceDamage() {
		return getPhysicalDefenceDamage() + getMagicDefenceDamage();
	}

	public int getRecovery() {
		return mRecovery;
	}

	public void setRecovery(int recovery) {
		this.mRecovery = recovery;
	}

	public int getValue() {
		return mValue;
	}

	public void setValue(int value) {
		this.mValue = value;
	}

}
