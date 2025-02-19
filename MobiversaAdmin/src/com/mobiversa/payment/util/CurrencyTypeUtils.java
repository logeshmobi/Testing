package com.mobiversa.payment.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

@SuppressWarnings("nls")
public class CurrencyTypeUtils {

	private static final Logger logger = Logger.getLogger(CurrencyTypeUtils.class);

	public static String formattedTimeStamp() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
		return now.format(formatter);
	}

	public static List<String> getCurrencyType() {
        return  Arrays.stream(CurrencyType.values())
                .map(CurrencyType::name)
                .collect(Collectors.toList());
    }

	public static List<String> getFormattedCurrencyTypeWithName() {
        return Arrays.stream(CurrencyType.values())
                	 .filter(currency -> currency != CurrencyType.MYR) // Ignore MYR
                     .map(CurrencyTypeUtils::formatCurrencyType)
                     .collect(Collectors.toList());
    }
	
	public static List<String> getFormattedCurrencyTypeWithName(Map<String, Double> exchangeRateMapForAllCountries) {
	    return exchangeRateMapForAllCountries.keySet().stream()
				.map(key -> {
					try {
						// Convert the string key to CurrencyType enum
						CurrencyType currencyType = CurrencyType.valueOf(key.trim());
						return currencyType;
					} catch (IllegalArgumentException e) {
						logger.error("Invalid country name: " + key + e.getMessage() + e);
						// Ignore invalid currency types
						return null;
					}
				})
	            .filter(currencyType -> currencyType != null && currencyType != CurrencyType.MYR) // Ignore MYR
	            .map(CurrencyTypeUtils::formatCurrencyType)
	            .collect(Collectors.toList());
	}

	public static String formatCurrencyType(CurrencyType type) {
        switch (type) {
            case MYR:
                return "Malaysian Ringgit  (MYR)";
            case USD:
                return "US Dollar (USD)";
            case IDR:
                return "Indonesian Rupiah (IDR)";
            case SGD:
                return "Singapore Dollar (SGD)";
            case EUR:
                return "Euro (EUR)";
            default:
			return type.name();
		}
	}

	public static String getCurrentDate() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return currentDate.format(formatter);
	}
}
