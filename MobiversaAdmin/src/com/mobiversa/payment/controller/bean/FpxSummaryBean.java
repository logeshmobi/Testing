package com.mobiversa.payment.controller.bean;

public class FpxSummaryBean {

	private String transactionDate;
	private String transactionTime;
	private String transactionId;
	private String cardNumber;
	private String transactionAmount;
	private String status;
	private String declinedReason;
	private String paymentMethod;
	private String cardHolderName;
	private String mid;
	private String tid;
	private String rrn;
	private String approvalCode;

	private String referenceNo;
	private String mdrAmount;

	private String settlementAmount;
	private String settlementDate;
	private String bankName;
	private String ezySettleAmount;
	private String subMerchantMID;
	private String sellerExOrderNo;

	private String buyerName;
	private String merchanName;
	private String address;

	private String state;
	private String contactNumber;

	private String debitAuthCode;

	private String timestamp;

	private String fpxSlipBankName;


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFpxSlipBankName() {
		return fpxSlipBankName;
	}

	public void setFpxSlipBankName(String fpxSlipBankName) {
		this.fpxSlipBankName = fpxSlipBankName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getDebitAuthCode() {
		return debitAuthCode;
	}

	public void setDebitAuthCode(String debitAuthCode) {
		this.debitAuthCode = debitAuthCode;
	}

	public String getBankName() {
		return bankName;
	}

	public String getSubMerchantMID() {
		return subMerchantMID;
	}

	public void setSubMerchantMID(String subMerchantMID) {
		this.subMerchantMID = subMerchantMID;
	}

	public String getEzySettleAmount() {
		return ezySettleAmount;
	}

	public void setEzySettleAmount(String ezySettleAmount) {
		this.ezySettleAmount = ezySettleAmount;
	}

	public String getSellerExOrderNo() {
		return sellerExOrderNo;
	}

	public void setSellerExOrderNo(String sellerExOrderNo) {
		this.sellerExOrderNo = sellerExOrderNo;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getMdrAmount() {
		return mdrAmount;
	}

	public void setMdrAmount(String mdrAmount) {
		this.mdrAmount = mdrAmount;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	public String getDeclinedReason() {
		return declinedReason;
	}

	public void setDeclinedReason(String declinedReason) {
		this.declinedReason = declinedReason;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
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

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(String settlementAmount) {
		this.settlementAmount = settlementAmount;
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


	@Override
	public String toString() {
		return "FpxSummaryBean{" +
				"transactionDate='" + transactionDate + '\'' +
				", transactionTime='" + transactionTime + '\'' +
				", transactionId='" + transactionId + '\'' +
				", cardNumber='" + cardNumber + '\'' +
				", transactionAmount='" + transactionAmount + '\'' +
				", status='" + status + '\'' +
				", declinedReason='" + declinedReason + '\'' +
				", paymentMethod='" + paymentMethod + '\'' +
				", cardHolderName='" + cardHolderName + '\'' +
				", mid='" + mid + '\'' +
				", tid='" + tid + '\'' +
				", rrn='" + rrn + '\'' +
				", approvalCode='" + approvalCode + '\'' +
				", referenceNo='" + referenceNo + '\'' +
				", mdrAmount='" + mdrAmount + '\'' +
				", settlementAmount='" + settlementAmount + '\'' +
				", settlementDate='" + settlementDate + '\'' +
				", bankName='" + bankName + '\'' +
				", ezySettleAmount='" + ezySettleAmount + '\'' +
				", subMerchantMID='" + subMerchantMID + '\'' +
				", sellerExOrderNo='" + sellerExOrderNo + '\'' +
				", buyerName='" + buyerName + '\'' +
				", merchanName='" + merchanName + '\'' +
				", address='" + address + '\'' +
				", state='" + state + '\'' +
				", contactNumber='" + contactNumber + '\'' +
				", debitAuthCode='" + debitAuthCode + '\'' +
				", timestamp='" + timestamp + '\'' +
				", fpxSlipBankName='" + fpxSlipBankName + '\'' +
				'}';
	}
}
