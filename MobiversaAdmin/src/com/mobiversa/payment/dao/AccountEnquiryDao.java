package com.mobiversa.payment.dao;

import java.util.List;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PayoutHoldTxn;
import com.mobiversa.payment.dto.PayoutAccountEnquiryDto;
import com.mobiversa.payment.exception.MobiException;

public interface AccountEnquiryDao extends BaseDAO {
	Long getMerchantIdFromUserName(String userName);

	int sizeOfApprovalTransactions(long merchantId, String invoiceId) throws MobiException;

	int sizeOfApprovalTransactionsForPayoutUser() throws MobiException;

	List<PayoutAccountEnquiryDto> listApprovalTransactions(long merchant, int pageNumber, int transactionsPerPage,
			String invoiceId) throws MobiException;

	List<PayoutAccountEnquiryDto> listApprovalTransactionsForPayoutUser(int pageNumber, int transactionsPerPage)
			throws MobiException;

	PayoutHoldTxn findPayoutByInvoiceId(String invoiceId) throws MobiException;

	String getMobiApiKeyFromMerchantId(String id) throws MobiException;

	String getMerchantEmailFromUserName(String userName);

	void updateOnHold(String invoiceId, String reason) throws MobiException;

	String getEnableAccountEnquiry(final long merchantId);

	List<PayoutAccountEnquiryDto> findById(String type, String id, Long merchantId) throws MobiException;

	List<PayoutAccountEnquiryDto> findById(String type, String id) throws MobiException;

	int fetchMaxPayoutLimitExceededTransactionCount(String type, String fromDate, String toDate) throws MobiException;

	List<PayoutAccountEnquiryDto> fetchMaxPayoutLimitExceededTransactions(String status, String fromDate, String toDate,
			int firstTransaction, int transactionsPerPage);

	List<PayoutAccountEnquiryDto> searchMaxPayoutLimitExceededTransactions(String status, String fromDate,
			String toDate, List<String> searchValues, String searchType);

	PayoutHoldTxn getPayoutHoldTxnByInvoiceIDProof(String invoiceID);

	int updatePayoutHoldTxnStatusByInvoiceID(String invoiceId, String status,String actionTakenBy);

	int updatePayoutTxnDetailsStatusAndDeclinedReasonByInvoiceId(String invoiceId, String status,
			String declinedReason);

	String getMerchantSettlementEmailByID(long merchantID);

	Merchant getMerchantDetailsByID(long merchantID);
	
	MobileUser getMobileUserByMerchantId(long merchantID);

}
