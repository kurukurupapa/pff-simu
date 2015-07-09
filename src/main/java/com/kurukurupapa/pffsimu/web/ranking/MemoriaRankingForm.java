package com.kurukurupapa.pffsimu.web.ranking;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.BattleType;
import com.kurukurupapa.pff.dp01.FitnessCalculator;

/**
 * ランキング機能 メモリアランキングフォームクラス
 */
public class MemoriaRankingForm {

	/** バトル形式 */
	@NotEmpty
	private String battleType;

	/** 敵の弱点属性 */
	private String[] enemyWeakPoints = new String[] {};

	/** 敵の耐性属性 */
	private String[] enemyStrongPoints = new String[] {};

	/** 敵の物理防御 */
	@Range(min = 0, max = 1000)
	private int physicalResistance;

	/**
	 * コンストラクタ
	 */
	public MemoriaRankingForm() {
		battleType = BattleType.NORMAL.name();
	}

	public String getBattleType() {
		return battleType;
	}

	public BattleType getBattleTypeObj() {
		return BattleType.valueOf(battleType);
	}

	public void setBattleType(String battleType) {
		this.battleType = battleType;
	}

	public String[] getEnemyWeakPoints() {
		return enemyWeakPoints;
	}

	public void setEnemyWeakPoints(String[] enemyWeakPoints) {
		this.enemyWeakPoints = enemyWeakPoints;
	}

	public String[] getEnemyStrongPoints() {
		return enemyStrongPoints;
	}

	public void setEnemyStrongPoints(String[] enemyStrongPoints) {
		this.enemyStrongPoints = enemyStrongPoints;
	}

	public int getPhysicalResistance() {
		return physicalResistance;
	}

	public void setPhysicalResistance(int physicalResistance) {
		this.physicalResistance = physicalResistance;
	}

	public FitnessCalculator getFitnessCalculator() {
		FitnessCalculator fitness = new FitnessCalculator();
		fitness.setBattleType(getBattleTypeObj());
		for (String e : enemyWeakPoints) {
			fitness.addEnemyWeak(Attr.valueOf(e));
		}
		for (String e : enemyStrongPoints) {
			fitness.addEnemyResistance(Attr.valueOf(e));
		}
		fitness.setEnemyPhysicalResistance(physicalResistance);

		return fitness;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
