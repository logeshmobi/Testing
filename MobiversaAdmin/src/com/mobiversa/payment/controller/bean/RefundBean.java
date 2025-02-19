package com.mobiversa.payment.controller.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mobiversa.payment.connect.ResponseData;

@SuppressWarnings("unqualified-field-access")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefundBean {

	private String onlineRefNum;
	private String transactionType;
	private String amount;
	private String transactionTime;
	private String boostRefNum;
	private String transactionStatus;

	private String transactionId;
	private String message;

	private String grabRfTxnId;
	private String reason;
	private String description;
	private String grabRfMsgId;

	private String txID;

	private String txStatus;

	private String token;

	private String responseCode;
	private String responseDescription;
	private ResponseData responseData;

	public String getGrabRfTxnId() {
		return grabRfTxnId;
	}

	public void setGrabRfTxnId(String grabRfTxnId) {
		this.grabRfTxnId = grabRfTxnId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGrabRfMsgId() {
		return grabRfMsgId;
	}

	public void setGrabRfMsgId(String grabRfMsgId) {
		this.grabRfMsgId = grabRfMsgId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOnlineRefNum() {
		return onlineRefNum;
	}

	public void setOnlineRefNum(String onlineRefNum) {
		this.onlineRefNum = onlineRefNum;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getBoostRefNum() {
		return boostRefNum;
	}

	public void setBoostRefNum(String boostRefNum) {
		this.boostRefNum = boostRefNum;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTxID() {
		return txID;
	}

	public void setTxID(String txID) {
		this.txID = txID;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

}
