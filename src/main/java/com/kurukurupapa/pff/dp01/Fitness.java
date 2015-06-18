package com.kurukurupapa.pff.dp01;

import java.util.List;

/**
 * 適応度計算クラス
 */
public abstract class Fitness {

	/** 計算種類名 */
	private String mName;

	public Fitness() {
		mName = this.getClass().getSimpleName();
	}

	public Fitness(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public FitnessValue calc(Party party) {
		FitnessValue fitnessValue = new FitnessValue();
		for (Memoria e : party.getMemoriaList()) {
			fitnessValue.add(calc(e));
		}
		return fitnessValue;
	}

	protected abstract MemoriaFitnessValue calc(Memoria memoria);

}
