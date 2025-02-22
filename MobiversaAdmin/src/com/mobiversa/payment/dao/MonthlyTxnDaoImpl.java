package com.mobiversa.payment.dao;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MonthlyTxnDetails;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MonthlyTxnDaoImpl extends BaseDAOImpl implements MonthlyTxnDao {

	@Override
	@Transactional(readOnly = true)
	public void getMonthlyTxnDetails(
			PaginationBean<MonthlyTxnDetails> paginationBean, String month,
			String year) {

		List<MonthlyTxnDetails> reportForSettlement = new ArrayList<MonthlyTxnDetails>();
		ArrayList<ForSettlement> listFS = new ArrayList<ForSettlement>();
		String sql = null;
		
		String sql1 = null;
		SimpleDateFormat format = new SimpleDateFormat("MM");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		Date date= new Date();
		int monthly = 0;
		if(month != null){
			monthly = Integer.parseInt(month);
		}else{
			monthly = Integer.parseInt(format.format(date));
		}
		if(year == null){
			year = format1.format(date);
		}
		
		sql = "select fs.TID,fs.MID,sum(fs.AMOUNT), count(*) from mobiversa.FOR_SETTLEMENT fs where month(fs.TIME_STAMP) = :month and year(fs.TIME_STAMP)= :year  group by fs.TID";
		logger.info("query : "+sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setInteger("month", monthly);
		sqlQuery.setString("year", year);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : "+resultSet.size());
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			fs.setTid(rec[0].toString());
			fs.setMid(rec[1].toString());
			
			Double d = new Double(rec[2].toString());
			d = d / 100;
			//logger.info("data : " + d);
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			//logger.info(d + " " + pattern + " " + output);
			fs.setAmount(output);
			fs.setBatchNo(rec[3].toString());
			//fs.setAmount(rec[4].toString());
			listFS.add(fs);
		}
		
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		
		
		for(ForSettlement fs : listFS){
			/*sql1 = "select t.DEVICE_ID,t.TID,distinct(t.MERCHANT_ID),f.BUSINESS_NAME,f.ACTIVATE_DATE "
					+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "where t.TID = :tid and t.MERCHANT_ID= :mid";*/
			
			sql1 = "select distinct(t.TID),t.MERCHANT_ID,f.BUSINESS_NAME,f.ACTIVATE_DATE "
					+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "where t.TID = :tid and t.MERCHANT_ID= :mid";
			
			logger.info("Query TERMINAL_DETAILS : " + sql1);
			logger.info("test mid:" + fs.getMid());
			logger.info("test tid:" + fs.getTid());
			logger.info("test time stamp:" + fs.getTimeStamp());
			
			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);// .addEntity(ForSettlement.class);
			sqlQuery1.setString("tid", fs.getTid());
			sqlQuery1.setString("mid", fs.getMid());
			@SuppressWarnings("unchecked")
			
			
			List<Object[]> resultSet1 = sqlQuery1.list();
			
			for (Object[] rec : resultSet1) {
				MonthlyTxnDetails rfs = new MonthlyTxnDetails();
				/*rfs.setDeviceId(rec[0].toString());
				rfs.setTid(rec[1].toString());
				rfs.setMid(rec[2].toString());
				rfs.setMerchantName(rec[3].toString());
				rfs.setAmount(fs.getAmount());
				//Date d1 = null;
				if(rec[4].toString() != null){
					try {

					    String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rec[4].toString()));
					    rfs.setDate(rd);
					} catch (ParseException e) {
					    e.printStackTrace();
					}
				}
				
				rfs.setNoofTxn(fs.getBatchNo());*/
				
				//rfs.setDeviceId(rec[0].toString());
				rfs.setTid(rec[0].toString());
				rfs.setMid(rec[1].toString());
				rfs.setMerchantName(rec[2].toString());
				rfs.setAmount(fs.getAmount());
				//Date d1 = null;
				if(rec[3].toString() != null){
					try {

						String rd = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rec[3].toString()));
					    rfs.setDate(rd);
					} catch (ParseException e) {
					    e.printStackTrace();
					}
				}
				
				rfs.setNoofTxn(fs.getBatchNo());
				
				//logger.info(" TID : "+ rfs.getTid()+" MID : "+rfs.getMid()+" Name : "+rfs.getMerchantName()+" Date : "+rfs.getDate()+"  Amount : "+rfs.getAmount()+" No Of Days Not Used :"+rfs.getNoofDays());
				reportForSettlement.add(rfs);
			}
			
		}
		
		paginationBean.setItemList(reportForSettlement);

	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<MonthlyTxnDetails> getMonthlyTxnExport( String month,String year) {

		List<MonthlyTxnDetails> reportForSettlement = new ArrayList<MonthlyTxnDetails>();
		ArrayList<ForSettlement> listFS = new ArrayList<ForSettlement>();
		String sql = null;
		
		String sql1 = null;
		SimpleDateFormat format = new SimpleDateFormat("MM");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
		Date date= new Date();
		int monthly = 0;
		if(month != null)
		{
			monthly = Integer.parseInt(month);
		}
		else
		{
			monthly = Integer.parseInt(format.format(date));
		}
		if(year == null){
			year = format1.format(date);
		}
		
		sql = "select fs.TID,fs.MID,sum(fs.AMOUNT), count(*) from mobiversa.FOR_SETTLEMENT fs where month(fs.TIME_STAMP) = :month and year(fs.TIME_STAMP)= :year  group by fs.TID";
		logger.info("query : "+sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setInteger("month", monthly);
		sqlQuery.setString("year", year);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : "+resultSet.size());
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			fs.setTid(rec[0].toString());
			fs.setMid(rec[1].toString());
			
			Double d = new Double(rec[2].toString());
			d = d / 100;
			//logger.info("data : " + d);
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			//logger.info(d + " " + pattern + " " + output);
			fs.setAmount(output);
			fs.setBatchNo(rec[3].toString());
			//fs.setAmount(rec[4].toString());
			listFS.add(fs);
		}
		
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		
		for(ForSettlement fs : listFS){
			/*sql1 = "select t.DEVICE_ID,t.TID,distinct(t.MERCHANT_ID),f.BUSINESS_NAME,f.ACTIVATE_DATE "
					+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "where t.TID = :tid and t.MERCHANT_ID= :mid";*/
			
			sql1 = "select distinct(t.TID),t.MERCHANT_ID,f.BUSINESS_NAME,f.ACTIVATE_DATE "
					+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
					+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+ "where t.TID = :tid and t.MERCHANT_ID= :mid";
			
			logger.info("Query TERMINAL_DETAILS : " + sql1);
			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);// .addEntity(ForSettlement.class);
			sqlQuery1.setString("tid", fs.getTid());
			sqlQuery1.setString("mid", fs.getMid());
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet1 = sqlQuery1.list();
			
			for (Object[] rec : resultSet1) {
				MonthlyTxnDetails rfs = new MonthlyTxnDetails();
				/*rfs.setDeviceId(rec[0].toString());
				rfs.setTid(rec[1].toString());
				rfs.setMid(rec[2].toString());
				rfs.setMerchantName(rec[3].toString());
				rfs.setAmount(fs.getAmount());
				//Date d1 = null;
				if(rec[4].toString() != null){
					try {

					    String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rec[4].toString()));
					    rfs.setDate(rd);
					} catch (ParseException e) {
					    e.printStackTrace();
					}
				}
				
				rfs.setNoofTxn(fs.getBatchNo());*/
				
				//rfs.setDeviceId(rec[0].toString());
				rfs.setTid(rec[0].toString());
				rfs.setMid(rec[1].toString());
				rfs.setMerchantName(rec[2].toString());
				rfs.setAmount(fs.getAmount());
				//Date d1 = null;
				if(rec[3].toString() != null){
					try {

						String rd = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(rec[3].toString()));
					    rfs.setDate(rd);
					} catch (ParseException e) {
					    e.printStackTrace();
					}
				}
				
				rfs.setNoofTxn(fs.getBatchNo());
				
				//logger.info(" TID : "+ rfs.getTid()+" MID : "+rfs.getMid()+" Name : "+rfs.getMerchantName()+" Date : "+rfs.getDate()+"  Amount : "+rfs.getAmount()+" No Of Days Not Used :"+rfs.getNoofDays());
				reportForSettlement.add(rfs);
			}
			
		}
		
		return reportForSettlement;

	}

}
