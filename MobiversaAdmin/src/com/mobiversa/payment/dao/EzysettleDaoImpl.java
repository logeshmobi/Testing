package com.mobiversa.payment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.payment.controller.bean.PayoutSettleBean;
import com.mobiversa.payment.exception.MobileApiException;
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.util.DBConnection;
import com.mobiversa.payment.util.Utils;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class EzysettleDaoImpl extends BaseDAOImpl implements EzysettleDao {

	protected final Logger logger = Logger.getLogger(EzysettleDaoImpl.class.getName());

	@Override
	public SettlementMDR loadNetAmountandsettlementdatebyCardEzysettle(String settlementdate, Merchant merchant)
			throws ParseException {
		logger.info("Card Settlement Date format =>  " + settlementdate);

		String umMid = Optional.ofNullable(merchant.getMid().getUmMid()).orElse(null);
		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
		String fiuuMid = Optional.ofNullable(merchant.getMid().getFiuuMid()).orElse(null);
		logger.info("Inside loadNetAmountandsettlementdatebyCard");

		settlementdate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settlementdate));
		//

		String sql = "select replace(round(sum(e.TXN_AMOUNT),2),',',''),replace(round(sum(e.MDR_AMT),2),',',''),replace(round(sum(e.NET_AMOUNT),2),',',''),e.SETTLEMENTDATE,e.STATUS from mobiversa.SETTLEMENT_MDR e where e.MID in (:umMid,:umEzywayMid,:umMotoMid,:fiuuMid) and e.STATUS in ('S') and e.SETTLEMENTDATE LIKE '"
				+ settlementdate + "%' and e.REASON is null ";

		logger.info("Card Query : "
				+ "select replace(round(sum(e.TXN_AMOUNT),2),',',''),replace(round(sum(e.MDR_AMT),2),',',''),replace(round(sum(e.NET_AMOUNT),2),',',''),e.SETTLEMENTDATE,e.STATUS from mobiversa.SETTLEMENT_MDR e where e.MID in ("
				+ umMid + "," + umEzywayMid + "," + umMotoMid +","+ fiuuMid+") and e.STATUS in ('S') and e.SETTLEMENTDATE LIKE '"
				+ settlementdate + "%' and e.REASON is null");
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("fiuuMid", fiuuMid);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());

		SettlementMDR settlementmdr = resultSet.stream().map(rec -> {
			Utils util = new Utils();
			SettlementMDR settledata = new SettlementMDR();

			String txnAmount = rec[0] == null ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[0].toString()));
			settledata.setTxnAmount(txnAmount);

			String mdrAmount = (rec[1] == null || rec[1].toString().isEmpty()) ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[1].toString()));
			settledata.setMdrAmt(mdrAmount);

			String netAmount = (rec[2] == null || rec[2].toString().isEmpty()) ? "0.00"
					: (rec[4].toString().equals("S") || rec[4].toString().equals("SLS")) ? rec[2].toString() : "0.00";
			settledata.setNetAmount(netAmount);

			try {
				String settlementDate = (rec[3] == null || rec[3].toString().isEmpty()) ? ""
						: new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[3].toString()));
				settledata.setSettlementDate(settlementDate);
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				settledata.setSettlementDate(""); // Set default value or handle the error appropriately
			}

			return settledata;
		}).findFirst() // Returns the first SettlementMDR object from the stream
				.orElse(null); // Returns null if no object is found;

		return settlementmdr;

	}

	@Override
	public BoostDailyRecon loadNetAmountandsettlementdatebyBoostEzysettle(String settlementdate, Merchant merchant) {

		String formatcurrentdate = null;
		try {
//			formatcurrentdate = new SimpleDateFormat("yyyyMMdd")
//					.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdate));
			formatcurrentdate = new SimpleDateFormat("yyyyMMdd")
					.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settlementdate));

		} catch (ParseException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.info("Boost Settlement Date format =>  " + formatcurrentdate);

		String umMid = Optional.ofNullable(merchant.getMid().getUmMid()).orElse(null);
		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
		String boostmid = Optional.ofNullable(merchant.getMid().getBoostMid()).orElse(null);
//		logger.info(" Inside loadNetAmountandsettlementdatebyBoost  ");

		ArrayList<BoostDailyRecon> boostdlyrecon = new ArrayList<BoostDailyRecon>();

		String sql = "select replace(round(sum(e.TXNAMOUNT),2),',',''),replace(round(sum(e.MDRAMOUNT),2),',',''),replace(round(sum(e.NETAMOUNT),2),',',''),e.SETTLE_DATE,e.PAYMENT from mobiversa.BOOST_DLY_RECON e where e.MID in (:umMid,:umEzywayMid,:umMotoMid,:boostmid) and e.PAYMENT in ('S') and e.SETTLE_DATE LIKE '"
				+ formatcurrentdate + "%' and e.REASON is null ";

		logger.info("BOOST QUERY: " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("boostmid", boostmid);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());

		BoostDailyRecon boostDailyRecon = resultSet.stream().map(rec -> {
			BoostDailyRecon boostdata = new BoostDailyRecon();
			Utils util = new Utils();

			String txnAmount = (rec[0] == null || rec[0].toString().isEmpty()) ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[0].toString()));
			boostdata.setTxnAmount(txnAmount);

			String mdrAmount = (rec[1] == null || rec[1].toString().isEmpty()) ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[1].toString()));
			boostdata.setMdrAmount(mdrAmount);

			String netAmount = (rec[2] == null || rec[2].toString().isEmpty()) ? "0.00"
					: (rec[4].toString().equals("S")) ? rec[2].toString() : "0.00";
			boostdata.setNetAmount(netAmount);

			try {
				String settlementDate = (rec[3] == null || rec[3].toString().isEmpty()) ? ""
						: new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyyMMdd").parse(rec[3].toString().substring(0, 8)));
				boostdata.setSettleDate(settlementDate);
			} catch (ParseException e) {
				logger.error(e.getMessage());
				boostdata.setSettleDate(""); // Set default value or handle the error appropriately
			}

			return boostdata;

		}).findFirst().orElse(null);

		return boostDailyRecon;

	}

	@Override
	public GrabPayFile loadNetAmountandsettlementdatebyGrabpayEzysettle(String settlementdate, Merchant merchant) {

		try {
			long currentMerchantid = merchant.getId();

			settlementdate = new SimpleDateFormat("yyyy-MM-dd")
					.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settlementdate));

			logger.info(
					"Grab Settlement Date format =>  " + settlementdate + " currentMerchantid " + currentMerchantid);

			String sql = "select replace(round(sum(e.TXNAMOUNT),2),',',''),replace(round(sum(e.MDR),2),',',''),replace(round(sum(e.NETAMT),2),',',''),e.SETTLEMENT_DATE,e.STATUS from mobiversa.GRABPAY_FILE e where e.MERCHANTID = :currentMerchantid and e.STATUS in ('A') and e.SETTLEMENT_DATE like '"
					+ settlementdate + "%' and e.REASON is null ";

			logger.info("GRAB QUERY: " + sql);
			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			sqlQuery.setLong("currentMerchantid", currentMerchantid);

			List<Object[]> resultSet = sqlQuery.list();
			logger.info("resultset size:" + resultSet.size());

			GrabPayFile grabPayFile = resultSet.stream().map(rec -> {
				GrabPayFile grabpaydata = new GrabPayFile();
				Utils util = new Utils();

				String txnAmount = (rec[0] == null || rec[0].toString().isEmpty()) ? "0.00"
						: util.amountFormatWithcomma(Double.parseDouble(rec[0].toString()));
				grabpaydata.setTxnAmount(txnAmount);

				String mdrAmount = (rec[1] == null || rec[1].toString().isEmpty()) ? "0.00"
						: util.amountFormatWithcomma(Double.parseDouble(rec[1].toString()));
				grabpaydata.setMdr(mdrAmount);

				String netAmount = (rec[2] == null || rec[2].toString().isEmpty()) ? "0.00"
						: (rec[4].toString().equals("A")) ? rec[2].toString() : "0.00";
				grabpaydata.setNetAmt(netAmount);

				try {
					String settlementDate = (rec[3] == null || rec[3].toString().isEmpty()) ? ""
							: new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[3].toString()));
					grabpaydata.setSettlementDate(settlementDate);
				} catch (ParseException e) {
					logger.error(e.getMessage());
					grabpaydata.setSettlementDate(""); // Set default value or handle the error appropriately
				}
				return grabpaydata;

			}).findFirst().orElse(null);

			return grabPayFile;
		} catch (Exception e) {
			logger.error("Exception in loading net amount from GRABPAY_FILE for Ezysettle: " + e);
			return new GrabPayFile();
		}

	}

	@Override
	public FpxTransaction loadNetAmountandsettlementdatebyFpxEzysettle(String settlementdate, Merchant merchant) {

		String fpxformatcurrentdate = settlementdate;
//		try {
//			fpxformatcurrentdate = new SimpleDateFormat("dd-MMM-yyyy")
//					.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdate));
//			
//			String formatcurrentdate = new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("dd-MMM-yyyy").parse("17-Sep-2024"));
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		logger.info("Fpx Settlement Date Format  : " + fpxformatcurrentdate);

		String umMid = Optional.ofNullable(merchant.getMid().getUmMid()).orElse(null);
		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
		String fpxmid = Optional.ofNullable(merchant.getMid().getFpxMid()).orElse(null);
		Long merchantId = Optional.ofNullable(merchant.getId()).orElse(null);

		String sql = "select replace(round(sum(e.TXNAMOUNT),2),',',''),replace(round(sum(e.MDR_AMT),2),',',''),replace(round(sum(e.PAYABLEAMT),2),',',''),e.SETTLED_DATE,e.STATUS from mobiversa.FPX_TRANSACTION e where e.MID in (:umMid,:umEzywayMid,:umMotoMid,:fpxmid) and e.STATUS in ('S') and e.SETTLED_DATE = :fpxformatcurrentdate and e.DEBITAUTHCODE = '00' and e.REASON is null ";

		logger.info("FPX QUERY: " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("fpxmid", fpxmid);
		sqlQuery.setString("fpxformatcurrentdate", fpxformatcurrentdate);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());

		FpxTransaction fpxTransaction = resultSet.stream().map(rec -> {
			FpxTransaction fpxTransactionData = new FpxTransaction();
			Utils util = new Utils();

			String txnAmount = (rec[0] == null || rec[0].toString().isEmpty()) ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[0].toString()));
			fpxTransactionData.setTxnAmount(txnAmount);

			String mdrAmount = (rec[1] == null || rec[1].toString().isEmpty()) ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[1].toString()));
			fpxTransactionData.setMdrAmt(mdrAmount);

			String netAmount = (rec[2] == null || rec[2].toString().isEmpty()) ? "0.00"
					: (rec[4].toString().equals("S")) ? rec[2].toString() : "0.00";
			fpxTransactionData.setPayableAmt(netAmount);

			String settlementDate = (rec[3] == null || rec[3].toString().isEmpty()) ? "" : rec[3].toString();
			fpxTransactionData.setSettledDate(settlementDate);

			return fpxTransactionData;

		}).findFirst().orElse(null);

		return fpxTransaction;

	}

	@Override
	public EwalletTxnDetails loadNetAmountandsettlementdatebym1PayEzysettle(String settlementdate, Merchant merchant)
			throws ParseException {

		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
		String tngMid = Optional.ofNullable(merchant.getMid().getTngMid()).orElse(null);
		String shoppyMid = Optional.ofNullable(merchant.getMid().getShoppyMid()).orElse(null);
		logger.info("Inside loadNetAmountandsettlementdateby Tng-Shoppy");
		logger.info("Tng-Shoppy Settlement Date format = > " + settlementdate);

		settlementdate = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd-MMM-yyyy").parse(settlementdate));

		ArrayList<EwalletTxnDetails> etdtransactionlist = new ArrayList<EwalletTxnDetails>();

		String sql = "select '' As AMOUNT,replace(round(sum(e.MDR_AMT),2),',',''),replace(round(sum(e.PAYABLEAMT),2),',',''),e.SETTLED_DATE,e.STATUS from mobiversa.EWALLET_TXN_DETAILS e where e.MID in (:tngMid,:shoppyMid,:umEzywayMid,:umMotoMid) and e.STATUS in ('TPS','SPS') and e.SETTLED_DATE = :settlementdate and e.REASON is null ";

//		logger.info("Tng-Shoppy Query : "
//				+ "select '' As AMOUNT,replace(round(sum(e.MDR_AMT),2),',',''),replace(round(sum(e.PAYABLEAMT),2),',',''),e.SETTLED_DATE,e.STATUS from mobiversa.EWALLET_TXN_DETAILS e where e.MID in ("
//				+ tngMid + "," + shoppyMid + "," + umEzywayMid + "," + umMotoMid
//				+ ") and e.STATUS in ('TPS','SPS') and e.SETTLED_DATE = " + settlementdate + " and e.REASON is null ");
//		String sql = "select '' As AMOUNT,replace(round(sum(e.MDR_AMT),2),',',''),replace(round(sum(e.PAYABLEAMT),2),',',''),e.SETTLED_DATE,e.STATUS "
//				+ "FROM mobiversa.EWALLET_TXN_DETAILS e "
//				+ "INNER JOIN mobiversa.MERCHANT m ON e.MERCHANT_FK = m.ID "
//				+ "WHERE e.STATUS IN ('TPS','SPS') "
//				+ "AND e.SETTLED_DATE = '"+settlementdate+"' "
//				+ "AND e.REASON IS NULL "
//				+ "GROUP BY e.MID";

		logger.info("TNG-SHOPPY QUERY: " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("settlementdate", settlementdate);
		sqlQuery.setString("tngMid", tngMid);
		sqlQuery.setString("shoppyMid", shoppyMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());

		EwalletTxnDetails ewalletTxnDetails = resultSet.stream().map(rec -> {
			EwalletTxnDetails EwalletTxnDetailsData = new EwalletTxnDetails();
			Utils util = new Utils();

			String txnAmount = (rec[0] == null || rec[0].toString().isEmpty()) ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[0].toString()));
			EwalletTxnDetailsData.setAmount(txnAmount);

			String mdrAmount = (rec[1] == null || rec[1].toString().isEmpty()) ? "0.00"
					: util.amountFormatWithcomma(Double.parseDouble(rec[1].toString()));
			EwalletTxnDetailsData.setMdrAmt(mdrAmount);

			String netAmount = (rec[2] == null || rec[2].toString().isEmpty()) ? "0.00"
					: (rec[4].toString().equals("TPS") || rec[4].toString().equals("SPS")) ? rec[2].toString() : "0.00";
			EwalletTxnDetailsData.setPayableAmt(netAmount);

			String settlementDate = (rec[3] == null || rec[3].toString().isEmpty()) ? "" : rec[3].toString();
			EwalletTxnDetailsData.setSettledDate(settlementDate);

			return EwalletTxnDetailsData;

		}).findFirst().orElse(null);

		return ewalletTxnDetails;
	}

	/* update for specific transaction */
	@Override
	public int updateEzysettleAmountForCard(PayoutSettleBean payoutSettleBean) throws MobileApiException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		int statusUpdateCount = 0;
		try {
			String query = "update mobiversa.SETTLEMENT_MDR s set s.EZYSETTLE_AMOUNT =?,s.SETTLEMENT_DATE_REFERENCE = ?,s.STATUS = 'H' , s.REASON ='Just Settle' where s.MID IN (?,?,?,?) and s.NET_AMOUNT = ? and s.SETTLEMENTDATE LIKE ?";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, payoutSettleBean.getEzyAmt());
			pstmt.setString(2, payoutSettleBean.getFormatsettledate());
			pstmt.setString(3, payoutSettleBean.getUmMid());
			pstmt.setString(4, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(5, payoutSettleBean.getUmMotoMid());
			pstmt.setString(6, payoutSettleBean.getFiuuMid());
			pstmt.setString(7, payoutSettleBean.getNetAmt());
			pstmt.setString(8, payoutSettleBean.getFormatsettledate() + "%");

			logger.info("Executing update query: " + pstmt.toString());
			statusUpdateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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

		Date currentDate = new Date();
		// Define the desired date format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Format the current date using the defined format
		String formattedDate = dateFormat.format(currentDate);

		Connection conn2 = null;
		PreparedStatement pstmt2 = null;
		conn2 = DBConnection.getConnection();
		try {
			String query2 = "update mobiversa.SETTLEMENT_MDR s set s.SETTLEMENTDATE = ? where s.MID IN (?,?,?,?) and s.NET_AMOUNT = ? and s.SETTLEMENT_DATE_REFERENCE LIKE ?";
			pstmt2 = conn2.prepareStatement(query2);

			// Set parameters
			pstmt2.setString(1, formattedDate);
			pstmt2.setString(2, payoutSettleBean.getUmMid());
			pstmt2.setString(3, payoutSettleBean.getUmEzywayMid());
			pstmt2.setString(4, payoutSettleBean.getUmMotoMid());
			pstmt2.setString(5, payoutSettleBean.getFiuuMid());
			pstmt2.setString(6, payoutSettleBean.getNetAmt());
			pstmt2.setString(7, payoutSettleBean.getFormatsettledate() + "%");

			logger.info("Executing update query: " + pstmt2.toString());
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new MobileApiException(Status.SQL_EXCEPTION);
		} finally {
			try {
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn2 != null) {
					conn2.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage());
			}
		}
		return statusUpdateCount;
	}

	@Override
	public int updateEzySettleAmountForBoost(PayoutSettleBean payoutSettleBean) throws MobileApiException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		int statusUpdateCount = 0;
		try {

			LocalDate localDate = LocalDate.parse(payoutSettleBean.getFormatsettledate(),
					DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String formattedDate = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			logger.info("After formating date : " + formattedDate);
			String query = "update mobiversa.BOOST_DLY_RECON b set b.EZYSETTLE_AMOUNT = ?,b.SETTLEMENT_DATE_REFERENCE = ?, b.PAYMENT = 'H' , b.REASON ='Just Settle' where b.MID IN (?,?,?,?) and b.NETAMOUNT = ? and b.SETTLE_DATE LIKE ?";
			pstmt = conn.prepareStatement(query);

			/* Set parameters */
			pstmt.setString(1, payoutSettleBean.getEzyAmt());
			pstmt.setString(2, payoutSettleBean.getFormatsettledate());
			pstmt.setString(3, payoutSettleBean.getUmMid());
			pstmt.setString(4, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(5, payoutSettleBean.getUmMotoMid());
			pstmt.setString(6, payoutSettleBean.getBoostmid());
			pstmt.setString(7, payoutSettleBean.getNetAmt());
			pstmt.setString(8, formattedDate + "%");

			logger.info("Executing update query: " + pstmt.toString());
			statusUpdateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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

		Date currentDate = new Date();
		// Define the desired date format
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
		// Format the current date using the defined format
		String ezySettleDate = dateFormat.format(currentDate);

		Connection conn2 = null;
		PreparedStatement pstmt2 = null;
		conn2 = DBConnection.getConnection();
		try {
			String query2 = "update mobiversa.BOOST_DLY_RECON b set b.SETTLE_DATE = ?  where b.MID IN (?,?,?,?) and b.NETAMOUNT = ? and b.SETTLEMENT_DATE_REFERENCE LIKE ?";
			pstmt2 = conn2.prepareStatement(query2);

			// Set parameters
			pstmt2.setString(1, ezySettleDate);
			pstmt2.setString(2, payoutSettleBean.getUmMid());
			pstmt2.setString(3, payoutSettleBean.getUmEzywayMid());
			pstmt2.setString(4, payoutSettleBean.getUmMotoMid());
			pstmt2.setString(5, payoutSettleBean.getBoostmid());
			pstmt2.setString(6, payoutSettleBean.getNetAmt());
			pstmt2.setString(7, payoutSettleBean.getFormatsettledate() + "%");
			logger.info("Executing update query: " + pstmt2.toString());
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new MobileApiException(Status.SQL_EXCEPTION);
		} finally {
			try {
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn2 != null) {
					conn2.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage());
			}
		}
		return statusUpdateCount;
	}

	@Override
	public int updateEzySettleAmountForGrabPay(PayoutSettleBean payoutSettleBean) throws MobileApiException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		int updatecountStatus = 0;
		logger.info("Forsettlement Grabpay : "+payoutSettleBean.getFormatsettledate());
		try {
			String query = "update mobiversa.GRABPAY_FILE g set g.EZYSETTLE_AMOUNT = ?,g.SETTLEMENT_DATE_REFERENCE = ?,g.STATUS = 'H' , g.TX_DATE_REFORMAT = ? , g.REASON ='Just Settle' where g.MERCHANTID =? and g.NETAMT =? and g.SETTLEMENT_DATE LIKE ?";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, payoutSettleBean.getEzyAmt());
			pstmt.setString(2, payoutSettleBean.getFormatsettledate());
			pstmt.setString(3, payoutSettleBean.getTxnFormatDate());
			pstmt.setString(4, String.valueOf(payoutSettleBean.getCurrentMerchantid()));
			pstmt.setString(5, payoutSettleBean.getNetAmt());
			pstmt.setString(6, payoutSettleBean.getFormatsettledate() + "%");

			logger.info("Executing update query: " + pstmt.toString());
			updatecountStatus = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

		Date currentDate = new Date();
		// Define the desired date format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Format the current date using the defined format
		String ezySettleDate = dateFormat.format(currentDate);
		Connection conn2 = null;
		PreparedStatement pstmt2 = null;
		conn2 = DBConnection.getConnection();
		try {
			String query2 = "update mobiversa.GRABPAY_FILE g set g.SETTLEMENT_DATE = ? where g.MERCHANTID =? and g.NETAMT =? and g.SETTLEMENT_DATE_REFERENCE LIKE ?";
			pstmt2 = conn2.prepareStatement(query2);

			// Set parameters
			pstmt2.setString(1, ezySettleDate);
			pstmt2.setString(2, String.valueOf(payoutSettleBean.getCurrentMerchantid()));
			pstmt2.setString(3, payoutSettleBean.getNetAmt());
			pstmt2.setString(4, payoutSettleBean.getFormatsettledate() + "%");

			logger.info("Executing update query: " + pstmt2.toString());
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new MobileApiException(Status.SQL_EXCEPTION);
		} finally {
			try {
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn2 != null) {
					conn2.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage());
			}
		}
		return updatecountStatus;
	}

	@Override
	public int updateEzySettleAmountForFpx(PayoutSettleBean payoutSettleBean) throws MobileApiException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		int updatecountStatus = 0;
		try {
			String query = "update mobiversa.FPX_TRANSACTION f set f.EZYSETTLE_AMOUNT = ?,f.SETTLEMENT_DATE_REFERENCE = ?, f.STATUS = 'H' , f.TX_DATE_REFORMAT =? , f.REASON ='Just Settle' where f.MID IN (?,?,?,?) and f.PAYABLEAMT = ? and f.SETTLED_DATE LIKE ? ";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, payoutSettleBean.getEzyAmt());
			pstmt.setString(2, payoutSettleBean.getSettledate());
			pstmt.setString(3, payoutSettleBean.getSettledate());
			pstmt.setString(4, payoutSettleBean.getUmMid());
			pstmt.setString(5, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(6, payoutSettleBean.getUmMotoMid());
			pstmt.setString(7, payoutSettleBean.getFpxmid());
			pstmt.setString(8, payoutSettleBean.getNetAmt());
			pstmt.setString(9, payoutSettleBean.getSettledate() + "%");

			logger.info("Executing update query: " + pstmt.toString());
			updatecountStatus = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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

		Date currentDate = new Date();
		// Define the desired date format
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		// Format the current date using the defined format
		String ezySettleDate = dateFormat.format(currentDate);

		Connection conn2 = null;
		PreparedStatement pstmt2 = null;
		conn2 = DBConnection.getConnection();
		try {
			String query2 = "update mobiversa.FPX_TRANSACTION f set f.SETTLED_DATE = ? where f.MID IN (?,?,?,?) and f.PAYABLEAMT = ? and f.SETTLEMENT_DATE_REFERENCE like ? ";
			pstmt2 = conn2.prepareStatement(query2);

			// Set parameters
			pstmt2.setString(1, ezySettleDate);
			pstmt2.setString(2, payoutSettleBean.getUmMid());
			pstmt2.setString(3, payoutSettleBean.getUmEzywayMid());
			pstmt2.setString(4, payoutSettleBean.getUmMotoMid());
			pstmt2.setString(5, payoutSettleBean.getFpxmid());
			pstmt2.setString(6, payoutSettleBean.getNetAmt());
			pstmt2.setString(7, payoutSettleBean.getSettledate() + "%");

			logger.info("Executing update query: " + pstmt2.toString());
			pstmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new MobileApiException(Status.SQL_EXCEPTION);
		} finally {
			try {
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn2 != null) {
					conn2.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage());
			}
		}
		return updatecountStatus;

	}

	@Override
	public int updateEzySettleAmountForm1pay(PayoutSettleBean payoutSettleBean) throws MobileApiException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		int affectedRowsStatus = 0;
		try {
			String query = "update mobiversa.EWALLET_TXN_DETAILS f set f.EZYSETTLE_AMOUNT = ?,f.SETTLEMENT_DATE_REFERENCE=?,f.STATUS = 'H' , f.REASON = 'Just Settle' where f.MID IN (?,?,?,?) and f.PAYABLEAMT = ? and f.SETTLED_DATE LIKE ?";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, payoutSettleBean.getEzyAmt());
			pstmt.setString(2, payoutSettleBean.getFormatsettledate());
			pstmt.setString(3, payoutSettleBean.getTngMid());
			pstmt.setString(4, payoutSettleBean.getShoppyMid());
			pstmt.setString(5, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(6, payoutSettleBean.getUmMotoMid());
			pstmt.setString(7, payoutSettleBean.getNetAmt());
			pstmt.setString(8, payoutSettleBean.getFormatsettledate() + "%");
			logger.info("Format Settled Date for TNG & SPP : " + payoutSettleBean.getFormatsettledate());
			logger.info("Update Ezysettle Amount (TNG & SPP ) : " + pstmt.toString());
			affectedRowsStatus = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
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

		Date currentDate = new Date();
		// Define the desired date format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Format the current date using the defined format
		String ezySettleDate = dateFormat.format(currentDate);

		Connection conn2 = null;
		PreparedStatement pstmt2 = null;
		conn2 = DBConnection.getConnection();
		try {
			String query2 = "update mobiversa.EWALLET_TXN_DETAILS f set f.SETTLED_DATE = ? where f.MID IN (?,?,?,?) and f.PAYABLEAMT = ? and f.SETTLEMENT_DATE_REFERENCE LIKE ? ";
			pstmt2 = conn2.prepareStatement(query2);

			// Set parameters
			pstmt2.setString(1, ezySettleDate);
			pstmt2.setString(2, payoutSettleBean.getTngMid());
			pstmt2.setString(3, payoutSettleBean.getShoppyMid());
			pstmt2.setString(4, payoutSettleBean.getUmEzywayMid());
			pstmt2.setString(5, payoutSettleBean.getUmMotoMid());
			pstmt2.setString(6, payoutSettleBean.getNetAmt());
			pstmt2.setString(7, payoutSettleBean.getFormatsettledate() + "%");
			logger.info("formatsettledate : " + payoutSettleBean.getFormatsettledate());
			logger.info("Executing update query for settlement date reference (TNG & SPP ): " + pstmt2.toString());
			int affectedRows = pstmt2.executeUpdate();
			logger.info("affectedRows : " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info("Exception updateEzysettleAmoutm1pay : " + e.getMessage());
			throw new MobileApiException(Status.SQL_EXCEPTION);
		} finally {
			try {
				if (pstmt2 != null) {
					pstmt2.close();
				}
				if (conn2 != null) {
					conn2.close();
				}
			} catch (SQLException e) {
				logger.error("Error while closing connection: " + e.getMessage());
			}
		}
		return affectedRowsStatus;
	}

	@Override
	public int loadTransactionCountbyCard(String settledate, Merchant merchant) {

		logger.info("Card Recheck Settlement date format :" + settledate);
		String umMid = Optional.ofNullable(merchant.getMid().getUmMid()).orElse(null);
		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);

		ArrayList<SettlementMDR> settlementmdr = new ArrayList<SettlementMDR>();

		String sql = "select COUNT(*) from mobiversa.SETTLEMENT_MDR e where e.MID in (:umMid,:umEzywayMid,:umMotoMid) and e.STATUS in ('H') and e.SETTLEMENT_DATE_REFERENCE LIKE '"
				+ settledate + "%' ";

		logger.info("Card Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);

		Number result = (Number) sqlQuery.uniqueResult();
		int count = (result != null) ? result.intValue() : 0;
		logger.info("SETTLEMENT_MDR Recheck Count: " + count);
		return count;

	}

	@Override
	public int loadTransactionCountbyBoost(String formatsettledate, Merchant merchant) {

		logger.info("Boost Recheck Settlement date format :" + formatsettledate);
		String umMid = Optional.ofNullable(merchant.getMid().getUmMid()).orElse(null);
		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
		String boostmid = Optional.ofNullable(merchant.getMid().getBoostMid()).orElse(null);

		ArrayList<BoostDailyRecon> boostdlyrecon = new ArrayList<BoostDailyRecon>();

		String sql = "select COUNT(*) from mobiversa.BOOST_DLY_RECON e where e.MID in (:umMid,:umEzywayMid,:umMotoMid,:boostmid) and e.PAYMENT in ('H') and e.SETTLEMENT_DATE_REFERENCE LIKE '"
				+ formatsettledate + "%' ";

		logger.info("Boost Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("boostmid", boostmid);

		Number result = (Number) sqlQuery.uniqueResult();
		int count = (result != null) ? result.intValue() : 0;
		logger.info("BOOST_DLY_RECON Recheck Count: " + count);
		return count;
	}

	@Override
	public int loadTransactionCountbyGrabpay(String formatsettledate, Merchant merchant) {

		logger.info("Grabpay Recheck Settlement date format :" + formatsettledate);
		long currentMerchantid = merchant.getId();

		logger.info(" currentMerchantid " + currentMerchantid);

		ArrayList<GrabPayFile> GrabPayfile = new ArrayList<GrabPayFile>();

		String sql = "select COUNT(*) from mobiversa.GRABPAY_FILE e where e.MERCHANTID = :currentMerchantid and e.STATUS in ('H') and e.SETTLEMENT_DATE_REFERENCE like '"
				+ formatsettledate + "%' ";

		logger.info("Grabpay Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("currentMerchantid", String.valueOf(currentMerchantid));

		Number result = (Number) sqlQuery.uniqueResult();
		int count = (result != null) ? result.intValue() : 0;
		logger.info("GRABPAY_FILE Recheck Count: " + count);
		return count;

	}

	@Override
	public int loadTransactionCountbyFpx(String formatsettledate, Merchant merchant) {

		logger.info("Fpx Recheck Settlement date format :" + formatsettledate);
		String fpxMid = Optional.ofNullable(merchant.getMid().getFpxMid()).orElse(null);
		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);

		logger.info("Fpx Recheck Settlement date format :" + formatsettledate);

		ArrayList<GrabPayFile> GrabPayfile = new ArrayList<GrabPayFile>();

		String sql = "select COUNT(*) from mobiversa.FPX_TRANSACTION e where  e.MID in (:fpxMid,:umEzywayMid,:umMotoMid)and e.STATUS in ('H') and e.SETTLEMENT_DATE_REFERENCE like '"
				+ formatsettledate + "%' ";

		logger.info("Grabpay Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("fpxMid", fpxMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);

		Number result = (Number) sqlQuery.uniqueResult();
		int count = (result != null) ? result.intValue() : 0;
		logger.info("FPX TRANSACTION Recheck Count: " + count);
		return count;

	}

	@Override
	public int loadTransactionCountbym1Pay(String settledate, String tngMid, String shoppyMid, String umEzywayMid,
			String umMotoMid, Merchant merchant) {

		logger.info("Second Execution ");
		logger.info("M1Pay Recheck Settlement date format :" + settledate);
//		String tngMid = Optional.ofNullable(merchant.getMid().getTngMid()).orElse(null);
//		String shoppyMid = Optional.ofNullable(merchant.getMid().getShoppyMid()).orElse(null);
//		String umEzywayMid = Optional.ofNullable(merchant.getMid().getUmEzywayMid()).orElse(null);
//		String umMotoMid = Optional.ofNullable(merchant.getMid().getUmMotoMid()).orElse(null);
		logger.info("TngMid :" + tngMid + " ShoppyMid :" + shoppyMid + " UmEzywayMid :" + umEzywayMid + " UmMotoMid :"
				+ umMotoMid);

		String sql = "SELECT COUNT(*) from mobiversa.EWALLET_TXN_DETAILS e where e.MID in (:tngMid,:shoppyMid,:umEzywayMid,:umMotoMid) and e.STATUS in ('H') and e.SETTLEMENT_DATE_REFERENCE = :settledate ";

		logger.info("Tng-Shoppy Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("tngMid", tngMid);
		sqlQuery.setString("shoppyMid", shoppyMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("settledate", settledate);

		Number result = (Number) sqlQuery.uniqueResult();
		logger.info("Result : " + result);
		int count = (result != null) ? result.intValue() : 0;
		logger.info("M1pay Recheck Count: " + count);
		logger.info("Result int value : " + result.intValue());
		return count;

//		Criteria criteria = super.getSessionFactory().createCriteria(EwalletTxnDetails.class);
//
//		// Create a Disjunction to handle OR conditions
//		Disjunction disjunction = Restrictions.disjunction();
//
//		// Add MID restrictions manually
//		if (tngMid != null) {
//		    disjunction.add(Restrictions.eq("mid", tngMid));
//		}
//		if (shoppyMid != null) {
//		    disjunction.add(Restrictions.eq("mid", shoppyMid));
//		}
//		if (umEzywayMid != null) {
//		    disjunction.add(Restrictions.eq("mid", umEzywayMid));
//		}
//		if (umMotoMid != null) {
//		    disjunction.add(Restrictions.eq("mid", umMotoMid));
//		}
//
//		// Add common restrictions
//		criteria.add(disjunction);
//		criteria.add(Restrictions.eq("status", "H"));
//		criteria.add(Restrictions.eq("settlementDateReference", settledate));
//
//		// Set projection to count
//		criteria.setProjection(Projections.rowCount());
//
//		// Fetch the count directly as an int
//		Long result = (Long) criteria.uniqueResult();
//
//		logger.info("M1pay Recheck Count: " + result);
//		logger.info("M1pay Recheck Count: " + (result != null ? result.intValue() : 0));
//
//		return (result != null) ? result.intValue() : 0;

	}

	@Override
	public void revertEzySettleStatusForM1pay(PayoutSettleBean payoutSettleBean) throws MobileApiException {

		logger.info(" Revert Ezysettle Status for M1pay ");

		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		try {
			String query = "update mobiversa.EWALLET_TXN_DETAILS f set f.STATUS = ?,f.SETTLED_DATE=?, f.REASON = ? where f.MID IN (?,?,?,?) and f.SETTLEMENT_DATE_REFERENCE LIKE ?";
			pstmt = conn.prepareStatement(query);

			// need to set reason null not string format
			// Set parameters
			pstmt.setString(1, payoutSettleBean.getStatus());
			pstmt.setString(2, payoutSettleBean.getFormatsettledate());
			pstmt.setString(3, null);
			pstmt.setString(4, payoutSettleBean.getTngMid());
			pstmt.setString(5, payoutSettleBean.getShoppyMid());
			pstmt.setString(6, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(7, payoutSettleBean.getUmMotoMid());

			pstmt.setString(8, payoutSettleBean.getFormatsettledate() + "%");
			logger.info("Format Settled Date for TNG & SPP : " + payoutSettleBean.getFormatsettledate());
			logger.info("Executing update query for settlement date reference and Ezysettle Amount (TNG & SPP ) : "
					+ pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
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
	public void revertEzySettleStatusForFpx(PayoutSettleBean payoutSettleBean) throws MobileApiException {
		logger.info(" Revert Ezysettle Status for Fpx ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		try {
			String query = "update mobiversa.FPX_TRANSACTION f set f.SETTLED_DATE = ?, f.STATUS = ? , f.REASON =? where f.MID IN (?,?,?,?) and f.SETTLEMENT_DATE_REFERENCE LIKE ? and f.STATUS = 'H' ";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, payoutSettleBean.getSettledate());
			pstmt.setString(2, payoutSettleBean.getStatus());
			pstmt.setString(3, null);
			pstmt.setString(4, payoutSettleBean.getUmMid());
			pstmt.setString(5, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(6, payoutSettleBean.getUmMotoMid());
			pstmt.setString(7, payoutSettleBean.getFpxmid());
			pstmt.setString(8, payoutSettleBean.getSettledate() + "%");

			logger.info("Executing update query: " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
	public void revertEzySettleStatusForGrab(PayoutSettleBean payoutSettleBean) throws MobileApiException {
		logger.info(" Revert Ezysettle Status for Grab ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		try {
			String query = "update mobiversa.GRABPAY_FILE g set g.SETTLEMENT_DATE = ?,g.REASON = ?,g.STATUS = ? where g.MERCHANTID =? and g.STATUS ='H' and g.SETTLEMENT_DATE_REFERENCE LIKE ?";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, payoutSettleBean.getFormatsettledate());
			pstmt.setString(2, null);
			pstmt.setString(3, payoutSettleBean.getStatus());
			pstmt.setString(4, String.valueOf(payoutSettleBean.getCurrentMerchantid()));
			pstmt.setString(5, payoutSettleBean.getFormatsettledate() + "%");

			logger.info("Executing update query: " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
	public void revertEzysettleStatusUpdateforBoost(PayoutSettleBean payoutSettleBean) throws MobileApiException {
		logger.info(" Revert Ezysettle Status for Boost ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		try {
			String query = "update mobiversa.BOOST_DLY_RECON b set b.SETTLE_DATE = ?, b.PAYMENT = ? , b.REASON =? where b.MID IN (?,?,?,?) and b.PAYMENT = 'H' and b.SETTLEMENT_DATE_REFERENCE LIKE ?";
			pstmt = conn.prepareStatement(query);

			/* Set parameters */
			pstmt.setString(1, payoutSettleBean.getFormatsettledate());
			pstmt.setString(2, payoutSettleBean.getStatus());
			pstmt.setString(3, null);
			pstmt.setString(4, payoutSettleBean.getUmMid());
			pstmt.setString(5, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(6, payoutSettleBean.getUmMotoMid());
			pstmt.setString(7, payoutSettleBean.getBoostmid());
			pstmt.setString(8, payoutSettleBean.getFormatsettledate() + "%");

			logger.info("Executing update query: " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
	public void revertEzysettleStatusUpdateforCard(PayoutSettleBean payoutSettleBean) throws MobileApiException {
		logger.info(" Revert Ezysettle Status for Card ");
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DBConnection.getConnection();
		try {
			String query = "update mobiversa.SETTLEMENT_MDR s set s.STATUS =?,s.SETTLEMENTDATE = ?, s.REASON ='NULL' where s.MID IN (?,?,?,?) and s.STATUS = 'H' and s.SETTLEMENT_DATE_REFERENCE LIKE ?";
			pstmt = conn.prepareStatement(query);

			// Set parameters
			pstmt.setString(1, payoutSettleBean.getStatus());
			pstmt.setString(2, payoutSettleBean.getFormatsettledate());
			pstmt.setString(3, payoutSettleBean.getUmMid());
			pstmt.setString(4, payoutSettleBean.getUmEzywayMid());
			pstmt.setString(5, payoutSettleBean.getUmMotoMid());
			pstmt.setString(6, payoutSettleBean.getFiuuMid());
			pstmt.setString(7, payoutSettleBean.getFormatsettledate() + "%");

			logger.info("Executing update query: " + pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
}
