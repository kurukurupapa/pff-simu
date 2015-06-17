package com.kurukurupapa.pff.dp01;

import java.util.List;

/**
 * 適応度クラス
 */
public abstract class Fitness {

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

    public int calc(Party party) {
        return calc(party.getMemoriaList());
    }

    protected int calc(List<Memoria> memoriaList) {
        int value = 0;
        for (Memoria e : memoriaList) {
            value += calc(e);
        }
        return value;
    }

    protected abstract int calc(Memoria memoria);

}
