package com.mobiversa.payment.util;

public class SettlementModel {

	private String date;
	private String time;
	private String txnAmount;
	private String netAmount;
	private String hostMdrAmt;
	private String mobiMdrAmt;
	private String mid;
	private String tid;
	private String paymentDate;
	private String maskedPan;
	private String mdrAmount;
	private String status;
	private String rrn;
	private String invoiceId;
	private String cardBrand;
	private String cardType;
	private String submerchantid;

	private String merchantName;
	

	public String getSubmerchantid() {
		return submerchantid;
	}

	public void setSubmerchantid(String submerchantid) {
		this.submerchantid = submerchantid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getMdrAmount() {
		return mdrAmount;
	}

	public void setMdrAmount(String mdrAmount) {
		this.mdrAmount = mdrAmount;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getHostMdrAmt() {
		return hostMdrAmt;
	}

	public void setHostMdrAmt(String hostMdrAmt) {
		this.hostMdrAmt = hostMdrAmt;
	}

	public String getMobiMdrAmt() {
		return mobiMdrAmt;
	}

	public void setMobiMdrAmt(String mobiMdrAmt) {
		this.mobiMdrAmt = mobiMdrAmt;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMaskedPan() {
		return maskedPan;
	}

	public void setMaskedPan(String maskedPan) {
		this.maskedPan = maskedPan;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getCardBrand() {
		return cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	

}
