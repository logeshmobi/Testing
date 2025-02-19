package com.mobiversa.payment.dto;

public class DuitnowTxnDto {

    private String createdDate;
    private String createdTime;
    private String invoiceId;
    private String transactionId;
    private String merchantName;
    private String status;
    private String hostMdr;
    private String mobiMdr;
    private String mdrAmount;
    private String txnAmount;
    private String netAmount;
    private String settlementDate;
    private String merchantId;

    private String dateFromBackend;

    private String date1FromBackend;

    private String txnType;

    private String subMerchantMid;

    private String currPage;

    private String totalRecords;

    private String ezysettleAmount;

    private String declinedReason;
    private String hostTransactionId;
    
    private String paidDate;
    private String paidTime;


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHostMdr() {
        return hostMdr;
    }

    public void setHostMdr(String hostMdr) {
        this.hostMdr = hostMdr;
    }

    public String getMobiMdr() {
        return mobiMdr;
    }

    public void setMobiMdr(String mobiMdr) {
        this.mobiMdr = mobiMdr;
    }

    public String getMdrAmount() {
        return mdrAmount;
    }

    public void setMdrAmount(String mdrAmount) {
        this.mdrAmount = mdrAmount;
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

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSubMerchantMid() {
        return subMerchantMid;
    }

    public void setSubMerchantMid(String subMerchantMid) {
        this.subMerchantMid = subMerchantMid;
    }

    public String getDateFromBackend() {
        return dateFromBackend;
    }

    public void setDateFromBackend(String dateFromBackend) {
        this.dateFromBackend = dateFromBackend;
    }

    public String getDate1FromBackend() {
        return date1FromBackend;
    }

    public void setDate1FromBackend(String date1FromBackend) {
        this.date1FromBackend = date1FromBackend;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getCurrPage() {
        return currPage;
    }

    public void setCurrPage(String currPage) {
        this.currPage = currPage;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getEzysettleAmount() {
        return ezysettleAmount;
    }

    public void setEzysettleAmount(String ezysettleAmount) {
        this.ezysettleAmount = ezysettleAmount;
    }

    public String getDeclinedReason() {
        return declinedReason;
    }

    public void setDeclinedReason(String declinedReason) {
        this.declinedReason = declinedReason;
    }

    public String getHostTransactionId() {
        return hostTransactionId;
    }

    public void setHostTransactionId(String hostTransactionId) {
        this.hostTransactionId = hostTransactionId;
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

	@Override
	public String toString() {
		return "DuitnowTxnDto [createdDate=" + createdDate + ", createdTime=" + createdTime + ", invoiceId=" + invoiceId
				+ ", transactionId=" + transactionId + ", merchantName=" + merchantName + ", status=" + status
				+ ", hostMdr=" + hostMdr + ", mobiMdr=" + mobiMdr + ", mdrAmount=" + mdrAmount + ", txnAmount="
				+ txnAmount + ", netAmount=" + netAmount + ", settlementDate=" + settlementDate + ", merchantId="
				+ merchantId + ", dateFromBackend=" + dateFromBackend + ", date1FromBackend=" + date1FromBackend
				+ ", txnType=" + txnType + ", subMerchantMid=" + subMerchantMid + ", currPage=" + currPage
				+ ", totalRecords=" + totalRecords + ", ezysettleAmount=" + ezysettleAmount + ", declinedReason="
				+ declinedReason + ", hostTransactionId=" + hostTransactionId + ", paidDate=" + paidDate + ", paidTime="
				+ paidTime + "]";
	}
    
	
}
