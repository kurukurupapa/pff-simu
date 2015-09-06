package com.kurukurupapa.pffsimu.domain.memoria;

import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.Unit;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.party.Party;

/**
 * リーダースキルクラス
 */
public class LeaderSkill {

	/** リーダースキル発動可能メモリア名 */
	protected String mMemoriaName;

	/** ステータス上昇効果 */
	private ItemData mItemData;

	/** ステータス以外の効果 */
	private LeaderSkillEffect mEffect;

	public static boolean equals(LeaderSkill arg1, LeaderSkill arg2) {
		if (arg1 == null) {
			if (arg2 == null) {
				return true;
			}
		} else {
			if (arg1.equals(arg2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 引数のパーティに含まれるリーダースキルが妥当であるか判定します。 パーティにリーダースキルが含まれていない場合も妥当とします。
	 *
	 * @param party
	 *            パーティ
	 * @return 妥当な場合true
	 */
	public static boolean validOrNone(Party party) {
		if (party.getMemoriaList().size() == 0) {
			return true;
		}
		if (!validStructure(party)) {
			return false;
		}
		LeaderSkill leaderSkill = party.getMemoria(0).getLeaderSkill();
		if (leaderSkill != null && !validCondition(leaderSkill, party)) {
			return false;
		}
		return true;
	}

	/**
	 * 引数のパーティに対して、引数のメモリアのリーダースキルが妥当であるか判定します。
	 * パーティやメモリアにリーダースキルが含まれていない場合も妥当とします。
	 *
	 * @param party
	 *            パーティ
	 * @param memoria
	 *            メモリア
	 * @return 妥当な場合true
	 */
	public static boolean validOrNone(Party party, Memoria memoria) {
		if (party.getMemoriaList().size() == 0) {
			return true;
		}
		if (equals(party.getMemoria(0).getLeaderSkill(),
				memoria.getLeaderSkill())) {
			return true;
		}
		return false;
	}

	/**
	 * 引数のパーティに含まれるリーダースキルの構成が妥当であるか判定します。 パーティにリーダースキルが含まれていない場合も妥当とします。
	 *
	 * @param party
	 *            パーティ
	 * @return 妥当な場合true
	 */
	private static boolean validStructure(Party party) {
		if (party.getMemoriaList().size() == 0) {
			return true;
		}

		// 全メモリアについて、リーダースキルなし、または同一リーダースキルであること。
		LeaderSkill leaderSkill = party.getMemoria(0).getLeaderSkill();
		for (Memoria e : party.getMemoriaList()) {
			if (!equals(leaderSkill, e.getLeaderSkill())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 引数のリーダースキルが、引数のパーティに対して、妥当か判定します。
	 *
	 * @param leaderSkill
	 *            リーダースキル
	 * @param party
	 *            パーティ
	 * @return 妥当な場合true
	 */
	public static boolean validCondition(LeaderSkill leaderSkill, Party party) {
		Validate.notNull(leaderSkill);
		Validate.notNull(party);

		for (Memoria e : party.getMemoriaList()) {
			if (validCondition(leaderSkill, e, party)) {
				return true;
			}
		}
		return false;
	}

	public static boolean validCondition(LeaderSkill leaderSkill,
			Memoria memoria) {
		return validCondition(leaderSkill, memoria, null);
	}

	/**
	 * 引数のリーダースキルが、引数のメモリアに対して、妥当か判定します。
	 *
	 * @param leaderSkill
	 *            リーダースキル
	 * @param memoria
	 *            メモリア
	 * @param party
	 *            パーティ。null以外の場合、パーティに関する条件も確認します。nullの場合、パーティ以外の条件のみ確認します。
	 * @return 妥当な場合true
	 */
	private static boolean validCondition(LeaderSkill leaderSkill,
			Memoria memoria, Party party) {
		Validate.notNull(leaderSkill);
		Validate.notNull(memoria);

		if (memoria.getName().indexOf(leaderSkill.mMemoriaName) < 0) {
			return false;
		}

		//
		// イベントメモリア
		//
		if (memoria.getName().indexOf("ヴァニラ") >= 0) {
			// TODO ピクトロジカで【13マス】以下を塗ったときパーティーの「知性」が【微小】アップ
		} else if (memoria.getName().indexOf("アーシェ") >= 0) {
			// 編成時に自身の物理防御が【30pt】以上のときパーティーの「知性」が【微小】アップ
			if (memoria.getPhysicalDefence() >= 30) {
				return true;
			}
		} else if (memoria.getName().indexOf("セシル") >= 0) {
			// TODO パーティーに【騎士剣】装備が【3人】以上のときパーティーの「物理防御」が【微小】アップ
		} else if (memoria.getName().indexOf("セーラ(No.217)") >= 0) {
			// メメントが【祈り】のとき
			// パーティーの「回復効果」が【大】アップ
			return true;
		}
		//
		// 限定プレミアムメモリア
		//
		if (memoria.getName().indexOf("元帥シド") >= 0) {
			// パーティーの「無属性武器攻撃」が【大】アップ
			// 無属性（武器系） 37%
			return true;
		}
		//
		// プレミアムメモリア
		//
		if (memoria.getName().indexOf("アーロン") >= 0) {
			// 編成時に自身の物理防御が【30pt】以上のときパーティーの「HP」が【小】アップ
			if (memoria.getPhysicalDefence() >= 30) {
				return true;
			}
		} else if (memoria.getName().indexOf("パンネロ") >= 0) {
			// 編成時に自身の物理防御が【10pt】以下のとき
			// パーティーの「知性」が【小】アップ
			if (memoria.getPhysicalDefence() <= 10) {
				return true;
			}
		} else if (memoria.getName().indexOf("キマリ") >= 0) {
			// パーティーのHPが【70%】以上のとき
			// パーティーの「物理防御」が【小】アップ
			// ※発動条件簡略化
			return true;
		} else if (memoria.getName().indexOf("トレイ") >= 0) {
			// 攻撃人数が【4人】以上のときパーティーの「力」が【小】アップ
			if (party == null) {
				return true;
			} else if (party.getMemoriaList().size() >= 4) {
				return true;
			}
		} else if (memoria.getName().indexOf("ポロム") >= 0) {
			// メメントが【祈り】のとき
			// パーティーの「白魔法効果」が【小】アップ
			return true;
		} else if (memoria.getName().indexOf("ティナ") >= 0) {
			// TODO ブレイクゲージが【200%】以上のときパーティーの「氷属性効果」が【中】アップ
		} else if (memoria.getName().indexOf("ライトニング(No.119)") >= 0) {
			// TODO ピクトロジカで【13マス】以上を塗ったときパーティーの「雷属性効果」が【中】アップ
		} else if (memoria.getName().indexOf("マキナ") >= 0) {
			if (party == null) {
				return true;
			} else if (party.getMemoriaList().size() <= 4) {
				// TODO 攻撃人数が【4人】以下のときパーティーの「クリティカル率」が【小】アップ
			}
		}

		return false;
	}

	protected LeaderSkill(String name, int hp, Unit hpUnit, int power,
			Unit powerUnit, int speed, Unit speedUnit, int intelligence,
			Unit intelligenceUnit, int luck, Unit luckUnit,
			int physicalDefence, int magicDefence, Attr attr) {
		mMemoriaName = name;
		mItemData = new ItemData("", name + "LS", hp, hpUnit, power, powerUnit,
				speed, speedUnit, intelligence, intelligenceUnit, luck,
				luckUnit, physicalDefence, magicDefence, attr, null, 1);
		mEffect = new LeaderSkillEffect();
	}

	protected LeaderSkill(String name, LeaderSkillEffect effect) {
		mMemoriaName = name;
		mItemData = new ItemData("", name + "LS", 0, Unit.VALUE, 0, Unit.VALUE,
				0, Unit.VALUE, 0, Unit.VALUE, 0, Unit.VALUE, 0, 0, Attr.NONE,
				null, 1);
		mEffect = effect;
	}

	@Override
	public String toString() {
		return mItemData.getName();
	}

	public String getName() {
		return mItemData.getName();
	}

	public ItemData getItemData() {
		return mItemData;
	}

	/**
	 * 引数のパーティに対して、当リーダースキルを適用可能か判定します。
	 *
	 * @param party
	 * @return
	 */
	public boolean validCondition(Party party) {
		return validCondition(this, party);
	}

	/**
	 * 引数のメモリアに対して、当リーダースキルを適用可能か判定します。
	 *
	 * @param memoria
	 * @return
	 */
	public boolean validCondition(Memoria memoria) {
		return validCondition(this, memoria);
	}

	/**
	 * 物理攻撃効果を適用します。
	 *
	 * @param damage
	 *            リーダースキル適用前のダメージ
	 * @param memoria
	 *            メモリア
	 * @return ダメージ
	 */
	public float calcPhysicalAttackDamage(float damage, Memoria memoria) {
		return mEffect.calcPhysicalAttackDamage(damage, memoria);
	}

	/**
	 * 回復効果を適用します。
	 *
	 * @param recovery
	 *            リーダースキル適用前の回復値
	 * @return 回復値
	 */
	public float calcRecovery(float recovery) {
		return mEffect.calcRecovery(recovery);
	}

	/**
	 * 白魔法効果を適用します。
	 *
	 * @param recovery
	 *            リーダースキル適用前の回復値
	 * @return 回復値
	 */
	public float calcWhiteMagic(float recovery) {
		return mEffect.calcWhiteMagic(recovery);
	}

}
