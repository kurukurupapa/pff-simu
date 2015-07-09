package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

/**
 * 適応度結果クラス
 */
public class FitnessValue {
	/** 評価結果 */
	private int mValue;
	/** メモリアごとの適応度結果 */
	private List<MemoriaFitness> mMemoriaFitnesss;

	public FitnessValue() {
		mValue = 0;
		mMemoriaFitnesss = new ArrayList<MemoriaFitness>();
	}

	public void add(MemoriaFitness MemoriaFitness) {
		mMemoriaFitnesss.add(MemoriaFitness);
		mValue += MemoriaFitness.getValue();
	}

	public int getValue() {
		return mValue;
	}

	public void setValue(int value) {
		this.mValue = value;
	}

	public int getHp() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesss) {
			value += e.getHp();
		}
		return value;
	}

	public int getAttackDamage() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesss) {
			value += e.getAttackDamage();
		}
		return value;
	}

	public int getDefenceDamage() {
		return getPhysicalDefenceDamage() + getMagicDefenceDamage();
	}

	public int getPhysicalDefenceDamage() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesss) {
			value += e.getPhysicalDefenceDamage();
		}
		return value;
	}

	public int getMagicDefenceDamage() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesss) {
			value += e.getMagicDefenceDamage();
		}
		return value;
	}

	public int getRecovery() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesss) {
			value += e.getRecovery();
		}
		return value;
	}

	public List<MemoriaFitness> getMemoriaFitnesses() {
		return mMemoriaFitnesss;
	}

}
