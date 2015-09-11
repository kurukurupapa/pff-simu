package com.kurukurupapa.pffsimu.domain.fitness;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * メモリア適応度結果集合クラス
 */
public class MemoriaFitnessSet extends TreeSet<MemoriaFitness> {

	private static Comparator<MemoriaFitness> descCommparator = new Comparator<MemoriaFitness>() {
		/**
		 * 比較メソッド
		 *
		 * 適応度が同じ要素があったとしても、両方の要素を残したい。 そのため、なるべく返却を0以外にします。
		 */
		@Override
		public int compare(MemoriaFitness arg0, MemoriaFitness arg1) {
			int result;
			// 適応度の降順
			int value0 = arg0 == null ? 0 : arg0.getValue();
			int value1 = arg1 == null ? 0 : arg1.getValue();
			result = value1 - value0;
			// 文字列表現の昇順
			if (result == 0) {
				String str0 = arg0 == null ? "" : arg0.toString();
				String str1 = arg1 == null ? "" : arg1.toString();
				result = str0.compareTo(str1);
			}
			// 一致しなければ0以外返却
			if (result == 0) {
				int hash0 = arg0 == null ? 0 : arg0.hashCode();
				int hash1 = arg1 == null ? 0 : arg1.hashCode();
				result = hash0 - hash1;
			}
			return result;
		}
	};

	/**
	 * 適応度の降順となるSetを生成する。
	 *
	 * @return インスタンス
	 */
	public static MemoriaFitnessSet createAsFitnessDesc() {
		return new MemoriaFitnessSet(descCommparator);
	}

	/**
	 * プライベートコンストラクタ
	 */
	private MemoriaFitnessSet(Comparator<MemoriaFitness> comparator) {
		super(comparator);
	}

	public List<MemoriaFitness> toList() {
		return new ArrayList<MemoriaFitness>(this);
	}
}
