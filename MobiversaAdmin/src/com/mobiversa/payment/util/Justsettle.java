package com.mobiversa.payment.util;

import com.mobiversa.payment.dto.EzysettleResponseData;

public class Justsettle {
	private String biccode;
	private String creditorAccNo;
	private String creditorAccName;
	private String creditorAccType;
	private String amt;
	private String mid;
	private String date;
	private String paymentReference;
	private String paymentDescription;
	private String lookupReference;
	private String mobiApiKey;
	private String bankName;
	private String brn;
	private String responseCode;
	private String responseMessage;
	private String responseDescription;
	
	private String batchId;
	
	private EzysettleResponseData responseData;
	
	public String getBiccode() {
		return biccode;
	}
	public void setBiccode(String biccode) {
		this.biccode = biccode;
	}
	public String getCreditorAccNo() {
		return creditorAccNo;
	}
	public void setCreditorAccNo(String creditorAccNo) {
		this.creditorAccNo = creditorAccNo;
	}
	public String getCreditorAccName() {
		return creditorAccName;
	}
	public void setCreditorAccName(String creditorAccName) {
		this.creditorAccName = creditorAccName;
	}
	public String getCreditorAccType() {
		return creditorAccType;
	}
	public void setCreditorAccType(String creditorAccType) {
		this.creditorAccType = creditorAccType;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
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
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	public String getPaymentDescription() {
		return paymentDescription;
	}
	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}
	public String getLookupReference() {
		return lookupReference;
	}
	public void setLookupReference(String lookupReference) {
		this.lookupReference = lookupReference;
	}
	public String getMobiApiKey() {
		return mobiApiKey;
	}
	public void setMobiApiKey(String mobiApiKey) {
		this.mobiApiKey = mobiApiKey;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBrn() {
		return brn;
	}
	public void setBrn(String brn) {
		this.brn = brn;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getResponseDescription() {
		return responseDescription;
	}
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	public EzysettleResponseData getResponseData() {
		return responseData;
	}
	public void setResponseData(EzysettleResponseData responseData) {
		this.responseData = responseData;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	
	
	
}
