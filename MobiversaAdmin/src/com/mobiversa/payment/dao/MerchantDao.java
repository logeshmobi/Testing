package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.ui.Model;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.BalanceAudit;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.EzyRecurringPayment;
import com.mobiversa.common.bo.FileUpload;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPaymentTxn;
import com.mobiversa.common.bo.HostBankDetails;
import com.mobiversa.common.bo.InternalTable;
import com.mobiversa.common.bo.JustSettle;
import com.mobiversa.common.bo.KeyManager;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MerchantStatusHistory;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.common.bo.MobiLiteTerminal;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MotoTxnDetails;
import com.mobiversa.common.bo.PayoutBankBalance;
import com.mobiversa.common.bo.PayoutDepositDetails;
import com.mobiversa.common.bo.PayoutDetail;
import com.mobiversa.common.bo.PayoutGrandDetail;
import com.mobiversa.common.bo.RefundRequest;
import com.mobiversa.common.bo.SettlementBalance;
import com.mobiversa.common.bo.SettlementDetails;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.common.bo.WithdrawalTransactionsDetails;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MerchantDTO;
import com.mobiversa.payment.dto.MerchantGPVData;
import com.mobiversa.payment.dto.MerchantPaymentDetailsDto;
import com.mobiversa.payment.dto.ProductWiseAmount;
import com.mobiversa.payment.exception.MobileApiException;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.Justsettle;
import com.mobiversa.payment.util.MobiliteTrackDetails;
import com.mobiversa.payment.util.PayeeDetails;
import com.mobiversa.payment.util.Payer;
import com.mobiversa.payment.util.PayoutModel;

public interface MerchantDao extends BaseDAO {
	public void findByUserNames(final String businessName, final PaginationBean<Merchant> paginationBean);

	public void listMerchantUser(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props);

	public void updateMerchantStatus(Long id, CommonStatus status, MerchantStatusHistory history);

	public MerchantStatusHistory loadMerchantStatusHistoryID(Merchant merchant);

	public Merchant loadMerchant(String username);

	public Merchant loadMerchantDetail1(String username);

	public List<MobileUser> loadMobileUserByFkTid(long id);

	public Merchant loadMerchantbyBussinessName(String username);

	public Merchant loadMerchant(MID mid);

	public int changeMerchantPassWord(String Username, String newPwd, String OldPwd);

	// rkdashboardchanges
	public void listproductwiseamount(PaginationBean<ProductWiseAmount> paginationBean, Merchant merchant, Model model);

	public int getsucesscount(String mid1, String mid2, String mid3, String mid4, String mid5);

	public int getfailurecount(String mid1, String mid2, String mid3, String mid4, String mid5);

	public int getumsucesscount(String mid1, String mid2, String mid3, String mid4, String mid5, String mid6);

	public int getumfailurecount(String mid1, String mid2, String mid3, String mid4, String mid5, String mid6);
	// rkdashboardchanges

	// rksubmerchant
	void listsubMerchantUserByMiddefault(PaginationBean<Merchant> paginationBean, Long id, Timestamp from,
			Timestamp to);

	void listadminsubMerchantdefault(PaginationBean<Merchant> paginationBean);

	void listadminsubMerchantdefault1(PaginationBean<Merchant> paginationBean, List<String> submerchantList);

	void listsubMerchant(PaginationBean<Merchant> paginationBean, String type);

	void listsubMerchant1(PaginationBean<Merchant> paginationBean, String type);

	void subMerchantListByAdmin(PaginationBean<ForSettlement> paginationBean, String fromDate, String toDate,
			Merchant findMerchant, String type, List<MobileUser> mobileuser);

	// rksubmerchant
	void listsubMerchantUserByMid(PaginationBean<Merchant> paginationBean, Timestamp from, Timestamp to, Long id);

	public int changeMerchantPassWordByAdmin(String Username, String newPwd);

	// public String generatePassword();

	public Merchant loadMerchantbyEmail(String email);

	public int updateMIDData(Long m_id, Long merchant_id);

	public List<Merchant> loadMerchant();

	public List<Merchant> loadMerchant1();

	public SettlementBalance settleMerchant(String id);

	public void savesettlement(String settlementAmount, String topupAmt, String id);

	public List<Merchant> loadMerchantByNOB(String nob);

	// public List<MID> loadMIDByNOB(List<Long> ids);

	public List<MID> loadMIDByNOB(List<Long> ids);

	public void listAgentMerchant(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props);

	public MID loadMid(String mid);

	public void listMerchantUser1(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props);

	public FileUpload loadFileById(String id);

	public FileUpload updateFileById(FileUpload fileUpload);

	public List<FileUpload> loadFileByMerchantId(String merchId);

	public Merchant loadMerchantDetails(String username);

//new method for merchant summary search condition 16062017

	public void listMerchantSearch(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props);

	public List<Merchant> listMerchantSummary(ArrayList<Criterion> props, final String date, final String date1);

	public Merchant loadmobileMerchant(MID mid);

	public Merchant loadMerchantbyid(MID mid);

	public KeyManager validatecaptcha(String captcha2);

	public boolean deleteCaptcha(String captcha2);

//public MerchantDetails loadMerchantPoints(String mid);

	public int changeMobileuserPassWordByAdmin(String Username, String newPwd);

	public MID loadMotoMid(String motomid);

	public MID loadEzyPassMid(String ezypassmid);

	public MID loadMerchantmid(String mid);

	public MID loadMerchantmotomid(String motomid);

	public MID loadMerchantbyMerchant_FK(Long merchant_fk);

	public MID loadMidtoUpdateAudit(String mid);

	public Merchant loadMerchantbymerchantid(Long id);

	public MobileUser loadBankByMerchantFk(Merchant merchant);

	public HostBankDetails loadBankBySellerId(MobileUser mUser);

	public MerchantDetails loadMerchantPoints(Merchant merchant);

	public TransactionRequest listMerchantCardDetails(BigInteger TxnId);

	public void listRecurringMerchantUser(PaginationBean<EzyRecurringPayment> paginationBean,
			ArrayList<Criterion> props);

	public int UpdateRecurringStatus(String status, Long id);

	public EzyRecurringPayment loadMerchantRecurring(Long recID);

	public MID loadEzywayMid(String ezywaymid);

	public MID loadEzyrecMid(String ezyrecmid);

	public MID loadMerchantMIDDetails(String mid, String midType);

	public void listMotoTxnReqMerchantUser(PaginationBean<MotoTxnDetails> paginationBean, ArrayList<Criterion> props);

	public void loadReqMotoData(PaginationBean<MotoTxnDetails> paginationBean, String fromDate, String toDate,
			String status);

	public List<Merchant> loadMerchantByAdmin();

	public MID loadUMMid(String um_mid);

	public MID loadUMMotoMid(String um_motomid);

	public MID loadUMEzyPassMid(String um_ezypassmid);

	public MID loadUMEzywayMid(String um_ezywaymid);

	public MID loadUMEzyrecMid(String um_ezyrecMid);

	MID loadMidByMerchant_PK(String id);

	TerminalDetails loadTerminalDetailsByMid(String mid);

	public List<Merchant> loadUMMerchant();

//public MID loadMerchantezypassmid(String ezypassMid);

	public String getMerchantCurrentMonthTxnByNOB(StringBuffer str, StringBuffer strUm);

	public String getMerchantDailyTxnByNOB(StringBuffer str, StringBuffer strUm);

	public String getMerchantWeeklyTxnByNOB(StringBuffer str, StringBuffer strUm);

	public List<Merchant> loadMerchantByAgID(BigInteger agid);

	public void listPayLaterMerchants(PaginationBean<Merchant> paginationBean);

	public void searchPayLaterMerchants(PaginationBean<Merchant> paginationBean, String date, String date1);

	public void listMerchantDetails(PaginationBean<Merchant> paginationBean);

	public void getMerchantGPV(StringBuffer str, PaginationBean<Merchant> paginationBean);

	public void loadTerminalDetails(StringBuffer midStr, PaginationBean<TerminalDetails> paginationBean);

	public void loadCurrentTxnDetails(Merchant merchant, PaginationBean<TerminalDetails> paginationBean,
			String agentName);

	public List<String> getProductDetails(StringBuffer midStr);

	public void searchTxnDetails(Merchant merchant, PaginationBean<TerminalDetails> paginationBean, String period,
			String productType, String year);

	public void listMerchantGPVDetails(PaginationBean<Merchant> paginationBean);

	public List<Merchant> loadFpxMerchant();

	public Merchant validateMerchantEmailId(String emailId);

	MID loadumMotoMid(String umMotoMid);

	MID loadumEzywayMid(String umEzywayMid);

	public List<MerchantGPVData> listMerchantGPVDetailsByAgent(String agId);

	List<MerchantGPVData> listMerchantGPVDetailsBySuperAgent();

	void listMerchantUsersByMid(PaginationBean<MerchantDTO> paginationBean);

	void listMerchantUserByMid(PaginationBean<Merchant> paginationBean);

	MobiLiteMerchant loadMobiliteMerchant(String username);

	public MobiLiteTerminal loadMobiliteTerminalDetailsByMid(Long mid);

	MobiLiteMerchant loadMobiLiteMerchant(String username);

	int updateTrackDetails(MobiliteTrackDetails det);

	int changeMobiliteMerchantPassWord(String Username, String newPwd, String OldPwd);

	int updateEzylinkssTrackDetails(MobiliteTrackDetails det);

//SettlementUser loadSettlementUser(String username);

//Newly added on 07042021 - santhosh

	public MobileOTP checkOTP(String mno, String emailId);

//Newly added on 05052021 - santhosh
	public void updateMobileOTP(String string, String key);

	public int updateMKData(Long m_id, Long merchant_id);

//PAYOUT BY DHINESH & RK - START

	public PayoutGrandDetail PayoutGrandDetailbymerchantid(Long id);

	List<UMMidTxnLimit> UmmidTxnlimit();

//PAYOUT BY DHINESH & RK - END

	// 27-03-23 PAYOUT BALANCE

	public void loadPayoutbalance(final PaginationBean<PayoutModel> paginationBean);

	public Payer getPayerAmount(String resDate, Merchant merchant,String status);

	public Payer getPayerAmountJust(String resDate, Merchant merchant);

//	public List<PayeeDetails> getPayeeDetails(String resDate, Merchant merchant);

	// public List<PayeeDetails> getPayeeDetailsjust(String resDate,Merchant
	// merchant);
	public String updateFinalStatus(String resDate, Merchant merchant);

	public void updateBalanceNetAmt(String amount, String merchantId);

	public SettlementBalance SearchMerchantSettlement(String merchantId);

	public PayoutBankBalance oldbankbalance();

	public void updateBankAmount(String amount, String date);

	public void updateOverdraft(double overdraftAmount, double netAmount, String merchantId);

	public void updateoverdraftwithdepo(double overdraftAmount, double settlementbalance, double deposit,
			double totalBalance, String merchantId);

	public void updatedepositAmount(double settlementbalance, double deposit, double totalBalance, String merchantId);

	public Merchant findMerchant(String merchantId);

	public UMEcomTxnResponse ezywayChrckTransaction(String txnId, String mid);

	public FpxTransaction fpxCheckTransaction(String txnId, String mid);

	public ForSettlement ezywireCheckTransaaction(String txnId, String mid);

	public UMEcomTxnResponse ezyAuthCheckTransaaction(String txnId, String mid);

	public UMEcomTxnResponse ezymotoCheckTransaction(String txnId, String mid);

	public UMEcomTxnResponse ezylinkCheckTransaction(String txnId, String mid);

	public ForSettlement boostCheckTransaction(String txnId, String mid);

	public ForSettlement grabCheckTransaction(String txnId, String mid);

	public EwalletTxnDetails m1PayCheckTransaction(String txnId, String mid);

	public RefundRequest checkTranactionIsValid(String txnId);

	public FpxTransaction findbyFpxData(String txnId);

	public UMEcomTxnResponse findbyCardEzywayData(String txnId);

	public UMEcomTxnResponse findbyCardEzyway(String invoice);

	public ForSettlement findbyCardEzywireData(String txnId);

	public ForSettlement findbyCardEzywire(String invoice);

	public UMEcomTxnResponse findbyCardEzyAuthData(String txnId);

	public UMEcomTxnResponse findbyCardEzyAuth(String invoiceId);

	public UMEcomTxnResponse findbyCardEzyMotoData(String txnId);

	public UMEcomTxnResponse findbyCardEzyMoto(String invoiceId);

	public UMEcomTxnResponse findbyCardEzyLinkData(String txnId);

	public UMEcomTxnResponse findbyCardEzyLink(String invoiceId);

	public MID findbyMerchantMid(String mid);

	public String cardTransaction(MID mid, String currentDate);

	public String walletsTransaction(MID mid, String currentDate);

	public String m1PayTranascton(MID mid, String currentDate);

	public ForSettlement findbyBoostData(String txnId, String txnMid);

	public ForSettlement findbyBoostData(String txnId);

	public ForSettlement findbyBoost(String invoiceId);

	public ForSettlement findbyGrabData(String txnId);

	public ForSettlement findbyGrab(String invoiceID);

	public EwalletTxnDetails findbyM1PayData(String txnId);

	public EwalletTxnDetails findbyM1Pay(String invoiceId);

	public Merchant findbyMerchant(MID mid);

	public Merchant findRefundMerchant(long merchantId);

	public void updateRefundStatus(String intiateDate, String txnId);

	public void updateFinalRefundStatus(String intiateDate, String txnId);

	public void finalfpxRefundStatus(FpxTransaction fpxData, String settlementDate, String txnMid);

	public void finalCardEzywayRefundStatus(UMEcomTxnResponse cardEzywayData, String settlementDate, String txnMid);

	public void finalCardEzywireRefundStatus(String txnId, String settlementDate, String txnMid);

	public void finalCardEzyAuthRefundStatus(String txnId, String settlementDate, String txnMid);

	public void finalCardEzyEzyMotoRefundStatus(String txnId, String settlementDate, String txnMid);

	public void finalCardEzyLinkRefundStatus(String txnId, String settlementDate, String txnMid);

	public void finalBoostRefundStatus(String txnId, String settlementDate, String txnMid);

	public void finalGrabRefundStatus(String txnId, String settlementDate, String txnMid);

	public void finalM1PayRefundStatus(String txnId, String settlementDate, String txnMid);

	public boolean refundRequeststatusUpdate(String txnId, String settlementDate);

	public boolean refundRequeststatusUpdateBoost(String txnId, String settlementDate, String rfNum);

	public boolean refundRequestCardstatusUpdate(String txnId, String settlementDate);

	public RefundRequest loadRequestRefundList(String intiateDate, String txnId);

	public RefundRequest findbyValidRefundAmount(Merchant merchant, String settlementDate);

	public void updatedepositAmountPayoutGrand(double depositamount, Merchant merchantData);

	public void addDepositAmount(String depositAmount, String idString);

	public GrabPaymentTxn loadTxnByPartnerTxId(String txnId);

	public GrabPaymentTxn updateGrabPaymentTxn(GrabPaymentTxn gptr);

	public ForSettlement loadForSettlementByPartnerTxId(String txnId);

	public ForSettlement saveForSettlement(ForSettlement forset);

	public TerminalDetails loadTerminalByTID(String tid);

	public Long getStan(String tid);

	public void updateInternalTable(InternalTable internalTable);

	public PayoutDetail searchTransaction(String transactionId);

	public boolean updateBoostVoidStatus(String transactionId);

	public boolean updateBoostRefundStatus(String transactionId);

	public EwalletTxnDetails findbyTouchnGoAndShopeePayData(String txnId);

	public boolean updateTouchnGoRefundStatus(String transactionId, String invoiceId);

	public boolean updateShopeePayRefundStatus(String transactionId, String invoiceId);

	public MobileUser loadMobileUserById(Long id);

	public MobileUser findMobileuserByMerchantId(Merchant merchant, String manualSettlement);

	public void updateAccountEnquiryAndQuickPayout(Merchant merchant, String accountEnquiryEnabled, String quickPayoutEnabled/*, String quickPayoutUrl*/);

	public PayoutDepositDetails checkdepositDetails(String referenceNo);

	public void updateWithdrawdetailsInWithdrawalTransactionDetailsTable(WithdrawalTransactionsDetails withdrawDetails);

	public PayoutBankBalance loadBankbalance();

	public void exportsubMerchant(PaginationBean<Merchant> paginationBean, String type);

	public MID loadFPXMid(String fpxMID);

	public MID loadBoostMid(String boostMID);
	
	public MID loadFiuuMid(String fiuuMID);

	public MID loadEwalletMid(String mID);
//	public List<String> tocheckholiday(String currentDate);

	public List<Merchant> loadActiveMerchants();

	public SettlementDetails loadSettlementDetailsByMerchantId(String merchantID);

	public Double getAvaliableBalanceByMerchantId(String merchantId);

	public void saveWithdrawalTransactionDetailsTable(WithdrawalTransactionsDetails withdrawDetails);

	public WithdrawalTransactionsDetails getWithdrawalTransactionsDetailsByPayoutId(String payoutId);

	public void saveBalanceAuditTable(BalanceAudit balanceAudit);

	public BalanceAudit checkBalanceAuditEntryUpdatedByWithdrawalMapping(String depositEntryID);

	public void updateWithdrawalTransactionDetailsTable(WithdrawalTransactionsDetails withDetails);

	public void loadRefactoredPayoutbalance(final PaginationBean<PayoutModel> paginationBean);

	public List<PayoutBankBalance> loadTotalPayoutBankbalance();

	public PayeeDetails getPayeeDetails(String resDate, Merchant merchant,String status);

	public void updateEzysettleFinalStatus(String finalPayoutDate, Merchant currentMerchant,
			MerchantService merchantService, Justsettle just, String ezysettleReferenceNo, Payer pAmount)
			throws MobileApiException;

	public void updateEzysettlePendingStatus(String finalPayoutDate, Merchant currentMerchant,
			MerchantService merchantService, Justsettle just, String ezysettleReferenceNo, Payer pAmount)
			throws MobileApiException;

	public JustSettle findByMidandDate(String merchant);

	public Merchant loadMerchantById(String id);

	public void disableAsyncPayoutHandlerDetailsInMobileUser(MobileUser mobileUserdetails, Long id);

	public Agent loadagentUsername(String username);

	public void listMerchantsFromAgent(PaginationBean<MerchantDTO> paginationBean, long agentId);

	public void listMerchantsFromAgentsearch(PaginationBean<MerchantDTO> paginationBean, String fromDate, String toDate,
			long agentId);

	public MerchantInfo loadmerchantInfoByFK(String id);

	public BankUser loadBankUserByUsername(String username);

	public void updateOtpTime(String time,String idOrUsername,String field,String tableName);

	void insertMerchantInfoByFk(String formattedTime, String fk, String fieldName, String tblName);

	public void insertMerchantIfNotFound(String fk,String secondary_email);

	public void updateSecondaryEmail(String fk,String secondary_email);
	
	
	public List<MerchantPaymentDetailsDto> loadMerchantPaymentDetailsByFk(Long id);
	 
	public List<String> loadAllMerchant();

	int insertMerchantInfoForJenfi(String id,String uniqueID);

	int updateuniqueIDForJenfi(String id,String uniqueID);
	
	public List<MerchantDTO> getMerchantDtoList(List<Merchant> merchantList);
}
