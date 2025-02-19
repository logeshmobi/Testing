package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PreAuthorization;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.common.bo.TID;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.Transaction;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.common.dto.AgentResponseDTO;
import com.mobiversa.common.dto.TerminalDTO;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.AgentVolumeData;

public interface BoostTransactionDao extends BaseDAO {

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

	public void getForSettlement(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);
	
	

	public void getForSettlementnonmerchant(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props);

	public void searchForSettlement(final String toDate, final String fromDate, final String tid, final String status,
			final PaginationBean<ForSettlement> paginationBean,ArrayList<Criterion> props);
	
	/*public void listAllTerminals(final String toDate, final String fromDate, final String merchantName,
			final PaginationBean<ForSettlement> paginationBean);*/

	public List<TerminalDetails> getTerminalDetails(String mid);

	public TransactionResponse loadTransactionResponse(String trx_id);

	public TransactionRequest loadTransactionRequest(String trx_id);

	public ForSettlement getForSettlement(String trxId);

	public void loadMerchantByName(final PaginationBean<ForSettlement> paginationBean, String merchantName, String date);

	public AgentResponseDTO loadAgentByName(String agentName);

	public void loadTerminalByName(final PaginationBean<TerminalDTO> paginationBean, String businessName);
	
	// list of all transactions
	public List<ForSettlement> exportAllTransaction(ArrayList<Criterion> props,String date, String date1, String txnType);
	
	public void listSearchTransactionDetails(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,
			String date, String date1);
	
	public Receipt loadReceiptSignature(String trxId);
	
	//loadMerchantDet
	public Merchant loadMerchantDet(String trxId);

	public List<ForSettlement> subAgentVolume(ArrayList<Criterion> props,SubAgent subAgent);
	
	public List<ForSettlement> agentVolume(ArrayList<Criterion> props,String  agentName);
	
	//new method merchant volume agent login 26072016
	
	public List<ForSettlement> loadMerchantByVolume(ArrayList<Criterion> props ,String agentName);   /* final PaginationBean<ForSettlement> paginationBean*/
	
	
	//New Change for PreAuth
	
			public void getPreAuthTxn(PaginationBean<PreAuthorization> paginationBean, ArrayList<Criterion> props);
			
			public void searchPreAuth(final String toDate, final String fromDate, final String tid, final String status,
					final PaginationBean<PreAuthorization> paginationBean,ArrayList<Criterion> props);
			
			public PreAuthorization getPreAuthTxn(String trxId);
			
			
			public void listPreAuthTransaction(PaginationBean<PreAuthorization> paginationBean, ArrayList<Criterion> props,
					String date, String date1);
			
			public void loadPreAuthByName(final PaginationBean<PreAuthorization> paginationBean, String merchantName, String date);
			
			
			//new method for contactName in transaction summary page admin & merchant login
			
			public   TerminalDetails getTerminalDetailsByTid(String tid);
			
			//new method demo 05-10-2016
			
			public List<ForSettlement> subAgentVolume1(ArrayList<Criterion> props,SubAgent subAgent);
			
	
			
			
			
			
			//new method AgentVolume 06/01/2017	
			public List<AgentVolumeData> agentVolumeData(String  agentName);
			
			
			public List<AgentVolumeData> subAgentVolumeData(SubAgent agent);
			
			public List<AgentVolumeData> merchantVolumeData(String agentName);
			
			
			
			// merchant volume start method 10-01-2017
			public List<String> midByTransaction(String agentName);
			
			public List<AgentVolumeData> getMerchantByMid(String mid);
			
			public List<AgentVolumeData> superAgentVolumeData(SubAgent agent,Agent AgId);
			
			
			//public SubAgent loadSubAgentDetails1(Agent AgId);
			
			
//merchant volume end	
			
			
//all transaction export new method
			
			
			public List<ForSettlement> listAllETransactionDetails(ArrayList<Criterion> props,
					String date, String date1);

			void getCashTransForSettlement(
					PaginationBean<ForSettlement> paginationBean,
					ArrayList<Criterion> props);

			void getCardTransForSettlement(
					PaginationBean<ForSettlement> paginationBean,
					ArrayList<Criterion> props);

			void searchnonmerchantForSettlement(String fromDate, String toDate,
					PaginationBean<ForSettlement> paginationBean,
					ArrayList<Criterion> props);

			
			public void searchForSettlementnew(String fromDate, String toDate,
					PaginationBean<ForSettlement> paginationBean,
					ArrayList<Criterion> criterionList);

			public void searchForSettlementcash(String fromDate, String toDate,
					PaginationBean<ForSettlement> paginationBean,
					ArrayList<Criterion> criterionList);

			

			public void searchForSettlementcard(String fromDate, String toDate,
					String tid, String status, PaginationBean<ForSettlement> paginationBean,
					ArrayList<Criterion> criterionList);

			
			public Merchant loadMerchantbyid(MID mid1);

			public MID loadMid(String mid);

			void listAllTransactionDetailsbyAdmin(
					PaginationBean<ForSettlement> paginationBean,
					ArrayList<Criterion> props, String date, String date1,String status);


			public List<ForSettlement> exportAllTransactionbyAdmin(
					ArrayList<Criterion> criterionList, String data,
					String data1, String status);

			

}
