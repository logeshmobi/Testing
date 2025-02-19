package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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
import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MerchantStatusHistory;
import com.mobiversa.common.bo.MerchantUserRole;
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
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.DBConnection;
import com.mobiversa.payment.util.Justsettle;
import com.mobiversa.payment.util.MobiliteTrackDetails;
import com.mobiversa.payment.util.PayeeDetails;
import com.mobiversa.payment.util.Payer;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.Utils;

@Component
@Repository
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class MerchantDaoImpl extends BaseDAOImpl implements MerchantDao {

	@Override
	@Transactional(readOnly = false)
	public void listMerchantUser(final PaginationBean<Merchant> paginationBean, final ArrayList<Criterion> props) {
		logger.info("MerchantDaoImpl:listMerchantUser");
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.desc("activateDate"));

	}

	@Override
	public TransactionRequest listMerchantCardDetails(final BigInteger TxnID) {
		// logger.info("MID: "+TxnID);
		return (TransactionRequest) getSessionFactory().createCriteria(TransactionRequest.class)
				.add(Restrictions.eq("txnId", TxnID)).setMaxResults(1).uniqueResult();
	}

	@Override
	public EzyRecurringPayment loadMerchantRecurring(final Long recID) {
		logger.info("MerchantDaoImpl:loadMerchant");
		return (EzyRecurringPayment) getSessionFactory().createCriteria(EzyRecurringPayment.class)
				.add(Restrictions.eq("id", recID)).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public void listRecurringMerchantUser(final PaginationBean<EzyRecurringPayment> paginationBean,
			final ArrayList<Criterion> props) {
		// logger.info("MerchantDaoImpl:listMerchantUser");
		super.getPaginationItemsByPage(paginationBean, EzyRecurringPayment.class, props, Order.desc("timeStamp"));

	}

	@Override
	@Transactional(readOnly = false)
	public void listMotoTxnReqMerchantUser(final PaginationBean<MotoTxnDetails> paginationBean,
			final ArrayList<Criterion> props) {
		// logger.info("MerchantDaoImpl:listMerchantUser");
		// super.getPaginationItemsByPage(paginationBean, MotoTxnDetails.class, props,
		// Order.desc("timestamp"));

	}

	public void loadReqMotoData(final PaginationBean<MotoTxnDetails> paginationBean, String fromDate, String toDate,
			String status) {

		ArrayList<MotoTxnDetails> forSettlementList = new ArrayList<MotoTxnDetails>();

		/*
		 * List<Object[]> resultSet = (List<Object[]>)
		 * super.getSessionFactory().createCriteria(Merchant.class, "m")
		 * .createCriteria("mid.MERCHANT_FK", "mi")
		 * .createCriteria("for_settlement.mid", "f"). add(Restrictions.))
		 * .setProjection(Projections.projectionList().add(Projections.property("m.Id"))
		 * .add(Projections.property("mi.MERCHANT_FK")).add(Projections.property("f.mid"
		 * ))) .list();
		 */
		logger.info("fromDate: " + fromDate + " toDate: " + toDate);
		String sql = null;
		Query sqlQuery = null;
		if (fromDate != null && toDate != null && status != null) {
			if (status.equals("I")) {
				sql = "select f.MID,m.BUSINESS_NAME,f.TIME_STAMP,f.`STATUS`,f.AMOUNT,f.TID from MERCHANT m "
						+ "inner join MID mi on m.ID=mi.MERCHANT_FK inner join MOTO_TXN_DETAILS f "
						+ "on f.MID=mi.MOTO_MID or f.MID=mi.EZYREC_MID or f.MID=mi.FIUU_MID where f.TIME_STAMP between :fromDate and :toDate "
						+ "and f.`STATUS` is null order by f.TIME_STAMP desc";
				sqlQuery = super.getSessionFactory().createSQLQuery(sql);
				sqlQuery.setString("fromDate", fromDate);
				sqlQuery.setString("toDate", toDate);
				/* sqlQuery.setString("status", status); */
			} else {
				sql = "select f.MID,m.BUSINESS_NAME,f.TIME_STAMP,f.`STATUS`,f.AMOUNT,f.TID from MERCHANT m "
						+ "inner join MID mi on m.ID=mi.MERCHANT_FK inner join MOTO_TXN_DETAILS f "
						+ "on f.MID=mi.MOTO_MID or f.MID=mi.EZYREC_MID or f.MID=mi.FIUU_MID where f.TIME_STAMP between :fromDate and :toDate "
						+ "and f.`STATUS`= :status order by f.TIME_STAMP desc";
				sqlQuery = super.getSessionFactory().createSQLQuery(sql);
				sqlQuery.setString("fromDate", fromDate);
				sqlQuery.setString("toDate", toDate);
				sqlQuery.setString("status", status);
			}

		} else {
			sql = "select f.MID,m.BUSINESS_NAME,f.TIME_STAMP,f.`STATUS`,f.AMOUNT,f.TID from MERCHANT m "
					+ "inner join MID mi on m.ID=mi.MERCHANT_FK inner join MOTO_TXN_DETAILS f on "
					+ "f.MID=mi.MOTO_MID or f.MID=mi.EZYREC_MID or f.MID=mi.FIUU_MID where f.TIME_STAMP between :fromDate and :toDate "
					+ "order by f.TIME_STAMP desc";
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);
		}

		logger.info("Query : " + sql);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());

		for (Object[] rec : resultSet) {

			MotoTxnDetails fs = new MotoTxnDetails();

			if (rec[0] != null) {
				fs.setMid(rec[0].toString());
			}

			if (rec[1] != null) {
				fs.setName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setTimestamp(rec[2].toString());
			}
			if (rec[3] != null) {
				fs.setStatus(rec[3].toString());
			}
			if (rec[4] != null) {
				fs.setAmount(rec[4].toString());
			}
			if (rec[5] != null) {
				fs.setTid(rec[5].toString());
			}
			forSettlementList.add(fs);

		}

		paginationBean.setItemList(forSettlementList);
	}

	@Override
	@Transactional(readOnly = false)
	public int UpdateRecurringStatus(String status, Long id) {
		logger.info("MerchantDaoImpl:  UpdateRecurringStatus");
		String query = "update EzyRecurringPayment c set c.status =:status where id =:id";
		int rs = sessionFactory.openSession().createQuery(query).setLong("id", id).setParameter("status", status)
				.executeUpdate();

		logger.info("Query  " + query);
		return rs;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void findByUserNames(final String businessName, final PaginationBean<Merchant> paginationBean) {
		// CHANGE INTERFACE
		logger.info("MerchantDaoImpl:findByUserNames");
		Session session = sessionFactory.getCurrentSession();
		List users = session

				.createQuery("from Merchant where business_name LIKE :business_name")
				.setParameter("business_name", "%" + businessName + "%").setMaxResults(paginationBean.getItemsPerPage())
				.setFirstResult(paginationBean.getStartIndex()).list();

		paginationBean.setItemList(users);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateMerchantStatus(final Long id, final CommonStatus status, final MerchantStatusHistory history) {
		logger.info("MerchantDaoImpl:updateMerchantStatus");
		getSessionFactory().save(history);

		// String query = "update " + Merchant.class.getName() + " c set c.status
		// =:status where id =:id";
		String query = "update Merchant c set c.status =:status where id =:id";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("status", status)
				.setLong("id", id).executeUpdate();
		if (updatedEntities != 1) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ updatedEntities);
		}
		// auto commit

	}

	@Override
	public MerchantDetails loadMerchantPoints(Merchant merchant) {

		logger.info("MerchantDaoImpl:loadMid: " + merchant.getMid().getMid() + " motomid: "
				+ merchant.getMid().getMotoMid() + " ezypassMid: " + merchant.getMid().getEzypassMid() + " ezyrecMid: "
				+ merchant.getMid().getEzyrecMid() + " ezywayMid: " + merchant.getMid().getEzywayMid());

		Disjunction orExp = Restrictions.disjunction();
		orExp.add(Restrictions.in("mid", new String[] { merchant.getMid().getMid(), merchant.getMid().getMotoMid(),
				merchant.getMid().getEzypassMid(), merchant.getMid().getEzyrecMid(), merchant.getMid().getEzywayMid(),
				merchant.getMid().getUmMid(), merchant.getMid().getUmEzypassMid(), merchant.getMid().getUmEzyrecMid(),
				merchant.getMid().getUmEzywayMid(), merchant.getMid().getUmMotoMid() }));

		return (MerchantDetails) getSessionFactory().createCriteria(MerchantDetails.class).add(orExp).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public MerchantStatusHistory loadMerchantStatusHistoryID(final Merchant merchant) {
		logger.info("MerchantDaoImpl:loadMerchantStatusHistoryID");
		Session session = sessionFactory.getCurrentSession();
		MerchantStatusHistory history = (MerchantStatusHistory) session
				.createQuery("from MerchantStatusHistory where merchant=:merchant order by ID desc")
				.setParameter("merchant", merchant).setMaxResults(1).uniqueResult();
		return history;
	}

	@Override
	public Merchant loadMerchant(final String username) {
		// logger.info("MerchantDaoImpl:loadMerchant");
		logger.info("Merchant loading starts by using USERNAME :" + username);

		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public Merchant loadMerchantDetail1(final String businessName) {
		// logger.info("MerchantDaoImpl:loadMerchant");
		logger.info("Merchant loading starts by using businessName :" + businessName);

		return (Merchant) getSessionFactory().createCriteria(Merchant.class)
				.add(Restrictions.eq("businessName", businessName)).setMaxResults(1).uniqueResult();
	}

	@Override
	public Merchant loadMerchantbyBussinessName(final String username) {
		// logger.info("MerchantDaoImpl:loadMerchant");
		logger.info("Merchant loading starts by using bussiness Name :" + username);

		return (Merchant) getSessionFactory().createCriteria(Merchant.class)
				.add(Restrictions.eq("businessName", username)).setMaxResults(1).uniqueResult();
	}

	@Override
	public List<MobileUser> loadMobileUserByFkTid(long merchnatID) {

		logger.info("merchant Id : " + merchnatID);

		String sql = "select m.ENBL_MOTO,m.ENBL_BOOST,m.GPAY_TID,m.ONLINE_GPAY from MOBILE_USER m inner join MERCHANT mm on "
				+ "m.MERCHANT_FK=mm.ID  where m.MERCHANT_FK = :mer_id";
		String mid = null;

		ArrayList<MobileUser> fss = new ArrayList<MobileUser>();
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setLong("mer_id", merchnatID);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			MobileUser mobileUser = new MobileUser();
			if (rec[0] != null) {
				mobileUser.setEnableMoto(rec[0].toString());

			}

			if (rec[1] != null) {
				mobileUser.setEnableBoost(rec[1].toString());

			}
			if (rec[2] != null) {
				mobileUser.setGpayTid(rec[2].toString());
				;

			}
			if (rec[3] != null) {
				mobileUser.setOnlineGpay(rec[3].toString());

			}

			fss.add(mobileUser);

		}

		return fss;

	}

	@Override
	public Payer getPayerAmount(String resDate, Merchant merchant, String status) {
		Payer fs = new Payer();
		try {
			logger.info("check payout amt");

			Query sqlQuery = null;
			double txnMDR = 0.00;
			double mobiMDR = 0.00;
			double hostMDR = 0.00;
			double mercMDR = 0.00;
			double dedMDR = 0.00;
			double netMDR = 0.00;
			double disMDR = 0.00;
			double investorMDR = 0.00;
			double newMobiMDR = 0.00;
			double oldMobiMDR = 0.00;
			double newInvestorMDR = 0.50;
			double number = 0.00;

			String txnAmount = "0.00";
			String mobiAmount = "0.00";
			String hostAmount = "0.00";
			String mercAmount = "0.00";
			String dedAmount = "0.00";
			String netAmount = "0.00";
			String disAmount = "0.00";
			String investorAmount = "0.00";

			String umMid = Optional.ofNullable(merchant.getMid().getUmMid()).orElse(null);
			String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
			String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
			String fpxMid = Optional.ofNullable(merchant.getMid().getFpxMid()).orElse(null);
			String boostMid = Optional.ofNullable(merchant.getMid().getBoostMid()).orElse(null);
			String grabPayMid = Optional.ofNullable(merchant.getMid().getGrabMid()).orElse(null);
			String tngMid = Optional.ofNullable(merchant.getMid().getTngMid()).orElse(null);
			String sppMid = Optional.ofNullable(merchant.getMid().getShoppyMid()).orElse(null);
			String fiuuMid = Optional.ofNullable(merchant.getMid().getFiuuMid()).orElse(null);

			String sql = null;
			String date = resDate;

			logger.info(String.format(
					"Ezysettle Payer MID Values:\n" + "UM MID:         %s\n" + "UM Ezyway MID:  %s\n" + "UM Moto MID:    %s\n"
							+ "FPX MID:        %s\n" + "Boost MID:      %s\n" + "GrabPay MID:    %s\n"
							+ "TNG MID:        %s\n" + "SPP MID:        %s\n"+ "FIUU MID:        %s",
					umMid, umEzywayMid, umMotoMid, fpxMid, boostMid, grabPayMid, tngMid, sppMid, fiuuMid));

//		sql = "select s.WITHDRAWFEE,s.NET_AMOUNT_PAYABLE AS SettlementAmount,s.BANK_REQUEST_AMOUNT,s.BANK_FEE from mobiversa.JUST_SETTLE s where s.MID not in ('000000000007198') and s.MID IN (:umMid,:umEzywayMid,:umMotoMid) and s.status in ('S') and s.SETTLEMENTDATE like '"
//				+ date + "%' order by s.TIME_STAMP desc";
//		
			if (status.equalsIgnoreCase("ezysettleSuccess")) {

				sql = "SELECT a.WITHDRAWFEE, a.NET_AMOUNT_PAYABLE, a.BANK_REQUEST_AMOUNT,a.BANK_FEE,a.`MID`,a.SETTLEMENTDATE FROM mobiversa.JUST_SETTLE a INNER JOIN mobiversa.MID m ON ( "
						+ "        a.MID = m.UM_MID OR "
						+ "        a.MID = m.UM_MOTO_MID OR "
						+ "        a.MID = m.UM_EZYWAY_MID OR "
						+ "        a.MID = m.FPX_MID OR "
						+ "        a.MID = m.BOOST_MID OR "
						+ "        a.MID = m.GRAB_MID OR "
						+ "        a.MID = m.TNG_MID OR "
						+ "        a.MID = m.SHOPPY_MID OR "
						+ "        a.MID = m.FIUU_MID "
						+ "    ) INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID "
						+ " WHERE a.MID IN (:umMid,:umEzywayMid,:umMotoMid,:fpxMid,:boostMid,:grabPayMid,:tngMid,:sppMid,:fiuuMid) AND a.status IN ('R') AND a.SETTLEMENTDATE LIKE '"
						+ date + "%'  ORDER BY a.TIME_STAMP DESC LIMIT 1";

				sqlQuery = super.getSessionFactory().createSQLQuery(sql);
				sqlQuery.setString("umMid", umMid);
				sqlQuery.setString("umEzywayMid", umEzywayMid);
				sqlQuery.setString("umMotoMid", umMotoMid);
				sqlQuery.setString("fpxMid", fpxMid);
				sqlQuery.setString("boostMid", boostMid);
				sqlQuery.setString("grabPayMid", grabPayMid);
				sqlQuery.setString("tngMid", tngMid);
				sqlQuery.setString("sppMid", sppMid);
				sqlQuery.setString("fiuuMid", fiuuMid);

			} else {
				sql = "SELECT a.WITHDRAWFEE, a.NET_AMOUNT_PAYABLE, a.BANK_REQUEST_AMOUNT,a.BANK_FEE,a.`MID`,a.SETTLEMENTDATE FROM mobiversa.JUST_SETTLE a INNER JOIN mobiversa.MID m ON ( "
						+ "        a.MID = m.UM_MID OR "
						+ "        a.MID = m.UM_MOTO_MID OR "
						+ "        a.MID = m.UM_EZYWAY_MID OR "
						+ "        a.MID = m.FPX_MID OR "
						+ "        a.MID = m.BOOST_MID OR "
						+ "        a.MID = m.GRAB_MID OR "
						+ "        a.MID = m.TNG_MID OR "
						+ "        a.MID = m.SHOPPY_MID OR "
						+ "        a.MID = m.FIUU_MID "
						+ "    ) INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID "
						+ " WHERE a.MID IN (:umMid,:umEzywayMid,:umMotoMid,:fpxMid,:boostMid,:grabPayMid,:tngMid,:sppMid,:fiuuMid) AND a.status IN ('S') AND a.SETTLEMENTDATE LIKE '"
						+ date + "%'  ORDER BY a.TIME_STAMP DESC LIMIT 1";

				sqlQuery = super.getSessionFactory().createSQLQuery(sql);
				sqlQuery.setString("umMid", umMid);
				sqlQuery.setString("umEzywayMid", umEzywayMid);
				sqlQuery.setString("umMotoMid", umMotoMid);
				sqlQuery.setString("fpxMid", fpxMid);
				sqlQuery.setString("boostMid", boostMid);
				sqlQuery.setString("grabPayMid", grabPayMid);
				sqlQuery.setString("tngMid", tngMid);
				sqlQuery.setString("sppMid", sppMid);
				sqlQuery.setString("fiuuMid", fiuuMid);
			}

			logger.info("getPayerAmount Sql : "+sql);
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet = sqlQuery.list();
			logger.info("Number of records in the List : " + resultSet.size());
			for (Object[] rec : resultSet) {
				if (rec[0] == null || rec[0].toString().isEmpty()) {
					logger.info("null:");
					fs.setTotalMerAmount("");
				} else if (rec[0] != null || !rec[0].toString().isEmpty()) {
					logger.info("withdraw fee :" + rec[0].toString());
					mercMDR = Double.parseDouble(rec[0].toString());
					number = mercMDR;
					mercMDR = DecimalUtils.round(number, 2);
					mercAmount = String.valueOf(mercMDR);
					fs.setTotalMerAmount(mercAmount);
				}
				if (rec[1] == null || rec[1].toString().isEmpty()) {
					logger.info("null:");
					fs.setTotalMerAmount("");
				} else if (rec[1] != null || !rec[1].toString().isEmpty()) {
					logger.info("net Amt :" + rec[1].toString());
					netMDR = Double.parseDouble(rec[1].toString());
					number = netMDR;
					netMDR = DecimalUtils.round(number, 2);
					logger.info("Total netMDR: " + number + " is rounded to: " + netMDR);
					netAmount = String.valueOf(netMDR);
					fs.setTotalNetAmount(netAmount);
				}

				if (rec[2] == null || rec[2].toString().isEmpty()) {
					logger.info("null:");
					fs.setBankRequestFee("0.00");
				} else if (rec[2] != null || !rec[2].toString().isEmpty()) {
					logger.info("Bank Request Fee :" + rec[2].toString());

					fs.setBankRequestFee(rec[2].toString());
				}

				if (rec[3] == null || rec[3].toString().isEmpty()) {
					logger.info("null:");
					fs.setBankFee("0.00");
				} else if (rec[3] != null || !rec[3].toString().isEmpty()) {
					logger.info("Bank fee :" + rec[3].toString());

					fs.setBankFee(rec[3].toString());
				}

				if (rec[4] == null || rec[4].toString().isEmpty()) {
					logger.info("null:");
					fs.setMid("");
				} else if (rec[4] != null || !rec[4].toString().isEmpty()) {
					logger.info("Mid :" + rec[4].toString());
					fs.setMid(rec[4].toString());
				}

				if (rec[5] == null || rec[5].toString().isEmpty()) {
					logger.info("null:");
					fs.setSettlementDate(date);
				} else if (rec[5] != null || !rec[5].toString().isEmpty()) {
					logger.info("SettlementDate :" + rec[5].toString());
					fs.setSettlementDate(rec[5].toString());
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return fs;

	}

	@Override
	public Payer getPayerAmountJust(String resDate, Merchant merchant) {
		logger.info("check payout amt");
		Payer fs = new Payer();
		Query sqlQuery = null;
		double txnMDR = 0.00;
		double mobiMDR = 0.00;
		double hostMDR = 0.00;
		double mercMDR = 0.00;
		double dedMDR = 0.00;
		double netMDR = 0.00;
		double disMDR = 0.00;
		double investorMDR = 0.00;
		double newMobiMDR = 0.00;
		double oldMobiMDR = 0.00;
		double newInvestorMDR = 0.50;
		double number = 0.00;
		String txnAmount = "0.00";
		String mobiAmount = "0.00";
		String hostAmount = "0.00";
		String mercAmount = "0.00";
		String dedAmount = "0.00";
		String netAmount = "0.00";
		String disAmount = "0.00";
		String investorAmount = "0.00";
		String sql = null;
		String date = resDate;
		String umMid = null, umEzywayMid = null, umMotoMid = null, umEzyrecMid = null, umEzypassMid = null,
				splitMid = null;
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid();
		}
		if (merchant.getMid().getUmEzyrecMid() != null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid() != null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getSplitMid() != null) {
			splitMid = merchant.getMid().getSplitMid();
		}

		logger.info("date:" + date);
		logger.info("umMid:" + umMid);
		logger.info("umEzywayMid:" + umEzywayMid);
		logger.info("umMotoMid:" + umMotoMid);
		logger.info("umEzyrecMid:" + umEzyrecMid);
		logger.info("umEzypassMid:" + umEzypassMid);
		logger.info("splitMid:" + splitMid);

		sql = "select sum(s.WITHDRAWFEE),sum(s.NET_AMOUNT_PAYABLE) AS SettlementAmount,s.MID,s.SETTLEMENTDATE from mobiversa.JUST_SETTLE s where s.MID not in ('000000000007198') and s.MID IN (:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid,:splitMid) and s.status in ('S') and s.SETTLEMENTDATE like '"
				+ date + "%'";
		sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		// sqlQuery.setString("date", date);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("umEzyrecMid", umEzyrecMid);
		sqlQuery.setString("umEzypassMid", umEzypassMid);
		sqlQuery.setString("splitMid", splitMid);

		logger.info("After Query :");

		logger.info("date:" + date);
		logger.info("umMid:" + umMid);
		logger.info("umEzywayMid:" + umEzywayMid);
		logger.info("umMotoMid:" + umMotoMid);
		logger.info("umEzyrecMid:" + umEzyrecMid);
		logger.info("umEzypassMid:" + umEzypassMid);
		logger.info("splitMid:" + splitMid);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			if (rec[0] == null || rec[0].toString().isEmpty()) {
				logger.info("null:");
				fs.setTotalMerAmount("");
			} else if (rec[0] != null || !rec[0].toString().isEmpty()) {
				logger.info("withdraw fee :" + rec[0].toString());
				mercMDR = Double.parseDouble(rec[0].toString());
				number = mercMDR;
				mercMDR = DecimalUtils.round(number, 2);
				mercAmount = String.valueOf(mercMDR);
				fs.setTotalMerAmount(mercAmount);
			}
			if (rec[1] == null || rec[1].toString().isEmpty()) {
				logger.info("net Amt null:");
				fs.setTotalMerAmount("");
			} else if (rec[1] != null || !rec[1].toString().isEmpty()) {
				logger.info("net Amt :" + rec[1].toString());
				netMDR = Double.parseDouble(rec[1].toString());
				number = netMDR;
				netMDR = DecimalUtils.round(number, 2);
				logger.info(" netMDR: " + number + " is rounded to: " + netMDR);
				netAmount = String.valueOf(netMDR);
				fs.setTotalNetAmount(netAmount);
			}
			if (rec[2] == null || rec[2].toString().isEmpty()) {
				logger.info("mid  : null:");
				fs.setMid("");
			} else if (rec[2] != null || !rec[2].toString().isEmpty()) {
				logger.info("mid  :" + rec[2].toString());
				// netMDR = Double.parseDouble(rec[2].toString());
				String mid = rec[2].toString();
				fs.setMid(mid);
			}
			if (rec[3] == null || rec[3].toString().isEmpty()) {
				logger.info("Settlement date  null:");
				fs.setDate("");
			} else if (rec[3] != null || !rec[3].toString().isEmpty()) {
				logger.info("Settlement date :" + rec[3].toString());
				String date1 = rec[3].toString();
				fs.setDate(date1);
			}

		}
		return fs;
	}

//	@Override
//	public List<PayeeDetails> getPayeeDetails(String resDate, Merchant merchant) {
//		logger.info("check payout amt");
//		logger.info("check payee Details");
//		List<PayeeDetails> list = new ArrayList<PayeeDetails>();
//		PayeeDetails payee = new PayeeDetails();
//		Query sqlQuery = null;
//		double txnMDR = 0.00;
//		double mobiMDR = 0.00;
//		double hostMDR = 0.00;
//		double mercMDR = 0.00;
//		double dedMDR = 0.00;
//		double netMDR = 0.00;
//		double disMDR = 0.00;
//		double investorMDR = 0.00;
//		double newMobiMDR = 0.00;
//		double oldMobiMDR = 0.00;
//		double newInvestorMDR = 0.50;
//		double number = 0.00;
//		String txnAmount = "0.00";
//		String mobiAmount = "0.00";
//		String hostAmount = "0.00";
//		String mercAmount = "0.00";
//		String dedAmount = "0.00";
//		String netAmount = "0.00";
//		String disAmount = "0.00";
//		String investorAmount = "0.00";
//
//		String sql = null;
//		String date = resDate;
//
//		String umMid = null, umEzywayMid = null, umMotoMid = null, umEzyrecMid = null, umEzypassMid = null,
//				splitMid = null;
//		if (merchant.getMid().getUmMid() != null) {
//			umMid = merchant.getMid().getUmMid();
//		}
//		if (merchant.getMid().getUmEzywayMid() != null) {
//			umEzywayMid = merchant.getMid().getUmEzywayMid();
//		}
//		if (merchant.getMid().getUmMotoMid() != null) {
//			umMotoMid = merchant.getMid().getUmMotoMid();
//		}
//		if (merchant.getMid().getUmEzyrecMid() != null) {
//			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
//		}
//		if (merchant.getMid().getUmEzypassMid() != null) {
//			umEzypassMid = merchant.getMid().getUmEzypassMid();
//		}
//		if (merchant.getMid().getSplitMid() != null) {
//			splitMid = merchant.getMid().getSplitMid();
//		}
//
//		logger.info("date:" + date);
//		logger.info("umMid:" + umMid);
//		logger.info("umEzywayMid:" + umEzywayMid);
//		logger.info("umMotoMid:" + umMotoMid);
//		logger.info("umEzyrecMid:" + umEzyrecMid);
//		logger.info("umEzypassMid:" + umEzypassMid);
//		logger.info("splitMid:" + splitMid);
//
//		sql = "select f.BUSINESS_SHORTNAME AS MerchantName,f.EMAIL,a.MID,f.BANK_ACC,f.BANK_NAME,a.SETTLEMENTDATE,sum(a.WITHDRAWFEE) AS MDR_AMT,sum(a.NET_AMOUNT_PAYABLE) AS NET_AMOUNT, o.FIRST_NAME AS AGENT_NAME "
//				+ "from mobiversa.JUST_SETTLE a INNER JOIN mobiversa.MID m on a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYREC_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYWAY_MID or a.MID=m.UM_MID or a.MID=m.UM_MOTO_MID or a.MID=m.UM_EZYREC_MID or a.MID=m.UM_EZYPASS_MID or a.MID=m.UM_EZYWAY_MID or a.MID=m.GPAY_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK=m.ID INNER JOIN mobiversa.AGENT o ON o.ID=f.AGID_FK where a.MID not in ('000000000007198') and a.MID IN (:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid,:splitMid) and a.status in ('S') and a.SETTLEMENTDATE like '"
//				+ date + "%' group by a.MID";
//		sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//		// sqlQuery.setString("date", date);
//		sqlQuery.setString("umMid", umMid);
//		sqlQuery.setString("umEzywayMid", umEzywayMid);
//		sqlQuery.setString("umMotoMid", umMotoMid);
//		sqlQuery.setString("umEzyrecMid", umEzyrecMid);
//		sqlQuery.setString("umEzypassMid", umEzypassMid);
//		sqlQuery.setString("splitMid", splitMid);
//
//		logger.info("After query ");
//		logger.info("date:" + date);
//		logger.info("umMid:" + umMid);
//		logger.info("umEzywayMid:" + umEzywayMid);
//		logger.info("umMotoMid:" + umMotoMid);
//		logger.info("umEzyrecMid:" + umEzyrecMid);
//		logger.info("umEzypassMid:" + umEzypassMid);
//		logger.info("splitMid:" + splitMid);
//
//		@SuppressWarnings("unchecked")
//		List<Object[]> resultSet = sqlQuery.list();
//		logger.info("Number of records in the List : " + resultSet.size());
//		for (Object[] rec : resultSet) {
//			if (rec[0] == null || rec[0].toString().isEmpty()) {
//				logger.info("null:");
//				payee.setMerchantName("");
//			} else if (rec[0] != null || !rec[0].toString().isEmpty()) {
//				logger.info("Business Name :" + rec[0].toString());
//				payee.setMerchantName(rec[0].toString());
//			}
//			if (rec[1] == null || rec[1].toString().isEmpty()) {
//				logger.info("Email null:");
//				payee.setEmail("");
//			} else if (rec[1] != null || !rec[1].toString().isEmpty()) {
//				logger.info("Email : " + rec[1].toString());
//				payee.setEmail(rec[1].toString());
//			}
//			if (rec[2] == null || rec[2].toString().isEmpty()) {
//				logger.info("MID null:");
//				payee.setMid("");
//			} else if (rec[2] != null || !rec[2].toString().isEmpty()) {
//				logger.info("MID : " + rec[2].toString());
//				payee.setMid(rec[2].toString());
//			}
//			if (rec[3] == null || rec[3].toString().isEmpty()) {
//				logger.info("BANK_ACC null:");
//				payee.setAccountNo("");
//			} else if (rec[3] != null || !rec[3].toString().isEmpty()) {
//				logger.info("BANK_ACC : " + rec[3].toString());
//				payee.setAccountNo(rec[3].toString());
//			}
//			if (rec[4] == null || rec[4].toString().isEmpty()) {
//				logger.info("BANK_ACC null:");
//				payee.setBankName("");
//			} else if (rec[4] != null || !rec[4].toString().isEmpty()) {
//				logger.info("BANK_ACC : " + rec[4].toString());
//				payee.setBankName(rec[4].toString());
//			}
//			if (rec[5] == null || rec[5].toString().isEmpty()) {
//				logger.info("Settlement Date null:");
//				payee.setSettlementDate("");
//			} else if (rec[5] != null || !rec[5].toString().isEmpty()) {
//				logger.info("Settlement Date : " + rec[5].toString());
//				payee.setSettlementDate(rec[5].toString());
//			}
//			if (rec[6] == null || rec[6].toString().isEmpty()) {
//				logger.info("Merc Amount null:");
//				payee.setMerchantAmount("0.00");
//			} else if (rec[6] != null || !rec[6].toString().isEmpty()) {
//				logger.info("Merc Amount : " + rec[6].toString());
//				mercMDR = Double.parseDouble(rec[6].toString());
//				number = mercMDR;
//				mercMDR = DecimalUtils.round(number, 2);
//				logger.info(" mercMDR: " + number + " is rounded to: " + mercMDR);
//				mercAmount = String.valueOf(mercMDR);
//				payee.setMerchantAmount(mercAmount);
//			}
//			if (rec[7] == null || rec[7].toString().isEmpty()) {
//				logger.info("net Amount null:");
//				payee.setNetAmount("0.00");
//			} else if (rec[7] != null || !rec[7].toString().isEmpty()) {
//				logger.info("net Amount : " + rec[7].toString());
//				netMDR = Double.parseDouble(rec[7].toString());
//				number = netMDR;
//				netMDR = DecimalUtils.round(number, 2);
//				logger.info(" netMDR: " + number + " is rounded to: " + netMDR);
//				netAmount = String.valueOf(netMDR);
//				payee.setNetAmount(netAmount);
//			}
//			if (rec[8] == null || rec[8].toString().isEmpty()) {
//				logger.info("MID null:");
//				payee.setAgentName("");
//			} else if (rec[8] != null || !rec[8].toString().isEmpty()) {
//				logger.info("MID : " + rec[8].toString());
//				payee.setAgentName(rec[8].toString());
//			}
//			list.add(payee);
//		}
//		return list;
//	}

	@Override
	public String updateFinalStatus(String resDate, Merchant merchant) {
		logger.info("Update final Status");

		Query sqlQuery = null;

		String sql = null;
		String date = resDate;

		String Mid = null;

		if (merchant.getMid().getUmMid() != null) {
			Mid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			Mid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			Mid = merchant.getMid().getUmMotoMid();
		}
		if (merchant.getMid().getUmEzyrecMid() != null) {
			Mid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid() != null) {
			Mid = merchant.getMid().getUmEzypassMid();

		}

		if (merchant.getMid().getSplitMid() != null) {
			Mid = merchant.getMid().getSplitMid();
		}

		logger.info("Update Mid :" + Mid);
		logger.info("Update date :" + date);

		sql = "UPDATE JUST_SETTLE fs set fs.STATUS ='R' where  fs.STATUS='S' and fs.MID like '" + Mid
				+ "' and fs.SETTLEMENTDATE like '" + date + "%'";

		sqlQuery = super.getSessionFactory().createSQLQuery(sql);

		logger.info("update Query executed");

		return null;

	}

	/*
	 * @Override public SettlementUser loadSettlementUser(final String username) {
	 * // logger.info("MerchantDaoImpl:loadMerchant"); return (SettlementUser)
	 * getSessionFactory().createCriteria(SettlementUser.class).add(Restrictions.eq(
	 * "username", username)) .setMaxResults(1).uniqueResult(); }
	 */

	@Override
	public MobiLiteMerchant loadMobiLiteMerchant(final String username) {
		// logger.info("MerchantDaoImpl:loadMerchant");
		return (MobiLiteMerchant) getSessionFactory().createCriteria(MobiLiteMerchant.class)
				.add(Restrictions.eq("username", username)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MobiLiteMerchant loadMobiliteMerchant(final String username) {
		// logger.info("MerchantDaoImpl:loadMerchant");
		return (MobiLiteMerchant) getSessionFactory().createCriteria(MobiLiteMerchant.class)
				.add(Restrictions.eq("username", username)).setMaxResults(1).uniqueResult();
	}

	@Override
	public KeyManager validatecaptcha(final String captcha2) {
		String tid = captcha2;

		/*
		 * logger.info("MerchantDaoImpl:validateCaptcha in KeyManager"+captcha); return
		 * (KeyManager)
		 * getSessionFactory().createCriteria(KeyManager.class).add(Restrictions.eq(
		 * "tid", captcha2.trim())); Employee emp= (Employee)
		 * session.createCriteria(Employee.class,"a") .createAlias("a.jobs", "j")
		 * .add(Restrictions.eq("id", id)) .add(Restrictions.eq("j.active", "1"));
		 */

		// logger.info("MerchantDaoImpl:validateCaptcha in KeyManager"+tid);
		Session session = sessionFactory.getCurrentSession();
		KeyManager keymanager = (KeyManager) session.createQuery("from KeyManager where tid= :tid")
				.setParameter("tid", tid).setMaxResults(1).uniqueResult();
		return keymanager;
	}

	@Transactional(readOnly = false)
	@Override
	public boolean deleteCaptcha(String captcha2) {
		String tid = captcha2;
		boolean status = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete from KeyManager where tid = :tid");
		query.setParameter("tid", tid);
		// logger.info("execute result deleted: "+query.executeUpdate());
		int result = query.executeUpdate();
		if (result == 0) {
			status = true;
		} else {
			status = false;
		}
		return status;
	}

	@Override
	public Merchant loadmobileMerchant(MID mid) {
		logger.info("MerchantDaoImpl:loadMerchant: " + mid);
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("mid", mid))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public Merchant loadMerchant(MID mid) {
		// logger.info("MerchantDaoImpl:loadMerchant MID");
		// TODO Auto-generated method stub
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("mid", mid))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadMerchantmid(String mid) {
		logger.info("MerchantDaoImpl:loadMerchant MID");
		// TODO Auto-generated method stub
		return (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.eq("mid", mid)).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public MID loadMerchantMIDDetails(String mid, String midType) {
		logger.info("MerchantDaoImpl:loadMerchant MID");
		MID merchantMID = null;
		if (midType.equals("ezywireMID")) {
			merchantMID = (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.eq("mid", mid))
					.setMaxResults(1).uniqueResult();
		} else if (midType.equals("motoMID")) {
			merchantMID = (MID) getSessionFactory().createCriteria(MID.class)
					.add(Restrictions.like("ezyrecMid", mid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
		} else if (midType.equals("ezyrecMID")) {

		} else if (midType.equals("ezypassMID")) {
			return (MID) getSessionFactory().createCriteria(MID.class)
					.add(Restrictions.like("ezypassMid", mid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
		}
		return merchantMID;

	}

	@Override
	public MID loadMerchantbyMerchant_FK(Long merchant_fk) {
		logger.info("MerchantDaoImpl:loadMerchant MID");
		// TODO Auto-generated method stub
		return (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.eq("merchant.id", merchant_fk))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadMerchantmotomid(String motomid) {
		logger.info("MerchantDaoImpl:loadMerchant MID");
		// TODO Auto-generated method stub
		return (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.eq("motoMid", motomid))
				.setMaxResults(1).uniqueResult();
	}

	/*
	 * @Override public MID loadMerchantezypassmid(String ezypassMid) {
	 * logger.info("MerchantDaoImpl:loadMerchant MID"); // TODO Auto-generated
	 * method stub return (MID)
	 * getSessionFactory().createCriteria(MID.class).add(Restrictions.eq(
	 * "ezypassMid", ezypassMid)) .setMaxResults(1).uniqueResult(); }
	 */
	@Override
	public Merchant loadMerchantbyid(MID mid) {
		// logger.info("MerchantDaoImpl:loadMerchant MID "+mid);

		// TODO Auto-generated method stub
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("mid", mid))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public Merchant loadMerchantbymerchantid(Long id) {
		logger.info("MerchantDaoImpl:loadMerchant merchant id " + id);

		// TODO Auto-generated method stub
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("id", id))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public MobileUser loadBankByMerchantFk(Merchant merchant) {
		logger.info("MerchantDaoImpl:loadMerchant merchant id " + merchant.getId());

		// TODO Auto-generated method stub
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant", merchant)).setMaxResults(1).uniqueResult();
	}

	@Override
	public HostBankDetails loadBankBySellerId(MobileUser mUser) {
		logger.info("MerchantDaoImpl:load Bank by seller id " + mUser.getFpxSellerId());

		// TODO Auto-generated method stub
		return (HostBankDetails) getSessionFactory().createCriteria(HostBankDetails.class)
				.add(Restrictions.eq("sellerId", mUser.getFpxSellerId())).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public int changeMerchantPassWord(String Username, String newPwd, String OldPwd) {
		// logger.info("MerchantDaoImpl:changeMerchantPassWord");
		String query = "update Merchant c set c.password =:password where userName =:userName";
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("userName", Username).executeUpdate();

		// logger.info("Query "+query);
		return rs;
	}

	@Override
	@Transactional(readOnly = false)
	public int changeMobiliteMerchantPassWord(String Username, String newPwd, String OldPwd) {
		// logger.info("MerchantDaoImpl:changeMerchantPassWord");
		String query = "update MobiLiteMerchant c set c.password =:password where userName =:userName";
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("userName", Username).executeUpdate();

		// logger.info("Query "+query);
		return rs;
	}

	@Override
	@Transactional(readOnly = false)
	public int changeMerchantPassWordByAdmin(String Username, String newPwd) {
		logger.info("MerchantDaoImpl:changeMerchantPassWord: \n" + Username + " : " + newPwd);
		String query = "update Merchant c set c.password =:password where userName =:userName";
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("userName", Username).executeUpdate();
		return rs;
	}

	@Override
	@Transactional(readOnly = false)
	public int changeMobileuserPassWordByAdmin(String Username, String newPwd) {
		logger.info("MerchantDaoImpl:changeMobileuserPassWord: \n" + Username + " : " + newPwd);
		String query = "update MobileUser c set c.password =:password where userName =:userName";
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("userName", Username).executeUpdate();
		return rs;
	}

	// new changes //
	@Override
	public Merchant loadMerchantbyEmail(String email) {
		logger.info("MerchantDaoImpl:loadMerchantbyEmail");
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("username", email))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public int updateMIDData(Long m_id, Long merchant_id) {
		logger.info("MerchantDaoImpl:updateMIDData");
		// MID mID = new MID();
		// BigInteger dfg = new BigInteger(id.toString());
		Session session = sessionFactory.getCurrentSession();
		/* String sql="insert into MID(MID,MERCHANT_FK) values ('?','?')"; */

		// String sql= " INSERT INTO MID ( MID , MERCHANT_FK ) VALUES ( '"+ mid +"',"+
		// dfg +") ";

		// String sql= "update MID set merchant_fk ="+merchant_id+" where id="+m_id;
		String sql = "update MID set  merchant_fk = :merchant_id where id= :m_id";

		logger.info(" Query :" + sql);

		Query insertQuery = session.createQuery(sql);
		insertQuery.setLong("merchant_id", merchant_id);
		insertQuery.setLong("m_id", m_id);
		// Query insertQuery = session.createSQLQuery("insert into MID(MID,MERCHANT_FK)
		// values ('"+mid+"',"+dfg+")");
		/*
		 * insertQuery.setParameter(0, mid); insertQuery.setBigInteger(1, dfg);
		 */
		int a = insertQuery.executeUpdate();

		// int result = insertQuery.executeUpdate();
		return a;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> loadMerchant() {
		logger.info("MerchantDaoImpl:loadMerchant");
		Criterion active = Restrictions.like("status", CommonStatus.ACTIVE);
		Criterion suspended = Restrictions.like("status", CommonStatus.SUSPENDED);
		Disjunction orExp = Restrictions.or(active, suspended, suspended);
		return (List<Merchant>) getSessionFactory().createCriteria(Merchant.class).add(orExp).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> loadMerchant1() {
		logger.info("MerchantDaoImpl:loadMerchant");

		String enable_payout = "yes";

		// String username = "martin.tan@mitrade.com";

		Session session = sessionFactory.getCurrentSession();

		Criteria settlementBalance = session.createCriteria(Merchant.class);
		settlementBalance.add(Restrictions.and(Restrictions.ilike("enblPayout", enable_payout, MatchMode.EXACT)));
		List<Merchant> merchantlist = settlementBalance.list();
//		Criterion active = Restrictions.like("status", CommonStatus.ACTIVE);
//		Criterion suspended = Restrictions.like("status", CommonStatus.SUSPENDED);
//		Disjunction orExp = Restrictions.or(active, suspended, suspended);
		return merchantlist;

	}

	@Override
	public SettlementBalance settleMerchant(String id) {
		logger.info("id :" + id);
		return (SettlementBalance) getSessionFactory().createCriteria(SettlementBalance.class)
				.add(Restrictions.eq("merchantId", id)).setMaxResults(1).uniqueResult();

	}

	@Override
	public void savesettlement(String settlementAmount, String topupAmt, String id) {

		logger.info("settlementAmount :" + settlementAmount);
		logger.info("topupAmt :" + topupAmt);
		logger.info("id :" + id);

		double Total = 0.00;

		double SettleAmt = Double.parseDouble(settlementAmount);
		double depositAmt = Double.parseDouble(topupAmt);
		Total = SettleAmt + depositAmt;

		String TotalAmt = Double.toString(Total);
		logger.info("TotalAmt :" + TotalAmt);

		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"update SETTLEMENT_BALANCE set NET_AMOUNT=:SettlementAmt,DEPOSIT_AMOUNT = :Topup,TOTAL_AMOUNT = :total where MERCHANTID = :id");
		query.setParameter("SettlementAmt", SettleAmt);
		query.setParameter("Topup", depositAmt);
		query.setParameter("total", TotalAmt);
		query.setParameter("id", id);
		query.executeUpdate();
		logger.info("sucessfully updated !!!!!!!!!!!!!!!! TotalAmt : " + TotalAmt + " depositAmt :" + depositAmt
				+ " SettleAmt :" + SettleAmt);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> loadFpxMerchant() {
		logger.info("MerchantDaoImpl:loadMerchant");
		ArrayList<Merchant> merchantDetail = new ArrayList<Merchant>();
		String sql = "select m.BUSINESS_NAME,f.MID,m.ID from MERCHANT m inner join MID mi on mi.MERCHANT_FK = m.ID "
				+ "inner join FPX_TRANSACTION f on f.MID = mi.MID or  f.MID = mi.MOTO_MID or  f.MID = mi.EZYPASS_MID "
				+ "or  f.MID = mi.EZYREC_MID or  f.MID = mi.EZYWAY_MID or  f.MID = mi.UM_MID "
				+ "or  f.MID = mi.UM_MOTO_MID or  f.MID = mi.UM_EZYWAY_MID "
				+ "Where m.STATUS = 'ACTIVE' group by f.MID ";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());
		for (Object[] rec : resultSet) {

			Merchant merchantData = new Merchant();
			merchantData.setBusinessName(rec[0].toString());
			merchantData.setReferralId(rec[1].toString());
			merchantData.setState(rec[2].toString());// merchant id
			merchantDetail.add(merchantData);
		}
		return merchantDetail;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> loadMerchantByNOB(String nob) {
		logger.info("MerchantDaoImpl:loadMerchantByNOB");
		return (List<Merchant>) getSessionFactory().createCriteria(Merchant.class)
				.add(Restrictions.like("natureOfBusiness", nob, MatchMode.START)).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> loadMerchantByAdmin() {
		logger.info("MerchantDaoImpl:loadMerchant");
		return (List<Merchant>) getSessionFactory().createCriteria(Merchant.class)
				.add(Restrictions.eq("status", CommonStatus.ACTIVE))
				.add(Restrictions.eq("role", MerchantUserRole.BANK_MERCHANT)).list();

	}

	@Override
	public void listAgentMerchant(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props) {
		logger.info("MerchantDaoImpl:listAgentMerchant");
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.desc("createdDate"));

	}

	@Override
	public MID loadMidtoUpdateAudit(String mid) {
		logger.info("MerchantDaoImpl:loadMid: " + mid);

		Disjunction orExp = Restrictions.disjunction();
		orExp.add(Restrictions.eq("mid", mid));
		orExp.add(Restrictions.eq("motoMid", mid));
		orExp.add(Restrictions.eq("ezypassMid", mid));
		orExp.add(Restrictions.eq("ezyrecMid", mid));
		orExp.add(Restrictions.eq("ezywayMid", mid));
		orExp.add(Restrictions.eq("umMid", mid));
		orExp.add(Restrictions.eq("umMotoMid", mid));
		orExp.add(Restrictions.eq("umEzywayMid", mid));
		orExp.add(Restrictions.eq("umEzyrecMid", mid));
		orExp.add(Restrictions.eq("umEzypassMid", mid));
		orExp.add(Restrictions.eq("umEzypassMid", mid));
		orExp.add(Restrictions.eq("umEzypassMid", mid));
		orExp.add(Restrictions.eq("umEzypassMid", mid));
		orExp.add(Restrictions.eq("umEzypassMid", mid));
		orExp.add(Restrictions.eq("umEzypassMid", mid));
		orExp.add(Restrictions.eq("umEzypassMid", mid));

		return (MID) getSessionFactory().createCriteria(MID.class).add(orExp).setMaxResults(1).uniqueResult();
	}

	// new method for mid already exist 24062016
	@Override
	public MID loadMid(String mid) {
		// logger.info("MerchantDaoImpl:loadMid: "+mid);

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("mid", mid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadMotoMid(String motomid) {
		// logger.info("MerchantDaoImpl:loadmotoMid :"+motomid);

//		return (MID) getSessionFactory().createCriteria(MID.class)
//				.add(Restrictions.like("motoMid", motomid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
		
		 return (MID) getSessionFactory().createCriteria(MID.class)
		            .add(Restrictions.or(
		                Restrictions.like("motoMid", motomid, MatchMode.ANYWHERE),
		                Restrictions.like("fiuuMid", motomid, MatchMode.ANYWHERE)
		            ))
		            .setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadEzyPassMid(String ezypassmid) {
		logger.info("MerchantDaoImpl:loadezypassMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("ezypassMid", ezypassmid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadumMotoMid(String umMotoMid) {
		logger.info("MerchantDaoImpl:loadummotoMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("umMotoMid", umMotoMid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadumEzywayMid(String umEzywayMid) {
		logger.info("MerchantDaoImpl:loadumezywayMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("umEzywayMid", umEzywayMid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadEzywayMid(String ezywaymid) {
		logger.info("MerchantDaoImpl:loadezywayMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("ezywayMid", ezywaymid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadEzyrecMid(String ezyrecMid) {
		logger.info("MerchantDaoImpl:loadezyrecMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("ezyrecMid", ezyrecMid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	// Umobile

	@Override
	public MID loadUMMid(String um_mid) {
		// logger.info("MerchantDaoImpl:loadMid: "+mid);

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("umMid", um_mid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadUMMotoMid(String um_motomid) {
		// logger.info("MerchantDaoImpl:loadmotoMid :"+motomid);

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("umMotoMid", um_motomid, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadUMEzyPassMid(String um_ezypassmid) {
		logger.info("MerchantDaoImpl:loadezypassMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("umEzypassMid", um_ezypassmid, MatchMode.ANYWHERE)).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public MID loadUMEzywayMid(String um_ezywaymid) {
		logger.info("MerchantDaoImpl:loadezywayMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("umEzywayMid", um_ezywaymid, MatchMode.ANYWHERE)).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public MID loadUMEzyrecMid(String um_ezyrecMid) {
		logger.info("MerchantDaoImpl:loadezyrecMid");

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("umEzyrecMid", um_ezyrecMid, MatchMode.ANYWHERE)).setMaxResults(1)
				.uniqueResult();
	}

	// new method in pending Merchant 24062016
	@Override
	public void listMerchantUser1(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props) {
		logger.info("MerchantDaoImpl:listMerchantUser1");
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.desc("id"));
	}

	@Override
	public FileUpload loadFileById(String id) {
		logger.info("MerchantDaoImpl:loadFileById FileId:" + id);
		Long lo = new Long(id);
		return (FileUpload) getSessionFactory().createCriteria(FileUpload.class).add(Restrictions.eq("id", lo))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public FileUpload updateFileById(FileUpload fileUpload) {
		logger.info("MerchantDaoImpl:updateFileById ");
		Session session = sessionFactory.getCurrentSession();

		// String sql= "update FileUpload set merchant_id
		// ="+fileUpload.getMerchantId()+" where id="+fileUpload.getId();
		String sql = "update FileUpload set  merchant_id = :merchantId where id= :Id";
		logger.info(" Query :" + sql);

		Query insertQuery = session.createQuery(sql);
		insertQuery.setString("merchantId", fileUpload.getMerchantId());
		insertQuery.setLong("Id", fileUpload.getId());
		int a = insertQuery.executeUpdate();

		return fileUpload;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FileUpload> loadFileByMerchantId(String merchId) {
		logger.info("MerchantDaoImpl:loadFileByMerchantId");
		return (List<FileUpload>) getSessionFactory().createCriteria(FileUpload.class)
				.add(Restrictions.eq("merchantId", merchId)).list();

	}

	@Override
	public Merchant loadMerchantDetails(String username) {
		logger.info("MerchantDaoImpl:loadMerchant");
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public void listMerchantSearch(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props) {
		// TODO Auto-generated method stub
		logger.info("check merchant data:");
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.desc("activateDate"));
	}

	@Override
	public MID loadMidByMerchant_PK(String id) {
		logger.info("MerchantDaoImpl:loadMid: " + id);
		long m_id = Long.parseLong(id);
		return (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.like("merchant.id", m_id))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public TerminalDetails loadTerminalDetailsByMid(String mid) {
		logger.info("loadTerminalDetailsByMid " + mid);
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.like("merchantId", mid)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MobiLiteTerminal loadMobiliteTerminalDetailsByMid(Long mid) {
		logger.info("loadTerminalDetailsByMid " + mid);
		return (MobiLiteTerminal) getSessionFactory().createCriteria(MobiLiteTerminal.class)
				.add(Restrictions.like("merchant.id", mid)).setMaxResults(1).uniqueResult();
	}

	@Override
	public List<Merchant> listMerchantSummary(ArrayList<Criterion> props, String date, String date1) {

		String dat = null;
		String dat1 = null;
		String year1 = null;
		String year2 = null;
		ArrayList<Merchant> merchantSummary = new ArrayList<Merchant>();

		// TerminalDetails reader = readerDAO.loadTerminalByDevice(id);
		String sql = null;

		if (date != null) {

			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
				logger.info("check activation date1:" + dat);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		if (date1 == null || date1.equals("")) {
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			dat1 = dateFormat1.format(dt1);
			logger.info("check activation date2:" + dat1);

		} else {

			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		sql = "select m.ACTIVATE_DATE,m.USERNAME,m.BUSINESS_NAME,m.CONTACT_PERSON_PHONE_NUMBER,t.MID,m.BUSINESS_ADDRESS1 from MERCHANT m  INNER JOIN mid t"
				+ " on t.MERCHANT_FK = m.MID_FK where m.ACTIVATE_DATE  between :dat and :dat1"
				+ " order by m.ACTIVATE_DATE desc ";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());
		for (Object[] rec : resultSet) {

			Merchant merchantData = new Merchant();

			// logger.info("activate date from mobile user:" +
			// merchantData.getActivateDate() );
			String rd = null;
			try {
				/*
				 * rd = new SimpleDateFormat("dd-MMM-yyyy") .format(new
				 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .parse(rec[0].toString()));
				 */

				/*
				 * Date actDate = merchantData.getActivateDate();
				 * 
				 * DateTime fromDate = new DateTime(actDate);
				 */
				merchantData.setActivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));

				// merchantData.setActivateDate(new Date(rec[0].toString()));

				logger.info(" activation date from merchant 12233:" + merchantData.getActivateDate());
			} catch (ParseException e) {

				e.printStackTrace();
			}
			merchantData.setUsername(rec[1].toString());
			merchantData.setBusinessName(rec[2].toString());
			merchantData.setContactPersonPhoneNo(rec[3].toString());
			merchantData.setFirstName((rec[4].toString()));
			merchantData.setBusinessAddress1(rec[5].toString());

			merchantSummary.add(merchantData);
		}

		return merchantSummary;
	}

	@Override
	public List<Merchant> loadUMMerchant() {
		logger.info("MerchantDaoImpl:loadMerchant");
		return (List<Merchant>) getSessionFactory().createCriteria(Merchant.class)
				.add(Restrictions.eq("status", CommonStatus.ACTIVE)).add(Restrictions.in("merchantType", new String[]{"U", "FIUU"})).list();

	}

	@Override
	public String getMerchantCurrentMonthTxnByNOB(StringBuffer midStr, StringBuffer ummidStr) {
		String totalTxn = null, txnTotalSum = "";

		String umtotalTxn = null;

		// Double umTxn=0.0, totTxn=0.0, sumtxn=0.0;
		double umTxn = 0.0, totTxn = 0.0, sumtxn = 0.0;

		// Double totalSum=0.0;
		double totalSum = 0.0;

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();

		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();

		logger.info("check year and mon: " + year + " " + mon);

		String sql1 = "select count(*),sum(AMOUNT) as TotalAmount from FOR_SETTLEMENT where STATUS in ('A','S') and MONTH(TIME_STAMP) ="
				+ mon + " and MID IN (" + midStr + ") and TIME_STAMP like '" + year + "%'";

		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);

		logger.info("Query1 : " + sql1);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();

		for (Object[] rec : resultSet1) {
			// totalTxn = rec[1].toString();
			int a = Integer.parseInt(rec[0].toString());
			if (a == 0) {
				totalTxn = "0.00";
			} else {
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}
		}

		logger.info("totalTxn : " + totalTxn);

		if ((totalTxn != null) && (!(totalTxn.isEmpty()))) {
			totalSum = totalSum + Double.valueOf(totalTxn);
		}
		logger.info("totalSum : " + totalSum);

		if (ummidStr != null) {
			if (ummidStr.length() != 0) {
				String sql = "select count(*),sum(F007_TXNAMT) as TotalAmount from UM_ECOM_TXNRESPONSE where STATUS in ('A','C') and MONTH(TIME_STAMP) ="
						+ mon + " and F001_MID IN (" + midStr + ") and TIME_STAMP like '" + year + "%'";

				Query sqlQuery = super.getSessionFactory().createSQLQuery(sql1);

				logger.info("Query1 : " + sql1);

				@SuppressWarnings("unchecked")
				List<Object[]> resultSet = sqlQuery.list();

				for (Object[] rec : resultSet1) {
					// totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if (a == 0) {
						umtotalTxn = "0.00";
					} else {
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						umtotalTxn = myFormatter.format(d);
					}
				}
				if ((umtotalTxn != null) && (!(umtotalTxn.isEmpty()))) {
					totalSum = totalSum + Double.parseDouble(umtotalTxn);
					logger.info("umTxn : " + totalSum);
				}
			}
		}
		// sumtxn = totTxn+umTxn;
		txnTotalSum = String.valueOf(totalSum);
		logger.info("txnTotalSum : " + txnTotalSum);
		return txnTotalSum;
	}

	@Override
	public String getMerchantDailyTxnByNOB(StringBuffer midStr, StringBuffer ummidStr) {
		String totalTxn = null, txnTotalSum = "";
		double totalSum = 0.0;
		String umtotalTxn = null;

		double umTxn = 0.0, totTxn = 0.0, sumtxn = 0.0;
		DateTime dateTime = new DateTime();
		LocalDate lastDate = dateTime.minusDays(1).toLocalDate();
		String lastDay = lastDate.toString();

		logger.info("check date: " + lastDay);

		String sql1 = "select count(*),sum(AMOUNT) as TotalAmount from FOR_SETTLEMENT where STATUS in ('A','S') and MID IN ("
				+ midStr + ") and TIME_STAMP like '" + lastDay + "%'";

		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();

		for (Object[] rec : resultSet1) {
			// totalTxn = rec[1].toString();
			int a = Integer.parseInt(rec[0].toString());
			if (a == 0) {
				totalTxn = "0.00";
			} else {
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}
		}

		if ((totalTxn != null) && (!(totalTxn.isEmpty()))) {
			totalSum = totalSum + Double.parseDouble(totalTxn);
		}

		if (ummidStr != null) {
			if (ummidStr.length() != 0) {
				String sql = "select count(*),sum(F007_TXNAMT) as TotalAmount from UM_ECOM_TXNRESPONSE where STATUS in ('A','C')"
						+ " and F001_MID IN (" + midStr + ") and TIME_STAMP like '" + lastDay + "%'";

				Query sqlQuery = super.getSessionFactory().createSQLQuery(sql1);

				logger.info("Query1 : " + sql1);

				@SuppressWarnings("unchecked")
				List<Object[]> resultSet = sqlQuery.list();

				for (Object[] rec : resultSet1) {
					// totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if (a == 0) {
						umtotalTxn = "0.00";
					} else {
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						umtotalTxn = myFormatter.format(d);
					}
				}
				if ((umtotalTxn != null) && (!(umtotalTxn.isEmpty()))) {
					totalSum = totalSum + Double.parseDouble(umtotalTxn);
				}

			}
		}
		// sumtxn= totTxn+umTxn;
		txnTotalSum = String.valueOf(totalSum);

		return txnTotalSum;
	}

	@Override
	public String getMerchantWeeklyTxnByNOB(StringBuffer midStr, StringBuffer ummidStr) {
		String totalTxn = null, txnTotalSum = "";
		double totalSum = 0.0;
		String umtotalTxn = null;

		double umTxn = 0.0, totTxn = 0.0, sumtxn = 0.0;
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String from = df.format(c.getTime());
		for (int i = 0; i < 6; i++) {
			c.add(Calendar.DATE, 1);
		}
		String to = df.format(c.getTime());

		logger.info("check date:from " + from + ":to:" + to);

		logger.info("ummidStr" + ummidStr);

		/*
		 * String sql1
		 * ="select  count(*),sum(a.AMOUNT) as TotalAmount from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID or a.MID=m.MOTO_MID or"
		 * +" a.MID=m.EZYREC_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYPASS_MID"
		 * +" INNER JOIN MERCHANT f ON f.MID_FK=m.ID"
		 * +" where a.STATUS IN ('A','S') and f.NATURE_OF_BUSINESS=:nob and a.TIME_STAMP between :fromDate and :toDate order by"
		 * +" a.TIME_STAMP desc limit 1000";
		 */

		String sql1 = "select count(*),sum(AMOUNT) as TotalAmount from FOR_SETTLEMENT where STATUS in ('A','S') and MID IN ("
				+ midStr + ") and TIME_STAMP between :fromDate and :toDate order by" + " TIME_STAMP desc limit 1000";

		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);
		sqlQuery1.setString("fromDate", from);
		sqlQuery1.setString("toDate", to);

		logger.info("Query : " + sql1);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();

		for (Object[] rec : resultSet1) {
			// totalTxn = rec[1].toString();
			int a = Integer.parseInt(rec[0].toString());
			if (a == 0) {
				totalTxn = "0.00";
			} else {
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}
		}
		if ((totalTxn != null) && (!(totalTxn.isEmpty()))) {
			totalSum = totalSum + Double.parseDouble(totalTxn);
		}

		if (ummidStr != null) {
			if (ummidStr.length() != 0) {
				String sql = "select count(*),sum(F007_TXNAMT) as TotalAmount from UM_ECOM_TXNRESPONSE where STATUS in ('A','C')"
						+ " and F001_MID IN (" + ummidStr + ") and TIME_STAMP between :fromDate and :toDate order by"
						+ " TIME_STAMP desc limit 1000";

				Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
				sqlQuery.setString("fromDate", from);
				sqlQuery.setString("toDate", to);
				logger.info("Query1 : " + sql);

				@SuppressWarnings("unchecked")
				List<Object[]> resultSet = sqlQuery.list();

				for (Object[] rec : resultSet1) {
					// totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if (a == 0) {
						umtotalTxn = "0.00";
					} else {
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						umtotalTxn = myFormatter.format(d);
					}
				}
				if ((umtotalTxn != null) && (!(umtotalTxn.isEmpty()))) {
					totalSum = totalSum + Double.parseDouble(umtotalTxn);
				}
			}
		}
		// sumtxn = totTxn+umTxn;
		txnTotalSum = String.valueOf(totalSum);

		return txnTotalSum;
	}

	@Override
	public List<MID> loadMIDByNOB(List<Long> ids) {

		logger.info("loadMIDByNOB:  " + ids);

		/*
		 * String query =
		 * "Select MID,MOTO_MID,EZYPASS_MID from MID where MERCHANT_FK IN (:ids)"; Query
		 * sqlQuery = super.getSessionFactory().createSQLQuery(query);
		 * sqlQuery.setParameter("ids", ids);
		 */
//		
//		CharSequence[] cs = ids.toArray(new CharSequence[ids.size()]);
//		
//		String query ="Select MID,MOTO_MID,EZYPASS_MID from MID WHERE FIND_IN_SET(MERCHANT_FK, @parameter) != 0";
//		Query sqlQuery = super.getSessionFactory().createSQLQuery(query);
//		sqlQuery.setParameter("parameter", String.join(",", cs));

		/* list_id = [1,2,3] */
		// String query = "Select MID,MOTO_MID,EZYPASS_MID from MID where MERCHANT_FK in
		// ({})".format(",".join([long(i) for i in ids]));
		// String sql="Select MID,MOTO_MID,EZYPASS_MID from MID where MERCHANT_FK in
		// ("+StringUtils.join(arr, ',')+")";
		// Query sqlQuery = super.getSessionFactory().createSQLQuery(query);

		List<MID> mid = new ArrayList<MID>();
		ArrayList<MID> MIDList = new ArrayList<MID>();
		String query = "Select * from MID where MERCHANT_FK IN (" + ids.toString().replaceAll("[\\[\\]\\(\\)]", "")
				+ ")";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(query);
		logger.info("query " + query);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();

		// for (Object[] rec : resultSet) {

		/*
		 * if(rec[0].toString()!=null) { mid.add(rec[0].toString());
		 * 
		 * } if(rec[1].toString()!=null) { mid.add(rec[1].toString());
		 * 
		 * } if(rec[2].toString()!=null) { mid.add(rec[2].toString());
		 * 
		 * } if(rec[3].toString()!=null) { mid.add(rec[3].toString());
		 * 
		 * } if(rec[4].toString()!=null) { mid.add(rec[4].toString());
		 * 
		 * }
		 */

		// }
		logger.info("mid " + mid);

		return null;

		// return (List<MID>)
		// getSessionFactory().createCriteria(MID.class).add(Restrictions.eq("merchant",ids)).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Merchant> loadMerchantByAgID(BigInteger id) {
		logger.info("MerchantDaoImpl:loadMerchant agent id " + id);

		return (List<Merchant>) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("agID", id))
				.list();
	}

	@Override
	@Transactional(readOnly = true)
	public void listPayLaterMerchants(final PaginationBean<Merchant> paginationBean) {
		logger.info("Inside   listPayLaterMerchants ");

		ArrayList<Merchant> fss = new ArrayList<Merchant>();
		String sql = null;

		sql = "select DISTINCT m.ACTIVATE_DATE,m.BUSINESS_NAME AS MerchantName ,m.STATUS ,m.CITY, "
				+ "mi.UM_MOTO_MID,md.AMOUNT " + "from MERCHANT m INNER JOIN MID mi on m.MID_FK=mi.ID "
				+ "INNER JOIN MOBIVERSA_MDR md ON md.MID = mi.UM_MOTO_MID "
				+ "where md.PAY_LATER = 'Yes' and m.STATUS = 'ACTIVE' " + "order by m.ACTIVATE_DATE desc";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				fs.setCreatedBy(rd); // activation date
			}
			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setState(rec[2].toString()); // status
			}
			if (rec[3] != null) {
				fs.setCity(rec[3].toString());
			} else {
				fs.setCity("");
			}
			if (rec[4] != null) {
				fs.setReferralId(rec[4].toString()); // mid
			}
			if (rec[5] != null) {
				fs.setMdr(rec[5].toString());
			}

			fss.add(fs);
		}

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = true)
	public void searchPayLaterMerchants(final PaginationBean<Merchant> paginationBean, String date, String date1) {
		logger.info("Inside searchPayLaterMerchants : " + date + "     " + date1);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;

		ArrayList<Merchant> fss = new ArrayList<Merchant>();
		String sql = null;

		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
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

		sql = "select DISTINCT m.ACTIVATE_DATE,m.BUSINESS_NAME AS MerchantName ,m.STATUS ,m.CITY, "
				+ "mi.UM_MOTO_MID,md.AMOUNT " + "from MERCHANT m INNER JOIN MID mi on m.MID_FK=mi.ID "
				+ "INNER JOIN MOBIVERSA_MDR md ON md.MID = mi.UM_MOTO_MID "
				+ "where md.PAY_LATER = 'Yes' and m.STATUS = 'ACTIVE' and m.ACTIVATE_DATE between :from and :to  "
				+ "order by m.ACTIVATE_DATE desc";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("from", from);
		sqlQuery.setString("to", to);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				fs.setCreatedBy(rd); // activation date
			}
			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setState(rec[2].toString()); // status
			}
			if (rec[3] != null) {
				fs.setCity(rec[3].toString());
			} else {
				fs.setCity("");
			}
			if (rec[4] != null) {
				fs.setReferralId(rec[4].toString()); // mid
			}
			if (rec[5] != null) {
				fs.setMdr(rec[5].toString());
			}

			fss.add(fs);
		}

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = true)
	public void listMerchantDetails(final PaginationBean<Merchant> paginationBean) {
		logger.info("listMerchantDetails ");
		String sql = null;
		ArrayList<Merchant> fss = new ArrayList<Merchant>();

		sql = "select  m.ACTIVATE_DATE,m.BUSINESS_NAME AS MerchantName,m.STATUS,m.ID,ag.FIRST_NAME "
				+ "from MERCHANT m INNER JOIN AGENT ag ON ag.ID = m.AGID_FK "
				+ "where m.STATUS = 'ACTIVE' order by m.ACTIVATE_DATE desc";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				fs.setCreatedBy(rd); // activation date
			}
			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setState(rec[2].toString()); // status
			}
			if (rec[3] != null) {
				fs.setApproved(rec[3].toString()); // Id
			}
			if (rec[4] != null) {
				fs.setOwnerName(rec[4].toString());
			}

			fss.add(fs);
		}

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = true)
	public void listMerchantGPVDetails(final PaginationBean<Merchant> paginationBean) {
		logger.info("listMerchantDetails ");
		String sql = null;
		ArrayList<Merchant> fss = new ArrayList<Merchant>();
		String from = null;
		String to = null;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 0);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date date = cal.getTime();
		SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
		from = fromFormat.format(date);

		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.YEAR, 0);
		cal1.add(Calendar.MONTH, -1);
		cal1.set(Calendar.DAY_OF_MONTH, cal1.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date date1 = cal1.getTime();
		SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
		to = toFormat.format(date1);

		sql = "select  m.ACTIVATE_DATE,t.MERCHANT_NAME,m.STATUS,t.MERCHANT_ID,t.AGENT_NAME, "
				+ "sum(t.AMOUNT) from TRANSACTION_SUMMARY t " + "INNER JOIN MERCHANT m ON m.ID = t.MERCHANT_ID "
				+ "where m.STATUS = 'ACTIVE' AND t.TXN_DATE BETWEEN :from and :to group by t.MERCHANT_ID order by t.TIME_STAMP desc";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("from", from);
		sqlQuery.setString("to", to);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				fs.setCreatedBy(rd); // activation date
			}

			logger.info("Activation date : " + fs.getCreatedBy());
			if (rec[1] != null) {
				logger.info("Business name : ");
				fs.setBusinessName(rec[1].toString());
			} else {
				fs.setBusinessName("");
			}
			if (rec[2] != null) {
				fs.setState(rec[2].toString()); // status
			}
			if (rec[3] != null) {
				fs.setApproved(rec[3].toString()); // Id
			}
			if (rec[4] != null) {
				fs.setOwnerName(rec[4].toString());
			}
			if (rec[5] != null) {
				Double d = new Double(rec[5].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String Txn = myFormatter.format(d);
				fs.setAutoSettled(Txn);
			}

			fss.add(fs);
		}

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = true)
	public void loadTerminalDetails(StringBuffer midStr, PaginationBean<TerminalDetails> paginationBean) {
		logger.info("listMerchantDetails ");
		String sql = null;

		ArrayList<TerminalDetails> fss = new ArrayList<TerminalDetails>();

		sql = "SELECT t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID "
				+ "FROM TERMINAL_DETAILS t WHERE t.MERCHANT_ID IN (" + midStr + ")";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			TerminalDetails term = new TerminalDetails();
			if (rec[0] != null) {

				term.setDeviceType(rec[0].toString());
			}
			if (rec[1] != null) {
				term.setTid(rec[1].toString());
			}
			if (rec[2] != null) {
				term.setConnectType(rec[2].toString()); // activation date
			}
			if (rec[3] != null) {
				term.setActiveStatus(rec[3].toString()); // suspension date
			}
			if (rec[4] != null) {
				term.setMerchantId(rec[4].toString());
			}

			fss.add(term);
		}

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	public List<String> getProductDetails(StringBuffer midStr) {

		String sql = null;
		List<String> productType = new ArrayList<String>();
		productType.add("ALL");

		sql = "SELECT DISTINCT t.DEVICE_TYPE FROM TERMINAL_DETAILS t "
				+ "WHERE t.DEVICE_TYPE IS NOT NULL AND t.MERCHANT_ID IN (" + midStr + ")";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> resultSet = sqlQuery.list();
		logger.info("Size ---: " + resultSet.size());

		for (String rec : resultSet) {

			if (rec != null) {

				productType.add(rec);
			}

		}
		return productType;
	}

	@Override
	@Transactional(readOnly = true)
	public void loadCurrentTxnDetails(Merchant merchant, PaginationBean<TerminalDetails> paginationBean,
			String agentName) {
		logger.info("listMerchantDetails ");
		String sql = null;
		String totalTxn = null;
		// String mid = merchant.getMid().getMid();

		List<String> midList = new ArrayList<String>();
		String mid = null, motoMid = null, ezypassMid = null, ezywayMid = null, ezyrecMid = null, gPayMid = null,
				umMid = null, umEzyway = null, umEzymoto = null, fiuuMid = null;
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
			midList.add(mid);
		} else if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
			midList.add(motoMid);
		} else if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
			midList.add(ezypassMid);
		} else if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
			midList.add(ezyrecMid);
		} else if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
			midList.add(ezywayMid);
		} else if (merchant.getMid().getGpayMid() != null) {
			gPayMid = merchant.getMid().getGpayMid();
			midList.add(gPayMid);
		} else if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
			midList.add(umMid);
		} else if (merchant.getMid().getUmEzywayMid() != null) {
			umEzyway = merchant.getMid().getUmEzywayMid();
			midList.add(umEzyway);
		} else if (merchant.getMid().getUmMotoMid() != null) {
			umEzymoto = merchant.getMid().getUmMotoMid();
			midList.add(umEzymoto);
		}else if (merchant.getMid().getFiuuMid() != null) {
			fiuuMid = merchant.getMid().getFiuuMid();
			midList.add(fiuuMid);
		}

		StringBuffer midStr = new StringBuffer();
		int u = 0;
		for (String strMid : midList) {

			if (u == 0) {
				midStr.append("\"");
				midStr.append(strMid);
				midStr.append("\"");
				u++;
			} else {
				midStr.append(",\"");
				midStr.append(strMid);
				midStr.append("\"");
			}
		}
		logger.info("String of MIDs:  " + midStr);

		ArrayList<TerminalDetails> fss = new ArrayList<TerminalDetails>();
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		Date dt = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		from = dateFormat.format(dt);
		String from1 = from.substring(0, from.length() - 2);
		from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
		logger.info("change date format:" + from);

		Date dt1 = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		to = dateFormat1.format(dt1);
		String to1 = to.substring(0, to.length() - 2);
		to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
		logger.info("date format:" + to);

		if ((merchant.getMerchantType() == null) || (merchant.getMerchantType().equals("P"))) {

			/*
			 * sql =
			 * "SELECT count(*),sum(a.AMOUNT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(a.TIME_STAMP) "
			 * + "FROM TERMINAL_DETAILS t INNER JOIN MID m " + "ON t.MERCHANT_ID = m.MID " +
			 * "INNER JOIN FOR_SETTLEMENT a ON a.MID=m.MID or a.MID=m.MOTO_MID or " +
			 * "a.MID=m.EZYPASS_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID "
			 * +
			 * "WHERE t.MERCHANT_ID IN ("+midStr+") AND a.TIME_STAMP BETWEEN :from AND :to "
			 * + "order by a.TIME_STAMP desc";
			 */

			sql = "SELECT count(*),sum(f.AMOUNT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(f.TIME_STAMP),'"
					+ agentName + "' " + " FROM  mobiversa.FOR_SETTLEMENT f , mobiversa.TERMINAL_DETAILS t WHERE "
					+ " t.MERCHANT_ID= f.MID AND f.MID IN (" + midStr + ") AND f.TIME_STAMP BETWEEN "
					+ ":from AND :to group by f.MID order by f.TIME_STAMP desc ";
		}

		else if ((merchant.getMerchantType() != null) || (merchant.getMerchantType().equals("U") || merchant.getMerchantType().equals("FIUU"))) {

			/*
			 * sql =
			 * "SELECT count(*),sum(a.F007_TXNAMT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(a.TIME_STAMP) "
			 * + "FROM TERMINAL_DETAILS t INNER JOIN MID m " + "ON t.MERCHANT_ID = m.MID " +
			 * "INNER JOIN UM_ECOM_TXNRESPONSE a ON a.F001_MID =m.UM_MOTO_MID or a.F001_MID =m.UM_EZYWAY_MID "
			 * +
			 * "WHERE t.MERCHANT_ID IN ("+midStr+") AND a.TIME_STAMP BETWEEN :from AND :to "
			 * + "order by a.TIME_STAMP desc";
			 */

			sql = "SELECT count(*),sum(f.F007_TXNAMT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(f.TIME_STAMP),'"
					+ agentName + "' " + "FROM  mobiversa.UM_ECOM_TXNRESPONSE f , mobiversa.TERMINAL_DETAILS t WHERE "
					+ " t.MERCHANT_ID= f.F001_MID AND f.F001_MID IN (" + midStr + ") AND f.TIME_STAMP BETWEEN "
					+ ":from AND :to group by f.F001_MID order by f.TIME_STAMP desc ";

		}

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("from", from);
		sqlQuery.setString("to", to);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			logger.info("records in the List : " + rec[0]);
			logger.info("records String : " + rec[0].toString());
			TerminalDetails term = new TerminalDetails();

			int a = Integer.parseInt(rec[0].toString());

			if (a == 0) {
				totalTxn = "0.00";
			} else {
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}

			if (rec[2] != null) {

				term.setDeviceType(rec[2].toString());
			}
			if (rec[3] != null) {
				term.setTid(rec[3].toString());
			}
			if (rec[4] != null) {
				term.setConnectType(rec[4].toString()); // activation date
			}
			if (rec[5] != null) {
				term.setActiveStatus(rec[5].toString()); // suspension date
			}

			if ((rec[6] != null) && (totalTxn != null)) {
				term.setMerchantId(rec[6].toString());
				term.setRemarks(totalTxn);
			}
			if (rec[7] != null) {
				term.setKeyStatus(rec[7].toString());
			}
			if (rec[8] != null) {
				term.setContactName(rec[8].toString());
			}

			fss.add(term);
		}

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = true)
	public void searchTxnDetails(Merchant merchant, PaginationBean<TerminalDetails> paginationBean, String period,
			String productType, String year) {
		logger.info("listMerchantDetails ");
		String sql = null;
		String totalTxn = null;
		// String mid = merchant.getMid().getMid();

		List<String> midList = new ArrayList<String>();
		String mid = null, motoMid = null, ezypassMid = null, ezywayMid = null, ezyrecMid = null, gPayMid = null,
				umMid = null, umEzyway = null, umEzymoto = null, fiuuMid = null;
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
			midList.add(mid);
		} else if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
			midList.add(motoMid);
		} else if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
			midList.add(ezypassMid);
		} else if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
			midList.add(ezyrecMid);
		} else if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
			midList.add(ezywayMid);
		} else if (merchant.getMid().getGpayMid() != null) {
			gPayMid = merchant.getMid().getGpayMid();
			midList.add(gPayMid);
		} else if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
			midList.add(umMid);
		} else if (merchant.getMid().getUmEzywayMid() != null) {
			umEzyway = merchant.getMid().getUmEzywayMid();
			midList.add(umEzyway);
		} else if (merchant.getMid().getUmMotoMid() != null) {
			umEzymoto = merchant.getMid().getUmMotoMid();
			midList.add(umEzymoto);
		}else if (merchant.getMid().getFiuuMid() != null) {
			fiuuMid = merchant.getMid().getFiuuMid();
			midList.add(fiuuMid);
		}

		StringBuffer midStr = new StringBuffer();
		int u = 0;
		for (String strMid : midList) {

			if (u == 0) {
				midStr.append("\"");
				midStr.append(strMid);
				midStr.append("\"");
				u++;
			} else {
				midStr.append(",\"");
				midStr.append(strMid);
				midStr.append("\"");
			}
		}
		logger.info("String of MIDs:  " + midStr);
		int quarter = 0;
		if (period.equals("Q1")) {

			// startDate = year+"-"+"01-01";
			quarter = 1;

		} else if (period.equals("Q2")) {
			quarter = 2;

		} else if (period.equals("Q3")) {
			quarter = 3;

		} else if (period.equals("Q4")) {
			quarter = 4;
		}

		ArrayList<TerminalDetails> fss = new ArrayList<TerminalDetails>();
		logger.info("quarter:: " + quarter);
		logger.info("productType:: " + productType);

		if ((merchant.getMerchantType() == null) || (merchant.getMerchantType().equals("P"))) {

			if (productType.equals("ALL")) {
				logger.info("productType ALL Txn");
				/*
				 * sql =
				 * "SELECT count(*),sum(a.AMOUNT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(a.TIME_STAMP) "
				 * + "FROM TERMINAL_DETAILS t INNER JOIN MID m " + "ON t.MERCHANT_ID = m.MID " +
				 * "INNER JOIN FOR_SETTLEMENT a ON a.MID=m.MID or a.MID=m.MOTO_MID or " +
				 * "a.MID=m.EZYPASS_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID "
				 * + "WHERE  QUARTER(a.TIME_STAMP)=:quarter AND YEAR(a.TIME_STAMP) =:year AND "
				 * + "t.MERCHANT_ID IN ("+midStr+") " + "GROUP BY MONTH(a.TIME_STAMP)";
				 */

				sql = "SELECT count(*),sum(f.AMOUNT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(f.TIME_STAMP) "
						+ "FROM  mobiversa.FOR_SETTLEMENT f , mobiversa.TERMINAL_DETAILS t WHERE "
						+ "t.MERCHANT_ID= f.MID AND f.MID IN (" + midStr
						+ ") AND QUARTER(f.TIME_STAMP)=:quarter AND YEAR(f.TIME_STAMP) =:year "
						+ "group by f.MID order by f.TIME_STAMP desc ";

			} else {

				/*
				 * sql =
				 * "SELECT count(*),sum(a.AMOUNT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(a.TIME_STAMP) "
				 * + "FROM TERMINAL_DETAILS t INNER JOIN MID m " + "ON t.MERCHANT_ID = m.MID " +
				 * "INNER JOIN FOR_SETTLEMENT a ON a.MID=m.MID or a.MID=m.MOTO_MID or " +
				 * "a.MID=m.EZYPASS_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID "
				 * + "WHERE  QUARTER(a.TIME_STAMP)=:quarter AND YEAR(a.TIME_STAMP) =:year AND "
				 * + "t.MERCHANT_ID IN ("+midStr+")  AND  t.DEVICE_TYPE ='"+productType +"' " +
				 * "GROUP BY MONTH(a.TIME_STAMP)";
				 */

				sql = "SELECT count(*),sum(f.AMOUNT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(f.TIME_STAMP) "
						+ "FROM  mobiversa.FOR_SETTLEMENT f , mobiversa.TERMINAL_DETAILS t WHERE "
						+ "t.MERCHANT_ID= f.MID AND f.MID IN (" + midStr
						+ ") AND QUARTER(f.TIME_STAMP)=:quarter AND YEAR(f.TIME_STAMP) =:year "
						+ "AND  t.DEVICE_TYPE ='" + productType + "' group by f.MID order by f.TIME_STAMP desc ";
			}
		}

		else if ((merchant.getMerchantType() != null) || (merchant.getMerchantType().equals("U"))) {

			if (productType.equals("ALL")) {
				logger.info("productType ALL Txn");

				/*
				 * sql =
				 * "SELECT count(*),sum(a.F007_TXNAMT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(a.TIME_STAMP) "
				 * + "FROM TERMINAL_DETAILS t INNER JOIN MID m " + "ON t.MERCHANT_ID = m.MID " +
				 * "INNER JOIN UM_ECOM_TXNRESPONSE a ON a.F001_MID =m.UM_MOTO_MID or a.F001_MID =m.UM_EZYWAY_MID "
				 * + "WHERE  QUARTER(a.TIME_STAMP)=:quarter AND YEAR(a.TIME_STAMP) =:year AND "
				 * + "t.MERCHANT_ID IN ("+midStr+") " + "GROUP BY MONTH(a.TIME_STAMP)";
				 */

				sql = "SELECT count(*),sum(f.F007_TXNAMT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(f.TIME_STAMP) "
						+ "FROM  mobiversa.UM_ECOM_TXNRESPONSE f , mobiversa.TERMINAL_DETAILS t WHERE "
						+ " t.MERCHANT_ID= f.F001_MID AND f.F001_MID IN (" + midStr
						+ ") AND QUARTER(f.TIME_STAMP)=:quarter AND YEAR(f.TIME_STAMP) =:year "
						+ "group by f.F001_MID order by f.TIME_STAMP desc ";

			} else {

				/*
				 * sql =
				 * "SELECT count(*),sum(a.F007_TXNAMT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(a.TIME_STAMP) "
				 * + "FROM TERMINAL_DETAILS t INNER JOIN MID m " + "ON t.MERCHANT_ID = m.MID " +
				 * "INNER JOIN UM_ECOM_TXNRESPONSE a ON a.F001_MID =m.UM_MOTO_MID or a.F001_MID =m.UM_EZYWAY_MID "
				 * + "WHERE  QUARTER(a.TIME_STAMP)=:quarter AND YEAR(a.TIME_STAMP) =:year AND "
				 * + "t.MERCHANT_ID IN ("+midStr+")  AND t.DEVICE_TYPE ='"+productType +"' " +
				 * "GROUP BY MONTH(a.TIME_STAMP)";
				 */

				sql = "SELECT count(*),sum(f.F007_TXNAMT),t.DEVICE_TYPE,t.TID,t.ACTIVATED_DATE,t.SUSPENDED_DATE,t.MERCHANT_ID,MONTHNAME(f.TIME_STAMP) "
						+ "FROM  mobiversa.UM_ECOM_TXNRESPONSE f , mobiversa.TERMINAL_DETAILS t WHERE "
						+ "t.MERCHANT_ID= f.F001_MID AND f.F001_MID IN (" + midStr
						+ ") AND QUARTER(f.TIME_STAMP)=:quarter AND YEAR(f.TIME_STAMP) =:year " + "AND t.DEVICE_TYPE ='"
						+ productType + "' group by f.F001_MID order by f.TIME_STAMP desc ";
			}
		}

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setLong("quarter", quarter);
		sqlQuery.setString("year", year);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			TerminalDetails term = new TerminalDetails();

			int a = Integer.parseInt(rec[0].toString());

			if (a == 0) {
				totalTxn = "0.00";
			} else {
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}

			if (rec[2] != null) {

				term.setDeviceType(rec[2].toString());
			}
			if (rec[3] != null) {
				term.setTid(rec[3].toString());
			}
			if (rec[4] != null) {
				term.setConnectType(rec[4].toString()); // activation date
			}
			if (rec[5] != null) {
				term.setActiveStatus(rec[5].toString()); // suspension date
			}
			if ((rec[6] != null) && (totalTxn != null)) {
				term.setMerchantId(rec[6].toString());
				term.setRemarks(totalTxn);

			}
			if (rec[7] != null) {
				term.setKeyStatus(rec[7].toString());
			}
			fss.add(term);
		}

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = true)
	public void getMerchantGPV(StringBuffer midList, final PaginationBean<Merchant> paginationBean) {
		logger.info("getMerchantGPV ");
		String sql = null;
		ArrayList<Merchant> fss = new ArrayList<Merchant>();
		String totalTxn = "";
		Double totalSum = 0.0;
		String totalTxn1 = null, totalTxn2 = null, totalTxn3 = null;

		sql = "select count(*),sum(a.AMOUNT)  " + "from FOR_SETTLEMENT a where a.MID IN (" + midList + ") "
				+ "a.TIME_STAMP desc";

		Query sqlQuery1 = super.getSessionFactory().createQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();

		for (Object[] rec : resultSet1) {

			int a = Integer.parseInt(rec[0].toString());
			if (a == 0) {
				totalTxn1 = "0.00";
			} else {
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn1 = myFormatter.format(d);
			}
		}

		if ((totalTxn1 != null) && (!(totalTxn1.isEmpty()))) {
			totalSum = totalSum + Double.parseDouble(totalTxn1);
		}

		String sql2 = "select count(*),sum(u.F007_TXNAMT) " + "from UM_ECOM_TXNRESPONSE u where u.F001_MID IN ("
				+ midList + ") " + "u.TIME_STAMP desc";

		logger.info("Query2 : " + sql2);
		Query sqlQuery2 = super.getSessionFactory().createQuery(sql2);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet2 = sqlQuery2.list();

		for (Object[] rec : resultSet2) {
			// totalTxn = rec[1].toString();
			int a = Integer.parseInt(rec[0].toString());
			if (a == 0) {
				totalTxn2 = "0.00";
			} else {
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn2 = myFormatter.format(d);
			}
		}

		if ((totalTxn2 != null) && (!(totalTxn2.isEmpty()))) {
			totalSum = totalSum + Double.parseDouble(totalTxn2);
		}

		totalTxn = String.valueOf(totalSum);

		logger.info("totalTxn : " + totalTxn);

		paginationBean.setItemList(fss);
		logger.info("No of Records: " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = true)
	public Merchant validateMerchantEmailId(String emailId) {
		logger.info("validateMerchantEmailId ");

		/*
		 * sql="SELECT EXISTS(SELECT * from mobiversa.merchant m " +
		 * "WHERE m.EMAIL='"+emailId+"')";
		 */

		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("email", emailId))
				.setMaxResults(1).uniqueResult();

	}

	@Override
	public List<MerchantGPVData> listMerchantGPVDetailsByAgent(String agentID) {

		String sql = null;
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		System.out.println("cDate: " + cDate);
		List<Integer> listMonth = getAllMonth(cDate);
		List<MerchantGPVData> fss = new ArrayList<MerchantGPVData>();

		sql = "select MONTH(t.TXN_DATE),t.MERCHANT_NAME,t.MERCHANT_ID,t.AGENT_NAME,t.PRODUCT_TYPE, "
				+ "sum(t.AMOUNT),t.HOST_TYPE from TRANSACTION_SUMMARY t "
				+ "INNER JOIN MERCHANT m ON m.ID = t.MERCHANT_ID " + "INNER JOIN AGENT ag ON ag.ID = m.AGID_FK "
				+ "WHERE ag.ID = " + agentID + " AND  t.TXN_DATE between DATE_SUB(now(), INTERVAL 4 MONTH)  AND now() "
				+ "AND m.STATUS = 'ACTIVE'  group BY t.PRODUCT_TYPE,MONTH(t.TXN_DATE), "
				+ "t.MERCHANT_ID ORDER BY t.MERCHANT_ID,MONTH(t.TXN_DATE) ";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		// sqlQuery.setString("agentID", agentID);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			MerchantGPVData fs = new MerchantGPVData();

			if (rec[0] != null) {

				fs.setMonthName(rec[0].toString()); // activation date
			}

			logger.info("Month : " + fs.getMonthName());
			if (rec[1] != null) {
				logger.info("Business name : ");
				fs.setMerchantName(rec[1].toString());
			} else {
				fs.setMerchantName("");
			}
			if (rec[2] != null) {
				fs.setMerchantId(rec[2].toString()); // merchant id
			}
			if (rec[3] != null) {
				fs.setAgentName(rec[3].toString()); // AgentName
			}
			/*
			 * if(rec[4]!=null) { fs.setPermiseType(rec[4].toString()); // productType }
			 */
			if (rec[5] != null) {
				if ((rec[4] != null) && (rec[4].toString().equals("EZYMOTO"))) {
					fs.setIsEzymoto(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzymotoAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("EZYWIRE"))) {
					fs.setIsEzywire(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzywireAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("EZYWAY"))) {
					fs.setIsEzyway(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzywayAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("EZYLINK"))) {
					fs.setIsEzylink(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzylinkAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("EZYREC"))) {
					fs.setIsEzyrec(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzyrecAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("EZYMOTO-VCC"))) {
					fs.setIsEzymotoVcc(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;
					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzymotoVccAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("RECURRING"))) {
					fs.setIsRecurring(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setRecurringAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("RECPLUS"))) {
					fs.setIsRecplus(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setRecplusAmt(Txn);
				}

				if (rec[6] != null) {
					fs.setMerchantType(rec[6].toString()); // AgentName
				} else {
					fs.setMerchantType("P");
				}

			}

			fss.add(fs);
		}
		return fss;
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

	@Override
	public List<MerchantGPVData> listMerchantGPVDetailsBySuperAgent() {

		String sql = null;
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		System.out.println("cDate: " + cDate);

		List<Integer> listMonth = getAllMonth(cDate);
		List<MerchantGPVData> fss = new ArrayList<MerchantGPVData>();

		sql = "select MONTH(t.TXN_DATE),t.MERCHANT_NAME,t.MERCHANT_ID,t.AGENT_NAME,t.PRODUCT_TYPE, "
				+ "sum(t.AMOUNT),t.HOST_TYPE from TRANSACTION_SUMMARY t "
				+ "INNER JOIN MERCHANT m ON m.ID = t.MERCHANT_ID " + "INNER JOIN AGENT ag ON ag.ID = m.AGID_FK "
				+ "WHERE t.TXN_DATE between DATE_SUB(now(), INTERVAL 3 MONTH)  AND now() "
				+ "AND m.STATUS = 'ACTIVE'  group BY t.PRODUCT_TYPE, "
				+ "t.MERCHANT_ID,MONTH(t.TXN_DATE) ORDER BY t.MERCHANT_ID,MONTH(t.TXN_DATE) ";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		// sqlQuery.setString("agentID", agentID);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			MerchantGPVData fs = new MerchantGPVData();

			if (rec[0] != null) {

				fs.setMonthName(rec[0].toString()); // activation date
			}

			logger.info("Month : " + fs.getMonthName());
			if (rec[1] != null) {
				logger.info("Business name : ");
				fs.setMerchantName(rec[1].toString());
			} else {
				fs.setMerchantName("");
			}
			if (rec[2] != null) {
				fs.setMerchantId(rec[2].toString()); // merchant id
			}
			if (rec[3] != null) {
				fs.setAgentName(rec[3].toString()); // AgentName
			}
			/*
			 * if(rec[4]!=null) { fs.setPermiseType(rec[4].toString()); // productType }
			 */
			if (rec[5] != null) {
				if ((rec[4] != null) && (rec[4].toString().equals("EZYMOTO"))) {
					fs.setIsEzymoto(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;
					logger.info("Amount : " + d);

					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzymotoAmt(Txn);
				}
				// as country
				if ((rec[4] != null) && (rec[4].toString().equals("EZYWAY"))) {
					fs.setIsEzyway(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzywayAmt(Txn);
				}
				// as currency
				if ((rec[4] != null) && (rec[4].toString().equals("EZYLINK"))) {
					fs.setIsEzylink(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzylinkAmt(Txn);
				}
				// as dateOfBirth
				if ((rec[4] != null) && (rec[4].toString().equals("EZYREC"))) {
					fs.setIsEzyrec(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzyrecAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("RECURRING"))) {
					fs.setIsRecurring(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setRecurringAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("RECPLUS"))) {
					fs.setIsRecplus(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setRecplusAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("EZYMOTO-VCC"))) {
					fs.setIsEzymotoVcc(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzymotoVccAmt(Txn);
				}

				if ((rec[4] != null) && (rec[4].toString().equals("EZYWIRE"))) {
					fs.setIsEzywire(rec[4].toString());
					Double d = new Double(rec[5].toString());
					d = d / 100;

					logger.info("Amount : " + d);
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(d);
					logger.info("Txn : " + Txn + "for::" + rec[4].toString());
					fs.setEzywireAmt(Txn);
				}

				if (rec[6] != null) {
					fs.setMerchantType(rec[6].toString()); // AgentName
				} else {
					fs.setMerchantType("P");
				}

			}

			fss.add(fs);
		}
		return fss;
	}

	@Override
	@Transactional(readOnly = false)
	public void listMerchantUserByMid(final PaginationBean<Merchant> paginationBean) {
		logger.info("listMerchantUserByMid::");

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;
		sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.STATUS,m.MODIFIED_DATE,m.CITY,m.AUTH_3DS, "
				+ "mi.MID,mi.MOTO_MID,mi.EZYPASS_MID,mi.EZYREC_MID,mi.EZYWAY_MID, "
				+ "mi.UM_MID,mi.UM_MOTO_MID,mi.UM_EZYWAY_MID,mi.UM_EZYREC_MID,m.ID,m.INTEGRATION_PLATFORM,m.MERCHANT_TYPE,mi.SPLIT_MID,"
				+ "mi.BOOST_MID,mi.GRAB_MID,mi.FPX_MID,mi.TNG_MID,mi.SHOPPY_MID,mi.BNPL_MID from MERCHANT m "
				+ "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK ORDER BY m.ACTIVATE_DATE desc";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			MID mi = new MID();

			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setCreatedBy(rd);
			}

			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}

			// status
			if (rec[2] != null) {
				fs.setState(rec[2].toString());
			}

			String rd1 = null;
			if (rec[3] != null) {
				try {
					rd1 = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[3].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setModifiedBy(rd1);
			} else {
				fs.setModifiedBy("");
			}

			if (rec[4] != null) {
				fs.setCity(rec[4].toString());
			} else {
				fs.setCity("");
			}

			if (rec[5] != null) {
				fs.setAuth3DS(rec[5].toString());
			}

			if (rec[6] != null) {
				mi.setMid(rec[6].toString());
			} else {
				mi.setMid("");
			}

			if (rec[7] != null) {

				mi.setMotoMid(rec[7].toString());

			} else {
				mi.setMotoMid("");

			}

			if (rec[8] != null) {
				mi.setEzypassMid(rec[8].toString());
			} else {
				mi.setEzypassMid("");
			}

			if (rec[9] != null) {
				mi.setEzyrecMid(rec[9].toString());
			} else {
				mi.setEzyrecMid("");
			}

			if (rec[10] != null) {
				mi.setEzywayMid(rec[10].toString());
			} else {
				mi.setEzywayMid("");
			}

			if (rec[11] != null) {
				mi.setUmMid(rec[11].toString());
			} else {
				mi.setUmMid("");
			}

			if (rec[12] != null) {

				mi.setUmMotoMid(rec[12].toString());
			}

			else {
				mi.setUmMotoMid("");

			}

			if (rec[13] != null) {
				mi.setUmEzywayMid(rec[13].toString());
			} else {
				mi.setUmEzywayMid("");
			}

			if (rec[14] != null) {
				mi.setUmEzyrecMid(rec[14].toString());
			} else {
				mi.setUmEzyrecMid("");
			}

			if (rec[15] != null) {

				fs.setId(Long.valueOf(rec[15].toString()));
			}

			if (rec[18] != null) {

				mi.setSplitMid(rec[18].toString());
			} else {
				mi.setSplitMid("");
			}

			fs.setMid(mi);

			if (rec[16] != null) {

				fs.setType(rec[16].toString());
			} else {
				fs.setType("");
			}

			if (rec[17] != null) {

				fs.setMerchantType(rec[17].toString());
			}

			if (rec[19] != null) {
				mi.setBoostMid(rec[19].toString());
			} else {
				mi.setBoostMid("");
			}
			if (rec[20] != null) {
				mi.setGrabMid(rec[20].toString());
			} else {
				mi.setGrabMid("");
			}
			if (rec[21] != null) {
				mi.setFpxMid(rec[21].toString());
			} else {
				mi.setFpxMid("");
			}

			if (rec[22] != null) {
				mi.setTngMid(rec[22].toString());
			} else {
				mi.setTngMid("");
			}
			if (rec[23] != null) {
				mi.setShoppyMid(rec[23].toString());
			} else {
				mi.setShoppyMid("");
			}
			if (rec[24] != null) {
				mi.setBnplMid(rec[24].toString());
			} else {
				mi.setBnplMid("");
			}

			fss.add(fs);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	@Override
	@Transactional(readOnly = false)
	public void listMerchantUsersByMid(final PaginationBean<MerchantDTO> paginationBean) {

		List<MerchantDTO> fss = new ArrayList<MerchantDTO>();

		String sql = "SELECT m.ACTIVATE_DATE, m.BUSINESS_NAME, m.STATUS, m.MODIFIED_DATE, m.CITY, m.AUTH_3DS, mi.MID, mi.MOTO_MID, mi.EZYPASS_MID, mi.EZYREC_MID, mi.EZYWAY_MID, mi.UM_MID, mi.UM_MOTO_MID, mi.UM_EZYWAY_MID, mi.UM_EZYREC_MID, m.ID, m.INTEGRATION_PLATFORM, m.MERCHANT_TYPE, mi.SPLIT_MID, mi.BOOST_MID, mi.GRAB_MID, mi.FPX_MID,"
				+ " mi.TNG_MID, mi.SHOPPY_MID, mi.BNPL_MID, mu.ENABLE_SETTELEMENT_PAYOUT, h.HOST_NAME, mi.FIUU_MID "
				+ "FROM mobiversa.MERCHANT m INNER JOIN mobiversa.MID mi ON m.ID = mi.MERCHANT_FK "
				+ "INNER JOIN mobiversa.MOBILE_USER mu ON m.ID = mu.MERCHANT_FK "
				+ "LEFT JOIN mobiversa.HOST_DETAILS h ON mu.FPX_HOST_ID = h.HOST_ID " + "ORDER BY m.ACTIVATE_DATE DESC";

		List<Object[]> resultSet = super.getSessionFactory().createSQLQuery(sql).list();

		logger.info(
				"List merchant User by Mid. Query: " + sql + ". Number of records in the List: " + resultSet.size());

		for (Object[] rec : resultSet) {
			MerchantDTO md = new MerchantDTO();

			md.setCreatedBy(formatDate(rec[0]));
			md.setBusinessName(getStringOrDefault(rec[1]));
			md.setModifiedBy(formatDate(rec[3]));
			md.setCity(getStringOrDefault(rec[4]));
			md.setAuth3DS(getStringOrDefault(rec[5]));
			md.setMid(getStringOrDefault(rec[6]));
			md.setMotoMid(getStringOrDefault(rec[7]));
			md.setEzypassMid(getStringOrDefault(rec[8]));
			md.setEzyrecMid(getStringOrDefault(rec[9]));
			md.setEzywayMid(getStringOrDefault(rec[10]));
			md.setUmMid(getStringOrDefault(rec[11]));
			md.setUmMotoMid(getStringOrDefault(rec[12]));
			md.setUmEzywayMid(getStringOrDefault(rec[13]));
			md.setUmEzyrecMid(getStringOrDefault(rec[14]));
			md.setId(getLongOrDefault(rec[15]));
			md.setType(getStringOrDefault(rec[16]));
			md.setMerchantType(getStringOrDefault(rec[17]));
			md.setSplitMid(getStringOrDefault(rec[18]));
			md.setBoostMid(getStringOrDefault(rec[19]));
			md.setGrabMid(getStringOrDefault(rec[20]));
			md.setFpxMid(getStringOrDefault(rec[21]));
			md.setTngMid(getStringOrDefault(rec[22]));
			md.setShoppyMid(getStringOrDefault(rec[23]));
			md.setBnplMid(getStringOrDefault(rec[24]));
			md.setManualSettlement(getStringOrDefault(rec[25]));
			md.setFpxHostName(getStringOrDefault(rec[26]));
			md.setFiuuMid(getStringOrDefault(rec[27]));

			fss.add(md);
		}

		paginationBean.setItemList(fss);

//		for (Object[] rec : resultSet) {
//			MerchantDTO md = new MerchantDTO();
//
//			String rd = null;
//			if (rec[0] != null) {
//				try {
//					rd = new SimpleDateFormat("dd/MM/yyyy")
//							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				md.setCreatedBy(rd);
//			}
//
//			if (rec[1] != null) {
//				md.setBusinessName(rec[1].toString());
//			}
//
//			// status if (rec[2] != null) { fs.setState(rec[2].toString()); }
//
//			String rd1 = null;
//			if (rec[3] != null) {
//				try {
//					rd1 = new SimpleDateFormat("dd/MM/yyyy")
//							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[3].toString()));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				md.setModifiedBy(rd1);
//			} else {
//				md.setModifiedBy("");
//			}
//
//			if (rec[4] != null) {
//				md.setCity(rec[4].toString());
//			} else {
//				md.setCity("");
//			}
//
//			if (rec[5] != null) {
//				md.setAuth3DS(rec[5].toString());
//			}
//
//			if (rec[6] != null) {
//				md.setMid(rec[6].toString());
//			} else {
//				md.setMid("");
//			}
//
//			if (rec[7] != null) {
//
//				md.setMotoMid(rec[7].toString());
//
//			} else {
//				md.setMotoMid("");
//
//			}
//
//			if (rec[8] != null) {
//				md.setEzypassMid(rec[8].toString());
//			} else {
//				md.setEzypassMid("");
//			}
//
//			if (rec[9] != null) {
//				md.setEzyrecMid(rec[9].toString());
//			} else {
//				md.setEzyrecMid("");
//			}
//
//			if (rec[10] != null) {
//				md.setEzywayMid(rec[10].toString());
//			} else {
//				md.setEzywayMid("");
//			}
//
//			if (rec[11] != null) {
//				md.setUmMid(rec[11].toString());
//			} else {
//				md.setUmMid("");
//			}
//
//			if (rec[12] != null) {
//
//				md.setUmMotoMid(rec[12].toString());
//			}
//
//			else {
//				md.setUmMotoMid("");
//
//			}
//
//			if (rec[13] != null) {
//				md.setUmEzywayMid(rec[13].toString());
//			} else {
//				md.setUmEzywayMid("");
//			}
//
//			if (rec[14] != null) {
//				md.setUmEzyrecMid(rec[14].toString());
//			} else {
//				md.setUmEzyrecMid("");
//			}
//
//			if (rec[15] != null) {
//
//				md.setId(Long.valueOf(rec[15].toString()));
//			}
//
//			if (rec[18] != null) {
//
//				md.setSplitMid(rec[18].toString());
//			} else {
//				md.setSplitMid("");
//			}
//
//			// fs.setMid(mi);
//
//			if (rec[16] != null) {
//
//				md.setType(rec[16].toString());
//			} else {
//				md.setType("");
//			}
//
//			if (rec[17] != null) {
//
//				md.setMerchantType(rec[17].toString());
//			}
//
//			if (rec[19] != null) {
//				md.setBoostMid(rec[19].toString());
//			} else {
//				md.setBoostMid("");
//			}
//			if (rec[20] != null) {
//				md.setGrabMid(rec[20].toString());
//			} else {
//				md.setGrabMid("");
//			}
//			if (rec[21] != null) {
//				md.setFpxMid(rec[21].toString());
//			} else {
//				md.setFpxMid("");
//			}
//
//			if (rec[22] != null) {
//				md.setTngMid(rec[22].toString());
//			} else {
//				md.setTngMid("");
//			}
//			if (rec[23] != null) {
//				md.setShoppyMid(rec[23].toString());
//			} else {
//				md.setShoppyMid("");
//			}
//			if (rec[24] != null) {
//				md.setBnplMid(rec[24].toString());
//			} else {
//				md.setBnplMid("");
//			}
//
//			if (rec[25] != null) {
//				md.setManualSettlement(rec[25].toString());
//			} else {
//				md.setManualSettlement("");
//			}
//			if (rec[26] != null) {
//				md.setFpxHostName(rec[26].toString());
//			} else {
//				md.setFpxHostName("");
//			}
//
//			fss.add(md);
//		}
//
//		paginationBean.setItemList(fss);
	}

	private static String getStringOrDefault(Object value) {
		return value != null ? value.toString() : "";
	}

	private static Long getLongOrDefault(Object value) {
		return value != null ? Long.valueOf(value.toString()) : null;
	}

	private static String formatDate(Object value) {
		if (value == null)
			return "";
		try {
			return new SimpleDateFormat("dd/MM/yyyy")
					.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(value.toString()));
		} catch (ParseException e) {
			logger.error("Error formatting date: ", e);
			return "";
		}
	}

	@Override
	@Transactional(readOnly = false)
	public int updateTrackDetails(MobiliteTrackDetails det) {
		logger.info("updateTrackDetails");

		Session session = sessionFactory.getCurrentSession();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String time = timestamp.toString();

		String sql = "update UMEcomTxnResponse set  trackNo = :trackNo , courierName = :courierName, sentDate= :sentDate, updatedDate= :time "
				+ "where mobiLiteTid= :mobiLiteTid";

		logger.info(" Query :" + sql);

		Query insertQuery = session.createQuery(sql);
		insertQuery.setString("trackNo", det.getTrackNumber());
		insertQuery.setString("courierName", det.getCourierName());
		insertQuery.setString("sentDate", det.getSentDate());
		insertQuery.setString("mobiLiteTid", det.getTid());
		insertQuery.setString("time", time);

		int a = insertQuery.executeUpdate();

		return a;

	}

	@Override
	@Transactional(readOnly = false)
	public int updateEzylinkssTrackDetails(MobiliteTrackDetails det) {
		logger.info("updateEzylinkssTrackDetails");

		Session session = sessionFactory.getCurrentSession();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String time = timestamp.toString();

		String sql = "update UMEcomTxnResponse set  trackNo = :trackNo , courierName = :courierName, sentDate= :sentDate, updatedDate= :time "
				+ "where f263_MRN = :mrn";

		logger.info(" Query :" + sql);

		Query insertQuery = session.createQuery(sql);
		insertQuery.setString("trackNo", det.getTrackNumber());
		insertQuery.setString("courierName", det.getCourierName());
		insertQuery.setString("sentDate", det.getSentDate());
		insertQuery.setString("mrn", det.getMrn());
		insertQuery.setString("time", time);

		int a = insertQuery.executeUpdate();

		return a;

	}

	@Override
	public MobileOTP checkOTP(String mno, String emailId) {

		if (mno == null || mno.trim().isEmpty()) {
			mno = "No-Mobile ";
		}

		if (emailId == null || emailId.trim().isEmpty()) {
			emailId = "No-Email";
		}

		logger.info("mno:::::::::" + mno);
		logger.info("emailId:::::" + emailId);

		Criterion mob = Restrictions.like("mobileNo", mno);
		Criterion email = Restrictions.like("deviceToken", emailId);
		LogicalExpression orExp = Restrictions.or(mob, email);
		MobileOTP mobileUser = (MobileOTP) sessionFactory.getCurrentSession().createCriteria(MobileOTP.class).add(orExp)
				.setMaxResults(1).uniqueResult();

		return mobileUser;
	}

	@Transactional(readOnly = false)
	@Override
	public void updateMobileOTP(String seq, String key) {

		String hql = null;
		Query query = null;

		logger.info("Update MobileOTP: " + seq);
		logger.info("Update Key: " + key);

		hql = "UPDATE MobileOTP set optData = :optData, deviceToken = :deviceToken, deviceType = :deviceType WHERE mobileNo= :mobileNo";
		query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("optData", seq);
		query.setString("deviceToken", key);
		query.setString("deviceType", "REG");
		query.setString("mobileNo", key);

		int result = query.executeUpdate();
		logger.info("Effected MerchantDetails Points : " + result);

	}

	// rksubmerchant
	@Override
	@Transactional(readOnly = false)
	public void listsubMerchantUserByMid(final PaginationBean<Merchant> paginationBean, Timestamp fromDate,
			Timestamp toDate, Long merchantid) {
		logger.info("listMerchantUserByMid::");

		logger.info("Timestamp fromDate::::::::" + fromDate);
		logger.info("Timestamp ToDate::::::::::" + toDate);

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;

		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		// Date date = new Date();

		int year = calendar.getWeekYear();
		// int year=2017;
		long mon = date.getMonth() + 1;
		int day = date.getDate() + 1;
		int daybefore = date.getDate();
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

		String mmid = null;

		// New changes Master merchant
		logger.info("Merchant ID :" + merchantid);
		String merchantId = String.valueOf(merchantid);
		MasterMerchant mm = (MasterMerchant) getSessionFactory().createCriteria(MasterMerchant.class)
				.add(Restrictions.eq("merchantId", merchantId)).setMaxResults(1).uniqueResult();

		if (mm != null) {
			mmid = mm.getBusinessName();
			logger.info("Business Name :" + mmid);
		} else {
			logger.info("Master merchant table data is invalid");
			mmid = "null";
		}

//		if (merchantid == 3824) {
//			logger.info("DEPANSUM : " + merchantid);
//			mmid = "DEPANSUM MALAYSIA SDN BHD";
//
//		} else if (merchantid == 4190) {
//			logger.info("MONETIX : " + merchantid);
//			mmid = "MNTX";
//
//		} else if (merchantid == 4272) {
//			logger.info("ZOTAPAY : " + merchantid);
//			mmid = "ZTP";
//
//		} else if (merchantid == 4347) {
//			logger.info("ECOMMPAY : " + merchantid);
//			mmid = "ECOMMPAY";
//
//		} else {
//			logger.info("DEFAULT DEPANSUM : " + merchantid);
//			mmid = "DEPANSUM MALAYSIA SDN BHD";
//
//		}

		logger.info("MM_ID : " + mmid);

		if ((fromDate != null && toDate != null)) {

			sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
					+ "mi.SUB_MERCHANT_MID , m.ID from MERCHANT m  "
					+ "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID = :mmid and m.ACTIVATE_DATE BETWEEN '"
					+ fromDate + "' and '" + toDate + "'  ";

		} else {
			sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
					+ "mi.SUB_MERCHANT_MID , m.ID from MERCHANT m  "
					+ "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID = :mmid and m.ACTIVATE_DATE BETWEEN '"
					+ fromDateToSearch + "' and '" + toDateToSearch + "'  ";

		}
		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mmid", mmid);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			MID mi = new MID();

			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setCreatedBy(rd);
			}

			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setEmail(rec[2].toString());
			}

			if (rec[3] != null) {
				fs.setCity(rec[3].toString());
			} else {
				fs.setCity("");
			}

			if (rec[4] != null) {
				fs.setState(rec[4].toString());
			}

			if (rec[5] != null) {
				fs.setMobiId(rec[5].toString());
			} else {
				fs.setMobiId("");
			}

			if (rec[6] != null) {

				fs.setSalutation(rec[6].toString());

			} else {
				fs.setSalutation("");
			}

			fss.add(fs);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	@Override
	@Transactional(readOnly = false)
	public void listsubMerchant(final PaginationBean<Merchant> paginationBean, String type) {

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;

		sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
				+ "mi.SUB_MERCHANT_MID ,m.ID ,m.MM_ID from MERCHANT m  "
				+ "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID LIKE '" + type
				+ "%' order by m.ACTIVATE_DATE desc ";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);

		// Dynamic changes for pagination count

		String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(DynamicPage);

		int pageNumFromJsp = paginationBean.getCurrPage();
		logger.info("Page Number:" + pageNumFromJsp);
		logger.info("Max Count for Records:" + pageSize);

		sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
		sqlQuery.setMaxResults(pageSize);

//		sqlQuery.setString("type", type);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			MID mi = new MID();

			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setCreatedBy(rd);
			}

			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setEmail(rec[2].toString());
			}

			if (rec[3] != null) {
				fs.setCity(rec[3].toString());
			} else {
				fs.setCity("");
			}

			if (rec[4] != null) {
				fs.setState(rec[4].toString());
			}

			if (rec[5] != null) {
				fs.setMobiId(rec[5].toString());
			} else {
				fs.setMobiId("");
			}

			if (rec[6] != null) {

				fs.setSalutation(rec[6].toString());

			} else {
				fs.setSalutation("");
			}

			if (rec[7] != null) {

				fs.setMmId(rec[7].toString());

			} else {
				fs.setMmId("");
			}

			fss.add(fs);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	@Override
	@Transactional(readOnly = false)
	public void listsubMerchant1(final PaginationBean<Merchant> paginationBean, String type) {

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;
		List<Object[]> resultSet = null;

		try {
			sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
					+ "mi.SUB_MERCHANT_MID ,m.ID ,m.MM_ID from mobiversa.MERCHANT m  "
					+ "INNER JOIN mobiversa.MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID LIKE '" + type
					+ "%' order by m.ACTIVATE_DATE desc ";

			logger.info("Query : " + sql);

			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			int querySize = sqlQuery.list().size();
			logger.info("Total query size is :" + querySize);
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);

			resultSet = sqlQuery.list();

			for (Object[] rec : resultSet) {
				Merchant fs = new Merchant();
				MID mi = new MID();

				String rd = getStringValue(rec[0]);
				if (!rd.isEmpty()) {
					try {
						rd = new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rd));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				fs.setCreatedBy(rd);
				fs.setBusinessName(getStringValue(rec[1]));
				fs.setEmail(getStringValue(rec[2]));
				fs.setCity(getStringValue(rec[3]));
				fs.setState(getStringValue(rec[4]));
				fs.setMobiId(getStringValue(rec[5]));
				fs.setSalutation(getStringValue(rec[6]));
				fs.setMmId(getStringValue(rec[7]));
				fss.add(fs);
			}
			logger.info("fss : " + fss);
			paginationBean.setItemList(fss);
		} catch (Exception e) {
			logger.warn("Exception in (listsubMerchant1 " + e.getMessage(), e);
			paginationBean.setItemList(new ArrayList<>());
		}

	}

	// old code karuppusamy

//	public void subMerchantListByAdmin(PaginationBean<ForSettlement> paginationBean, String fromDate, String toDate,
//			Merchant findMerchant, String type, List<MobileUser> mobileuser) {
//
//		logger.info("inside TransactionDetailsbyAdmin " + " from date: " + fromDate + "toDate: " + toDate);
//
//		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
//		String sql = null;
//		Query sqlQuery = null;
//
//		String ummid = "";
//		String umEzywayMid = "";
//		String umMotoMid = "";
//		String umEzyRecMid = "";
//		String boostMid = "";
//		String ezypassMid = "";
//		String tngMid = "";
//		String sppMid = "";
//
//		String mid = "";
//		String ezywayMid = "";
//		String motoMid = "";
//
//		String onlineGpay = "";
//		String gpayTid = "";
//		String fpxMid = "";
//		String splitMid = "";
//
//		logger.info("mobile User Size :" + mobileuser.size());
//		if (mobileuser.size() != 0) {
//			if (mobileuser.get(0).getOnlineGpay() != null) {
//				onlineGpay = mobileuser.get(0).getOnlineGpay();
//			}
//			if (mobileuser.get(0).getGpayTid() != null) {
//				gpayTid = mobileuser.get(0).getGpayTid();
//			}
//		}
//
//		if (findMerchant.getMid().getEzywayMid() != null) {
//			ezywayMid = findMerchant.getMid().getEzywayMid();
//		}
//		if (findMerchant.getMid().getMid() != null) {
//			mid = findMerchant.getMid().getMid();
//		}
//		if (findMerchant.getMid().getMotoMid() != null) {
//			motoMid = findMerchant.getMid().getMotoMid();
//		}
//
//		if (findMerchant.getMid().getUmEzywayMid() != null) {
//			umEzywayMid = findMerchant.getMid().getUmEzywayMid();
//		}
//		if (findMerchant.getMid().getUmMotoMid() != null) {
//			umMotoMid = findMerchant.getMid().getUmMotoMid();
//		}
//		if (findMerchant.getMid().getUmEzyrecMid() != null) {
//			umEzyRecMid = findMerchant.getMid().getEzyrecMid();
//		}
//		if (findMerchant.getMid().getUmMid() != null) {
//			ummid = findMerchant.getMid().getUmMid();
//		}
//		if (findMerchant.getMid().getBoostMid() != null) {
//			boostMid = findMerchant.getMid().getBoostMid();
//		}
//		if (findMerchant.getMid().getUmEzypassMid() != null) {
//			ezypassMid = findMerchant.getMid().getUmEzypassMid();
//		}
//		if (findMerchant.getMid().getTngMid() != null) {
//			tngMid = findMerchant.getMid().getTngMid();
//		}
//		if (findMerchant.getMid().getShoppyMid() != null) {
//			sppMid = findMerchant.getMid().getShoppyMid();
//		}
//		if (findMerchant.getMid().getFpxMid() != null) {
//			fpxMid = findMerchant.getMid().getFpxMid();
//		}
//		if (findMerchant.getMid().getSplitMid() != null) {
//			splitMid = findMerchant.getMid().getSplitMid();
//		}
//
//		logger.info(" umEzywayMid :: " + umEzywayMid);
//		logger.info(" umMotoMid :: " + umMotoMid);
//		logger.info(" umEzyRecMid :: " + umEzyRecMid);
//		logger.info(" ummid :: " + ummid);
//		logger.info(" boostMid :: " + boostMid);
//		logger.info(" ezypassMid :: " + ezypassMid);
//		logger.info(" tngMid :: " + tngMid);
//		logger.info(" sppMid :: " + sppMid);
//		logger.info(" fpxMid :: " + fpxMid);
//
//		logger.info(" boostMid :: " + boostMid);
//		logger.info(" gpayTid :: " + gpayTid);
//		logger.info(" onlineGpay :: " + onlineGpay);
//
//		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty()) && type == null) {
//
//			logger.info(" type :: " + type);
//
//			sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
//					+ "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
//					+ "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
//					+ "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
//					+ "INNER JOIN MERCHANT  mt on m.MERCHANT_FK = mt.ID "
//					+ "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
//					+ "e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc ) " + "UNION "
//					+ "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
//					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//					+ "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + "UNION "
//					+ "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
//					+ "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
//					+ "from FPX_TRANSACTION f INNER JOIN MID m on f.MID = m.FPX_MID "
//					+ "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
//					+ "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  f.TIME_STAMP BETWEEN "
//					+ " :fromDate and :toDate order by f.TIME_STAMP desc ) " + "UNION "
//					+ "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
//					+ "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
//					+ "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
//					+ "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
//					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + " UNION "
//					+ "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID INNER JOIN "
//					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS','H') AND w.MID IN (:tngMid) AND w.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
//					+ " UNION "
//					+ "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID = m.SHOPPY_MID INNER JOIN "
//					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND w.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
//					+ " UNION "
//					+ " ( select a.MID AS MID, a.AMOUNT AS AMOUNT ,a.TID AS TID, a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE ,a.TRX_ID AS RRN ,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID ,'' AS AUTH,a.TXN_TYPE AS TXN_TYPE,t.CARD_HOLDER_NAME AS CARD_HOLDER_NAME ,t.MASKED_PAN  AS MASKED_PAN , a.STAN AS STAN,a.TIME AS TIME from FOR_SETTLEMENT a INNER JOIN TRANSACTION_REQUEST t on a.TRX_ID=t.TXN_ID  INNER JOIN MID m on a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN MERCHANT f "
//					+ " ON f.MID_FK=m.ID where a.STATUS in ('A','S','CT','C','R','CV','P') AND a.`MID` IN (:mid,:ezywayMid,:motoMid) AND a.TIME_STAMP between :fromDate and :toDate order by a.TIME_STAMP desc ) ";
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//
//			sqlQuery.setString("ummid", ummid);
//			sqlQuery.setString("umEzywayMid", umEzywayMid);
//			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//			sqlQuery.setString("umMotoMid", umMotoMid);
//			sqlQuery.setString("ezypassMid", ezypassMid);
//			sqlQuery.setString("boostMid", boostMid);
//			sqlQuery.setString("fpxMid", fpxMid);
//			sqlQuery.setString("splitMid", splitMid);
//			sqlQuery.setString("gpayTid", gpayTid);
//			sqlQuery.setString("onlineGpay", onlineGpay);
//			sqlQuery.setString("tngMid", tngMid);
//			sqlQuery.setString("sppMid", sppMid);
//
//			sqlQuery.setString("mid", mid);
//			sqlQuery.setString("ezywayMid", ezywayMid);
//			sqlQuery.setString("motoMid", motoMid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		}
//
//		else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//				&& type.equalsIgnoreCase("cards")) {
//
//			logger.info(" Cards :: " + type);
//			sql = "select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
//					+ " e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
//					+ " e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
//					+ " INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID  "
//					+ " INNER JOIN MERCHANT mt on m.MERCHANT_FK = mt.ID "
//					+ " WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND e.H001_MTI in ('0290','0210') AND "
//					+ " e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc  ";
//
////			sql ="select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
////					+ "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
////					+ "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
////					+ "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
////					+ "INNER JOIN MERCHANT  mt on m.MERCHANT_FK = mt.ID "
////					+ "WHERE e.STATUS in ('A','S','C') AND  e.H001_MTI in ('0290','0210') AND "
////					+ "e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc limit 100 " ;
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//
//			sqlQuery.setString("umEzywayMid", umEzywayMid);
//			sqlQuery.setString("umMotoMid", umMotoMid);
//			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//			sqlQuery.setString("ummid", ummid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//				&& type.equalsIgnoreCase("boost")) {
//			logger.info(" boost :: " + type);
//
//			sql = "select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID = m.BOOST_MID  "
//					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//					+ "WHERE a.STATUS in ('BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";
//
////			sql = "select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
////					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
////					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
////					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
////					+ "WHERE a.STATUS in ('BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
////					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//
//			sqlQuery.setString("umEzywayMid", umEzywayMid);
//			sqlQuery.setString("umMotoMid", umMotoMid);
//			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//			sqlQuery.setString("ummid", ummid);
//			sqlQuery.setString("boostMid", boostMid);
//			sqlQuery.setString("ezypassMid", ezypassMid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		}
//
//		else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//				&& type.equalsIgnoreCase("grab")) {
//
//			logger.info(" Grab :: " + type);
//
//			sql = "select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
//					+ "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
//					+ "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
//					+ "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
//					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//
//			sqlQuery.setString("onlineGpay", onlineGpay);
//			sqlQuery.setString("gpayTid", gpayTid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//				&& type.equalsIgnoreCase("tng")) {
//
//			logger.info(" tng :: " + type);
//
//			sql = "SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,"
//					+ " w.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH,"
//					+ " w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON "
//					+ " w.MID =m.TNG_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS', 'H') AND w.MID IN (:tngMid) AND w.TIME_STAMP BETWEEN :fromDate AND :toDate "
//					+ " ORDER BY w.TIME_STAMP DESC ";
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//
//			sqlQuery.setString("tngMid", tngMid);
////			sqlQuery.setString("ummid", ummid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//				&& type.equalsIgnoreCase("spp")) {
//
//			logger.info(" Spp :: " + type);
//
//			sql = "SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,"
//					+ " w.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH,"
//					+ " w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON "
//					+ " w.MID =m.SHOPPY_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND w.TIME_STAMP BETWEEN :fromDate AND :toDate "
//					+ " ORDER BY w.TIME_STAMP DESC ";
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//			sqlQuery.setString("sppMid", sppMid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//				&& type.equalsIgnoreCase("fpx")) {
//
//			logger.info(" FPX :: " + type);
//
////			sql = "select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
////					+ "'' AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
////					+ "from FPX_TRANSACTION f INNER JOIN MID m on f.MID=m.UM_MOTO_MID OR f.MID=m.UM_EZYWAY_MID OR f.MID=m.UM_EZYREC_MID OR f.MID=m.SPLIT_MID OR f.MID = m.FPX_MID "
////					+ "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "	
////					+ "WHERE AND f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND f.TIME_STAMP BETWEEN "
////					+ " :fromDate and :toDate order by f.TIME_STAMP desc ";
//
//			sql = " SELECT f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE,f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME,f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,"
//					+ " '' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,f.TX_TIME AS TIME FROM FPX_TRANSACTION f INNER JOIN MID m ON f.MID = m.FPX_MID "
//					+ "INNER JOIN MERCHANT mr ON mr.MID_FK = m.ID WHERE f.STATUS IN ('A', 'S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND f.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY f.TIME_STAMP DESC ";
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//
//			sqlQuery.setString("ummid", ummid);
//			sqlQuery.setString("umEzywayMid", umEzywayMid);
//			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//			sqlQuery.setString("umMotoMid", umMotoMid);
//			sqlQuery.setString("splitMid", splitMid);
//			sqlQuery.setString("fpxMid", fpxMid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		} else {
//
//			logger.info(" All :: " + type);
//
//			sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
//					+ "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
//					+ "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
//					+ "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
//					+ "INNER JOIN MERCHANT  mt on m.MERCHANT_FK = mt.ID "
//					+ "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
//					+ "e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc ) " + "UNION "
//					+ "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
//					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//					+ "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + "UNION "
//					+ "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
//					+ "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
//					+ "from FPX_TRANSACTION f INNER JOIN MID m on f.MID = m.FPX_MID "
//					+ "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
//					+ "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  f.TIME_STAMP BETWEEN "
//					+ " :fromDate and :toDate order by f.TIME_STAMP desc ) " + "UNION "
//					+ "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
//					+ "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
//					+ "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
//					+ "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
//					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + " UNION "
//					+ "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID INNER JOIN "
//					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS','H') AND w.MID IN (:tngMid) AND w.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
//					+ " UNION "
//					+ "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID = m.SHOPPY_MID INNER JOIN "
//					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND w.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
//					+ " UNION "
//					+ " ( select a.MID AS MID, a.AMOUNT AS AMOUNT ,a.TID AS TID, a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE ,a.TRX_ID AS RRN ,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID ,'' AS AUTH,a.TXN_TYPE AS TXN_TYPE,t.CARD_HOLDER_NAME AS CARD_HOLDER_NAME ,t.MASKED_PAN  AS MASKED_PAN , a.STAN AS STAN,a.TIME AS TIME from FOR_SETTLEMENT a INNER JOIN TRANSACTION_REQUEST t on a.TRX_ID=t.TXN_ID  INNER JOIN MID m on a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN MERCHANT f "
//					+ " ON f.MID_FK=m.ID where a.STATUS in ('A','S','CT','C','R','CV','P') AND a.`MID` IN (:mid,:ezywayMid,:motoMid) AND a.TIME_STAMP between :fromDate and :toDate order by a.TIME_STAMP desc ) ";
//
////			sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
////					+ "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
////					+ "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
////					+ "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
////					+ "INNER JOIN MERCHANT mt on m.MERCHANT_FK = mt.ID "
////					+ "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
////					+ "e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc ) " + "UNION "
////					+ "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
////					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
////					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
////					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
////					+ "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
////					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + "UNION "
////					+ "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
////					+ "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
////					+ "from FPX_TRANSACTION f INNER JOIN MID m on f.MID=m.UM_MOTO_MID OR f.MID=m.UM_EZYWAY_MID OR f.MID=m.UM_EZYREC_MID OR f.MID=m.SPLIT_MID OR f.MID = m.FPX_MID "
////					+ "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
////					+ "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  f.TIME_STAMP BETWEEN "
////					+ " :fromDate and :toDate order by f.TIME_STAMP desc ) " + "UNION "
////					+ "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
////					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
////					+ "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
////					+ "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
////					+ "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
////					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " +" UNION "+"(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
////					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID OR m.SHOPPY_MID INNER JOIN "
////					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS', 'SPA','SPS','H') AND w.MID IN (:tngMid,:sppMid) AND w.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )";
//
//			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//			sqlQuery.setString("fromDate", fromDate);
//			sqlQuery.setString("toDate", toDate);
//
//			sqlQuery.setString("ummid", ummid);
//			sqlQuery.setString("umEzywayMid", umEzywayMid);
//			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//			sqlQuery.setString("umMotoMid", umMotoMid);
//			sqlQuery.setString("ezypassMid", ezypassMid);
//			sqlQuery.setString("boostMid", boostMid);
//			sqlQuery.setString("fpxMid", fpxMid);
//			sqlQuery.setString("splitMid", splitMid);
//			sqlQuery.setString("gpayTid", gpayTid);
//			sqlQuery.setString("onlineGpay", onlineGpay);
//			sqlQuery.setString("tngMid", tngMid);
//			sqlQuery.setString("sppMid", sppMid);
//
//			sqlQuery.setString("mid", mid);
//			sqlQuery.setString("ezywayMid", ezywayMid);
//			sqlQuery.setString("motoMid", motoMid);
//
//			// String type1 =paginationBean.setSelectType(type);
//
//			String FromDate = paginationBean.setDateFromBackend(fromDate);
//			String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//
//			int pageSize = Integer.parseInt(DynamicPage);
//
//			int pageNumFromJsp = paginationBean.getCurrPage();
//
//			logger.info("Page Number:" + pageNumFromJsp);
//
//			logger.info("Max Count for Records:" + pageSize);
//			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//
//			sqlQuery.setMaxResults(pageSize);
//
//		}
//
//		logger.info("Query : " + sql);
//
//		@SuppressWarnings("unchecked")
//		List<Object[]> resultSet = sqlQuery.list();
//		logger.info("Number of records in the List : " + resultSet.size());
//		for (Object[] rec : resultSet) {
//
//			ForSettlement fs = new ForSettlement();
//
//			if (rec[0] != null) {
//				// MID
//				fs.setMid(rec[0].toString());
//			} else {
//				fs.setMid("");
//			}
//			if (rec[1] != null || rec[1] != "") {
//
//				if (rec[1].toString().contains(".")) {
//					fs.setAmount(rec[1].toString());
//				} else {
//
//					double amount = 0;
//					amount = Double.parseDouble(rec[1].toString()) / 100;
//					String pattern = "#,##0.00";
//					DecimalFormat myFormatter = new DecimalFormat(pattern);
//					String output = myFormatter.format(amount);
//					fs.setAmount(output);
//				}
//
//			} else {
//
//				fs.setAmount("0.00");
//			}
//
//			if (rec[2] != null) {
//				// TID
//				fs.setTid(rec[2].toString());
//			} else {
//				fs.setTid("");
//
//			}
//
//			if (rec[3] != null) {
//
//				// STATUS
//				if (rec[3].toString().equals("CT")) {
//					fs.setStatus("CASH SALE");
//				}
//				if (rec[3].toString().equals("CV")) {
//					fs.setStatus("CASH CANCELLED");
//				}
//				if (rec[4].toString().equals("R")) {
//					fs.setStatus("REVERSAL");
//				}
//				if (rec[3].toString().equals("S")) {
//					fs.setStatus("SETTLED");
//				}
//				if (rec[3].toString().equals("P")) {
//					fs.setStatus("PENDING");
//				}
//				if (rec[3].toString().equals("A")) {
//					fs.setStatus("NOT SETTLED");
//				}
//				if (rec[3].toString().equals("C")) {
//					fs.setStatus("CANCELLED");
//				}
//				if (rec[3].toString().equals("BP")) {
//					fs.setStatus("BOOST PENDING");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("BPC")) {
//					fs.setStatus("BOOST CANCELLED");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("BPS")) {
//					fs.setStatus("BOOST SETTLED");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("BPA")) {
//					fs.setStatus("BOOST PAYMENT");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("GPS")) {
//					fs.setStatus("GRABPAY COMPLETED");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("GRF")) {
//					fs.setStatus("GRABPAY REFUND");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("GPT")) {
//					fs.setStatus("GRABPAY SETTLED");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("GBC")) {
//					fs.setStatus("GRABPAY CANCELLED");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("SPS")) {
//					fs.setStatus("SHOPEE PAYMENT");
//					// logger.info("check status: "+fs.getStatus());
//				}
//				if (rec[3].toString().equals("TNG")) {
//					fs.setStatus("TNG PAYMENT");
//					// logger.info("check status: "+fs.getStatus());
//				}
//			}
//			if (rec[4] != null) {
//				fs.setAidResponse(rec[4].toString());
//			} else {
//				fs.setAidResponse("");
//			}
//			if (rec[5] != null) {
//				fs.setRrn(rec[5].toString());
//			} else {
//				fs.setRrn("");
//			}
//
//			// TIME STAMP --- FIELDS
//			if (rec[6] != null) {
//				// a.time_stamp
//				// fs.setTimeStamp(rec[1].toString());
//				String rd = null;
//				try {
//					rd = new SimpleDateFormat("dd/MM/yyyy")
//							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[6].toString()));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				fs.setDate(rd);
//			}
//
//			if (rec[7] != null) {
//				// merchant name
//				fs.setNumOfSale(rec[7].toString());
//			} else {
//				fs.setNumOfSale("");
//			}
//			if (rec[8] != null) {
//				// reference
//				fs.setInvoiceId(rec[8].toString());
//			} else {
//				fs.setInvoiceId("");
//			}
//			if (rec[11] != null) {
//
//				fs.setNumOfRefund(rec[11].toString());
//			} else {
//
//				fs.setNumOfRefund("");
//			}
//			if (rec[12] != null) {
//
//				fs.setPan(rec[12].toString());
//			} else {
//				fs.setPan("");
//			}
//			if (rec[13] != null) {
//
//				fs.setStan(rec[13].toString());
//			} else {
//				fs.setStan("");
//			}
//
//			// TIME -- FIELDS
//
//			if (rec[14] != null) {
//
//				try {
//					String rt = new SimpleDateFormat("HH:mm:ss")
//							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[6].toString()));
//
//					fs.setTime(rt);
//
//				} catch (ParseException e) {
//				}
//
//			}
//
//			if (rec[10] != null) {
//				if (rec[9] != null) {
//
//					// logger.info("auth3ds:++: " + rec[9].toString());
//					// logger.info("setTxnType:++: " + rec[10].toString());
//					if ((rec[9].toString().equals("Yes")) && (rec[10].toString().equals("EZYMOTO"))) {
//
//						logger.info("type:++: " + rec[10].toString());
//						fs.setTxnType("EZYLINK");
//					}
//
//					if ((rec[10].toString().equals("BOOST"))) {
//
//						logger.info("type:++: " + rec[10].toString());
//						fs.setTxnType("BOOST");
//					}
//					if ((rec[10].toString().equals("FPX"))) {
//
//						logger.info("type:++: " + rec[10].toString());
//						fs.setTxnType("FPX");
//					}
//					if ((rec[10].toString().equals("GRABPAY"))) {
//
//						logger.info("type:++: " + rec[10].toString());
//						fs.setTxnType("GRABPAY");
//					}
//					if ((rec[10].toString().equals("TNG"))) {
//						fs.setTxnType("TNG");
//					}
//					if ((rec[10].toString().equals("SPP"))) {
//						fs.setTxnType("SPP");
//					} else {
//						logger.info("type:++: " + rec[10].toString());
//						fs.setTxnType(rec[10].toString());
//					}
//
//				} else {
//					logger.info("type:++: " + rec[10].toString());
//					fs.setTxnType(rec[10].toString());
//				}
//
//			} else {
//				fs.setTxnType("");
//			}
//
//			fss.add(fs);
//		}
//		paginationBean.setItemList(fss);
//
//		// paginationBean.setTotalRowCount(fss.size());
//	}

	// new code

//	
//	@Override
//	@Transactional(readOnly = false)
//
//    public void subMerchantListByAdmin(PaginationBean<ForSettlement> paginationBean, String fromDate, String toDate,
//          Merchant findMerchant, String type, List<MobileUser> mobileuser) {
//
//       logger.info("inside TransactionDetailsbyAdmin " + " from date: " + fromDate + "toDate: " + toDate);
//
//       ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
//       String sql = null;
//       Query sqlQuery = null;
//       List<Object[]> resultSet=null;
//       int querySize = 0;
//
//       String ummid = "";
//       String umEzywayMid = "";
//       String umMotoMid = "";
//       String umEzyRecMid = "";
//       String boostMid = "";
//       String ezypassMid = "";
//       String tngMid = "";
//       String sppMid = "";
//
//       String mid = "";
//       String ezywayMid = "";
//       String motoMid = "";
//
//       String onlineGpay = "";
//       String gpayTid = "";
//       String fpxMid = "";
//       String splitMid = "";
//
//       logger.info("mobile User Size :" + mobileuser.size());
//       if (mobileuser.size() != 0) {
//          if (mobileuser.get(0).getOnlineGpay() != null) {
//             onlineGpay = mobileuser.get(0).getOnlineGpay();
//          }
//          if (mobileuser.get(0).getGpayTid() != null) {
//             gpayTid = mobileuser.get(0).getGpayTid();
//          }
//       }
//
//       if (findMerchant.getMid().getEzywayMid() != null) {
//          ezywayMid = findMerchant.getMid().getEzywayMid();
//       }
//       if (findMerchant.getMid().getMid() != null) {
//          mid = findMerchant.getMid().getMid();
//       }
//       if (findMerchant.getMid().getMotoMid() != null) {
//          motoMid = findMerchant.getMid().getMotoMid();
//       }
//
//       if (findMerchant.getMid().getUmEzywayMid() != null) {
//          umEzywayMid = findMerchant.getMid().getUmEzywayMid();
//       }
//       if (findMerchant.getMid().getUmMotoMid() != null) {
//          umMotoMid = findMerchant.getMid().getUmMotoMid();
//       }
//       if (findMerchant.getMid().getUmEzyrecMid() != null) {
//          umEzyRecMid = findMerchant.getMid().getEzyrecMid();
//       }
//       if (findMerchant.getMid().getUmMid() != null) {
//          ummid = findMerchant.getMid().getUmMid();
//       }
//       if (findMerchant.getMid().getBoostMid() != null) {
//          boostMid = findMerchant.getMid().getBoostMid();
//       }
//       if (findMerchant.getMid().getUmEzypassMid() != null) {
//          ezypassMid = findMerchant.getMid().getUmEzypassMid();
//       }
//       if (findMerchant.getMid().getTngMid() != null) {
//          tngMid = findMerchant.getMid().getTngMid();
//       }
//       if (findMerchant.getMid().getShoppyMid() != null) {
//          sppMid = findMerchant.getMid().getShoppyMid();
//       }
//       if (findMerchant.getMid().getFpxMid() != null) {
//          fpxMid = findMerchant.getMid().getFpxMid();
//       }
//       if (findMerchant.getMid().getSplitMid() != null) {
//          splitMid = findMerchant.getMid().getSplitMid();
//       }
//
//       logger.info(" umEzywayMid :: " + umEzywayMid);
//       logger.info(" umMotoMid :: " + umMotoMid);
//       logger.info(" umEzyRecMid :: " + umEzyRecMid);
//       logger.info(" ummid :: " + ummid);
//       logger.info(" boostMid :: " + boostMid);
//       logger.info(" ezypassMid :: " + ezypassMid);
//       logger.info(" tngMid :: " + tngMid);
//       logger.info(" sppMid :: " + sppMid);
//       logger.info(" fpxMid :: " + fpxMid);
//
//       logger.info(" boostMid :: " + boostMid);
//       logger.info(" gpayTid :: " + gpayTid);
//       logger.info(" onlineGpay :: " + onlineGpay);
//
//       if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty()) && type == null) {
//
//          logger.info(" type :: " + type);
//
////        sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
////              + "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
////              + "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
////              + "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
////              + "INNER JOIN MERCHANT  mt on m.MERCHANT_FK = mt.ID "
////              + "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
////              + "DATE(e.TIME_STAMP) BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc ) " + "UNION "
////              + "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
////              + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
////              + "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
////              + "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
////              + "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
////              + "DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + "UNION "
////              + "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
////              + "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
////              + "from FPX_TRANSACTION f INNER JOIN MID m on f.MID = m.FPX_MID "
////              + "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
////              + "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  DATE(f.TIME_STAMP) BETWEEN "
////              + " :fromDate and :toDate order by f.TIME_STAMP desc ) " + "UNION "
////              + "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
////              + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
////              + "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
////              + "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
////              + "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
////              + " DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + " UNION "
////              + "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
////              + " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID INNER JOIN "
////              + " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS','H') AND w.MID IN (:tngMid) AND DATE(w.TIME_STAMP)BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
////              + " UNION "
////              + "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
////              + " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID = m.SHOPPY_MID INNER JOIN "
////              + " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND DATE(w.TIME_STAMP) BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
////              + " UNION "
////              + " ( select a.MID AS MID, a.AMOUNT AS AMOUNT ,a.TID AS TID, a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE ,a.TRX_ID AS RRN ,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID ,'' AS AUTH,a.TXN_TYPE AS TXN_TYPE,t.CARD_HOLDER_NAME AS CARD_HOLDER_NAME ,t.MASKED_PAN  AS MASKED_PAN , a.STAN AS STAN,a.TIME AS TIME from FOR_SETTLEMENT a INNER JOIN TRANSACTION_REQUEST t on a.TRX_ID=t.TXN_ID  INNER JOIN MID m on a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN MERCHANT f "
////              + " ON f.MID_FK=m.ID where a.STATUS in ('A','S','CT','C','R','CV','P') AND a.`MID` IN (:mid,:ezywayMid,:motoMid) AND DATE(a.TIME_STAMP) between :fromDate and :toDate order by a.TIME_STAMP desc ) ";
//
//
//
//          sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
//                + "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
//                + "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
//                + "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
//                + "INNER JOIN MERCHANT  mt on m.MERCHANT_FK = mt.ID "
//                + "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
//                + "DATE(e.TIME_STAMP) BETWEEN :fromDate and :toDate ) " + "UNION "
//                + "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//                + "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
//                + "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//                + "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//                + "DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate ) " + "UNION "
//                + "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
//                + "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
//                + "from FPX_TRANSACTION f INNER JOIN MID m on f.MID = m.FPX_MID "
//                + "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
//                + "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  DATE(f.TIME_STAMP) BETWEEN "
//                + " :fromDate and :toDate ) " + "UNION "
//                + "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
//                + "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
//                + "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
//                + "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
//                + " DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate ) " + " UNION "
//                + "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//                + " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID INNER JOIN "
//                + " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS','H') AND w.MID IN (:tngMid) AND DATE(w.TIME_STAMP)BETWEEN :fromDate and :toDate )"
//                + " UNION "
//                + "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//                + " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID = m.SHOPPY_MID INNER JOIN "
//                + " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND DATE(w.TIME_STAMP) BETWEEN :fromDate and :toDate )"
//                + " UNION "
//                + " ( select a.MID AS MID, a.AMOUNT AS AMOUNT ,a.TID AS TID, a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE ,a.TRX_ID AS RRN ,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID ,'' AS AUTH,a.TXN_TYPE AS TXN_TYPE,t.CARD_HOLDER_NAME AS CARD_HOLDER_NAME ,t.MASKED_PAN  AS MASKED_PAN , a.STAN AS STAN,a.TIME AS TIME from FOR_SETTLEMENT a INNER JOIN TRANSACTION_REQUEST t on a.TRX_ID=t.TXN_ID  INNER JOIN MID m on a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN MERCHANT f "
//                + " ON f.MID_FK=m.ID where a.STATUS in ('A','S','CT','C','R','CV','P') AND a.`MID` IN (:mid,:ezywayMid,:motoMid) AND DATE(a.TIME_STAMP) between :fromDate and :toDate ) "
//                + " ORDER BY TIME_STAMP DESC";
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//
//          sqlQuery.setString("ummid", ummid);
//          sqlQuery.setString("umEzywayMid", umEzywayMid);
//          sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//          sqlQuery.setString("umMotoMid", umMotoMid);
//          sqlQuery.setString("ezypassMid", ezypassMid);
//          sqlQuery.setString("boostMid", boostMid);
//          sqlQuery.setString("fpxMid", fpxMid);
//          sqlQuery.setString("splitMid", splitMid);
//          sqlQuery.setString("gpayTid", gpayTid);
//          sqlQuery.setString("onlineGpay", onlineGpay);
//          sqlQuery.setString("tngMid", tngMid);
//          sqlQuery.setString("sppMid", sppMid);
//
//          sqlQuery.setString("mid", mid);
//          sqlQuery.setString("ezywayMid", ezywayMid);
//          sqlQuery.setString("motoMid", motoMid);
//
//          logger.info("from date :"+fromDate);
//          logger.info("to date :"+toDate);
//
//          // String type1 =paginationBean.setSelectType(type);
//
//          logger.info("total size of query is :"+sqlQuery.list().size());
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       }
//
//       else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//             && type.equalsIgnoreCase("cards")) {
//
//          logger.info(" Cards :: " + type);
//          sql = "select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
//                + " e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
//                + " e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
//                + " INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID  "
//                + " INNER JOIN MERCHANT mt on m.MERCHANT_FK = mt.ID "
//                + " WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND e.H001_MTI in ('0290','0210') AND "
//                + " e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc  ";
//
//
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//
//          sqlQuery.setString("umEzywayMid", umEzywayMid);
//          sqlQuery.setString("umMotoMid", umMotoMid);
//          sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//          sqlQuery.setString("ummid", ummid);
//
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       } else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//             && type.equalsIgnoreCase("boost")) {
//          logger.info(" boost :: " + type);
//
//          sql = "select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//                + "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID = m.BOOST_MID  "
//                + "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//                + "WHERE a.STATUS in ('BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//                + " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";
//
////        sql = "select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
////              + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
////              + "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
////              + "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
////              + "WHERE a.STATUS in ('BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
////              + " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//
//          sqlQuery.setString("umEzywayMid", umEzywayMid);
//          sqlQuery.setString("umMotoMid", umMotoMid);
//          sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//          sqlQuery.setString("ummid", ummid);
//          sqlQuery.setString("boostMid", boostMid);
//          sqlQuery.setString("ezypassMid", ezypassMid);
//
//          // String type1 =paginationBean.setSelectType(type);
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       }
//
//       else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//             && type.equalsIgnoreCase("grab")) {
//
//          logger.info(" Grab :: " + type);
//
//          sql = "select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
//                + "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
//                + "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
//                + "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
//                + " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//
//          sqlQuery.setString("onlineGpay", onlineGpay);
//          sqlQuery.setString("gpayTid", gpayTid);
//
//          // String type1 =paginationBean.setSelectType(type);
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       } else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//             && type.equalsIgnoreCase("tng")) {
//
//          logger.info(" tng :: " + type);
//
//          sql = "SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,"
//                + " w.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH,"
//                + " w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON "
//                + " w.MID =m.TNG_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS', 'H') AND w.MID IN (:tngMid) AND w.TIME_STAMP BETWEEN :fromDate AND :toDate "
//                + " ORDER BY w.TIME_STAMP DESC ";
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//
//          sqlQuery.setString("tngMid", tngMid);
////        sqlQuery.setString("ummid", ummid);
//
//          // String type1 =paginationBean.setSelectType(type);
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       } else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//             && type.equalsIgnoreCase("spp")) {
//
//          logger.info(" Spp :: " + type);
//
//          sql = "SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,"
//                + " w.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH,"
//                + " w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON "
//                + " w.MID =m.SHOPPY_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND w.TIME_STAMP BETWEEN :fromDate AND :toDate "
//                + " ORDER BY w.TIME_STAMP DESC ";
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//          sqlQuery.setString("sppMid", sppMid);
//
//          // String type1 =paginationBean.setSelectType(type);
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       } else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
//             && type.equalsIgnoreCase("fpx")) {
//
//          logger.info(" FPX :: " + type);
//
////        sql = "select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
////              + "'' AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
////              + "from FPX_TRANSACTION f INNER JOIN MID m on f.MID=m.UM_MOTO_MID OR f.MID=m.UM_EZYWAY_MID OR f.MID=m.UM_EZYREC_MID OR f.MID=m.SPLIT_MID OR f.MID = m.FPX_MID "
////              + "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
////              + "WHERE AND f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND f.TIME_STAMP BETWEEN "
////              + " :fromDate and :toDate order by f.TIME_STAMP desc ";
//
//          sql = " SELECT f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE,f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME,f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,"
//                + " '' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,f.TX_TIME AS TIME FROM FPX_TRANSACTION f INNER JOIN MID m ON f.MID = m.FPX_MID "
//                + "INNER JOIN MERCHANT mr ON mr.MID_FK = m.ID WHERE f.STATUS IN ('A', 'S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND f.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY f.TIME_STAMP DESC ";
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//
//          sqlQuery.setString("ummid", ummid);
//          sqlQuery.setString("umEzywayMid", umEzywayMid);
//          sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//          sqlQuery.setString("umMotoMid", umMotoMid);
//          sqlQuery.setString("splitMid", splitMid);
//          sqlQuery.setString("fpxMid", fpxMid);
//
//          // String type1 =paginationBean.setSelectType(type);
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       } else {
//
//          logger.info(" All :: " + type);
//
//
//          sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
//                + "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
//                + "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
//                + "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
//                + "INNER JOIN MERCHANT mt on m.MERCHANT_FK = mt.ID "
//                + "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
//                + "e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc ) " + "UNION "
//                + "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//                + "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
//                + "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//                + "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//                + " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + "UNION "
//                + "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
//                + "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
//                + "from FPX_TRANSACTION f INNER JOIN MID m on f.MID=m.UM_MOTO_MID OR f.MID=m.UM_EZYWAY_MID OR f.MID=m.UM_EZYREC_MID OR f.MID=m.SPLIT_MID OR f.MID = m.FPX_MID "
//                + "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
//                + "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  f.TIME_STAMP BETWEEN "
//                + " :fromDate and :toDate order by f.TIME_STAMP desc ) " + "UNION "
//                + "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
//                + "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
//                + "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
//                + "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
//                + " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " +" UNION "+"(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//                + " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID OR m.SHOPPY_MID INNER JOIN "
//                + " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS', 'SPA','SPS','H') AND w.MID IN (:tngMid,:sppMid) AND w.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )";
//
//          sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//
//          sqlQuery.setString("fromDate", fromDate);
//          sqlQuery.setString("toDate", toDate);
//
//          sqlQuery.setString("ummid", ummid);
//          sqlQuery.setString("umEzywayMid", umEzywayMid);
//          sqlQuery.setString("umEzyRecMid", umEzyRecMid);
//          sqlQuery.setString("umMotoMid", umMotoMid);
//          sqlQuery.setString("ezypassMid", ezypassMid);
//          sqlQuery.setString("boostMid", boostMid);
//          sqlQuery.setString("fpxMid", fpxMid);
//          sqlQuery.setString("splitMid", splitMid);
//          sqlQuery.setString("gpayTid", gpayTid);
//          sqlQuery.setString("onlineGpay", onlineGpay);
//          sqlQuery.setString("tngMid", tngMid);
//          sqlQuery.setString("sppMid", sppMid);
//
//          sqlQuery.setString("mid", mid);
//          sqlQuery.setString("ezywayMid", ezywayMid);
//          sqlQuery.setString("motoMid", motoMid);
//
//          // String type1 =paginationBean.setSelectType(type);
//
//          String FromDate = paginationBean.setDateFromBackend(fromDate);
//          String From1Date = paginationBean.setDate1FromBackend(toDate);
//
//          querySize = sqlQuery.list().size();
//          paginationBean.setQuerySize(String.valueOf(querySize));
//          String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
//          int pageSize = Integer.parseInt(DynamicPage);
//          int pageNumFromJsp = paginationBean.getCurrPage();
//          sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
//          sqlQuery.setMaxResults(pageSize);
//          resultSet = sqlQuery.list();
//
//       }
//
//       logger.info("Query : " + sql);
//
//       logger.info("Number of records in the List : " + resultSet.size());
//       for (Object[] rec : resultSet) {
//
//          ForSettlement fs = new ForSettlement();
//
//          if (rec[0] != null) {
//             // MID
//             fs.setMid(rec[0].toString());
//          } else {
//             fs.setMid("");
//          }
//          
////        if (rec[1] != null || rec[1] != "") {
////            if (rec[1].toString().contains(".")) {
////                fs.setAmount(rec[1].toString());
////            } else {
////                double amount = 0;
////                amount = Double.parseDouble(rec[1].toString()) / 100;
////                String pattern = "#,##0.00";
////                DecimalFormat myFormatter = new DecimalFormat(pattern);
////                String output = myFormatter.format(amount);
////                fs.setAmount(output);
////            }
////        } else {
////
////           fs.setAmount("0.00");
////        }
//          
//          if (rec[1] != null && !rec[1].toString().isEmpty()) {
//              if (rec[1].toString().contains(".")) {
//                  fs.setAmount(rec[1].toString());
//              } else {
//                  double amount = 0;
//                  amount = Double.parseDouble(rec[1].toString()) / 100;
//                  String pattern = "#,##0.00";
//                  DecimalFormat myFormatter = new DecimalFormat(pattern);
//                  String output = myFormatter.format(amount);
//                  fs.setAmount(output);
//              }
//          } else {
//              fs.setAmount("0.00");
//          }
//
//
//          if (rec[2] != null) {
//             // TID
//             fs.setTid(rec[2].toString());
//          } else {
//             fs.setTid("");
//
//          }
//
//          if (rec[3] != null) {
//
//             // STATUS
//             if (rec[3].toString().equals("CT")) {
//                fs.setStatus("CASH SALE");
//             }
//             if (rec[3].toString().equals("CV")) {
//                fs.setStatus("CASH CANCELLED");
//             }
////           if (rec[4].toString().equals("R")) {
////              fs.setStatus("REVERSAL");
////           }else {fs.setStatus("");}
//             if (rec[4] != null && rec[4].toString().equals("R")) {
//                 fs.setStatus("REVERSAL");
//             } else {
//                 fs.setStatus("");
//             }
//
//             if (rec[3].toString().equals("S")) {
//                fs.setStatus("SETTLED");
//             }
//             if (rec[3].toString().equals("P")) {
//                fs.setStatus("PENDING");
//             }
//             if (rec[3].toString().equals("A")) {
//                fs.setStatus("NOT SETTLED");
//             }
//             if (rec[3].toString().equals("C")) {
//                fs.setStatus("CANCELLED");
//             }
//             if (rec[3].toString().equals("BP")) {
//                fs.setStatus("BOOST PENDING");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("BPC")) {
//                fs.setStatus("BOOST CANCELLED");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("BPS")) {
//                fs.setStatus("BOOST SETTLED");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("BPA")) {
//                fs.setStatus("BOOST PAYMENT");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("GPS")) {
//                fs.setStatus("GRABPAY COMPLETED");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("GRF")) {
//                fs.setStatus("GRABPAY REFUND");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("GPT")) {
//                fs.setStatus("GRABPAY SETTLED");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("GBC")) {
//                fs.setStatus("GRABPAY CANCELLED");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("SPS")) {
//                fs.setStatus("SHOPEE PAYMENT");
//                // logger.info("check status: "+fs.getStatus());
//             }
//             if (rec[3].toString().equals("TNG")) {
//                fs.setStatus("TNG PAYMENT");
//                // logger.info("check status: "+fs.getStatus());
//             }
//          } else {
//             fs.setStatus("");
//          }
//          if (rec[4] != null) {
//             fs.setAidResponse(rec[4].toString());
//          } else {
//             fs.setAidResponse("");
//          }
//          if (rec[5] != null) {
//             fs.setRrn(rec[5].toString());
//          } else {
//             fs.setRrn("");
//          }
//
//          // TIME STAMP --- FIELDS
//          if (rec[6] != null) {
//             // a.time_stamp
//             // fs.setTimeStamp(rec[1].toString());
//             String rd = null;
//             try {
//                rd = new SimpleDateFormat("dd/MM/yyyy")
//                      .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[6].toString()));
//             } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//             }
//             fs.setDate(rd);
//          }
//
//          if (rec[7] != null) {
//             // merchant name
//             fs.setNumOfSale(rec[7].toString());
//          } else {
//             fs.setNumOfSale("");
//          }
//          if (rec[8] != null) {
//             // reference
//             fs.setInvoiceId(rec[8].toString());
//          } else {
//             fs.setInvoiceId("");
//          }
//          if (rec[11] != null) {
//
//             fs.setNumOfRefund(rec[11].toString());
//          } else {
//
//             fs.setNumOfRefund("");
//          }
//          if (rec[12] != null) {
//
//             fs.setPan(rec[12].toString());
//          } else {
//             fs.setPan("");
//          }
//          if (rec[13] != null) {
//
//             fs.setStan(rec[13].toString());
//          } else {
//             fs.setStan("");
//          }
//
//          // TIME -- FIELDS
//
//          if (rec[14] != null) {
//
//             try {
//                String rt = new SimpleDateFormat("HH:mm:ss")
//                      .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[6].toString()));
//
//                fs.setTime(rt);
//
//             } catch (ParseException e) {
//             }
//
//          }
//
//          if (rec[10] != null) {
//             if (rec[9] != null) {
//
//                // logger.info("auth3ds:++: " + rec[9].toString());
//                // logger.info("setTxnType:++: " + rec[10].toString());
//                if ((rec[9].toString().equals("Yes")) && (rec[10].toString().equals("EZYMOTO"))) {
//
//                   logger.info("type:++: " + rec[10].toString());
//                   fs.setTxnType("EZYLINK");
//                }
//
//                if ((rec[10].toString().equals("BOOST"))) {
//
//                   logger.info("type:++: " + rec[10].toString());
//                   fs.setTxnType("BOOST");
//                }
//                if ((rec[10].toString().equals("FPX"))) {
//
//                   logger.info("type:++: " + rec[10].toString());
//                   fs.setTxnType("FPX");
//                }
//                if ((rec[10].toString().equals("GRABPAY"))) {
//
//                   logger.info("type:++: " + rec[10].toString());
//                   fs.setTxnType("GRABPAY");
//                }
//                if ((rec[10].toString().equals("TNG"))) {
//                   fs.setTxnType("TNG");
//                }
//                if ((rec[10].toString().equals("SPP"))) {
//                   fs.setTxnType("SPP");
//                } else {
//                   logger.info("type:++: " + rec[10].toString());
//                   fs.setTxnType(rec[10].toString());
//                }
//
//             } else {
//                logger.info("type:++: " + rec[10].toString());
//                fs.setTxnType(rec[10].toString());
//             }
//
//          } else {
//             fs.setTxnType("");
//          }
//
//          fss.add(fs);
//       }
//       paginationBean.setItemList(fss);
//
//       // paginationBean.setTotalRowCount(fss.size());
//    }
//
// 

	@Override
	public void subMerchantListByAdmin(PaginationBean<ForSettlement> paginationBean, String fromDate, String toDate,
			Merchant findMerchant, String type, List<MobileUser> mobileuser) {

		logger.info("inside TransactionDetailsbyAdmin " + " from date: " + fromDate + "toDate: " + toDate);

		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
		String sql = null;
		Query sqlQuery = null;
		List<Object[]> resultSet = null;
		int querySize = 0;

		String ummid = "";
		String umEzywayMid = "";
		String umMotoMid = "";
		String umEzyRecMid = "";
		String boostMid = "";
		String ezypassMid = "";
		String tngMid = "";
		String sppMid = "";

		String mid = "";
		String ezywayMid = "";
		String motoMid = "";

		String onlineGpay = "";
		String gpayTid = "";
		String fpxMid = "";
		String splitMid = "";

		logger.info("mobile User Size :" + mobileuser.size());
		if (mobileuser.size() != 0) {
			if (mobileuser.get(0).getOnlineGpay() != null) {
				onlineGpay = mobileuser.get(0).getOnlineGpay();
			}
			if (mobileuser.get(0).getGpayTid() != null) {
				gpayTid = mobileuser.get(0).getGpayTid();
			}
		}

		if (findMerchant.getMid().getEzywayMid() != null) {
			ezywayMid = findMerchant.getMid().getEzywayMid();
		}
		if (findMerchant.getMid().getMid() != null) {
			mid = findMerchant.getMid().getMid();
		}
		if (findMerchant.getMid().getMotoMid() != null) {
			motoMid = findMerchant.getMid().getMotoMid();
		}

		if (findMerchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = findMerchant.getMid().getUmEzywayMid();
		}
		if (findMerchant.getMid().getUmMotoMid() != null) {
			umMotoMid = findMerchant.getMid().getUmMotoMid();
		}
		if (findMerchant.getMid().getUmEzyrecMid() != null) {
			umEzyRecMid = findMerchant.getMid().getEzyrecMid();
		}
		if (findMerchant.getMid().getUmMid() != null) {
			ummid = findMerchant.getMid().getUmMid();
		}
		if (findMerchant.getMid().getBoostMid() != null) {
			boostMid = findMerchant.getMid().getBoostMid();
		}
		if (findMerchant.getMid().getUmEzypassMid() != null) {
			ezypassMid = findMerchant.getMid().getUmEzypassMid();
		}
		if (findMerchant.getMid().getTngMid() != null) {
			tngMid = findMerchant.getMid().getTngMid();
		}
		if (findMerchant.getMid().getShoppyMid() != null) {
			sppMid = findMerchant.getMid().getShoppyMid();
		}
		if (findMerchant.getMid().getFpxMid() != null) {
			fpxMid = findMerchant.getMid().getFpxMid();
		}
		if (findMerchant.getMid().getSplitMid() != null) {
			splitMid = findMerchant.getMid().getSplitMid();
		}

		logger.info(" umEzywayMid :: " + umEzywayMid);
		logger.info(" umMotoMid :: " + umMotoMid);
		logger.info(" umEzyRecMid :: " + umEzyRecMid);
		logger.info(" ummid :: " + ummid);
		logger.info(" boostMid :: " + boostMid);
		logger.info(" ezypassMid :: " + ezypassMid);
		logger.info(" tngMid :: " + tngMid);
		logger.info(" sppMid :: " + sppMid);
		logger.info(" fpxMid :: " + fpxMid);

		logger.info(" boostMid :: " + boostMid);
		logger.info(" gpayTid :: " + gpayTid);
		logger.info(" onlineGpay :: " + onlineGpay);

		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty()) && type == null) {

			logger.info(" type :: " + type);

//          sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
//                + "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
//                + "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
//                + "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
//                + "INNER JOIN MERCHANT  mt on m.MERCHANT_FK = mt.ID "
//                + "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
//                + "DATE(e.TIME_STAMP) BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc ) " + "UNION "
//                + "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//                + "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
//                + "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//                + "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//                + "DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + "UNION "
//                + "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
//                + "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
//                + "from FPX_TRANSACTION f INNER JOIN MID m on f.MID = m.FPX_MID "
//                + "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
//                + "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  DATE(f.TIME_STAMP) BETWEEN "
//                + " :fromDate and :toDate order by f.TIME_STAMP desc ) " + "UNION "
//                + "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
//                + "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
//                + "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
//                + "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
//                + " DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + " UNION "
//                + "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//                + " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID INNER JOIN "
//                + " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS','H') AND w.MID IN (:tngMid) AND DATE(w.TIME_STAMP)BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
//                + " UNION "
//                + "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
//                + " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID = m.SHOPPY_MID INNER JOIN "
//                + " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND DATE(w.TIME_STAMP) BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )"
//                + " UNION "
//                + " ( select a.MID AS MID, a.AMOUNT AS AMOUNT ,a.TID AS TID, a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE ,a.TRX_ID AS RRN ,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID ,'' AS AUTH,a.TXN_TYPE AS TXN_TYPE,t.CARD_HOLDER_NAME AS CARD_HOLDER_NAME ,t.MASKED_PAN  AS MASKED_PAN , a.STAN AS STAN,a.TIME AS TIME from FOR_SETTLEMENT a INNER JOIN TRANSACTION_REQUEST t on a.TRX_ID=t.TXN_ID  INNER JOIN MID m on a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN MERCHANT f "
//                + " ON f.MID_FK=m.ID where a.STATUS in ('A','S','CT','C','R','CV','P') AND a.`MID` IN (:mid,:ezywayMid,:motoMid) AND DATE(a.TIME_STAMP) between :fromDate and :toDate order by a.TIME_STAMP desc ) ";

			sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
					+ "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
					+ "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from mobiversa.UM_ECOM_TXNRESPONSE e "
					+ "INNER JOIN mobiversa.MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
					+ "INNER JOIN mobiversa.MERCHANT  mt on m.MERCHANT_FK = mt.ID "
					+ "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
					+ "DATE(e.TIME_STAMP) BETWEEN :fromDate and :toDate ) " + "UNION "
					+ "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
					+ "from mobiversa.FOR_SETTLEMENT a INNER JOIN mobiversa.MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
					+ "INNER JOIN mobiversa.MERCHANT f ON f.MID_FK=m.ID "
					+ "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
					+ "DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate ) " + "UNION "
					+ "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
					+ "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
					+ "from mobiversa.FPX_TRANSACTION f INNER JOIN mobiversa.MID m on f.MID = m.FPX_MID "
					+ "INNER JOIN mobiversa.MERCHANT mr ON mr.MID_FK=m.ID "
					+ "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  DATE(f.TIME_STAMP) BETWEEN "
					+ " :fromDate and :toDate ) " + "UNION "
					+ "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
					+ "from mobiversa.MID m INNER JOIN mobiversa.MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN mobiversa.MERCHANT c ON c.ID = m.MERCHANT_FK "
					+ "INNER JOIN mobiversa.FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
					+ "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
					+ " DATE(a.TIME_STAMP) BETWEEN :fromDate and :toDate ) " + " UNION "
					+ "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID INNER JOIN "
					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS','H') AND w.MID IN (:tngMid) AND DATE(w.TIME_STAMP)BETWEEN :fromDate and :toDate )"
					+ " UNION "
					+ "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID = m.SHOPPY_MID INNER JOIN "
					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND DATE(w.TIME_STAMP) BETWEEN :fromDate and :toDate )"
					+ " UNION "
					+ " ( select a.MID AS MID, a.AMOUNT AS AMOUNT ,a.TID AS TID, a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE ,a.TRX_ID AS RRN ,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID ,'' AS AUTH,a.TXN_TYPE AS TXN_TYPE,t.CARD_HOLDER_NAME AS CARD_HOLDER_NAME ,t.MASKED_PAN  AS MASKED_PAN , a.STAN AS STAN,a.TIME AS TIME from mobiversa.FOR_SETTLEMENT a INNER JOIN mobiversa.TRANSACTION_REQUEST t on a.TRX_ID=t.TXN_ID  INNER JOIN mobiversa.MID m on a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN mobiversa.MERCHANT f "
					+ " ON f.MID_FK=m.ID where a.STATUS in ('A','S','CT','C','R','CV','P') AND a.`MID` IN (:mid,:ezywayMid,:motoMid) AND DATE(a.TIME_STAMP) between :fromDate and :toDate ) "
					+ " ORDER BY TIME_STAMP DESC";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

			sqlQuery.setString("ummid", ummid);
			sqlQuery.setString("umEzywayMid", umEzywayMid);
			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
			sqlQuery.setString("umMotoMid", umMotoMid);
			sqlQuery.setString("ezypassMid", ezypassMid);
			sqlQuery.setString("boostMid", boostMid);
			sqlQuery.setString("fpxMid", fpxMid);
			sqlQuery.setString("splitMid", splitMid);
			sqlQuery.setString("gpayTid", gpayTid);
			sqlQuery.setString("onlineGpay", onlineGpay);
			sqlQuery.setString("tngMid", tngMid);
			sqlQuery.setString("sppMid", sppMid);

			sqlQuery.setString("mid", mid);
			sqlQuery.setString("ezywayMid", ezywayMid);
			sqlQuery.setString("motoMid", motoMid);

			logger.info("from date :" + fromDate);
			logger.info("to date :" + toDate);

			// String type1 =paginationBean.setSelectType(type);

			logger.info("total size of query is :" + sqlQuery.list().size());

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		}

		else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
				&& type.equalsIgnoreCase("cards")) {

			logger.info(" Cards :: " + type);
			sql = "select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
					+ " e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
					+ " e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
					+ " INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID  "
					+ " INNER JOIN MERCHANT mt on m.MERCHANT_FK = mt.ID "
					+ " WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND e.H001_MTI in ('0290','0210') AND "
					+ " e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc  ";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

			sqlQuery.setString("umEzywayMid", umEzywayMid);
			sqlQuery.setString("umMotoMid", umMotoMid);
			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
			sqlQuery.setString("ummid", ummid);

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
				&& type.equalsIgnoreCase("boost")) {
			logger.info(" boost :: " + type);

			sql = "select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID = m.BOOST_MID  "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "WHERE a.STATUS in ('BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";

//          sql = "select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
//                + "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
//                + "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
//                + "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
//                + "WHERE a.STATUS in ('BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
//                + " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

			sqlQuery.setString("umEzywayMid", umEzywayMid);
			sqlQuery.setString("umMotoMid", umMotoMid);
			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
			sqlQuery.setString("ummid", ummid);
			sqlQuery.setString("boostMid", boostMid);
			sqlQuery.setString("ezypassMid", ezypassMid);

			// String type1 =paginationBean.setSelectType(type);

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		}

		else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
				&& type.equalsIgnoreCase("grab")) {

			logger.info(" Grab :: " + type);

			sql = "select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
					+ "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
					+ "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
					+ "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

			sqlQuery.setString("onlineGpay", onlineGpay);
			sqlQuery.setString("gpayTid", gpayTid);

			// String type1 =paginationBean.setSelectType(type);

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
				&& type.equalsIgnoreCase("tng")) {

			logger.info(" tng :: " + type);

			sql = "SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,"
					+ " w.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH,"
					+ " w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON "
					+ " w.MID =m.TNG_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS', 'H') AND w.MID IN (:tngMid) AND w.TIME_STAMP BETWEEN :fromDate AND :toDate "
					+ " ORDER BY w.TIME_STAMP DESC ";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

			sqlQuery.setString("tngMid", tngMid);
//          sqlQuery.setString("ummid", ummid);

			// String type1 =paginationBean.setSelectType(type);

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
				&& type.equalsIgnoreCase("spp")) {

			logger.info(" Spp :: " + type);

			sql = "SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,"
					+ " w.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH,"
					+ " w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON "
					+ " w.MID =m.SHOPPY_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('SPA','SPS','H') AND w.MID IN (:sppMid) AND w.TIME_STAMP BETWEEN :fromDate AND :toDate "
					+ " ORDER BY w.TIME_STAMP DESC ";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);
			sqlQuery.setString("sppMid", sppMid);

			// String type1 =paginationBean.setSelectType(type);

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		} else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
				&& type.equalsIgnoreCase("fpx")) {

			logger.info(" FPX :: " + type);

//          sql = "select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
//                + "'' AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
//                + "from FPX_TRANSACTION f INNER JOIN MID m on f.MID=m.UM_MOTO_MID OR f.MID=m.UM_EZYWAY_MID OR f.MID=m.UM_EZYREC_MID OR f.MID=m.SPLIT_MID OR f.MID = m.FPX_MID "
//                + "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
//                + "WHERE AND f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND f.TIME_STAMP BETWEEN "
//                + " :fromDate and :toDate order by f.TIME_STAMP desc ";

			sql = " SELECT f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE,f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME,f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,"
					+ " '' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,f.TX_TIME AS TIME FROM FPX_TRANSACTION f INNER JOIN MID m ON f.MID = m.FPX_MID "
					+ "INNER JOIN MERCHANT mr ON mr.MID_FK = m.ID WHERE f.STATUS IN ('A', 'S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND f.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY f.TIME_STAMP DESC ";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

			sqlQuery.setString("ummid", ummid);
			sqlQuery.setString("umEzywayMid", umEzywayMid);
			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
			sqlQuery.setString("umMotoMid", umMotoMid);
			sqlQuery.setString("splitMid", splitMid);
			sqlQuery.setString("fpxMid", fpxMid);

			// String type1 =paginationBean.setSelectType(type);

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		} else {

			logger.info(" All :: " + type);

			sql = "(select e.F001_MID AS MID ,e.F007_TXNAMT AS AMOUNT ,e.F354_TID AS TID, e.STATUS AS STATUS, "
					+ "e.F011_AUTHIDRESP AS AID_RESPONSE,e.F023_RRN AS RRN,e.TIME_STAMP AS TIME_STAMP,mt.BUSINESS_NAME AS BUSINESS_NAME, "
					+ "e.F270_ORN AS INVOICE_ID ,mt.AUTH_3DS AS AUTH,e.TXN_TYPE AS TXN_TYPE,e.F268_CHNAME AS CARD_HOLDER_NAME,e.MASKED_PAN AS MASKED_PAN,'' AS STAN , e.H004_TTM AS TIME from UM_ECOM_TXNRESPONSE e "
					+ "INNER JOIN MID m on e.F001_MID = m.UM_EZYWAY_MID OR e.F001_MID = m.UM_MOTO_MID OR e.F001_MID = m.UM_EZYREC_MID "
					+ "INNER JOIN MERCHANT mt on m.MERCHANT_FK = mt.ID "
					+ "WHERE e.STATUS in ('A','S','C') AND e.F001_MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid) AND  e.H001_MTI in ('0290','0210') AND "
					+ "e.TIME_STAMP BETWEEN :fromDate and :toDate order by e.TIME_STAMP desc ) " + "UNION "
					+ "(select a.MID AS MID,a.AMOUNT AS AMOUNT,a.TID AS TID ,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,f.BUSINESS_NAME AS BUSINESS_NAME ,a.INVOICE_ID AS INVOICE_ID,f.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE, '' AS CARD_HOLDER_NAME, '' AS MASKED_PAN ,a.STAN AS STAN , a.TIME AS TIME "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.UM_MID OR a.MID=m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYREC_MID OR  a.MID = m.UM_EZYPASS_MID OR a.MID = m.BOOST_MID  "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "WHERE a.STATUS in ('A','S','BPS','BPA','BPC') AND a.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:boostMid,:ezypassMid) AND "
					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + "UNION "
					+ "(select f.MID AS MID,f.TXNAMOUNT AS AMOUNT,f.TID AS TID,f.STATUS AS STATUS,'' AS AID_RESPONSE, "
					+ "f.FPXTXNID AS RRN,f.TIME_STAMP AS TIME_STAMP,mr.BUSINESS_NAME AS BUSINESS_NAME, f.SELLERORDERNO AS INVOICE_ID,mr.AUTH_3DS AS AUTH,'FPX' AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , f.TX_TIME AS TIME "
					+ "from FPX_TRANSACTION f INNER JOIN MID m on f.MID=m.UM_MOTO_MID OR f.MID=m.UM_EZYWAY_MID OR f.MID=m.UM_EZYREC_MID OR f.MID=m.SPLIT_MID OR f.MID = m.FPX_MID "
					+ "INNER JOIN MERCHANT mr ON mr.MID_FK=m.ID "
					+ "WHERE f.STATUS in ('A','S') AND f.MID IN (:ummid,:umEzywayMid,:umMotoMid,:umEzyRecMid,:splitMid,:fpxMid) AND  f.TIME_STAMP BETWEEN "
					+ " :fromDate and :toDate order by f.TIME_STAMP desc ) " + "UNION "
					+ "(select '' AS MID,a.AMOUNT AS AMOUNT,'' AS TID,a.STATUS AS STATUS,a.AID_RESPONSE AS AID_RESPONSE, "
					+ "a.RRN AS RRN,a.TIME_STAMP AS TIME_STAMP,c.BUSINESS_NAME AS BUSINESS_NAME,a.INVOICE_ID AS INVOICE_ID,c.AUTH_3DS AS AUTH,a.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN , a.TIME AS TIME "
					+ "from MID m INNER JOIN MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK INNER JOIN MERCHANT c ON c.ID = m.MERCHANT_FK "
					+ "INNER JOIN FOR_SETTLEMENT a ON a.TID = u.GPAY_TID "
					+ "WHERE a.STATUS in ('GPS','GRF','GBC','GPT') AND a.TID IN (:onlineGpay,:gpayTid) AND "
					+ " a.TIME_STAMP BETWEEN :fromDate and :toDate order by a.TIME_STAMP desc ) " + " UNION "
					+ "(SELECT w.MID AS MID,w.AMOUNT AS AMOUNT,w.TID AS tid,w.STATUS AS STATUS,'' AS AID_RESPONSE,w.TNG_TXN_ID AS RRN,w.TIME_STAMP AS TIME_STAMP,"
					+ " f.BUSINESS_NAME AS BUSINESS_NAME,w.INVOICE_ID AS INVOICE_ID,'' AS AUTH, w.TXN_TYPE AS TXN_TYPE,'' AS CARD_HOLDER_NAME,'' AS MASKED_PAN,'' AS STAN,w.TIME AS TIME FROM mobiversa.EWALLET_TXN_DETAILS w INNER JOIN mobiversa.MID m ON w.MID =m.TNG_MID OR m.SHOPPY_MID INNER JOIN "
					+ " mobiversa.MERCHANT f ON f.MID_FK = m.ID WHERE w.STATUS IN ('TPA', 'TPS', 'SPA','SPS','H') AND w.MID IN (:tngMid,:sppMid) AND w.TIME_STAMP BETWEEN :fromDate and :toDate ORDER BY w.TIME_STAMP DESC )";

			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

			sqlQuery.setString("ummid", ummid);
			sqlQuery.setString("umEzywayMid", umEzywayMid);
			sqlQuery.setString("umEzyRecMid", umEzyRecMid);
			sqlQuery.setString("umMotoMid", umMotoMid);
			sqlQuery.setString("ezypassMid", ezypassMid);
			sqlQuery.setString("boostMid", boostMid);
			sqlQuery.setString("fpxMid", fpxMid);
			sqlQuery.setString("splitMid", splitMid);
			sqlQuery.setString("gpayTid", gpayTid);
			sqlQuery.setString("onlineGpay", onlineGpay);
			sqlQuery.setString("tngMid", tngMid);
			sqlQuery.setString("sppMid", sppMid);

			sqlQuery.setString("mid", mid);
			sqlQuery.setString("ezywayMid", ezywayMid);
			sqlQuery.setString("motoMid", motoMid);

			// String type1 =paginationBean.setSelectType(type);

			String FromDate = paginationBean.setDateFromBackend(fromDate);
			String From1Date = paginationBean.setDate1FromBackend(toDate);

			querySize = sqlQuery.list().size();
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();

		}

		logger.info("Query : " + sql);

		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {

			ForSettlement fs = new ForSettlement();

			if (rec[0] != null) {
				// MID
				fs.setMid(rec[0].toString());
			} else {
				fs.setMid("");
			}

//          if (rec[1] != null || rec[1] != "") {
//              if (rec[1].toString().contains(".")) {
//                  fs.setAmount(rec[1].toString());
//              } else {
//                  double amount = 0;
//                  amount = Double.parseDouble(rec[1].toString()) / 100;
//                  String pattern = "#,##0.00";
//                  DecimalFormat myFormatter = new DecimalFormat(pattern);
//                  String output = myFormatter.format(amount);
//                  fs.setAmount(output);
//              }
//          } else {
			//
//             fs.setAmount("0.00");
//          }

			if (rec[1] != null && !rec[1].toString().isEmpty()) {
				if (rec[1].toString().contains(".")) {
					fs.setAmount(rec[1].toString());
				} else {
					double amount = 0;
					amount = Double.parseDouble(rec[1].toString()) / 100;
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					fs.setAmount(output);
				}
			} else {
				fs.setAmount("0.00");
			}

			if (rec[2] != null) {
				// TID
				fs.setTid(rec[2].toString());
			} else {
				fs.setTid("");

			}

			if (rec[3] != null) {

				// STATUS
				if (rec[3].toString().equals("CT")) {
					fs.setStatus("CASH SALE");
				}
				if (rec[3].toString().equals("CV")) {
					fs.setStatus("CASH CANCELLED");
				}
//             if (rec[4].toString().equals("R")) {
//                fs.setStatus("REVERSAL");
//             }else {fs.setStatus("");}
				if (rec[4] != null && rec[4].toString().equals("R")) {
					fs.setStatus("REVERSAL");
				} else {
					fs.setStatus("");
				}

				if (rec[3].toString().equals("S")) {
					fs.setStatus("SETTLED");
				}
				if (rec[3].toString().equals("P")) {
					fs.setStatus("PENDING");
				}
				if (rec[3].toString().equals("A")) {
					fs.setStatus("NOT SETTLED");
				}
				if (rec[3].toString().equals("C")) {
					fs.setStatus("CANCELLED");
				}
				if (rec[3].toString().equals("BP")) {
					fs.setStatus("BOOST PENDING");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("BPC")) {
					fs.setStatus("BOOST CANCELLED");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("BPS")) {
					fs.setStatus("BOOST SETTLED");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("BPA")) {
					fs.setStatus("BOOST PAYMENT");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("GPS")) {
					fs.setStatus("GRABPAY COMPLETED");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("GRF")) {
					fs.setStatus("GRABPAY REFUND");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("GPT")) {
					fs.setStatus("GRABPAY SETTLED");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("GBC")) {
					fs.setStatus("GRABPAY CANCELLED");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("SPS")) {
					fs.setStatus("SHOPEE PAYMENT");
					// logger.info("check status: "+fs.getStatus());
				}
				if (rec[3].toString().equals("TNG")) {
					fs.setStatus("TNG PAYMENT");
					// logger.info("check status: "+fs.getStatus());
				}
			} else {
				fs.setStatus("");
			}
			if (rec[4] != null) {
				fs.setAidResponse(rec[4].toString());
			} else {
				fs.setAidResponse("");
			}
			if (rec[5] != null) {
				fs.setRrn(rec[5].toString());
			} else {
				fs.setRrn("");
			}

			// TIME STAMP --- FIELDS
			if (rec[6] != null) {
				// a.time_stamp
				// fs.setTimeStamp(rec[1].toString());
				String rd = null;
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[6].toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fs.setDate(rd);
			}

			if (rec[7] != null) {
				// merchant name
				fs.setNumOfSale(rec[7].toString());
			} else {
				fs.setNumOfSale("");
			}
			if (rec[8] != null) {
				// reference
				fs.setInvoiceId(rec[8].toString());
			} else {
				fs.setInvoiceId("");
			}
			if (rec[11] != null) {

				fs.setNumOfRefund(rec[11].toString());
			} else {

				fs.setNumOfRefund("");
			}
			if (rec[12] != null) {

				fs.setPan(rec[12].toString());
			} else {
				fs.setPan("");
			}
			if (rec[13] != null) {

				fs.setStan(rec[13].toString());
			} else {
				fs.setStan("");
			}

			// TIME -- FIELDS

			if (rec[14] != null) {

				try {
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[6].toString()));

					fs.setTime(rt);

				} catch (ParseException e) {
				}

			}

			if (rec[10] != null) {
				if (rec[9] != null) {

					// logger.info("auth3ds:++: " + rec[9].toString());
					// logger.info("setTxnType:++: " + rec[10].toString());
					if ((rec[9].toString().equals("Yes")) && (rec[10].toString().equals("EZYMOTO"))) {

						logger.info("type:++: " + rec[10].toString());
						fs.setTxnType("EZYLINK");
					}

					if ((rec[10].toString().equals("BOOST"))) {

						logger.info("type:++: " + rec[10].toString());
						fs.setTxnType("BOOST");
					}
					if ((rec[10].toString().equals("FPX"))) {

						logger.info("type:++: " + rec[10].toString());
						fs.setTxnType("FPX");
					}
					if ((rec[10].toString().equals("GRABPAY"))) {

						logger.info("type:++: " + rec[10].toString());
						fs.setTxnType("GRABPAY");
					}
					if ((rec[10].toString().equals("TNG"))) {
						fs.setTxnType("TNG");
					}
					if ((rec[10].toString().equals("SPP"))) {
						fs.setTxnType("SPP");
					} else {
						logger.info("type:++: " + rec[10].toString());
						fs.setTxnType(rec[10].toString());
					}

				} else {
					logger.info("type:++: " + rec[10].toString());
					fs.setTxnType(rec[10].toString());
				}

			} else {
				fs.setTxnType("");
			}

			fss.add(fs);
		}
		paginationBean.setItemList(fss);

		// paginationBean.setTotalRowCount(fss.size());
	}

//	@Override
//	@Transactional(readOnly = false)
//	public void listadminsubMerchantdefault(final PaginationBean<Merchant> paginationBean) {@Override
	@Override
	public void listadminsubMerchantdefault(final PaginationBean<Merchant> paginationBean) {
		logger.info("listMerchantUserByMid::");

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;

//			String dlocalmmid = "DEPANSUM MALAYSIA SDN BHD";
//			String mntxmmid = "MNTX";
//			String zotaPaymmid = "ZOTAPAY";
//			String mobiDemo = "MOBI ASIA SDN BHD - TEST";
//			String jomorder = "JOMORDER";
//			String exim = "EXIM TECHNOLOGIES LTD";
//			String toyyibpay = "TOYYIBPAY";
//			String mtd = "MTD";

//			sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
//					+ "mi.SUB_MERCHANT_MID ,m.ID ,m.MM_ID from MERCHANT m  "
//					+ "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID IN (:dlocalmmid, :mntxmmid, :zotaPaymmid,:mobiDemo,:jomorder,:exim,:toyyibpay,:mtd) order by m.ACTIVATE_DATE desc ";

		sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
				+ "mi.SUB_MERCHANT_MID ,m.ID ,m.MM_ID from MERCHANT m  "
				+ "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK order by m.ACTIVATE_DATE desc ";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);

		// Dynamic changes for pagination count

		String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
		int pageSize = Integer.parseInt(DynamicPage);

		int pageNumFromJsp = paginationBean.getCurrPage();
		logger.info("Page Number:" + pageNumFromJsp);
		logger.info("Max Count for Records:" + pageSize);

		sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
		sqlQuery.setMaxResults(pageSize);
//			sqlQuery.setString("dlocalmmid", dlocalmmid);
//			sqlQuery.setString("mntxmmid", mntxmmid);
//			sqlQuery.setString("zotaPaymmid", zotaPaymmid);
//			sqlQuery.setString("mobiDemo", mobiDemo);
//			sqlQuery.setString("jomorder", jomorder);
//			sqlQuery.setString("exim", exim);
//			sqlQuery.setString("toyyibpay", toyyibpay);
//			sqlQuery.setString("mtd", mtd);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			MID mi = new MID();

			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setCreatedBy(rd);
			}

			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setEmail(rec[2].toString());
			}

			if (rec[3] != null) {
				fs.setCity(rec[3].toString());
			} else {
				fs.setCity("");
			}

			if (rec[4] != null) {
				fs.setState(rec[4].toString());
			}

			if (rec[5] != null) {
				fs.setMobiId(rec[5].toString());
			} else {
				fs.setMobiId("");
			}

			if (rec[6] != null) {

				fs.setSalutation(rec[6].toString());

			} else {
				fs.setSalutation("");
			}

			if (rec[7] != null) {

				fs.setMmId(rec[7].toString());

			} else {
				fs.setMmId("");
			}

			fss.add(fs);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	// karuppusamy new dao impl

	@Override
	public void listadminsubMerchantdefault1(final PaginationBean<Merchant> paginationBean,
			List<String> submerchantList) {
		logger.info("listMerchantUserByMid::");

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;
		int querySize = 0;
		List<Object[]> resultSet = null;

		try {
			sql = "SELECT m.ACTIVATE_DATE, m.BUSINESS_NAME, m.EMAIL, m.CITY, m.STATE, "
					+ "mi.SUB_MERCHANT_MID, m.ID, m.MM_ID " + "FROM mobiversa.MERCHANT m "
					+ "INNER JOIN mobiversa.MID mi ON m.ID = mi.MERCHANT_FK "
					+ "WHERE m.MM_ID NOT IN ('0', '--Select the Master Merchant--') " + "AND m.MM_ID IS NOT NULL "
					+ "ORDER BY m.ACTIVATE_DATE DESC";

			logger.info("Query : " + sql);

			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);

			querySize = sqlQuery.list().size();
			logger.info("Total query size is :" + querySize);
			paginationBean.setQuerySize(String.valueOf(querySize));
			String DynamicPage = PropertyLoad.getFile().getProperty("paginationCount");
			int pageSize = Integer.parseInt(DynamicPage);
			int pageNumFromJsp = paginationBean.getCurrPage();
			sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
			sqlQuery.setMaxResults(pageSize);
			resultSet = sqlQuery.list();
			for (Object[] rec : resultSet) {
				Merchant fs = new Merchant();
				MID mi = new MID();

				String rd = getStringValue(rec[0]);
				if (!rd.isEmpty()) {
					try {
						rd = new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rd));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				fs.setCreatedBy(rd);
				fs.setBusinessName(getStringValue(rec[1]));
				fs.setEmail(getStringValue(rec[2]));
				fs.setCity(getStringValue(rec[3]));
				fs.setState(getStringValue(rec[4]));
				fs.setMobiId(getStringValue(rec[5]));
				fs.setSalutation(getStringValue(rec[6]));
				String mmidStr = getStringValue(rec[7]);
				submerchantList.add(mmidStr);
				logger.info("list is : " + submerchantList);
				fs.setMmId(getStringValue(rec[7]));
				fss.add(fs);
			}
			logger.info("fss : " + fss);
			paginationBean.setItemList(fss);
		} catch (Exception e) {
			logger.error("Exception in sub merchant summary Method : (listadminsubMerchantdefault1) " + e.getMessage(),
					e);
			paginationBean.setItemList(new ArrayList<>());

		}
	}

	private static String getStringValue(Object value) {
		if (value == null) {
			return "";
		} else {
			String strValue = value.toString();
			return strValue.trim().isEmpty() ? "" : strValue;
		}
	}

	// rksubmerchant
	@Override
	@Transactional(readOnly = false)
	public void listsubMerchantUserByMiddefault(final PaginationBean<Merchant> paginationBean, Long merchantid,
			Timestamp fromDate, Timestamp toDate) {
		logger.info("listMerchantUserByMid::");

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;

		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		// Date date = new Date();

		int year = calendar.getWeekYear();
		// int year=2017;
		long mon = date.getMonth() + 1;
		int day = date.getDate() + 1;
		int daybefore = date.getDate();
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

		String mmid = null;

		// New changes Master merchant
		logger.info("Merchant ID :" + merchantid);
		String merchantId = String.valueOf(merchantid);
		MasterMerchant mm = (MasterMerchant) getSessionFactory().createCriteria(MasterMerchant.class)
				.add(Restrictions.eq("merchantId", merchantId)).setMaxResults(1).uniqueResult();

		if (mm != null) {
			mmid = mm.getBusinessName();
			logger.info("Business Name :" + mmid);
		} else {
			logger.info("Master merchant table data is invalid");
			mmid = "null";
		}

//		if (merchantid == 3824) {	
//			logger.info("DEPANSUM : " + merchantid);
//			mmid = "DEPANSUM MALAYSIA SDN BHD";
//
//		} else if (merchantid == 4190) {
//			logger.info("MONETIX : " + merchantid);
//			mmid = "MNTX";
//
//		} else if (merchantid == 4272) {
//			logger.info("ZOTA PAY : " + merchantid);
//			mmid = "ZTP";
//
//		} else if (merchantid == 4347) {
//			logger.info("ECOMMPAY : " + merchantid);
//			mmid = "ECOMMPAY";
//
//		} else {
//			logger.info("DEFAULT DEPANSUM : " + merchantid);
//			mmid = "DEPANSUM MALAYSIA SDN BHD";
//
//		}

		logger.info("MM_ID : " + mmid);

//		sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
//                + "mi.SUB_MERCHANT_MID ,m.ID,m.MM_ID  from MERCHANT m  "
//                + "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID IN ('DEPANSUM MALAYSIA SDN BHD','MNTX') order by m.ACTIVATE_DATE desc ";
//		
		sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
				+ "mi.SUB_MERCHANT_MID ,m.ID from MERCHANT m  "
				+ "INNER JOIN MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID = :mmid order by m.ACTIVATE_DATE desc ";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mmid", mmid);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			MID mi = new MID();

			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setCreatedBy(rd);
			}

			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setEmail(rec[2].toString());
			}

			if (rec[3] != null) {
				fs.setCity(rec[3].toString());
			} else {
				fs.setCity("");
			}

			if (rec[4] != null) {
				fs.setState(rec[4].toString());
			}

			if (rec[5] != null) {
				fs.setMobiId(rec[5].toString());
			} else {
				fs.setMobiId("");
			}
			if (rec[6] != null) {
				fs.setSalutation(rec[6].toString());
			} else {
				fs.setSalutation("");
			}

			fss.add(fs);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	@Override
	@Transactional(readOnly = false)
	public int updateMKData(Long m_id, Long merchant_id) {
		logger.info("MerchantDaoImpl:updateMKData");

		Session session = sessionFactory.getCurrentSession();

		String sql = "update PayoutGrandDetail set merchant_fk = :merchant_id where id= :m_id";
		logger.info(" Query :" + sql);

		Query insertQuery = session.createQuery(sql);
		insertQuery.setLong("merchant_id", merchant_id);
		insertQuery.setLong("m_id", m_id);

		int a = insertQuery.executeUpdate();

		return a;
	}

	// rkdashboardchanges
	@Override
	public int getsucesscount(String mid1, String mid2, String mid3, String mid4, String mid5) {
		Query sqlQuery = super.getSessionFactory().createSQLQuery(
				"SELECT COUNT(*) FROM TRANSACTION_REQUEST AS re inner join TRANSACTION_RESPONSE AS rs ON re.TXN_ID=rs.TXN_ID where rs.RESPONSE_CODE=00 AND re.MID in(:mid1,:mid2,:mid3,:mid4,:mid5)");
		sqlQuery.setString("mid1", mid1);
		sqlQuery.setString("mid2", mid2);
		sqlQuery.setString("mid3", mid3);
		sqlQuery.setString("mid4", mid4);
		sqlQuery.setString("mid5", mid5);
		int i = 0;
		try {
			i = ((Number) sqlQuery.uniqueResult()).intValue();
		} catch (NullPointerException e) {
		}
		logger.info("sql query sucessvalues are" + i);
		return i;
	}

	// rk newely added dashboardchanges
	@Override
	public int getfailurecount(String mid1, String mid2, String mid3, String mid4, String mid5) {
		// TODO Auto-generated method stub
		Query sqlQuery = super.getSessionFactory().createSQLQuery(
				"SELECT COUNT(*) FROM TRANSACTION_REQUEST AS re inner join TRANSACTION_RESPONSE AS rs ON re.TXN_ID=rs.TXN_ID where rs.RESPONSE_CODE NOT IN('00') AND re.MID in(:mid1,:mid2,:mid3,:mid4,:mid5)");
		sqlQuery.setString("mid1", mid1);
		sqlQuery.setString("mid2", mid2);
		sqlQuery.setString("mid3", mid3);
		sqlQuery.setString("mid4", mid4);
		sqlQuery.setString("mid5", mid5);
		int i = 0;
		try {
			i = ((Number) sqlQuery.uniqueResult()).intValue();
		} catch (NullPointerException e) {
		}
		logger.info("sql query failurevalues are" + i);
		return i;
	}

	@Override
	public int getumsucesscount(String mid1, String mid2, String mid3, String mid4, String mid5, String mid6) {
		Query sqlQuery = super.getSessionFactory().createSQLQuery(
				"SELECT COUNT(*) FROM  UM_ECOM_TXNRESPONSE AS rs where rs.STATUS IN ('A','C','S') AND rs.F001_MID in(:mid1,:mid2,:mid3,:mid4,:mid5,:mid6)");
		sqlQuery.setString("mid1", mid1);
		sqlQuery.setString("mid2", mid2);
		sqlQuery.setString("mid3", mid3);
		sqlQuery.setString("mid4", mid4);
		sqlQuery.setString("mid5", mid5);
		sqlQuery.setString("mid6", mid6);
		int i = 0;
		try {
			i = ((Number) sqlQuery.uniqueResult()).intValue();
		} catch (NullPointerException e) {
		}
		logger.info("sql query sucessvalues are" + i);
		return i;
	}

	@Override
	public int getumfailurecount(String mid1, String mid2, String mid3, String mid4, String mid5, String mid6) {
		Query sqlQuery = super.getSessionFactory().createSQLQuery(
				"SELECT COUNT(*) FROM  UM_ECOM_TXNRESPONSE AS rs  where rs.F009_RESPCODE NOT IN ('00') AND rs.F001_MID in(:mid1,:mid2,:mid3,:mid4,:mid5,:mid6)");
		sqlQuery.setString("mid1", mid1);
		sqlQuery.setString("mid2", mid2);
		sqlQuery.setString("mid3", mid3);
		sqlQuery.setString("mid4", mid4);
		sqlQuery.setString("mid5", mid5);
		sqlQuery.setString("mid6", mid6);
		int i = 0;
		try {
			i = ((Number) sqlQuery.uniqueResult()).intValue();
		} catch (NullPointerException e) {
		}
		logger.info("sql query failurevalues are" + i);
		return i;
	}
// rk newely added dashboardchanges

//rk productwiseamount
	public void listproductwiseamount(final PaginationBean<ProductWiseAmount> paginationBean, Merchant merchant,
			Model model) {
		logger.info("Inside productwise amount");
		String mid = null, ezywaymid = null, ezyrecmid = null, motomid = null, ezypassmid = null, gpaymid = null,
				ummid = null, umezywaymid = null, umezyrecmid = null, ummotomid = null, splitmid = null,
				umezypassmid = null;

		Query query = null;
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();

		logger.info("check year and mon: " + year + " " + mon);
		logger.info("merchant type is : " + merchant.getMerchantType());
		ProductWiseAmount p = new ProductWiseAmount();
		List<ProductWiseAmount> pr = new ArrayList<ProductWiseAmount>();
		int waycount = 0, wirecount = 0, reccount = 0, linkcount = 0, motocount = 0, walletcount = 0;
		if ((merchant.getMerchantType() == null) || (merchant.getMerchantType().equalsIgnoreCase("P"))) {
			logger.info("Inside paydee");
			if (merchant.getMid().getMid() != null) {

				mid = merchant.getMid().getMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(AMOUNT) from FOR_SETTLEMENT where MONTH(TIME_STAMP)=:mon AND MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);

				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				wirecount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("wirecount", output);

				logger.info(" type is Ezywire and Count is : " + output);

			} else {

				String s = "findoutmore";
				model.addAttribute("wirecount", s);
				logger.info(" type is Ezywire and Count is : " + s);
			}
			if (merchant.getMid().getEzywayMid() != null) {
				mid = merchant.getMid().getEzywayMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(AMOUNT) from FOR_SETTLEMENT where MONTH(TIME_STAMP)=:mon AND MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);

				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				waycount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("waycount", output);
				logger.info(" type is Ezyway and Count is : " + output);
			} else {

				String s = "findoutmore";
				model.addAttribute("waycount", s);
				logger.info(" type is Ezyway and Count is : " + s);
			}
			if (merchant.getMid().getEzyrecMid() != null) {
				mid = merchant.getMid().getEzyrecMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(AMOUNT) from FOR_SETTLEMENT where MONTH(TIME_STAMP)=:mon AND MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);

				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				reccount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("reccount", output);
				logger.info("Type is Ezyrec and Count is " + output);

			} else {

				String s = "findoutmore";
				model.addAttribute("reccount", s);
				logger.info("Type is Ezyrec and Count is " + s);

			}

			if ((merchant.getMid().getMotoMid() != null) && (merchant.getAuth3DS() == "YES"
					|| merchant.getAuth3DS() == "yes" || merchant.getAuth3DS() == "Yes")) {

				mid = merchant.getMid().getMotoMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(AMOUNT) from FOR_SETTLEMENT where MONTH(TIME_STAMP)=:mon AND MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);

				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				linkcount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("linkcount", output);
				logger.info("Type is Ezylink and Count is : " + output);
			} else {

				String s = "findoutmore";
				model.addAttribute("linkcount", s);
				logger.info("Type is Ezylink and Count is : " + s);
			}
			if ((merchant.getMid().getMotoMid() != null) && (merchant.getAuth3DS() != "YES"
					|| merchant.getAuth3DS() != "yes" || merchant.getAuth3DS() != "Yes")) {
				mid = merchant.getMid().getMotoMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(AMOUNT) from FOR_SETTLEMENT where MONTH(TIME_STAMP)=:mon AND MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);
				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				motocount = i;

				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("motocount", output);

				logger.info("Type is Ezymoto and Count is : " + output);
			} else {

				String s = "findoutmore";
				model.addAttribute("motocount", s);
				logger.info("Type is Ezymoto and Count is : " + s);
			}

		}
		if ((merchant.getMerchantType() != null) && (merchant.getMerchantType().equalsIgnoreCase("U") || merchant.getMerchantType().equalsIgnoreCase("FIUU"))) {
			logger.info("Inside Umobile");
			if (merchant.getMid().getUmMid() != null) {

				mid = merchant.getMid().getUmMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(AMOUNT) from FOR_SETTLEMENT where MONTH(TIME_STAMP)=:mon AND MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);
				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				wirecount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);

				model.addAttribute("wirecount", output);

				logger.info(" type is Ezywire and Count is : " + output);
			} else {
				String s = "findoutmore";

				model.addAttribute("wirecount", s);

				logger.info(" type is Ezywire and Count is : " + s);
			}

			if (merchant.getMid().getUmEzywayMid() != null) {
				mid = merchant.getMid().getUmEzywayMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(F007_TXNAMT) from UM_ECOM_TXNRESPONSE where MONTH(TIME_STAMP)=:mon AND F001_MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);
				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				waycount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("waycount", output);

				logger.info(" type is Ezyway and Count is : " + output);
			} else {

				String s = "findoutmore";
				model.addAttribute("waycount", s);
				logger.info(" type is Ezyway and Count is : " + s);
			}
			if (merchant.getMid().getUmEzyrecMid() != null) {
				mid = merchant.getMid().getUmEzyrecMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(F007_TXNAMT) from UM_ECOM_TXNRESPONSE where MONTH(TIME_STAMP)=:mon AND F001_MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);
				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				reccount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("reccount", output);
				logger.info("Type is Ezyrec and Count is " + output);

			} else {

				String s = "findoutmore";
				model.addAttribute("reccount", s);
				logger.info("Type is Ezyrec and Count is " + s);

			}
			if ((merchant.getMid().getUmMotoMid() != null) && (merchant.getAuth3DS() == "YES"
					|| merchant.getAuth3DS() == "yes" || merchant.getAuth3DS() == "Yes")) {
				mid = merchant.getMid().getUmMotoMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(F007_TXNAMT) from UM_ECOM_TXNRESPONSE where MONTH(TIME_STAMP)=:mon AND F001_MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);
				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				linkcount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("linkcount", output);
				logger.info("Type is Ezylink and Count is : " + output);
			} else {

				String s = "findoutmore";
				model.addAttribute("linkcount", s);
				logger.info("Type is Ezylink and Count is : " + s);
			}
			if ((merchant.getMid().getUmMotoMid() != null) && (merchant.getAuth3DS() != "YES"
					|| merchant.getAuth3DS() != "yes" || merchant.getAuth3DS() != "Yes")) {
				mid = merchant.getMid().getUmMotoMid();

				query = super.getSessionFactory().createSQLQuery(
						"select sum(F007_TXNAMT) from UM_ECOM_TXNRESPONSE where MONTH(TIME_STAMP)=:mon AND F001_MID =:mid AND STATUS in('A','S') AND "
								+ "TIME_STAMP like '" + year + "%'");
				query.setString("mid", mid);
				query.setLong("mon", mon);
				int i = 0;
				try {

					i = ((Number) query.uniqueResult()).intValue();
				} catch (NullPointerException e) {

				}
				motocount = i;
				String s = String.valueOf(i);
				Double d = new Double(s);
				d = d / 100;
				NumberFormat myFormat = NumberFormat.getInstance();
				myFormat.setGroupingUsed(true);
				String output = myFormat.format(d);
				model.addAttribute("motocount", output);

				logger.info("Type is Ezymoto and Count is : " + output);
			} else {

				String s = "findoutmore";
				model.addAttribute("motocount", s);
				logger.info("Type is Ezymoto and Count is : " + s);
			}

		}
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motomid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzypassMid() != null) {
			ezypassmid = merchant.getMid().getEzypassMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecmid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getEzywayMid() != null) {
			ezywaymid = merchant.getMid().getEzywayMid();
		}
		if (merchant.getMid().getGpayMid() != null) {
			gpaymid = merchant.getMid().getGpayMid();
		}

		if (merchant.getMid().getUmMid() != null) {
			ummid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			ummotomid = merchant.getMid().getUmMotoMid();
		}
		if (merchant.getMid().getUmEzypassMid() != null) {
			umezypassmid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getUmEzyrecMid() != null) {
			umezyrecmid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umezywaymid = merchant.getMid().getUmEzywayMid();
		}

		if (merchant.getMid().getSplitMid() != null) {
			splitmid = merchant.getMid().getSplitMid();
		}

		query = super.getSessionFactory()
				.createSQLQuery("select sum(AMOUNT) from FOR_SETTLEMENT where MONTH(TIME_STAMP)=:mon AND "
						+ "STATUS IN('BPA','BPS','GPT','GPS') AND MID IN(:mid,:motomid,:ezypassmid,:ezyrecmid,:ezywaymid,:splitmid,:gpaymid,:ummid,:ummotomid,:umezypassmid,:umezyrecmid,:umezywaymid) AND "
						+ "TIME_STAMP like '" + year + "%'");
		query.setString("mid", mid);
		query.setLong("mon", mon);
		query.setString("motomid", motomid);
		query.setString("ezypassmid", ezypassmid);
		query.setString("ezyrecmid", ezyrecmid);
		query.setString("ezywaymid", ezywaymid);
		query.setString("splitmid", splitmid);
		query.setString("gpaymid", gpaymid);
		query.setString("ummid", ummid);
		query.setString("ummotomid", ummotomid);
		query.setString("umezypassmid", umezypassmid);
		query.setString("umezyrecmid", umezyrecmid);
		query.setString("umezywaymid", umezywaymid);
		int i = 0;
		try {

			i = ((Number) query.uniqueResult()).intValue();
		} catch (NullPointerException e) {

		}
		walletcount = i;
		String s = String.valueOf(i);
		Double d = new Double(s);
		d = d / 100;
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		String output = myFormat.format(d);
		model.addAttribute("walletcount", output);

		logger.info(" Type is EWallet and Count is : " + output);
//Total of all products

		int total = wirecount + linkcount + motocount + reccount + waycount + walletcount;
		String stotal = String.valueOf(total);
		Double dd = new Double(stotal);
		dd = dd / 100;
		NumberFormat myFormatt = NumberFormat.getInstance();
		myFormatt.setGroupingUsed(true);
		String outputt = myFormat.format(dd);
		logger.info("Total is " + outputt);

		model.addAttribute("total", outputt);

	}

	// PAYOUT BY DHINESH & RK - START

	@Override
	public PayoutGrandDetail PayoutGrandDetailbymerchantid(Long id) {

		return (PayoutGrandDetail) getSessionFactory().createCriteria(PayoutGrandDetail.class)
				.add(Restrictions.eq("merchant.id", id)).setMaxResults(1).uniqueResult();

	}

	// PAYOUT BY DHINESH & RK - END

	@Override
	public List<UMMidTxnLimit> UmmidTxnlimit() {
		logger.info("dtl");
		return (List<UMMidTxnLimit>) getSessionFactory().createCriteria(UMMidTxnLimit.class).list();
	}

	// PAYOUT BALANCE

	@Override
	public void loadPayoutbalance(final PaginationBean<PayoutModel> paginationBean) {

		ArrayList<PayoutModel> fss = new ArrayList<PayoutModel>();
		String sql1 = null;
//		sql1 = "select t1.NET_AMOUNT,t1.MERCHANTID,t1.DEPOSIT_AMOUNT,t1.SETTLEMENT_DATE,t1.TOTAL_AMOUNT,t2.BUSINESS_NAME,t1.OVERDRAFT_AMOUNT from mobiversa.SETTLEMENT_BALANCE t1 INNER JOIN MERCHANT t2 ON t1.MERCHANTID = t2.ID ";
		sql1 = "select t1.NET_AMOUNT,t1.MERCHANTID,t1.DEPOSIT_AMOUNT,t1.SETTLEMENT_DATE,t1.TOTAL_AMOUNT,t2.BUSINESS_NAME,t1.OVERDRAFT_AMOUNT,t1.BALANCE_NET_AMOUNT from mobiversa.SETTLEMENT_BALANCE t1 INNER JOIN MERCHANT t2 ON t1.MERCHANTID = t2.ID ";

		logger.info("Query : " + sql1);
		logger.info("***********" + fss);

		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);// .addEntity(ForSettlement.class);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery1.list();
		for (Object[] rec : resultSet) {

			PayoutModel fs = new PayoutModel();

			if (rec[0] == null || rec[0].toString().isEmpty()) {

				fs.setNetAmount("");

			} else if (rec[0] != null) {

				String NetAmt = rec[0].toString();

				String amountWithoutCommas = NetAmt.replace(",", "");
				double amount;
				try {
					NumberFormat numberFormat = NumberFormat.getInstance();
					amount = numberFormat.parse(amountWithoutCommas).doubleValue();
				} catch (ParseException e) {
					logger.info("Invalid Net amount format: " + NetAmt);
					return;
				}

				// Format the amount with comma separators
				DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
				String formattedNetAmount = decimalFormat.format(amount);

				logger.info("Formatted Net amount: " + formattedNetAmount);

				fs.setNetAmount(formattedNetAmount);
			}

			if (rec[1] == null || rec[1].toString().isEmpty()) {

				fs.setMerchantId("");
			}

			else if (rec[1] != null) {

				fs.setMerchantId(rec[1].toString());

			}

			if (rec[2] == null || rec[2].toString().isEmpty()) {

				fs.setDepositamount("");

			} else if (rec[2] != null) {

				String DepositAmt = rec[2].toString();
				String amountWithoutCommas = DepositAmt.replace(",", "");
				double amount;
				try {
					NumberFormat numberFormat = NumberFormat.getInstance();
					amount = numberFormat.parse(amountWithoutCommas).doubleValue();
				} catch (ParseException e) {
					logger.info("Invalid Deposit amount format: " + DepositAmt);
					return;
				}

				// Format the amount with comma separators
				DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
				String formattedDepositAmount = decimalFormat.format(amount);

				logger.info("Formatted Deposit amount: " + formattedDepositAmount);

				fs.setDepositamount(formattedDepositAmount);
			}

			if (rec[3] == null || rec[3].toString().isEmpty()) {

				fs.setSettlementdate("");

			} else if (rec[3] != null) {

				fs.setSettlementdate(rec[3].toString());

			}

			if (rec[4] == null || rec[4].toString().isEmpty()) {

				fs.setTotalamount("");

			} else

			if (rec[4] != null) {

				String TotalAmt = rec[4].toString();
				String amountWithoutCommas = TotalAmt.replace(",", "");
				double amount;
				try {
					NumberFormat numberFormat = NumberFormat.getInstance();
					amount = numberFormat.parse(amountWithoutCommas).doubleValue();
				} catch (ParseException e) {
					logger.info("Invalid Total amount format: " + TotalAmt);
					return;
				}

				// Format the amount with comma separators
				DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
				String formattedTotalAmount = decimalFormat.format(amount);

				logger.info("Formatted Total amount: " + formattedTotalAmount);

				fs.setTotalamount(formattedTotalAmount);

			}
			if (rec[5] == null || rec[5].toString().isEmpty()) {

				fs.setBusinessname("");

			} else

			if (rec[5] != null) {

				fs.setBusinessname(rec[5].toString());

			}

			if (rec[6] == null || rec[6].toString().isEmpty() || rec[6].equals("0.00")) {
				fs.setOverDraftAmt("0.00");
			} else {
				if (rec[6] != null) {

					String OverDraftAmt = rec[6].toString();
					String amountWithoutCommas = OverDraftAmt.replace(",", "");
					double amount;
					try {
						NumberFormat numberFormat = NumberFormat.getInstance();
						amount = numberFormat.parse(amountWithoutCommas).doubleValue();
					} catch (ParseException e) {
						logger.info("Invalid OverDraftAmt format: " + OverDraftAmt);
						return;
					}

					// Format the amount with comma separators
					DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
					String formattedDraftAmount = decimalFormat.format(amount);

					logger.info("Formatted OverDraft amount: " + formattedDraftAmount);

					fs.setOverDraftAmt(formattedDraftAmount);
				}
			}

			if (rec[7] == null || rec[7].toString().isEmpty() || rec[7].equals("0.00")) {
				fs.setTotalamount("");
			} else {
				if (rec[7] != null) {

					String totalBalance = rec[7].toString();
					String amountWithoutCommas = totalBalance.replace(",", "");
					double amount;
					try {
						NumberFormat numberFormat = NumberFormat.getInstance();
						amount = numberFormat.parse(amountWithoutCommas).doubleValue();
					} catch (ParseException e) {
						logger.info("Invalid totalBalance format: " + totalBalance);
						return;
					}

					// Format the amount with comma separators
					DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
					String formattedTotalAmount = decimalFormat.format(amount);

					logger.info("Formatted TotalBalance amount: " + formattedTotalAmount);

					fs.setTotalamount(formattedTotalAmount);
				}
			}

			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		logger.info("No of Records in the PAYOUT_DETAIL : " + paginationBean.getItemList().size());

	}

	@Override
	@Transactional(readOnly = false)
	public void updateBalanceNetAmt(String amount, String merchantID) {
		logger.info(" Balance Net Amount Before Update :" + amount);
		logger.info(" merchantId Before Update :" + merchantID);
		Query sqlQuery = null;

		logger.info("Update Balance Net Amount :" + amount);
		logger.info("Update merchantID :" + merchantID);

//		sql="UPDATE mobiversa.SETTLEMENT_BALANCE sb set sb.BALANCE_NET_AMOUNT ='"+amount+"' where sb.MERCHANTID='"+merchantID+"'";
//
//		sqlQuery = super.getSessionFactory().createSQLQuery(sql);

		// logger.info("MerchantDaoImpl:changeMerchantPassWord");
//				String query = "update mobiversa.SETTLEMENT_BALANCE sb set sb.BALANCE_NET_AMOUNT =:amount where sb.MERCHANTID =:merchantID";
//				int rs = sessionFactory.getCurrentSession().createQuery(query).setParameter("amount", amount)
//						.setParameter("merchantID", merchantID).executeUpdate();
//
//		
		Session session = sessionFactory.getCurrentSession();

		String sql = "update mobiversa.SETTLEMENT_BALANCE set BALANCE_NET_AMOUNT = :netAmt where MERCHANTID= :merchant_id ";
		logger.info(" Query :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("netAmt", amount);

		insertQuery.setString("merchant_id", merchantID);
		insertQuery.executeUpdate();
		logger.info("!!! SuccessFully update Balance Net Amount !!!");

	}

	@Override
	public SettlementBalance SearchMerchantSettlement(final String merchantId) {
		// logger.info("MerchantDaoImpl:loadMerchant");
		logger.info("merchantId :" + merchantId);

		return (SettlementBalance) getSessionFactory().createCriteria(SettlementBalance.class)
				.add(Restrictions.eq("merchantId", merchantId)).setMaxResults(1).uniqueResult();
	}

	@Override
	public PayoutBankBalance oldbankbalance() {

		long Id = 1;

		return (PayoutBankBalance) getSessionFactory().createCriteria(PayoutBankBalance.class)
				.add(Restrictions.eq("id", Id)).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateBankAmount(String amount, String date) {
		logger.info(" Amount Before Update :" + amount);
		logger.info(" Date Before Update :" + amount);

		Query sqlQuery = null;

		Session session = sessionFactory.getCurrentSession();

		String sql = "update mobiversa.PAYOUT_BANK_BALANCE  set AMOUNT = :Amt , UPDATED_DATE =:date where ID= :id ";
		logger.info(" Query :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("Amt", amount);
		insertQuery.setLong("id", 1);
		insertQuery.setString("date", date);
		insertQuery.executeUpdate();
		logger.info("!!! SuccessFully update Amount !!!");

	}

	@Override
	@Transactional(readOnly = false)
	public void updateOverdraft(double overdraftAmount, double netAmount, String merchantId) {
		logger.info(" OverdraftAmount Before Update :" + overdraftAmount);
		logger.info(" NetAmount Before Update :" + netAmount);
		logger.info(" MerchantId Before Update :" + merchantId);

		Query sqlQuery = null;
		String netAmt = String.valueOf(netAmount);
		String overDraftAmount = String.valueOf(overdraftAmount);

		Session session = sessionFactory.getCurrentSession();

		String sql = "update mobiversa.SETTLEMENT_BALANCE s  SET s.OVERDRAFT_AMOUNT = :overdraft ,s.NET_AMOUNT = :netamount WHERE s.MERCHANTID= :id";
		logger.info(" Query :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("overdraft", overDraftAmount);
		insertQuery.setString("netamount", netAmt);
		insertQuery.setString("id", merchantId);
		insertQuery.executeUpdate();
		logger.info("!!! SuccessFully update Amount !!!");

	}

	@Override
	@Transactional(readOnly = false)
	public void updateoverdraftwithdepo(double overdraftAmount, double settlementbalance, double deposit,
			double totalBalance, String merchantId) {
		logger.info(" OverdraftAmount Before Update :" + overdraftAmount);
		logger.info(" deposit Before Update :" + deposit);
		logger.info(" MerchantId Before Update :" + merchantId);
		logger.info(" settlementbalance Before Update :" + settlementbalance);
		logger.info(" totalBalance Before Update :" + totalBalance);

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		Query sqlQuery = null;
		String settlementbalanceamount = decimalFormat.format(settlementbalance);
		String depositAmount = decimalFormat.format(deposit);
		String overDraftAmount = decimalFormat.format(overdraftAmount);
		String totalbalance = decimalFormat.format(totalBalance);

		Session session = sessionFactory.getCurrentSession();

		String sql = "update mobiversa.SETTLEMENT_BALANCE s SET s.NET_AMOUNT=:settlementamount, s.OVERDRAFT_AMOUNT = :overdraftamt ,s.DEPOSIT_AMOUNT = :deposit,s.BALANCE_NET_AMOUNT=:totalBalance  WHERE s.MERCHANTID= :id ";
		logger.info(" Query :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("settlementamount", settlementbalanceamount);
		insertQuery.setString("deposit", depositAmount);
		insertQuery.setString("overdraftamt", overDraftAmount);
		insertQuery.setString("totalBalance", totalbalance);
		insertQuery.setString("id", merchantId);

		insertQuery.executeUpdate();
		logger.info("!!! SuccessFully update Amount !!!");

	}

	@Override
	@Transactional(readOnly = false)
	public void updatedepositAmount(double settlementbalance, double deposit, double totalBalance, String merchantId) {

		logger.info(" deposit Before Update :" + deposit);
		logger.info(" MerchantId Before Update :" + merchantId);
		logger.info(" settlementbalance Before Update :" + settlementbalance);

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

//        String formattedAmt = decimalFormat.format(amt);

		Query sqlQuery = null;
		String settlementbalanceamount = decimalFormat.format(settlementbalance);
		String depositAmount = decimalFormat.format(deposit);
		String totalbalance = decimalFormat.format(totalBalance);

		Session session = sessionFactory.getCurrentSession();

		String sql = "update mobiversa.SETTLEMENT_BALANCE s SET s.NET_AMOUNT=:settlementamount,s.DEPOSIT_AMOUNT = :deposit,s.BALANCE_NET_AMOUNT=:totalbalance WHERE s.MERCHANTID= :id ";
		logger.info(" Query :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("settlementamount", settlementbalanceamount);
		insertQuery.setString("deposit", depositAmount);
		insertQuery.setString("totalbalance", totalbalance);
		insertQuery.setString("id", merchantId);

		insertQuery.executeUpdate();
		logger.info("!!! SuccessFully update Amount !!!");

	}

	@Override
	@Transactional(readOnly = false)
	public void updatedepositAmountPayoutGrand(double deposit, Merchant merchantData) {

		logger.info(" deposit Before Update :" + deposit);

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		Query sqlQuery = null;
		String depositAmount = decimalFormat.format(deposit);

		DecimalFormat df1 = new DecimalFormat("#.##");
		String limitamountfinalforpayoutgranddetail = df1.format(deposit);

		if (limitamountfinalforpayoutgranddetail.contains(".")) {
			limitamountfinalforpayoutgranddetail = (String.format("%012d",
					(long) Double.parseDouble(limitamountfinalforpayoutgranddetail.replace(".", ""))));
			logger.info(
					"inside if , final limitamount for payoutgrandetail    " + limitamountfinalforpayoutgranddetail);
		} else {
			limitamountfinalforpayoutgranddetail = (String.format("%012d",
					(long) Double.parseDouble(limitamountfinalforpayoutgranddetail) * 100));
			logger.info(
					" inside else , final limitamount for payoutgrandetail  " + limitamountfinalforpayoutgranddetail);
		}

		Session session = sessionFactory.getCurrentSession();

		String sql = "update mobiversa.PAYOUT_GRAND_DETAIL s SET s.TOPUP_AMOUNT=:deposit WHERE s.ID= :id ";
		logger.info(" Query :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("deposit", limitamountfinalforpayoutgranddetail);
		insertQuery.setLong("id", merchantData.getPayoutGrandDetail().getId());

		insertQuery.executeUpdate();
		logger.info("!!! SuccessFully update Amount !!!");

	}

	@Override
	@Transactional(readOnly = false)
	public FpxTransaction fpxCheckTransaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(FpxTransaction.class);

		criteria.add(Restrictions.eq("mid", mid));
		criteria.add(Restrictions.eq("status", "R"));
		criteria.add(Restrictions.eq("fpxTxnId", txnId));

		return (FpxTransaction) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse ezywayChrckTransaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("f001_MID", mid));
		criteria.add(Restrictions.eq("status", "R"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse ezyAuthCheckTransaaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("f001_MID", mid));
		criteria.add(Restrictions.eq("status", "R"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse ezymotoCheckTransaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("f001_MID", mid));
		criteria.add(Restrictions.eq("status", "R"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse ezylinkCheckTransaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("f001_MID", mid));
		criteria.add(Restrictions.eq("status", "R"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement boostCheckTransaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("mid", mid));
		criteria.add(Restrictions.eq("status", "BPR"));
		criteria.add(Restrictions.eq("rrn", txnId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement grabCheckTransaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("mid", mid));
		criteria.add(Restrictions.eq("status", "GRF"));
		criteria.add(Restrictions.eq("rrn", txnId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public EwalletTxnDetails m1PayCheckTransaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(EwalletTxnDetails.class);

		criteria.add(Restrictions.eq("mid", mid));
		criteria.add(Restrictions.in("status", Arrays.asList("TPR", "SPR")));
		criteria.add(Restrictions.eq("tngTxnId", txnId));

		return (EwalletTxnDetails) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public RefundRequest checkTranactionIsValid(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(RefundRequest.class);

		criteria.add(Restrictions.eq("txnId", txnId));

		return (RefundRequest) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public FpxTransaction findbyFpxData(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(FpxTransaction.class);

		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("fpxTxnId", txnId));

		return (FpxTransaction) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzywayData(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYWAY"));
		criteria.add(Restrictions.eq("h002_VNO", "03"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzyway(String invoiceId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYWAY"));
		criteria.add(Restrictions.eq("h002_VNO", "03"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("F270_ORN", invoiceId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzyAuthData(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYAUTH"));
		criteria.add(Restrictions.eq("h001_MTI", "0110"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzyAuth(String invoiceId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYAUTH"));
		criteria.add(Restrictions.eq("h001_MTI", "0110"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("f270_ORN", invoiceId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzyMotoData(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYMOTO"));
		criteria.add(Restrictions.eq("h002_VNO", "05"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzyMoto(String invoiceId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYMOTO"));
		criteria.add(Restrictions.eq("h002_VNO", "05"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("f270_ORN", invoiceId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzyLinkData(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYMOTO"));
		criteria.add(Restrictions.eq("h002_VNO", "03"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("f023_RRN", txnId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public UMEcomTxnResponse findbyCardEzyLink(String invoiceId) {

		Criteria criteria = getSessionFactory().createCriteria(UMEcomTxnResponse.class);

		criteria.add(Restrictions.eq("txnType", "EZYMOTO"));
		criteria.add(Restrictions.eq("h002_VNO", "03"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("f270_ORN", invoiceId));

		return (UMEcomTxnResponse) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public MID findbyMerchantMid(String mid) {

		Criteria criteria = getSessionFactory().createCriteria(MID.class);

		Disjunction orConditions = Restrictions.disjunction();

		orConditions.add(Restrictions.eq("boostMid", mid));
		orConditions.add(Restrictions.eq("fpxMid", mid));
		orConditions.add(Restrictions.eq("tngMid", mid));
		orConditions.add(Restrictions.eq("shoppyMid", mid));
		orConditions.add(Restrictions.eq("bnplMid", mid));
		orConditions.add(Restrictions.eq("grabMid", mid));
		orConditions.add(Restrictions.eq("ezywayMid", mid));
		orConditions.add(Restrictions.eq("umEzywayMid", mid));
		orConditions.add(Restrictions.eq("ezyrecMid", mid));
		orConditions.add(Restrictions.eq("umEzyrecMid", mid));
//		orConditions.add(Restrictions.eq("EZYWIRE_MID", mid));
		orConditions.add(Restrictions.eq("umMotoMid", mid));
		orConditions.add(Restrictions.eq("motoMid", mid));

		criteria.add(orConditions);

		return (MID) criteria.setMaxResults(1).uniqueResult();

	}

	@Override
	@Transactional(readOnly = false)
	public String cardTransaction(MID mid, String currentDate) {

		String cardAmount = null;
		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();

		String sql = null;

		sql = "SELECT SUM(d.F007_TXNAMT) AS total_value FROM mobiversa.UM_ECOM_TXNRESPONSE d WHERE d.F001_MID IN (:ezywayMid,:umEzywayMid,:motoMid,:ummotoMid,:umMid,:mid,:ezyrecMid,:UmEzyrecMid) AND d.TIME_STAMP like '"
				+ currentDate + "%'";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);

		sqlQuery.setString("ezywayMid", mid.getEzywayMid());
		sqlQuery.setString("umEzywayMid", mid.getUmEzywayMid());
		sqlQuery.setString("motoMid", mid.getMotoMid());
		sqlQuery.setString("ummotoMid", mid.getUmMotoMid());
		sqlQuery.setString("umMid", mid.getUmMid());
		sqlQuery.setString("mid", mid.getMid());
		sqlQuery.setString("ezyrecMid", mid.getEzyrecMid());
		sqlQuery.setString("UmEzyrecMid", mid.getUmEzyrecMid());

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {

			ForSettlement fs = new ForSettlement();
			if (rec[0] != null) {
				cardAmount = rec[0].toString();
			}
		}

		return cardAmount;

	}

	@Override
	@Transactional(readOnly = false)
	public String walletsTransaction(MID mid, String currentDate) {

		String walletAmount = null;

		String sql = null;

		sql = "SELECT SUM(d.AMOUNT) AS total_value FROM mobiversa.FOR_SETTLEMENT d WHERE d.mid IN (:boostMid,:grabMid) AND d.TIME_STAMP like '"
				+ currentDate + "%'";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);

		sqlQuery.setString("boostMid", mid.getBoostMid());
		sqlQuery.setString("grabMid", mid.getGrabMid());

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {

			ForSettlement fs = new ForSettlement();
			if (rec[0] != null) {
				walletAmount = rec[0].toString();
			}
		}

		return walletAmount;

	}

	@Override
	@Transactional(readOnly = false)
	public String m1PayTranascton(MID mid, String currentDate) {

		String walletAmount = null;

		String sql = null;

		sql = "SELECT SUM(d.AMOUNT) AS total_value FROM mobiversa.EWALLET_TXN_DETAILS d WHERE d.mid IN (:tngMid,:shoppyMid) AND d.TIME_STAMP like '"
				+ currentDate + "%'";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);

		sqlQuery.setString("tngMid", mid.getTngMid());
		sqlQuery.setString("shoppyMid", mid.getShoppyMid());

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {

			ForSettlement fs = new ForSettlement();
			if (rec[0] != null) {
				walletAmount = rec[0].toString();
			}
		}

		return walletAmount;

	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement ezywireCheckTransaaction(String txnId, String mid) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("mid", mid));
		criteria.add(Restrictions.eq("status", "R"));
		criteria.add(Restrictions.eq("rrn", txnId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement findbyCardEzywireData(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("ezywireType", "null"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("rrn", txnId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement findbyCardEzywire(String invoiceId) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("ezywireType", "null"));
		criteria.add(Restrictions.eq("status", "S"));
		criteria.add(Restrictions.eq("invoiceId", invoiceId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement findbyBoostData(String txnId, String txnMid) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("txnType", "BOOST"));
		criteria.add(Restrictions.in("status", Arrays.asList("BPS")));
		criteria.add(Restrictions.eq("rrn", txnId));
		criteria.add(Restrictions.eq("mid", txnMid));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement findbyBoostData(String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("txnType", "BOOST"));
		criteria.add(Restrictions.in("status", Arrays.asList("BPS", "BPA")));
		criteria.add(Restrictions.eq("rrn", txnId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement findbyBoost(String invoiceId) {

		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("txnType", "BOOST"));
		criteria.add(Restrictions.in("status", Arrays.asList("BPS")));
		criteria.add(Restrictions.eq("invoiceId", invoiceId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement findbyGrabData(String txnId) {
		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("txnType", "GRABPAY"));
		criteria.add(Restrictions.in("status", Arrays.asList("GPS")));
		criteria.add(Restrictions.eq("rrn", txnId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement findbyGrab(String invoiceId) {
		Criteria criteria = getSessionFactory().createCriteria(ForSettlement.class);

		criteria.add(Restrictions.eq("txnType", "GRAB"));
		criteria.add(Restrictions.in("status", Arrays.asList("GPS")));
		criteria.add(Restrictions.eq("invoiceId", invoiceId));

		return (ForSettlement) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public EwalletTxnDetails findbyM1PayData(String txnId) {
		Criteria criteria = getSessionFactory().createCriteria(EwalletTxnDetails.class);

		criteria.add(Restrictions.in("status", Arrays.asList("TPS", "SPS")));
		criteria.add(Restrictions.eq("tngTxnId", txnId));

		return (EwalletTxnDetails) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public EwalletTxnDetails findbyM1Pay(String invoiceId) {
		Criteria criteria = getSessionFactory().createCriteria(EwalletTxnDetails.class);

		criteria.add(Restrictions.in("status", Arrays.asList("TPS", "SPS")));
		criteria.add(Restrictions.eq("invoiceId", invoiceId));

		return (EwalletTxnDetails) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public Merchant findbyMerchant(MID mid) {
		Criteria criteria = getSessionFactory().createCriteria(Merchant.class);
		logger.info("getMerchant MID :" + mid.getMerchant());
		criteria.add(Restrictions.eq("id", mid.getMerchant().getId()));

		return (Merchant) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public Merchant findMerchant(String merchantId) {
		Criteria criteria = getSessionFactory().createCriteria(Merchant.class);
		logger.info("merchantId :" + merchantId);
		long merchantIdLong = Long.parseLong(merchantId);
		criteria.add(Restrictions.eq("id", merchantIdLong));

		return (Merchant) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public Merchant findRefundMerchant(long id) {
		Criteria criteria = getSessionFactory().createCriteria(Merchant.class);
		logger.info(" Merchant ID :" + id);
		criteria.add(Restrictions.eq("id", id));

		return (Merchant) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateRefundStatus(String intiatedate, String txnid) {
		logger.info(" while update intiatedate  :" + intiatedate);
		logger.info(" while update txnid  :" + txnid);
		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.REFUND_REQUEST fs set fs.STATUS ='RIP' where  fs.INITIATE_DATE= :intiatedate and fs.TXN_ID = :txnid";

		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("intiatedate", intiatedate);
		insertQuery.setString("txnid", txnid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void updateFinalRefundStatus(String intiatedate, String txnid) {
		logger.info(" while update intiatedate  :" + intiatedate);
		logger.info(" while update txnid  :" + txnid);
		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.REFUND_REQUEST fs set fs.STATUS ='RS' where fs.STATUS=:status and fs.INITIATE_DATE= :intiatedate and fs.TXN_ID = :txnid";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("status", "RIP");
		insertQuery.setString("intiatedate", intiatedate);
		insertQuery.setString("txnid", txnid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalCardEzywayRefundStatus(UMEcomTxnResponse cardEzywayData, String settlementDate, String txnMid) {
		logger.info(" cardEzyway txn Id  :" + cardEzywayData.getF023_RRN());
		logger.info(" cardEzyway txn settlementDate  :" + settlementDate);
		logger.info(" cardEzyway txnMid  :" + txnMid);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.UM_ECOM_TXNRESPONSE fs set fs.STATUS ='R' where fs.STATUS IN ('S') and fs.F023_RRN = :txnid and fs.F001_MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", cardEzywayData.getF023_RRN());
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalfpxRefundStatus(FpxTransaction fpxData, String settlementDate, String txnMid) {
		logger.info(" Fpx txn Id  :" + fpxData.getFpxTxnId());
		logger.info(" Fpx txn settlementDate  :" + settlementDate);
		logger.info(" Fpx txn Mid  :" + txnMid);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.FPX_TRANSACTION fs set fs.STATUS ='R' where fs.STATUS IN ('S') and fs.FPXTXNID = :txnid and fs.MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", fpxData.getFpxTxnId());
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalCardEzywireRefundStatus(String txnId, String settlementDate, String txnMid) {
		logger.info("Ezywire txn Id  :" + txnId);
		logger.info("Ezywire txn MID  :" + txnMid);
		logger.info("Ezywire  settlementDate  :" + settlementDate);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.FOR_SETTLEMENT fs set fs.STATUS ='R' where fs.STATUS IN ('S') and fs.RRN = :txnid and fs.F001_MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);

		insertQuery.setString("txnid", txnId);
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalCardEzyAuthRefundStatus(String txnId, String settlementDate, String txnMid) {
		logger.info("cardEzyAuth txn Id  :" + txnId);
		logger.info(" cardEzyAuth txn MID  :" + txnMid);
		logger.info(" cardEzyAuth  settlementDate  :" + settlementDate);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.UM_ECOM_TXNRESPONSE fs set fs.STATUS ='R' where fs.STATUS IN ('S') and fs.F023_RRN = :txnid and fs.F001_MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalCardEzyEzyMotoRefundStatus(String txnId, String settlementDate, String txnMid) {
		logger.info(" EzyMoto txn Id  :" + txnId);
		logger.info(" EzyMoto txn MID  :" + txnMid);
		logger.info(" EzyMoto  settlementDate  :" + settlementDate);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.UM_ECOM_TXNRESPONSE fs set fs.STATUS ='R' where fs.STATUS IN ('S') and fs.F023_RRN = :txnid and fs.F001_MID = :txnMid";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalCardEzyLinkRefundStatus(String txnId, String settlementDate, String txnMid) {
		logger.info(" EzyLink txn Id  :" + txnId);
		logger.info(" EzyLink txn MID  :" + txnMid);
		logger.info(" EzyLink  settlementDate  :" + settlementDate);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.UM_ECOM_TXNRESPONSE fs set fs.STATUS ='R' where fs.STATUS IN ('S') and fs.F023_RRN = :txnid and fs.F001_MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)

	public void finalBoostRefundStatus(String txnId, String settlementDate, String txnMid) {
		logger.info("Boost txn Id  :" + txnId);
		logger.info("Boost txn MID  :" + txnMid);
		logger.info("Boost  settlementDate  :" + settlementDate);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.FOR_SETTLEMENT fs set fs.STATUS ='BPR' where fs.STATUS IN ('BPS') AND fs.RRN = :txnid AND fs.MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalGrabRefundStatus(String txnId, String settlementDate, String txnMid) {
		logger.info("Grab txn Id  :" + txnId);
		logger.info("Grab txn settlementDate  :" + settlementDate);
		logger.info("Grab txn txnMid  :" + txnMid);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.FOR_SETTLEMENT fs set fs.STATUS ='GPR' where fs.STATUS IN ('GPS') AND fs.RRN = :txnid AND fs.MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public void finalM1PayRefundStatus(String txnId, String settlementDate, String txnMid) {
		logger.info("M1Pay txn Id  :" + txnId);
		logger.info("M1Pay txn settlementDate  :" + settlementDate);
		logger.info("M1Pay txn txnMid  :" + txnMid);

		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.EWALLET_TXN_DETAILS fs SET fs.STATUS = CASE WHEN fs.STATUS = 'TPS' THEN 'TPR' WHEN fs.STATUS = 'SPS' THEN 'SPR' "
				+ "ELSE fs.STATUS END WHERE (fs.STATUS = 'TPS' OR fs.STATUS = 'SPS') AND fs.TNG_TXN_ID = :txnid AND fs.MID = :txnMid ";
		logger.info("final status update sql Query  :" + sql);
		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("txnMid", txnMid);
		insertQuery.executeUpdate();

	}

	@Override
	@Transactional(readOnly = false)
	public boolean refundRequeststatusUpdate(String txnId, String settlementDate) {

		logger.info("Txnid  :" + txnId);
		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.REFUND_REQUEST fs set fs.STATUS ='RS' where  fs.TXN_ID = :txnid and FS.SETTLEMENT_DATE = :settlementDate";

		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("settlementDate", settlementDate);
		insertQuery.executeUpdate();

		return true;

	}

	@Override
	@Transactional(readOnly = false)
	public boolean refundRequeststatusUpdateBoost(String txnId, String settlementDate, String rfNum) {

		logger.info("Txnid  :" + txnId);
		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.REFUND_REQUEST fs set fs.STATUS ='RS' , fs.REFUND_TXN_ID = :rfTxnId where  fs.TXN_ID = :txnid and FS.SETTLEMENT_DATE = :settlementDate";

		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("rfTxnId", rfNum);
		insertQuery.setString("settlementDate", settlementDate);
		insertQuery.executeUpdate();

		return true;

	}

	@Override
	@Transactional(readOnly = false)
	public boolean refundRequestCardstatusUpdate(String txnId, String settlementDate) {

		logger.info("Txnid  :" + txnId);
		Session session = sessionFactory.getCurrentSession();
		Query sqlQuery = null;
		String sql = null;
		sql = "UPDATE mobiversa.REFUND_REQUEST fs set fs.STATUS ='RIP' where  fs.TXN_ID = :txnid and FS.SETTLEMENT_DATE = :settlementDate";

		Query insertQuery = session.createSQLQuery(sql);
		insertQuery.setString("txnid", txnId);
		insertQuery.setString("settlementDate", settlementDate);
		insertQuery.executeUpdate();

		return true;

	}

	@Override
	@Transactional(readOnly = false)
	public RefundRequest loadRequestRefundList(String intiateDate, String txnId) {

		Criteria criteria = getSessionFactory().createCriteria(RefundRequest.class);

		criteria.add(Restrictions.eq("initiateDate", intiateDate));
		criteria.add(Restrictions.eq("txnId", txnId));

		return (RefundRequest) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public RefundRequest findbyValidRefundAmount(Merchant merchant, String settlementDate) {

		logger.info("SettlementDate :" + settlementDate);
		String umMid = null, umEzywayMid = null, umMotoMid = null, umEzyrecMid = null, umEzypassMid = null,
				splitMid = null, boostmid = null, tngMid = null, shoppyMid = null, grabMid = null;

		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid();
		}
		if (merchant.getMid().getUmEzyrecMid() != null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid() != null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getSplitMid() != null) {
			splitMid = merchant.getMid().getSplitMid();
		}

		if (merchant.getMid().getBoostMid() != null) {
			boostmid = merchant.getMid().getBoostMid();
		}
		if (merchant.getMid().getGrabMid() != null) {
			grabMid = merchant.getMid().getGrabMid();
		}

		if (merchant.getMid().getTngMid() != null) {
			tngMid = merchant.getMid().getTngMid();
		}
		if (merchant.getMid().getShoppyMid() != null) {
			shoppyMid = merchant.getMid().getShoppyMid();
		}
		RefundRequest refundRequest = new RefundRequest();

		logger.info("umMid :" + umMid);
		logger.info("umEzywayMid :" + umEzywayMid);
		logger.info("umMotoMid :" + umMotoMid);
		logger.info("umEzyrecMid :" + umEzyrecMid);
		logger.info("umEzypassMid :" + umEzypassMid);
		logger.info("splitMid :" + splitMid);
		logger.info("boostmid :" + boostmid);
		logger.info("grabMid :" + grabMid);
		logger.info("tngMid :" + tngMid);
		logger.info("shoppyMid :" + shoppyMid);

		String sql = "SELECT replace(round(sum(r.REQUEST_REFUND_AMOUNT),2),',',''),replace(round(sum(r.TXN_AMOUNT),2),',','') FROM mobiversa.REFUND_REQUEST r WHERE r.`MID` IN (:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid,:splitMid,:boostmid,:tngMid,:shoppyMid,:grabMid) AND r.SETTLEMENT_DATE = :settlementDate ";
//		String sql = "select replace(round(sum(e.TXNAMOUNT),2),',',''),replace(round(sum(e.MDRAMOUNT),2),',',''),replace(round(sum(e.NETAMOUNT),2),',',''),e.SETTLE_DATE,e.PAYMENT from mobiversa.BOOST_DLY_RECON e where e.MID in (:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid,:splitMid,:boostmid) and e.PAYMENT in ('S','H') and e.SETTLE_DATE LIKE '"
//				+ formatcurrentdate + "%' and e.REASON is null ";

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("umEzyrecMid", umEzyrecMid);
		sqlQuery.setString("umEzypassMid", umEzypassMid);
		sqlQuery.setString("splitMid", splitMid);
		sqlQuery.setString("boostmid", boostmid);
		sqlQuery.setString("tngMid", tngMid);
		sqlQuery.setString("shoppyMid", shoppyMid);
		sqlQuery.setString("grabMid", grabMid);
		// here one add &&&&&&
		sqlQuery.setString("settlementDate", settlementDate);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());
		for (Object[] rec : resultSet) {

			logger.info("rec " + rec[0]);

			if (rec[0] == "null" || rec[0] == null) {

				refundRequest.setRequestRefundAmount("");
				logger.info("refundRequest :" + refundRequest.getRequestRefundAmount());
			} else if (rec[0] != null || rec[0] != "") {
				logger.info("rec[0].toString() :" + rec[0].toString());

				Double d = new Double(rec[0].toString());
				String pattern = "##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(d);
				refundRequest.setRequestRefundAmount(output);
				logger.info("refundRequest not null :" + refundRequest.getRequestRefundAmount());

			}

		}
		return refundRequest;
	}

	@Override
	@Transactional(readOnly = false)
	public void addDepositAmount(String deposit, String merchantId) {

		logger.info(" deposit Before Update :" + deposit);
		logger.info(" MerchantId Before Update :" + merchantId);

		DecimalFormat decimalFormat = new DecimalFormat("0.00");

		Query sqlQuery = null;

		String depositAmount = decimalFormat.format(Double.parseDouble(deposit));
		logger.info(" depositAmount Update :" + depositAmount);

		Session session = sessionFactory.getCurrentSession();

		String sql = "update mobiversa.SETTLEMENT_BALANCE s SET s.DEPOSIT_AMOUNT = :deposit WHERE s.MERCHANTID= :id ";
		logger.info(" Query :" + sql);
		Query insertQuery = session.createSQLQuery(sql);

		insertQuery.setString("deposit", depositAmount);

		insertQuery.setString("id", merchantId);

		insertQuery.executeUpdate();
		logger.info("!!! SuccessFully update Amount !!!");

	}

	@Override
	public GrabPaymentTxn loadTxnByPartnerTxId(String partnerTxID) {
		return (GrabPaymentTxn) sessionFactory.getCurrentSession().createCriteria(GrabPaymentTxn.class)
				.add(Restrictions.eq("partnerTxId", partnerTxID)).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public GrabPaymentTxn updateGrabPaymentTxn(GrabPaymentTxn gptr) {
		sessionFactory.getCurrentSession().saveOrUpdate(gptr);
		logger.info("update GrabPaymentTxn table for :" + gptr.getPartnerTxId() + "");
		return gptr;
	}

	@Override
	public ForSettlement loadForSettlementByPartnerTxId(String partnerTxId) {
		return (ForSettlement) sessionFactory.getCurrentSession().createCriteria(ForSettlement.class)
				.add(Restrictions.eq("rrn", partnerTxId)).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public ForSettlement saveForSettlement(ForSettlement forSet) {
		sessionFactory.getCurrentSession().saveOrUpdate(forSet);
		logger.info("saving GrabPaymentTxn table for :" + forSet.getTrxId() + "");
		return forSet;

	}

	@Override
	public TerminalDetails loadTerminalByTID(String tid) {
		logger.info("In loadTerminalByTID tid : " + tid);
		TerminalDetails terminal = (TerminalDetails) sessionFactory.getCurrentSession()
				.createCriteria(TerminalDetails.class).add(Restrictions.eq("tid", tid)).setMaxResults(1).uniqueResult();
		return terminal;
	}

	@Transactional(readOnly = false)
	@Override
	public Long getStan(String tid) {
		Long runningTrace = (Long) sessionFactory.getCurrentSession().createCriteria(InternalTable.class)
				.setProjection(Projections.max("stan")).add(Restrictions.eq("tid", tid)).setMaxResults(1)
				.uniqueResult();

		if (runningTrace == null) {
			InternalTable internalTable = saveInternalTable(tid);
			runningTrace = internalTable.getBatchId();
		}
		logger.info("Batch Number of " + tid + "/s is :" + runningTrace);
		return runningTrace;
	}

	@Transactional(readOnly = false)
	public InternalTable saveInternalTable(String tid) {
		TerminalDetails terDetails = loadMID(tid);
		InternalTable internalTable = new InternalTable();
		internalTable.setTid(tid);
		internalTable.setBatchId((long) 1);
		internalTable.setStan((long) 1);
		internalTable.setMid(terDetails.getMerchantId());
		sessionFactory.getCurrentSession().save(internalTable);
		logger.info("saving internal table for :" + tid + "");
		return internalTable;
	}

	public TerminalDetails loadMID(String tid) {
		TerminalDetails transacion = (TerminalDetails) sessionFactory.getCurrentSession()
				.createCriteria(TerminalDetails.class).add(Restrictions.eq("tid", tid)).setMaxResults(1).uniqueResult();
		return transacion;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateInternalTable(InternalTable internalTable) {
		if (internalTable.getStan() != null) {
			String hql = "UPDATE InternalTable set stan = :stan " + "WHERE tid = :tid";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setLong("stan", internalTable.getStan());
			query.setString("tid", internalTable.getTid());
			query.executeUpdate();
			logger.info("updated stan :" + internalTable.getStan());
		}
		if (internalTable.getBatchId() != null) {
			String hql = "UPDATE InternalTable set batchId = :batchId " + "WHERE tid = :tid";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setLong("batchId", internalTable.getBatchId());
			query.setString("tid", internalTable.getTid());
			query.executeUpdate();
			logger.info("updated batchId :" + internalTable.getBatchId());
		}
		if (internalTable.getInvoiceNo() != null) {
			String hql = "UPDATE InternalTable set invoiceNo = :invoiceNo " + "WHERE tid = :tid";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setLong("invoiceNo", internalTable.getInvoiceNo());
			query.setString("tid", internalTable.getTid());
			query.executeUpdate();
			logger.info("updated invoiceNo :" + internalTable.getBatchId());
		}

	}

	public PayoutDetail searchTransaction(String txnId) {
		PayoutDetail payoutTxn = null;

		logger.info("txnId :" + txnId);

		payoutTxn = (PayoutDetail) sessionFactory.getCurrentSession().createCriteria(PayoutDetail.class)
				.add(Restrictions.eq("invoiceIdProof", txnId)).setMaxResults(1).uniqueResult();
		return payoutTxn;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateBoostVoidStatus(String transactionId) {
		logger.info("Boost Void Transaction Id: " + transactionId);
		Session session = sessionFactory.getCurrentSession();
		String sql = "UPDATE mobiversa.FOR_SETTLEMENT fs SET fs.STATUS = 'BPC' "
				+ "WHERE fs.STATUS = 'BPA' AND fs.RRN = :transactionId";
		logger.info("Update Boost Void Status Query: " + sql);

		Query updateQuery = session.createSQLQuery(sql).setString("transactionId", transactionId);

		int rowsAffected = updateQuery.executeUpdate();

		boolean updateSuccessful = rowsAffected > 0;

		if (updateSuccessful) {
			logger.info("Update successful. Rows affected: " + rowsAffected);
		} else {
			logger.info("Update failed. No rows affected.");
		}

		return updateSuccessful;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateBoostRefundStatus(String transactionId) {
		logger.info("Boost Refund Transaction Id: " + transactionId);
		Session session = sessionFactory.getCurrentSession();
		String sql = "UPDATE mobiversa.FOR_SETTLEMENT fs SET fs.STATUS = 'BPR' "
				+ "WHERE fs.STATUS = 'BPS' AND fs.RRN = :transactionId";
		logger.info("Update Boost Refund Status Query: " + sql);

		Query updateQuery = session.createSQLQuery(sql).setString("transactionId", transactionId);

		int rowsAffected = updateQuery.executeUpdate();

		boolean updateSuccessful = rowsAffected > 0;

		if (updateSuccessful) {
			logger.info("Update successful. Rows affected: " + rowsAffected);
		} else {
			logger.info("Update failed. No rows affected.");
		}

		return updateSuccessful;
	}

	@Override
	@Transactional(readOnly = false)
	public EwalletTxnDetails findbyTouchnGoAndShopeePayData(String txnId) {
		Criteria criteria = getSessionFactory().createCriteria(EwalletTxnDetails.class);

		criteria.add(Restrictions.in("status", Arrays.asList("TPS", "SPS")));
		criteria.add(Restrictions.eq("tngTxnId", txnId));

		return (EwalletTxnDetails) criteria.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateTouchnGoRefundStatus(String transactionId, String invoiceId) {
		logger.info("TouchnGo Refund Transaction Id: " + transactionId);
		Session session = sessionFactory.getCurrentSession();
		String sql = "UPDATE mobiversa.EWALLET_TXN_DETAILS fs SET fs.STATUS = 'TPR' "
				+ "WHERE fs.STATUS = 'TPS' AND fs.TNG_TXN_ID = :transactionId AND fs.INVOICE_ID = :invoiceId";
		logger.info("Update TouchnGo Refund Status Query: " + sql);

		Query updateQuery = session.createSQLQuery(sql).setString("transactionId", transactionId).setString("invoiceId",
				invoiceId);

		int rowsAffected = updateQuery.executeUpdate();

		boolean updateSuccessful = rowsAffected > 0;

		if (updateSuccessful) {
			logger.info("Update successful. Rows affected: " + rowsAffected);
		} else {
			logger.info("Update failed. No rows affected.");
		}

		return updateSuccessful;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateShopeePayRefundStatus(String transactionId, String invoiceId) {
		logger.info("ShopeePay Refund Transaction Id: " + transactionId);
		Session session = sessionFactory.getCurrentSession();
		String sql = "UPDATE mobiversa.EWALLET_TXN_DETAILS fs SET fs.STATUS = 'SPR' "
				+ "WHERE fs.STATUS = 'SPS' AND fs.TNG_TXN_ID = :transactionId AND fs.INVOICE_ID = :invoiceId";
		logger.info("Update ShopeePay Refund Status Query: " + sql);

		Query updateQuery = session.createSQLQuery(sql).setString("transactionId", transactionId).setString("invoiceId",
				invoiceId);

		int rowsAffected = updateQuery.executeUpdate();

		boolean updateSuccessful = rowsAffected > 0;

		if (updateSuccessful) {
			logger.info("Update successful. Rows affected: " + rowsAffected);
		} else {
			logger.info("Update failed. No rows affected.");
		}

		return updateSuccessful;
	}

	@Override
	public MobileUser loadMobileUserById(Long id) {
		logger.info("Merchant ID: " + id);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("merchant.id", id))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public MobileUser findMobileuserByMerchantId(Merchant merchant, String manualSettlement) {
		logger.info("Merchant ID -----> :" + merchant.getId());
		logger.info("manualSettlement @@@@@@ :" + manualSettlement);

		String sql = "UPDATE MOBILE_USER SET ENABLE_SETTELEMENT_PAYOUT = :manualSettlement WHERE MERCHANT_FK = :merchantId";
		logger.info(sql);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("manualSettlement", manualSettlement);
		query.setParameter("merchantId", merchant.getId());
		query.executeUpdate();
		logger.info(query + "Runned Successfully");
		return null;
	}
	@Override
	public void updateAccountEnquiryAndQuickPayout(Merchant merchant,String accountEnquiryEnabled,String quickPayoutEnabled/*, String quickPayoutUrl*/) {
		logger.info("Merchant ID :" + merchant.getId());
		logger.info("Before updation Account Enquiry :" + accountEnquiryEnabled);
		logger.info("Before updation Quick Payout :" + quickPayoutEnabled);

		/*String sql = "UPDATE MOBILE_USER SET ENABLE_ACCOUNT_ENQUIRY = :accountEnquiryEnabled,ENABLEQUICKPAYOUT = :quickPayoutEnabled,QUICKPAYOUTURL = :quickPayoutUrl WHERE MERCHANT_FK = :merchantId";*/
		String sql = "UPDATE MOBILE_USER SET ENABLE_ACCOUNT_ENQUIRY = :accountEnquiryEnabled,ENABLEQUICKPAYOUT = :quickPayoutEnabled WHERE MERCHANT_FK = :merchantId";

		logger.info("SQL UPDATE QUERY : " + sql);

		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("accountEnquiryEnabled", accountEnquiryEnabled);
		query.setParameter("quickPayoutEnabled", quickPayoutEnabled);
		/*query.setParameter("quickPayoutUrl", quickPayoutUrl);*/
		query.setParameter("merchantId", merchant.getId());

		int result = query.executeUpdate();
		logger.info("Update Successful. " + result + " record(s) affected.");
	}


	public PayoutDepositDetails checkdepositDetails(String referenceNo) {
		logger.info("MerchantDaoImpl:loadMerchant Reference No :" + referenceNo);
		return (PayoutDepositDetails) getSessionFactory().createCriteria(PayoutDepositDetails.class)
				.add(Restrictions.eq("referenceNo", referenceNo)).setMaxResults(1).uniqueResult();
	}

	// Payout Withdraw
	@Override
	public void updateWithdrawdetailsInWithdrawalTransactionDetailsTable(
			WithdrawalTransactionsDetails withdrawDetails) {
		sessionFactory.getCurrentSession().save(withdrawDetails);
	}

	@Override
	public PayoutBankBalance loadBankbalance() {
		logger.info("To get bank balance table data ");
		return (PayoutBankBalance) getSessionFactory().createCriteria(PayoutBankBalance.class).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public void exportsubMerchant(final PaginationBean<Merchant> paginationBean, String type) {

		List<Merchant> fss = new ArrayList<Merchant>();

		String sql = null;

		sql = " SELECT m.ACTIVATE_DATE,m.BUSINESS_NAME,m.EMAIL,m.CITY,m.STATE, "
				+ "mi.SUB_MERCHANT_MID ,m.ID ,m.MM_ID from mobiversa.MERCHANT m  "
				+ "INNER JOIN mobiversa.MID mi ON m.ID = mi.MERCHANT_FK where m.MM_ID LIKE '" + type
				+ "%' order by m.ACTIVATE_DATE desc ";

		logger.info("Query for export: " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);

//   		sqlQuery.setString("type", type);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			MID mi = new MID();

			String rd = null;
			if (rec[0] != null) {
				try {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setCreatedBy(rd);
			}

			if (rec[1] != null) {
				fs.setBusinessName(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setEmail(rec[2].toString());
			}

			if (rec[3] != null) {
				fs.setCity(rec[3].toString());
			} else {
				fs.setCity("");
			}

			if (rec[4] != null) {
				fs.setState(rec[4].toString());
			}

			if (rec[5] != null) {
				fs.setMobiId(rec[5].toString());
			} else {
				fs.setMobiId("");
			}

			if (rec[6] != null) {

				fs.setSalutation(rec[6].toString());

			} else {
				fs.setSalutation("");
			}

			if (rec[7] != null) {

				fs.setMmId(rec[7].toString());

			} else {
				fs.setMmId("");
			}

			fss.add(fs);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	@Override
	public MID loadFPXMid(String fpxMID) {
		logger.info("MerchantDaoImpl:load fpx Mid :" + fpxMID);

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("fpxMid", fpxMID, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadBoostMid(String boostMID) {
		logger.info("MerchantDaoImpl:load Boost Mid :" + boostMID);

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("boostMid", boostMID, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MID loadFiuuMid(String fiuuMID) {
		logger.info("MerchantDaoImpl:load Fiuu Mid :" + fiuuMID);

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("fiuuMid", fiuuMID, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}
	
	@Override
	public MID loadEwalletMid(String mID) {
		// logger.info("MerchantDaoImpl:loadmotoMid :"+motomid);

		return (MID) getSessionFactory().createCriteria(MID.class)
				.add(Restrictions.like("mid", mID, MatchMode.ANYWHERE)).setMaxResults(1).uniqueResult();
	}
	/*
	 * @Override public List<String> tocheckholiday(String currentDate1){
	 * 
	 * List<HolidayHistory> checkholiday = (List<HolidayHistory>)
	 * sessionFactory.getCurrentSession()
	 * .createCriteria(HolidayHistory.class).list(); Set<String> holidayDates = new
	 * HashSet<>(); for (HolidayHistory holiday : checkholiday) {
	 * holidayDates.add(holiday.getLeaveDate()); }
	 * 
	 * // Get current date LocalDate currentDate =LocalDate.now(); int workdays = 5;
	 * int addedDays = 0; List<String> finalSettleDates = new ArrayList<>();
	 * 
	 * while (addedDays < workdays) { currentDate = currentDate.plusDays(1);
	 * 
	 * if (currentDate.getDayOfWeek()==DayOfWeek.SATURDAY) { // Check if the date is
	 * not a holiday if (!holidayDates.contains(currentDate.toString())) {
	 * finalSettleDates.add(currentDate.toString()); ++addedDays; } } } return
	 * finalSettleDates; }
	 */

	@Override
	public List<Merchant> loadActiveMerchants() {

		List<Merchant> merchantlist = new ArrayList<Merchant>();
		try {
			return sessionFactory.getCurrentSession().createCriteria(Merchant.class)
					.add(Restrictions.and(Restrictions.like("status", CommonStatus.ACTIVE),
							Restrictions.like("enblPayout", "Yes", MatchMode.ANYWHERE)))
					.list();
		} catch (Exception e) {
			logger.error("Exception in loading ActiveMerchants: " + e.getMessage(), e);
			throw new RuntimeException();
		}
	}

	@Override
	public SettlementDetails loadSettlementDetailsByMerchantId(String merchantID) {
		return (SettlementDetails) getSessionFactory().createCriteria(SettlementDetails.class)
				.add(Restrictions.eq("merchantId", merchantID)).setMaxResults(1).uniqueResult();
	}

	@Override
	public Double getAvaliableBalanceByMerchantId(String merchantId) {
		try {

			String sql = "SELECT * FROM mobiversa.AVAILABLE_BALANCE WHERE MERCHANT_ID = :merchantId";
			Query query = getSessionFactory().createSQLQuery(sql);
			query.setString("merchantId", merchantId);

			Object[] resultSet = (Object[]) query.uniqueResult();

			String availableBalance = (String) resultSet[2].toString().replace(",", "");

			return availableBalance == null || availableBalance.trim().isEmpty() ? 0.00
					: Double.parseDouble(availableBalance);

		} catch (Exception e) {
			logger.error("Error retrieving available balance for merchantId: " + merchantId, e);
			throw new RuntimeException("Error retrieving available balance", e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void saveWithdrawalTransactionDetailsTable(WithdrawalTransactionsDetails withdrawDetails) {
		sessionFactory.getCurrentSession().save(withdrawDetails);
	}

	@Override
	public WithdrawalTransactionsDetails getWithdrawalTransactionsDetailsByPayoutId(String payoutId) {

		return (WithdrawalTransactionsDetails) getSessionFactory().createCriteria(WithdrawalTransactionsDetails.class)
				.add(Restrictions.eq("payoutId", payoutId)).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public void saveBalanceAuditTable(BalanceAudit balanceAudit) {
		sessionFactory.getCurrentSession().save(balanceAudit);
	}

	@Override
	public BalanceAudit checkBalanceAuditEntryUpdatedByWithdrawalMapping(String withdrawEntryID) {
		return (BalanceAudit) getSessionFactory().createCriteria(BalanceAudit.class)
				.add(Restrictions.eq("withdrawalMapping", withdrawEntryID)).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateWithdrawalTransactionDetailsTable(WithdrawalTransactionsDetails withDetails) {

		try {
			String sql = "UPDATE mobiversa.WITHDRAWAL_TRANSACTION_DETAILS wtd "
					+ "SET wtd.POST_ADJUSTMENT_AVAILABLE_BALANCE = :postAdjustmantAvaliableBalance WHERE wtd.ID = :id";
			Query updateQuery = sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setString("postAdjustmantAvaliableBalance", withDetails.getPostWithdrawAvailableBalance())
					.setLong("id", withDetails.getId());

			int rowsAffected = updateQuery.executeUpdate();
		} catch (Exception e) {
			logger.error("Sql error in updating withdrawalTransactionDetails Table: " + e.getMessage(), e);
		}

	}

	@Override
	public void loadRefactoredPayoutbalance(final PaginationBean<PayoutModel> paginationBean) {

		ArrayList<PayoutModel> fss = new ArrayList<PayoutModel>();
		String sql1 = "SELECT m.BUSINESS_NAME, sd.OVERDRAFT_LIMIT, a.AVAILABLE_BALANCE FROM "
				+ "mobiversa.SETTLEMENT_DETAILS sd INNER JOIN MERCHANT m ON sd.MERCHANT_ID = m.ID "
				+ "INNER JOIN mobiversa.AVAILABLE_BALANCE a ON a.MERCHANT_ID = sd.MERCHANT_ID";

		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);
		List<Object[]> resultSet = sqlQuery1.list();
		for (Object[] rec : resultSet) {

			PayoutModel fs = new PayoutModel();

			// Businessname
			if (rec[0] == null || rec[0].toString().isEmpty()) {
				fs.setBusinessname("");
			} else if (rec[0] != null) {
				fs.setBusinessname(rec[0].toString());
			}

			// OverDraftLimit
			if (rec[1] == null || rec[1].toString().isEmpty() || rec[1].equals("0.00")) {
				fs.setOverDraftLimit("0.00");
			} else {
				if (rec[1] != null) {

					String OverDraftAmt = rec[1].toString();
					String amountWithoutCommas = OverDraftAmt.replace(",", "");
					double amount;
					try {
						NumberFormat numberFormat = NumberFormat.getInstance();
						amount = numberFormat.parse(amountWithoutCommas).doubleValue();
					} catch (ParseException e) {
						logger.error("Invalid OverDraftLimit amount format: " + OverDraftAmt);
						return;
					}

					// Format the amount with comma separators
					DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
					String formattedDraftAmount = decimalFormat.format(amount);

					fs.setOverDraftLimit(formattedDraftAmount);
				}
			}

			if (rec[2] == null || rec[2].toString().isEmpty() || rec[2].equals("0.00")) {
				fs.setAvailableBalance("0.00");
			} else {
				if (rec[2] != null) {

					String availableBal = rec[2].toString();
					String amountWithoutCommas = availableBal.replace(",", "");
					double amount;
					try {
						NumberFormat numberFormat = NumberFormat.getInstance();
						amount = numberFormat.parse(amountWithoutCommas).doubleValue();
					} catch (ParseException e) {
						logger.error("Invalid available balance amount format: " + availableBal);
						return;
					}

					// Format the amount with comma separators
					DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");
					String formattedAvailableBal = decimalFormat.format(amount);

					fs.setAvailableBalance(formattedAvailableBal);
				}
			}
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
	}

	@Override
	public List<PayoutBankBalance> loadTotalPayoutBankbalance() {
//		try {
		List<PayoutBankBalance> resultSet = getSessionFactory().createCriteria(PayoutBankBalance.class).list();

//			// Summing all amounts to get TotalPayoutBankBalance
//			double totalAmount = 0.0;
//
//			for (PayoutBankBalance balance : resultSet) {
//				totalAmount += Double.valueOf(balance.getAmount());
//			}
//
//			return String.valueOf(totalAmount);
		return resultSet;
//		} catch (Exception e) {
//			return "0.0";
//		}
	}

	@Override
	public PayeeDetails getPayeeDetails(String resDate, Merchant merchant, String status) {
		PayeeDetails payee = new PayeeDetails();
		List<PayeeDetails> list = new ArrayList<>();

		try {

			String umMid = Optional.ofNullable(merchant.getMid().getUmMid()).orElse(null);
			String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
			String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
			String fpxMid = Optional.ofNullable(merchant.getMid().getFpxMid()).orElse(null);
			String boostMid = Optional.ofNullable(merchant.getMid().getBoostMid()).orElse(null);
			String grabPayMid = Optional.ofNullable(merchant.getMid().getGrabMid()).orElse(null);
			String tngMid = Optional.ofNullable(merchant.getMid().getTngMid()).orElse(null);
			String sppMid = Optional.ofNullable(merchant.getMid().getShoppyMid()).orElse(null);
			String fiuuMid = Optional.ofNullable(merchant.getMid().getFiuuMid()).orElse(null);
			
			logger.info(String.format(
					"MID Values:\n" + "UM MID:         %s\n" + "UM Ezyway MID:  %s\n" + "UM Moto MID:    %s\n"
							+ "FPX MID:        %s\n" + "Boost MID:      %s\n" + "GrabPay MID:    %s\n"
							+ "TNG MID:        %s\n" + "SPP MID:        %s\n"+ "FIUU MID:        %s",
					umMid, umEzywayMid, umMotoMid, fpxMid, boostMid, grabPayMid, tngMid, sppMid, fiuuMid));
					
			
			String sql;
			if ("ezysettleSuccess".equalsIgnoreCase(status)) {
				sql = "SELECT f.BUSINESS_SHORTNAME AS MerchantName, f.EMAIL, a.MID, f.BANK_ACC,"
						+ " f.BANK_NAME,a.SETTLEMENTDATE, a.WITHDRAWFEE AS MDR_AMT, a.NET_AMOUNT_PAYABLE AS NET_AMOUNT,"
						+ "a.BANK_REQUEST_AMOUNT,a.BANK_FEE FROM mobiversa.JUST_SETTLE a INNER JOIN mobiversa.MID m ON a.MID = m.UM_MID OR a.MID = m.UM_MOTO_MID  OR a.MID = m.UM_EZYWAY_MID "
						+ "OR a.MID = m.FPX_MID OR a.MID = m.BOOST_MID OR a.MID = m.GRAB_MID OR a.MID = m.TNG_MID OR a.MID = m.SHOPPY_MID OR a.MID = m.FIUU_MID "
						+ " INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID  WHERE "
						+ " a.MID IN (:umMid,:umEzywayMid,:umMotoMid,:fpxMid,:boostMid,:grabPayMid,:tngMid,:sppMid,:fiuuMid) AND a.status IN ('R') AND a.SETTLEMENTDATE LIKE '"
						+ resDate + "%'  ORDER BY a.TIME_STAMP DESC LIMIT 1";
			} else {
				sql = "SELECT f.BUSINESS_SHORTNAME AS MerchantName, f.EMAIL, a.MID, f.BANK_ACC,"
						+ " f.BANK_NAME,a.SETTLEMENTDATE, a.WITHDRAWFEE AS MDR_AMT, a.NET_AMOUNT_PAYABLE AS NET_AMOUNT,"
						+ "a.BANK_REQUEST_AMOUNT,a.BANK_FEE FROM mobiversa.JUST_SETTLE a INNER JOIN mobiversa.MID m ON "
						+ "a.MID = m.UM_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.UM_EZYWAY_MID OR a.MID = m.FPX_MID OR a.MID = m.BOOST_MID OR a.MID = m.GRAB_MID OR a.MID = m.TNG_MID OR a.MID = m.SHOPPY_MID OR a.MID = m.FIUU_MID INNER JOIN mobiversa.MERCHANT f ON f.MID_FK = m.ID  WHERE "
						+ " a.MID IN (:umMid,:umEzywayMid,:umMotoMid,:fpxMid,:boostMid,:grabPayMid,:tngMid,:sppMid,:fiuuMid) AND a.status IN ('S') AND a.SETTLEMENTDATE LIKE '"
						+ resDate + "%'  ORDER BY a.TIME_STAMP DESC LIMIT 1";
			}

			logger.info("SQL Query: " + sql);

			// Processing the SQL query
			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			sqlQuery.setString("umMid", umMid);
			sqlQuery.setString("umEzywayMid", umEzywayMid);
			sqlQuery.setString("umMotoMid", umMotoMid);
			sqlQuery.setString("fpxMid", fpxMid);
			sqlQuery.setString("boostMid", boostMid);
			sqlQuery.setString("grabPayMid", grabPayMid);
			sqlQuery.setString("tngMid", tngMid);
			sqlQuery.setString("sppMid", sppMid);
			sqlQuery.setString("fiuuMid", fiuuMid);
			
			logger.info("After query execution");

			@SuppressWarnings("unchecked")
			List<Object[]> resultSet = sqlQuery.list();

			logger.info("Number of records in the List: " + resultSet.size());

			for (Object[] rec : resultSet) {

				payee.setMerchantName(rec[0] != null ? rec[0].toString() : "");
				payee.setEmail(rec[1] != null ? rec[1].toString() : "");
				payee.setMid(rec[2] != null ? rec[2].toString() : "");
				payee.setAccountNo(rec[3] != null ? rec[3].toString() : "");
				payee.setBankName(rec[4] != null ? rec[4].toString() : "");
				payee.setSettlementDate(rec[5] != null ? rec[5].toString() : "");
				payee.setWithdrawFee(rec[6] != null ? rec[6].toString() : "0.00");

				payee.setNetAmount(
						rec[7] != null ? String.valueOf(DecimalUtils.round(Double.parseDouble(rec[7].toString()), 2))
								: "0.00");

				payee.setBankRequestAmount(
						rec[8] != null ? String.valueOf(DecimalUtils.round(Double.parseDouble(rec[8].toString()), 2))
								: "0.00");

				payee.setBankFee(rec[9] != null ? rec[9].toString() : "0.00");

				logger.info("Merchant Name : " + payee.getMerchantName() + " Email : " + payee.getEmail() + " Mid : "
						+ payee.getMid() + " Account No :" + payee.getAccountNo() + " Bank Name :" + payee.getBankName()
						+ " Settlemenet Date :" + payee.getSettlementDate() + " Merchant Amount :"
						+ payee.getMerchantAmount() + " Net Amount :" + payee.getNetAmount() + " Agent Name :"
						+ payee.getAgentName() + "Bank Request Amount :" + payee.getBankRequestAmount() + "Bank Fee :"
						+ payee.getBankFee());

			}
		} catch (Exception e) {
			logger.info("Payee Exception : " + e.getMessage());
		}

		return payee;

	}

	@Override
	public void updateEzysettleFinalStatus(String finalPayoutDate, Merchant currentMerchant,
			MerchantService merchantService, Justsettle just, String ezysettleReferenceNo, Payer pAmount)
			throws MobileApiException {

		logger.info("Update Ezysettle Final Status Update Query ");
		logger.info("Reference No :" + ezysettleReferenceNo);
//		Payer pAmountBank;
//		List<Payer> payerListBank = new ArrayList<Payer>();
//		pAmountBank = merchantService.getPayerAmountJust(finalPayoutDate, currentMerchant);

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnectionForEzysettle();
		try {
			String query = "UPDATE JUST_SETTLE fs set fs.STATUS ='R' where fs.STATUS='S' and fs.MID= ? and fs.SETTLEMENTDATE like ? and fs.EZYSETTLE_REFERENCE_NO = ?";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, pAmount.getMid());
			pstmt.setString(2, pAmount.getSettlementDate());
			pstmt.setString(3, ezysettleReferenceNo);

			logger.info("Ezysettle Final Status Update Query : " + pstmt.toString());
			int affectedRows = pstmt.executeUpdate();
			logger.info("Affected Rows : " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Ezysettle Final Status Update Exception :" + e.getMessage());
			throw new MobileApiException(Status.SQL_EXCEPTION);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage());
			}
		}

	}

	@Override
	public void updateEzysettlePendingStatus(String finalPayoutDate, Merchant currentMerchant,
			MerchantService merchantService, Justsettle just, String ezysettleReferenceNo, Payer pAmount)
			throws MobileApiException {

		logger.info("Update Ezysettle Final Status Update Query ");
		logger.info("Reference No :" + ezysettleReferenceNo);
		logger.info("SRC Reference No :" + just.getResponseData().getBatchid());
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnectionForEzysettle();
		try {
			String query = "UPDATE JUST_SETTLE fs set fs.STATUS ='P',fs.SRC_REF_NO = ? where fs.STATUS='S' and fs.MID= ? and fs.SETTLEMENTDATE like ? and fs.EZYSETTLE_REFERENCE_NO = ?";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, just.getResponseData().getBatchid());
			pstmt.setString(2, pAmount.getMid());
			pstmt.setString(3, pAmount.getSettlementDate());
			pstmt.setString(4, ezysettleReferenceNo);

			logger.info("Ezysettle Final Status Update Query : " + pstmt.toString());
			int affectedRows = pstmt.executeUpdate();
			logger.info("Affected Rows : " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Ezysettle Final Status Update Exception :" + e.getMessage());
			throw new MobileApiException(Status.SQL_EXCEPTION);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage());
			}
		}

	}

	@Override
	public JustSettle findByMidandDate(String mid) {
		logger.info("Selected mid :" + mid);
		return (JustSettle) getSessionFactory().createCriteria(JustSettle.class).add(Restrictions.eq("mid", mid))
				.addOrder(Order.desc("timeStamp")).setMaxResults(1).uniqueResult();
	}

	@Override
	public void disableAsyncPayoutHandlerDetailsInMobileUser(MobileUser mobileUserdetails, Long id) {

		try {
			String sql = "UPDATE mobiversa.MOBILE_USER mu SET mu.ENABLEASYNCPAYOUT = :status "
					+ "WHERE mu.MERCHANT_FK = :merchantId";

			Query updateQuery = sessionFactory.getCurrentSession().createSQLQuery(sql)
					.setParameter("status", mobileUserdetails.getEnableAsyncPayout()).setParameter("merchantId", id);

			int rowsAffected = updateQuery.executeUpdate();

			logger.info("Updated async Payout handler as 'NO' in MobileUser table for merchantId: " + id
					+ ". Rows affected: " + rowsAffected);
		} catch (Exception e) {
			logger.error("SQL error in updating MobileUser table: " + e.getMessage(), e);
		}
	}

	@Override
	public Merchant loadMerchantById(String id) {
		logger.info("Merchant loading starts by using USERNAME :" + id);

		return (Merchant) getSessionFactory().createCriteria(Merchant.class)
				.add(Restrictions.eq("id", Long.parseLong(id))).setMaxResults(1).uniqueResult();
	}

	@Override
	public Agent loadagentUsername(String username) {
		logger.info("Merchant loading starts by using USERNAME :" + username);
		return (Agent) getSessionFactory().createCriteria(Agent.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public void listMerchantsFromAgent(final PaginationBean<MerchantDTO> paginationBean, long agentId) {
		logger.info("listMerchantUserByMid::");
		List<MerchantDTO> fss = new ArrayList<MerchantDTO>();
		logger.info("agentId :: " + agentId);
		String sql = null;

		sql = "SELECT m.ACTIVATE_DATE, m.BUSINESS_NAME, m.EMAIL as email, m.BUSINESS_CONTACT_NUMBER as contactNo, m.CITY, m.AUTH_3DS, mi.MID, mi.MOTO_MID, mi.EZYPASS_MID, mi.EZYREC_MID,"
				+ " mi.EZYWAY_MID, mi.UM_MID, mi.UM_MOTO_MID, mi.UM_EZYWAY_MID, mi.UM_EZYREC_MID, m.ID, m.INTEGRATION_PLATFORM, m.MERCHANT_TYPE, mi.SPLIT_MID, mi.BOOST_MID,"
				+ " mi.GRAB_MID, mi.FPX_MID, mi.TNG_MID, mi.SHOPPY_MID, mi.BNPL_MID FROM mobiversa.MERCHANT m INNER JOIN mobiversa.MID mi ON m.ID = mi.MERCHANT_FK "
				+ " WHERE m.agid_fk = '" + agentId + "' ORDER BY m.ACTIVATE_DATE DESC";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			MerchantDTO md = new MerchantDTO();

			String rd = null;
			try {
				if (rec[0] != null) {

					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
					md.setCreatedBy(rd);
				} else {
					md.setCreatedBy("");
				}
			} catch (ParseException e) {
				e.printStackTrace();
				md.setCreatedBy("");
				logger.error("Exception while Date format (ListMerchantsFromAgent) :" + e);
			}

			md.setBusinessName(rec[1] != null ? rec[1].toString() : "");
			md.setEmail(rec[2] != null ? rec[2].toString() : "");
			md.setBusinessContactNo(rec[3] != null ? rec[3].toString() : "");
			md.setCity(rec[4] != null ? rec[4].toString() : "");
			md.setAuth3DS(rec[5] != null ? rec[5].toString() : null);
			md.setMid(rec[6] != null ? rec[6].toString() : "");
			md.setMotoMid(rec[7] != null ? rec[7].toString() : "");
			md.setEzypassMid(rec[8] != null ? rec[8].toString() : "");
			md.setEzyrecMid(rec[9] != null ? rec[9].toString() : "");
			md.setEzywayMid(rec[10] != null ? rec[10].toString() : "");
			md.setUmMid(rec[11] != null ? rec[11].toString() : "");
			md.setUmMotoMid(rec[12] != null ? rec[12].toString() : "");
			md.setUmEzywayMid(rec[13] != null ? rec[13].toString() : "");
			md.setUmEzyrecMid(rec[14] != null ? rec[14].toString() : "");
			md.setId(rec[15] != null ? Long.valueOf(rec[15].toString()) : null);
			md.setSplitMid(rec[18] != null ? rec[18].toString() : "");
			md.setType(rec[16] != null ? rec[16].toString() : "");
			md.setMerchantType(rec[17] != null ? rec[17].toString() : null);
			md.setBoostMid(rec[19] != null ? rec[19].toString() : "");
			md.setGrabMid(rec[20] != null ? rec[20].toString() : "");
			md.setFpxMid(rec[21] != null ? rec[21].toString() : "");
			md.setTngMid(rec[22] != null ? rec[22].toString() : "");
			md.setShoppyMid(rec[23] != null ? rec[23].toString() : "");
			md.setBnplMid(rec[24] != null ? rec[24].toString() : "");

			fss.add(md);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	@Override
	@Transactional(readOnly = false)
	public void listMerchantsFromAgentsearch(final PaginationBean<MerchantDTO> paginationBean, String fromDate,
			String toDate, long agentId) {

		List<MerchantDTO> fss = new ArrayList<MerchantDTO>();
		fromDate = Utils.dateformat(fromDate);
		toDate = Utils.dateformat(toDate);

		String sql = null;

		sql = "SELECT m.ACTIVATE_DATE, m.BUSINESS_NAME,m.EMAIL as email, m.BUSINESS_CONTACT_NUMBER as contactNo, m.CITY, m.AUTH_3DS, mi.MID, mi.UM_MID, mi.UM_MOTO_MID, mi.UM_EZYWAY_MID, mi.UM_EZYREC_MID, m.ID, m.INTEGRATION_PLATFORM, m.MERCHANT_TYPE, mi.SPLIT_MID, mi.BOOST_MID,"
				+ " mi.GRAB_MID, mi.FPX_MID, mi.TNG_MID, mi.SHOPPY_MID, mi.BNPL_MID,m.EMAIL FROM mobiversa.MERCHANT m INNER JOIN mobiversa.MID mi ON m.ID = mi.MERCHANT_FK "
				+ " WHERE m.agid_fk = '" + agentId
				+ "' AND m.ACTIVATE_DATE BETWEEN :fromdate AND :todate ORDER BY m.ACTIVATE_DATE DESC";

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("fromdate", fromDate);
		sqlQuery.setString("todate", toDate);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			MerchantDTO md = new MerchantDTO();

			String rd = null;
			try {
				if (rec[0] != null) {
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
					md.setCreatedBy(rd);
				} else {
					md.setCreatedBy("");
				}

			} catch (ParseException e) {
				e.printStackTrace();
				md.setCreatedBy("");
				logger.error("Exception in date format (listMerchantsFromAgentsearch) : " + e);
			}

			md.setBusinessName(rec[1] != null ? rec[1].toString() : "");
			md.setEmail(rec[2] != null ? rec[2].toString() : "");
			md.setBusinessContactNo(rec[3] != null ? rec[3].toString() : "");
			md.setCity(rec[4] != null ? rec[4].toString() : "");
			md.setAuth3DS(rec[5] != null ? rec[5].toString() : "");
			md.setMid(rec[6] != null ? rec[6].toString() : "");
			md.setUmMid(rec[7] != null ? rec[7].toString() : "");
			md.setUmMotoMid(rec[8] != null ? rec[8].toString() : "");
			md.setUmEzywayMid(rec[9] != null ? rec[9].toString() : "");
			md.setUmEzyrecMid(rec[10] != null ? rec[10].toString() : "");
			md.setId(rec[11] != null ? Long.valueOf(rec[11].toString()) : null);
			md.setSplitMid(rec[12] != null ? rec[12].toString() : "");
			md.setType(rec[13] != null ? rec[13].toString() : "");
			md.setMerchantType(rec[14] != null ? rec[14].toString() : "");
			md.setBoostMid(rec[15] != null ? rec[15].toString() : "");
			md.setGrabMid(rec[16] != null ? rec[16].toString() : "");
			md.setFpxMid(rec[17] != null ? rec[17].toString() : "");
			md.setTngMid(rec[18] != null ? rec[18].toString() : "");
			md.setShoppyMid(rec[19] != null ? rec[19].toString() : "");
			md.setBnplMid(rec[20] != null ? rec[20].toString() : "");
			md.setEmail(rec[21] != null ? rec[21].toString() : "");

			fss.add(md);

		}

		logger.info("fss : " + fss);
		paginationBean.setItemList(fss);

	}

	@Override
	public BankUser loadBankUserByUsername(final String username) {
		logger.info("Merchant loading starts by using USERNAME :" + username);
		return (BankUser) getSessionFactory().createCriteria(BankUser.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

	public void updateSecondaryEmail(String fk, String secondaryEmail) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnectionForEzysettle();
			String query = "UPDATE mobiversa.MERCHANT_INFO mi SET mi.SECONDARY_EMAIL = ? WHERE mi.MERCHANT_FK = ?";
			pstmt = conn.prepareStatement(query);
			// Set parameters
			pstmt.setString(1, secondaryEmail);
			pstmt.setString(2, fk);
			int affectedRows = pstmt.executeUpdate();
			logger.info("Affected Rows: " + affectedRows);
		} catch (SQLException e) {
			logger.error("Ezysettle Final Status Update Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage(), e);
			}
		}
	}

	@Override
	public MerchantInfo loadmerchantInfoByFK(String id) {
		String sql = "SELECT * FROM mobiversa.MERCHANT_INFO i WHERE i.MERCHANT_FK = :merchantFk";
		Query sqlQuery = getSessionFactory().createSQLQuery(sql).addEntity(MerchantInfo.class);
		sqlQuery.setParameter("merchantFk", id);
		logger.info("loadMerchantInfoByFK: " + sql);
		MerchantInfo merchantInfo = (MerchantInfo) sqlQuery.uniqueResult();
		return merchantInfo;
	}

	@Override
	public void insertMerchantInfoByFk(String formattedTime, String fk, String fieldName, String tblName) {
		logger.info("Insert record in MerchantInfo table by MERCHANT_FK :" + fk);
		String sql = "INSERT INTO " + tblName
				+ " (MERCHANT_FK, OTP_EXPIRY_TIME,SUBMERCHANT_APPROVAL_INDICATOR) VALUES (:merchantFk, :otpExpiryTime,:submerchantApprovalIndicator)";
		Query sqlQuery = getSessionFactory().createSQLQuery(sql).setParameter("merchantFk", fk)
				.setParameter("otpExpiryTime", formattedTime).setParameter("submerchantApprovalIndicator", '1');

		int rowsAffected = sqlQuery.executeUpdate();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateOtpTime(String time, String idOrUsername, String field, String tableName) {
		logger.info("Field & table " + field + ", " + tableName);
//        String sql = "UPDATE mobiversa.MERCHANT_INFO SET OTP_EXPIRY_TIME = :time WHERE MERCHANT_FK = :merchantFk";
		String sql = "UPDATE " + tableName + " SET OTP_EXPIRY_TIME = :time WHERE " + field + " = :field";
		Query sqlQuery = getSessionFactory().createSQLQuery(sql).setParameter("time", time).setParameter("field",
				idOrUsername);
		int rowsAffected = sqlQuery.executeUpdate();

	}

	@Override
	public void insertMerchantIfNotFound(String fk, String secondaryEmail) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DBConnection.getConnectionForEzysettle();
			String query = "INSERT INTO mobiversa.MERCHANT_INFO (MERCHANT_FK, SECONDARY_EMAIL) VALUES (?, ?)";
			pstmt = conn.prepareStatement(query);
			// Set parameters
			pstmt.setString(1, fk);
			pstmt.setString(2, secondaryEmail);
			int affectedRows = pstmt.executeUpdate();
			logger.info("Affected Rows: " + affectedRows);
		} catch (SQLException e) {
			logger.error("Ezysettle Final Status Update Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage(), e);
			}
		}
	}
	
	@Override
	public List<String> loadAllMerchant() {
		List<String> merchantNames = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			conn = DBConnection.getConnectionForEzysettle();
			String query = "SELECT i.BUSINESS_NAME FROM mobiversa.MERCHANT i";
			pstmt = conn.prepareStatement(query);
			resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				merchantNames.add(resultSet.getString("BUSINESS_NAME"));
			}
		} catch (SQLException e) {
			logger.error("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage(), e);
			}
		}
		return merchantNames;
	}

	@Override
	public int insertMerchantInfoForJenfi(String id, String uniqueID) {
		try {
			String sql = "INSERT INTO mobiversa.MERCHANT_INFO (MERCHANT_FK, JENFI_REF) " +
					"VALUES (:merchant_fk, :jenfi_ref)";
			Query sqlQuery = getSessionFactory().createSQLQuery(sql)
					.setParameter("merchant_fk", id)
					.setParameter("jenfi_ref", uniqueID);
			int rowsAffected = sqlQuery.executeUpdate();
			return rowsAffected;
		} catch (Exception e) {
			logger.error("Exception occurred while inserting row for - uniqueID insertion "+e);
			throw e;
		}
	}


	@Override
	public int updateuniqueIDForJenfi(String id, String uniqueID) {
		try {
			String sql = "UPDATE mobiversa.MERCHANT_INFO " +
					"SET JENFI_REF = :jenfiRef " +
					"WHERE MERCHANT_FK = :merchant_fk";
			Query sqlQuery = getSessionFactory().createSQLQuery(sql)
					.setParameter("jenfiRef", uniqueID)
					.setParameter("merchant_fk", id);
			int rowsAffected = sqlQuery.executeUpdate();
			return rowsAffected;
		} catch (Exception e) {
			logger.error("Exception occurred while updating  - uniqueID updation "+e);
			throw e;
		}
	}


	//method for getting merchant payment details 
	@Override
	public List<MerchantPaymentDetailsDto> loadMerchantPaymentDetailsByFk(Long id) {
	    List<MerchantPaymentDetailsDto> fss = new ArrayList<>();

	    String sql = "SELECT m.ENABLE_PAYMENT_METHOD, m.PAYMENT_METHOD, m.HOST_TYPE " +
	                 "FROM mobiversa.MERCHANT_PAYMENT_DETAILS m " +
	                 "INNER JOIN mobiversa.MERCHANT me ON m.MERCHANT_FK = me.ID " +
	                 "WHERE m.MERCHANT_FK = :mer_id";

	    try {
	        Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
	        sqlQuery.setLong("mer_id", id);
	        @SuppressWarnings("unchecked")
	        List<Object[]> resultSet = sqlQuery.list();
	        
	        logger.info("SQL QUERY: "+sqlQuery);
	        logger.info("result set size: "+resultSet.size());
	        
	        for (Object[] rec : resultSet) {
	            MerchantPaymentDetailsDto merchantPaymentDetails = new MerchantPaymentDetailsDto();
	            if (rec[0] != null) {
	                merchantPaymentDetails.setEnablePaymentMethod(rec[0].toString());
	            }
	            if (rec[1] != null) {
	                merchantPaymentDetails.setPaymentMethod(rec[1].toString());
	            }
	            if (rec[2] != null) {
	                merchantPaymentDetails.setHostType(rec[2].toString());
	            }

	            fss.add(merchantPaymentDetails);
	          
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        logger.info("error while getting merchant payment details : "+e.getMessage());
	    }

	    return fss; 
	}


@Override
@Transactional(readOnly = true)
public List<MerchantDTO> getMerchantDtoList(List<Merchant> merchantList) {
    List<MerchantDTO> merchantDTOs = new ArrayList<>();

    Map<Long, Object[]> merchantDataMap = fetchMerchantDataForSummary(
    	    merchantList.stream().map(Merchant::getId).collect(Collectors.toList())
    );


    for (Merchant rec : merchantList) {
        MerchantDTO md = new MerchantDTO();

        md.setCreatedBy(formatDate(rec.getActivateDate()));
        md.setBusinessName(getStringOrDefault(rec.getBusinessName()));
        md.setModifiedBy(getStringOrDefault(rec.getModifiedBy()));
        md.setCity(getStringOrDefault(rec.getCity()));
        md.setAuth3DS(getStringOrDefault(rec.getAuth3DS()));

        if (rec.getMid() != null) {
            md.setMid(getStringOrDefault(rec.getMid().getMid()));
            md.setMotoMid(getStringOrDefault(rec.getMid().getMotoMid()));
            md.setEzypassMid(getStringOrDefault(rec.getMid().getEzypassMid()));
            md.setEzyrecMid(getStringOrDefault(rec.getMid().getEzyrecMid()));
            md.setEzywayMid(getStringOrDefault(rec.getMid().getEzywayMid()));
            md.setUmMid(getStringOrDefault(rec.getMid().getUmMid()));
            md.setUmMotoMid(getStringOrDefault(rec.getMid().getUmMotoMid()));
            md.setUmEzywayMid(getStringOrDefault(rec.getMid().getUmEzywayMid()));
            md.setUmEzyrecMid(getStringOrDefault(rec.getMid().getUmEzyrecMid()));
            md.setSplitMid(getStringOrDefault(rec.getMid().getSplitMid()));
            md.setBoostMid(getStringOrDefault(rec.getMid().getBoostMid()));
            md.setGrabMid(getStringOrDefault(rec.getMid().getGrabMid()));
            md.setFpxMid(getStringOrDefault(rec.getMid().getFpxMid()));
            md.setTngMid(getStringOrDefault(rec.getMid().getTngMid()));
            md.setShoppyMid(getStringOrDefault(rec.getMid().getShoppyMid()));
            md.setBnplMid(getStringOrDefault(rec.getMid().getBnplMid()));
            md.setFiuuMid(getStringOrDefault(rec.getMid().getFiuuMid()));
        }

        md.setId(getLongOrDefault(rec.getId()));
        md.setType(getStringOrDefault(rec.getType()));
        md.setMerchantType(getStringOrDefault(rec.getMerchantType()));

        Object[] merchantData = merchantDataMap.get(rec.getId());
        if (merchantData != null) {
            md.setManualSettlement(getStringOrDefault(merchantData[0]));
            md.setFpxHostName(getStringOrDefault(merchantData[1]));
        }

        merchantDTOs.add(md);
    }

    return merchantDTOs;
}


private Map<Long, Object[]> fetchMerchantDataForSummary(List<Long> merchantIds) {
    String sql = "SELECT m.ID, mo.ENABLE_SETTELEMENT_PAYOUT, h.HOST_NAME " +
                 "FROM mobiversa.MERCHANT m " +
                 "INNER JOIN mobiversa.MOBILE_USER mo ON mo.MERCHANT_FK = m.ID " +
                 "INNER JOIN mobiversa.HOST_DETAILS h ON mo.FPX_HOST_ID = h.HOST_ID " +
                 "WHERE m.ID IN (:merchantIds)";

    Query sqlQuery = super.getSessionFactory().createSQLQuery(sql)
            .setParameterList("merchantIds", merchantIds);

    @SuppressWarnings("unchecked")
    List<Object[]> resultSet = sqlQuery.list();

    Map<Long, Object[]> dataMap = new HashMap<>();
    for (Object[] row : resultSet) {
        dataMap.put(((Number) row[0]).longValue(), new Object[]{row[1], row[2]});
    }

    return dataMap;
}


}
