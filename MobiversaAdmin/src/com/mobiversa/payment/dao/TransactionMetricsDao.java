package com.mobiversa.payment.dao;


import com.mobiversa.payment.dto.TransactionMetricsDto;

import java.util.List;

public interface TransactionMetricsDao extends BaseDAO {

    public String fetchFpxTransactionMetrics(String startTime, String endTime);
    public String fetchPayoutMetrics(String startTime, String endTime);
}
