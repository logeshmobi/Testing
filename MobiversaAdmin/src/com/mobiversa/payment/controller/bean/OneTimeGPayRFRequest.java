package com.mobiversa.payment.controller.bean;

public class OneTimeGPayRFRequest {
	
	String partnerGroupTxID;
	String partnerTxID;
	String currency;
	String description;
	String originTxID;
	int amount;
	
	String merchantID;
	
	
	
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPartnerGroupTxID() {
		return partnerGroupTxID;
	}
	public void setPartnerGroupTxID(String partnerGroupTxID) {
		this.partnerGroupTxID = partnerGroupTxID;
	}
	public String getPartnerTxID() {
		return partnerTxID;
	}
	public void setPartnerTxID(String partnerTxID) {
		this.partnerTxID = partnerTxID;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getOriginTxID() {
		return originTxID;
	}
	public void setOriginTxID(String originTxID) {
		this.originTxID = originTxID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	

}
