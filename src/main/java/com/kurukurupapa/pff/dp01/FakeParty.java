package com.kurukurupapa.pff.dp01;

import com.kurukurupapa.pff.domain.LeaderSkill;

/**
 * 疑似パーティクラス
 * 
 * 大雑把な最大適応度を計算するための疑似的なパーティクラスです。
 */
public class FakeParty extends Party {

	public FakeParty(Party party, MemoriaFitness memoriaFitness) {
		super(party);
		add(memoriaFitness.getMemoria());
	}

	@Override
	public void calcFitness(FitnessCalculator fitnessCalculator) {
		// 適用される可能性があるリーダースキルをすべて付加します。
		clearLeaderSkill();
		for (Memoria e : mMemoriaList) {
			LeaderSkill leaderSkill = LeaderSkill.parse(e.getName());
			if (leaderSkill != null) {
				addLeaderSkill(leaderSkill);
			}
		}

		// 適応度計算
		mFitnessValue = fitnessCalculator.calc(this);

		// リーダースキルとアイテムの相性もあるので、最大適応度を大目します。
		// TODO 正確に判断することはできる？
		mFitnessValue.setValue((int) (mFitnessValue.getValue() * 1.5));
	}

}
