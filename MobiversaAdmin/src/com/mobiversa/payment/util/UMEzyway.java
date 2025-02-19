package com.mobiversa.payment.util;

import java.math.BigInteger;

public class UMEzyway {
	private String preauthfee;
	private String F001_MID;
	private String F005_EXPDATE;
	private String F007_TXNAMT;
	private String F354_TID;
	private String PAN;
	private String STATUS;
	private String F263_MRN;
	private String H003_TDT;
	private String H004_TTM;
	private String F011_AUTHIDRESP;
	private String F023_RRN;
	private String time;
	private String date;
	private String merchantName;
	private String txnstatMsg;
	private String cardType;
	private String txnStatus;
	private String F009_RESPCODE;
	private String respMessage;
	private String Time_Stamp;
	private String F268_CHNAME;
	private String CBDate;
	private String CBReason;
	private String txnType;
	private String F270_ORN;
	private String merchantId;
	private String F278_EMAILADDR;
	private String F279_HP;
	private String fraudScore;
	private String fraudId;
	private String fraudStatus;
	private String authremamt;
	private String submerchantmid;
	private String ppid;
	private String respcode;
	
	private String serviceId;

	//payout filter
	private String createdDate;
	private String createdTime;
	private String createdBy;
	private String payeeName;
	private String payeeAccNumber;
	private String payoutStatus;
	private String payoutAmount;
	private String payeeBankName;
	private String payoutId;
	private String invoiceId;
	private String assigneeName;
	private String failureReason;



	public String getRespcode() {
		return respcode;
	}
	public void setRespcode(String respcode) {
		this.respcode = respcode;
	}
	public String getPpid() {
		return ppid;
	}
	public void setPpid(String ppid) {
		this.ppid = ppid;
	}
	public String getSubmerchantmid() {
		return submerchantmid;
	}
	public void setSubmerchantmid(String submerchantmid) {
		this.submerchantmid = submerchantmid;
	}
	public String getAuthremamt() {
		return authremamt;
	}
	public void setAuthremamt(String authremamt) {
		this.authremamt = authremamt;
	}
	private String submid;
	
	private String ezysettleAmt;
	
	public String getSubmid() {
		return submid;
	}
	public void setSubmid(String submid) {
		this.submid = submid;
	}
	private String goodsStatus;
	private String updatedDate;
	private String notrxid;
	
	public String getNotrxid() {
		return notrxid;
	}
	public void setNotrxid(String notrxid) {
		this.notrxid = notrxid;
	}
	public String getNotrxId() {
		return notrxId;
	}
	public void setNotrxId(String notrxId) {
		this.notrxId = notrxId;
	}
	private String netAmount;
	private String mdrAmt;
	private String settlementDate;
	
	private String cardscheme;
	
	public String getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}
	public String getMdrAmt() {
		return mdrAmt;
	}
	public String getEzysettleAmt() {
		return ezysettleAmt;
	}
	public void setEzysettleAmt(String ezysettleAmt) {
		this.ezysettleAmt = ezysettleAmt;
	}
	public void setMdrAmt(String mdrAmt) {
		this.mdrAmt = mdrAmt;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getCardscheme() {
		return cardscheme;
	}
	public void setCardscheme(String cardscheme) {
		this.cardscheme = cardscheme;
	}
	public BigInteger getTrxId() {
		return trxId;
	}
	public void setTrxId(BigInteger trxId) {
		this.trxId = trxId;
	}
	private BigInteger trxId;
	
	private String notrxId;
	
	
	
	
	public String getFraudScore() {
		return fraudScore;
	}
	public void setFraudScore(String fraudScore) {
		this.fraudScore = fraudScore;
	}
	public String getFraudId() {
		return fraudId;
	}
	public void setFraudId(String fraudId) {
		this.fraudId = fraudId;
	}
	public String getFraudStatus() {
		return fraudStatus;
	}
	public void setFraudStatus(String fraudStatus) {
		this.fraudStatus = fraudStatus;
	}
	public String getGoodsStatus() {
		return goodsStatus;
	}
	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	
	
	public String getF270_ORN() {
		return F270_ORN;
	}
	public void setF270_ORN(String f270_ORN) {
		F270_ORN = f270_ORN;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getCBDate() {
		return CBDate;
	}
	public void setCBDate(String cBDate) {
		CBDate = cBDate;
	}
	
	public String getCBReason() {
		return CBReason;
	}
	public void setCBReason(String cBReason) {
		CBReason = cBReason;
	}
	public String getF001_MID() {
		return F001_MID;
	}
	public void setF001_MID(String f001_MID) {
		F001_MID = f001_MID;
	}
	public String getF005_EXPDATE() {
		return F005_EXPDATE;
	}
	public void setF005_EXPDATE(String f005_EXPDATE) {
		F005_EXPDATE = f005_EXPDATE;
	}
	public String getF007_TXNAMT() {
		return F007_TXNAMT;
	}
	public void setF007_TXNAMT(String f007_TXNAMT) {
		F007_TXNAMT = f007_TXNAMT;
	}
	public String getF354_TID() {
		return F354_TID;
	}
	public void setF354_TID(String f354_TID) {
		F354_TID = f354_TID;
	}
/*	public String getMASKED_PAN() {
		return MASKED_PAN;
	}
	public void setMASKED_PAN(String mASKED_PAN) {
		MASKED_PAN = mASKED_PAN;
	}*/
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public String getF263_MRN() {
		return F263_MRN;
	}
	public void setF263_MRN(String f263_MRN) {
		F263_MRN = f263_MRN;
	}
	public String getH003_TDT() {
		return H003_TDT;
	}
	public void setH003_TDT(String h003_TDT) {
		H003_TDT = h003_TDT;
	}
	public String getH004_TTM() {
		return H004_TTM;
	}
	public void setH004_TTM(String h004_TTM) {
		H004_TTM = h004_TTM;
	}
	public String getF011_AUTHIDRESP() {
		return F011_AUTHIDRESP;
	}
	public void setF011_AUTHIDRESP(String f011_AUTHIDRESP) {
		F011_AUTHIDRESP = f011_AUTHIDRESP;
	}
	public String getF023_RRN() {
		return F023_RRN;
	}
	public void setF023_RRN(String f023_RRN) {
		F023_RRN = f023_RRN;
	}
	public String getPAN() {
		return PAN;
	}
	public void setPAN(String pAN) {
		PAN = pAN;
	}
	public String getTime_Stamp() {
		return Time_Stamp;
	}
	public void setTime_Stamp(String time_Stamp) {
		Time_Stamp = time_Stamp;
	}
	public String getF268_CHNAME() {
		return F268_CHNAME;
	}
	public void setF268_CHNAME(String f268_CHNAME) {
		F268_CHNAME = f268_CHNAME;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getTxnstatMsg() {
		return txnstatMsg;
	}
	public void setTxnstatMsg(String txnstatMsg) {
		this.txnstatMsg = txnstatMsg;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getF009_RESPCODE() {
		return F009_RESPCODE;
	}
	public void setF009_RESPCODE(String f009_RESPCODE) {
		F009_RESPCODE = f009_RESPCODE;
	}
	public String getRespMessage() {
		return respMessage;
	}
	public void setRespMessage(String respMessage) {
		this.respMessage = respMessage;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getF278_EMAILADDR() {
		return F278_EMAILADDR;
	}
	public void setF278_EMAILADDR(String f278_EMAILADDR) {
		F278_EMAILADDR = f278_EMAILADDR;
	}
	public String getF279_HP() {
		return F279_HP;
	}
	public void setF279_HP(String f279_HP) {
		F279_HP = f279_HP;
	}
	public String getPreauthfee() {
		return preauthfee;
	}
	public void setPreauthfee(String preauthfee) {
		this.preauthfee = preauthfee;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeAccNumber() {
		return payeeAccNumber;
	}
	public void setPayeeAccNumber(String payeeAccNumber) {
		this.payeeAccNumber = payeeAccNumber;
	}
	public String getPayoutStatus() {
		return payoutStatus;
	}
	public void setPayoutStatus(String payoutStatus) {
		this.payoutStatus = payoutStatus;
	}
	public String getPayoutAmount() {
		return payoutAmount;
	}
	public void setPayoutAmount(String payoutAmount) {
		this.payoutAmount = payoutAmount;
	}
	public String getPayeeBankName() {
		return payeeBankName;
	}
	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}
	public String getPayoutId() {
		return payoutId;
	}
	public void setPayoutId(String payoutId) {
		this.payoutId = payoutId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	
	
}
