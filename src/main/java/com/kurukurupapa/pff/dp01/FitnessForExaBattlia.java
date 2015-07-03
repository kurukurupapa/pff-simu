package com.kurukurupapa.pff.dp01;

/**
 * 適応度クラス（エクサバトリア用）
 */
public class FitnessForExaBattlia extends FitnessForBattle {

	@Override
	protected MemoriaFitnessValue calc(Memoria memoria) {
		MemoriaFitnessValue value = new MemoriaFitnessValue(memoria);

		// HP
		value.setHp(memoria.getHp());

		// 物理/魔法与ダメージ
		value.setAttackDamage(memoria.getAttackDamage(TURN, CHARGE_PER_BATTLE,
				mWeakList, mResistanceList, mPhysicalResistance,
				mMagicResistance));

		// 物理被ダメージ
		value.setPhysicalDefenceDamage(memoria.getPhysicalDefenceDamage(TURN,
				mEnemyPower));

		// 魔法被ダメージ
		value.setMagicDefenceDamage(memoria.getMagicDefenceDamage(TURN));

		// 回復量
		value.setRecovery(memoria.getRecovery(TURN, CHARGE_PER_BATTLE));

		// 評価
		// 物理/魔法与ダメージのみで評価します。
		value.setValue(value.getAttackDamage());

		return value;
	}

}
