package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

import com.kurukurupapa.pff.domain.Attr;

/**
 * 適応度クラス（通常バトル用）
 */
public class FitnessForBattle extends Fitness {
	/** 1バトルあたりのターン数 */
	protected static final int TURN = 10;
	/** 1ターンあたりのチャージ */
	protected static final int CHARGE_PER_TURN = 5 * 5 / 2;
	/** 1バトルあたりのチャージ */
	protected static final int CHARGE_PER_BATTLE = CHARGE_PER_TURN * TURN;

	/** 敵の弱点属性 */
	protected List<Attr> mWeakList;
	/** 敵の耐性属性 */
	protected List<Attr> mResistanceList;
	/** 敵の物理防御 */
	protected int mPhysicalResistance;

	/** 敵の力 */
	protected int mEnemyPower;

	/**
	 * コンストラクタ
	 */
	public FitnessForBattle() {
		mWeakList = new ArrayList<Attr>();
		mResistanceList = new ArrayList<Attr>();
	}

	@Override
	public FitnessValue calc(Party party) {
		// 敵の力
		// とりあえず、パーティの力の平均を、敵の力とします。
		mEnemyPower = party.getAveragePower();

		return super.calc(party);
	}

	@Override
	protected MemoriaFitnessValue calc(Memoria memoria) {
		MemoriaFitnessValue value = new MemoriaFitnessValue(memoria);

		// HP
		value.setHp(memoria.getHp());

		// 物理/魔法与ダメージ
		value.setAttackDamage(memoria.getAttackDamage(TURN, CHARGE_PER_BATTLE,
				mWeakList, mResistanceList, mPhysicalResistance));

		// 物理被ダメージ
		value.setPhysicalDefenceDamage(memoria.getPhysicalDefenceDamage(TURN,
				mEnemyPower));

		// 魔法被ダメージ
		value.setMagicDefenceDamage(memoria.getMagicDefenceDamage(TURN));

		// 回復量
		value.setRecovery(memoria.getRecovery(TURN, CHARGE_PER_BATTLE));

		// 評価
		value.setValue(value.getHp() + value.getAttackDamage()
				+ value.getPhysicalDefenceDamage()
				+ value.getMagicDefenceDamage() + value.getRecovery());

		return value;
	}

	/**
	 * 敵の弱点を登録します。
	 *
	 * @param attr
	 *            属性
	 */
	public void addEnemyWeak(Attr attr) {
		if (!attr.isNone()) {
			mWeakList.add(attr);
		}
	}

	/**
	 * 敵の耐性を登録します。
	 *
	 * @param attr
	 *            属性
	 */
	public void addEnemyResistance(Attr attr) {
		if (!attr.isNone()) {
			mResistanceList.add(attr);
		}
	}

	/**
	 * 敵の物理防御を登録します。
	 *
	 * @param physicalResistance
	 *            防御
	 */
	public void setEnemyPhysicalResistance(int physicalResistance) {
		mPhysicalResistance = physicalResistance;
	}

}
