package com.kurukurupapa.pff.domain;

import org.apache.commons.lang3.Validate;

import com.kurukurupapa.pff.dp01.Memoria;
import com.kurukurupapa.pff.dp01.Party;

/**
 * リーダースキルクラス
 */
public enum LeaderSkill {
	/** アーロンLS HP小（18%）アップ */
	LS029("アーロン", 18, Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	/** トレイLS 力小（10%）アップ */
	LS052("トレイ", 0, Unit.PERCENT, 10, Unit.PERCENT, 0, Unit.PERCENT, 0,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	/** アーシェLS 知性微小（6%）アップ */
	LS117("アーシェ", 0, Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 6,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	/** 元帥シド 「無属性武器攻撃」が【大】アップ（無属性（武器系） 37%） */
	LS187("元帥シド", 0, Unit.PERCENT, 37, Unit.PERCENT, 0, Unit.PERCENT, 0,
			Unit.PERCENT, 0, Unit.PERCENT, 0, 0, Attr.NONE),
	//
	;

	private String mMemoriaName;
	private ItemData mItemData;

	public static LeaderSkill parse(Memoria memoria) {
		return parse(memoria.getName());
	}

	public static LeaderSkill parse(MemoriaData memoriaData) {
		return parse(memoriaData.getName());
	}

	public static LeaderSkill parse(String memoriaName) {
		LeaderSkill leaderSkill = null;
		for (LeaderSkill e : values()) {
			if (memoriaName.indexOf(e.mMemoriaName) >= 0) {
				leaderSkill = e;
				break;
			}
		}
		return leaderSkill;
	}

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
	public static boolean validStructue(Party party) {
		// 全メモリアについて、リーダースキルなし、または同一リーダースキルであること。
		LeaderSkill leaderSkill = null;
		for (Memoria e : party.getMemoriaList()) {
			LeaderSkill ls = e.getLeaderSkill();
			if (ls == null) {
				continue;
			}
			if (leaderSkill == null) {
				leaderSkill = ls;
			} else if (!leaderSkill.equals(ls)) {
				return false;
			}
		}

		// パーティにリーダースキルが設定されていなければ終了
		if (leaderSkill == null) {
			return true;
		}

		// 上記で取得したリーダースキルが適用可能であること。
		return valid(leaderSkill, party);
	}

	/**
	 * 引数のリーダースキルを、引数のパーティに対して、妥当か判定します。
	 * 
	 * @param leaderSkill
	 *            リーダースキル
	 * @param party
	 *            パーティ
	 * @return 妥当な場合true
	 */
	public static boolean valid(LeaderSkill leaderSkill, Party party) {
		Validate.notNull(leaderSkill);

		// 上記で取得したリーダースキルが適用可能であること。
		for (Memoria e : party.getMemoriaList()) {
			//
			// イベントメモリア
			//
			if (e.getName().indexOf("ヴァニラ") >= 0) {
				// TODO ピクトロジカで【13マス】以下を塗ったときパーティーの「知性」が【微小】アップ
			} else if (e.getName().indexOf("アーシェ") >= 0) {
				// 編成時に自身の物理防御が【30pt】以上のときパーティーの「知性」が【微小】アップ
				if (e.getPhysicalDefence() >= 30) {
					if (LS117.equals(leaderSkill)) {
						return true;
					}
				}
			} else if (e.getName().indexOf("セシル") >= 0) {
				// TODO パーティーに【騎士剣】装備が【3人】以上のときパーティーの「物理防御」が【微小】アップ
			}
			//
			// 限定プレミアムメモリア
			//
			if (e.getName().indexOf("元帥シド") >= 0) {
				// パーティーの「無属性武器攻撃」が【大】アップ
				// 無属性（武器系） 37%
				if (LS187.equals(leaderSkill)) {
					return true;
				}
			}
			//
			// プレミアムメモリア
			//
			if (e.getName().indexOf("アーロン") >= 0) {
				// 編成時に自身の物理防御が【30pt】以上のときパーティーの「HP」が【小】アップ
				if (e.getPhysicalDefence() >= 30) {
					if (LS029.equals(leaderSkill)) {
						return true;
					}
				}
			} else if (e.getName().indexOf("トレイ") >= 0) {
				// 攻撃人数が【4人】以上のときパーティーの「力」が【小】アップ
				if (party.getMemoriaList().size() >= 4) {
					if (LS052.equals(leaderSkill)) {
						return true;
					}
				}
			} else if (e.getName().indexOf("ティナ") >= 0) {
				// TODO ブレイクゲージが【200%】以上のときパーティーの「氷属性効果」が【中】アップ
			} else if (e.getName().indexOf("ライトニング(No.119)") >= 0) {
				// TODO ピクトロジカで【13マス】以上を塗ったときパーティーの「雷属性効果」が【中】アップ
			} else if (e.getName().indexOf("マキナ") >= 0) {
				if (party.getMemoriaList().size() <= 4) {
					// TODO 攻撃人数が【4人】以下のときパーティーの「クリティカル率」が【小】アップ
				}
			}
		}

		return false;
	}

	private LeaderSkill(String name, int hp, Unit hpUnit, int power,
			Unit powerUnit, int speed, Unit speedUnit, int intelligence,
			Unit intelligenceUnit, int luck, Unit luckUnit,
			int physicalDefence, int magicDefence, Attr attr) {
		mMemoriaName = name;
		mItemData = new ItemData("", name + "LS", hp, hpUnit, power, powerUnit,
				speed, speedUnit, intelligence, intelligenceUnit, luck,
				luckUnit, physicalDefence, magicDefence, attr, null, 1);
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
	public boolean valid(Party party) {
		return valid(this, party);
	}

}
