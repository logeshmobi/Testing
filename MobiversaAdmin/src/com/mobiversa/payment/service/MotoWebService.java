package com.mobiversa.payment.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.controller.MerchantMotoTransactionController;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.dto.PreAuthTxnDet;
import com.mobiversa.payment.util.ResponseDetails;

@Service
public class MotoWebService {
	private static final Logger logger = Logger.getLogger(MerchantMotoTransactionController.class);

	public static ResponseDetails motoRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.motosubmitTrans(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}
	
	// rk added
	public static ResponseDetails paydeemotopaymentRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.paydeemotopayment(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}
	
	
	public static ResponseDetails paydeeauthviamotopaymentRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.paydeeauthviamotopayment(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}
	
	public static ResponseDetails umobileauthviamotopaymentRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.umobileauthviamotopayment(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}
	
	
	public static ResponseDetails umobilemotopaymentRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.umobilemotopayment(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}
	//rk end
	
	
	

	public static ResponseDetails preAuthRequest(PreAuthTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.preAuthsubmitTrans(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}

	public static ResponseDetails authDDRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.authDDRequest(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}
	
	
	
	//fiuu changes
	
	//ezymoto request
	
	public static ResponseDetails fiuuMotoPaymentRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.fiuuMotoPayment(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); 
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); 
		}
		return rd;
	}
	
	public static ResponseDetails fiuuAuthViaMotoPaymentRequest(MotoTxnDet fs) throws IllegalStateException, IOException {
		ResponseDetails rd = MotoPaymentCommunication.fiuuAuthViaMotoPayment(fs);
		logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		if (rd != null) {
			logger.info("Response Data.." + rd.getResponseMessage()); //$NON-NLS-1$
		}
		return rd;
	}

}
