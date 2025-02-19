package com.mobiversa.payment.controller.bean;

import com.mobiversa.payment.connect.ResponseData;

public class ResponseDetails1 {
	private String responseCode;
	private String responseMessage;
	private String responseDescription;
	private String id;
	private String natureOfBusiness;
	private String businessName;
	private long payoutGrandDetail;
	private long mid;
	private ResponseData responseData;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ResponseData getResponseData() {
		return responseData;
	}

	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public long getPayoutGrandDetail() {
		return payoutGrandDetail;
	}

	public void setPayoutGrandDetail(long payoutGrandDetail) {
		this.payoutGrandDetail = payoutGrandDetail;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "ResponseDetails [responseCode=" + responseCode + ", responseMessage=" + responseMessage
				+ ", responseDescription=" + responseDescription + ", id=" + id + ", natureOfBusiness="
				+ natureOfBusiness + ", businessName=" + businessName + ", payoutGrandDetail=" + payoutGrandDetail
				+ ", mid=" + mid + ", responseData=" + responseData + "]";
	}

}
