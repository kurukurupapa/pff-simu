package com.kurukurupapa.pff.ga01.domain;

/**
 * 適応度クラス
 */
public class FitnessForBattle extends Fitness {
	private static final int NUM_TURN = 10;

    public int calc(Party party) {
        int value = 0;
        
        // HP
        value += party.getHp();
        // 物理与ダメージ＝(力×攻撃力補正－物理防御力)×倍率
        value += party.getPhysicalAttackDamage();
        // 物理被ダメージ＝(力－物理防御力)×倍率
        value += party.getPhysicalDefenceDamage();
        // 魔法ダメージ＝(知性＋効果値)×倍率－魔法防御力
        value += party.getMagicAttackDamage();
        // 魔法被ダメージ＝(効果値×メメント効果－魔法防御力×倍率)×魔法効果
        value += party.getMagicDefenceDamage();
        // 回復量＝(知性＋効果値)×魔法倍率
        value += party.getInoriRecovery();
        
        return value;
    }

}
