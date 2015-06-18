package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

import com.kurukurupapa.pff.domain.Attr;

/**
 * 適応度クラス
 */
public class FitnessForAttack extends Fitness {
	/** 1バトルあたりのターン数 */
	private static final int TURN = 10;
	/** 1ターンあたりのチャージ */
	private static final int CHARGE_PER_TURN = 5 * 5 / 2;
	/** 1バトルあたりのチャージ */
	private static final int CHARGE_PER_BATTLE = CHARGE_PER_TURN * TURN;

	/** 敵の弱点属性 */
	private List<Attr> mWeakList;
	/** 敵の耐性属性 */
	private List<Attr> mResistanceList;

	/**
	 * コンストラクタ
	 */
	public FitnessForAttack() {
		mWeakList = new ArrayList<Attr>();
		mResistanceList = new ArrayList<Attr>();
	}

	@Override
	protected MemoriaFitnessValue calc(Memoria memoria) {
		MemoriaFitnessValue value = new MemoriaFitnessValue(memoria);

		// 評価
		// 物理/魔法与ダメージのみ
		value.setValue(memoria.getAttackDamage(TURN, CHARGE_PER_BATTLE,
				mWeakList, mResistanceList));

		return value;
	}

	/**
	 * 敵の弱点を登録します。
	 *
	 * @param attr
	 *            属性
	 */
	public void addEnemyWeak(Attr attr) {
		mWeakList.add(attr);
	}

	/**
	 * 敵の耐性を登録します。
	 *
	 * @param attr
	 *            属性
	 */
	public void addEnemyResistance(Attr attr) {
		mResistanceList.add(attr);
	}

}
