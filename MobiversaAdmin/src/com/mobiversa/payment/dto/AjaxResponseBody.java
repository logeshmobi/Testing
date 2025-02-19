package com.mobiversa.payment.dto;

//import jdk.nashorn.internal.objects.annotations.Getter;
//import jdk.nashorn.internal.objects.annotations.Setter;


public class AjaxResponseBody {
    private String responseCode;
    private String ppId;
    private boolean isSuccess;
    private String url;
    private int attempts;
    private String responseFromFpxStatusUrl;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getPpId() {
        return ppId;
    }

    public void setPpId(String ppId) {
        this.ppId = ppId;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }


    public AjaxResponseBody()
    {

    }

    public AjaxResponseBody(String responseCode, String ppId, boolean isSuccess, String url) {
        this.responseCode = responseCode;
        this.ppId = ppId;
        this.isSuccess = isSuccess;
        this.url = url;
    }
    public AjaxResponseBody(String responseCode, String ppId, boolean isSuccess,int attempts,String responseFromFpxStatusUrl) {
        this.responseCode = responseCode;
        this.ppId = ppId;
        this.isSuccess = isSuccess;
        this.attempts = attempts;
        this.responseFromFpxStatusUrl = responseFromFpxStatusUrl;
    }
    public AjaxResponseBody(int attempts)
    {
        this.attempts = attempts;
    }

    @Override
    public String toString() {
        return "AjaxResponseBody{" +
                "responseCode='" + responseCode + '\'' +
                ", ppId='" + ppId + '\'' +
                ", isSuccess=" + isSuccess +
                ", url='" + url + '\'' +
                '}';
    }
    public int getAttempts() {
        return attempts;
    }
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

}
