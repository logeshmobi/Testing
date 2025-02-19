package com.mobiversa.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantIPNResponse {
    private boolean isIpnEnable;
    private int statusCode;
    private String responseDetails;
    private String url;

    public boolean isIpnEnable() {
        return isIpnEnable;
    }

    public void setIpnEnable(boolean ipnEnable) {
        isIpnEnable = ipnEnable;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MerchantIPNResponse(boolean isIpnEnable, int statusCode, String responseDetails, String url) {
        this.isIpnEnable = isIpnEnable;
        this.statusCode = statusCode;
        this.responseDetails = responseDetails;
        this.url = url;
    }

    public MerchantIPNResponse() {

    }

    @Override
    public String toString() {
        return "MerchantIPNResponse{" +
                "isIpnEnable=" + isIpnEnable +
                ", statusCode=" + statusCode +
                ", responseDetails='" + responseDetails + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
