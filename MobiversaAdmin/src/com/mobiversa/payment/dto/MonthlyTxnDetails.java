package com.mobiversa.payment.dto;

public class MonthlyTxnDetails {
	
	private String tid;
	private String mid;
	private String date;
	private String merchantName;
	private String deviceId;
	private String amount;
	private String noofTxn;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getNoofTxn() {
		return noofTxn;
	}
	public void setNoofTxn(String noofTxn) {
		this.noofTxn = noofTxn;
	}
	
	

}
