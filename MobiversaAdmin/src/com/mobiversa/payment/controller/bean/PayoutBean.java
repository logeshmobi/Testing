package com.mobiversa.payment.controller.bean;



public class PayoutBean {
	private String invoiceIdProof; 
	private String paidDate; 
	private String paidTime; 
	private String payoutStatus;
	private String declineReason;
	
	
	
	public PayoutBean(String invoiceIdProof, String paidDate, String paidTime, String payoutStatus,
			String declineReason) {
		super();
		this.invoiceIdProof = invoiceIdProof;
		this.paidDate = paidDate;
		this.paidTime = paidTime;
		this.payoutStatus = payoutStatus;
		this.declineReason = declineReason;
	}
	public PayoutBean(String invoiceIdProof, String paidDate, String paidTime, String payoutStatus) {
		super();
		this.invoiceIdProof = invoiceIdProof;
		this.paidDate = paidDate;
		this.paidTime = paidTime;
		this.payoutStatus = payoutStatus;
		
	}
	
	
	public String getInvoiceIdProof() {
		return invoiceIdProof;
	}
	public void setInvoiceIdProof(String invoiceIdProof) {
		this.invoiceIdProof = invoiceIdProof;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	public String getPaidTime() {
		return paidTime;
	}
	public void setPaidTime(String paidTime) {
		this.paidTime = paidTime;
	}
	public String getPayoutStatus() {
		return payoutStatus;
	}
	public void setPayoutStatus(String payoutStatus) {
		this.payoutStatus = payoutStatus;
	}
	public String getDeclineReason() {
		return declineReason;
	}
	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}
	
}