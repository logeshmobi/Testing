package com.mobiversa.payment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EzysettleResponseData {
	
	private String creditorAccountName;
	private String transactionStatus;
	private String batchid;
	public String getCreditorAccountName() {
		return creditorAccountName;
	}
	public void setCreditorAccountName(String creditorAccountName) {
		this.creditorAccountName = creditorAccountName;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	@Override
	public String toString() {
		return "ResponseData [creditorAccountName=" + creditorAccountName + ", transactionStatus=" + transactionStatus
				+ ", batchid=" + batchid + "]";
	}

	
}
