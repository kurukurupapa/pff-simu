package com.kurukurupapa.pffsimu.web.partymaker;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * パーティメーカー機能 検討対象選択フォームクラス
 */
public class TargetForm {
	public enum Operation {
		ADD, EDIT
	}

	public enum Kind {
		MEMORIA, WEAPON, ACCESSORY1, ACCESSORY2;
		public String getName() {
			return name();
		}
	}

	@NotEmpty
	private String btn;

	private Operation operation;
	private Kind kind;
	private int index;

	public String getBtn() {
		return btn;
	}

	public void setBtn(String btn) {
		this.btn = btn;
	}

	public void parse() {
		String[] arr = btn.split(",");
		operation = Operation.valueOf(arr[0]);
		kind = Kind.valueOf(arr[1]);
		index = Integer.parseInt(arr[2]);
	}

	public Operation getOperation() {
		return operation;
	}

	public Kind getKind() {
		return kind;
	}

	public int getIndex() {
		return index;
	}
}
