package com.mobiversa.payment.controller.bean;

public class OperationParentDataBean {

	private String date;
	private String bussinessName;
	private String mid;
	private String mmId;
	private String website;
	private String email;
	private String country;
	private String natureOfBusiness;
	private MDRDetailsBean mdrdetailsBean;
	private String mdrdetailsBeanJson;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBussinessName() {
		return bussinessName;
	}
	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}
	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}
	public MDRDetailsBean getMdrdetailsBean() {
		return mdrdetailsBean;
	}
	public void setMdrdetailsBean(MDRDetailsBean mdrdetailsBean) {
		this.mdrdetailsBean = mdrdetailsBean;
	}
	
	public String getMmId() {
		return mmId;
	}
	public void setMmId(String mmId) {
		this.mmId = mmId;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMdrdetailsBeanJson() {
		return mdrdetailsBeanJson;
	}
	public void setMdrdetailsBeanJson(String mdrdetailsBeanJson) {
		this.mdrdetailsBeanJson = mdrdetailsBeanJson;
	}
	@Override
	public String toString() {
		return "OperationParentDataBean [date=" + date + ", bussinessName=" + bussinessName + ", mid=" + mid + ", mmId="
				+ mmId + ", website=" + website + ", email=" + email + ", country=" + country + ", natureOfBusiness="
				+ natureOfBusiness + ", mdrdetailsBean=" + mdrdetailsBean + ", mdrdetailsBeanJson=" + mdrdetailsBeanJson
				+ "]";
	}
	
	
	
	
}
