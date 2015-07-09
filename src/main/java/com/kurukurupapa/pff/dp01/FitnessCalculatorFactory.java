package com.kurukurupapa.pff.dp01;

import com.kurukurupapa.pff.domain.BattleType;

/**
 * 適応度計算ファクトリークラス
 */
public class FitnessCalculatorFactory {

	public static FitnessCalculator createForAttack() {
		FitnessCalculator fitnessCalculator = new FitnessCalculator("Attack");
		fitnessCalculator.setBattleType(BattleType.EXA_BATTLIA);
		return fitnessCalculator;
	}

	public static FitnessCalculator createForBattle() {
		FitnessCalculator fitnessCalculator = new FitnessCalculator("Battle");
		return fitnessCalculator;
	}

	public static FitnessCalculator createForHp() {
		FitnessCalculator fitnessCalculator = new FitnessCalculator("Hp");
		fitnessCalculator.setWeight(1, 0, 0, 0, 0);
		return fitnessCalculator;
	}

	public static FitnessCalculator createForRecovery() {
		FitnessCalculator fitnessCalculator = new FitnessCalculator("Recovery");
		fitnessCalculator.setWeight(0, 0, 0, 0, 1);
		return fitnessCalculator;
	}

}
