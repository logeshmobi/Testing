package com.mobiversa.payment.dto;

public class WithdrawDeposit {
	

	private String timeStamp;
	private String merchantName;
	private String previousBalance;
	private String depositAmount;
	private String availableBalance;
	private String emailStatus;
	private String referenceNo;
	private String reason;
	private String withdrawAmount;
	private String status;
	private String type;
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(String previousBalance) {
		this.previousBalance = previousBalance;
	}
	public String getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getEmailStatus() {
		return emailStatus;
	}
	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(String withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	@Override
	public String toString() {
		return "WithdrawDeposit [timeStamp=" + timeStamp + ", merchantName=" + merchantName + ", previousBalance="
				+ previousBalance + ", depositAmount=" + depositAmount + ", availableBalance=" + availableBalance
				+ ", emailStatus=" + emailStatus + ", referenceNo=" + referenceNo + ", reason=" + reason
				+ ", withdrawAmount=" + withdrawAmount + ", status=" + status + ", type=" + type + "]";
	}


}
