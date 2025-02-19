package com.mobiversa.payment.dto;

public class RegMerchantMDR {

	public String id;
	public String mid;
	public String prodId;
	public String cardBrand;
	public String businessName;
	private float creditLocalHostMDR;
	private float creditLocalMerchantMDR;
	private float creditLocalMobiMDR;
	private float creditForeignHostMDR;
	private float creditForeignMerchantMDR;
	private float creditForeignMobiMDR;
	private float debitLocalHostMDR;
	private float debitLocalMerchantMDR;
	private float debitLocalMobiMDR;
	private float debitForeignHostMDR;
	private float debitForeignMerchantMDR;
	private float debitForeignMobiMDR;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	public String getCardBrand() {
		return cardBrand;
	}
	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public float getCreditLocalHostMDR() {
		return creditLocalHostMDR;
	}
	public void setCreditLocalHostMDR(float creditLocalHostMDR) {
		this.creditLocalHostMDR = creditLocalHostMDR;
	}
	public float getCreditLocalMerchantMDR() {
		return creditLocalMerchantMDR;
	}
	public void setCreditLocalMerchantMDR(float creditLocalMerchantMDR) {
		this.creditLocalMerchantMDR = creditLocalMerchantMDR;
	}
	public float getCreditLocalMobiMDR() {
		return creditLocalMobiMDR;
	}
	public void setCreditLocalMobiMDR(float creditLocalMobiMDR) {
		this.creditLocalMobiMDR = creditLocalMobiMDR;
	}
	public float getCreditForeignHostMDR() {
		return creditForeignHostMDR;
	}
	public void setCreditForeignHostMDR(float creditForeignHostMDR) {
		this.creditForeignHostMDR = creditForeignHostMDR;
	}
	public float getCreditForeignMerchantMDR() {
		return creditForeignMerchantMDR;
	}
	public void setCreditForeignMerchantMDR(float creditForeignMerchantMDR) {
		this.creditForeignMerchantMDR = creditForeignMerchantMDR;
	}
	public float getCreditForeignMobiMDR() {
		return creditForeignMobiMDR;
	}
	public void setCreditForeignMobiMDR(float creditForeignMobiMDR) {
		this.creditForeignMobiMDR = creditForeignMobiMDR;
	}
	public float getDebitLocalHostMDR() {
		return debitLocalHostMDR;
	}
	public void setDebitLocalHostMDR(float debitLocalHostMDR) {
		this.debitLocalHostMDR = debitLocalHostMDR;
	}
	public float getDebitLocalMerchantMDR() {
		return debitLocalMerchantMDR;
	}
	public void setDebitLocalMerchantMDR(float debitLocalMerchantMDR) {
		this.debitLocalMerchantMDR = debitLocalMerchantMDR;
	}
	public float getDebitLocalMobiMDR() {
		return debitLocalMobiMDR;
	}
	public void setDebitLocalMobiMDR(float debitLocalMobiMDR) {
		this.debitLocalMobiMDR = debitLocalMobiMDR;
	}
	public float getDebitForeignMerchantMDR() {
		return debitForeignMerchantMDR;
	}
	public void setDebitForeignMerchantMDR(float debitForeignMerchantMDR) {
		this.debitForeignMerchantMDR = debitForeignMerchantMDR;
	}
	public float getDebitForeignMobiMDR() {
		return debitForeignMobiMDR;
	}
	public void setDebitForeignMobiMDR(float debitForeignMobiMDR) {
		this.debitForeignMobiMDR = debitForeignMobiMDR;
	}
	public float getDebitForeignHostMDR() {
		return debitForeignHostMDR;
	}
	public void setDebitForeignHostMDR(float debitForeignHostMDR) {
		this.debitForeignHostMDR = debitForeignHostMDR;
	}
	
}
