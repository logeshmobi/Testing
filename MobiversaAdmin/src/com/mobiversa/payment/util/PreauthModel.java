package com.mobiversa.payment.util;

import java.math.BigInteger;

public class PreauthModel {

	private String date;
	private String time;
	private String status;
	private String amount;
	private String cardNo;
	private String tid;
	private String approvalCode;
	private String reference;
	private String txnid;
	private BigInteger itxnid;
	private String notxnid;
	
	private String txntype;
	
	private String serviceId;

	public String getTxntype() {
		return txntype;
	}

	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}

	private String userAmount;
	private long merchantId;
	private String merchantType;

	private String responseCode;
	private String responseDescription;

	public long getMerchantId() {
		return merchantId;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public void setMerchantId(long merchantId) {
		this.merchantId = merchantId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getNotxnid() {
		return notxnid;
	}

	public String getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(String userAmount) {
		this.userAmount = userAmount;
	}

	public void setNotxnid(String notxnid) {
		this.notxnid = notxnid;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public BigInteger getItxnid() {
		return itxnid;
	}

	public void setItxnid(BigInteger itxnid) {
		this.itxnid = itxnid;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	

}
