package com.mobiversa.payment.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mobiversa.common.bo.BankUser;
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
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MotoVCDetails;
import com.mobiversa.common.bo.PayoutDetail;
import com.mobiversa.common.bo.PayoutHoldTxn;
import com.mobiversa.common.bo.PreAuthorization;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.RefundRequest;
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
import com.mobiversa.common.dto.TransactionDTO;
import com.mobiversa.payment.controller.bean.JsonRequestDto;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.PayoutSettleBean;
import com.mobiversa.payment.controller.bean.Settlementbalance;
import com.mobiversa.payment.dao.AccountEnquiryDao;
import com.mobiversa.payment.dao.EzysettleDao;
import com.mobiversa.payment.dao.MobileUserDao;
import com.mobiversa.payment.dao.TransactionDao;
import com.mobiversa.payment.dao.UserDao;
import com.mobiversa.payment.dto.AgentVolumeData;
import com.mobiversa.payment.dto.EarlySettlementModel;
import com.mobiversa.payment.dto.FinanceReport;
import com.mobiversa.payment.dto.PayoutAccountEnquiryDto;
import com.mobiversa.payment.dto.PayoutCallBack;
import com.mobiversa.payment.dto.SettlementDetailsList;
import com.mobiversa.payment.dto.TempletFields;
import com.mobiversa.payment.dto.WithdrawDeposit;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.MobileApiException;
import com.mobiversa.payment.util.ElasticEmailClient;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PreauthModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.util.SettlementModel;
import com.mobiversa.payment.util.UMEzyway;
import com.mobiversa.payment.util.Utils;
import com.mobiversa.payment.util.forsettlement;

@Service
public class TransactionService {

	@Autowired
	private TransactionDao transactionDAO;

	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	private MerchantService merchantService;

    @Autowired
    private  AccountEnquiryDao accountEnquiryDao;

	@Autowired
	private MobileUserDao mobileuserDAO;

	@Autowired
	private EzysettleDao ezysettleDao;

	@Autowired
	private UserDao userDao;

	private static final Logger logger = Logger.getLogger(TransactionService.class);

	/*
	 * @Autowired private ReportDataDAO reportDataDAO;
	 */

	// load transactions by pk

	public Transaction loadTransactionByPk(final Long id) {
		Transaction transaction = transactionDAO.loadEntityByKey(Transaction.class, id);
		if (transaction == null) {
			throw new RuntimeException("Transaction Not found. ID:: " + id);
		}
		return transaction;
	}

	// @javax.transaction.Transactional
	public void loadMerchantName(final PaginationBean<ForSettlement> paginationBean, final String merchantName,
			final String date, String txnType) {
		transactionDAO.loadMerchantByName(paginationBean, merchantName, date, txnType);
	}

	public void loadUmMerchantByName(final PaginationBean<ForSettlement> paginationBean, final String merchantName,
			final String date, String txnType) {
		transactionDAO.loadUmMerchantByName(paginationBean, merchantName, date, txnType);
	}

	public ForSettlement loadMerchantName(String merchantName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Merchant loadMerchantbymid(final String mid) {
		/* System.out.print("loadmobilemerchant: "+mid); */
		MID mid1 = transactionDAO.loadMid(mid);

		return transactionDAO.loadMerchantbyid(mid1);
	}

	public List<AgentVolumeData> agentVolumeData1(String agentName) {
		System.out.println("Inside  agentVolumeData1");

		return transactionDAO.agentVolumeData1(agentName);
	}
	/*
	 * @javax.transaction.Transactional public void
	 * loadLocationName(PaginationBean<ForSettlement> paginationBean,final String
	 * Location) { transactionDAO.loadMerchantByLocation(paginationBean,Location);
	 * // TODO Auto-generated method stub
	 *
	 * }
	 */

	// to display list of all transactions
	// @javax.transaction.Transactional
	public void listAllTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	public void listAllUMTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllUmTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	public void searchAllUmEzywireTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchAllUmEzywireTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	public void listAllUmEzywireTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllUmEzywireTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	public void listAllForsettlementTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllForsettlementTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	public void listFPXTransaction(PaginationBean<FpxTransaction> paginationBean, String fromDate, String toDate,
			String VALUE, String TXNTYPE) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		// transactionDAO.listFPXTransaction(paginationBean, from, to);
		transactionDAO.listFPXTransaction(paginationBean, from, to, VALUE, TXNTYPE);
	}

	// search

	public void listFPXTransactionsearch(PaginationBean<FpxTransaction> paginationBean, String fromDate,
			String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.listFPXTransactionsearch(paginationBean, from, to);
	}

	public void listFPXTransactionsearchexport(PaginationBean<FpxTransaction> paginationBean, String fromDate,
			String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.listFPXTransactionsearchexport(paginationBean, from, to);
	}

	public void listFPXTransactionByMid(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String fromDate, String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.listFPXTransactionByMid(paginationBean, midDetails, from, to);
	}

	public void listFPXTransactionByMidexport(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String fromDate, String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.listFPXTransactionByMidexport(paginationBean, midDetails, from, to);
	}

	public void listMerchantFPXTransactionByMid(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String fromDate, String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.listMerchantFPXTransactionByMid(paginationBean, midDetails, from, to);
	}

	// search

	public void listMerchantFPXTransactionByMidsearch(PaginationBean<FpxTransaction> paginationBean,
			Merchant midDetails, String fromDate, String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.listMerchantFPXTransactionByMidsearch(paginationBean, midDetails, from, to);
	}

	public void searchAllForsettlementTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchAllForsettlementTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	// loading tid by pk

	public TID loadTidByPk(final Long id) {
		TID tid = transactionDAO.loadEntityByKey(TID.class, id);
		if (tid == null) {
			throw new RuntimeException("TID Not found. ID:: " + id);
		}
		return tid;
	}

	// loading mid based on pk of mid id
	public MID loadMIDByPk(final long id) {
		MID mid = transactionDAO.loadEntityByKey(MID.class, id);
		if (mid == null) {
			throw new RuntimeException("MID Not found. ID:: " + id);
		}
		return mid;
	}

	// to view list of transactions based on mobileuser

	// @javax.transaction.Transactional
	public void listTransactionTID(final PaginationBean<Transaction> paginationBean, final Merchant merchant) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant != null) {

			@SuppressWarnings("rawtypes")
			List allMobileUsers = mobileuserDAO.listMobileUsers(merchant);

			if ((allMobileUsers != null) && (allMobileUsers.size() > 0)) {

				criterionList.add(Restrictions.in("mobileUser", allMobileUsers));

			}

		}

		transactionDAO.listTransactionTIDUsers(paginationBean, criterionList);
	}

	// to view list of transactions based on mobileuser
	// @javax.transaction.Transactional
	public List<ForSettlement> listTransactionTID(final PaginationBean<ForSettlement> paginationBean,
			final String mid) {

		return transactionDAO.listTransaction(paginationBean, mid);
		// transactionDAO.listTransactionTIDUsers(paginationBean,
		// criterionList);
	}

	// to display list of transactions related to a TID

	public Transaction listTransactionsByTid(final PaginationBean<TransactionDTO> paginationBean, final String tid) {
		/*
		 * ArrayList < Criterion > criterionList = new ArrayList < Criterion > ( ) ;
		 * criterionList . add ( Restrictions . eq ( "tid" , tid ) ) ; Transaction trxn
		 * = new Transaction ( ) ;
		 *
		 * return transactionDAO . listTransactionsByTid ( paginationBean ) , tid ) ;
		 *
		 * / / TODO Auto - generated method stub
		 */
		return null;
	}

	// OLD CODE
	// @javax.transaction.Transactional
	/*
	 * public void getForSettlement(final PaginationBean<ForSettlement>
	 * paginationBean, final Merchant merchant) {
	 * //System.out.println("testttttt getForSettlement"); ArrayList<Criterion>
	 * criterionList = new ArrayList<Criterion>();
	 *
	 * criterionList.add(Restrictions.eq("mid",
	 * merchant.getMid().getMid().toString()));
	 * transactionDAO.getForSettlement(paginationBean, criterionList); }
	 */
	// NEW CODE
	public void getForSettlement(final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		// System.out.println("mid: " + merchant.getMid().getMid());

		/*
		 * Disjunction orExp=Restrictions.disjunction();
		 * orExp.add(Restrictions.eq("mid", merchant.getMid().getMid()));
		 * orExp.add(Restrictions.eq("motoMid", merchant.getMid().getMid()));
		 * orExp.add(Restrictions.eq("ezyrecMid", merchant.getMid().getEzyrecMid()));
		 * orExp.add(Restrictions.eq("ezywayMid", merchant.getMid().getEzywayMid()));
		 * orExp.add(Restrictions.eq("ezypassMid", merchant.getMid().getEzypassMid()));
		 */
		/*
		 * criterionList.add(Restrictions.eq("mid", merchant.getMid().getMid()
		 * .toString()));
		 */

		transactionDAO.getAllTransactionbyMerchant(paginationBean, criterionList, merchant);
		// transactionDAO.getForSettlement(paginationBean, criterionList);
	}

	public void getUMForSettlement(final PaginationBean<UMEzyway> paginationBean, final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		transactionDAO.getAllUMTransactionbyMerchant(paginationBean, criterionList, merchant);

	}

	// @javax.transaction.Transactional
	public void getCashTransForSettlement(final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant.getMid().getMid() != null) {
			System.out.println("mid: " + merchant.getMid().getMid().toString());

		}
		if (merchant.getMid().getMotoMid() != null) {
			System.out.println("moto mid: " + merchant.getMid().getMotoMid().toString());

		}
		if (merchant.getMid().getEzypassMid() != null) {
			System.out.println("ezypass mid: " + merchant.getMid().getEzypassMid().toString());

		}

		criterionList.add(Restrictions.in("mid", new String[] { merchant.getMid().getMid(),
				merchant.getMid().getMotoMid(), merchant.getMid().getEzypassMid() }));

		criterionList.add(Restrictions.eq("txnType", "CASH"));
		transactionDAO.getForSettlement(paginationBean, criterionList);
		// System.out.println(paginationBean.getItemList().size());
		System.out.println("getCashForSettlement");
	}

	public void getBoostTransForSettlement(final PaginationBean<ForSettlement> paginationBean,
			final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant.getMid().getMid() != null) {
			System.out.println("mid: " + merchant.getMid().getMid().toString());

		}
		if (merchant.getMid().getMotoMid() != null) {
			System.out.println("moto mid: " + merchant.getMid().getMotoMid().toString());

		}
		if (merchant.getMid().getEzypassMid() != null) {
			System.out.println("ezypass mid: " + merchant.getMid().getEzypassMid().toString());

		}

		criterionList.add(Restrictions.in("mid", new String[] { merchant.getMid().getMid(),
				merchant.getMid().getMotoMid(), merchant.getMid().getEzypassMid() }));

		criterionList.add(Restrictions.eq("txnType", "BOOST"));
		criterionList.add(Restrictions.in("status", new String[] { "BP", "BPC", "BPA", "BPS" }));
		transactionDAO.getForSettlement(paginationBean, criterionList);
		// System.out.println(paginationBean.getItemList().size());
		System.out.println("getboostForSettlement");
	}

	public void getMotoTransForSettlement(final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant.getMid().getMotoMid() != null) {
			System.out.println("moto mid: " + merchant.getMid().getMotoMid());
		}

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getMotoMid()));

		// criterionList.add(Restrictions.eq("txnType", "MOTO"));
		// criterionList.add(Restrictions.eq("txnType", "RECURRING"));
		criterionList.add(Restrictions.in("txnType", new String[] { "MOTO", "RECURRING" }));
		criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
		transactionDAO.getForSettlement(paginationBean, criterionList);
		// System.out.println(paginationBean.getItemList().size());
		System.out.println("getmotoForSettlement");
	}

	public void getEzyWayTransForSettlement(final PaginationBean<ForSettlement> paginationBean,
			final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant.getMid().getEzywayMid() != null) {
			System.out.println("Ezyway mid: " + merchant.getMid().getEzywayMid());
		}

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzywayMid()));

		// criterionList.add(Restrictions.eq("txnType", "MOTO"));
		// criterionList.add(Restrictions.eq("txnType", "RECURRING"));
		// criterionList.add(Restrictions.in("txnType", new String[] { "MOTO",
		// "RECURRING"}));
		criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
		transactionDAO.getForSettlement(paginationBean, criterionList);
		// System.out.println(paginationBean.getItemList().size());
		System.out.println("getEzywayForSettlement");
	}

	public void searchTransactionForSettlement(String fromDate, String toDate,
			final PaginationBean<ForSettlement> paginationBean, final Merchant merchant, String txnType) {
		System.out.println("TXN TYPE: " + txnType);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (txnType.equals("EZYWAY")) {
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzywayMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			criterionList.add(Restrictions.eq("txnType", "EZYWAY"));
			System.out.println(merchant.getMid().getEzywayMid());
		} else if (txnType.equals("EZYREC")) {
			System.out.println("ezyrec trnasction: ");
			criterionList.add(Restrictions.eq("txnType", "EZYREC"));
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzyrecMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			System.out.println(merchant.getMid().getEzyrecMid());
		} else if (txnType.equals("EZYPASS")) {
			criterionList.add(Restrictions.eq("txnType", "EZYPASS"));
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzypassMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			System.out.println(merchant.getMid().getEzypassMid());
		} else if (txnType.equals("MOTO")) {
			criterionList.add(Restrictions.eq("txnType", "MOTO"));
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getMotoMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			System.out.println(merchant.getMid().getMotoMid());
		} else if (txnType.equals("BOOST")) {
			criterionList.add(
					Restrictions.in("mid", new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
							merchant.getMid().getEzypassMid(), merchant.getMid().getEzyrecMid() }));
			criterionList.add(Restrictions.eq("txnType", "BOOST"));
			criterionList.add(Restrictions.in("status", new String[] { "BP", "BPC", "BPA", "BPS" }));
		} else if (txnType.equals("CASH")) {
			criterionList.add(
					Restrictions.in("mid", new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
							merchant.getMid().getEzypassMid(), merchant.getMid().getEzyrecMid() }));

			if (fromDate != null && toDate != null) {
				criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
			}
			String status = "CT";
			criterionList.add(Restrictions.eq("txnType", "CASH"));
			criterionList.add(Restrictions.in("status", new String[] { "CT", "CV" }));

		}

		transactionDAO.getForSettlement(paginationBean, criterionList);
		// System.out.println(paginationBean.getItemList().size());
		System.out.println("getTransactionForSettlement");
	}

	public void getTransactionForSettlement(final PaginationBean<ForSettlement> paginationBean, final Merchant merchant,
			String txnType) {
		System.out.println("Get Trans -: " + txnType);
		String fromDate = null, toDate = null;

		Date date = new Date();

		// Date fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new
		// SimpleDateFormat("dd/MM/yyyy").parse(date));

		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		// Date date = new Date();

		int year = calendar.getWeekYear();
		// int year=2017;
		long mon = date.getMonth() + 1;
		int day = date.getDate() + 1;
		int daybefore = date.getDate() - 20;
		String fromDateToSearch = null;
		String toDateToSearch = null;
		String dateorg2 = day + "/" + mon + "/" + year;
		String dateorg1 = daybefore + "/" + mon + "/" + year;
		logger.info("date to find: " + dateorg1 + " " + dateorg2);
		try {
			toDateToSearch = new SimpleDateFormat("yyyy-MM-dd")
					.format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
			fromDateToSearch = new SimpleDateFormat("yyyy-MM-dd")
					.format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("check date and year : " + fromDateToSearch + "  " + toDateToSearch);

		logger.info("TxnType : " + txnType + "::mid::" + merchant.getMid().getMid() + "::getMotoMid::"
				+ merchant.getMid().getMotoMid() + "::getMotoMid::" + merchant.getMid().getMotoMid() + "::getMotoMid::"
				+ merchant.getMid().getMotoMid());

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (txnType.equals("EZYWAY")) {
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzywayMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			criterionList.add(Restrictions.eq("txnType", "EZYWAY"));
			System.out.println(merchant.getMid().getEzywayMid());
		} else if (txnType.equals("EZYREC")) {
			criterionList.add(Restrictions.eq("txnType", "EZYREC"));
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzyrecMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			System.out.println(merchant.getMid().getEzyrecMid());
		} else if (txnType.equals("RECPLUS")) {
			logger.info(txnType + " mid: " + merchant.getMid().getEzyrecMid());
			criterionList.add(Restrictions.eq("txnType", "RECPLUS"));
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzyrecMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			System.out.println(merchant.getMid().getEzyrecMid());
		} else if (txnType.equals("EZYPASS")) {
			criterionList.add(Restrictions.eq("txnType", "EZYPASS"));
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzypassMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			System.out.println(merchant.getMid().getEzypassMid());
		} else if (txnType.equals("MOTO")) {
			criterionList.add(Restrictions.eq("txnType", "MOTO"));
			criterionList.add(Restrictions.eq("mid", merchant.getMid().getMotoMid()));
			criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
			System.out.println(merchant.getMid().getMotoMid());
		} else if (txnType.equals("BOOST")) {
			criterionList.add(Restrictions.in("mid",
					new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
							merchant.getMid().getEzypassMid(), merchant.getMid().getEzywayMid(),
							merchant.getMid().getEzyrecMid(), merchant.getMid().getUmMid(),
							merchant.getMid().getUmMotoMid(), merchant.getMid().getUmEzywayMid(),
							merchant.getMid().getUmEzyrecMid(), merchant.getMid().getUmEzypassMid() }));
			criterionList.add(Restrictions.eq("txnType", "BOOST"));
			criterionList.add(Restrictions.in("status", new String[] { "BPC", "BPA", "BPS" }));
		} else if (txnType.equals("CASH")) {
			criterionList.add(Restrictions.in("mid",
					new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
							merchant.getMid().getEzypassMid(), merchant.getMid().getEzywayMid(),
							merchant.getMid().getEzyrecMid(), merchant.getMid().getUmMid(),
							merchant.getMid().getUmMotoMid(), merchant.getMid().getUmEzywayMid(),
							merchant.getMid().getUmEzyrecMid(), merchant.getMid().getUmEzypassMid() }));
			criterionList.add(Restrictions.eq("txnType", "CASH"));
		} else if (txnType.equals("GRABPAY")) {
			logger.info(" TXN_TYPE: " + txnType);
			logger.info("mid: " + merchant.getMid().getMid() + " " + merchant.getMid().getMotoMid() + " "
					+ merchant.getMid().getEzypassMid() + " " + merchant.getMid().getEzyrecMid());
			criterionList.add(Restrictions.in("mid",
					new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
							merchant.getMid().getEzypassMid(), merchant.getMid().getEzywayMid(),
							merchant.getMid().getEzyrecMid(), merchant.getMid().getUmMid(),
							merchant.getMid().getUmMotoMid(), merchant.getMid().getUmEzywayMid(),
							merchant.getMid().getUmEzyrecMid(), merchant.getMid().getUmEzypassMid() }));
			criterionList.add(Restrictions.eq("txnType", "GRABPAY"));
			// criterionList.add(Restrictions.in("status", new String[] { "GPC",
			// "GPS","GRF", "GBC","GPF","GPP" }));
		}

		criterionList.add(Restrictions.between("timeStamp", fromDateToSearch, toDateToSearch));

		transactionDAO.getForSettlement(paginationBean, criterionList);
		// System.out.println(paginationBean.getItemList().size());
		// System.out.println("getTransactionForSettlement");
	}

	public void getEzypassTransForSettlement(final PaginationBean<ForSettlement> paginationBean,
			final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant.getMid().getEzypassMid() != null) {
			System.out.println("moto mid: " + merchant.getMid().getEzypassMid());
		}

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzypassMid()));
		// String[] status={"BP","BPC","BPA","BPS"};
		criterionList.add(Restrictions.eq("txnType", "EZYPASS"));
		criterionList.add(Restrictions.in("status", new String[] { "A", "C", "S" }));
		transactionDAO.getForSettlement(paginationBean, criterionList);
		// System.out.println(paginationBean.getItemList().size());
		System.out.println("getezypassForSettlement");
	}

	public void getCardTransForSettlement(PaginationBean<forsettlement> paginationBean, final Merchant merchant,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

//		transactionDAO.getCardTransForSettlement(paginationBean, merchant
//				.getMid().getMid());

		transactionDAO.getCardTransForSettlement(paginationBean, merchant, txnType);

	}

	public void getMobiliteEzylinkTxn(PaginationBean<UMEzyway> paginationBean, final MobiLiteMerchant merchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		// transactionDAO.getMobiliteEzylinkTxn(paginationBean, merchant);

	}

	// new for all card Transaction list - 21-02-2019

	public void getCardTransactionForSettlement(PaginationBean<ForSettlement> paginationBean, final Merchant merchant,
			String txnType, String date, String date1, String status) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		/*
		 * String mid=null; if(txnType.equals("EZYWIRE")) {
		 * mid=merchant.getMid().getMid(); }else if(txnType.equals("EZYMOTO")){
		 * mid=merchant.getMid().getMotoMid(); }else if(txnType.equals("EZYREC")){
		 * mid=merchant.getMid().getEzyrecMid(); }else if(txnType.equals("EZYPASS")){
		 * mid=merchant.getMid().getEzypassMid(); }else if(txnType.equals("EZYWAY")){
		 * mid=merchant.getMid().getEzywayMid(); }
		 */
		transactionDAO.getCardTransactionForSettlement(paginationBean, merchant, txnType, date, date1, status);

	}

	public void getUMMidTransForSettlement(PaginationBean<ForSettlement> paginationBean, final String mid,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		transactionDAO.getUMMidTransForSettlement(paginationBean, mid, txnType);

	}

	public String getMonth(int m) {

		// System.out.println(" Data :"+m);
		String mon = "";
		switch (m) {
		case 1:
			mon = "JAN";
			break;
		case 2:
			mon = "FEB";
			break;
		case 3:
			mon = "MAR";
			break;
		case 4:
			mon = "APR";
			break;
		case 5:
			mon = "MAY";
			break;
		case 6:
			mon = "JUN";
			break;
		case 7:
			mon = "JUL";
			break;
		case 8:
			mon = "AUG";
			break;
		case 9:
			mon = "SEP";
			break;
		case 10:
			mon = "OCT";
			break;
		case 11:
			mon = "NOV";
			break;
		case 12:
			mon = "DEC";
			break;

		default:
			mon = "";
			break;
		}

		return mon;

	}

	private static String getStringOrDefault(Object value, String defaultValue) {
		if (value == null || value.toString().trim().isEmpty())
			return defaultValue;
		return value.toString();
	}

	private static List<Integer> getAllMonth(int month) {
		System.out.println("month debug... " + month);
		List<Integer> listMonth = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			if (month == 0) {
				System.out.println("debug 0");
				listMonth.add(12);
			} else if (month == -1) {
				System.out.println("debug -1");
				listMonth.add(11);
			} else if (month == -2) {
				System.out.println("debug -2");
				listMonth.add(10);
			} else {
				System.out.println("debug else ");
				listMonth.add(month);
			}
			month--;
		}
		return listMonth;
	}

	/*
	 * public void getCardNewTransForSettlement( PaginationBean<ForSettlement>
	 * paginationBean, final Merchant merchant) { ArrayList<Criterion> criterionList
	 * = new ArrayList<Criterion>();
	 *
	 * List<Object[]>resultList=
	 * (List<Object[]>)session.createCriteria(ForSettlement.class, "f")
	 * .add(Restrictions.eq("aliasOfTableA.columnAA", "AAA"))
	 * .add(Restrictions.eq("f.MID", mid)) .add(Restrictions.eq("f.TXN_TYPE", mid))
	 * .add(Restrictions.in("f.STATUS", ('A','S','C','R','P')))
	 * .createCriteria("f.TRX_ID" , "t")
	 *
	 * .add(Restrictions.eq("aliasOfTableB.columnBB", "BBB"))
	 *
	 *
	 * .setProjection( Projections.projectionList() .add(
	 * Projections.property(" f.TIME_STAMP") ) .add( Projections.property("f.TIME")
	 * ) .add( Projections.property("f.STATUS") ) .add(
	 * Projections.property("f.STAN") ) .add( Projections.property("f.AMOUNT") )
	 * .add( Projections.property("f.LOCATION") ) .add(
	 * Projections.property("f.MID") ) .add( Projections.property("f.TRX_ID") )
	 * .add( Projections.property("f.TID") ) .add(
	 * Projections.property("f.TXN_TYPE") ) .add(
	 * Projections.property("t.MASKED_PAN"))
	 *
	 * ).list();
	 *
	 *
	 * transactionDAO.getCardTransForSettlement(paginationBean, merchant
	 * .getMid().getMid());
	 *
	 * }
	 */

	// @javax.transaction.Transactional
	public void getForSettlementnonmerchant(final PaginationBean<ForSettlement> paginationBean,
			final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		System.out.println("inside getforsettlementnonmerchant() " + merchant.getMid().getMid());
		criterionList.add(Restrictions.eq("mid", merchant.getMid().getMid().toString()));
		transactionDAO.getForSettlementnonmerchant(paginationBean, criterionList);
	}

	public void searchForSettlementMoto(final String fromDate, final String toDate,
			final PaginationBean<forsettlement> paginationBean, final Merchant merchant) {

		transactionDAO.searchForSettlementMotoByTid(paginationBean, fromDate, toDate, merchant);
	}

	public void searchUMForSettlementMoto(final String fromDate, final String toDate, final String tid,
			final String status, final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getMotoMid()));

		if (fromDate != null && toDate != null) {
			// System.out.println("Date!!!!!!!!!!!!!!!!!!"+fromDate+" "+toDate);
			// criterionList.add(Restrictions.between("date", fromDate,
			// toDate));
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			/* System.out.println("tid !!!!!!!!!!!!!!!"+tid); */
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {
			/* System.out.println("status !!!!!!!!!!!!!!!!!!!!!"+status); */
			criterionList.add(Restrictions.eq("status", status));
		}

		/*
		 * if(devId != null){ criterionList.add(Restrictions.eq("devId", devId)); }
		 */
		// transactionDAO.searchForSettlement(fromDate ,toDate ,tid, status,
		// paginationBean,criterionList);

		/*
		 * transactionDAO.getMotoList(paginationBean, criterionList, fromDate, toDate,
		 * status, merchant);
		 */
		transactionDAO.searchForSettlementMoto(paginationBean, criterionList, fromDate, toDate, status, merchant);
	}

	public void searchForSettlementEzyWay(final String fromDate, final String toDate,
			final PaginationBean<forsettlement> paginationBean, final Merchant merchant) {

		transactionDAO.searchForSettlementEzyWay(paginationBean, fromDate, toDate, merchant);
	}

	public void searchForSettlementEzyPass(final String fromDate, final String toDate, final String tid,
			final String status, final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getEzypassMid()));

		if (fromDate != null && toDate != null) {

			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			/* System.out.println("tid !!!!!!!!!!!!!!!"+tid); */
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {
			/* System.out.println("status !!!!!!!!!!!!!!!!!!!!!"+status); */
			criterionList.add(Restrictions.eq("status", status));
		}

		transactionDAO.searchForSettlementEzyPass(paginationBean, criterionList, fromDate, toDate, status, tid,
				merchant);
	}

	public void searchForSettlementEzyRec(final String fromDate, final String toDate,
			final PaginationBean<forsettlement> paginationBean, final Merchant merchant) {

		transactionDAO.searchForSettlementEzyRec(paginationBean, fromDate, toDate, merchant);
	}

	public void searchForSettlementEzyRecplus(final String fromDate, final String toDate,
			final PaginationBean<forsettlement> paginationBean, final Merchant merchant) {

		transactionDAO.searchForSettlementEzyRecplus(paginationBean, fromDate, toDate, merchant);
	}

	public void searchBoostForSettlement(final String fromDate, final String toDate, final String tid,
			final String status, final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant.getMid().getMid() != null) {
			System.out.println("mid: " + merchant.getMid().getMid().toString());

		}
		if (merchant.getMid().getMotoMid() != null) {
			System.out.println("moto mid: " + merchant.getMid().getMotoMid().toString());

		}
		if (merchant.getMid().getEzypassMid() != null) {
			System.out.println("ezypass mid: " + merchant.getMid().getEzypassMid().toString());

		}

		criterionList.add(Restrictions.in("mid", new String[] { merchant.getMid().getMid(),
				merchant.getMid().getMotoMid(), merchant.getMid().getEzypassMid() }));

		if (fromDate != null && toDate != null) {
			// System.out.println("Date!!!!!!!!!!!!!!!!!!"+fromDate+" "+toDate);
			// criterionList.add(Restrictions.between("date", fromDate,
			// toDate));
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			/* System.out.println("tid !!!!!!!!!!!!!!!"+tid); */
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {
			/* System.out.println("status !!!!!!!!!!!!!!!!!!!!!"+status); */
			criterionList.add(Restrictions.eq("status", status));
		}

		criterionList.add(Restrictions.eq("txnType", "BOOST"));
		/*
		 * if(devId != null){ criterionList.add(Restrictions.eq("devId", devId)); }
		 */
		transactionDAO.searchForSettlement(fromDate, toDate, tid, status, paginationBean, criterionList);
		/*
		 * transactionDAO .searchForSettlement(paginationBean, criterionList, fromDate,
		 * toDate, status);
		 */
	}

	// @javax.transaction.Transactional
	public void searchForSettlement(final String fromDate, final String toDate, final String tid, final String status,
			final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (fromDate != null && toDate != null) {
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			/* System.out.println("tid !!!!!!!!!!!!!!!"+tid); */
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {
			/* System.out.println("status !!!!!!!!!!!!!!!!!!!!!"+status); */
			criterionList.add(Restrictions.eq("status", status));
		}

		/*
		 * if(devId != null){ criterionList.add(Restrictions.eq("devId", devId)); }
		 */
		// transactionDAO.searchForSettlement(fromDate ,toDate ,tid, status,
		// paginationBean,criterionList);
		transactionDAO.searchForSettlement1(paginationBean, criterionList, fromDate, toDate, status, tid, merchant);

		/*
		 * transactionDAO .searchcardDetails(fromDate, toDate, tid, status,
		 * paginationBean, merchant.getMid().getMid());
		 */
	}

	public void searchCardForSettlement(final String fromDate, final String toDate,
			final PaginationBean<forsettlement> paginationBean, final Merchant merchant) {

		transactionDAO.searchAllForSettlement(paginationBean, fromDate, toDate, merchant);

	}

	public void searchCardUMForSettlement(final String fromDate, final String toDate, final String tid,
			final String status, final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		/*
		 * criterionList.add(Restrictions.eq("mid", merchant.getMid().getMid()
		 * .toString()));
		 */

		if (fromDate != null && toDate != null) {
			// System.out.println("Date!!!!!!!!!!!!!!!!!!"+fromDate+" "+toDate);
			// criterionList.add(Restrictions.between("date", fromDate,
			// toDate));
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			/* System.out.println("tid !!!!!!!!!!!!!!!"+tid); */
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {
			/* System.out.println("status !!!!!!!!!!!!!!!!!!!!!"+status); */
			criterionList.add(Restrictions.eq("status", status));
		}

		transactionDAO.searchAllForSettlement(paginationBean, criterionList, fromDate, toDate, status, merchant);

		/*
		 * transactionDAO.searchAllForSettlement(paginationBean, criterionList,
		 * fromDate, toDate, status,tid, merchant);
		 */

	}

	public void searchUMForSettlement(final String fromDate, final String toDate, final String tid, final String status,
			final PaginationBean<UMEzyway> paginationBean, final Merchant merchant, final String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchUMForSettlement(paginationBean, criterionList, fromDate, toDate, status, merchant,
				txnType);

	}

	/*
	 * public void searchCardForSettlement(final String fromDate, final String
	 * toDate, final String tid, final String status, final
	 * PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
	 *
	 * transactionDAO.searchcardDetails(fromDate, toDate, tid, status,
	 * paginationBean, merchant); }
	 */

	public void searchUMCardForSettlement(final String fromDate, final String toDate, final String tid,
			final String status, final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {

		transactionDAO.searchcardDetails(fromDate, toDate, tid, status, paginationBean, merchant);
	}

	// @javax.transaction.Transactional
	public void searchForSettlementnew(final String fromDate, final String toDate,
			final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getMid().toString()));

		if (fromDate != null && toDate != null) {

			// criterionList.add(Restrictions.between("date", fromDate,
			// toDate));
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}

		transactionDAO.searchForSettlementnew(fromDate, toDate, paginationBean, criterionList);
	}

	public void searchForSettlementcash(final String fromDate, final String toDate,
			final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		/*
		 * criterionList.add(Restrictions.eq("mid", merchant.getMid().getMid()
		 * .toString()));
		 */

		if (merchant.getMid().getMid() != null) {
			System.out.println("mid: " + merchant.getMid().getMid().toString());

		}
		if (merchant.getMid().getMotoMid() != null) {
			System.out.println("moto mid: " + merchant.getMid().getMotoMid().toString());

		}
		if (merchant.getMid().getEzypassMid() != null) {
			System.out.println("ezypass mid: " + merchant.getMid().getEzypassMid().toString());

		}

		criterionList.add(Restrictions.in("mid",
				new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
						merchant.getMid().getEzypassMid(), merchant.getMid().getEzyrecMid(),
						merchant.getMid().getEzywayMid() }));

		if (fromDate != null && toDate != null) {

			// criterionList.add(Restrictions.between("date", fromDate,
			// toDate));
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		String status = "CT";
		// criterionList.add(Restrictions.eq("status", status));

		criterionList.add(Restrictions.eq("txnType", "CASH"));
		criterionList.add(Restrictions.in("status", new String[] { "CT", "CV" }));

		transactionDAO.searchForSettlementcash(fromDate, toDate, paginationBean, criterionList);
	}

	/*
	 * public void searchForSettlementcard(final String fromDate, final String
	 * toDate, final String tid, final String status, final
	 * PaginationBean<ForSettlement> paginationBean, final Merchant merchant) { //
	 * System.out.println("In Service toDate and fromDate :" + toDate + // ": and :"
	 * + fromDate + ": TID :" + tid + ": Status:" + status);
	 *
	 * ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
	 *
	 * criterionList.add(Restrictions.eq("mid", merchant.getMid().getMid()
	 * .toString()));
	 *
	 * if (fromDate != null && toDate != null) { //
	 * System.out.println("Date!!!!!!!!!!!!!!!!!!"+fromDate+"  "+toDate); //
	 * criterionList.add(Restrictions.between("date", fromDate, // toDate));
	 * criterionList.add(Restrictions.between("timeStamp", fromDate, toDate)); } if
	 * (tid != null && (!tid.equals(""))) { System.out.println("tid !!!!!!!!!!!!!!!"
	 * + tid); criterionList.add(Restrictions.eq("tid", tid)); } if (status != null
	 * && (!status.equals(""))) { System.out.println("status !!!!!!!!!!!!!!!!!!!!!"
	 * + status); criterionList.add(Restrictions.eq("status", status)); }
	 *
	 *
	 * if(devId != null){ criterionList.add(Restrictions.eq("devId", devId)); }
	 *
	 *
	 * transactionDAO.searchForSettlementcard(fromDate, toDate, tid, status,
	 * paginationBean, criterionList);
	 *
	 * }
	 */

	public void searchForSettlementBoost(final String fromDate, final String toDate, final String tid,
			final String status, final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (merchant.getMid().getMid() != null) {
			System.out.println("mid: " + merchant.getMid().getMid().toString());

		}
		if (merchant.getMid().getMotoMid() != null) {
			System.out.println("moto mid: " + merchant.getMid().getMotoMid().toString());

		}
		if (merchant.getMid().getEzywayMid() != null) {
			System.out.println("ezypass mid: " + merchant.getMid().getEzypassMid().toString());

		}
		if (merchant.getMid().getUmMid() != null) {
			System.out.println("UMMID mid: " + merchant.getMid().getUmMid().toString());

		}
		if (merchant.getMid().getUmMotoMid() != null) {
			System.out.println("ummoto mid: " + merchant.getMid().getUmMotoMid().toString());

		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			System.out.println("umezyway mid: " + merchant.getMid().getUmEzywayMid().toString());

		}

		criterionList.add(Restrictions.in("mid",
				new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
						merchant.getMid().getEzypassMid(), merchant.getMid().getUmMid(),
						merchant.getMid().getUmMotoMid(), merchant.getMid().getUmEzywayMid(),
						merchant.getMid().getEzyrecMid(), merchant.getMid().getEzywayMid() }));
		if (fromDate != null && toDate != null) {
			// System.out.println("Date!!!!!!!!!!!!!!!!!!"+fromDate+" "+toDate);
			// criterionList.add(Restrictions.between("date", fromDate,
			// toDate));
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			System.out.println("tid !!!!!!!!!!!!!!!" + tid);
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {
			System.out.println("status !!!!!!!!!!!!!!!!!!!!!" + status);
			criterionList.add(Restrictions.eq("status", status));
		}

		/*
		 * if(devId != null){ criterionList.add(Restrictions.eq("devId", devId)); }
		 */

		transactionDAO.boostTransactionbyMerchant(fromDate, toDate, tid, status, paginationBean, criterionList,
				merchant);

	}

	// grabpaysearch
	public void searchForGrabpay(final String fromDate, final String toDate, final String tid, final String status,
			final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getGpayMid()));

		if (fromDate != null && toDate != null) {

			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			/* System.out.println("tid !!!!!!!!!!!!!!!"+tid); */
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {
			/* System.out.println("status !!!!!!!!!!!!!!!!!!!!!"+status); */
			criterionList.add(Restrictions.eq("status", status));
		}

		transactionDAO.searchForSettlementGrabpay(paginationBean, criterionList,

				fromDate, toDate, status, tid, merchant);
	}

	// @javax.transaction.Transactional
	public void searchnonmerchantForSettlement(final String fromDate, final String toDate,
			final PaginationBean<ForSettlement> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		criterionList.add(Restrictions.eq("mid", merchant.getMid().getMid().toString()));

		if (fromDate != null && toDate != null) {
			// System.out.println("Date!!!!!!!!!!!!!!!!!!"+fromDate+" "+toDate);
			// criterionList.add(Restrictions.between("date", fromDate,
			// toDate));
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}

		/*
		 * if(devId != null){ criterionList.add(Restrictions.eq("devId", devId)); }
		 */
		transactionDAO.searchnonmerchantForSettlement(fromDate, toDate, paginationBean, criterionList);
	}

	public MobileUser getMobileUserByMotoTid(final String motoTid) {
		return transactionDAO.getMobileUserByMotoTid(motoTid);
	}

	public MobileUser getMobileUserByAuthTid(final String motoTid) {
		return transactionDAO.getMobileUserByAuthTid(motoTid);
	}

	public List<MobileUser> getMobileUser(final String motoTid) {
		return transactionDAO.getMobileUser(motoTid);
	}

	// @javax.transaction.Transactional
	public List<TerminalDetails> getTerminalDetails(final String mid, String motoMid, String ezyrecMid) {
		return transactionDAO.getTerminalDetails(mid, motoMid, ezyrecMid);
	}

	public List<TerminalDetails> getTerminalDetails(final String mid) {
		return transactionDAO.getTerminalDetails(mid);
	}

	public List<TerminalDetails> getTerminalDetails(final Merchant merchant) {
		return transactionDAO.getTerminalDetails(merchant);
	}

	public List<TerminalDetails> getEzywireTerminalDetails(final Merchant merchant) {
		return transactionDAO.getEzywireTerminalDetails(merchant);
	}

	public List<TerminalDetails> getGpayTerminalDetails(final Merchant merchant) {
		return transactionDAO.getGpayTerminalDetails(merchant);
	}

	// @javax.transaction.Transactional
	public TransactionResponse loadTransactionResponse(String trx_id) {
		return transactionDAO.loadTransactionResponse(trx_id);
	}

	// @javax.transaction.Transactional
	public TransactionRequest loadTransactionRequest(String trx_id) {
		return transactionDAO.loadTransactionRequest(trx_id);
	}

	// @javax.transaction.Transactional
	public ForSettlement getForSettlement(String trxId) {
		return transactionDAO.getForSettlement(trxId);
	}

	// @javax.transaction.Transactional
	public AgentResponseDTO loadAgentName(String agentName) {
		return transactionDAO.loadAgentByName(agentName);
	}

	// @javax.transaction.Transactional
	public void loadTerminalName(final PaginationBean<TerminalDTO> paginationBean, String businessName) {
		transactionDAO.loadTerminalByName(paginationBean, businessName);
	}

	// to display list of all transactions
	// @javax.transaction.Transactional
	public List<ForSettlement> exportAllTransaction(final String data, final String data1, String txnType) {
		// System.out.println("Inside listAllTransaction");
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		return transactionDAO.exportAllTransaction(criterionList, data, data1, txnType);
	}

	public List<ForSettlement> exportAllUmTransaction(final String data, final String data1, String txnType) {
		// System.out.println("Inside listAllTransaction");
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		return transactionDAO.exportAllUmTransaction(criterionList, data, data1, txnType);
	}

	// @javax.transaction.Transactional
	public void listAllTransactionDetails(final PaginationBean<ForSettlement> paginationBean, final String date1,
			final String date2, final String status) {
		// System.out.println("Inside listAllTransaction:::::"+date1+year1+"
		// "+date2+year2+" Status :"+status+"TEST");
		// System.out.println("Inside listAllTransaction:::::"+date1+" "+date2+" Status
		// :"+status+"TEST");
		// String data= null;

		System.out.println("inside listAllTransactionDetails service..");
		/* logger.info("inside listAllTransactionDetails service.."); */
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (date1 != null && date2 != null) {
			// System.out.println(" Data date :"+date1+ date2);
			criterionList.add(Restrictions.between("timeStamp", date1, date2));
		}
		/*
		 * if(date1 != null && date2 != null){ System.out.println(" Data date :"+date1+
		 * date2); criterionList.add(Restrictions.between("date", date1, date2)); }
		 */
		/*
		 * if(year1 != null && year2 != null){ System.out.println(" Data year:"+year1 +
		 * year2); criterionList.add(Restrictions.between("txnYear", year1, year2)); }
		 */

		if (status != null && !status.isEmpty()) {
			// System.out.println(" Data status:"+status);
			criterionList.add(Restrictions.eq("status", status));
		}
		// System.out.println(" Data :"+criterionList.size());
		/*
		 * if(criterionList.size() < 1){ System.out.println(" criterionList null");
		 * transactionDAO.listAllTransactionDetails(paginationBean, null, date1, date2);
		 * }else{
		 */
		// System.out.println(" criterionList not null");
		transactionDAO.listAllTransactionDetails(paginationBean, criterionList, date1, date2);
		// }
	}

	// @javax.transaction.Transactional
	public void listSearchTransactionDetails(final PaginationBean<ForSettlement> paginationBean, final String date1,
			final String date2, final String year1, final String year2, final String status) {
		// System.out.println("Inside listAllTransaction:::::"+date1+" "+date2);
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (date1 != null && date2 != null) {
			criterionList.add(Restrictions.between("date", date1, date2));
		}
		if (year1 != null && year2 != null) {
			criterionList.add(Restrictions.between("txnYear", year1, year2));
		}
		if (status != null) {

			criterionList.add(Restrictions.eq("status", status));
		}
		// transactionDAO.listSearchTransactionDetails(paginationBean,
		// criterionList, date1, date2);
		transactionDAO.listAllTransactionDetails(paginationBean, criterionList, date1, date2);
	}

	// @javax.transaction.Transactional
	public Receipt getReceiptSignature(String trx_id) {
		return transactionDAO.loadReceiptSignature(trx_id);
	}

	// loadMerchantDetails
	// @javax.transaction.Transactional
	public Merchant loadMerchantDetails(String trx_id) {
		return transactionDAO.loadMerchantDet(trx_id);
	}

	// agent volume summary new method 08/08/2016
	// @javax.transaction.Transactional
	public List<ForSettlement> agentVolume(String agentName) {
		// System.out.println("Inside listAllTransaction");
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		return transactionDAO.agentVolume(criterionList, agentName);
	}

	public List<ForSettlement> subAgentVolume(final SubAgent subAgent) {
		// System.out.println("Inside listAllTransaction");
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		/*
		 * if(agent != null){ criterionList.add(Restrictions.eq("agent", agent)); }
		 */

		return transactionDAO.subAgentVolume(criterionList, subAgent);
	}

	// new method merchant volume agent login 26072016

	// @javax.transaction.Transactional
	public List<ForSettlement> loadMerchantByVolume(
			final String merchantName) { /*
											 * final PaginationBean < ForSettlement > paginationBean ,
											 */

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (merchantName != null) {
			criterionList.add(Restrictions.eq("agent", merchantName));
		}
		return transactionDAO.loadMerchantByVolume(criterionList, merchantName);
	}

	// New Preauth change

	// @javax.transaction.Transactional
	public void getPreAuthTxn(final PaginationBean<PreAuthorization> paginationBean, final Merchant merchant) {
		// System.out.println("testttttt getPreAuthTxn");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		String mid = null;
		String motoMid = null;
		String ezyrecMid = null;
		String umMid = null;
		String ummotoMid = null;

		System.out.println("check preauth and ezyauth trxn");

		System.out.println("mid: " + merchant.getMid().getMid() + " motomid: " + merchant.getMid().getMotoMid()
				+ " ezyrecMid: " + merchant.getMid().getEzyrecMid());

		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			ummotoMid = merchant.getMid().getUmMotoMid();
		}
//		if (merchant.getMid().getEzyrecMid() != null) {
//			ezyrecMid = merchant.getMid().getEzyrecMid();
//		}
//		if (merchant.getMid().getUmMid() != null) {
//			umMid = merchant.getMid().getUmMid();
//		}
		criterionList.add(Restrictions.in("mid", new String[] { motoMid, ummotoMid }));

		criterionList.add(Restrictions.ne("status", "M"));
		// criterionList.add(Restrictions.isNull(("txnType")));
		transactionDAO.getPreAuthTxn(paginationBean, criterionList);
	}

	// @javax.transaction.Transactional
	public void searchPreAuth(final String fromDate, final String toDate, final String tid, final String status,
			final PaginationBean<PreAuthorization> paginationBean, final Merchant merchant) {
		// System.out.println("In Service toDate and fromDate :" + toDate +
		// ": and :" + fromDate + ": TID :" + tid + ": Status:" + status);

		System.out.println("date: " + fromDate + " " + toDate);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		String mid = null;
		String motoMid = null;
		String ezyrecMid = null;
		String umMid = null;

		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}

		criterionList.add(Restrictions.in("mid", new String[] { mid, motoMid, ezyrecMid, umMid }));

		if (fromDate != null && toDate != null) {
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		if (tid != null && (!tid.equals(""))) {
			criterionList.add(Restrictions.eq("tid", tid));
		}
		if (status != null && (!status.equals(""))) {

			criterionList.add(Restrictions.ne("status", "M"));
		}
		/*
		 * if (txnType.equals("MOTO")) { criterionList.add(Restrictions.eq("txnType",
		 * txnType)); }else{ criterionList.add(Restrictions.isNull("txnType")); }
		 */
		transactionDAO.searchPreAuth(fromDate, toDate, tid, status, paginationBean, criterionList);
	}

	// @javax.transaction.Transactional
	public PreAuthorization getPreAuthTxn(String trxId) {
		return transactionDAO.getPreAuthTxn(trxId);
	}

	public TransactionRequest loadTxnDetailsByID(BigInteger bigInteger) {
		return transactionDAO.loadTxnDetailsByID(bigInteger);
	}

	// @javax.transaction.Transactional
	public void listPreAuthTransaction(final PaginationBean<PreAuthorization> paginationBean, final String data,
			final String date1, String txnType) {
		System.out.println("Inside  listAllTransaction");
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listPreAuthTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	// @javax.transaction.Transactional
	public void loadPreAuthByName(final PaginationBean<PreAuthorization> paginationBean, final String merchantName,
			final String date, final String txnType) {
		transactionDAO.loadPreAuthByName(paginationBean, merchantName, date, txnType);
	}

	// new method for contactName in transaction summary page admin & merchant
	// login
	// @javax.transaction.Transactional
	public TerminalDetails getTerminalDetailsByTid(final String tid) {
		return transactionDAO.getTerminalDetailsByTid(tid);
	}

	// ecom
	public MobileUser getMobileUserByGpayTid(final String tid) {
		return transactionDAO.getMobileUserByGpayTid(tid);
	}

	// qr
	public MobileUser getMobileUserByGpayTidqr(final String tid) {
		return transactionDAO.getMobileUserByGpayTidqr(tid);
	}

	// settlement summary

	public void listSettlementMDRTransaction(final PaginationBean<SettlementModel> paginationBean, final String data,
			final String date1, final String txntype) {

		transactionDAO.listSettlementMDRTransaction(paginationBean, data, date1, txntype);
	}

	public SettlementMDR loadSettlementMDR(final String rrn) {
		return transactionDAO.loadSettlementMDR(rrn);
	}

	public BoostDailyRecon loadBoostdlyrecon(final String rrn) {
		return transactionDAO.loadBoostdlyrecon(rrn);
	}

	public FpxTransaction loadFpxTransaction(final String rrn, final String invoiceId) {
		return transactionDAO.loadFpxTransaction(rrn, invoiceId);
	}

	// new method 05-10-2016 demo
	public List<ForSettlement> subAgentVolume1(final SubAgent subAgent) {
		// System.out.println("Inside listAllTransaction");
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		/*
		 * if(agent != null){ criterionList.add(Restrictions.eq("agent", agent)); }
		 */

		return transactionDAO.subAgentVolume(criterionList, subAgent);
	}

	// start agent volume
	// @javax.transaction.Transactional
	public List<AgentVolumeData> agentVolumeData(String agentName) {
		System.out.println("Inside  agentVolumeData");
		/*
		 * System.out.println("Inside  agentVolumeData" + agentName);
		 *
		 * ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		 * criterionList.add(Restrictions.eq("username", agentName));
		 */

		return transactionDAO.agentVolumeData(agentName);
	}

	public List<AgentVolumeData> getAgentVolumeData(String agentName, StringBuffer str, StringBuffer strUm, long id) {
		System.out.println("Inside  agentVolumeData");
		/*
		 * System.out.println("Inside  agentVolumeData" + agentName);
		 *
		 * ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		 * criterionList.add(Restrictions.eq("username", agentName));
		 */

		return transactionDAO.getAgentVolumeData(agentName, str, strUm, id);
	}

	// @javax.transaction.Transactional
	public List<AgentVolumeData> subAgentVolumeData(SubAgent agentName) {

		return transactionDAO.subAgentVolumeData(agentName);
	}

	// @javax.transaction.Transactional
	public List<AgentVolumeData> merchantVolumeData(
			final String merchantName) { /*
											 * final PaginationBean < ForSettlement > paginationBean ,
											 */

		// ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		/*
		 * if(merchantName != null) { criterionList.add(Restrictions.eq("agent",
		 * merchantName)); }
		 */
		return transactionDAO.merchantVolumeData(merchantName);
	}

	// new method for merchant volume
	// @javax.transaction.Transactional

	public String merchantCount(String agentName) {
		return transactionDAO.merchantCount(agentName);
	}

	public List<Merchant> getMerchantDataByAgent(BigInteger agid) {
		return transactionDAO.getMerchantDataByAgent(agid);
	}

	public List<MasterMerchant> getMasterMerchantDataByAgent(String agid) {
		return transactionDAO.getMasterMerchantDataByAgent(agid);
	}

	public List<String> loadmidBymerchant(Long merchantid) {
		return transactionDAO.loadmidBymerchant(merchantid);
	}

	public List<AgentVolumeData> getMerchantVolByMid(String mid) {
		return transactionDAO.getMerchantByMid(mid);
	}

	public List<AgentVolumeData> getMerchantVolByMid1(String mid) {
		return transactionDAO.getMerchantByMid1(mid);
	}

	public List<AgentVolumeData> getMerchantVolBymmId(String mid) {
		return transactionDAO.getMerchantBymmId(mid);
	}

	public List<AgentVolumeData> getMerchantVolBymmId1(String mid) {
		return transactionDAO.getMerchantBymmId1(mid);
	}

	public List<AgentVolumeData> getHotelMerchantVolByMid(String mid) {
		return transactionDAO.getHotelMerchantByMid(mid);
	}

	public List<AgentVolumeData> getHotelMerchantVolByUMid(StringBuffer uMid) {
		return transactionDAO.getHotelMerchantVolByUMid(uMid);
	}

	/*
	 * public List<AgentVolumeData> merchantVolumeDataByAgent( final String
	 * merchantName,final Long agId) { final PaginationBean<ForSettlement>
	 * paginationBean,
	 *
	 *
	 * List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();
	 *
	 * //List<String> listMid = transactionDAO.midByTransaction(merchantName);
	 * List<String> listMid = transactionDAO.midByTransaction(merchantName);
	 *
	 * for (String mid : listMid) { logger.info("MerchantId: "+mid);
	 * List<AgentVolumeData> listAVD1 = new ArrayList<AgentVolumeData>(); //
	 * listAVD1 = transactionDAO.getMerchantByMid(String.valueOf(agId)); listAVD1 =
	 * transactionDAO.getMerchantByMid(String.valueOf(mid)); for (AgentVolumeData
	 * avd : listAVD1) { System.out.println("Merchant Id: " + avd.getAgId());
	 * System.out.println("MerchantName: " + avd.getAgentName());
	 * //System.out.println("Txn Type :" + avd.getTxnType());
	 * System.out.println("Amount : " + avd.getAmount());
	 * System.out.println("Month :" + avd.getDate());
	 *
	 * listAVD.add(avd); } } if(mid[1]!=null){
	 * System.out.println("motoMid: "+mid[1]); List<AgentVolumeData> listAVD1 = new
	 * ArrayList<AgentVolumeData>(); listAVD1 =
	 * transactionDAO.getMerchantByMid(mid[1]); for (AgentVolumeData avd : listAVD1)
	 * { System.out.println("Merchant agentId: " + avd.getAgId());
	 * System.out.println("Merchant agentName: " + avd.getAgentName());
	 * System.out.println("Amount : " + avd.getAmount());
	 * System.out.println("Month :" + avd.getDate());
	 *
	 * listAVD.add(avd); } } return listAVD; }
	 */

	// new trying
	public List<AgentVolumeData> merchantVolumeDataByAgent(final String merchantName, final Long agId,
			String index) { /*
							 * final PaginationBean<ForSettlement> paginationBean,
							 */

		List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();

		// List<String> listMid = transactionDAO.midByTransaction(merchantName);
		List<String> listMid = transactionDAO.midByTransaction(merchantName, index);

		StringBuffer str = new StringBuffer();
		int j = 0;
		for (String mid : listMid) {

			if (j == 0) {
				str.append("\'");
				str.append(mid);
				str.append("\'");
				j++;
			} else {
				str.append(",\'");
				str.append(mid);
				str.append("\'");
			}
		}

		List<AgentVolumeData> listAVD1 = new ArrayList<AgentVolumeData>();
		listAVD1 = transactionDAO.getMerchantByMidList(str);
		List<AgentVolumeData> listUMAVD = new ArrayList<AgentVolumeData>();
		listUMAVD = transactionDAO.getMerchantByMid1List(str);

		// Method to get current and last three months name
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getAllMonth(cDate);
		List<String> date = new ArrayList<String>();

		for (int i = 0; i < listMonth.size(); i++) {
			date.add(getMonth(cDate));
			cDate--;
		}

		for (String mid : listMid) {
			logger.info("MerchantId: " + mid);
			BigInteger merchantId = new BigInteger(mid);
			Merchant merchant = transactionDAO.loadMerchantByID(merchantId);
			AgentVolumeData finalVolumeData = new AgentVolumeData();

			List<String> amountUM = new ArrayList<String>();

			List<String> amountFOR = new ArrayList<String>();
			int count = 0;
			int count1 = 0;

			logger.info("listUMAVD.size()  :" + listUMAVD.size());
			for (int i = 0; i < listUMAVD.size(); i++) {
				logger.info("listUMAVD.size()  : " + listUMAVD.size());
				logger.info("i  : " + i);

				if (listUMAVD.get(i).getAgId().equals(mid)) {
					logger.info("listUMAVD.get(i).getAgId()  : " + listUMAVD.get(i).getAgId());
					logger.info("mid  : " + mid);
					int date1 = Integer.parseInt(listUMAVD.get(i).getMonth());
//					logger.info("date1  : " + date1 );
//					logger.info("month  : " + listMonth.get(count).intValue());
					while (listMonth.get(count).intValue() != date1) {
//						dateUM.add(getMonth(listMonth.get(count).intValue()));
						amountUM.add("0.00");
						count++;

					} // else{
//					dateUM.add(getMonth(date1));
					Double d = new Double(listUMAVD.get(i).getAmount1());
					d = d / 100;

					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amountUM.add(output);
					count++;

				}

			}

			logger.info("listAVD1.size()  : " + listAVD1.size());
			for (int k = 0; k < listAVD1.size(); k++) {

				logger.info("listUMAVD.size()  : " + listAVD1.size());
				logger.info("k  : " + k);

//				logger.info("Second loop  : " + listAgentVolumeData1.get(k).getAgId());

				if (listAVD1.get(k).getAgId().equals(mid)) {

					logger.info("listAVD1.get(k).getAgId()  : " + listAVD1.get(k).getAgId());
					int date1 = Integer.parseInt(listAVD1.get(k).getMonth());
//					logger.info("month  : " + date1 + " : " + listMonth.get(count1).intValue());
					while (listMonth.get(count1).intValue() != date1) {
//						dateFOR.add(getMonth(listMonth.get(count1).intValue()));
						amountFOR.add("0.00");
						count1++;

					} // else{
//					dateFOR.add(getMonth(date1));
					Double d = new Double(listAVD1.get(k).getAmount1());
					d = d / 100;

					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amountFOR.add(output);
					count1++;

				}

			}

			// UM Amount String List to Long list
			logger.info("List UMTxn String Amount ::" + amountUM);
			logger.info("List UMTxn String Amount Size ::" + amountUM.size());

			for (int y = amountUM.size(); y < 4; y++) {
				amountUM.add("0.00");
			}
			logger.info("After List UMTxn String Amount Size ::" + amountUM.size());

			List<Double> forLongAmountUM = new ArrayList<Double>(amountUM.size());
			for (String s : amountUM)
				forLongAmountUM.add(Double.parseDouble(s));
//				logger.info("List UMTxn Long Amount ::" + forLongAmountUM);
//				logger.info("List UMTxn Long Amount Size ::" + forLongAmountUM.size());

			// forSettlement Amount String List to Long list
			logger.info("List forTxn String Amount ::" + amountFOR);
			logger.info("List forTxn String Amount Size ::" + amountFOR.size());

			for (int z = amountFOR.size(); z < 4; z++) {
				amountFOR.add("0.00");
			}
			logger.info("After List forTxn String Amount Size ::" + amountFOR.size());

			List<Double> forLongAmount = new ArrayList<Double>(amountFOR.size());
			for (String s : amountFOR)
				forLongAmount.add(Double.parseDouble(s));
//				logger.info("List forTxn Long Amount ::" + forLongAmount);
//				logger.info("List forTxn Long Amount Size ::" + forLongAmount.size());

			// Add two long list value and convert string list

			int len = 0;
			if (forLongAmountUM.size() > forLongAmount.size()) {
				len = forLongAmountUM.size();
			} else {
				len = forLongAmount.size();
			}

			List<Double> newAmount = new ArrayList<Double>(len);
			for (int i = 0; i < len; i++) {
				Double Amount = forLongAmount.get(i) + forLongAmountUM.get(i);
//					logger.info("List Amount ::"+Amount);
				newAmount.add(Amount);
			}

//				logger.info("List newAmount Long Amount ::"+newAmount);
//				logger.info("List newAmount Long Amount Size ::"+newAmount.size());

			List<String> newAmountString = new ArrayList<String>(newAmount.size());
			for (Double s : newAmount)
				newAmountString.add(String.valueOf(s));
			logger.info("List newAmount String Amount ::" + newAmountString);
			logger.info("List newAmount String Amount Size ::" + newAmountString.size());

			finalVolumeData.setAgId(merchant.getId().toString());
			finalVolumeData.setAgentName(merchant.getBusinessName());
			finalVolumeData.setAmount(newAmountString);
			finalVolumeData.setDate(date);

			logger.info("Controller agId : " + finalVolumeData.getAgId());
			logger.info("Controller agentName : " + finalVolumeData.getAgentName());
			logger.info("Controller amount : " + finalVolumeData.getAmount());
			logger.info("Controller date : " + finalVolumeData.getDate());

			listAVD.add(finalVolumeData);

		}

		return listAVD;
	}

	public List<AgentVolumeData> getMerchantVolumeByMID(final String merchantID, final Long agId) {

		List<AgentVolumeData> listAVD1 = new ArrayList<AgentVolumeData>();
		listAVD1 = transactionDAO.getMerchantVolumeByMID(merchantID, String.valueOf(agId));

		return listAVD1;
	}

	// new method for all transaction export 09052017

	public List<ForSettlement> listEAllTransactionDetails(final String date1, final String date2, final String status) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (date1 != null && date2 != null) {
			// System.out.println(" Data date :"+date1+ date2);
			criterionList.add(Restrictions.between("timeStamp", date1, date2));
		}

		if (status != null && !status.isEmpty()) {
			// System.out.println(" Data status:"+status);
			criterionList.add(Restrictions.eq("status", status));
		}
		// criterionList.add();
		return transactionDAO.listAllETransactionDetails(criterionList, date1, date2);

	}

	public List<ForSettlement> exportAllTransactionByAdmin(final String data, final String data1, String status) {
		// System.out.println("Inside listAllTransaction");
		// String data= null;
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		return transactionDAO.exportAllTransactionbyAdmin(criterionList, data, data1, status);

	}

	public void listAllTransactionDetailsbyAdmin(PaginationBean<ForSettlement> paginationBean, String fromDate1,
			String toDate1, String status) {
		System.out.println(fromDate1 + toDate1 + status);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (fromDate1 != null && toDate1 != null) {
			// System.out.println(" Data date :"+date1+ date2);
			criterionList.add(Restrictions.between("timeStamp", fromDate1, toDate1));
		}
		if (status != null && !status.isEmpty()) {
			// System.out.println(" Data status:"+status);
			criterionList.add(Restrictions.eq("status", status));
		}

		transactionDAO.listAllTransactionDetailsbyAdmin(paginationBean, criterionList, fromDate1, toDate1, status);

	}

	public void listAllUmTransactionDetailsbyAdmin(PaginationBean<ForSettlement> paginationBean, String fromDate1,
			String toDate1, String status) {
		System.out.println(fromDate1 + toDate1 + status);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (fromDate1 != null && toDate1 != null) {
			// System.out.println(" Data date :"+date1+ date2);
			criterionList.add(Restrictions.between("timeStamp", fromDate1, toDate1));
		}
		if (status != null && !status.isEmpty()) {
			// System.out.println(" Data status:"+status);
			criterionList.add(Restrictions.eq("status", status));
		}

		transactionDAO.listAllUmTransactionDetailsbyAdmin(paginationBean, criterionList, fromDate1, toDate1, status);

	}

	public void listAllUmTransactionDetails(PaginationBean<ForSettlement> paginationBean, Merchant merchant,
			String date, String date1) {

		transactionDAO.listAllUmTransactionDetails(paginationBean, merchant, date, date1);

	}

	public void getTransactionRequest(PaginationBean<TransactionRequest> paginationBean, String fromDate1,
			String toDate1, String status) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		transactionDAO.getTransactionRequest(paginationBean, criterionList);

	}

	public void getTransactionEnquiry(PaginationBean<TransactionRequest> paginationBean, String fromDate, String toDate,
			String mid, String tid) {

		transactionDAO.getTransactionEnquiry(paginationBean, fromDate, toDate, mid, tid);

	}

	public void getTransactionResponse(PaginationBean<TransactionResponse> paginationBean, String fromDate1,
			String toDate1, String status) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		transactionDAO.getTransactionResponse(paginationBean, criterionList);

	}

	public void MerchantTransactionSummByAdmin(PaginationBean<ForSettlement> paginationBean, String fromDate1,
			String toDate1, String status) {
		System.out.println(fromDate1 + toDate1 + status);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (fromDate1 != null && toDate1 != null) {
			// System.out.println(" Data date :"+date1+ date2);
			criterionList.add(Restrictions.between("timeStamp", fromDate1, toDate1));
		}
		if (status != null && !status.isEmpty()) {
			// System.out.println(" Data status:"+status);
			criterionList.add(Restrictions.eq("status", status));
		}

		transactionDAO.MerchantTransactionSummByAdmin(paginationBean, criterionList, fromDate1, toDate1, status);

	}

	public List<ForSettlement> MerchantExportTrans(String fromDate1, String toDate1, String status) {
		System.out.println(fromDate1 + toDate1 + status);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (fromDate1 != null && toDate1 != null) {
			// System.out.println(" Data date :"+date1+ date2);
			criterionList.add(Restrictions.between("timeStamp", fromDate1, toDate1));
		}
		if (status != null && !status.isEmpty()) {
			// System.out.println(" Data status:"+status);
			criterionList.add(Restrictions.eq("status", status));
		}

		return transactionDAO.MerchantExportTrans(criterionList, fromDate1, toDate1, status);

	}

	public List<CountryCurPhone> loadCountryCurrency() {
		return transactionDAO.loadCountryData();
	}

	public List<TerminalDetails> getAllTid(Merchant currentMerchant) {
		return transactionDAO.getAllTid(currentMerchant);
	}

	public List<TerminalDetails> getGpayTid(Merchant currentMerchant) {
		return transactionDAO.getGpayTid(currentMerchant);
	}

	public List<MobileUser> getGpayTidbyFK(Merchant currentMerchant) {
		return transactionDAO.getGpayTidbyFK(currentMerchant);
	}

	public List<String> getGpayTidbymerchantFK(Merchant currentMerchant) {
		List<MobileUser> listM = transactionDAO.getGpayTidbyFK(currentMerchant);
		List<String> listgTid = new ArrayList<String>();
		if (listM != null) {
			for (MobileUser m : listM) {
				if (m.getGpayTid() != null) {
					if (!m.getGpayTid().isEmpty()) {
						listgTid.add(m.getGpayTid());
					}

				}

			}
		}
		return listgTid;
	}

//	public List<String> getAllGpayTidbymerchantFK() {
//		List<MobileUser> listM = transactionDAO.getAllGpayTid();
//		List<String> listgTid = new ArrayList<String>();
//		if (listM != null) {
//			for (MobileUser m : listM) {
//				if (m.getGpayTid() != null) {
//					if (!m.getGpayTid().isEmpty()) {
//						listgTid.add(m.getGpayTid());
//
//					}
//
//				}
//
//			}
//		}
//		return listgTid;
//	}

	public List<String> getAllGpayTidbymerchantFK() {
		List<MobileUser> listM = transactionDAO.getAllGpayTid();
		List<String> listgTid = new ArrayList<String>();
		if (listM != null) {
			for (MobileUser m : listM) {
				if (m.getGpayTid() != null) {
					if (!m.getGpayTid().isEmpty()) {
						listgTid.add(m.getGpayTid());

					}

				}

			}
		}
		return listgTid;
	}

	// grabpay qr

	public List<String> getAllGpayTidbymerchantFK1() {
		List<MobileUser> listM = transactionDAO.getAllGpayTid();
		List<String> listgTid = new ArrayList<String>();
		if (listM != null) {
			for (MobileUser m : listM) {
				if (m.getGpayTid() != null) {
					if (!m.getGpayTid().isEmpty()) {
						listgTid.add(m.getGpayTid());

					}

				}

			}
		}
		return listgTid;
	}

	public List<TerminalDetails> loadAlltid() {
		// TODO Auto-generated method stub
		return transactionDAO.loadAlltid();
	}

	public List<MID> loadAllmid() {
		return transactionDAO.loadAllmid();
	}

	public void updateTxnStatus(ForSettlement settle) {
		transactionDAO.saveOrUpdateEntity(settle);
	}

	public List<TransactionRequest> exportTransactionExpiry(String fromDate1, String toDate1, String mid, String tid) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		return transactionDAO.exportTransactionExpiry(criterionList, fromDate1, toDate1, mid, tid);
	}

	public void listUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywayTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMEzyauthTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyauthTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	// rk added

	public void listUMAuthTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMAuthTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}
	// rk added

	public void listUMMotoTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMMotoTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMEzyrecTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyrecTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMLinkTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMLinkTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	// rk added
	public void listpreauthfee(PaginationBean<UMEzyway> paginationBean, String date1, String date2) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listpreauthfee(paginationBean, date1, date2);

	}

	public void listUMVccTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMVccTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMMotoTransactionByAgent(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			StringBuffer strUm, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMMotoTransactionByAgent(paginationBean, criterionList, data, date1, strUm, txnType);

	}

	public void listUMEzywayTransactionByAgent(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			StringBuffer strUm, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywayTransactionByAgent(paginationBean, criterionList, data, date1, strUm, txnType);

	}

	public void listUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywayTransaction(paginationBean, criterionList, data, date1, umEzywayMid, txnType,
				currentMerchant);

	}

	public void SearchlistUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.SearchlistUMEzywayTransaction(paginationBean, criterionList, data, date1, umEzywayMid, txnType,
				currentMerchant);

	}

	public void listUMMotoTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String fiuuMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMMotoTransaction(paginationBean, criterionList, data, date1, umMotoMid,fiuuMid, txnType,
				currentMerchant);

	}

	public void listUMEzyrecTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzyrecMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyrecTransaction(paginationBean, criterionList, data, date1, umEzyrecMid, txnType,
				currentMerchant);

	}

	public void listUMLinkTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMLinkTransaction(paginationBean, criterionList, data, date1, umMotoMid, txnType,
				currentMerchant);

	}

	public void listUMEzyLinkSSTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umSsMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyLinkSSTransaction(paginationBean, criterionList, data, date1, umSsMotoMid, txnType);

	}

	public void listMobiliteLinkTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String tid) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listMobiliteLinkTransaction(paginationBean, criterionList, data, date1, tid);

	}

	public void listUMVccTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMVccTransaction(paginationBean, criterionList, data, date1, umMotoMid, txnType);

	}

	public void listUMEzyauthMerchantTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			Merchant merchant, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyauthMerchantTransaction(paginationBean, criterionList, data, date1, merchant, txnType);

	}

	//

	public void listUMEzyauthFailedTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			Merchant merchant, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyauthFailedTransaction(paginationBean, criterionList, data, date1, merchant, txnType);

	}

	public void listUMEzyauthFailedTransaction1(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			Merchant merchant, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyauthFailedTransaction1(paginationBean, criterionList, data, date1, merchant, txnType);

	}
	//

	public void listUMEzywayTransactionEnq(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywayTransactionEnq(paginationBean, criterionList, data, date1, umEzywayMid, txnType);

	}

	public void listUMMotoTransactionEnq(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMMotoTransactionEnq(paginationBean, criterionList, data, date1, umMotoMid, txnType);

	}

	public void listUMVccTransactionEnq(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMVccTransactionEnq(paginationBean, criterionList, data, date1, umMotoMid, txnType);

	}

	public void listUMEzywayTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywayTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMMotoTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMMotoTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMEzyrecTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyrecTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMLinkTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMLinkTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void listUMVccTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMVccTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMEzywayTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzywayTransaction(paginationBean, criterionList, data, date1, umEzywayMid, txnType,
				currentMerchant);

	}

	public void exportUMMotoTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid,String fiuuMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMMotoTransaction(paginationBean, criterionList, data, date1, umMotoMid,fiuuMid, txnType,
				currentMerchant);

	}

	public void exportMobiliteLinkTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String tid) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportMobiliteLinkTransaction(paginationBean, criterionList, data, date1, tid);

	}

	public void exportUMEzyrecTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzyrecMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzyrecTransaction(paginationBean, criterionList, data, date1, umEzyrecMid, txnType,
				currentMerchant);

	}

	public void exportUMLinkTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMLinkTransaction(paginationBean, criterionList, data, date1, umMotoMid, txnType,
				currentMerchant);

	}

	public void exportUMLinkSSTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umSsMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMLinkSSTransaction(paginationBean, criterionList, data, date1, umSsMotoMid, txnType);

	}

	public void exportUMVccTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMVccTransaction(paginationBean, criterionList, data, date1, umMotoMid, txnType);

	}

	public void exportUMEzyauthTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			Merchant merchant, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzyauthTransaction(paginationBean, criterionList, data, date1, merchant, txnType);

	}

	public void exportUMEzywayTransactionAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzywayTransactionAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMEzyauthTransactionAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzyauthTransactionAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMMotoTransactionAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMMotoTransactionAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMEzyrecTransactionAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzyrecTransactionAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMLinkTransactionAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMLinkTransactionAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMVccTransactionAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMVccTransactionAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMMotoTransactionAgent(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			StringBuffer strUm, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMMotoTransactionAgent(paginationBean, criterionList, data, date1, strUm, txnType);

	}

	public void exportUMEzywayTransactionAgent(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			StringBuffer strUm, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzywayTransactionAgent(paginationBean, criterionList, data, date1, strUm, txnType);

	}

	public void exportUMEzywayTransactionEnq(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzywayTransactionEnq(paginationBean, criterionList, data, date1, umEzywayMid, txnType);

	}

	public void exportUMMotoTransactionEnq(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMMotoTransactionEnq(paginationBean, criterionList, data, date1, umMotoMid, txnType);

	}

	public void exportUMVccTransactionEnq(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMVccTransactionEnq(paginationBean, criterionList, data, date1, umMotoMid, txnType);

	}

	public void exportUMEzywayTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzywayTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMMotoTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMMotoTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMEzyrecTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzyrecTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMLinkTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMLinkTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public void exportUMVccTxnEnqByAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMVccTxnEnqByAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	public UMEcomTxnRequest loadUMEzywayTransactionRequest(String id) {
		return transactionDAO.loadUMEzywayTransactionRequest(id);
	}

	// BNPL VOID

	public BnplTxnDetails loadBnplDetails(String id) {
		return transactionDAO.loadBnplDetails(id);
	}

	public UMEcomTxnResponse loadUMEzywayTransactionResponse(String id) {
		return transactionDAO.loadUMEzywayTransactionResponse(id);
	}

	public ForSettlement loadBoostForSettlement(String rrn) {
		return transactionDAO.loadBoostForSettlement(rrn);
	}

	public ForSettlement loadGrabpayForSettlement(String rrn) {
		return transactionDAO.loadGrabpayForSettlement(rrn);
	}

	public TempletFields getMerchantDetByGrabpayTid(String tid) {
		return transactionDAO.getMerchantDetByGrabpayTid(tid);
	}

	public TempletFields getMerchantDetByGrabpayecom(String tid) {
		return transactionDAO.getMerchantDetByGrabpayecom(tid);
	}

	public FpxTransaction loadFpxTransaction(String txnId) {
		return transactionDAO.loadFpxTransaction(txnId);
	}

	public void addVC(MotoVCDetails vc) {
		transactionDAO.saveOrUpdateEntity(vc);
	}

	public void motoVC(PaginationBean<MotoVCDetails> paginationBean, String data, String date1, String motoMid)
			throws Exception {
		transactionDAO.motoVC(paginationBean, data, date1, motoMid);

	}

	public List<MotoVCDetails> getActiveMoto(String mid) {
		return transactionDAO.getActiveMoto(mid);
	}

	public MotoVCDetails motoVCById(String id) {
		// TODO Auto-generated method stub
		return transactionDAO.motoVCById(id);
	}

	public void updateUMTxnRes(UMEcomTxnResponse ChargeBack) {
		transactionDAO.saveOrUpdateEntity(ChargeBack);
	}

	public void listBoostTransaction(PaginationBean<BoostDailyRecon> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listBoostTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	public String getVCTid(String mid) {

		String tid;

		logger.info("Moto VC MID: " + mid);

		tid = PropertyLoad.getFileData().getProperty("MOTOVC_" + mid);

		logger.info("Moto VC TID: " + tid);

		return tid;
	}

	public void getGrabTransactionForSettlement(PaginationBean<ForSettlement> paginationBean,
			List<String> terminalDetailsList, String date, String date1) {

		logger.info("Inside getGrabTransactionForSettlement : " + date + "  " + date1);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		ArrayList<UMEzyway> fss = new ArrayList<UMEzyway>();
		String sql = null;

		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + " : " + to);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		String[] array = new String[terminalDetailsList.size()];
		int index = 0;
		for (Object value : terminalDetailsList) {
			logger.info("tid: " + (String) value);
			array[index] = (String) value;
			index++;
		}

//			logger.info(" TXN_TYPE: "+txnType);
//			logger.info("tid: "+array);
		criterionList.add(Restrictions.in("tid", array));
		criterionList.add(Restrictions.eq("txnType", "GRABPAY"));
		criterionList.add(Restrictions.in("status", new String[] { "GPC", "GPS", "GRF", "GPT" }));

		criterionList.add(Restrictions.between("timeStamp", from, to));

		transactionDAO.getForSettlement(paginationBean, criterionList);

//		 System.out.println(paginationBean.getItemList().size());
		// System.out.println("getTransactionForSettlement");

	}

	// Grabpay QR Txn Summary

	public void getGrabTransactionqrForSettlement(PaginationBean<ForSettlement> paginationBean,
			List<String> terminalDetailsList, String date, String date1) {

		logger.info("Inside getGrabTransactionForSettlement : " + date + "  " + date1);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		ArrayList<UMEzyway> fss = new ArrayList<UMEzyway>();
		String sql = null;

		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + " : " + to);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		String[] array = new String[terminalDetailsList.size()];
		int index = 0;
		for (Object value : terminalDetailsList) {
			logger.info("tid: " + (String) value);
			array[index] = (String) value;
			index++;
		}

//				logger.info(" TXN_TYPE: "+txnType);
//				logger.info("tid: "+array);
		criterionList.add(Restrictions.in("tid", array));
		criterionList.add(Restrictions.eq("txnType", "GRABPAY"));
		criterionList.add(Restrictions.in("status", new String[] { "GPC", "GPS", "GRF", "GPT", "GPP" }));
		criterionList.add(Restrictions.eq("reqMode", "App"));
		criterionList.add(Restrictions.between("timeStamp", from, to));

		transactionDAO.getForSettlementgrabpayqr(paginationBean, criterionList);

//			 System.out.println(paginationBean.getItemList().size());
		// System.out.println("getTransactionForSettlement");

	}

	// Grabpay Ecom Txn Summary

	public void getGrabTransactionecomForSettlement(PaginationBean<ForSettlement> paginationBean,
			List<String> terminalDetailsList, String date, String date1) {

		logger.info("Inside getGrabTransactionForSettlement : " + date + "  " + date1);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		ArrayList<UMEzyway> fss = new ArrayList<UMEzyway>();
		String sql = null;

		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + " : " + to);

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		String[] array = new String[terminalDetailsList.size()];
		int index = 0;
		for (Object value : terminalDetailsList) {
			logger.info("tid: " + (String) value);
			array[index] = (String) value;
			index++;
		}

//					logger.info(" TXN_TYPE: "+txnType);
//					logger.info("tid: "+array);
		criterionList.add(Restrictions.in("tid", array));
		criterionList.add(Restrictions.eq("txnType", "GRABPAY"));
		criterionList.add(Restrictions.in("status", new String[] { "GPC", "GPS", "GRF", "GPT", "GPP" }));
		criterionList.add(Restrictions.eq("reqMode", "Link"));
		criterionList.add(Restrictions.between("timeStamp", from, to));

		transactionDAO.getForSettlementgrabpayecom(paginationBean, criterionList);

//				 System.out.println(paginationBean.getItemList().size());
		// System.out.println("getTransactionForSettlement");

	}

	public BoostDailyRecon loadBoostSettlement(String date) {

		String from = null;

		if ((date == null) || (date.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from);

		return transactionDAO.loadBoostSettlement(from);
	}

	public void listBoostSettlement(PaginationBean<BoostDailyRecon> paginationBean, String date) {
		String from = null;

		if ((date == null) || (date.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from);

		transactionDAO.listBoostSettlement(paginationBean, from);

	}

	public SettlementMDR loadSettlement(String date, String date1) {

		String from = null;
		String to = null;
		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + " : " + to);

		return transactionDAO.loadSettlement(from, to);

	}

	public SettlementMDR loadLatestSettlement() {
		return transactionDAO.loadLatestSettlement();

	}

	public SettlementMDR loadmobiliteSettlement(String date, String date1) {

		String from = null;
		String to = null;
		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + "::" + to);

		return transactionDAO.loadmobiliteSettlement(from, to);

	}

	public SettlementMDR loadLatestmobiliteSettlement() {

		return transactionDAO.loadLatestmobiliteSettlement();

	}

	public BizAppSettlement loadLatestbizappSettlement() {

		return transactionDAO.loadLatestbizappSettlement();

	}

	public void listSettlement(PaginationBean<SettlementMDR> paginationBean, String date, String date1) {
		String from = null;
		String to = null;
		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + " : " + to);

		transactionDAO.listSettlement(paginationBean, from, to);
	}

	public void listLatestSettlement(PaginationBean<SettlementMDR> paginationBean) {

		transactionDAO.listLatestSettlement(paginationBean);
	}

	public void listmobiliteSettlement(PaginationBean<SettlementMDR> paginationBean, String date, String date1) {
		String from = null;
		String to = null;

		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			// logger.info("Inside listAllTransaction 12131313: " + date + " " + date1);
			/*
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM"); from =
			 * dateFormat.format(dt); from = from + "-01"; logger.info("change date format:"
			 * + from);
			 */

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + " : " + to);
		transactionDAO.listmobiliteSettlement(paginationBean, from, to);
	}

	public void listLatestmobiliteSettlement(PaginationBean<SettlementMDR> paginationBean) {

		transactionDAO.listLatestmobiliteSettlement(paginationBean);
	}

	public void listbizappSettlement(PaginationBean<BizAppSettlement> paginationBean, String date, String date1) {
		String from = null;
		String to = null;
		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			to = date1;
			/*
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { from
			 * = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy") .parse(from));
			 * logger.info("date format:" + date); } catch (ParseException e) {
			 *
			 * e.printStackTrace(); }
			 */

		}

		logger.info("checkd date: " + from + "::" + to);

		transactionDAO.listbizappSettlement(paginationBean, from, to);
	}

	public List<AgentVolumeData> agentVolumeUM(StringBuffer str) {
		// TODO Auto-generated method stub
		return transactionDAO.agentVolumeUM(str);
	}

	public List<AgentVolumeData> agentVolumeForsettle(StringBuffer str) {
		// TODO Auto-generated method stub
		return transactionDAO.agentVolumeForsettle(str);
	}

	public List<AgentVolumeData> agentTotalVolume(StringBuffer str) {
		// TODO Auto-generated method stub
		return transactionDAO.agentTotalVolume(str);
	}

	public List<AgentVolumeData> merchantVolumeDataInTxnSummary(final String merchantName, final Long agId,
			String index) {

		List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();

		// List<String> listMid = transactionDAO.midByTransaction(merchantName);
		List<String> listMid = transactionDAO.midByTransaction(merchantName, index);

		StringBuffer str = new StringBuffer();
		int j = 0;
		for (String mid : listMid) {

			if (j == 0) {
				str.append("\'");
				str.append(mid);
				str.append("\'");
				j++;
			} else {
				str.append(",\'");
				str.append(mid);
				str.append("\'");
			}
		}

		List<AgentVolumeData> listAVD1 = new ArrayList<AgentVolumeData>();

		listAVD1 = transactionDAO.getMerchantInTxnSummary(str);

		// Method to get current and last three months name
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getAllMonth(cDate);
		List<String> date = new ArrayList<String>();

		for (int i = 0; i < listMonth.size(); i++) {
			date.add(getMonth(cDate));
			cDate--;
		}

		for (String mid : listMid) {
			logger.info("MerchantId: " + mid);
			BigInteger merchantId = new BigInteger(mid);
			Merchant merchant = transactionDAO.loadMerchantByID(merchantId);
			AgentVolumeData finalVolumeData = new AgentVolumeData();

			List<String> amountFOR = new ArrayList<String>();
			int count1 = 0;

			logger.info("listAVD1.size()  : " + listAVD1.size());
			for (int k = 0; k < listAVD1.size(); k++) {

				logger.info("listUMAVD.size()  : " + listAVD1.size());
				logger.info("k  : " + k);

//			logger.info("Second loop  : " + listAgentVolumeData1.get(k).getAgId());

				if (listAVD1.get(k).getAgId().equals(mid)) {

					logger.info("listAVD1.get(k).getAgId()  : " + listAVD1.get(k).getAgId());
					int date1 = Integer.parseInt(listAVD1.get(k).getMonth());
//				logger.info("month  : " + date1 + " : " + listMonth.get(count1).intValue());
					while (listMonth.get(count1).intValue() != date1) {
//					dateFOR.add(getMonth(listMonth.get(count1).intValue()));
						amountFOR.add("0.00");
						count1++;

					} // else{
//				dateFOR.add(getMonth(date1));
					Double d = new Double(listAVD1.get(k).getAmount1());
					d = d / 100;

					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amountFOR.add(output);
					count1++;

				}

			}

			for (int z = amountFOR.size(); z < 4; z++) {
				amountFOR.add("0.00");
			}

			finalVolumeData.setAgId(merchant.getId().toString());
			finalVolumeData.setAgentName(merchant.getBusinessName());
			finalVolumeData.setAmount(amountFOR);
			finalVolumeData.setDate(date);

			listAVD.add(finalVolumeData);

		}

		return listAVD;
	}

	public void listbizappSettlementByMerchant(PaginationBean<BizAppSettlement> paginationBean, Merchant merchant,
			String date) {
		String from = null;

		if ((date == null) || (date.equals(""))) {

			Date dt = new Date();

			// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

		} else {

			from = date;
			/*
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { from
			 * = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy") .parse(from));
			 * logger.info("date format:" + date); } catch (ParseException e) {
			 *
			 * e.printStackTrace(); }
			 */

		}

		logger.info("checkd date: " + from);

		transactionDAO.listbizappSettlementByMerchant(paginationBean, merchant, from);
	}

	public BizAppSettlement loadbizappSettlement(String date, String date1) {

		String from = null;
		String to = null;
		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			from = dateFormat.format(dt);
			// logger.info("change date format:" + from);
			// from = from + "-01";
			String from1 = from.substring(0, from.length() - 2);
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("change date format:" + from);

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
			to = dateFormat1.format(dt1);
			String to1 = to.substring(0, to.length() - 2);
			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("date format:" + to);

		} else {

			from = date;
			to = date1;
			/*
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { from
			 * = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy") .parse(from));
			 * logger.info("date format:" + date); } catch (ParseException e) {
			 *
			 * e.printStackTrace(); }
			 */

		}

		logger.info("checkd date: " + from + ":::" + to);

		return transactionDAO.loadbizappSettlement(from, to);

	}

	public void listLatestbizappSettlement(PaginationBean<BizAppSettlement> paginationBean) {

		transactionDAO.listLatestbizappSettlement(paginationBean);
	}

	public void listMerchantFPXSettlementByMid(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String fromDate, String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.listMerchantFPXSettlementByMid(paginationBean, midDetails, from, to);
	}

	public MobiMDR loadMobiMdr(String mid) {
		return transactionDAO.loadMobiMdr(mid);
	}

	public String totalSettleAmount(String from, String to, String Mid) {
		return transactionDAO.totalSettleAmount(from, to, Mid);

	}

	// EZYWIRE+ RK

	public void listAllUmEzywireplusTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllUmEzywireplusTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	public void searchAllUmEzywireplusTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchAllUmEzywireplusTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	// rk

	public void listUMSplitTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String SplitMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMSplitTransaction(paginationBean, criterionList, data, date1, SplitMid, txnType);
	}

	public void exportUMSplitTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String SplitMid, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMSplitTransaction(paginationBean, criterionList, data, date1, SplitMid, txnType);

	}

	// rk

	public void listUMSplitTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMSplitTransaction(paginationBean, criterionList, data, date1, txnType);

	}

	// rk

	public void exportUMSplitTransactionAdmin(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMSplitTransactionAdmin(paginationBean, criterionList, data, date1, txnType);

	}

	// rkboostqr
	public void listAllForsettlementTransactionQR(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllForsettlementTransactionQR(paginationBean, criterionList, data, date1, txnType);
	}

	// rkboostecom
	public void listAllForsettlementTransactionEcom(final PaginationBean<ForSettlement> paginationBean,
			final String data, final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllForsettlementTransactionEcom(paginationBean, criterionList, data, date1, txnType);
	}

	// rkboostecom
	public void searchecomForsettlementTransaction(final PaginationBean<ForSettlement> paginationBean,
			final String data, final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchecomForsettlementTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	// rkboostqr
	public void searchqrForsettlementTransaction(final PaginationBean<ForSettlement> paginationBean, final String data,
			final String date1, String txnType) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchqrForsettlementTransaction(paginationBean, criterionList, data, date1, txnType);
	}

	// settlementsum
	public void listsettleDetails(final PaginationBean<SettlementMDR> paginationBean, Merchant merchant, String date,
			String date1) {

		transactionDAO.listsettleDetails(paginationBean, merchant, date, date1);
	}

	// boostsettlementsum
	public void listboostsettleDetails(final PaginationBean<BoostDailyRecon> paginationBean, Merchant merchant,
			String date, String date1) {

		transactionDAO.listboostsettleDetails(paginationBean, merchant, date, date1);
	}

	// grabpaysettlementsum
	public void listgrabpaysettleDetails(final PaginationBean<GrabPayFile> paginationBean, Merchant merchant,
			String date, String date1) {

		transactionDAO.listgrabpaysettleDetails(paginationBean, merchant, date, date1);
	}

	// rk

	public void FPXTransactionEnqByMid(PaginationBean<FpxTransaction> paginationBean, Merchant midDetails,
			String fromDate, String toDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		transactionDAO.FPXTransactionEnqByMid(paginationBean, midDetails, from, to);
	}

	public void ListGrabpaySummaryAdmin(final PaginationBean<ForSettlement> paginationBean, Merchant merchant,
			String fromDate, String toDate, String VALUE, String TXNTYPE, String export) {

		transactionDAO.ListGrabpaySummaryAdmin(paginationBean, merchant, fromDate, toDate, VALUE, TXNTYPE, export);
	}

// Ezyway Failed Transaction - Start

	public void listUMEzywayTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywayTransactionFailure(paginationBean, criterionList, data, date1, umEzywayMid, txnType,
				currentMerchant);

	}

	public void exportUMEzywayTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzywayTransactionFailure(paginationBean, criterionList, data, date1, umEzywayMid,
				txnType, currentMerchant);

	}

// Ezyway Failed Transaction - End

// EzyLink Failed Transaction - Start

	public void listUMLinkTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMLinkTransactionFailure(paginationBean, criterionList, data, date1, umMotoMid, txnType,
				currentMerchant);

	}

	public void exportUMLinkTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMLinkTransactionFailure(paginationBean, criterionList, data, date1, umMotoMid, txnType,
				currentMerchant);

	}

// EzyLink Failed Transaction - End

// EzyMoto Failed Transaction - Start

	public void listUMMotoTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMMotoTransactionFailure(paginationBean, criterionList, data, date1, umMotoMid, txnType,
				currentMerchant);

	}

	public void exportUMMotoTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umMotoMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMMotoTransactionFailure(paginationBean, criterionList, data, date1, umMotoMid, txnType,
				currentMerchant);

	}

// EzyMoto Failed Transaction - End

// ezyauth failed Transaction - start

	// ezyauth failed transaction end

// EzyRec Failed Transaction - Start

	public void listUMEzyrecTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzyrecMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyrecTransactionFailure(paginationBean, criterionList, data, date1, umEzyrecMid, txnType,
				currentMerchant);

	}

	public void exportUMEzyrecTransactionFailure(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzyrecMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzyrecTransactionFailure(paginationBean, criterionList, data, date1, umEzyrecMid,
				txnType, currentMerchant);

	}

// EzyRec Failed Transaction - End

// EzyWire Failed Transaction - Start

	public void getUMForSettlementFailure(final PaginationBean<UMEzyway> paginationBean, final Merchant merchant) {
		// System.out.println("testttttt getForSettlement");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		transactionDAO.getAllUMTransactionbyMerchantFailure(paginationBean, criterionList, merchant);

	}

	public void searchUMForSettlementFailure(final String date, final String date1,
			final PaginationBean<UMEzyway> paginationBean, final Merchant merchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchUMForSettlementFailure(paginationBean, criterionList, date, date1, merchant);

	}

// EzyWire Failed Transaction - End

	// EARLY SETTLEMENT - START

	public List<String> tocheckholiday(String Currentdate, String CurrentTime) {

		logger.info(" Inside Validated List of Settlement Dates  ");

		return transactionDAO.tocheckholiday(Currentdate, CurrentTime);
	}

	public List<String> tocheckholidayList(String Currentdate) {

		logger.info(" Inside Validated List of Settlement Dates  ");

		return transactionDAO.tocheckholidayList(Currentdate);
	}

	public LocalDate tocheckholidaybeforePayoutTime(LocalDate CurrentDate) {

		logger.info("Inside tocheckholidaybeforePayoutTime  : " + CurrentDate);

		LocalDate result = CurrentDate.minusDays(1);

		int addedDays = 0;
		int workdays = 1;

		while (addedDays < workdays) {
			result = result.plusDays(1);
			if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				String finalDate = result.format(DateTimeFormatter.ISO_DATE);
				logger.info("Converted String to check holiday : " + finalDate);
				HolidayHistory leave = transactionDAO.loadHoliday(finalDate);
				if (leave == null) {
					++addedDays;
				} else {
					logger.info("Holiday ::::::::" + finalDate);

				}

			}
		}
		return result;

	}

	public LocalDate tocheckholidayafterPayoutTime(LocalDate CurrentDate) {

		logger.info("Inside tocheckholidayafterPayoutTime  : " + CurrentDate);

		LocalDate result = CurrentDate;
		int addedDays = 0;
		int workdays = 1;

		while (addedDays < workdays) {
			result = result.plusDays(1);
			if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				String finalDate = result.format(DateTimeFormatter.ISO_DATE);
				logger.info("Converted String to check holiday : " + finalDate);
				HolidayHistory leave = transactionDAO.loadHoliday(finalDate);
				if (leave == null) {
					++addedDays;
				} else {
					logger.info("Holiday ::::::::" + finalDate);

				}

			}
		}
		return result;

	}

	public List<SettlementMDR> loadNetAmountandsettlementdatebyCard(String settlementdate, Merchant merchant) {
		logger.info(" Inside loadNetAmountandsettlementdatebyCard Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyCard(settlementdate, merchant);
	}

	public List<BoostDailyRecon> loadNetAmountandsettlementdatebyBoost(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebyBoost Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyBoost(settlementdate, merchant);
	}

	public List<GrabPayFile> loadNetAmountandsettlementdatebyGrabpay(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebyGrabpay Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyGrabpay(settlementdate, merchant);
	}

	public List<FpxTransaction> loadRefundAndsettlementdatebyFpx(String settlementdate, Merchant merchant) {

		logger.info(" Inside Refund loadNetAmountandsettlementdatebyFpx Service  ");

		return transactionDAO.loadRefundAndsettlementdatebyFpx(settlementdate, merchant);
	}

	public List<FpxTransaction> loadNetAmountandsettlementdatebyFpx(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebyFpx Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyFpx(settlementdate, merchant);
	}

	public List<EwalletTxnDetails> loadNetAmountandsettlementdatebym1Pay(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebym1Pay Service ");

		return transactionDAO.loadNetAmountandsettlementdatebym1Pay(settlementdate, merchant);
	}

	public void updateJustsettletable(EarlySettlementModel updateinjustsettle, Merchant merchant) {

		logger.info(" Inside Just Settle Service ");

		String Mid = null;
		String Tid = null;
		TerminalDetails data = null;

		if (merchant.getMid().getUmMid() != null) {
			Mid = merchant.getMid().getUmMid();

		} else if (merchant.getMid().getUmEzywayMid() != null) {
			Mid = merchant.getMid().getUmEzywayMid();

		} else if (merchant.getMid().getUmMotoMid() != null) {
			Mid = merchant.getMid().getUmMotoMid();

		} else if (merchant.getMid().getBoostMid() != null) {
			Mid = merchant.getMid().getBoostMid();

		} else if (merchant.getMid().getGrabMid() != null) {
			Mid = merchant.getMid().getGrabMid();

		} else if (merchant.getMid().getTngMid() != null) {
			Mid = merchant.getMid().getTngMid();
		}
		else if (merchant.getMid().getShoppyMid() != null) {
			Mid = merchant.getMid().getShoppyMid();
		}
		else if (merchant.getMid().getFpxMid() != null) {
			Mid = merchant.getMid().getFpxMid();
		}
		
		//fiuuMid
		
		else if (merchant.getMid().getFiuuMid() != null) {
			Mid = merchant.getMid().getFiuuMid();
		}
		
// Load Tid by Mid
		try {
			data = merchantService.loadTerminalDetailsByMid(Mid);

			if(data!= null) {
				Tid = data.getTid();
			}

			logger.info("Found TID = " + Tid + "For this MID = " + Mid);

			Utils util = new Utils();

			// Saving values in Just settle Table
			JustSettle updatejsdata = new JustSettle();
			updatejsdata.setMerchantName(merchant.getBusinessName());
			updatejsdata.setMerchantEmail(merchant.getEmail());
			updatejsdata.setMid(Mid);
			updatejsdata.setTid(Tid);
			updatejsdata.setNetAmountPayable(
					util.amountFormatWithoutcomma(Double.parseDouble(updateinjustsettle.getUpdatepayableAmount())));
			updatejsdata.setWithDrawFee(
					util.amountFormatWithoutcomma(Double.parseDouble(updateinjustsettle.getUpdatewithdrawfeeAmount())));
			updatejsdata.setNetAmount(
					util.amountFormatWithoutcomma(Double.parseDouble(updateinjustsettle.getUpdatenetAmount())));
			updatejsdata.setWithDrawDate(updateinjustsettle.getWithdrawDate());
			updatejsdata.setStatus(updateinjustsettle.getStatus());
			updatejsdata.setSettlementDate(updateinjustsettle.getSettlementDate());
			updatejsdata.setTimeStamp(updateinjustsettle.getWithdrawDate());
			updatejsdata.setBankRequestAmount(util.amountFormatWithoutcomma(
					Double.parseDouble(updateinjustsettle.getCurlecRequestAmount().replace(",", ""))));
			updatejsdata.setBankFee(updateinjustsettle.getCurlecFee());
			updatejsdata.setEzysettleReferenceNo(updateinjustsettle.getEzysettleReferenceNo());

			transactionDAO.saveJustSettle(updatejsdata);
		} catch (Exception e) {
			logger.info("Exception Message while saveing Just settle table : " + e.getMessage());
			e.printStackTrace();
		}

//		transactionDAO.saveOrUpdateEntity(updatejsdata);

	}

//
	public boolean updatesettledate(String settledate, double ezysettleFee, Merchant merchant)
			throws ParseException, MobileApiException {

		String formatsettledate = null;
		formatsettledate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settledate));
		logger.info("Format Settlement date Update in Card = " + formatsettledate);

		String umMid = Optional.ofNullable(merchant.getMid()).map(MID::getUmMid).orElse("");
		String umEzywayMid = Optional.ofNullable(merchant.getMid()).map(MID::getUmEzywayMid).orElse("");
		String umMotoMid = Optional.ofNullable(merchant.getMid()).map(MID::getUmMotoMid).orElse("");
		String boostmid = Optional.ofNullable(merchant.getMid()).map(MID::getBoostMid).orElse("");
		String fpxmid = Optional.ofNullable(merchant.getMid()).map(MID::getFpxMid).orElse("");
		String tngMid = Optional.ofNullable(merchant.getMid()).map(MID::getTngMid).orElse("");
		String shoppyMid = Optional.ofNullable(merchant.getMid()).map(MID::getShoppyMid).orElse("");
		String fiuuMid = Optional.ofNullable(merchant.getMid()).map(MID::getFiuuMid).orElse("");

		// Card
		List<SettlementMDR> cardTrx = transactionDAO.loadNetAmountbyCard(formatsettledate, ezysettleFee, merchant);
		int updatecountForCard = this.updateCardFinalStatus(cardTrx, umMid, umEzywayMid, umMotoMid,fiuuMid, formatsettledate);


		// Boost
		String formatsettledateForBoost = null;
		formatsettledateForBoost = new SimpleDateFormat("yyyyMMdd")
				.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settledate));

		logger.info("Format Settlement date Update in Boost = " + formatsettledateForBoost);
		List<BoostDailyRecon> boostTrx = transactionDAO.loadNetAmountbyBoost(formatsettledateForBoost, ezysettleFee,
				merchant);
		logger.info("Format Settle Date in Boost = " + formatsettledate);
		int updatecountForBoost = this.updateBoostFinalStatus(boostTrx, umMid, umEzywayMid, umMotoMid, boostmid,
				formatsettledate);

		// Grab
		long currentMerchantid = merchant.getId();
		logger.info(" currentMerchantid " + currentMerchantid);
		List<GrabPayFile> grabTrx = transactionDAO.loadNetAmountbyGrabpay(formatsettledate, ezysettleFee, merchant);
		int updatecountForGrab = this.updateGrabPayFinalStatus(grabTrx, currentMerchantid,formatsettledate);

		// Fpx
		List<FpxTransaction> fpxTrx = transactionDAO.loadNetAmountbyFpx(settledate, ezysettleFee, merchant);
		int updatecountForfpx = this.updateFpxFinalStatus(fpxTrx, umMid, umEzywayMid, umMotoMid, fpxmid, settledate,
				ezysettleDao);

		// M1pay
		List<EwalletTxnDetails> m1payTrx = transactionDAO.loadNetAmountbym1Pay(formatsettledate, ezysettleFee,
				merchant);
		int updatecountForM1pay = this.updateM1payFinalStatus(m1payTrx, tngMid, shoppyMid, formatsettledate, umEzywayMid, umMotoMid, ezysettleDao);

		logger.info(String.format("Update Data List Counts - Card: %d, Boost: %d, Grab: %d, FPX: %d, M1Pay: %d",
				updatecountForCard, updatecountForBoost, updatecountForGrab, updatecountForfpx, updatecountForM1pay));
		logger.info(String.format("Total Data List Counts - Card: %d, Boost: %d, Grab: %d, FPX: %d, M1Pay: %d",
				cardTrx.size(), boostTrx.size(), grabTrx.size(), fpxTrx.size(), m1payTrx.size()));

		int totalDataList = cardTrx.size() + boostTrx.size() + grabTrx.size() + fpxTrx.size() + m1payTrx.size();
		int updateDataList = updatecountForCard + updatecountForBoost + updatecountForGrab + updatecountForfpx
				+ updatecountForM1pay;

		/* If each transaction is not updated properly, revert the changes */
		if (totalDataList != updateDataList) {
			this.revertEzysettleStatusUpdateforCard(cardTrx, umMid, umEzywayMid, umMotoMid,fiuuMid, formatsettledate);
			this.revertEzysettleStatusUpdateforBoost(boostTrx, umMid, umEzywayMid, umMotoMid, boostmid,
					formatsettledate);
			this.revertEzySettleStatusForGrab(grabTrx, currentMerchantid, formatsettledate);
			this.revertEzySettleStatusForFpx(fpxTrx, umMid, umEzywayMid, umMotoMid, fpxmid, settledate);
			this.revertEzySettleStatusForM1pay(m1payTrx, tngMid, shoppyMid, umEzywayMid, umMotoMid, formatsettledate);
			throw new RuntimeException(
					"Method name: updateSettleDate =>  transaction rechecked: Not all  transaction statuses were updated properly.");
		}
		return true;
	}

	public int updateCardFinalStatus(List<SettlementMDR> cardTrx, String umMid, String umEzywayMid, String umMotoMid, String fiuuMid,
			String formatsettledate) throws MobileApiException {
		int updateCountForCard = 0;

		for (SettlementMDR settlementMDR : cardTrx) {
			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setUmMid(umMid);
			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setFiuuMid(fiuuMid);

			payoutSettleBean.setNetAmt(settlementMDR.getNetAmount());
			payoutSettleBean.setEzyAmt(settlementMDR.getEzySettleAmt());
			payoutSettleBean.setFormatsettledate(formatsettledate);

			int count = ezysettleDao.updateEzysettleAmountForCard(payoutSettleBean);
			updateCountForCard += count;
		}

		return updateCountForCard;
	}

	public int updateBoostFinalStatus(List<BoostDailyRecon> boostTrx, String umMid, String umEzywayMid,
			String umMotoMid, String boostmid, String formatsettledate) throws MobileApiException {
		int updatecountForBoost = 0;
		for (BoostDailyRecon boostDailyRecon : boostTrx) {

			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setUmMid(umMid);
			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setBoostmid(boostmid);
			payoutSettleBean.setNetAmt(boostDailyRecon.getNetAmount());
			payoutSettleBean.setEzyAmt(boostDailyRecon.getEzySettleAmt());
			payoutSettleBean.setFormatsettledate(formatsettledate);

			int count = ezysettleDao.updateEzySettleAmountForBoost(payoutSettleBean);
			updatecountForBoost += count;
		}
		return updatecountForBoost;
	}

	public int updateGrabPayFinalStatus(List<GrabPayFile> grabTrx, Long currentMerchantid,String formatsettledate) throws MobileApiException {
		int updateCountForGrab = 0;
		for (GrabPayFile grabPayFile : grabTrx) {
//			String txnDate = grabPayFile.getPaymentDate();
//			logger.info("TxnDate Grabpay = " + txnDate);
//			String txnFormatDate = null;
//			if (txnDate != null && !txnDate.isEmpty()) {
//				try {
//					txnFormatDate = new SimpleDateFormat("yyyy-MM-dd")
//							.format(new SimpleDateFormat("dd-MMM-yyyy").parse(txnDate));
//				} catch (ParseException e) {
//					logger.error("Exception in formatTransactionDate: " + e.getMessage(), e);
//				}
//			}
//			if (txnFormatDate != null) {
//				logger.info("TxnFormatDate Grabpay = " + txnFormatDate);
//			}
			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setCurrentMerchantid(currentMerchantid);
			payoutSettleBean.setNetAmt(grabPayFile.getNetAmt());
			payoutSettleBean.setEzyAmt(grabPayFile.getEzySettleAmt());
			payoutSettleBean.setFormatsettledate(formatsettledate);

			int count = ezysettleDao.updateEzySettleAmountForGrabPay(payoutSettleBean);
			updateCountForGrab += count;
		}
		return updateCountForGrab;
	}

	public int updateFpxFinalStatus(List<FpxTransaction> fpxTrx, String umMid, String umEzywayMid, String umMotoMid,
			String fpxmid, String settledate, EzysettleDao ezysettleDao) throws MobileApiException {
		int updateCountForFpx = 0; // Initialize the update count

		for (FpxTransaction fpxTransaction : fpxTrx) {
			String txnDate = fpxTransaction.getTxDate();
			logger.info("TxnDate FPX = " + txnDate);
			String txnFormatDate = null;

			if (txnDate != null && !txnDate.isEmpty()) {
				try {
					txnFormatDate = new SimpleDateFormat("yyyy-MM-dd")
							.format(new SimpleDateFormat("dd-MMM-yyyy").parse(txnDate));
					logger.info("TxnFormatDate FPX = " + txnFormatDate);
				} catch (ParseException e) {
					logger.error("Error parsing transaction date: " + e.getMessage(), e);
				}
			}

			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setUmMid(umMid);
			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setFpxmid(fpxmid);
			payoutSettleBean.setSettledate(settledate);
			payoutSettleBean.setNetAmt(fpxTransaction.getPayableAmt());
			payoutSettleBean.setEzyAmt(fpxTransaction.getEzySettleAmt());
			payoutSettleBean.setTxnFormatDate(txnFormatDate);

			int count = ezysettleDao.updateEzySettleAmountForFpx(payoutSettleBean);
			updateCountForFpx += count;
		}

		return updateCountForFpx;
	}

	public int updateM1payFinalStatus(List<EwalletTxnDetails> m1payTrx, String tngMid, String shoppyMid,
			String formatsettledate, String umEzywayMid, String umMotoMid, EzysettleDao ezysettleDao) throws MobileApiException {
		int updateCountForM1pay = 0;

		for (EwalletTxnDetails ewalletTxnDetails : m1payTrx) {
			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setTngMid(tngMid);
			payoutSettleBean.setShoppyMid(shoppyMid);
			payoutSettleBean.setFormatsettledate(formatsettledate);
			payoutSettleBean.setNetAmt(ewalletTxnDetails.getPayableAmt());
			payoutSettleBean.setEzyAmt(ewalletTxnDetails.getEzySettleAmt());
			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setStatus(ewalletTxnDetails.getStatus());

			int count = ezysettleDao.updateEzySettleAmountForm1pay(payoutSettleBean);
			updateCountForM1pay += count;
		}

		return updateCountForM1pay;
	}

//	updatecount = updatecount/2;
//	if (updatecount != cardTrx.size()) {
//		this.revertEzysettleStatusUpdateforCard(cardTrx, umMid, umEzywayMid, umMotoMid, formatsettledate);
//		throw new RuntimeException(
//				"Method name: updateSettleDateByCard => Card transaction rechecked: Not all card transaction statuses were updated properly.");
//	} else {
//		return true;
//	}
	private void revertEzysettleStatusUpdateforCard(List<SettlementMDR> cardTrx, String umMid, String umEzywayMid,
			String umMotoMid,String fiuuMid, String formatsettledate) throws MobileApiException {
		for (SettlementMDR settlementMDR : cardTrx) {

			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setUmMid(umMid);
			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setFiuuMid(fiuuMid);
			payoutSettleBean.setFormatsettledate(formatsettledate);
			payoutSettleBean.setStatus(settlementMDR.getStatus());
			ezysettleDao.revertEzysettleStatusUpdateforCard(payoutSettleBean);

		}

	}

//	public boolean updatesettledatebyBoost(String settledate, double ezysettleFee, Merchant merchant)
//			throws MobileApiException, ParseException {
//
//		String formatsettledate = null;
//
//		formatsettledate = new SimpleDateFormat("yyyyMMdd")
//				.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settledate));
//
//		logger.info("Format Settlement date Update in Boost = " + formatsettledate);
//
//		String umMid = (merchant.getMid().getUmMid() != null) ? merchant.getMid().getUmMid() : "";
//		String umEzywayMid = (merchant.getMid().getUmEzywayMid() != null) ? merchant.getMid().getUmEzywayMid() : "";
//		String umMotoMid = (merchant.getMid().getUmMotoMid() != null) ? merchant.getMid().getUmMotoMid() : "";
//		String boostmid = (merchant.getMid().getBoostMid() != null) ? merchant.getMid().getBoostMid() : "";
//
//		Session session = sessionFactory.getCurrentSession();
//
//		// Each transaction updates the EZYSETTLE Amount - Start
//
//		List<BoostDailyRecon> boostTrx = transactionDAO.loadNetAmountbyBoost(formatsettledate, ezysettleFee, merchant);
//		int updatecount = 0;
//		for (BoostDailyRecon boostDailyRecon : boostTrx) {
//
//			String netAmt = boostDailyRecon.getNetAmount();
//			String ezyAmt = boostDailyRecon.getEzySettleAmt();
//
//			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
//			payoutSettleBean.setUmMid(umMid);
//			payoutSettleBean.setUmEzywayMid(umEzywayMid);
//			payoutSettleBean.setUmMotoMid(umMotoMid);
//			payoutSettleBean.setBoostmid(boostmid);
//			payoutSettleBean.setNetAmt(netAmt);
//			payoutSettleBean.setEzyAmt(ezyAmt);
//			payoutSettleBean.setFormatsettledate(formatsettledate);
//
//			updatecount = ezysettleDao.updateEzySettleAmountForBoost(payoutSettleBean);
//			updatecount += updatecount;
//		}
//		// need to change
////		int boostTransactionRecheck = ezysettleDao.loadTransactionCountbyBoost(formatsettledate, merchant);
//		updatecount = updatecount / 2;
//		logger.info("updatecount for Boost : " + updatecount);
//		if (updatecount != boostTrx.size()) {
//			this.revertEzysettleStatusUpdateforBoost(boostTrx, umMid, umEzywayMid, umMotoMid, boostmid,
//					formatsettledate);
//			throw new RuntimeException(
//					"Method name: updatesettledatebyBoost => Boost transaction rechecked: Not all Boost transaction statuses were updated properly.");
//		} else {
//			return true;
//		}
//	}

	private void revertEzysettleStatusUpdateforBoost(List<BoostDailyRecon> boostTrx, String umMid, String umEzywayMid,
			String umMotoMid, String boostmid, String formatsettledate) throws MobileApiException {

		for (BoostDailyRecon boostDailyRecon : boostTrx) {
			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setUmMid(umMid);
			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setBoostmid(boostmid);
			payoutSettleBean.setStatus(boostDailyRecon.getStatus());
			payoutSettleBean.setFormatsettledate(formatsettledate);
			ezysettleDao.revertEzysettleStatusUpdateforBoost(payoutSettleBean);
		}

	}

//	public boolean updatesettledatebyGrabpay(String settledate, double ezysettleFee, Merchant merchant)
//			throws ParseException, MobileApiException {
//
//		String formatsettledate = null;
//
//		formatsettledate = new SimpleDateFormat("yyyy-MM-dd")
//				.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settledate));
//
//		long currentMerchantid = merchant.getId();
//
//		logger.info(" currentMerchantid " + currentMerchantid);
//
//		logger.info("Format Settlement date Update in Grabpay = " + formatsettledate);
//
//		String umMid = (merchant.getMid().getUmMid() != null) ? merchant.getMid().getUmMid() : "";
//		String umEzywayMid = (merchant.getMid().getUmEzywayMid() != null) ? merchant.getMid().getUmEzywayMid() : "";
//		String umMotoMid = (merchant.getMid().getUmMotoMid() != null) ? merchant.getMid().getUmMotoMid() : "";
//
//		Session session = sessionFactory.getCurrentSession();
//		// Each transaction updates the EZYSETTLE Amount - Start
//
//		List<GrabPayFile> grabTrx = transactionDAO.loadNetAmountbyGrabpay(formatsettledate, ezysettleFee, merchant);
//		int updatecount = 0;
//		for (GrabPayFile grabPayFile : grabTrx) {
//
//			String netAmt = grabPayFile.getNetAmt();
//			String ezyAmt = grabPayFile.getEzySettleAmt();
//
//			String TxnDate = grabPayFile.getPaymentDate();
//			logger.info(" TxnDate Grabpay = " + TxnDate);
//			String TxnFormatDate = null;
//			if (TxnDate != null && !TxnDate.isEmpty()) {
//				try {
//					TxnFormatDate = new SimpleDateFormat("yyyy-MM-dd")
//							.format(new SimpleDateFormat("dd-MMM-yyyy").parse(TxnDate));
//					logger.info(" TxnFormatDate Grabpay = " + TxnFormatDate);
//				} catch (ParseException e) {
//					logger.error("Exception in updatesettledatebyGrabpay : " + e.getMessage() + e);
//				}
//			}
//
//			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
//			payoutSettleBean.setCurrentMerchantid(currentMerchantid);
//			payoutSettleBean.setNetAmt(netAmt);
//			payoutSettleBean.setEzyAmt(ezyAmt);
//			payoutSettleBean.setFormatsettledate(formatsettledate);
//
//			updatecount = ezysettleDao.updateEzySettleAmountForGrabPay(payoutSettleBean);
//			updatecount += updatecount;
//		}
//
//		updatecount = updatecount / 2;
////		int grabTransactionRecheck = ezysettleDao.loadTransactionCountbyGrabpay(formatsettledate, merchant);
//		logger.info("updatecount for GrabPay  : " + updatecount);
//		if (updatecount != grabTrx.size()) {
//			this.revertEzySettleStatusForGrab(grabTrx, currentMerchantid, formatsettledate);
//			throw new RuntimeException(
//					"Method name: updatesettledatebyGrabpay => Grabpay transaction rechecked: Not all Grabpay transaction statuses were updated properly.");
//		} else {
//			return true;
//		}
//	}

	private void revertEzySettleStatusForGrab(List<GrabPayFile> grabTrx, long currentMerchantid,
			String formatsettledate) throws MobileApiException {

		for (GrabPayFile grabPayFile : grabTrx) {
			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setCurrentMerchantid(currentMerchantid);
			payoutSettleBean.setFormatsettledate(formatsettledate);
			payoutSettleBean.setStatus(grabPayFile.getStatus());
			ezysettleDao.revertEzySettleStatusForGrab(payoutSettleBean);
		}
	}

//	public boolean updatesettledatebyFpx(String settledate, double ezysettleFee, Merchant merchant)
//			throws MobileApiException {
//
//		String umMid = merchant.getMid().getUmMid() != null ? merchant.getMid().getUmMid() : "";
//		String umEzywayMid = merchant.getMid().getUmEzywayMid() != null ? merchant.getMid().getUmEzywayMid() : "";
//		String umMotoMid = merchant.getMid().getUmMotoMid() != null ? merchant.getMid().getUmMotoMid() : "";
//		String fpxmid = merchant.getMid().getFpxMid() != null ? merchant.getMid().getFpxMid() : "";
//
//		Session session = sessionFactory.getCurrentSession();
//
//		// Each transaction updates the EZYSETTLE Amount - Start
//		List<FpxTransaction> fpxTrx = transactionDAO.loadNetAmountbyFpx(settledate, ezysettleFee, merchant);
//
//		int updatecount = 0;
//		for (FpxTransaction fpxTransaction : fpxTrx) {
//
//			String netAmt = fpxTransaction.getPayableAmt();
//			String ezyAmt = fpxTransaction.getEzySettleAmt();
//			String TxnDate = fpxTransaction.getTxDate();
//
//			logger.info(" TxnDate FPX = " + TxnDate);
//
//			String TxnFormatDate = null;
//			if (TxnDate != null && !TxnDate.isEmpty()) {
//				try {
//					TxnFormatDate = new SimpleDateFormat("yyyy-MM-dd")
//							.format(new SimpleDateFormat("dd-MMM-yyyy").parse(TxnDate));
//					logger.info(" TxnFormatDate FPX = " + TxnFormatDate);
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
//
//			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
//			payoutSettleBean.setUmMid(umMid);
//			payoutSettleBean.setUmEzywayMid(umEzywayMid);
//			payoutSettleBean.setUmMotoMid(umMotoMid);
//			payoutSettleBean.setFpxmid(fpxmid);
//			payoutSettleBean.setSettledate(settledate);
//			payoutSettleBean.setNetAmt(netAmt);
//			payoutSettleBean.setEzyAmt(ezyAmt);
//			payoutSettleBean.setTxnFormatDate(TxnFormatDate);
//
//			updatecount = ezysettleDao.updateEzySettleAmountForFpx(payoutSettleBean);
//			updatecount += updatecount;
//		}
//
//		updatecount = updatecount / 2;
//
////		int fpxTransactionRecheck = ezysettleDao.loadTransactionCountbyFpx(settledate, merchant);
//		logger.info("updatecount for Fpx  : " + updatecount);
//		if (updatecount != fpxTrx.size()) {
//			this.revertEzySettleStatusForFpx(fpxTrx, umMid, umEzywayMid, umMotoMid, fpxmid, settledate);
//			throw new RuntimeException(
//					"Method name: updatesettledatebyFpx => Fpx transaction rechecked: Not all Fpx transaction statuses were updated properly.");
//		} else {
//			return true;
//		}
//
//	}

	private void revertEzySettleStatusForFpx(List<FpxTransaction> fpxTrx, String umMid, String umEzywayMid,
			String umMotoMid, String fpxmid, String settledate) throws MobileApiException {

		for (FpxTransaction fpxTransaction : fpxTrx) {

			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setUmMid(umMid);
			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setFpxmid(fpxmid);
			payoutSettleBean.setSettledate(settledate);
			payoutSettleBean.setSettledate(settledate);

			ezysettleDao.revertEzySettleStatusForFpx(payoutSettleBean);

		}
	}

//	public boolean updatesettledatebym1Pay(String settledate, double ezysettleFee, Merchant merchant)
//			throws ParseException, MobileApiException {
//
//		String formatsettledate = null;
//
//		formatsettledate = new SimpleDateFormat("yyyy-MM-dd")
//				.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settledate));
//
//		String tngMid = merchant.getMid().getTngMid() != null ? merchant.getMid().getTngMid() : "";
//		String shoppyMid = merchant.getMid().getShoppyMid() != null ? merchant.getMid().getShoppyMid() : "";
//		String umEzywayMid = merchant.getMid().getUmEzywayMid() != null ? merchant.getMid().getUmEzywayMid() : "";
//		String umMotoMid = merchant.getMid().getUmMotoMid() != null ? merchant.getMid().getUmMotoMid() : "";
//
//		Session session = sessionFactory.getCurrentSession();
//		// Each transaction updates the EZYSETTLE Amount - Start
//		logger.info("For all check formatsettledate :" + formatsettledate);
//		List<EwalletTxnDetails> m1payTrx = transactionDAO.loadNetAmountbym1Pay(formatsettledate, ezysettleFee,
//				merchant);
//		logger.info("first Execution ");
//		int updatecount = 0;
//		for (EwalletTxnDetails ewalletTxnDetails : m1payTrx) {
//
//			String netAmt = ewalletTxnDetails.getPayableAmt();
//			String ezyAmt = ewalletTxnDetails.getEzySettleAmt();
//
//			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
//			payoutSettleBean.setTngMid(tngMid);
//			payoutSettleBean.setShoppyMid(shoppyMid);
//			payoutSettleBean.setFormatsettledate(formatsettledate);
//			payoutSettleBean.setNetAmt(netAmt);
//			payoutSettleBean.setEzyAmt(ezyAmt);
//			payoutSettleBean.setUmEzywayMid(umEzywayMid);
//			payoutSettleBean.setUmMotoMid(umMotoMid);
//			payoutSettleBean.setStatus(ewalletTxnDetails.getStatus());
//
//			updatecount = ezysettleDao.updateEzySettleAmountForm1pay(payoutSettleBean);
//			updatecount += updatecount;
//		}
//
//		logger.info("********** updatecount M1pay before :" + updatecount);
//		updatecount = updatecount / 2;
////		int m1PayTransactionRecheck = ezysettleDao.loadTransactionCountbym1Pay(formatsettledate, tngMid, shoppyMid,
////				umEzywayMid, umMotoMid, merchant);
////		logger.info("m1payTrx : " + m1payTrx.size() + " m1PayTransactionRecheck :" + m1PayTransactionRecheck);
//
//		/* If the recheck count doesn't match the original count, revert the status */
//		logger.info("updatecount for M1pay  : " + updatecount);
//		if (updatecount != m1payTrx.size()) {
//			this.revertEzySettleStatusForM1pay(m1payTrx, tngMid, shoppyMid, umEzywayMid, umMotoMid, formatsettledate);
//			throw new RuntimeException(
//					"Method name: updatesettledatebym1pay => M1pay transaction rechecked: Not all M1pay transaction statuses were updated properly.");
//		} else {
//			return true;
//		}
//
//	}

	private void revertEzySettleStatusForM1pay(List<EwalletTxnDetails> m1payTrx, String tngMid, String shoppyMid,
			String umEzywayMid, String umMotoMid, String formatsettledate) throws MobileApiException {
		for (EwalletTxnDetails ewalletTxnDetails : m1payTrx) {

			PayoutSettleBean payoutSettleBean = new PayoutSettleBean();
			payoutSettleBean.setTngMid(tngMid);
			payoutSettleBean.setShoppyMid(shoppyMid);
			payoutSettleBean.setFormatsettledate(formatsettledate);

			payoutSettleBean.setUmEzywayMid(umEzywayMid);
			payoutSettleBean.setUmMotoMid(umMotoMid);
			payoutSettleBean.setStatus(ewalletTxnDetails.getStatus());

			ezysettleDao.revertEzySettleStatusForM1pay(payoutSettleBean);
		}
	}

	public void updateMerchantCount(String sTotalCount, String currentid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"update mobiversa.MERCHANT m  set m.JUSTSETTLE_SWTCOUNT = :sTotalCount  where m.ID = :currentid ");
		query.setString("sTotalCount", sTotalCount);
		query.setString("currentid", currentid);
		query.executeUpdate();

	}

	// update checkbox count

	public void updateCheckboxCount(String sTotalCount, String currentid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"update mobiversa.MERCHANT m  set m.JS_CHECKBOX_COUNT = :sTotalCount  where m.ID = :currentid ");
		query.setString("sTotalCount", sTotalCount);
		query.setString("currentid", currentid);
		query.executeUpdate();

	}

	// update withdraw count

	public void updateWithdrawCount(String sTotalCount, String currentid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"update mobiversa.MERCHANT m  set m.JS_WITHDRAW_COUNT = :sTotalCount  where m.ID = :currentid ");
		query.setString("sTotalCount", sTotalCount);
		query.setString("currentid", currentid);
		query.executeUpdate();

	}

	// EARLY SETTLEMENT - END

	// EZYSETTLE SUMMARY BY ADMIN - START (27/03/2022)

//	public void ListofEzySettleSummary(final PaginationBean<SettlementModel> paginationBean, final String data,
//			final String date1, final String txntype) {
//
//		transactionDAO.ListofEzySettleSummary(paginationBean, data, date1, txntype);
//	}

	// EZYSETTLE SUMMARY BY ADMIN - END (27/03/2022)

	// M1 PAY SUMMARY BY ADMIN - START (29/07/2022)

	public void ListofM1PaySummary(final PaginationBean<SettlementModel> paginationBean, final String data,
			final String date1, final String txntype, String export) {

		transactionDAO.ListofM1PaySummary(paginationBean, data, date1, txntype, export);
	}

	// M1 PAY SUMMARY BY ADMIN - END (29/07/2022)

	// BNPL SUMMARY BY ADMIN - START (30/11/2022)

	public void ListofBnplSummary(final PaginationBean<SettlementModel> paginationBean, final String data,
			final String date1, final String txntype) {

		transactionDAO.ListofBnplSummary(paginationBean, data, date1, txntype);
	}

	// BNPL SUMMARY BY ADMIN - END (30/11/2022)

	// RK PORTAL(28/06/22) Ezysettle By Merchant Start
//	public void ListofEzySettleSummarymerchant(final PaginationBean<SettlementModel> paginationBean, final String data,
//			final String date1, final String txntype, Merchant currentMerchant) {
//
//		transactionDAO.ListofEzySettleSummarymerchant(paginationBean, data, date1, txntype, currentMerchant);
//	}
	// RK PORTAL(28/06/22) End

	// PREAUTH SUMMARY BY MERCHANT - START (04/05/2022)

	public void PreAuthList(PaginationBean<PreauthModel> paginationBean, String data, String date1,
			Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.PreAuthList(paginationBean, criterionList, data, date1, currentMerchant);

	}

	public void PreAuthListExport(PaginationBean<PreauthModel> paginationBean, String data, String date1,
			Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.PreAuthListExport(paginationBean, criterionList, data, date1, currentMerchant);

	}

	public void PreAuthList1(PaginationBean<PreauthModel> paginationBean, String data, String date1,
			Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.PreAuthList1(paginationBean, criterionList, data, date1, currentMerchant);

	}

	public void PreAuthList1Export(PaginationBean<PreauthModel> paginationBean, String data, String date1,
			Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.PreAuthList1Export(paginationBean, criterionList, data, date1, currentMerchant);

	}

	public PreAuthorization loadPreAuthorizationbyTxnId(BigInteger txid) {
		return transactionDAO.loadPreAuthorizationbyTxnId(txid);
	}

	public UMEcomTxnResponse loadUMEcomTxnResponsebyTxnId(String txid) {
		return transactionDAO.loadUMEcomTxnResponsebyTxnId(txid);
	}

	// PREAUTH SUMMARY BY MERCHANT - END (04/05/2022)

	// PAYOUT BY DHINESH & RK - START

	public void listPayoutTransaction(final PaginationBean<PayoutModel> paginationBean, final String data,
			final String date1, String export) {

		transactionDAO.listPayoutTransaction(paginationBean, data, date1, export);
	}

	public void listPayoutTransactionByMerchant(final PaginationBean<PayoutModel> paginationBean, final String data,
			final String date1, String merchant) {

		transactionDAO.listPayoutTransactionByMerchant(paginationBean, data, date1, merchant);
	}

	// With Draw for MI TRADE

	public void withDrawAmount(final PaginationBean<PayoutModel> paginationBean, String merchant) {

		transactionDAO.withDrawAmount(paginationBean, merchant);
	}

	public void PayoutCallBack(String txnId, String ipnUrl) {

		PayoutDetail payoutDetail = transactionDAO.loadPayoutDetailByTxnId(txnId);

		if (payoutDetail == null) {
			logger.info("No records Found");
		} else {

			PayoutCallBack data = new PayoutCallBack();

			data.setMerchantName(payoutDetail.getCreatedBy());
			data.setBankAccNo(payoutDetail.getPayeeAccNumber());
			data.setBankName(payoutDetail.getPayeeBankName());
			data.setBusinessRegNo(payoutDetail.getPayeeBRN());
			data.setAmount(payoutDetail.getPayoutAmount());
			data.setPayoutStatus("Paid");
			data.setPayoutDate(payoutDetail.getPayoutDate());
			data.setCustomerName(payoutDetail.getPayeeName());
			data.setTrxId(payoutDetail.getInvoiceIdProof());
			data.setPaidDate(payoutDetail.getPaidDate());
			data.setPaidTime(payoutDetail.getPaidTime());

			// Start -Sending IPN Callback value
			Gson gson = new Gson();
			String requestBody = gson.toJson(data);
			logger.info("requestBody:::" + requestBody);

			String urlString = null;
			StringBuffer urlStringBuffer = new StringBuffer();

			String requrl = ipnUrl;
			urlStringBuffer.append(requrl);
			urlStringBuffer.append("?callBack=" + URLEncoder.encode(requestBody));
			urlString = urlStringBuffer.toString();

			logger.info("urlString : " + urlString);

			String line1 = null;

			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request1 = new HttpGet(urlString);

			try {
				HttpResponse response1 = client.execute(request1);
				HttpEntity entity = response1.getEntity();
				if (entity != null) {
					try (InputStream stream = entity.getContent()) {
						BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
						String line;
						while ((line = reader.readLine()) != null) {
							logger.info("Response : " + line1);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// End -Sending IPN Callback value
		}

	}

	// PAYOUT BY DHINESH & RK - END

	public EwalletTxnDetails loadEwalletTxnDetails(String mrn) {
		return transactionDAO.loadEwalletTxnDetails(mrn);
	}

	// BNPL - loadBnplTxnDetails

	public BnplTxnDetails loadBnplTxnDetails(String mrn) {
		return transactionDAO.loadBnplTxnDetails(mrn);
	}

	public void merchantEwallet(PaginationBean<UMEzyway> paginationBean, String data, String date1, String umEzywayMid,
			String paydeeEzywaymid, String ummid, String motomid, String ezyrecmid, String mid, String bnplMid,
			String boostmid, String tngMid, String shoppyMid, String grabmid, String txnType,
			Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.merchantEwallet(paginationBean, criterionList, data, date1, umEzywayMid, paydeeEzywaymid, ummid,
				motomid, ezyrecmid, mid, bnplMid, boostmid, tngMid, shoppyMid, grabmid, txnType, currentMerchant);

	}

	public void merchantEwallet1(PaginationBean<UMEzyway> paginationBean, String data, String date1, String umEzywayMid,
			String paydeeEzywaymid, String ummid, String motomid, String ezyrecmid, String mid, String bnplMid,
			String boostmid, String tngMid, String shoppyMid, String grabmid, String txnType,
			Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.merchantEwallet1(paginationBean, criterionList, data, date1, umEzywayMid, paydeeEzywaymid, ummid,
				motomid, ezyrecmid, mid, bnplMid, boostmid, tngMid, shoppyMid, grabmid, txnType, currentMerchant);

	}

	public void merchantFpxtranscation(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String fpxmid, String ummotomid, String mid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.merchantFpxtranscation(paginationBean, criterionList, data, date1, umEzywayMid, fpxmid,
				ummotomid, mid, txnType, currentMerchant);
	}

	public void merchantFpxtranscation1(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String fpxmid, String ummotomid, String mid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.merchantFpxtranscation1(paginationBean, criterionList, data, date1, umEzywayMid, fpxmid,
				ummotomid, mid, txnType, currentMerchant);
	}

	public void listAllForsettlementTransactionSearchAPI(final PaginationBean<ForSettlement> paginationBean,
			final String data, final String date1, String txnType, String VALUE) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllForsettlementTransactionSearchAPI(paginationBean, criterionList, data, date1, txnType,
				VALUE);
	}

	public void ListofM1PaySummarySearchApi(final PaginationBean<SettlementModel> paginationBean, final String data,
			final String date1, String txnType, String VALUE) {

		transactionDAO.ListofM1PaySummarySearchApi(paginationBean, data, date1, txnType, VALUE);
	}

	public void ListofBnplSummarySearchApi(final PaginationBean<SettlementModel> paginationBean, final String data,
			final String date1, final String txntype, final String VALUE) {

		transactionDAO.ListofBnplSummarySearchApi(paginationBean, data, date1, txntype, VALUE);
	}

	public void listUMEzywayTransactionSearchApi(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType, String VALUE) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywayTransactionSearchApi(paginationBean, criterionList, data, date1, txnType, VALUE);

	}

	public void listAllUmEzywireTransactionSearchAPI(final PaginationBean<ForSettlement> paginationBean,
			final String data, final String date1, String txnType, String VALUE) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listAllUmEzywireTransactionSearchAPI(paginationBean, criterionList, data, date1, txnType, VALUE);
	}

	public void listUMMotoTransactionSearchApi(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType, String VALUE) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMMotoTransactionSearchApi(paginationBean, criterionList, data, date1, txnType, VALUE);

	}

	public void listUMLinkTransactionSearchAPI(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType, String VALUE) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMLinkTransactionSearchAPI(paginationBean, criterionList, data, date1, txnType, VALUE);

	}

	public void listUMEzyauthTransactionSearchAPI(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String txnType, String VALUE) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzyauthTransactionSearchAPI(paginationBean, criterionList, data, date1, txnType, VALUE);

	}

	// UM-EZYWIRE PAGINATION CHANGES

	public void listUMEzywireTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listUMEzywireTransaction(paginationBean, criterionList, data, date1, umEzywayMid, txnType,
				currentMerchant);

	}

	public void exportUMEzywireTransaction(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.exportUMEzywireTransaction(paginationBean, criterionList, data, date1, umEzywayMid, txnType,
				currentMerchant);

	}

	public void loadPayoutbalance(PaginationBean<PayoutModel> paginationBean, Long id) {
		// TODO Auto-generated method stub
		transactionDAO.loadPayoutbalance(paginationBean, id);
	}

	public List<Settlementbalance> loadbankbalance() {
		// TODO Auto-generated method stub
		return transactionDAO.loadbankbalance();
	}

	public List<EwalletTxnDetails> loadRefundsettlementdatebym1Pay(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadRefundsettlementdatebym1Pay Service ");

		return transactionDAO.loadRefundsettlementdatebym1Pay(settlementdate, merchant);
	}

	public List<FpxTransaction> loadRefundAndSettlementdatebyFpx(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadRefundAndSettlementdatebyFpx Service  ");

		return transactionDAO.loadRefundAndSettlementdatebyFpx(settlementdate, merchant);
	}

	public List<GrabPayFile> loadRefundAndSettlementdatebyGrabpay(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebyGrabpay Service  ");

		return transactionDAO.loadRefundAndSettlementdatebyGrabpay(settlementdate, merchant);
	}

	public List<BoostDailyRecon> loadRefundAndSettlementdatebyBoost(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadRefundAndSettlementdatebyBoost Service  ");

		return transactionDAO.loadRefundAndSettlementdatebyBoost(settlementdate, merchant);
	}

	public List<SettlementMDR> loadRefundAndSettlementdatebyCard(String settlementdate, Merchant merchant) {
		logger.info(" Inside loadNetAmountandsettlementdatebyCard Service  ");

		return transactionDAO.loadRefundAndSettlementdatebyCard(settlementdate, merchant);
	}

//public ArrayList<RefundRequest> listRefund(String date) {
//	logger.info(" Inside listRefund Service  ");
//
//	return transactionDAO.listRefund(date);
//}
	public void listRefund(PaginationBean<RefundRequest> paginationBean, String date) {
		logger.info(" Inside listRefund Service  ");

		transactionDAO.listRefund(paginationBean, date);
	}

	public void searchRefundTransaction(PaginationBean<RefundRequest> paginationBean, String transactionId,
			String selectType) {
		logger.info(" Inside listRefund Service  ");

		transactionDAO.searchRefundTransaction(paginationBean, transactionId, selectType);
	}

	public void merchantFpxtranscationSearch(PaginationBean<UMEzyway> paginationBean, String data, String date1,
			String umEzywayMid, String fpxmid, String ummotomid, String mid, String txnType, Merchant currentMerchant,
			String value) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.merchantFpxtranscationSearch(paginationBean, criterionList, data, date1, umEzywayMid, fpxmid,
				ummotomid, mid, txnType, currentMerchant, value);
	}

	public void searchPayoutTransactionList(final PaginationBean<PayoutModel> paginationBean, final String payoutId) {
		transactionDAO.searchPayoutTransactionList(paginationBean, payoutId);
	}

	public void listFPXSettlementsearchexport(PaginationBean<FpxTransaction> paginationBean, String fromDate) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (fromDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			String dateorg2 = fromDay + "" + mon + "" + year;

			try {
				from = new SimpleDateFormat("yyMMdd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
			try {
				from = dateFormat.format(new SimpleDateFormat("yyMMdd").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from);

		transactionDAO.listFPXSettlementsearchexport(paginationBean, from);
	}

	public void payoutTxnList(PaginationBean<PayoutModel> paginationBean, String data, String date1) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.payoutTxnList(paginationBean, criterionList, data, date1);

	}

	public GrabPayFile loadGrabPayFile(final String rrn) {
		return transactionDAO.loadGrabPayFile(rrn);
	}

	public String getTotalRefundAmountProcessed(String settlementDate, String merchant) {
		return transactionDAO.getTotalRefundAmountProcessed(settlementDate, merchant);
	}

	public List<Object[]> getBusinessNamesAndUsernames() {
		return transactionDAO.getBusinessNamesAndUsernames();
	}

	// export settlement report for FPX

	public void FpxMonthlySettlementSummaryexport(PaginationBean<FpxTransaction> paginationBean, String fromDate,
			String businessName, String username) {
		String from = null;

		if (((fromDate == null) || (fromDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			String dateorg2 = mon + "-" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM").format(new SimpleDateFormat("yyyy/MM").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

		} else {

			try {
				// Parse the input date using the correct format
				Date parsedDate = new SimpleDateFormat("MM/yyyy").parse(fromDate);
				from = new SimpleDateFormat("yyyy-MM").format(parsedDate);
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		logger.info("checkd date: " + from);

		transactionDAO.FpxMonthlySettlementSummaryexport(paginationBean, from, businessName, username);
	}

	// export settlement report for Boost

	public void BoostMonthlySettlementSummaryexport(PaginationBean<BoostDailyRecon> paginationBean, String fromDate,
			String businessName, String username) {
		String from = null;

		if (((fromDate == null) || (fromDate.equals("")))) {
			// If fromDate is not provided, use the current date
			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			String dateorg2 = mon + "-" + year;

			try {
				from = new SimpleDateFormat("yyyyMM").format(new SimpleDateFormat("MM-yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);
		} else {
			// Parse the input date using the correct format
			try {
				Date parsedDate = new SimpleDateFormat("MM/yyyy").parse(fromDate);
				from = new SimpleDateFormat("yyyyMM").format(parsedDate);
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		logger.info("checked date: " + from);
		transactionDAO.BoostMonthlySettlementSummaryexport(paginationBean, from, businessName, username);
	}

	// For Host Bank Switch
	public List<String> getHostBankList() {
		return transactionDAO.getHostBankList();
	}

	public void updateAllMerchantSellerID(String selectedBank) {

		int responseCode;
		String fpxModuleResponseStatus = null;
		String responseData = null;
		String jsonString = null;
		List<Long> emptyList = new ArrayList<Long>();
		try {
			String sellerId = transactionDAO.getSellerIdByBankName(selectedBank).trim();

			if (Objects.isNull(sellerId) && sellerId.trim().isEmpty()) {
				logger.error("SellerId : " + sellerId + " for the Bank : " + selectedBank);
				throw new RuntimeException("SellerId : " + sellerId + " for the Bank : " + selectedBank);
			}

			// Triggering FpxModule to Update the MobileUserTable SellerId Column,For All
			// mer
			URL url = new URL(PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));
			logger.info("FPX_BANK_SWITCH_API_URL :" + PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			JsonRequestDto requestDto = new JsonRequestDto(emptyList, sellerId, "all");
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				jsonString = objectMapper.writeValueAsString(requestDto);
				logger.info("Request Body Send to FPX Module : " + jsonString);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception Wile Parsing data : " + e.getMessage());
			}

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			responseCode = connection.getResponseCode();

			if (responseCode != HttpURLConnection.HTTP_OK) {

				logger.error("Triggering FPX module request failed with response code: " + responseCode);
			} else {

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					responseData = response.toString(); // Store the response data

					JSONObject jsonObject = new JSONObject(responseData);
					fpxModuleResponseStatus = jsonObject.getString("status");

					logger.info("Triggering FPX module request Success with Http Response code: " + responseCode + " : "
							+ fpxModuleResponseStatus + ", Response Data : " + responseData);

					if (fpxModuleResponseStatus.equalsIgnoreCase("Success")) {

						// If the Responsd from FPX module is success, Send email
						ExecutorService executor = Executors.newSingleThreadExecutor();
						CompletableFuture.runAsync(() -> {
							sendAcknowledgementEmailForFPXBankSwitch(null, selectedBank, "All Merchants", null);
						}, executor).whenComplete((result, throwable) -> {
							executor.shutdown();
						});
					}
				}
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Connecting With Fpx module, Or Internal Excepyion : " + e.getMessage(), e);
		}
	}

//	public void updateAllMerchantSellerID(String selectedBank) {
//
//		int responseCode;
//		String fpxModuleResponseStatus = null;
//		String responseData = null;
//		String jsonString = null;
//		List<Long> emptyList = new ArrayList<Long>();
//		try {
//			String sellerId = transactionDAO.getSellerIdByBankName(selectedBank).trim();
//
//			if (Objects.isNull(sellerId) && sellerId.trim().isEmpty()) {
//				logger.error("SellerId : " + sellerId + " for the Bank : " + selectedBank);
//				throw new RuntimeException("SellerId : " + sellerId + " for the Bank : " + selectedBank);
//			}
//
//			// Triggering FpxModule to Update the MobileUserTable SellerId Column,For All
//			// mer
//			URL url = new URL(PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));
//			logger.info("FPX_BANK_SWITCH_API_URL :" + PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));
//
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//			connection.setRequestMethod("POST");
//			connection.setDoOutput(true);
//			connection.setRequestProperty("Content-Type", "application/json");
//
//			JsonRequestDto requestDto = new JsonRequestDto(emptyList, sellerId, "all");
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			try {
//				jsonString = objectMapper.writeValueAsString(requestDto);
//				logger.info("Request Body Send to FPX Module : " + jsonString);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("Exception Wile Parsing data : " + e.getMessage());
//			}
//
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//
//			responseCode = connection.getResponseCode();
//
//			if (responseCode != HttpURLConnection.HTTP_OK) {
//
//				logger.error("Triggering FPX module request failed with response code: " + responseCode);
//			} else {
//
//				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//					StringBuilder response = new StringBuilder();
//					String line;
//					while ((line = reader.readLine()) != null) {
//						response.append(line);
//					}
//					responseData = response.toString(); // Store the response data
//
//					JSONObject jsonObject = new JSONObject(responseData);
//					fpxModuleResponseStatus = jsonObject.getString("status");
//
//					logger.info("Triggering FPX module request Success with Http Response code: " + responseCode
//							+ ", Response Data : " + responseData);
//				}
//			}
//
//			connection.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while Connecting With Fpx module, Or Internal Excepyion : " + e.getMessage());
//		}
//	}

//	public void updateSelectedMerchantSellerID(String selectedBank, List<String> merchantIdList) {
//
//		int responseCode;
//		String fpxModuleResponseStatus = null;
//		String responseData = null;
//		String jsonString = null;
//
//		try {
//			// Convert List<String> to List<Long>
//			List<Long> longMerchantIDList = merchantIdList.stream()
//					.flatMap(s -> Arrays.stream(s.replaceAll("[\\[\\]\"]", "").split(", ")))
//					.map(toLong -> Long.valueOf(toLong)).filter(value -> value != null).collect(Collectors.toList());
//
//			// Obtaining SellerId for the corresponding bank
//			String sellerId = transactionDAO.getSellerIdByBankName(selectedBank).trim();
//
//			if (Objects.isNull(sellerId) && sellerId.trim().isEmpty()) {
//				logger.error("SellerId : " + sellerId + " for the Bank : " + selectedBank);
//				throw new RuntimeException("SellerId : " + sellerId + " for the Bank : " + selectedBank);
//			}
//
//			// Triggering Fpx Module to Update the MobileUser table's Seller_Id Column
//			URL url = new URL(PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));
//			logger.info("FPX_BANK_SWITCH_API_URL :" + PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));
//
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//			connection.setRequestMethod("POST");
//			connection.setDoOutput(true);
//			connection.setRequestProperty("Content-Type", "application/json");
//
//			JsonRequestDto requestDto = new JsonRequestDto(longMerchantIDList, sellerId, "selected");
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			try {
//				jsonString = objectMapper.writeValueAsString(requestDto);
//				logger.info("Request Body Send to FPX Module : " + jsonString);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.error("Exception Wile Parsing data : " + e.getMessage());
//			}
//
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//
//			responseCode = connection.getResponseCode();
//
//			if (responseCode != HttpURLConnection.HTTP_OK) {
//
//				logger.error("Triggering FPX module request failed with response code: " + responseCode);
//			} else {
//
//				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//					StringBuilder response = new StringBuilder();
//					String line;
//					while ((line = reader.readLine()) != null) {
//						response.append(line);
//					}
//					responseData = response.toString(); // Store the response data
//
//					JSONObject jsonObject = new JSONObject(responseData);
//					fpxModuleResponseStatus = jsonObject.getString("status");
//
//					logger.info("Triggering FPX module request Success with Http Response code: " + responseCode
//							+ ", Response Data : " + responseData);
//				}
//			}
//
//			connection.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while Connecting With Fpx module, Or Internal Exception : " + e.getMessage());
//		}
//	}

	public void updateSelectedMerchantSellerID(String selectedBank, List<String> merchantIdList) {

		int responseCode;
		String fpxModuleResponseStatus = null;
		String responseData = null;
		String jsonString = null;

		try {
			// Convert List<String> to List<Long>
			List<Long> longMerchantIDList = merchantIdList.stream()
					.flatMap(s -> Arrays.stream(s.replaceAll("[\\[\\]\"]", "").split(", ")))
					.map(toLong -> Long.valueOf(toLong)).filter(value -> value != null).collect(Collectors.toList());

			// Obtaining SellerId for the corresponding bank
			String sellerId = transactionDAO.getSellerIdByBankName(selectedBank).trim();

			if (Objects.isNull(sellerId) && sellerId.trim().isEmpty()) {
				logger.error("SellerId : " + sellerId + " for the Bank : " + selectedBank);
				throw new RuntimeException("SellerId : " + sellerId + " for the Bank : " + selectedBank);
			}

			// Obtaining the previous bank information for a single merchant's bank switch
			final String previousBank;
			if (longMerchantIDList != null && !longMerchantIDList.isEmpty() && longMerchantIDList.size() == 1) {
				previousBank = transactionDAO.getBankNameByMerchantId(String.valueOf(longMerchantIDList.get(0)));
			} else {
				previousBank = null;
			}

			// Triggering Fpx Module to Update the MobileUser table's Seller_Id Column
			URL url = new URL(PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));
			logger.info("FPX_BANK_SWITCH_API_URL :" + PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_API_URL"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			JsonRequestDto requestDto = new JsonRequestDto(longMerchantIDList, sellerId, "selected");
			ObjectMapper objectMapper = new ObjectMapper();

			try {
				jsonString = objectMapper.writeValueAsString(requestDto);
				logger.info("Request Body Send to FPX Module : " + jsonString);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception Wile Parsing data : " + e.getMessage());
			}

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			responseCode = connection.getResponseCode();

			if (responseCode != HttpURLConnection.HTTP_OK) {

				logger.error("Triggering FPX module request failed with response code: " + responseCode);
			} else {

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					responseData = response.toString(); // Store the response data

					JSONObject jsonObject = new JSONObject(responseData);
					fpxModuleResponseStatus = jsonObject.getString("status");

					logger.info("Triggering FPX module request Success with Http Response code: " + responseCode + " :"
							+ fpxModuleResponseStatus + ", Response Data : " + responseData);

					if (fpxModuleResponseStatus.equalsIgnoreCase("Success")) {

						// If the Responsd from FPX module is success, Send email
						ExecutorService executor = Executors.newSingleThreadExecutor();
						CompletableFuture.runAsync(() -> {

							List<Merchant> merchantsDetails = transactionDAO
									.getListOfMerchantsByMerchantId(longMerchantIDList);

							String businessNameString = merchantsDetails.stream().map(Merchant::getBusinessName)
									.collect(Collectors.joining(","));

							sendAcknowledgementEmailForFPXBankSwitch(previousBank, selectedBank, businessNameString,
									longMerchantIDList);
						}, executor);
					}
				}
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Connecting With Fpx module, Or Internal Exception : " + e.getMessage(), e);
		}
	}

	public static String sendAcknowledgementEmailForFPXBankSwitch(String previousHostBank, String newHostBank,
			String merchantName, List<Long> merchantId) {

		try {
			String emailFrom = PropertyLoad.getFile().getProperty("FROMMAIL");
			String subject = PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_SUBJECT");
			String fromName = PropertyLoad.getFile().getProperty("FROMNAME");
			String toAddress = PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_MAIL_TO");
			String ccAddress = PropertyLoad.getFile().getProperty("FPX_BANK_SWITCH_MAIL_CC");

			String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH));
			String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

			StringBuilder emailContent = new StringBuilder();

			emailContent.append("<html>");
			emailContent.append("<head>");
			emailContent.append("<title>Transition Completed</title>");
			emailContent.append("<style>");
			emailContent.append("table {");
			emailContent.append("  width: 100%;");
			emailContent.append("  border-collapse: collapse;");
			emailContent.append("}");
			emailContent.append("table, th, td {");
			emailContent.append("  border: 1px solid black;");
			emailContent.append("  padding: 8px;");
			emailContent.append("}");
			emailContent.append("th {");
			emailContent.append("  text-align: left;");
			emailContent.append("}");
			emailContent.append("</style>");
			emailContent.append("</head>");
			emailContent.append("<body>");
			emailContent.append("<p>Hi Team,</p>");
			emailContent.append("<p>The transition to the new host bank has been completed successfully.</p>");
			emailContent.append("<table>");
			emailContent.append("<tr><th>Date:</th><td>" + currentDate + "</td></tr>");
			emailContent.append("<tr><th>Time:</th><td>" + currentTime + "</td></tr>");
			emailContent.append("<tr><th>Merchant Name:</th><td>" + merchantName + "</td></tr>");

			if (previousHostBank != null) {
				emailContent.append("<tr><th>Old Host Bank:</th><td>" + previousHostBank + "</td></tr>");
			}
			emailContent.append("<tr><th>New Host Bank:</th><td>" + newHostBank + "</td></tr>");
			emailContent.append("</table>");
			emailContent.append("<br/><br/>");
			emailContent.append("<p>Thank you.</p>");
			emailContent.append("<p>Best Regards,</p>");
			emailContent.append("</body>");
			emailContent.append("</html>");

			int mailResponse = ElasticEmailClient.sendemailSlip(emailFrom, subject, fromName, toAddress, ccAddress,
					null, emailContent.toString());
			logger.info("Email Sent Successfully: " + mailResponse + " to " + toAddress);

			return String.valueOf(mailResponse);
		} catch (Exception e) {
			logger.error("Exception while sending mail : " + e.getMessage(), e);
			return "400";
		}
	}

	public List<Object[]> loadpayoutTxn(String invoiceid) {
		return transactionDAO.loadpayoutTxn(invoiceid);
	}

	// Filter
	public void listPayoutIdSearchApi(PaginationBean<UMEzyway> paginationBean, String txnType, String VALUE) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.listPayoutIdSearchApi(paginationBean, criterionList, txnType, VALUE);

	}

	/* FPX Failed Summary Start */

	public void merchantfailedFpxtranscation(PaginationBean<FpxTransaction> paginationBean, String data, String date1,
			String umEzywayMid, String fpxmid, String ummotomid, String mid, String txnType, Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.merchantfailedFpxtranscation(paginationBean, criterionList, data, date1, umEzywayMid, fpxmid,
				ummotomid, mid, txnType, currentMerchant);
	}

	public void merchantfailedFpxtranscationexport(PaginationBean<FpxTransaction> paginationBean, String data,
			String date1, String umEzywayMid, String fpxmid, String ummotomid, String mid, String txnType,
			Merchant currentMerchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.merchantfailedFpxtranscationexport(paginationBean, criterionList, data, date1, umEzywayMid,
				fpxmid, ummotomid, mid, txnType, currentMerchant);
	}

	/* FPX Failed Summary end */

	/* filter */

	public void listFPXfailedTransaction(PaginationBean<FpxTransaction> paginationBean, String fromDate, String toDate,
			String VALUE, String TXNTYPE) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		// transactionDAO.listFPXTransaction(paginationBean, from, to);
		transactionDAO.listFPXfailedTransaction(paginationBean, from, to, VALUE, TXNTYPE);
	}

	/* filter */

	public Settlementbalance getPayoutBankBalance(Settlementbalance settlementbalance) throws MobiException {

		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = currentDateTime.format(formatter);

		// Load AmBank & OCBCBank details
		settlementbalance = transactionDAO.loadAmBankBalance(settlementbalance);
		settlementbalance = transactionDAO.loadOCBCBankBalance(settlementbalance);
		// Assign local date and time in TotalBalance timeStamp
		settlementbalance.setTotalBankBalanceUpdatedDate(formattedDateTime);

		double amBankBalance = Double.parseDouble(settlementbalance.getAmBankBalance().replace(",", ""));
		double ocbcBankBalance = Double.parseDouble(settlementbalance.getOcbcBankBalance().replace(",", ""));

		double totalBankBalance = Double.sum(amBankBalance, ocbcBankBalance);

		DecimalFormat formatterWithCommas = new DecimalFormat("#,###,###,##0.00");
		String formattedTotalBankBalance = formatterWithCommas.format(totalBankBalance);

		// Add OCBCBankBalance & AmBankBalance balance amount to TotalBankBalanceAmount
		settlementbalance.setTotalBankBalance(formattedTotalBankBalance);

		return settlementbalance;
	}

	public Object updateloadPayoutbalance(Long id) {
		return transactionDAO.updateloadPayoutbalance(id);
	}

	// boost export
	public void searchAllForsettlementTransactionForBoostExport(final PaginationBean<ForSettlement> paginationBean,
			final String data, final String date1, String txnType, String export) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		transactionDAO.searchAllForsettlementTransactionForBoostExport(paginationBean, criterionList, data, date1,
				txnType, export);
	}

	// UM CARD FAILED SEARCH FILTER

	public void listcardfailedTransaction(PaginationBean<UMEzyway> paginationBean, String fromDate, String toDate,
			String VALUE, String TXNTYPE) {
		String from = null;
		String to = null;

		if (((fromDate == null) || (toDate == null)) || (fromDate.equals("") || (toDate.equals("")))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int fromDay = date1.getDate() - 1;
			int toDay = date1.getDate() + 1;
			String dateorg2 = fromDay + "/" + mon + "/" + year;
			String dateorg1 = toDay + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("fromDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

			try {
				to = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
				System.out.println("toDate   " + to);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + to);

		} else {

			from = fromDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + fromDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			to = toDate;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				to = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(to));
				logger.info("date format:" + toDate);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from + ":::" + to);

		// transactionDAO.listFPXTransaction(paginationBean, from, to);
		transactionDAO.listcardfailedTransaction(paginationBean, from, to, VALUE, TXNTYPE);
	}

	// Karupasamy changes
	public void listPayoutTransactionByMerchant(final PaginationBean<PayoutModel> paginationBean, final String data,
			final String date1, String merchantID, int currPage) {
		transactionDAO.listPayoutTransactionByMerchantData(paginationBean, null, null, merchantID, currPage, null);
	}

	public void listPayoutTransactionByMerchantForCurrDate(final PaginationBean<PayoutModel> paginationBean,
			final String date, final String date1, String merchant, int currPage) {
		transactionDAO.listPayoutTransactionByMerchantData(paginationBean, date, date1, merchant, currPage, null);

	}

	public void listPayoutTransactionByMerchantExport(final PaginationBean<PayoutModel> paginationBean,
			final String data, final String date1, String merchant, int currPage, String export) {
		logger.info("for search pagination");
		transactionDAO.listPayoutTransactionByMerchantData(paginationBean, data, date1, merchant, currPage, export);
	}

	public void searchPayoutTransactionList(final PaginationBean<PayoutModel> paginationBean, final String value,
			final String transaction_type, String merchant) {
		transactionDAO.searchPayoutTransactionList(paginationBean, value, transaction_type, merchant);
	}

	public static byte[] generatePayoutDetailCSVContentForEmail(List<PayoutModel> payoutModel) {

//		logger.info("Bind CSV content from list.................................................................");

		StringBuilder writer = new StringBuilder();
		// COLUMN
		writer.append(Objects.requireNonNull(PropertyLoad.getFile().getProperty("PAYOUT_USER_CSV_EXPORT_HEADER"))
				.toUpperCase());
		writer.append(NEW_LINE_SEPARATOR);
		// ROW
		for (PayoutModel payoutDetail : payoutModel) {
//			payoutDetail.setCreateddate(payoutDetail.getCreateddate().replace("-", "/"));

			writer.append(payoutDetail.getPaidDate().replace("/", "-"));
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getPaidTime());

//			writer.append(payoutDetail.getCreateddate());
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getCreatedby());
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getPayeename());
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getPayeebrn());
			writer.append(COMMA_DELIMITER);
			writer.append(String.format("=\"%s\"", payoutDetail.getPayeeaccnumber()));
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getPayeebankname());
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getInvoiceidproof());
			writer.append(COMMA_DELIMITER);
			writer.append(String.format("=\"%s\"", payoutDetail.getPayoutamount()));
			writer.append(COMMA_DELIMITER);
			writer.append(String.format("=\"%s\"", payoutDetail.getPayoutfee()));
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getPayoutstatus());
			writer.append(COMMA_DELIMITER);
//			writer.append(payoutDetail.getPaidDate().replace("/", "-"));
//			writer.append(COMMA_DELIMITER);
//			writer.append(payoutDetail.getPaidTime());
//			writer.append(COMMA_DELIMITER);
			writer.append(String.format("=\"%s\"", payoutDetail.getSubmerchantMid()));
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getBusinessname());
			writer.append(COMMA_DELIMITER);
			writer.append(String.format("=\"%s\"", payoutDetail.getPayoutId()));
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getFailurereason());
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getPayouttype());
			writer.append(COMMA_DELIMITER);
			writer.append(payoutDetail.getCurlecRefNo());

			writer.append(NEW_LINE_SEPARATOR);
		}

//		logger.info("CSV Content: " + writer);
//		logger.info(Arrays.toString(writer.toString().getBytes(StandardCharsets.UTF_8)));
//		logger.info("CSV Content: " + writer.toString().getBytes(StandardCharsets.UTF_8));

		return writer.toString().getBytes(StandardCharsets.UTF_8);
	}

	public void searchPayoutLoginTransactionList(final PaginationBean<PayoutModel> paginationBean, final String value,
			final String transaction_type) {
		transactionDAO.searchPayoutLoginTransactionList(paginationBean, value, transaction_type);
	}

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	// Ezysettle v2 changes
	public SettlementMDR loadNetAmountandsettlementdatebyCardEzysettle(String settlementdate, Merchant merchant) {
		logger.info(" Inside loadNetAmountandsettlementdatebyCard Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyCardEzysettle(settlementdate, merchant);
	}

	public BoostDailyRecon loadNetAmountandsettlementdatebyBoostEzysettle(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebyBoost Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyBoostEzysettle(settlementdate, merchant);
	}

	public GrabPayFile loadNetAmountandsettlementdatebyGrabpayEzysettle(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebyGrabpay Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyGrabpayEzysettle(settlementdate, merchant);
	}

	public FpxTransaction loadNetAmountandsettlementdatebyFpxEzysettle(String settlementdate, Merchant merchant) {

		logger.info(" Inside loadNetAmountandsettlementdatebyFpx Service  ");

		return transactionDAO.loadNetAmountandsettlementdatebyFpxEzysettle(settlementdate, merchant);
	}

	public EwalletTxnDetails loadNetAmountandsettlementdatebym1PayEzysettle(String settlementdate, Merchant merchant) {
		logger.info(" Inside loadNetAmountandsettlementdatebym1Pay Service ");
		return transactionDAO.loadNetAmountandsettlementdatebym1PayEzysettle(settlementdate, merchant);
	}

	// withdraw and deposit

	public void listDepositDetails(PaginationBean<WithdrawDeposit> paginationBean, String fromDate, String toDate,
			int currPage) {

		transactionDAO.listDepositDetails(paginationBean, fromDate, toDate, currPage);
	}

	public void listDepositDetailsUsingId(PaginationBean<WithdrawDeposit> paginationBean, int currPage,
			String merchantId) {

		transactionDAO.listDepositDetailsUsingId(paginationBean, currPage, merchantId);
	}

	public void listWithdrawDetails(PaginationBean<WithdrawDeposit> paginationBean, String fromDate, String toDate,
			int currPage) {

		transactionDAO.listWithdrawDetails(paginationBean, fromDate, toDate, currPage);
	}

	public void listWithdrawDetailsUsingId(PaginationBean<WithdrawDeposit> paginationBean, int currPage,
			String merchantId) {

		transactionDAO.listWithdrawDetailsUsingId(paginationBean, currPage, merchantId);
	}

	public List<Object[]> getBusinessNamesAndIds() {
		return transactionDAO.getBusinessNamesAndIds();
	}

	// dhivya changes

	public List<FinanceReport> getPayinTxnDetailsBetweenDates(String fromDate, String toDate, String umEzywayMid,
			String fpxmid, String ummotomid, String ummid, String mid, String paydeeEzywaymid, String ezyrecmid,
			String bnplMid, String boostmid, String tngMid, String shoppyMid, String grabmid, String fiuuMid,
			Merchant currentMerchant) {
		return transactionDAO.getPayinTxnDetailsBetweenDates(fromDate, toDate, umEzywayMid, fpxmid, ummotomid, mid,
				ummid, paydeeEzywaymid, ezyrecmid, bnplMid, boostmid, tngMid, shoppyMid, grabmid, fiuuMid, currentMerchant);
	}

	public List<FinanceReport> getPayoutTxnDetailsBetweenDates(String fromDate, String toDate, Long Merchant,
			Merchant currentMerchant) {
		return transactionDAO.getPayoutTxnDetailsBetweenDates(fromDate, toDate, Merchant, currentMerchant);
	}

	public static byte[] generatePayinDetailExcelContentForEmail(List<FinanceReport> financeReport, String fromDate,
			String toDate, String businessName) {
		try (HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Payin Transaction Report");
			logger.info("Payin Transaction Report");

			// Set metadata
			int metaRowIndex = 0;

			HSSFRow reportTypeRow = sheet.createRow(metaRowIndex++);

			reportTypeRow.createCell(0).setCellValue("Report Type:");
			reportTypeRow.createCell(1).setCellValue("Payin Report");

			String date1 = convertDateFormatTo_dd_MM_yyyy(fromDate);
			String date2 = convertDateFormatTo_dd_MM_yyyy(toDate);
			HSSFRow fromDateRow = sheet.createRow(metaRowIndex++);
			fromDateRow.createCell(0).setCellValue("From");
			fromDateRow.createCell(1).setCellValue(date1);

			HSSFRow toDateRow = sheet.createRow(metaRowIndex++);
			toDateRow.createCell(0).setCellValue("To");
			toDateRow.createCell(1).setCellValue(date2);

			HSSFRow toTimeRow = sheet.createRow(metaRowIndex++);
			toTimeRow.createCell(0).setCellValue("Time");
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
			String formattedDateTime = now.format(formatter);
			toTimeRow.createCell(1).setCellValue(formattedDateTime);

			// Collect unique payment methods
			Set<String> paymentMethods = new HashSet<>();
			for (FinanceReport report : financeReport) {
				if (report.getPaymentmethod() != null && !report.getPaymentmethod().isEmpty()) {
					paymentMethods.add(report.getPaymentmethod());
				}
			}
			String paymentMethodsString = paymentMethods.stream().collect(Collectors.joining(","));

			// Add payment methods to the Nature row
			HSSFRow toNatureRow = sheet.createRow(metaRowIndex++);
			toNatureRow.createCell(0).setCellValue("Nature");
			toNatureRow.createCell(1).setCellValue(paymentMethodsString.isEmpty() ? null : paymentMethodsString);

			HSSFRow toMercNameRow = sheet.createRow(metaRowIndex++);
			toMercNameRow.createCell(0).setCellValue("Merchant Name");
			toMercNameRow.createCell(1).setCellValue(businessName);

			// Total number of lines (unique txntypes)
			int totalLines = financeReport.size();

			// Add total lines dynamically
			HSSFRow toTotalLinesRow = sheet.createRow(metaRowIndex++);
			toTotalLinesRow.createCell(0).setCellValue("Total Lines");
			toTotalLinesRow.createCell(1).setCellValue(totalLines);

			// Total Transaction Amount (sum of all transaction amounts)
			double totalTransactionAmount = financeReport.stream()
					.filter(report -> report.getAmount() != null && !report.getAmount().isEmpty()
							&& !report.getAmount().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getAmount().replace(",", ""))) // Convert String to
																									// double, removing
																									// commas
					.sum();
			logger.info("toTotalLinesRow::::" + toTotalLinesRow);

			HSSFRow toTxnAmtRow = sheet.createRow(metaRowIndex++);
			toTxnAmtRow.createCell(0).setCellValue("Total Transaction Amount");
			toTxnAmtRow.createCell(1).setCellValue(totalTransactionAmount);

			// Total Net Amount (sum of all net amounts)
			double totalNetAmount = financeReport.stream()
					.filter(report -> report.getNetamount() != null && !report.getNetamount().isEmpty()
							&& !report.getNetamount().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getNetamount().replace(",", ""))).sum();
			HSSFRow toNetAmtRow = sheet.createRow(metaRowIndex++);
			toNetAmtRow.createCell(0).setCellValue("Total NetAmount");
			toNetAmtRow.createCell(1).setCellValue(totalNetAmount);
			

			// Total HOST MDR Amount (sum of all HOST MDR amounts)
			double totalHostMDRAmount = financeReport.stream()
			.filter(report -> report.getHostMdrAmt() != null && !report.getHostMdrAmt().isEmpty()
			&& !report.getHostMdrAmt().equalsIgnoreCase("null"))
			.mapToDouble(report -> Double.parseDouble(report.getHostMdrAmt())).sum();
			
			HSSFRow totalHostMDRAmountRow = sheet.createRow(metaRowIndex++);
			totalHostMDRAmountRow.createCell(0).setCellValue("Total Host MDR Amount");
			totalHostMDRAmountRow.createCell(1).setCellValue(totalHostMDRAmount);
						
			// Total Mobi MDR Amount (sum of all MOBI MDR amounts)
			double totalMobiMDRAmount = financeReport.stream()
			.filter(report -> report.getMobiMdrAmt() != null && !report.getMobiMdrAmt().isEmpty()
			&& !report.getMobiMdrAmt().equalsIgnoreCase("null"))
			.mapToDouble(report -> Double.parseDouble(report.getMobiMdrAmt())).sum();

			HSSFRow totalMobiMDRAmountRow = sheet.createRow(metaRowIndex++);
			totalMobiMDRAmountRow.createCell(0).setCellValue("Total Mobi MDR Amount");
			totalMobiMDRAmountRow.createCell(1).setCellValue(totalMobiMDRAmount);


			// Total MDR Amount (sum of all MDR amounts)
			double totalMDRAmount = financeReport.stream()
					.filter(report -> report.getMdramount() != null && !report.getMdramount().isEmpty()
							&& !report.getMdramount().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getMdramount())).sum();

			HSSFRow toMdrAmtRow = sheet.createRow(metaRowIndex++);
			toMdrAmtRow.createCell(0).setCellValue("Total MDR Amount");
			toMdrAmtRow.createCell(1).setCellValue(totalMDRAmount);

			// Leave an empty row between metadata and headers
			metaRowIndex++;

			// Set header
			HSSFRow header = sheet.createRow(metaRowIndex);
			String[] headers = PropertyLoad.getFile().getProperty("PAYIN_REPORT_XLS_EXPORT_HEADER").toUpperCase()
					.split(",");
			for (int i = 0; i < headers.length; i++) {
				header.createCell(i).setCellValue(headers[i]);
			}

			// Set rows
			int rowIndex = metaRowIndex + 1;
			for (FinanceReport report : financeReport) {
				HSSFRow row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(report.getDate().replace("/", "-"));
				row.createCell(1).setCellValue(report.getTime());
				row.createCell(2).setCellValue(report.getMid());
				row.createCell(3).setCellValue(report.getTid());
				row.createCell(4).setCellValue(report.getAmount());
				row.createCell(5).setCellValue(report.getReference());
				row.createCell(6).setCellValue(report.getApprovalcode());
				row.createCell(7).setCellValue(report.getRrn());
				row.createCell(8).setCellValue(report.getStatus());
				row.createCell(9).setCellValue(report.getPaymentmethod());
				row.createCell(10).setCellValue(report.getHostMdrAmt());
				row.createCell(11).setCellValue(report.getMobiMdrAmt());
				row.createCell(12).setCellValue(report.getMdramount());
				row.createCell(13).setCellValue(report.getNetamount());
				row.createCell(14).setCellValue(report.getPaymentdate());
				row.createCell(15).setCellValue(report.getEzysettleamount());
				row.createCell(16).setCellValue(report.getSubmerchantid());
				
			}

			logger.info("Payin Transaction Report--Completed");
			// Convert workbook to byte array
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				workbook.write(bos);
				return bos.toByteArray();
			}

		} catch (Exception e) {
			logger.error("Error generating Excel content for payin report", e);
			return null;
		}
	}

	public static byte[] generatePayoutReportXLSContentForEmail(List<FinanceReport> financeReport, String fromDate,
			String toDate, String merchantName) {
		try (HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Payout Transaction Report");
			logger.info("Payout Transaction Report");
			int metaRowIndex = 0;

			HSSFRow reportTypeRow1 = sheet.createRow(metaRowIndex++);
			reportTypeRow1.createCell(0).setCellValue("Report Type:");
			reportTypeRow1.createCell(1).setCellValue("Payout Report");

			String date1 = convertDateFormatTo_dd_MM_yyyy(fromDate);
			String date2 = convertDateFormatTo_dd_MM_yyyy(toDate);
			HSSFRow fromDateRow = sheet.createRow(metaRowIndex++);
			fromDateRow.createCell(0).setCellValue("From");
			fromDateRow.createCell(1).setCellValue(date1);

			HSSFRow toDateRow = sheet.createRow(metaRowIndex++);
			toDateRow.createCell(0).setCellValue("To");
			toDateRow.createCell(1).setCellValue(date2);

			HSSFRow toTimeRow = sheet.createRow(metaRowIndex++);
			toTimeRow.createCell(0).setCellValue("Time");
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
			String formattedDateTime = now.format(formatter);
			toTimeRow.createCell(1).setCellValue(formattedDateTime);

			HSSFRow toNatureRow1 = sheet.createRow(metaRowIndex++);
			toNatureRow1.createCell(0).setCellValue("Nature");
			toNatureRow1.createCell(1).setCellValue("PAYOUT");

			HSSFRow toMercNameRow = sheet.createRow(metaRowIndex++);
			toMercNameRow.createCell(0).setCellValue("Merchant Name");
			toMercNameRow.createCell(1).setCellValue(merchantName);

			// Total number of lines (unique txntypes)
			int totalLines = financeReport.size();

			// Add total lines dynamically
			HSSFRow toTotalLinesRow = sheet.createRow(metaRowIndex++);
			toTotalLinesRow.createCell(0).setCellValue("Total Lines");
			toTotalLinesRow.createCell(1).setCellValue(totalLines);

			// Total Transaction Amount (sum of all transaction amounts)
			double totalTransactionAmount = financeReport.stream()
					.filter(report -> report.getAmount() != null && !report.getAmount().isEmpty()
							&& !report.getAmount().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getAmount())) // Convert String to double
					.sum();
			logger.info("totalTransactionAmount::::" + totalTransactionAmount);

			HSSFRow toTxnAmtRow = sheet.createRow(metaRowIndex++);
			toTxnAmtRow.createCell(0).setCellValue("Total Transaction Amount");
			toTxnAmtRow.createCell(1).setCellValue(totalTransactionAmount);

			// Calculate Total Payout Fee
			double totalPayoutFee = financeReport.stream()
					.filter(report -> report.getPayoutFee() != null && !report.getPayoutFee().isEmpty()
							&& !report.getPayoutFee().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getPayoutFee())) // Convert String to double
					.sum();
			logger.info("Total Payout Fee: " + totalPayoutFee);

			HSSFRow toPayoutFeeRow = sheet.createRow(metaRowIndex++);
			toPayoutFeeRow.createCell(0).setCellValue("Total Payout Fee");
			toPayoutFeeRow.createCell(1).setCellValue(totalPayoutFee);

			// Calculate Total Payout Amount
			double totalPayoutAmt = financeReport.stream()
					.filter(report -> report.getPayoutamount() != null && !report.getPayoutamount().isEmpty()
							&& !report.getPayoutamount().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getPayoutamount())) // Convert String to double
					.sum();
			logger.info("Total Payout Amout: " + totalPayoutAmt);
			HSSFRow totalPayoutAmtRow = sheet.createRow(metaRowIndex++);
			totalPayoutAmtRow.createCell(0).setCellValue("Total Payout Amount");
			totalPayoutAmtRow.createCell(1).setCellValue(totalPayoutAmt);
			
			// Calculate Total host MDR Amount
			double totalHostMdrAmt = financeReport.stream()
					.filter(report -> report.getHostMdrAmt() != null && !report.getHostMdrAmt().isEmpty()
							&& !report.getHostMdrAmt().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getHostMdrAmt())) // Convert String to double
					.sum();
			logger.info("Total Host MDR Amount: " + totalHostMdrAmt);

			HSSFRow totalHostMdrRow = sheet.createRow(metaRowIndex++);
			totalHostMdrRow.createCell(0).setCellValue("Total Host MDR Amount");
			totalHostMdrRow.createCell(1).setCellValue(totalHostMdrAmt);
			
			// Calculate Total Mobi MDR Amount
			double totalMobiMdrAmt = financeReport.stream()
					.filter(report -> report.getMobiMdrAmt() != null && !report.getMobiMdrAmt().isEmpty()
							&& !report.getMobiMdrAmt().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getMobiMdrAmt())) // Convert String to double
					.sum();
			logger.info("Total Mobi MDR Amount: " + totalMobiMdrAmt);

			HSSFRow totalMobiMdrAmtRow = sheet.createRow(metaRowIndex++);
			totalMobiMdrAmtRow.createCell(0).setCellValue("Total Mobi MDR Amount");
			totalMobiMdrAmtRow.createCell(1).setCellValue(totalMobiMdrAmt);


			// Leave an empty row between metadata and headers
			metaRowIndex++;

			// Set header
			HSSFRow header = sheet.createRow(metaRowIndex);
			String[] headers = PropertyLoad.getFile().getProperty("PAYOUT_REPORT_XLS_EXPORT_HEADER").toUpperCase()
					.split(",");
			for (int i = 0; i < headers.length; i++) {
				header.createCell(i).setCellValue(headers[i]);
			}

			// Set rows
			int rowIndex = metaRowIndex + 1;
			for (FinanceReport report : financeReport) {
				HSSFRow row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(report.getDate().replace("/", "-"));
				row.createCell(1).setCellValue(report.getTime());
				row.createCell(2).setCellValue(report.getCustomerName());
				row.createCell(3).setCellValue(report.getBrn());
				row.createCell(4).setCellValue(report.getAccountNo());
				row.createCell(5).setCellValue(report.getBankName());
				row.createCell(6).setCellValue(report.getTransaction_id());
				row.createCell(7).setCellValue(report.getPayoutamount());
				row.createCell(8).setCellValue(report.getHostMdrAmt());
				row.createCell(9).setCellValue(report.getMobiMdrAmt());
				row.createCell(10).setCellValue(report.getPayoutFee());
				row.createCell(11).setCellValue(report.getStatus());
				row.createCell(12).setCellValue(report.getPaidTime());
				row.createCell(13).setCellValue(report.getPaidDate());
				row.createCell(14).setCellValue(report.getSubmerchantMid());
				row.createCell(15).setCellValue(report.getSubmerchantName());
				row.createCell(16).setCellValue(report.getPayoutId());
				row.createCell(17).setCellValue(report.getAmount());
				row.createCell(18).setCellValue(report.getPayoutType());
			}

			logger.info("Payout Transaction Report");
			// Convert workbook to byte array
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				workbook.write(bos);
				return bos.toByteArray();
			}
		} catch (Exception e) {
			logger.error("Error generating Excel content for payout report", e);
			return null;
		}
	}

	public static String convertDateFormatTo_dd_MM_yyyy(String dateStr) {
		logger.info("Format Method:::");
		SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat toFormat = new SimpleDateFormat("dd/MM/yyyy");

		Date date = null;
		try {
			date = fromFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return toFormat.format(date);
	}

	public List<String> getFpxHostList() {
		return transactionDAO.getFpxHostList();
	}

	public void updateAllMerchantsHostId(String hostName) {

		int responseCode;
		String failureReason = "";
		String fpxModuleResponseStatus = null;
		String responseData = null;
		String jsonString = null;
		List<Long> emptyList = new ArrayList<Long>();
		try {
			String hostId = transactionDAO.getHostIdByHostName(hostName);

			if (Objects.isNull(hostId) || hostId.trim().isEmpty()) {
				logger.error("HostId: " + hostId + " for the host name: " + hostName);
				throw new RuntimeException("No HostId found for " + hostName);
			}

			if (transactionDAO.areAllFpxHostIdsSame()) {
				String randomFpxHostId = transactionDAO.getFirstFpxHostId();
				if (!randomFpxHostId.equals("N/A") && randomFpxHostId.equals(hostId)) {
					logger.warn("FPX Host switch is attempting to switch to the same host: " + hostName
							+ ". Host switch prevented.");
					failureReason = "FPX Host switch is attempting to switch to the same host: " + hostName
							+ ". Host switch prevented.";
					throw new RuntimeException(failureReason);
				}
			}

			// Triggering FpxModule to Update the MobileUserTable hostId,For All merchants
			URL url = new URL(PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_API_URL"));
			logger.info("FPX_HOST_SWITCH_API_URL -> " + PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_API_URL"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonRequestDto requestDto = new JsonRequestDto(emptyList, hostId, "all");
				jsonString = objectMapper.writeValueAsString(requestDto);

				logger.info("Request Body Send to FPX Module: " + jsonString);
			} catch (Exception e) {
				logger.error("Exception Wile Parsing data : " + e.getMessage() + e);
			}

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			responseCode = connection.getResponseCode();

			if (responseCode != HttpURLConnection.HTTP_OK) {
				logger.error("Triggering FPX module request failed with response code: " + responseCode);
				failureReason = "All merchants' FPX host switch failed.";

				throw new RuntimeException("Triggering FPX module request failed with response code: " + responseCode);
			} else {

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					responseData = response.toString(); // Store the response data

					JSONObject jsonObject = new JSONObject(responseData);
					fpxModuleResponseStatus = jsonObject.getString("status");

					logger.info("Triggering FPX module request Success with Http Response code: " + responseCode
							+ ", Response Data : " + responseData);

					if (fpxModuleResponseStatus.equalsIgnoreCase("Success")) {

						// If the Responsd from FPX module is success, Send email
						sendAcknowledgementEmailForFPXHostSwitch(null, hostName, "All Merchants", null);
					}
				}
			}

			connection.disconnect();
		} catch (Exception e) {

			logger.error("Error : " + e.getMessage(), e);
			int mailResponse = ElasticEmailClient.sendemailSlip(PropertyLoad.getFile().getProperty("FROMMAIL"),
					"Host switch failure", PropertyLoad.getFile().getProperty("FROMNAME"),
					PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_TO"),
					PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_CC"), null,
					getHtlmContentForFpxHostSwitchFailure(failureReason));
			logger.info("Email Sent Successfully: " + mailResponse + " to "
					+ PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_TO"));
		}
	}

	public void updateSelectedMerchantHostId(String selectedHost, List<String> merchantIdList) {

		int responseCode;
		String fpxModuleResponseStatus = null;
		String responseData = null;
		String jsonString = null;
		String failureReason = "";
		try {
			// Convert List<String> to List<Long>
			List<Long> longMerchantIDList = merchantIdList.stream()
					.flatMap(s -> Arrays.stream(s.replaceAll("[\\[\\]\"]", "").split(", ")))
					.map(toLong -> Long.valueOf(toLong)).filter(value -> value != null).collect(Collectors.toList());

			// Obtaining HostId for the corresponding host.
			String hostId = transactionDAO.getHostIdByHostName(selectedHost);

			if (Objects.isNull(hostId) || hostId.trim().isEmpty()) {
				logger.error("HostId: " + hostId + " for the host: " + hostId);
				throw new RuntimeException("No HostId found for " + selectedHost);
			}

			// Obtaining the previous host information for a single merchant's host switch
			final String previousBank;
			if (longMerchantIDList != null && !longMerchantIDList.isEmpty() && longMerchantIDList.size() == 1) {
				previousBank = transactionDAO.getHostNameByMerchantId(String.valueOf(longMerchantIDList.get(0)));

				if (previousBank != null && !previousBank.equals("N/A")
						&& previousBank.toUpperCase().equals(selectedHost.toUpperCase())) {
					logger.warn("Merchant ID: " + merchantIdList + ", previous host: " + previousBank
							+ ", is attempting to switch to the same host: " + selectedHost
							+ ". Host switch prevented.");

					List<Merchant> merchantsDetails = transactionDAO.getListOfMerchantsByMerchantId(longMerchantIDList);

					String businessNameString = merchantsDetails.stream().map(Merchant::getBusinessName)
							.collect(Collectors.joining(","));

					failureReason = "Business Name: " + businessNameString + ". The previous host was: " + previousBank
							+ ", but an attempt was made to switch to the same host: " + selectedHost
							+ ". Therefore, the host switch has been prevented.";

					throw new RuntimeException(failureReason);
				}
			} else {
				previousBank = null;
			}

			// Check all provided FpxHostId are not same.
			List<String> respectiveHostIds = transactionDAO.areAllProvidedFpxHostIdsSame(longMerchantIDList);
			List<String> hostIdsWithoutNulls = respectiveHostIds.stream().filter(Objects::nonNull)
					.collect(Collectors.toList());

			if (!hostIdsWithoutNulls.isEmpty()) {
				if (checkIfAllValuesAreEqual(hostIdsWithoutNulls) && hostId.equals(hostIdsWithoutNulls.get(0))) {

					failureReason = "Attempt was made to switch to the same host: " + selectedHost
							+ ". Therefore, the host switch has been prevented.";

					throw new RuntimeException("Attempting to switch, to the same host");
				}
			}

			// Triggering Fpx Module to Update the MobileUser table's Host_Id Column
			URL url = new URL(PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_API_URL"));
			logger.info("FPX_HOST_SWITCH_API_URL -> " + PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_API_URL"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			try {
				JsonRequestDto requestDto = new JsonRequestDto(longMerchantIDList, hostId, "selected");
				ObjectMapper objectMapper = new ObjectMapper();
				jsonString = objectMapper.writeValueAsString(requestDto);
				logger.info("Request Body Send to FPX Module : " + jsonString);
			} catch (Exception e) {

				logger.error("Exception Wile Parsing data : " + e.getMessage() + e);
				failureReason = "The host switch failed before triggering the FPX module.";
				throw new RuntimeException(failureReason);
			}

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			responseCode = connection.getResponseCode();

			if (responseCode != HttpURLConnection.HTTP_OK) {
				logger.error("Triggering FPX module request failed with response code: " + responseCode);
				failureReason = "The FPX module was unable to complete the host switch process.";
				throw new RuntimeException(failureReason);
			} else {

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					responseData = response.toString(); // Store the response data

					JSONObject jsonObject = new JSONObject(responseData);
					fpxModuleResponseStatus = jsonObject.getString("status");

					logger.info("Triggering FPX module request Success with Http Response code: " + responseCode + " :"
							+ fpxModuleResponseStatus + ", Response Data : " + responseData);

					if (fpxModuleResponseStatus.equalsIgnoreCase("Success")) {

						// If the Responsd from FPX module is success, Send email
						List<Merchant> merchantsDetails = transactionDAO
								.getListOfMerchantsByMerchantId(longMerchantIDList);

						String businessNameString = merchantsDetails.stream().map(Merchant::getBusinessName)
								.collect(Collectors.joining(","));

						sendAcknowledgementEmailForFPXHostSwitch(previousBank, selectedHost, businessNameString,
								longMerchantIDList);
					}
				}
			}

			connection.disconnect();
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);

			int mailResponse = ElasticEmailClient.sendemailSlip(PropertyLoad.getFile().getProperty("FROMMAIL"),
					"Host switch failure", PropertyLoad.getFile().getProperty("FROMNAME"),
					PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_TO"),
					PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_CC"), null,
					getHtlmContentForFpxHostSwitchFailure(failureReason));
			logger.info("Email Sent Successfully: " + mailResponse + " to "
					+ PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_TO"));
		}
	}

	public static boolean checkIfAllValuesAreEqual(List<String> hostIds) {

		// Check if the first value is null
		String firstValue = hostIds.get(0);
		if (firstValue == null) {
			// If the first value is null, ensure all elements are null
			return hostIds.stream().allMatch(Objects::isNull);
		} else {
			// If the first value is not null, ensure all elements are equal to the first
			// value
			return hostIds.stream().allMatch(value -> firstValue.equals(value));
		}
	}

	public static String sendAcknowledgementEmailForFPXHostSwitch(String previousHostBank, String newHostBank,
			String merchantName, List<Long> merchantId) {

		try {
			String emailFrom = PropertyLoad.getFile().getProperty("FROMMAIL");
			String subject = "Confirmation Of FPX Host Switch";
			String fromName = PropertyLoad.getFile().getProperty("FROMNAME");
			String toAddress = PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_TO");
			String ccAddress = PropertyLoad.getFile().getProperty("FPX_HOST_SWITCH_MAIL_CC");

			String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH));
			String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

			StringBuilder emailContent = new StringBuilder();

			emailContent.append("<!DOCTYPE html>");
			emailContent.append("<html lang=\"en\">");
			emailContent.append("<head>");
			emailContent.append("<meta charset=\"UTF-8\">");
			emailContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			emailContent.append("<title>Transition Completed</title>");
			emailContent.append("<style>");
			emailContent
					.append("body { font-family: Arial, sans-serif; margin: 0; padding: 0; box-sizing: border-box; }");
			emailContent.append(".container { width: 100%; padding: 20px; box-sizing: border-box; }");
			emailContent.append(".header, .footer { text-align: center; padding: 10px 0; background-color: #f8f8f8; }");
			emailContent.append(".content { margin: 20px 0; }");
			emailContent.append("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
			emailContent.append("table, th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }");
			emailContent.append("th { background-color: #f2f2f2; font-weight: bold; }");
			emailContent.append("td { background-color: #ffffff; }");
			emailContent.append(".message { margin: 20px 0; }");
			emailContent.append(
					"@media (max-width: 600px) { .container { padding: 10px; } table, th, td { padding: 8px; } }");
			emailContent.append("</style>");
			emailContent.append("</head>");
			emailContent.append("<body>");
			emailContent.append("<div class=\"container\">");
			emailContent.append("<div class=\"header\">");
			emailContent.append("<h2>Transition Completed</h2>");
			emailContent.append("</div>");
			emailContent.append("<div class=\"content\">");
			emailContent.append("<p>Hi Team,</p>");
			emailContent.append("<p>The transition to the new host has been completed successfully.</p>");
			emailContent.append("<table>");
			emailContent.append("<tr><th>Date</th><td>" + currentDate + "</td></tr>");
			emailContent.append("<tr><th>Time</th><td>" + currentTime + "</td></tr>");
			emailContent.append("<tr><th>Merchant Name</th><td>" + merchantName + "</td></tr>");

			if (previousHostBank != null) {
				emailContent.append("<tr><th>Old Host</th><td>" + previousHostBank + "</td></tr>");
			}

			emailContent.append("<tr><th>New Host</th><td>" + newHostBank + "</td></tr>");
			emailContent.append("</table>");
			emailContent.append("<p class=\"message\">Thank you.</p>");
			emailContent.append("<p class=\"message\">Best Regards,</p>");
			emailContent.append("<p class=\"message\">Team Tech</p>");
			emailContent.append("</div>");
			emailContent.append("</div>");
			emailContent.append("</body>");
			emailContent.append("</html>");

			int mailResponse = ElasticEmailClient.sendemailSlip(emailFrom, subject, fromName, toAddress, ccAddress,
					null, emailContent.toString());
			logger.info("Email Sent Successfully: " + mailResponse + " to " + toAddress);

			return String.valueOf(mailResponse);
		} catch (Exception e) {
			logger.error("Exception while sending mail : " + e.getMessage(), e);
			return "400";
		}
	}

	public static String getHtlmContentForFpxHostSwitchFailure(String failureReason) {
		StringBuilder htmlContent = new StringBuilder();
		htmlContent.append("<!DOCTYPE html>").append("<html lang=\"en\">").append("<head>")
				.append("<meta charset=\"UTF-8\">")
				.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
				.append("<title>Fpx Host Switch Failure</title>").append("<style>")
				.append("    /* CSS styles to highlight the error message */").append("    body {")
				.append("        font-family: Arial, sans-serif;").append("        background-color: #f5f5f5;")
				.append("        color: #333;").append("    }").append("    .container {")
				.append("        max-width: 600px;").append("        margin: 20px auto;")
				.append("        padding: 20px;").append("        background-color: #fff;")
				.append("        border-radius: 5px;").append("        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);")
				.append("    }").append("    h1 {").append("        color: #d9534f;")
				.append("        text-align: center;").append("    }").append("    p {")
				.append("        font-size: 16px;").append("        line-height: 1.6;")
				.append("        margin-bottom: 20px;").append("    }").append("</style>").append("</head>")
				.append("<body>").append("    <div class=\"container\">")
				.append("        <h1>Fpx Host Switch Failure</h1>");
		if (failureReason != null) {
			htmlContent.append("<p>Hey team,</p>").append("<p>The host switch is failing due to an error: ")
					.append(failureReason).append("</p>");
		}
		htmlContent.append("    </div>").append("</body>").append("</html>");

		return htmlContent.toString();
	}

	public void ListofEzySettleSummarymerchant(final PaginationBean<SettlementModel> paginationBean, final String data,
			final String date1, Merchant currentMerchant, boolean isExport) {
		transactionDAO.ListofEzySettleSummarymerchant(paginationBean, data, date1, currentMerchant, isExport);
	}

	public void ListofEzySettleSummary(final PaginationBean<SettlementModel> paginationBean, final String data,
			final String date1, boolean isExport) {
		transactionDAO.ListofEzySettleSummary(paginationBean, data, date1, isExport);
	}

//	public void updateInternationalMerchantsData(ConcurrentMap<String, Double> settledAmountsMap,Merchant currentMerchant) {
//		 transactionDAO.updateInternationalMerchantsData(settledAmountsMap,currentMerchant);
//	}
//
//	public boolean getMerchantDataInternational(String settlementdate,Merchant currentMerchant) {
//		 return transactionDAO.getMerchantDataInternational(settlementdate,currentMerchant);
//	}
//
//	public List<MerchantSettlementInternational> getStoredSettlementData(String settlementdate,Merchant currentMerchant) {
//		 return transactionDAO.getStoredSettlementData(settlementdate,currentMerchant);
//	}
//
//	public ConcurrentMap<String, Double> getAllTransactions(String settlementdate, Merchant currentMerchant,ConcurrentMap<String, Double> settledAmountsMap,Set<String> uniqueSettlementDates) {
//		 return transactionDAO.getAllTransactions(settlementdate,currentMerchant,settledAmountsMap,uniqueSettlementDates);
//	}
//
	public void getSettlementDetails(PaginationBean<SettlementDetailsList> paginationBean) {
		transactionDAO.listSettlementDetails(paginationBean);
	}

	public void getSettlementDetailsByMerchant(PaginationBean<SettlementDetailsList> paginationBean,
			String businessName) {
		transactionDAO.listSettlementDetailsByMerchant(paginationBean, businessName);
	}

	public List<FinanceReport> getAllPayoutTxnDetailsBetweenDates(String fromDate, String toDate) {
		return transactionDAO.getAllPayoutTxnDetailsBetweenDates(fromDate, toDate);
	}


	public static byte[] generateAllPayoutReportXLSContentForEmail(List<FinanceReport> financeReport, String fromDate,
			String toDate) {
		try (HSSFWorkbook workbook = new HSSFWorkbook()) {
			HSSFSheet sheet = workbook.createSheet("Payout Transaction Report");
			logger.info("Payout Transaction Report");
			int metaRowIndex = 0;

			HSSFRow reportTypeRow1 = sheet.createRow(metaRowIndex++);
			reportTypeRow1.createCell(0).setCellValue("Report Type:");
			reportTypeRow1.createCell(1).setCellValue("Payout Report");

			String date1 = convertDateFormatTo_dd_MM_yyyy(fromDate);
			String date2 = convertDateFormatTo_dd_MM_yyyy(toDate);
			HSSFRow fromDateRow = sheet.createRow(metaRowIndex++);
			fromDateRow.createCell(0).setCellValue("From");
			fromDateRow.createCell(1).setCellValue(date1);

			HSSFRow toDateRow = sheet.createRow(metaRowIndex++);
			toDateRow.createCell(0).setCellValue("To");
			toDateRow.createCell(1).setCellValue(date2);

			HSSFRow toTimeRow = sheet.createRow(metaRowIndex++);
			toTimeRow.createCell(0).setCellValue("Time");
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
			String formattedDateTime = now.format(formatter);
			toTimeRow.createCell(1).setCellValue(formattedDateTime);

			HSSFRow toNatureRow1 = sheet.createRow(metaRowIndex++);
			toNatureRow1.createCell(0).setCellValue("Nature");
			toNatureRow1.createCell(1).setCellValue("PAYOUT");

			HSSFRow toMercNameRow = sheet.createRow(metaRowIndex++);
			toMercNameRow.createCell(0).setCellValue("Merchant Name");
			toMercNameRow.createCell(1).setCellValue("All Merchants");

			// Total number of lines (unique txntypes)
			int totalLines = financeReport.size();

			// Add total lines dynamically
			HSSFRow toTotalLinesRow = sheet.createRow(metaRowIndex++);
			toTotalLinesRow.createCell(0).setCellValue("Total Lines");
			toTotalLinesRow.createCell(1).setCellValue(totalLines);

			// Total Transaction Amount (sum of all transaction amounts)
			double totalTransactionAmount = financeReport.stream()
					.filter(report -> report.getAmount() != null && !report.getAmount().isEmpty()
							&& !report.getAmount().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getAmount())) // Convert String to double
					.sum();
			logger.info("totalTransactionAmount::::" + totalTransactionAmount);

			HSSFRow toTxnAmtRow = sheet.createRow(metaRowIndex++);
			toTxnAmtRow.createCell(0).setCellValue("Total Transaction Amount");
			toTxnAmtRow.createCell(1).setCellValue(totalTransactionAmount);

			// Calculate Total Payout Fee
			double totalPayoutFee = financeReport.stream()
					.filter(report -> report.getPayoutFee() != null && !report.getPayoutFee().isEmpty()
							&& !report.getPayoutFee().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getPayoutFee())) // Convert String to double
					.sum();
			logger.info("Total Payout Fee: " + totalPayoutFee);

			HSSFRow toPayoutFeeRow = sheet.createRow(metaRowIndex++);
			toPayoutFeeRow.createCell(0).setCellValue("Total Payout Fee");
			toPayoutFeeRow.createCell(1).setCellValue(totalPayoutFee);

			// Calculate Total Payout Amount
			double totalPayoutAmt = financeReport.stream()
					.filter(report -> report.getPayoutamount() != null && !report.getPayoutamount().isEmpty()
							&& !report.getPayoutamount().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getPayoutamount())) // Convert String to double
					.sum();
			logger.info("Total Payout Amout: " + totalPayoutAmt);

			HSSFRow totalPayoutAmtRow = sheet.createRow(metaRowIndex++);
			totalPayoutAmtRow.createCell(0).setCellValue("Total Payout Amount");
			totalPayoutAmtRow.createCell(1).setCellValue(totalPayoutAmt);
			

			// Calculate Total host MDR Amount
			double totalHostMdrAmt = financeReport.stream()
					.filter(report -> report.getHostMdrAmt() != null && !report.getHostMdrAmt().isEmpty()
							&& !report.getHostMdrAmt().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getHostMdrAmt())) // Convert String to double
					.sum();
			logger.info("Total Host MDR Amount: " + totalHostMdrAmt);

			HSSFRow totalHostMdrRow = sheet.createRow(metaRowIndex++);
			totalHostMdrRow.createCell(0).setCellValue("Total Host MDR Amount");
			totalHostMdrRow.createCell(1).setCellValue(totalHostMdrAmt);
						
			// Calculate Total Mobi MDR Amount
			double totalMobiMdrAmt = financeReport.stream()
					.filter(report -> report.getMobiMdrAmt() != null && !report.getMobiMdrAmt().isEmpty()
							&& !report.getMobiMdrAmt().equalsIgnoreCase("null"))
					.mapToDouble(report -> Double.parseDouble(report.getMobiMdrAmt())) // Convert String to double
					.sum();
			logger.info("Total Mobi MDR Amount: " + totalMobiMdrAmt);

			HSSFRow totalMobiMdrAmtRow = sheet.createRow(metaRowIndex++);
			totalMobiMdrAmtRow.createCell(0).setCellValue("Total Mobi MDR Amount");
			totalMobiMdrAmtRow.createCell(1).setCellValue(totalMobiMdrAmt);


			// Leave an empty row between metadata and headers
			metaRowIndex++;

			// Set header
			HSSFRow header = sheet.createRow(metaRowIndex);
			String[] headers = PropertyLoad.getFile().getProperty("PAYOUT_REPORT_XLS_EXPORT_HEADER").toUpperCase()
					.split(",");
			for (int i = 0; i < headers.length; i++) {
				header.createCell(i).setCellValue(headers[i]);
			}

			// Set rows
			int rowIndex = metaRowIndex + 1;
			for (FinanceReport report : financeReport) {
				HSSFRow row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(report.getDate().replace("/", "-"));
				row.createCell(1).setCellValue(report.getTime());
				row.createCell(2).setCellValue(report.getCustomerName());
				row.createCell(3).setCellValue(report.getBrn());
				row.createCell(4).setCellValue(report.getAccountNo());
				row.createCell(5).setCellValue(report.getBankName());
				row.createCell(6).setCellValue(report.getTransaction_id());
				row.createCell(7).setCellValue(report.getPayoutamount());
				row.createCell(8).setCellValue(report.getHostMdrAmt());
				row.createCell(9).setCellValue(report.getMobiMdrAmt());
				row.createCell(10).setCellValue(report.getPayoutFee());
				row.createCell(11).setCellValue(report.getStatus());
				row.createCell(12).setCellValue(report.getPaidTime());
				row.createCell(13).setCellValue(report.getPaidDate());
				row.createCell(14).setCellValue(report.getSubmerchantMid());
				row.createCell(15).setCellValue(report.getSubmerchantName());
				row.createCell(16).setCellValue(report.getPayoutId());
				row.createCell(17).setCellValue(report.getAmount());
				row.createCell(18).setCellValue(report.getPayoutType());
			}

			logger.info("Payout Transaction Report");
			// Convert workbook to byte array
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				workbook.write(bos);
				return bos.toByteArray();
			}
		} catch (Exception e) {
			logger.error("Error generating Excel content for payout report All", e);
			return null;
		}
	}

	private static String[] getPast10Days() {

		// Get the current date
		LocalDate currentDate = LocalDate.now().plusDays(1);

		// Calculate the date 10 days before
		LocalDate fromDate = currentDate.minusDays(10);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String fromDateString = fromDate.format(formatter);
		String toDateString = currentDate.format(formatter);

		return new String[] { fromDateString, toDateString };
	}

	// Get the pagination count from properties, default to 20 if null or empty
	private static int getTransactionsPerPage() {
	    String paginationCountProperty = PropertyLoad.getFile().getProperty("paginationCount");
	    return (paginationCountProperty != null && !paginationCountProperty.isEmpty()) ?
	            Integer.parseInt(paginationCountProperty) : 20; // Default value
	}

	private static void handleNoTransactions(Model model, PageBean pageBean, PaginationBean<PayoutAccountEnquiryDto> paginationBean, int currentPage) {
	    paginationBean.setQuerySize("0");
	    model.addAttribute("responseData", "No Records Found");
	    model.addAttribute("pageBean", pageBean);
	    model.addAttribute("paginationBean", paginationBean);
	    model.addAttribute("currentPageNumber", currentPage);
	}

	public void fetchMaxPayoutExceededTransactions(int currentPage, Model model, PageBean pageBean) {

		PaginationBean<PayoutAccountEnquiryDto> paginationBean = new PaginationBean<>();

		try {
			String[] dateRange = getPast10Days();

			int totalTransactionSize = accountEnquiryDao.fetchMaxPayoutLimitExceededTransactionCount("ExceededMaxLimit", dateRange[0], dateRange[1]);

		    // Handle case when there are no transactions
	        if (totalTransactionSize == 0) {
	            handleNoTransactions(model, pageBean, paginationBean, currentPage);
	            return; // return to avoid further processing
	        }

			int transactionsPerPage = getTransactionsPerPage();

	        // Calculate first record & No. of pages required for this summary.
			int requiredPages = (int) Math.ceil((double) totalTransactionSize / transactionsPerPage);
			int firstRecord = (currentPage - 1) * transactionsPerPage;

			List<PayoutAccountEnquiryDto> enquiryDtos = accountEnquiryDao.fetchMaxPayoutLimitExceededTransactions(
					"ExceededMaxLimit", dateRange[0], dateRange[1], firstRecord, transactionsPerPage);

			logger.info("Date Range: {" + Arrays.toString(dateRange) + "}, Total Transaction Size: {"
					+ totalTransactionSize + "}, Transactions Per Page: {" + transactionsPerPage
					+ "}, Required Pages: {" + requiredPages + "}, First Record: {" + firstRecord + "}, Enquiry DTOs: {"
					+ enquiryDtos + "}");

			if (enquiryDtos != null && !enquiryDtos.isEmpty()) {
				paginationBean.setItemList(enquiryDtos);
			} else {
				model.addAttribute("responseData", "No Records Found");
			}

			paginationBean.setQuerySize(Integer.toString(requiredPages));
		} catch (Exception e) {
			logger.error("Error in obtaining 'Transaction Approval' details list (MaxPayoutExceededTransactions): " + e.getMessage(), e);
		} finally {
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("currentPageNumber", currentPage);
		}
	}

	private static List<String> validateAndConvertSearchValues(String searchValue) {

		if (searchValue == null || searchValue.trim().isEmpty()) {
			logger.warn("isEmpty");
	        return Collections.emptyList();
	    }

	    // Remove any trailing comma if present
	    searchValue = searchValue.trim();
	    if (searchValue.endsWith(",")) {
			logger.warn("endsWith");
	        searchValue = searchValue.substring(0, searchValue.length() - 1);
	    }

	    List<String> searchValuesList = Arrays.asList(searchValue.split(","));

	    // Limit the list to a maximum of 20 values
	    if (searchValuesList.size() > 20) {
			logger.warn("searchValuesList.size() > 20");
	        searchValuesList = searchValuesList.subList(0, 20);  // Keep only the first 20 elements
	    }

	    return searchValuesList;
	}

	public void searchExceededLimitPayoutTransactions(Model model, PageBean pageBean, String searchType, String searchValue, int currentPageNo) {

		PaginationBean<PayoutAccountEnquiryDto> paginationBean = new PaginationBean<>();

		try {
			String[] dateRange = getPast10Days();
			List<String> searchValues = validateAndConvertSearchValues(searchValue.trim());

			if (searchValues != null && !searchValues.isEmpty()) {
				List<PayoutAccountEnquiryDto> enquiryDtos = accountEnquiryDao.searchMaxPayoutLimitExceededTransactions(
						"ExceededMaxLimit", dateRange[0], dateRange[1], searchValues, searchType);

				paginationBean.setItemList(enquiryDtos != null && !enquiryDtos.isEmpty() ? enquiryDtos : new ArrayList<>());
			} else {
				model.addAttribute("responseData", "No Records Found");
			}
		} catch (Exception e) {
			logger.error("Error in obtaining 'Transaction Approval' details list (MaxPayoutExceededTransactions): "
					+ e.getMessage(), e);
		} finally {
			if (paginationBean.getItemList().isEmpty()) {
	            model.addAttribute("responseData", "No Records Found");
	        }
			paginationBean.setQuerySize("1");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("currentPageNumber",currentPageNo);
			model.addAttribute("paginationBean", paginationBean);
		}
	}

	private static String encryptedMaxPayoutApprovalRequestData(String apikey, String requestData) throws Exception {
		try {
			DESedeKeySpec dks = new DESedeKeySpec(apikey.getBytes(StandardCharsets.UTF_8));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			byte[] b = cipher.doFinal(requestData.getBytes());
			String encryptedValue = Base64.getEncoder().encodeToString(b);
			return new String(encryptedValue).trim();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Map<String, String> maxPayoutApprovalRequestData(PayoutHoldTxn payoutHoldTxn,String rejectReason) throws MobiException {

		Map<String, String> requestData = new HashMap<>();

		addIfValid(requestData, "service", "PAYOUT_TXN_REQ");
		addIfValid(requestData, "mobiApiKey", payoutHoldTxn.getMobiApiKey());
		addIfValid(requestData, "businessRegNo", payoutHoldTxn.getBusinessRegNo());
		addIfValid(requestData, "bankName", payoutHoldTxn.getBankName());
		addIfValid(requestData, "bankAccNo", payoutHoldTxn.getBankAccNo());
		if(payoutHoldTxn.getPayoutType()!=null)
			addIfValid(requestData, "payoutType", payoutHoldTxn.getPayoutType().toUpperCase().equals("NORMAL") ? null : payoutHoldTxn.getPayoutType());
		addIfValid(requestData, "customerName", payoutHoldTxn.getCustomerName());
		addIfValid(requestData, "amount", payoutHoldTxn.getAmount());
		addIfValid(requestData, "subMID", payoutHoldTxn.getSubMID());
		addIfValid(requestData, "payoutid", payoutHoldTxn.getPayoutId());
		addIfValid(requestData, "bicCode", payoutHoldTxn.getBicCode());
		addIfValid(requestData, "remarks", payoutHoldTxn.getRemarks());
		addIfValid(requestData, "invoiceId", payoutHoldTxn.getInvoiceIdProof());
//		addIfValid(requestData, "onHoldPayout", action);
		addIfValid(requestData, "payoutRejectReason", rejectReason);

		return requestData;
	}

	private static void addIfValid(Map<String, String> map, String key, String value) {
		String finalValue = getValue(value);
		if (finalValue != null) {
			map.put(key, finalValue);
		}
	}

	private static String getValue(String mayBeStringNull) {
		return "null".equalsIgnoreCase(mayBeStringNull) ? null : mayBeStringNull;
	}

	private static String getPropertyValue(String propertyKey) throws Exception {
	    String propertyValue = PropertyLoader.getFileData().getProperty(propertyKey);

		if (propertyValue == null || propertyValue.trim().isEmpty()) {
			String errorMessage = String.format("Property '%s' is missing or not found in the properties file.", propertyKey);
			logger.error(
					"Error retrieving property value: {" + errorMessage + "}. Key: {" + propertyKey + "}, Value: null");

			throw new Exception("Property '" + propertyKey + "' is missing or not found in the properties file.");
		}

	    return propertyValue;
	}

	public void handlePayoutTransactionApproval(String invoiceID, PayoutHoldTxn payoutHoldTxn, String actionTakenBy) {
		try {
			final String approvalUrl =  getPropertyValue("MAX_LIMIT_PAYOUT_APPROVAL_ENDPOINT") ;
			contactExternalApi(invoiceID,payoutHoldTxn,actionTakenBy,approvalUrl,null);
			final String merchantId = payoutHoldTxn.getMerchantId();
			// Trigger Acknowledgment email to Opration regarding the payout has been approved.
			sendMaxLimitPayoutTransactionApprovalEmailToOperation(invoiceID, payoutHoldTxn,
					getMerchantDetailsByID(merchantId), getMobileUserByID(merchantId),actionTakenBy);
		} catch (Exception e) {
			logger.error("Exception while accepting " + e.getMessage(), e);
		}
	}

	public void handlePayoutTransactionReject(String invoiceID, PayoutHoldTxn payoutHoldTxn, String actionTakenBy,String rejectReason) {
		try {
			final String rejectUrl = getPropertyValue("MAX_LIMIT_PAYOUT_REJECT_ENDPOINT");
			contactExternalApi(invoiceID,payoutHoldTxn,actionTakenBy,rejectUrl,rejectReason);
			final String merchantId = payoutHoldTxn.getMerchantId();
			// Trigger Acknowledgment email to Opration regarding the payout has been approved.
			sendMaxLimitPayoutTransactionRejectionEmailToOperation(invoiceID, payoutHoldTxn,
					getMerchantDetailsByID(merchantId), getMobileUserByID(merchantId),actionTakenBy);
		} catch (Exception e) {
			logger.error("Exception while rejecting  " + e.getMessage(), e);
		}
	}

	private void contactExternalApi(String invoiceID, PayoutHoldTxn payoutHoldTxn, String actionTakenBy,String approveOrRejectUrl,String rejectReason) throws Exception {
		String action = rejectReason==null ? "Approval " : "Rejection ";
		try {

			Map<String, String> requestData = maxPayoutApprovalRequestData(payoutHoldTxn,rejectReason);
			String requestBody = new ObjectMapper().writeValueAsString(requestData);

			// Encrypt request data
			final String apiKey = getPropertyValue("KeyForAccountEnquiry");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("encryptedRequest", encryptedMaxPayoutApprovalRequestData(apiKey, requestBody));

			// Send HTTP request to ExternalApi to process payout


			URL url = new URL(approveOrRejectUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			// Handle the response
			int responseCode = connection.getResponseCode();

			StringBuilder response = new StringBuilder();
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
				String responseLine;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
			}

			logger.info("Max limit Payout transaction "+action+" details: Invoice ID: {" + invoiceID + "}, URL: {" + approveOrRejectUrl
					+ "}, Request Body: {" + requestBody + "}, Encrypted data sent: {" + jsonObject.toString()
					+ "}, Response Code: {" + responseCode + "}, Response: {" + response.toString() + "}");

			// If the response code is not in the 200 range, throw an exception
			if (responseCode < 200 || responseCode >= 300) {
				logger.error("Received non-success response code while processing max payout "+action+": {" + responseCode + "} for Invoice ID: {" + invoiceID + "}");
				throw new Exception("ExternalApi failed with response code: " + responseCode + " for Invoice ID: " + invoiceID + ". Response: " + response.toString());
			}

		}
	 catch (Exception e) {
		// Update the status in PAYOUT_HOLD_TXN to 'failed'
		accountEnquiryDao.updatePayoutHoldTxnStatusByInvoiceID(invoiceID, "Failed",actionTakenBy);

		logger.error("Error processing max payout "+ action +" for Invoice ID: {" + invoiceID + "}. Error: " + e.getMessage(), e);
	}
}

    // Obtain Merchant details by MerchantId.
    public Merchant getMerchantDetailsByID(String merchantId) {
        long merchantIdLong = Long.parseLong(merchantId.trim());
        return accountEnquiryDao.getMerchantDetailsByID(merchantIdLong);
    }

    // Obtain Mobile_User details by MerchantId.
    public MobileUser getMobileUserByID(String merchantId) {
        long merchantIdLong = Long.parseLong(merchantId.trim());
        return accountEnquiryDao.getMobileUserByMerchantId(merchantIdLong);
    }

    private static String getPropertyWithDefault(String propertyKey, String defaultValue) {
        String propertyValue = PropertyLoader.getFileData().getProperty(propertyKey);
        return (propertyValue == null || propertyValue.isEmpty()) ? defaultValue : propertyValue;
    }

	private static String getCurrentTimestamp() {

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy h.mm a");
		return LocalDateTime.now().format(formatter);
	}

	private static String addCommaSeparator(String numberString) {
	    if (numberString == null || numberString.isEmpty()) {
	        return null; // Return null for null or empty input
	    }

	    try {
	        BigDecimal number = new BigDecimal(numberString);
	        return String.format("%,d", number.longValue());
		} catch (NumberFormatException e) {
			logger.error("Invalid number format: " + numberString);
			return numberString;
		}
	}

	private static String addCommaSeparator(BigDecimal number) {
	    if (number == null) {
	        return null; // Return null for null input
	    }

	    final DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
	    try {
	        return decimalFormat.format(number);
	    } catch (Exception e) {
			logger.error("Invalid number format: " + number);
	        return number.toString(); // Return the original number as a string on error
	    }
	}

//	public void handlePayoutTransactionRejection(String invoiceID, PayoutHoldTxn holdTxnDetails, String reason) {
//
////		accountEnquiryDao.updatePayoutHoldTxnStatusByInvoiceID(invoiceID, "Rejected");
////		accountEnquiryDao.updatePayoutTxnDetailsStatusAndDeclinedReasonByInvoiceId(invoiceID, "pd", reason);
//
//		String merchantID = holdTxnDetails.getMerchantId();
//
//		// Trigger email to CS & Merchant regarding this Txn is rejected.
//		sendMaxLimitPayoutTransactionRejectionEmailToOperation(invoiceID, holdTxnDetails,
//				getMerchantDetailsByID(merchantID), getMobileUserByID(merchantID));
//
////		String settlementEmail = accountEnquiryDao.getMerchantSettlementEmailByID(Long.valueOf(merchantID));
////
////		if (settlementEmail != null && !settlementEmail.trim().isEmpty()) {
////		    sendMaxLimitPayoutTransactionRejectionEmailToMerchant(invoiceID, merchantID, settlementEmail);
////		} else {
////		    logger.warn("Settlement email not found for merchantID: " + merchantID);
////		}
//	}

	private static String sendMaxLimitPayoutTransactionApprovalEmailToOperation(String invoiceID,
			PayoutHoldTxn holdTxnDetails, Merchant merchantDetails, MobileUser mobileUser,String actionTakenBy) {

		try {
			String fromName = getPropertyValue("FROMNAME");
			String fromAddress = getPropertyValue("FROMMAIL");

			String toAddress = getPropertyWithDefault("PAYOUT_MAX_TXN_LIMIT_APPROVAL", "kavin@gomobi.io");

			String subject = "Confirmation: Approval for Exceeded Limit Payout Transaction";

			StringBuilder emailContent = new StringBuilder();
			emailContent.append("<!DOCTYPE html>");
			emailContent.append("<html lang=\"en\">");
			emailContent.append("<head>");
			emailContent.append("<meta charset=\"UTF-8\">");
			emailContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			emailContent.append("<title>Payout Approved</title>");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
			emailContent.append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">");
			emailContent.append("<style>");
			emailContent.append("@media (max-width: 600px) {");
			emailContent.append(".container { padding: 15px; margin: 20px auto !important; }");
			emailContent.append(".header td { padding-left: 2px !important; }");
			emailContent.append(".header img { width: 100px !important; height: 50px !important; }");
			emailContent.append(".title { font-size: 1.2rem !important; margin-bottom: 10px !important; }");
			emailContent.append(".details td { font-size: 10px !important; padding: 6px !important; padding-left: 2px !important; }");
			emailContent.append(".note, .contact { font-size: 10px !important; }");
			emailContent.append("}");
			emailContent.append("</style>");
			emailContent.append("</head>");
			emailContent.append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">");
			emailContent.append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>");
			emailContent.append("<table class=\"header\" style=\"width: 100%; text-align: left;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding-left: 2px;\">");
			emailContent.append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #4CAF50; font-size: 1.4rem; font-weight: bold; margin-bottom: 10px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>Payout Approved</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"intro\" style=\"width: 100%; font-size: 14px; margin-bottom: 15px; text-align: left; line-height: 1.6; color: #333739;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>Dear Operations Team,</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding-top: 8px;\">The payout request that exceeded the transaction limit has been approved. Please find the transaction details below:</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"details-header\" style=\"width: 100%; text-align: left; color: #333739; font-size: 14px; font-weight: bold; margin-top: 10px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>Transaction Details:</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant Name</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(merchantDetails.getBusinessName()).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Transaction ID</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(holdTxnDetails.getInvoiceIdProof()).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Requested Payout Amount (RM)</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(addCommaSeparator(holdTxnDetails.getAmount().replace(",", ""))).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Allowed Limit (RM)</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(addCommaSeparator(mobileUser.getPayoutTransactionLimit())).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Status</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">Approved (Limit Exceeded)</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Approved By</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(actionTakenBy).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Approval Date & Time</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(getCurrentTimestamp()).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">The payout will now proceed as requested, and the funds will be deducted from the merchant's account.</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"signature\" style=\"width: 100%; font-size: 14px; text-align: left; margin-top: 20px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">Best regards,</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">Engineering Team</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">mobi</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("</body>");
			emailContent.append("</html>");


//			emailContent.append("<!DOCTYPE html>")
//			            .append("<html lang=\"en\">")
//			            .append("<head>")
//			            .append("<meta charset=\"UTF-8\">")
//			            .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
//			            .append("<title>Payout Approved</title>")
//			            .append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">")
//			            .append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>")
//			            .append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">")
//			            .append("<style>")
//			            .append("@media (max-width: 600px) {")
//			            .append(".container {")
//			            .append("padding: 15px;")
//			            .append("margin: 20px auto !important;")
//			            .append("}")
//			            .append(".header td {")
//			            .append("padding-left: 2px !important;")
//			            .append("}")
//			            .append(".header img {")
//			            .append("width: 100px !important;")
//			            .append("height: 50px !important;")
//			            .append("}")
//			            .append(".title {")
//			            .append("font-size: 1.2rem !important;")
//			            .append("margin-bottom: 10px !important;")
//			            .append("}")
//			            .append(".details td {")
//			            .append("font-size: 10px !important;")
//			            .append("padding: 6px !important;")
//			            .append("padding-left: 2px !important;")
//			            .append("}")
//			            .append(".note,")
//			            .append(".contact {")
//			            .append("font-size: 10px !important;")
//			            .append("}")
//			            .append("}")
//			            .append("</style>")
//			            .append("</head>")
//			            .append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">")
//			            .append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">")
//			            .append("<tr>")
//			            .append("<td>")
//			            .append("<table class=\"header\" style=\"width: 100%; text-align: left;\">")
//			            .append("<tr>")
//			            .append("<td style=\"padding-left: 2px;\">")
//			            .append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">")
//			            .append("</td>")
//			            .append("</tr>")
//			            .append("</table>")
//			            .append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #4CAF50; font-size: 1.4rem !important; font-weight: bold; margin-bottom: 10px;\">")
//
//			            .append("<tr>")
//			            .append("<td>Payout Approved</td>")
//			            .append("</tr>")
//			            .append("</table>")
//			            .append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">")
//
//			            .append("<tr>")
//			            .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant Name</td>")
//			            .append("<td>:</td>")
//			            .append("<td style=\"padding: 5px; color: #333739;\">").append(merchantDetails.getBusinessName()).append("</td>")
//			            .append("</tr>")
//
//			            .append("<tr>")
//			            .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Transaction ID</td>")
//			            .append("<td>:</td>")
//			            .append("<td style=\"padding: 5px; color: #333739;\">").append(holdTxnDetails.getInvoiceIdProof()).append("</td>")
//			            .append("</tr>")
//
//			            .append("<tr>")
//			            .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Requested Payout Amount (RM)</td>")
//			            .append("<td>:</td>")
//			            .append("<td style=\"padding: 5px; color: #333739;\">").append(holdTxnDetails.getAmount().replace(",", "")).append("</td>")
//			            .append("</tr>")
//
//			            .append("<tr>")
//			            .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Allowed Limit (RM)</td>")
//			            .append("<td>:</td>")
//			            .append("<td style=\"padding: 5px; color: #333739;\">").append(mobileUser.getPayoutTransactionLimit()).append("</td>")
//			            .append("</tr>")
//
//			            .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Status</td>")
//			            .append("<td>:</td>")
//			            .append("<td style=\"padding: 5px; color: #333739;\">").append("Approved (Limit Exceeded)").append("</td>")
//			            .append("</tr>")
//
//			            .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Approved By:</td>")
//			            .append("<td>:</td>")
//			            .append("<td style=\"padding: 5px; color: #333739;\">").append(merchantDetails.getUsername()).append("</td>")
//			            .append("</tr>")
//
//			            .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Approval Date & Time:</td>")
//			            .append("<td>:</td>")
//			            .append("<td style=\"padding: 5px; color: #333739;\">").append(getCurrentTimestamp()).append("</td>")
//			            .append("</tr>")
//
//			            .append("</table>")
//			            .append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
////			            .append("<tr>")
////			            .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Confirmation</td>")
////			            .append("</tr>")
//
//			            .append("<tr>")
//			            .append("<td style=\"color: #333739;\">The payout will now proceed as requested, and the funds will be deducted from the merchant�s account.</td>")
//			            .append("</tr>")
//
//			            .append("</table>")
////			            .append("<table class=\"instructions\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
////			            .append("<tr>")
////			            .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Next Steps</td>")
////			            .append("</tr>")
////			            .append("<tr>")
////			            .append("<td style=\"color: #333739;\">The payout will now be processed, and the merchant will be notified accordingly.</td>")
////			            .append("</tr>")
////			            .append("</table>")
////			            .append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
////			            .append("<tr>")
////			            .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Need Help? Contact Information</td>")
////			            .append("</tr>")
////			            .append("<tr>")
////			            .append("<td>")
////			            .append("<a href=\"mailto:csmobi@gomobi.io\" style=\"text-decoration: none; color: #333739;\">csmobi@gomobi.io</a>, <a style=\"color: #333739 !important; text-decoration: none;\"> +60 10-970-7880</a>")
////			            .append("</td>")
////			            .append("</tr>")
////			            .append("</table>")
//			            .append("</td>")
//			            .append("</tr>")
//			            .append("</table>")
//			            .append("</body>")
//			            .append("</html>");


			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, null, null, emailContent.toString());

			logger.info(String.format(
					"DECLINED: Max payout transaction limit reached, triggering email to merchant- Invoice ID: %s, From Address: %s, To Address: %s, Subject: %s. Sent Successfully: %d",
					invoiceID, fromAddress, toAddress, subject, mailResponse));

			return String.valueOf(mailResponse);
		} catch (Exception pe) {
			logger.error("Exception while sending mail: " + pe.getMessage(), pe);
			return "400";
		}
	}

	private static String sendMaxLimitPayoutTransactionRejectionEmailToMerchant(String invoiceID, String merchantID, String toAddress) {

		try {
			String fromName = getPropertyValue("FROMNAME");
			String fromAddress = getPropertyValue("FROMMAIL");

			String subject = "Payout transaction rejected";

			StringBuilder emailContent = new StringBuilder()
				    .append("<!DOCTYPE html>")
				    .append("<html lang=\"en\">")
				    .append("<head>")
				    .append("<meta charset=\"UTF-8\">")
				    .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
				    .append("<title>Payout Rejected - Exceeded Max Limit</title>")
				    .append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">")
				    .append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>")
				    .append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">")
				    .append("<style>")
				    .append("@media (max-width: 600px) {")
				    .append(".container {")
				    .append("padding: 15px;")
				    .append("margin: 20px auto !important;")
				    .append("}")
				    .append(".header td {")
				    .append("padding-left: 2px !important;")
				    .append("}")
				    .append(".header img {")
				    .append("width: 100px !important;")
				    .append("height: 50px !important;")
				    .append("}")
				    .append(".title {")
				    .append("font-size: 1.2rem !important;")
				    .append("margin-bottom: 10px !important;")
				    .append("}")
				    .append(".details td {")
				    .append("font-size: 10px !important;")
				    .append("padding: 6px !important;")
				    .append("padding-left: 2px !important;")
				    .append("}")
				    .append(".note,")
				    .append(".contact {")
				    .append("font-size: 10px !important;")
				    .append("}")
				    .append("}")
				    .append("</style>")
				    .append("</head>")
				    .append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">")
				    .append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">")
				    .append("<tr>")
				    .append("<td>")
				    .append("<table class=\"header\" style=\"width: 100%; text-align: left;\">")
				    .append("<tr>")
				    .append("<td style=\"padding-left: 2px;\">")
				    .append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">")
				    .append("</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #FF0000; font-size: 1.4rem !important; font-weight: bold; margin-bottom: 10px;\">")
				    .append("<tr>")
				    .append("<td>Payout Rejected</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("<table class=\"greeting\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
				    .append("<tr>")
				    .append("<td style=\"color: #333739;\">Dear [Merchant Name],</td>")
				    .append("</tr>")
				    .append("<tr>")
				    .append("<td style=\"color: #333739;\">We regret to inform you that your recent payout request was declined. Please find the details of the transaction below:</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">")
				    .append("<tr>")
				    .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant Name</td>")
				    .append("<td>:</td>")
				    .append("<td style=\"padding: 5px; color: #333739;\">[Merchant Name]</td>")
				    .append("</tr>")
				    .append("<tr>")
				    .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant ID</td>")
				    .append("<td>:</td>")
				    .append("<td style=\"padding: 5px; color: #333739;\">[Merchant ID]</td>")
				    .append("</tr>")
				    .append("<tr>")
				    .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Payout Amount (MYR)</td>")
				    .append("<td>:</td>")
				    .append("<td style=\"padding: 5px; color: #333739;\">[Payout Amount]</td>")
				    .append("</tr>")
				    .append("<tr>")
				    .append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Max Allowed Limit (MYR)</td>")
				    .append("<td>:</td>")
				    .append("<td style=\"padding: 5px; color: #333739;\">[Max Allowed Limit]</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
				    .append("<tr>")
				    .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Reason</td>")
				    .append("</tr>")
				    .append("<tr>")
				    .append("<td style=\"color: #333739;\">Your payout request has been rejected as it exceeds the maximum limit set for your account.</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("<table class=\"instructions\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
				    .append("<tr>")
				    .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Next Steps</td>")
				    .append("</tr>")
				    .append("<tr>")
				    .append("<td style=\"color: #333739;\">Please review the transaction by logging into the <a href=\"https://portal.gomobi.io/MobiversaAdmin/\" style=\"color: #FF0000; text-decoration: none;\">Merchant Portal</a> for more details. If you have any further inquiries, feel free to contact our support team.</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
				    .append("<tr>")
				    .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Need Help? Contact Information</td>")
				    .append("</tr>")
				    .append("<tr>")
				    .append("<td style=\"color: #333739;\">If you need assistance, please reach out to our support team via email at <a href=\"mailto:support@gomobi.io\" style=\"color: #FF0000; text-decoration: none;\">support@gomobi.io</a>.</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("<table class=\"footer\" style=\"width: 100%; font-size: 12px; color: #999999; margin-top: 20px; text-align: center;\">")
				    .append("<tr>")
				    .append("<td>&copy; 2024 Mobiversa. All rights reserved.</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("</td>")
				    .append("</tr>")
				    .append("</table>")
				    .append("</body>")
					.append("</html>");

			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, null, null, emailContent.toString());

			logger.info(String.format(
					"DECLINED: Max payout transaction limit reached, triggering email to merchant- Invoice ID: %s, From Address: %s, To Address: %s, Subject: %s. Sent Successfully: %d",
					invoiceID, fromAddress, toAddress, subject, mailResponse));

			return String.valueOf(mailResponse);
		} catch (Exception pe) {
			logger.error("Exception while sending mail: " + pe.getMessage(), pe);
			return "400";
		}
	}

	private static String sendMaxLimitPayoutTransactionRejectionEmailToOperation(String invoiceID,
			PayoutHoldTxn holdTxnDetails, Merchant merchantDetails, MobileUser mobileUser,String actionTakenBy) {
		try {
			String fromName = getPropertyValue("FROMNAME");
			String fromAddress = getPropertyValue("FROMMAIL");

			String toAddress = getPropertyWithDefault("PAYOUT_MAX_TXN_LIMIT_APPROVAL", "kavin@gomobi.io");

			String subject = "Confirmation: Rejection of Exceeded Limit Payout Transaction";

			StringBuilder emailContent = new StringBuilder();
			emailContent.append("<!DOCTYPE html>");
			emailContent.append("<html lang=\"en\">");
			emailContent.append("<head>");
			emailContent.append("<meta charset=\"UTF-8\">");
			emailContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			emailContent.append("<title>Payout Rejected</title>");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
			emailContent.append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">");
			emailContent.append("<style>");
			emailContent.append("@media (max-width: 600px) {");
			emailContent.append(".container { padding: 15px; margin: 20px auto !important; }");
			emailContent.append(".header td { padding-left: 2px !important; }");
			emailContent.append(".header img { width: 100px !important; height: 50px !important; }");
			emailContent.append(".title { font-size: 1.2rem !important; margin-bottom: 10px !important; color: #FF0000 !important; }");
			emailContent.append(".details td { font-size: 10px !important; padding: 6px !important; padding-left: 2px !important; }");
			emailContent.append(".note, .contact { font-size: 10px !important; }");
			emailContent.append("}");
			emailContent.append("</style>");
			emailContent.append("</head>");
			emailContent.append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">");
			emailContent.append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>");
			emailContent.append("<table class=\"header\" style=\"width: 100%; text-align: left;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding-left: 2px;\">");
			emailContent.append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #FF0000; font-size: 1.4rem; font-weight: bold; margin-bottom: 10px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>Payout Rejected</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"intro\" style=\"width: 100%; font-size: 14px; margin-bottom: 15px; text-align: left; line-height: 1.6; color: #333739;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>Dear Operations Team,</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding-top: 8px;\">The payout request that exceeded the transaction limit has been rejected and the transaction has been recorded as follows:</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"details-header\" style=\"width: 100%; text-align: left; font-size: 14px; font-weight: bold; margin-top: 10px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>Transaction Details:</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant Name</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(merchantDetails.getBusinessName()).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Transaction ID</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(holdTxnDetails.getInvoiceIdProof()).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Requested Payout Amount (RM)</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(addCommaSeparator(holdTxnDetails.getAmount().replace(",", ""))).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Allowed Limit (RM)</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(addCommaSeparator(mobileUser.getPayoutTransactionLimit())).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold;\">Status</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px;\">Declined (Limit exceeded)</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Approved By</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(actionTakenBy).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">Approval Date & Time</td>");
			emailContent.append("<td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">").append(getCurrentTimestamp()).append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">Please let us know if any further action is required.</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"signature\" style=\"width: 100%; font-size: 14px; text-align: left; margin-top: 20px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">Best regards,</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">Engineering Team</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">mobi</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("</body>");
			emailContent.append("</html>");

//			StringBuilder emailContent = new StringBuilder()
//					.append("<!DOCTYPE html>")
//				    .append("<html lang=\"en\">")
//				    .append("  <head>")
//				    .append("    <meta charset=\"UTF-8\">")
//				    .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
//				    .append("    <title>Payout Rejected</title>")
//				    .append("    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">")
//				    .append("    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>")
//				    .append("    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">")
//				    .append("    <style>")
//				    .append("      @media (max-width: 600px) {")
//				    .append("        .container { padding: 15px; margin: 20px auto !important; }")
//				    .append("        .header td { padding-left: 2px !important; }")
//				    .append("        .header img { width: 100px !important; height: 50px !important; }")
//				    .append("        .title { font-size: 1.2rem !important; margin-bottom: 10px !important; }")
//				    .append("        .details td { font-size: 10px !important; padding: 6px !important; padding-left: 2px !important; }")
//				    .append("        .note, .contact { font-size: 10px !important; }")
//				    .append("      }")
//				    .append("    </style>")
//				    .append("  </head>")
//				    .append("  <body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">")
//				    .append("    <table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">")
//				    .append("      <tr>")
//				    .append("        <td>")
//				    .append("          <table class=\"header\" style=\"width: 100%; text-align: left;\">")
//				    .append("            <tr>")
//				    .append("              <td style=\"padding-left: 2px;\">")
//				    .append("                <img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">")
//				    .append("              </td>")
//				    .append("            </tr>")
//				    .append("          </table>")
//				    .append("          <table class=\"title\" style=\"width: 100%; text-align: left; color: #FF0000; font-size: 1.4rem !important; font-weight: bold; margin-bottom: 10px;\">")
//				    .append("            <tr>")
//				    .append("              <td>Payout Rejected</td>")
//				    .append("            </tr>")
//				    .append("          </table>")
//				    .append("          <table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">")
//				    .append("            <tr>")
//				    .append("              <td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant Name</td>")
//				    .append("              <td>:</td>")
//				    .append("              <td style=\"padding: 5px; color: #333739;\">[Merchant Name]</td>")
//				    .append("            </tr>")
//				    .append("            <tr>")
//				    .append("              <td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant ID</td>")
//				    .append("              <td>:</td>")
//				    .append("              <td style=\"padding: 5px; color: #333739;\">[Merchant ID]</td>")
//				    .append("            </tr>")
//				    .append("            <tr>")
//				    .append("              <td style=\"padding: 5px; font-weight: bold; color: #333739;\">Payout Amount (MYR)</td>")
//				    .append("              <td>:</td>")
//				    .append("              <td style=\"padding: 5px; color: #333739;\">[Payout Amount]</td>")
//				    .append("            </tr>")
//				    .append("            <tr>")
//				    .append("              <td style=\"padding: 5px; font-weight: bold; color: #333739;\">Max Allowed Limit (MYR)</td>")
//				    .append("              <td>:</td>")
//				    .append("              <td style=\"padding: 5px; color: #333739;\">[Max Allowed Limit]</td>")
//				    .append("            </tr>")
//				    .append("          </table>")
//				    .append("          <table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//				    .append("            <tr>")
//				    .append("              <td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Reason</td>")
//				    .append("            </tr>")
//				    .append("            <tr>")
//				    .append("              <td style=\"color: #333739;\">The payout request for the merchant has been rejected as it exceeds the maximum limit set for this merchant.</td>")
//				    .append("            </tr>")
//				    .append("          </table>")
//				    .append("          <table class=\"instructions\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//				    .append("            <tr>")
//				    .append("              <td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Next Steps</td>")
//				    .append("            </tr>")
//				    .append("            <tr>")
//				    .append("              <td style=\"color: #333739;\">The payout will not be initiated. An email notification has been sent to the merchant regarding the rejection.</td>")
//				    .append("            </tr>")
//				    .append("          </table>")
//				    .append("          <table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//				    .append("            <tr>")
//				    .append("              <td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Need Help? Contact Information</td>")
//				    .append("            </tr>")
//				    .append("            <tr>")
//				    .append("              <td>")
//				    .append("                <a href=\"mailto:csmobi@gomobi.io\" style=\"text-decoration: none; color: #333739;\">csmobi@gomobi.io</a>, <a style=\"color: #333739 !important; text-decoration: none;\"> +60 10-970-7880</a>")
//				    .append("              </td>")
//				    .append("            </tr>")
//				    .append("          </table>")
//				    .append("        </td>")
//				    .append("      </tr>")
//				    .append("    </table>")
//				    .append("  </body>")
//					.append("</html>");

			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, null, null, emailContent.toString());

			logger.info(String.format(
					"DECLINED: Max payout transaction limit reached, triggering email to finance - Invoice ID: %s, From Address: %s, To Address: %s, Subject: %s. Sent Successfully: %d",
					invoiceID, fromAddress, toAddress, subject, mailResponse));

			return String.valueOf(mailResponse);
		} catch (Exception pe) {
			logger.error("Exception while sending mail: " + pe.getMessage(), pe);
			return "400";
		}
	}

//	StringBuilder maxTxnRequestionApprovalContent = new StringBuilder();
//
//	emailContent.append("<!DOCTYPE html>")
//	    .append("<html lang=\"en\">")
//	    .append("<head>")
//	    .append("<meta charset=\"UTF-8\">")
//	    .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
//	    .append("<title>Max Payout Limit Exceeded</title>")
//	    .append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">")
//	    .append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>")
//	    .append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">")
//	    .append("<style>")
//	    .append("@media (max-width: 600px) {")
//	    .append(".container { padding: 15px; margin: 20px auto !important; }")
//	    .append(".header td { padding-left: 2px !important; }")
//	    .append(".header img { width: 100px !important; height: 50px !important; }")
//	    .append(".title { font-size: 1.2rem !important; margin-bottom: 10px !important; }")
//	    .append(".details td { font-size: 10px !important; padding: 6px !important; padding-left: 2px !important; }")
//	    .append(".note, .contact { font-size: 10px !important; }")
//	    .append("}")
//	    .append("</style>")
//	    .append("</head>")
//	    .append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">")
//	    .append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">")
//	    .append("<tr><td>")
//	    .append("<table class=\"header\" style=\"width: 100%; text-align: left;\">")
//	    .append("<tr><td style=\"padding-left: 2px;\">")
//	    .append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">")
//	    .append("</td></tr>")
//	    .append("</table>")
//	    .append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #ff4d4d; font-size: 1.4rem !important; font-weight: bold; margin-bottom: 10px;\">")
//	    .append("<tr><td>Exceeded Max Payout Limit</td></tr>")
//	    .append("</table>")
//	    .append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">")
//	    .append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant Name</td><td>:</td><td style=\"padding: 5px; color: #333739;\">[Merchant Name]</td></tr>")
//	    .append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant ID</td><td>:</td><td style=\"padding: 5px; color: #333739;\">[Merchant ID]</td></tr>")
//	    .append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Payout Amount (MYR)</td><td>:</td><td style=\"padding: 5px; color: #333739;\">[Payout Amount]</td></tr>")
//	    .append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Max Allowed Limit (MYR)</td><td>:</td><td style=\"padding: 5px; color: #333739;\">[Max Allowed Limit]</td></tr>")
//	    .append("</table>")
//	    .append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//	    .append("<tr><td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Note</td></tr>")
//	    .append("<tr><td style=\"color: #333739;\">The payout request exceeds the maximum limit set for this merchant. Please review and take action accordingly.</td></tr>")
//	    .append("</table>")
//	    .append("<table class=\"instructions\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//	    .append("<tr><td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Action Required</td></tr>")
//	    .append("<tr><td style=\"color: #333739;\">The operations team can use their respective login at the <a href=\"https://portal.gomobi.io/MobiversaAdmin/\" style=\"color: #ff4d4d; text-decoration: none;\">Portal</a> to approve or reject the held transactions. Once approved, the payout will be processed.</td></tr>")
//	    .append("</table>")
//	    .append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//	    .append("<tr><td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Need Help? Contact Information</td></tr>")
//	    .append("<tr><td><a href=\"mailto:csmobi@gomobi.io\" style=\"text-decoration: none; color: #333739;\">csmobi@gomobi.io</a>, <a style=\"color: #333739 !important; text-decoration: none;\"> +60 10-970-7880</a></td></tr>")
//	    .append("</table>")
//	    .append("</td></tr>")
//	    .append("</table>")
//	    .append("</body>")
//	    .append("</html>");
//
//	String htmlContent = emailContent.toString();

		public String getRoleFromUserName(String userName)
		{
			String role = null;
			try{
				BankUser bankUser = userDao.findByUserName(userName);
				 role = bankUser.getDepartment();
			}
			catch(Exception e)
			{
				logger.error("Exception occured while searching for Bankuser : "+e.getMessage(),e);
				role = "OPR";
			}
			return role;
		}

		public static byte[] generateMerchantPayinDetailExcelContentForEmail(List<FinanceReport> financeReport, String fromDate,
				String toDate, String businessName) {
			try (HSSFWorkbook workbook = new HSSFWorkbook()) {
				HSSFSheet sheet = workbook.createSheet("Payin Transaction Report");
				logger.info("Payin Transaction Report");

				// Set metadata
				int metaRowIndex = 0;

				HSSFRow reportTypeRow = sheet.createRow(metaRowIndex++);

				reportTypeRow.createCell(0).setCellValue("Report Type:");
				reportTypeRow.createCell(1).setCellValue("Payin Report");

				String date1 = convertDateFormatTo_dd_MM_yyyy(fromDate);
				String date2 = convertDateFormatTo_dd_MM_yyyy(toDate);
				HSSFRow fromDateRow = sheet.createRow(metaRowIndex++);
				fromDateRow.createCell(0).setCellValue("From");
				fromDateRow.createCell(1).setCellValue(date1);

				HSSFRow toDateRow = sheet.createRow(metaRowIndex++);
				toDateRow.createCell(0).setCellValue("To");
				toDateRow.createCell(1).setCellValue(date2);

				HSSFRow toTimeRow = sheet.createRow(metaRowIndex++);
				toTimeRow.createCell(0).setCellValue("Time");
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
				String formattedDateTime = now.format(formatter);
				toTimeRow.createCell(1).setCellValue(formattedDateTime);

				// Collect unique payment methods
				Set<String> paymentMethods = new HashSet<>();
				for (FinanceReport report : financeReport) {
					if (report.getPaymentmethod() != null && !report.getPaymentmethod().isEmpty()) {
						paymentMethods.add(report.getPaymentmethod());
					}
				}
				String paymentMethodsString = paymentMethods.stream().collect(Collectors.joining(","));

				// Add payment methods to the Nature row
				HSSFRow toNatureRow = sheet.createRow(metaRowIndex++);
				toNatureRow.createCell(0).setCellValue("Nature");
				toNatureRow.createCell(1).setCellValue(paymentMethodsString.isEmpty() ? null : paymentMethodsString);

				HSSFRow toMercNameRow = sheet.createRow(metaRowIndex++);
				toMercNameRow.createCell(0).setCellValue("Merchant Name");
				toMercNameRow.createCell(1).setCellValue(businessName);

				// Total number of lines (unique txntypes)
				int totalLines = financeReport.size();

				// Add total lines dynamically
				HSSFRow toTotalLinesRow = sheet.createRow(metaRowIndex++);
				toTotalLinesRow.createCell(0).setCellValue("Total Lines");
				toTotalLinesRow.createCell(1).setCellValue(totalLines);

				// Total Transaction Amount (sum of all transaction amounts)
				double totalTransactionAmount = financeReport.stream()
						.filter(report -> report.getAmount() != null && !report.getAmount().isEmpty()
								&& !report.getAmount().equalsIgnoreCase("null"))
						.mapToDouble(report -> Double.parseDouble(report.getAmount().replace(",", ""))) // Convert String to
																										// double, removing
																										// commas
						.sum();
				logger.info("toTotalLinesRow::::" + toTotalLinesRow);

				HSSFRow toTxnAmtRow = sheet.createRow(metaRowIndex++);
				toTxnAmtRow.createCell(0).setCellValue("Total Transaction Amount");
				toTxnAmtRow.createCell(1).setCellValue(totalTransactionAmount);

				// Total Net Amount (sum of all net amounts)
				double totalNetAmount = financeReport.stream()
						.filter(report -> report.getNetamount() != null && !report.getNetamount().isEmpty()
								&& !report.getNetamount().equalsIgnoreCase("null"))
						.mapToDouble(report -> Double.parseDouble(report.getNetamount().replace(",", ""))).sum();
				HSSFRow toNetAmtRow = sheet.createRow(metaRowIndex++);
				toNetAmtRow.createCell(0).setCellValue("Total NetAmount");
				toNetAmtRow.createCell(1).setCellValue(totalNetAmount);
				

				// Total MDR Amount (sum of all MDR amounts)
				double totalMDRAmount = financeReport.stream()
						.filter(report -> report.getMdramount() != null && !report.getMdramount().isEmpty()
								&& !report.getMdramount().equalsIgnoreCase("null"))
						.mapToDouble(report -> Double.parseDouble(report.getMdramount())).sum();

				HSSFRow toMdrAmtRow = sheet.createRow(metaRowIndex++);
				toMdrAmtRow.createCell(0).setCellValue("Total MDR Amount");
				toMdrAmtRow.createCell(1).setCellValue(totalMDRAmount);

				// Leave an empty row between metadata and headers
				metaRowIndex++;

				// Set header
				HSSFRow header = sheet.createRow(metaRowIndex);
				String[] headers = PropertyLoad.getFile().getProperty("PAYIN_MERCHANT_REPORT_XLS_EXPORT_HEADER").toUpperCase()
						.split(",");
				for (int i = 0; i < headers.length; i++) {
					header.createCell(i).setCellValue(headers[i]);
				}

				// Set rows
				int rowIndex = metaRowIndex + 1;
				for (FinanceReport report : financeReport) {
					HSSFRow row = sheet.createRow(rowIndex++);
					row.createCell(0).setCellValue(report.getDate().replace("/", "-"));
					row.createCell(1).setCellValue(report.getTime());
					row.createCell(2).setCellValue(report.getMid());
					row.createCell(3).setCellValue(report.getTid());
					row.createCell(4).setCellValue(report.getAmount());
					row.createCell(5).setCellValue(report.getReference());
					row.createCell(6).setCellValue(report.getApprovalcode());
					row.createCell(7).setCellValue(report.getRrn());
					row.createCell(8).setCellValue(report.getStatus());
					row.createCell(9).setCellValue(report.getPaymentmethod());
					row.createCell(10).setCellValue(report.getMdramount());
					row.createCell(11).setCellValue(report.getNetamount());
					row.createCell(12).setCellValue(report.getPaymentdate());
					row.createCell(13).setCellValue(report.getEzysettleamount());
					row.createCell(14).setCellValue(report.getSubmerchantid());
					
				}

				logger.info("Payin Transaction Report--Completed");
				// Convert workbook to byte array
				try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
					workbook.write(bos);
					return bos.toByteArray();
				}

			} catch (Exception e) {
				logger.error("Error generating Excel content for payin report", e);
				return null;
			}
		}

		public static byte[] generateMerchantPayoutReportXLSContentForEmail(List<FinanceReport> financeReport, String fromDate,
				String toDate, String merchantName) {
			try (HSSFWorkbook workbook = new HSSFWorkbook()) {
				HSSFSheet sheet = workbook.createSheet("Payout Transaction Report");
				logger.info("Payout Transaction Report");
				int metaRowIndex = 0;

				HSSFRow reportTypeRow1 = sheet.createRow(metaRowIndex++);
				reportTypeRow1.createCell(0).setCellValue("Report Type:");
				reportTypeRow1.createCell(1).setCellValue("Payout Report");

				String date1 = convertDateFormatTo_dd_MM_yyyy(fromDate);
				String date2 = convertDateFormatTo_dd_MM_yyyy(toDate);
				HSSFRow fromDateRow = sheet.createRow(metaRowIndex++);
				fromDateRow.createCell(0).setCellValue("From");
				fromDateRow.createCell(1).setCellValue(date1);

				HSSFRow toDateRow = sheet.createRow(metaRowIndex++);
				toDateRow.createCell(0).setCellValue("To");
				toDateRow.createCell(1).setCellValue(date2);

				HSSFRow toTimeRow = sheet.createRow(metaRowIndex++);
				toTimeRow.createCell(0).setCellValue("Time");
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
				String formattedDateTime = now.format(formatter);
				toTimeRow.createCell(1).setCellValue(formattedDateTime);

				HSSFRow toNatureRow1 = sheet.createRow(metaRowIndex++);
				toNatureRow1.createCell(0).setCellValue("Nature");
				toNatureRow1.createCell(1).setCellValue("PAYOUT");

				HSSFRow toMercNameRow = sheet.createRow(metaRowIndex++);
				toMercNameRow.createCell(0).setCellValue("Merchant Name");
				toMercNameRow.createCell(1).setCellValue(merchantName);

				// Total number of lines (unique txntypes)
				int totalLines = financeReport.size();

				// Add total lines dynamically
				HSSFRow toTotalLinesRow = sheet.createRow(metaRowIndex++);
				toTotalLinesRow.createCell(0).setCellValue("Total Lines");
				toTotalLinesRow.createCell(1).setCellValue(totalLines);

				// Total Transaction Amount (sum of all transaction amounts)
				double totalTransactionAmount = financeReport.stream()
						.filter(report -> report.getAmount() != null && !report.getAmount().isEmpty()
								&& !report.getAmount().equalsIgnoreCase("null"))
						.mapToDouble(report -> Double.parseDouble(report.getAmount())) // Convert String to double
						.sum();
				logger.info("totalTransactionAmount::::" + totalTransactionAmount);

				HSSFRow toTxnAmtRow = sheet.createRow(metaRowIndex++);
				toTxnAmtRow.createCell(0).setCellValue("Total Transaction Amount");
				toTxnAmtRow.createCell(1).setCellValue(totalTransactionAmount);

				// Calculate Total Payout Fee
				double totalPayoutFee = financeReport.stream()
						.filter(report -> report.getPayoutFee() != null && !report.getPayoutFee().isEmpty()
								&& !report.getPayoutFee().equalsIgnoreCase("null"))
						.mapToDouble(report -> Double.parseDouble(report.getPayoutFee())) // Convert String to double
						.sum();
				logger.info("Total Payout Fee: " + totalPayoutFee);

				HSSFRow toPayoutFeeRow = sheet.createRow(metaRowIndex++);
				toPayoutFeeRow.createCell(0).setCellValue("Total Payout Fee");
				toPayoutFeeRow.createCell(1).setCellValue(totalPayoutFee);

				// Calculate Total Payout Amount
				double totalPayoutAmt = financeReport.stream()
						.filter(report -> report.getPayoutamount() != null && !report.getPayoutamount().isEmpty()
								&& !report.getPayoutamount().equalsIgnoreCase("null"))
						.mapToDouble(report -> Double.parseDouble(report.getPayoutamount())) // Convert String to double
						.sum();
				logger.info("Total Payout Amout: " + totalPayoutAmt);
				HSSFRow totalPayoutAmtRow = sheet.createRow(metaRowIndex++);
				totalPayoutAmtRow.createCell(0).setCellValue("Total Payout Amount");
				totalPayoutAmtRow.createCell(1).setCellValue(totalPayoutAmt);
				


				// Leave an empty row between metadata and headers
				metaRowIndex++;

				// Set header
				HSSFRow header = sheet.createRow(metaRowIndex);
				String[] headers = PropertyLoad.getFile().getProperty("PAYOUT_MERCHANT_REPORT_XLS_EXPORT_HEADER").toUpperCase()
						.split(",");
				for (int i = 0; i < headers.length; i++) {
					header.createCell(i).setCellValue(headers[i]);
				}

				// Set rows
				int rowIndex = metaRowIndex + 1;
				for (FinanceReport report : financeReport) {
					HSSFRow row = sheet.createRow(rowIndex++);
					row.createCell(0).setCellValue(report.getDate().replace("/", "-"));
					row.createCell(1).setCellValue(report.getTime());
					row.createCell(2).setCellValue(report.getCustomerName());
					row.createCell(3).setCellValue(report.getBrn());
					row.createCell(4).setCellValue(report.getAccountNo());
					row.createCell(5).setCellValue(report.getBankName());
					row.createCell(6).setCellValue(report.getTransaction_id());
					row.createCell(7).setCellValue(report.getPayoutamount());
					row.createCell(8).setCellValue(report.getPayoutFee());
					row.createCell(9).setCellValue(report.getStatus());
					row.createCell(10).setCellValue(report.getPaidTime());
					row.createCell(11).setCellValue(report.getPaidDate());
					row.createCell(12).setCellValue(report.getSubmerchantMid());
					row.createCell(13).setCellValue(report.getSubmerchantName());
					row.createCell(14).setCellValue(report.getPayoutId());
					row.createCell(15).setCellValue(report.getAmount());
					row.createCell(16).setCellValue(report.getPayoutType());
				}

				logger.info("Payout Transaction Report");
				// Convert workbook to byte array
				try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
					workbook.write(bos);
					return bos.toByteArray();
				}
			} catch (Exception e) {
				logger.error("Error generating Excel content for payout report", e);
				return null;
			}
		}
}
