package com.mobiversa.payment.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.BizAppSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobiProductMDR;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.RegMobileUser;

@Component
@Repository
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class MDRDaoImpl  extends BaseDAOImpl implements MDRDao {
	
	private static final Logger logger=Logger.getLogger(MDRDaoImpl.class.getName());
	


		
	/*@SuppressWarnings("unchecked")
	public List<MobiMDR> getMDRDetailsByMid(String mid) {

		logger.info("getMDRDetailsByMid : ");

		return (List<MobiMDR>) getSessionFactory().createCriteria(MobiMDR.class).add(Restrictions.eq("mid", mid))
				.list();

	}*/
	
	



	@Override
	public MobiMDR loadCardAndMDRDetails(String mid) {
	
		
		return (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class)
				.add(Restrictions.eq("mid", mid)).setMaxResults(1).uniqueResult();
	}



	@Override
	public MobiMDR loadMDRById(String mid,String brand) {
		return (MobiMDR) getSessionFactory().createCriteria(MobiMDR.class).add(Restrictions.eq("mid", mid))
				.add(Restrictions.eq("cardBrand", brand))
				.setMaxResults(1).uniqueResult();
	}

	




	@SuppressWarnings("unchecked")
	@Override
	public  List<SettlementMDR> listAllSettlementMDR() {
	
		Criterion isStatusHold = Restrictions.like("status", "H");
		Criterion isStatusSettle = Restrictions.like("status","S");
		
		Criterion completeCondition = 
		Restrictions.disjunction().add(isStatusHold)
		                              .add(isStatusSettle);
		  
		
		return  (List<SettlementMDR>) getSessionFactory().createCriteria(SettlementMDR.class)
				.add(completeCondition)
				.list();
		
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  void listAllFpxSettlementMDR(PaginationBean<FpxTransaction> paginationBean, String date) {
	
		ArrayList<FpxTransaction> fss = new ArrayList<FpxTransaction>();
		String sql = null;
	
		
		sql = "select b.SETTLED_DATE,b.TXNAMOUNT,b.BUYERBANKID,b.SELLEREXORDERNO,b.SELLERORDERNO, "
			  + "b.HOST_MDR_AMT,b.MOBI_MDR_AMT,b.MDR_AMT,m.BUSINESS_NAME,b.MID from FPX_TRANSACTION b  "
			  + "INNER JOIN MID mi ON b.MID = mi.MID or  b.MID = mi.MOTO_MID or  b.MID = mi.EZYPASS_MID "
			  + "or b.MID = mi.EZYREC_MID or  b.MID = mi.EZYWAY_MID or  b.MID = mi.UM_MID "  
			  + "or b.MID = mi.UM_MOTO_MID or  b.MID = mi.UM_EZYWAY_MID "
			  + "INNER JOIN MERCHANT m ON mi.MERCHANT_FK = m.ID "
			  + "where b.SETTLED_DATE LIKE '"+date+"%' ";
		
		  
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		/*sqlQuery.setString("from", from);*/
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		
		for (Object[] rec : resultSet) {
			
			FpxTransaction boostss = new FpxTransaction();
			if(rec[0]!=null){
				boostss.setSettledDate(rec[0].toString());
			}else {
				boostss.setSettledDate("");
			}
			
			if(rec[1]!=null){
				boostss.setTxnAmount(rec[1].toString());
			}else {
				boostss.setTxnAmount("");
			}
			if(rec[2]!=null){
				boostss.setBuyerBankId(rec[2].toString());
			}else {
				boostss.setBuyerBankId("");
			}
			if(rec[3]!=null){
				boostss.setSellerExOrderNo(rec[3].toString());
			}else {
				boostss.setSellerExOrderNo("");
			}
			if(rec[4]!=null){
				boostss.setSellerOrderNo(rec[4].toString());
			}else {
				boostss.setSellerOrderNo("");
			}
			if(rec[5]!=null){
				boostss.setHostMdrAmt(rec[5].toString());
			}else {
				boostss.setHostMdrAmt("");
			}
			if(rec[6]!=null){
				boostss.setMobiMdrAmt(rec[6].toString());
			}else {
				boostss.setMobiMdrAmt("");
			}
			if(rec[7]!=null){
				boostss.setMdrAmt(rec[7].toString());
			}else {
				boostss.setMdrAmt("");
			}
			if(rec[8]!=null){
				boostss.setMakerName(rec[8].toString());
			}else {
				boostss.setMakerName("");
			}
			if(rec[9]!=null){
                boostss.setMid(rec[9].toString());
            }else {
                boostss.setMid("");
            }
			fss.add(boostss);
		}
		paginationBean.setItemList(fss);
		logger.info("No of Records: "+paginationBean.getItemList().size());
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<BizAppSettlement> listBizAppSettlementMDR() {
	
		Criterion isStatusHold = Restrictions.like("status", "S");
		Criterion isStatusSettle = Restrictions.like("status","S");
		
		Criterion completeCondition = 
		Restrictions.disjunction().add(isStatusHold)
		                              .add(isStatusSettle);
		  
		
		return  (List<BizAppSettlement>) getSessionFactory().createCriteria(BizAppSettlement.class)
				.add(completeCondition)
				.list();
		
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public  List<SettlementMDR> listAllMobiliteSettlementMDR() {
	
		Criterion isStatusHold = Restrictions.like("status", "L");
		Criterion isStatusSettle = Restrictions.like("status","SS");
		
		Criterion completeCondition = 
		Restrictions.disjunction().add(isStatusHold)
		                              .add(isStatusSettle);
		  
		
		return  (List<SettlementMDR>) getSessionFactory().createCriteria(SettlementMDR.class)
				.add(completeCondition)
				.list();
		
	
	}



	/*@SuppressWarnings("unchecked")
	@Override
	public List<SettlementMDR> listSettlementMDRById(String mid) {
		logger.info("getSettlementMDRByMid : " );
		return (List<SettlementMDR>) getSessionFactory().createCriteria(SettlementMDR.class).add(Restrictions.eq("mid", mid))
							.list();
	}*/





	/*@Override
	public List<SettlementMDR> listSettlementMDRById(ArrayList<Criterion> criterionList) {
		// TODO Auto-generated method stub
		return null;
		super.getPaginationItemsByPage(paginationBean, SettlementMDR.class,
				criterionList, Order.asc("id"));
	}*/





	@Override
	public void listSettlementMDRById(PaginationBean<SettlementMDR> paginationBean,
			ArrayList<Criterion> criterionList) {
		super.getPaginationItemsByPage(paginationBean, SettlementMDR.class,
				criterionList, Order.desc("timeStamp"));
	}
	
	@Override
	public void listFpxSettlementMDRByMid(PaginationBean<FpxTransaction> paginationBean,
			Merchant merchant,String date) {
		
			logger.info("Inside listFpxSettlementMDRByMid : " + date );
			MID midDetails = merchant.getMid();
			 List<String> midList = new ArrayList<String>();
			if(midDetails !=null) {
				if(midDetails.getMid() != null) {
		    		if(!midDetails.getMid().isEmpty()) {
		    			
		    			midList.add(midDetails.getMid());
		    			
		    		}
		    		
		    	}
		    	if(midDetails.getMotoMid() != null) {
		    		if(!midDetails.getMotoMid().isEmpty()) {
		    			
		    			midList.add(midDetails.getMotoMid());
		    		}
		    		
		    	}
		    	if(midDetails.getEzypassMid() != null) {
		    		if(!midDetails.getEzypassMid().isEmpty()) {
		    			
		    			midList.add(midDetails.getEzypassMid());
		    		}
		    		
		    	}
		    	
		    	if(midDetails.getEzywayMid() != null) {
		    		if(!midDetails.getEzywayMid().isEmpty()) {
		    			
		    			midList.add(midDetails.getEzywayMid());
		    		}
		    		
		    	}
		    	
		    	if(midDetails.getEzyrecMid() != null) {
		    		if(!midDetails.getEzyrecMid().isEmpty()) {
		    			
		    			midList.add(midDetails.getEzyrecMid());
		    		}
		    		
		    	}
		    	
		    	if(midDetails.getUmMid() != null) {
		    		if(!midDetails.getUmMid().isEmpty()) {
		    			
		    			midList.add(midDetails.getUmMid());
		    		}
		    		
		    	}
		    	
		    	if(midDetails.getUmMotoMid() != null) {
		    		if(!midDetails.getUmMotoMid().isEmpty()) {
		    			midList.add(midDetails.getUmMotoMid());
		    		}
		    		
		    	}
		    	if(midDetails.getUmEzywayMid() != null) {
		    		if(!midDetails.getUmEzywayMid().isEmpty()) {
		    			midList.add(midDetails.getUmEzywayMid());
		    		}
		    		
		    	}
			}
			int u =0;
			StringBuffer str = new StringBuffer();
		    for(String strMid : midList) {
		    	
		    	if(u == 0) {
		    		str.append("\"");
	    			str.append(strMid);
	    			str.append("\"");
	    			u++;
		    	}else {		    	
			    	str.append(",\"");
	    			str.append(strMid);
	    			str.append("\"");
		    	}
		    }
		    logger.info("String of MIDs:  "+str);
		    String merchantName = merchant.getBusinessName().replaceAll("[^a-zA-Z0-9\\s+]", "");
			logger.info("merchantName: "+merchantName);
			
			ArrayList<FpxTransaction> fss = new ArrayList<FpxTransaction>();
			String sql = null;
			sql = "select b.SETTLED_DATE,b.TXNAMOUNT,b.BUYERBANKID,b.SELLEREXORDERNO,b.SELLERORDERNO, "
					  + "b.HOST_MDR_AMT,b.MOBI_MDR_AMT,b.MDR_AMT,'"+ merchantName +"',b.MID from FPX_TRANSACTION b  "
					  + "where b.MID IN ("+str+") AND b.SETTLED_DATE LIKE '"+date+"%' ";
				
				  
				logger.info("Query : " + sql);
				Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
				/*sqlQuery.setString("from", from);*/
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet = sqlQuery.list();
				logger.info("Number of records in the List : " + resultSet.size());
				
				for (Object[] rec : resultSet) {
					
					FpxTransaction boostss = new FpxTransaction();
					if(rec[0]!=null){
						boostss.setSettledDate(rec[0].toString());
					}else {
						boostss.setSettledDate("");
					}
					
					if(rec[1]!=null){
						boostss.setTxnAmount(rec[1].toString());
					}else {
						boostss.setTxnAmount("");
					}
					if(rec[2]!=null){
						boostss.setBuyerBankId(rec[2].toString());
					}else {
						boostss.setBuyerBankId("");
					}
					if(rec[3]!=null){
						boostss.setSellerExOrderNo(rec[3].toString());
					}else {
						boostss.setSellerExOrderNo("");
					}
					if(rec[4]!=null){
						boostss.setSellerOrderNo(rec[4].toString());
					}else {
						boostss.setSellerOrderNo("");
					}
					if(rec[5]!=null){
						boostss.setHostMdrAmt(rec[5].toString());
					}else {
						boostss.setHostMdrAmt("");
					}
					if(rec[6]!=null){
						boostss.setMobiMdrAmt(rec[6].toString());
					}else {
						boostss.setMobiMdrAmt("");
					}
					if(rec[7]!=null){
						boostss.setMdrAmt(rec[7].toString());
					}else {
						boostss.setMdrAmt("");
					}
					if(rec[8]!=null){
						boostss.setMakerName(rec[8].toString());
					}else {
						boostss.setMakerName("");
					}
					if(rec[9]!=null){
                        boostss.setMid(rec[9].toString());
                    }else {
                        boostss.setMid("");
                    }
					fss.add(boostss);
				}
				paginationBean.setItemList(fss);
				logger.info("No of Records: "+paginationBean.getItemList().size());
	}
	
	@Override
	public void listBizappSettlementMDRById(PaginationBean<BizAppSettlement> paginationBean,
			ArrayList<Criterion> criterionList) {
		super.getPaginationItemsByPage(paginationBean, BizAppSettlement.class,
				criterionList, Order.desc("timeStamp"));
	}



	@Override
	public void listproMDRByMid(PaginationBean<MobiProductMDR> paginationBean, ArrayList<Criterion> criterionList) {
		super.getPaginationItemsByPage(paginationBean, MobiProductMDR.class,
				criterionList, Order.desc("timeStamp"));
	}





	@Override
	public MobiProductMDR loadProMDR(String mid, String prodType) {
		return (MobiProductMDR) getSessionFactory().createCriteria(MobiProductMDR.class).add(Restrictions.eq("mid", mid))
				.add(Restrictions.eq("prodType", prodType))
				.setMaxResults(1).uniqueResult();
	}





	@Override
	public MobiProductMDR loadProMDR(String id) {
		return (MobiProductMDR) getSessionFactory().createCriteria(MobiProductMDR.class).add(Restrictions.eq("id", Long.valueOf(id)))
				.setMaxResults(1).uniqueResult();
	}



	@Override
	public void exportSettlementMDRByAdmin(PaginationBean<SettlementMDR> paginationBean,
			ArrayList<Criterion> criterionList, MID mid, String Settledate) {

		// TODO Auto-generated method stub

		logger.info("Inside exportSettlementMDRByAdmin : " + mid + "  "+Settledate);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		
		ArrayList<SettlementMDR> fss = new ArrayList<SettlementMDR>();
		String sql = null;
		Query sqlQuery = null;
	
		if ((Settledate == null ) || (Settledate.equals("") )) {

			/*Date dt = new Date();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			//logger.info("change date format:" + from);
			//from = from + "-01";
			String from1=from.substring(0, from.length() - 2);
			from=from1+String.format("%02d", -1+Integer.valueOf(from.substring(8,10)));
			logger.info("change date format:" + from);
			
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1=to.substring(0, to.length() - 2);
			to=to1+String.format("%02d", 1+Integer.valueOf(to.substring(8,10)));
			logger.info("date format:" + to);*/
			
			
			from = "";

		} else {

			from = Settledate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
							.parse(from));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("date format:" + Settledate);

		}

		logger.info("checkd date: "+from );
		if(mid != null && from !="" )
		{
			
			logger.info("Inside MID & SettleDate condition" );
			
			logger.info("UmMid:::::::"+mid.getUmMid());
			logger.info("UmMotoMid:::::::"+mid.getUmMotoMid());
			logger.info("UmEzywayMid:::::::"+mid.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::"+mid.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::"+mid.getUmEzypassMid());
			logger.info("GpayMid:::::::"+mid.getGpayMid());
			
			logger.info("Mid:::::::"+mid.getMid());
			logger.info("MotoMid:::::::"+mid.getMotoMid());
			logger.info("EzywayMid:::::::"+mid.getEzywayMid());
			logger.info("EzyrecMid:::::::"+mid.getEzyrecMid());
			logger.info("EzypassMid:::::::"+mid.getEzypassMid());
			
			logger.info("FiuuMid:::::::"+mid.getFiuuMid());
			
			logger.info("SettleDate:::::::"+from);
			
			
			sql = "SELECT s.SETTLEMENTDATE,  s.MERCHANTNAME ,s.MID,s.TID,s.TXN_TYPE,s.CARD_BRAND ,"
					+ "s.CARD_TYPE,s.MASKED_PAN,s.TXN_AMOUNT,s.HOST_MDR_AMT,s.MOBI_MDR_AMT,s.MDR_AMT,"
					+ "s.EXTRA_DEDUCT_AMT,s.NET_AMOUNT FROM mobiversa.SETTLEMENT_MDR s "
					+ "WHERE s.MID in("+mid.getUmMid()+","+mid.getUmMotoMid()+","+mid.getUmEzywayMid()+","
					+mid.getUmEzyrecMid()+","+mid.getUmEzypassMid()+","+mid.getGpayMid()+","+mid.getMid()+","
					+mid.getMotoMid()+","+mid.getEzywayMid()+","+mid.getEzyrecMid()+","+mid.getEzypassMid()+","+mid.getFiuuMid()+") "
					+ " AND s.SETTLEMENTDATE like'%"+from+"%' ORDER by s.TIME_STAMP  DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
//			sqlQuery.setString("from", from);
		
		}
		else if(mid != null )
		{
			
			logger.info("Inside MID  condition" );
			
			logger.info("UmMid:::::::"+mid.getUmMid());
			logger.info("UmMotoMid:::::::"+mid.getUmMotoMid());
			logger.info("UmEzywayMid:::::::"+mid.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::"+mid.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::"+mid.getUmEzypassMid());
			logger.info("GpayMid:::::::"+mid.getGpayMid());
			
			logger.info("Mid:::::::"+mid.getMid());
			logger.info("MotoMid:::::::"+mid.getMotoMid());
			logger.info("EzywayMid:::::::"+mid.getEzywayMid());
			logger.info("EzyrecMid:::::::"+mid.getEzyrecMid());
			logger.info("EzypassMid:::::::"+mid.getEzypassMid());
			
			logger.info("FiuuMid:::::::"+mid.getFiuuMid());
			
			
			sql = "SELECT s.SETTLEMENTDATE,  s.MERCHANTNAME ,s.MID,s.TID,s.TXN_TYPE,s.CARD_BRAND ,"
					+ "s.CARD_TYPE,s.MASKED_PAN,s.TXN_AMOUNT,s.HOST_MDR_AMT,s.MOBI_MDR_AMT,s.MDR_AMT,"
					+ "s.EXTRA_DEDUCT_AMT,s.NET_AMOUNT FROM mobiversa.SETTLEMENT_MDR s "
					+ "WHERE s.MID in("+mid.getUmMid()+","+mid.getUmMotoMid()+","+mid.getUmEzywayMid()+","
					+mid.getUmEzyrecMid()+","+mid.getUmEzypassMid()+","+mid.getGpayMid()+","+mid.getMid()+","
					+mid.getMotoMid()+","+mid.getEzywayMid()+","+mid.getEzyrecMid()+","+mid.getEzypassMid()+","+mid.getFiuuMid()+") ORDER by s.TIME_STAMP  DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
			
		}else
		{
			logger.info("Inside SettleDate condition"+from);
			
			sql = "SELECT s.SETTLEMENTDATE,  s.MERCHANTNAME ,s.MID,s.TID,s.TXN_TYPE,s.CARD_BRAND, "
					+ "s.CARD_TYPE,s.MASKED_PAN,s.TXN_AMOUNT,s.HOST_MDR_AMT,s.MOBI_MDR_AMT,s.MDR_AMT,"
					+ "s.EXTRA_DEDUCT_AMT,s.NET_AMOUNT FROM mobiversa.SETTLEMENT_MDR s "
					+ "WHERE s.SETTLEMENTDATE like'%"+from+"%' ORDER by s.TIME_STAMP DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
//			sqlQuery.setString("from", from);
		}
		
		
		  
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		
		for (Object[] rec : resultSet) {
			
			SettlementMDR fs = new SettlementMDR();
			
			if(rec[0]!=null){
				try {
                    String Tdate;
                    Tdate = new SimpleDateFormat("dd/MM/yyyy")
                            .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
                    fs.setSettlementDate(Tdate);
//                    boostss.setTxDate(Tdate);
                    }
                    catch (ParseException e) {

                       e.printStackTrace();
                    }
			}else {
				fs.setSettlementDate("");
			}
			
			if(rec[1]!=null){
				fs.setMerchantName(rec[1].toString());
			}else {
				fs.setMerchantName("");
			}
			
			
			if(rec[2]!=null){
				/*double amount = 0;
				amount = Double.parseDouble(rec[2].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);*/
				fs.setMid(rec[2].toString());
			}else {
				fs.setMid("");
			}
			
			
			if(rec[3]!=null){
				fs.setTid(rec[3].toString());
			}else {
				fs.setTid("");
			}
			
			if(rec[4]!=null){
				fs.setTxnType(rec[4].toString());
			}else {
				fs.setTxnType("");
			}
			
			if(rec[5]!=null){
//				if(rec[5].toString().equals("A")) {
//					fs.setSTATUS("NOT SETTLED");
//				}else if(rec[5].toString().equals("C")) {
//					fs.setSTATUS("VOIDED");
//				}else if(rec[5].toString().equals("S")){
//					fs.setSTATUS("SETTLED");
//				}
				fs.setCardBrand(rec[5].toString());
			}else {
				fs.setCardBrand("");
			}
			
			if(rec[6]!=null){
				fs.setCardType(rec[6].toString());
			}else {
				fs.setCardType("");
			}
			
			if(rec[7]!=null){
				/*String rd = null;
					try {
						rd = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyyMMdd").parse(rec[7].toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					fs.setMaskedPan(rec[7].toString());
				
			}else {
				fs.setMaskedPan("");
			}
			
			if(rec[8]!=null){
				/*String rt = null;
					try {
						rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(rec[8].toString()));

					} catch (ParseException e) {
						e.printStackTrace();
					}*/
					fs.setTxnAmount(rec[8].toString());
				
				
			}else {
				fs.setTxnAmount("");
			}
			
			
			if(rec[9]!=null){
				fs.setHostMdrAmt(rec[9].toString());
			}else {
				fs.setHostMdrAmt("");
			}
			
			if(rec[10]!=null){
				fs.setMobiMdrAmt(rec[10].toString());
			}else {
				fs.setMobiMdrAmt("");
			}
			
			if(rec[11]!=null){
				/*String rd=null;
				String rt = null;
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[11].toString()));
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[11].toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fs.setDate(rd);*/
				fs.setMdrAmt(rec[11].toString());
			}else {
//				fs.setDate("");
				fs.setMdrAmt("");
			}
			
			if(rec[12]!=null){
				fs.setExtraDeductAmt(rec[12].toString());
			}else {
				fs.setExtraDeductAmt("");
			}
			
			if(rec[13]!=null){
				fs.setNetAmount(rec[13].toString());
			}else {
				fs.setNetAmount("");
			}
			
			//logger.info("check activation date:" + fs.getNumOfRefund());
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		logger.info("No of Records: "+paginationBean.getItemList().size());
		// paginationBean.setTotalRowCount(fss.size());
	
		
	}



	@Override
	public void exportMobiliteSettlementMDRByAdmin(PaginationBean<SettlementMDR> paginationBean,
			ArrayList<Criterion> criterionList, MID mid, String Settledate) {

		// TODO Auto-generated method stub

		logger.info("Inside exportMobiliteSettlementMDRByAdmin : " + mid + "  "+Settledate);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		
		ArrayList<SettlementMDR> fss = new ArrayList<SettlementMDR>();
		String sql = null;
		Query sqlQuery = null;
	
		if ((Settledate == null ) || (Settledate.equals("") )) {

			/*Date dt = new Date();
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			from = dateFormat.format(dt);
			//logger.info("change date format:" + from);
			//from = from + "-01";
			String from1=from.substring(0, from.length() - 2);
			from=from1+String.format("%02d", -1+Integer.valueOf(from.substring(8,10)));
			logger.info("change date format:" + from);
			
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			to = dateFormat1.format(dt1);
			String to1=to.substring(0, to.length() - 2);
			to=to1+String.format("%02d", 1+Integer.valueOf(to.substring(8,10)));
			logger.info("date format:" + to);*/
			
			
			from = "";

		} else {

			from = Settledate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
							.parse(from));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("date format:" + Settledate);

		}

		logger.info("checkd date: "+from );
		if(mid != null && from !="" )
		{
			
			logger.info("Inside MID & SettleDate condition" );
			
			logger.info("UmMid:::::::"+mid.getUmMid());
			logger.info("UmMotoMid:::::::"+mid.getUmMotoMid());
			logger.info("UmEzywayMid:::::::"+mid.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::"+mid.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::"+mid.getUmEzypassMid());
			logger.info("GpayMid:::::::"+mid.getGpayMid());
			
			logger.info("Mid:::::::"+mid.getMid());
			logger.info("MotoMid:::::::"+mid.getMotoMid());
			logger.info("EzywayMid:::::::"+mid.getEzywayMid());
			logger.info("EzyrecMid:::::::"+mid.getEzyrecMid());
			logger.info("EzypassMid:::::::"+mid.getEzypassMid());
			
			logger.info("SettleDate:::::::"+from);
			
			
			sql = "SELECT s.SUB_SETTLE_DATE,  s.SUB_MERCHANTNAME ,s.SUB_MID,s.SUB_TID,s.TXN_TYPE,s.CARD_BRAND ,"
					+ "s.CARD_TYPE,s.MASKED_PAN,s.TXN_AMOUNT,s.SUB_HOST_MDR_AMT,s.SUB_MOBI_MDR_AMT,s.SUB_MDR_AMT,"
					+ "s.EXTRA_DEDUCT_AMT,s.SUB_NET_AMOUNT FROM mobiversa.SETTLEMENT_MDR s "
					+ "WHERE s.SUB_MID in("+mid.getUmMid()+","+mid.getUmMotoMid()+","+mid.getUmEzywayMid()+","
					+mid.getUmEzyrecMid()+","+mid.getUmEzypassMid()+","+mid.getGpayMid()+","+mid.getMid()+","
					+mid.getMotoMid()+","+mid.getEzywayMid()+","+mid.getEzyrecMid()+","+mid.getEzypassMid()+") "
					+ " AND s.SUB_SETTLE_DATE like'%"+from+"%' ORDER by s.TIME_STAMP  DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
//			sqlQuery.setString("from", from);
		
		}
		else if(mid != null )
		{
			
			logger.info("Inside MID  condition" );
			
			logger.info("UmMid:::::::"+mid.getUmMid());
			logger.info("UmMotoMid:::::::"+mid.getUmMotoMid());
			logger.info("UmEzywayMid:::::::"+mid.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::"+mid.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::"+mid.getUmEzypassMid());
			logger.info("GpayMid:::::::"+mid.getGpayMid());
			
			logger.info("Mid:::::::"+mid.getMid());
			logger.info("MotoMid:::::::"+mid.getMotoMid());
			logger.info("EzywayMid:::::::"+mid.getEzywayMid());
			logger.info("EzyrecMid:::::::"+mid.getEzyrecMid());
			logger.info("EzypassMid:::::::"+mid.getEzypassMid());
			
			
			
			sql = "SELECT s.SUB_SETTLE_DATE,  s.SUB_MERCHANTNAME ,s.SUB_MID,s.SUB_TID,s.TXN_TYPE,s.CARD_BRAND ,"
					+ "s.CARD_TYPE,s.MASKED_PAN,s.TXN_AMOUNT,s.SUB_HOST_MDR_AMT,s.SUB_MOBI_MDR_AMT,s.SUB_MDR_AMT,"
					+ "s.EXTRA_DEDUCT_AMT,s.SUB_NET_AMOUNT FROM mobiversa.SETTLEMENT_MDR s "
					+ "WHERE s.SUB_MID in("+mid.getUmMid()+","+mid.getUmMotoMid()+","+mid.getUmEzywayMid()+","
					+mid.getUmEzyrecMid()+","+mid.getUmEzypassMid()+","+mid.getGpayMid()+","+mid.getMid()+","
					+mid.getMotoMid()+","+mid.getEzywayMid()+","+mid.getEzyrecMid()+","+mid.getEzypassMid()+") ORDER by s.TIME_STAMP  DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
			
		}else
		{
			logger.info("Inside SettleDate condition"+from);
			
			sql = "SELECT s.SUB_SETTLE_DATE,  s.SUB_MERCHANTNAME ,s.SUB_MID,s.SUB_TID,s.TXN_TYPE,s.CARD_BRAND, "
					+ "s.CARD_TYPE,s.MASKED_PAN,s.TXN_AMOUNT,s.SUB_HOST_MDR_AMT,s.SUB_MOBI_MDR_AMT,s.SUB_MDR_AMT,"
					+ "s.EXTRA_DEDUCT_AMT,s.SUB_NET_AMOUNT FROM mobiversa.SETTLEMENT_MDR s "
					+ "WHERE s.SUB_SETTLE_DATE like'%"+from+"%' ORDER by s.TIME_STAMP DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
//			sqlQuery.setString("from", from);
		}
		
		
		  
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		
		for (Object[] rec : resultSet) {
			
			SettlementMDR fs = new SettlementMDR();
			
			if(rec[0]!=null){
				fs.setSubSettleDate(rec[0].toString());
			}else {
				fs.setSubSettleDate("");
			}
			
			if(rec[1]!=null){
				fs.setSubMerchantName(rec[1].toString());
			}else {
				fs.setSubMerchantName("");
			}
			
			
			if(rec[2]!=null){
				/*double amount = 0;
				amount = Double.parseDouble(rec[2].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);*/
				fs.setSubMid(rec[2].toString());
			}else {
				fs.setSubMid("");
			}
			
			
			if(rec[3]!=null){
				fs.setSubTid(rec[3].toString());
			}else {
				fs.setSubTid("");
			}
			
			if(rec[4]!=null){
				fs.setTxnType(rec[4].toString());
			}else {
				fs.setTxnType("");
			}
			
			if(rec[5]!=null){
//				if(rec[5].toString().equals("A")) {
//					fs.setSTATUS("NOT SETTLED");
//				}else if(rec[5].toString().equals("C")) {
//					fs.setSTATUS("VOIDED");
//				}else if(rec[5].toString().equals("S")){
//					fs.setSTATUS("SETTLED");
//				}
				fs.setCardBrand(rec[5].toString());
			}else {
				fs.setCardBrand("");
			}
			
			if(rec[6]!=null){
				fs.setCardType(rec[6].toString());
			}else {
				fs.setCardType("");
			}
			
			if(rec[7]!=null){
				/*String rd = null;
					try {
						rd = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyyMMdd").parse(rec[7].toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					fs.setMaskedPan(rec[7].toString());
				
			}else {
				fs.setMaskedPan("");
			}
			
			if(rec[8]!=null){
				/*String rt = null;
					try {
						rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(rec[8].toString()));

					} catch (ParseException e) {
						e.printStackTrace();
					}*/
					fs.setTxnAmount(rec[8].toString());
				
				
			}else {
				fs.setTxnAmount("");
			}
			
			
			if(rec[9]!=null){
				fs.setSubHostMdrAmt(rec[9].toString());
			}else {
				fs.setSubHostMdrAmt("");
			}
			
			if(rec[10]!=null){
				fs.setSubMobiMdrAmt(rec[10].toString());
			}else {
				fs.setSubMobiMdrAmt("");
			}
			
			if(rec[11]!=null){
				/*String rd=null;
				String rt = null;
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[11].toString()));
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[11].toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fs.setDate(rd);*/
				fs.setSubMdrAmt(rec[11].toString());
			}else {
//				fs.setDate("");
				fs.setSubMdrAmt("");
			}
			
			if(rec[12]!=null){
				fs.setExtraDeductAmt(rec[12].toString());
			}else {
				fs.setExtraDeductAmt("");
			}
			
			if(rec[13]!=null){
				fs.setSubNetAmount(rec[13].toString());
			}else {
				fs.setSubNetAmount("");
			}
			
			//logger.info("check activation date:" + fs.getNumOfRefund());
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		logger.info("No of Records: "+paginationBean.getItemList().size());
		// paginationBean.setTotalRowCount(fss.size());
	
		
	}



	


	@Override
	public List<MobiMDR> getMDRDetailsByMid(MID mid) {

		logger.info("getMDRDetailsByMid : ");
				
				Disjunction orExp = null; 
				Criterion mid1 = Restrictions.like("mid", mid.getMid());
				Criterion mmid = Restrictions.like("mid", mid.getMotoMid());
				Criterion wmid = Restrictions.like("mid", mid.getEzywayMid());
				Criterion rmid = Restrictions.like("mid", mid.getEzyrecMid());
				Criterion ummid = Restrictions.like("mid", mid.getUmMid());
				Criterion ummmid = Restrictions.like("mid", mid.getUmMotoMid());
				Criterion umwmid = Restrictions.like("mid", mid.getUmEzywayMid());
				Criterion umrmid = Restrictions.like("mid", mid.getUmEzyrecMid());
				orExp = Restrictions.or(mid1, mmid,wmid,rmid,ummid,ummmid,umwmid,umrmid);
				@SuppressWarnings("unchecked")
				List<MobiMDR> MobiMDR =  sessionFactory.getCurrentSession().createCriteria(MobiMDR.class)
					    .add(orExp).list();
				
				return MobiMDR;
				
	}



	@Override
	public void listChargeBackByMid(PaginationBean<MobiProductMDR> paginationBean, ArrayList<Criterion> criterionList) {
		super.getPaginationItemsByPage(paginationBean, MobiProductMDR.class,
				criterionList, Order.desc("timeStamp"));
	}





	@Override
	public void listAllChargeBack(PaginationBean<RegMobileUser> paginationBean, MID object, String object2) {

		
		ArrayList<RegMobileUser> fss = new ArrayList<RegMobileUser>();
		String sql = null;
		Query sqlQuery = null;
		
		logger.info("Inside listAllChargeBack");
		
		/*sql = "select m.TIME_STAMP ,s.BUSINESS_NAME,m.MID ,m.`STATUS` ,m.MOBI_MDR from MOBI_PROD_MDR m "
				+ "INNER JOIN MID  f on m.MID = f.MID or m.MID = f.EZYWAY_MID or m.MID = f.EZYPASS_MID or m.MID = f.EZYREC_MID or "
				+ "m.MID = f.MOTO_MID or m.MID = f.UM_MOTO_MID or m.MID = f.UM_MID or m.MID = f.UM_EZYWAY_MID or m.MID = f.UM_EZYREC_MID or "
				+ "m.MID = f.UM_EZYPASS_MID INNER JOIN MERCHANT s ON s.ID =f.MERCHANT_FK where m.PROD_TYPE like 'CH_BACK' order by m.time_stamp desc";
		*/
		
		sql = "select m.TIME_STAMP ,s.BUSINESS_NAME,m.MID ,m.`STATUS` ,m.MOBI_MDR,m.HOST_MDR,m.PROD_TYPE from MOBI_PROD_MDR m "
				+ "INNER JOIN MID  f on m.MID = f.UM_MOTO_MID or m.MID = f.UM_MID or m.MID = f.UM_EZYWAY_MID or "
				+ "m.MID = f.UM_EZYREC_MID or m.MID = f.UM_EZYPASS_MID INNER JOIN MERCHANT s ON s.ID =f.MERCHANT_FK "
				+ "where m.PROD_TYPE IN ('CH_BACK','FEE') order by m.time_stamp desc";
		
		
		
		logger.info("Query : " + sql);
		sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		
		for (Object[] rec : resultSet) {
			
			RegMobileUser fs = new RegMobileUser();
			
			if(rec[0]!=null){
				fs.setActivateDate(rec[0].toString());
			}else {
				fs.setActivateDate("");
			}
			
			if(rec[1]!=null){
				fs.setBusinessName(rec[1].toString());
			}else {
				fs.setBusinessName("");
			}
			
			
			if(rec[2]!=null){
				fs.setMid(rec[2].toString());
			}else {
				fs.setMid("");
			}
			
			
			if(rec[3]!=null){
				/*if(rec[3].toString().equals("ACTIVE")){
					fs.setStatus("NOT SETTLED");
				}
				
				if(rec[3].toString().equals("TERMINATED")){
					fs.setStatus("NOT SETTLED");
				}*/
				fs.setStatus(rec[3].toString());
			}else {
				fs.setStatus("");
			}
			
			if(rec[4]!=null){
				fs.setCh_amount(rec[4].toString());
			}else {
				fs.setCh_amount("");
			}
			
			if(rec[5]!=null){
				fs.setRepayableAmt(rec[5].toString());
			}else {
				fs.setRepayableAmt("");
			}
			
			if(rec[6]!=null){
				fs.setDeviceType(rec[6].toString());
			}else {
				fs.setDeviceType("");
			}
			
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		logger.info("No of Records: "+paginationBean.getItemList().size());
		
	}

	
	

	@Override
	public int getMDRValuesCount(String  mid) {
		
		
		String sql = null;
		Query sqlQuery = null;
		int count = 0;
		
		logger.info("Inside listAllChargeBack");
		
		sql = "SELECT COUNT(m.MID),m.RATE_ID from mobiversa.MOBIVERSA_MDR m WHERE m.MID = '"+mid+"'";
		
		
		
		logger.info("Query : " + sql);
		sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		
		for (Object[] rec : resultSet) {
			
			
			
			if(rec[0] != null) {
				count = Integer.parseInt(rec[0].toString());
			}
			
		}
		
		return count;
		
	}
	


	@Override
	public List<SettlementMDR> listMIDByDate(String date) {
		
		String dat = null;
		ArrayList<SettlementMDR> settleMDR = new ArrayList<SettlementMDR>();
		String sql = null;
		String resDate1 = null;
		logger.info("Optained settle Date:" + date);
		
		if (date == null || date.equals("")) {
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			dat = dateFormat1.format(dt1);
			logger.info("Default settle Date:" + dat);

		} else {

			dat = date;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			
			 Date resDate= null;
		        try {
		            resDate = new SimpleDateFormat("dd/MM/yyyy").parse(dat);
		        } catch (ParseException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
			
		        resDate1 = dateFormat1.format(resDate);
				logger.info("settle Date:" + resDate1);

		}
		
		sql = "SELECT DISTINCT m.MID, m.MERCHANTEMAIL ,m.MERCHANTNAME, m.SETTLETYPE from SETTLEMENT_MDR m  "
				+ " where m.SETTLEMENTDATE like  '%"+resDate1+"%' order by m.TIME_STAMP desc" ;
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());
		for (Object[] rec : resultSet) {
			
			SettlementMDR settleData = new SettlementMDR();
			
			if(rec[0]!=null){
				settleData.setMid((rec[0].toString()));
			}else {
				settleData.setMid("");
			}
			
			if(rec[1]!=null){
				settleData.setMerchantEmail(rec[1].toString());
			}else {
				settleData.setMerchantEmail("");
			}
			
			if(rec[2]!=null){
				settleData.setMerchantName(rec[2].toString());
			}else {
				settleData.setMerchantName("");
			}
			

			if(rec[3]!=null){
				settleData.setSettleType(rec[3].toString());
			}else {
				settleData.setSettleType("");
			}
			
			settleMDR.add(settleData);
		}
		
		return settleMDR;
		
	}
	
	
	
	
	@Override
	public void exportbizappSettlementMDRByAdmin(PaginationBean<BizAppSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String Settledate) {

		// TODO Auto-generated method stub

		logger.info("Inside exportMobiliteSettlementMDRByAdmin : "+Settledate);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		
		ArrayList<BizAppSettlement> fss = new ArrayList<BizAppSettlement>();
		String sql = null;
		Query sqlQuery = null;
	
	

			from = Settledate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
							.parse(from));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("date format:" + Settledate);

		

		logger.info("checkd date: "+from );

			logger.info("Inside SettleDate condition"+from);
			
			sql = "SELECT s.SETTLEMENTDATE,  s.MERCHANTNAME,"
					+ "s.GROSSAMT,s.HOST_MDR_AMT,s.MOBI_MDR_AMT,s.MDR_AMT,"
					+ "s.DETECTIONAMT,s.NETAMT FROM mobiversa.BIZAPP_SETTLEMENT s "
					+ "WHERE s.SETTLEMENTDATE like'%"+from+"%' ORDER by s.TIME_STAMP DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		
		for (Object[] rec : resultSet) {
			
			BizAppSettlement fs = new BizAppSettlement();
			
			if(rec[0]!=null){
				fs.setSettlementDate(rec[0].toString());
			}else {
				fs.setSettlementDate("");
			}
			
			if(rec[1]!=null){
				fs.setMerchantName(rec[1].toString());
			}else {
				fs.setMerchantName("");
			}
			

			if(rec[2]!=null){

					fs.setGrossAmt(rec[2].toString());
				
				
			}else {
				fs.setGrossAmt("");
			}
			
			
			if(rec[3]!=null){
				fs.setHostMdrAmt(rec[3].toString());
			}else {
				fs.setHostMdrAmt("");
			}
			
			if(rec[4]!=null){
				fs.setMobiMdrAmt(rec[4].toString());
			}else {
				fs.setMobiMdrAmt("");
			}
			
			if(rec[5]!=null){
	
				fs.setMdrAmt(rec[5].toString());
			}else {
//				fs.setDate("");
				fs.setMdrAmt("");
			}
			
			if(rec[6]!=null){
				fs.setDetectionAmt(rec[6].toString());
			}else {
				fs.setDetectionAmt("");
			}
			
			if(rec[7]!=null){
				fs.setNetAmt(rec[7].toString());
			}else {
				fs.setNetAmt("");
			}
			
			//logger.info("check activation date:" + fs.getNumOfRefund());
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		logger.info("No of Records: "+paginationBean.getItemList().size());
		// paginationBean.setTotalRowCount(fss.size());
	
		
	}
	
	@Override
	public void exportFpxSettlementMDRByAdmin(PaginationBean<FpxTransaction> paginationBean,
			ArrayList<Criterion> criterionList, MID mid, String Settledate) {

		// TODO Auto-generated method stub

		logger.info("Inside exportSettlementMDRByAdmin : " + mid + "  "+Settledate);
		String from = null;
		String to = null;
		String year1 = null;
		String year2 = null;
		
		ArrayList<FpxTransaction> fss = new ArrayList<FpxTransaction>();
		String sql = null;
		Query sqlQuery = null;
	
		if ((Settledate == null ) || (Settledate.equals("") )) {

			from = "";

		} else {

			from = Settledate;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
							.parse(from));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("date format:" + Settledate);

		}

		logger.info("checkd date: "+from );
		if(mid != null && from !="" )
		{
			
			logger.info("Inside MID & SettleDate condition" );
			
			logger.info("UmMid:::::::"+mid.getUmMid());
			logger.info("UmMotoMid:::::::"+mid.getUmMotoMid());
			logger.info("UmEzywayMid:::::::"+mid.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::"+mid.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::"+mid.getUmEzypassMid());
			logger.info("GpayMid:::::::"+mid.getGpayMid());
			
			logger.info("Mid:::::::"+mid.getMid());
			logger.info("MotoMid:::::::"+mid.getMotoMid());
			logger.info("EzywayMid:::::::"+mid.getEzywayMid());
			logger.info("EzyrecMid:::::::"+mid.getEzyrecMid());
			logger.info("EzypassMid:::::::"+mid.getEzypassMid());
			
			logger.info("SettleDate:::::::"+from);
			
			
			sql = "SELECT s.SETTLED_DATE,  s.BUYERNAME ,s.MID, "
					+ "s.TXN_AMOUNT,s.HOST_MDR_AMT,s.MOBI_MDR_AMT,s.MDR_AMT, "
					+ "FROM mobiversa.FPX_TRANSACTION s "
					+ "WHERE s.MID in("+mid.getUmMid()+","+mid.getUmMotoMid()+","+mid.getUmEzywayMid()+","
					+mid.getUmEzyrecMid()+","+mid.getUmEzypassMid()+","+mid.getGpayMid()+","+mid.getMid()+","
					+mid.getMotoMid()+","+mid.getEzywayMid()+","+mid.getEzyrecMid()+","+mid.getEzypassMid()+") "
					+ " AND s.SETTLED_DATE like'%"+from+"%' ORDER by s.TIME_STAMP  DESC ";
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
//			sqlQuery.setString("from", from);
		
		}
		else if(mid != null )
		{
			
			logger.info("Inside MID  condition" );
			
			logger.info("UmMid:::::::"+mid.getUmMid());
			logger.info("UmMotoMid:::::::"+mid.getUmMotoMid());
			logger.info("UmEzywayMid:::::::"+mid.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::"+mid.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::"+mid.getUmEzypassMid());
			logger.info("GpayMid:::::::"+mid.getGpayMid());
			
			logger.info("Mid:::::::"+mid.getMid());
			logger.info("MotoMid:::::::"+mid.getMotoMid());
			logger.info("EzywayMid:::::::"+mid.getEzywayMid());
			logger.info("EzyrecMid:::::::"+mid.getEzyrecMid());
			logger.info("EzypassMid:::::::"+mid.getEzypassMid());
			
			
			sql ="SELECT s.SETTLED_DATE,  s.BUYERNAME ,s.MID, "
			+ "s.TXN_AMOUNT,s.HOST_MDR_AMT,s.MOBI_MDR_AMT,s.MDR_AMT, "
			+ "FROM mobiversa.FPX_TRANSACTION s "
			+ "WHERE s.MID in("+mid.getUmMid()+","+mid.getUmMotoMid()+","+mid.getUmEzywayMid()+","
			+mid.getUmEzyrecMid()+","+mid.getUmEzypassMid()+","+mid.getGpayMid()+","+mid.getMid()+","
			+mid.getMotoMid()+","+mid.getEzywayMid()+","+mid.getEzyrecMid()+","+mid.getEzypassMid()+") "
			+ "ORDER by s.TIME_STAMP  DESC ";
	
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
			
		}else
		{
			logger.info("Inside SettleDate condition"+from);
			
			
			sql = "SELECT s.SETTLED_DATE,  s.BUYERNAME ,s.MID, "
					+ "s.TXN_AMOUNT,s.HOST_MDR_AMT,s.MOBI_MDR_AMT,s.MDR_AMT, "
					+ "FROM mobiversa.FPX_TRANSACTION s "
					+ "WHERE s.SETTLED_DATE like'%"+from+"%' ORDER by s.TIME_STAMP  DESC ";
			
			
			logger.info("Query : " + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
//			sqlQuery.setString("from", from);
		}
		
		
		  
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		
		for (Object[] rec : resultSet) {
			
			FpxTransaction fs = new FpxTransaction();
			
			if(rec[0]!=null){
				fs.setSettledDate(rec[0].toString());
			}else {
				fs.setSettledDate("");
			}
			
			if(rec[1]!=null){
				fs.setBuyerName(rec[1].toString());
			}else {
				fs.setBuyerName("");
			}
			
			
			if(rec[2]!=null){

				fs.setMid(rec[2].toString());
			}else {
				fs.setMid("");
			}
			
			
		
			if(rec[3]!=null){
				
					fs.setTxnAmount(rec[3].toString());
				
				
			}else {
				fs.setTxnAmount("");
			}
			
			
			if(rec[4]!=null){
				fs.setHostMdrAmt(rec[4].toString());
			}else {
				fs.setHostMdrAmt("");
			}
			
			if(rec[5]!=null){
				fs.setMobiMdrAmt(rec[5].toString());
			}else {
				fs.setMobiMdrAmt("");
			}
			
			if(rec[6]!=null){
				fs.setMdrAmt(rec[6].toString());
			}else {
//				fs.setDate("");
				fs.setMdrAmt("");
			}
			
			
			//logger.info("check activation date:" + fs.getNumOfRefund());
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		logger.info("No of Records: "+paginationBean.getItemList().size());
		// paginationBean.setTotalRowCount(fss.size());
	
		
	}
	
	
	/*
	 * @Override public List<MdrRates> getMDRDetailsByRateId(StringBuffer midList) {
	 * 
	 * logger.info("getMDRDetailsByRateId : "); String sql = null; Query sqlQuery =
	 * null;
	 * 
	 * ArrayList<MdrRates> fss = new ArrayList<MdrRates>(); sql
	 * ="SELECT r.FO_CR_VISA_HOST,r.FO_CR_VISA_MERCH,r.FO_CR_VISA_MOBI,r.LO_CR_VISA_HOST,r.LO_CR_VISA_MERCH,r.LO_CR_VISA_MOBI "
	 * +",r.FO_DR_VISA_HOST,r.FO_DR_VISA_MERCH,r.FO_DR_VISA_MOBI,r.LO_DR_VISA_HOST,r.LO_DR_VISA_MERCH,r.LO_DR_VISA_MOBI "
	 * +",r.FO_CR_MC_HOST,r.FO_CR_MC_MERCH,r.FO_CR_MC_MOBI,r.LO_CR_MC_HOST,r.LO_CR_MC_MERCH,r.LO_CR_MC_MOBI "
	 * +",r.FO_DR_MC_HOST,r.FO_DR_MC_MERCH,r.FO_DR_MC_MOBI,r.LO_DR_MC_HOST,r.LO_DR_MC_MERCH,r.LO_DR_MC_MOBI "
	 * +",r.FO_CR_UP_HOST,r.FO_CR_UP_MERCH,r.FO_CR_UP_MOBI,r.LO_CR_UP_HOST,r.LO_CR_UP_MERCH,r.LO_CR_UP_MOBI "
	 * +",r.FO_DR_UP_HOST,r.FO_DR_UP_MERCH,r.FO_DR_UP_MOBI,r.LO_DR_UP_HOST,r.LO_DR_UP_MERCH,r.LO_DR_UP_MOBI,m.MID FROM MDR_RATES r "
	 * +"INNER JOIN MOBIVERSA_MDR m ON m.RATE_ID = r.RATE_ID "
	 * +"WHERE m.MID IN( "+midList+" ) ";
	 * 
	 * logger.info("Query : " + sql); sqlQuery =
	 * super.getSessionFactory().createSQLQuery(sql);
	 * 
	 * @SuppressWarnings("unchecked") List<Object[]> resultSet = sqlQuery.list();
	 * logger.info("Number of records in the List : " + resultSet.size());
	 * 
	 * for (Object[] rec : resultSet) { MdrRates mdrRates = new MdrRates();
	 * 
	 * if(rec[0] != null) {
	 * mdrRates.setCreditForeignVisaHost(Float.parseFloat(rec[0].toString())); }
	 * 
	 * if(rec[1] != null) {
	 * mdrRates.setCreditForeignVisaMerch(Float.parseFloat(rec[1].toString())); }
	 * 
	 * if(rec[2] != null) {
	 * mdrRates.setCreditForeignVisaMobi(Float.parseFloat(rec[2].toString())); }
	 * if(rec[3] != null) {
	 * mdrRates.setCreditLocalVisaHost(Float.parseFloat(rec[3].toString())); }
	 * if(rec[4] != null) {
	 * mdrRates.setCreditLocalVisaMerch(Float.parseFloat(rec[4].toString())); }
	 * if(rec[5] != null) {
	 * mdrRates.setCreditLocalVisaMobi(Float.parseFloat(rec[5].toString())); }
	 * 
	 * if(rec[6] != null) {
	 * mdrRates.setDebitForeignVisaHost(Float.parseFloat(rec[6].toString())); }
	 * 
	 * if(rec[7] != null) {
	 * mdrRates.setDebitForeignVisaMerch(Float.parseFloat(rec[7].toString())); }
	 * 
	 * if(rec[8] != null) {
	 * mdrRates.setDebitForeignVisaMobi(Float.parseFloat(rec[8].toString())); }
	 * if(rec[9] != null) {
	 * mdrRates.setDebitLocalVisaHost(Float.parseFloat(rec[9].toString())); }
	 * if(rec[10] != null) {
	 * mdrRates.setDebitLocalVisaMerch(Float.parseFloat(rec[10].toString())); }
	 * if(rec[11] != null) {
	 * mdrRates.setDebitLocalVisaMobi(Float.parseFloat(rec[11].toString())); }
	 * 
	 * //mc if(rec[12] != null) {
	 * mdrRates.setCreditForeignMcHost(Float.parseFloat(rec[12].toString())); }
	 * 
	 * if(rec[13] != null) {
	 * mdrRates.setCreditForeignMcMerch(Float.parseFloat(rec[13].toString())); }
	 * 
	 * if(rec[14] != null) {
	 * mdrRates.setCreditForeignMcMobi(Float.parseFloat(rec[14].toString())); }
	 * if(rec[15] != null) {
	 * mdrRates.setCreditLocalMcHost(Float.parseFloat(rec[15].toString())); }
	 * if(rec[16] != null) {
	 * mdrRates.setCreditLocalMcMerch(Float.parseFloat(rec[16].toString())); }
	 * if(rec[17] != null) {
	 * mdrRates.setCreditLocalMcMobi(Float.parseFloat(rec[17].toString())); }
	 * 
	 * if(rec[18] != null) {
	 * mdrRates.setDebitForeignMcHost(Float.parseFloat(rec[18].toString())); }
	 * 
	 * if(rec[19] != null) {
	 * mdrRates.setDebitForeignMcMerch(Float.parseFloat(rec[19].toString())); }
	 * 
	 * if(rec[20] != null) {
	 * mdrRates.setDebitForeignMcMobi(Float.parseFloat(rec[20].toString())); }
	 * if(rec[21] != null) {
	 * mdrRates.setDebitLocalMcHost(Float.parseFloat(rec[21].toString())); }
	 * if(rec[22] != null) {
	 * mdrRates.setDebitLocalMcMerch(Float.parseFloat(rec[22].toString())); }
	 * if(rec[23] != null) {
	 * mdrRates.setDebitLocalMcMobi(Float.parseFloat(rec[23].toString())); }
	 * 
	 * //up if(rec[24] != null) {
	 * mdrRates.setCreditForeignUpHost(Float.parseFloat(rec[24].toString())); }
	 * 
	 * if(rec[25] != null) {
	 * mdrRates.setCreditForeignUpMerch(Float.parseFloat(rec[25].toString())); }
	 * 
	 * if(rec[26] != null) {
	 * mdrRates.setCreditForeignUpMobi(Float.parseFloat(rec[26].toString())); }
	 * if(rec[27] != null) {
	 * mdrRates.setCreditLocalUpHost(Float.parseFloat(rec[27].toString())); }
	 * if(rec[28] != null) {
	 * mdrRates.setCreditLocalUpMerch(Float.parseFloat(rec[28].toString())); }
	 * if(rec[29] != null) {
	 * mdrRates.setCreditLocalUpMobi(Float.parseFloat(rec[29].toString())); }
	 * 
	 * if(rec[30] != null) {
	 * mdrRates.setDebitForeignUpHost(Float.parseFloat(rec[30].toString())); }
	 * 
	 * if(rec[31] != null) {
	 * mdrRates.setDebitForeignUpMerch(Float.parseFloat(rec[31].toString())); }
	 * 
	 * if(rec[32] != null) {
	 * mdrRates.setDebitForeignUpMobi(Float.parseFloat(rec[32].toString())); }
	 * if(rec[33] != null) {
	 * mdrRates.setDebitLocalUpHost(Float.parseFloat(rec[33].toString())); }
	 * if(rec[34] != null) {
	 * mdrRates.setDebitLocalUpMerch(Float.parseFloat(rec[34].toString())); }
	 * if(rec[35] != null) {
	 * mdrRates.setDebitLocalUpMobi(Float.parseFloat(rec[35].toString())); }
	 * 
	 * if(rec[36] != null) { mdrRates.setMidMapped(rec[36].toString()); }
	 * fss.add(mdrRates); }
	 * 
	 * return fss;
	 * 
	 * }
	 */
	
	
	@Override
	public List<MobiMDR> getMobiMDRDetails(String mid) {

		logger.info("getMDRDetailsByMid : ");
				

				return (List<MobiMDR>) getSessionFactory().createCriteria(MobiMDR.class).add(Restrictions.eq("mid",mid)).list();
				
				
	}


//rk	
	@Override
    public List<MobiMDR> getMDRDetailsByRateId(StringBuffer midList) {

 

        logger.info("getMDRDetailsByRateId : ");
        String sql = null;
        Query sqlQuery = null;
        
    ArrayList<MobiMDR> fss = new ArrayList<MobiMDR>();
        
        sql ="SELECT r.CARD_BRAND,r.CR_FR_HOST_MDR,r.CR_FR_MRCH_MDR,r.CR_FR_MOBI_MDR,r.CR_LO_HOST_MDR,r.CR_LO_MRCH_MDR"
                +",r.CR_LO_MOBI_MDR,r.DR_FR_HOST_MDR,r.DR_FR_MRCH_MDR,r.DR_FR_MOBI_MDR,r.DR_LO_HOST_MDR,r.DR_LO_MRCH_MDR"
                +",r.DR_LO_MOBI_MDR,r.SETTLE_PERIOD,r.MID from MOBIVERSA_MDR r WHERE r.MID IN( "+midList+" )";
        logger.info("Query : " + sql);
        sqlQuery = super.getSessionFactory().createSQLQuery(sql);
        @SuppressWarnings("unchecked")
        List<Object[]> resultSet = sqlQuery.list();
        logger.info("Number of records in the List : " + resultSet.size());
        
        for (Object[] rec : resultSet) {
            MobiMDR mobiMdr = new MobiMDR();
            
            if(rec[0] != null) {
                mobiMdr.setCardBrand(rec[0].toString());
            }
            
            if(rec[1] != null) {
                mobiMdr.setCreditForeignHostMDR(Float.parseFloat(rec[1].toString()));
            }
            
            if(rec[2] != null) {
                mobiMdr.setCreditForeignMerchantMDR(Float.parseFloat(rec[2].toString()));
            }
            if(rec[3] != null) {
                mobiMdr.setCreditForeignMobiMDR(Float.parseFloat(rec[3].toString()));
            }
            if(rec[4] != null) {
                mobiMdr.setCreditLocalHostMDR(Float.parseFloat(rec[4].toString()));
            }
            if(rec[5] != null) {
                mobiMdr.setCreditLocalMerchantMDR(Float.parseFloat(rec[5].toString()));
            }
            
            if(rec[6] != null) {
                mobiMdr.setCreditLocalMobiMDR(Float.parseFloat(rec[6].toString()));
                
            }
            
            if(rec[7] != null) {
                mobiMdr.setDebitForeignHostMDR(Float.parseFloat(rec[7].toString()));
            }
            
            if(rec[8] != null) {
                mobiMdr.setDebitForeignMerchantMDR(Float.parseFloat(rec[8].toString()));
            }
            if(rec[9] != null) {
                mobiMdr.setDebitForeignMobiMDR(Float.parseFloat(rec[9].toString()));
            }
            if(rec[10] != null) {
                mobiMdr.setDebitLocalHostMDR(Float.parseFloat(rec[10].toString()));
            }
            if(rec[11] != null) {
                mobiMdr.setDebitLocalMerchantMDR(Float.parseFloat(rec[11].toString()));
            }
            
            //mc
            if(rec[12] != null) {
                mobiMdr.setDebitLocalMobiMDR(Float.parseFloat(rec[12].toString()));
            }if(rec[13] != null) {
                mobiMdr.setSettlePeriod(rec[13].toString());
            }
            
            if(rec[14] != null) {
                mobiMdr.setMid(rec[14].toString());
            }
            
            
            
            fss.add(mobiMdr);
            System.out.println(fss);
        }
        
        return fss;
                
    }

	
public UMMidTxnLimit Adddtl(final RegMobileUser entity) {
        
        UMMidTxnLimit midDetails = new UMMidTxnLimit();
        String DTL=entity.getDTL();
        String MID=entity.getMid();
        
        midDetails.setDtl(DTL);
        midDetails.setMid(MID);
        logger.info("mid********"+midDetails.getMid());
        logger.info("Dtl********"+midDetails.getDtl());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery("update UMMIDTXNLIMIT set DTL = :dtl  where MID = :mid");
        query.setParameter("dtl", midDetails.getDtl());
        query.setParameter("mid",midDetails.getMid());
        query.executeUpdate();
        logger.info("sucessfully updated!!!!!!!!!!!!!!!!");
        
    //    midDetails =merchantDAO.saveOrUpdateEntity(midDetails);
        return midDetails;
    }


}


