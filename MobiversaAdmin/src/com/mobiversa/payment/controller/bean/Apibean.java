package com.mobiversa.payment.controller.bean;

import java.util.List;

import com.mobiversa.payment.connect.ResponseData;

public class Apibean {
	private String responseCode;
	private String responseMessage;
	private String responseDescription;
	private ResponseData responseData;
	private List<ResponseData> responseList;
	

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
	public ResponseData getResponseData() {
		return responseData;
	}
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}
	public List<ResponseData> getResponseList() {
		return responseList;
	}
	public void setResponseList(List<ResponseData> responseList) {
		this.responseList = responseList;
	}

}
