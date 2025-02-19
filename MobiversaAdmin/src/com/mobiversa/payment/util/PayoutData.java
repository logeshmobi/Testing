package com.mobiversa.payment.util;

public class PayoutData {

	public String merchantID;
	public String txnId;
	public String payoutStatus;
	public String responseCode;
	
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getPayoutStatus() {
		return payoutStatus;
	}
	public void setPayoutStatus(String payoutStatus) {
		this.payoutStatus = payoutStatus;
	}
	


}
