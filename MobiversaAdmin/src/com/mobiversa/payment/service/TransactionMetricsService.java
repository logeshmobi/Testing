package com.mobiversa.payment.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.payment.dao.TransactionMetricsDao;


@Service
public class TransactionMetricsService {
    private static final Logger logger = Logger.getLogger(TransactionMetricsService.class.getName());

    @Autowired
    private TransactionMetricsDao transactionMetricsDao;


    public String getFpxTransactionMetrics(String startTime, String endTime) {
        try {
            return transactionMetricsDao.fetchFpxTransactionMetrics(startTime, endTime);
        } catch (Exception e) {
            logger.error("Error fetching FPX transaction metrics", e);
            return "Error fetching FPX transaction metrics: " + e.getMessage();
        }
    }

    public String getPayoutTransactionMetrics(String startTime, String endTime) {
        try {
            return transactionMetricsDao.fetchPayoutMetrics(startTime, endTime);
        } catch (Exception e) {
            logger.error("Error fetching payout transaction metrics", e);
            return "Error fetching payout transaction metrics: " + e.getMessage();
        }
    }
}
