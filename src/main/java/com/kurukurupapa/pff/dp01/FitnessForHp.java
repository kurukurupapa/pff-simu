package com.kurukurupapa.pff.dp01;

public class FitnessForHp extends Fitness {

    @Override
    protected int calc(Memoria memoria) {
        return memoria.getHp();
    }

}
