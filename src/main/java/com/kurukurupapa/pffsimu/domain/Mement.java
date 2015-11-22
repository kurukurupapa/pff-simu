package com.kurukurupapa.pffsimu.domain;

/**
 * メメントクラス
 */
public class Mement {

	/** 力メメントの物理倍率 */
	public static final float CHIKARA_PHYSICAL_RATE = 1.2f;
	/** 知恵メメントの黒魔法倍率 */
	public static final float CHIE_BLACK_RATE = 1.2f;
	/** 祈りメメントの白魔法倍率 */
	public static final float INORI_WHITE_RATE = 1.25f;
	/** 守りメメントの被物理倍率 */
	public static final float MAMORI_PHYSICAL_DEFENCE_RATE = 0.4f;

	/** 力メメントのリメントゲージ上昇率（メモリアの攻撃1回あたりの増加比率）（%） */
	public static final float CHIKARA_REMENT_RATE = 4.4f;
	/** 知恵メメントのリメントゲージ上昇率（メモリアの攻撃1回あたりの増加比率）（%） */
	public static final float CHIE_REMENT_RATE = 11.0f;
	/** 力メメントのブレイクゲージ上昇率（パズル1マスあたりのブレイクゲージの上昇率）（%） */
	public static final float CHIKARA_BREAK_RATE = 0.40f;
	/** 知恵メメントのブレイクゲージ上昇率（パズル1マスあたりのブレイクゲージの上昇率）（%） */
	public static final float CHIE_BREAK_RATE = 4.65f;

}
