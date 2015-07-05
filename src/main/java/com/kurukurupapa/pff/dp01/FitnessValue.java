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
	private List<MemoriaFitnessValue> mMemoriaFitnessValues;

	public FitnessValue() {
		mValue = 0;
		mMemoriaFitnessValues = new ArrayList<MemoriaFitnessValue>();
	}

	public void add(MemoriaFitnessValue memoriaFitnessValue) {
		mMemoriaFitnessValues.add(memoriaFitnessValue);
		mValue += memoriaFitnessValue.getValue();
	}

	public int getValue() {
		return mValue;
	}

	public void setValue(int value) {
		this.mValue = value;
	}

	public int getHp() {
		int value = 0;
		for (MemoriaFitnessValue e : mMemoriaFitnessValues) {
			value += e.getHp();
		}
		return value;
	}

	public int getAttackDamage() {
		int value = 0;
		for (MemoriaFitnessValue e : mMemoriaFitnessValues) {
			value += e.getAttackDamage();
		}
		return value;
	}

	public int getDefenceDamage() {
		return getPhysicalDefenceDamage() + getMagicDefenceDamage();
	}

	public int getPhysicalDefenceDamage() {
		int value = 0;
		for (MemoriaFitnessValue e : mMemoriaFitnessValues) {
			value += e.getPhysicalDefenceDamage();
		}
		return value;
	}

	public int getMagicDefenceDamage() {
		int value = 0;
		for (MemoriaFitnessValue e : mMemoriaFitnessValues) {
			value += e.getMagicDefenceDamage();
		}
		return value;
	}

	public int getRecovery() {
		int value = 0;
		for (MemoriaFitnessValue e : mMemoriaFitnessValues) {
			value += e.getRecovery();
		}
		return value;
	}

	public List<MemoriaFitnessValue> getMemoriaFitnesses() {
		return mMemoriaFitnessValues;
	}

}
