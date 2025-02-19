package com.mobiversa.payment.controller.bean;

public class CardSummaryBean {

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

    private String mdrAmount;

    private String netAmount;


    private String cardType;

    private String state;


    private String buyerName;
    private String merchantName;
    private String address;

    private String contactNumber;


    private String auth3Ds;

    private String timestamp;

    private String refereceNo;

    private String cardDetail;

    private String settlementDateCard;
    private String actualPaymentMethod;

    public String getActualPaymentMethod() {
        return actualPaymentMethod;
    }

    public void setActualPaymentMethod(String actualPaymentMethod) {
        this.actualPaymentMethod = actualPaymentMethod;
    }

    public String getSettlementDateCard() {
        return settlementDateCard;
    }

    public void setSettlementDateCard(String settlementDateCard) {
        this.settlementDateCard = settlementDateCard;
    }

    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCardDetail() {
        return cardDetail;
    }

    public void setCardDetail(String cardDetail) {
        this.cardDetail = cardDetail;
    }

    public String getRefereceNo() {
        return refereceNo;
    }

    public void setRefereceNo(String refereceNo) {
        this.refereceNo = refereceNo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuth3Ds() {
        return auth3Ds;
    }

    public void setAuth3Ds(String auth3Ds) {
        this.auth3Ds = auth3Ds;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getMdrAmount() {
        return mdrAmount;
    }

    public void setMdrAmount(String mdrAmount) {
        this.mdrAmount = mdrAmount;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String toString() {
        return "CardSummaryBean{" +
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
                ", mdrAmount='" + mdrAmount + '\'' +
                ", netAmount='" + netAmount + '\'' +
                ", cardType='" + cardType + '\'' +
                ", state='" + state + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", auth3Ds='" + auth3Ds + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", refereceNo='" + refereceNo + '\'' +
                ", cardDetail='" + cardDetail + '\'' +
                ", settlementDateCard='" + settlementDateCard + '\'' +
                '}';
    }
}




