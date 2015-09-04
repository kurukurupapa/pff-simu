package com.kurukurupapa.pffsimu.domain.partyfinder.impl2;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * 疑似パーティ集合クラス
 * <p>
 * 適応度の降順でソートします。
 * </p>
 */
public class FakePartySet extends TreeSet<FakeParty> {

	/** シリアルバージョンID */
	private static final long serialVersionUID = 1L;

	public FakePartySet() {
		super(new Comparator<FakeParty>() {
			/**
			 * 比較メソッド
			 * 
			 * 適応度が同じ要素があったとしても、パーティを構成する際に、妥当性が変わるので、両方の要素を残したい。
			 * そのため、なるべく返却を0以外にします。
			 */
			@Override
			public int compare(FakeParty arg0, FakeParty arg1) {
				int result;
				// 適応度の降順
				result = arg1.getFitness() - arg0.getFitness();
				// 文字列表現の昇順
				if (result == 0) {
					result = arg0.toString().compareTo(arg1.toString());
				}
				// 一致しなければ0以外返却
				if (result == 0) {
					result = arg0.hashCode() - arg1.hashCode();
				}
				return result;
			}
		});
	}
}
