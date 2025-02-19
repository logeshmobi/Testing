package com.mobiversa.payment.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class AmountFormatter {

    public static String convertAmountFormat(String amountStr) throws Exception {
        try {

            Number number = NumberFormat.getInstance().parse(amountStr);
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            return decimalFormat.format(number);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
