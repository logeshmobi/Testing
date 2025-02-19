package com.mobiversa.payment.dto;

public class FinanceReport {
	
	private String time;
	private String mid;
	private String tid;
	private String amount;
	private String cardname;
	private String cardnumber;
	private String reference;
	private String approvalcode;
	private String rrn;
	private String status;
	private String paymentmethod;
	private String mdramount;
	private String netamount;
	private String paymentdate;
	private String ezysettleamount;
	private String fraudscore;
	private String fraudid;
	private String submerchantid;
	private String hostMdrAmt;
	private String mobiMdrAmt;
	

	//  for payout 
	
	private String timeStamp;
	private String customerName;
	private String brn;
	private String accountNo;
	private String bankName;
	private String transaction_id;
	private String payoutFee;
	private String paidDate;
	private String paidTime;
	private String submerchantMid;
	private String submerchantName;
	private String payoutId;
	private String declineReason;
	private String payoutType;

	private String date;
	private String payoutamount;

	public String getPayoutamount() {
		return payoutamount;
	}
	public void setPayoutamount(String payoutamount) {
		this.payoutamount = payoutamount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCardname() {
		return cardname;
	}
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getApprovalcode() {
		return approvalcode;
	}
	public void setApprovalcode(String approvalcode) {
		this.approvalcode = approvalcode;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPaymentmethod() {
		return paymentmethod;
	}
	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}
	public String getMdramount() {
		return mdramount;
	}
	public void setMdramount(String mdramount) {
		this.mdramount = mdramount;
	}
	public String getNetamount() {
		return netamount;
	}
	public void setNetamount(String netamount) {
		this.netamount = netamount;
	}
	public String getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(String paymentdate) {
		this.paymentdate = paymentdate;
	}
	public String getEzysettleamount() {
		return ezysettleamount;
	}
	public void setEzysettleamount(String ezysettleamount) {
		this.ezysettleamount = ezysettleamount;
	}
	public String getFraudscore() {
		return fraudscore;
	}
	public void setFraudscore(String fraudscore) {
		this.fraudscore = fraudscore;
	}
	public String getFraudid() {
		return fraudid;
	}
	public void setFraudid(String fraudid) {
		this.fraudid = fraudid;
	}
	public String getSubmerchantid() {
		return submerchantid;
	}
	public void setSubmerchantid(String submerchantid) {
		this.submerchantid = submerchantid;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBrn() {
		return brn;
	}
	public void setBrn(String brn) {
		this.brn = brn;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getPayoutFee() {
		return payoutFee;
	}
	public void setPayoutFee(String payoutFee) {
		this.payoutFee = payoutFee;
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
	public String getSubmerchantMid() {
		return submerchantMid;
	}
	public void setSubmerchantMid(String submerchantMid) {
		this.submerchantMid = submerchantMid;
	}
	public String getSubmerchantName() {
		return submerchantName;
	}
	public void setSubmerchantName(String submerchantName) {
		this.submerchantName = submerchantName;
	}
	public String getPayoutId() {
		return payoutId;
	}
	public void setPayoutId(String payoutId) {
		this.payoutId = payoutId;
	}
	public String getDeclineReason() {
		return declineReason;
	}
	public void setDeclineReason(String declineReason) {
		this.declineReason = declineReason;
	}
	public String getPayoutType() {
		return payoutType;
	}
	public void setPayoutType(String payoutType) {
		this.payoutType = payoutType;
	}
	

	public String getHostMdrAmt() {
		return hostMdrAmt;
	}
	public void setHostMdrAmt(String hostMdrAmt) {
		this.hostMdrAmt = hostMdrAmt;
	}
	public String getMobiMdrAmt() {
		return mobiMdrAmt;
	}
	public void setMobiMdrAmt(String mobiMdrAmt) {
		this.mobiMdrAmt = mobiMdrAmt;
	}
	
	@Override
	public String toString() {
		return "FinanceReport [time=" + time + ", mid=" + mid + ", tid=" + tid + ", amount=" + amount + ", cardname="
				+ cardname + ", cardnumber=" + cardnumber + ", reference=" + reference + ", approvalcode="
				+ approvalcode + ", rrn=" + rrn + ", status=" + status + ", paymentmethod=" + paymentmethod
				+ ", mdramount=" + mdramount + ", netamount=" + netamount + ", paymentdate=" + paymentdate
				+ ", ezysettleamount=" + ezysettleamount + ", fraudscore=" + fraudscore + ", fraudid=" + fraudid
				+ ", submerchantid=" + submerchantid + ", hostMdrAmt=" + hostMdrAmt + ", mobiMdrAmt=" + mobiMdrAmt
				+ ", timeStamp=" + timeStamp + ", customerName=" + customerName + ", brn=" + brn + ", accountNo="
				+ accountNo + ", bankName=" + bankName + ", transaction_id=" + transaction_id + ", payoutFee="
				+ payoutFee + ", paidDate=" + paidDate + ", paidTime=" + paidTime + ", submerchantMid=" + submerchantMid
				+ ", submerchantName=" + submerchantName + ", payoutId=" + payoutId + ", declineReason=" + declineReason
				+ ", payoutType=" + payoutType + ", date=" + date + ", payoutamount=" + payoutamount + "]";
	}
	
	
}
