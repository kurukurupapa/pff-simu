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
		if (obj == null || !(obj instanceof JobSkill)) {
			return false;
		}
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

	/**
	 * 回復魔法に効果があるか判定します。
	 * 
	 * @return 効果がある場合true
	 */
	public boolean isRecoveryMagic() {
		return "オーラ".equals(mName);
	}

	/**
	 * 回復魔法に対する効果を適用します。
	 * 
	 * @param recoveryDamage
	 *            ジョブスキル適用前の回復値
	 * @return 回復値
	 */
	public int calcRecoveryMagicDamage(int recoveryDamage) {
		int damage = recoveryDamage;
		if (mName.equals("オーラ")) {
			// オーラ
			// 自身の白魔法アビリティ効果が上昇
			// 回復量1.8倍 他の白魔法にも効果あり
			damage = (int) (recoveryDamage * 1.8f);
		}
		return damage;
	}

	public int getFureaAttackDamage(int intelligence, float magicAttack) {
		return (int) ((intelligence + 100) * magicAttack * Mement.CHIE_BLACK_RATE);
	}

}
