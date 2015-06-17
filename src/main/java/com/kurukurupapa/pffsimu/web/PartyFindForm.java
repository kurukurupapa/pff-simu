package com.kurukurupapa.pffsimu.web;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Range;
import org.springframework.ui.Model;

public class PartyFindForm {

	private String[] enemyWeakPoints = new String[] {};

	private String[] enemyStrongPoints = new String[] {};

	@Range(min = 0, max = 1000)
	private int physicalResistance;

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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
