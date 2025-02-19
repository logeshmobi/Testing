package com.mobiversa.payment.dto;

public class SubmerchantDto {

	private String createdDate;
	private String businessName;
	private String subMerchantMID;
	private String mmId;
	private String status;
	private String country;
	private String website;
	private String email;
	private String natureOfBusiness;
	private String subMerchantDescription;
	
	private int SubmerchantListSizeForOperationChild;
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getSubMerchantMID() {
		return subMerchantMID;
	}
	public void setSubMerchantMID(String subMerchantMID) {
		this.subMerchantMID = subMerchantMID;
	}
	public String getMmId() {
		return mmId;
	}
	public void setMmId(String mmId) {
		this.mmId = mmId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}
	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}
	public String getSubMerchantDescription() {
		return subMerchantDescription;
	}
	public void setSubMerchantDescription(String subMerchantDescription) {
		this.subMerchantDescription = subMerchantDescription;
	}
	
	
	public int getSubmerchantListSizeForOperationChild() {
		return SubmerchantListSizeForOperationChild;
	}
	public void setSubmerchantListSizeForOperationChild(int submerchantListSizeForOperationChild) {
		SubmerchantListSizeForOperationChild = submerchantListSizeForOperationChild;
	}
	@Override
	public String toString() {
		return "SubmerchantDto [createdDate=" + createdDate + ", businessName=" + businessName + ", subMerchantMID="
				+ subMerchantMID + ", mmId=" + mmId + ", status=" + status + ", country=" + country + ", website="
				+ website + ", email=" + email + ", natureOfBusiness=" + natureOfBusiness + ", subMerchantDescription="
				+ subMerchantDescription + "]";
	}
	
	
}
