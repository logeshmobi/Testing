package com.mobiversa.payment.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.mobiversa.payment.dto.TransactionMetricsDto;


@Repository
@Transactional(readOnly = true)
public class TransactionMetricsDaoImpl extends BaseDAOImpl implements TransactionMetricsDao {

    static class MerchantData {
        int totalCount = 0;
        int successCount = 0;
        int failureCount = 0;
        int inProcessCount = 0;
        int pendingCount = 0;
        Map<String, Integer> failureReasons = new HashMap<>();
    }

    private static final Logger logger = Logger.getLogger(TransactionMetricsDaoImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public String fetchFpxTransactionMetrics(String startTime, String endTime) {
        logger.info("Fetching All - FPX transaction metrics for time range: " + startTime + " - " + endTime);
        Gson gson = new Gson();
        Map<String, Object> response = new HashMap<>();

        try {

            if (startTime == null || endTime == null || startTime.isEmpty() || endTime.isEmpty()) {
                throw new IllegalArgumentException("Start time or end time is invalid.");
            }

            List<TransactionMetricsDto> fpxMetricsDtoList = loadFpxTxnCounts(startTime, endTime);

            loadFailureReasons(startTime, endTime, fpxMetricsDtoList);

            String resultJson = convertResultsToJson(fpxMetricsDtoList);
            logger.info("fpx metrics list json  : " + resultJson);
            return resultJson;
        }
        catch (IllegalArgumentException e) {
            logger.error("Invalid argument for fpx: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Invalid input provided for fpx.");
            response.put("errorDetails", e.getMessage());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred.");
            response.put("errorDetails", e.getMessage() + e);
        }

        return gson.toJson(response);
    }

//    private List<TransactionMetricsDto> loadFpxTxnCounts(String startTime, String endTime) {
//        String mainQuery = "SELECT " +
//                "f.BUYERBANKID, " +
//                "f.BANK_NAME, " +
//                "COUNT(*) AS TOTAL_COUNT, " +
//                "SUM(CASE WHEN f.DEBITAUTHCODE = '00' THEN 1 ELSE 0 END) AS SUCCESS_COUNT, " +
//                "SUM(CASE WHEN f.DEBITAUTHCODE != '00' THEN 1 ELSE 0 END) AS FAILURE_COUNT, " +
//                "ROUND((SUM(CASE WHEN f.DEBITAUTHCODE = '00' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 2) AS SUCCESS_RATE, " +
//                "ROUND((SUM(CASE WHEN f.DEBITAUTHCODE != '00' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 2) AS FAILURE_RATE " +
//                "FROM mobiversa.FPX_TRANSACTION f " +
//                "WHERE f.TIME_STAMP BETWEEN :startTime AND :endTime " +
//                "GROUP BY f.BUYERBANKID";
//
//        logger.info("Main Query : "+mainQuery);
//        logger.info(" Start Time : "+startTime);
//        logger.info(" End Time : "+endTime);
//
//        Query query = sessionFactory.getCurrentSession().createSQLQuery(mainQuery);
//        query.setParameter("startTime", startTime);
//        query.setParameter("endTime", endTime);
//
//        List<Object[]> resultList = query.list();
//        List<TransactionMetricsDto> dtoList = new ArrayList<>();
//        logger.info(" Result Set Size : "+resultList.size());
//        for (Object[] row : resultList) {
//            TransactionMetricsDto dto = new TransactionMetricsDto();
//
//            dto.setBankCode((row[0] != null) ? (String) row[0] : "");
//            dto.setBankName((row[1] != null) ? (String) row[1] : "");
//            dto.setTotalTxnCount((row[2] != null) ? ((BigInteger) row[2]).intValue() : 0);
//            dto.setTotalSuccessTxnCount((row[3] != null) ? ((BigDecimal) row[3]).intValue() : 0);
//            dto.setTotalFailureTxnCount((row[4] != null) ? ((BigDecimal) row[4]).intValue() : 0);
//            dto.setSuccessRate((row[5] != null) ? ((BigDecimal) row[5]).doubleValue() : 0.0);
//            dto.setFailureRate((row[6] != null) ? ((BigDecimal) row[6]).doubleValue() : 0.0);
//            dtoList.add(dto);
//        }
//        return dtoList;
//    }
    
    
    private List<TransactionMetricsDto> loadFpxTxnCounts(String startTime, String endTime) {
        String mainQuery = "SELECT " +
                "f.BUYERBANKID, " +
                "f.BANK_NAME, " +
                "COUNT(*) AS TOTAL_COUNT, " +
                "SUM(CASE WHEN f.DEBITAUTHCODE = '00' THEN 1 ELSE 0 END) AS SUCCESS_COUNT, " +
                "SUM(CASE WHEN f.DEBITAUTHCODE != '00' THEN 1 ELSE 0 END) AS FAILURE_COUNT, " +
                "ROUND((SUM(CASE WHEN f.DEBITAUTHCODE = '00' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 2) AS SUCCESS_RATE, " +
                "ROUND((SUM(CASE WHEN f.DEBITAUTHCODE != '00' THEN 1 ELSE 0 END) / COUNT(*)) * 100, 2) AS FAILURE_RATE " +
                "FROM mobiversa.FPX_TRANSACTION f " +
                "WHERE f.TIME_STAMP BETWEEN :startTime AND :endTime " +
                "GROUP BY f.BUYERBANKID";

        logger.info("Main Query : " + mainQuery);
        logger.info(" Start Time : " + startTime);
        logger.info(" End Time : " + endTime);

        List<TransactionMetricsDto> dtoList = new ArrayList<>();
        try {
            Query query = sessionFactory.getCurrentSession().createSQLQuery(mainQuery);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            List<Object[]> resultList = query.list();
            logger.info(" Result Set Size : " + resultList.size());
            for (Object[] row : resultList) {
                TransactionMetricsDto dto = new TransactionMetricsDto();

                dto.setBankCode(sanitizeString(row[0]));
                dto.setBankName(sanitizeString(row[1]));
                dto.setTotalTxnCount((row[2] != null) ? ((BigInteger) row[2]).intValue() : 0);
                dto.setTotalSuccessTxnCount((row[3] != null) ? ((BigDecimal) row[3]).intValue() : 0);
                dto.setTotalFailureTxnCount((row[4] != null) ? ((BigDecimal) row[4]).intValue() : 0);
                dto.setSuccessRate((row[5] != null) ? ((BigDecimal) row[5]).doubleValue() : 0.0);
                dto.setFailureRate((row[6] != null) ? ((BigDecimal) row[6]).doubleValue() : 0.0);
                dtoList.add(dto);
            }
        } catch (Exception e) {
            logger.error("Error loading FPX transaction counts", e);
        }
        return dtoList;
    }

    
    private static String sanitizeString(Object obj) {
        if (obj == null) {
            return "";
        }
        String str = obj.toString();
        if (str.equalsIgnoreCase("null") || str.trim().isEmpty()) {
            return "";
        }
        return str;
    }


//    private void loadFailureReasons(String startTime, String endTime, List<TransactionMetricsDto> dtoList) {
//
//        String failureReasonsQuery = "SELECT " +
//                "f.BUYERBANKID, " +
//                "f.DEBITAUTHCODE AS reasonCode, " +
//                "f.DEBITAUTHCODESTR AS reasonText, " +
//                "COUNT(*) AS reasonCount " +
//                "FROM mobiversa.FPX_TRANSACTION f " +
//                "WHERE f.DEBITAUTHCODE != '00' AND f.TIME_STAMP BETWEEN :startTime AND :endTime " +
//                "GROUP BY f.BUYERBANKID, f.DEBITAUTHCODE";
//
//
//        Query query = sessionFactory.getCurrentSession().createSQLQuery(failureReasonsQuery);
//        query.setParameter("startTime", startTime);
//        query.setParameter("endTime", endTime);
//
//        logger.info("failure Reasons Query of FPX : "+failureReasonsQuery);
//
//        List<Object[]> resultList = query.list();
//
//        if (resultList == null || resultList.isEmpty()) {
//            logger.warn("No failure reasons found for the given time range.");
//            return;
//        }
//
//        Map<String, TransactionMetricsDto> dtoMap = new HashMap<>();
//        for (TransactionMetricsDto dto : dtoList) {
//            if (dto != null && dto.getBankCode() != null) {
//                dtoMap.put(dto.getBankCode(), dto);
//            }
//        }
//
//        for (Object[] row : resultList) {
//            String bankCode = (row[0] != null) ? (String) row[0] : null;
//            String reasonCode = (row[1] != null) ? (String) row[1] : null;
//            String reasonText = (row[2] != null) ? (String) row[2] : "Unknown reason";
//            Integer reasonCount = (row[3] instanceof BigInteger) ? ((BigInteger) row[3]).intValue() : (row[3] instanceof Integer) ? (Integer) row[3] : 0;
//
//            if (bankCode == null || reasonCount == 0) {
//                continue;
//            }
//            TransactionMetricsDto dto = dtoMap.get(bankCode);
//            if (dto == null) {
//                logger.warn("No Metrics found for bank code: " + bankCode);
//                continue;
//            }
//
//            TransactionMetricsDto.FailureMetrics failureMetrics = new TransactionMetricsDto.FailureMetrics();
//            failureMetrics.setReasonCode(reasonCode);
//            failureMetrics.setReasonText(reasonText);
//            failureMetrics.setReasonCount(reasonCount);
//
//            int totalCount = dto.getTotalTxnCount() != null ? dto.getTotalTxnCount() : 0;
//            double reasonRate = (totalCount > 0) ? ((reasonCount / (double) totalCount) * 100) : 0.0;
//            failureMetrics.setReasonRate(Math.round(reasonRate * 10.0) / 10.0);
//            dto.getFailureMetricsList().add(failureMetrics);
//
//        }
//    }
    
    private void loadFailureReasons(String startTime, String endTime, List<TransactionMetricsDto> dtoList) {
        String failureReasonsQuery = "SELECT " +
                "f.BUYERBANKID, " +
                "f.DEBITAUTHCODE AS reasonCode, " +
                "f.DEBITAUTHCODESTR AS reasonText, " +
                "COUNT(*) AS reasonCount " +
                "FROM mobiversa.FPX_TRANSACTION f " +
                "WHERE f.DEBITAUTHCODE != '00' AND f.TIME_STAMP BETWEEN :startTime AND :endTime " +
                "GROUP BY f.BUYERBANKID, f.DEBITAUTHCODE";

        logger.info("Failure Reasons Query of FPX: " + failureReasonsQuery);

        try {
            Query query = sessionFactory.getCurrentSession().createSQLQuery(failureReasonsQuery);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            List<Object[]> resultList = query.list();
            logger.warn("Failure Reasons of fpx resultset size : "+resultList.size());
            if (resultList == null || resultList.isEmpty()) {
                logger.warn("No failure reasons found for the given time range.");
                return;
            }

            Map<String, TransactionMetricsDto> dtoMap = new HashMap<>();
            for (TransactionMetricsDto dto : dtoList) {
                if (dto != null && dto.getBankCode() != null) {
                    dtoMap.put(dto.getBankCode(), dto);
                }
            }

            for (Object[] row : resultList) {
                String bankCode = sanitizeString(row[0]);
                String reasonCode = sanitizeString(row[1]);
                String reasonText = sanitizeString(row[2]);
                int reasonCount = (row[3] != null) ? ((BigInteger) row[3]).intValue() : 0;

                if (bankCode.isEmpty() || reasonCount == 0) {
                    continue;
                }

                TransactionMetricsDto dto = dtoMap.get(bankCode);
                if (dto == null) {
                    logger.warn("No Metrics found for bank code: " + bankCode);
                    continue;
                }

                TransactionMetricsDto.FailureMetrics failureMetrics = new TransactionMetricsDto.FailureMetrics();
                failureMetrics.setReasonCode(reasonCode);
                failureMetrics.setReasonText(reasonText);
                failureMetrics.setReasonCount(reasonCount);

                int totalCount = dto.getTotalTxnCount() != null ? dto.getTotalTxnCount() : 0;
                double reasonRate = (totalCount > 0) ? ((reasonCount / (double) totalCount) * 100) : 0.0;
                failureMetrics.setReasonRate(Math.round(reasonRate * 10.0) / 10.0);
                dto.getFailureMetricsList().add(failureMetrics);
            }
        } catch (Exception e) {
            logger.error("Error loading failure reasons for FPX transactions : ", e);
        }
    }

    @Transactional
    public String fetchPayoutMetrics(String startTime, String endTime) {
        logger.info("Fetching payout metrics for time range: " + startTime + " - " + endTime);
        Gson gson = new Gson();
        Map<String, Object> response = new HashMap<>();

        try {
            List<Object[]> payoutTxnData = fetchPayoutDataFromDB(startTime, endTime);
            List<TransactionMetricsDto> processedData = processPayoutData(payoutTxnData);

            logger.info("Payout metrics JSON: " + convertResultsToJson(processedData));
            return convertResultsToJson(processedData);

        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Invalid input provided.");
            response.put("errorDetails", e.getMessage());
        } catch (HibernateException e) {
            logger.error("Database access error: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "Database error occurred while fetching payout metrics.");
            response.put("errorDetails", e.getMessage());
        } catch (Exception e) {
            logger.error("General exception occurred: " + e.getMessage(), e);
            response.put("status", "error");
            response.put("message", "An unexpected error occurred.");
            response.put("errorDetails", e.getMessage());
        }

        return gson.toJson(response);
    }


    private List<Object[]> fetchPayoutDataFromDB(String startTime, String endTime) {
        logger.info("Fetching payout data from DB for time range: " + startTime + " - " + endTime);
        String query = "SELECT p.MERCHANT_FK, m.BUSINESS_NAME, p.CREATED_DATE, p.PAYOUT_STATUS, " +
                "p.FAILUREREASON, p.PAID_DATE, p.PAID_TIME, " +
                "STR_TO_DATE(CONCAT(p.PAID_DATE, ' ', p.PAID_TIME), '%Y-%m-%d %H:%i:%s') AS paidDateTime, " +
                "TIMESTAMPDIFF(MINUTE, p.CREATED_DATE, STR_TO_DATE(CONCAT(p.PAID_DATE, ' ', p.PAID_TIME), '%Y-%m-%d %H:%i:%s')) AS diff_in_minutes " +
                "FROM mobiversa.PAYOUT_DETAIL p " +
                "INNER JOIN mobiversa.MERCHANT m ON m.ID = p.MERCHANT_FK " +
                "WHERE p.CREATED_DATE BETWEEN :startTime AND :endTime" ;

        try {
            Query payoutQuery = sessionFactory.getCurrentSession().createSQLQuery(query);
            payoutQuery.setParameter("startTime", startTime);
            payoutQuery.setParameter("endTime", endTime);

            logger.info("Payout Main Query : "+query);

            List<Object[]> resultList = payoutQuery.list();
            
            logger.info("Payout Main Query size : "+resultList.size());
            return resultList;
        } catch (HibernateException e) {
            logger.error("Error fetching payout data from DB: " + e.getMessage(), e);
            throw e;
        }
    }

    private static List<TransactionMetricsDto> processPayoutData(List<Object[]> resultSetList) {
        return resultSetList.stream()
                .collect(Collectors.groupingBy(
                        rs -> rs[0] != null ? ((BigInteger) rs[0]).toString() : "0",
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    TransactionMetricsDto dto = new TransactionMetricsDto();

                                    dto.setMerchantId(list.get(0)[0] != null ? ((BigInteger) list.get(0)[0]).intValue() : 0);
                                    dto.setMerchantName(list.get(0)[1] != null ? (String) list.get(0)[1] : "");

                                    int totalCount = 0;
                                    int successCount = 0;
                                    int inProcessCount = 0;
                                    int failureCount = 0;
                                    int sumOfTimeDiff = 0;

                                    Map<String, Integer> failureReasons = new HashMap<>();

                                    for (Object[] rs : list) {

                                        String payoutStatus = rs[3] != null ? (String) rs[3] : "";
                                        String failureReason = rs[4] != null ? (String) rs[4] : "";


                                        BigInteger timeDifferenceBigInt = (rs[8] != null) ? (BigInteger) rs[8] : BigInteger.ZERO;
                                        int timeDifference = timeDifferenceBigInt.intValue();

                                      //  totalCount++;

                                        switch (payoutStatus) {
                                            case "pp":
                                                successCount++;
                                                totalCount++;
                                                sumOfTimeDiff += timeDifference;
                                                break;
                                            case "pd":
                                                failureCount++;
                                                totalCount++;
                                                failureReasons.put(failureReason, failureReasons.getOrDefault(failureReason, 0) + 1);
                                                break;
                                            case "On Process":
                                            case "On process":
                                                inProcessCount++;
                                                totalCount++;
                                                break;
                                            case "pending":
                                                break;
                                        }
                                    }

                                    dto.setTotalTxnCount(totalCount);
                                    dto.setTotalSuccessTxnCount(successCount);
                                    dto.setTotalFailureTxnCount(failureCount);
                                    dto.setTotalOnProcessCount(inProcessCount);


                                    dto.setSuccessRate((totalCount > 0) ? roundToTwoDecimalPlaces((double) successCount / totalCount * 100) : 0);
                                    dto.setFailureRate((totalCount > 0) ? roundToTwoDecimalPlaces((double) failureCount / totalCount * 100) : 0);
                                    dto.setOnProcessRate((totalCount > 0) ? roundToTwoDecimalPlaces((double) inProcessCount / totalCount * 100) : 0);


                                    dto.setAvgProcessingTimeForPp(convertMinutesToHoursAndMinutes((successCount > 0 && sumOfTimeDiff > 0) ? roundToTwoDecimalPlaces((double) sumOfTimeDiff / successCount) : 0));

                                    int finalTotalCount = totalCount;
                                    List<TransactionMetricsDto.FailureMetrics> failureMetricsList = failureReasons.entrySet().stream()
                                            .map(entry -> {
                                                TransactionMetricsDto.FailureMetrics reason = new TransactionMetricsDto.FailureMetrics();
                                                reason.setReasonText(entry.getKey());
                                                reason.setReasonRate(roundToTwoDecimalPlaces((double) entry.getValue() / finalTotalCount * 100));
                                                reason.setReasonCount(entry.getValue());
                                                return reason;
                                            })
                                            .collect(Collectors.toList());

                                    dto.setFailureMetricsList(failureMetricsList);

                                    return dto;
                                }
                        )
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }


    private static double roundToTwoDecimalPlaces(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid Double value: " + value);
        }
        return Math.round(value * 100.0) / 100.0;
    }


    public static String convertMinutesToHoursAndMinutes(double minutes) {
        if (Double.isNaN(minutes) || Double.isInfinite(minutes)) {
            throw new IllegalArgumentException("Invalid minutes value: " + minutes);
        }
        if (minutes < 0) {
            throw new IllegalArgumentException("Minutes cannot be negative: " + minutes);
        }

        int hours = (int) minutes / 60;
        int remainingMinutes = (int) minutes % 60;
        if (hours == 0) {
            return String.format("%dm", remainingMinutes);
        }
        return String.format("%dh %dm", hours, remainingMinutes);
    }



    private static String convertResultsToJson(List<TransactionMetricsDto> merchantDataMap) {
        Gson gson = new Gson();
        return gson.toJson(merchantDataMap);
    }


}



