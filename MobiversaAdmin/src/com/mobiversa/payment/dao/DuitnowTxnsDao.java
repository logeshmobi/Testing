package com.mobiversa.payment.dao;

import com.mobiversa.payment.controller.bean.TransactionResult;

public interface DuitnowTxnsDao extends BaseDAO{
    public TransactionResult fetchDuitNowTxnsDetails(String fromDate, String toDate, int currPage,String id);
    public TransactionResult fetchSearchedData(String searchId, String searchType, String id);
    public TransactionResult exportDuitnowTransactionsData(String fromDate, String toDate, String id);
    
    public boolean UpdateUserData(String invoiceId, String username,String updatedDate);


//    for admin and payout

    public TransactionResult fetchAllDuitNowTxnsDetails(String fromDate, String toDate, int currPage);
    public TransactionResult searchDuitnowTxnData(String searchId, String searchType);
    public TransactionResult exportAllDuitnowTxnData(String fromDate, String toDate);
}
