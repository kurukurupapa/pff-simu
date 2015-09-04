package com.kurukurupapa.pffsimu.domain.fitness;

import java.util.ArrayList;
import java.util.List;

import com.kurukurupapa.pffsimu.domain.AppException;
import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.BattleType;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * 適応度計算クラス
 */
public class FitnessCalculator {
	/** 1バトルあたりのターン数 */
	protected static final int TURN = 10;
	/** 1ターンあたりのチャージ */
	protected static final int CHARGE_PER_TURN = 5 * 5 / 2;
	/** 1バトルあたりのチャージ */
	protected static final int CHARGE_PER_BATTLE = CHARGE_PER_TURN * TURN;

	/** 計算種類名 */
	private String mName;

	/** 敵の弱点属性 */
	protected List<Attr> mWeakList;
	/** 敵の耐性属性 */
	protected List<Attr> mResistanceList;
	/** 敵の物理防御 */
	protected int mPhysicalResistance;
	/** 敵の魔法防御 */
	protected int mMagicResistance;

	/** 敵の力 登録状態 */
	protected boolean mEnemyPowerFlag;
	/** 敵の力 */
	protected int mEnemyPower;

	/** HP評価の重み */
	private int mHpWeight = 1;
	/** 攻撃評価の重み */
	private int mAttackWeight = 1;
	/** 物理防御評価の重み */
	private int mPhysicalDefenceWeight = 1;
	/** 魔法防御評価の重み */
	private int mMagicDefenceWeight = 1;
	/** 回復評価の重み */
	private int mRecoveryWeight = 1;

	/**
	 * コンストラクタ
	 */
	public FitnessCalculator() {
		this(FitnessCalculator.class.getSimpleName());
	}

	/**
	 * コンストラクタ
	 */
	public FitnessCalculator(String name) {
		mName = name;
		setBattleType(BattleType.NORMAL);
		mWeakList = new ArrayList<Attr>();
		mResistanceList = new ArrayList<Attr>();
	}

	public String getName() {
		return mName;
	}

	public FitnessValue calc(Party party) {
		// 敵の力
		// 案１：とりあえず、パーティの力の平均を、敵の力とします。
		// mEnemyPower = party.getAveragePower();
		// 案２：敵1体、自メモリア4対での戦いを想定し、敵は自メモリアの4倍の力とします。
		// mEnemyPower = party.getAveragePower() * 4;
		// →素早さの評価が上がりすぎる気がする。
		// 案３：案２の微調整
		// mEnemyPower = party.getAveragePower() * 2;
		// →メモリアの適応度の単純な積み上げが、パーティの適応度にならない。
		// 　最適パーティの探索が難しくなるため、別案を検討する。
		// 案４：
		// 案３の問題点と、既存処理の互換性を考慮し、メモリアごとに敵の力を計算します。

		FitnessValue fitnessValue = new FitnessValue();
		for (Memoria e : party.getMemoriaList()) {
			fitnessValue.add(calc(e));
		}
		return fitnessValue;
	}

	protected MemoriaFitness calc(Memoria memoria) {
		MemoriaFitness value = new MemoriaFitness(memoria);

		// 敵の力
		int enemyPower;
		if (mEnemyPowerFlag) {
			enemyPower = mEnemyPower;
		} else {
			enemyPower = memoria.getPower() * 2;
		}

		// HP
		value.setHp(memoria.getHp());

		// 物理/魔法与ダメージ
		value.setAttackDamage(memoria.getAttackDamage(TURN, CHARGE_PER_BATTLE,
				mWeakList, mResistanceList, mPhysicalResistance,
				mMagicResistance));

		// 物理被ダメージ
		value.setPhysicalDefenceDamage(memoria.getPhysicalDefenceDamage(TURN,
				enemyPower));

		// 魔法被ダメージ
		value.setMagicDefenceDamage(memoria.getMagicDefenceDamage(TURN));

		// 回復量
		value.setRecovery(memoria.getRecovery(TURN, CHARGE_PER_BATTLE));

		// 評価
		value.setValue(value.getHp() * mHpWeight + value.getAttackDamage()
				* mAttackWeight + value.getPhysicalDefenceDamage()
				* mPhysicalDefenceWeight + value.getMagicDefenceDamage()
				* mMagicDefenceWeight + value.getRecovery() * mRecoveryWeight);
		return value;
	}

	/**
	 * バトル形式を設定します。
	 * 
	 * @param battleType
	 *            バトル形式
	 */
	public void setBattleType(BattleType battleType) {
		mHpWeight = 1;
		mAttackWeight = 1;
		mPhysicalDefenceWeight = 1;
		mMagicDefenceWeight = 1;
		mRecoveryWeight = 1;

		switch (battleType) {
		case NORMAL:
			break;
		case ATTACK:
			mAttackWeight = 2;
			break;
		case DEFENCE:
			mPhysicalDefenceWeight = 2;
			mMagicDefenceWeight = 2;
			break;
		case PHYSICAL_DEFENCE:
			mPhysicalDefenceWeight = 2;
			break;
		case MAGIC_DEFENCE:
			mMagicDefenceWeight = 2;
			break;
		case RECOVERY:
			mRecoveryWeight = 2;
			break;
		case HP_DEFENCE_RECOVERY:
			mHpWeight = 2;
			mPhysicalDefenceWeight = 2;
			mMagicDefenceWeight = 2;
			mRecoveryWeight = 2;
			break;
		case EXA_BATTLIA:
			mHpWeight = 0;
			mAttackWeight = 1;
			mPhysicalDefenceWeight = 0;
			mMagicDefenceWeight = 0;
			mRecoveryWeight = 0;
			break;
		default:
			throw new AppException("想定外のバトル形式です。mBattleType="
					+ battleType.getText());
		}
	}

	public void setWeight(int hp, int attack, int physicalDefence,
			int magicDefence, int recovery) {
		mHpWeight = hp;
		mAttackWeight = attack;
		mPhysicalDefenceWeight = physicalDefence;
		mMagicDefenceWeight = magicDefence;
		mRecoveryWeight = recovery;
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

	/**
	 * 敵の魔法防御を登録します。
	 * 
	 * @param magicResistance
	 *            防御
	 */
	public void setEnemyMagicResistance(int magicResistance) {
		mMagicResistance = magicResistance;
	}

	/**
	 * 敵の力を登録します。
	 * 
	 * @param power
	 *            力
	 */
	public void setEnemyPower(int power) {
		mEnemyPowerFlag = true;
		mEnemyPower = power;
	}
}
