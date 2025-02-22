package com.mobiversa.payment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mobiversa.common.bo.BizAppSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.MdrRates;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobiProductMDR;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MDRDao;
import com.mobiversa.payment.dto.RegMobileUser;
import com.mobiversa.payment.util.ResponseDetails;
import com.mobiversa.payment.util.UMFileGenerateDetails;

@Service
public class MDRDetailsService {

	@Autowired
	private MDRDao mdrDAO;

	private static final Logger logger = Logger.getLogger(MDRDetailsService.class.getName());

	public MobiMDR addMDR(final RegMobileUser entity) throws JMSException {

		logger.info("addMdr Service");
		MobiMDR regMerchantMDR = new MobiMDR();

		String mid = entity.getMid();
//		String prodId = entity.getProdId();
		String cardBrand = entity.getBrand();
		float creditLocalHostMDR = Float.valueOf(entity.getDomCreditHostMDR());
		float creditLocalMerchantMDR = Float.valueOf(entity.getDomCreditMerchantMDR());

		float creditForeignHostMDR = Float.valueOf(entity.getFoCreditHostMDR());
		float creditForeignMerchantMDR = Float.valueOf(entity.getFoCreditMerchantMDR());

		float debitLocalHostMDR = Float.valueOf(entity.getDomDebitHostMDR());
		float debitLocalMerchantMDR = Float.valueOf(entity.getDomDebitMerchantMDR());

		float debitForeignHostMDR = Float.valueOf(entity.getFoDebitHostMDR());
		float debitForeignMerchantMDR = Float.valueOf(entity.getFoDebitMerchantMDR());
		
		float creditLocalHostMDR1 = Float.valueOf(entity.getDomCreditHostMDR1());
        float creditLocalMerchantMDR1 = Float.valueOf(entity.getDomCreditMerchantMDR1());



       float creditForeignHostMDR1 = Float.valueOf(entity.getFoCreditHostMDR1());
        float creditForeignMerchantMDR1 = Float.valueOf(entity.getFoCreditMerchantMDR1());



       float debitLocalHostMDR1 = Float.valueOf(entity.getDomDebitHostMDR1());
        float debitLocalMerchantMDR1 = Float.valueOf(entity.getDomDebitMerchantMDR1());



       float debitForeignHostMDR1 = Float.valueOf(entity.getFoDebitHostMDR1());
        float debitForeignMerchantMDR1 = Float.valueOf(entity.getFoDebitMerchantMDR1());
        
        
        float boostEcomMerchantMDR = Float.valueOf(entity.getBoostEcomMerchantMDR());
        float boostEcomHostMDR =  Float.valueOf(entity.getBoostEcomHostMDR());
        float boostQrMerchantMDR =  Float.valueOf(entity.getBoostQrMerchantMDR());
        float boostQrHostMDR = Float.valueOf(entity.getBoostQrHostMDR());
        float grabEcomMerchantMDR= Float.valueOf(entity.getGrabEcomMerchantMDR());
        float grabEcomHostMDR = Float.valueOf(entity.getGrabEcomHostMDR());
        float grabQrMerchantMDR =  Float.valueOf(entity.getGrabQrMerchantMDR());
        float grabQrHostMDR = Float.valueOf(entity.getGrabQrHostMDR());
        float fpxMerchantMDR = Float.valueOf(entity.getFpxMerchantMDR());
        float fpxHostMDR = Float.valueOf(entity.getFpxHostMDR());
        float tngEcomMerchantMDR = Float.valueOf(entity.getTngEcomMerchantMDR());
        float tngEcomHostMDR = Float.valueOf(entity.getTngEcomHostMDR());
        float tngQrMerchantMDR = Float.valueOf(entity.getTngQrMerchantMDR());
        float tngQrHostMDR = Float.valueOf(entity.getTngQrHostMDR());

		float debitForeignMobiMDR = debitForeignMerchantMDR - debitForeignHostMDR;
		float creditLocalMobiMDR = creditLocalMerchantMDR - creditLocalHostMDR;
		float debitLocalMobiMDR = debitLocalMerchantMDR - debitLocalHostMDR;
		float creditForeignMobiMDR = creditForeignMerchantMDR - creditForeignHostMDR;
		
		float debitForeignMobiMDR1 = debitForeignMerchantMDR1 - debitForeignHostMDR1;
        float creditLocalMobiMDR1 = creditLocalMerchantMDR1 - creditLocalHostMDR1;
        float debitLocalMobiMDR1 = debitLocalMerchantMDR1 - debitLocalHostMDR1;
        float creditForeignMobiMDR1 = creditForeignMerchantMDR1 - creditForeignHostMDR1;
        
        float boostEcomMobiMDR= boostEcomMerchantMDR- boostEcomHostMDR;
        float boostQrMobiMDR= boostQrMerchantMDR - boostQrHostMDR;
        float grabEcomMobiMDR= grabEcomMerchantMDR- grabEcomHostMDR;
        float grabQrMobiMDR= grabQrMerchantMDR - grabQrHostMDR;
        float fpxMobiMDR= fpxMerchantMDR- fpxHostMDR;
        
        float tngEcomMobiMDR= tngEcomMerchantMDR- tngEcomHostMDR;
        float tngQrMobiMDR= tngQrMerchantMDR - tngQrHostMDR;

		String settlePeriod = null;
		if (entity.getSettlePeriod().equals("Normal")) {
			settlePeriod = null;
		} else {
			settlePeriod = entity.getSettlePeriod();
		}
		regMerchantMDR.setCardBrand(cardBrand);
        regMerchantMDR.setMid(mid);
        
        if(cardBrand.equalsIgnoreCase("VISA")) {
            
        creditForeignHostMDR = creditForeignHostMDR1;
        creditForeignMerchantMDR=creditForeignMerchantMDR1;
        creditForeignMobiMDR=creditForeignMobiMDR1;
        creditLocalMobiMDR=creditLocalMobiMDR1;
        creditLocalHostMDR=creditLocalHostMDR1;
        creditLocalMerchantMDR=creditLocalMerchantMDR1;
        debitForeignHostMDR=debitForeignHostMDR1;
        debitForeignMerchantMDR=debitForeignMerchantMDR1;
        debitForeignMobiMDR=debitForeignMobiMDR1;
        debitLocalHostMDR=debitLocalHostMDR1;
        debitLocalMerchantMDR=debitLocalMerchantMDR1;
        debitLocalMobiMDR=debitLocalMobiMDR1;
        regMerchantMDR.setCreditForeignHostMDR(creditForeignHostMDR);
        regMerchantMDR.setCreditForeignMerchantMDR(creditForeignMerchantMDR);
        regMerchantMDR.setCreditForeignMobiMDR(creditForeignMobiMDR);
        regMerchantMDR.setCreditLocalHostMDR(creditLocalHostMDR);
        regMerchantMDR.setCreditLocalMerchantMDR(creditLocalMerchantMDR);
        regMerchantMDR.setCreditLocalMobiMDR(creditLocalMobiMDR);
        regMerchantMDR.setDebitForeignHostMDR(debitForeignHostMDR);
        regMerchantMDR.setDebitForeignMerchantMDR(debitForeignMerchantMDR);
        regMerchantMDR.setDebitForeignMobiMDR(debitForeignMobiMDR);
        regMerchantMDR.setDebitLocalHostMDR(debitLocalHostMDR);
        regMerchantMDR.setDebitLocalMerchantMDR(debitLocalMerchantMDR);
        regMerchantMDR.setDebitLocalMobiMDR(debitLocalMobiMDR);
        
        
        regMerchantMDR.setBoostEcomMerchantMDR(boostEcomMerchantMDR);
        regMerchantMDR.setBoostEcomHostMDR(boostEcomHostMDR);
        regMerchantMDR.setBoostEcomMobiMDR(boostEcomMobiMDR);
        regMerchantMDR.setBoostQrMerchantMDR(boostQrMerchantMDR);
        regMerchantMDR.setBoostQrHostMDR(boostQrHostMDR);
        regMerchantMDR.setBoostQrMobiMDR(boostQrMobiMDR);
        
        regMerchantMDR.setGrabEcomMerchantMDR(grabEcomMerchantMDR);
        regMerchantMDR.setGrabEcomHostMDR(grabEcomHostMDR);
        regMerchantMDR.setGrabEcomMobiMDR(grabEcomMobiMDR);
        regMerchantMDR.setGrabQrMerchantMDR(grabQrMerchantMDR);
        regMerchantMDR.setGrabQrHostMDR(grabQrHostMDR);
        regMerchantMDR.setGrabQrMobiMDR(grabQrMobiMDR);
        regMerchantMDR.setFpxMercAmt(fpxMerchantMDR);
        regMerchantMDR.setFpxHostAmt(fpxHostMDR);
        regMerchantMDR.setFpxMobiAmt(fpxMobiMDR);
        
        
        float fpxTxnMDR=(fpxMobiMDR/fpxMerchantMDR)*100;
        
        regMerchantMDR.setFpxTxnMdr(fpxTxnMDR);
        
        regMerchantMDR.setTngEcomMerchantMDR(tngEcomMerchantMDR);
        regMerchantMDR.setTngEcomHostMDR(tngEcomHostMDR); //setTngEcomHostMDR
        regMerchantMDR.setTngEcomMobiMDR(tngEcomMobiMDR);
        regMerchantMDR.setTngQrMerchantMDR(tngQrMerchantMDR);
        regMerchantMDR.setTngQrHostMDR(tngQrHostMDR);
        regMerchantMDR.setTngQrMobiMDR(tngQrMobiMDR);
        
        
        
        
        }
        if(cardBrand == "MASTERCARD" ||cardBrand == "UNIONPAY" ) {
            regMerchantMDR.setCreditForeignHostMDR(creditForeignHostMDR);
            regMerchantMDR.setCreditForeignMerchantMDR(creditForeignMerchantMDR);
            regMerchantMDR.setCreditForeignMobiMDR(creditForeignMobiMDR);
            regMerchantMDR.setCreditLocalHostMDR(creditLocalHostMDR);
            regMerchantMDR.setCreditLocalMerchantMDR(creditLocalMerchantMDR);
            regMerchantMDR.setCreditLocalMobiMDR(creditLocalMobiMDR);
            regMerchantMDR.setDebitForeignHostMDR(debitForeignHostMDR);
            regMerchantMDR.setDebitForeignMerchantMDR(debitForeignMerchantMDR);
            regMerchantMDR.setDebitForeignMobiMDR(debitForeignMobiMDR);
            regMerchantMDR.setDebitLocalHostMDR(debitLocalHostMDR);
            regMerchantMDR.setDebitLocalMerchantMDR(debitLocalMerchantMDR);
            regMerchantMDR.setDebitLocalMobiMDR(debitLocalMobiMDR);
        }
        
        



       logger.info("Add MDR Details in database");

//		regMerchantMDR.setCardBrand(cardBrand);
//		regMerchantMDR.setMid(mid);
//		regMerchantMDR.setCreditForeignHostMDR(creditForeignHostMDR);
//		regMerchantMDR.setCreditForeignMerchantMDR(creditForeignMerchantMDR);
//		regMerchantMDR.setCreditForeignMobiMDR(creditForeignMobiMDR);
//		regMerchantMDR.setCreditLocalHostMDR(creditLocalHostMDR);
//		regMerchantMDR.setCreditLocalMerchantMDR(creditLocalMerchantMDR);
//		regMerchantMDR.setCreditLocalMobiMDR(creditLocalMobiMDR);
//		regMerchantMDR.setDebitForeignHostMDR(debitForeignHostMDR);
//		regMerchantMDR.setDebitForeignMerchantMDR(debitForeignMerchantMDR);
//		regMerchantMDR.setDebitForeignMobiMDR(debitForeignMobiMDR);
//		regMerchantMDR.setDebitLocalHostMDR(debitLocalHostMDR);
//		regMerchantMDR.setDebitLocalMerchantMDR(debitLocalMerchantMDR);
//		regMerchantMDR.setDebitLocalMobiMDR(debitLocalMobiMDR);
		regMerchantMDR.setSettlePeriod(settlePeriod);
		regMerchantMDR.setPayLater(entity.getPayLater());
		regMerchantMDR.setAmount(entity.getAmount());
		regMerchantMDR.setInstallment(entity.getInstallment());
		regMerchantMDR.setRateId(entity.getRateId());

		logger.info("Add MDR Details in database");
		regMerchantMDR = mdrDAO.saveOrUpdateEntity(regMerchantMDR);

		int count = mdrDAO.getMDRValuesCount(mid);

		logger.info("count::" + count);
		if (count != 0 && count == 3) {

			MdrRates mdrRates = new MdrRates();

			List<MobiMDR> mdrList = new ArrayList<MobiMDR>();

			mdrList = mdrDAO.getMobiMDRDetails(mid);

			logger.info("mdrList::" + mdrList);

			if (mdrList != null) {

				for (MobiMDR mdr : mdrList) {

					logger.info("card::" + mdr.getCardBrand());
					if (mdr.getCardBrand().equals("MASTERCARD")) {

						logger.info("MASTERCARD");
						mdrRates.setCreditLocalMcHost(mdr.getCreditLocalHostMDR());
						mdrRates.setCreditLocalMcMerch(mdr.getCreditLocalMerchantMDR());
						mdrRates.setCreditLocalMcMobi(mdr.getCreditLocalMobiMDR());
						mdrRates.setDebitLocalMcHost(mdr.getDebitLocalHostMDR());
						mdrRates.setDebitLocalMcMerch(mdr.getDebitLocalMerchantMDR());
						mdrRates.setDebitLocalMcMobi(mdr.getDebitLocalMobiMDR());

						mdrRates.setCreditForeignMcHost(mdr.getCreditForeignHostMDR());
						mdrRates.setCreditForeignMcMerch(mdr.getCreditForeignMerchantMDR());
						mdrRates.setCreditForeignMcMobi(mdr.getCreditForeignMobiMDR());
						mdrRates.setDebitForeignMcHost(mdr.getDebitForeignHostMDR());
						mdrRates.setDebitForeignMcMerch(mdr.getDebitForeignMerchantMDR());
						mdrRates.setDebitForeignMcMobi(mdr.getDebitForeignMobiMDR());

					}

					if (mdr.getCardBrand().equals("VISA")) {

						logger.info("VISA");
						mdrRates.setCreditLocalVisaHost(mdr.getCreditLocalHostMDR());
						mdrRates.setCreditLocalVisaMerch(mdr.getCreditLocalMerchantMDR());
						mdrRates.setCreditLocalVisaMobi(mdr.getCreditLocalMobiMDR());
						mdrRates.setDebitLocalVisaHost(mdr.getDebitLocalHostMDR());
						mdrRates.setDebitLocalVisaMerch(mdr.getDebitLocalMerchantMDR());
						mdrRates.setDebitLocalVisaMobi(mdr.getDebitLocalMobiMDR());

						mdrRates.setCreditForeignVisaHost(mdr.getCreditForeignHostMDR());
						mdrRates.setCreditForeignVisaMerch(mdr.getCreditForeignMerchantMDR());
						mdrRates.setCreditForeignVisaMobi(mdr.getCreditForeignMobiMDR());
						mdrRates.setDebitForeignVisaHost(mdr.getDebitForeignHostMDR());
						mdrRates.setDebitForeignVisaMerch(mdr.getDebitForeignMerchantMDR());
						mdrRates.setDebitForeignVisaMobi(mdr.getDebitForeignMobiMDR());

					}

					if (mdr.getCardBrand().equals("UNIONPAY")) {

						logger.info("UNIONPAY");
						mdrRates.setCreditLocalUpHost(mdr.getCreditLocalHostMDR());
						mdrRates.setCreditLocalUpMerch(mdr.getCreditLocalMerchantMDR());
						mdrRates.setCreditLocalUpMobi(mdr.getCreditLocalMobiMDR());
						mdrRates.setDebitLocalUpHost(mdr.getDebitLocalHostMDR());
						mdrRates.setDebitLocalUpMerch(mdr.getDebitLocalMerchantMDR());
						mdrRates.setDebitLocalUpMobi(mdr.getDebitLocalMobiMDR());

						mdrRates.setCreditForeignUpHost(mdr.getCreditForeignHostMDR());
						mdrRates.setCreditForeignUpMerch(mdr.getCreditForeignMerchantMDR());
						mdrRates.setCreditForeignUpMobi(mdr.getCreditForeignMobiMDR());
						mdrRates.setDebitForeignUpHost(mdr.getDebitForeignHostMDR());
						mdrRates.setDebitForeignUpMerch(mdr.getDebitForeignMerchantMDR());
						mdrRates.setDebitForeignUpMobi(mdr.getDebitForeignMobiMDR());

					}

					mdrRates.setRateId(mdr.getRateId());
				}

				mdrRates.setCreatedBy("PORTAL");
				mdrRates.setUserRefference("ENTRY FROM PORTAL");
				mdrRates.setBoostMdr((float) 1.00);
				mdrRates.setGrabpayMdr((float) 1.00);

				mdrDAO.saveOrUpdateEntity(mdrRates);

			} else {
				logger.info("No Mobi MDR Found");
			}

		} else {
			logger.info("Not All Crad Brands Present");
		}

		return regMerchantMDR;

	}

	public MobiMDR loadCardAndMDRDetails(String mid) {
		return mdrDAO.loadCardAndMDRDetails(mid);

	}

	public MobiMDR loadMDRById(String mid, String brand) {
		// TODO Auto-generated method stub
		return mdrDAO.loadMDRById(mid, brand);
	}

	public void listMDRDetailsByMid(PaginationBean<MobiMDR> paginationBean, MID mid) {

		logger.info("listMDRDetailsByMid" + mid);
		List<MobiMDR> mdrList = new ArrayList<MobiMDR>();
		/*
		 * if(mid2!=null) {
		 * 
		 * logger.info("paginationBean : " +paginationBean);
		 * 
		 * // mdrDAO.getMDRDetailsByMid(paginationBean,midDetails);
		 * 
		 * for (String mid:mid2) {
		 */

//			logger.info("mid : " +mid);

		mdrList = mdrDAO.getMDRDetailsByMid(mid);
//		}
		paginationBean.setItemList(mdrList);

//	}

	}

	public MobiMDR updateMDR(final MobiMDR MDRDetails) {

		logger.info("updateMdr Service");
//			logger.info("updateMdr Service"+MDRDetails.getId());

		String mid = MDRDetails.getMid();
		String brand = MDRDetails.getCardBrand();

		MobiMDR updMerchantMDR = mdrDAO.loadMDRById(mid, brand);
		/* MobiMDR updMerchantMDR = new MobiMDR(); */
		/*
		 * entity.setDebitForeignMobiMDR(entity.getDebitForeignMerchantMDR()-
		 * entity.getDebitForeignHostMDR());
		 * entity.setCreditLocalMobiMDR(entity.getCreditLocalMerchantMDR() -
		 * entity.getCreditLocalHostMDR());
		 * entity.setDebitLocalMobiMDR(entity.getDebitLocalMerchantMDR() -
		 * entity.getDebitLocalHostMDR()); entity.setCreditForeignMobiMDR(
		 * entity.getCreditForeignMerchantMDR() - entity.getCreditForeignHostMDR());
		 */

		float debitForeignMobiMDR = MDRDetails.getDebitForeignMerchantMDR() - MDRDetails.getDebitForeignHostMDR();
		float creditLocalMobiMDR = MDRDetails.getCreditLocalMerchantMDR() - MDRDetails.getCreditLocalHostMDR();
		float debitLocalMobiMDR = MDRDetails.getDebitLocalMerchantMDR() - MDRDetails.getDebitLocalHostMDR();
		float creditForeignMobiMDR = MDRDetails.getCreditForeignMerchantMDR() - MDRDetails.getCreditForeignHostMDR();

		updMerchantMDR.setCardBrand(MDRDetails.getCardBrand());
		updMerchantMDR.setMid(MDRDetails.getMid());
		updMerchantMDR.setCreditForeignHostMDR(MDRDetails.getCreditForeignHostMDR());
		updMerchantMDR.setCreditForeignMerchantMDR(MDRDetails.getCreditForeignMerchantMDR());
		updMerchantMDR.setCreditForeignMobiMDR(creditForeignMobiMDR);
		updMerchantMDR.setCreditLocalHostMDR(MDRDetails.getCreditLocalHostMDR());
		updMerchantMDR.setCreditLocalMerchantMDR(MDRDetails.getCreditLocalMerchantMDR());
		updMerchantMDR.setCreditLocalMobiMDR(creditLocalMobiMDR);
		updMerchantMDR.setDebitForeignHostMDR(MDRDetails.getDebitForeignHostMDR());
		updMerchantMDR.setDebitForeignMerchantMDR(MDRDetails.getDebitForeignMerchantMDR());
		updMerchantMDR.setDebitForeignMobiMDR(debitForeignMobiMDR);
		updMerchantMDR.setDebitLocalHostMDR(MDRDetails.getDebitLocalHostMDR());
		updMerchantMDR.setDebitLocalMerchantMDR(MDRDetails.getDebitLocalMerchantMDR());
		updMerchantMDR.setDebitLocalMobiMDR(debitLocalMobiMDR);
		updMerchantMDR.setSettlePeriod(MDRDetails.getSettlePeriod());
		updMerchantMDR.setPayLater(MDRDetails.getPayLater());
		updMerchantMDR.setAmount(MDRDetails.getAmount());
		updMerchantMDR.setInstallment(MDRDetails.getInstallment());

		logger.info("updMerchantMDR" + updMerchantMDR.getMid());

		mdrDAO.saveOrUpdateEntity(updMerchantMDR);
		return updMerchantMDR;

	}

	public void listAllSettlementMDR(PaginationBean<SettlementMDR> paginationBean, MID midDetails, String date) {
		logger.info("listAllSettlementMDR" + midDetails);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		List<SettlementMDR> mdrList = new ArrayList<SettlementMDR>();
		String sDate = null;
//			String date1= null;

		if (midDetails != null && date != "") {
			logger.info("paginationBean by MID , Date ");

			logger.info("UmMid:::::::" + midDetails.getUmMid());
			logger.info("UmMotoMid:::::::" + midDetails.getUmMotoMid());
			logger.info("UmEzywayMid:::::::" + midDetails.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::" + midDetails.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::" + midDetails.getUmEzypassMid());
			logger.info("GpayMid:::::::" + midDetails.getGpayMid());

			logger.info("Mid:::::::" + midDetails.getMid());
			logger.info("MotoMid:::::::" + midDetails.getMotoMid());
			logger.info("EzywayMid:::::::" + midDetails.getEzywayMid());
			logger.info("EzyrecMid:::::::" + midDetails.getEzyrecMid());
			logger.info("EzypassMid:::::::" + midDetails.getEzypassMid());
			logger.info("FiuuMid:::::::" + midDetails.getFiuuMid());

			logger.info("date:::::::" + date);

			Disjunction orExp = null;
			Disjunction orExpStatus = null;

			Criterion um_ezyway = Restrictions.like("mid", midDetails.getUmEzywayMid());
			Criterion um_ezywire = Restrictions.like("mid", midDetails.getUmMid());
			Criterion um_moto = Restrictions.like("mid", midDetails.getUmMotoMid());
			Criterion um_ezypass = Restrictions.like("mid", midDetails.getUmEzypassMid());
			Criterion um_ezyrec = Restrictions.like("mid", midDetails.getUmEzyrecMid());
			Criterion gpay = Restrictions.like("mid", midDetails.getGpayMid());
			Criterion ezyway = Restrictions.like("mid", midDetails.getEzywayMid());
			Criterion ezywire = Restrictions.like("mid", midDetails.getMid());
			Criterion moto = Restrictions.like("mid", midDetails.getMotoMid());
			Criterion ezypass = Restrictions.like("mid", midDetails.getEzypassMid());
			Criterion ezyrec = Restrictions.like("mid", midDetails.getEzyrecMid());
			Criterion fiuu = Restrictions.like("mid", midDetails.getFiuuMid());

			Criterion isStatusHold = Restrictions.like("status", "H");
			Criterion isStatusSettle = Restrictions.like("status", "S");

			orExpStatus = Restrictions.or(isStatusHold, isStatusSettle, isStatusHold);

			// Criterion cashvoid = Restrictions.like("status", "CV");
			orExp = Restrictions.or(um_ezyway, um_ezywire, um_moto, um_ezypass, um_ezyrec, gpay, ezyway, ezywire, moto,
					ezypass, ezyrec,fiuu);

			try {
				sDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				logger.info("sDate:::::::" + sDate);
				/*
				 * date1= sDate+" "+"17:02:21"; logger.info("date1:::::::"+date1);
				 */

			} catch (ParseException e) {
				e.printStackTrace();
			}

			/*
			 * criterionList.add(Restrictions.in("mid", new String[] {
			 * midDetails.getMid().getUmMid(),midDetails.getMid().getUmMotoMid() ,
			 * midDetails.getMid().getUmEzywayMid(),id}));
			 */
			criterionList.add(orExp);
			criterionList.add(orExpStatus);
			criterionList.add(Restrictions.like("SettlementDate", sDate, MatchMode.START));

			mdrDAO.listSettlementMDRById(paginationBean, criterionList);

		} else if (midDetails != null) {
			logger.info("paginationBean by MID ");

			logger.info("UmMid:::::::" + midDetails.getUmMid());
			logger.info("UmMotoMid:::::::" + midDetails.getUmMotoMid());
			logger.info("UmEzywayMid:::::::" + midDetails.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::" + midDetails.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::" + midDetails.getUmEzypassMid());
			logger.info("GpayMid:::::::" + midDetails.getGpayMid());

			logger.info("Mid:::::::" + midDetails.getMid());
			logger.info("MotoMid:::::::" + midDetails.getMotoMid());
			logger.info("EzywayMid:::::::" + midDetails.getEzywayMid());
			logger.info("EzyrecMid:::::::" + midDetails.getEzyrecMid());
			logger.info("EzypassMid:::::::" + midDetails.getEzypassMid());
			logger.info("FiuuMid:::::::" + midDetails.getFiuuMid());

			Disjunction orExp = null;
			Criterion um_ezyway = Restrictions.like("mid", midDetails.getUmEzywayMid());
			Criterion um_ezywire = Restrictions.like("mid", midDetails.getUmMid());
			Criterion um_moto = Restrictions.like("mid", midDetails.getUmMotoMid());
			Criterion um_ezypass = Restrictions.like("mid", midDetails.getUmEzypassMid());
			Criterion um_ezyrec = Restrictions.like("mid", midDetails.getUmEzyrecMid());
			Criterion gpay = Restrictions.like("mid", midDetails.getGpayMid());
			Criterion ezyway = Restrictions.like("mid", midDetails.getEzywayMid());
			Criterion ezywire = Restrictions.like("mid", midDetails.getMid());
			Criterion moto = Restrictions.like("mid", midDetails.getMotoMid());
			Criterion ezypass = Restrictions.like("mid", midDetails.getEzypassMid());
			Criterion ezyrec = Restrictions.like("mid", midDetails.getEzyrecMid());
			Criterion fiuu = Restrictions.like("mid", midDetails.getFiuuMid());

			Disjunction orExpStatus = null;
			Criterion isStatusHold = Restrictions.like("status", "H");
			Criterion isStatusSettle = Restrictions.like("status", "S");

			orExpStatus = Restrictions.or(isStatusHold, isStatusSettle, isStatusHold);
			criterionList.add(orExpStatus);

			// Criterion cashvoid = Restrictions.like("status", "CV");
			orExp = Restrictions.or(um_ezyway, um_ezywire, um_moto, um_ezypass, um_ezyrec, gpay, ezyway, ezywire, moto,
					ezypass, ezyrec,fiuu);

			/*
			 * criterionList.add(Restrictions.in("mid", new String[] {
			 * midDetails.getMid().getUmMid(),midDetails.getMid().getUmMotoMid() ,
			 * midDetails.getMid().getUmEzywayMid(),id}));
			 */
			criterionList.add(orExp);

			mdrDAO.listSettlementMDRById(paginationBean, criterionList);

		} else if (date != null) {
			logger.info("paginationBean by Date ");

			logger.info("date:::::::" + date);

			try {
				sDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				logger.info("sDate:::::::" + sDate);
				/*
				 * date1= sDate+" "+"17:02:21"; logger.info("date1:::::::"+date1);
				 */

			} catch (ParseException e) {
				e.printStackTrace();
			}

			criterionList.add(Restrictions.like("SettlementDate", sDate, MatchMode.START));
			Disjunction orExpStatus = null;
			Criterion isStatusHold = Restrictions.like("status", "H");
			Criterion isStatusSettle = Restrictions.like("status", "S");

			orExpStatus = Restrictions.or(isStatusHold, isStatusSettle, isStatusHold);
			criterionList.add(orExpStatus);

			mdrDAO.listSettlementMDRById(paginationBean, criterionList);

		} else {
			logger.info("paginationBean : " + paginationBean);
			logger.info("else case:::");

			mdrList = mdrDAO.listAllSettlementMDR();

			logger.info("mdrList:::" + mdrList.size());

			DecimalFormat df = new DecimalFormat("0.00");
			df.setRoundingMode(RoundingMode.HALF_UP);

			for (SettlementMDR s : mdrList) {
				double txa = Double.parseDouble(s.getTxnAmount());
				double mma = Double.parseDouble(s.getMobiMdrAmt());
				double hma = Double.parseDouble(s.getHostMdrAmt());
				double mer = Double.parseDouble(s.getMdrAmt());
				double ded = Double.parseDouble(s.getExtraDeductAmt());
				double nta = Double.parseDouble(s.getNetAmount());
				String txnAmt = df.format(txa);
				String mobiAmt = df.format(mma);
				String hostAmt = df.format(hma);
				String merAmt = df.format(mer);
				String dedAmt = df.format(ded);
				String netAmt = df.format(nta);
				s.setTxnAmount(txnAmt);
				s.setMobiMdrAmt(mobiAmt);
				s.setHostMdrAmt(hostAmt);
				s.setMdrAmt(merAmt);
				s.setExtraDeductAmt(dedAmt);
				s.setNetAmount(netAmt);
			}

			paginationBean.setItemList(mdrList);
		}
	}

	public void listMobiliteAllSettlementMDR(PaginationBean<SettlementMDR> paginationBean, MID midDetails,
			String date) {
		logger.info("listAllSettlementMDR" + midDetails);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		List<SettlementMDR> mdrList = new ArrayList<SettlementMDR>();
		String sDate = null;
//			String date1= null;

		if (midDetails != null && date != "") {
			logger.info("paginationBean by MID , Date ");

			logger.info("UmMid:::::::" + midDetails.getUmMid());
			logger.info("UmMotoMid:::::::" + midDetails.getUmMotoMid());
			logger.info("UmEzywayMid:::::::" + midDetails.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::" + midDetails.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::" + midDetails.getUmEzypassMid());
			logger.info("GpayMid:::::::" + midDetails.getGpayMid());

			logger.info("Mid:::::::" + midDetails.getMid());
			logger.info("MotoMid:::::::" + midDetails.getMotoMid());
			logger.info("EzywayMid:::::::" + midDetails.getEzywayMid());
			logger.info("EzyrecMid:::::::" + midDetails.getEzyrecMid());
			logger.info("EzypassMid:::::::" + midDetails.getEzypassMid());

			logger.info("date:::::::" + date);

			Disjunction orExp = null;
			Disjunction orExpStatus = null;

			Criterion um_ezyway = Restrictions.like("subMid", midDetails.getUmEzywayMid());
			Criterion um_ezywire = Restrictions.like("subMid", midDetails.getUmMid());
			Criterion um_moto = Restrictions.like("subMid", midDetails.getUmMotoMid());
			Criterion um_ezypass = Restrictions.like("subMid", midDetails.getUmEzypassMid());
			Criterion um_ezyrec = Restrictions.like("subMid", midDetails.getUmEzyrecMid());
			Criterion gpay = Restrictions.like("subMid", midDetails.getGpayMid());
			Criterion ezyway = Restrictions.like("subMid", midDetails.getEzywayMid());
			Criterion ezywire = Restrictions.like("subMid", midDetails.getMid());
			Criterion moto = Restrictions.like("subMid", midDetails.getMotoMid());
			Criterion ezypass = Restrictions.like("subMid", midDetails.getEzypassMid());
			Criterion ezyrec = Restrictions.like("subMid", midDetails.getEzyrecMid());

			Criterion isStatusHold = Restrictions.like("status", "L");
			Criterion isStatusSettle = Restrictions.like("status", "SS");

			orExpStatus = Restrictions.or(isStatusHold, isStatusSettle, isStatusHold);

			// Criterion cashvoid = Restrictions.like("status", "CV");
			orExp = Restrictions.or(um_ezyway, um_ezywire, um_moto, um_ezypass, um_ezyrec, gpay, ezyway, ezywire, moto,
					ezypass, ezyrec);

			try {
				sDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				logger.info("sDate:::::::" + sDate);
				/*
				 * date1= sDate+" "+"17:02:21"; logger.info("date1:::::::"+date1);
				 */

			} catch (ParseException e) {
				e.printStackTrace();
			}

			/*
			 * criterionList.add(Restrictions.in("mid", new String[] {
			 * midDetails.getMid().getUmMid(),midDetails.getMid().getUmMotoMid() ,
			 * midDetails.getMid().getUmEzywayMid(),id}));
			 */
			criterionList.add(orExp);
			criterionList.add(orExpStatus);
			criterionList.add(Restrictions.like("subSettleDate", sDate, MatchMode.START));

			mdrDAO.listSettlementMDRById(paginationBean, criterionList);

		} else if (midDetails != null) {
			logger.info("paginationBean by MID ");

			logger.info("UmMid:::::::" + midDetails.getUmMid());
			logger.info("UmMotoMid:::::::" + midDetails.getUmMotoMid());
			logger.info("UmEzywayMid:::::::" + midDetails.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::" + midDetails.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::" + midDetails.getUmEzypassMid());
			logger.info("GpayMid:::::::" + midDetails.getGpayMid());

			logger.info("Mid:::::::" + midDetails.getMid());
			logger.info("MotoMid:::::::" + midDetails.getMotoMid());
			logger.info("EzywayMid:::::::" + midDetails.getEzywayMid());
			logger.info("EzyrecMid:::::::" + midDetails.getEzyrecMid());
			logger.info("EzypassMid:::::::" + midDetails.getEzypassMid());

			Disjunction orExp = null;
			Criterion um_ezyway = Restrictions.like("subMid", midDetails.getUmEzywayMid());
			Criterion um_ezywire = Restrictions.like("subMid", midDetails.getUmMid());
			Criterion um_moto = Restrictions.like("subMid", midDetails.getUmMotoMid());
			Criterion um_ezypass = Restrictions.like("subMid", midDetails.getUmEzypassMid());
			Criterion um_ezyrec = Restrictions.like("subMid", midDetails.getUmEzyrecMid());
			Criterion gpay = Restrictions.like("subMid", midDetails.getGpayMid());
			Criterion ezyway = Restrictions.like("subMid", midDetails.getEzywayMid());
			Criterion ezywire = Restrictions.like("subMid", midDetails.getMid());
			Criterion moto = Restrictions.like("subMid", midDetails.getMotoMid());
			Criterion ezypass = Restrictions.like("subMid", midDetails.getEzypassMid());
			Criterion ezyrec = Restrictions.like("subMid", midDetails.getEzyrecMid());

			Disjunction orExpStatus = null;
			Criterion isStatusHold = Restrictions.like("status", "L");
			Criterion isStatusSettle = Restrictions.like("status", "SS");

			orExpStatus = Restrictions.or(isStatusHold, isStatusSettle, isStatusHold);
			criterionList.add(orExpStatus);

			// Criterion cashvoid = Restrictions.like("status", "CV");
			orExp = Restrictions.or(um_ezyway, um_ezywire, um_moto, um_ezypass, um_ezyrec, gpay, ezyway, ezywire, moto,
					ezypass, ezyrec);

			/*
			 * criterionList.add(Restrictions.in("mid", new String[] {
			 * midDetails.getMid().getUmMid(),midDetails.getMid().getUmMotoMid() ,
			 * midDetails.getMid().getUmEzywayMid(),id}));
			 */
			criterionList.add(orExp);

			mdrDAO.listSettlementMDRById(paginationBean, criterionList);

		} else if (date != null) {
			logger.info("paginationBean by Date ");

			logger.info("date:::::::" + date);

			try {
				sDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				logger.info("sDate:::::::" + sDate);
				/*
				 * date1= sDate+" "+"17:02:21"; logger.info("date1:::::::"+date1);
				 */

			} catch (ParseException e) {
				e.printStackTrace();
			}

			criterionList.add(Restrictions.like("subSettleDate", sDate, MatchMode.START));
			Disjunction orExpStatus = null;
			Criterion isStatusHold = Restrictions.like("status", "L");
			Criterion isStatusSettle = Restrictions.like("status", "SS");

			orExpStatus = Restrictions.or(isStatusHold, isStatusSettle, isStatusHold);
			criterionList.add(orExpStatus);

			mdrDAO.listSettlementMDRById(paginationBean, criterionList);

		} else {
			logger.info("paginationBean : " + paginationBean);
			logger.info("else case:::");

			mdrList = mdrDAO.listAllMobiliteSettlementMDR();

			logger.info("mdrList:::" + mdrList);

			DecimalFormat df = new DecimalFormat("0.00");
			df.setRoundingMode(RoundingMode.HALF_UP);

			for (SettlementMDR s : mdrList) {
				double txa = Double.parseDouble(s.getTxnAmount());
				double mma = Double.parseDouble(s.getSubMobiMdrAmt());
				double hma = Double.parseDouble(s.getSubHostMdrAmt());
				double mer = Double.parseDouble(s.getSubMdrAmt());
				double ded = Double.parseDouble(s.getExtraDeductAmt());
				double nta = Double.parseDouble(s.getSubNetAmount());
				String txnAmt = df.format(txa);
				String mobiAmt = df.format(mma);
				String hostAmt = df.format(hma);
				String merAmt = df.format(mer);
				String dedAmt = df.format(ded);
				String netAmt = df.format(nta);
				s.setTxnAmount(txnAmt);
				s.setSubMobiMdrAmt(mobiAmt);
				s.setSubHostMdrAmt(hostAmt);
				s.setSubMdrAmt(merAmt);
				s.setExtraDeductAmt(dedAmt);
				s.setSubNetAmount(netAmt);
			}

			paginationBean.setItemList(mdrList);
		}
	}

	public MobiProductMDR addProMDR(MobiProductMDR entity) {

		logger.info("addProMdr Service");
		mdrDAO.saveOrUpdateEntity(entity);
		logger.info("Add Product MDR Details in database");
		return entity;

	}

	public void listproMDRByMid(PaginationBean<MobiProductMDR> paginationBean, Merchant midDetails, Long id) {

		logger.info("listproMDRByMid" + id);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		List<MobiProductMDR> mdrList = new ArrayList<MobiProductMDR>();
//			if(midDetails!=null) {
		logger.info("paginationBean by MID: " + paginationBean);

		logger.info("UmMid:::::::" + midDetails.getMid().getUmMid());
		logger.info("UmMotoMid:::::::" + midDetails.getMid().getUmMotoMid());
		logger.info("UmEzywayMid:::::::" + midDetails.getMid().getUmEzywayMid());
		logger.info("UmEzyrecMid:::::::" + midDetails.getMid().getUmEzyrecMid());
		logger.info("UmEzypassMid:::::::" + midDetails.getMid().getUmEzypassMid());
		logger.info("GpayMid:::::::" + midDetails.getMid().getGpayMid());
		logger.info("id:::::::" + id);

		criterionList.add(Restrictions.in("mid", new String[] { midDetails.getMid().getUmMid(),
				midDetails.getMid().getUmMotoMid(), midDetails.getMid().getUmEzywayMid(), String.valueOf(id) }));

		mdrDAO.listproMDRByMid(paginationBean, criterionList);
//			paginationBean.setItemList(mdrList);

//			}

	}

	public MobiProductMDR loadProMDR(String mid, String prodType) {
		return mdrDAO.loadProMDR(mid, prodType);
	}

	public MobiProductMDR loadProMDRbyId(Long id) {
		return mdrDAO.loadProMDR(String.valueOf(id));
	}

	public MobiProductMDR updateProMDR(MobiProductMDR mDRDetailsSavedInHttpSession) {
		logger.info("updateProMDR Service");
		mdrDAO.saveOrUpdateEntity(mDRDetailsSavedInHttpSession);
		logger.info("Update Product MDR Details in database");
		return mDRDetailsSavedInHttpSession;
	}

	public void exportSettlementMDRByAdmin(PaginationBean<SettlementMDR> paginationBean, MID mid, String date) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		mdrDAO.exportSettlementMDRByAdmin(paginationBean, criterionList, mid, date);
	}

	public void exportFpxSettlementMDRByAdmin(PaginationBean<FpxTransaction> paginationBean, MID mid, String date) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		mdrDAO.exportFpxSettlementMDRByAdmin(paginationBean, criterionList, mid, date);
	}

	public void exportMobiliteSettlementMDRByAdmin(PaginationBean<SettlementMDR> paginationBean, MID mid, String date) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		mdrDAO.exportMobiliteSettlementMDRByAdmin(paginationBean, criterionList, mid, date);
	}

	public void exportbizappSettlementMDRByAdmin(PaginationBean<BizAppSettlement> paginationBean, String date) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		mdrDAO.exportbizappSettlementMDRByAdmin(paginationBean, criterionList, date);
	}

	public void listChargeBackByMid(PaginationBean<MobiProductMDR> paginationBean, MID midDetails, String date) {
		logger.info("listChargeBackByMid" + midDetails);
		logger.info("date" + date);
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		String sDate = null;
		if (midDetails != null && date != null) {
			logger.info("paginationBean by MID , Date ");

			logger.info("UmMid:::::::" + midDetails.getUmMid());
			logger.info("UmMotoMid:::::::" + midDetails.getUmMotoMid());
			logger.info("UmEzywayMid:::::::" + midDetails.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::" + midDetails.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::" + midDetails.getUmEzypassMid());
			logger.info("GpayMid:::::::" + midDetails.getGpayMid());

			logger.info("Mid:::::::" + midDetails.getMid());
			logger.info("MotoMid:::::::" + midDetails.getMotoMid());
			logger.info("EzywayMid:::::::" + midDetails.getEzywayMid());
			logger.info("EzyrecMid:::::::" + midDetails.getEzyrecMid());
			logger.info("EzypassMid:::::::" + midDetails.getEzypassMid());

			logger.info("date:::::::" + date);

			Disjunction orExp = null;
			Criterion um_ezyway = Restrictions.like("mid", midDetails.getUmEzywayMid());
			Criterion um_ezywire = Restrictions.like("mid", midDetails.getUmMid());
			Criterion um_moto = Restrictions.like("mid", midDetails.getUmMotoMid());
			Criterion um_ezypass = Restrictions.like("mid", midDetails.getUmEzypassMid());
			Criterion um_ezyrec = Restrictions.like("mid", midDetails.getUmEzyrecMid());
			Criterion gpay = Restrictions.like("mid", midDetails.getGpayMid());
			Criterion ezyway = Restrictions.like("mid", midDetails.getEzywayMid());
			Criterion ezywire = Restrictions.like("mid", midDetails.getMid());
			Criterion moto = Restrictions.like("mid", midDetails.getMotoMid());
			Criterion ezypass = Restrictions.like("mid", midDetails.getEzypassMid());
			Criterion ezyrec = Restrictions.like("mid", midDetails.getEzyrecMid());

			orExp = Restrictions.or(um_ezyway, um_ezywire, um_moto, um_ezypass, um_ezyrec, gpay, ezyway, ezywire, moto,
					ezypass, ezyrec);

			try {
				sDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				logger.info("sDate:::::::" + sDate);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			criterionList.add(orExp);
//			criterionList.add(Restrictions.eq("status", CommonStatus.ACTIVE));
			criterionList.add(Restrictions.eq("prodType", "CH_BACK"));
			criterionList.add(Restrictions.like("timeStamp", sDate, MatchMode.START));
			mdrDAO.listChargeBackByMid(paginationBean, criterionList);

		} else if (midDetails != null) {
			logger.info("paginationBean by MID ");

			logger.info("UmMid:::::::" + midDetails.getUmMid());
			logger.info("UmMotoMid:::::::" + midDetails.getUmMotoMid());
			logger.info("UmEzywayMid:::::::" + midDetails.getUmEzywayMid());
			logger.info("UmEzyrecMid:::::::" + midDetails.getUmEzyrecMid());
			logger.info("UmEzypassMid:::::::" + midDetails.getUmEzypassMid());
			logger.info("GpayMid:::::::" + midDetails.getGpayMid());

			logger.info("Mid:::::::" + midDetails.getMid());
			logger.info("MotoMid:::::::" + midDetails.getMotoMid());
			logger.info("EzywayMid:::::::" + midDetails.getEzywayMid());
			logger.info("EzyrecMid:::::::" + midDetails.getEzyrecMid());
			logger.info("EzypassMid:::::::" + midDetails.getEzypassMid());

			Disjunction orExp = null;
			Criterion um_ezyway = Restrictions.like("mid", midDetails.getUmEzywayMid());
			Criterion um_ezywire = Restrictions.like("mid", midDetails.getUmMid());
			Criterion um_moto = Restrictions.like("mid", midDetails.getUmMotoMid());
			Criterion um_ezypass = Restrictions.like("mid", midDetails.getUmEzypassMid());
			Criterion um_ezyrec = Restrictions.like("mid", midDetails.getUmEzyrecMid());
			Criterion gpay = Restrictions.like("mid", midDetails.getGpayMid());
			Criterion ezyway = Restrictions.like("mid", midDetails.getEzywayMid());
			Criterion ezywire = Restrictions.like("mid", midDetails.getMid());
			Criterion moto = Restrictions.like("mid", midDetails.getMotoMid());
			Criterion ezypass = Restrictions.like("mid", midDetails.getEzypassMid());
			Criterion ezyrec = Restrictions.like("mid", midDetails.getEzyrecMid());

			orExp = Restrictions.or(um_ezyway, um_ezywire, um_moto, um_ezypass, um_ezyrec, gpay, ezyway, ezywire, moto,
					ezypass, ezyrec);

			criterionList.add(orExp);
//				criterionList.add(Restrictions.eq("status", CommonStatus.ACTIVE));
			criterionList.add(Restrictions.eq("prodType", "CH_BACK"));

			mdrDAO.listChargeBackByMid(paginationBean, criterionList);

		} else if (date != null) {
			logger.info("paginationBean by Date ");

			logger.info("date:::::::" + date);

			try {
				sDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				logger.info("sDate:::::::" + sDate);
				/*
				 * date1= sDate+" "+"17:02:21"; logger.info("date1:::::::"+date1);
				 */

			} catch (ParseException e) {
				e.printStackTrace();
			}

//				criterionList.add(Restrictions.eq("status", CommonStatus.ACTIVE));
			criterionList.add(Restrictions.eq("prodType", "CH_BACK"));
			criterionList.add(Restrictions.like("timeStamp", sDate, MatchMode.START));
			mdrDAO.listChargeBackByMid(paginationBean, criterionList);

		}

	}

	public void listChargeBack(PaginationBean<RegMobileUser> paginationBean, Object object, Object object2) {
		List<RegMobileUser> chargeBackList = new ArrayList<RegMobileUser>();
		logger.info("paginationBean : " + paginationBean);
		mdrDAO.listAllChargeBack(paginationBean, null, null);

	}

	public ResponseDetails sendMDRDetails(MobiMDR regMerchantMDR, String serviceName) {

		logger.info("service URL: " + getMDRFilePath());
		logger.info("MID: " + regMerchantMDR.getMid() + " CardBrand: " + regMerchantMDR.getCardBrand());

		ResponseDetails rd = null;
		URL object = null;
		String inputLine = null;
		String output = null;
		Gson gson = new Gson();
		try {
			JSONObject mdrObj = new JSONObject();

			mdrObj.put("service", serviceName);
			mdrObj.put("mid", regMerchantMDR.getMid());
			mdrObj.put("cardBrand", regMerchantMDR.getCardBrand());
			mdrObj.put("debitForeignMerchantMDR", regMerchantMDR.getDebitForeignMerchantMDR());
			mdrObj.put("creditLocalMerchantMDR", regMerchantMDR.getCreditLocalMerchantMDR());
			mdrObj.put("debitLocalMerchantMDR", regMerchantMDR.getDebitLocalMerchantMDR());
			mdrObj.put("debitForeignHostMDR", regMerchantMDR.getDebitForeignHostMDR());
			mdrObj.put("creditLocalHostMDR", regMerchantMDR.getCreditLocalHostMDR());
			mdrObj.put("debitLocalHostMDR", regMerchantMDR.getDebitLocalHostMDR());
			mdrObj.put("creditForeignHostMDR", regMerchantMDR.getCreditForeignHostMDR());
			mdrObj.put("creditForeignMerchantMDR", regMerchantMDR.getCreditForeignMerchantMDR());

			logger.info("Request JSON: " + mdrObj.toString());
			object = new URL(getMDRFilePath());

			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(mdrObj.toString());

			wr.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output response .... " + output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return rd;
	}

	public static String getMDRFilePath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("MDR_SERVICE_URL"));
			path = prop.getProperty("MDR_SERVICE_URL");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;
	}

	public static String getFileRegenrate() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("File_Regenerate"));
			path = prop.getProperty("File_Regenerate");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;
	}

	public List<SettlementMDR> listMIDByDate(String date) {
		logger.info("date:::::::" + date);
		List<SettlementMDR> mdrList = mdrDAO.listMIDByDate(date);
		return mdrList;
	}

	public void listBizappSettlementMDR(PaginationBean<BizAppSettlement> paginationBean, String date) {
		logger.info("listBizappSettlementMDR");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		List<BizAppSettlement> mdrList = new ArrayList<BizAppSettlement>();
		String sDate = null;
//			String date1= null;

		if (date != null) {
			logger.info("paginationBean by Date ");

			logger.info("date:::::::" + date);

			try {
				sDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				logger.info("sDate:::::::" + sDate);

			} catch (ParseException e) {
				e.printStackTrace();
			}

			criterionList.add(Restrictions.like("settlementDate", sDate, MatchMode.START));
			Disjunction orExpStatus = null;
			Criterion isStatusHold = Restrictions.like("status", "S");
			Criterion isStatusSettle = Restrictions.like("status", "S");

			orExpStatus = Restrictions.or(isStatusHold, isStatusSettle, isStatusHold);
			criterionList.add(orExpStatus);

			// mdrDAO.listSettlementMDRById(paginationBean,criterionList);

			mdrDAO.listBizappSettlementMDRById(paginationBean, criterionList);

		} else {
			logger.info("paginationBean : " + paginationBean);
			logger.info("else case:::");

			// mdrList= mdrDAO.listAllSettlementMDR();

			mdrList = mdrDAO.listBizAppSettlementMDR();

			logger.info("mdrList:::" + mdrList);

			DecimalFormat df = new DecimalFormat("0.00");
			df.setRoundingMode(RoundingMode.HALF_UP);

			for (BizAppSettlement s : mdrList) {
				double txa = Double.parseDouble(s.getGrossAmt());// grossamt
				double mma = Double.parseDouble(s.getMobiMdrAmt());
				double hma = Double.parseDouble(s.getHostMdrAmt());
				double mer = Double.parseDouble(s.getMdrAmt());
				double ded = Double.parseDouble(s.getDetectionAmt());
				double nta = Double.parseDouble(s.getNetAmt());
				String txnAmt = df.format(txa);
				String mobiAmt = df.format(mma);
				String hostAmt = df.format(hma);
				String merAmt = df.format(mer);
				String dedAmt = df.format(ded);
				String netAmt = df.format(nta);
				s.setGrossAmt(txnAmt);
				s.setMobiMdrAmt(mobiAmt);
				s.setHostMdrAmt(hostAmt);
				s.setMdrAmt(merAmt);
				s.setDetectionAmt(dedAmt);
				s.setNetAmt(netAmt);
			}

			paginationBean.setItemList(mdrList);
		}
	}

	public void listFpxSettlementMDR(PaginationBean<FpxTransaction> paginationBean, String date) {
		String from = null;

		if ((date == null) || (date.equals(""))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int day = date1.getDate();
			String dateorg2 = day + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("toDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from);

		mdrDAO.listAllFpxSettlementMDR(paginationBean, from);

	}

	public void listFpxSettlementMDRByMid(PaginationBean<FpxTransaction> paginationBean, Merchant merchant,
			String date) {
		String from = null;

		if ((date == null) || (date.equals(""))) {

			Date date1 = new Date();
			Calendar calendar = Calendar.getInstance();
			date1 = calendar.getTime();
			int year = calendar.getWeekYear();
			long mon = date1.getMonth() + 1;
			int day = date1.getDate();
			String dateorg2 = day + "/" + mon + "/" + year;

			try {
				from = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
				System.out.println("toDate   " + from);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			logger.info("change date format:" + from);

		} else {

			from = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				from = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(from));
				logger.info("date format:" + date);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		logger.info("checkd date: " + from);

		mdrDAO.listFpxSettlementMDRByMid(paginationBean, merchant, from);

	}

	/*
	 * public void listMDRDetailsByRateId(PaginationBean<MdrRates> paginationBean,
	 * MID mi) {
	 * 
	 * logger.info("listMDRDetailsByMid" +mi); List<MdrRates> mdrList = new
	 * ArrayList<MdrRates>();
	 * 
	 * StringBuffer str = new StringBuffer();
	 * 
	 * List<String> midList = new ArrayList<String>();
	 * 
	 * 
	 * 
	 * if(mi.getMid() != null) { if(!mi.getMid().isEmpty()) { str.append("\"");
	 * str.append(mi.getMid()); str.append("\","); midList.add(mi.getMid());
	 * 
	 * }
	 * 
	 * } if(mi.getMotoMid() != null) { if(!mi.getMotoMid().isEmpty()) {
	 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
	 * midList.add(mi.getMotoMid()); }
	 * 
	 * } if(mi.getEzypassMid() != null) { if(!mi.getEzypassMid().isEmpty()) {
	 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
	 * midList.add(mi.getEzypassMid()); }
	 * 
	 * }
	 * 
	 * if(mi.getEzywayMid() != null) { if(!mi.getEzywayMid().isEmpty()) {
	 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
	 * midList.add(mi.getEzywayMid()); }
	 * 
	 * }
	 * 
	 * if(mi.getEzyrecMid() != null) { if(!mi.getEzyrecMid().isEmpty()) {
	 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
	 * midList.add(mi.getEzyrecMid()); }
	 * 
	 * }
	 * 
	 * if(mi.getUmMid() != null) { if(!mi.getUmMid().isEmpty()) { str.append("\"");
	 * str.append(mi.getMotoMid()); str.append("\","); midList.add(mi.getUmMid()); }
	 * 
	 * }
	 * 
	 * if(mi.getUmMotoMid() != null) { if(!mi.getUmMotoMid().isEmpty()) {
	 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
	 * midList.add(mi.getUmMotoMid()); }
	 * 
	 * } if(mi.getUmEzywayMid() != null) { if(!mi.getUmEzywayMid().isEmpty()) {
	 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
	 * midList.add(mi.getUmEzywayMid()); }
	 * 
	 * }
	 * 
	 * 
	 * int u =0; for(String strMid : midList) {
	 * 
	 * if(u == 0) { str.append("\""); str.append(strMid); str.append("\""); u++;
	 * }else { str.append(",\""); str.append(strMid); str.append("\""); } }
	 * logger.info("String of MIDs:  "+str);
	 * 
	 * mdrList= mdrDAO.getMDRDetailsByRateId(str);
	 * 
	 * paginationBean.setItemList(mdrList);
	 * 
	 * 
	 * }
	 */

	public ResponseDetails sendSettlementMDRDetails(UMFileGenerateDetails umdet, String serviceName) {

		logger.info("service URL: " + getFileRegenrate());

		ResponseDetails rd = null;
		URL object = null;
		String inputLine = null;
		String output = null;
		Gson gson = new Gson();
		try {
			JSONObject mdrObj = new JSONObject();

			mdrObj.put("service", serviceName);
			mdrObj.put("merchantFile", umdet.getMercFile());
			mdrObj.put("mdrFile", umdet.getMdrFile());
			mdrObj.put("deductionFile", umdet.getDedFile());
			mdrObj.put("csvFile", umdet.getCsvFile());
			mdrObj.put("settlePeriod", umdet.getStPeriod());
			mdrObj.put("date", umdet.getFrom());

			logger.info("Request JSON: " + mdrObj.toString());
			object = new URL(getFileRegenrate());

			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(mdrObj.toString());

			wr.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output response .... " + output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return rd;
	}

	// Dhinesh Boost , Grabpay and Fpx File Generate Start - 18-03-2022

	// Boost - Service

	public ResponseDetails sendBoostRequestDetails(UMFileGenerateDetails umdet, String serviceName) {

		logger.info("service URL: " + getBoostFileRegenrate());

		ResponseDetails rd = null;
		URL object = null;
		String inputLine = null;
		String output = null;
		Gson gson = new Gson();
		try {
			JSONObject mdrObj = new JSONObject();

			mdrObj.put("service", serviceName);
			mdrObj.put("merchantFile", umdet.getMercFile());
			mdrObj.put("csvFile", umdet.getCsvFile());
			mdrObj.put("date", umdet.getFrom());

			logger.info("Request JSON: " + mdrObj.toString());
			object = new URL(getBoostFileRegenrate());

			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(mdrObj.toString());

			wr.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output response .... " + output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return rd;
	}

	// Grabpay Service

	public ResponseDetails sendGrabpayRequestDetails(UMFileGenerateDetails umdet, String serviceName) {

		logger.info("service URL: " + getGrabpayFileRegenrate());

		ResponseDetails rd = null;
		URL object = null;
		String inputLine = null;
		String output = null;
		Gson gson = new Gson();
		try {
			JSONObject mdrObj = new JSONObject();

			mdrObj.put("service", serviceName);
			mdrObj.put("merchantFile", umdet.getMercFile());
			mdrObj.put("csvFile", umdet.getCsvFile());
			mdrObj.put("date", umdet.getFrom());

			logger.info("Request JSON: " + mdrObj.toString());
			object = new URL(getGrabpayFileRegenrate());

			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(mdrObj.toString());

			wr.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output response .... " + output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return rd;
	}

	// Fpx Service

	public ResponseDetails sendFpxRequestDetails(UMFileGenerateDetails umdet, String serviceName) {

		logger.info("service URL: " + getFpxFileRegenrate());

		ResponseDetails rd = null;
		URL object = null;
		String inputLine = null;
		String output = null;
		Gson gson = new Gson();
		try {
			JSONObject mdrObj = new JSONObject();

			mdrObj.put("service", serviceName);
			mdrObj.put("merchantFile", umdet.getMercFile());
			mdrObj.put("csvFile", umdet.getCsvFile());
			mdrObj.put("date", umdet.getFrom());

			logger.info("Request JSON: " + mdrObj.toString());
			object = new URL(getFpxFileRegenrate());

			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");

			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(mdrObj.toString());

			wr.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output response .... " + output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return rd;
	}

	public static String getBoostFileRegenrate() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Boost URL :" + prop.getProperty("Boost_File_Regenerate"));
			path = prop.getProperty("Boost_File_Regenerate");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;
	}

	public static String getGrabpayFileRegenrate() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Grabpay URL :" + prop.getProperty("Grabpay_File_Regenerate"));
			path = prop.getProperty("Grabpay_File_Regenerate");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;
	}

	public static String getFpxFileRegenrate() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Fpx URL :" + prop.getProperty("Fpx_File_Regenerate"));
			path = prop.getProperty("Fpx_File_Regenerate");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;
	}

	// Dhinesh Boost , Grabpay and Fpx File Generate End - 18-03-2022

	public void listMDRDetailsByRateId(PaginationBean<MobiMDR> paginationBean, MID mi)
//public  void listMDRDetailsByRateId(PaginationBean<MdrRates> paginationBean, MID mi) 
	{
		logger.info("listMDRDetailsByMid" + mi);
		// List<MdrRates> mdrList = new ArrayList<MdrRates>();
		List<MobiMDR> mdrList = new ArrayList<MobiMDR>();

		StringBuffer str = new StringBuffer();

		List<String> midList = new ArrayList<String>();

		if (mi.getMid() != null) {
			if (!mi.getMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMid()); str.append("\",");
				 */
				midList.add(mi.getMid());

			}

		}
		if (mi.getMotoMid() != null) {
			if (!mi.getMotoMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
				 */
				midList.add(mi.getMotoMid());
			}

		}
		if (mi.getEzypassMid() != null) {
			if (!mi.getEzypassMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
				 */
				midList.add(mi.getEzypassMid());
			}

		}

		if (mi.getEzywayMid() != null) {
			if (!mi.getEzywayMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
				 */
				midList.add(mi.getEzywayMid());
			}

		}

		if (mi.getEzyrecMid() != null) {
			if (!mi.getEzyrecMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
				 */
				midList.add(mi.getEzyrecMid());
			}

		}

		if (mi.getUmMid() != null) {
			if (!mi.getUmMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
				 */
				midList.add(mi.getUmMid());
			}

		}

		if (mi.getUmMotoMid() != null) {
			if (!mi.getUmMotoMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
				 */
				midList.add(mi.getUmMotoMid());
			}

		}
		if (mi.getUmEzywayMid() != null) {
			if (!mi.getUmEzywayMid().isEmpty()) {
				/*
				 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
				 */
				midList.add(mi.getUmEzywayMid());
			}

		}
		
		//fiuu MID 
		if (mi.getFiuuMid() != null) {
			if (!mi.getFiuuMid().isEmpty()) {
				midList.add(mi.getFiuuMid());
			}

		}
		

		int u = 0;
		for (String strMid : midList) {

			if (u == 0) {
				str.append("\"");
				str.append(strMid);
				str.append("\"");
				u++;
			} else {
				str.append(",\"");
				str.append(strMid);
				str.append("\"");
			}
		}
		logger.info("String of MIDs:  " + str);

		mdrList = mdrDAO.getMDRDetailsByRateId(str);

		paginationBean.setItemList(mdrList);

	}

}
