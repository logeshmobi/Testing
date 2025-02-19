package com.mobiversa.payment.controller.bean;

public class PayoutSettleBean {
private String umMid; 
private String umEzywayMid; 
private String umMotoMid; 
private String umEzyrecMid; 
private long currentMerchantid;
private String TxnFormatDate;
private String  TxnDate;
private String netAmt;
private String ezyAmt;
private String formatsettledate;
private String boostmid;
private String  settledate;
private String  fpxmid;
private String tngMid;
private String shoppyMid;
private String fiuuMid;

private String status;

public String getTngMid() {
	return tngMid;
}
public void setTngMid(String tngMid) {
	this.tngMid = tngMid;
}
public String getShoppyMid() {
	return shoppyMid;
}
public void setShoppyMid(String shoppyMid) {
	this.shoppyMid = shoppyMid;
}
public String getFpxmid() {
	return fpxmid;
}
public void setFpxmid(String fpxmid) {
	this.fpxmid = fpxmid;
}
public String getSettledate() {
	return settledate;
}
public void setSettledate(String settledate) {
	this.settledate = settledate;
}
public String getTxnFormatDate() {
	return TxnFormatDate;
}
public void setTxnFormatDate(String txnFormatDate) {
	TxnFormatDate = txnFormatDate;
}
public String getTxnDate() {
	return TxnDate;
}
public void setTxnDate(String txnDate) {
	TxnDate = txnDate;
}

public long getCurrentMerchantid() {
	return currentMerchantid;
}
public void setCurrentMerchantid(long currentMerchantid) {
	this.currentMerchantid = currentMerchantid;
}
private String umEzypassMid; 
private String splitMid; 


public String getBoostmid() {
	return boostmid;
}
public void setBoostmid(String boostmid) {
	this.boostmid = boostmid;
}

public String getUmMid() {
	return umMid;
}
public void setUmMid(String umMid) {
	this.umMid = umMid;
}
public String getUmEzywayMid() {
	return umEzywayMid;
}
public void setUmEzywayMid(String umEzywayMid) {
	this.umEzywayMid = umEzywayMid;
}
public String getUmMotoMid() {
	return umMotoMid;
}
public void setUmMotoMid(String umMotoMid) {
	this.umMotoMid = umMotoMid;
}
public String getUmEzyrecMid() {
	return umEzyrecMid;
}
public void setUmEzyrecMid(String umEzyrecMid) {
	this.umEzyrecMid = umEzyrecMid;
}
public String getUmEzypassMid() {
	return umEzypassMid;
}
public void setUmEzypassMid(String umEzypassMid) {
	this.umEzypassMid = umEzypassMid;
}
public String getSplitMid() {
	return splitMid;
}
public void setSplitMid(String splitMid) {
	this.splitMid = splitMid;
}
public String getNetAmt() {
	return netAmt;
}
public void setNetAmt(String netAmt) {
	this.netAmt = netAmt;
}
public String getEzyAmt() {
	return ezyAmt;
}
public void setEzyAmt(String ezyAmt) {
	this.ezyAmt = ezyAmt;
}

public String getFormatsettledate() {
	return formatsettledate;
}
public void setFormatsettledate(String formatsettledate) {
	this.formatsettledate = formatsettledate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getFiuuMid() {
	return fiuuMid;
}
public void setFiuuMid(String fiuuMid) {
	this.fiuuMid = fiuuMid;
}



}
//query.setString("umMid", umMid);
//query.setString("umEzywayMid", umEzywayMid);
//query.setString("umMotoMid", umMotoMid);
//query.setString("umEzyrecMid", umEzyrecMid);
//query.setString("umEzypassMid", umEzypassMid);
//query.setString("splitMid", splitMid);
//query.setString("netAmt", netAmt);
//query.setString("ezyAmt", ezyAmt);