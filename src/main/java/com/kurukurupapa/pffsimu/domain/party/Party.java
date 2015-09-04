package com.kurukurupapa.pffsimu.domain.party;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kurukurupapa.pffsimu.domain.fitness.FitnessCalculator;
import com.kurukurupapa.pffsimu.domain.fitness.FitnessValue;
import com.kurukurupapa.pffsimu.domain.item.ItemData;
import com.kurukurupapa.pffsimu.domain.memoria.LeaderSkill;
import com.kurukurupapa.pffsimu.domain.memoria.Memoria;
import com.kurukurupapa.pffsimu.domain.memoria.MemoriaData;

public class Party implements Cloneable {
	public static final int MAX_MEMORIAS = 4;

	protected List<Memoria> mMemoriaList;
	protected FitnessValue mFitnessValue = new FitnessValue();

	public static Party max(Party arg1, Party arg2) {
		if (arg1 == null) {
			return arg2;
		}
		if (arg2 == null) {
			return arg1;
		}
		if (arg1.getFitness() < arg2.getFitness()) {
			return arg2;
		} else {
			return arg2;
		}
	}

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
		calcLeaderSkill(fitnessCalculator);
		calcJobSkill(fitnessCalculator);
	}

	private void calcLeaderSkill(FitnessCalculator fitnessCalculator) {
		// 全リーダースキルを各々適用してみて最大適応度のリーダースキルを求めます。
		clearLeaderSkill();
		LeaderSkill maxLeaderSkill = null;
		FitnessValue maxFitnessValue = fitnessCalculator.calc(this);
		for (Memoria e : mMemoriaList) {
			// 当該メモリアのリーダースキルが適用可能か調べます。
			LeaderSkill leaderSkill = LeaderSkill.parse(e.getMemoriaData());
			if (leaderSkill == null || !leaderSkill.validCondition(this)) {
				continue;
			}

			// リーダスキルを適用して、適応度を求めます。
			setLeaderSkill(leaderSkill);
			FitnessValue fitnessValue = fitnessCalculator.calc(this);
			if (maxFitnessValue == null
					|| maxFitnessValue.getValue() < fitnessValue.getValue()) {
				maxFitnessValue = fitnessValue;
				maxLeaderSkill = leaderSkill;
			}
		}

		// 最大適応度のリーダースキルを反映します。
		setLeaderSkill(maxLeaderSkill);
		mFitnessValue = maxFitnessValue;
	}

	private void calcJobSkill(FitnessCalculator fitnessCalculator) {
		// 排他的なジョブスキルを洗い出す。
		// 排他的でないジョブスキルを有効にする。
		List<Memoria> firstAttackMemorias = new ArrayList<Memoria>();
		for (Memoria e : mMemoriaList) {
			if (e.getMemoriaData().hasJobSkill()) {
				if (e.getMemoriaData().getJobSkill().isFirstAttackCondition()) {
					firstAttackMemorias.add(e);
					e.disableJobSkill();
				} else {
					e.enableJobSkill();
				}
			} else {
				e.disableJobSkill();
			}
		}

		// 一番始めの攻撃を条件とするジョブスキルを適用してみて、
		// 最大適応度となるメモリアのジョブスキルを求める。
		Memoria maxFirstAttackMemoria = null;
		FitnessValue maxFitnessValue = null;
		for (Memoria e : firstAttackMemorias) {
			e.enableJobSkill();

			FitnessValue fitnessValue = fitnessCalculator.calc(this);
			if (maxFitnessValue == null
					|| maxFitnessValue.getValue() < fitnessValue.getValue()) {
				maxFirstAttackMemoria = e;
				maxFitnessValue = fitnessValue;
			}

			e.disableJobSkill();
		}

		// 最大適応度のジョブスキルを反映します。
		if (maxFirstAttackMemoria != null) {
			maxFirstAttackMemoria.enableJobSkill();
			mFitnessValue = maxFitnessValue;
		} else {
			mFitnessValue = fitnessCalculator.calc(this);
		}
	}

	public void setLeaderSkill(LeaderSkill leaderSkill) {
		for (Memoria e : mMemoriaList) {
			e.setLeaderSkill(leaderSkill);
		}
	}

	protected void clearLeaderSkill() {
		for (Memoria e : mMemoriaList) {
			e.clearLeaderSkill();
		}
	}

}
