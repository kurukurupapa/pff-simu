package com.kurukurupapa.pff.dp01;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kurukurupapa.pff.domain.ItemData;
import com.kurukurupapa.pff.domain.LeaderSkill;
import com.kurukurupapa.pff.domain.MemoriaData;

public class Party implements Cloneable {
	public static final int MAX_MEMORIAS = 4;

	protected List<Memoria> mMemoriaList;
	protected FitnessValue mFitnessValue;

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
		mFitnessValue = party.mFitnessValue;
	}

	@Override
	public Party clone() {
		return new Party(this);
	}

	@Override
	public String toString() {
		return toString(",");
	}

	public String toMultiLineString() {
		return toString("\n") + "\n";
	}

	public String toString(String sep) {
		StringBuilder sb = new StringBuilder();
		sb.append(mFitnessValue == null ? "null" : mFitnessValue.getValue());
		sb.append(sep);
		sb.append(StringUtils.join(mMemoriaList, sep));
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

	public boolean contains(MemoriaData memoriaData) {
		for (Memoria e : mMemoriaList) {
			if (e.getMemoriaData().isSame(memoriaData)) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(Memoria memoria) {
		for (Memoria e : mMemoriaList) {
			if (e.isSame(memoria)) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(ItemData itemData) {
		for (Memoria e : mMemoriaList) {
			if (e.isWeapon() && e.getWeapon() == itemData) {
				return true;
			}
			for (ItemData e2 : e.getAccessories()) {
				if (e2 == itemData) {
					return true;
				}
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
		int power = 0;
		if (mMemoriaList.size() > 0) {
			power = getTotalPower() / mMemoriaList.size();
		}
		return power;
	}

	public void clearWeaponAccessories() {
		for (Memoria e : mMemoriaList) {
			e.clearWeapon();
			e.clearAccessories();
		}
	}

	public int getFitness() {
		return mFitnessValue != null ? mFitnessValue.getValue() : 0;
	}

	public FitnessValue getFitnessObj() {
		return mFitnessValue;
	}

	public void calcFitness(FitnessCalculator fitnessCalculator) {
		calcLeaderSkill();
		mFitnessValue = fitnessCalculator.calc(this);
	}

	protected void calcLeaderSkill() {
		List<ItemData> list = new ArrayList<ItemData>();
		clearLeaderSkill();
		for (Memoria e : mMemoriaList) {
			// イベントメモリア
			if (e.getName().indexOf("ヴァニラ") >= 0) {
				// TODO ピクトロジカで【13マス】以下を塗ったときパーティーの「知性」が【微小】アップ
			} else if (e.getName().indexOf("アーシェ") >= 0) {
				// 編成時に自身の物理防御が【30pt】以上のときパーティーの「知性」が【微小】アップ
				if (e.getPhysicalDefence() >= 30) {
					list.add(LeaderSkill.LS117.getItemData());
				}
			} else if (e.getName().indexOf("セシル") >= 0) {
				// TODO パーティーに【騎士剣】装備が【3人】以上のときパーティーの「物理防御」が【微小】アップ
			}
			// 限定プレミアムメモリア
			if (e.getName().indexOf("元帥シド") >= 0) {
				// パーティーの「無属性武器攻撃」が【大】アップ
				// 無属性（武器系） 37%
				list.add(LeaderSkill.LS187.getItemData());
			}
			// プレミアムメモリア
			if (e.getName().indexOf("アーロン") >= 0) {
				// 編成時に自身の物理防御が【30pt】以上のときパーティーの「HP」が【小】アップ
				if (e.getPhysicalDefence() >= 30) {
					list.add(LeaderSkill.LS029.getItemData());
				}
			} else if (e.getName().indexOf("トレイ") >= 0) {
				// 攻撃人数が【4人】以上のときパーティーの「力」が【小】アップ
				if (mMemoriaList.size() >= 4) {
					list.add(LeaderSkill.LS052.getItemData());
				}
			} else if (e.getName().indexOf("ティナ") >= 0) {
				// TODO ブレイクゲージが【200%】以上のときパーティーの「氷属性効果」が【中】アップ
			} else if (e.getName().indexOf("ライトニング(No.119)") >= 0) {
				// TODO ピクトロジカで【13マス】以上を塗ったときパーティーの「雷属性効果」が【中】アップ
			} else if (e.getName().indexOf("マキナ") >= 0) {
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

	protected void addLeaderSkill(LeaderSkill leaderSkill) {
		addLeaderSkill(leaderSkill.getItemData());
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
