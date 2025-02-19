package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.PreAuthorization;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.common.bo.TID;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.Transaction;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.common.dto.AgentResponseDTO;
import com.mobiversa.common.dto.MerchantSettlementDTO;
import com.mobiversa.common.dto.TerminalDTO;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.AgentVolumeData;
import com.mobiversa.payment.dto.MerchantComparator;
import com.mobiversa.payment.dto.MerchantVolumeData;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class BoostTransactionDaoImpl extends BaseDAOImpl implements BoostTransactionDao {
	@Override
	@Transactional(readOnly = true)
	public void listAllTransaction(
			final PaginationBean<ForSettlement> paginationBean,
			final ArrayList<Criterion> props, final String date,
			final String date1, final String txnType) {
		//logger.info("Inside   listAllTransaction : " + date + "     " + date1);
		String dat = null;
		String dat1 = null;
		String year1 = null;
		String year2 = null;
		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
		String sql = null;
		/*if(txnType.isEmpty() || txnType == null){
			txnType = "ALL";
		}*/
/*if(date != null){// || !date.equals("1")){
			
			//logger.info(" DD else data:" + date);
			 logger.info("DD else data: " + date1); 
			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//logger.info("date1: " + dat );
			
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//dat = dateFormat.format(date);
			}

		if(date1 != null){// || !date1.equals("1")){
			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}*/
		
		
	
		if ((date == null || date1 == null)
				|| (date.equals("") || date1.equals(""))) 
		{

			Date dt = new Date();

			logger.info("Inside   listAllTransaction 12131313: " + date + "     " + date1);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			dat = dateFormat.format(dt);
			dat = dat + "-01";
			logger.info("change date format:" + dat);
			
			// SimpleDateFormat yr1 = new SimpleDateFormat("yyyy"); year1 =
			//  yr1.format(dt);
			 

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			dat1 = dateFormat1.format(dt1);
			logger.info("date format:" + dat1);

		} else {

			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat1));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		/*
		 * sql =
		 * "select f.city , f.BUSINESS_NAME AS MerchantName ,a.date , a.txn_year , sum(a.AMOUNT)  TotalAmount , ag.ag_name "
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID INNER JOIN agent ag on f.AGID_FK=ag.id " +
		 * "where a.status='S' and DATE between '" + dat + "' and '" + dat1 +
		 * "' and txn_year between '"
		 * +year1+"' and '"+year2+"' group by a.MID,a.DATE order by a.DATE desc"
		 * ;
		 */

		/*
		 * sql =
		 * "select f.city , f.BUSINESS_NAME AS MerchantName ,a.date , a.txn_year , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID INNER JOIN agent ag on f.AGID_FK=ag.id " +
		 * "where a.status='S' and time_stamp between '" + dat + "' and '" +
		 * dat1 + "'  group by a.MID,a.date order by a.time_stamp desc";
		 */

		/*sql = "select f.state , f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+ "where a.status='S' and time_stamp between '"
				+ dat
				+ "' and '"
				+ dat1
				+ "'  group by a.MID,a.date order by a.time_stamp desc";*/
		
		/*sql = "select f.state,f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME,f.ACTIVATE_DATE "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+ "where a.status='S' and time_stamp between :dat  and :dat1"
				+ " group by a.MID,a.date order by a.time_stamp desc";*/
		if(txnType.equals("ALL") || txnType.isEmpty() || txnType == null){
			sql = "select f.state,f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME,f.ACTIVATE_DATE "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "where a.status in ('BPS','BP','BPA','BPC') and time_stamp between :dat  and :dat1"
					+ " group by a.MID,a.date order by a.time_stamp desc";
		}
		/*else if(txnType.equals("CARD")){
			sql = "select f.state,f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME,f.ACTIVATE_DATE "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "where a.status in ('S') and time_stamp between :dat  and :dat1"
					+ " group by a.MID,a.date order by a.time_stamp desc";
		}else{
			sql = "select f.state,f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME,f.ACTIVATE_DATE "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "where a.status in ('CT','CV') and time_stamp between :dat  and :dat1"
					+ " group by a.MID,a.date order by a.time_stamp desc";
		}*/
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			fs.setLocation(rec[0].toString());
			fs.setMerchantName(rec[1].toString().toUpperCase());

			String rd = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[2].toString()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			fs.setDate(rd);
			// /logger.info(" Amount : " + rec[3]);
			Double d = new Double(rec[3].toString());
			d = d / 100;
			// logger.info("data : " + d);
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			// logger.info(d + " " + pattern + " " + output);
			fs.setAmount(output);
			fs.setAgentName(rec[4].toString());
			
			
			String rd1 = null;
			try {
				rd1 = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[5].toString()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			fs.setNumOfRefund(rd1);
			//logger.info("check activation date:" + fs.getNumOfRefund());
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		// paginationBean.setTotalRowCount(fss.size());
	}

	@Override
	public TID getTID(final Transaction transaction) {
		TID tid = (TID) super.getSessionFactory().createCriteria(TID.class)
				.add(Restrictions.eq("transaction", transaction))
				.setMaxResults(1).uniqueResult();
		return tid;
	}

	@Override
	@Transactional(readOnly = true)
	public void listTransactionTIDUsers(
			final PaginationBean<Transaction> paginationBean,
			final ArrayList<Criterion> mobileUser) {
		super.getPaginationItemsByPage(paginationBean, Transaction.class,
				mobileUser, Order.asc("id"));

	}

	@Override
	public ArrayList<ForSettlement> listTransaction(
			final PaginationBean<ForSettlement> paginationBean, final String mid) {

		ArrayList<ForSettlement> transactionList = new ArrayList<ForSettlement>();
		if (mid != null) {
			// logger.info("teste  IF");
			paginationBean.setItemList(transactionList);
			paginationBean.setTotalRowCount(transactionList.size());
		} else {
			// logger.info("teste  ELSE");
			/*
			 * String sql =
			 * "select treq.TXN_ID,treq.MID,treq.TID,treq.TERMINAL_DEVICE,treq.AMOUNT,tres.LOCAL_DATE,tres.LOCAL_TIME,tres.RESPONSE_CODE"
			 * +
			 * " from transaction_request treq left join transaction_response tres on treq.TXN_ID=tres.TXN_ID"
			 * + " where treq.MID=:mid";
			 * 
			 * 
			 * String sql =
			 * "select a.date , a.batchno AS BatchNO, f.BUSINESS_NAME AS MerchantName , a.mid AS MID,sum(a.AMOUNT) TotalAmount "
			 * +
			 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
			 * +
			 * "ON f.MID_FK=m.ID where a.status='S' group by a.MID,a.BATCHNO,a.DATE order by a.DATE desc"
			 * ;
			 */
			String sql1 = "select a.date , a.batchno AS BatchNO,  a.mid , sum(a.AMOUNT) TotalAmount "
					+ "from FOR_SETTLEMENT a  where a.status='S' and a.DATE like '%03' group by a.MID,a.BATCHNO,a.DATE order by a.DATE desc";

			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql1)
					.addEntity(ForSettlement.class);// .setParameter("mid",
													// mid);

			List<ForSettlement> resultSet = sqlQuery.list();
			for (ForSettlement record : resultSet) {
				ForSettlement row = record;
				transactionList.add(row);

			}
			paginationBean.setItemList(transactionList);
			paginationBean.setTotalRowCount(transactionList.size());
		}
		return transactionList;
	}

	@Override
	public void getForSettlement(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		logger.info("In dao impl*******");
		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}
	@Override
	public void getCardTransForSettlement(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		logger.info("In dao impl*******");
		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}
	
	
	
	@Override
	public void getCashTransForSettlement(PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		logger.info("In dao impl*******");
		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}

	@Override
	public void getForSettlementnonmerchant(
			PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		logger.info("In dao impl non merchant*******");
		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}

	@SuppressWarnings("unchecked")
	@Override
	public void searchForSettlement(String fromDate, String toDate, String tid,
			final String status, PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		// http://www.mkyong.com/hibernate/hibernate-criteria-examples/

		logger.info("Data : " + fromDate  + " " +toDate  + " " + tid + " "
				+ status);

		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void searchForSettlementnew(String fromDate, String toDate,  PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		// http://www.mkyong.com/hibernate/hibernate-criteria-examples/

		logger.info("Data : " + fromDate + " " + toDate );

		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void searchForSettlementcash(String fromDate, String toDate,  PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		// http://www.mkyong.com/hibernate/hibernate-criteria-examples/

		logger.info("Data : " + fromDate + " " + toDate );

		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}
	@SuppressWarnings("unchecked")
	@Override
	public void searchForSettlementcard(String fromDate, String toDate,String tid,
			final String status,  PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		// http://www.mkyong.com/hibernate/hibernate-criteria-examples/

		logger.info("Data : " + fromDate + " " + toDate );

		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}
	@SuppressWarnings("unchecked")
	public void searchnonmerchantForSettlement(String fromDate, String toDate, PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props) {
		// http://www.mkyong.com/hibernate/hibernate-criteria-examples/

		logger.info("Data searchnonmerchantForSettlement: " + fromDate + " " + toDate );

		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));

	}

	@Override
	public List<TerminalDetails> getTerminalDetails(final String merchantId) {
		@SuppressWarnings("unchecked")
		List<TerminalDetails> td = super.getSessionFactory()
				.createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("merchantId", merchantId)).list();
		return td;
	}

	@Override
	public TransactionResponse loadTransactionResponse(String trx_id) {
		// TODO Auto-generated method stub
		BigInteger txId = new BigInteger(trx_id);
		return (TransactionResponse) sessionFactory.getCurrentSession()
				.createCriteria(TransactionResponse.class)
				.add(Restrictions.eq("txnId", txId)).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public TransactionRequest loadTransactionRequest(String trx_id) {
		BigInteger txnId = new BigInteger(trx_id);
		return (TransactionRequest) sessionFactory.getCurrentSession()
				.createCriteria(TransactionRequest.class)
				.add(Restrictions.eq("txnId", txnId)).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public ForSettlement getForSettlement(String trxId) {
		BigInteger trx = new BigInteger(trxId);
		ForSettlement fSettlement = (ForSettlement) sessionFactory
				.getCurrentSession().createCriteria(ForSettlement.class)
				.add(Restrictions.eq("trxId", trx)).setMaxResults(1)
				.uniqueResult();
		return fSettlement;

	}

	@Override
	public AgentResponseDTO loadAgentByName(String agentName) {
		System.out.println(agentName);

		ArrayList<MerchantSettlementDTO> merList = new ArrayList<MerchantSettlementDTO>();
		AgentResponseDTO agent = new AgentResponseDTO();
		//MerchantSettlementDTO merchant = new MerchantSettlementDTO();

		/*String sql2 = "select a.FIRST_NAME,a.ag_code, a.ag_city, a.ag_phoneno "
				+ "from AGENT a  where a.FIRST_NAME='" + agentName + "'";*/
		
		String sql2 = "select a.FIRST_NAME,a.ag_code, a.ag_city, a.ag_phoneno "
				+ "from AGENT a  where a.FIRST_NAME= :agentName";

		// System.out.println("query" + sql2);
		logger.info("Query : " + sql2);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql2);// .addEntity(ForSettlement.class);
		sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			// Agent ags = new Agent();
			agent.setAgName(rec[0].toString());
			agent.setAgCode(rec[1].toString());
			agent.setAgCity(rec[2].toString());
			agent.setAgPhoneNo(rec[3].toString());

		}
		/*String sql3 = "select m.business_name,m.business_address1,m.business_address2,m.city,m.postcode"
				+ " from MERCHANT m, AGENT a  where a.id = m.agid_fk and a.FIRST_NAME='"
				+ agentName + "'";*/
		String sql3 = "select m.business_name,m.business_address1,m.business_address2,m.city,m.postcode"
				+ " from MERCHANT m, AGENT a  where a.id = m.agid_fk and a.FIRST_NAME= :agentName";
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql3);// .addEntity(ForSettlement.class);
		sqlQuery1.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		for (Object[] rec : resultSet1) {
			MerchantSettlementDTO msd = new MerchantSettlementDTO();
			if (rec[0] != null) {
				msd.setMerchantName(rec[0].toString().toUpperCase());
			}
			// logger.info("MerchantName:" + rec[0].toString().toUpperCase());
			if (rec[1] != null) {
				msd.setMerchantAddr1(rec[1].toString());
			}
			// logger.info("Merchantaddr1:" + rec[1].toString());
			if (rec[2] != null) {
				msd.setMerchantAddr2(rec[2].toString());
			}
			// logger.info("MerchantAddr2:" + rec[2].toString());
			if (rec[3] != null) {
				msd.setMerchantCity(rec[3].toString());
			}
			// logger.info("Merchantcity:" + rec[3].toString());
			if (rec[4] != null) {
				msd.setMerchantPostcode(rec[4].toString());
			}
			// logger.info("MerchantPostcode:" + rec[4].toString());

			/*String sqlterminal = "select count(*) from TERMINAL_DETAILS where merchant_id in (select mid from MID where id in (select mid_fk from MERCHANT"
					+ " where business_name='" + rec[0].toString() + "'))";*/
			String sqlterminal = "select count(*) from TERMINAL_DETAILS where merchant_id in "
					+ "(select mid from MID where id in (select mid_fk from MERCHANT"
					+ " where business_name= :name))";
			Query queryTerminal = super.getSessionFactory().createSQLQuery(
					sqlterminal);// .addEntity(ForSettlement.class);
			queryTerminal.setString("name", rec[0].toString());
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List resultSetTerminal = queryTerminal.list();// list();

			msd.setNoOfTid(resultSetTerminal.get(0).toString());

			merList.add(msd);

		}

		agent.setMerSettle(merList);
		return agent;
		// paginationBean.setItemList(agentNameList);
		// paginationBean.setTotalRowCount(agentNameList.size());
	}

	@Override
	public void loadTerminalByName(
			final PaginationBean<TerminalDTO> paginationBean,
			String businessName) {
		// logger.info(businessName);
		ArrayList<TerminalDTO> tdtoList = new ArrayList<TerminalDTO>();

		/*String sql = "select device_id, device_name, device_type,tid from TERMINAL_DETAILS "
				+ "where merchant_id in (select merchant_id from MERCHANT where business_name='"
				+ businessName + "')";*/
		String sql = "select device_id, device_name, device_type,tid from TERMINAL_DETAILS "
				+ "where merchant_id in (select merchant_id from MERCHANT where business_name="
				+ ":businessName )";
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery1.setString("businessName", businessName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		for (Object[] rec : resultSet1) {
			TerminalDTO tdto = new TerminalDTO();
			tdto.setDeviceId(rec[0].toString());
			if (rec[1] != null) {
				tdto.setDeviceName(rec[1].toString());
			} else {
				tdto.setDeviceName("");
			}
			// System.out.println("DeviceName:" + rec[1].toString());
			if (rec[2] != null) {
				tdto.setDeviceType(rec[2].toString());
			} else {
				tdto.setDeviceType("");
			}
			// System.out.println("DeviceType:" + rec[2].toString());
			if (rec[3] != null) {
				tdto.setTid(rec[3].toString());
			} else {
				tdto.setTid("");
			}

			// System.out.println("Tid:" + rec[3].toString());
			tdtoList.add(tdto);
		}

		paginationBean.setItemList(tdtoList);
		paginationBean.setTotalRowCount(tdtoList.size());

	}

	@Override
	public List<ForSettlement> exportAllTransaction(
			final ArrayList<Criterion> props, final String date,
			final String date1, final String txnType) {
		logger.info("Inside   listAllTransaction");
		String dat = null;
		String dat1 = null;
		String year1 = null;
		String year2 = null;
		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
		String sql = null;

		if (date == null || date.equals("")) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			dat = dateFormat.format(dt);
			dat = dat + "-01";

		} else {

			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		if (date1 == null || date1.equals("")) {
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			dat1 = dateFormat1.format(dt1);

		} else {

			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat1));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		/*
		 * sql =
		 * "select f.city , f.BUSINESS_NAME AS MerchantName ,a.date ,a.txn_year , sum(a.AMOUNT)  TotalAmount , ag.ag_name "
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID INNER JOIN agent ag on f.AGID_FK=ag.id " +
		 * "where a.status='S' and DATE between '" + dat + "' and '" + dat1 +
		 * "' and a.txn_year between '"
		 * +year1+"' and '"+year2+"' group by a.MID,a.DATE order by a.DATE desc"
		 * ;
		 */

		/*
		 * sql =
		 * "select f.city , f.BUSINESS_NAME AS MerchantName ,a.date ,a.txn_year , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID INNER JOIN agent ag on f.AGID_FK=ag.id " +
		 * "where a.status='S' and time_stamp between '" + dat + "' and '" +
		 * dat1 + "' group by a.MID,a.date order by a.time_stamp desc";
		 */

		/*sql = "select f.state , f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+ "where a.status='S' and time_stamp between '"
				+ dat
				+ "' and '"
				+ dat1
				+ "'  group by a.MID,a.date order by a.time_stamp desc";*/
		if(txnType.equals("ALL") || txnType.isEmpty() || txnType == null){
			sql = "select f.state, f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME,f.ACTIVATE_DATE,a.mid "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+ "where a.status in ('S','CT','CV') and time_stamp between :dat and :dat1"
				+ " group by a.MID,a.date order by a.time_stamp desc";
		
		}else if(txnType.equals("CARD")){
			sql = "select f.state, f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME,f.ACTIVATE_DATE,a.mid "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "where a.status='S' and time_stamp between :dat and :dat1"
					+ " group by a.MID,a.date order by a.time_stamp desc";
			
		}else{
			sql = "select f.state, f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME,f.ACTIVATE_DATE,a.mid "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "where a.status in ('CT','CV') and time_stamp between :dat and :dat1"
					+ " group by a.MID,a.date order by a.time_stamp desc";
			
		}
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			fs.setLocation(rec[0].toString());
			fs.setMerchantName(rec[1].toString().toUpperCase());

			String rd = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[2].toString()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			fs.setDate(rd);

			Double d = new Double(rec[3].toString());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			fs.setAmount(output);
			fs.setAgentName(rec[4].toString());
			String rd1 = null;
			try {
				rd1 = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[5].toString()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			
			fs.setNumOfRefund(rd1);
			fs.setMid(rec[6].toString());
			fss.add(fs);
		}
		return fss;
	}

	@Override
	public void listAllTransactionDetails(
			PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props, String date, String date1) {

		logger.info("Inside listAllTransactionDetails : " + date + "     "
				+ date1);
		super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
				props, Order.desc("timeStamp"));
	}
	
	public void listAllTransactionDetailsbyAdmin(PaginationBean<ForSettlement> paginationBean, 
			ArrayList<Criterion> props,
			String fromDate, String toDate,String status) {
		
		logger.info("inside listAllTransactionDetailsbyAdmin "+" from date: "+fromDate+"toDate: "+toDate+"status: "+status);
		
			ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
			String sql = null;
			Query sqlQuery = null;
			/*if ((fromDate == null || toDate == null)
					|| (fromDate.equals("") || toDate.equals(""))) {

				*/
			
			
			/*sql = " select f.DATE,f.TIME,f.STATUS, f.AMOUNT,f.TID,f.MID,f.LOCATION,f.TIME_STAMP,t.CONTACT_NAME "
					+ "from FOR_SETTLEMENT f, TERMINAL_DETAILS t "
					+ "where t.TID=f.TID ";*/
			if((fromDate != null && toDate != null)
					&&(!fromDate.isEmpty() && !toDate.isEmpty()) && (!status.isEmpty() && status!=null))
			{
				logger.info("inside date and status criteria: "+"from date: "+fromDate+"toDate: "+toDate+"status: "+status);
				sql = "select f.BUSINESS_NAME,a.TIME_STAMP ,a.AMOUNT ,a.TIME,a.STATUS, a.TID,a.MID,a.LOCATION,a.TRX_ID,a.STAN"
						+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
						+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
						+ "where a.STATUS=:status and a.TIME_STAMP between :fromDate and :toDate order by a.TIME_STAMP desc limit 1000";
					 sqlQuery = super.getSessionFactory().createSQLQuery(sql);
					 sqlQuery.setString("status", status);
					sqlQuery.setString("fromDate", fromDate);
					sqlQuery.setString("toDate", toDate);
			}
			else if((fromDate != null && toDate != null)
					&&(!fromDate.isEmpty() && !toDate.isEmpty()))
			{
				logger.info("inside date criteria: "+"from date: "+fromDate+"toDate: "+toDate+"status: "+status);
				
				sql = "select f.BUSINESS_NAME,a.TIME_STAMP ,a.AMOUNT ,a.TIME,a.STATUS, a.TID,a.MID,a.LOCATION,a.TRX_ID,a.STAN "
						+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
						+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
						+ "where a.STATUS in ('BP','BPS','BPC','BPA') and a.TIME_STAMP between :fromDate and :toDate order by a.TIME_STAMP desc limit 1000";
					 sqlQuery = super.getSessionFactory().createSQLQuery(sql);
					// sqlQuery.setString("status", status);
					sqlQuery.setString("fromDate", fromDate);
					sqlQuery.setString("toDate", toDate);
			}
			else{
				logger.info("from date: "+fromDate+"toDate: "+toDate+"status: "+status);
				sql = "select f.BUSINESS_NAME ,a.TIME_STAMP , a.AMOUNT , a.TIME,a.STATUS, a.TID,a.MID,a.LOCATION,a.TRX_ID,a.STAN "
						+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
						+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
						+ "where a.STATUS in ('BP','BPS','BPC','BPA') order by a.TIME_STAMP desc limit 1000";
				 sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			
			}
			/*  m.activateDate, m.activateDate"select f.state , f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
			+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
			+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
			+ "where a.status='S' and time_stamp between :dat  and :dat1"
			+ " group by a.MID,a.date order by a.time_stamp desc";*/
		
			logger.info("Query : " + sql);
			//Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
			/*sqlQuery.setString("dat", dat);
			sqlQuery.setString("dat1", dat1);*/
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet = sqlQuery.list();
			logger.info("Number of records in the List : " + resultSet.size());
			for (Object[] rec : resultSet) {
				ForSettlement fs = new ForSettlement();
				
				if(rec[0]!=null)
				{
					//businessname
					fs.setNumOfSale(rec[0].toString());
				}
				if(rec[1]!=null)
				{
					//a.time_stamp 
					//fs.setTimeStamp(rec[1].toString());
					//logger.info("date for boost: "+rec[1].toString());
					String rd = null;
					try {
						rd = new SimpleDateFormat("dd-MMM-yyyy").format(
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[1].toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					fs.setDate(rd);
				}
				if(rec[2]!=null){
					//a.AMOUNT
					
					double amount=0;
					amount=Double.parseDouble(rec[2].toString());
					//forSettlement.setAmount(amount+"0");
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					//System.out.println(" Amount :"+output);
					fs.setAmount(output);
				
				}
				if(rec[3]!=null)
				{
					//a.TIME
				fs.setTime(rec[3].toString());
				try {
					
					
					String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(rec[3].toString()));
					//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
					
					fs.setTime(rt);
				} catch (ParseException e) {}
				}
				if(rec[4]!=null)
				{
					
					//a.`STATUS`
					
					if(rec[4].toString().equals("BPS")){
						fs.setStatus("BOOST SETTLED");
					}if(rec[4].toString().equals("BPA")){
						fs.setStatus("BOOST PAYMENT");
					}
					if(rec[4].toString().equals("BP")){
						fs.setStatus("BOOST PAYMENT");
					}
					if(rec[4].toString().equals("BPC")){
						fs.setStatus("BOOST VOID");
					}
					
				//fs.setStatus(rec[4].toString());
				}
				if(rec[5]!=null){
					//a.TID
					//logger.info("boost tid: "+rec[5].toString());
				fs.setTid(rec[5].toString());
					if(!fs.getStatus().equals("CT") && !fs.getStatus().equals("CV")){
						//logger.info("TID : "+rec[5].toString());
						TerminalDetails  td =getTerminalDetailsByTid(rec[5].toString());
						if(td != null){
							//logger.info("TID : "+td.getTid());
							if(td.getContactName()!=null)
							{
								//logger.info("TID : "+td.getContactName());
							fs.setMerchantName(td.getContactName());
							}else
							{
								fs.setMerchantName("");
							}
						}
					}
				}
				if(rec[6]!=null){
					//a.MID
				fs.setMid(rec[6].toString());
				}
				if(rec[7]!=null)
				{
					//a.LOCATION
				fs.setLocation(rec[7].toString());
				//logger.info("boost location: "+rec[7].toString());
				}
				
				if(rec[8]!=null)
				{
					BigInteger number = new BigInteger(rec[8].toString());
					//logger.info("trid: "+number);
					fs.setTrxId(number);
				
				}
				if(rec[9]!=null)
				{
					//BigInteger number = new BigInteger(rec[8].toString());
					//logger.info("stan: "+rec[9].toString());
					fs.setStan(rec[9].toString());
				
				}
				
				fss.add(fs);
			}
			  paginationBean.setItemList(fss);
			// paginationBean.setTotalRowCount(fss.size());
		}
	@Override
	public List<ForSettlement> exportAllTransactionbyAdmin(
			final ArrayList<Criterion> props, final String fromDate,
			final String toDate, final String status) {
		logger.info("inside listAllTransactionDetailsbyAdmin "+" from date: "+fromDate+"toDate: "+toDate+"status: "+status);
		
		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
		String sql = null;
		Query sqlQuery = null;
		/*if ((fromDate == null || toDate == null)
				|| (fromDate.equals("") || toDate.equals(""))) {

			*/
		
		
		/*sql = " select f.DATE,f.TIME,f.STATUS, f.AMOUNT,f.TID,f.MID,f.LOCATION,f.TIME_STAMP,t.CONTACT_NAME "
				+ "from FOR_SETTLEMENT f, TERMINAL_DETAILS t "
				+ "where t.TID=f.TID ";*/
		if((fromDate != null && toDate != null)
				&&(!fromDate.isEmpty() && !toDate.isEmpty()) && (!status.isEmpty() && status!=null))
		{
			logger.info("inside date criteria: "+"from date: "+fromDate+"toDate: "+toDate+"status: "+status);
			sql = "select f.BUSINESS_NAME,a.TIME_STAMP ,a.AMOUNT ,a.TIME,a.STATUS, a.TID,a.MID,a.LOCATION,a.TRX_ID "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "where a.STATUS=:status and a.TIME_STAMP between :fromDate and :toDate order by a.TIME_STAMP desc limit 1000";
				 sqlQuery = super.getSessionFactory().createSQLQuery(sql);
				 sqlQuery.setString("status", status);
				sqlQuery.setString("fromDate", fromDate);
				sqlQuery.setString("toDate", toDate);
		}else if((fromDate != null && toDate != null)
				&&(!fromDate.isEmpty() && !toDate.isEmpty()))
		{
			logger.info("inside status date criteria: "+"from date: "+fromDate+"toDate: "+toDate);
			sql = "select f.BUSINESS_NAME,a.TIME_STAMP ,a.AMOUNT ,a.TIME,a.STATUS, a.TID,a.MID,a.LOCATION,a.TRX_ID "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "where a.STATUS in ('BPS','BP','BPC','BPA') and a.TIME_STAMP between :fromDate and :toDate order by a.TIME_STAMP desc limit 1000";
				 sqlQuery = super.getSessionFactory().createSQLQuery(sql);
				sqlQuery.setString("fromDate", fromDate);
				sqlQuery.setString("toDate", toDate);
			
		}
		else{
			logger.info("from date: "+fromDate+"toDate: "+toDate+"status: "+status);
			sql = "select f.BUSINESS_NAME ,a.TIME_STAMP , a.AMOUNT , a.TIME,a.STATUS, a.TID,a.MID,a.LOCATION,a.TRX_ID  "
					+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "where a.STATUS in ('BPS','BP','BPC','BPA') order by a.TIME_STAMP desc limit 1000";
			 sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		
		}
		/*  m.activateDate, m.activateDate"select f.state , f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
		+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
		+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
		+ "where a.status='S' and time_stamp between :dat  and :dat1"
		+ " group by a.MID,a.date order by a.time_stamp desc";*/
	
		logger.info("Query : " + sql);
		//Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		/*sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);*/
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			
			if(rec[0]!=null)
			{
				//businessname
				fs.setNumOfSale(rec[0].toString());
			}
			if(rec[1]!=null)
			{
				//a.time_stamp 
				//fs.setTimeStamp(rec[1].toString());
				String rd = null;
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[1].toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fs.setDate(rd);
			}
			if(rec[2]!=null){
				//a.AMOUNT
				
				double amount=0;
				amount=Double.parseDouble(rec[2].toString())/100;
				//forSettlement.setAmount(amount+"0");
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				//System.out.println(" Amount :"+output);
				fs.setAmount(output);
			
			}
			if(rec[3]!=null)
			{
				//a.TIME
			fs.setTime(rec[3].toString());
			try {
				
				
				String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(rec[3].toString()));
				//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
				
				fs.setTime(rt);
			} catch (ParseException e) {}
			}
			if(rec[4]!=null)
			{
				
				//a.`STATUS`
				
				if(rec[4].toString().equals("BPS")){
					fs.setStatus("SETTLED");
				}if(rec[4].toString().equals("BP")){
					fs.setStatus("DONE");
				}
				if(rec[4].toString().equals("BPA")){
					fs.setStatus("ACCEPTED");
				}
				if(rec[4].toString().equals("BPC")){
					fs.setStatus("VOID");
				}
				
			//fs.setStatus(rec[4].toString());
			}
			if(rec[5]!=null){
				//a.TID
			fs.setTid(rec[5].toString());
				if(!fs.getStatus().equals("CT") && !fs.getStatus().equals("CV")){
					//logger.info("TID : "+rec[5].toString());
					TerminalDetails  td =getTerminalDetailsByTid(rec[5].toString());
					if(td != null){
						//logger.info("TID : "+td.getTid());
						if(td.getContactName()!=null)
						{
							//logger.info("TID : "+td.getContactName());
						fs.setMerchantName(td.getContactName());
						}else
						{
							fs.setMerchantName("");
						}
					}
				}
			}
			if(rec[6]!=null){
				//a.MID
			fs.setMid(rec[6].toString());
			}
			if(rec[7]!=null){
				//a.LOCATION
			fs.setLocation(rec[7].toString());
			}
			
			if(rec[8]!=null)
			{
				BigInteger number = new BigInteger(rec[8].toString());
				//logger.info("trid: "+number);
				fs.setTrxId(number);
			
			}
			
			fss.add(fs);
		}
		return fss;
	}

	

	@Override
	public Receipt loadReceiptSignature(String trx_id) {
		logger.info("signature finding");
		BigInteger txId = new BigInteger(trx_id);
		return (Receipt) sessionFactory.getCurrentSession()
				.createCriteria(Receipt.class)
				.add(Restrictions.eq("trxId", txId)).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public Merchant loadMerchantDet(String mid) {
		Merchant merchant = new Merchant();

		/*String sql = "select BUSINESS_NAME,BUSINESS_ADDRESS1,BUSINESS_ADDRESS2,CITY,"
				+ "POSTCODE,BUSINESS_CONTACT_NUMBER,FIRST_NAME,LAST_NAME,EMAIL from MERCHANT where mid_fk in "
				+ "(select id from MID where mid = '" + mid + "')";*/
		
		String sql = "select BUSINESS_NAME,BUSINESS_ADDRESS1,BUSINESS_ADDRESS2,CITY,"
				+ "POSTCODE,BUSINESS_CONTACT_NUMBER,FIRST_NAME,LAST_NAME,EMAIL from MERCHANT where mid_fk in "
				+ "(select id from MID where mid = :mid )";

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mid", mid);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			if (rec[0] != null) {
				merchant.setBusinessName(rec[0].toString().toUpperCase());
			}
			if (rec[1] != null) {
				merchant.setBusinessAddress1(rec[1].toString());
			}
			if (rec[2] != null) {
				merchant.setBusinessAddress2(rec[2].toString());
			}
			if (rec[3] != null) {
				merchant.setCity(rec[3].toString());
			}
			if (rec[4] != null) {
				merchant.setPostcode(rec[4].toString());
			}
			if (rec[5] != null) {
				merchant.setBusinessContactNumber(rec[5].toString());
			}
			if (rec[6] != null) {
				merchant.setFirstName(rec[6].toString());
			}
			if (rec[7] != null) {
				merchant.setLastName(rec[7].toString());
			}
			if (rec[8] != null) {
				merchant.setEmail(rec[8].toString());
			}

		}
		return merchant;

	}

	@Override
	public void listSearchTransactionDetails(
			PaginationBean<ForSettlement> paginationBean,
			ArrayList<Criterion> props, String date, String date1) {

		logger.info("Inside   listAllTransaction test : " + date + "     "
				+ date1);
		ArrayList<ForSettlement> MerchantNameList = new ArrayList<ForSettlement>();

		/*String sql2 = "select a.date ,a.txn_year ,a.status ,a.mid, a.location,a.Tid,a.time,round(a.AMOUNT)"
				+ "from FOR_SETTLEMENT a INNER JOIN mid m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID where DATE between '"
				+ date
				+ "' and '"
				+ date1 + "'   order by date desc";*/
		
		String sql2 = "select a.date ,a.txn_year ,a.status ,a.mid, a.location,a.Tid,a.time,round(a.AMOUNT)"
				+ "from FOR_SETTLEMENT a INNER JOIN mid m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID where DATE between :date and :date1 order by date desc";

		logger.info("Query : " + sql2);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql2);// .addEntity(ForSettlement.class);
		sqlQuery.setString("date", date);
		sqlQuery.setString("date1", date1);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			fs.setDate(rec[0].toString());
			String pattern1 = rec[0].toString();
			String su = pattern1.substring(0, 2);
			String sub2 = pattern1.substring(2, 4);

			String month = null;

			if (su == "01" || su.equals("01"))
				month = "Jan";
			else if (su == "02" || su.equals("02"))
				month = "Feb";
			else if (su == "03" || su.equals("03"))
				month = "Mar";
			else if (su == "04" || su.equals("04"))
				month = "Apr";
			else if (su == "05" || su.equals("05"))
				month = "May";
			else if (su == "06" || su.equals("06"))
				month = "Jun";
			else if (su == "07" || su.equals("07"))
				month = "Jul";
			else if (su == "08" || su.equals("08"))
				month = "Aug";
			else if (su == "09" || su.equals("09"))
				month = "Sep";
			else if (su == "10" || su.equals("10"))
				month = "Oct";
			else if (su == "11" || su.equals("11"))
				month = "Nov";
			else if (su == "12" || su.equals("12"))
				month = "Dec";
			fs.setDate(sub2 + "-" + month + "-" + rec[1].toString());
			System.out.println("test date : " + sub2 + "-" + month + "-"
					+ rec[1].toString());
			// fs.setStatus(rec[1].toString());
			String status = null;
			// System.out.println("test data" + rec[1].toString());
			if (rec[2].toString() == "A" || rec[2].toString().equals("A")) {
				status = "COMPLETED";
			} else if (rec[2].toString() == "C"
					|| rec[2].toString().equals("C")) {
				status = "VOID";
			} else if (rec[2].toString() == "R"
					|| rec[2].toString().equals("R")) {
				status = "REVERSAL";
			} else if (rec[2].toString() == "P"
					|| rec[2].toString().equals("P")) {
				status = "PENDING";
			} else if (rec[2].toString() == "S"
					|| rec[2].toString().equals("S")) {
				status = "SETTLED";
			}
			fs.setStatus(status);
			logger.info("Status" + rec[2].toString());
			fs.setMid(rec[3].toString());
			if (rec[4] != null) {
				fs.setLocation(rec[4].toString());
			}
			if (rec[5] != null) {
				fs.setTid(rec[5].toString());
			}
			fs.setTime(rec[6].toString());

			Double d = new Double(rec[7].toString());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);

			fs.setAmount(output);
			MerchantNameList.add(fs);
		}

	}

	@Override
	public void loadMerchantByName(
			final PaginationBean<ForSettlement> paginationBean,
			String merchantName, String date) {
		logger.info("loadMerchantByName merchantName: " + merchantName
				+ " date : " + date);
		// System.out.println("load merchantCity:" +City);
		ArrayList<ForSettlement> MerchantNameList = new ArrayList<ForSettlement>();
		@SuppressWarnings("deprecation")
		// Date dt = new Date(date+"-2015");
		Date dt = new Date(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dat = dateFormat.format(dt);

		/*
		 * String sql2 =
		 * "select a.date ,a.txn_year,a.status, f.BUSINESS_NAME AS MerchantName , a.location,a.Tid,a.time,round(a.AMOUNT),a.trx_id "
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID where a.status='S' and a.date='"+ dat
		 * +"' and a.txn_year='"+yr+"' and f.BUSINESS_NAME='" + merchantName +
		 * "'  order by time_stamp desc";
		 */

		/*String sql2 = "select a.time_stamp,a.status, f.BUSINESS_NAME AS MerchantName , a.location,a.Tid,a.time,round(a.AMOUNT),a.trx_id "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID where a.status='S' and a.time_stamp like '"
				+ dat
				+ "%' and upper(f.BUSINESS_NAME)='"
				+ merchantName
				+ "'  order by a.time_stamp desc";*/
		
		/*String sql2 = "select a.time_stamp,a.status, f.BUSINESS_NAME AS MerchantName , a.location,a.Tid,a.time,round(a.AMOUNT),a.trx_id "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID where a.status='S' and a.time_stamp like :dat "
				+ "and upper(f.BUSINESS_NAME)= :merchantName  order by a.time_stamp desc";*/
		
		String sql2 = "select a.time_stamp,a.status, f.BUSINESS_NAME AS MerchantName , a.location,a.Tid,a.time,round(a.AMOUNT),a.trx_id "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID where a.status in ('S','CT','CV') and a.time_stamp like :dat "
				+ "and upper(f.BUSINESS_NAME)= :merchantName  order by a.time_stamp desc";

		/*
		 * String sql2 =
		 * "select a.date ,a.batchNo AS BatchNO, f.BUSINESS_NAME AS MerchantName , a.location,a.Tid,a.time,round(a.AMOUNT)"
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID where a.status='S' and f.BUSINESS_NAME='" +
		 * merchantName + "' order by date desc";
		 */
		// System.out.println("sql");

		logger.info("Query : " + sql2);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql2);// .addEntity(ForSettlement.class);
		sqlQuery.setString("dat", dat+"%");
		sqlQuery.setString("merchantName", merchantName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			String rd = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[0].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fs.setDate(rd);

			String status = null;
			// System.out.println("test data" + rec[1].toString());
			if (rec[1].toString() == "A" || rec[1].toString().equals("A")) {
				status = "COMPLETED";
			} else if (rec[1].toString() == "C"
					|| rec[1].toString().equals("C")) {
				status = "VOID";
			} else if (rec[1].toString() == "R"
					|| rec[1].toString().equals("R")) {
				status = "REVERSAL";
			} else if (rec[1].toString() == "P"
					|| rec[1].toString().equals("P")) {
				status = "PENDING";
			} else if (rec[1].toString() == "S"
					|| rec[1].toString().equals("S")) {
				status = "SETTLED";
			}else if (rec[1].toString() == "CT"
					|| rec[1].toString().equals("CT")) {
				status = "CASH SALE";
			}else if (rec[1].toString() == "CV"
					|| rec[1].toString().equals("CV")) {
				status = "CASH CANCELLED";
			}
			fs.setStatus(status);
			/* logger.info("Status" + rec[1].toString()); */
			fs.setMerchantName(rec[2].toString().toUpperCase());
			if (rec[3] != null) {
				fs.setLocation(rec[3].toString());
			}
			if (rec[4] != null) {
				fs.setTid(rec[4].toString());
			}
			if (rec[5] != null) {
				String rt = null;
				try {
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(rec[5]
									.toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setTime(rt);
			}

			Double d = new Double(rec[6].toString());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);

			fs.setAmount(output);
			fs.setTrxId(new BigInteger(rec[7].toString()));
			MerchantNameList.add(fs);
		}

		paginationBean.setItemList(MerchantNameList);

	}

	// start agent volume summary new method 08/08/2016
	@Transactional(readOnly = true)
	public List<ForSettlement> agentVolume(final ArrayList<Criterion> props,
			final String agentName) {

		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
		String sql = null;
		/*
		 * int cDate=0;
		 * 
		 * int pDate=0;
		 * 
		 * Date dt = new Date(); cDate = dt.getMonth()+1; pDate =
		 * dt.getMonth()-2;
		 */

		/*
		 * sql =
		 * "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME ,ag.id from "
		 * +
		 * " FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
		 * +
		 * " ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK= ag.id where month(a.time_stamp) between  '"
		 * + pDate + "'  and '" + cDate + "'" +
		 * " and a.status='S' and ag.FIRST_NAME = '" + agentName + "' " +
		 * " group by month(a.time_stamp) order by a.time_stamp desc";
		 */

		/*sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME ,ag.id from "
				+ " FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ " ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK= ag.id where month(a.time_stamp) between  DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
				+ " and a.status='S' and ag.FIRST_NAME = '"
				+ agentName
				+ "' "
				+ " group by month(a.time_stamp) order by a.time_stamp desc";*/
		
		sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME ,ag.id from "
				+ " FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ " ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK= ag.id where month(a.time_stamp) between  DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
				+ " and a.status='S' and ag.FIRST_NAME = :agentName "
				+ " group by month(a.time_stamp) order by a.time_stamp desc";

		// super.getPaginationItemsByPage(paginationBean,
		// ForSettlement.class,props, Order.asc("merchantName"));
		logger.info("Query : " + sql);

		// System.out.println("no records found:" +sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			String su = rec[0].toString();
			String month = null;
			fs.setDate(su);
			Double d = new Double(rec[1].toString());
			d = d / 100;

			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			// logger.info(d + " " + pattern + " " + output);
			fs.setAmount(output);
			fs.setAgentName(rec[2].toString());
			// logger.info("agent__id:" + rec[3].toString() );
			fs.setMerchantName(rec[3].toString() + "~AGENT~"
					+ rec[2].toString());

			fss.add(fs);
		}

		return fss;
	}

	// end agent volume summary

	// start subagent volume summary 08/08/2016
	@Transactional(readOnly = true)
	public List<ForSettlement> subAgentVolume(ArrayList<Criterion> props,
			SubAgent subAgent) {
		ArrayList<ForSettlement> fs1 = new ArrayList<ForSettlement>();
		String sql = null;
		/*
		 * int cDate=0; int pDate=0;
		 * 
		 * Date dt = new Date();
		 * 
		 * cDate = dt.getMonth()+1; pDate = dt.getMonth()-2;
		 */
		// System.out.println("Month :"+pDate +" "+cDate);
		/* ag.FIRST_NAME */

		/*
		 * sql =
		 * "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.NAME  ,ag.id from "
		 * +
		 * "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
		 * +
		 * "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id where  month(a.time_stamp) between  '"
		 * + pDate + "'  and '" + cDate + "'" + " and a.status='S' and ag.id='"
		 * + subAgent.getId() + "' " +
		 * "group by month(a.time_stamp) order by a.time_stamp desc";
		 */

		/*sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.NAME  ,ag.id from "
				+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id where  month(a.time_stamp) between  DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
				+ " and a.status='S' and ag.id='"
				+ subAgent.getId()
				+ "' "
				+ "group by month(a.time_stamp) order by a.time_stamp desc";*/
		
		sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.NAME  ,ag.id from "
				+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id where  month(a.time_stamp) between  DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
				+ " and a.status='S' and ag.id= :id "
				+ "group by month(a.time_stamp) order by a.time_stamp desc";

		/*
		 * super.getPaginationItemsByPage(paginationBean,
		 * ForSettlement.class,props, Order.asc("merchantName"));
		 */
		logger.info("Query : " + sql);
		// System.out.println("no records found:" +sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setLong("id", subAgent.getId());
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();

		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			String su = rec[0].toString();

			fs.setDate(su);

			Double d = new Double(rec[1].toString());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			fs.setAmount(output);
			fs.setAgentName(rec[2].toString());
			// logger.info("subagent__id:" + rec[3].toString() );
			fs.setMerchantName(rec[3].toString() + "~SUBAGENT~"
					+ rec[2].toString());
			fs1.add(fs);
		}
		return fs1;

	}

	// end subagent volume summary 08/08/2016

	// new method for merchant volume summary 08/08/2016
	@Override
	public List<ForSettlement> loadMerchantByVolume(
			final ArrayList<Criterion> props, String agentName) {

		ArrayList<ForSettlement> MerchantNameList = new ArrayList<ForSettlement>();
		int cDate = 0;
		int pDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		pDate = dt.getMonth() - 2;

		String sql2 = null;
		String data[] = null;
		String agid = null;
		String condt = null;
		String agname = null;
		if (agentName.contains("~")) {
			data = agentName.split("~");
			agid = data[0];
			condt = data[1];
			agname = data[2];
		}
		// System.out.println("Condt : "+condt +" Agname :"+agname);

		if (condt.equals("AGENT")) {

			/*
			 * sql2 =
			 * "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName from "
			 * // ag.FIRST_NAME +
			 * "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
			 * +"ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id " +
			 * "where month(a.time_stamp) between  '" + pDate + "'  and '" +
			 * cDate + "'" + " and a.status='S' and ag.FIRST_NAME='" + agname +
			 * "' and ag.id=' "+agid+"'" +
			 * "group by a.MID,month(a.time_stamp) order by a.time_stamp desc";
			 */

			/*sql2 = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName from " // ag.FIRST_NAME
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "where month(a.time_stamp) between  DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S' and ag.FIRST_NAME='"
					+ agname
					+ "' and ag.id=' "
					+ agid
					+ "'"
					+ "group by a.MID,month(a.time_stamp) order by a.time_stamp desc";*/
			
			sql2 = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName from " // ag.FIRST_NAME
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "where month(a.time_stamp) between  DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S' and ag.FIRST_NAME= :agname and ag.id= :agid "
					+ " group by a.MID,month(a.time_stamp) order by a.time_stamp desc";

		} else {

			/*
			 * sql2 =
			 * "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName from "
			 * // ag.FIRST_NAME +
			 * "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
			 * +
			 * "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id "
			 * + "where month(a.time_stamp) between  '" + pDate + "'  and '" +
			 * cDate + "'" + " and a.status='S' and ag.NAME='" + agname +
			 * "' and ag.id= '" + agid+"' " +
			 * "group by a.MID,month(a.time_stamp) order by a.time_stamp desc";
			 */

			sql2 = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName from " // ag.FIRST_NAME
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id "
					+ "where month(a.time_stamp) between  DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S' and ag.NAME= :agname and ag.id= :agid "
					+ " group by a.MID,month(a.time_stamp) order by a.time_stamp desc";

		}

		logger.info("Query : " + sql2);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql2);
		sqlQuery.setString("agname", agname);
		sqlQuery.setString("agid", agid);
		// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			String su = rec[0].toString();
			String month = null;
			fs.setDate(su);

			Double d = new Double(rec[1].toString());

			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			fs.setAmount(output);

			fs.setMerchantName(rec[2].toString());
			MerchantNameList.add(fs);
		}
		/* } */
		return MerchantNameList;
		// paginationBean.setItemList(MerchantNameList);

	}

	// New Change for Preauth

	@Override
	public void getPreAuthTxn(PaginationBean<PreAuthorization> paginationBean,
			ArrayList<Criterion> props) {
		logger.info("In dao impl*******");
		super.getPaginationItemsByPage(paginationBean, PreAuthorization.class,
				props, Order.desc("timeStamp"));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void searchPreAuth(String fromDate, String toDate, String tid,
			final String status,
			PaginationBean<PreAuthorization> paginationBean,
			ArrayList<Criterion> props) {
		logger.info("Data : " + toDate + " " + fromDate + " " + tid + " "
				+ status);
		super.getPaginationItemsByPage(paginationBean, PreAuthorization.class,
				props, Order.desc("timeStamp"));
	}

	@Override
	public PreAuthorization getPreAuthTxn(String trxId) {
		BigInteger trx = new BigInteger(trxId);
		PreAuthorization fSettlement = (PreAuthorization) sessionFactory
				.getCurrentSession().createCriteria(PreAuthorization.class)
				.add(Restrictions.eq("trxId", trx)).setMaxResults(1)
				.uniqueResult();
		return fSettlement;

	}

	@Override
	@Transactional(readOnly = true)
	public void listPreAuthTransaction(
			final PaginationBean<PreAuthorization> paginationBean,
			final ArrayList<Criterion> props, final String date,
			final String date1) {
		logger.info("Inside   listAllTransaction ");
		String dat = null;
		String dat1 = null;
		String year1 = null;
		String year2 = null;
		ArrayList<PreAuthorization> fss = new ArrayList<PreAuthorization>();
		String sql = null;

		if ((date == null || date1 == null)
				|| (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			dat = dateFormat.format(dt);
			dat = dat + "-01";
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			dat1 = dateFormat1.format(dt1);
		} else {
			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(dat1));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		/*
		 * sql =
		 * "select f.city , f.BUSINESS_NAME AS MerchantName ,a.date , a.txn_year , sum(a.AMOUNT)  TotalAmount , ag.ag_name "
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID INNER JOIN agent ag on f.AGID_FK=ag.id " +
		 * "where a.status='S' and DATE between '" + dat + "' and '" + dat1 +
		 * "' and txn_year between '"
		 * +year1+"' and '"+year2+"' group by a.MID,a.DATE order by a.DATE desc"
		 * ;
		 */

		/*
		 * sql =
		 * "select f.city , f.BUSINESS_NAME AS MerchantName ,a.date , a.txn_year , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
		 * +
		 * "from for_settlement a INNER JOIN mid m on a.MID=m.MID INNER JOIN merchant f "
		 * + "ON f.MID_FK=m.ID INNER JOIN agent ag on f.AGID_FK=ag.id " +
		 * "where a.status='S' and time_stamp between '" + dat + "' and '" +
		 * dat1 + "'  group by a.MID,a.date order by a.time_stamp desc";
		 */

		/*sql = "select f.state , f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
				+ "from PRE_AUTH a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+ "where time_stamp between '"
				+ dat
				+ "' and '"
				+ dat1
				+ "'  group by a.MID,a.date order by a.time_stamp desc";*/
		
		sql = "select f.state , f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
				+ "from PRE_AUTH a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+ "where time_stamp between :dat and :dat1 "
				+ " group by a.MID,a.date order by a.time_stamp desc";
		
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			PreAuthorization fs = new PreAuthorization();
			fs.setLocation(rec[0].toString());
			fs.setMerchantName(rec[1].toString().toUpperCase());
			String rd = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[2].toString()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			fs.setDate(rd);
			Double d = new Double(rec[3].toString());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			fs.setAmount(output);
			fs.setAgentName(rec[4].toString());
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
	}

	@Override
	public void loadPreAuthByName(
			final PaginationBean<PreAuthorization> paginationBean,
			String merchantName, String date) {
		ArrayList<PreAuthorization> MerchantNameList = new ArrayList<PreAuthorization>();
		@SuppressWarnings("deprecation")
		Date dt = new Date(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dat = dateFormat.format(dt);

		/*String sql2 = "select a.time_stamp,a.status, f.BUSINESS_NAME AS MerchantName , a.location,a.Tid,a.time,round(a.AMOUNT),a.trx_id "
				+ "from PRE_AUTH a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID where a.time_stamp like '"
				+ dat
				+ "%' and upper(f.BUSINESS_NAME)='"
				+ merchantName
				+ "'  order by a.time_stamp desc";*/
		
		String sql2 = "select a.time_stamp,a.status, f.BUSINESS_NAME AS MerchantName , a.location,a.Tid,a.time,round(a.AMOUNT),a.trx_id "
				+ "from PRE_AUTH a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID where a.time_stamp like :dat and "
				+ "upper(f.BUSINESS_NAME)= :merchantName  order by a.time_stamp desc";

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql2);// .addEntity(ForSettlement.class);
		sqlQuery.setString("dat", dat+"%");
		sqlQuery.setString("merchantName", merchantName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			PreAuthorization fs = new PreAuthorization();
			String rd = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(rec[0].toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fs.setDate(rd);
			String status = null;
			if (rec[1].toString() == "A" || rec[1].toString().equals("A")
					|| rec[1].toString() == "P"
					|| rec[1].toString().equals("P")) {
				status = "PRE-AUTH SALE";
			} else if (rec[1].toString() == "C"
					|| rec[1].toString().equals("C")) {
				status = "PRE-AUTH CANCEL";
			} else if (rec[1].toString() == "R"
					|| rec[1].toString().equals("R")) {
				status = "REVERSAL";
			} else if (rec[1].toString() == "D"
					|| rec[1].toString().equals("D")
					|| rec[1].toString() == "E"
					|| rec[1].toString().equals("E")) {
				status = "PRE-AUTHORIZATION";
			} else if (rec[1].toString() == "S"
					|| rec[1].toString().equals("S")) {
				status = "SETTLED";
			}
			fs.setStatus(status);
			fs.setMerchantName(rec[2].toString().toUpperCase());
			if (rec[3] != null) {
				fs.setLocation(rec[3].toString());
			}
			if (rec[4] != null) {
				fs.setTid(rec[4].toString());
			}
			if (rec[5] != null) {
				String rt = null;
				try {
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(rec[5]
									.toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				fs.setTime(rt);
			}

			Double d = new Double(rec[6].toString());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);

			fs.setAmount(output);
			fs.setTrxId(new BigInteger(rec[7].toString()));
			MerchantNameList.add(fs);
		}
		paginationBean.setItemList(MerchantNameList);
	}

	// new method for contactName in transaction summary page admin & merchant
	// login
	@Override
	public TerminalDetails getTerminalDetailsByTid(String tid) {

		return (TerminalDetails) sessionFactory.getCurrentSession()
				.createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("tid", tid)).setMaxResults(1)
				.uniqueResult();
	}

	public MID loadMid(String mid) {
		/*System.out.print("MerchantDaoImpl:loadMid");
		*/
		return (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.like("mid", mid , MatchMode.ANYWHERE))
				.setMaxResults(1).uniqueResult();
	}
	
	public Merchant loadMerchantbyid(MID mid) {
		/*logger.info("MerchantDaoImpl:loadMerchant MID");*/
		// TODO Auto-generated method stub
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("mid", mid))
				.setMaxResults(1).uniqueResult();
	}
	// demo method 05-10-2016

	// start subagent volume summary 08/08/2016
	@Transactional(readOnly = true)
	public List<ForSettlement> subAgentVolume1(ArrayList<Criterion> props,
			SubAgent subAgent) {
		ArrayList<ForSettlement> fs1 = new ArrayList<ForSettlement>();
		String sql = null;
		int cDate = 0;
		int pDate = 0;

		Date dt = new Date();

		cDate = dt.getMonth() + 1;
		pDate = dt.getMonth();
		// System.out.println("Month :"+pDate +" "+cDate);
		/* ag.FIRST_NAME */

		/*sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.NAME  ,ag.id from "
				+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id where  month(a.time_stamp) between  '"
				+ pDate
				+ "'  and '"
				+ cDate
				+ "'"
				+ " and a.status='S' and ag.id='"
				+ subAgent.getId()
				+ "' "
				+ "group by month(a.time_stamp) order by a.time_stamp desc";*/
		
		sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.NAME  ,ag.id from "
				+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id "
				+ "where  month(a.time_stamp) between  :pDate  and :cDate"
				+ " and a.status='S' and ag.id= :id"
				+ "group by month(a.time_stamp) order by a.time_stamp desc";
		
		/*
		 * super.getPaginationItemsByPage(paginationBean,
		 * ForSettlement.class,props, Order.asc("merchantName"));
		 */
		logger.info("Query : " + sql);
		// System.out.println("no records found:" +sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setLong("pDate", pDate);
		sqlQuery.setLong("cDate", cDate);
		sqlQuery.setLong("id", subAgent.getId());
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();

		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			String su = rec[0].toString();

			fs.setDate(su);

			Double d = new Double(rec[1].toString());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			fs.setAmount(output);
			fs.setAgentName(rec[2].toString());
			// logger.info("subagent__id:" + rec[3].toString() );
			fs.setMerchantName(rec[3].toString() + "~SUBAGENT~"
					+ rec[2].toString());
			fs1.add(fs);
		}
		return fs1;

	}

	// Start agentvolume 06-01-2017

	@Transactional(readOnly = true)
	public List<AgentVolumeData> agentVolumeData(final String agentName) {

		List<AgentVolumeData> agentVolumeDataList = new ArrayList<AgentVolumeData>();
		String sql = null;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		String agId = null;
		String agent = null;
		String agentDet = null;
		String present = null;
		int cDate = 0;
		// String amount1= "0.00";

		int count = 0;

		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		List<Integer> listMonth = getAllMonth(cDate);

		// pDate = dt.getMonth()-2;
		// logger.info("Current Month : " + cDate);
		/*sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME ,ag.id from "
				+ " FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ " ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK= ag.id where a.time_stamp between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
				+ " and a.status='S' and ag.FIRST_NAME = '"
				+ agentName
				+ "' "
				+ " group by month(a.time_stamp) order by a.time_stamp desc";*/
		
		sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME ,ag.id from "
				+ " FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ " ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK= ag.id where a.time_stamp between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
				+ " and a.status='S' and ag.FIRST_NAME = :agentName "
				+ " group by month(a.time_stamp) order by a.time_stamp desc";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size : " + resultSet.size());
		logger.info("Size : " + listMonth.size());
		if (resultSet.size() <= 0) {

			Agent agent1 = (Agent) getSessionFactory()
					.createCriteria(Agent.class)
					.add(Restrictions.eq("firstName", agentName))
					.setMaxResults(1).uniqueResult();
			agId = agent1.getId().toString();
			agent = agent1.getFirstName();
			agentDet = agId + "~AGENT~" + agent;
			present = "No";
			for (int a : listMonth) {
				date.add(getMonth(a));
				amount.add("0.00");

			}

		} else {

			for (Object[] rec : resultSet) {

				int date1 = Integer.parseInt(rec[0].toString());

				logger.info("month  : " + date1 + " : "
						+ listMonth.get(count).intValue());
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count++;

				}// else{
				date.add(getMonth(date1));
				Double d = new Double(rec[1].toString());
				d = d / 100;

				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(d);
				amount.add(output);

				agId = rec[3].toString();
				agent = rec[2].toString();
				present = "Yes";
				agentDet = rec[3].toString() + "~AGENT~" + rec[2].toString();

				count++;
			}
			while(count < 4){
				date.add(getMonth(listMonth.get(count).intValue()));
				amount.add("0.00");
				count++;				
			}
		}

		AgentVolumeData agentVolumeData = new AgentVolumeData();
		agentVolumeData.setAgId(agId);
		agentVolumeData.setAgentName(agent);
		agentVolumeData.setAgentDet(agentDet);
		agentVolumeData.setAmount(amount);
		agentVolumeData.setDate(date);
		agentVolumeData.setTxnPresent(present);
		agentVolumeDataList.add(agentVolumeData);
		return agentVolumeDataList;
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

	private static List<Integer> getAllMonth(int month) {
		List<Integer> listMonth = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			if (month == 0) {
				listMonth.add(12);
			} else if (month == -1) {
				listMonth.add(11);
			} else if (month == -2) {
				listMonth.add(10);
			} else {
				listMonth.add(month);
			}
			month--;
		}
		return listMonth;
	}

	// sugAgent Volume Data 06-01-2017
	@Override
	public List<AgentVolumeData> subAgentVolumeData(SubAgent subAgent) {
		List<AgentVolumeData> agentVolumeDataList = new ArrayList<AgentVolumeData>();
		String sql = null;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		String agId = null;
		String agent = null;
		String agentDet = null;
		String present = null;
		int cDate = 0;
		// String amount1= "0.00";

		int count = 0;

		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		List<Integer> listMonth = getAllMonth(cDate);

		// pDate = dt.getMonth()-2;
		logger.info("Current Month : " + cDate);

		sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.NAME  ,ag.id from "
				+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id where  a.time_stamp between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
				+ " and a.status='S'  and ag.id= :id "
				+ "group by month(a.time_stamp) order by a.time_stamp  desc";

		logger.info("Query : " + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setLong("id", subAgent.getId());
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size : " + resultSet.size());
		logger.info("Size : " + listMonth.size());
		if (resultSet.size() <= 0) {

			// String sql1 =
			// "select a.ID,a.FIRST_NAME from mobiversa.agent a where a.FIRST_NAME='"+agentName+"'";
			logger.info(" size is ZERO  ");
			SubAgent agent1 = (SubAgent) getSessionFactory()
					.createCriteria(SubAgent.class)
					.add(Restrictions.eq("id", subAgent.getId()))
					.setMaxResults(1).uniqueResult();
			agId = agent1.getId().toString();
			agent = agent1.getName();
			agentDet = agId + "~SUBAGENT~" + agent;
			present = "No";
			for (int a : listMonth) {
				date.add(getMonth(a));
				amount.add("0.00");
				// count ++;

			}

		} else {

			for (Object[] rec : resultSet) {
				// for(Integer mon : listMonth){
				int date1 = Integer.parseInt(rec[0].toString());
				// if(cDate != date1 && count == 0){
				/*logger.info("month  : " + date1 + " : "
						+ listMonth.get(count).intValue());*/
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count++;

				}// else{
				date.add(getMonth(date1));
				Double d = new Double(rec[1].toString());
				d = d / 100;

				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(d);
				// logger.info(d + " " + pattern + " " + output);

				amount.add(output);

				agId = rec[3].toString();
				agent = rec[2].toString();
				present = "Yes";
				agentDet = rec[3].toString() + "~SUBAGENT~" + rec[2].toString();
				count++;
			}
			while(count < 4){
				date.add(getMonth(listMonth.get(count).intValue()));
				amount.add("0.00");
				count++;				
			}
		}
		// logger.info("agId :" + agId);
		// logger.info("agent :" + agent);
		// logger.info("agentDet :" + agentDet);
		// logger.info("amount :" + amount);
		// logger.info("date :" + date);
		AgentVolumeData agentVolumeData = new AgentVolumeData();
		agentVolumeData.setAgId(agId);
		agentVolumeData.setAgentName(agent);
		agentVolumeData.setAgentDet(agentDet);
		agentVolumeData.setAmount(amount);
		agentVolumeData.setDate(date);
		agentVolumeData.setTxnPresent(present);
		agentVolumeDataList.add(agentVolumeData);
		return agentVolumeDataList;
	}

	@Override
	public List<AgentVolumeData> merchantVolumeData(String agentName) {
		List<AgentVolumeData> agentVolumeDataList = new ArrayList<AgentVolumeData>();
		String sql = null;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		String agId = null;
		String agent = null;
		String agentDet = null;
		int cDate = 0;

		int count = 0;

		String sql2 = null;
		String data[] = null;
		String agid = null;
		String condt = null;
		String agname = null;
		if (agentName.contains("~")) {
			data = agentName.split("~");
			agid = data[0];
			condt = data[1];
			agname = data[2];
		}
		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		List<Integer> listMonth = getAllMonth(cDate);

		logger.info("listMonth : " + listMonth);

		if (condt.equals("AGENT")) {

			/*sql2 = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName ,f.Id from " 
																															 * ag
																															 * .
																															 * FIRST_NAME
																															 
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ " where a.time_stamp   between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S' and ag.FIRST_NAME='"
					+ agname
					+ "' and ag.id='"
					+ agid
					+ "'"
					+ " group by a.MID,month(a.time_stamp) order by a.time_stamp desc";*/
			
			sql2 = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName ,f.Id from " 
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ " where a.time_stamp   between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S' and ag.FIRST_NAME= :agname and ag.id= :agid"
					+ " group by a.MID,month(a.time_stamp) order by a.time_stamp desc";

		} else {

			/*sql2 = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName,f.Id from " // ag.FIRST_NAME
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ " ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id "
					+ " where a.time_stamp  between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S' and ag.NAME='"
					+ agname
					+ "' and ag.id= '"
					+ agid
					+ "' "
					+ " group by a.MID,month(a.time_stamp) order by a.time_stamp desc";*/
			
			sql2 = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName,f.Id from " // ag.FIRST_NAME
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ " ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id "
					+ " where a.time_stamp  between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S' and ag.NAME= :agname and ag.id= :agid "
					+ " group by a.MID,month(a.time_stamp) order by a.time_stamp desc";

		}

		logger.info("Query : " + sql2);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql2);// .addEntity(ForSettlement.class);
		sqlQuery.setString("agname", agname);
		sqlQuery.setString("agid", agid);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size : " + resultSet.size());
		logger.info("Size : " + listMonth.size());

		/* Start Sorting */
		List<MerchantVolumeData> listMVD = new ArrayList<MerchantVolumeData>();
		for (Object[] rec : resultSet) {
			MerchantVolumeData mvd = new MerchantVolumeData();

			mvd.setId(rec[3].toString());
			mvd.setName(rec[2].toString());
			mvd.setAmount(rec[1].toString());
			mvd.setMonth(rec[0].toString());
			listMVD.add(mvd);
		}

		Collections.sort(listMVD, new MerchantComparator());

		for (MerchantVolumeData mvd : listMVD) {
			/*logger.info("Sorted Data : " + mvd.getId() + ":" + mvd.getName()
					+ ":" + mvd.getAmount() + ":" + mvd.getMonth());*/
		}

		for (MerchantVolumeData mvd : listMVD) {
			//logger.info("getmonth:" + mvd.getMonth());

			int date1 = Integer.parseInt(mvd.getMonth());

			// String id = mvd.getId().toString();
			// logger.info("COUNT  : " + count);
			//logger.info("q month  : " + date1);
			while (listMonth.get(count).intValue() != date1) {
				// logger.info("While COUNT  : " + count);
				/*logger.info("c month  : " + listMonth.get(count).intValue()
						+ " q month :" + date1);*/
				date.add(getMonth(listMonth.get(count).intValue()));
				amount.add("0.00");
				count++;
			}
			//logger.info("End while");
			date.add(getMonth(date1));
			Double d = new Double(mvd.getAmount());
			d = d / 100;

			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			amount.add(output);
			agent = mvd.getName();
			count++;

			if (count == 4) {
				logger.info("Count :" + count);
				logger.info("agent :" + agent);
				logger.info("amount :" + amount);
				logger.info("date :" + date);
				AgentVolumeData agentVolumeData = new AgentVolumeData();
				agentVolumeData.setAgentName(agent);
				agentVolumeData.setAmount(amount);
				agentVolumeData.setDate(date);
				agentVolumeDataList.add(agentVolumeData);
				amount = new ArrayList<String>();
				date = new ArrayList<String>();
				count = 0;
			}

		}
		return agentVolumeDataList;
	}

	/* End Sorting */

	// new method for merchant volume start
	@Override
	public List<String> midByTransaction(String agentName) {

		String sql = null;
		List<String> listMid = new ArrayList<String>();

		String data[] = null;
		String agid = null;
		String condt = null;
		String agname = null;
		if (agentName.contains("~")) {
			data = agentName.split("~");
			agid = data[0];
			condt = data[1];
			agname = data[2];
		}

		if (condt.equals("AGENT")) {

			/*sql = "select f.BUSINESS_NAME ,m.mid,f.id from FOR_SETTLEMENT a "
					+ "INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "INNER JOIN AGENT ag on f.AGID_FK=ag.id  "
					+ "where a.time_stamp   between DATE_SUB(now(), INTERVAL 90 DAY)  and now() "
					+ "and a.status='S' and ag.FIRST_NAME='" + agname
					+ "' and ag.id='" + agid + "'"
					+ "group by a.MID order by a.mid desc ";*/
			
			sql = "select f.BUSINESS_NAME ,m.mid,f.id from FOR_SETTLEMENT a "
					+ " INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ " INNER JOIN AGENT ag on f.AGID_FK=ag.id  "
					+ " where a.time_stamp   between DATE_SUB(now(), INTERVAL 90 DAY)  and now() "
					+ " and a.status='S' and ag.FIRST_NAME= :agname and ag.id= :agid "
					+ " group by a.MID order by a.mid desc ";

		} else {

			/*sql = "select f.BUSINESS_NAME ,m.mid,f.id from FOR_SETTLEMENT a "
					+ "INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "INNER JOIN INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id "
					+ "where a.time_stamp   between DATE_SUB(now(), INTERVAL 90 DAY)  and now() "
					+ "and a.status='S' and ag.NAME='" + agname
					+ "' and ag.id= '" + agid + "' "
					+ "group by a.MID order by a.mid desc ";*/
			
			sql = "select f.BUSINESS_NAME ,m.mid,f.id from FOR_SETTLEMENT a "
					+ " INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ " INNER JOIN INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id "
					+ " where a.time_stamp   between DATE_SUB(now(), INTERVAL 90 DAY)  and now() "
					+ " and a.status='S' and ag.NAME= :agname and ag.id= :agid "
					+ " group by a.MID order by a.mid desc ";

		}

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("agname", agname);
		sqlQuery.setString("agid", agid);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size : " + resultSet.size());

		for (Object[] rec : resultSet) {

			listMid.add(rec[1].toString());
			//logger.info("display list MID: " + rec[0].toString());

		}
		return listMid;
	}

	// end

	// merchant volume multiple merchant start

	@Transactional(readOnly = true)
	public List<AgentVolumeData> getMerchantByMid(final String mid) {

		List<AgentVolumeData> agentVolumeDataList = new ArrayList<AgentVolumeData>();
		String sql = null;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		String agId = null;
		String agent = null;
		int cDate = 0;
		int count = 0;

		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		List<Integer> listMonth = getAllMonth(cDate);

		sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , f.BUSINESS_NAME AS MerchantName ,f.Id from FOR_SETTLEMENT a "
				+ " INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
				+ " where a.time_stamp   between DATE_SUB(now(), INTERVAL 90 DAY)  and now() "
				+ " and a.status='S' and a.mid= :mid "
				+ " group by a.MID,month(a.time_stamp) order by a.time_stamp desc ";

		logger.info("Query : " + sql);

		// System.out.println("no records found:" +sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mid", mid);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size : " + resultSet.size());
		logger.info("Size : " + listMonth.size());
		if (resultSet.size() <= 0) {

			Merchant merchant = (Merchant) getSessionFactory()
					.createCriteria(Merchant.class)
					.add(Restrictions.eq("mid", mid)).setMaxResults(1)
					.uniqueResult();
			agId = merchant.getId().toString();
			agent = merchant.getBusinessName();

			for (int a : listMonth) {
				date.add(getMonth(a));
				amount.add("0.00");

			}

		} else {

			for (Object[] rec : resultSet) {

				int date1 = Integer.parseInt(rec[0].toString());

				logger.info("month  : " + date1 + " : "
						+ listMonth.get(count).intValue());
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count++;

				}
				date.add(getMonth(date1));
				Double d = new Double(rec[1].toString());
				d = d / 100;

				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(d);

				amount.add(output);

				agId = rec[3].toString();
				agent = rec[2].toString();

				count++;
			}
			while(count < 4){
				date.add(getMonth(listMonth.get(count).intValue()));
				amount.add("0.00");
				count++;				
			}
		}

		AgentVolumeData agentVolumeData = new AgentVolumeData();
		agentVolumeData.setAgId(agId);
		agentVolumeData.setAgentName(agent);
		agentVolumeData.setAmount(amount);
		agentVolumeData.setDate(date);
		agentVolumeDataList.add(agentVolumeData);
		return agentVolumeDataList;
	}

	// end

	
	
	
	
	
	// sugAgent Volume Data 06-01-2017 new method for superAgent
	

		@Override
		public List<AgentVolumeData> superAgentVolumeData(SubAgent subAgent, Agent AgId) {
			List<AgentVolumeData> agentVolumeDataList = new ArrayList<AgentVolumeData>();
			String sql = null;
			List<String> date = new ArrayList<String>();
			List<String> amount = new ArrayList<String>();
			String agId = null;
			String agent = null;
			String agentDet = null;
			String present = null;
			int cDate = 0;
			// String amount1= "0.00";

			int count = 0;

			Date dt = new Date();
			cDate = dt.getMonth() + 1;

			List<Integer> listMonth = getAllMonth(cDate);

			// pDate = dt.getMonth()-2;
			logger.info("Current Month : " + cDate);

			sql = "select month(a.time_stamp) , sum(a.AMOUNT)  TotalAmount , ag.NAME  ,ag.id from "
					+ "FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
					+ "ON f.MID_FK=m.ID INNER JOIN SUB_AGENT ag on f.SUBAGID_FK=ag.id where  a.time_stamp between DATE_SUB(now(), INTERVAL 90 DAY)  and now()"
					+ " and a.status='S'  and ag.id= :id "
					+ "group by month(a.time_stamp) order by a.time_stamp  desc";

			logger.info("Query : " + sql);

			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
			sqlQuery.setLong("id", subAgent.getId());
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet = sqlQuery.list();
			logger.info("Size : " + resultSet.size());
			logger.info("Size : " + listMonth.size());
			if (resultSet.size() <= 0) {

				// String sql1 =
				// "select a.ID,a.FIRST_NAME from mobiversa.agent a where a.FIRST_NAME='"+agentName+"'";
				logger.info(" size is ZERO  ");
				SubAgent agent1 = (SubAgent) getSessionFactory()
						.createCriteria(SubAgent.class)
						.add(Restrictions.eq("id", subAgent.getId()))
						.setMaxResults(1).uniqueResult();
				agId = agent1.getId().toString();
				agent = agent1.getName();
				agentDet = agId + "~SUBAGENT~" + agent;
				present = "No";
				for (int a : listMonth) {
					date.add(getMonth(a));
					amount.add("0.00");
					// count ++;

				}

			} else {

				for (Object[] rec : resultSet) {
					// for(Integer mon : listMonth){
					int date1 = Integer.parseInt(rec[0].toString());
					// if(cDate != date1 && count == 0){
					logger.info("month  : " + date1 + " : "
							+ listMonth.get(count).intValue());
					while (listMonth.get(count).intValue() != date1) {
						date.add(getMonth(listMonth.get(count).intValue()));
						amount.add("0.00");
						count++;

					}// else{
					date.add(getMonth(date1));
					Double d = new Double(rec[1].toString());
					d = d / 100;

					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					// logger.info(d + " " + pattern + " " + output);

					amount.add(output);

					agId = rec[3].toString();
					agent = rec[2].toString();
					present = "Yes";
					agentDet = rec[3].toString() + "~SUBAGENT~" + rec[2].toString();
					count++;
				}
				while(count < 4){
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count++;				
				}
			}
			// logger.info("agId :" + agId);
			// logger.info("agent :" + agent);
			// logger.info("agentDet :" + agentDet);
			// logger.info("amount :" + amount);
			// logger.info("date :" + date);
			AgentVolumeData agentVolumeData = new AgentVolumeData();
			agentVolumeData.setAgId(agId);
			agentVolumeData.setAgentName(agent);
			agentVolumeData.setAgentDet(agentDet);
			agentVolumeData.setAmount(amount);
			agentVolumeData.setDate(date);
			agentVolumeData.setTxnPresent(present);
			agentVolumeDataList.add(agentVolumeData);
			return agentVolumeDataList;
		}
		//}

		//all transaction export new method
		@SuppressWarnings("unchecked")
		@Override
		public List<ForSettlement> listAllETransactionDetails(
							ArrayList<Criterion> props, String date, String date1) {

			logger.info("Inside listAllTransactionDetails : " + date + "     "
					+ date1);
			/*super.getPaginationItemsByPage(paginationBean, ForSettlement.class,
					props, Order.desc("timeStamp"));*/
			if(props.size() == 2){
				return (List<ForSettlement>) getSessionFactory().createCriteria(ForSettlement.class).add(props.get(0)).add(props.get(1)).addOrder(Order.desc("timeStamp")).list();
			}else{
				return (List<ForSettlement>) getSessionFactory().createCriteria(ForSettlement.class).add(props.get(0)).addOrder(Order.desc("timeStamp")).list();
			}
		}
	
}
