package com.mobiversa.payment.controller.bean;

public class UpdateInfoEzysettle {
	private final String finalipdate;
	private final double ezysettleFee;

	public UpdateInfoEzysettle(String finalipdate, double ezysettleFee) {
		this.finalipdate = finalipdate;
		this.ezysettleFee = ezysettleFee;
	}

	public String getFinalipdate() {
		return finalipdate;
	}

	public double getEzysettleFee() {
		return ezysettleFee;
	}
}
