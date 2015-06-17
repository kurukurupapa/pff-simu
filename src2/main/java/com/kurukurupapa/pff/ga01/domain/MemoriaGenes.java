package com.kurukurupapa.pff.ga01.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaData;

/**
 * メモリア遺伝子クラス
 */
public abstract class MemoriaGenes {

    private MemoriaData mMemoriaData;
    private Gene mUseRate;
    private Gene[] mWeaponRateArr;
    private Gene[] mAccessoryRateArr;
    private Gene[] mGeneArr;

    /**
     * コンストラクタ
     *
     * ランダムな遺伝子を持つメモリア遺伝子を作成します。
     *
     * @param memoriaData
     *            メモリアデータ
     */
    public MemoriaGenes(MemoriaData memoriaData, ItemDataSet itemDataSet) {
        int numWeapons = memoriaData.getNumWeapons();
        int numAccessories = memoriaData.getNumAccessories();
        mMemoriaData = memoriaData;
        mUseRate = new Gene();
        mWeaponRateArr = new Gene[numWeapons];
        for (int i = 0; i < numWeapons; i++) {
            mWeaponRateArr[i] = new Gene();
        }
        mAccessoryRateArr = new Gene[numAccessories];
        for (int i = 0; i < numAccessories; i++) {
            mAccessoryRateArr[i] = new Gene();
        }

        randomize();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mUseRate.toString());
        for (Gene gene : mWeaponRateArr) {
            sb.append("," + gene.toString());
        }
        for (Gene gene : mAccessoryRateArr) {
            sb.append("," + gene.toString());
        }
        return sb.toString();
    }

    public MemoriaData getMemoriaData() {
        return mMemoriaData;
    }

    public Gene getUseRate() {
        return mUseRate;
    }

    public Gene[] getGenes() {
        if (mGeneArr == null) {
            List<Gene> list = new ArrayList<Gene>();
            list.add(mUseRate);
            Collections.addAll(list, mWeaponRateArr);
            Collections.addAll(list, mAccessoryRateArr);
            mGeneArr = list.toArray(new Gene[] {});
        }
        return mGeneArr;
    }

    public void randomize() {
        mUseRate.randomize();
        for (Gene gene : mWeaponRateArr) {
            gene.randomize();
        }
        for (Gene gene : mAccessoryRateArr) {
            gene.randomize();
        }
    }

    public Gene[] getWeaponRateArr() {
        return mWeaponRateArr;
    }

    public Gene[] getAccessoryRateArr() {
        return mAccessoryRateArr;
    }
}
