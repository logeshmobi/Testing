package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.common.bo.MobiLiteTerminal;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.SixMonthTxnData;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.dto.TopFiveMerchant;
import com.mobiversa.payment.service.MerchantService;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DashBoardDaoImpl extends BaseDAOImpl implements DashBoardDao {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private MerchantService merchantService;

	@Override
	@Transactional(readOnly = true)
	public List<TopFiveMerchant> getTopFiveMerchantDet() {
		
		//DashBoardDataObject dashBoardData = new DashBoardDataObject();
		List<TopFiveMerchant> listFs = new ArrayList<TopFiveMerchant>();
		String sql =null;
		
		Date curDate = new Date();
		
		String rd = new SimpleDateFormat("MM").format(curDate);
		String strdt1 = null;
		String strdt2 = null;
		int aa = Integer.parseInt(rd);
		
		int date1 = aa;
		int date2 = aa-3;
		if(date1 < 10){
			if(date1 %2 ==0){
				strdt1 = "0"+date1+"30";
			}else{
				strdt1 = "0"+date1+"31";
			}
		}else{
			if(date1 % 2 == 0){
				strdt1= ""+date1+"30";
			}else{
				strdt1= ""+date1+"31";
			}
		}
		
		if(date2 < 10){
			strdt2 = "0"+date2+"01";
		}else{
			strdt2 = ""+date2+"01";
		}
		
	
		//sql="select sum(amount),mid from for_settlement where group by mid order by sum(amount) desc";
		sql = "select f.BUSINESS_NAME AS MerchantName , sum(a.AMOUNT) as TotalAmount "+
				"from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f " +
				"ON f.MID_FK=m.ID  where a.status='S' and DATE between '"+strdt2+"' and '"+strdt1+"' group by a.MID order by TotalAmount desc limit 5";
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			TopFiveMerchant fs = new TopFiveMerchant();
			fs.setMerchantName(rec[0].toString());
			fs.setAmount(rec[1].toString());
			listFs.add(fs);
		}
		//dashBoardData.setListTopFiveMerchant(listFs);
		return listFs;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<TopFiveMerchant> getAgentWiseDet() {
		
		//DashBoardDataObject dashBoardData = new DashBoardDataObject();
		List<TopFiveMerchant> listFs = new ArrayList<TopFiveMerchant>();
		String sql =null;
		
		//sql="select sum(amount),mid from for_settlement where group by mid order by sum(amount) desc";
		//sql = "select sum(amount),mid from for_settlement where date between '1103' and '1231' group by mid order by sum(amount) desc";
		//sql = "select first_Name, id from Agent";
		Date curDate = new Date();
		
		String rd = new SimpleDateFormat("MM").format(curDate);
		String strdt1 = null;
		String strdt2 = null;
		int aa = Integer.parseInt(rd);
		
		int date1 = aa;
		int date2 = aa-3;
		if(date1 < 10){
			if(date1 %2 ==0){
				strdt1 = "0"+date1+"30";
			}else{
				strdt1 = "0"+date1+"31";
			}
		}else{
			if(date1 % 2 == 0){
				strdt1= ""+date1+"30";
			}else{
				strdt1= ""+date1+"31";
			}
		}
		
		if(date2 < 10){
			strdt2 = "0"+date2+"01";
		}else{
			strdt2 = ""+date2+"01";
		}
		
		sql = "select  sum(a.AMOUNT) as TotalAmount , ag.ag_name "
				+ "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
				+ "ON f.MID_FK=m.ID INNER JOIN agent ag on f.AGID_FK=ag.id where a.status='S' and DATE between '"
				+ strdt2 + "' and '" + strdt1 + "' group by ag.ag_name order by TotalAmount desc limit 5";
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			TopFiveMerchant fs = new TopFiveMerchant();
			fs.setAmount(rec[0].toString());
			fs.setMerchantName(rec[1].toString());
			listFs.add(fs);
		}
		//dashBoardData.setListTopFiveMerchant(listFs);
		return listFs;
	}


	@Override
	public <E> E saveOrUpdateEntity(E entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrUpdateEntities(Iterable<? extends Object> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <E> E loadEntityByKey(Class<E> clazz, Long key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPaginationItemsByPage(PaginationBean paginationBean, Class clazz, ArrayList<Criterion> props,
			Order order) {
		// TODO Auto-generated method stub
		
	}


	@Override
	@Transactional(readOnly = true)
	public List<DashBoardData> getTxnVolumeCount() {
		
		List<DashBoardData> listFs = new ArrayList<DashBoardData>();
		
		String sql ="select sum(a.AMOUNT) as TotalAmount , count(*) as Volume,month(a.time_stamp) "
				+ "from FOR_SETTLEMENT a "
				+ "where a.status='S' group by MONTH(a.time_stamp) order by a.time_stamp";
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		
		for (Object[] rec : resultSet) {
			DashBoardData dbd = new DashBoardData();
			dbd.setAmount(rec[0].toString());
			dbd.setCount(rec[1].toString());
			dbd.setTxnDate(rec[2].toString());
			
			listFs.add(dbd);
			
		}
		
		return listFs;
	}


	@Override
	@Transactional(readOnly = true)
	public List<DashBoardData> getTopMerchant() {
		List<DashBoardData> listFs = new ArrayList<DashBoardData>();
		String sql =null;
		
		Date curDate = new Date();
		
		DashBoardData fsothers = new DashBoardData();
		
		String rd = new SimpleDateFormat("MM").format(curDate);
		String strdt1 = null;
		String strdt2 = null;
		int aa = Integer.parseInt(rd);
		
		int date1 = aa-1;
		int date2 = aa-3;
		if(date1 < 10){
			if(date1 %2 ==0){
				strdt1 = "0"+date1+"30";
			}else{
				strdt1 = "0"+date1+"31";
			}
		}else{
			if(date1 % 2 == 0){
				strdt1= ""+date1+"30";
			}else{
				strdt1= ""+date1+"31";
			}
		}
		
		if(date2 < 10){
			strdt2 = "0"+date2+"01";
		}else{
			strdt2 = ""+date2+"01";
		}
		
	
		sql = "select f.BUSINESS_NAME AS MerchantName , sum(a.AMOUNT) as TotalAmount , count(*) "+
		"from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f " +
		"ON f.MID_FK=m.ID  where a.status='S' and DATE between '"+strdt2+"' and '"+strdt1+"' group by a.MID order by TotalAmount desc";
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		int cnt =0;
		long amount = 0l;
		long txnCnt = 0l;
		for (Object[] rec : resultSet) {
			if(cnt <= 5){
			DashBoardData fs = new DashBoardData();
			cnt=cnt+1;
			fs.setMerchantName(rec[0].toString());
			fs.setAmount(rec[1].toString());
			fs.setCount(rec[2].toString());
			listFs.add(fs);
			}else{
				cnt = cnt +1;
				amount = amount+(Integer.parseInt(rec[1].toString()));
				txnCnt = txnCnt +(Integer.parseInt(rec[2].toString()));
			}
		}
		//String amt = amount;
		if(cnt > 5){
			fsothers.setMerchantName("Others");
			fsothers.setAmount(""+amount);
			fsothers.setCount(""+txnCnt);
			listFs.add(fsothers);
		}
		//dashBoardData.setListTopFiveMerchant(listFs);
		return listFs;
	}
	

	@Override
	@Transactional(readOnly = true)
	public List<DashBoardData> getTxnStateWise() {
		
		logger.info("Inside getTxnStateWise");
		
		List<DashBoardData> listDBD = new ArrayList<DashBoardData>();
		
		String sql = "select f.state As State , sum(a.AMOUNT) as TotalAmount from FOR_SETTLEMENT a "
				+ "INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID  "
				+ "where a.status='S' group by f.state order by f.state";
		
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			
			DashBoardData fs = new DashBoardData();
			fs.setMerchantName(rec[0].toString());
			fs.setAmount(rec[1].toString());
			listDBD.add(fs);
			
		}
		
		return listDBD;
	}


	@Override
	@Transactional(readOnly = true)
	public List<DashBoardData> getMerchantDevice() {
		
		
		logger.info("Inside getMerchantDevice");
		
		List<DashBoardData> listDBD = new ArrayList<DashBoardData>();
		
		String sql = "select count(*),MONTH(ACTIVATE_DATE) from MERCHANT where status ='ACTIVE' group by MONTH(ACTIVATE_DATE) order by ACTIVATE_DATE";

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			
			DashBoardData fs = new DashBoardData();
			fs.setMerchantName("Merchant");
			fs.setCount(rec[0].toString());
			fs.setTxnDate(rec[1].toString());
			listDBD.add(fs);
			
		}
		
		String sql1 = "select count(*),MONTH(ACTIVATED_DATE) from TERMINAL_DETAILS group by MONTH(ACTIVATED_DATE) order by ACTIVATED_DATE";
	
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		for (Object[] rec : resultSet1) {
			
			DashBoardData fs = new DashBoardData();
			fs.setMerchantName("TerminalDetails");
			fs.setCount(rec[0].toString());
			fs.setTxnDate(rec[1].toString());
			listDBD.add(fs);
			
		}
		
		return listDBD;
	}


	@Override
	public List<DashBoardData> getDeviceTxnList() {
		
		String sql= "";
		
		List<DashBoardData> listFs = new ArrayList<DashBoardData>();
		
		sql = "select sum(AMOUNT) as TotalAmount , MONTH(TIME_STAMP) from FOR_SETTLEMENT where status='S' group by MONTH(TIME_STAMP) order by TIME_STAMP";
	
				Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet = sqlQuery.list();
				
				for (Object[] rec : resultSet) {
					DashBoardData fs = new DashBoardData();
					logger.info("loop : " + rec[1].toString());
					fs.setAmount(rec[0].toString());
					fs.setTxnDate(rec[1].toString());
					listFs.add(fs);
				}
				
				List<DashBoardData> listDBD = new ArrayList<DashBoardData>();
				
				String sql1 = "select count(*),MONTH(ACTIVATED_DATE) from TERMINAL_DETAILS group by MONTH(ACTIVATED_DATE) order by ACTIVATED_DATE";
				
				logger.info("Query : " + sql1);
				Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);// .addEntity(ForSettlement.class);
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet1 = sqlQuery1.list();
				for (Object[] rec : resultSet1) {
					
					DashBoardData fs = new DashBoardData();
					//fs.setMerchantName("TerminalDetails");
					fs.setCount(rec[0].toString());
					fs.setTxnDate(rec[1].toString());
					listDBD.add(fs);
					
				}
				
				int count = 0;
				
				for(DashBoardData dbd : listFs){
					for(DashBoardData dbd1 : listDBD){
						//System.out.println("terminal details :"+dbd1.getTxnDate());
						if(dbd1.getTxnDate().equals(dbd.getTxnDate())){
							count = count + Integer.parseInt(dbd1.getCount());
							dbd.setCount(""+count);
						}
					}
					
				}
				
		
		return listFs;
	}


	@Override
	public int getTotalDevice() {
		int totaldevice =0;
		String sql1 = "select count(*) from TERMINAL_DETAILS t where t.MERCHANT_ID not in ('000002214000016','APEOOS9999','000002214000156','2214000016','000001111000001')";
		logger.info("Query : " + sql1);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);
		@SuppressWarnings("unchecked")
		List<BigInteger> resultSet1 = sqlQuery1.list();
		
		for (BigInteger rec : resultSet1) {
			
			totaldevice = Integer.parseInt(rec.toString());
			
		}
		
		return totaldevice;
		
	}


	@Override
	public String getCurrentMonthTxn() {
		String totalTxn = "", txnTotalSum="";
		
		String umtotalTxn = null;
		
		double paytxn=0.0;double umtxn=0.0;
		double totalSum=0.0;
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
       // int year = calendar.get(Calendar.YEAR);
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		//int month = now.getMonthValue();
		//int day = now.getDayOfMonth();
		//logger.info("year:"+now.getYear()+ " year: "+year);
       // int year=2017;
		long mon = now.getMonthValue();
		//String sql = "select count(*),sum(AMOUNT) as TotalAmount from FOR_SETTLEMENT where status='S' and MONTH(TIME_STAMP) = "+mon +" and time_Stamp like '2017%'" ;
		
		logger.info("check date and year : " +year+" "+mon+" date: "+now);
		
		String sql = "select count(*),sum(AMOUNT) as TotalAmount from FOR_SETTLEMENT where status='S' "
				+ "and MONTH(TIME_STAMP) = "+mon +" and time_Stamp like '"+year+"%'" ;
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {

			//totalTxn = rec[1].toString();
			int cnt = Integer.parseInt(rec[0].toString());
			if(cnt > 0){
				
				paytxn=new Double(rec[1].toString());
				paytxn = paytxn / 100;
			/*Double d = new Double(rec[1].toString());
			d = d / 100;
			//String pattern = "###0.00";
			String pattern ="#,###.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			 totalTxn = myFormatter.format(d);*/
			}else{
				//totalTxn ="0.00";
				paytxn = 0.00;
			}
			
		}
		
		/*logger.info("totalTxn : " + totalTxn);
		
		if((totalTxn!=null) && (!(totalTxn.isEmpty()))) {
			totalSum=totalSum+Double.valueOf(totalTxn);
		}
		logger.info("totalSum : " + totalSum);*/
		
		String sql1 = "select count(*),sum(F007_TXNAMT) as TotalAmount from UM_ECOM_TXNRESPONSE where STATUS='S' "
				+ "and MONTH(TIME_STAMP) = "+mon +" and TIME_STAMP like '"+year+"%'" ;
		
		logger.info("Query : " + sql1);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql1);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		
		for (Object[] rec : resultSet) {

			//totalTxn = rec[1].toString();
			int cnt = Integer.parseInt(rec[0].toString());
			if(cnt > 0){
				umtxn=new Double(rec[1].toString());
				umtxn = umtxn / 100;
			/*Double d = new Double(rec[1].toString());
			d = d / 100;
			//String pattern = "###0.00";
			String pattern ="#,###.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			 umtotalTxn = myFormatter.format(d);*/
			}else{
				//umtotalTxn ="0.00";
				umtxn= 0.00;
			}
			
		}
		
		/*if((umtotalTxn!=null) && (!(umtotalTxn.isEmpty()))){
			totalSum=totalSum+Double.parseDouble(umtotalTxn);
		 logger.info("umTxn : " + totalSum);
		}
		
		txnTotalSum=String.valueOf(totalSum);
		logger.info("txnTotalSum : " + txnTotalSum);*/
		
		totalSum = paytxn + umtxn;
		logger.info("totalSum : " + totalSum);
		String pattern ="###0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		txnTotalSum = myFormatter.format(totalSum);
		
		logger.info("txnTotalSum : " + txnTotalSum);
		
	return txnTotalSum;
	
	}


	@Override
	public String getTotalMerchant() {
		String totalMerchant = "";
		
		String sql = "select count(*) from MERCHANT m where m.status ='ACTIVE' and m.id not in (1,57,60) "
				+ "and m.ROLE='BANK_MERCHANT'";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<BigInteger> resultSet1 = sqlQuery1.list();
		
		for (BigInteger rec : resultSet1) {

			//totalTxn = rec[1].toString();
			
			totalMerchant = rec.toString();
			
			
		}
		
		return totalMerchant;
	}


	

	@Override
	public int getMerchantTotalDevice(String mid) {
		int totaldevice =0;
		//String sql1 = "select count(*) from TERMINAL_DETAILS t where t.MERCHANT_ID='"+mid+"'";
		String sql1 = "select count(*) from TerminalDetails t where merchantId= :mid";
		//logger.info("Query : " + sql1);
		Query sqlQuery1 = super.getSessionFactory().createQuery(sql1);
		sqlQuery1.setString("mid", mid);
		@SuppressWarnings("unchecked")
		List<Long> resultSet1 = sqlQuery1.list();
		
		for (Long rec : resultSet1) {
			
			totaldevice = Integer.parseInt(rec.toString());
			
		}
		
		return totaldevice;
	}
	
	
	@Override
	public int getMobiliteMerchantTotalDevice(String tid) {
		int totaldevice =0;
		//String sql1 = "select count(*) from TERMINAL_DETAILS t where t.MERCHANT_ID='"+mid+"'";
		String sql1 = "select count(*) from MobiLiteTerminal t where tid= :tid";
		//logger.info("Query : " + sql1);
		Query sqlQuery1 = super.getSessionFactory().createQuery(sql1);
		sqlQuery1.setString("tid", tid);
		@SuppressWarnings("unchecked")
		List<Long> resultSet1 = sqlQuery1.list();
		
		for (Long rec : resultSet1) {
			
			totaldevice = Integer.parseInt(rec.toString());
			
		}
		
		return totaldevice;
	}

	
	@Override
	public int getMerchantTotalDeviceCount(Merchant merchant)
	{
		int totaldevicecount = 0;
		String mid=null,motoMid=null,ezypassMid=null,ezywayMid=null,ezyrecMid=null,ssmid = null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null,umssmid = null;
		
		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
		}
		if(merchant.getMid().getMotoMid()!=null) {
			motoMid=merchant.getMid().getMotoMid();
		}
		if(merchant.getMid().getEzypassMid()!=null) {
			ezypassMid=merchant.getMid().getEzypassMid();
		}
		if(merchant.getMid().getEzywayMid()!=null) {
			ezywayMid=merchant.getMid().getEzywayMid();
		}
		if(merchant.getMid().getEzyrecMid()!=null) {
			ezyrecMid=merchant.getMid().getEzyrecMid();
		}
		
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid() ;
		}
		if (merchant.getMid().getUmEzyrecMid()!= null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid()!= null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}

		if (merchant.getMid().getSsMotoMid() != null) {
			ssmid = merchant.getMid().getSsMotoMid();
		}
		
		
		if (merchant.getMid().getUmSsMotoMid() != null) {
			umssmid = merchant.getMid().getUmSsMotoMid();
		}
		//logger.info("merchanat id for totaldevice count"+mid);
		String sql= "select COUNT(*),DATEDIFF(now(),t.suspendedDate) from TerminalDetails t "
				+ "where DATEDIFF(now(),t.suspendedDate)> 30 and t.merchantId IN (:mid,:motoMid,:ezypassMid,:ezywayMid,:ezyrecMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid,:umssmid)"
				+ " GROUP BY t.merchantId";
		//logger.info("Query: "+sql);
		Query sqlQuery=super.getSessionFactory().createQuery(sql);
		sqlQuery.setString("mid", mid);
		sqlQuery.setString("motoMid", motoMid);
		sqlQuery.setString("ezypassMid", ezypassMid);
		sqlQuery.setString("ezywayMid", ezywayMid);
		sqlQuery.setString("ezyrecMid", ezyrecMid);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("umEzyrecMid", umEzyrecMid);
		sqlQuery.setString("umEzypassMid", umEzypassMid);
		sqlQuery.setString("umssmid", umssmid);
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		if (resultSet != null) {
			for (Object[] rs : resultSet) {
				totaldevicecount = Integer.parseInt(rs[0].toString());
			}
		}
		return totaldevicecount;
	}
	
	
	@Override
	public String getMerchantDailyTxn(Merchant merchant) {
		String totalTxn = "";
		
		Double totalSum=0.0;
		String totalTxn1 = null,totalTxn2 = null,totalTxn3 = null;
		DateTime dateTime = new DateTime();
		LocalDate lastDate =dateTime.minusDays(1).toLocalDate();
	    String lastDay = lastDate.toString();
	    
		logger.info("check date: "+lastDay);

		
		String mid=null,motoMid=null,ezyrecMid=null,ezywayMid=null,ezypassMid=null,ssmid = null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null, umssmid = null;
		
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getSsMotoMid() != null) {
			ssmid = merchant.getMid().getSsMotoMid();
		}
		if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
		}
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid() ;
		}
		if (merchant.getMid().getUmEzyrecMid()!= null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid()!= null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getUmSsMotoMid() != null) {
			umssmid = merchant.getMid().getUmSsMotoMid();
		}
		logger.info("Merchant type: "+merchant.getMerchantType());
		if((merchant.getMerchantType()== null) || (merchant.getMerchantType().equals("P"))){
			
			String sql1 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') "
					+ "and mid IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:ssmid) and timeStamp like '"+lastDay+"%'";
			 logger.info("Query1 : " + sql1);
				
				Query sqlQuery1 = super.getSessionFactory().createQuery(sql1);
				sqlQuery1.setString("mid", mid);
				sqlQuery1.setString("motoMid", motoMid);
				sqlQuery1.setString("ezyrecMid", ezyrecMid);
				sqlQuery1.setString("ezywayMid", ezywayMid);
				sqlQuery1.setString("ezypassMid", ezypassMid);
				sqlQuery1.setString("umMid", umMid);
				sqlQuery1.setString("ssmid", ssmid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet1 = sqlQuery1.list();
				
				for (Object[] rec : resultSet1) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn1 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn1 = myFormatter.format(d);
					}
				}
				
				if((totalTxn1!=null) && (!(totalTxn1.isEmpty()))) {
					totalSum=totalSum+ Double.parseDouble(totalTxn1);
				}
				
		}
		
		
		
		
		if((merchant.getMerchantType()!= null) && (merchant.getMerchantType().equals("U") || merchant.getMerchantType().equals("FIUU"))){
			
			logger.info("Merchant type:::: "+merchant.getMerchantType());
			if(  umMid!= null) {
				logger.info("Merchant type:::: "+umMid);
			String sql2 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') "
					+ "and mid IN (:umMid) and timeStamp like '"+lastDay+"%'";
			 
			 logger.info("Query2 : " + sql2);
				Query sqlQuery2 = super.getSessionFactory().createQuery(sql2);
				
				sqlQuery2.setString("umMid", umMid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet2 = sqlQuery2.list();
				
				for (Object[] rec : resultSet2) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn2 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn2 = myFormatter.format(d);
					}
				}
				
				if((totalTxn2!=null) && (!(totalTxn2.isEmpty()))) {
					totalSum=totalSum+Double.parseDouble(totalTxn2);
				}
			}
			
			
			
			if((umEzywayMid!=null) || (umMotoMid!=null)){
				
			String sql3 ="Select count(*),sum(f007_TxnAmt) as TotalAmount from UMEcomTxnResponse where status in ('A','S') "
						+"and f001_MID IN (:umEzywayMid,:umMotoMid,:umssmid,:umEzyrecMid) and timeStamp like '"+lastDay+"%'";
			 logger.info("Query3 : " + sql3);
				Query sqlQuery3 = super.getSessionFactory().createQuery(sql3);
				sqlQuery3.setString("umEzywayMid", umEzywayMid);
				sqlQuery3.setString("umMotoMid", umMotoMid);
				sqlQuery3.setString("umEzyrecMid", umEzyrecMid);
				sqlQuery3.setString("umssmid", umssmid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet3 = sqlQuery3.list();
				
				for (Object[] rec : resultSet3) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn3 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn3 = myFormatter.format(d);
					}
			}
			
				if((totalTxn3!=null) && (!(totalTxn3.isEmpty()))) {
					totalSum=totalSum+Double.parseDouble(totalTxn3);
				}
			}
		}
			
			
		
		totalTxn=String.valueOf(totalSum);
		
		logger.info("totalTxn : " + totalTxn);
		return totalTxn;
		
	}
	
	@Override
	public String getMerchantWeeklyTxn(Merchant merchant) {
		String totalTxn = "";
		
		Double totalSum=0.0;
		String totalTxn1 = null,totalTxn2 = null,totalTxn3 = null;
		Calendar c = Calendar.getInstance();
	     c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

	       DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	       String from= df.format(c.getTime());
	        for (int i = 0; i <6; i++) {
	         c.add(Calendar.DATE, 1);
	           }
	       String to= df.format(c.getTime());
	       
	       
		logger.info("check date:from "+from+":to:"+to);
		

		
		String mid=null,motoMid=null,ezyrecMid=null,ezywayMid=null,ezypassMid=null,ssmid = null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null,umssmid = null;
		
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
		}
		if (merchant.getMid().getSsMotoMid() != null) {
			ssmid = merchant.getMid().getSsMotoMid();
		}
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid() ;
		}
		if (merchant.getMid().getUmEzyrecMid()!= null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid()!= null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getUmSsMotoMid() != null) {
			umssmid = merchant.getMid().getUmSsMotoMid();
		}
		logger.info("Merchant type: "+merchant.getMerchantType());
		if((merchant.getMerchantType()== null) || (merchant.getMerchantType().equals("P"))){
			
			String sql1 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') "
					+ "and mid IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:ssmid) and"
					+ " timeStamp between :fromDate and :toDate order by"
					+ " timeStamp desc";
			 logger.info("Query1 : " + sql1);
				
				Query sqlQuery1 = super.getSessionFactory().createQuery(sql1);
				sqlQuery1.setString("fromDate", from);
				sqlQuery1.setString("toDate", to);
				sqlQuery1.setString("mid", mid);
				sqlQuery1.setString("motoMid", motoMid);
				sqlQuery1.setString("ezyrecMid", ezyrecMid);
				sqlQuery1.setString("ezywayMid", ezywayMid);
				sqlQuery1.setString("ezypassMid", ezypassMid);
				sqlQuery1.setString("umMid", umMid);
				sqlQuery1.setString("ssmid", ssmid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet1 = sqlQuery1.list();
				
				for (Object[] rec : resultSet1) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn1 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn1 = myFormatter.format(d);
					}
				}
				
				if((totalTxn1!=null) && (!(totalTxn1.isEmpty()))) {
					totalSum=totalSum+ Double.parseDouble(totalTxn1);
				}
				
		}
		
		
		
		
		if((merchant.getMerchantType()!= null) && (merchant.getMerchantType().equals("U") || merchant.getMerchantType().equals("FIUU"))){
			
			logger.info("Merchant type:::: "+merchant.getMerchantType());
			if(  umMid!= null) {
				logger.info("Merchant type:::: "+umMid);
			String sql2 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') "
					+ "and mid IN (:umMid) and"
					+ " timeStamp between :fromDate and :toDate order by"
					+ " timeStamp desc";
			 
			 logger.info("Query2 : " + sql2);
				Query sqlQuery2 = super.getSessionFactory().createQuery(sql2);
				sqlQuery2.setString("fromDate", from);
				sqlQuery2.setString("toDate", to);
				
				sqlQuery2.setString("umMid", umMid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet2 = sqlQuery2.list();
				
				for (Object[] rec : resultSet2) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn2 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn2 = myFormatter.format(d);
					}
				}
				
				if((totalTxn2!=null) && (!(totalTxn2.isEmpty()))) {
					totalSum=totalSum+Double.parseDouble(totalTxn2);
				}
			}
			
			
			
			if((umEzywayMid!=null) || (umMotoMid!=null)){
				
			String sql3 ="Select count(*),sum(f007_TxnAmt) as TotalAmount from UMEcomTxnResponse where status in ('A','S') "
						+"and f001_MID IN (:umEzywayMid,:umMotoMid,:umEzyrecMid,:umssmid) and"
						+ " timeStamp between :fromDate and :toDate order by"
						+ " timeStamp desc";
			 logger.info("Query3 : " + sql3);
				Query sqlQuery3 = super.getSessionFactory().createQuery(sql3);
				sqlQuery3.setString("fromDate", from);
				sqlQuery3.setString("toDate", to);
				sqlQuery3.setString("umEzywayMid", umEzywayMid);
				sqlQuery3.setString("umMotoMid", umMotoMid);
				sqlQuery3.setString("umEzyrecMid", umEzyrecMid);
				sqlQuery3.setString("umssmid", umssmid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet3 = sqlQuery3.list();
				
				for (Object[] rec : resultSet3) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn3 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn3 = myFormatter.format(d);
					}
			}
			
				if((totalTxn3!=null) && (!(totalTxn3.isEmpty()))) {
					totalSum=totalSum+Double.parseDouble(totalTxn3);
				}
			}
		}
			
			
		
		totalTxn=String.valueOf(totalSum);
		
		logger.info("totalTxn : " + totalTxn);
		return totalTxn;
		
	}
	
	/*@Override
	public String getMerchantCurrentMonthTxn(String mid,String motoMid) {*/
	
	
	
	
	
	@Override
	public String getMerchantCurrentMonthTxn(Merchant merchant) {
		String totalTxn = "";
		
		Double totalSum=0.0;
		String totalTxn1 = null,totalTxn2 = null,totalTxn3 = null;
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
		//int year=calendar.getWeekYear();
        LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();
		//long mon = date.getMonth()+1;
		logger.info("check year and mon: "+year+" "+mon);
		
		String mid=null,motoMid=null,ezyrecMid=null,ezywayMid=null,ezypassMid=null,ssmid = null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null,umssmid = null;
		
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
		}
		if (merchant.getMid().getSsMotoMid() != null) {
			ssmid = merchant.getMid().getSsMotoMid();
		}
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid() ;
		}
		if (merchant.getMid().getUmEzyrecMid()!= null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid()!= null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getUmSsMotoMid() != null) {
			umssmid = merchant.getMid().getUmSsMotoMid();
		}
		//logger.info("check date and year : " +year+" "+mon +" "+date );
		
		//String sql = "select count(*),sum(AMOUNT) as TotalAmount from FOR_SETTLEMENT where status='S' and MONTH(TIME_STAMP) = "+mon+" and mid='"+mid+"' and time_Stamp like '2017%'";
		
		
		
		/*String sql = "select count(*),sum(amount) as TotalAmount from ForSettlement where status='S' "
				+ "and MONTH(timeStamp) = :mon and mid IN (:mid,:motoMid) and timeStamp like '"+year+"%'";*/
		
		/*if((umEzywayMid!=null) || (umMotoMid!=null)){
			
			String sql ="Select count(*),sum(F007_TXNAMT) as TotalAmount from UM_ECOM_TXNRESPONSE where status in ('A','S') and MONTH(TIME_STAMP) = :mon "
					+"and F001_MID IN (umEzywayMid,:umMotoMid) and TIME_STAMP like '"+year+"%'";
			
		}
		
		
		else {
			
			String sql = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') and MONTH(timeStamp) = :mon "
					+ "and mid IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid) and timeStamp like '"+year+"%'";
			
		}*/
		
		logger.info("Merchant type: "+merchant.getMerchantType());
		if((merchant.getMerchantType()== null) || (merchant.getMerchantType().equals("P"))){
			
			String sql1 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') and MONTH(timeStamp) = :mon "
					+ "and mid IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:ssmid) and timeStamp like '"+year+"%'";
			 logger.info("Query1 : " + sql1);
				
				Query sqlQuery1 = super.getSessionFactory().createQuery(sql1);
				sqlQuery1.setLong("mon", mon);
				sqlQuery1.setString("mid", mid);
				sqlQuery1.setString("motoMid", motoMid);
				sqlQuery1.setString("ezyrecMid", ezyrecMid);
				sqlQuery1.setString("ezywayMid", ezywayMid);
				sqlQuery1.setString("ezypassMid", ezypassMid);
				sqlQuery1.setString("umMid", umMid);
				sqlQuery1.setString("ssmid", ssmid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet1 = sqlQuery1.list();
				
				for (Object[] rec : resultSet1) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn1 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn1 = myFormatter.format(d);
					}
				}
				
				if((totalTxn1!=null) && (!(totalTxn1.isEmpty()))) {
					totalSum=totalSum+ Double.parseDouble(totalTxn1);
				}
				
		}
		
		
		
		
		if((merchant.getMerchantType()!= null) && (merchant.getMerchantType().equals("U") || merchant.getMerchantType().equals("FIUU"))){
			
			logger.info("Merchant type:::: "+merchant.getMerchantType());
			if(  umMid!= null) {
				logger.info("Merchant type:::: "+umMid);
			String sql2 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') and MONTH(timeStamp) = :mon "
					+ "and mid IN (:umMid) and timeStamp like '"+year+"%'";
			 
			 logger.info("Query2 : " + sql2);
				Query sqlQuery2 = super.getSessionFactory().createQuery(sql2);
				sqlQuery2.setLong("mon", mon);
				sqlQuery2.setString("umMid", umMid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet2 = sqlQuery2.list();
				
				for (Object[] rec : resultSet2) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn2 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn2 = myFormatter.format(d);
					}
				}
				
				if((totalTxn2!=null) && (!(totalTxn2.isEmpty()))) {
					totalSum=totalSum+Double.parseDouble(totalTxn2);
				}
			}
			
			
			
			if((umEzywayMid!=null) || (umMotoMid!=null)){
				
			String sql3 ="Select count(*),sum(f007_TxnAmt) as TotalAmount from UMEcomTxnResponse where status in ('A','S') and MONTH(timeStamp) = :mon "
						+"and f001_MID IN (:umEzywayMid,:umMotoMid,:umEzyrecMid,:umssmid) and timeStamp like '"+year+"%'";
			 logger.info("Query3 : " + sql3);
				Query sqlQuery3 = super.getSessionFactory().createQuery(sql3);
				sqlQuery3.setLong("mon", mon);
				sqlQuery3.setString("umEzywayMid", umEzywayMid);
				sqlQuery3.setString("umMotoMid", umMotoMid);
				sqlQuery3.setString("umEzyrecMid", umEzyrecMid);
				sqlQuery3.setString("umssmid", umssmid);
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet3 = sqlQuery3.list();
				
				for (Object[] rec : resultSet3) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn3 = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn3 = myFormatter.format(d);
					}
			}
			
				if((totalTxn3!=null) && (!(totalTxn3.isEmpty()))) {
					totalSum=totalSum+Double.parseDouble(totalTxn3);
				}
			}
		}
			
			
		
		totalTxn=String.valueOf(totalSum);
		
		logger.info("totalTxn : " + totalTxn);
		
		
		
		
		
		
		
		
		
		
		
		
		/*String sql = "select count(*),sum(amount) as TotalAmount from ForSettlement where status in ('A','S') and MONTH(timeStamp) = :mon "
				+ "and mid IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid) and timeStamp like '"+year+"%'";*/
		
		
		/*String sql = "select count(*),sum(amount) as TotalAmount from ForSettlement where status='S' and MONTH(timeStamp) = :mon and mid= :mid and timeStamp between DATE_SUB(now(), INTERVAL 90 DAY)  and now()";*/
		/*logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createQuery(sql);
		sqlQuery1.setLong("mon", mon);
		//sqlQuery1.setLong("year", year);
		sqlQuery1.setString("mid", mid);
		sqlQuery1.setString("motoMid", motoMid);
		sqlQuery1.setString("ezyrecMid", ezyrecMid);
		sqlQuery1.setString("ezywayMid", ezywayMid);
		sqlQuery1.setString("ezypassMid", ezypassMid);
		sqlQuery1.setString("umMid", umMid);
		sqlQuery1.setString("umEzywayMid", umEzywayMid);
		sqlQuery1.setString("umMotoMid", umMotoMid);
		sqlQuery1.setString("umEzyrecMid", umEzyrecMid);
		sqlQuery1.setString("umEzypassMid", umEzypassMid);

		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			//totalTxn = rec[1].toString();
			int a = Integer.parseInt(rec[0].toString());
			if(a == 0){
				totalTxn = "0.00";
			}else{
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				 totalTxn = myFormatter.format(d);
			}
			*/
			
			
			/*String sql1 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status='S' and MONTH(timeStamp) = :mon and mid= :mid and timeStamp like '2017%'";*/
		//}
		
		
		return totalTxn;
	}
	@Override
	public String getnonMerchantCurrentMonthTxn(String mid) {
		String totalTxn = "";
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
		/*int year=calendar.getWeekYear();
		long mon = date.getMonth()+1;*/
        LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();
		//long mon = date.getMonth()+1;
        String ezypassMid=null;
		
		logger.info("check date and year : " +year+" "+mon +" "+date );
		
		//String sql = "select count(*),sum(AMOUNT) as TotalAmount from FOR_SETTLEMENT where status='S' and MONTH(TIME_STAMP) = "+mon+" and mid='"+mid+"' and time_Stamp like '2017%'";
		
		
		
		String sql = "select count(*),sum(amount) as TotalAmount from ForSettlement where status='CT' and MONTH(timeStamp) = :mon and mid= :mid and timeStamp like '"+year+"%'";
		
		
		/*String sql = "select count(*),sum(amount) as TotalAmount from ForSettlement where status='S' and MONTH(timeStamp) = :mon and mid= :mid and timeStamp between DATE_SUB(now(), INTERVAL 90 DAY)  and now()";*/
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createQuery(sql);
		sqlQuery1.setLong("mon", mon);
		sqlQuery1.setString("mid", mid);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			//totalTxn = rec[1].toString();
			int a = Integer.parseInt(rec[0].toString());
			
			if(a == 0){
				totalTxn = "0.00";
			}else{
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				 totalTxn = myFormatter.format(d);
			}
			
			
			
			/*String sql1 = "select count(*),sum(amount) as TotalAmount from ForSettlement where status='S' and MONTH(timeStamp) = :mon and mid= :mid and timeStamp like '2017%'";*/
		}
		
		return totalTxn;
	}


	@Override
	public int getAgentTotalDevice(long id) {
		int totaldevice =0;
		//select count(*) from TERMINAL_DETAILS where merchant_id in (select mid from MID where id in (select mid_fk from MERCHANT m where m.AGID_FK=1));
		String sql1 = "select count(*) from TERMINAL_DETAILS where merchant_id in (select mid from MID where id "
				+ "in (select mid_fk from MERCHANT m where m.AGID_FK="+id+"))";
		logger.info("Query : " + sql1);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);
		@SuppressWarnings("unchecked")
		List<BigInteger> resultSet1 = sqlQuery1.list();
		
		for (BigInteger rec : resultSet1) {
			
			totaldevice = Integer.parseInt(rec.toString());
			
		}
		
		return totaldevice;
	}


	@Override
	public String getAgentTotalMerchant(long id) {
		String totalMerchant = "";
		
		String sql = "select count(*) from MERCHANT where status ='ACTIVE' and AGID_FK="+id;
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<BigInteger> resultSet1 = sqlQuery1.list();
		
		for (BigInteger rec : resultSet1) {
			
			totalMerchant = rec.toString();
			
			
		}
		
		return totalMerchant;
	}


	@Override
	public String getAgentCurrentMonthTxn(long id) {
		String totalTxn = "";
		
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
		/*int year=calendar.getWeekYear();
		int mon = date.getMonth()+1;*/
        LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();
		//long mon = date.getMonth()+1;
		logger.info("check date and year : " +year+" "+mon +" "+date );
		
		//select count(*) , sum(a.AMOUNT)  TotalAmount from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id where a.status='S' and MONTH(TIME_STAMP)=9 and f.AGID_FK=1
		String sql = "select count(*) , sum(a.AMOUNT)  TotalAmount from FOR_SETTLEMENT a INNER JOIN MID m on "
				+ "a.MID=m.MID or a.MID=m.MOTO_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYREC_MID or a.MID=m.EZYPASS_MID "
				+ "or a.MID=m.EZYWAY_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id where "
				+ "a.status='S' and MONTH(TIME_STAMP)="+mon+" and f.AGID_FK="+id +" and time_Stamp like '"+year+"%'";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			int a = Integer.parseInt(rec[0].toString());
			if(a == 0){
				totalTxn = "0.00";
			}else{
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}
		}
		
		return totalTxn;
	}
	
	
	@Override
	public String getAgentMerchantMonthTxn(StringBuffer mid) {
		String totalTxn = "";
		Double totalSum=0.0;
		String totalTxn1 = null,totalTxn2 = null,totalTxn3 = null;
		
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
		/*int year=calendar.getWeekYear();
		int mon = date.getMonth()+1;*/
        LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();
		//long mon = date.getMonth()+1;
		logger.info("check date and year : " +year+" "+mon +" "+date );
		
		String sql1 = "select count(*) , sum(a.AMOUNT)  TotalAmount from FOR_SETTLEMENT a "
				+"WHERE a.MID IN ("+mid+") AND a.status='S' and MONTH(TIME_STAMP)="+mon+" and time_Stamp like '"+year+"%'";
		
		logger.info("Query : " + sql1);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			int a = Integer.parseInt(rec[0].toString());
			if(a == 0){
				totalTxn1 = "0.00";
			}else{
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn1 = myFormatter.format(d);
			}
		}
		
		if((totalTxn1!=null) && (!(totalTxn1.isEmpty()))) {
			totalSum=totalSum+ Double.parseDouble(totalTxn1);
		}
		
		String sql2 = "select count(*) , sum(f007_TxnAmt)  TotalAmount from UMEcomTxnResponse "
				+"WHERE f001_MID IN ("+mid+") AND status in ('A','S') and MONTH(timeStamp)="+mon+" and time_Stamp like '"+year+"%'";
		
		
		logger.info("Query : " + sql2);
		Query sqlQuery2 = super.getSessionFactory().createSQLQuery(sql2);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet2 = sqlQuery1.list();
		
		for (Object[] rec : resultSet2) {
			int a = Integer.parseInt(rec[0].toString());
			if(a == 0){
				totalTxn2 = "0.00";
			}else{
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn2 = myFormatter.format(d);
			}
		}
		
		if((totalTxn2!=null) && (!(totalTxn2.isEmpty()))) {
			totalSum=totalSum+Double.parseDouble(totalTxn2);
		}
		
		totalTxn=String.valueOf(totalSum);	
		logger.info("totalTxn : " + totalTxn);
		return totalTxn;
	}


	@Override
	@Transactional(readOnly = true)
	public List<ThreeMonthTxnData> getThreeMonthTxn() {
		List<ThreeMonthTxnData> TxnDataList = new ArrayList<ThreeMonthTxnData>();
	
		String threeMonthTxn = "";
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		
		
		List<Integer> listMonth = getAllMonth(cDate);
		logger.info("check current month:" + cDate + "listMonth : "+listMonth);
		sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS`='S' "
			+ "and f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		
		logger.info("Query : " + sql);
		//sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 4) {
				listMonth.remove(3);

				for (int a : listMonth) {
					date.add(getMonth(a));

					logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("0.00");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
		for (Object[] rec : resultSet) {
			
		/*String txnCount = rec[0].toString();
		
		
		Double d = new Double(rec[1].toString());
		d = d / 100;
		logger.info("count Size : " + txnCount);
		String pattern = "###0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(d);
		amount.add(output);
		count1.add(txnCount);
		logger.info("dto amount : " + amount);*/
		
		int date1 = Integer.parseInt(rec[2].toString());
		//logger.info("check date:"+ date1 +" : "+listMonth.get(count).intValue());
		while (listMonth.get(count).intValue() != date1) {
			date.add(getMonth(listMonth.get(count).intValue()));
			amount.add("0.00");
			count1.add("0");
			count++;
		}
		if (count <= 2) /*{
			amount.add("");
			count1.add("");
			date.add("");
			count++;
		} else*/ {
		String txnCount = rec[0].toString();
		
		
		Double d = new Double(rec[1].toString());
		d = d / 100;
		//logger.info("count Size : " + txnCount);
		String pattern = "###0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(d);
		amount.add(output);
		count1.add(txnCount);
		//logger.info("dto amount : " + amount);
		
		date.add(getMonth(date1));
		//logger.info(" dto month : " + date);
		count++;
		}
		}
		}
		while(count < 3){
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		ThreeMonthTxnData txnData = new ThreeMonthTxnData();
		txnData.setCount(count1);
		logger.info("check dto count:"+ txnData.getCount());
		txnData.setAmount(amount);
		logger.info("check dto amount:"+ txnData.getAmount());
		txnData.setDate1(date);
		logger.info("check dto month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
		//}
		//}
		return TxnDataList;
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
			} else if(month== -3)
			{
				listMonth.add(9);
			}else if(month == -4)
			{
				listMonth.add(8);
			}
			
			
			
			else {
				listMonth.add(month);
			}
			month--;
		}
		return listMonth;
	}

	
	private static List<Integer> getMerchantAllMonth(int month) {
		List<Integer> listMonth = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			if (month == 0) {
				listMonth.add(12);
			} else if (month == -1) {
				listMonth.add(11);
			} else if (month == -2) {
				listMonth.add(10);
			} else if(month== -3)
			{
				listMonth.add(9);
			}else if(month == -4)
			{
				listMonth.add(8);
			}
			
			
			
			else {
				listMonth.add(month);
			}
			month--;
		}
		return listMonth;
	}
	/*@Override
	public List<ThreeMonthTxnData> getMerchantTxnCount(final String mid,String motoMid) {*/
	@Override
	public List<ThreeMonthTxnData> getMerchantTxnCount(Merchant merchant) {
		List<ThreeMonthTxnData> TxnDataList = new ArrayList<ThreeMonthTxnData>();
		
		//String threeMonthTxn = "";
		String mid=null;
		String motoMid=null;
		String ezyrecMid=null;
		String ezywayMid=null;
		String ezypassMid=null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null;
		String fiuuMid = null;
		
		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
		}
if(merchant.getMid().getMotoMid()!=null) {
	motoMid=merchant.getMid().getMotoMid();	
		}
if(merchant.getMid().getEzypassMid()!=null) {
	ezypassMid=merchant.getMid().getEzypassMid();
}
if(merchant.getMid().getEzyrecMid()!=null) {
	ezyrecMid=merchant.getMid().getEzyrecMid();
}
if(merchant.getMid().getEzywayMid()!=null) {
	ezywayMid=merchant.getMid().getEzywayMid();
}
if (merchant.getMid().getUmMid() != null) {
	umMid = merchant.getMid().getUmMid();
}
if (merchant.getMid().getUmEzywayMid() != null) {
	umEzywayMid = merchant.getMid().getUmEzywayMid();
}
if (merchant.getMid().getUmMotoMid() != null) {
	umMotoMid = merchant.getMid().getUmMotoMid() ;
}
if (merchant.getMid().getUmEzyrecMid()!= null) {
	umEzyrecMid = merchant.getMid().getUmEzyrecMid();
}
if (merchant.getMid().getUmEzypassMid()!= null) {
	umEzypassMid = merchant.getMid().getUmEzypassMid();
}
if (merchant.getMid().getFiuuMid()!= null) {
	fiuuMid = merchant.getMid().getFiuuMid();
}


		
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getAllMonth(cDate);
		
		//logger.info("check current month date:" + cDate);
		/*sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS`='S'"
				+ " and f.MID IN ( :mid, :motoMid) and f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
		*/
		
		
		if((merchant.getMerchantType()==null) || (merchant.getMerchantType().equals("P"))) {
			
			sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
					+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid) and "
					+ "f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
			logger.info("query : " + sql);
		}
		
		if((merchant.getMerchantType()!= null) && (merchant.getMerchantType().equals("U") || merchant.getMerchantType().equals("FIUU"))){
			/*if(umMid!=null) {
				
				if((umEzywayMid!=null) || (umMotoMid!=null)) {
					
					sql= "(SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
							+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid) and"
							+ " f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc)"
							+ " UNION ALL "
							+ "(SELECT count(*),sum(f.F007_TXNAMT),month(f.TIME_STAMP) FROM mobiversa.UM_ECOM_TXNRESPONSE f WHERE f.`STATUS` IN ('A','S')"
							+ " and f.F001_MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid) and "
							+ "f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc)";
				}else {
					sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
							+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid) and "
							+ "f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
				}
				
				logger.info("query : " + sql);
				
				
			}else {*/
				
				sql="SELECT count(*),sum(f.F007_TXNAMT),month(f.TIME_STAMP) FROM mobiversa.UM_ECOM_TXNRESPONSE f WHERE f.`STATUS` IN ('A','S')"
						+ " and f.F001_MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:fiuuMid) and "
						+ "f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
				
				logger.info("query : " + sql);
				
			//}
		}
		
		/*sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
				+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid) and "
				+ "f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";*/
		
		
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mid", mid);
		sqlQuery.setString("motoMid", motoMid);
		sqlQuery.setString("ezyrecMid", ezyrecMid);
		sqlQuery.setString("ezywayMid", ezywayMid);
		sqlQuery.setString("ezypassMid", ezypassMid);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("fiuuMid", fiuuMid);

	//	logger.info("Query : " + sql);
		//sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		//logger.info("Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 4) {
				listMonth.remove(3);
				
				logger.info("listMonth : " + listMonth.size());

				for (int a : listMonth) {
					date.add(getMonth(a));

					//logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					//logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("0.00");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
				for (Object[] rec : resultSet) {
					
				/*String txnCount = rec[0].toString();
				
				
				Double d = new Double(rec[1].toString());
				d = d / 100;
				logger.info("count Size : " + txnCount);
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(d);
				amount.add(output);
				count1.add(txnCount);
				logger.info("dto amount : " + amount);*/
					logger.info("listMonth : " + listMonth.size());
				int date1 = Integer.parseInt(rec[2].toString());
				//logger.info("check merchant date:"+ date1 +":" + listMonth.get(count).intValue() );
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count1.add("0");
					count++;
				}
				
				logger.info("count1 : " + count1+"count : "+ count);
				if (count <= 2) /*{
					amount.add("");
					count1.add("");
					date.add("");
					count++;
				} else*/ {
					
					logger.info("count <= 2 " + listMonth.size());
					String txnCount = rec[0].toString();
					
					
					Double d = new Double(rec[1].toString());
					d = d / 100;
					//logger.info("count Size : " + txnCount);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amount.add(output);
					count1.add(txnCount);
					//logger.info("dto amount : " + amount);
					
					date.add(getMonth(date1));
					count++;
					//logger.info(" dto month : " + date);
				}
				logger.info("count1 : " + count1+"count : "+ count);				
				}
				}
		
		while(count < 3){
			
			logger.info("count <3 " + listMonth.size());
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		ThreeMonthTxnData txnData = new ThreeMonthTxnData();
		txnData.setCount(count1);
		//logger.info("check dto count:"+ txnData.getCount());
		txnData.setAmount(amount);
		//logger.info("check dto amount:"+ txnData.getAmount());
		txnData.setDate1(date);
		//logger.info("check dto month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
		
		return TxnDataList;
	}

	@Override
	public List<ThreeMonthTxnData> getnonMerchantTxnCount(final String mid) {
		List<ThreeMonthTxnData> TxnDataList = new ArrayList<ThreeMonthTxnData>();
		
		//String threeMonthTxn = "";
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getAllMonth(cDate);
		
		//logger.info("check current month date:" + cDate);
		sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS`='CT' and f.MID = :mid and f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mid", mid);
		logger.info("Query : " + sql);
		//sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 4) {
				listMonth.remove(3);

				for (int a : listMonth) {
					date.add(getMonth(a));

					logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("0.0");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
				for (Object[] rec : resultSet) {
					
				/*String txnCount = rec[0].toString();
				
				
				Double d = new Double(rec[1].toString());
				d = d / 100;
				logger.info("count Size : " + txnCount);
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(d);
				amount.add(output);
				count1.add(txnCount);
				logger.info("dto amount : " + amount);*/
				
				int date1 = Integer.parseInt(rec[2].toString());
				//logger.info("check merchant date:"+ date1 +":" + listMonth.get(count).intValue() );
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count1.add("0");
					count++;
				}
				if (count <= 2) /*{
					amount.add("");
					count1.add("");
					date.add("");
					count++;
				} else*/ {
					String txnCount = rec[0].toString();
					
					
					Double d = new Double(rec[1].toString());
					d = d / 100;
					//logger.info("count Size : " + txnCount);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amount.add(output);
					count1.add(txnCount);
					//logger.info("dto amount : " + amount);
					
					date.add(getMonth(date1));
					count++;
					//logger.info(" dto month : " + date);
				}
				}
				}
		
		while(count < 3){
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		ThreeMonthTxnData txnData = new ThreeMonthTxnData();
		txnData.setCount(count1);
		logger.info("check dto count:"+ txnData.getCount());
		txnData.setAmount(amount);
		logger.info("check dto amount:"+ txnData.getAmount());
		txnData.setDate1(date);
		logger.info("check dto month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
		
		return TxnDataList;
	}

	@Override
	public List<ThreeMonthTxnData> getAgentTxnCount(long id) {
	List<ThreeMonthTxnData> TxnDataList = new ArrayList<ThreeMonthTxnData>();
		
		//String threeMonthTxn = "";
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getAllMonth(cDate);
		//sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS`='S' and f.MID = :mid and f.TIME_STAMP >= now()-interval 4 month group by month(f.TIME_STAMP) ";
		
		
//	sql="SELECT count(*) , sum(a.AMOUNT)  TotalAmount ,month(a.TIME_STAMP) from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
//		+ "or a.MID=m.MOTO_MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
//		+" where a.status='S' and a.TIME_STAMP >= now()-interval 4 month and f.AGID_FK= :aid group by month(a.TIME_STAMP) "
//		+ "order by a.TIME_STAMP desc";
	
	
	sql ="SELECT count(*) , sum(a.AMOUNT)  TotalAmount ,month(a.TIME_STAMP) from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID "
			   + "or a.MID=m.MOTO_MID or a.MID=m.EZYPASS_MID or a.MID=m.EZYWAY_MID or a.MID=m.EZYREC_MID or a.MID=m.UM_MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+" where a.status='S' and a.TIME_STAMP >= now()-interval 3 month and f.AGID_FK="+id +" group by month(a.TIME_STAMP) order by a.TIME_STAMP desc";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		//sqlQuery.setString("mid", mid);
//		sqlQuery.setLong("aid", id);
		logger.info("Query : " + sql);
		//sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("data Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 4) {
				listMonth.remove(3);

				for (int a : listMonth) {
					date.add(getMonth(a));

					logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
			for (Object[] rec : resultSet) {
				
			/*String txnCount = rec[0].toString();
			
			
			Double d = new Double(rec[1].toString());
			d = d / 100;
			logger.info("count Size : " + txnCount);
			String pattern = "###0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			amount.add(output);
			count1.add(txnCount);
			logger.info("dto amount : " + amount);*/
			
			int date1 = Integer.parseInt(rec[2].toString());
			//logger.info("check date:"+ date1);
			while (listMonth.get(count).intValue() != date1) {
				date.add(getMonth(listMonth.get(count).intValue()));
				amount.add("0.00");
				count1.add("0");
				count++;
			}
			if (count <= 2) /*{
				amount.add("");
				count1.add("");
				date.add("");
				count++;
			} else*/ {
			String txnCount = rec[0].toString();
			
			
			Double d = new Double(rec[1].toString());
			d = d / 100;
			//logger.info("count Size : " + txnCount);
			String pattern = "###0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			amount.add(output);
			count1.add(txnCount);
			//logger.info("dto amount : " + amount);
			
			date.add(getMonth(date1));
			count++;
			//logger.info(" dto month : " + date);
			}
			}	}	
		
		while(count < 3){
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		ThreeMonthTxnData txnData = new ThreeMonthTxnData();
		txnData.setCount(count1);
		logger.info("check dto agent count:"+ txnData.getCount());
		txnData.setAmount(amount);
		logger.info("check dto agent amount:"+ txnData.getAmount());
		txnData.setDate1(date);
		logger.info("check dto agent month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
	//	}
		//}
		return TxnDataList;
	
	}

	@Override
	public List<String> loadNobList() {
		
		String sql = "Select Distinct NATURE_OF_BUSINESS from Merchant m where m.STATUS ='ACTIVE'";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<String> resultSet = sqlQuery.list();
		logger.info("resultset" +resultSet);
		
		return null;
		
		
		
	}
	
	
	
	@Override
	public void getLastFiveTxn(PaginationBean<DashBoardData> paginationBean) {
		
		
		List<String> ids = new ArrayList<String>();
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();		
		/*String sql="select a.AMOUNT,a.TIME_STAMP,me.BUSINESS_NAME,a.TXN_TYPE from FOR_SETTLEMENT a "
				+"INNER JOIN MID m ON a.MID=m.MID or a.MID=m.MOTO_MID " 
				+"or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.EZYWAY_MID "
				+"or a.MID=m.GPAY_MID or a.MID=m.UM_MID "
				+"INNER JOIN MERCHANT me ON me.ID = m.MERCHANT_FK " 
				+"order by TIME_STAMP desc LIMIT 5";*/
		
		String sql="select a.AMOUNT,a.TIME_STAMP,a.MID,a.TXN_TYPE from FOR_SETTLEMENT a " 
				+"order by TIME_STAMP desc LIMIT 5";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[3] != null) {
				if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
			}
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			dsList.add(dData);	
		}
	logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	@Override
	public void getMerchantLastFiveTxn(PaginationBean<DashBoardData> paginationBean, Merchant merchant, String ezypodcheck,String ezylinkCheck) {
		
		String mid=null,motoMid=null,ezyrecMid=null,ezywayMid=null,ezypassMid=null,ssmid = null;
		String umMid=null,umEzywayMid=null,umMotoMid=null,umEzyrecMid=null,umEzypassMid=null,umssmid = null;
		String fiuuMid = null;
				
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
		}
		if (merchant.getMid().getSsMotoMid() != null) {
			ssmid = merchant.getMid().getSsMotoMid();
		}		
		
		if (merchant.getMid().getUmSsMotoMid() != null) {
			umssmid = merchant.getMid().getUmSsMotoMid();
		}
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid() ;
		}
		if (merchant.getMid().getUmEzyrecMid()!= null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid()!= null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getFiuuMid()!= null) {
			fiuuMid = merchant.getMid().getFiuuMid();
		}
	
		String merchantName = merchant.getBusinessName().replaceAll("[^a-zA-Z0-9\\s+]", "");
		logger.info("merchantName: "+merchantName);
		logger.info("Merchant type: "+merchant.getMerchantType());
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();	
		if((merchant.getMerchantType()== null) || (merchant.getMerchantType().equals("P"))){
			
		/*String sql="select a.AMOUNT,a.TIME_STAMP,me.BUSINESS_NAME,a.TXN_TYPE from FOR_SETTLEMENT a "
				+"INNER JOIN MID m ON " 
				+"a.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid) "
				+"INNER JOIN MERCHANT me ON me.ID = m.MERCHANT_FK "
				+"where a.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid) "
				+"order by TIME_STAMP desc LIMIT 5";*/
		
			
			
		String sql="select a.AMOUNT,a.TIME_STAMP,'"+merchantName+"',a.TXN_TYPE from FOR_SETTLEMENT a where a.MID IN  "
					+"(:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:ssmid) AND a.STATUS IN ('A','C','S') order by TIME_STAMP desc LIMIT 5";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery1.setString("mid", mid);
		sqlQuery1.setString("motoMid", motoMid);
		sqlQuery1.setString("ezyrecMid", ezyrecMid);
		sqlQuery1.setString("ezywayMid", ezywayMid);
		sqlQuery1.setString("ezypassMid", ezypassMid);
		sqlQuery1.setString("umMid", umMid);
		sqlQuery1.setString("ssmid", ssmid);
		
		
		
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}
			if (rec[3] != null) {
				
				if((rec[3].toString().equals("EZYREC")) && (ezypodcheck.equals("YES"))) {
					dData.setTxnType("EZYPOD");
				}
				else if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
				
				
			}
			
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			
			logger.info("dData: " + dData);
			dsList.add(dData);	
		}
	}
		
	if((merchant.getMerchantType()!= null) && (merchant.getMerchantType().equals("U") || merchant.getMerchantType().equals("FIUU"))){
			
			logger.info("Merchant type:::: "+merchant.getMerchantType());
			String sql1 ;
			if(umMid!=null) {
				
				if((umEzywayMid!=null) || (umMotoMid!=null)) {
					
					/*sql1= "(SELECT a.AMOUNT,a.TIME_STAMP,'"+merchantName+"',a.TXN_TYPE from FOR_SETTLEMENT a where a.MID IN  "
							+"(:umEzywayMid,:umMotoMid,:umMid) AND a.STATUS IN ('A','C','S') order by TIME_STAMP desc LIMIT 5)"
							+ " UNION ALL "
							+ "(SELECT u.F007_TXNAMT,u.TIME_STAMP,'"+merchantName+"',u.TXN_TYPE from UM_ECOM_TXNRESPONSE u " 
							+ "where u.F001_MID IN (:umEzywayMid,:umMotoMid,:umMid) AND u.STATUS IN ('A','C','S') "
							+ "order by u.TIME_STAMP desc LIMIT 5)";*/
					
					sql1= "SELECT a.AMOUNT,a.TXN_DATE,'"+merchantName+"',a.PRODUCT_TYPE from mobiversa.TRANSACTION_SUMMARY a where a.MID IN "
						 + "(:umEzywayMid,:umMotoMid,:umMid,:umEzyrecMid,:umssmid)  order by TXN_DATE desc LIMIT 5 ";
				}else {
					sql1="SELECT a.AMOUNT,a.TIME_STAMP,'"+merchantName+"',a.TXN_TYPE FROM mobiversa.FOR_SETTLEMENT a where a.MID IN "
							+ " (:umEzywayMid,:umMotoMid,:umMid,:umEzyrecMid,:umssmid) AND a.STATUS IN ('A','C','S') order by TIME_STAMP desc LIMIT 5 ";
				}
				
				logger.info("query : " + sql1);
				
				
			}else {
				
				sql1="SELECT u.F007_TXNAMT,u.TIME_STAMP,'"+merchantName+"',u.TXN_TYPE from UM_ECOM_TXNRESPONSE u "
						+ " where u.F001_MID IN (:umEzywayMid,:umMotoMid,:umMid,:umEzyrecMid,:umssmid,:fiuuMid) AND u.STATUS IN ('A','C','S')  "
						+ " order by u.TIME_STAMP desc LIMIT 5";
				
				logger.info("query : " + sql1);
				
			}
			
			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);
			sqlQuery1.setString("umEzywayMid", umEzywayMid);
			sqlQuery1.setString("umMotoMid", umMotoMid);
			sqlQuery1.setString("umMid", umMid);
			sqlQuery1.setString("umEzyrecMid", umEzyrecMid);
			sqlQuery1.setString("umssmid", umssmid);
			sqlQuery1.setString("fiuuMid", fiuuMid);
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet1 = sqlQuery1.list();
			
			for (Object[] rec : resultSet1) {
				DashBoardData dData = new DashBoardData();
				if (rec[0] != null) {
					double amount = 0;
					amount = Double.parseDouble(rec[0].toString()) / 100;
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					dData.setAmount(output);
				}
				if (rec[1] != null) {
					dData.setTxnDate(rec[1].toString());
				}
				if (rec[2] != null) {
					dData.setMerchantName(rec[2].toString());
				}
				if (rec[3] != null) {
					if((rec[3].toString().equalsIgnoreCase("EZYMOTO")) && (ezylinkCheck.equals("YES"))) {
						dData.setTxnType("EZYLINK");
					}
					else {
					dData.setTxnType(rec[3].toString());
					}
				}
				if(dData.getTxnType() == null) {
					dData.setTxnType("EZYWIRE");
				}
				dsList.add(dData);	
			
			}
			
			/*if(umMid!= null) {
			
			String sql2="select a.AMOUNT,a.TIME_STAMP,'"+merchantName+"',a.TXN_TYPE from FOR_SETTLEMENT a where a.MID IN  "
						+"(:umMid) AND a.STATUS IN ('A','C','S') order by TIME_STAMP desc LIMIT 5";
			
			logger.info("Query : " + sql2);
			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql2);
			sqlQuery1.setString("umMid", umMid);
			
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet1 = sqlQuery1.list();
			
			for (Object[] rec : resultSet1) {
				DashBoardData dData = new DashBoardData();
				if (rec[0] != null) {
					double amount = 0;
					amount = Double.parseDouble(rec[0].toString()) / 100;
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					dData.setAmount(output);
				}
				if (rec[1] != null) {
					dData.setTxnDate(rec[1].toString());
				}
				if (rec[2] != null) {
					dData.setMerchantName(rec[2].toString());
				}
				if (rec[3] != null) {
					
					if(rec[3].toString().isEmpty()) {
						dData.setTxnType("EZYWIRE");
					}else {
					dData.setTxnType(rec[3].toString());
					}
				}
				
				if(dData.getTxnType() == null) {
					dData.setTxnType("EZYWIRE");
				}
				dsList.add(dData);	
			}
			
		}
			
		if((umEzywayMid!=null) || (umMotoMid!=null)){
			

			String sql3="select u.F007_TXNAMT,u.TIME_STAMP,'"+merchantName+"',u.TXN_TYPE from UM_ECOM_TXNRESPONSE u  " 
					+"where u.F001_MID IN (:umEzywayMid,:umMotoMid,:umMid) AND u.STATUS IN ('A','C','S') "
					+"order by u.TIME_STAMP desc LIMIT 5";
			
			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql3);
			sqlQuery1.setString("umEzywayMid", umEzywayMid);
			sqlQuery1.setString("umMotoMid", umMotoMid);
			sqlQuery1.setString("umMid", umMid);
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet1 = sqlQuery1.list();
			
			for (Object[] rec : resultSet1) {
				DashBoardData dData = new DashBoardData();
				if (rec[0] != null) {
					double amount = 0;
					amount = Double.parseDouble(rec[0].toString()) / 100;
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					dData.setAmount(output);
				}
				if (rec[1] != null) {
					dData.setTxnDate(rec[1].toString());
				}
				if (rec[2] != null) {
					dData.setMerchantName(rec[2].toString());
				}
				if (rec[3] != null) {
					if((rec[3].toString().equalsIgnoreCase("EZYMOTO")) && (ezylinkCheck.equals("YES"))) {
						dData.setTxnType("EZYLINK");
					}
					else {
					dData.setTxnType(rec[3].toString());
					}
				}
				if(dData.getTxnType() == null) {
					dData.setTxnType("EZYWIRE");
				}
				dsList.add(dData);	
			}
		}	*/
	}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
}
	
	
	@Override
	public void getLastHundredTxn(PaginationBean<DashBoardData> paginationBean) {
		
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();		
		/*String sql="select a.AMOUNT,a.TIME_STAMP,me.BUSINESS_NAME,a.TXN_TYPE from FOR_SETTLEMENT a "
				+"INNER JOIN MID m ON a.MID=m.MID or a.MID=m.MOTO_MID " 
				+"or a.MID=m.EZYPASS_MID or a.MID=m.EZYREC_MID or a.MID=m.EZYWAY_MID "
				+"or a.MID=m.GPAY_MID or a.MID=m.UM_MID "
				+"INNER JOIN MERCHANT me ON me.ID = m.MERCHANT_FK " 
				+"order by TIME_STAMP desc LIMIT 100";*/
		
		String sql="select a.AMOUNT,a.TIME_STAMP,a.MID,a.TXN_TYPE from FOR_SETTLEMENT a " 
				+"order by TIME_STAMP desc LIMIT 100";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[3] != null) {
				if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
			}
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			dsList.add(dData);	
		}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	@Override
	public void getMerchantHundredTxn(PaginationBean<DashBoardData> paginationBean, Merchant merchant, String ezypodcheck,String ezylinkcheck) {
		
		String mid=null,motoMid=null,ezyrecMid=null,ezywayMid=null,ezypassMid=null;
		String umMid=null,umEzywayMid=null,umMotoMid=null,umEzyrecMid=null,umEzypassMid=null;
		String fiuuMid = null;
		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
		}
		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid() ;
		}
		if (merchant.getMid().getUmEzyrecMid()!= null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzypassMid()!= null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getFiuuMid()!= null) {
			fiuuMid = merchant.getMid().getFiuuMid();
		}
		
		String merchantName = merchant.getBusinessName().replaceAll("[^a-zA-Z0-9\\s+]", "");
		logger.info("merchantName: "+merchantName);
		
		logger.info("Merchant type: "+merchant.getMerchantType());
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();	
		if((merchant.getMerchantType()== null) || (merchant.getMerchantType().equals("P"))){
			
		/*String sql="select a.AMOUNT,a.TIME_STAMP,me.BUSINESS_NAME,a.TXN_TYPE from FOR_SETTLEMENT a "
				+"INNER JOIN MID m ON " 
				+"a.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid) "
				+"INNER JOIN MERCHANT me ON me.ID = m.MERCHANT_FK "
				+"where a.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid) "
				+"order by TIME_STAMP desc LIMIT 100";*/
		
		String sql="select a.AMOUNT,a.TIME_STAMP,'"+merchantName+"',a.TXN_TYPE from FOR_SETTLEMENT a where a.MID IN  "
				+"(:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid) AND a.STATUS IN ('A','C','S') order by TIME_STAMP desc LIMIT 100";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery1.setString("mid", mid);
		sqlQuery1.setString("motoMid", motoMid);
		sqlQuery1.setString("ezyrecMid", ezyrecMid);
		sqlQuery1.setString("ezywayMid", ezywayMid);
		sqlQuery1.setString("ezypassMid", ezypassMid);
		sqlQuery1.setString("umMid", umMid);
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}
			if (rec[3] != null) {
				if((rec[3].toString().equalsIgnoreCase("EZYREC")) && (ezypodcheck.equals("YES"))) {
					dData.setTxnType("EZYPOD");
				}
				else if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
			}
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			dsList.add(dData);	
		}
	}
		
	if((merchant.getMerchantType()!= null) && (merchant.getMerchantType().equals("U") || merchant.getMerchantType().equals("FIUU"))){
			
			logger.info("Merchant type:::: "+merchant.getMerchantType());
			
			
		//if((umEzywayMid!=null) || (umMotoMid!=null)){
			
			String sql3="select u.F007_TXNAMT,u.TIME_STAMP,'"+merchantName+"',u.TXN_TYPE from UM_ECOM_TXNRESPONSE u  " 
					+"where u.F001_MID IN (:umEzywayMid,:umMotoMid,:umMid,:fiuuMid) AND u.STATUS IN ('A','C','S') "
					+"order by u.TIME_STAMP desc LIMIT 100";

			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql3);
			sqlQuery1.setString("umEzywayMid", umEzywayMid);
			sqlQuery1.setString("umMotoMid", umMotoMid);
			sqlQuery1.setString("umMid", umMid);
			sqlQuery1.setString("fiuuMid", fiuuMid);
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet1 = sqlQuery1.list();
			
			for (Object[] rec : resultSet1) {
				DashBoardData dData = new DashBoardData();
				if (rec[0] != null) {
					double amount = 0;
					amount = Double.parseDouble(rec[0].toString()) / 100;
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					dData.setAmount(output);
				}
				if (rec[1] != null) {
					dData.setTxnDate(rec[1].toString());
				}
				if (rec[2] != null) {
					dData.setMerchantName(rec[2].toString());
				}
				if (rec[3] != null) {
					 if((rec[3].toString().equalsIgnoreCase("EZYMOTO")) && (ezylinkcheck.equals("YES"))) {
							dData.setTxnType("EZYLINK");
						}
						else {
						dData.setTxnType(rec[3].toString());
						}
				}
				if(dData.getTxnType() == null) {
					dData.setTxnType("EZYWIRE");
				}
				dsList.add(dData);	
			}
			

	}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	
	
	@Override
	public void getLastHundredTxnUmobile(PaginationBean<DashBoardData> paginationBean) {
		
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();		
		/*String sql="select f.F007_TXNAMT,f.TIME_STAMP,me.BUSINESS_NAME,f.TXN_TYPE from UM_ECOM_TXNRESPONSE f "
				+"INNER JOIN MID m ON f.F001_MID = m.UM_EZYWAY_MID or f.F001_MID=m.UM_MOTO_MID or "
				+"f.F001_MID=m.UM_EZYREC_MID or f.F001_MID=m.UM_EZYPASS_MID or f.F001_MID = m.UM_EZYWAY_MID "
				+"INNER JOIN MERCHANT me ON me.ID = m.MERCHANT_FK " 
				+"order by TIME_STAMP desc LIMIT 100";*/
		
		String sql="select f.F007_TXNAMT,f.TIME_STAMP,f.F001_MID,f.TXN_TYPE from UM_ECOM_TXNRESPONSE f "				
				+"order by TIME_STAMP desc LIMIT 100";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[3] != null) {
				if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
			}
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			
			dsList.add(dData);	
		}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}

	@Override
	public String getAgentCurrentMonthTxnByAgID(long id) {
		String totalTxn = "";
		
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
		/*int year=calendar.getWeekYear();
		int mon = date.getMonth()+1;*/
        LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();
		//long mon = date.getMonth()+1;
		logger.info("check date and year : " +year+" "+mon +" "+date );
		
		//select count(*) , sum(a.AMOUNT)  TotalAmount from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id where a.status='S' and MONTH(TIME_STAMP)=9 and f.AGID_FK=1
		String sql = "select count(*) , sum(a.F007_TXNAMT)  TotalAmount from UM_ECOM_TXNRESPONSE a INNER JOIN MID m on "
				+ "a.F001_MID=m.UM_MOTO_MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id where "
				+ "a.status='S' and MONTH(TIME_STAMP)="+mon+" and f.AGID_FK="+id +" and time_Stamp like '"+year+"%'";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			int a = Integer.parseInt(rec[0].toString());
			if(a == 0){
				totalTxn = "0.00";
			}else{
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}
		}
		
		return totalTxn;
	}
	
	
	
	@Override
	public String getToypayCurrentMonthTxnByAgID(long id) {
		String totalTxn = "";
		
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
		/*int year=calendar.getWeekYear();
		int mon = date.getMonth()+1;*/
        LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();
		//long mon = date.getMonth()+1;
		logger.info("check date and year : " +year+" "+mon +" "+date );
		
		//select count(*) , sum(a.AMOUNT)  TotalAmount from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id where a.status='S' and MONTH(TIME_STAMP)=9 and f.AGID_FK=1
		String sql = "select count(*) , sum(a.F007_TXNAMT)  TotalAmount from UM_ECOM_TXNRESPONSE a INNER JOIN MID m on "
				+ "a.F001_MID=m.UM_EZYWAY_MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id where "
				+ "a.status='S' and MONTH(TIME_STAMP)="+mon+" and f.AGID_FK="+id +" and time_Stamp like '"+year+"%'";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			int a = Integer.parseInt(rec[0].toString());
			if(a == 0){
				totalTxn = "0.00";
			}else{
				Double d = new Double(rec[1].toString());
				d = d / 100;
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				totalTxn = myFormatter.format(d);
			}
		}
		
		return totalTxn;
	}
	

	@Override
	public List<ThreeMonthTxnData> getAgentTxnCountByAgID(long id) {
	List<ThreeMonthTxnData> TxnDataList = new ArrayList<ThreeMonthTxnData>();
		
		//String threeMonthTxn = "";
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getAllMonth(cDate);
		
	
	sql ="SELECT count(*) , sum(a.F007_TXNAMT) as TotalAmount ,month(a.TIME_STAMP) from UM_ECOM_TXNRESPONSE a INNER JOIN MID m on a.F001_MID=m.UM_MOTO_MID "
			   + " INNER JOIN MERCHANT f ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id "
				+" where a.status='S' and a.TIME_STAMP >= now()-interval 3 month and f.AGID_FK="+id +" group by month(a.TIME_STAMP) order by a.TIME_STAMP desc";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		//sqlQuery.setString("mid", mid);
//		sqlQuery.setLong("aid", id);
		logger.info("Query : " + sql);
		//sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("data Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 4) {
				listMonth.remove(3);

				for (int a : listMonth) {
					date.add(getMonth(a));

					logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("0.0");
					count1.add("0");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
			for (Object[] rec : resultSet) {
				
			/*String txnCount = rec[0].toString();
			
			
			Double d = new Double(rec[1].toString());
			d = d / 100;
			logger.info("count Size : " + txnCount);
			String pattern = "###0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			amount.add(output);
			count1.add(txnCount);
			logger.info("dto amount : " + amount);*/
			
			int date1 = Integer.parseInt(rec[2].toString());
			//logger.info("check date:"+ date1);
			while (listMonth.get(count).intValue() != date1) {
				date.add(getMonth(listMonth.get(count).intValue()));
				amount.add("0.00");
				count1.add("0");
				count++;
			}
			if (count <= 2) /*{
				amount.add("");
				count1.add("");
				date.add("");
				count++;
			} else*/ {
			String txnCount = rec[0].toString();
			
			
			Double d = new Double(rec[1].toString());
			d = d / 100;
			//logger.info("count Size : " + txnCount);
			String pattern = "###0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			amount.add(output);
			count1.add(txnCount);
			//logger.info("dto amount : " + amount);
			
			date.add(getMonth(date1));
			count++;
			//logger.info(" dto month : " + date);
			}
			}	}	
		
		while(count < 3){
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
		
		
			
		ThreeMonthTxnData txnData = new ThreeMonthTxnData();
		
		txnData.setCount(count1);
		logger.info("check dto agent count:"+ txnData.getCount());
		txnData.setAmount(amount);
		logger.info("check dto agent amount:"+ txnData.getAmount());
		txnData.setDate1(date);
		logger.info("check dto agent month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
	//	}
		//}
		return TxnDataList;
	
	}
	
	@Override
	public void getAgentLastFiveTxn(PaginationBean<DashBoardData> paginationBean,StringBuffer mid) {
		
		
		List<String> ids = new ArrayList<String>();
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();		
		
		
		String sql="select a.AMOUNT,a.TIME_STAMP,a.MID,a.TXN_TYPE from FOR_SETTLEMENT a " 
				+"where a.MID IN ("+mid+") "
				+"order by TIME_STAMP desc LIMIT 5";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[3] != null) {
				if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
			}
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			dsList.add(dData);	
		}
	logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	
	@Override
	public void getHotelMerchantFiveTxn(PaginationBean<DashBoardData> paginationBean,StringBuffer pMid, 
			StringBuffer uMid) {
		
		
		List<String> ids = new ArrayList<String>();
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();		
		
		
		String sql="select u.F007_TXNAMT,u.TIME_STAMP,u.F001_MID,u.H002_VNO,u.TXN_TYPE,m.BUSINESS_NAME from UM_ECOM_TXNRESPONSE u  " 
				+"INNER JOIN MID mi ON mi.UM_MOTO_MID=u.F001_MID "
				+"INNER JOIN MERCHANT m ON m.ID = mi.MERCHANT_FK "
				+"where u.F001_MID IN ("+uMid+") "
				+"order by u.TIME_STAMP desc LIMIT 5";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[4] != null) {
				if((rec[3].toString().equals("05V")) && (rec[4].toString().equals("EZYMOTO"))) {
					dData.setTxnType("EZYMOTO VCC");
				}else {
				dData.setTxnType(rec[4].toString());
				}
			}else {
				dData.setTxnType("");
			}
			
			if (rec[5] != null) {
				dData.setMerchantName(rec[5].toString());
				
			}
			
			dsList.add(dData);	
		}
	logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	
	@Override
	public void getToypayMerchantFiveTxn(PaginationBean<DashBoardData> paginationBean,StringBuffer pMid, 
			StringBuffer uMid) {
		
		
		List<String> ids = new ArrayList<String>();
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();		
		
		
		String sql="select u.F007_TXNAMT,u.TIME_STAMP,u.F001_MID,u.H002_VNO,u.TXN_TYPE,m.BUSINESS_NAME from UM_ECOM_TXNRESPONSE u  " 
				+"INNER JOIN MID mi ON mi.UM_EZYWAY_MID=u.F001_MID "
				+"INNER JOIN MERCHANT m ON m.ID = mi.MERCHANT_FK "
				+"where u.F001_MID IN ("+uMid+") "
				+"order by u.TIME_STAMP desc LIMIT 5";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[4] != null) {
				
				dData.setTxnType(rec[4].toString());
				
			}else {
				dData.setTxnType("");
			}
			
			if (rec[5] != null) {
				dData.setMerchantName(rec[5].toString());
				
			}
			
			dsList.add(dData);	
		}
	logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	@Override
	public void getAgentLastHundredTxn(PaginationBean<DashBoardData> paginationBean,StringBuffer pMid,
			StringBuffer uMid) {
		
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();		
		
		String sql="select a.AMOUNT,a.TIME_STAMP,a.MID,a.TXN_TYPE from FOR_SETTLEMENT a " 
				+"where a.MID IN ("+pMid+") "
				+"order by TIME_STAMP desc LIMIT 100";
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[3] != null) {
				if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
			}
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			dsList.add(dData);	
		}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	@Override
	public void getHotelMerchantLastHundredUmTxn(PaginationBean<DashBoardData> paginationBean,StringBuffer pMid,
			StringBuffer uMid) {
		
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();	
		
		String sql="select u.F007_TXNAMT,u.TIME_STAMP,u.F001_MID,u.H002_VNO,u.TXN_TYPE,m.BUSINESS_NAME from UM_ECOM_TXNRESPONSE u  " 
				+"INNER JOIN MID mi ON mi.UM_MOTO_MID=u.F001_MID "
				+"INNER JOIN MERCHANT m ON m.ID = mi.MERCHANT_FK "
				+"where u.F001_MID IN ("+uMid+") "
				+"order by u.TIME_STAMP desc LIMIT 100";
		
		/*String sql="select a.AMOUNT,a.TIME_STAMP,a.MID,a.TXN_TYPE from FOR_SETTLEMENT a " 
				+"where a.MID IN ("+uMid+") "
				+"order by TIME_STAMP desc LIMIT 100";*/
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[4] != null) {
				if((rec[3].toString().equals("05V")) && (rec[4].toString().equals("EZYMOTO"))) {
					dData.setTxnType("EZYMOTO VCC");
				}else {
				dData.setTxnType(rec[4].toString());
				}
			}else {
				dData.setTxnType("");
			}
			
			if (rec[5] != null) {
				dData.setMerchantName(rec[5].toString());
				
			}
			dsList.add(dData);	
		}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	
	@Override
	public void getToypayMerchantLastHundredUmTxn(PaginationBean<DashBoardData> paginationBean,StringBuffer pMid,
			StringBuffer uMid) {
		
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();	
		
		String sql="select u.F007_TXNAMT,u.TIME_STAMP,u.F001_MID,u.H002_VNO,u.TXN_TYPE,m.BUSINESS_NAME from UM_ECOM_TXNRESPONSE u  " 
				+"INNER JOIN MID mi ON mi.UM_EZYWAY_MID=u.F001_MID "
				+"INNER JOIN MERCHANT m ON m.ID = mi.MERCHANT_FK "
				+"where u.F001_MID IN ("+uMid+") "
				+"order by u.TIME_STAMP desc LIMIT 100";
		
		/*String sql="select a.AMOUNT,a.TIME_STAMP,a.MID,a.TXN_TYPE from FOR_SETTLEMENT a " 
				+"where a.MID IN ("+uMid+") "
				+"order by TIME_STAMP desc LIMIT 100";*/
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[4] != null) {
				
				dData.setTxnType(rec[4].toString());
				
			}else {
				dData.setTxnType("");
			}
			
			if (rec[5] != null) {
				dData.setMerchantName(rec[5].toString());
				
			}
			dsList.add(dData);	
		}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	@Override
	public void getAgentLastHundredUmTxn(PaginationBean<DashBoardData> paginationBean,StringBuffer pMid,
			StringBuffer uMid) {
		
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();	
		
		String sql="select u.F007_TXNAMT,u.TIME_STAMP,u.F001_MID,u.TXN_TYPE from UM_ECOM_TXNRESPONSE u  " 
				+"INNER JOIN MID mi ON mi.UM_MOTO_MID=u.F001_MID OR mi.UM_EZYWAY_MID=u.F001_MID "
				+"INNER JOIN MERCHANT m ON m.ID = mi.MERCHANT_FK "
				+"where u.F001_MID IN ("+uMid+") "
				+"order by u.TIME_STAMP desc LIMIT 100";
		
		/*String sql="select a.AMOUNT,a.TIME_STAMP,a.MID,a.TXN_TYPE from FOR_SETTLEMENT a " 
				+"where a.MID IN ("+uMid+") "
				+"order by TIME_STAMP desc LIMIT 100";*/
		
		logger.info("Query : " + sql);
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet1 = sqlQuery1.list();
		
		for (Object[] rec : resultSet1) {
			DashBoardData dData = new DashBoardData();
			if (rec[0] != null) {
				double amount = 0;
				amount = Double.parseDouble(rec[0].toString()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				dData.setAmount(output);
			}
			if (rec[1] != null) {
				dData.setTxnDate(rec[1].toString());
			}
			/*if (rec[2] != null) {
				dData.setMerchantName(rec[2].toString());
			}*/
			if (rec[2] != null) {
				dData.setMID(rec[2].toString());
				
			}
			if (rec[3] != null) {
				if(rec[3].toString().isEmpty()) {
					dData.setTxnType("EZYWIRE");
				}else {
				dData.setTxnType(rec[3].toString());
				}
			}
			if(dData.getTxnType() == null) {
				dData.setTxnType("EZYWIRE");
			}
			dsList.add(dData);	
		}
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}
	
	@Override
	public List<SixMonthTxnData> getMerchantSixMonTxn(Merchant merchant) {
		List<SixMonthTxnData> TxnDataList = new ArrayList<SixMonthTxnData>();
		
		//String threeMonthTxn = "";
		String mid=null;
		String motoMid=null;
		String ezyrecMid=null;
		String ezywayMid=null;
		String ezypassMid=null;
		String ssmid = null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null,umssmid = null;;

		String fiuuMid = null;

		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
		}
if(merchant.getMid().getMotoMid()!=null) {
	motoMid=merchant.getMid().getMotoMid();	
		}
if(merchant.getMid().getEzypassMid()!=null) {
	ezypassMid=merchant.getMid().getEzypassMid();
}
if(merchant.getMid().getEzyrecMid()!=null) {
	ezyrecMid=merchant.getMid().getEzyrecMid();
}
if(merchant.getMid().getEzywayMid()!=null) {
	ezywayMid=merchant.getMid().getEzywayMid();
}
if (merchant.getMid().getUmMid() != null) {
	umMid = merchant.getMid().getUmMid();
}
if (merchant.getMid().getUmEzywayMid() != null) {
	umEzywayMid = merchant.getMid().getUmEzywayMid();
}
if (merchant.getMid().getUmMotoMid() != null) {
	umMotoMid = merchant.getMid().getUmMotoMid() ;
}
if (merchant.getMid().getUmEzyrecMid()!= null) {
	umEzyrecMid = merchant.getMid().getUmEzyrecMid();
}
if (merchant.getMid().getUmEzypassMid()!= null) {
	umEzypassMid = merchant.getMid().getUmEzypassMid();
}
if (merchant.getMid().getSsMotoMid() != null) {
	ssmid = merchant.getMid().getSsMotoMid();
}
if (merchant.getMid().getUmSsMotoMid() != null) {
	umssmid = merchant.getMid().getUmSsMotoMid();
}
if (merchant.getMid().getFiuuMid() != null) {
	fiuuMid = merchant.getMid().getFiuuMid();
}
		
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getMerchantAllMonth(cDate);
		
		//logger.info("check current month date:" + cDate);
		/*sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS`='S'"
				+ " and f.MID IN ( :mid, :motoMid) and f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
		*/
		
		
		if((merchant.getMerchantType()==null) || (merchant.getMerchantType().equals("P"))) {
			
			sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
					+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umssmid) and "
					+ "f.TIME_STAMP >= now()-interval 5 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
			logger.info("query : " + sql);
		}
		
		if((merchant.getMerchantType()!= null) && (merchant.getMerchantType().equals("U"))){
			if(umMid!=null) {
				
				if((umEzywayMid!=null) || (umMotoMid!=null)) {
					
					/*sql= "(SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
							+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid) and"
							+ " f.TIME_STAMP >= now()-interval 5 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc)"
							+ " UNION ALL "
							+ "(SELECT count(*),sum(f.F007_TXNAMT),month(f.TIME_STAMP) FROM mobiversa.UM_ECOM_TXNRESPONSE f WHERE f.`STATUS` IN ('A','S')"
							+ " and f.F001_MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid) and "
							+ "f.TIME_STAMP >= now()-interval 5 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc)";
							*/
					sql =" SELECT count(*),SUM(f.AMOUNT),month(f.TXN_DATE) FROM mobiversa.TRANSACTION_SUMMARY f " 
						+ "WHERE  f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umssmid) AND f.TXN_DATE >= NOW() - INTERVAL 5 MONTH " 
						+ "GROUP BY month(f.TXN_DATE) order by f.TXN_DATE desc";

							
				}else {
					sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
							+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umssmid) and "
							+ "f.TIME_STAMP >= now()-interval 5 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
				}
				
				logger.info("query : " + sql);
				
				
			}else {
				
				sql="SELECT count(*),sum(f.F007_TXNAMT),month(f.TIME_STAMP) FROM mobiversa.UM_ECOM_TXNRESPONSE f WHERE f.`STATUS` IN ('A','S')"
						+ " and f.F001_MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umssmid,:fiuuMid) and "
						+ "f.TIME_STAMP >= now()-interval 5 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
				
				logger.info("query : " + sql);
				
			}
		}
		
		/*sql="SELECT count(*),sum(f.AMOUNT),month(f.TIME_STAMP) FROM mobiversa.FOR_SETTLEMENT f WHERE f.`STATUS` IN ('A','S')"
				+ " and f.MID IN (:mid,:motoMid,:ezyrecMid,:ezywayMid,:ezypassMid,:umMid,:umEzywayMid,:umMotoMid,:umEzyrecMid,:umEzypassMid) and "
				+ "f.TIME_STAMP >= now()-interval 3 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";*/
		
		
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mid", mid);
		sqlQuery.setString("motoMid", motoMid);
		sqlQuery.setString("ezyrecMid", ezyrecMid);
		sqlQuery.setString("ezywayMid", ezywayMid);
		sqlQuery.setString("ezypassMid", ezypassMid);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umEzyrecMid", umEzyrecMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("umssmid", umssmid);
		sqlQuery.setString("fiuuMid", fiuuMid);

	//	logger.info("Query : " + sql);
		//sqlQuery.setString("agentName", agentName);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		//logger.info("Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 7) {
				listMonth.remove(6);
				
				logger.info("listMonth : " + listMonth.size());

				for (int a : listMonth) {
					date.add(getMonth(a));

					//logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					//logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("0.00");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
				for (Object[] rec : resultSet) {
					
				/*String txnCount = rec[0].toString();
				
				
				Double d = new Double(rec[1].toString());
				d = d / 100;
				logger.info("count Size : " + txnCount);
				String pattern = "###0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(d);
				amount.add(output);
				count1.add(txnCount);
				logger.info("dto amount : " + amount);*/
					logger.info("listMonth : " + listMonth.size());
				int date1 = Integer.parseInt(rec[2].toString());
				//logger.info("check merchant date:"+ date1 +":" + listMonth.get(count).intValue() );
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count1.add("0");
					count++;
				}
				
				logger.info("count1 : " + count1+"count : "+ count);
				if (count <= 5) /*{
					amount.add("");
					count1.add("");
					date.add("");
					count++;
				} else*/ {
					
					logger.info("count <= 2 " + listMonth.size());
					String txnCount = rec[0].toString();
					
					
					Double d = new Double(rec[1].toString());
					d = d / 100;
					/*
					 * //rk NumberFormat myFormat = NumberFormat.getInstance();
					 * myFormat.setGroupingUsed(true); String output= myFormat.format(d); //rk
					 */					//logger.info("count Size : " + txnCount);
					
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					 
					amount.add(output);
					count1.add(txnCount);
					//logger.info("dto amount : " + amount);
					
					date.add(getMonth(date1));
					count++;
					//logger.info(" dto month : " + date);
				}
				logger.info("count1 : " + count1+"count : "+ count);				
				}
				}
		
		while(count < 6){
			
			logger.info("count <3 " + listMonth.size());
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		SixMonthTxnData txnData = new SixMonthTxnData();
		//txnData.setCount(count1);
		List<String> countRev = new ArrayList<String>();
		for (int i = count1.size() - 1; i >= 0; i--) {
			countRev.add(count1.get(i));
		}
		txnData.setCount(countRev);
		//logger.info("check dto count:"+ txnData.getCount());
		//txnData.setAmount(amount);
		List<String> amountRev = new ArrayList<String>();
		for (int i = amount.size() - 1; i >= 0; i--) {
			amountRev.add(amount.get(i));
		}
		txnData.setAmount(amountRev);
		//logger.info("check dto amount:"+ txnData.getAmount());
		//txnData.setDate1(date);
		List<String> dateRev = new ArrayList<String>();
		for (int i = date.size() - 1; i >= 0; i--) {
			dateRev.add(date.get(i));
		}
		txnData.setDate1(dateRev);
		//logger.info("check dto month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
		
		return TxnDataList;
	}

	
	@Override
	public List<SixMonthTxnData> getHotelMerchantSixMonTxn(long id,StringBuffer uMid) {
		List<SixMonthTxnData> TxnDataList = new ArrayList<SixMonthTxnData>();
		
		//String threeMonthTxn = "";
		String mid=null;
		String motoMid=null;
		String ezyrecMid=null;
		String ezywayMid=null;
		String ezypassMid=null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null;
		
		
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getMerchantAllMonth(cDate);
		
		
			sql="SELECT count(*),sum(a.F007_TXNAMT),MONTH(a.TIME_STAMP) FROM mobiversa.UM_ECOM_TXNRESPONSE a "
					+ "INNER JOIN MID m on a.F001_MID=m.UM_MOTO_MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+"INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "WHERE a.status='S' and a.F001_MID IN ("+uMid+") and f.AGID_FK="+id+" and  "
					+ "a.TIME_STAMP >= now()-interval 6 month group by month(a.TIME_STAMP) order by a.TIME_STAMP desc";
			logger.info("query : " + sql);
				
	
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		//logger.info("Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 7) {
				listMonth.remove(6);
				
				logger.info("listMonth : " + listMonth.size());

				for (int a : listMonth) {
					date.add(getMonth(a));

					//logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					//logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("0.00");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
				for (Object[] rec : resultSet) {
					
				
					logger.info("listMonth : " + listMonth.size());
				int date1 = Integer.parseInt(rec[2].toString());
				//logger.info("check merchant date:"+ date1 +":" + listMonth.get(count).intValue() );
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count1.add("0");
					count++;
				}
				
				logger.info("count1 : " + count1+"count : "+ count);
				if (count <= 5) /*{
					amount.add("");
					count1.add("");
					date.add("");
					count++;
				} else*/ {
					
					logger.info("count <= 2 " + listMonth.size());
					String txnCount = rec[0].toString();
					
					
					Double d = new Double(rec[1].toString());
					d = d / 100;
					//logger.info("count Size : " + txnCount);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amount.add(output);
					count1.add(txnCount);
					//logger.info("dto amount : " + amount);
					
					date.add(getMonth(date1));
					count++;
					//logger.info(" dto month : " + date);
				}
				logger.info("count1 : " + count1+"count : "+ count);				
				}
				}
		
		while(count < 6){
			
			logger.info("count <3 " + listMonth.size());
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		SixMonthTxnData txnData = new SixMonthTxnData();
		//txnData.setCount(count1);
		List<String> countRev = new ArrayList<String>();
		for (int i = count1.size() - 1; i >= 0; i--) {
			countRev.add(count1.get(i));
		}
		txnData.setCount(countRev);
		//logger.info("check dto count:"+ txnData.getCount());
		//txnData.setAmount(amount);
		List<String> amountRev = new ArrayList<String>();
		for (int i = amount.size() - 1; i >= 0; i--) {
			amountRev.add(amount.get(i));
		}
		txnData.setAmount(amountRev);
		//logger.info("check dto amount:"+ txnData.getAmount());
		//txnData.setDate1(date);
		List<String> dateRev = new ArrayList<String>();
		for (int i = date.size() - 1; i >= 0; i--) {
			dateRev.add(date.get(i));
		}
		txnData.setDate1(dateRev);
		//logger.info("check dto month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
		
		return TxnDataList;
	}

	
	
	@Override
	public int getMerchantTotalDeviceCount(String mid) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public List<SixMonthTxnData> getToypayMerchantSixMonTxn(long id,StringBuffer uMid) {
		List<SixMonthTxnData> TxnDataList = new ArrayList<SixMonthTxnData>();
		
		//String threeMonthTxn = "";
		String mid=null;
		String motoMid=null;
		String ezyrecMid=null;
		String ezywayMid=null;
		String ezypassMid=null;
		String umMid=null,umEzywayMid=null,umMotoMid=null, umEzyrecMid=null,umEzypassMid=null;
		
		
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getMerchantAllMonth(cDate);
		
		
			sql="SELECT count(*),sum(a.F007_TXNAMT),MONTH(a.TIME_STAMP) FROM mobiversa.UM_ECOM_TXNRESPONSE a "
					+ "INNER JOIN MID m on a.F001_MID=m.UM_EZYWAY_MID INNER JOIN MERCHANT f ON f.MID_FK=m.ID "
					+"INNER JOIN AGENT ag on f.AGID_FK=ag.id "
					+ "WHERE a.status='S' and a.F001_MID IN ("+uMid+") and f.AGID_FK="+id+" and  "
					+ "a.TIME_STAMP >= now()-interval 6 month group by month(a.TIME_STAMP) order by a.TIME_STAMP desc";
			logger.info("query : " + sql);
				
	
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		//logger.info("Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 7) {
				listMonth.remove(6);
				
				logger.info("listMonth : " + listMonth.size());

				for (int a : listMonth) {
					date.add(getMonth(a));

					//logger.info("check current month1212:" + cDate + "listMonth : " + listMonth);

					//logger.info("month data check the values:" + listMonth.size());
					// logger.info("check maximum values in listMonth in
					// resultset :" + a);

					// date.add("");
					amount.add("0.00");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
				for (Object[] rec : resultSet) {
					
				
					logger.info("listMonth : " + listMonth.size());
				int date1 = Integer.parseInt(rec[2].toString());
				//logger.info("check merchant date:"+ date1 +":" + listMonth.get(count).intValue() );
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count1.add("0");
					count++;
				}
				
				logger.info("count1 : " + count1+"count : "+ count);
				if (count <= 5) /*{
					amount.add("");
					count1.add("");
					date.add("");
					count++;
				} else*/ {
					
					logger.info("count <= 2 " + listMonth.size());
					String txnCount = rec[0].toString();
					
					
					Double d = new Double(rec[1].toString());
					d = d / 100;
					//logger.info("count Size : " + txnCount);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amount.add(output);
					count1.add(txnCount);
					//logger.info("dto amount : " + amount);
					
					date.add(getMonth(date1));
					count++;
					//logger.info(" dto month : " + date);
				}
				logger.info("count1 : " + count1+"count : "+ count);				
				}
				}
		
		while(count < 6){
			
			logger.info("count <3 " + listMonth.size());
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		SixMonthTxnData txnData = new SixMonthTxnData();
		//txnData.setCount(count1);
		List<String> countRev = new ArrayList<String>();
		for (int i = count1.size() - 1; i >= 0; i--) {
			countRev.add(count1.get(i));
		}
		txnData.setCount(countRev);
		//logger.info("check dto count:"+ txnData.getCount());
		//txnData.setAmount(amount);
		List<String> amountRev = new ArrayList<String>();
		for (int i = amount.size() - 1; i >= 0; i--) {
			amountRev.add(amount.get(i));
		}
		txnData.setAmount(amountRev);
		//logger.info("check dto amount:"+ txnData.getAmount());
		//txnData.setDate1(date);
		List<String> dateRev = new ArrayList<String>();
		for (int i = date.size() - 1; i >= 0; i--) {
			dateRev.add(date.get(i));
		}
		txnData.setDate1(dateRev);
		//logger.info("check dto month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
		
		return TxnDataList;
	}
	
	
	
	@Override
	public String getMobiliteMerchantCurrentMonthTxn(MobiLiteMerchant merchant) {
		
		String totalTxn = "";
		
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
		//Date date = new Date();
		
		//int year=calendar.getWeekYear();
        LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
		long mon = now.getMonthValue();
		//long mon = date.getMonth()+1;
		logger.info("check year and mon: "+year+" "+mon);
		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());

			
			String sql3 ="Select count(*),sum(f007_TxnAmt) as TotalAmount from UMEcomTxnResponse where status in ('A','S') and MONTH(timeStamp) = :mon "
						+"and mobiLiteTid IN (:tid) and timeStamp like '"+year+"%'";
			 logger.info("Query3 : " + sql3);
				Query sqlQuery3 = super.getSessionFactory().createQuery(sql3);
				sqlQuery3.setLong("mon", mon);
				sqlQuery3.setString("tid", termDetails.getTid());
				
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet3 = sqlQuery3.list();
				
				for (Object[] rec : resultSet3) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn = myFormatter.format(d);
					}
			}

		logger.info("totalTxn : " + totalTxn);

		return totalTxn;
		
		
	}
	
	
	@Override
	public String getMobiliteMerchantDailyTxn(MobiLiteMerchant merchant) {
		String totalTxn = "";
		
		
		DateTime dateTime = new DateTime();
		LocalDate lastDate =dateTime.minusDays(1).toLocalDate();
	    String lastDay = lastDate.toString();
	    
		logger.info("check date: "+lastDay);

		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());
		
				
			String sql3 ="Select count(*),sum(f007_TxnAmt) as TotalAmount from UMEcomTxnResponse where status in ('A','S') "
						+"and mobiLiteTid IN (:tid) and timeStamp like '"+lastDay+"%'";
			 logger.info("Query3 : " + sql3);
				Query sqlQuery3 = super.getSessionFactory().createQuery(sql3);
				sqlQuery3.setString("tid", termDetails.getTid());
				
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet3 = sqlQuery3.list();
				
				for (Object[] rec : resultSet3) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn = myFormatter.format(d);
					}
			}
			
			
		
		
		
		logger.info("totalTxn : " + totalTxn);
		return totalTxn;
		
	}
	
	
	
	@Override
	public String getMobiliteMerchantWeeklyTxn(MobiLiteMerchant merchant) {
		String totalTxn = "";
		
		Calendar c = Calendar.getInstance();
	     c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

	       DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	       String from= df.format(c.getTime());
	        for (int i = 0; i <6; i++) {
	         c.add(Calendar.DATE, 1);
	           }
	       String to= df.format(c.getTime());
	       
	       
	   	MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());
	       
		logger.info("check date:from "+from+":to:"+to);
	
			String sql3 ="Select count(*),sum(f007_TxnAmt) as TotalAmount from UMEcomTxnResponse where status in ('A','S') "
						+"and mobiLiteTid IN (:tid) and"
						+ " timeStamp between :fromDate and :toDate order by"
						+ " timeStamp desc";
			 logger.info("Query3 : " + sql3);
				Query sqlQuery3 = super.getSessionFactory().createQuery(sql3);
				sqlQuery3.setString("fromDate", from);
				sqlQuery3.setString("toDate", to);
				sqlQuery3.setString("tid", termDetails.getTid());
				
				@SuppressWarnings("unchecked")
				List<Object[]> resultSet3 = sqlQuery3.list();
				
				for (Object[] rec : resultSet3) {
					//totalTxn = rec[1].toString();
					int a = Integer.parseInt(rec[0].toString());
					if(a == 0){
						totalTxn = "0.00";
					}else{
						Double d = new Double(rec[1].toString());
						d = d / 100;
						String pattern = "###0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						totalTxn = myFormatter.format(d);
					}
			}
			
		
		logger.info("totalTxn : " + totalTxn);
		return totalTxn;
		
	}
	
	
	

	@Override
	public void getMobiLiteMerchantLastFiveTxn(PaginationBean<DashBoardData> paginationBean, MobiLiteMerchant merchant) {
		
		
		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());

		String merchantName = merchant.getBusinessName().replaceAll("[^a-zA-Z0-9\\s+]", "");
		logger.info("merchantName: "+merchantName);
		
		ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();	
		
	
				
				String sql1="SELECT u.F007_TXNAMT,u.TIME_STAMP,'"+merchantName+"',u.TXN_TYPE from UM_ECOM_TXNRESPONSE u "
						+ " where u.MOBILITE_TID IN (:tid) AND u.STATUS IN ('A','C','S')  "
						+ " order by u.TIME_STAMP desc LIMIT 5";
				
				logger.info("query : " + sql1);
				
				
			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql1);
			sqlQuery1.setString("tid", termDetails.getTid());
		
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet1 = sqlQuery1.list();
			
			for (Object[] rec : resultSet1) {
				DashBoardData dData = new DashBoardData();
				if (rec[0] != null) {
					double amount = 0;
					amount = Double.parseDouble(rec[0].toString()) / 100;
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					dData.setAmount(output);
				}
				if (rec[1] != null) {
					dData.setTxnDate(rec[1].toString());
				}
				if (rec[2] != null) {
					dData.setMerchantName(rec[2].toString());
				}
				if (rec[3] != null) {
					
					//dData.setTxnType(rec[3].toString());
					
					dData.setTxnType("EZYLINK");
					
				}
				
				dsList.add(dData);	
			
			}
			
	
	
		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
}
	
	
	
	
	@Override
	public List<SixMonthTxnData> getMobiLiteMerchantSixMonTxn(MobiLiteMerchant merchant) {
		List<SixMonthTxnData> TxnDataList = new ArrayList<SixMonthTxnData>();
		
		
		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());
		
		int count = 0;
		int cDate = 0;
		List<String> date = new ArrayList<String>();
		List<String> amount = new ArrayList<String>();
		List<String> count1 = new ArrayList<String>();
		String sql = null;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getMerchantAllMonth(cDate);
		
		
				
	 sql="SELECT count(*),sum(f.F007_TXNAMT),month(f.TIME_STAMP) FROM mobiversa.UM_ECOM_TXNRESPONSE f WHERE f.`STATUS` IN ('A','S')"
			+ " and f.MOBILITE_TID IN (:tid) and "
			+ "f.TIME_STAMP >= now()-interval 6 month group by month(f.TIME_STAMP) order by f.TIME_STAMP desc";
	
	logger.info("query : " + sql);
	

		
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("tid", termDetails.getTid());
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		//logger.info("Size : " + resultSet.size());
		if (resultSet.size() <= 0) {

			if (listMonth.size() == 7) {
				listMonth.remove(6);
				
				logger.info("listMonth : " + listMonth.size());

				for (int a : listMonth) {
					date.add(getMonth(a));

					
					amount.add("0.00");
					count1.add("");
					// logger.info("check month count:" + count1);
					count++;

				}
			} 
	
		} else {
		
				for (Object[] rec : resultSet) {
					
				
					logger.info("listMonth : " + listMonth.size());
				int date1 = Integer.parseInt(rec[2].toString());
				//logger.info("check merchant date:"+ date1 +":" + listMonth.get(count).intValue() );
				while (listMonth.get(count).intValue() != date1) {
					date.add(getMonth(listMonth.get(count).intValue()));
					amount.add("0.00");
					count1.add("0");
					count++;
				}
				
				logger.info("count1 : " + count1+"count : "+ count);
				if (count <= 5) {
					
					logger.info("count <= 2 " + listMonth.size());
					String txnCount = rec[0].toString();
					
					
					Double d = new Double(rec[1].toString());
					d = d / 100;
					//logger.info("count Size : " + txnCount);
					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amount.add(output);
					count1.add(txnCount);
					//logger.info("dto amount : " + amount);
					
					date.add(getMonth(date1));
					count++;
					//logger.info(" dto month : " + date);
				}
				logger.info("count1 : " + count1+"count : "+ count);				
				}
				}
		
		while(count < 6){
			
			logger.info("count <3 " + listMonth.size());
			date.add(getMonth(listMonth.get(count).intValue()));
			//logger.info("query values:" + date);
			amount.add("0.00");
			count1.add("0");
			count++;				
		}
			
		SixMonthTxnData txnData = new SixMonthTxnData();
		//txnData.setCount(count1);
		List<String> countRev = new ArrayList<String>();
		for (int i = count1.size() - 1; i >= 0; i--) {
			countRev.add(count1.get(i));
		}
		txnData.setCount(countRev);
		//logger.info("check dto count:"+ txnData.getCount());
		//txnData.setAmount(amount);
		List<String> amountRev = new ArrayList<String>();
		for (int i = amount.size() - 1; i >= 0; i--) {
			amountRev.add(amount.get(i));
		}
		txnData.setAmount(amountRev);
		//logger.info("check dto amount:"+ txnData.getAmount());
		//txnData.setDate1(date);
		List<String> dateRev = new ArrayList<String>();
		for (int i = date.size() - 1; i >= 0; i--) {
			dateRev.add(date.get(i));
		}
		txnData.setDate1(dateRev);
		//logger.info("check dto month:"+ txnData.getDate1());
		TxnDataList.add(txnData);
		
		return TxnDataList;
	}
	
	
	@Override
	public int getMobiliteMerchantTotalDeviceCount(MobiLiteMerchant merchant)
	{
		int totaldevicecount = 0;
		
		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());
		//logger.info("merchanat id for totaldevice count"+mid);
		String sql= "select COUNT(*),DATEDIFF(now(),t.suspendedDate) from MobiLiteTerminal t "
				+ "where DATEDIFF(now(),t.suspendedDate)> 30 and t.tid IN (:tid)"
				+ " GROUP BY t.tid";
		//logger.info("Query: "+sql);
		Query sqlQuery=super.getSessionFactory().createQuery(sql);
		sqlQuery.setString("tid", termDetails.getTid());
		
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		if (resultSet != null) {
			for (Object[] rs : resultSet) {
				totaldevicecount = Integer.parseInt(rs[0].toString());
			}
		}
		return totaldevicecount;
	}
	
	
	@Override
	public void getMobiliteMerchantHundredTxn(PaginationBean<DashBoardData> paginationBean, MobiLiteMerchant merchant) {
		
		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());
		
		String merchantName = merchant.getBusinessName().replaceAll("[^a-zA-Z0-9\\s+]", "");
		logger.info("merchantName: "+merchantName);
		

			
			String sql3="select u.F007_TXNAMT,u.TIME_STAMP,'"+merchantName+"',u.TXN_TYPE from UM_ECOM_TXNRESPONSE u  " 
					+"where u.MOBILITE_TID IN (:tid) AND u.STATUS IN ('A','C','S') "
					+"order by u.TIME_STAMP desc LIMIT 100";

			Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sql3);
			sqlQuery1.setString("tid", termDetails.getTid());
			
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet1 = sqlQuery1.list();
			ArrayList<DashBoardData> dsList = new ArrayList<DashBoardData>();	
			for (Object[] rec : resultSet1) {
				DashBoardData dData = new DashBoardData();
				if (rec[0] != null) {
					double amount = 0;
					amount = Double.parseDouble(rec[0].toString()) / 100;
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					dData.setAmount(output);
				}
				if (rec[1] != null) {
					dData.setTxnDate(rec[1].toString());
				}
				if (rec[2] != null) {
					dData.setMerchantName(rec[2].toString());
				}
				if (rec[3] != null) {
					dData.setTxnType("EZYLINK");
				}
				else {
				dData.setTxnType("");
				}
				
				dsList.add(dData);	
				}

		logger.info("dslist : " + dsList);
		paginationBean.setItemList(dsList);
		
	}

	


}
