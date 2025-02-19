package com.mobiversa.payment.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

	public static String formatNumberGrouping(final long number) {
		if (number < 1000) {
			return ("" + number);
		} else {
			final int exp = (int) (Math.log(number) / Math.log(1000));
			return (String.format("%.1f %c", number / Math.pow(1000, exp), "KMBT".charAt(exp - 1)));
		}
	}

	public static String formatFileSize(final long sizeInByte) {
		if (sizeInByte < 1000) {
			return ("" + sizeInByte) + "B";
		} else {
			final int exp = (int) (Math.log(sizeInByte) / Math.log(1000));
			return (String.format("%.1f %c", sizeInByte / Math.pow(1000, exp), "KMGT".charAt(exp - 1))) + "Bytes";
		}
	}

	public static String formatAmount(final long price, final boolean useCurrency) {
		return formatAmount(price, useCurrency, true);
	}

	public static String formatAmount(final long price, final boolean useCurrency, final boolean digitGrouping) {
		DecimalFormat df;
		if (Boolean.FALSE.equals(useCurrency)) {
			df = new DecimalFormat("#0.00");
		} else {
			df = new DecimalFormat("RM #0.00");
		}
		df.setGroupingUsed(digitGrouping);
		return (df.format((1.0d * price) / 100));
	}

	/**
	 * Format date as yyyy-MMM-dd hh:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateFullTimestamp(final Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String formatDateCurrentYear() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(new Date());
	}

	/**
	 * Format Date as yyyy-MMM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDatePrint(final Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
		return dateFormat.format(date);
	}

	/**
	 * Format date as dd/MMM/yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static Object formatDatePDF(final Date date) {
		if (date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		return dateFormat.format(date);
	}

	public static String formatAsHTML(final String escapeHtml) {
		return escapeHtml.replaceAll("(\r\n|\n\r|\r|\n)", "<br>");
	}
}
