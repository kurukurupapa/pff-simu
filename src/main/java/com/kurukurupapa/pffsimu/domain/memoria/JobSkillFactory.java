package com.kurukurupapa.pffsimu.domain.memoria;

/**
 * ジョブスキルファクトリークラス
 */
public class JobSkillFactory {
	private static final JobSkill[] arr = new JobSkill[] { //
			// TODO オーバーパワー
			// 攻撃するたびに自分の力が5%ずつ増加する（最大増加値：50%）
			new JobSkillDummy("オーバーパワー"),

			// 居合い抜き
			// 旧
			//// 自身が一番初めの攻撃を行うと与ダメージ50%アップの居合い抜きを発動
			// new JobSkillPhysicalAttack("居合い抜き",
			// JobSkillCondition.FIRST_ATTACK, 1.5f),
			// 新
			// 自分が最初に出撃すると、必ず先制攻撃となる居合い抜きが発動する（攻撃力増加：80%）
			new JobSkillPhysicalAttack("居合い抜き", JobSkillCondition.FIRST_ATTACK, 1.8f),

			// TODO かばう
			// 自分より物防か魔防の低い仲間のダメージを引き受け、スキル発生時は被ダメージをすこし軽減する（発動率：50%）
			new JobSkillDummy("かばう"),

			// TODO 暗黒剣
			// 自分が敵を倒すたびにパーティーのHPが回復する（回復量：最大HPの20%）
			new JobSkillDummy("暗黒剣"),

			// ジャンプ
			// 旧
			// // ブレイク時に攻撃すると与ダメージ20%アップのジャンプを発動
			// // TODO とりあえず、1バトルの半分のターンをブレイク中と考える。
			// new JobSkillPhysicalAttack("ジャンプ", JobSkillCondition.NONE, 1.1f),
			// 新
			// ブレイク中に攻撃すると、対空属性のジャンプが発動する（攻撃力増加：40%）
			// TODO とりあえず、1バトルの半分のターンをブレイク中と考える。
			new JobSkillPhysicalAttack("ジャンプ", JobSkillCondition.NONE, (1f + 1.4f) / 2f),

			// 強斬り
			// ブレイク時に自身が最後の攻撃を行うと強斬りを発動（与ダメージ2倍）
			// TODO とりあえず、1バトルの半分のターンをブレイク中と考える。
			new JobSkillPhysicalAttack("強斬り", JobSkillCondition.LAST_ATTACK, (1f + 2f) / 2f),

			// TODO HP吸収
			// 敵の弱点を突くと、HPを吸収する攻撃を行う（与ダメージの18%）
			new JobSkillDummy("HP吸収"),

			// TODO 奥義
			// 敵にトドメを刺すと、リメントゲージが上昇（ブレイク中はブレイクゲージが回復）
			new JobSkillDummy("奥義"),

			// 盗む
			// クエスト終了時に全ドロップアイテムの中から1つを盗む 成功率：50%
			new JobSkillDummy("盗む"),

			// 分身
			// 自身が一番初めの攻撃を行うと分身し、自身への攻撃を必ず回避
			// 分身は一度回避すると消滅。持続時間：1ターン
			new JobSkillBunshin(),

			// TODO マントラ
			// パーティー全体の回復効果が10%上昇
			new JobSkillDummy("マントラ"),

			// ためる
			// 自分が一番最後の攻撃をすると防御無視の溜め攻撃を発動する
			// (攻撃力増加：40％)
			new JobSkillPower("ためる", JobSkillCondition.LAST_ATTACK, 1.4f),

			// オーバーラッシュ
			// ブレイク中に力メメントで攻撃すると、初回のみオーバーラッシュが発動する（与ダメージ2.8倍）
			// (限界突破：+5000)
			// (発動条件：リメントオーバー250%以上)
			new JobSkillOverRush(),

			// TODO 連続魔
			// スロットにセットされた同じ魔法を全て連続で使用する
			// アビリティチャージ：セットされた合計の90%
			new JobSkillDummy("連続魔"),

			// TODO 魔法剣
			// 力メメントに炎・氷・雷属性の魔法アビリティをセットすると魔法剣を放つ
			new JobSkillDummy("魔法剣"),

			// オーラ
			// 自身の白魔法アビリティ効果が上昇（回復量1.8倍 他の白魔法にも効果あり）
			new JobSkillRecoveryMagic("オーラ", JobSkillCondition.NONE, 1.8f),

			// TODO 女神の祝福
			// 白魔法アビリティのチャージが減少する
			// （現象効果：15%）
			new JobSkillDummy("女神の祝福"),

			// ホーリー
			// ブレイクゲージ200%以上の時に祈りメメントで攻撃すると初回のみホーリーを発動（対象：単体、威力100）
			new JobSkillWhiteMagicAttack("ホーリー", JobSkillCondition.BREAK_ONE, 100),

			// TODO 調合
			// 自身のスロットに2つ以上のアビリティをセットするとターン開始時に薬を調合
			// 調合成功率：50%
			new JobSkillDummy("調合"),

			// 黒魔道士
			// TODO 魔力の泉
			// 黒魔法アビリティのチャージが減少する
			// （現象効果：15%）
			new JobSkillDummy("魔力の泉"),

			// TODO ファストキャスト
			// ブレイク中、黒魔法アビリティのパズルによるチャージ量が2倍になる。
			new JobSkillDummy("ファストキャスト"),

			// 魔人
			// フレア
			// ブレイクゲージ200%以上の時に知恵メメントで攻撃すると初回のみフレアを発動（対象：単体、威力100）
			new JobSkillBlackMagicAttack("フレア", JobSkillCondition.BREAK_ONE, 100),

			// 算術士
			// TODO 算術
			// 自身がいる列の問題数字の合計番目に攻撃を行うとリメントゲージが上昇
			// （ブレイク中はブレイクゲージが回復）
			new JobSkillDummy("算術"),

			// 狩人
			// 乱れ撃ち
			// 自身が一番初めの攻撃を行うと2～4回の連続攻撃を発動。1回あたりの与ダメージは通常の60%。
			// ※平均3回攻撃と考えます。
			new JobSkillPhysicalAttack("乱れ撃ち", JobSkillCondition.FIRST_ATTACK, 3 * 0.60f),

			// 砲撃士
			// TODO 砲撃
			// バトル開始時に砲撃による全体攻撃を行う
			// （効果値：30×砲撃成功数＋砲撃成功した中で一番高いHP(max時)/20）
			// パーティーのHPが少ないほど威力アップ
			new JobSkillDummy("砲撃"),

			// バーサーカー
			// TODO バーサク
			// 攻撃対象が制御ができない状態で攻撃力が増加する
			// （攻撃力増加：50%）
			new JobSkillPhysicalAttack("バーサク", JobSkillCondition.NONE, 1.5f),

			// バイキング
			// TODO あらくれ
			// 自身が攻撃すると、対象に雷の弱点属性を与える
			// 成功率：30%
			new JobSkillDummy("あらくれ"),

			// 機工士
			// TODO 狙撃
			// 自身が攻撃すると、対象に炎の弱点属性を与える
			// 成功率：30%
			new JobSkillDummy("狙撃"),

			// スナイパー
			// TODO ファストブースト
			// 自分が最後に出撃すると、ターン開始時にランダムに３連続射撃（1回あたりの与ダメージは通常の30%）
			// 属性効果アップのアクセサリを付けていると、ファストブーストはその属性に変わる。
			// 例えば、ウィザードリングを装備すると、雷・炎・氷の３属性の攻撃に変わる。
			new JobSkillPhysicalAttack("ファストブースト", JobSkillCondition.LAST_ATTACK, 1f + 0.3f * 3f),

			// ギャンブラー
			// TODO スロット
			// ブレイク中に自身が一番初めの攻撃を行うとスロットを発動
			new JobSkillDummy("スロット"),

			// 幻術師
			// TODO アストラルフロウ
			// 召喚魔法アビリティのチャージが減少する
			// （減少効果：15%）
			new JobSkillDummy("アストラルフロウ"),

			// 召喚士
			// TODO バハムート
			// ブレイク中に力メメントで攻撃すると、初回のみバハムートを召喚する
			// （発動条件：リメントオーバー値200%以上）
			new JobSkillDummy("バハムート"),

			// 踊り子
			// TODO 踊る
			// 自身が攻撃すると、踊りによる様々な特殊攻撃を行う
			// 成功率：75%
			new JobSkillDummy("踊る"),

			// 曲芸士
			// TODO なげる
			// 自身の攻撃時にクリティカルが発生するとなげるによる様々な特殊攻撃を行う
			new JobSkillDummy("なげる"),

			// 吟遊詩人
			// TODO うたう
			// 装備する竪琴によってバトル開始時に様々な歌をうたう
			// 敵に攻撃を受けると効果消滅 効果時間3ターン
			new JobSkillDummy("うたう"),

			//
	};

	public JobSkill get(String name) {
		for (JobSkill e : arr) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return new JobSkillDummy(name);
	}

}
