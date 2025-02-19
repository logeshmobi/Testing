package com.postmark.java;

import java.io.Serializable;
import java.util.ArrayList;

public class MsgDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private String TID;
	private String MID;
	private String date;
	private String time;
	private String cardNo;
	private String holder;
	private String app;
	private String AID;
	private String TC;
	private String refNo;
	private String apprCode;
	private String batchNo;
	private String traceNo;
	private String type;
	private String amount;
	private String tips;
	private String singnature;
	
	//change
	private String cardType;
	private String merchantName;
	private String merchantAddr1;
	private String merchantAddr2;
	private String merchantCity;
	private String merchantPostCode;
	private String merchantContractNo;
	private String merchantEmail;
	
	private String custMailId;
	private String custContactNo; 
	private String invoiceNo;
	
	
	private String custName;
	private String period; 
	private String cycle;
	private String reason;
	
	private String userName;
	private String password;
	private String officeEmail;
	private String smsData;
	private String mobileNo;
	private String contactNo;
	
	
	
	private String cardHolderName;
	private ArrayList<SendReceiptTemplet> tmList;	// List of settlement
	private int item;							// Size of list
	private String firstName;
	private String lastName;
	 
	private String maskedPan;
	private String tc;
	private String rrn;
	private String stan;
	private String postalCode;
	private String city;
	
	private String ezywayMid;
	private String ezyrecMid;
	private String activationCode;
	private boolean ezyPOD;
	private String ezywayApiKey;
	private String ezypodApiKey;
	private String ezywayTid;
	private String ezyrecTid;
	private String customerEmail;
	private String mapImg;
	private String imgBase64;
	private String whatsAppNo;
	private String deviceID;
	
	
	
	
	
	
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public String getWhatsAppNo() {
		return whatsAppNo;
	}
	public void setWhatsAppNo(String whatsAppNo) {
		this.whatsAppNo = whatsAppNo;
	}
	public String getImgBase64() {
		return imgBase64;
	}
	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}
	public String getMapImg() {
		return mapImg;
	}
	public void setMapImg(String mapImg) {
		this.mapImg = mapImg;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}



	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}



	public String getTc() {
		return tc;
	}
	
	

	public String getEzyrecTid() {
		return ezyrecTid;
	}

	public void setEzyrecTid(String ezyrecTid) {
		this.ezyrecTid = ezyrecTid;
	}

	public String getEzywayApiKey() {
		return ezywayApiKey;
	}

	public void setEzywayApiKey(String ezywayApiKey) {
		this.ezywayApiKey = ezywayApiKey;
	}

	public String getEzypodApiKey() {
		return ezypodApiKey;
	}

	public void setEzypodApiKey(String ezypodApiKey) {
		this.ezypodApiKey = ezypodApiKey;
	}

	public String getEzywayTid() {
		return ezywayTid;
	}

	public void setEzywayTid(String ezywayTid) {
		this.ezywayTid = ezywayTid;
	}

	
	public String getEzywayMid() {
		return ezywayMid;
	}
	public void setEzywayMid(String ezywayMid) {
		this.ezywayMid = ezywayMid;
	}
	public String getEzyrecMid() {
		return ezyrecMid;
	}
	public void setEzyrecMid(String ezyrecMid) {
		this.ezyrecMid = ezyrecMid;
	}
	
	public boolean isEzyPOD() {
		return ezyPOD;
	}
	public void setEzyPOD(boolean ezyPOD) {
		this.ezyPOD = ezyPOD;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setTc(String tc) {
		this.tc = tc;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getMaskedPan() {
		return maskedPan;
	}
	public void setMaskedPan(String maskedPan) {
		this.maskedPan = maskedPan;
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
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSmsData() {
		return smsData;
	}
	public void setSmsData(String smsData) {
		this.smsData = smsData;
	}
	public String getOfficeEmail() {
		return officeEmail;
	}
	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}
	public ArrayList<SendReceiptTemplet> getTmList() {
		return tmList;
	}
	public void setTmList(ArrayList<SendReceiptTemplet> tmList) {
		this.tmList = tmList;
	}
	
	public int getItem() {
		return item;
	}
	
	public void setItem(int item) {
		this.item = item;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	private String latitude;
	private String longitude;
	
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public String getCustMailId() {
		return custMailId;
	}

	public void setCustMailId(String custMailId) {
		this.custMailId = custMailId;
	}

	public String getCustContactNo() {
		return custContactNo;
	}

	public void setCustContactNo(String custContactNo) {
		this.custContactNo = custContactNo;
	}

	public String getMerchantEmail() {
		return merchantEmail;
	}

	public void setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

		public String getMerchantAddr1() {
		return merchantAddr1;
	}

	public void setMerchantAddr1(String merchantAddr1) {
		this.merchantAddr1 = merchantAddr1;
	}

	public String getMerchantAddr2() {
		return merchantAddr2;
	}

	public void setMerchantAddr2(String merchantAddr2) {
		this.merchantAddr2 = merchantAddr2;
	}

	public String getMerchantPostCode() {
		return merchantPostCode;
	}

	public void setMerchantPostCode(String merchantPostCode) {
		this.merchantPostCode = merchantPostCode;
	}

	public String getMerchantContractNo() {
		return merchantContractNo;
	}

	public void setMerchantContractNo(String merchantContractNo) {
		this.merchantContractNo = merchantContractNo;
	}

		public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}

	public String getSingnature() {
		return singnature;
	}

	public void setSingnature(String singnature) {
		this.singnature = singnature;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public String getMID() {
		return MID;
	}

	public void setMID(String mID) {
		MID = mID;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getAID() {
		return AID;
	}

	public void setAID(String aID) {
		AID = aID;
	}

	public String getTC() {
		return TC;
	}

	public void setTC(String tC) {
		TC = tC;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getApprCode() {
		return apprCode;
	}

	public void setApprCode(String apprCode) {
		this.apprCode = apprCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	private String total;

	

}
