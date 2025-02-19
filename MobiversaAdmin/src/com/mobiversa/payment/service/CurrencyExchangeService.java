package com.mobiversa.payment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.payment.dto.AjaxResponseBody;
import com.mobiversa.payment.dto.FpxStatusRequest;
import com.mobiversa.payment.dto.MerchantStatusResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.SettlementDetails;
import com.mobiversa.common.bo.WithdrawalTransactionsDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.TransactionDao;
import com.mobiversa.payment.dto.ExchangeRateResponse;
import com.mobiversa.payment.dto.ExchangeRateResponse.ExchangeRateData;
import com.mobiversa.payment.util.CurrencyType;
import com.mobiversa.payment.util.CurrencyTypeUtils;
import com.mobiversa.payment.util.ElasticEmailClient;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.PropertyLoader;

@Service
@SuppressWarnings("nls")
public class CurrencyExchangeService {
	
	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private MerchantDao merchantDao;

	private Map<String,Short> FPXIPNHITCOUNT = new ConcurrentHashMap<>();

	static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final Logger logger = Logger.getLogger(CurrencyExchangeService.class);

	public ExchangeRateResponse getExchangeRates() {
		try {
			final String currencyExchangeApiUrl = PropertyLoad.getFile().getProperty("CURRENCY_EXCHANGE_API_URL");

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<ExchangeRateResponse> responseEntity = restTemplate.getForEntity(currencyExchangeApiUrl,
					ExchangeRateResponse.class);
			int statusCode = responseEntity.getStatusCode().value();
			ExchangeRateResponse exchangeRateResponse = responseEntity.getBody();

			logger.info("Currency exchange API triggered - URL: " + currencyExchangeApiUrl + ", ResponseCode: "
					+ statusCode + ", ResponseData: " + exchangeRateResponse);

			if (exchangeRateResponse == null || exchangeRateResponse.getData() == null) {
				return null;
			}

	        // Filter the data
	        List<ExchangeRateData> filteredData = exchangeRateResponse.getData().stream()
	                .filter(data -> data.getDate().equals(LocalDate.now().format(formatter))
	                        && "MYR".equals(data.getBaseCurrency())) //$NON-NLS-1$
	                .collect(Collectors.toList());

	        // Create a new ExchangeRateResponse with the filtered data
	        ExchangeRateResponse result = new ExchangeRateResponse();
	        result.setStatus(exchangeRateResponse.getStatus());
	        result.setMessage(exchangeRateResponse.getMessage());
	        result.setData(filteredData);

	        return result;
		} catch (Exception e) {
			logger.error("Exception in currency exchange API: " + e.getMessage() + "," + e);
			return null;
		}
	}

	public Map<String, Double> getExchangeRateAsMap(ExchangeRateResponse exchangeRates) {
		// Map with targetCurrency as key and exchangeRate as value
		Map<String, Double> exchangeRateMap = new HashMap<>();
		if (exchangeRates == null || exchangeRates.getData() == null) {
			logger.error("ExchangeRateResponse or its data is null:" + exchangeRates);
			return exchangeRateMap;
		}

		for (ExchangeRateResponse.ExchangeRateData data : exchangeRates.getData()) {
			if (data != null) {
				exchangeRateMap.put(data.getTargetCurrency(), data.getExchangeRate());
			} else {
				logger.warn("Encountered null ExchangeRateData: " + exchangeRates);
			}
		}

		return exchangeRateMap;
	}
	
	public static boolean isAllCountryRatesArePresent(Map<String, Double> exchangeRateMapForAllCountries) {
	    // Check if at least one of the currencies (excluding MYR) is present
	    for (CurrencyType currency : CurrencyType.values()) {
	        if (currency != CurrencyType.MYR && exchangeRateMapForAllCountries.containsKey(currency.name())) {
	            return true; // At least one currency rate is present
	        }
	    }
	    return false;
	}
	
	public static boolean isAnyCountryRatePresent(Map<String, Double> exchangeRateMapForAllCountries) {
		try {
			// Check if the exchangeRateMapForAllCountries map is empty
			if (exchangeRateMapForAllCountries.isEmpty()) {
				return false;
			}

			// Check if at least one of the currencies (excluding MYR) is present
			for (CurrencyType currency : CurrencyType.values()) {
				if (currency != CurrencyType.MYR && exchangeRateMapForAllCountries.containsKey(currency.name())) {
					return true; // At least one currency rate is present
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static Map<String, Double> getFormattedExchangeRateMap(Map<String, Double> exchangeRateMapForAllCountries, String merchantId) {
		
		logger.info("Received raw exchange rate map for all countries: " + exchangeRateMapForAllCountries + ", Merchant ID: " + merchantId);
		
		return exchangeRateMapForAllCountries.entrySet().stream()
				.filter(entry -> !"EXCHANGE_RATE".equals(entry.getKey())) // Exclude the EXCHANGE_RATE key
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
//	    Double finalExchangeRateMultiplier = exchangeRateFee;
//	    
//	    // Multiplying all values except for EXCHANGE_RATE
//	    Map<String, Double> formattedExchangeRateMap = exchangeRateMapForAllCountries.entrySet().stream()
//	            .filter(entry -> !"EXCHANGE_RATE".equals(entry.getKey())) // Exclude the EXCHANGE_RATE key 
//				.collect(Collectors.toMap(
//						entry -> CurrencyTypeUtils.formatCurrencyType(CurrencyType.valueOf(entry.getKey())), // Format the Currency Type
//	                    entry -> entry.getValue() * finalExchangeRateMultiplier // Multiply by the exchange rate multiplier
//						)
//					);
//	    
//	    logger.info("Formatted exchange rate map with applied multiplier: " + formattedExchangeRateMap + ". Associated Merchant ID: " + merchantId);
//		return formattedExchangeRateMap;
	}
	
	public static Double getExchangeRateFee(Map<String, Double> exchangeRateMapForAllCountries, String merchantId) {
        // Get the exchange rate fee, ensure it's at least defaultExchangerate from config.
        Double exchangeRateFee = exchangeRateMapForAllCountries.getOrDefault("EXCHANGE_RATE", 0.0);
        Double defaultExchangerate = Double.valueOf(PropertyLoad.getFile().getProperty("DEFAULT_EXCHANGE_RATE").replace(",", "").trim());

        if (exchangeRateFee < defaultExchangerate) {
            // Get default value if not available or less than default value.
			logger.warn("Exchange rate MDR is {" + exchangeRateFee + "} < defaultExchangeRate {" + defaultExchangerate
					+ "}. Considering default value: " + defaultExchangerate + ". Associated Merchant ID: "
					+ merchantId);
            exchangeRateFee = defaultExchangerate;
        }

        return exchangeRateFee;
    }
	
	public static Double getUsdtExchangeRateFee(Map<String, Double> exchangeRateMapForAllCountries, String merchantId) {
//        // Get the exchange rate fee, ensure it's at least defaultExchangerate from config.
//        Double usdtExchangeRateFee = exchangeRateMapForAllCountries.getOrDefault("USDT_EXCHANGE_RATE", 0.0);
//        Double defaultUsdtExchangerate = Double.valueOf(PropertyLoad.getFile().getProperty("USDT_DEFAULT_EXCHANGE_RATE").replace(",", "").trim());
//
//        if (usdtExchangeRateFee < defaultUsdtExchangerate) {
//            // Get default value if not available or less than default value.
//			logger.warn("USDT Exchange rate MDR is {" + usdtExchangeRateFee + "} < defaultExchangeRate {" + defaultUsdtExchangerate
//					+ "}. Considering default value: " + defaultUsdtExchangerate + ". Associated Merchant ID: "
//					+ merchantId);
//			usdtExchangeRateFee = defaultUsdtExchangerate;
//        }
//
//        return usdtExchangeRateFee;
		
		return 0.0;
    }
	
    public static Map<String, String> getCurrencyCodeMapWithAbbreviation(Map<String, Double> exchangeRateMapForAllCountries, String string) {
        return exchangeRateMapForAllCountries.entrySet().stream()
			.filter(entry -> !"EXCHANGE_RATE".equals(entry.getKey())) // Exclude the EXCHANGE_RATE key
            .filter(entry -> !"MYR".equals(entry.getKey())) // Exclude the MYR key
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> CurrencyTypeUtils.formatCurrencyType(CurrencyType.valueOf(entry.getKey())) // Format the Currency Type
            ));
    }

	public boolean isWithinWorkingHours() {
		
        String currentYear = String.valueOf(LocalDate.now().getYear());
        List<String> publicHolidays = this.transactionDao.getHolidayHistoryList(currentYear);
        
		return isWithinWorkingHours(publicHolidays, currentYear);
	}
	
	public static boolean isWithinWorkingHours(List<String> publicHolidays, String currentYear) {
		
		LocalDateTime now = LocalDateTime.now();
	    LocalDate currentDate = now.toLocalDate();
	    LocalTime currentTime = now.toLocalTime();
		
	    logger.info("As of " + now + ", public holidays in the year " + currentYear + " are: " + publicHolidays);
	    	    
		// Define the working hours
	    LocalTime startWorkingTime = LocalTime.of(8, 10); // 08:10 AM
	    LocalTime endWorkingTime = LocalTime.of(11, 0);  // 11:00 AM

	    // For UAT - Need to disable in live.
//	    endWorkingTime = LocalTime.of(23, 59);  // 11:59 AM

	    // Check if the current date is a public holiday
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    if (publicHolidays.contains(currentDate.format(dateFormatter))) {
	        return false;
	    }

	    // Check if the current date is a weekend (Saturday or Sunday)
	    DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
	    if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
	        return false;
	    }
	    
	    // Check if the current time is within working hours
	    if (currentTime.isAfter(startWorkingTime) && currentTime.isBefore(endWorkingTime)) {
	        return true;
	    }

	    return false;
	}

	public double getexchangeRateMDR(String merchantId) {
        // Get the exchange rate fee, ensure it's at least defaultExchangerate from config.
		Map<String, Double> exchangeRateMapForAllCountries = this.transactionDao.getExchangeRatesForCurrentDay(CurrencyTypeUtils.getCurrentDate(), "MYR", merchantId);
        Double exchangeRateFee = exchangeRateMapForAllCountries.getOrDefault("EXCHANGE_RATE", 0.0);
        Double defaultExchangerate = Double.valueOf(PropertyLoad.getFile().getProperty("DEFAULT_EXCHANGE_RATE").replace(",", "").trim());

        if (exchangeRateFee < defaultExchangerate) {
            // Get default value if not available or less than default value.
			logger.warn("Exchange rate MDR is {" + exchangeRateFee + "} < defaultExchangeRate {" + defaultExchangerate
					+ "}. Considering default value: " + defaultExchangerate + ". Associated Merchant ID: "
					+ merchantId);
            exchangeRateFee = defaultExchangerate;
        }

        return exchangeRateFee;
	}
	
	public static String sendConfirmationEmailToFiannce(Merchant currentMerchant, double amountWithdrawInMYR,
			double exchangeRate, double exchangeRateMultiplier, String currency, String currentDateAndTime, String uuidString, double convertedAmount, double actualWithdrawAmountInMYR) {
		try {
			final DecimalFormat df = new DecimalFormat("#,##0.00");
			final DecimalFormat df4DigitsRoundOff = new DecimalFormat("#,##0.0000");
	
			String fromAddress = PropertyLoader.getFileData().getProperty("FROMMAIL");
			String toAddress = PropertyLoader.getFileData().getProperty("MERCHANT_WITHDRAW_MAIL_TO");
			String ccAddress = PropertyLoader.getFileData().getProperty("MERCHANT_WITHDRAW_MAIL_CC");
			String subject = "Request for Withdrawal Approval";
			String fromName = PropertyLoader.getFileData().getProperty("FROMNAME");

			String baseUrl = PropertyLoader.getFileData().getProperty("WITHDRAWAL_CURRENCY_EXCHANGE_APPROVAL_LINK");
			baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
			
			// Construct the links.
			String approvalLink = buildLink(currentMerchant.getId(), uuidString);
			String rejectionLink = buildLink(currentMerchant.getId(), uuidString);

			// Encode the approval link.
			String encodedApprovalLink = baseUrl + "approval" + encodeBase64(approvalLink);
			String encodedRejectionLink = baseUrl + "rejection" + encodeBase64(rejectionLink);

			StringBuilder emailContent = new StringBuilder()
			    .append("<!DOCTYPE html>")
			    .append("<html lang=\"en\">")
			    .append("<head>")
			    .append("<meta charset=\"UTF-8\">")
			    .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
			    .append("<title>Approval Request</title>")
			    .append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">")
			    .append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>")
			    .append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap\" rel=\"stylesheet\">")
			    .append("<style>")
			    .append("@media (max-width: 600px) {")
			    .append(".container {")
			    .append("padding: 15px;")
			    .append("margin: 20px auto !important;")
			    .append("}")
			    .append(".header td {")
			    .append("padding-left: 2px !important;")
			    .append("}")
			    .append(".header img {")
			    .append("width: 100px !important;")
			    .append("height: 50px !important;")
			    .append("}")
			    .append(".title {")
			    .append("font-size: 1.2rem !important;")
			    .append("margin-bottom: 10px !important;")
			    .append("}")
			    .append(".details td {")
			    .append("font-size: 10px !important;")
			    .append("padding: 6px !important;")
			    .append("padding-left: 2px !important;")
			    .append("}")
			    .append(".transaction-table th, .transaction-table td {")
			    .append("font-size: 10px !important;")
			    .append("padding: 6px;")
			    .append("}")
			    .append(".note, .contact {")
			    .append("font-size: 10px !important;")
			    .append("}")
			    .append("}")
			    .append("</style>")
			    .append("</head>")
			    .append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">")
			    .append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">")
			    .append("<tr>")
			    .append("<td>")

			    .append("<table class=\"header\" style=\"width: 100%; text-align: left;\">")
			    .append("<tr>")
			    .append("<td style=\"padding-left: 2px;\">")
			    .append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">")
			    .append("</td>")
			    .append("</tr>")
			    .append("</table>")

			    .append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #51CB49; font-size: 1.4rem !important; font-weight: bold; margin-bottom: 10px;\">")
			    .append("<tr>")
			    .append("<td>Withdrawal Request Review</td>")
			    .append("</tr>")
			    .append("</table>")

			    .append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">")
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Merchant Name</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px \">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px ; font-weight: normal; word-break: break-all; color: #333739;\">").append(currentMerchant.getBusinessName()).append("</td>")
			    .append("</tr>")
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Merchant</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px \">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px ; font-weight: normal; word-break: break-all; color: #333739;\">").append(currentMerchant.getId()).append("</td>")
			    .append("</tr>")
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Withdrawal Amount (MYR)</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px\">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;word-break: break-all;font-weight: normal; color: #333739;\">").append(df.format(actualWithdrawAmountInMYR)).append("</td>")
			    .append("</tr>")
			    			 			   
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Exchange Currency</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px\">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;word-break: break-all;font-weight: normal; color: #333739;\">").append(currency).append("</td>")
			    .append("</tr>")
			    
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Exchange Rate</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px\">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;word-break: break-all;font-weight: normal; color: #333739;\">").append(df4DigitsRoundOff.format(exchangeRate)).append("</td>")
			    .append("</tr>")
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Conversion Fee (MDR)</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px\">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;word-break: break-all;font-weight: normal; color: #333739;\">").append(exchangeRateMultiplier).append("%").append("</td>")
			    .append("</tr>")
			    
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Amount to be Paid").append(" (").append(currency.substring(currency.indexOf("(") + 1, currency.indexOf(")"))).append(")").append("</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px\">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;word-break: break-all;font-weight: normal; color: #333739;\">").append(df.format(convertedAmount)).append("</td>")
			    .append("</tr>")			    
			    			    
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Withdrawal Request Date</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px\">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;word-break: break-all;font-weight: normal; color: #333739;\">").append(separateDateAndTime(currentDateAndTime)[0]).append("</td>")
			    .append("</tr>")
			    
			    .append("<tr>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;font-weight: bold; white-space: nowrap; color: #333739;\">Time of Request</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px\">:</td>")
			    .append("<td style=\"vertical-align: baseline;padding: 5px;word-break: break-all;font-weight: normal; color: #333739;\">").append(separateDateAndTime(currentDateAndTime)[1]).append("</td>")
			    .append("</tr>")

			    
			    .append("</table>")
			    .append("<!-- Approval and Reject Buttons -->")
			    .append("<table style=\"width: 100%; margin-bottom: 20px;\">")
			    .append("<tr>")
			    .append("<td>")

//			    .append("<a href=").append(encodedApprovalLink).append(" style=\"background-color: #51CB49; font-size:14px;color: white; padding: 2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold;\">Approve</a>")
//			    .append("<a href=").append(encodedRejectionLink).append(" style=\"background-color: #ff4d4d; font-size:14px;  color: white; padding:2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold; margin-left: 20px;\">Reject</a>")

			    .append("<a href=\"").append(encodedApprovalLink).append("\" style=\"background-color: #51CB49; font-size:14px;color: white; padding: 2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold;\">Approve</a>")
			    .append("<a href=\"").append(encodedRejectionLink).append("\" style=\"background-color: #ff4d4d; font-size:14px; color: white; padding:2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold; margin-left: 20px;\">Reject</a>")
			    		
			    .append("</td>")
			    .append("</tr>")
			    .append("</table>")
			    .append("<!-- Note -->")
			    .append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
			    .append("<tr>")
			    .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Note</td>")
			    .append("</tr>")
			    .append("<tr>")
			    .append("<td style=\"margin: 5px 0; color: #333739;\">Please review the withdrawal details carefully  before <span style=\"font-weight:550\">approving</span> or <span style=\"font-weight:550\">rejecting</span> this request.</td>")
			    .append("</tr>")
			    .append("</table>")
			    
			    .append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
			    .append("<tr>")
			    .append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Need Help? Contact Information</td>")
			    .append("</tr>")
			    .append("<tr>")
			    .append("<td>")
			    .append("<a href=\"#\" style=\"text-decoration: none; color: #333739;cursor: text; pointer-events: none;\">csmobi@gomobi.io</a>, <a style=\"color: #333739 !important; text-decoration: none; cursor: text; pointer-events: none;\"> +60 10-970-7880</a>")
			    .append("</td>")
			    .append("</tr>")
			    .append("</table>")
			    .append("</td>")
			    .append("</tr>")
			    .append("</table>")
			    .append("</body>")
			    .append("</html>");
			
			
//			StringBuilder emailContent1 = new StringBuilder()
//
//			.append("<!DOCTYPE html>")
//			.append("<html lang=\"en\">")
//			.append("<head>")
//			.append("<meta charset=\"UTF-8\">")
//			.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
//			.append("<title>Approval Request</title>")
//			.append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">")
//			.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>")
//			.append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap\" rel=\"stylesheet\">")
//			.append("<style>")
//			.append(".modal { display: none; position: fixed; z-index: 1; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); justify-content: center; align-items: center; }")
//			.append(".modal-content { background-color: #fff; padding: 20px; border-radius: 10px; text-align: center; max-width: 400px; width: 100%; font-family: 'Poppins', sans-serif; }")
//			.append(".modal-header { font-size: 1.5rem; font-weight: bold; color: #333; margin-bottom: 10px; }")
//			.append(".modal-body { margin-bottom: 20px; }")
//			.append(".modal-footer { display: flex; justify-content: space-between; }")
//			.append(".modal-footer button { padding: 10px 20px; border: none; border-radius: 5px; font-size: 1rem; cursor: pointer; font-weight: bold; }")
//			.append(".modal-footer .confirm { background-color: #51CB49; color: #fff; }")
//			.append(".modal-footer .cancel { background-color: #ff4d4d; color: #fff; }")
//			.append("@media (max-width: 600px) { .container { padding: 15px; margin: 20px auto !important; }")
//			.append(".header img { width: 100px !important; height: 50px !important; }")
//			.append(".title { font-size: 1.2rem !important; margin-bottom: 10px !important; }")
//			.append(".details td { font-size: 10px !important; padding: 6px !important; }")
//			.append(".transaction-table th, .transaction-table td { font-size: 10px !important; }")
//			.append(".note, .contact { font-size: 10px !important; } }")
//			.append("</style>")
//			.append("</head>")
//			.append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">")
//			.append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">")
//			.append("<tr><td>")
//			.append("<table class=\"header\" style=\"width: 100%; text-align: left;\">")
//			.append("<tr><td style=\"padding-left: 2px;\">")
//			.append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">")
//			.append("</td></tr>")
//			.append("</table>")
//			.append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #51CB49; font-size: 1.4rem; font-weight: bold; margin-bottom: 10px;\">")
//			.append("<tr><td>Request for Withdrawal Approval</td></tr>")
//			.append("</table>")
//			.append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">")
//			.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Merchant Name</td><td style=\"padding: 5px;\">:</td>")
//			.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">{{merchantName}}</td>")
//			.append("</tr>")
//			.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">MID</td><td style=\"padding: 5px;\">:</td>")
//			.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">{{mid}}</td>")
//			.append("</tr>")
//			.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Amount withdrawn in MYR</td><td style=\"padding: 5px;\">:</td>")
//			.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">{{amountWithdrawInMYR}}</td>")
//			.append("</tr>")
//			.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Exchange rate</td><td style=\"padding: 5px;\">:</td>")
//			.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">{{exchangeRate}}</td>")
//			.append("</tr>")
//			.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">MDR rate for exchange</td><td style=\"padding: 5px;\">:</td>")
//			.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">{{exchangeRateMultiplier}}</td>")
//			.append("</tr>")
//			.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Currency</td><td style=\"padding: 5px;\">:</td>")
//			.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">{{currency}}</td>")
//			.append("</tr>")
//			.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Time of withdrawal</td><td style=\"padding: 5px;\">:</td>")
//			.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">{{currentDateAndTime}}</td>")
//			.append("</tr>")
//			.append("</table>")
//			.append("<table style=\"width: 100%; margin-bottom: 20px;\">")
//			.append("<tr><td>")
//			.append("<a href=\"{{approvalLink}}\" style=\"background-color: #51CB49; font-size:14px;color: white; padding: 2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold;\">Approve</a>")
//			.append("<a href=\"javascript:void(0);\" onclick=\"openRejectModal();\" style=\"background-color: #ff4d4d; font-size:14px; color: white; padding:2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold; margin-left: 20px;\">Reject</a>")
//			.append("</td></tr>")
//			.append("</table>")
//			.append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//			.append("<tr><td style=\"font-weight: bold; color: #333739;\">Note</td></tr>")
//			.append("<tr><td style=\"color: #333739;\">Please review the withdrawal request and approve or reject it as per your discretion.</td></tr>")
//			.append("</table>")
//			.append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">")
//			.append("<tr><td style=\"font-weight: bold; color: #333739;\">Contact Information</td></tr>")
//			.append("<tr><td><a href=\"#\" style=\"color: #333739; text-decoration: none;\">csmobi@gomobi.io</a>, <a style=\"color: #333739; text-decoration: none;\">+60123456789</a></td></tr>")
//			.append("</table>")
//			.append("</td></tr>")
//			.append("</table>")
//			.append("<div id=\"rejectModal\" class=\"modal\">")
//			.append("<div class=\"modal-content\">")
//			.append("<div class=\"modal-header\">Confirm Rejection</div>")
//			.append("<div class=\"modal-body\">")
//			.append("<p>Are you sure you want to reject the withdrawal request?</p>")
//			.append("<textarea id=\"rejectionReason\" rows=\"4\" placeholder=\"Enter rejection reason here...\" style=\"width: 100%; padding: 10px; border-radius: 5px; border: 1px solid #ccc;\"></textarea>")
//			.append("</div>")
//			.append("<div class=\"modal-footer\">")
//			.append("<button class=\"confirm\" onclick=\"submitRejection();\">Reject</button>")
//			.append("<button class=\"cancel\" onclick=\"closeRejectModal();\">Cancel</button>")
//			.append("</div>")
//			.append("</div>")
//			.append("</div>")
//			.append("<script>")
//			.append("function openRejectModal() { document.getElementById('rejectModal').style.display = 'flex'; }")
//			.append("function closeRejectModal() { document.getElementById('rejectModal').style.display = 'none'; }")
//			.append("function submitRejection() { var reason = document.getElementById('rejectionReason').value;")
//			.append("if(reason.trim() === '') { alert('Please provide a reason for rejection.'); return; }")
//			.append("alert('Request rejected with reason: ' + reason); closeRejectModal(); }")
//			.append("</script>")
//			.append("</body>")
//			.append("</html>");

						
			
//			StringBuilder emailContent1 = new StringBuilder();
//
//			emailContent1.append("<!DOCTYPE html>");
//			emailContent.append("<html lang=\"en\">");
//			emailContent.append("<head>");
//			emailContent.append("<meta charset=\"UTF-8\">");
//			emailContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
//			emailContent.append("<title>Approval Request</title>");
//			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
//			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
//			emailContent.append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap\" rel=\"stylesheet\">");
//			emailContent.append("<style>");
//			emailContent.append(".modal { display: none; position: fixed; z-index: 1; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.5); justify-content: center; align-items: center; }");
//			emailContent.append(".modal-content { background-color: #fff; padding: 20px; border-radius: 10px; text-align: center; max-width: 400px; width: 100%; font-family: 'Poppins', sans-serif; }");
//			emailContent.append(".modal-header { font-size: 1.5rem; font-weight: bold; color: #333; margin-bottom: 10px; }");
//			emailContent.append(".modal-body { margin-bottom: 20px; }");
//			emailContent.append(".modal-footer { display: flex; justify-content: space-between; }");
//			emailContent.append(".modal-footer button { padding: 10px 20px; border: none; border-radius: 5px; font-size: 1rem; cursor: pointer; font-weight: bold; }");
//			emailContent.append(".modal-footer .confirm { background-color: #51CB49; color: #fff; }");
//			emailContent.append(".modal-footer .cancel { background-color: #ff4d4d; color: #fff; }");
//			emailContent.append("@media (max-width: 600px) { .container { padding: 15px; margin: 20px auto !important; }");
//			emailContent.append(".header td { padding-left: 2px !important; }");
//			emailContent.append(".header img { width: 100px !important; height: 50px !important; }");
//			emailContent.append(".title { font-size: 1.2rem !important; margin-bottom: 10px !important; }");
//			emailContent.append(".details td { font-size: 10px !important; padding: 6px !important; padding-left: 2px !important; }");
//			emailContent.append(".transaction-table th, .transaction-table td { font-size: 10px !important; padding: 6px; }");
//			emailContent.append(".note, .contact { font-size: 10px !important; }");
//			emailContent.append("#rejectionReason:focus, #rejectionReason:active { border: 1px solid #005baa; } }");
//			emailContent.append("</style>");
//			emailContent.append("</head>");
//			emailContent.append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">");
//			emailContent.append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">");
//
//			emailContent.append("<tr><td>");
//			emailContent.append("<table class=\"header\" style=\"width: 100%; text-align: left;\"><tr><td style=\"padding-left: 2px;\">");
//			emailContent.append("<img src=\"https://san.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">");
//			emailContent.append("</td></tr></table>");
//
//			emailContent.append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #51CB49; font-size: 1.4rem; font-weight: bold; margin-bottom: 10px;\">");
//			emailContent.append("<tr><td>Request for Withdrawal Approval</td></tr>");
//			emailContent.append("</table>");
//
//			emailContent.append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">");
//			// Dynamic content starts here (merchant, MID, amount, exchange rate, etc.)
//			emailContent.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Merchant Name</td><td style=\"padding: 5px;\">:</td>");
//			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">").append(currentMerchant.getBusinessName()).append("</td></tr>");
//
//			emailContent.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">MID</td><td style=\"padding: 5px;\">:</td>");
//			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">").append(currentMerchant.getId()).append("</td></tr>");
//
//			emailContent.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Amount withdrawn in MYR</td><td style=\"padding: 5px;\">:</td>");
//			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">").append(amountWithdrawInMYR).append("</td></tr>");
//
//			emailContent.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Exchange rate</td><td style=\"padding: 5px;\">:</td>");
//			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">").append(exchangeRate).append("</td></tr>");
//
//			emailContent.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">MDR rate for exchange</td><td style=\"padding: 5px;\">:</td>");
//			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">").append(exchangeRateMultiplier).append("</td></tr>");
//
//			emailContent.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Currency</td><td style=\"padding: 5px;\">:</td>");
//			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">").append(currency).append("</td></tr>");
//
//			emailContent.append("<tr><td style=\"padding: 5px;font-weight: bold; color: #333739;\">Time of withdrawal</td><td style=\"padding: 5px;\">:</td>");
//			emailContent.append("<td style=\"padding: 5px; font-weight: bold; color: #333739;\">").append(currentDateAndTime).append("</td></tr>");
//			emailContent.append("</table>");
//
//			emailContent.append("<table style=\"width: 100%; margin-bottom: 20px;\"><tr><td>");
//			emailContent.append("<a href=\"").append(approvalLink).append("\" style=\"background-color: #51CB49; font-size:14px;color: white; padding: 2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold;\">Approve</a>");
//			emailContent.append("<a href=\"javascript:void(0);\" onclick=\"openModal();\" style=\"background-color: #ff4d4d; font-size:14px;  color: white; padding:2% 10%; border-radius: 5px; text-decoration: none; font-weight: bold; margin-left: 20px;\">Reject</a>");
//			emailContent.append("</td></tr></table>");
//
//			// Note and contact information
//			emailContent.append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
//			emailContent.append("<tr><td style=\"font-weight: bold; color: #333739;\">Note</td></tr>");
//			emailContent.append("<tr><td style=\"color: #333739;\">Please review the withdrawal request and approve or reject it as per your discretion.</td></tr>");
//			emailContent.append("</table>");
//
//			emailContent.append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
//			emailContent.append("<tr><td style=\"font-weight: bold; color: #333739;\">Contact Information</td></tr>");
//			emailContent.append("<tr><td><a href=\"#\" style=\"color: #333739; text-decoration: none;\">csmobi@gomobi.io</a>, <a style=\"color: #333739; text-decoration: none;\">+60123456789</a></td></tr>");
//			emailContent.append("</table>");
//			emailContent.append("</td></tr>");
//			emailContent.append("</table>");
//
//			emailContent.append("<script>");
//			emailContent.append("function openModal() { document.getElementById('myModal').style.display = 'block'; }");
//			emailContent.append("function closeModal() { document.getElementById('myModal').style.display = 'none'; }");
//			emailContent.append("</script>");
//
//			emailContent.append("</body>");
//			emailContent.append("</html>");				

			
			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress,
					null, emailContent.toString());

			logger.info(String.format(
				    "Triggering currency exchange withdraw email for merchantId: %s, fromAddress: %s, toAddress: %s, subject: %s. Sent Successfully: %d",
				    currentMerchant.getId(), fromAddress, toAddress, subject, mailResponse));

			return String.valueOf(mailResponse);
		} catch (Exception pe) {
			pe.getMessage();
		    logger.error("Exception while sending mail: " + pe.getMessage(), pe);
			return "400";
		}
	}

	private static String[] separateDateAndTime(String dateTime) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String[] dateTimeParts = new String[2];

		try {
			Date date = inputFormat.parse(dateTime);
			dateTimeParts[0] = dateFormat.format(date);
			dateTimeParts[1] = timeFormat.format(date);
		} catch (ParseException e) {
			dateTimeParts[0] = "N/A";
			dateTimeParts[1] = "N/A";
		}

		return dateTimeParts;
	}

	// Create the approval or rejection link.
	private static String buildLink(Long merchantId, String uuid) {
		return merchantId + "/" + uuid;
	}

	// Base64 encode a string.
	private static String encodeBase64(String input) {
		return "/" + Base64.getEncoder().encodeToString(input.getBytes());
	}
	
    // Method to convert a date string in "dd MMMM yyyy HH:mm:ss" format to java.sql.Timestamp
	private static Timestamp convertStringToTimestamp(String dateString) {

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        return Timestamp.valueOf(localDateTime);
    }

	public void updateWithdrawDetails(Merchant currentMerchant, double amountWithdrawInMYR, double exchangeRate,
			double exchangeRateFee, String currency, String currentDateAndTime, String uuidString, String withdrawalComment,
			double convertedAmount, double actualWithdrawAmountInMYR) {
		
		final DecimalFormat df = new DecimalFormat("#,##0.00");
		final DecimalFormat df4DigitsRoundOff = new DecimalFormat("#,##0.0000");
		
		// Get AvaliableBalance View data by mercahntId
		Double avaliableBalanceBeforeWithdraw = this.merchantDao.getAvaliableBalanceByMerchantId(currentMerchant.getId().toString());
		
		// Make a entry in WithdrawalTransactionsDetails table
		WithdrawalTransactionsDetails withdrawDetails = new WithdrawalTransactionsDetails();
		withdrawDetails.setCreatedDate(convertStringToTimestamp(currentDateAndTime));
		withdrawDetails.setMerchantId(currentMerchant.getId().toString());
		withdrawDetails.setPayoutFundDisbursmentStatus("Initiated");
		
		withdrawDetails.setWithdrawAmount(df.format(actualWithdrawAmountInMYR).replace(",", ""));
		withdrawDetails.setTotalWithdrawAmount(df.format(amountWithdrawInMYR).replace(",", ""));
		withdrawDetails.setExchangeRate(df4DigitsRoundOff.format(exchangeRate).replace(",", ""));		
		withdrawDetails.setConversionChargeRate(df.format(exchangeRateFee).replace(",", ""));
		withdrawDetails.setCurrencyCode(currency);
		withdrawDetails.setConvertedAmount(df.format(convertedAmount).replace(",", ""));
		
		withdrawDetails.setPayoutId(uuidString);
		withdrawDetails.setComment(withdrawalComment);
		withdrawDetails.setPreWithdrawAvailableBalance(df.format(avaliableBalanceBeforeWithdraw).replace(",", ""));
		withdrawDetails.setWithdrawType("CurrencyExchangeWithdraw");
		this.merchantDao.saveWithdrawalTransactionDetailsTable(withdrawDetails);

//		// Get WithdrawalTransactionsDetails by PayoutId
//		WithdrawalTransactionsDetails withdrawDetail = merchantDao.getWithdrawalTransactionsDetailsByPayoutId(uuidString);
//
//		// Make a entry in BalanceAudit table
//		BalanceAudit balanceAudit = new BalanceAudit();
//		balanceAudit.setMerchantId(currentMerchant.getId().toString());
//		balanceAudit.setTransactionType("Withdrawal");
//		balanceAudit.setSettlementDate(currentDate);
//		balanceAudit.setAmount(withdrawAmount);
//		balanceAudit.setWithdrawalMapping(String.valueOf(withdrawDetail.getId()));
//		merchantDao.saveBalanceAuditTable(balanceAudit);
//
//		// Double check the WithdrawalTransactionsDetails_Table & BalanceAudit_Table has
//		// been Updated
//		if (Objects.isNull(withdrawDetail)) {
//			logger.error("Withdraw hasn't been updated in PayoutDepositDetails table for merchantId: " + merchantId
//					+ ", " + merchant.getBusinessName() + ", PayoutID" + payoutID);
//		} else {
//			BalanceAudit balanceAuditIsUpdated = merchantDao
//					.checkBalanceAuditEntryUpdatedByWithdrawalMapping(String.valueOf(withdrawDetail.getId()));
//
//			if (Objects.isNull(balanceAuditIsUpdated)) {
//				logger.error("Withdraw hasn't been updated in BalanceAudit table for merchantId: " + merchantId
//						+ "," + merchant.getBusinessName() + ", PayoutID:" + payoutID + ", BalanceAudit: "
//						+ balanceAuditIsUpdated);
//			} else {
//				isUpdated = true;
//			}
//		}
//
//		if (isUpdated) {
//			// If WithdrawalTransactionsDetails_Table is updated update the
//			// postAvaliableBalance
//			WithdrawalTransactionsDetails withdrawDetailToUpdatePostWithdraw = new WithdrawalTransactionsDetails();
//			withdrawDetailToUpdatePostWithdraw.setId(withdrawDetail.getId());
//			withdrawDetailToUpdatePostWithdraw.setPostWithdrawAvailableBalance(
//					String.valueOf(merchantDao.getAvaliableBalanceByMerchantId(merchantId)));
//			merchantDao.updateWithdrawalTransactionDetailsTable(withdrawDetailToUpdatePostWithdraw);
//
//			// Trigger an acknowledgment email.
//			Double avaliableBalanceAfterWithdraw = avaliableBalanceBeforeWithdraw - Double.valueOf(withdrawAmount);
//			String mailResponse = sendEmailtoFinance_PayoutRefactored(payoutID, withdrawAmount, merchantId,
//					bankBalance, merchant, avaliableBalanceBeforeWithdraw, avaliableBalanceAfterWithdraw,
//					withdrawDetails.getWithdrawType(), withdrawDetails.getComment());
//		} else {
//			logger.error("Withdraw update has failed. No acknowledgment email sent.");
//		}
	}

	public static void setWithdrawModelAttributes(Model model, SettlementDetails settlementDetails, boolean isOverdraftAvailable, 
	                                boolean enableCurrencyExchange, Double availableBalance, 
	                                double eligibleSettlementAmount, Map<String, Double> exchangeRateMapWithConversionFee, 
	                                Map<String, String> currencyCodesMapWithAbbreviation, String jsonResponse, 
	                                String adminUserName, Merchant currentMerchant, Principal principal, 
	                                boolean isFinanceWithdrawEnabled, PageBean pageBean, Map<String, Object> response) {
		
		final DecimalFormat df = new DecimalFormat("#,##0.00");
	    
	    model.addAttribute("availableBalance", (availableBalance == null) ? "0.00" : df.format(availableBalance))
			 .addAttribute("overdraftLimit",
						settlementDetails == null || settlementDetails.getOverDraftLimit() == null ? "0.00"
								: df.format(Double.parseDouble(settlementDetails.getOverDraftLimit())))
	         .addAttribute("pageBean", pageBean)
	         .addAttribute("merchantid", currentMerchant.getId())
	         .addAttribute("adminusername", adminUserName)
	         .addAttribute("loginname", principal.getName())
	         .addAttribute("eligiblePayoutAmount", settlementDetails == null ? "0.00" : df.format(eligibleSettlementAmount))
	         .addAttribute("settlementDetails", settlementDetails)
	         .addAttribute("isOverdraftAvailable", isOverdraftAvailable)
	         .addAttribute("enableCurrencyExchange", enableCurrencyExchange)
	         .addAttribute("formattedExchangeRateMap", exchangeRateMapWithConversionFee)
	         .addAttribute("payoutBalancePageResponse", jsonResponse)
	         .addAttribute("rawPayoutBalancePageResponse", response)
	         .addAttribute("currencyCodesMapWithAbbreviation", currencyCodesMapWithAbbreviation)
	         .addAttribute("enableFinanceWithdraw", isFinanceWithdrawEnabled);
	}

	public static Map<String, Object> createResponseMap(Long merchantId, String merchantUserName, String adminUserName, 
            boolean isOverdraftAvailable, boolean enableCurrencyExchange, 
            String availableBalance, String eligibleSettlementAmount, 
            String overdraftAmount, String availableBalanceLastFetched, 
            Double exchangeRateFee, Map<String, String> currencyCodesMapWithAbbreviation, 
            Map<String, Double> conversionRates, boolean enableFinanceWithdraw,
            Double usdtexchangeRateFee) {
		
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				
			Map<String, Object> response = new HashMap<>();
			response.put("merchant_id", merchantId);
			response.put("login_username", merchantUserName);
			response.put("admin_username", adminUserName);
			response.put("is_overdraft_available", isOverdraftAvailable);
			response.put("enable_currency_exchange", enableCurrencyExchange);
			response.put("available_balance", availableBalance);
			response.put("eligible_settlement_amount", eligibleSettlementAmount);
			response.put("overdraft_amount", overdraftAmount);
			response.put("available_balance_last_fetched", availableBalanceLastFetched);
			response.put("base_currency", "MYR");
			response.put("base_currency_value", 1);
			response.put("exchange_rate_last_fetched", LocalDate.now().format(formatter));
			response.put("exchange_rate_fee", exchangeRateFee);
			response.put("usdt_exchange_rate_fee", usdtexchangeRateFee);
			response.put("currency_codes_map_with_abbreviation", currencyCodesMapWithAbbreviation);
			response.put("conversion_rates", conversionRates);
			response.put("enable_finance_withdraw", enableFinanceWithdraw);
			return response;
	}

//	public AjaxResponseBody sendFpxIpn(String sellerOrderNumber, String mid, String tid) throws IOException {
//			try {
//				FpxStatusRequest fpxStatusRequest = new FpxStatusRequest(mid, tid, sellerOrderNumber);
//				return callFpx(fpxStatusRequest);
//			}catch (RuntimeException e)
//			{
//				logger.error("Exception occurred while sending IPN  and the exception message is "+e.getMessage()+" Trace Of Exception  ",e);
//				return new AjaxResponseBody("0001",sellerOrderNumber,false,1);
//			}
//
//	}

	public AjaxResponseBody sendFpxIpnViaAdmin(String fpxTxnId) throws IOException {
		int clickCount = FPXIPNHITCOUNT.getOrDefault(fpxTxnId, (short)0);
		int allowedHit = Integer.parseInt(PropertyLoad.getFile().getProperty("FPX_IPN_MAX_HIT_COUNT"));
		if(clickCount >= allowedHit)
		{
			return new AjaxResponseBody("3Times",fpxTxnId,false,allowedHit-clickCount,null);
		}
		FPXIPNHITCOUNT.put(fpxTxnId, (short) ++clickCount);
		try {
			FpxTransaction fpxTransaction = transactionDao.loadFpxTransaction(fpxTxnId);
			FpxStatusRequest fpxStatusRequest = new FpxStatusRequest(fpxTransaction.getMid(), fpxTransaction.getTid(), fpxTransaction.getSellerOrderNo());
			return callFpx(fpxStatusRequest,clickCount);
		} catch (RuntimeException e) {
			logger.error("Exception occurred while sending IPN  and the exception message is " + e.getMessage() + " Trace Of Exception  ", e);
			return new AjaxResponseBody("0001", fpxTxnId, false,1,"{exception : \""+e.getMessage()+"\"}");
		}

	}

	public AjaxResponseBody getHitCount(String fpxTxnId)
	{
		try {
			int maxHitCount = Integer.parseInt(PropertyLoad.getFile().getProperty("FPX_IPN_MAX_HIT_COUNT"));
			int hitCount = FPXIPNHITCOUNT.getOrDefault(fpxTxnId, (short) 0);
			int remainingCount = maxHitCount - hitCount;
			FPXIPNHITCOUNT.keySet().forEach(System.out::println);
			logger.info("max hit count : "+maxHitCount);
			logger.info("actual hit count : "+hitCount);
			logger.info("remainingCount hit count : "+remainingCount);


			return new AjaxResponseBody(remainingCount);
		}catch (Exception e)
		{
			return new AjaxResponseBody(2);

		}
	}


	private static FpxStatusRequest fpxTransactionToStatusRequest(FpxTransaction fpxTransaction) {
		FpxStatusRequest fpxStatusRequest = new FpxStatusRequest();
		fpxStatusRequest.setMid(fpxTransaction.getMid());
		fpxStatusRequest.setTid(fpxTransaction.getTid());
		fpxStatusRequest.setMerchantOrderNo(fpxTransaction.getSellerOrderNo());

		return fpxStatusRequest;

	}

	private static  AjaxResponseBody callFpx(FpxStatusRequest statusRequest,int count) throws IOException {
		String fpxIpnUrl = PropertyLoad.getFile().getProperty("FPX_IPN_URL");
		String apiHeader = PropertyLoad.getFile().getProperty("FPX_IPN_URL_HEADER");
		String responseFromAPi = null;
		ObjectMapper objectMapper = new ObjectMapper();
		String requestBody = objectMapper.writeValueAsString(statusRequest);

		URL url = new URL(fpxIpnUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {

			connection.setRequestMethod("POST");
			connection.setRequestProperty("apiKey", apiHeader);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);

			// Write request body
			try (OutputStream os = connection.getOutputStream()) {
				os.write(requestBody.getBytes(StandardCharsets.UTF_8));
				os.flush();
			}


			int responseCode = connection.getResponseCode();
			StringBuilder response = new StringBuilder();
			if (responseCode == HttpURLConnection.HTTP_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

				while ((responseFromAPi = br.readLine()) != null) {
					response.append(responseFromAPi);
				}
				logger.info(" Request : "+requestBody +" Header : "+apiHeader+" url : "+url+" Response from url : "+response.toString());

				JsonNode jsonNode = objectMapper.readTree(response.toString());
//
//				MerchantStatusResponse responseBody = objectMapper.readValue(response.toString(), MerchantStatusResponse.class);
//				logger.info("MerchantStatusResponse : "+responseBody.toString());
//				logger.info("IPN RESPONSE  : "+responseBody.getIpnResponse());
				// Read and return the response
				return new AjaxResponseBody(String.valueOf(responseCode),statusRequest.getMerchantOrderNo(),jsonNode.get("responseCode").asInt() < 400,3-count,response.toString());
			} else {
				logger.info(" Request : "+requestBody +" Header : "+apiHeader+" url : "+url+" Response from url : "+response.toString());

				return new AjaxResponseBody(String.valueOf(responseCode),statusRequest.getMerchantOrderNo(),false,3-count,null);
			}
		}catch(Exception e)
		{
			logger.error("Exception occurred while hitting the FPX IPN endpoint the endpoint is   "+fpxIpnUrl+"  exception message  : "+e.getMessage(),e);
			throw new RuntimeException(e.getMessage());
		}
		finally {
			connection.disconnect();
		}

	}
}