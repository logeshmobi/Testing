package com.mobiversa.payment.dao;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.ReportForSettlement;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ReportDataDAOImpl extends BaseDAOImpl implements ReportDataDAO {

	
	
	@Override
	@Transactional(readOnly = true)
	public void getDetailsUM(final PaginationBean<ReportForSettlement> paginationBean,String days) {
		
		logger.info("inn");
		List<ReportForSettlement> reportForSettlement = new ArrayList<ReportForSettlement>();
		ArrayList<UMEcomTxnResponse> listFS = new ArrayList<UMEcomTxnResponse>();
		String sql = null;
		
		String sql1 = null;
		
		int day = Integer.parseInt(days);
		
		sql = "select fs.F354_TID,max(fs.TIME_STAMP) as data,fs.F001_MID, curdate() as mydate,fs.F007_TXNAMT from mobiversa.UM_ECOM_TXNRESPONSE fs  group by fs.F354_TID order by data";

		//logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : "+resultSet.size());
		for (Object[] rec : resultSet) {
			UMEcomTxnResponse fs = new UMEcomTxnResponse();
			if (rec[0] != null) {
				fs.setF354_TID(rec[0].toString());
			}
			if (rec[1] != null) {
				fs.setTimeStamp(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setF001_MID(rec[2].toString());
			}
			if (rec[3] != null) {
				fs.setSentDate(rec[3].toString());
			}

			Double d = new Double(rec[4].toString());
			d = d / 100;
			// logger.info("data : " + d);
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			// logger.info(d + " " + pattern + " " + output);
			fs.setF007_TxnAmt(output);

			// fs.setAmount(rec[4].toString());
			listFS.add(fs);
		}
		
		/*sql1 = "select t.DEVICE_ID,t.TID,t.MERCHANT_ID,f.BUSINESS_NAME, curdate() - interval 90 day as mydate "
				+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
				+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
				+ "where t.TID ='22000031' and t.MERCHANT_ID='000002203000084'";
*/
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		for(UMEcomTxnResponse fs : listFS){
			
			Date d1 = null;
			Date d2 = null;
			
			try {
				d1 = format.parse(fs.getTimeStamp());
				d2 = format.parse(fs.getSentDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
					
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			//logger.info("TID : "+ fs.getTid()+" MID : "+fs.getMid()+" Date : "+fs.getTimeStamp()+"  Diff Date : "+fs.getDate());
			//logger.info("TID : "+ fs.getTid()+" MID : "+fs.getMid()+" Date : "+dt1+"  My Date : "+dt1 + " Diff Date : "+Days.daysBetween(dt1, dt2).getDays());
			if(Days.daysBetween(dt1, dt2).getDays() > day){
				//logger.info(" IF TID : "+ fs.getTid()+" MID : "+fs.getMid()+" Date : "+fs.getTimeStamp()+"  Diff Date : "+Days.daysBetween(dt1, dt2).getDays());
				
				/*sql1 = "select t.DEVICE_ID,t.TID,t.MERCHANT_ID,f.BUSINESS_NAME "
						+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
						+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
						+ "where t.TID ='"+fs.getTid()+"' and t.MERCHANT_ID='"+fs.getMid()+"'";*/
				
				sql1 = "select t.DEVICE_ID,t.TID,t.MERCHANT_ID,f.BUSINESS_NAME "
						+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.UM_MOTO_MID or t.MERCHANT_ID=m.UM_EZYWAY_MID or t.MERCHANT_ID=m.UM_EZYREC_MID "
						+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
						+ "where t.TID = :tid and t.MERCHANT_ID= :mid";
				
				//logger.info("Query TERMINAL_DETAILS : " + sql1);
				Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);// .addEntity(ForSettlement.class);
				sqlQuery1.setString("tid", fs.getF354_TID());
				sqlQuery1.setString("mid", fs.getF001_MID());
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet1 = sqlQuery1.list();
				//logger.info("Number of records in the List : "+resultSet.size());
				for (Object[] rec : resultSet1) {
					ReportForSettlement rfs = new ReportForSettlement();
					rfs.setDeviceId(rec[0].toString());
					rfs.setTid(rec[1].toString());
					rfs.setMid(rec[2].toString());
					rfs.setMerchantName(rec[3].toString());
					rfs.setAmount(fs.getF007_TxnAmt());
					rfs.setDate(fs.getTimeStamp());
					rfs.setNoofDays(""+Days.daysBetween(dt1, dt2).getDays());
					//logger.info(" TID : "+ rfs.getTid()+" MID : "+rfs.getMid()+" Name : "+rfs.getMerchantName()+" Date : "+rfs.getDate()+"  Amount : "+rfs.getAmount()+" No Of Days Not Used :"+rfs.getNoofDays());
					reportForSettlement.add(rfs);
				}
				
			}
			
		}
		
		/*logger.info("Number of Merchant in the List :"+reportForSettlement.size());
		
		for(ReportForSettlement rfss : reportForSettlement){
			logger.info(" TID : "+ rfss.getTid()+" MID : "+rfss.getMid()+" Name : "+rfss.getMerchantName()+" Date : "+rfss.getDate()+"  Amount : "+rfss.getAmount()+" No Of Days Not Used :"+rfss.getNoofDays());
		}*/
		paginationBean.setItemList(reportForSettlement);
		//return reportForSettlement;
		
	}

	
	
	
	
	
	
	
	
	@Override
	@Transactional(readOnly = true)
	public void getDetails(final PaginationBean<ReportForSettlement> paginationBean,String days) {
		List<ReportForSettlement> reportForSettlement = new ArrayList<ReportForSettlement>();
		ArrayList<ForSettlement> listFS = new ArrayList<ForSettlement>();
		String sql = null;
		
		String sql1 = null;
		
		int day = Integer.parseInt(days);
		
		sql = "select fs.TID,max(fs.TIME_STAMP) as data,fs.MID, curdate() as mydate,fs.AMOUNT from mobiversa.FOR_SETTLEMENT fs  group by fs.TID order by data";

		//logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : "+resultSet.size());
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			if (rec[0] != null) {
				fs.setTid(rec[0].toString());
			}
			if (rec[1] != null) {
				fs.setTimeStamp(rec[1].toString());
			}
			if (rec[2] != null) {
				fs.setMid(rec[2].toString());
			}
			if (rec[3] != null) {
				fs.setDate(rec[3].toString());
			}

			Double d = new Double(rec[4].toString());
			d = d / 100;
			// logger.info("data : " + d);
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			// logger.info(d + " " + pattern + " " + output);
			fs.setAmount(output);

			// fs.setAmount(rec[4].toString());
			listFS.add(fs);
		}
		
		/*sql1 = "select t.DEVICE_ID,t.TID,t.MERCHANT_ID,f.BUSINESS_NAME, curdate() - interval 90 day as mydate "
				+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
				+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
				+ "where t.TID ='22000031' and t.MERCHANT_ID='000002203000084'";
*/
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		for(ForSettlement fs : listFS){
			
			Date d1 = null;
			Date d2 = null;
			
			try {
				d1 = format.parse(fs.getTimeStamp());
				d2 = format.parse(fs.getDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
					
			DateTime dt1 = new DateTime(d1);
			DateTime dt2 = new DateTime(d2);
			//logger.info("TID : "+ fs.getTid()+" MID : "+fs.getMid()+" Date : "+fs.getTimeStamp()+"  Diff Date : "+fs.getDate());
			//logger.info("TID : "+ fs.getTid()+" MID : "+fs.getMid()+" Date : "+dt1+"  My Date : "+dt1 + " Diff Date : "+Days.daysBetween(dt1, dt2).getDays());
			if(Days.daysBetween(dt1, dt2).getDays() > day){
				//logger.info(" IF TID : "+ fs.getTid()+" MID : "+fs.getMid()+" Date : "+fs.getTimeStamp()+"  Diff Date : "+Days.daysBetween(dt1, dt2).getDays());
				
				/*sql1 = "select t.DEVICE_ID,t.TID,t.MERCHANT_ID,f.BUSINESS_NAME "
						+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID "
						+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
						+ "where t.TID ='"+fs.getTid()+"' and t.MERCHANT_ID='"+fs.getMid()+"'";*/
				
				sql1 = "select t.DEVICE_ID,t.TID,t.MERCHANT_ID,f.BUSINESS_NAME "
						+ "from mobiversa.TERMINAL_DETAILS t INNER JOIN MID m on t.MERCHANT_ID=m.MID or t.MERCHANT_ID=m.UM_MID "
						+ "INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
						+ "where t.TID = :tid and t.MERCHANT_ID= :mid";
				
				//logger.info("Query TERMINAL_DETAILS : " + sql1);
				Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);// .addEntity(ForSettlement.class);
				sqlQuery1.setString("tid", fs.getTid());
				sqlQuery1.setString("mid", fs.getMid());
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet1 = sqlQuery1.list();
				//logger.info("Number of records in the List : "+resultSet.size());
				for (Object[] rec : resultSet1) {
					ReportForSettlement rfs = new ReportForSettlement();
					rfs.setDeviceId(rec[0].toString());
					rfs.setTid(rec[1].toString());
					rfs.setMid(rec[2].toString());
					rfs.setMerchantName(rec[3].toString());
					rfs.setAmount(fs.getAmount());
					rfs.setDate(fs.getTimeStamp());
					rfs.setNoofDays(""+Days.daysBetween(dt1, dt2).getDays());
					//logger.info(" TID : "+ rfs.getTid()+" MID : "+rfs.getMid()+" Name : "+rfs.getMerchantName()+" Date : "+rfs.getDate()+"  Amount : "+rfs.getAmount()+" No Of Days Not Used :"+rfs.getNoofDays());
					reportForSettlement.add(rfs);
				}
				
			}
			
		}
		
		/*logger.info("Number of Merchant in the List :"+reportForSettlement.size());
		
		for(ReportForSettlement rfss : reportForSettlement){
			logger.info(" TID : "+ rfss.getTid()+" MID : "+rfss.getMid()+" Name : "+rfss.getMerchantName()+" Date : "+rfss.getDate()+"  Amount : "+rfss.getAmount()+" No Of Days Not Used :"+rfss.getNoofDays());
		}*/
		paginationBean.setItemList(reportForSettlement);
		//return reportForSettlement;
		
	}

}
