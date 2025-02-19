package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.BizAppSettlement;
import com.mobiversa.common.bo.BnplTxnDetails;
import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.CountryCurPhone;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.HolidayHistory;
import com.mobiversa.common.bo.JustSettle;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MotoVCDetails;
import com.mobiversa.common.bo.PayoutBankBalance;
import com.mobiversa.common.bo.PayoutDetail;
import com.mobiversa.common.bo.PreAuthorization;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.RefundRequest;
import com.mobiversa.common.bo.SettlementDetails;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.common.bo.TID;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.Transaction;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.common.bo.UMEcomTxnRequest;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.common.dto.AgentResponseDTO;
import com.mobiversa.common.dto.TerminalDTO;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.Settlementbalance;
import com.mobiversa.payment.dto.AgentVolumeData;
import com.mobiversa.payment.dto.FinanceReport;
import com.mobiversa.payment.dto.SettlementDetailsList;
import com.mobiversa.payment.dto.TempletFields;
import com.mobiversa.payment.dto.WithdrawDeposit;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PreauthModel;
import com.mobiversa.payment.util.SettlementModel;
import com.mobiversa.payment.util.UMEzyway;
import com.mobiversa.payment.util.forsettlement;

public interface TransactionDao extends BaseDAO {

	// list of all transactions
	public void listAllTransaction(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1, String txnType);

	public void listAllTransactionDetails(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1);

	public TID getTID(Transaction transaction);

	// this method is for listing all the transaction of particular mobileuser
	public void listTransactionTIDUsers(PaginationBean<Transaction> paginationBean, ArrayList<Criterion> props);

	// public void listTransaction(final PaginationBean<Transaction>
	// paginationBean, final ArrayList<Criterion> mid);
	public List<ForSettlement> listTransaction(final PaginationBean<ForSettlement> paginationBean, String mid);

	// grabpay
	public void getForSettlement(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);

	// Grabpay qr

	public void getForSettlementgrabpayqr(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);

	// Grabpay ecom

	public void getForSettlementgrabpayecom(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);

	public void searchForSettlement1(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String fromDate, String toDate, String status, String tid, Merchant merchant);

	// settlementsum
	void listsettleDetails(final PaginationBean<SettlementMDR> paginationBean, Merchant merchant, String date,
			String date1);

	// boostsettlementsum
	void listboostsettleDetails(final PaginationBean<BoostDailyRecon> paginationBean, Merchant merchant, String date,
			String date1);

	// grabpaysettlementsum
	void listgrabpaysettleDetails(final PaginationBean<GrabPayFile> paginationBean, Merchant merchant, String date,
			String date1);

	public void getForSettlementnonmerchant(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);

	public void searchForSettlement(final String toDate, final String fromDate, final String tid, final String status,
			final PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);

	/*
	 * public void listAllTerminals(final String toDate, final String fromDate,
	 * final String merchantName, final PaginationBean<ForSettlement>
	 * paginationBean);
	 */

	public List<TerminalDetails> getTerminalDetails(String mid, String motoMid, String ezyrecMid);

	public TransactionResponse loadTransactionResponse(String trx_id);

	public TransactionRequest loadTransactionRequest(String trx_id);

	public ForSettlement getForSettlement(String trxId);

	public void loadMerchantByName(final PaginationBean<ForSettlement> paginationBean, String merchantName,
			String date);

	public AgentResponseDTO loadAgentByName(String agentName);

	public void loadTerminalByName(final PaginationBean<TerminalDTO> paginationBean, String businessName);

	// list of all transactions
	public List<ForSettlement> exportAllTransaction(ArrayList<Criterion> props, String date, String date1,
			String txnType);

	public List<ForSettlement> exportAllUmTransaction(ArrayList<Criterion> props, String date, String date1,
			String txnType);

	public void listSearchTransactionDetails(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1);

	public Receipt loadReceiptSignature(String trxId);

	// loadMerchantDet
	public Merchant loadMerchantDet(String trxId);

	public List<ForSettlement> subAgentVolume(ArrayList<Criterion> props, SubAgent subAgent);

	public List<ForSettlement> agentVolume(ArrayList<Criterion> props, String agentName);

	// new method merchant volume agent login 26072016

	public List<ForSettlement> loadMerchantByVolume(ArrayList<Criterion> props,
			String agentName); /* final PaginationBean<ForSettlement> paginationBean */

	// New Change for PreAuth

	public void getPreAuthTxn(PaginationBean<PreAuthorization> paginationBean, ArrayList<Criterion> props);

	public void searchPreAuth(final String toDate, final String fromDate, final String tid, final String status,
			final PaginationBean<PreAuthorization> paginationBean, ArrayList<Criterion> props);

	public PreAuthorization getPreAuthTxn(String trxId);

	public void listPreAuthTransaction(PaginationBean<PreAuthorization> paginationBean, ArrayList<Criterion> props,
			String date, String date1, String txnType);

	public void loadPreAuthByName(final PaginationBean<PreAuthorization> paginationBean, String merchantName,
			String date, String txnType);

	// new method for contactName in transaction summary page admin & merchant login

	public TerminalDetails getTerminalDetailsByTid(String tid);

	public MobileUser getMobileUserByGpayTid(String tid);

	public MobileUser getMobileUserByGpayTidqr(String tid);

	// new method demo 05-10-2016

	public List<ForSettlement> subAgentVolume1(ArrayList<Criterion> props, SubAgent subAgent);

	// new method AgentVolume 06/01/2017
	public List<AgentVolumeData> agentVolumeData(String agentName);

	public List<AgentVolumeData> subAgentVolumeData(SubAgent agent);

	public List<AgentVolumeData> merchantVolumeData(String agentName);

	// merchant volume start method 10-01-2017
	// public List<String> midByTransaction(String agentName);

	public List<AgentVolumeData> getMerchantByMid(String mid);
	// public List<AgentVolumeData> getMerchantByMid(String mid,String motoMid);

	public List<AgentVolumeData> superAgentVolumeData(SubAgent agent, Agent AgId);

	// public SubAgent loadSubAgentDetails1(Agent AgId);

//merchant volume end	

//all transaction export new method

	public List<ForSettlement> listAllETransactionDetails(ArrayList<Criterion> props, String date, String date1);

	/*
	 * void getCashTransForSettlement( PaginationBean<ForSettlement> paginationBean,
	 * ArrayList<Criterion> props);
	 */

	public void getCardTransForSettlement(PaginationBean<forsettlement> paginationBean, Merchant merchant,
			String txnType);

	public void searchnonmerchantForSettlement(String fromDate, String toDate,
			PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);

	public void searchForSettlementnew(String fromDate, String toDate, PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList);

	public void searchForSettlementcash(String fromDate, String toDate, PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList);

	public void searchForSettlementcard(String fromDate, String toDate, String tid, String status,
			PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> criterionList);

	public Merchant loadMerchantbyid(MID mid1);

	public MID loadMid(String mid);

	public void listAllTransactionDetailsbyAdmin(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props, String date, String date1, String status);

	public List<ForSettlement> exportAllTransactionbyAdmin(ArrayList<Criterion> criterionList, String data,
			String data1, String status);

	public void boostTransactionbyMerchant(String fromDate, String toDate, String tid, String status,
			PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props, Merchant merchant);

	/*
	 * void getBoostTransactionbyMerchant( PaginationBean<ForSettlement>
	 * paginationBean, ArrayList<Criterion> props);
	 */
	public void getAllTransactionbyMerchant(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			Merchant merchant);

	public void searchAllForSettlement(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String fromDate, String toDate, String status, Merchant merchant);

	public List<CountryCurPhone> loadCountryData();

	public void searchcardDetails(String fromDate, String toDate, String tid, String status,
			PaginationBean<ForSettlement> paginationBean, Merchant merchant);

	public void loadMerchantByName(PaginationBean<ForSettlement> paginationBean, String mid, String date,
			String txnType);

	public void searchForSettlementMoto(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String fromDate, String toDate, String status, Merchant merchant);

	public List<MobileUser> getMobileUser(String tid);

	public List<String> midByTransaction(String agentName);

	public MobileUser getMobileUserByMotoTid(String motoTid);

	public MobileUser getMobileUserByAuthTid(String motoTid);

	public void searchForSettlementEzyWay(PaginationBean<forsettlement> paginationBean, String fromDate, String toDate,
			Merchant merchant);

	public void searchForSettlementEzyRec(PaginationBean<forsettlement> paginationBean, String fromDate, String toDate,
			Merchant merchant);

	public void searchForSettlementEzyPass(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String fromDate, String toDate, String status, String tid,
			Merchant merchant);

	public List<TerminalDetails> getTerminalDetails(String merchantId);

	public void MerchantTransactionSummByAdmin(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String fromDate, String toDate, String status);

	public List<ForSettlement> MerchantExportTrans(ArrayList<Criterion> props, String fromDate, String toDate,
			String status);

	public void getCardTransactionForSettlement(PaginationBean<ForSettlement> paginationBean, Merchant merchant,
			String txnType, String date, String date1, String status);

	public List<AgentVolumeData> getMerchantVolumeByMID(String merchantID, String agID);

	public String merchantCount(String agentName);

	public List<String> midByTransaction(String agentName, String offset);

	public List<TerminalDetails> getAllTid(Merchant merchant);

	public List<TerminalDetails> getGpayTid(Merchant merchant);

	public List<MobileUser> getGpayTidbyFK(Merchant merchant);

	public List<MobileUser> getAllGpayTid();

	public void getMotoList(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> criterionList,
			String tid, String status, String status2, Merchant merchant);

	public void getAllUMTransactionbyMerchant(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> props,
			Merchant merchant);

	public void getTransactionRequest(PaginationBean<TransactionRequest> paginationBean, ArrayList<Criterion> props);

	public void getTransactionResponse(PaginationBean<TransactionResponse> paginationBean, ArrayList<Criterion> props);

	public void getTransactionEnquiry(PaginationBean<TransactionRequest> paginationBean, String fromDate, String toDate,
			String mid, String tid);

	public List<MID> loadAllmid();

	public List<TerminalDetails> loadAlltid();

	public void searchForSettlementEzyRecplus(PaginationBean<forsettlement> paginationBean, String fromDate,
			String toDate, Merchant merchant);

	public void searchForSettlementGrabpay(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String fromDate, String toDate, String status, String tid,
			Merchant merchant);

	public void searchUMForSettlement(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> props,
			String fromDate, String toDate, String status, Merchant merchant, String txnType);

	public void getUMMidTransForSettlement(PaginationBean<ForSettlement> paginationBean, String mid, String txnType);

	public List<TerminalDetails> getTerminalDetails(Merchant merchant);

	public List<TerminalDetails> getEzywireTerminalDetails(Merchant merchant);

	public void listAllUmTransaction(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1, String txnType);

	public void listAllUmEzywireTransaction(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1, String txnType);

	public void loadUmMerchantByName(PaginationBean<ForSettlement> paginationBean, String mid, String date,
			String txnType);

	public void searchAllForSettlement(PaginationBean<forsettlement> paginationBean, String fromDate, String toDate,
			Merchant merchant);

	public void searchForSettlementMotoByTid(PaginationBean<forsettlement> paginationBean, String fromDate,
			String toDate, Merchant merchant);

	public List<Merchant> getMerchantDataByAgent(BigInteger agid);

	public List<String> loadmidBymerchant(Long merchantid);

	public List<TransactionRequest> exportTransactionExpiry(ArrayList<Criterion> criterionList, String fromDate1,
			String toDate1, String mid, String tid);

	public void listUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String txnType);

	public void listUMEzyauthTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String txnType);

	public void listUMMotoTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String txnType);

	public void listUMLinkTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String txnType);

	public void listUMVccTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String txnType);

	public void listUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String txnType, Merchant currentMerchant);

	public void SearchlistUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umEzywayMid, String txnType,
			Merchant currentMerchant);

	public void listUMMotoTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umMotoMid, String fiuuMid, String txnType, Merchant currentMerchant);

	public void listUMLinkTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umMotoMid, String txnType, Merchant currentMerchant);

	public void listUMVccTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umMotoMid, String txnType);

	public void listUMEzywayTransactionEnq(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String txnType);

	public void listUMMotoTransactionEnq(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umMotoMid, String txnType);

	public void listUMVccTransactionEnq(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umMotoMid, String txnType);

	public UMEcomTxnRequest loadUMEzywayTransactionRequest(String id);

	// BNPL VOID INTEGRATION

	public BnplTxnDetails loadBnplDetails(String id);

	public UMEcomTxnResponse loadUMEzywayTransactionResponse(String id);

	public ForSettlement loadBoostForSettlement(String rrn);

	public ForSettlement loadGrabpayForSettlement(String rrn);

	public TempletFields getMerchantDetByGrabpayTid(String tid);

	public TempletFields getMerchantDetByGrabpayecom(String tid);

	public FpxTransaction loadFpxTransaction(String txnId);

	void exportUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umEzywayMid, String txnType, Merchant currentMerchant);

	void exportUMMotoTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umMotoMid,String fiuuMid, String txnType, Merchant currentMerchant);

	void exportUMVccTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umMotoMid, String txnType);

	void exportUMEzywayTransactionEnq(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umEzywayMid, String txnType);

	void exportUMMotoTransactionEnq(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umMotoMid, String txnType);

	void exportUMVccTransactionEnq(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umMotoMid, String txnType);

	void exportUMEzywayTransactionAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMMotoTransactionAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMLinkTransactionAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMVccTransactionAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void listUMEzywayTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void listUMMotoTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void listUMLinkTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void listUMVccTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMEzywayTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMMotoTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMLinkTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMVccTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	public void motoVC(PaginationBean<MotoVCDetails> paginationBean, String data, String date1, String motoMid)
			throws Exception;

	public List<MotoVCDetails> getActiveMoto(String mid);

	public MotoVCDetails motoVCById(String id);

	public void exportUMEzyauthTransactionAdmin(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	/* public List<AgentVolumeData> getMerchantVolByMid(String mid); */

	public void listBoostTransaction(PaginationBean<BoostDailyRecon> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String txnType);

	public void listUMEzyauthMerchantTransaction(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, Merchant merchant, String txnType);

	public void exportUMEzyauthTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, Merchant merchant, String txnType);

	public BoostDailyRecon loadBoostSettlement(String from);

	public void listBoostSettlement(PaginationBean<BoostDailyRecon> paginationBean, String date);

	public SettlementMDR loadSettlement(String from, String to);

	public void listSettlement(PaginationBean<SettlementMDR> paginationBean, String from, String to);

	public void listUMMotoTransactionByAgent(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, StringBuffer strUm, String txnType);

	public void exportUMMotoTransactionAgent(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, StringBuffer strUm, String txnType);

	public List<AgentVolumeData> getMerchantVolByMid(String mid, String type);

	public List<AgentVolumeData> getAgentVolumeData(String agentName, StringBuffer str, StringBuffer strUm, long id);

	public Merchant getMerchantTypeByID(String id);

	public List<AgentVolumeData> agentVolumeData1(String agentName);

	public List<AgentVolumeData> getMerchantByMid1(String mid);

	public void searchAllForsettlementTransaction(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	public void listAllForsettlementTransaction(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	void searchAllUmEzywireTransaction(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1, String txnType);

	public void exportUMLinkTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umMotoMid, String txnType, Merchant currentMerchant);

	public List<AgentVolumeData> agentVolumeUM(StringBuffer str);

	public List<AgentVolumeData> agentVolumeForsettle(StringBuffer str);

	public List<AgentVolumeData> getMerchantByMidList(StringBuffer str);

	public List<AgentVolumeData> getMerchantByMid1List(StringBuffer str);

	Merchant loadMerchantByID(BigInteger merchantId);

	public void listmobiliteSettlement(PaginationBean<SettlementMDR> paginationBean, String from, String to);

	public SettlementMDR loadmobiliteSettlement(String from, String to);

	public List<AgentVolumeData> getHotelMerchantByMid(String mid);

	public TransactionRequest loadTxnDetailsByID(BigInteger bigInteger);

	public BizAppSettlement loadbizappSettlement(String from, String to);

	public List<AgentVolumeData> getHotelMerchantVolByUMid(StringBuffer uMid);

	void listFPXTransaction(PaginationBean<FpxTransaction> paginationBean, String fromDate, String toDate, String VALUE,
			String TXNTYPE);

	// search

	void listFPXTransactionsearch(PaginationBean<FpxTransaction> paginationBean, String fromDate, String toDate);

	void listFPXTransactionByMid(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails, String fromDate,
			String toDate);

	public void listMerchantFPXTransactionByMid(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String fromDate, String toDate);

	// search
	public void listMerchantFPXTransactionByMidsearch(PaginationBean<FpxTransaction> paginationBean,
			Merchant midDetails, String fromDate, String toDate);

	public List<AgentVolumeData> agentTotalVolume(StringBuffer str);

	public void listAllUmTransactionDetailsbyAdmin(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String fromDate1, String toDate1, String status);

	public List<AgentVolumeData> getMerchantInTxnSummary(StringBuffer str);

	void listbizappSettlementByMerchant(PaginationBean<BizAppSettlement> paginationBean, Merchant merchant,
			String date);

	void listbizappSettlement(PaginationBean<BizAppSettlement> paginationBean, String date, String date1);

	BizAppSettlement loadLatestbizappSettlement();

	void listLatestbizappSettlement(PaginationBean<BizAppSettlement> paginationBean);

	SettlementMDR loadLatestmobiliteSettlement();

	void listLatestmobiliteSettlement(PaginationBean<SettlementMDR> paginationBean);

	public SettlementMDR loadLatestSettlement();

	public void listLatestSettlement(PaginationBean<SettlementMDR> paginationBean);

	List<TerminalDetails> getGpayTerminalDetails(Merchant merchant);

	void listUMEzyrecTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umEzyrecMid, String txnType, Merchant currentMerchant);

	void exportUMEzyrecTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umEzyrecMid, String txnType, Merchant currentMerchant);

	void listUMEzyrecTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMEzyrecTransactionAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void listUMEzyrecTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void exportUMEzyrecTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void listUMEzywayTransactionByAgent(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, StringBuffer strUm, String txnType);

	void exportUMEzywayTransactionAgent(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, StringBuffer strUm, String txnType);

	List<MasterMerchant> getMasterMerchantDataByAgent(String agid);

	public List<AgentVolumeData> getMerchantBymmId(String mid);

	public List<AgentVolumeData> getMerchantBymmId1(String mid);

	void listMobiliteLinkTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String mid);

	void exportMobiliteLinkTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String mid);

	public void listAllUmTransactionDetails(PaginationBean<ForSettlement> paginationBean, Merchant merchant,
			String fromDate, String toDate);

	void listMerchantFPXSettlementByMid(PaginationBean<FpxTransaction> paginationBean, Merchant merchant, String from,
			String to);

	void listUMEzyLinkSSTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umSsMotoMid, String txnType);

	void exportUMLinkSSTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umSsMotoMid, String txnType);

	void listSettlementMDRTransaction(PaginationBean<SettlementModel> paginationBean, String date1, String date2,
			String txntype);

	SettlementMDR loadSettlementMDR(String rrn);

	BoostDailyRecon loadBoostdlyrecon(String rrn);

	FpxTransaction loadFpxTransaction(String rrn, String invoiceId);

	// Newly added on 12042021- Santhosh
	public MobiMDR loadMobiMdr(String mid);

	// Newly added on 13042021- Santhosh
	public String totalSettleAmount(String from, String to, String mid);

	void searchAllUmEzywireplusTransaction(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1, String txnType);

	public void listAllUmEzywireplusTransaction(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	public void listUMSplitTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String SplitMid, String txnType);

	public void exportUMSplitTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String SplitMid, String txnType);

	// rk

	void exportUMSplitTransactionAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	public void listUMSplitTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String txnType);

	public void FPXTransactionEnqByMid(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String fromDate, String toDate);

	// rkboostecom
	public void searchecomForsettlementTransaction(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	// rkboostecom
	public void searchqrForsettlementTransaction(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	// rkboostqr
	public void listAllForsettlementTransactionQR(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	// rkboostecom
	public void listAllForsettlementTransactionEcom(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType);

	// GRABPAY NEW sum
	void ListGrabpaySummaryAdmin(final PaginationBean<ForSettlement> paginationBean, Merchant merchant, String date,
			String date1, String VALUE, String TXNTYPE, String export);

	public void listAllForsettlementTransactionSearchAPI(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType, String VALUE);

	void ListofM1PaySummarySearchApi(PaginationBean<SettlementModel> paginationBean, String date1, String date2,
			String txntype, String VALUE);

	void ListofBnplSummarySearchApi(PaginationBean<SettlementModel> paginationBean, String date1, String date2,
			String txntype, String VALUE);

	public void listUMEzywayTransactionSearchApi(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType, String VALUE);

	public void listAllUmEzywireTransactionSearchAPI(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props, String date, String date1, String txnType, String VALUE);

	public void listUMMotoTransactionSearchApi(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType, String VALUE);

	public void listUMLinkTransactionSearchAPI(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType, String VALUE);

	public void listUMEzyauthTransactionSearchAPI(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType, String VALUE);

	// Ezyway Failed Transaction - Start
	public void listUMEzywayTransactionFailure(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umEzywayMid, String txnType,
			Merchant currentMerchant);

	void exportUMEzywayTransactionFailure(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umEzywayMid, String txnType, Merchant currentMerchant);

	// Ezyway Failed Transaction - End

	// EzyLink Failed Transaction - Start
	public void listUMLinkTransactionFailure(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umMotoMid, String txnType,
			Merchant currentMerchant);

	public void exportUMLinkTransactionFailure(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umMotoMid, String txnType,
			Merchant currentMerchant);

	// EzyLink Failed Transaction - End

	// EzyMoto Failed Transaction - Start

	public void listUMMotoTransactionFailure(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umMotoMid, String txnType,
			Merchant currentMerchant);

	void exportUMMotoTransactionFailure(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umMotoMid, String txnType, Merchant currentMerchant);

	// EzyMoto Failed Transaction - End

	// EzyRec Failed Transaction - Start

	void listUMEzyrecTransactionFailure(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umEzyrecMid, String txnType, Merchant currentMerchant);

	void exportUMEzyrecTransactionFailure(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String umEzyrecMid, String txnType, Merchant currentMerchant);

	// EzyRec Failed Transaction - End

	// EzyWire Failed Transaction - Start

	public void getAllUMTransactionbyMerchantFailure(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> props, Merchant merchant);

	public void searchUMForSettlementFailure(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> props,
			String fromDate, String toDate, Merchant merchant);

	// EzyWire Failed Transaction - End

	// EARLY SETTLEMENT - START

	public List<String> tocheckholiday(String Currentdate, String CurrentTime);

	public List<String> tocheckholidayList(String Currentdate);

	public HolidayHistory loadHoliday(String currentdate);

	public List<SettlementMDR> loadNetAmountandsettlementdatebyCard(String settlementdate, Merchant merchant);

	public List<SettlementMDR> loadNetAmountbyCard(String settledate, double ezysettleFee, Merchant merchant);

	public List<BoostDailyRecon> loadNetAmountandsettlementdatebyBoost(String settlementdate, Merchant merchant);

	public List<BoostDailyRecon> loadNetAmountbyBoost(String settledate, double ezysettleFee, Merchant merchant);

	public List<GrabPayFile> loadNetAmountandsettlementdatebyGrabpay(String settlementdate, Merchant merchant);

	public List<GrabPayFile> loadNetAmountbyGrabpay(String settledate, double ezysettleFee, Merchant merchant);

	public List<FpxTransaction> loadNetAmountandsettlementdatebyFpx(String settlementdate, Merchant merchant);

	public List<FpxTransaction> loadRefundAndsettlementdatebyFpx(String settlementdate, Merchant merchant);

	public List<FpxTransaction> loadNetAmountbyFpx(String settledate, double ezysettleFee, Merchant merchant);

	public List<EwalletTxnDetails> loadNetAmountandsettlementdatebym1Pay(String settlementdate, Merchant merchant);

	public List<EwalletTxnDetails> loadRefundsettlementdatebym1Pay(String settlementdate, Merchant merchant);

	public List<EwalletTxnDetails> loadNetAmountbym1Pay(String settledate, double ezysettleFee, Merchant merchant);

	public List<GrabPayFile> loadGrabpayTxndatebySettledate(String settlementdate, long currentMerchantid);

	public List<FpxTransaction> loadFpxTxndatebySettledate(String settlementdate, Merchant merchant);

	// EARLY SETTLEMENT - END

	// EZYSETTLE SUMMARY BY ADMIN - START (27/03/2022)

	void ListofEzySettleSummary(PaginationBean<SettlementModel> paginationBean, String date1, String date2,
			String txntype);
	// EZYSETTLE SUMMARY BY ADMIN - END (27/03/2022)

	// M1 PAY SUMMARY BY ADMIN - START (29/07/2022)
	void ListofM1PaySummary(PaginationBean<SettlementModel> paginationBean, String date1, String date2, String txntype,
			String export);
	// M1 PAY SUMMARY BY ADMIN - END (29/07/2022)

	// BNPL SUMMARY BY ADMIN - START (30/11/2022)
	void ListofBnplSummary(PaginationBean<SettlementModel> paginationBean, String date1, String date2, String txntype);
	// BNPL SUMMARY BY ADMIN - END (30/11/2022)

	// PREAUTH SUMMARY BY MERCHANT - START (04/05/2022)
	public void PreAuthList(PaginationBean<PreauthModel> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, Merchant currentMerchant);

	public void PreAuthListExport(PaginationBean<PreauthModel> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, Merchant currentMerchant);

	public void PreAuthList1(PaginationBean<PreauthModel> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, Merchant currentMerchant);

	public void PreAuthList1Export(PaginationBean<PreauthModel> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, Merchant currentMerchant);

	public PreAuthorization loadPreAuthorizationbyTxnId(BigInteger txid);

	public UMEcomTxnResponse loadUMEcomTxnResponsebyTxnId(String txid);

	// RK PORTAL(28/06/22) Ezysettle By Merchant Start
//	public void ListofEzySettleSummarymerchant(PaginationBean<SettlementModel> paginationBean, String data,
//			String date1, String txntype, Merchant currentMerchant);

	// PAYOUT BY DHINESH & RK - START

	public void listPayoutTransaction(PaginationBean<PayoutModel> paginationBean, String date1, String date2,
			String export);

	PayoutDetail loadPayoutDetailByTxnId(String txnId);
	// PAYOUT BY DHINESH & RK - END

	EwalletTxnDetails loadEwalletTxnDetails(String mrn);

	BnplTxnDetails loadBnplTxnDetails(String mrn);

	void listUMEzyauthFailedTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, Merchant currentmerchant, String txnType);

	void listUMEzyauthFailedTransaction1(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, Merchant currentmerchant, String txnType);

	void listUMAuthTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1, String txnType);

	void listpreauthfee(PaginationBean<UMEzyway> paginationBean, String date, String date1);

	public void listFPXTransactionByMidexport(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String from, String to);

	void listFPXTransactionsearchexport(PaginationBean<FpxTransaction> paginationBean, String from, String to);

	public void merchantEwallet(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String paydeeEzywaymid, String ummid, String motomid,
			String ezyrecmid, String mid, String bnplMid, String boost, String tngMid, String shoppyMid, String grabmid,
			String txnType, Merchant currentMerchant);

	public void merchantEwallet1(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String paydeeEzywaymid, String ummid, String motomid,
			String ezyrecmid, String mid, String bnplMid, String boost, String tngMid, String shoppyMid, String grabmid,
			String txnType, Merchant currentMerchant);

	public void merchantFpxtranscation(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String fpxmid, String ummotomid, String mid, String txnType,
			Merchant currentMerchant);

	public void merchantFpxtranscation1(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String fpxmid, String ummotomid, String mid, String txnType,
			Merchant currentMerchant);

	// UM_EZYWIRE PAGINATION

	public void listUMEzywireTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String txnType, Merchant currentMerchant);

	public void exportUMEzywireTransaction(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1, String umEzywayMid, String txnType, Merchant currentMerchant);

	public void listPayoutTransactionByMerchant(PaginationBean<PayoutModel> paginationBean, String data, String date1,
			String merchant);

	// withDrawAmount

	public void withDrawAmount(PaginationBean<PayoutModel> paginationBean, String merchant);

	public void loadPayoutbalance(PaginationBean<PayoutModel> paginationBean, Long id);

	public List<Settlementbalance> loadbankbalance();

	public PayoutBankBalance oldbankbalance();

	public List<FpxTransaction> loadRefundAndSettlementdatebyFpx(String settlementdate, Merchant merchant);

	public List<GrabPayFile> loadRefundAndSettlementdatebyGrabpay(String settlementdate, Merchant merchant);

	public List<BoostDailyRecon> loadRefundAndSettlementdatebyBoost(String settlementdate, Merchant merchant);

	public List<SettlementMDR> loadRefundAndSettlementdatebyCard(String settlementdate, Merchant merchant);

//	public ArrayList<RefundRequest> listRefund(String date);
	public void listRefund(PaginationBean<RefundRequest> paginationBean, String date);

	public void searchRefundTransaction(PaginationBean<RefundRequest> paginationBean, String transaction,
			String selectType);

	public void merchantFpxtranscationSearch(PaginationBean<UMEzyway> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umEzywayMid, String fpxmid,
			String ummotomid, String mid, String txnType, Merchant currentMerchant, String value);

	public void searchPayoutTransactionList(PaginationBean<PayoutModel> paginationBean, String payoutId);

	public void listFPXSettlementsearchexport(PaginationBean<FpxTransaction> paginationBean, String from);

	public void payoutTxnList(PaginationBean<PayoutModel> paginationBean, ArrayList<Criterion> criterionList,
			String data, String date1);

	public GrabPayFile loadGrabPayFile(String rrn);

	public String getTotalRefundAmountProcessed(String settlementDate, String merchant);

	List<Object[]> getBusinessNamesAndUsernames();

	void FpxMonthlySettlementSummaryexport(PaginationBean<FpxTransaction> paginationBean, String from,
			String businessName, String username);

	void BoostMonthlySettlementSummaryexport(PaginationBean<BoostDailyRecon> paginationBean, String from,
			String businessName, String username);

	// For Switch Host
	public List<String> getHostBankList();

	public String getSellerIdByBankName(String selectedBank);

	public List<Object[]> loadpayoutTxn(String invoiceid);

	// Filter
	public void listPayoutIdSearchApi(PaginationBean<UMEzyway> paginationBean, ArrayList<Criterion> criterionList,
			String txnType, String VALUE);

	// Dhivya changes

	/* FPX Failed Summary Start */

	public void merchantfailedFpxtranscation(PaginationBean<FpxTransaction> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umEzywayMid, String fpxmid,
			String ummotomid, String mid, String txnType, Merchant currentMerchant);

	public void merchantfailedFpxtranscationexport(PaginationBean<FpxTransaction> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String umEzywayMid, String fpxmid,
			String ummotomid, String mid, String txnType, Merchant currentMerchant);

	/* FPX Failed Summary end */

	/* filter */

	void listFPXfailedTransaction(PaginationBean<FpxTransaction> paginationBean, String fromDate, String toDate,
			String VALUE, String TXNTYPE);

	/* filter */

	// OCBC BANK BALANCE API
	public Settlementbalance loadAmBankBalance(Settlementbalance settlementbalance) throws MobiException;

	public Settlementbalance loadOCBCBankBalance(Settlementbalance settlementbalance) throws MobiException;

	// update balance
	public Object updateloadPayoutbalance(Long id);

	public void searchAllForsettlementTransactionForBoostExport(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String data, String date1, String txnType, String export);

	void listcardfailedTransaction(PaginationBean<UMEzyway> paginationBean, String fromDate, String toDate,
			String VALUE, String TXNTYPE);

	public String loadPayoutbalanceView(Long id);

	public List<Merchant> getListOfMerchantsByMerchantId(List<Long> id);

	public String getBankNameByMerchantId(String id);

	public SettlementDetails getDataFromSettlementDetails(String id);

	public List<PayoutModel> getPayoutTxnDetailsBetweenDates(final String date1, final String date2);

	public boolean loadAllMerchantPayoutbalanceView();

	public void listPayoutTransactionByMerchantData(PaginationBean<PayoutModel> paginationBean, String data,
			String date1, String merchantID, int currPage, String export);

	public void searchPayoutTransactionList(PaginationBean<PayoutModel> paginationBean, String value,
			String transaction_type, String merchant);

	public void searchPayoutLoginTransactionList(PaginationBean<PayoutModel> paginationBean, String value,
			String transaction_type);

	public SettlementMDR loadNetAmountandsettlementdatebyCardEzysettle(String settlementdate, Merchant merchant);

	public BoostDailyRecon loadNetAmountandsettlementdatebyBoostEzysettle(String settlementdate, Merchant merchant);

	public GrabPayFile loadNetAmountandsettlementdatebyGrabpayEzysettle(String settlementdate, Merchant merchant);

	public FpxTransaction loadNetAmountandsettlementdatebyFpxEzysettle(String settlementdate, Merchant merchant);

	public EwalletTxnDetails loadNetAmountandsettlementdatebym1PayEzysettle(String settlementdate, Merchant merchant);

	public void saveJustSettle(JustSettle updatejsdata);

	// withdraw and Deposit Summary

	void listDepositDetails(PaginationBean<WithdrawDeposit> paginationBean, String fromDate, String toDate,
			int currPage);

	void listDepositDetailsUsingId(PaginationBean<WithdrawDeposit> paginationBean, int currPage, String merchantId);

	void listWithdrawDetails(PaginationBean<WithdrawDeposit> paginationBean, String fromDate, String toDate,
			int currPage);

	void listWithdrawDetailsUsingId(PaginationBean<WithdrawDeposit> paginationBean, int currPage, String merchantId);

	List<Object[]> getBusinessNamesAndIds();

	public List<FinanceReport> getPayinTxnDetailsBetweenDates(String fromDate, String toDate, String umEzywayMid,
			String fpxmid, String ummotomid, String ummid, String mid, String paydeeEzywaymid, String ezyrecmid,
			String bnplMid, String boostmid, String tngMid, String shoppyMid, String grabmid, String fiuuMid, Merchant currentMerchant);

	public List<FinanceReport> getPayoutTxnDetailsBetweenDates(String fromDate, String toDate, Long Merchant,
			Merchant currentMerchant);

	List<String> getFpxHostList();

	String getHostIdByHostName(String hostName);

	boolean areAllFpxHostIdsSame();

	String getFirstFpxHostId();

	String getHostNameByMerchantId(String id);
	
	List<String> areAllProvidedFpxHostIdsSame(List<Long> merchantId);

	public void ListofEzySettleSummary(PaginationBean<SettlementModel> paginationBean, String date1, String date2,
			boolean isExport);

	public void ListofEzySettleSummarymerchant(PaginationBean<SettlementModel> paginationBean, String date1, String date2,
			Merchant currentMerchant, boolean isExport);

//	public void updateInternationalMerchantsData(ConcurrentMap<String, Double> settledAmountsMap,Merchant currentMerchant);
//
//	public boolean  getMerchantDataInternational(String settlementdate,Merchant currentMerchant);
//	
//	public List<MerchantSettlementInternational> getStoredSettlementData(String settlementdate,Merchant currentMerchant);
//
//	public ConcurrentMap<String, Double> getAllTransactions(String settlementdate, Merchant currentMerchant,ConcurrentMap<String, Double> settledAmountsMap,Set<String> uniqueSettlementDates);
//
	public void listSettlementDetails(PaginationBean<SettlementDetailsList> paginationBean);
	public void listSettlementDetailsByMerchant(PaginationBean<SettlementDetailsList> paginationBean, String businessName);
	
	public List<FinanceReport> getAllPayoutTxnDetailsBetweenDates(String fromDate, String toDate);

	public boolean loadHolidayHistory(String currentdate);
	

	boolean isCurrencyExchangeEnabled(String merchantId);

	Map<String, Double> getExchangeRatesForCurrentDay(String currentDay, String baseCurrency, String merchantId);
	
	List<String> getHolidayHistoryList(String currentYear);

	FpxTransaction loadFpxTransactionBySellerExOrderNumber(String sellerExOrderNumber);

}
