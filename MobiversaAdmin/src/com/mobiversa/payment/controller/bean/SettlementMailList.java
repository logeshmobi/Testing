package com.mobiversa.payment.controller.bean;

public class SettlementMailList {
	private String merchantName;
	private String bankName;
	private String accNo;
	private String balanceNetAmt;
	private String mid;
	
	private String exsitingoverdraftAmount;
	private String overdraftAmount;
	private String exsitingdepositAmount;
	private String depositAmount;
	private String exsitingsettlementBalance;
	private String settlementBalance;
	private String exsitingtotalBalance;
	private String totalBalance;
	
	private String refundRequestAmount;
	private String intiateDate;
	private String txnId;
	private String txnAmount;
	
	
	
	
	public String getRefundRequestAmount() {
		return refundRequestAmount;
	}
	public void setRefundRequestAmount(String refundRequestAmount) {
		this.refundRequestAmount = refundRequestAmount;
	}
	public String getIntiateDate() {
		return intiateDate;
	}
	public void setIntiateDate(String intiateDate) {
		this.intiateDate = intiateDate;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getExsitingtotalBalance() {
		return exsitingtotalBalance;
	}
	public void setExsitingtotalBalance(String exsitingtotalBalance) {
		this.exsitingtotalBalance = exsitingtotalBalance;
	}
	public String getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}
	public String getOverdraftAmount() {
		return overdraftAmount;
	}
	public void setOverdraftAmount(String overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}
	public String getExsitingoverdraftAmount() {
		return exsitingoverdraftAmount;
	}
	public void setExsitingoverdraftAmount(String exsitingoverdraftAmount) {
		this.exsitingoverdraftAmount = exsitingoverdraftAmount;
	}
	
	public String getExsitingdepositAmount() {
		return exsitingdepositAmount;
	}
	public void setExsitingdepositAmount(String exsitingdepositAmount) {
		this.exsitingdepositAmount = exsitingdepositAmount;
	}
	public String getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getExsitingsettlementBalance() {
		return exsitingsettlementBalance;
	}
	public void setExsitingsettlementBalance(String exsitingsettlementBalance) {
		this.exsitingsettlementBalance = exsitingsettlementBalance;
	}
	public String getSettlementBalance() {
		return settlementBalance;
	}
	public void setSettlementBalance(String settlementBalance) {
		this.settlementBalance = settlementBalance;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getBalanceNetAmt() {
		return balanceNetAmt;
	}
	public void setBalanceNetAmt(String balanceNetAmt) {
		this.balanceNetAmt = balanceNetAmt;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
}
