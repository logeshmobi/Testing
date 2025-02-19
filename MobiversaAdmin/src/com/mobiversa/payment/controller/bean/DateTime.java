package com.mobiversa.payment.controller.bean;

public class DateTime {
	private String date;
	private String time;

	public DateTime(String date, String time) {
		this.date = date;
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}
}
