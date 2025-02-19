package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.payment.controller.bean.TransactionResult;
import com.mobiversa.payment.util.PropertyLoad;

@Repository
public class DuitnowTxnsDaoImpl extends BaseDAOImpl implements DuitnowTxnsDao {

    private static final Logger logger = Logger.getLogger(DuitnowTxnsDaoImpl.class);

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @SuppressWarnings("nls")
    public TransactionResult fetchDuitNowTxnsDetails(String fromDate, String toDate, int currPage, String id) {
        List<Object[]> resultList = new ArrayList<>();
        int totalRecords = 0;
        TransactionResult transactionResult = new TransactionResult();
        StringBuilder sqlQuery = new StringBuilder();

        // Base query for fetching data
        sqlQuery.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, m.BUSINESS_NAME, ")
                .append("q.INVOICE_ID, q.TRANSACTION_ID, q.STATUS, q.AMOUNT, ")
                .append("q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE, q.EZYSETTLE_AMOUNT,q.RESPONSE_MESSAGE,q.HOST_TRANSACTION_ID, q.PAID_TIMESTAMP ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ")
                .append("WHERE q.MERCHANT_FK = :id ");

        StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ")
                .append("WHERE q.MERCHANT_FK = :id ");

        if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
            sqlQuery.append(" AND q.CREATED_DATE BETWEEN :fromDate AND :toDate ");
            countQuery.append(" AND q.CREATED_DATE BETWEEN :fromDate AND :toDate ");
        }

        sqlQuery.append(" ORDER BY q.CREATED_DATE DESC "); // Ensure proper space

        try {

            Query dataQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
            Query totalCountQuery = sessionFactory.getCurrentSession().createSQLQuery(countQuery.toString());

            if (id != null) {
                logger.info("Setting parameter id: " + id);
                dataQuery.setParameter("id", id);
                totalCountQuery.setParameter("id", id);
            } else {
                logger.error("Merchant ID is null");
            }

            if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
                dataQuery.setParameter("fromDate", fromDate);
                totalCountQuery.setParameter("fromDate", fromDate);
                dataQuery.setParameter("toDate", toDate);
                totalCountQuery.setParameter("toDate", toDate);
            }

            // Execute count query to get the total number of records
            Number totalRecordsCount = (Number) totalCountQuery.uniqueResult();
            totalRecords = (totalRecordsCount != null) ? totalRecordsCount.intValue() : 0;

            logger.info("Total Records: " + totalRecords);

            // Pagination logic
            String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
            int pageSize = Integer.parseInt(dynamicPage); // Ensure this value is set properly
            int pageNumFromJsp = Math.max(currPage, 1); // Ensure pageNumFromJsp is at least 1
            logger.info("Page Number: " + pageNumFromJsp);
            logger.info("Max Count for Records: " + pageSize);

            // Set pagination
            dataQuery.setFirstResult((pageNumFromJsp - 1) * pageSize);
            dataQuery.setMaxResults(pageSize);

            logger.info("Final SQL Query: " + sqlQuery.toString());
            logger.info("Pagination Offset: " + ((pageNumFromJsp - 1) * pageSize) + ", Limit: " + pageSize);

            // Fetch results
            resultList = dataQuery.list();
            logger.info("Result Set Size: " + resultList.size());
            transactionResult.setTransactions(resultList);
            transactionResult.setTotalRecords(totalRecords);

            return transactionResult;

        } catch (Exception e) {
            logger.error("Exception occurred while fetching DuitNow transactions: " + e.getMessage(), e);
            return new TransactionResult();
        }
    }


//    public List<Object[]> fetchDuitNowTxnsDetails(String fromDate, String toDate, int currPage) {
//        List<Object[]> resultList = new ArrayList<>();
//        StringBuilder sqlQuery = new StringBuilder();
//
//        sqlQuery.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, m.BUSINESS_NAME, q.INVOICE_ID, ")
//                .append("q.TRANSACTION_ID, q.STATUS, q.AMOUNT, q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, ")
//                .append("q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE ")
//                .append("FROM mobiversa.QRTRANSACTIONS q ")
//                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ");
//
//        if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
//            sqlQuery.append("WHERE q.CREATED_DATE BETWEEN :fromDate AND :toDate ");
//        }
//
//        sqlQuery.append("ORDER BY q.CREATED_DATE DESC");
//
//        try {
//            logger.info("SQL Query: " + sqlQuery);
//            Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
//
//            if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
//                query.setParameter("fromDate", fromDate);
//                query.setParameter("toDate", toDate);
//            }
//
//            String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//            int pageSize = 10;
//            int pageNumFromJsp = currPage;
//            logger.info("Page Number: " + pageNumFromJsp);
//            logger.info("Max Count for Records: " + pageSize);
//
//            query.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//            query.setMaxResults(pageSize);
//
//            resultList = query.list();
//            logger.info("Result Set Size: " + resultList.size());
//
//        } catch (Exception e) {
//            logger.error("Exception occurred while fetching DuitNow transactions: " + e.getMessage(), e);
//        }
//        return resultList;
//    }
    
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @SuppressWarnings("nls")
    public TransactionResult fetchSearchedData(String searchId, String searchType, String id) {
        List<Object[]> resultSet = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder();
        TransactionResult transactionResult = new TransactionResult();

        sqlQuery.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, m.BUSINESS_NAME, q.INVOICE_ID, ")
                .append("q.TRANSACTION_ID, q.STATUS, q.AMOUNT, q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, ")
                .append("q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE, q.EZYSETTLE_AMOUNT,q.RESPONSE_MESSAGE,q.HOST_TRANSACTION_ID, q.PAID_TIMESTAMP  ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ")
                .append("WHERE q.MERCHANT_FK = :id ");

        if (searchId != null && !searchId.trim().isEmpty()) {
            if (searchType.equals("INVOICE_ID")) {
                sqlQuery.append("AND q.INVOICE_ID = :searchId ");
            } else if (searchType.equals("TRANSACTION_ID")) {
                sqlQuery.append("AND q.TRANSACTION_ID = :searchId ");
            }
        }
        try {
            logger.info("SQL Query: " + sqlQuery);
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());

            query.setParameter("id", id);

            if (searchId != null && !searchId.trim().isEmpty()) {
                query.setParameter("searchId", searchId);
                query.setParameter("id", id);
            }
            
            resultSet = query.list();
            logger.info("Result Set Size: " + resultSet.size());
            transactionResult.setTransactions(resultSet);
            transactionResult.setTotalRecords(resultSet.size());
            return transactionResult;

        } catch (Exception e) {
            logger.error("Exception occurred while fetching DuitNow transactions: " + e.getMessage(), e);
            return new TransactionResult();
        }

    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @SuppressWarnings("nls")
    public TransactionResult exportDuitnowTransactionsData(String fromDate, String toDate, String id) {
        List<Object[]> resultList = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder();
        TransactionResult transactionResult = new TransactionResult();

        sqlQuery.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, m.BUSINESS_NAME, q.INVOICE_ID, ")
                .append("q.TRANSACTION_ID, q.STATUS, q.AMOUNT, q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, ")
                .append("q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE , q.EZYSETTLE_AMOUNT,q.RESPONSE_MESSAGE,q.HOST_TRANSACTION_ID, q.PAID_TIMESTAMP ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ")
                .append("WHERE q.MERCHANT_FK = :id ");
        

        if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
            sqlQuery.append("AND q.CREATED_DATE BETWEEN :fromDate AND :toDate ");
        }

        sqlQuery.append("ORDER BY q.CREATED_DATE DESC");

        try {
            logger.info("SQL Query: " + sqlQuery);
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
            query.setParameter("id", id);

            if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }
            resultList = query.list();
            logger.info("Result Set Size: " + resultList.size());
            transactionResult.setTransactions(resultList);
            return transactionResult;

        } catch (Exception e) {
            logger.error("Exception occurred while fetching DuitNow transactions for export: " + e.getMessage(), e);
            return new TransactionResult();
        }

    }



//    for admin and payout

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @SuppressWarnings("nls")
    public TransactionResult fetchAllDuitNowTxnsDetails(String fromDate, String toDate, int currPage) {
        List<Object[]> resultList = new ArrayList<>();
        int totalRecords = 0;
        TransactionResult transactionResult = new TransactionResult();
        StringBuilder sqlQuery = new StringBuilder();
      
        sqlQuery.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, m.BUSINESS_NAME, ")
                .append("q.INVOICE_ID, q.TRANSACTION_ID, q.STATUS, q.AMOUNT, ")
                .append("q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE, q.EZYSETTLE_AMOUNT,q.RESPONSE_MESSAGE,q.HOST_TRANSACTION_ID, q.PAID_TIMESTAMP ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ");

        StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ");

        if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
            sqlQuery.append("WHERE q.CREATED_DATE BETWEEN :fromDate AND :toDate ");
            countQuery.append("WHERE q.CREATED_DATE BETWEEN :fromDate AND :toDate ");
        }

        sqlQuery.append(" ORDER BY q.CREATED_DATE DESC "); // Ensure proper space

        try {

            Query dataQuery = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());
            Query totalCountQuery = sessionFactory.getCurrentSession().createSQLQuery(countQuery.toString());

            if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
                dataQuery.setParameter("fromDate", fromDate);
                totalCountQuery.setParameter("fromDate", fromDate);
                dataQuery.setParameter("toDate", toDate);
                totalCountQuery.setParameter("toDate", toDate);
            }

            Number totalRecordsCount = (Number) totalCountQuery.uniqueResult();
            totalRecords = (totalRecordsCount != null) ? totalRecordsCount.intValue() : 0;

            logger.info("Total Records: " + totalRecords);

            String dynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
            int pageSize = Integer.parseInt(dynamicPage); // Ensure this value is set properly
            int pageNumFromJsp = Math.max(currPage, 1); // Ensure pageNumFromJsp is at least 1
            logger.info("Page Number: " + pageNumFromJsp);
            logger.info("Max Count for Records: " + pageSize);

            // Set pagination
            dataQuery.setFirstResult((pageNumFromJsp - 1) * pageSize);
            dataQuery.setMaxResults(pageSize);

            logger.info("Final SQL Query: " + sqlQuery.toString());
            logger.info("Pagination Offset: " + ((pageNumFromJsp - 1) * pageSize) + ", Limit: " + pageSize);

            // Fetch results
            resultList = dataQuery.list();
            logger.info("Result Set Size: " + resultList.size());
            transactionResult.setTransactions(resultList);
            transactionResult.setTotalRecords(totalRecords);

            return transactionResult;

        } catch (Exception e) {
            logger.error("Exception occurred while fetching DuitNow transactions: " + e.getMessage(), e);
            return new TransactionResult();
        }
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @SuppressWarnings("nls")
    public TransactionResult searchDuitnowTxnData(String searchId, String searchType) {
        List<Object[]> resultSet = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder();
        TransactionResult transactionResult = new TransactionResult();

        sqlQuery.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, m.BUSINESS_NAME, q.INVOICE_ID, ")
                .append("q.TRANSACTION_ID, q.STATUS, q.AMOUNT, q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, ")
                .append("q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE , q.EZYSETTLE_AMOUNT,q.RESPONSE_MESSAGE,q.HOST_TRANSACTION_ID, q.PAID_TIMESTAMP ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ");

        if (searchId != null && !searchId.trim().isEmpty()) {
            if (searchType.equals("INVOICE_ID")) {
                sqlQuery.append("WHERE q.INVOICE_ID = :searchId ");
            } else if (searchType.equals("TRANSACTION_ID")) {
                sqlQuery.append("WHERE q.TRANSACTION_ID = :searchId ");
            }
        }
        try {
            logger.info("SQL Query: " + sqlQuery);
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());

            if (searchId != null && !searchId.trim().isEmpty()) {
                query.setParameter("searchId", searchId);
            }
            resultSet = query.list();
            logger.info("Result Set Size: " + resultSet.size());
            transactionResult.setTransactions(resultSet);
            transactionResult.setTotalRecords(resultSet.size());
            return transactionResult;

        } catch (Exception e) {
            logger.error("Exception occurred while fetching DuitNow transactions: " + e.getMessage(), e);
            return new TransactionResult();
        }

    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @SuppressWarnings("nls")
    public TransactionResult exportAllDuitnowTxnData(String fromDate, String toDate) {
        List<Object[]> resultList = new ArrayList<>();
        StringBuilder sqlQuery = new StringBuilder();
        TransactionResult transactionResult = new TransactionResult();

        sqlQuery.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, m.BUSINESS_NAME, q.INVOICE_ID, ")
                .append("q.TRANSACTION_ID, q.STATUS, q.AMOUNT, q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, ")
                .append("q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE , q.EZYSETTLE_AMOUNT,q.RESPONSE_MESSAGE,q.HOST_TRANSACTION_ID, q.PAID_TIMESTAMP ")
                .append("FROM mobiversa.QRTRANSACTIONS q ")
                .append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ");

        if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
            sqlQuery.append("WHERE q.CREATED_DATE BETWEEN :fromDate AND :toDate ");
        }

        sqlQuery.append("ORDER BY q.CREATED_DATE DESC");

        try {
            logger.info("SQL Query: " + sqlQuery);
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());

            if (fromDate != null && !fromDate.trim().isEmpty() && toDate != null && !toDate.trim().isEmpty()) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            }
            resultList = query.list();
            logger.info("Result Set Size: " + resultList.size());
            transactionResult.setTransactions(resultList);
            return transactionResult;

        } catch (Exception e) {
            logger.error("Exception occurred while fetching DuitNow transactions for export: " + e.getMessage(), e);
            return new TransactionResult();
        }

    }


	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	@SuppressWarnings("nls")
	public boolean UpdateUserData(String invoiceId, String username,String updatedDate) {
		boolean updateSuccess = false;
		StringBuilder sqlQuery = new StringBuilder();
		
		 try {
		      
		        sqlQuery.append("UPDATE mobiversa.QRTRANSACTIONS q ")
		                .append("SET q.MODIFIED_BY = :username, ")
		                .append("q.MODIFIED_DATE = :updatedDate ")
		                .append("WHERE q.INVOICE_ID = :invoiceId");

		        logger.info("SQL Query: " + sqlQuery);

		        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery.toString());

		        query.setParameter("username", username);
		        query.setParameter("updatedDate", updatedDate);
		        query.setParameter("invoiceId", invoiceId);
		        

		        int rowsAffected = query.executeUpdate();
		        if (rowsAffected > 0) {
		            updateSuccess = true;  
		        }

		        logger.info("Rows affected: " + rowsAffected);
		    } catch (Exception e) {
		        logger.error("Exception occurred while updating user data: " + e.getMessage(), e);
		    }
		 
		return updateSuccess;
	}






}
