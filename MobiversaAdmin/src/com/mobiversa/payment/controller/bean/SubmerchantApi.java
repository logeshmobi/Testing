package com.mobiversa.payment.controller.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;

public class SubmerchantApi {

	private static Logger logger = Logger.getLogger(SubmerchantApi.class);

	public static String getupdateMerchantByNativeQuery() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("Update_Merchant_By_NativeQuery"));
			path = prop.getProperty("Update_Merchant_By_NativeQuery");
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
	
	public static String getaddSubMerchant() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("ADD_SUBMERCHANT_URL"));
			path = prop.getProperty("ADD_SUBMERCHANT_URL");
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
	
	public static String getmerchantDetails1() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("Merchant_Details1"));
			path = prop.getProperty("Merchant_Details1");
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
	
	public static String getupdateMIDData() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("Update_MID_Data"));
			path = prop.getProperty("Update_MID_Data");
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
	
	public static String updateMKData() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("Update_MK_Data"));
			path = prop.getProperty("Update_MK_Data");
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
	
	public static String updateMobileOTP() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("Update_Mobile_OTP"));
			path = prop.getProperty("Update_Mobile_OTP");
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
	
	

	public static JSONObject updateMerchantByNativeQuery(Merchant merchant) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;

		try {

			url = new URL(getupdateMerchantByNativeQuery()); //$NON-NLS-1$

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST"); //$NON-NLS-1$
			con.setRequestProperty("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$

			JSONObject params = new JSONObject();

			logger.info("id: " + merchant.getId()); //$NON-NLS-1$
			logger.info("businessName: " + merchant.getBusinessName()); //$NON-NLS-1$
			logger.info("email: " + merchant.getEmail()); //$NON-NLS-1$
			logger.info("city: " + merchant.getCity()); //$NON-NLS-1$
			logger.info("state: " + merchant.getState()); //$NON-NLS-1$
			logger.info("salutation: " + merchant.getSalutation()); //$NON-NLS-1$
			logger.info("contactPersonName: " + merchant.getContactPersonName()); //$NON-NLS-1$
			logger.info("contactPersonNo: " + merchant.getContactPersonPhoneNo()); //$NON-NLS-1$
			logger.info("TradingName: " + merchant.getTradingName()); //$NON-NLS-1$
			logger.info("website: " + merchant.getWebsite()); //$NON-NLS-1$
			logger.info("businessRegNo: " + merchant.getBusinessRegistrationNumber()); //$NON-NLS-1$
			logger.info("businessRegName: " + merchant.getBusinessShortName()); //$NON-NLS-1$
			logger.info("businessType: " + merchant.getBusinessType()); //$NON-NLS-1$
			logger.info("businessAddress: " + merchant.getBusinessAddress2()); //$NON-NLS-1$
			logger.info("businessNature: " + merchant.getNatureOfBusiness()); //$NON-NLS-1$
			logger.info("businessPostCode: " + merchant.getPostcode()); //$NON-NLS-1$
			logger.info("businessCountry: " + merchant.getCountry()); //$NON-NLS-1$
			logger.info("ownerSalutation: " + merchant.getOwnerSalutation()); //$NON-NLS-1$
			logger.info("ownerName: " + merchant.getOwnerName()); //$NON-NLS-1$
			logger.info("ownerContact: " + merchant.getOwnerContactNo()); //$NON-NLS-1$
			logger.info("ownerPassport: " + merchant.getOwnerPassportNo()); //$NON-NLS-1$
			logger.info("bankName: " + merchant.getBankName()); //$NON-NLS-1$
			logger.info("accountNo: " + merchant.getBankAcc()); //$NON-NLS-1$

			params.put("id", merchant.getId()); //$NON-NLS-1$
			params.put("businessName", merchant.getBusinessName()); //$NON-NLS-1$
			params.put("email", merchant.getEmail()); //$NON-NLS-1$
			params.put("city", merchant.getCity()); //$NON-NLS-1$
			params.put("state", merchant.getState()); //$NON-NLS-1$
			params.put("salutation", merchant.getSalutation()); //$NON-NLS-1$
			params.put("contactPersonName", merchant.getContactPersonName()); //$NON-NLS-1$
			params.put("contactPersonNo", merchant.getContactPersonPhoneNo()); //$NON-NLS-1$
			params.put("TradingName", merchant.getTradingName()); //$NON-NLS-1$
			params.put("website", merchant.getWebsite()); //$NON-NLS-1$
			params.put("businessRegNo", merchant.getBusinessRegistrationNumber()); //$NON-NLS-1$
			params.put("businessRegName", merchant.getBusinessShortName()); //$NON-NLS-1$
			params.put("businessType", merchant.getBusinessType()); //$NON-NLS-1$
			params.put("businessAddress", merchant.getBusinessAddress2()); //$NON-NLS-1$
			params.put("businessNature", merchant.getNatureOfBusiness()); //$NON-NLS-1$
			params.put("businessPostCode", merchant.getPostcode()); //$NON-NLS-1$
			params.put("businessCountry", merchant.getCountry()); //$NON-NLS-1$
			params.put("ownerSalutation", merchant.getOwnerSalutation()); //$NON-NLS-1$
			params.put("ownerName", merchant.getOwnerName()); //$NON-NLS-1$
			params.put("ownerContact", merchant.getOwnerContactNo()); //$NON-NLS-1$
			params.put("ownerPassport", merchant.getOwnerPassportNo()); //$NON-NLS-1$
			params.put("bankName", merchant.getBankName()); //$NON-NLS-1$
			params.put("accountNo", merchant.getBankAcc()); //$NON-NLS-1$
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss); //$NON-NLS-1$

			os.flush();
			StringBuffer response = new StringBuffer();
			logger.info(con);
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}

	public static ResponseDetails1 addSubMerchant(Merchant merchant, MID mm) {
		String inputLine = null;
		ResponseDetails1 rd = null;
		Gson gson = new Gson();
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {
			url = new URL(getaddSubMerchant()); //$NON-NLS-1$
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST"); //$NON-NLS-1$
			con.setRequestProperty("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$
			JSONObject params = new JSONObject();

			Date sa = new Date();
			// MID mm = new MID();
			String ad = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(merchant.getActivateDate());
			String cd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(merchant.getCreatedDate());

			params.put("agID", merchant.getAgID()); //$NON-NLS-1$
			params.put("subAgID", merchant.getSubAgID()); //$NON-NLS-1$
			params.put("businessShortName", merchant.getBusinessShortName()); //$NON-NLS-1$
			params.put("businessName", merchant.getBusinessName()); //$NON-NLS-1$
			params.put("businessRegistrationNumber", merchant.getBusinessRegistrationNumber());//$NON-NLS-1$
			params.put("businessAddress1", merchant.getBusinessAddress1());//$NON-NLS-1$
			params.put("businessAddress2", merchant.getBusinessAddress2());//$NON-NLS-1$
			params.put("businessAddress3", merchant.getBusinessAddress3());//$NON-NLS-1$
			params.put("contactPersonName", merchant.getContactPersonName());//$NON-NLS-1$
			params.put("contactPersonPhoneNo", merchant.getContactPersonPhoneNo());//$NON-NLS-1$
			params.put("email", merchant.getEmail());//$NON-NLS-1$
			params.put("salutation", merchant.getSalutation());//$NON-NLS-1$
			params.put("username", merchant.getUsername());//$NON-NLS-1$
			params.put("website", merchant.getWebsite());//$NON-NLS-1$
			params.put("businessContactNumber", merchant.getBusinessContactNumber());//$NON-NLS-1$
			params.put("faxNo", merchant.getFaxNo());//$NON-NLS-1$
			params.put("bankName", merchant.getBankName());//$NON-NLS-1$
			params.put("bankAcc", merchant.getBankAcc());//$NON-NLS-1$
			params.put("state", merchant.getState());//$NON-NLS-1$
			params.put("city", merchant.getCity());//$NON-NLS-1$
			params.put("postcode", merchant.getPostcode());//$NON-NLS-1$
			params.put("referralId", merchant.getReferralId());//$NON-NLS-1$
			params.put("waiverMonth", merchant.getWaiverMonth());//$NON-NLS-1$
			params.put("tradingName", merchant.getTradingName());//$NON-NLS-1$
			params.put("yearIncorporated", merchant.getYearIncorporated());//$NON-NLS-1$
			params.put("signedPackage", merchant.getSignedPackage());//$NON-NLS-1$
			params.put("readerSerialNo", merchant.getReaderSerialNo());//$NON-NLS-1$
			params.put("residentialAddress", merchant.getResidentialAddress());//$NON-NLS-1$
			params.put("businessType", merchant.getBusinessType());//$NON-NLS-1$
			params.put("companyType", merchant.getCompanyType());//$NON-NLS-1$
			params.put("natureOfBusiness", merchant.getNatureOfBusiness());//$NON-NLS-1$
			params.put("PermiseType", merchant.getPermiseType());//$NON-NLS-1$
			params.put("remarks", merchant.getRemarks());//$NON-NLS-1$
			params.put("preAuth", merchant.getPreAuth());//$NON-NLS-1$
			params.put("auth3DS", merchant.getAuth3DS());//$NON-NLS-1$
			params.put("mdr", merchant.getMdr());//$NON-NLS-1$
			params.put("autoSettled", merchant.getAutoSettled());//$NON-NLS-1$
			params.put("merchantType", merchant.getMerchantType());//$NON-NLS-1$
			params.put("ezyMotoVcc", merchant.getEzyMotoVcc());//$NON-NLS-1$
			params.put("accType", merchant.getAccType());//$NON-NLS-1$
			params.put("mmId", merchant.getMmId());//$NON-NLS-1$
			params.put("ownerSalutation", merchant.getOwnerSalutation());//$NON-NLS-1$
			params.put("ownerName", merchant.getOwnerName());//$NON-NLS-1$
			params.put("ownerPassportNo", merchant.getOwnerPassportNo());//$NON-NLS-1$
			params.put("ownerContactNo", merchant.getOwnerContactNo());//$NON-NLS-1$
			params.put("residentialAddress", merchant.getResidentialAddress());//$NON-NLS-1$
			params.put("enabled", true);//$NON-NLS-1$
			params.put("username", merchant.getUsername());//$NON-NLS-1$
			// params.put("createdDate", cd);//$NON-NLS-1$
			params.put("role", merchant.getRole());//$NON-NLS-1$
			params.put("mid", mm.getSubMerchantMID());//$NON-NLS-1$
			params.put("status", merchant.getStatus());//$NON-NLS-1$
			// params.put("activateDate", ad);//$NON-NLS-1$
			params.put("password", merchant.getPassword());//$NON-NLS-1$
			params.put("payoutGrandDetail", "000000000000"); //$NON-NLS-1$ //$NON-NLS-2$

			logger.info(merchant.getAgID()); // $NON-NLS-1$
			logger.info(merchant.getSubAgID()); // $NON-NLS-1$
			logger.info(merchant.getBusinessShortName()); // $NON-NLS-1$
			logger.info(merchant.getBusinessName()); // $NON-NLS-1$
			logger.info(merchant.getBusinessRegistrationNumber());// $NON-NLS-1$
			logger.info(merchant.getBusinessAddress1());// $NON-NLS-1$
			logger.info(merchant.getBusinessAddress2());// $NON-NLS-1$
			logger.info(merchant.getBusinessAddress3());// $NON-NLS-1$
			logger.info(merchant.getContactPersonName());// $NON-NLS-1$
			logger.info(merchant.getContactPersonPhoneNo());// $NON-NLS-1$
			logger.info(merchant.getEmail());// $NON-NLS-1$
			logger.info(merchant.getSalutation());// $NON-NLS-1$
			logger.info(merchant.getUsername());// $NON-NLS-1$
			logger.info(merchant.getWebsite());// $NON-NLS-1$
			logger.info(merchant.getBusinessContactNumber());// $NON-NLS-1$
			logger.info(merchant.getFaxNo());// $NON-NLS-1$
			logger.info(merchant.getBankName());// $NON-NLS-1$
			logger.info(merchant.getBankAcc());// $NON-NLS-1$
			logger.info(merchant.getState());// $NON-NLS-1$
			logger.info(merchant.getCity());// $NON-NLS-1$
			logger.info(merchant.getPostcode());// $NON-NLS-1$
			logger.info(merchant.getReferralId());// $NON-NLS-1$
			logger.info(merchant.getWaiverMonth());// $NON-NLS-1$
			logger.info(merchant.getTradingName());// $NON-NLS-1$
			logger.info(merchant.getYearIncorporated());// $NON-NLS-1$
			logger.info(merchant.getSignedPackage());// $NON-NLS-1$
			logger.info(merchant.getReaderSerialNo());// $NON-NLS-1$
			logger.info(merchant.getResidentialAddress());// $NON-NLS-1$
			logger.info(merchant.getBusinessType());// $NON-NLS-1$
			logger.info(merchant.getCompanyType());// $NON-NLS-1$
			logger.info(merchant.getNatureOfBusiness());// $NON-NLS-1$
			logger.info(merchant.getPermiseType());// $NON-NLS-1$
			logger.info(merchant.getRemarks());// $NON-NLS-1$
			logger.info(merchant.getPreAuth());// $NON-NLS-1$
			logger.info(merchant.getAuth3DS());// $NON-NLS-1$
			logger.info(merchant.getMdr());// $NON-NLS-1$
			logger.info(merchant.getAutoSettled());// $NON-NLS-1$
			logger.info(merchant.getMerchantType());// $NON-NLS-1$
			logger.info(merchant.getEzyMotoVcc());// $NON-NLS-1$
			logger.info(merchant.getAccType());// $NON-NLS-1$
			logger.info(merchant.getMmId());// $NON-NLS-1$
			logger.info(merchant.getOwnerSalutation());// $NON-NLS-1$
			logger.info(merchant.getOwnerName());// $NON-NLS-1$
			logger.info(merchant.getOwnerPassportNo());// $NON-NLS-1$
			logger.info(merchant.getOwnerContactNo());// $NON-NLS-1$
			logger.info(merchant.getResidentialAddress());// $NON-NLS-1$
			logger.info(true);// $NON-NLS-1$
			logger.info(merchant.getUsername());// $NON-NLS-1$
			logger.info(merchant.getCreatedDate());// $NON-NLS-1$
			logger.info(merchant.getRole());// $NON-NLS-1$
			logger.info("MID Submerchant: " + mm.getSubMerchantMID());// $NON-NLS-1$
			logger.info(merchant.getStatus());// $NON-NLS-1$
			logger.info(merchant.getActivateDate());// $NON-NLS-1$
			logger.info(merchant.getPassword());// $NON-NLS-1$
			logger.info("000000000");// $NON-NLS-1$

			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss.toString()); //$NON-NLS-1$

			os.flush();
			StringBuffer response = new StringBuffer();
			logger.info(con);
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();
			logger.info(" Output from Server .... " + output); //$NON-NLS-1$

			rd = gson.fromJson(output, ResponseDetails1.class);
			logger.info(" Output ID from Server .... " + rd.getId()); //$NON-NLS-1$
			logger.info(" Output BusinessName from Server .... " + rd.getBusinessName()); //$NON-NLS-1$
			logger.info(" Output Mid from Server .... " + rd.getMid()); //$NON-NLS-1$
			logger.info(" Output NatureOfBusiness from Server .... " + rd.getNatureOfBusiness()); //$NON-NLS-1$
			logger.info(" Output PayoutGrandDetail from Server .... " + rd.getPayoutGrandDetail()); //$NON-NLS-1$
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rd;

	}

	public static JSONObject merchantDetails1(MerchantDetails merchant) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {
			url = new URL(getmerchantDetails1()); //$NON-NLS-1$
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST"); //$NON-NLS-1$
			con.setRequestProperty("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$
			JSONObject params = new JSONObject();
			params.put("merchantId", merchant.getMerchantId()); //$NON-NLS-1$
			params.put("merchantCategory", merchant.getMerchantCategory()); //$NON-NLS-1$
			params.put("mid", merchant.getMid()); //$NON-NLS-1$
			params.put("points", merchant.getPoints()); //$NON-NLS-1$
			params.put("merchantName", merchant.getMerchantName()); //$NON-NLS-1$

			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss.toString()); //$NON-NLS-1$

			os.flush();
			StringBuffer response = new StringBuffer();
			logger.info(con);
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}

	public static JSONObject updateMIDData(Long m_id, String merchant_id) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {
			url = new URL(getupdateMIDData()); //$NON-NLS-1$
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST"); //$NON-NLS-1$
			con.setRequestProperty("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$
			JSONObject params = new JSONObject();
			params.put("mid", m_id); //$NON-NLS-1$
			params.put("id", merchant_id); //$NON-NLS-1$
			logger.info("mid" + m_id);
			logger.info("id" + merchant_id);

			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss.toString()); //$NON-NLS-1$

			os.flush();
			StringBuffer response = new StringBuffer();
			logger.info(con);
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}

	public static JSONObject updateMKData(Long m_id, Long merchant_id) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {
			url = new URL(updateMKData()); //$NON-NLS-1$
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST"); //$NON-NLS-1$
			con.setRequestProperty("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$
			JSONObject params = new JSONObject();
			params.put("pgmid", m_id); //$NON-NLS-1$
			params.put("id", merchant_id); //$NON-NLS-1$

			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss.toString()); //$NON-NLS-1$

			os.flush();
			StringBuffer response = new StringBuffer();
			logger.info(con);
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}

	public static JSONObject updateMobileOTP(String seq, String key) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {
			url = new URL(updateMobileOTP()); //$NON-NLS-1$
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST"); //$NON-NLS-1$
			con.setRequestProperty("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$
			JSONObject params = new JSONObject();
			params.put("submid", seq); //$NON-NLS-1$
			params.put("key", key); //$NON-NLS-1$

			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss.toString()); //$NON-NLS-1$

			os.flush();
			StringBuffer response = new StringBuffer();
			logger.info(con);
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}

}
