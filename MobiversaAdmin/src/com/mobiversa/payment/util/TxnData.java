package com.mobiversa.payment.util;

public class TxnData {
private String month;
private String amount;
private String merchantName;
private String txnType;
private String id;
public String getMonth() {
	return month;
}
public void setMonth(String month) {
	this.month = month;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getMerchantName() {
	return merchantName;
}
public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
}
public String getTxnType() {
	return txnType;
}
public void setTxnType(String txnType) {
	this.txnType = txnType;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}


}
