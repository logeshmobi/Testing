package com.mobiversa.payment.util;

public class FileGenerate {
	
	private String date;
	private String mid;
	private String merchantFile;
	private String mdrFile;
	private String deductionFile;
	private String csvFile;
	private String settlePeriod;
	private String mailTo;
	private String mailCC;
	private String settleType;
	
	
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public String getSettlePeriod() {
		return settlePeriod;
	}
	public void setSettlePeriod(String settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	public String getMailTo() {
		return mailTo;
	}
	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}
	public String getMailCC() {
		return mailCC;
	}
	public void setMailCC(String mailCC) {
		this.mailCC = mailCC;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMerchantFile() {
		return merchantFile;
	}
	public void setMerchantFile(String merchantFile) {
		this.merchantFile = merchantFile;
	}
	public String getMdrFile() {
		return mdrFile;
	}
	public void setMdrFile(String mdrFile) {
		this.mdrFile = mdrFile;
	}
	public String getDeductionFile() {
		return deductionFile;
	}
	public void setDeductionFile(String deductionFile) {
		this.deductionFile = deductionFile;
	}
	public String getCsvFile() {
		return csvFile;
	}
	public void setCsvFile(String csvFile) {
		this.csvFile = csvFile;
	}

}
