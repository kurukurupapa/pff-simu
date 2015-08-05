package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 適応度結果クラス
 */
public class FitnessValue {
	/** 評価結果 */
	private int mValue;
	/** メモリアごとの適応度結果 */
	private List<MemoriaFitness> mMemoriaFitnesses;

	public FitnessValue() {
		mValue = 0;
		mMemoriaFitnesses = new ArrayList<MemoriaFitness>();
	}

	@Override
	public String toString() {
		return mValue + ",[" + StringUtils.join(mMemoriaFitnesses, ",") + "]";
	}

	public void add(MemoriaFitness MemoriaFitness) {
		mMemoriaFitnesses.add(MemoriaFitness);
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
		for (MemoriaFitness e : mMemoriaFitnesses) {
			value += e.getHp();
		}
		return value;
	}

	public int getAttackDamage() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesses) {
			value += e.getAttackDamage();
		}
		return value;
	}

	public int getDefenceDamage() {
		return getPhysicalDefenceDamage() + getMagicDefenceDamage();
	}

	public int getPhysicalDefenceDamage() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesses) {
			value += e.getPhysicalDefenceDamage();
		}
		return value;
	}

	public int getMagicDefenceDamage() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesses) {
			value += e.getMagicDefenceDamage();
		}
		return value;
	}

	public int getRecovery() {
		int value = 0;
		for (MemoriaFitness e : mMemoriaFitnesses) {
			value += e.getRecovery();
		}
		return value;
	}

	public List<MemoriaFitness> getMemoriaFitnesses() {
		return mMemoriaFitnesses;
	}

}
