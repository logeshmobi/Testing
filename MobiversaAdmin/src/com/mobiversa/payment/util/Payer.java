package com.mobiversa.payment.util;

public class Payer {
	private String paymentDate;
	private String currency;
	private String totalMobiAmount;
	private String totalHostAmount;
	private String totalMerAmount;
	private String totalDedAmount;
	private String totalNetAmount;
	private String totalDisAmount;
	private String totalInvestorAmount;
	private String payerName;
	private String mid;
	private String cSettleAmount;
	private String cTotalCB;
	private String cRemainCB;
	private String cNetAmount;
	private String totalSetAmount;
	private String totalCBAmount;
	private String totalRCBAmount;
	private String totalCNetAmount;
	private String txnAmount;
	private String hostMdr;
	private String mobiMdr;
	private String investorMdr;
	private String netAmt;
	private String date;
	

	private String bankRequestFee;
	private String bankFee;
	
	private String settlementDate;
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTotalInvestorAmount() {
		return totalInvestorAmount;
	}

	public void setTotalInvestorAmount(String totalInvestorAmount) {
		this.totalInvestorAmount = totalInvestorAmount;
	}

	public String getInvestorMdr() {
		return investorMdr;
	}

	public void setInvestorMdr(String investorMdr) {
		this.investorMdr = investorMdr;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
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

	public String getNetAmt() {
		return netAmt;
	}

	public void setNetAmt(String netAmt) {
		this.netAmt = netAmt;
	}

	public String getTotalDisAmount() {
		return totalDisAmount;
	}

	public void setTotalDisAmount(String totalDisAmount) {
		this.totalDisAmount = totalDisAmount;
	}

	public String getTotalSetAmount() {
		return totalSetAmount;
	}

	public void setTotalSetAmount(String totalSetAmount) {
		this.totalSetAmount = totalSetAmount;
	}

	public String getTotalCBAmount() {
		return totalCBAmount;
	}

	public void setTotalCBAmount(String totalCBAmount) {
		this.totalCBAmount = totalCBAmount;
	}

	public String getTotalRCBAmount() {
		return totalRCBAmount;
	}

	public void setTotalRCBAmount(String totalRCBAmount) {
		this.totalRCBAmount = totalRCBAmount;
	}

	public String getTotalCNetAmount() {
		return totalCNetAmount;
	}

	public void setTotalCNetAmount(String totalCNetAmount) {
		this.totalCNetAmount = totalCNetAmount;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getcSettleAmount() {
		return cSettleAmount;
	}

	public void setcSettleAmount(String cSettleAmount) {
		this.cSettleAmount = cSettleAmount;
	}

	public String getcTotalCB() {
		return cTotalCB;
	}

	public void setcTotalCB(String cTotalCB) {
		this.cTotalCB = cTotalCB;
	}

	public String getcRemainCB() {
		return cRemainCB;
	}

	public void setcRemainCB(String cRemainCB) {
		this.cRemainCB = cRemainCB;
	}

	public String getcNetAmount() {
		return cNetAmount;
	}

	public void setcNetAmount(String cNetAmount) {
		this.cNetAmount = cNetAmount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getTotalMobiAmount() {
		return totalMobiAmount;
	}

	public void setTotalMobiAmount(String totalMobiAmount) {
		this.totalMobiAmount = totalMobiAmount;
	}

	public String getTotalHostAmount() {
		return totalHostAmount;
	}

	public void setTotalHostAmount(String totalHostAmount) {
		this.totalHostAmount = totalHostAmount;
	}

	public String getTotalMerAmount() {
		return totalMerAmount;
	}

	public void setTotalMerAmount(String totalMerAmount) {
		this.totalMerAmount = totalMerAmount;
	}

	public String getTotalDedAmount() {
		return totalDedAmount;
	}

	public void setTotalDedAmount(String totalDedAmount) {
		this.totalDedAmount = totalDedAmount;
	}

	public String getTotalNetAmount() {
		return totalNetAmount;
	}

	public void setTotalNetAmount(String totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}

	public String getBankRequestFee() {
		return bankRequestFee;
	}

	public void setBankRequestFee(String bankRequestFee) {
		this.bankRequestFee = bankRequestFee;
	}

	public String getBankFee() {
		return bankFee;
	}

	public void setBankFee(String bankFee) {
		this.bankFee = bankFee;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	@Override
	public String toString() {
		return "Payer [paymentDate=" + paymentDate + ", currency=" + currency + ", totalMobiAmount=" + totalMobiAmount
				+ ", totalHostAmount=" + totalHostAmount + ", totalMerAmount=" + totalMerAmount + ", totalDedAmount="
				+ totalDedAmount + ", totalNetAmount=" + totalNetAmount + ", totalDisAmount=" + totalDisAmount
				+ ", totalInvestorAmount=" + totalInvestorAmount + ", payerName=" + payerName + ", mid=" + mid
				+ ", cSettleAmount=" + cSettleAmount + ", cTotalCB=" + cTotalCB + ", cRemainCB=" + cRemainCB
				+ ", cNetAmount=" + cNetAmount + ", totalSetAmount=" + totalSetAmount + ", totalCBAmount="
				+ totalCBAmount + ", totalRCBAmount=" + totalRCBAmount + ", totalCNetAmount=" + totalCNetAmount
				+ ", txnAmount=" + txnAmount + ", hostMdr=" + hostMdr + ", mobiMdr=" + mobiMdr + ", investorMdr="
				+ investorMdr + ", netAmt=" + netAmt + ", date=" + date + ", bankRequestFee=" + bankRequestFee
				+ ", bankFee=" + bankFee + ", settlementDate=" + settlementDate + "]";
	}

}
