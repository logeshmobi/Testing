package com.mobiversa.payment.controller.bean;

import java.util.List;

public class TransactionResult {
    private List<Object[]> transactions;
    private int totalRecords;

    public TransactionResult(){

    }

    public TransactionResult(List<Object[]> resultList, int totalRecords) {
    }

    public List<Object[]> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Object[]> transactions) {
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
        return "TransactionResult{" +
                "transactions=" + transactions +
                ", totalRecords=" + totalRecords +
                '}';
    }
}
