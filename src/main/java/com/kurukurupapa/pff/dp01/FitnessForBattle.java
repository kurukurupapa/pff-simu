package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.AppException;
import com.kurukurupapa.pff.domain.BattleType;

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

	/** バトル形式 */
	protected BattleType mBattleType;
	/** 敵の弱点属性 */
	protected List<Attr> mWeakList;
	/** 敵の耐性属性 */
	protected List<Attr> mResistanceList;
	/** 敵の物理防御 */
	protected int mPhysicalResistance;
	/** 敵の魔法防御 */
	protected int mMagicResistance;

	/** 敵の力 */
	protected int mEnemyPower;

	/**
	 * コンストラクタ
	 */
	public FitnessForBattle() {
		mBattleType = BattleType.NORMAL;
		mWeakList = new ArrayList<Attr>();
		mResistanceList = new ArrayList<Attr>();
	}

	@Override
	public FitnessValue calc(Party party) {
		// 敵の力
		// 案１：とりあえず、パーティの力の平均を、敵の力とします。
		// mEnemyPower = party.getAveragePower();
		// 案２：敵1体、自メモリア4対での戦いを想定し、敵は自メモリアの4倍の力とします。
		// →素早さの評価が上がりすぎる気がする。
		// mEnemyPower = party.getAveragePower() * 4;
		// 案３：案２の微調整
		mEnemyPower = party.getAveragePower() * 2;

		return super.calc(party);
	}

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
		switch (mBattleType) {
		case NORMAL:
			value.setValue(value.getHp() + value.getAttackDamage()
					+ value.getDefenceDamage() + value.getRecovery());
			break;
		case ATTACK:
			value.setValue(value.getHp() + value.getAttackDamage() * 2
					+ value.getDefenceDamage() + value.getRecovery());
			break;
		case RECOVERY:
			value.setValue(value.getHp() + value.getAttackDamage()
					+ value.getDefenceDamage() + value.getRecovery() * 2);
			break;
		case HP_DEFENCE_RECOVERY:
			value.setValue(value.getHp() * 2 + value.getAttackDamage()
					+ value.getDefenceDamage() * 2 + value.getRecovery() * 2);
			break;
		case EXA_BATTLIA:
			value.setValue(value.getAttackDamage());
			break;
		default:
			throw new AppException("想定外のバトル形式です。mBattleType="
					+ mBattleType.getText());
		}

		return value;
	}

	/**
	 * バトル形式を設定します。
	 * 
	 * @param battleType
	 *            バトル形式
	 */
	public void setBattleType(BattleType battleType) {
		mBattleType = battleType;
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

	public void setEnemyMagicResistance(int magicResistance) {
		mMagicResistance = magicResistance;
	}

}
