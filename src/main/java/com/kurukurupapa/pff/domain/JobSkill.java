package com.kurukurupapa.pff.domain;

/**
 * ジョブスキルクラス
 */
public class JobSkill {
	private String mName;

	public JobSkill(String name) {
		mName = name.trim();
	}

	@Override
	public boolean equals(Object obj) {
		JobSkill other = (JobSkill) obj;
		return mName.equals(other.mName);
	}

	@Override
	public String toString() {
		return mName;
	}

	public boolean isIainuki() {
		return "居合い抜き".equals(mName);
	}

	public boolean isMidareuchi() {
		return "乱れ撃ち".equals(mName);
	}

	public boolean isFurea() {
		return "フレア".equals(mName);
	}

	public int getFureaAttackDamage(int intelligence, float magicAttack) {
		return (int) ((intelligence + 100) * magicAttack * Mement.CHIE_BLACK_RATE);
	}

}
