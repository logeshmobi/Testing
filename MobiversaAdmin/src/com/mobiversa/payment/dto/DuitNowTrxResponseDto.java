package com.mobiversa.payment.dto;

import java.util.List;

public class DuitNowTrxResponseDto {

    private List<DuitnowTxnDto> transactions;
    private int totalRecords;

    public DuitNowTrxResponseDto() {}

    public DuitNowTrxResponseDto(List<DuitnowTxnDto> transactions, int totalRecords) {
        this.transactions = transactions;
        this.totalRecords = totalRecords;
    }

    public List<DuitnowTxnDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<DuitnowTxnDto> transactions) {
        this.transactions = transactions;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    @Override
    public String toString() {
        return "DuitNowTrxResponseDto{" +
                "transactions=" + transactions +
                ", totalRecords=" + totalRecords +
                '}';
    }
}
