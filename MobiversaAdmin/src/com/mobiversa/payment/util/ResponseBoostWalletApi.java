package com.mobiversa.payment.util;

public class ResponseBoostWalletApi 
{
	private String currentWalletBalance ;
	private String startingWalletBalance ;
	private String totalNoPaymentTransaction ;
	private String totalNoVoidTransaction ;
	private String totalPaymentAmount ;
	private String totalVoidAmount ;
	public String getCurrentWalletBalance() {
		return currentWalletBalance;
	}
	public void setCurrentWalletBalance(String currentWalletBalance) {
		this.currentWalletBalance = currentWalletBalance;
	}
	public String getStartingWalletBalance() {
		return startingWalletBalance;
	}
	public void setStartingWalletBalance(String startingWalletBalance) {
		this.startingWalletBalance = startingWalletBalance;
	}
	public String getTotalNoPaymentTransaction() {
		return totalNoPaymentTransaction;
	}
	public void setTotalNoPaymentTransaction(String totalNoPaymentTransaction) {
		this.totalNoPaymentTransaction = totalNoPaymentTransaction;
	}
	public String getTotalNoVoidTransaction() {
		return totalNoVoidTransaction;
	}
	public void setTotalNoVoidTransaction(String totalNoVoidTransaction) {
		this.totalNoVoidTransaction = totalNoVoidTransaction;
	}
	public String getTotalPaymentAmount() {
		return totalPaymentAmount;
	}
	public void setTotalPaymentAmount(String totalPaymentAmount) {
		this.totalPaymentAmount = totalPaymentAmount;
	}
	public String getTotalVoidAmount() {
		return totalVoidAmount;
	}
	public void setTotalVoidAmount(String totalVoidAmount) {
		this.totalVoidAmount = totalVoidAmount;
	}
	
	
	}
