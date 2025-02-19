package com.mobiversa.payment.dto;

import com.mobiversa.common.bo.PayoutHoldTxn;

public class PayoutAccountEnquiryDto {
    private String date;
    private String time;
    private String name;
    private String nameInBank;
    private String accountNumber;
    private String amount;
    private String payoutId;
    private String pendingReason;
    private String invoiceId;
    private String merchantName;

    private String status;


    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getNameInBank() {
        return nameInBank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public String getPayoutId() {
        return payoutId;
    }

    public String getPendingReason() {
        return pendingReason;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameInBank(String nameInBank) {
        this.nameInBank = nameInBank;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPayoutId(String payoutId) {
        this.payoutId = payoutId;
    }

    public void setPendingReason(String pendingReason) {
        this.pendingReason = pendingReason;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "PayoutAccountEnquiryDto{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", nameInBank='" + nameInBank + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount='" + amount + '\'' +
                ", payoutId='" + payoutId + '\'' +
                ", pendingReason='" + pendingReason + '\'' +
                ", invoiceId='" + invoiceId + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
    public PayoutAccountEnquiryDto(PayoutHoldTxn payoutHoldTxn) {
        this.name = payoutHoldTxn.getCustomerName();
        this.nameInBank = payoutHoldTxn.getNameInBank();
        this.accountNumber = payoutHoldTxn.getBankAccNo();
        this.amount = payoutHoldTxn.getAmount();
        this.payoutId = payoutHoldTxn.getPayoutId();
        this.pendingReason = payoutHoldTxn.getRemarks();
        this.invoiceId = payoutHoldTxn.getInvoiceIdProof();
        this.merchantName = payoutHoldTxn.getMerchantId();
        this.status = payoutHoldTxn.getStatus();
    }

    public PayoutAccountEnquiryDto() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
