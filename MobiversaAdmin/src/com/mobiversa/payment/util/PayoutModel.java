package com.mobiversa.payment.util;

public class PayoutModel {
	
	private String createdby;
	private String createddate;
	private String modifiedby;
	private String modifieddate;
	
	private String payeeaccnumber;

	private String payeebrn;

	private String payeebankname;
	
	private String payeeic;
	private String payeemobile;
	private String payeename;
	private String payeeemail;
	private String payoutamount;
	private String payoutdate;
	private String payoutstatus;
	private String settledate;
	private String settlenetamount;
	private String invoiceidproof;
	private String paymentreason;
	private String sourceoffund;
	private String swiftifsccode;
	
	private String merchantId;
	private String merchantName;
	private String NetAmount;
	private String paidTime;
	private String paidDate;
	private String submerchantMid;
	private String mmId;
	
	private String overDraftAmt;
	
	private String payoutId;
	
	private String payoutfee;

	private String payouttype;
	
	
	private String overDraftLimit;
	private String availableBalance;
	private String timeStamp;
	private String srcrefno;
	private String paymentReference;
	private String curlecRefNo;
	
	
	
	public String getCurlecRefNo() {
		return curlecRefNo;
	}
	public void setCurlecRefNo(String curlecRefNo) {
		this.curlecRefNo = curlecRefNo;
	}
	public String getPaymentReference() {
		return paymentReference;
	}
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}
	public String getPayouttype() {
		return payouttype;
	}
	public void setPayouttype(String payouttype) {
		this.payouttype = payouttype;
	}
	public String getPayoutfee() {
		return payoutfee;
	}
	public void setPayoutfee(String payoutfee) {
		this.payoutfee = payoutfee;
	}
	public String getPayoutId() {
		return payoutId;
	}
	public void setPayoutId(String payoutId) {
		this.payoutId = payoutId;
	}


	//payout Decline Reason
	 private String failurereason;

	 public String getFailurereason()  {
		 return failurereason;
		 }
	 public void setFailurereason(String failurereason) {
		 this.failurereason = failurereason;
	 }
	
	
	//27-03-2023
	   private String businessname;
	   private String Id;
	   private String totalamount;
	   private String depositamount;
	   private String settlementdate;
	   private String balanceNetAmt;
	   private String responseCode;
	   private String overdraftAmount;
	   
	   
	   
	   
	   public String getDepositamount() {
		return depositamount;
	}
	public void setDepositamount(String depositamount) {
		this.depositamount = depositamount;
	}
	public String getOverdraftAmount() {
		return overdraftAmount;
	}
	public void setOverdraftAmount(String overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}
	public String getResponseCode() {
			return responseCode;
		}
		public void setResponseCode(String responseCode) {
			this.responseCode = responseCode;
		}
	   
	  
	  
	public String getBalanceNetAmt() {
			return balanceNetAmt;
		}
		public void setBalanceNetAmt(String balanceNetAmt) {
			this.balanceNetAmt = balanceNetAmt;
		}
	public String getOverDraftAmt() {
			return overDraftAmt;
		}
		public void setOverDraftAmt(String overDraftAmt) {
			this.overDraftAmt = overDraftAmt;
		}
	public String getBusinessname() {
		return businessname;
	}
	public void setBusinessname(String businessname) {
		this.businessname = businessname;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getTotalamount() {
		return totalamount;
	}
	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}
	
	public String getSettlementdate() {
		return settlementdate;
	}
	public void setSettlementdate(String settlementdate) {
		this.settlementdate = settlementdate;
	}
	public String getSubmerchantMid() {
		return submerchantMid;
	}
	public void setSubmerchantMid(String submerchantMid) {
		this.submerchantMid = submerchantMid;
	}
	public String getMmId() {
		return mmId;
	}
	public void setMmId(String mmId) {
		this.mmId = mmId;
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
	public String getPayeeaccnumber() {
		return payeeaccnumber;
	}
	public void setPayeeaccnumber(String payeeaccnumber) {
		this.payeeaccnumber = payeeaccnumber;
	}
	public String getPayeebrn() {
		return payeebrn;
	}
	public void setPayeebrn(String payeebrn) {
		this.payeebrn = payeebrn;
	}
	public String getPayeebankname() {
		return payeebankname;
	}
	public void setPayeebankname(String payeebankname) {
		this.payeebankname = payeebankname;
	}
	public String getPayeeic() {
		return payeeic;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getNetAmount() {
		return NetAmount;
	}
	public void setNetAmount(String netAmount) {
		NetAmount = netAmount;
	}
	public void setPayeeic(String payeeic) {
		this.payeeic = payeeic;
	}
	public String getPayeemobile() {
		return payeemobile;
	}
	public void setPayeemobile(String payeemobile) {
		this.payeemobile = payeemobile;
	}
	public String getPayeename() {
		return payeename;
	}
	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}
	public String getPayeeemail() {
		return payeeemail;
	}
	public void setPayeeemail(String payeeemail) {
		this.payeeemail = payeeemail;
	}
	public String getPayoutamount() {
		return payoutamount;
	}
	public void setPayoutamount(String payoutamount) {
		this.payoutamount = payoutamount;
	}
	public String getPayoutdate() {
		return payoutdate;
	}
	public void setPayoutdate(String payoutdate) {
		this.payoutdate = payoutdate;
	}
	public String getPayoutstatus() {
		return payoutstatus;
	}
	public void setPayoutstatus(String payoutstatus) {
		this.payoutstatus = payoutstatus;
	}
	public String getSettledate() {
		return settledate;
	}
	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}
	public String getSettlenetamount() {
		return settlenetamount;
	}
	public void setSettlenetamount(String settlenetamount) {
		this.settlenetamount = settlenetamount;
	}
	public String getInvoiceidproof() {
		return invoiceidproof;
	}
	public void setInvoiceidproof(String invoiceidproof) {
		this.invoiceidproof = invoiceidproof;
	}
	public String getPaymentreason() {
		return paymentreason;
	}
	public void setPaymentreason(String paymentreason) {
		this.paymentreason = paymentreason;
	}
	public String getSourceoffund() {
		return sourceoffund;
	}
	public void setSourceoffund(String sourceoffund) {
		this.sourceoffund = sourceoffund;
	}

	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCreateddate() {
		return createddate;
	}
	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
	}
	public String getSwiftifsccode() {
		return swiftifsccode;
	}
	public void setSwiftifsccode(String swiftifsccode) {
		this.swiftifsccode = swiftifsccode;
	}
	public String getOverDraftLimit() {
		return overDraftLimit;
	}
	public void setOverDraftLimit(String overDraftLimit) {
		this.overDraftLimit = overDraftLimit;
	}
	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSrcrefno() {
		return srcrefno;
	}
	public void setSrcrefno(String srcrefno) {
		this.srcrefno = srcrefno;
	}

	
	
}
