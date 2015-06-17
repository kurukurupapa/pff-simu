package com.kurukurupapa.pff.ga01.domain;

/**
 * 適応度クラス
 */
public class FitnessForAttack extends Fitness {

    public int calc(Party party) {
        int value = 0;
        // 物理与ダメージ＝(力×攻撃力補正－物理防御力)×倍率
        value += party.getPhysicalAttackDamage();
        // 魔法ダメージ＝(知性＋効果値)×倍率－魔法防御力
        value += party.getMagicAttackDamage();
        return value;
    }

}
