package com.kurukurupapa.pffsimu.domain;

/**
 * ブレイククラス
 * 
 * 「REゲージ」「ブレイクゲージ」などを扱うクラスです。
 */
public class Brake {

	/**
	 * RE増加率(/攻撃回数)
	 * <ul>
	 * <li>力メメント： 4.4%
	 * <li>知恵メメント： 11.0%
	 * <li>祈りメメント： 5.5%
	 * <li>守りメメント： 4.0%
	 * <ul>
	 * →基本的に、力と知恵メメントを使うの思うので、この2つの平均で考えます。
	 */
	private static final float RE_RATE = (4.4f + 11.0f) / 2 / 100;

	/**
	 * 1ターンあたりの時間（秒）
	 * 
	 * とりあえず10秒ぐらい。
	 */
	private static final int SEC_PER_TURN = 10;

	// ブレイクゲージ上昇率(％)
	// 力メメント：0.40
	// 知恵メメント：4.65
	// 祈りメメント：1.40
	// 守りメメント：1.40
	// →とりあえず平均で考える。1.96

	/**
	 * REゲージ100%までのターン数を取得します。
	 * 
	 * @param numMemorias
	 *            メモリアの数
	 * @return ターン数。このターン数の後から、ブレイクとなります。
	 */
	public int getReTurnNum(int numMemorias) {
		return (int) Math.ceil(1.0f / RE_RATE / numMemorias);
	}

	/**
	 * ブレイクのターン数を取得します。
	 * 
	 * @param speed
	 *            パーティ全体の素早さ
	 * @return ターン数。
	 */
	public int getBreakTurnNum(int speed) {
		// ブレイク時間(秒)＝35秒＋素早さ×0.025秒
		int breakSec = (int) (35 + speed * 0.025f);
		return breakSec / SEC_PER_TURN;
	}
}
