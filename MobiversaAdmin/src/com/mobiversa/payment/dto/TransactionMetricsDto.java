package com.mobiversa.payment.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for Transaction Metrics.
 */
public class TransactionMetricsDto {

    private String bankCode;
    private String bankName;

    private Integer merchantId;
    private Integer totalTxnCount;
    private Integer totalSuccessTxnCount;
    private Integer totalFailureTxnCount;
    private Integer totalOnProcessCount;
    private Double successRate;
    private Double failureRate;

    private String merchantName;

    private Double onProcessRate;

    private Integer totalDeclinedCount;

    private Double declinedRate;

    private String avgProcessingTimeForPp;


    private List<FailureMetrics> failureMetricsList = new ArrayList<>();

    // Getters and Setters


    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getTotalTxnCount() {
        return totalTxnCount;
    }

    public void setTotalTxnCount(Integer totalTxnCount) {
        this.totalTxnCount = totalTxnCount;
    }

    public Integer getTotalSuccessTxnCount() {
        return totalSuccessTxnCount;
    }

    public void setTotalSuccessTxnCount(Integer totalSuccessTxnCount) {
        this.totalSuccessTxnCount = totalSuccessTxnCount;
    }

    public Integer getTotalFailureTxnCount() {
        return totalFailureTxnCount;
    }

    public void setTotalFailureTxnCount(Integer totalFailureTxnCount) {
        this.totalFailureTxnCount = totalFailureTxnCount;
    }


    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(Double failureRate) {
        this.failureRate = failureRate;
    }

    public List<FailureMetrics> getFailureMetricsList() {
        return failureMetricsList;
    }

    public void setFailureMetricsList(List<FailureMetrics> failureMetricsList) {
        this.failureMetricsList = failureMetricsList;
    }

    public Integer getTotalOnProcessCount() {
        return totalOnProcessCount;
    }

    public void setTotalOnProcessCount(Integer totalOnProcessCount) {
        this.totalOnProcessCount = totalOnProcessCount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Double getOnProcessRate() {
        return onProcessRate;
    }

    public void setOnProcessRate(Double onProcessRate) {
        this.onProcessRate = onProcessRate;
    }

    public Integer getTotalDeclinedCount() {
        return totalDeclinedCount;
    }

    public void setTotalDeclinedCount(Integer totalDeclinedCount) {
        this.totalDeclinedCount = totalDeclinedCount;
    }

    public Double getDeclinedRate() {
        return declinedRate;
    }

    public void setDeclinedRate(Double declinedRate) {
        this.declinedRate = declinedRate;
    }

    public String getAvgProcessingTimeForPp() {
        return avgProcessingTimeForPp;
    }

    public void setAvgProcessingTimeForPp(String avgProcessingTimeForPp) {
        this.avgProcessingTimeForPp = avgProcessingTimeForPp;
    }

    @Override
    public String toString() {
        return "TransactionMetricsDto{" +
                "bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", merchantId=" + merchantId +
                ", totalTxnCount=" + totalTxnCount +
                ", totalSuccessTxnCount=" + totalSuccessTxnCount +
                ", totalFailureTxnCount=" + totalFailureTxnCount +
                ", totalOnProcessCount=" + totalOnProcessCount +
                ", successRate=" + successRate +
                ", failureRate=" + failureRate +
                ", merchantName='" + merchantName + '\'' +
                ", onProcessRate=" + onProcessRate +
                ", totalDeclinedCount=" + totalDeclinedCount +
                ", declinedRate=" + declinedRate +
                ", avgProcessingTimeForPp='" + avgProcessingTimeForPp + '\'' +
                ", failureMetricsList=" + failureMetricsList +
                '}';
    }

    /**
     * Inner class representing Failure Metrics.
     */
    public static class FailureMetrics {
        private String reasonText;
        private String reasonCode;
        private Integer reasonCount;
        private Double reasonRate;

        // Getters and Setters

        public String getReasonCode() {
            return reasonCode;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }

        public Integer getReasonCount() {
            return reasonCount;
        }

        public void setReasonCount(Integer reasonCount) {
            this.reasonCount = reasonCount;
        }

        public String getReasonText() {
            return reasonText;
        }

        public void setReasonText(String reasonText) {
            this.reasonText = reasonText;
        }

        public Double getReasonRate() {
            return reasonRate;
        }

        public void setReasonRate(Double reasonRate) {
            this.reasonRate = reasonRate;
        }

        @Override
        public String toString() {
            return "FailureMetrics{" +
                    "reasonText='" + reasonText + '\'' +
                    ", reasonCode='" + reasonCode + '\'' +
                    ", reasonCount=" + reasonCount +
                    ", reasonRate=" + reasonRate +
                    '}';
        }
    }
}
