package com.mobiversa.payment.dto;

import java.util.List;

public class EditMdrResponse {

	private List<String> type;
	private EditMDRDetailsBean mdrDetails;
	private String mid;

	public List<String> getType() {
		return this.type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public EditMDRDetailsBean getMdrDetails() {
		return this.mdrDetails;
	}

	public void setMdrDetails(EditMDRDetailsBean mdrDetails) {
		this.mdrDetails = mdrDetails;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "EditMdrResponse [type=" + type + ", mdrDetails=" + mdrDetails + ", mid=" + mid + "]";
	}

}
