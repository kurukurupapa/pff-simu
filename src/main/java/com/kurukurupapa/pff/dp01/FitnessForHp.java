package com.kurukurupapa.pff.dp01;

public class FitnessForHp extends Fitness {

	@Override
	protected MemoriaFitnessValue calc(Memoria memoria) {
		MemoriaFitnessValue value = new MemoriaFitnessValue(memoria);
		value.setValue(memoria.getHp());
		return value;
	}

}
