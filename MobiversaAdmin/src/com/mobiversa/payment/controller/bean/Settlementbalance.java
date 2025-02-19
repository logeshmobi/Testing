package com.mobiversa.payment.controller.bean;

public class Settlementbalance {
	private String updatedDate;
	private String amount;
	private String bankAccNumber;
	
	
	//For Updated PayoutBalanceTable(OCBC Changes)
    private String amBankBalance;
    private String amBankUpdatedDate;
    private String amBankAccNumber;

    private String ocbcBankBalance;
    private String ocbcBankUpdatedDate;
    private String ocbcBankAccNumber;
    
    private String totalBankBalance;
    private String totalBankBalanceUpdatedDate;
    
    
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBankAccNumber() {
		return bankAccNumber;
	}
	public void setBankAccNumber(String bankAccNumber) {
		this.bankAccNumber = bankAccNumber;
	}
	
	
	 public String getAmBankBalance() {
		return amBankBalance;
	}
	public void setAmBankBalance(String amBankBalance) {
		this.amBankBalance = amBankBalance;
	}
	public String getAmBankUpdatedDate() {
		return amBankUpdatedDate;
	}
	public void setAmBankUpdatedDate(String amBankUpdatedDate) {
		this.amBankUpdatedDate = amBankUpdatedDate;
	}
	public String getAmBankAccNumber() {
		return amBankAccNumber;
	}
	public void setAmBankAccNumber(String amBankAccNumber) {
		this.amBankAccNumber = amBankAccNumber;
	}
	public String getOcbcBankBalance() {
		return ocbcBankBalance;
	}
	public void setOcbcBankBalance(String ocbcBankBalance) {
		this.ocbcBankBalance = ocbcBankBalance;
	}
	public String getOcbcBankUpdatedDate() {
		return ocbcBankUpdatedDate;
	}
	public void setOcbcBankUpdatedDate(String ocbcBankUpdatedDate) {
		this.ocbcBankUpdatedDate = ocbcBankUpdatedDate;
	}
	public String getOcbcBankAccNumber() {
		return ocbcBankAccNumber;
	}
	public void setOcbcBankAccNumber(String ocbcBankAccNumber) {
		this.ocbcBankAccNumber = ocbcBankAccNumber;
	}
	public String getTotalBankBalance() {
		return totalBankBalance;
	}
	public void setTotalBankBalance(String totalBankBalance) {
		this.totalBankBalance = totalBankBalance;
	}
	public String getTotalBankBalanceUpdatedDate() {
		return totalBankBalanceUpdatedDate;
	}
	public void setTotalBankBalanceUpdatedDate(String totalBankBalanceUpdatedDate) {
		this.totalBankBalanceUpdatedDate = totalBankBalanceUpdatedDate;
	}
	@Override
	    public String toString() {
	        return "Settlementbalance [amBankBalance=" + amBankBalance + ", amBankUpdatedDate=" + amBankUpdatedDate
	                + ", amBankAccNumber=" + amBankAccNumber + ", ocbcBankBalance=" + ocbcBankBalance
	                + ", ocbcBankUpdatedDate=" + ocbcBankUpdatedDate + ", ocbcBankAccNumber=" + ocbcBankAccNumber
	                + ", totalBankBalance=" + totalBankBalance + ", totalBankBalanceUpdatedDate="
	                + totalBankBalanceUpdatedDate + "]";
	    }

}
