package com.mobiversa.payment.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PayoutHoldTxn;
import com.mobiversa.payment.dto.PayoutAccountEnquiryDto;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.Status;

@Component
@Repository
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AccountEnquiryDaoImplementation extends BaseDAOImpl implements  AccountEnquiryDao{
    public Long getMerchantIdFromUserName(String userName) {
        Long merchantId = null;
        logger.info("Merchant is : "+userName);
        String query = "SELECT m.ID FROM mobiversa.MERCHANT m WHERE m.USERNAME = :userName";

        Session session = super.getSessionFactory();
        logger.info("Is session present "+session);
        try {
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("userName", userName);
            sqlQuery.addScalar("ID", org.hibernate.type.LongType.INSTANCE);
            sqlQuery.uniqueResult();

            List<?> resultList = sqlQuery.list();
            if (!resultList.isEmpty()) {
                merchantId = (Long) resultList.get(0);
            }
            logger.info("Merchant id : "+merchantId);
        } catch (Exception e) {
            logger.info("Error fetching merchant ID for username: " + userName + ". Reason: " + e.getMessage());
            e.printStackTrace();
        }
        return merchantId;
    }

    public String getMerchantEmailFromUserName(String userName) {
        String  merchantEmail = null;
        logger.info("Merchant is : "+userName);
        String query = "SELECT m.EMAIL FROM mobiversa.MERCHANT m WHERE m.USERNAME = :userName";

        Session session = super.getSessionFactory();
        logger.info("Is session present "+session);
        try {
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("userName", userName);
            sqlQuery.uniqueResult();

            List<?> resultList = sqlQuery.list();
            if (!resultList.isEmpty()) {
                merchantEmail = (String) resultList.get(0);
            }
            logger.info("Merchant Email is  : "+merchantEmail);
        } catch (Exception e) {
            logger.info("Error fetching merchant ID for username: " + userName + ". Reason: " + e.getMessage());
            e.printStackTrace();
        }
        return merchantEmail;
    }


    @SuppressWarnings("nls")
	public List<PayoutAccountEnquiryDto> listApprovalTransactions(long merchantId,int firstTransaction,int transactionsPerPage,String invoiceId) throws MobiException {
        List<PayoutAccountEnquiryDto> enquiryList = new ArrayList<>();

        String query = "SELECT pht.CREATED_DATE, "
                + "pht.CUSTOMER_NAME , "
                + "pht.BANK_ACC_NO , "
                + "pht.AMOUNT , "
                + "pht.PAYOUT_ID, "
                + "pht.INVOICE_ID_PROOF, "
                + "pht.NAME_IN_BANK "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pht "
                + "WHERE pht.MERCHANT_ID = :merchantId "
                + "AND  pht.STATUS = :status "
                + "ORDER BY pht.CREATED_DATE DESC";

        try {
            SQLQuery sqlQuery = super.getSessionFactory().createSQLQuery(query);
            sqlQuery.setParameter("merchantId", merchantId);
            sqlQuery.setParameter("status", "On Hold");
            sqlQuery.setFirstResult(firstTransaction);
            sqlQuery.setMaxResults(transactionsPerPage);

            @SuppressWarnings("unchecked")
			List<Object[]> results = sqlQuery.list();
            logger.info(results != null ? "Total approval transactions : " + results.size() : "emptylist");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            if (results != null) {
            	
                for (Object[] row : results) {
                    if (row[6] != null && invoiceId != null && invoiceId.equalsIgnoreCase((String) row[6])) {
                        logger.info("Already approve Transaction found in the Query " + row[6]);
                        continue;
                    }
                    PayoutAccountEnquiryDto dto = new PayoutAccountEnquiryDto();
                    Timestamp createdDate = (Timestamp) row[0];
                    String date = dateFormat.format(createdDate);
                    String time = timeFormat.format(createdDate);

                    dto.setDate(date);
                    dto.setTime(time);
                    dto.setName((String) row[1]);
                    dto.setAccountNumber((String) row[2]);
                    dto.setAmount((String) row[3]);
                    dto.setPayoutId((String) row[4]);
//                    dto.setPendingReason((String) row[]);
                    dto.setInvoiceId((String) row[5]);
                    dto.setNameInBank((String) row[6]);

                    enquiryList.add(dto);
                }
            }

        } catch(Exception e){
                logger.info("Error fetching approval transactions for merchant id : " + merchantId + "& the  Reason is  " + e.getMessage());
                e.printStackTrace();
                throw new MobiException(Status.SQL_EXCEPTION);

            }
            logger.info("Exceuted Successfully " + enquiryList);
            return enquiryList;
        }


    @Override
    public PayoutHoldTxn findPayoutByInvoiceId(String invoiceId) throws MobiException {

        String query = "SELECT pd.* "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pd "
                + "WHERE pd.INVOICE_ID_PROOF =  :invoiceId ";

        PayoutHoldTxn payoutHoldTxn = null;

        try {
            Session session = super.getSessionFactory();
            Query sqlQuery = session.createSQLQuery(query)
                    .addEntity(PayoutHoldTxn.class)
                    .setParameter("invoiceId", invoiceId);

            Object result = sqlQuery.uniqueResult();
            if (result != null) {
                payoutHoldTxn = (PayoutHoldTxn) result;
            }
        } catch (Exception e) {
            logger.error("Error while fetching the details for the invoice id : "+invoiceId);
            e.printStackTrace();
            throw new MobiException(Status.SQL_EXCEPTION);
        }

        return payoutHoldTxn;
    }
    @Override
    public String getMobiApiKeyFromMerchantId(String id) throws MobiException {

        String query = "SELECT mu.MOTO_API_KEY "
                + "FROM mobiversa.MOBILE_USER mu "
                + "WHERE mu.MERCHANT_FK =  :merchantId ";

        String mobiApiKey = null;

        try {
            Session session = super.getSessionFactory();
            Query sqlQuery = session.createSQLQuery(query)
                    .setParameter("merchantId", id);

            Object result = sqlQuery.uniqueResult();
            if (result != null) {
                mobiApiKey =  result.toString();
            }
        } catch (Exception e) {
            logger.error("Error while fetching the mobiapikey for the merchant Id  : "+id);
            throw new MobiException(Status.SQL_EXCEPTION);
        }

        return mobiApiKey;
    }

    @Override
    public void updateOnHold(String invoiceId,String reason) throws MobiException {
        try {
            String sql = "UPDATE mobiversa.PAYOUT_HOLD_TXN pd " +
                    "SET pd.STATUS = :payoutStatus " +
                    "WHERE pd.INVOICE_ID_PROOF = :invoiceId";
            Session session = super.getSessionFactory();

            Query query = session.createSQLQuery(sql);
            query.setParameter("payoutStatus", reason);
            query.setParameter("invoiceId", invoiceId);

            query.executeUpdate();
            logger.info("Payout rejected by merchant for the invoice id  : "+ invoiceId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new MobiException(Status.SQL_EXCEPTION);
        }
    }


    public int sizeOfApprovalTransactions(long merchantId,String invoiceId) throws MobiException{
        int resultCount = 0;

        String query = "SELECT COUNT(*) "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pd "
                + "WHERE pd.MERCHANT_ID = :merchantId "
                + "AND pd.STATUS = :status ";

        if(invoiceId != null)
            query+= "AND pd.INVOICE_ID_PROOF != :invoiceId ";
        logger.info("Sql query "+query);

        try {
            Session session = super.getSessionFactory();
            Query sqlQuery = session.createSQLQuery(query);
            sqlQuery.setParameter("merchantId", merchantId);
            sqlQuery.setParameter("status", "On Hold");
            if(invoiceId != null)
                sqlQuery.setParameter("invoiceId", invoiceId);

            Object result = sqlQuery.uniqueResult();
            if (result != null) {
                resultCount = ((Number) result).intValue();
            }
        } catch (Exception e) {
            logger.error("Error executing sizeOfApprovalTransactions query ", e);
            throw new MobiException(Status.SQL_EXCEPTION);
        }

        return resultCount;
    }

    @Override
    public int sizeOfApprovalTransactionsForPayoutUser() throws MobiException {
        int resultCount = 0;

        String query = "SELECT COUNT(*) "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pd "
                + "WHERE pd.STATUS = 'On Hold' ";

        logger.info("Sql query "+query);

        try {
            Session session = super.getSessionFactory();
            Query sqlQuery = session.createSQLQuery(query);

            Object result = sqlQuery.uniqueResult();
            if (result != null) {
                resultCount = ((Number) result).intValue();
            }
            logger.info("Total number of transactions present : "+resultCount);
        } catch (Exception e) {
            logger.error("Error executing sizeOfApprovalTransactions query ", e);
            throw new MobiException(Status.SQL_EXCEPTION);
        }

        return resultCount;
    }
    public List<PayoutAccountEnquiryDto> listApprovalTransactionsForPayoutUser(int firstTransaction, int transactionsPerPage) throws MobiException {
        List<PayoutAccountEnquiryDto> enquiryList = new ArrayList<>();

        String query = "SELECT pht.CREATED_DATE, "
                + "pht.CUSTOMER_NAME, "
                + "pht.BANK_ACC_NO, "
                + "pht.AMOUNT, "
                + "pht.PAYOUT_ID, "
                + "pht.INVOICE_ID_PROOF, "
                + "m.BUSINESS_NAME, "
                + "pht.NAME_IN_BANK "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pht "
                + "INNER JOIN mobiversa.MERCHANT m ON pht.MERCHANT_ID = m.ID "
                + "WHERE pht.STATUS = 'On Hold' "
                + "ORDER BY pht.CREATED_DATE DESC";

        try {
            SQLQuery sqlQuery = super.getSessionFactory().createSQLQuery(query);
            sqlQuery.setFirstResult(firstTransaction);
            sqlQuery.setMaxResults(transactionsPerPage);

            List<Object[]> results = sqlQuery.list();
            logger.info(results != null ? "Total approval transactions: " + results.size() : "empty list");

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            if (results != null) {
                for (Object[] row : results) {
                    PayoutAccountEnquiryDto dto = new PayoutAccountEnquiryDto();
                    Timestamp createdDate = (Timestamp) row[0];
                    String date = dateFormat.format(createdDate);
                    String time = timeFormat.format(createdDate);

                    dto.setDate(date);
                    dto.setTime(time);
                    dto.setName((String) row[1]);
                    dto.setAccountNumber((String) row[2]);
                    dto.setAmount((String) row[3]);
                    dto.setPayoutId((String) row[4]);
                    dto.setInvoiceId((String) row[5]);
                    dto.setMerchantName((String) row[6]);
                    dto.setNameInBank((String) row[7]);
                    logger.info("merchant name -->" + (String) row[6]);

                    enquiryList.add(dto);
                }
            }

        } catch (Exception e) {
            logger.info("Error fetching approval transactions. Reason: " + e.getMessage());
            e.printStackTrace();
            throw new MobiException(Status.SQL_EXCEPTION);
        }

        logger.info("Executed Successfully " + enquiryList);
        return enquiryList;
    }


    @Override
    public String getEnableAccountEnquiry(final long merchantId) {
        try {
            String enableAccountEnquiry = null;
            String sql = "SELECT mu.ENABLE_ACCOUNT_ENQUIRY FROM mobiversa.MOBILE_USER mu WHERE mu.MERCHANT_FK = "+merchantId;
            Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
            @SuppressWarnings("unchecked")
            List<String> resultSet = sqlQuery.list();
            for (String rec : resultSet) {
                if (rec != null) {
                    enableAccountEnquiry = rec;
                } else {
                    enableAccountEnquiry = "No";
                }
            }
            return enableAccountEnquiry;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<PayoutAccountEnquiryDto> findById(String type , String id,Long merchantId) throws MobiException {
        String query = "SELECT pht.CREATED_DATE, "
                + "pht.CUSTOMER_NAME , "
                + "pht.BANK_ACC_NO , "
                + "pht.AMOUNT , "
                + "pht.PAYOUT_ID, "
                + "pht.INVOICE_ID_PROOF, "
                + "pht.NAME_IN_BANK "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pht "
                + "WHERE pht.MERCHANT_ID = :merchantId "
                + "AND  pht." + type +" = :id "
                + "AND  pht.STATUS = 'On Hold'";
        List<PayoutAccountEnquiryDto> enquiryList = new ArrayList<>();

        try {
            SQLQuery sqlQuery = super.getSessionFactory().createSQLQuery(query);
            sqlQuery.setParameter("merchantId", merchantId);
            sqlQuery.setParameter("id", id);

            List<Object[]> results = sqlQuery.list();
            logger.info(results != null ? "Total approval transactions : " + results.size() : "emptylist");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            if (results != null) {
                for (Object[] row : results) {

                    PayoutAccountEnquiryDto dto = new PayoutAccountEnquiryDto();
                    Timestamp createdDate = (Timestamp) row[0];
                    String date = dateFormat.format(createdDate);
                    String time = timeFormat.format(createdDate);

                    dto.setDate(date);
                    dto.setTime(time);
                    dto.setName((String) row[1]);
                    dto.setAccountNumber((String) row[2]);
                    dto.setAmount((String) row[3]);
                    dto.setPayoutId((String) row[4]);
                    dto.setInvoiceId((String) row[5]);
                    dto.setNameInBank((String) row[6]);

                    enquiryList.add(dto);
                }
            }

        } catch(Exception e){
            logger.info("Error fetching approval transactions for merchant id : " + merchantId + "& the  Reason is  " + e.getMessage());
            e.printStackTrace();
            throw new MobiException(Status.SQL_EXCEPTION);

        }
        logger.info("Exceuted Successfully " + enquiryList);
        return enquiryList;
    }
    public List<PayoutAccountEnquiryDto> findById(String type , String id) throws MobiException {
        String query = "SELECT pht.CREATED_DATE, "
                + "pht.CUSTOMER_NAME , "
                + "pht.BANK_ACC_NO , "
                + "pht.AMOUNT , "
                + "pht.PAYOUT_ID, "
                + "pht.INVOICE_ID_PROOF, "
                + "pht.NAME_IN_BANK, "
                + "m.BUSINESS_NAME "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pht "
                + "INNER JOIN mobiversa.MERCHANT m ON pht.MERCHANT_ID = m.ID "
                + "WHERE "
                + "pht." + type +" = :id "
                + "AND  pht.STATUS = 'On Hold'";
        List<PayoutAccountEnquiryDto> enquiryList = new ArrayList<>();

        try {
            SQLQuery sqlQuery = super.getSessionFactory().createSQLQuery(query);
            sqlQuery.setParameter("id", id);

            List<Object[]> results = sqlQuery.list();
            logger.info(results != null ? "Total approval transactions : " + results.size() : "emptylist");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            if (results != null) {
                for (Object[] row : results) {

                    PayoutAccountEnquiryDto dto = new PayoutAccountEnquiryDto();
                    Timestamp createdDate = (Timestamp) row[0];
                    String date = dateFormat.format(createdDate);
                    String time = timeFormat.format(createdDate);

                    dto.setDate(date);
                    dto.setTime(time);
                    dto.setName((String) row[1]);
                    dto.setAccountNumber((String) row[2]);
                    dto.setAmount((String) row[3]);
                    dto.setPayoutId((String) row[4]);
                    dto.setInvoiceId((String) row[5]);
                    dto.setNameInBank((String) row[6]);
                    dto.setMerchantName((String) row[7]);

                    enquiryList.add(dto);
                }
            }
        } catch(Exception e){
            logger.info("Error fetching approval transactions id : " + id + "& the  Reason is  " + e.getMessage());
            e.printStackTrace();
            throw new MobiException(Status.SQL_EXCEPTION);
        }
        logger.info("Exceuted Successfully " + enquiryList);
        return enquiryList;
    }
    
    @Override
	public int fetchMaxPayoutLimitExceededTransactionCount(String status, String fromDate, String toDate) throws MobiException {

		final String query = "SELECT COUNT(*) FROM mobiversa.PAYOUT_HOLD_TXN pd WHERE pd.STATUS = :status AND pd.CREATED_DATE BETWEEN :fromDate AND :toDate";

		try {
			Number result = (Number) super.getSessionFactory().createSQLQuery(query)
					.setParameter("status", status)
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate)
					.uniqueResult();

			logger.info(
					"Total number of exceeded max limit transactions: " + ((result != null) ? result.intValue() : 0));

			return (result != null) ? result.intValue() : 0;
		} catch (Exception e) {
			logger.error("Error executing fetch Max-Limit exceeded transaction count query: {" + query + "} with Parameters: status = '{ExceededMaxLimit}'. Exception: " + e);
			return 0;
		}
	}
    
    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    @Override
	public List<PayoutAccountEnquiryDto> fetchMaxPayoutLimitExceededTransactions(String status, String fromDate, String toDate, int firstTransaction, int transactionsPerPage) {
        
    	List<PayoutAccountEnquiryDto> enquiryList = new ArrayList<>();

        final String query = "SELECT pht.CREATED_DATE, "
                + "pht.CUSTOMER_NAME , "
                + "pht.BANK_ACC_NO , "
                + "pht.AMOUNT , "
                + "pht.PAYOUT_ID, "
                + "pht.INVOICE_ID_PROOF, "
                + "pht.NAME_IN_BANK, "
                + "m.BUSINESS_NAME "
                + "FROM mobiversa.PAYOUT_HOLD_TXN pht "
                + "INNER JOIN mobiversa.MERCHANT m on m.ID = pht.MERCHANT_ID "
                + "WHERE pht.STATUS = :status "
                + "AND pht.CREATED_DATE BETWEEN :fromDate AND :toDate "
				+ "ORDER BY pht.CREATED_DATE DESC";
        
		try {
			List<Object[]> results = super.getSessionFactory().createSQLQuery(query)
					.setParameter("status", status)
					.setParameter("fromDate", fromDate)
					.setParameter("toDate", toDate)
					.setFirstResult(firstTransaction)
					.setMaxResults(transactionsPerPage).list();

			logger.info("Executing fetch Max-Limit exceeded transactions query: {" + query + "} with Parameters: status = '{" + status + "}', fromDate = '" + fromDate + "', toDate = '" + toDate + "'. Number of records found: " + results.size());

			if (results != null && !results.isEmpty()) {
				for (Object[] row : results) {
					PayoutAccountEnquiryDto dto = new PayoutAccountEnquiryDto();

					dto.setDate(dateFormat.format((Timestamp) row[0]));
					dto.setTime(timeFormat.format((Timestamp) row[0]));
					dto.setName((String) row[1]);
					dto.setAccountNumber((String) row[2]);
					dto.setAmount((String) row[3]);
					dto.setPayoutId((String) row[4]);
					dto.setInvoiceId((String) row[5]);
					dto.setNameInBank((String) row[6]);
					dto.setMerchantName((String) row[7]);

					enquiryList.add(dto);
				}
			} else {
				logger.warn("Max payout exceeded transactions: " + (results == null ? "NULL" : "Empty list, size: " + results.size()));
			}
		} catch (Exception e) {
			logger.error("Exception fetching Max-Limit exceeded transactions query: {" + query + "} with Parameters: status = '{" + status + "}'. Exception: " + e.getMessage(), e);
		}
		return enquiryList;
	}

    @Override
	public List<PayoutAccountEnquiryDto> searchMaxPayoutLimitExceededTransactions(String status, String fromDate,
			String toDate, List<String> searchValues, String searchType) {

		List<PayoutAccountEnquiryDto> enquiryList = new ArrayList<>();
		
	    String searchValuesString = searchValues.stream()
	            .map(value -> "'" + value.trim().replace("'", "''") + "'")  // Escape single quotes in search values
	            .collect(Collectors.joining(", "));

		StringBuilder queryBuilder = new StringBuilder("SELECT pht.CREATED_DATE, " 
					+ "pht.CUSTOMER_NAME, "
		            + "pht.BANK_ACC_NO, "
		            + "pht.AMOUNT, "
		            + "pht.PAYOUT_ID, "
		            + "pht.INVOICE_ID_PROOF, "
		            + "pht.NAME_IN_BANK, "
		            + "m.BUSINESS_NAME "
		            + "FROM mobiversa.PAYOUT_HOLD_TXN pht "
		            + "INNER JOIN mobiversa.MERCHANT m ON m.ID = pht.MERCHANT_ID "
		            + "WHERE pht.STATUS = '" + status + "'" 
		            + "AND pht.CREATED_DATE BETWEEN '" + fromDate + "' AND '" + toDate + "' ");

		    if (searchType.equals("Payout ID")) {
		        queryBuilder.append("AND pht.PAYOUT_ID IN (" + searchValuesString + ") ");
		    } else if (searchType.equals("Transaction ID")) {
		        queryBuilder.append("AND pht.INVOICE_ID_PROOF IN (" + searchValuesString + ") ");
		    }

		    queryBuilder.append("ORDER BY pht.CREATED_DATE DESC");
	    
		try {
			List<Object[]> results = super.getSessionFactory().createSQLQuery(queryBuilder.toString()).list();

			if (results != null && !results.isEmpty()) {
				for (Object[] row : results) {
					PayoutAccountEnquiryDto dto = new PayoutAccountEnquiryDto();

					dto.setDate(dateFormat.format((Timestamp) row[0]));
					dto.setTime(timeFormat.format((Timestamp) row[0]));
					dto.setName((String) row[1]);
					dto.setAccountNumber((String) row[2]);
					dto.setAmount((String) row[3]);
					dto.setPayoutId((String) row[4]);
					dto.setInvoiceId((String) row[5]);
					dto.setNameInBank((String) row[6]);
					dto.setMerchantName((String) row[7]);

					enquiryList.add(dto);
				}
			}
		} catch (Exception e) {
			logger.error("Exception occurred while fetching max payout limit exceeded transactions." + " Query: {"
					+ queryBuilder.toString() + "}, " + " Parameters: {status: " + status + ", fromDate: " + fromDate + ", toDate: "
					+ toDate + ", searchValues: " + searchValues + "}, " + "Exception: " + e.getMessage(), e);
		}
		return enquiryList;
	}
    
	@Override
    public PayoutHoldTxn getPayoutHoldTxnByInvoiceIDProof(String invoiceID) {
		try {
			return (PayoutHoldTxn) sessionFactory.getCurrentSession().createCriteria(PayoutHoldTxn.class)
					.add(Restrictions.eq("invoiceIdProof", invoiceID)).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
			logger.error("Exception in loading Payout Hold Txn by InvoiceID: " + e.getMessage(), e);
			return null;
		}
    }

	@Override
	public int updatePayoutHoldTxnStatusByInvoiceID(String invoiceId, String status,String actionTakenBy) {
		try {
			String sql = "UPDATE mobiversa.PAYOUT_HOLD_TXN pd SET pd.STATUS = :payoutStatus, "
                    + "pd.MODIFIED_TIME = NOW(), " //time of action
                    + "pd.MODIFIED_BY = :actionTakenBy "//who took the action
                    + "WHERE pd.INVOICE_ID_PROOF = :invoiceId";

			int rowsAffected = super.getSessionFactory().createSQLQuery(sql).setParameter("payoutStatus", status).setParameter("actionTakenBy", actionTakenBy)
					.setParameter("invoiceId", invoiceId).executeUpdate();

	        logUpdateResult(rowsAffected, sql, status, invoiceId); 
	        
	        return rowsAffected;
		} catch (Exception e) {
			logger.error("Exception in updating PayoutHoldTxn status by InvoiceID: {" + invoiceId + "}, Status: '"
					+ status + "'. Error message: " + e.getMessage(), e);
			return 0;
		}
	}
	
	private static void logUpdateResult(int rowsAffected, String sql, String status, String invoiceId) {
	    String logMessage = "Query: [" + sql + "], Status: [" + status + "], InvoiceID: [" + invoiceId + "]";
	    if (rowsAffected > 0) {
	        logger.info("Number of rows affected: " + rowsAffected + ". " + logMessage);
	    } else {
	        logger.error("No rows affected. " + logMessage);
	    }
	}

	@Override
	public int updatePayoutTxnDetailsStatusAndDeclinedReasonByInvoiceId(String invoiceId, String status, String declinedReason) {
		try {
			String sql = "UPDATE mobiversa.PAYOUT_DETAIL pd SET pd.PAYOUT_STATUS = :payoutStatus, pd.FAILUREREASON = :declinedReason,"
					+ " pd.CURLEC_FAILURE_REASON = :declinedReason WHERE pd.INVOICE_ID_PROOF = :invoiceId AND pd.PAYOUT_STATUS NOT IN ('pd')";

			int rowsAffected = super.getSessionFactory().createSQLQuery(sql).setParameter("payoutStatus", status)
					.setParameter("invoiceId", invoiceId).setParameter("declinedReason", declinedReason)
					.executeUpdate();

			logUpdateResult(rowsAffected, sql, status, invoiceId);
			
			return rowsAffected;
		} catch (Exception e) {
			logger.error("Exception in updating PayoutDetail status by InvoiceID: {" + invoiceId + "}, Status: '"
					+ status + "'. Error message: " + e.getMessage(), e);
			return 0;
		}
	}
	
	@Override
	public String getMerchantSettlementEmailByID(long merchantID) {
		try {
			return (String) sessionFactory.getCurrentSession().createCriteria(Merchant.class)
					.setProjection(Projections.property("settlementEmail")).add(Restrictions.eq("id", merchantID))
					.setMaxResults(1).uniqueResult();
		} catch (Exception e) {
			logger.error("Exception in loading settlement email for merchantID: " + merchantID + " - " + e.getMessage(),
					e);
			return null;
		}
	}

	@Override
	public Merchant getMerchantDetailsByID(long merchantID) {
		try {
			return (Merchant) sessionFactory.getCurrentSession().createCriteria(Merchant.class)
					.add(Restrictions.eq("id", merchantID)).setMaxResults(1).uniqueResult();
		} catch (Exception e) {
			logger.error("Exception in loading settlement email for merchantID: " + merchantID + " - " + e.getMessage(),
					e);
			return null;
		}
	}
	
	@Override
	public MobileUser getMobileUserByMerchantId(long merchantID) {
	    try {
	        return (MobileUser) sessionFactory.getCurrentSession().createCriteria(MobileUser.class)
	                .createAlias("merchant", "m")
	                .add(Restrictions.eq("m.id", merchantID))
	                .setMaxResults(1)
	                .uniqueResult();
	    } catch (Exception e) {
	        logger.error("Exception in reterving MobileUser by MerchantId: " + merchantID + " - " + e.getMessage(), e);
	        return null;
	    }
	}
}
