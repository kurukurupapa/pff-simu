package com.kurukurupapa.pff.dp01;

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
		// 適応度計算
		super.calcFitness(fitnessCalculator);

		// リーダースキルとアイテムの相性もあるので、最大適応度を大目します。
		// TODO 正確に判断することはできる？
		mFitnessValue.setValue((int) (mFitnessValue.getValue() * 1.5));
	}

}
