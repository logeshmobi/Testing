package com.mobiversa.payment.controller.bean;

public class EwalletSummaryBean {

	private String transactionDate;
	private String transactionTime;
	private String transactionID;
	private String paymentMethod;
	private String mid;
	private String tid;
	private String transactionAmount;
	private String status;
	private String settlementAmount;
	private String settlementDate;
	private String rrn;
	private String approvalCode;
	private String referanceNo;
	private String mdrAmount;
	private String subMerchantMID;
	private String ezySettleAmt;
	private String merchantName;
	
	private String paidTimeStamp;
	private String hostTransactionID;

	private String hostType;

	private  String timeStamp;

	private String buyerName;
	private String merchanName;
	private String address;

	private String state;


	private String contactNumber;

	private String failureReason;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getMerchanName() {
		return merchanName;
	}

	public void setMerchanName(String merchanName) {
		this.merchanName = merchanName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getHostType() {
		return hostType;
	}

	public String setHostType(String hostType) {
		return this.hostType = hostType;
	}

	//	private String createdTimeStamp;
//	private String invoiceId;
//	private String ezySettleAmt;
//	private String subMerchantMID;
//	private String tngTxnId;
////	private String payableAmount;
//	private String transactionType;
//	private String merchantName;
//	private String aidResponse;

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getApprovalCode() {
		return approvalCode;
	}

	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}

	public String getReferanceNo() {
		return referanceNo;
	}

	public void setReferanceNo(String referanceNo) {
		this.referanceNo = referanceNo;
	}

	public String getMdrAmount() {
		return mdrAmount;
	}

	public void setMdrAmount(String mdrAmount) {
		this.mdrAmount = mdrAmount;
	}

	public String getSubMerchantMID() {
		return subMerchantMID;
	}

	public void setSubMerchantMID(String subMerchantMID) {
		this.subMerchantMID = subMerchantMID;
	}

	public String getEzySettleAmt() {
		return ezySettleAmt;
	}

	public void setEzySettleAmt(String ezySettleAmt) {
		this.ezySettleAmt = ezySettleAmt;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	


	public String getPaidTimeStamp() {
		return paidTimeStamp;
	}

	public void setPaidTimeStamp(String paidTimeStamp) {
		this.paidTimeStamp = paidTimeStamp;
	}

	public String getHostTransactionID() {
		return hostTransactionID;
	}

	public void setHostTransactionID(String hostTransactionID) {
		this.hostTransactionID = hostTransactionID;
	}

	@Override
	public String toString() {
		return "EwalletSummaryBean [transactionDate=" + transactionDate + ", transactionTime=" + transactionTime
				+ ", transactionID=" + transactionID + ", paymentMethod=" + paymentMethod + ", mid=" + mid + ", tid="
				+ tid + ", transactionAmount=" + transactionAmount + ", status=" + status + ", settlementAmount="
				+ settlementAmount + ", settlementDate=" + settlementDate + ", rrn=" + rrn + ", approvalCode="
				+ approvalCode + ", referanceNo=" + referanceNo + ", mdrAmount=" + mdrAmount + ", subMerchantMID="
				+ subMerchantMID + ", ezySettleAmt=" + ezySettleAmt + ", merchantName=" + merchantName
				+ ", paidTimeStamp=" + paidTimeStamp + ", hostTransactionID=" + hostTransactionID + ", hostType="
				+ hostType + ", timeStamp=" + timeStamp + ", buyerName=" + buyerName + ", merchanName=" + merchanName
				+ ", address=" + address + ", state=" + state + ", contactNumber=" + contactNumber + ", failureReason="
				+ failureReason + "]";
	}

	
	
}

























