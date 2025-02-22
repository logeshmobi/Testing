package com.postmark.java;

public class TempletFields {

	private String firstName;
	private String lastName;
	private String date;
	private String time;
	private String userName;
	private String password;
	private String salutation;
	private String activationCode;
	// new field added for promotion email preview 10072017

	private String promoName;
	private String promoCode;
	private String promoDesc;
	private String promoImg1;
	private String promoImg2;

	private String ezywayMid;
	private String ezywayApiKey;
	private String ezywayTid;

	private String ezyrecMid;
	private String ezypodApiKey;
	private String ezyrecTid;
	private String emailType;
	private String toAddress;
	private boolean ezyPOD;

	private String mid;
	private String maskedPan;
	private String refNo;
	private String amount;
	private String MID;

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMaskedPan() {
		return maskedPan;
	}

	public void setMaskedPan(String maskedPan) {
		this.maskedPan = maskedPan;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public boolean isEzyPOD() {
		return ezyPOD;
	}

	public void setEzyPOD(boolean ezyPOD) {
		this.ezyPOD = ezyPOD;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public String getEzyrecMid() {
		return ezyrecMid;
	}

	public void setEzyrecMid(String ezyrecMid) {
		this.ezyrecMid = ezyrecMid;
	}

	public String getEzyrecTid() {
		return ezyrecTid;
	}

	public void setEzyrecTid(String ezyrecTid) {
		this.ezyrecTid = ezyrecTid;
	}

	public String getEzypodApiKey() {
		return ezypodApiKey;
	}

	public void setEzypodApiKey(String ezypodApiKey) {
		this.ezypodApiKey = ezypodApiKey;
	}

	public String getEzywayMid() {
		return ezywayMid;
	}

	public void setEzywayMid(String ezywayMid) {
		this.ezywayMid = ezywayMid;
	}

	public String getEzywayApiKey() {
		return ezywayApiKey;
	}

	public void setEzywayApiKey(String ezywayApiKey) {
		this.ezywayApiKey = ezywayApiKey;
	}

	public String getEzywayTid() {
		return ezywayTid;
	}

	public void setEzywayTid(String ezywayTid) {
		this.ezywayTid = ezywayTid;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMerchantPhoneno() {
		return merchantPhoneno;
	}

	public void setMerchantPhoneno(String merchantPhoneno) {
		this.merchantPhoneno = merchantPhoneno;
	}

	private String merchantName;
	private String merchantAddress;
	private String merchantPhoneno;

	public String getPromoName() {
		return promoName;
	}

	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getPromoDesc() {
		return promoDesc;
	}

	public void setPromoDesc(String promoDesc) {
		this.promoDesc = promoDesc;
	}

	public String getPromoImg1() {
		return promoImg1;
	}

	public void setPromoImg1(String promoImg1) {
		this.promoImg1 = promoImg1;
	}

	public String getPromoImg2() {
		return promoImg2;
	}

	public void setPromoImg2(String promoImg2) {
		this.promoImg2 = promoImg2;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	private String deviceId;
	private String motodeviceId;
	private String ezypassdeviceId;
	private String ezywaydeviceId;
	private String ezyrecdeviceId;

	public String getEzywaydeviceId() {
		return ezywaydeviceId;
	}

	public void setEzywaydeviceId(String ezywaydeviceId) {
		this.ezywaydeviceId = ezywaydeviceId;
	}

	public String getEzyrecdeviceId() {
		return ezyrecdeviceId;
	}

	public void setEzyrecdeviceId(String ezyrecdeviceId) {
		this.ezyrecdeviceId = ezyrecdeviceId;
	}

	public String getMotodeviceId() {
		return motodeviceId;
	}

	public void setMotodeviceId(String motodeviceId) {
		this.motodeviceId = motodeviceId;
	}

	public String getEzypassdeviceId() {
		return ezypassdeviceId;
	}

	public void setEzypassdeviceId(String ezypassdeviceId) {
		this.ezypassdeviceId = ezypassdeviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
