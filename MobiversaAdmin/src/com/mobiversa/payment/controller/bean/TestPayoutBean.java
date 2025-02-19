package com.mobiversa.payment.controller.bean;

import com.mobiversa.payment.connect.ResponseData;

public class TestPayoutBean {
	
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
	
	
	private String failureReason;
	private String transactionId;
	private ResponseData responseData;
	
	
	
	
	public ResponseData getResponseData() {
		return responseData;
	}
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
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
	
	
	
}
