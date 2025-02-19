package com.mobiversa.payment.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.payment.controller.bean.PaginationBean;


@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SettlementWebDaoImpl extends BaseDAOImpl implements SettlementWebDao {

	@Override
	@Transactional(readOnly = true)
	public void listSettlement(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props,String merchantName) {

		
		logger.info("Inside  SettlementWebDaoImpl::listSettlement");
		//String dat = null;
		//String date = null;
		String mID = null;
		String motoMID = null;
		String ezypassMID = null;
		String ezywayMID = null;
		String ezyrecMID = null;
		ArrayList<ForSettlement> fss = new ArrayList<ForSettlement>();
		
		//String sqlmid = "select mid from MID where id in (select mid_fk from MERCHANT where 
		//username='"+merchantName+"')";
		String sqlmid = "select MID,MOTO_MID,EZYPASS_MID,EZYWAY_MID,EZYREC_MID from MID where "
				+ "id in (select mid_fk from MERCHANT where username= :merchantName)";
		
		Query sqlQuery1 = super.getSessionFactory().createSQLQuery(sqlmid);
		sqlQuery1.setString("merchantName", merchantName);
		List<Object[]> resultSet1= sqlQuery1.list();
		for(Object[] rec: resultSet1){
			if(rec[0]!=null){
				mID = rec[0].toString();
			}
			if(rec[1]!=null){
				motoMID = rec[1].toString();
					}
			if(rec[2]!=null){
				ezypassMID = rec[2].toString();
					}
			if(rec[3]!=null){
				ezywayMID = rec[3].toString();
					}
			if(rec[4]!=null){
				ezyrecMID = rec[4].toString();
					}
		 
		
		}
		logger.info("mid: "+mID+" motoMid: "+motoMID+" ezypassMID: "+ezypassMID);
		logger.info("ezywaymid: "+ezywayMID+" ezyrecMID: "+ezyrecMID);
	
		//String sql="select tid,count(Status),sum(amount),Batchno from FOR_SETTLEMENT where status='A' 
		//and mid ='"+mID+"' group by status,tid,batchno";
		String sql="select tid,count(Status),sum(amount),Batchno from FOR_SETTLEMENT where status='A' and "
				+ "mid in (:mID,:motoMID,:ezypassMID,:ezywayMID,:ezyrecMID)  group by status,tid,batchno";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("mID", mID);
		sqlQuery.setString("motoMID", motoMID);
		sqlQuery.setString("ezypassMID", ezypassMID);
		sqlQuery.setString("ezywayMID", ezywayMID);
		sqlQuery.setString("ezyrecMID", ezyrecMID);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("No of Records: "+resultSet.size());
		for (Object[] rec : resultSet) {
			ForSettlement fs = new ForSettlement();
			if(rec[0]!=null) {
			fs.setTid(rec[0].toString());
			}
			if(rec[1]!=null) {
				fs.setStatus(rec[1].toString());
			}
			if(rec[2]!=null) {
				Double d =new Double(rec[2].toString());
				d=d/100;
				String pattern1 ="#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern1);
				String output = myFormatter.format(d);
				fs.setAmount(output);
			}
			if(rec[3]!=null) {
				fs.setBatchNo(rec[3].toString());
			}
			
			
			
			
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		paginationBean.setTotalRowCount(fss.size());

	}
	
	
	public String getDeviceIdByTid(String tID){
		String deviceId = null;
		
		//String sql ="select DEVICE_ID from TERMINAL_DETAILS where tid='"+tID+"'";
		String sql ="select DEVICE_ID from TERMINAL_DETAILS where tid= :tID";
		
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("tID", tID);
		List<Object> resultSet = sqlQuery.list();
		for (Object rec : resultSet){
			deviceId = rec.toString();
			logger.info(" Data :"+deviceId);
		}
		return deviceId;
	}
	
	
}
