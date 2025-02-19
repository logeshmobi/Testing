package com.mobiversa.payment.dao;

import java.text.ParseException;

import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.payment.controller.bean.PayoutSettleBean;
import com.mobiversa.payment.exception.MobileApiException;

public interface EzysettleDao extends BaseDAO{
	
	public SettlementMDR loadNetAmountandsettlementdatebyCardEzysettle(String settlementdate, Merchant merchant) throws ParseException;

	public BoostDailyRecon loadNetAmountandsettlementdatebyBoostEzysettle(String settlementdate, Merchant merchant);

	public GrabPayFile loadNetAmountandsettlementdatebyGrabpayEzysettle(String settlementdate, Merchant merchant);

	public FpxTransaction loadNetAmountandsettlementdatebyFpxEzysettle(String settlementdate, Merchant merchant);

	public EwalletTxnDetails loadNetAmountandsettlementdatebym1PayEzysettle(String settlementdate, Merchant merchant) throws ParseException;

	public int updateEzysettleAmountForCard(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public int updateEzySettleAmountForBoost(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public int updateEzySettleAmountForGrabPay(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public int updateEzySettleAmountForFpx(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public int updateEzySettleAmountForm1pay(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public int loadTransactionCountbyCard(String settledate, Merchant merchant);

	public int loadTransactionCountbyBoost(String formatsettledate, Merchant merchant);

	public int loadTransactionCountbyGrabpay(String formatsettledate, Merchant merchant);
	
	public int loadTransactionCountbyFpx(String formatsettledate, Merchant merchant);

	public int loadTransactionCountbym1Pay(String settledate,String tngMid,String shoppyMid,String umEzywayMid,String umMotoMid,Merchant merchant);
	
	public void revertEzySettleStatusForM1pay(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public void revertEzySettleStatusForFpx(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public void revertEzySettleStatusForGrab(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public void revertEzysettleStatusUpdateforBoost(PayoutSettleBean payoutSettleBean) throws MobileApiException;

	public void revertEzysettleStatusUpdateforCard(PayoutSettleBean payoutSettleBean) throws MobileApiException;
	
//	public void updateEzysettleStausAndReasonCard(PayoutSettleBean payoutSettleBean) throws MobileApiException;
//
//	public void updateEzysettleStausAndReasonBoost(PayoutSettleBean payoutSettleBean) throws MobileApiException;
//
//	public void updateEzysettleStausAndReasonGrabpay() throws MobileApiException;

	
}
