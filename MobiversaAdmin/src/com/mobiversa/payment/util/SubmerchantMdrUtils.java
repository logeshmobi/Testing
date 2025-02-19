package com.mobiversa.payment.util;

import org.apache.log4j.Logger;

import com.mobiversa.payment.controller.bean.MDRDetailsBean;
import com.mobiversa.payment.controller.bean.RestOfMdrRatesBean;

public class SubmerchantMdrUtils {

	private static final Logger logger = Logger.getLogger(SubmerchantMdrUtils.class);

	public RestOfMdrRatesBean submerchantRestOfMdrRates(String cardBrand, MDRDetailsBean mdrDetails) {

		RestOfMdrRatesBean restOfMdr = new RestOfMdrRatesBean();
		
		Float foriegncreditMerchmdr = 0.0f;
		Float foriegndebitMerchmdr = 0.0f;
		Float localcreditMerchmdr = 0.0f;
		Float localdebitMerchmdr = 0.0f;

		Float foriegncreditHostmdr = 0.0f;
		Float foriegndebitHostmdr = 0.0f;
		Float localcreditHostmdr = 0.0f;
		Float localdebitHostmdr = 0.0f;

		Float foriegncreditmobimdr = 0.0f;
		Float foriegndebitmobimdr = 0.0f;
		Float localcreditmobimdr = 0.0f;
		Float localdebitmobimdr = 0.0f;

		Float boostHostMdr = 0.0f;
		Float boostMobiMdr = 0.0f;
		Float boostMercMdr = 0.0f;
		String minimumMdr = "0.0";

		Float grabHostMdr = 0.0f;
		Float grabMobiMdr = 0.0f;
		Float grabMercMdr = 0.0f;

		Float tngHostMdr = 0.0f;
		Float tngMobiMdr = 0.0f;
		Float tngMercMdr = 0.0f;

		Float sppHostMdr = 0.0f;
		Float sppMobiMdr = 0.0f;
		Float sppMercMdr = 0.0f;

		Float fpxHostMdr = 0.0f;
		Float fpxMobiMdr = 0.0f;
		Float fpxMercMdr = 0.0f;

		Utils util = new Utils();
		
		switch (cardBrand.toUpperCase()) {
		case "VISA":
			
			foriegncreditMerchmdr = util.stringTofloat(mdrDetails.getCards().getVisa().getForiegncreditmdr());
			foriegndebitMerchmdr = util.stringTofloat(mdrDetails.getCards().getVisa().getForiegndebitmdr());
			localcreditMerchmdr = util.stringTofloat(mdrDetails.getCards().getVisa().getLocalcreditmdr());
			localdebitMerchmdr = util.stringTofloat(mdrDetails.getCards().getVisa().getLocaldebitmdr());

			foriegncreditHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("VISA_FOREIGN_CREDIT_HOST_MDR"));
			foriegndebitHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("VISA_FOREIGN_DEBIT_HOST_MDR"));
			localcreditHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("VISA_LOCAL_CREDIT_HOST_MDR"));
			localdebitHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("VISA_LOCAL_DEBIT_HOST_MDR"));

			foriegncreditmobimdr = foriegncreditMerchmdr - foriegncreditHostmdr;
			foriegndebitmobimdr = foriegndebitMerchmdr - foriegndebitHostmdr;
			localcreditmobimdr = localcreditMerchmdr - localcreditHostmdr;
			localdebitmobimdr = localdebitMerchmdr - localdebitHostmdr;

			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
			
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
			
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);
			restOfMdr.setMinimumMdr("0.0");
			break;
		case "MASTERCARD":

			foriegncreditMerchmdr = util.stringTofloat(mdrDetails.getCards().getMaster().getForiegncreditmdr());
			foriegndebitMerchmdr = util.stringTofloat(mdrDetails.getCards().getMaster().getForiegndebitmdr());
			localcreditMerchmdr = util.stringTofloat(mdrDetails.getCards().getMaster().getLocalcreditmdr());
			localdebitMerchmdr = util.stringTofloat(mdrDetails.getCards().getMaster().getLocaldebitmdr());

			foriegncreditHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("MASTER_FOREIGN_CREDIT_HOST_MDR"));
			foriegndebitHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("MASTER_FOREIGN_DEBIT_HOST_MDR"));
			localcreditHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("MASTER_LOCAL_CREDIT_HOST_MDR"));
			localdebitHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("MASTER_LOCAL_DEBIT_HOST_MDR"));

			foriegncreditmobimdr = foriegncreditMerchmdr - foriegncreditHostmdr;
			foriegndebitmobimdr = foriegndebitMerchmdr - foriegndebitHostmdr;
			localcreditmobimdr = localcreditMerchmdr - localcreditHostmdr;
			localdebitmobimdr = localdebitMerchmdr - localdebitHostmdr;

			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);

			restOfMdr.setMinimumMdr("0.0");
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
			
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
			
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);
			
			break;
		case "UNIONPAY":

			foriegncreditMerchmdr = util.stringTofloat(mdrDetails.getCards().getUnion().getForiegncreditmdr());
			foriegndebitMerchmdr = util.stringTofloat(mdrDetails.getCards().getUnion().getForiegndebitmdr());
			localcreditMerchmdr = util.stringTofloat(mdrDetails.getCards().getUnion().getLocalcreditmdr());
			localdebitMerchmdr = util.stringTofloat(mdrDetails.getCards().getUnion().getLocaldebitmdr());

			foriegncreditHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("UNION_FOREIGN_CREDIT_HOST_MDR"));
			foriegndebitHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("UNION_FOREIGN_DEBIT_HOST_MDR"));
			localcreditHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("UNION_LOCAL_CREDIT_HOST_MDR"));
			localdebitHostmdr = Float
					.parseFloat(PropertyLoader.getFileData().getProperty("UNION_LOCAL_DEBIT_HOST_MDR"));

			foriegncreditmobimdr = foriegncreditMerchmdr - foriegncreditHostmdr;
			foriegndebitmobimdr = foriegndebitMerchmdr - foriegndebitHostmdr;
			localcreditmobimdr = localcreditMerchmdr - localcreditHostmdr;
			localdebitmobimdr = localdebitMerchmdr - localdebitHostmdr;
			
			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
			restOfMdr.setMinimumMdr(minimumMdr);
			
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
			
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);
			
			break;
		case "BOOST":
			
			boostHostMdr = util.stringTofloat(mdrDetails.getEwallet().getBoost().getHostmdr());
			boostMercMdr = util.stringTofloat(mdrDetails.getEwallet().getBoost().getMerchantmdr());
			boostMobiMdr = util.stringTofloat(mdrDetails.getEwallet().getBoost().getMobimdr());
			minimumMdr = mdrDetails.getEwallet().getBoost().getMinimummdr().equals("") ? "0.0"
					: mdrDetails.getEwallet().getBoost().getMinimummdr();

			
			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
			restOfMdr.setMinimumMdr(minimumMdr);
			
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
			
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);

			break;
		case "GRAB":
			grabHostMdr = util.stringTofloat(mdrDetails.getEwallet().getGrab().getHostmdr());
			grabMercMdr = util.stringTofloat(mdrDetails.getEwallet().getGrab().getMerchantmdr());
			grabMobiMdr = util.stringTofloat(mdrDetails.getEwallet().getGrab().getMobimdr());
			minimumMdr = mdrDetails.getEwallet().getGrab().getMinimummdr().equals("") ? "0.0"
					: mdrDetails.getEwallet().getGrab().getMinimummdr();

			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
			
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			restOfMdr.setMinimumMdr(minimumMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
			
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);
			
			break;
		case "TNG":
			tngHostMdr = util.stringTofloat(mdrDetails.getEwallet().getTng().getHostmdr());
			tngMercMdr = util.stringTofloat(mdrDetails.getEwallet().getTng().getMerchantmdr());
			tngMobiMdr = util.stringTofloat(mdrDetails.getEwallet().getTng().getMobimdr());
			minimumMdr = mdrDetails.getEwallet().getTng().getMinimummdr().equals("") ? "0.0"
					: mdrDetails.getEwallet().getTng().getMinimummdr();

			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
			
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
			restOfMdr.setMinimumMdr(minimumMdr);
			
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);
			
			break;
		case "SHOPPY":

			sppHostMdr = util.stringTofloat(mdrDetails.getEwallet().getSpp().getHostmdr());
			sppMercMdr = util.stringTofloat(mdrDetails.getEwallet().getSpp().getMerchantmdr());
			sppMobiMdr = util.stringTofloat(mdrDetails.getEwallet().getSpp().getMobimdr());
			minimumMdr = mdrDetails.getEwallet().getSpp().getMinimummdr().equals("") ? "0.0"
					: mdrDetails.getEwallet().getSpp().getMinimummdr();
			
			logger.info("Spp host mdr : "+sppHostMdr);
			logger.info("Spp Merc mdr : "+sppMercMdr);
			logger.info("Spp Mobi mdr : "+sppMobiMdr);
			logger.info("minimumMdr : "+minimumMdr);
			
			
			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
			
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
			
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			restOfMdr.setMinimumMdr(minimumMdr);
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);
			
			
			break;
		case "FPX":
			fpxHostMdr = util.stringTofloat(mdrDetails.getFpx().getHostmdr());
			fpxMercMdr = util.stringTofloat(mdrDetails.getFpx().getMerchantmdr());
			fpxMobiMdr = util.stringTofloat(mdrDetails.getFpx().getMobimdr());
			minimumMdr = mdrDetails.getFpx().getMinimummdr().equals("") ? "0.0" : mdrDetails.getFpx().getMinimummdr();
		
			restOfMdr.setForiegncreditMerchmdr(foriegncreditMerchmdr);
			restOfMdr.setForiegncreditHostmdr(foriegncreditHostmdr);
			restOfMdr.setForiegncreditmobimdr(foriegncreditmobimdr);
			restOfMdr.setForiegndebitHostmdr(foriegndebitHostmdr);
			restOfMdr.setForiegndebitMerchmdr(foriegndebitMerchmdr);
			restOfMdr.setForiegndebitmobimdr(foriegndebitmobimdr);
			
			restOfMdr.setLocalcreditHostmdr(localcreditHostmdr);
			restOfMdr.setLocalcreditMerchmdr(localcreditMerchmdr);
			restOfMdr.setLocalcreditmobimdr(localcreditmobimdr);
			restOfMdr.setLocaldebitHostmdr(localdebitHostmdr);
			restOfMdr.setLocaldebitMerchmdr(localdebitMerchmdr);
			restOfMdr.setLocaldebitmobimdr(localdebitmobimdr);
			
			restOfMdr.setBoostHostMdr(boostHostMdr);
			restOfMdr.setBoostMercMdr(boostMercMdr);
			restOfMdr.setBoostMobiMdr(boostMobiMdr);
		
			restOfMdr.setGrabHostMdr(grabHostMdr);
			restOfMdr.setGrabMercMdr(grabMercMdr);
			restOfMdr.setGrabMobiMdr(grabMobiMdr);
			
			restOfMdr.setTngHostMdr(tngHostMdr);
			restOfMdr.setTngMercMdr(tngMercMdr);
			restOfMdr.setTngMobiMdr(tngMobiMdr);
		
			restOfMdr.setSppHostMdr(sppHostMdr);
			restOfMdr.setSppMercMdr(sppMercMdr);
			restOfMdr.setSppMobiMdr(sppMobiMdr);
			
			restOfMdr.setFpxHostMdr(fpxHostMdr);
			restOfMdr.setFpxMercMdr(fpxMercMdr);
			restOfMdr.setFpxMobiMdr(fpxMobiMdr);
			restOfMdr.setMinimumMdr(minimumMdr);
			
			break;
		default:
			logger.error("Payment Method is Invalid");
			break;
			
		}

		
		return restOfMdr;
	}
}
