package com.kurukurupapa.pffsimu.domain.memoria;

import com.kurukurupapa.pffsimu.domain.Attr;
import com.kurukurupapa.pffsimu.domain.Unit;

/**
 * リーダースキル生成クラス
 */
public class LeaderSkillFactory {

	private static final LeaderSkill[] mLeaderSkills = {
			//
			// イベントメモリア
			//
			/** アーシェLS 知性微小（6%）アップ */
			new LeaderSkill("アーシェ", 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
					Unit.PERCENT, 6, Unit.PERCENT, 0, Unit.PERCENT, 0, 0,
					Attr.NONE),
			/** セーラ(No.217)LS パーティーの「回復効果」が【大】アップ（30%） */
			new LeaderSkill("セーラ(No.217)", new LeaderSkillRecoveryEffect(30)),
			//
			// 限定プレミアムメモリア
			//
			/** 元帥シドLS 「無属性武器攻撃」が【大】アップ（無属性（武器系） 37%） */
			new LeaderSkill("元帥シド", 0, Unit.PERCENT, 37, Unit.PERCENT, 0,
					Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0, 0,
					Attr.NONE),
			// new LeaderSkill("元帥シド", new LeaderSkillNoAttrWeaponEffect(37)),
			//
			// プレミアムメモリア
			//
			/** アーロンLS HP小（18%）アップ */
			new LeaderSkill("アーロン", 18, Unit.PERCENT, 0, Unit.PERCENT, 0,
					Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0, 0,
					Attr.NONE),
			/** パンネロLS パーティーの「知性」が【小】アップ（12%） */
			new LeaderSkill("パンネロ", 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
					Unit.PERCENT, 12, Unit.PERCENT, 0, Unit.PERCENT, 0, 0,
					Attr.NONE),
			/**
			 * キマリLS 物理防御小（16pt）アップ<br>
			 * ※発動条件は「パーティーのHPが【70%】以上のとき」であるが、処理を簡略化するため「バトル中の7割でHP70%以上である」
			 * と考える。
			 */
			new LeaderSkill("キマリ", 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
					Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT,
					(int) (16 * 0.7), 0, Attr.NONE),
			/** トレイLS 力小（10%）アップ */
			new LeaderSkill("トレイ", 0, Unit.PERCENT, 10, Unit.PERCENT, 0,
					Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0, 0,
					Attr.NONE),
			/** ポロムLS パーティーの「白魔法効果」が【小】アップ（15%） */
			new LeaderSkill("ポロム", new LeaderSkillWhiteMagicEffect(15)),
	//
	};

	public static LeaderSkill get(Memoria memoria) {
		return get(memoria.getName());
	}

	public static LeaderSkill get(MemoriaData memoriaData) {
		return get(memoriaData.getName());
	}

	public static LeaderSkill get(String memoriaName) {
		LeaderSkill leaderSkill = null;
		for (LeaderSkill e : mLeaderSkills) {
			if (memoriaName.indexOf(e.mMemoriaName) >= 0) {
				leaderSkill = e;
				break;
			}
		}
		return leaderSkill;
	}

}
