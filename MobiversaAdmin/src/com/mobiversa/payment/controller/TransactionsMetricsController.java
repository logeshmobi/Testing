package com.mobiversa.payment.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.dto.TransactionMetricsDto;
import com.mobiversa.payment.service.TransactionMetricsService;
import com.mobiversa.payment.util.PropertyLoad;

@Controller
@RequestMapping(value = TransactionsMetricsController.URL_BASE)
public class TransactionsMetricsController extends BaseController {

    private static final Logger logger = Logger.getLogger(TransactionsMetricsController.class);

    public static final String URL_BASE = "/TxnMetrics";

    @Autowired
    TransactionMetricsService transactionMetricsService;


    @RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
    public String defaultPage() {
        logger.info("default url");
        return "redirect:" + URL_BASE + "/getTxnMetricsUI";
    }


    @RequestMapping(value="/getTxnMetricsUI", method = RequestMethod.GET)
    public String displayTransactionMetrics(Model model, final java.security.Principal principal){
        String fpxMetricsList = null;
        String payoutMetricsList = null;
        logger.info("User name in Transaction Metrics :" + principal.getName());

        PageBean pageBean = new PageBean("transactionMetrics", "transaction/TransactionMetrics", PageBean.Module.TRANSACTION,
                "transaction/sideMenuTransaction");
        model.addAttribute("pageBean", pageBean);
        
        String fromDate = null;
        String toDate = null;

        try {
        	
        	String[] dates = getDateRange();
        	fromDate = dates[0];
        	toDate = dates[1];
        	
        	logger.info("from date : "+fromDate);
        	logger.info("to date : "+toDate);
        	
			/*
			 * fromDate = "2024-10-30 00:00:00"; 
			 * toDate = "2024-11-02 23:59:59";
			 * 
			 * logger.info("from date hardcode : "+fromDate);
			 * logger.info("to date hardcode : "+toDate);
			 */
        	
            fpxMetricsList = transactionMetricsService.getFpxTransactionMetrics(fromDate,toDate);
            model.addAttribute("fpxMetricsList", fpxMetricsList);

            payoutMetricsList = transactionMetricsService.getPayoutTransactionMetrics(fromDate,toDate);
            model.addAttribute("payoutMetricsList", payoutMetricsList);


        } catch (Exception e) {
            model.addAttribute("fpxMetricsList", new ArrayList<TransactionMetricsDto>());
            model.addAttribute("payoutMetricsList", new ArrayList<TransactionMetricsDto>());
            e.printStackTrace();

        }

        return TEMPLATE_DEFAULT;
    }

	    private static String[] getDateRange() {
	        try {
	            LocalDateTime endTime = LocalDateTime.now();
	           
	            long duration = Long.parseLong(PropertyLoad.getFile().getProperty("TRANSACTION_METRICS_TIMEPERIOD_IN_MINUTES"));  
	              LocalDateTime startTime = endTime.minusMinutes(duration);
	            // LocalDateTime startTime = endTime.minusHours(24);
	
	            logger.info("from time : " + startTime);
	            logger.info("to time : " + endTime);
	
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	            String to = endTime.format(formatter);
	            String from = startTime.format(formatter);
	
	            return new String[]{from, to};
	
	        } catch (DateTimeParseException e) {
	            logger.error("Error formatting date: " + e.getMessage());
	            return new String[]{"", ""};
	        } catch (Exception e) {
	            logger.error("Unexpected error occurred: " + e.getMessage());
	            return new String[]{"", ""};
	        }
	    }





}
