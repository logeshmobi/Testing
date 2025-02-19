package com.mobiversa.payment.util;

import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.payment.dto.UMVoidResponse;

public class ResponseDetails {

	
	private String responseCode;
	private String responseMessage;
	private String responseDescription;
	private  ResponseData1 responseData;
	private  UMVoidResponse voidResp;
    private UMEcomTxnResponse saleResp;
	 
	

	public UMEcomTxnResponse getSaleResp() {
		return saleResp;
	}

	public void setSaleResp(UMEcomTxnResponse saleResp) {
		this.saleResp = saleResp;
	}
	//private String invoiceId;
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
	public ResponseData1 getResponseData() {
		return responseData;
	}
	public void setResponseData(ResponseData1 responseData) {
		this.responseData = responseData;
	}
	public UMVoidResponse getVoidResp() {
		return voidResp;
	}
	public void setVoidResp(UMVoidResponse voidResp) {
		this.voidResp = voidResp;
	}
	
	
}
