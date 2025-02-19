package com.mobiversa.payment.dto;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EditMDRDetailsBean {

	private List<String> type;
	private String mid;

	// Fpx Mdr Details
	private String fpxMerchantMDR;
	private String fpxHostMDR;
	private String fpxMobiMDR;
	private String fpxMinimumMDR;

	// Boost Mdr Details
	private String boostMerchantMDR;
	private String boostHostMDR;
	private String boostMobiMDR;
	private String boostMinimumMDR;

	// Grab Mdr Details
	private String grabMerchantMDR;
	private String grabHostMDR;
	private String grabMobiMDR;
	private String grabMinimumMDR;

	// TNG Mdr Details
	private String tngMerchantMDR;
	private String tngHostMDR;
	private String tngMobiMDR;
	private String tngMinimumMDR;

	// SPP Mdr Details
	private String sppMerchantMDR;
	private String sppHostMDR;
	private String sppMobiMDR;
	private String sppMinimumMDR;

	// M1Pay Mdr Details
	private String m1PayMerchantMDR;
	private String m1PayHostMDR;
	private String m1PayMobiMDR;
	private String m1PayMinimumMDR;

	// Payout Mdr Details
	private String payoutMerchantMDR;
	private String payoutHostMDR;
	private String payoutMobiMDR;
	private String payoutMinimumMDR;

	// Visa Mdr Details
	private String visaLocalDebitCardMDR;
	private String visaLocalCreditCardMDR;
	private String visaForeignDebitCardMDR;
	private String visaForeignCreditCardMDR;

	// Master Mdr Details
	private String masterLocalDebitCardMDR;
	private String masterLocalCreditCardMDR;
	private String masterForeignDebitCardMDR;
	private String masterForeignCreditCardMDR;

	// UnionPay Mdr Details
	private String unionPayLocalDebitCardMDR;
	private String unionPayLocalCreditCardMDR;
	private String unionPayForeignDebitCardMDR;
	private String unionPayForeignCreditCardMDR;

	// Min_Values
	private String minValue;

	public String getFpxMerchantMDR() {
		return fpxMerchantMDR;
	}

	public void setFpxMerchantMDR(String fpxMerchantMDR) {
		this.fpxMerchantMDR = fpxMerchantMDR;
	}

	public String getFpxHostMDR() {
		return fpxHostMDR;
	}

	public void setFpxHostMDR(String fpxHostMDR) {
		this.fpxHostMDR = fpxHostMDR;
	}

	public String getFpxMobiMDR() {
		return fpxMobiMDR;
	}

	public void setFpxMobiMDR(String fpxMobiMDR) {
		this.fpxMobiMDR = fpxMobiMDR;
	}

	public String getFpxMinimumMDR() {
		return fpxMinimumMDR;
	}

	public void setFpxMinimumMDR(String fpxMinimumMDR) {
		this.fpxMinimumMDR = fpxMinimumMDR;
	}

	public String getBoostMerchantMDR() {
		return boostMerchantMDR;
	}

	public void setBoostMerchantMDR(String boostMerchantMDR) {
		this.boostMerchantMDR = boostMerchantMDR;
	}

	public String getBoostHostMDR() {
		return boostHostMDR;
	}

	public void setBoostHostMDR(String boostHostMDR) {
		this.boostHostMDR = boostHostMDR;
	}

	public String getBoostMobiMDR() {
		return boostMobiMDR;
	}

	public void setBoostMobiMDR(String boostMobiMDR) {
		this.boostMobiMDR = boostMobiMDR;
	}

	public String getBoostMinimumMDR() {
		return boostMinimumMDR;
	}

	public void setBoostMinimumMDR(String boostMinimumMDR) {
		this.boostMinimumMDR = boostMinimumMDR;
	}

	public String getGrabMerchantMDR() {
		return grabMerchantMDR;
	}

	public void setGrabMerchantMDR(String grabMerchantMDR) {
		this.grabMerchantMDR = grabMerchantMDR;
	}

	public String getGrabHostMDR() {
		return grabHostMDR;
	}

	public void setGrabHostMDR(String grabHostMDR) {
		this.grabHostMDR = grabHostMDR;
	}

	public String getGrabMobiMDR() {
		return grabMobiMDR;
	}

	public void setGrabMobiMDR(String grabMobiMDR) {
		this.grabMobiMDR = grabMobiMDR;
	}

	public String getGrabMinimumMDR() {
		return grabMinimumMDR;
	}

	public void setGrabMinimumMDR(String grabMinimumMDR) {
		this.grabMinimumMDR = grabMinimumMDR;
	}

	public String getTngMerchantMDR() {
		return tngMerchantMDR;
	}

	public void setTngMerchantMDR(String tngMerchantMDR) {
		this.tngMerchantMDR = tngMerchantMDR;
	}

	public String getTngHostMDR() {
		return tngHostMDR;
	}

	public void setTngHostMDR(String tngHostMDR) {
		this.tngHostMDR = tngHostMDR;
	}

	public String getTngMobiMDR() {
		return tngMobiMDR;
	}

	public void setTngMobiMDR(String tngMobiMDR) {
		this.tngMobiMDR = tngMobiMDR;
	}

	public String getTngMinimumMDR() {
		return tngMinimumMDR;
	}

	public void setTngMinimumMDR(String tngMinimumMDR) {
		this.tngMinimumMDR = tngMinimumMDR;
	}

	public String getSppMerchantMDR() {
		return sppMerchantMDR;
	}

	public void setSppMerchantMDR(String sppMerchantMDR) {
		this.sppMerchantMDR = sppMerchantMDR;
	}

	public String getSppHostMDR() {
		return sppHostMDR;
	}

	public void setSppHostMDR(String sppHostMDR) {
		this.sppHostMDR = sppHostMDR;
	}

	public String getSppMobiMDR() {
		return sppMobiMDR;
	}

	public void setSppMobiMDR(String sppMobiMDR) {
		this.sppMobiMDR = sppMobiMDR;
	}

	public String getSppMinimumMDR() {
		return sppMinimumMDR;
	}

	public void setSppMinimumMDR(String sppMinimumMDR) {
		this.sppMinimumMDR = sppMinimumMDR;
	}

	public String getM1PayMerchantMDR() {
		return m1PayMerchantMDR;
	}

	public void setM1PayMerchantMDR(String m1PayMerchantMDR) {
		this.m1PayMerchantMDR = m1PayMerchantMDR;
	}

	public String getM1PayHostMDR() {
		return m1PayHostMDR;
	}

	public void setM1PayHostMDR(String m1PayHostMDR) {
		this.m1PayHostMDR = m1PayHostMDR;
	}

	public String getM1PayMobiMDR() {
		return m1PayMobiMDR;
	}

	public void setM1PayMobiMDR(String m1PayMobiMDR) {
		this.m1PayMobiMDR = m1PayMobiMDR;
	}

	public String getM1PayMinimumMDR() {
		return m1PayMinimumMDR;
	}

	public void setM1PayMinimumMDR(String m1PayMinimumMDR) {
		this.m1PayMinimumMDR = m1PayMinimumMDR;
	}

	public String getPayoutMerchantMDR() {
		return payoutMerchantMDR;
	}

	public void setPayoutMerchantMDR(String payoutMerchantMDR) {
		this.payoutMerchantMDR = payoutMerchantMDR;
	}

	public String getPayoutHostMDR() {
		return payoutHostMDR;
	}

	public void setPayoutHostMDR(String payoutHostMDR) {
		this.payoutHostMDR = payoutHostMDR;
	}

	public String getPayoutMobiMDR() {
		return payoutMobiMDR;
	}

	public void setPayoutMobiMDR(String payoutMobiMDR) {
		this.payoutMobiMDR = payoutMobiMDR;
	}

	public String getPayoutMinimumMDR() {
		return payoutMinimumMDR;
	}

	public void setPayoutMinimumMDR(String payoutMinimumMDR) {
		this.payoutMinimumMDR = payoutMinimumMDR;
	}

	public String getVisaLocalDebitCardMDR() {
		return visaLocalDebitCardMDR;
	}

	public void setVisaLocalDebitCardMDR(String visaLocalDebitCardMDR) {
		this.visaLocalDebitCardMDR = visaLocalDebitCardMDR;
	}

	public String getVisaLocalCreditCardMDR() {
		return visaLocalCreditCardMDR;
	}

	public void setVisaLocalCreditCardMDR(String visaLocalCreditCardMDR) {
		this.visaLocalCreditCardMDR = visaLocalCreditCardMDR;
	}

	public String getVisaForeignDebitCardMDR() {
		return visaForeignDebitCardMDR;
	}

	public void setVisaForeignDebitCardMDR(String visaForeignDebitCardMDR) {
		this.visaForeignDebitCardMDR = visaForeignDebitCardMDR;
	}

	public String getVisaForeignCreditCardMDR() {
		return visaForeignCreditCardMDR;
	}

	public void setVisaForeignCreditCardMDR(String visaForeignCreditCardMDR) {
		this.visaForeignCreditCardMDR = visaForeignCreditCardMDR;
	}

	public String getMasterLocalDebitCardMDR() {
		return masterLocalDebitCardMDR;
	}

	public void setMasterLocalDebitCardMDR(String masterLocalDebitCardMDR) {
		this.masterLocalDebitCardMDR = masterLocalDebitCardMDR;
	}

	public String getMasterLocalCreditCardMDR() {
		return masterLocalCreditCardMDR;
	}

	public void setMasterLocalCreditCardMDR(String masterLocalCreditCardMDR) {
		this.masterLocalCreditCardMDR = masterLocalCreditCardMDR;
	}

	public String getMasterForeignDebitCardMDR() {
		return masterForeignDebitCardMDR;
	}

	public void setMasterForeignDebitCardMDR(String masterForeignDebitCardMDR) {
		this.masterForeignDebitCardMDR = masterForeignDebitCardMDR;
	}

	public String getMasterForeignCreditCardMDR() {
		return masterForeignCreditCardMDR;
	}

	public void setMasterForeignCreditCardMDR(String masterForeignCreditCardMDR) {
		this.masterForeignCreditCardMDR = masterForeignCreditCardMDR;
	}

	public String getUnionPayLocalDebitCardMDR() {
		return unionPayLocalDebitCardMDR;
	}

	public void setUnionPayLocalDebitCardMDR(String unionPayLocalDebitCardMDR) {
		this.unionPayLocalDebitCardMDR = unionPayLocalDebitCardMDR;
	}

	public String getUnionPayLocalCreditCardMDR() {
		return unionPayLocalCreditCardMDR;
	}

	public void setUnionPayLocalCreditCardMDR(String unionPayLocalCreditCardMDR) {
		this.unionPayLocalCreditCardMDR = unionPayLocalCreditCardMDR;
	}

	public String getUnionPayForeignDebitCardMDR() {
		return unionPayForeignDebitCardMDR;
	}

	public void setUnionPayForeignDebitCardMDR(String unionPayForeignDebitCardMDR) {
		this.unionPayForeignDebitCardMDR = unionPayForeignDebitCardMDR;
	}

	public String getUnionPayForeignCreditCardMDR() {
		return unionPayForeignCreditCardMDR;
	}

	public void setUnionPayForeignCreditCardMDR(String unionPayForeignCreditCardMDR) {
		this.unionPayForeignCreditCardMDR = unionPayForeignCreditCardMDR;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "EditMDRDetailsBean [type=" + type + ", mid=" + mid + ", fpxMerchantMDR=" + fpxMerchantMDR
				+ ", fpxHostMDR=" + fpxHostMDR + ", fpxMobiMDR=" + fpxMobiMDR + ", fpxMinimumMDR=" + fpxMinimumMDR
				+ ", boostMerchantMDR=" + boostMerchantMDR + ", boostHostMDR=" + boostHostMDR + ", boostMobiMDR="
				+ boostMobiMDR + ", boostMinimumMDR=" + boostMinimumMDR + ", grabMerchantMDR=" + grabMerchantMDR
				+ ", grabHostMDR=" + grabHostMDR + ", grabMobiMDR=" + grabMobiMDR + ", grabMinimumMDR=" + grabMinimumMDR
				+ ", tngMerchantMDR=" + tngMerchantMDR + ", tngHostMDR=" + tngHostMDR + ", tngMobiMDR=" + tngMobiMDR
				+ ", tngMinimumMDR=" + tngMinimumMDR + ", sppMerchantMDR=" + sppMerchantMDR + ", sppHostMDR="
				+ sppHostMDR + ", sppMobiMDR=" + sppMobiMDR + ", sppMinimumMDR=" + sppMinimumMDR + ", m1PayMerchantMDR="
				+ m1PayMerchantMDR + ", m1PayHostMDR=" + m1PayHostMDR + ", m1PayMobiMDR=" + m1PayMobiMDR
				+ ", m1PayMinimumMDR=" + m1PayMinimumMDR + ", payoutMerchantMDR=" + payoutMerchantMDR
				+ ", payoutHostMDR=" + payoutHostMDR + ", payoutMobiMDR=" + payoutMobiMDR + ", payoutMinimumMDR="
				+ payoutMinimumMDR + ", visaLocalDebitCardMDR=" + visaLocalDebitCardMDR + ", visaLocalCreditCardMDR="
				+ visaLocalCreditCardMDR + ", visaForeignDebitCardMDR=" + visaForeignDebitCardMDR
				+ ", visaForeignCreditCardMDR=" + visaForeignCreditCardMDR + ", masterLocalDebitCardMDR="
				+ masterLocalDebitCardMDR + ", masterLocalCreditCardMDR=" + masterLocalCreditCardMDR
				+ ", masterForeignDebitCardMDR=" + masterForeignDebitCardMDR + ", masterForeignCreditCardMDR="
				+ masterForeignCreditCardMDR + ", unionPayLocalDebitCardMDR=" + unionPayLocalDebitCardMDR
				+ ", unionPayLocalCreditCardMDR=" + unionPayLocalCreditCardMDR + ", unionPayForeignDebitCardMDR="
				+ unionPayForeignDebitCardMDR + ", unionPayForeignCreditCardMDR=" + unionPayForeignCreditCardMDR
				+ ", minValue=" + minValue + "]";
	}

}