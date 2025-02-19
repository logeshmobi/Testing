package com.mobiversa.payment.dto;

public class SettlementDetailsList {
    private String merchantId;
    private String businessName;
    private String previousBalance;

    private String settlementAmount;
    private String settlementDate;
    private String updatedAt;
    private String overDraftLimit;
    private String overDraftAmount;
    private String enableOverDraft;

    public String getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(String runningBalance) {
        this.runningBalance = runningBalance;
    }

    private String runningBalance;

//    public SettlementDetailsList(String merchantId, String merchantShortName, String previousBalance, String settlementAmount, String settlementDate, String updatedAt, String overDraftLimit, String overDraftAmount, String enableOverDraft) {
//        this.merchantId = merchantId;
//        this.merchantShortName = merchantShortName;
//        this.previousBalance = previousBalance;
//        this.settlementAmount = settlementAmount;
//        this.settlementDate = settlementDate;
//        this.updatedAt = updatedAt;
//        this.overDraftLimit = overDraftLimit;
//        this.overDraftAmount = overDraftAmount;
//        this.enableOverDraft = enableOverDraft;
//    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(String previousBalance) {
        this.previousBalance = previousBalance;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getOverDraftLimit() {
        return overDraftLimit;
    }

    public void setOverDraftLimit(String overDraftLimit) {
        this.overDraftLimit = overDraftLimit;
    }

    public String getOverDraftAmount() {
        return overDraftAmount;
    }

    public void setOverDraftAmount(String overDraftAmount) {
        this.overDraftAmount = overDraftAmount;
    }

    public String getEnableOverDraft() {
        return enableOverDraft;
    }

    public void setEnableOverDraft(String enableOverDraft) {
        this.enableOverDraft = enableOverDraft;
    }


    @Override
    public String toString() {
        return "SettlementDetailsList{" +
                "merchantId='" + merchantId + '\'' +
                ", businessName='" + businessName + '\'' +
                ", previousBalance='" + previousBalance + '\'' +
                ", settlementAmount='" + settlementAmount + '\'' +
                ", settlementDate='" + settlementDate + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", overDraftLimit='" + overDraftLimit + '\'' +
                ", overDraftAmount='" + overDraftAmount + '\'' +
                ", enableOverDraft='" + enableOverDraft + '\'' +
                ", runningBalance='" + runningBalance + '\'' +
                '}';
    }
}
