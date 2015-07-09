package com.kurukurupapa.pff.dp01;

import org.apache.log4j.Logger;

import com.kurukurupapa.pff.domain.ItemDataSet;
import com.kurukurupapa.pff.domain.MemoriaDataSet;

/**
 * 動的計画法を用いて、ピクトロジカファイナルファンタジーの最強パーティを求める！
 */
public class Dp01Main {

	/**
	 * ロガー
	 */
	private Logger logger;

	/**
	 * メインメソッドです。
	 *
	 * @param args
	 *            実行時引数
	 */
	public static void main(String[] args) {
		Dp01Main dp01Main = new Dp01Main();
		dp01Main.run(args);
	}

	/**
	 * コンストラクタ
	 */
	public Dp01Main() {
		logger = Logger.getLogger(Dp01Main.class);
	}

	/**
	 * 処理を実行します。
	 *
	 * @param args
	 *            実行時引数
	 */
	public void run(String[] args) {
		logger.trace("処理開始");

		// データ読み込み
		ItemDataSet itemDataSet = new ItemDataSet();
		itemDataSet.read();
		MemoriaDataSet memoriaDataSet = new MemoriaDataSet(itemDataSet);
		memoriaDataSet.read();

		// 実行
		// Fitness fitness = new FitnessForHp();
		// Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, fitness);
		// dp.run();
		// System.out.println(dp.getParty());

		FitnessCalculator[] fitnessCalculators = new FitnessCalculator[] { //
		FitnessCalculatorFactory.createForHp(), //
				FitnessCalculatorFactory.createForAttack(), //
				FitnessCalculatorFactory.createForRecovery(), //
				FitnessCalculatorFactory.createForBattle(), //
		};
		for (FitnessCalculator e : fitnessCalculators) {
			System.out.println(e.getName());
			Dp01 dp = new Dp01(memoriaDataSet, itemDataSet, e);
			dp.run();
			System.out.println(dp.getParty());
		}

		logger.trace("処理終了");
	}

}
