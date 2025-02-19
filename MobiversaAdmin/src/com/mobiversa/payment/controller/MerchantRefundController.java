package com.mobiversa.payment.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.RefundRequest;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.payment.controller.bean.RefundBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.RefundApi;
import com.mobiversa.payment.util.RefundEncrypt;

@Controller
@RequestMapping(value = MerchantRefundController.URL_BASE)
public class MerchantRefundController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private TransactionService transactionService;

	public static final String URL_BASE = "/merchant/transaction"; //$NON-NLS-1$

	private static final Logger logger = Logger.getLogger(MerchantRefundController.class);

	@SuppressWarnings("nls")
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public static String defaultPage() {
		return "redirect:/auth/login";
	}

	@RequestMapping(value = "void/boosttest", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	public @ResponseBody RefundBean processBoostVoidTest(@RequestParam String transactionId,
			@RequestParam String voidType, @RequestParam String voidAmount, HttpServletRequest request)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, ParseException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating Boost Void by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Void Amount: {}" + voidAmount);
		logger.info("Void Type: {}" + voidType);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		ForSettlement forSettlement = merchantService.findbyBoostData(transactionId);

		if (forSettlement != null && "BPA".equalsIgnoreCase(forSettlement.getStatus())) {

			logger.info("Proceeding with Boost API Void");

//			String merchantId = PropertyLoad.getFile().getProperty("BOOST_MERCHANTID");
//			String onlineRefNum = forSettlement.getAidResponse();
//			String boostPaymentRefNum = forSettlement.getRrn();
//			String remark = PropertyLoad.getFile().getProperty("BOOST_REMARK");
//			String apiSecretKey = PropertyLoad.getFile().getProperty("BOOST_API_SECRET");
//			String checksumValue = merchantId + onlineRefNum + boostPaymentRefNum + remark;

//			String checksum = RefundEncrypt.TDES(checksumValue, apiSecretKey);

//			refundBean = RefundApi.ConnectToBoost(merchantId, onlineRefNum, boostPaymentRefNum, remark, voidType,
//					voidAmount, checksum, "void");

//			if (refundBean != null) {
			if ("completed".equalsIgnoreCase(PropertyLoad.getFile().getProperty("BOOST_RESPONSE"))) {
				RefundRequest refundRequest = new RefundRequest();
				refundRequest.setMerchantName(currentMerchant.getBusinessName());
				refundRequest.setAccNo(currentMerchant.getBankAcc());
				refundRequest.setInitiateDate(getCurrentDateStringFormat());
				refundRequest.setRequestRefundAmount(voidAmount);
				refundRequest.setTxnId(transactionId);
//					refundRequest.setRefundTxnId(refundBean.getBoostRefNum());
				refundRequest.setProductType("BOOST");
				refundRequest.setStatus("BPC");
//					refundRequest.setTransactionType(refundBean.getTransactionType());
				merchantService.saveRefundRequest(refundRequest);
				merchantService.updateBoostVoidStatus(transactionId);
				RefundApi.sendVoidNotificationEmail(refundRequest,
						getFormatTimestampStringFormat(forSettlement.getTimeStamp()), currentMerchant.getEmail());

				refundBean.setTransactionId(transactionId);
				refundBean.setAmount(voidAmount);
				refundBean.setResponseCode("0000");
				refundBean.setResponseDescription("Boost API void completed successfully");
				logger.info("Boost API void completed successfully");
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Boost API void status: " + refundBean.getTransactionStatus());
				logger.info("Boost API void status: {}" + refundBean.getTransactionStatus());
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Error processing Boost API void. Response is null.");
			logger.error("Error processing Boost API void. Response is null.");
		}

//		} else {
//			refundBean.setResponseCode("0001");
//			refundBean.setResponseDescription("Internal Server Error");
//		}

		return refundBean;
	}

	@RequestMapping(value = "refund/boosttest", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	public @ResponseBody RefundBean processBoostRefund(@RequestParam String transactionId,
			@RequestParam String refundType, @RequestParam String refundAmount, HttpServletRequest request)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, ParseException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating Boost Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);
		logger.info("Refund Type: {}" + refundType);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		ForSettlement forSettlement = merchantService.findbyBoostData(transactionId);

		if (forSettlement != null && "BPS".equalsIgnoreCase(forSettlement.getStatus())) {
			BoostDailyRecon boostDailyRecon = transactionService.loadBoostdlyrecon(forSettlement.getRrn());

			if (boostDailyRecon != null
					&& isValidSettlementDate(FormatYYYY_MM_DD(boostDailyRecon.getSettleDate(), "Boost"))) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with Boost API refund");

//					String merchantId = PropertyLoad.getFile().getProperty("BOOST_MERCHANTID");
//					String onlineRefNum = forSettlement.getAidResponse();
//					String boostPaymentRefNum = forSettlement.getRrn();
//					String remark = PropertyLoad.getFile().getProperty("BOOST_REMARK");
//					String apiSecretKey = PropertyLoad.getFile().getProperty("BOOST_API_SECRET");
//					String checksumValue = merchantId + onlineRefNum + boostPaymentRefNum + remark;
//
//					String checksum = RefundEncrypt.TDES(checksumValue, apiSecretKey);

//					refundBean = RefundApi.ConnectToBoost(merchantId, onlineRefNum, boostPaymentRefNum, remark,
//							refundType, refundAmount, checksum, "refund");

//					if (refundBean != null) {
					if ("completed".equalsIgnoreCase(PropertyLoad.getFile().getProperty("BOOST_RESPONSE"))) {
						RefundRequest refundRequest = new RefundRequest();
						refundRequest.setMerchantName(currentMerchant.getBusinessName());
						refundRequest.setAccNo(currentMerchant.getBankAcc());
						refundRequest.setInitiateDate(getCurrentDateStringFormat());
						refundRequest.setRequestRefundAmount(refundAmount);
						refundRequest.setSettlementDate(settlementDate);
						refundRequest.setTxnId(transactionId);
//							refundRequest.setRefundTxnId(refundBean.getBoostRefNum());
						refundRequest.setProductType("BOOST");
						refundRequest.setStatus("BPR");
//							refundRequest.setTransactionType(refundBean.getTransactionType());

						merchantService.saveRefundRequest(refundRequest);
						merchantService.updateBoostRefundStatus(transactionId);
						RefundApi.sendRefundNotificationEmail(refundRequest,
								getFormatTimestampStringFormat(forSettlement.getTimeStamp()),
								currentMerchant.getEmail());
						refundBean.setTransactionId(transactionId);
						refundBean.setAmount(refundAmount);
						refundBean.setResponseCode("0000");
						refundBean.setResponseDescription("Boost API refund completed successfully");
						logger.info("Boost API refund completed successfully");
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription(
								"Boost API refund status: " + refundBean.getTransactionStatus());
						logger.info("Boost API refund status: {}" + refundBean.getTransactionStatus());
					}
//				} else {
//					refundBean.setResponseCode("0001");
//					refundBean.setResponseDescription("Error processing Boost API refund. Response is null.");
//					logger.error("Error processing Boost API refund. Response is null.");
//				}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "refund/grabpaytest", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	public @ResponseBody RefundBean processGrabPayRefundTest(@RequestParam String transactionId,
			@RequestParam String refundAmount, HttpServletRequest request) throws ParseException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating GrabPay Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		ForSettlement forSettlement = merchantService.findbyGrabData(transactionId);

		if (forSettlement != null && "GPS".equalsIgnoreCase(forSettlement.getStatus())) {
			GrabPayFile grabPayFile = transactionService.loadGrabPayFile(forSettlement.getRrn());

			if (grabPayFile != null
					&& isValidSettlementDate(FormatYYYY_MM_DD(grabPayFile.getSettlementDate(), "GrabPay"))) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with GrabPay API refund");

//					String service = PropertyLoad.getFile().getProperty("GRABPAY_SERVICE");
//					String sessionId = PropertyLoad.getFile().getProperty("GRABPAY_SESSIONID");
//					String partnerTxID = forSettlement.getRrn();
//					String description = PropertyLoad.getFile().getProperty("GRABPAY_DESCRIPTION");

//					refundBean = RefundApi.ConnectToGrabPay(service, sessionId, partnerTxID, description);
//
//					if (refundBean != null) {
					if ("success".equalsIgnoreCase(PropertyLoad.getFile().getProperty("GRABPAY_RESPONSE"))) {
						RefundRequest refundRequest = new RefundRequest();
						refundRequest.setMerchantName(currentMerchant.getBusinessName());
						refundRequest.setAccNo(currentMerchant.getBankAcc());
						refundRequest.setInitiateDate(getCurrentDateStringFormat());
						refundRequest.setRequestRefundAmount(refundAmount);
						refundRequest.setSettlementDate(settlementDate);
						refundRequest.setTxnId(transactionId);
//						refundRequest.setRefundTxnId(refundBean.getResponseData().getTxID());
						refundRequest.setProductType("GRABPAY");
						refundRequest.setStatus("GRF");
						refundRequest.setTransactionType("refund");

						merchantService.saveRefundRequest(refundRequest);
						refundBean.setTransactionId(transactionId);
						RefundApi.sendRefundNotificationEmail(refundRequest,
								getFormatTimestampStringFormat(forSettlement.getTimeStamp()),
								currentMerchant.getEmail());
						refundBean.setAmount(refundAmount);
						refundBean.setResponseCode("0000");
						refundBean.setResponseDescription("GrabPay API refund completed successfully");
						logger.info("GrabPay API refund completed successfully");
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription("GrabPay API refund status: null");
//						refundBean.setResponseDescription(
//								"GrabPay API refund status: " + refundBean.getResponseData().getTxStatus());
//						logger.info("GrabPay API refund status: {}" + refundBean.getResponseData().getTxStatus());
					}
//					} else {
//						refundBean.setResponseCode("0001");
//						refundBean.setResponseDescription("Error processing GrabPay API refund. Response is null.");
//						logger.error("Error processing GrabPay API refund. Response is null.");
//					}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "refund/tngtest", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	public @ResponseBody RefundBean processTouchnGoTest(@RequestParam String transactionId,
			@RequestParam String refundAmount, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating Touch'n Go Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		EwalletTxnDetails ewalletTxnDetails = merchantService.findbyTouchnGoAndShopeePayData(transactionId);

		if (ewalletTxnDetails != null && "TPS".equalsIgnoreCase(ewalletTxnDetails.getStatus())) {

			if (isValidSettlementDate(ewalletTxnDetails.getSettledDate())) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with Touch'n Go API refund");

					String tngtransactionId = ewalletTxnDetails.getTngTxnId();
					String amount = refundAmount;
					String currencyType = PropertyLoad.getFile().getProperty("TNG_SPP_CURRENCY_TYPE");
					String reason = PropertyLoad.getFile().getProperty("TNG_SPP_REASON");
					String refundType = PropertyLoad.getFile().getProperty("TNG_SPP_REFUND_TYPE");
					// String service = PropertyLoad.getFile().getProperty("TNG_SPP_SERVICE");
					String merchantId = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_MERCHANTID")
									: PropertyLoad.getFile().getProperty("TNG_SPP_MERCHANTID");

					String keyPath = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_KEYPATH")
									: PropertyLoad.getFile().getProperty("TNG_SPP_KEYPATH");

					StringBuilder sb = new StringBuilder();
					sb.append(tngtransactionId).append("|");
					sb.append(amount).append("|");
					sb.append(currencyType).append("|");
					sb.append(reason).append("|");
					sb.append(refundType).append("|");
					sb.append(merchantId);

					logger.info("Key Path : " + keyPath);

					logger.info("Sign data : " + sb.toString());

					String xSignature = null;

					xSignature = RefundEncrypt.tngSignData(keyPath, sb.toString());

					logger.info("X-Signature : " + xSignature);

//					refundBean = RefundApi.ConnectToTouchnGoAndShopeePay(tngtransactionId, amount, currencyType, reason,
//							refundType, merchantId, service, xSignature);

//					if (refundBean != null) {
					if ("SUCCESSFUL".equalsIgnoreCase(PropertyLoad.getFile().getProperty("TNG_SPP_RESPONSE"))) {
						RefundRequest refundRequest = new RefundRequest();
						refundRequest.setMerchantName(currentMerchant.getBusinessName());
						refundRequest.setAccNo(currentMerchant.getBankAcc());
						refundRequest.setInitiateDate(getCurrentDateStringFormat());
						refundRequest.setRequestRefundAmount(refundAmount);
						refundRequest.setSettlementDate(settlementDate);
//							refundRequest.setTxnId(refundBean.getTransactionId());
						refundRequest.setTxnId(transactionId);
						refundRequest.setProductType("TOUCH_N_GO");
						refundRequest.setStatus("TPR");
						refundRequest.setTransactionType("refund");

						merchantService.saveRefundRequest(refundRequest);
						merchantService.updateTouchnGoRefundStatus(transactionId, ewalletTxnDetails.getInvoiceId());
						RefundApi.sendRefundNotificationEmail(refundRequest,
								getFormatTimestampStringFormat(ewalletTxnDetails.getTimeStamp()),
								currentMerchant.getEmail());
						refundBean.setTransactionId(transactionId);
						refundBean.setAmount(refundAmount);
						refundBean.setResponseCode("0000");
						refundBean.setResponseDescription("Touch'n Go API refund completed successfully");
						logger.info("Touch'n Go API refund completed successfully");
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription("Touch'n Go API refund status: " + refundBean.getMessage());
						logger.info("Touch'n Go API refund status: {}" + refundBean.getMessage());
					}
//					} else {
//						refundBean.setResponseCode("0001");
//						refundBean.setResponseDescription("Error processing Touch'n Go API refund. Response is null.");
//						logger.error("Error processing Touch'n Go API refund. Response is null.");
//					}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "refund/spptest", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	public @ResponseBody RefundBean processShopeePayRefundTest(@RequestParam String transactionId,
			@RequestParam String refundAmount, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating ShopeePay Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		EwalletTxnDetails ewalletTxnDetails = merchantService.findbyTouchnGoAndShopeePayData(transactionId);

		if (ewalletTxnDetails != null && "SPS".equalsIgnoreCase(ewalletTxnDetails.getStatus())) {

			if (isValidSettlementDate(ewalletTxnDetails.getSettledDate())) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with ShopeePay API refund");

					String tngtransactionId = ewalletTxnDetails.getTngTxnId();
					String amount = refundAmount;
					String currencyType = PropertyLoad.getFile().getProperty("TNG_SPP_CURRENCY_TYPE");
					String reason = PropertyLoad.getFile().getProperty("TNG_SPP_REASON");
					String refundType = PropertyLoad.getFile().getProperty("TNG_SPP_REFUND_TYPE");
//					String service = PropertyLoad.getFile().getProperty("TNG_SPP_SERVICE");
					String merchantId = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_MERCHANTID")
									: PropertyLoad.getFile().getProperty("TNG_SPP_MERCHANTID");

					String keyPath = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_KEYPATH")
									: PropertyLoad.getFile().getProperty("TNG_SPP_KEYPATH");

					StringBuilder sb = new StringBuilder();
					sb.append(tngtransactionId).append("|");
					sb.append(amount).append("|");
					sb.append(currencyType).append("|");
					sb.append(reason).append("|");
					sb.append(refundType).append("|");
					sb.append(merchantId);

					logger.info("Key Path : " + keyPath);

					logger.info("Sign data : " + sb.toString());

					String xSignature = null;

					xSignature = RefundEncrypt.tngSignData(keyPath, sb.toString());

					logger.info("X-Signature : " + xSignature);

//					refundBean = RefundApi.ConnectToTouchnGoAndShopeePay(tngtransactionId, amount, currencyType, reason,
//							refundType, merchantId, service, xSignature);
//
//					if (refundBean != null) {
					if ("SUCCESSFUL".equalsIgnoreCase(PropertyLoad.getFile().getProperty("TNG_SPP_RESPONSE"))) {
						RefundRequest refundRequest = new RefundRequest();
						refundRequest.setMerchantName(currentMerchant.getBusinessName());
						refundRequest.setAccNo(currentMerchant.getBankAcc());
						refundRequest.setInitiateDate(getCurrentDateStringFormat());
						refundRequest.setRequestRefundAmount(refundAmount);
						refundRequest.setSettlementDate(settlementDate);
//							refundRequest.setTxnId(refundBean.getTransactionId());
						refundRequest.setTxnId(transactionId);
						refundRequest.setProductType("SHOPEE_PAY");
						refundRequest.setStatus("SPR");
						refundRequest.setTransactionType("refund");

						merchantService.saveRefundRequest(refundRequest);
						merchantService.updateShopeePayRefundStatus(transactionId, ewalletTxnDetails.getInvoiceId());
						RefundApi.sendRefundNotificationEmail(refundRequest,
								getFormatTimestampStringFormat(ewalletTxnDetails.getTimeStamp()),
								currentMerchant.getEmail());
						refundBean.setTransactionId(transactionId);
						refundBean.setAmount(refundAmount);
						refundBean.setResponseCode("0000");
						refundBean.setResponseDescription("ShopeePay API refund completed successfully");
						logger.info("ShopeePay API refund completed successfully");
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription("ShopeePay API refund status: " + refundBean.getMessage());
						logger.info("ShopeePay API refund status: {}" + refundBean.getMessage());
					}
//					} else {
//						refundBean.setResponseCode("0001");
//						refundBean.setResponseDescription("Error processing ShopeePay API refund. Response is null.");
//						logger.error("Error processing ShopeePay API refund. Response is null.");
//					}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "void/boost", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access", "null" })
	public @ResponseBody RefundBean processBoostVoid(@RequestParam String transactionId, @RequestParam String voidType,
			@RequestParam String voidAmount, HttpServletRequest request)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, ParseException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating Boost Void by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Void Amount: {}" + voidAmount);
		logger.info("Void Type: {}" + voidType);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		ForSettlement forSettlement = merchantService.findbyBoostData(transactionId);

		if (forSettlement != null && "BPA".equalsIgnoreCase(forSettlement.getStatus())) {

			logger.info("Proceeding with Boost API Void");

			String merchantId = PropertyLoad.getFile().getProperty("BOOST_MERCHANTID");
			String onlineRefNum = forSettlement.getAidResponse();
			String boostPaymentRefNum = forSettlement.getRrn();
			String remark = PropertyLoad.getFile().getProperty("BOOST_REMARK");
			String apiSecretKey = PropertyLoad.getFile().getProperty("BOOST_API_SECRET");
			String checksumValue = merchantId + onlineRefNum + boostPaymentRefNum + remark;

			String checksum = RefundEncrypt.TDES(checksumValue, apiSecretKey);

			refundBean = RefundApi.ConnectToBoost(merchantId, onlineRefNum, boostPaymentRefNum, remark, voidType,
					voidAmount, checksum, "void");

			if (refundBean != null) {
				if ("completed".equalsIgnoreCase(refundBean.getTransactionStatus())) {
					RefundRequest refundRequest = new RefundRequest();
					refundRequest.setMerchantName(currentMerchant.getBusinessName());
					refundRequest.setAccNo(currentMerchant.getBankAcc());
					refundRequest.setInitiateDate(getCurrentDateStringFormat());
					refundRequest.setRequestRefundAmount(voidAmount);
					refundRequest.setTxnId(transactionId);
					refundRequest.setMid(Long.toString(currentMerchant.getId()));
					refundRequest.setRefundTxnId(refundBean.getBoostRefNum());
					refundRequest.setProductType("BOOST");
					refundRequest.setStatus("BPC");
					refundRequest.setTransactionType(refundBean.getTransactionType());

					merchantService.saveRefundRequest(refundRequest);
					merchantService.updateBoostVoidStatus(transactionId);
					RefundApi.sendVoidNotificationEmail(refundRequest,
							getFormatTimestampStringFormat(forSettlement.getTimeStamp()), currentMerchant.getEmail());
					refundBean.setTransactionId(transactionId);
					refundBean.setAmount(voidAmount);
					refundBean.setResponseCode("0000");
					refundBean.setResponseDescription("Boost API void completed successfully");
					logger.info("Boost API void completed successfully");
				} else {
					refundBean.setResponseCode("0001");
					refundBean.setResponseDescription("Boost API void status: " + refundBean.getTransactionStatus());
					logger.info("Boost API void status: {}" + refundBean.getTransactionStatus());
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Error processing Boost API void. Response is null.");
				logger.error("Error processing Boost API void. Response is null.");
			}

		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "refund/boost", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access", "null" })
	public @ResponseBody RefundBean processBoostRefundTest(@RequestParam String transactionId,
			@RequestParam String refundType, @RequestParam String refundAmount, HttpServletRequest request)
			throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, ParseException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating Boost Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);
		logger.info("Refund Type: {}" + refundType);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		ForSettlement forSettlement = merchantService.findbyBoostData(transactionId);

		if (forSettlement != null && "BPS".equalsIgnoreCase(forSettlement.getStatus())) {
			BoostDailyRecon boostDailyRecon = transactionService.loadBoostdlyrecon(forSettlement.getRrn());

			if (boostDailyRecon != null
					&& isValidSettlementDate(FormatYYYY_MM_DD(boostDailyRecon.getSettleDate(), "Boost"))) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with Boost API refund");

					String merchantId = PropertyLoad.getFile().getProperty("BOOST_MERCHANTID");
					String onlineRefNum = forSettlement.getAidResponse();
					String boostPaymentRefNum = forSettlement.getRrn();
					String remark = PropertyLoad.getFile().getProperty("BOOST_REMARK");
					String apiSecretKey = PropertyLoad.getFile().getProperty("BOOST_API_SECRET");
					String checksumValue = merchantId + onlineRefNum + boostPaymentRefNum + remark;

					String checksum = RefundEncrypt.TDES(checksumValue, apiSecretKey);

					refundBean = RefundApi.ConnectToBoost(merchantId, onlineRefNum, boostPaymentRefNum, remark,
							refundType, refundAmount, checksum, "refund");

					if (refundBean != null) {
						if ("completed".equalsIgnoreCase(refundBean.getTransactionStatus())) {
							RefundRequest refundRequest = new RefundRequest();
							refundRequest.setMerchantName(currentMerchant.getBusinessName());
							refundRequest.setAccNo(currentMerchant.getBankAcc());
							refundRequest.setInitiateDate(getCurrentDateStringFormat());
							refundRequest.setRequestRefundAmount(refundAmount);
							refundRequest.setSettlementDate(settlementDate);
							refundRequest.setTxnId(transactionId);
							refundRequest.setMid(Long.toString(currentMerchant.getId()));
							refundRequest.setRefundTxnId(refundBean.getBoostRefNum());
							refundRequest.setProductType("BOOST");
							refundRequest.setStatus("BPR");
							refundRequest.setTransactionType(refundBean.getTransactionType());

							merchantService.saveRefundRequest(refundRequest);
							merchantService.updateBoostRefundStatus(transactionId);
							RefundApi.sendRefundNotificationEmail(refundRequest,
									getFormatTimestampStringFormat(forSettlement.getTimeStamp()),
									currentMerchant.getEmail());
							refundBean.setTransactionId(transactionId);
							refundBean.setAmount(refundAmount);
							refundBean.setResponseCode("0000");
							refundBean.setResponseDescription("Boost API refund completed successfully");
							logger.info("Boost API refund completed successfully");
						} else {
							refundBean.setResponseCode("0001");
							refundBean.setResponseDescription(
									"Boost API refund status: " + refundBean.getTransactionStatus());
							logger.info("Boost API refund status: {}" + refundBean.getTransactionStatus());
						}
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription("Error processing Boost API refund. Response is null.");
						logger.error("Error processing Boost API refund. Response is null.");
					}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "refund/grabpay", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access", "null" })
	public @ResponseBody RefundBean processGrabPayRefund(@RequestParam String transactionId,
			@RequestParam String refundAmount, HttpServletRequest request) throws ParseException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating GrabPay Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		ForSettlement forSettlement = merchantService.findbyGrabData(transactionId);

		if (forSettlement != null && "GPS".equalsIgnoreCase(forSettlement.getStatus())) {
			GrabPayFile grabPayFile = transactionService.loadGrabPayFile(forSettlement.getRrn());

			if (grabPayFile != null
					&& isValidSettlementDate(FormatYYYY_MM_DD(grabPayFile.getSettlementDate(), "GrabPay"))) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with GrabPay API refund");

					String service = PropertyLoad.getFile().getProperty("GRABPAY_SERVICE");
					String sessionId = PropertyLoad.getFile().getProperty("GRABPAY_SESSIONID");
					String partnerTxID = forSettlement.getRrn();
					String description = PropertyLoad.getFile().getProperty("GRABPAY_DESCRIPTION");

					refundBean = RefundApi.ConnectToGrabPay(service, sessionId, partnerTxID, description);

					if (refundBean != null) {
						if ("success".equalsIgnoreCase(refundBean.getResponseData().getTxStatus())) {
							RefundRequest refundRequest = new RefundRequest();
							refundRequest.setMerchantName(currentMerchant.getBusinessName());
							refundRequest.setAccNo(currentMerchant.getBankAcc());
							refundRequest.setInitiateDate(getCurrentDateStringFormat());
							refundRequest.setRequestRefundAmount(refundAmount);
							refundRequest.setSettlementDate(settlementDate);
							refundRequest.setTxnId(transactionId);
							refundRequest.setMid(Long.toString(currentMerchant.getId()));
							refundRequest.setRefundTxnId(refundBean.getResponseData().getTxID());
							refundRequest.setProductType("GRABPAY");
							refundRequest.setStatus("GRF");
							refundRequest.setTransactionType("refund");

							merchantService.saveRefundRequest(refundRequest);
							RefundApi.sendRefundNotificationEmail(refundRequest,
									getFormatTimestampStringFormat(forSettlement.getTimeStamp()),
									currentMerchant.getEmail());
							refundBean.setTransactionId(transactionId);
							refundBean.setAmount(refundAmount);
							refundBean.setResponseCode("0000");
							refundBean.setResponseDescription("GrabPay API refund completed successfully");
							logger.info("GrabPay API refund completed successfully");
						} else {
							refundBean.setResponseCode("0001");
							refundBean.setResponseDescription(
									"GrabPay API refund status: " + refundBean.getResponseData().getTxStatus());
							logger.info("GrabPay API refund status: {}" + refundBean.getResponseData().getTxStatus());
						}
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription("Error processing GrabPay API refund. Response is null.");
						logger.error("Error processing GrabPay API refund. Response is null.");
					}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "refund/tng", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access", "null" })
	public @ResponseBody RefundBean processTouchnGo(@RequestParam String transactionId,
			@RequestParam String refundAmount, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating Touch'n Go Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		EwalletTxnDetails ewalletTxnDetails = merchantService.findbyTouchnGoAndShopeePayData(transactionId);

		if (ewalletTxnDetails != null && "TPS".equalsIgnoreCase(ewalletTxnDetails.getStatus())) {

			if (isValidSettlementDate(ewalletTxnDetails.getSettledDate())) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with Touch'n Go API refund");

					String tngtransactionId = ewalletTxnDetails.getTngTxnId();
					String amount = refundAmount;
					String currencyType = PropertyLoad.getFile().getProperty("TNG_SPP_CURRENCY_TYPE");
					String reason = PropertyLoad.getFile().getProperty("TNG_SPP_REASON");
					String refundType = PropertyLoad.getFile().getProperty("TNG_SPP_REFUND_TYPE");
					String service = PropertyLoad.getFile().getProperty("TNG_SPP_SERVICE");
					String merchantId = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_MERCHANTID")
									: PropertyLoad.getFile().getProperty("TNG_SPP_MERCHANTID");

					String keyPath = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_KEYPATH")
									: PropertyLoad.getFile().getProperty("TNG_SPP_KEYPATH");

					StringBuilder sb = new StringBuilder();
					sb.append(tngtransactionId).append("|");
					sb.append(amount).append("|");
					sb.append(currencyType).append("|");
					sb.append(reason).append("|");
					sb.append(refundType).append("|");
					sb.append(merchantId);

					logger.info("Key Path : " + keyPath);

					logger.info("Sign data : " + sb.toString());

					String xSignature = null;

					xSignature = RefundEncrypt.tngSignData(keyPath, sb.toString());

					logger.info("X-Signature : " + xSignature);

					refundBean = RefundApi.ConnectToTouchnGoAndShopeePay(tngtransactionId, amount, currencyType, reason,
							refundType, merchantId, service, xSignature);

					if (refundBean != null) {
						if ("SUCCESSFUL".equalsIgnoreCase(refundBean.getMessage())) {
							RefundRequest refundRequest = new RefundRequest();
							refundRequest.setMerchantName(currentMerchant.getBusinessName());
							refundRequest.setAccNo(currentMerchant.getBankAcc());
							refundRequest.setInitiateDate(getCurrentDateStringFormat());
							refundRequest.setRequestRefundAmount(refundAmount);
							refundRequest.setSettlementDate(settlementDate);
							refundRequest.setTxnId(refundBean.getTransactionId());
							refundRequest.setMid(Long.toString(currentMerchant.getId()));
							refundRequest.setProductType("TOUCH_N_GO");
							refundRequest.setStatus("TPR");
							refundRequest.setTransactionType("refund");

							merchantService.saveRefundRequest(refundRequest);
							merchantService.updateTouchnGoRefundStatus(transactionId, ewalletTxnDetails.getInvoiceId());
							RefundApi.sendRefundNotificationEmail(refundRequest,
									getFormatTimestampStringFormat(ewalletTxnDetails.getTimeStamp()),
									currentMerchant.getEmail());
							refundBean.setTransactionId(transactionId);
							refundBean.setAmount(refundAmount);
							refundBean.setResponseCode("0000");
							refundBean.setResponseDescription("Touch'n Go API refund completed successfully");
							logger.info("Touch'n Go API refund completed successfully");
						} else {
							refundBean.setResponseCode("0001");
							refundBean
									.setResponseDescription("Touch'n Go API refund status: " + refundBean.getMessage());
							logger.info("Touch'n Go API refund status: {}" + refundBean.getMessage());
						}
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription("Error processing Touch'n Go API refund. Response is null.");
						logger.error("Error processing Touch'n Go API refund. Response is null.");
					}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@RequestMapping(value = "refund/spp", method = RequestMethod.GET)
	@SuppressWarnings({ "nls", "unqualified-field-access", "null" })
	public @ResponseBody RefundBean processShopeePayRefund(@RequestParam String transactionId,
			@RequestParam String refundAmount, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating ShopeePay Refund by: {}" + userName);

		logger.info("Transaction ID: {}" + transactionId);
		logger.info("Refund Amount: {}" + refundAmount);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		RefundBean refundBean = new RefundBean();
		refundBean.setResponseCode("0000"); // Default success response code

		EwalletTxnDetails ewalletTxnDetails = merchantService.findbyTouchnGoAndShopeePayData(transactionId);

		if (ewalletTxnDetails != null && "SPS".equalsIgnoreCase(ewalletTxnDetails.getStatus())) {

			if (isValidSettlementDate(ewalletTxnDetails.getSettledDate())) {

				Map<String, Object> refundValidationResult = isRefundAmountCovered(refundAmount, currentMerchant);

				boolean isRefundCovered = (boolean) refundValidationResult.get("isCovered");
				String settlementDate = (String) refundValidationResult.get("settlementDate");

				if (isRefundCovered) {
					logger.info("Proceeding with ShopeePay API refund");

					String tngtransactionId = ewalletTxnDetails.getTngTxnId();
					String amount = refundAmount;
					String currencyType = PropertyLoad.getFile().getProperty("TNG_SPP_CURRENCY_TYPE");
					String reason = PropertyLoad.getFile().getProperty("TNG_SPP_REASON");
					String refundType = PropertyLoad.getFile().getProperty("TNG_SPP_REFUND_TYPE");
					String service = PropertyLoad.getFile().getProperty("TNG_SPP_SERVICE");
					String merchantId = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_MERCHANTID")
									: PropertyLoad.getFile().getProperty("TNG_SPP_MERCHANTID");

					String keyPath = currentMerchant.getUsername()
							.equals(PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_USERNAME"))
									? PropertyLoad.getFile().getProperty("TNG_SPP_MNTX_KEYPATH")
									: PropertyLoad.getFile().getProperty("TNG_SPP_KEYPATH");

					StringBuilder sb = new StringBuilder();
					sb.append(tngtransactionId).append("|");
					sb.append(amount).append("|");
					sb.append(currencyType).append("|");
					sb.append(reason).append("|");
					sb.append(refundType).append("|");
					sb.append(merchantId);

					logger.info("Key Path : " + keyPath);

					logger.info("Sign data : " + sb.toString());

					String xSignature = null;

					xSignature = RefundEncrypt.tngSignData(keyPath, sb.toString());

					logger.info("X-Signature : " + xSignature);

					refundBean = RefundApi.ConnectToTouchnGoAndShopeePay(tngtransactionId, amount, currencyType, reason,
							refundType, merchantId, service, xSignature);

					if (refundBean != null) {
						if ("SUCCESSFUL".equalsIgnoreCase(refundBean.getMessage())) {
							RefundRequest refundRequest = new RefundRequest();
							refundRequest.setMerchantName(currentMerchant.getBusinessName());
							refundRequest.setAccNo(currentMerchant.getBankAcc());
							refundRequest.setInitiateDate(getCurrentDateStringFormat());
							refundRequest.setRequestRefundAmount(refundAmount);
							refundRequest.setSettlementDate(settlementDate);
							refundRequest.setTxnId(refundBean.getTransactionId());
							refundRequest.setMid(Long.toString(currentMerchant.getId()));
							refundRequest.setProductType("SHOPEE_PAY");
							refundRequest.setStatus("SPR");
							refundRequest.setTransactionType("refund");

							merchantService.saveRefundRequest(refundRequest);
							merchantService.updateShopeePayRefundStatus(transactionId,
									ewalletTxnDetails.getInvoiceId());
							RefundApi.sendRefundNotificationEmail(refundRequest,
									getFormatTimestampStringFormat(ewalletTxnDetails.getTimeStamp()),
									currentMerchant.getEmail());
							refundBean.setTransactionId(transactionId);
							refundBean.setAmount(refundAmount);
							refundBean.setResponseCode("0000");
							refundBean.setResponseDescription("ShopeePay API refund completed successfully");
							logger.info("ShopeePay API refund completed successfully");
						} else {
							refundBean.setResponseCode("0001");
							refundBean
									.setResponseDescription("ShopeePay API refund status: " + refundBean.getMessage());
							logger.info("ShopeePay API refund status: {}" + refundBean.getMessage());
						}
					} else {
						refundBean.setResponseCode("0001");
						refundBean.setResponseDescription("Error processing ShopeePay API refund. Response is null.");
						logger.error("Error processing ShopeePay API refund. Response is null.");
					}

				} else {
					refundBean.setResponseCode("0002");
					refundBean.setResponseDescription("Refund Amount not covered in the next Settlement 5 days");

					logger.info("Refund Amount not covered in the next Settlement 5 days");
				}
			} else {
				refundBean.setResponseCode("0001");
				refundBean.setResponseDescription("Transaction not eligible for a refund");
			}
		} else {
			refundBean.setResponseCode("0001");
			refundBean.setResponseDescription("Internal Server Error");
		}

		return refundBean;
	}

	@SuppressWarnings("nls")
	private Map<String, Object> isRefundAmountCovered(String refundAmount, Merchant currentMerchant) {
		double requestAmount = Double.parseDouble(refundAmount);
		Map<String, Object> result = new HashMap<>();

		List<String> settlementDates = getRecentSettlementDates();

		for (String settlementDate : settlementDates) {
			double totalNetAmount = getTotalNetAmount(settlementDate, currentMerchant);
			double totalRefundAmountProcessed = getTotalRefundAmountProcessed(settlementDate, currentMerchant.getId());

			logger.info("Settlement Date = {}, Net Amount = {}" + settlementDate + ", " + totalNetAmount);

			if (requestAmount <= (totalNetAmount - totalRefundAmountProcessed)) {
				logger.info(
						"Refund Amount Covers this Settlement Amount = {}. Fully Covered in this Settlement Date: {}"
								+ totalNetAmount + ", " + settlementDate);
				result.put("settlementDate", settlementDate);
				result.put("isCovered", true);
				return result;
			} else {
				logger.info("Refund Amount does not cover the Settlement Amount for this date: {}" + totalNetAmount
						+ ", " + settlementDate);
			}
		}

		result.put("settlementDate", null);
		result.put("isCovered", false);
		return result;
	}

	@SuppressWarnings({ "unqualified-field-access", "nls" })
	private double getTotalNetAmount(String settlementDate, Merchant currentMerchant) {
		List<SettlementMDR> settlementDataCard = transactionService.loadNetAmountandsettlementdatebyCard(settlementDate,
				currentMerchant);
		List<BoostDailyRecon> settlementDataBoost = transactionService
				.loadNetAmountandsettlementdatebyBoost(settlementDate, currentMerchant);
		List<GrabPayFile> settlementDataGrabpay = transactionService
				.loadNetAmountandsettlementdatebyGrabpay(settlementDate, currentMerchant);
		List<FpxTransaction> settlementDataFpx = transactionService.loadNetAmountandsettlementdatebyFpx(settlementDate,
				currentMerchant);
		List<EwalletTxnDetails> settlementDataM1Pay = transactionService
				.loadNetAmountandsettlementdatebym1Pay(settlementDate, currentMerchant);

		double totalNetAmount = settlementDataCard.stream().mapToDouble(data -> parseDouble(data.getNetAmount())).sum()
				+ settlementDataBoost.stream().mapToDouble(data -> parseDouble(data.getNetAmount())).sum()
				+ settlementDataGrabpay.stream().mapToDouble(data -> parseDouble(data.getNetAmt())).sum()
				+ settlementDataFpx.stream().mapToDouble(data -> parseDouble(data.getPayableAmt())).sum()
				+ settlementDataM1Pay.stream().mapToDouble(data -> parseDouble(data.getPayableAmt())).sum();

		logger.info("Total Net Amount for Settlement Date {}: {}" + settlementDate + ", " + totalNetAmount);

		return totalNetAmount;
	}

	@SuppressWarnings({ "unqualified-field-access", "nls" })
	private double getTotalRefundAmountProcessed(String settlementDate, Long merchantId) {

		String totalRefundAmountProcessed = transactionService.getTotalRefundAmountProcessed(settlementDate,
				Long.toString(merchantId));

		double totalRefundAmount = Double.parseDouble(totalRefundAmountProcessed);

		logger.info("Total Refund Net Amount for Settlement Date {}: {}" + settlementDate + ", " + totalRefundAmount);

		return totalRefundAmount;
	}

	@SuppressWarnings({ "unqualified-field-access", "nls" })
	private List<String> getRecentSettlementDates() {
		String currentDate = getCurrentDateStringFormat();
		List<String> settlementDates = transactionService.tocheckholiday(currentDate, "");
		logger.info("Recent Settlement Dates: {}" + settlementDates);
		return settlementDates;
	}

	@SuppressWarnings("nls")
	public static String FormatYYYY_MM_DD(String incomingDate, String transactionType) {
		DateTimeFormatter originalFormatter = null;

		if (transactionType.equalsIgnoreCase("Boost")) {
			originalFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
		}

		if (transactionType.equalsIgnoreCase("GrabPay")) {
			originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		}

		if (transactionType.equalsIgnoreCase("All")) {
			originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		}

		LocalDateTime originalDateTime = LocalDateTime.parse(incomingDate, originalFormatter);
		DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = originalDateTime.format(targetFormatter);

		logger.info("Formatted Date ({}): {}" + transactionType + ", " + formattedDate);

		return formattedDate;
	}

	@SuppressWarnings("nls")
	public static boolean isValidSettlementDate(String incomingDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(incomingDate, formatter);
		LocalDate currentDate = LocalDate.now();

		boolean status = date.isBefore(currentDate);

		logger.info("Is Settlement Date {} in the past? {}" + incomingDate + ", " + status);

		return status;
	}

	@SuppressWarnings("nls")
	public static String getCurrentDateStringFormat() {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = localDate.format(formatter);

		logger.info("Current Date: {}" + formattedString);

		return formattedString;
	}

	@SuppressWarnings("nls")
	public static String getFormatTimestampStringFormat(String timeStamp) throws ParseException {

		String formattedString = new SimpleDateFormat("dd-MMM-yyyy")
				.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp));
		return formattedString;

	}

	@SuppressWarnings("nls")
	private static double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			logger.error("Error parsing double value: {}" + value, e);
			return 0.0; // Default value or throw an exception based on your requirements
		}
	}

}
