package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kurukurupapa.pff.domain.Attr;
import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.MemoriaData;
import com.kurukurupapa.pff.domain.Unit;

public class Party implements Cloneable {
	public static final int MAX_MEMORIAS = 4;

	private List<Memoria> mMemoriaList;
	private FitnessValue mFitness;

	public Party() {
		mMemoriaList = new ArrayList<Memoria>();
	}

	public Party(Memoria memorias) {
		mMemoriaList = new ArrayList<Memoria>();
		mMemoriaList.add(memorias);
	}

	public Party(List<Memoria> memoriaList) {
		mMemoriaList = memoriaList;
	}

	public Party(Party party) {
		this();
		for (int i = 0; i < party.mMemoriaList.size(); i++) {
			Memoria memoria = party.mMemoriaList.get(i);
			add(memoria.clone());
		}
		mFitness = party.mFitness;
	}

	@Override
	public Party clone() {
		return new Party(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(mFitness == null ? "null" : mFitness.getValue());
		sb.append("," + StringUtils.join(mMemoriaList, ","));
		return sb.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Party)) {
			return false;
		}
		Party other = (Party) obj;
		if (size() != other.size()) {
			return false;
		}
		for (int i = 0; i < size(); i++) {
			if (!mMemoriaList.get(i).equals(other.mMemoriaList.get(i))) {
				return false;
			}
		}
		return true;
	}

	public void add(Memoria memoria) {
		mMemoriaList.add(memoria);
	}

	public void add(MemoriaData memoria) {
		add(new Memoria(memoria));
	}

	public void remove(int index) {
		mMemoriaList.remove(index);
	}

	public List<Memoria> getMemoriaList() {
		return mMemoriaList;
	}

	public boolean contains(Memoria memoria) {
		for (Memoria e : mMemoriaList) {
			if (e.isSame(memoria)) {
				return true;
			}
		}
		return false;
	}

	public int size() {
		return mMemoriaList.size();
	}

	public Memoria getMemoria(int index) {
		return mMemoriaList.get(index);
	}

	public int getTotalPower() {
		int power = 0;
		for (Memoria e : mMemoriaList) {
			power += e.getPower();
		}
		return power;
	}

	public int getAveragePower() {
		return getTotalPower() / mMemoriaList.size();
	}

	public void clearWeaponAccessories() {
		for (Memoria e : mMemoriaList) {
			e.clearWeapon();
			e.clearAccessories();
		}
	}

	public int getFitness() {
		return mFitness != null ? mFitness.getValue() : 0;
	}

	public FitnessValue getFitnessObj() {
		return mFitness;
	}

	public void calcFitness(Fitness fitness) {
		calcLeaderSkill();
		mFitness = fitness.calc(this);
	}

	protected void calcLeaderSkill() {
		List<ItemData> list = new ArrayList<ItemData>();
		clearLeaderSkill();
		for (Memoria e : mMemoriaList) {
			// イベントメモリア
			if (e.getName().equals("ヴァニラ")) {
				// TODO ピクトロジカで【13マス】以下を塗ったときパーティーの「知性」が【微小】アップ
			} else if (e.getName().equals("アーシェ")) {
				// 編成時に自身の物理防御が【30pt】以上のときパーティーの「知性」が【微小】アップ
				if (e.getPhysicalDefence() >= 30) {
					list.add(new ItemData("", "アーシェLS", 0, Unit.PERCENT, 0,
							Unit.PERCENT, 0, Unit.PERCENT, 6, Unit.PERCENT, 0,
							Unit.PERCENT, 0, 0, Attr.NONE, null, 1));
				}
			} else if (e.getName().equals("セシル")) {
				// TODO パーティーに【騎士剣】装備が【3人】以上のときパーティーの「物理防御」が【微小】アップ
			}
			// 限定プレミアムメモリア
			if (e.getName().equals("元帥シド")) {
				// TODO パーティーの「無属性武器攻撃」が【大】アップ
			}
			// プレミアムメモリア
			if (e.getName().equals("アーロン")) {
				// 編成時に自身の物理防御が【30pt】以上のときパーティーの「HP」が【小】アップ
				if (e.getPhysicalDefence() >= 30) {
					list.add(new ItemData("", "アーロンLS", 18, Unit.PERCENT, 0,
							Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
							Unit.PERCENT, 0, 0, Attr.NONE, null, 1));
				}
			} else if (e.getName().equals("トレイ")) {
				// 攻撃人数が【4人】以上のときパーティーの「力」が【小】アップ
				if (mMemoriaList.size() >= 4) {
					list.add(new ItemData("", "トレイLS", 0, Unit.PERCENT, 10,
							Unit.PERCENT, 0, Unit.PERCENT, 0, Unit.PERCENT, 0,
							Unit.PERCENT, 0, 0, Attr.NONE, null, 1));
				}
			} else if (e.getName().equals("ティナ")) {
				// TODO ブレイクゲージが【200%】以上のときパーティーの「氷属性効果」が【中】アップ
			} else if (e.getName().equals("ライトニング(No.119)")) {
				// TODO ピクトロジカで【13マス】以上を塗ったときパーティーの「雷属性効果」が【中】アップ
			} else if (e.getName().equals("マキナ")) {
				if (mMemoriaList.size() <= 4) {
					// TODO 攻撃人数が【4人】以下のときパーティーの「クリティカル率」が【小】アップ
				}
			}
		}
		addLeaderSkill(list);
	}

	protected void addLeaderSkill(List<ItemData> leaderSkillList) {
		for (ItemData e : leaderSkillList) {
			addLeaderSkill(e);
		}
	}

	protected void addLeaderSkill(ItemData leaderSkill) {
		for (Memoria e : mMemoriaList) {
			e.addLeaderSkill(leaderSkill);
		}
	}

	protected void clearLeaderSkill() {
		for (Memoria e : mMemoriaList) {
			e.clearLeaderSkill();
		}
	}

}
