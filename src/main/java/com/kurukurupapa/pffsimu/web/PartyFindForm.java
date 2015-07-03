package com.kurukurupapa.pffsimu.web;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.kurukurupapa.pff.domain.BattleType;

/**
 * パーティ検討フォームクラス
 */
public class PartyFindForm {

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

	/** 敵の魔法防御 */
	@Range(min = 0, max = 1000)
	private int magicResistance;

	/**
	 * コンストラクタ
	 */
	public PartyFindForm() {
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

	public int getMagicResistance() {
		return magicResistance;
	}

	public void setMagicResistance(int magicResistance) {
		this.magicResistance = magicResistance;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
