package com.mobiversa.payment.dto;

public class PreAuthTxnDet {
	private String amount;
	private String contactName;
	private String tid;
	private String motoMid;
	private String phno;
	private String referrence;
	private String expectedDate;
	private String email;
	private String longitude;
	private String latitude;
	private String period;
	private String totalAmount;
	private String installmentCount;
	private String hostType;

	private String sessionid;
	private String trxid;
	
	private String phncode1;

	public String getPhncode1() {
		return phncode1;
	}

	public void setPhncode1(String phncode1) {
		this.phncode1 = phncode1;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public String getTrxid() {
		return trxid;
	}

	public void setTrxid(String trxid) {
		this.trxid = trxid;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getInstallmentCount() {
		return installmentCount;
	}

	public void setInstallmentCount(String installmentCount) {
		this.installmentCount = installmentCount;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getExpectedDate() {
		return expectedDate;
	}

	public void setExpectedDate(String expectedDate) {
		this.expectedDate = expectedDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getMotoMid() {
		return motoMid;
	}

	public void setMotoMid(String motoMid) {
		this.motoMid = motoMid;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getReferrence() {
		return referrence;
	}

	public void setReferrence(String referrence) {
		this.referrence = referrence;
	}

	public String getHostType() {
		return hostType;
	}

	public void setHostType(String hostType) {
		this.hostType = hostType;
	}

}
