package com.mobiversa.payment.dto;

public class FpxStatusRequest {
    private String mid;
    private String tid;
    private String merchantOrderNo;


    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    @Override
    public String toString() {
        return "FpxStatusRequest{" +
                "mid='" + mid + '\'' +
                ", tid='" + tid + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                '}';
    }

    public FpxStatusRequest(String mid, String tid, String merchantOrderNo) {
        this.mid = mid;
        this.tid = tid;
        this.merchantOrderNo = merchantOrderNo;
    }
    public FpxStatusRequest()
    {

    }
}
