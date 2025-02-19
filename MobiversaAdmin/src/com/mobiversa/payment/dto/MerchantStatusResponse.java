package com.mobiversa.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantStatusResponse {

    private int responseCode;
    private String responseMessage;
    private String transactionStatus;
    private String transactionCode;
    private String fpxTransactionId;
    private String mobiTransactionId;
    private String transactionAmount;
    private String merchantOrderNo;

    private MerchantIPNResponse ipnResponse;

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getFpxTransactionId() {
        return fpxTransactionId;
    }

    public void setFpxTransactionId(String fpxTransactionId) {
        this.fpxTransactionId = fpxTransactionId;
    }

    public String getMobiTransactionId() {
        return mobiTransactionId;
    }

    public void setMobiTransactionId(String mobiTransactionId) {
        this.mobiTransactionId = mobiTransactionId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public MerchantIPNResponse getIpnResponse() {
        return ipnResponse;
    }

    public void setIpnResponse(MerchantIPNResponse ipnResponse) {
        this.ipnResponse = ipnResponse;
    }

    public MerchantStatusResponse(String transactionStatus, String transactionCode, String fpxTransactionId, String mobiTransactionId, String transactionAmount, String merchantOrderNo, MerchantIPNResponse ipnResponse) {
        this.transactionStatus = transactionStatus;
        this.transactionCode = transactionCode;
        this.fpxTransactionId = fpxTransactionId;
        this.mobiTransactionId = mobiTransactionId;
        this.transactionAmount = transactionAmount;
        this.merchantOrderNo = merchantOrderNo;
        this.ipnResponse = ipnResponse;
    }

    public MerchantStatusResponse() {

    }

    @Override
    public String toString() {
        return "MerchantStatusResponse{" +
                "transactionStatus='" + transactionStatus + '\'' +
                ", transactionCode='" + transactionCode + '\'' +
                ", fpxTransactionId='" + fpxTransactionId + '\'' +
                ", mobiTransactionId='" + mobiTransactionId + '\'' +
                ", transactionAmount='" + transactionAmount + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", ipnResponse=" + ipnResponse +
                '}';
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public MerchantStatusResponse(int responseCode, String responseMessage, String transactionStatus, String transactionCode, String fpxTransactionId, String mobiTransactionId, String transactionAmount, String merchantOrderNo, MerchantIPNResponse ipnResponse) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.transactionStatus = transactionStatus;
        this.transactionCode = transactionCode;
        this.fpxTransactionId = fpxTransactionId;
        this.mobiTransactionId = mobiTransactionId;
        this.transactionAmount = transactionAmount;
        this.merchantOrderNo = merchantOrderNo;
        this.ipnResponse = ipnResponse;
    }
}
