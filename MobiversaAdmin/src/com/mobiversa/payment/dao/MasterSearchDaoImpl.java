package com.mobiversa.payment.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.CardSummaryBean;
import com.mobiversa.payment.controller.bean.EwalletSummaryBean;
import com.mobiversa.payment.controller.bean.FpxSummaryBean;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;

@Repository
@SuppressWarnings({"unchecked", "nls"})
@Transactional(readOnly = true, rollbackFor = Exception.class)

public class MasterSearchDaoImpl extends BaseDAOImpl implements MasterSearchDao {


    // master search fpx
    @Override
    public ArrayList<Object> masterSearchByFPX(PaginationBean<Object> paginationBean,
                                               Merchant merchant,
                                               String currentMerchant,
                                               String chooseType,
                                               List<String> searchList) {

        {
            ArrayList<Object> fss = new ArrayList<>();


            searchList = filterSearchValuesFromUi(searchList);


            try {
                String sql = "SELECT " +
                        "f.MID AS MID, " +
                        "f.TXNAMOUNT AS AMOUNT, " +
                        "f.TID AS TID, " +
                        "f.STATUS AS STATUS, " +
                        "f.FPXTXNID AS AID_RESPONSE, " +
                        "f.TIME_STAMP AS TIME_STAMP, " +
                        "f.SELLERORDERNO AS INVOICE_ID, " +
                        "f.MDR_AMT AS MDRAMT, " +
                        "f.SETTLED_DATE AS SETTLE_DATE, " +
                        "f.BANK_NAME AS BANK_NAME, " +
                        "f.EZYSETTLE_AMOUNT, " +
                        "f.SUB_MERCHANT_MID AS SUB_MERCHANT_MID, " +
                        "f.SELLEREXORDERNO AS PPID, " +
                        "f.DEBITAUTHCODESTR, " +
                        "f.TX_TIME, " +
                        "f.TX_DATE, " +
                        "f.PAYABLEAMT, " +
                        "f.DEBITAUTHCODE, " +
                        "f.BUYERNAME, " +
                        "m.BUSINESS_NAME, " +
                        "m.BUSINESS_ADDRESS1, " +
                        "m.BUSINESS_ADDRESS2, " +
                        "m.CITY, " +
                        "m.STATE, " +
                        "m.POSTCODE, " +
                        "m.BUSINESS_CONTACT_NUMBER " +
                        "FROM " +
                        "FPX_TRANSACTION f " +
                        "INNER JOIN " +
                        "mobiversa.`MID` mi ON (mi.FPX_MID = f.`MID` OR mi.UM_EZYWAY_MID = f.`MID` OR mi.UM_MOTO_MID = f.`MID`) " +
                        "INNER JOIN " +
                        "mobiversa.MERCHANT m ON m.ID = mi.MERCHANT_FK " +
                        "WHERE " +
                        "f.MID IN (:ezyWayMid, :fpxMid, :motoMid) AND ";

                if (chooseType.equalsIgnoreCase("REFERENCENO")) {
                    sql += "f.SELLERORDERNO IN (:searchValues)";
                } else if (chooseType.equalsIgnoreCase("APPROVALCODE")) {
                    sql += "f.FPXTXNID IN (:searchValues)";
                }
                Query sqlQuery = super.getSessionFactory().createSQLQuery(sql)
                        .setString("ezyWayMid", merchant.getMid().getUmEzywayMid())
                        .setString("fpxMid", merchant.getMid().getFpxMid())
                        .setString("motoMid", merchant.getMid().getUmMotoMid())
                        .setParameterList("searchValues", searchList);
                logger.info("Master search FPX query :" + sql);

                int querySize = sqlQuery.list().size();
                paginationBean.setQuerySize(String.valueOf(querySize));
                String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
                int pageSize = Integer.parseInt(DynamicPage);
                int pageNumFromJsp = paginationBean.getCurrPage();
                sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
                sqlQuery.setMaxResults(pageSize);

                List<Object[]> resultSet = sqlQuery.list();

                for (Object[] rec : resultSet) {

                    FpxSummaryBean fs = new FpxSummaryBean();
                    fs.setMid(getStringValue(rec[0]));
                    fs.setTransactionAmount(formatAmount(getStringValue(rec[1])));
                    fs.setTid(getStringValue(rec[2]));
                    fs.setStatus(getFpxStatus(getStringValue(rec[3]), getStringValue(rec[17])));
                    fs.setTransactionId(getStringValue(rec[4]));
                    fs.setTimestamp(getStringValue(rec[5]));
                    fs.setReferenceNo(getStringValue(rec[6]));
                    fs.setMdrAmount(getStringValue(rec[7]));
                    fs.setSettlementDate(getStringValue(rec[8]));
                    fs.setBankName("FPX - " + getStringValue(rec[9]));
                    fs.setEzySettleAmount(getStringValue(rec[10]));
                    fs.setSubMerchantMID(getStringValue(rec[11]));
                    fs.setSellerExOrderNo(getStringValue(rec[12]));
                    fs.setDeclinedReason(getStringValue(capitalizeFirstCharReason((String) rec[13])));
                    fs.setTransactionTime(getStringValue(rec[14]));
                    fs.setTransactionDate(getStringValue(convertFPxDate((String) rec[15])));
                    fs.setSettlementAmount(getStringValue(rec[16]));
                    fs.setDebitAuthCode(getStringValue(rec[17]));
                    fs.setFpxSlipBankName(getStringValue(rec[9]));
                    fs.setBuyerName(getStringValue(getFilteredValue((rec[18]))));
                    fs.setMerchanName(getStringValue(rec[19]));
                    fs.setContactNumber(getStringValue(rec[25]));
                    fs = addressFormatter(fs, (String) rec[20], (String) rec[21], (String) rec[22], (String) rec[24], (String) rec[23]);
                    fss.add(fs);
                }
            } catch (Exception e) {
                logger.error("Exception in master search fpx with chooseType " + chooseType + " searchList " + searchList + " " + e.getMessage(), e);
                paginationBean.setItemList(new ArrayList<>());
                return new ArrayList<>();
            }
            return fss;
        }
    }
    
    
    
    // created date instead of paid date
    private static String parseDateToStringForPayoutSummary(Object value,Object timestamp) throws ParseException {
        if (value == null || value.toString().isEmpty()) {
            logger.info("payout date is null ");
            if(timestamp.toString().isEmpty() || timestamp.toString() ==null) {
                logger.info("created date is  null ");
                logger.info("created date is not null "+timestamp);
            return "";
            }else {
                String createdDateValue = convertTimeStampDateFormat(timestamp.toString());
                logger.info("createdDateValue :"+createdDateValue);
                return createdDateValue;
            }
        }else {
        String dateValue = value.toString();
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue));
        } catch (ParseException e) {
            logger.error("Error parsing date: " + e.getMessage(), e);
            return "";
        }
    }
    }
    
    
    public static String convertTimeStampDateFormat(String inputDate) {

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.S]");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
           LocalDateTime date = LocalDateTime.parse(inputDate, inputFormatter);
           return date.format(outputFormatter);
        } catch (DateTimeParseException e) {
           logger.error("Parse Exception: " + e.getMessage() + e);
           return null;
        }  }


    private static String getFpxStatus(String status, String debitAuthCode) {
        if (debitAuthCode == null || debitAuthCode.trim().isEmpty() || !debitAuthCode.equals("00")) {
            return "FAILED";
        }
        switch (status) {
            case "H":
                return "EZYSETTLE";
            case "PPA":
                return "NOT SETTLED";
            case "S":
                return "SETTLED";
            case "R":
                return "REFUNDED";
            case "C":
                return "VOIDED";
            default:
                return "NOT SETTLED";
        }
    }


    //payout master search
    @Override
    @Transactional(readOnly = true)
    public ArrayList<Object> masterSearchByPayout(PaginationBean<Object> paginationBean, String merchant, List<String> listOfSearchValue, int currentPage, String search_type) {
        List<Object[]> resultSet = null;
        int querySize = 0;
        StringBuilder queryBuilder = new StringBuilder();

        List<String> listOfPayoutValues = filterSearchValuesFromUi(listOfSearchValue);


        try {
            queryBuilder.append("SELECT p.CREATED_BY, p.REQUEST_DATE, p.MODIFIED_DATE, p.PAYEE_ACC_NUMBER, ")
                    .append("p.PAYEE_BRN, p.PAYEE_BANK_NAME, p.PAYEE_EMAIL, p.PAYEE_IC, p.PAYEE_MOBILE, ")
                    .append("p.PAYEE_NAME, p.PAYOUT_AMOUNT, p.PAYOUT_DATE, p.PAYOUT_STATUS, p.SETTLE_DATE, ")
                    .append("p.SETTLE_NET_AMOUNT, p.INVOICE_ID_PROOF, p.PAYMENT_REASON, p.SOURCE_OF_FUND, ")
                    .append("p.SWIFT_IFSC_CODE, p.MERCHANT_FK, p.PAID_TIME, p.PAID_DATE, p.SUB_MERCHANT_MID, ")
                    .append("ml.BUSINESS_NAME, p.PAYOUT_ID, p.CURLEC_FAILURE_REASON, p.CREATED_DATE, p.SRCREFNO, p.PAYOUTTYPE,p.PAYMENTREFERENCE ")
                    .append("FROM PAYOUT_DETAIL p INNER JOIN MERCHANT ml ON ml.ID = p.MERCHANT_FK ")
                    .append("WHERE p.MERCHANT_FK = :merchant_Id ");

            logger.info("Master search payout query : " + queryBuilder);
            logger.info("merchant id " + merchant);
            if (search_type.equalsIgnoreCase("Payout_Id")) {
                queryBuilder.append("AND p.PAYOUT_ID IN (:payoutId) ");
            } else if (search_type.equalsIgnoreCase("Transaction_Id")) {
                queryBuilder.append("AND p.INVOICE_ID_PROOF IN (:payoutId) ");
            } else {
                logger.warn("Not a correct option");
                throw new IllegalArgumentException("Invalid search_type: " + search_type);
            }
            Query sqlQuery = super.getSessionFactory().createSQLQuery(queryBuilder.toString())
                    .setParameterList("payoutId", listOfPayoutValues)
                    .setString("merchant_Id", merchant);
            querySize = sqlQuery.list().size();
            paginationBean.setQuerySize(String.valueOf(querySize));
            String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
            int pageSize = Integer.parseInt(DynamicPage);
            int pageNumFromJsp = paginationBean.getCurrPage();
            sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
            sqlQuery.setMaxResults(pageSize);
            resultSet = sqlQuery.list();
            @SuppressWarnings("unchecked")
            ArrayList<Object> fss = new ArrayList<>();
            for (Object[] rec : resultSet) {
                PayoutModel fs = new PayoutModel();
                fs.setCreatedby(getStringValue(rec[0]));
                fs.setCreateddate(parseDateTimeToStringForPayout(rec[1]));
                fs.setModifieddate(getStringValue(rec[2]));
                fs.setPayeeaccnumber(getStringValue(rec[3]));
                fs.setPayeebrn(getStringValue(rec[4]));
                fs.setPayeebankname(getStringValue(rec[5]));
                fs.setPayeeemail(getStringValue(rec[6]));
                fs.setPayeeic(getStringValue(rec[7]));
                fs.setPayeemobile(getStringValue(rec[8]));
                fs.setPayeename(getStringValue(rec[9]));
                fs.setPayoutamount(formatAmount(getStringValue(rec[10])));
               // fs.setPayoutdate(parseDateToStringForPayoutSummary(rec[11]));
                fs.setPayoutdate(parseDateToStringForPayoutSummary(rec[11],rec[26]));
                fs.setPayoutstatus(getPayoutStatus(getStringValue(rec[12])));
                fs.setSettlenetamount(formatAmount(getStringValue(rec[14])));
                fs.setInvoiceidproof(getStringValue(rec[15]));
                fs.setPaymentreason(getStringValue(rec[16]));
                fs.setSourceoffund(getStringValue(rec[17]));
                fs.setSwiftifsccode(getStringValue(rec[18]));
                fs.setMerchantId(getStringValue(rec[19]));
                fs.setPaidTime(getStringValue(rec[20]));
                fs.setPaidDate(getStringValue(getEwalletTngAndSppSettlementDate((String) rec[21])));
                fs.setSubmerchantMid(getStringValue(rec[22]));
                fs.setMmId(getStringValue(rec[23]));
                fs.setPayoutId(getStringValue(rec[24]));
                fs.setFailurereason(getStringValue(capitalizeFirstCharReason((String) rec[25])));
                fs.setTimeStamp(getStringValue(rec[26]));
                fs.setSrcrefno(getStringValue(rec[27]));
                fs.setPayouttype(getStringValue(rec[28]));
                fs.setPaymentReference(getStringValue(rec[29]));
                fss.add(fs);
            }
            return fss;
        } catch (Exception e) {
            logger.error("Exception in MasterSearch by," + search_type + ": " + " with searcValue" + listOfSearchValue + " " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }


    private static String getPayoutStatus(String status) {
        switch (status) {
            case "A":
                return "To Process";
            case "pending":
                return "Pending";
            case "F":
                return "Failed";
            case "S":
                return "Processing";
            case "pp":
                return "Paid";
            case "pd":
                return "Declined";
            case "On Process":
                return "In Process";
    		case "rejected":
    			return "Rejected";
            default:
                return "Requested";
        }
    }


    private static String parseDateTimeToStringForPayout(Object value) {
        if (value == null || value.toString().isEmpty()) {
            return "";
        }
        String dateTime = value.toString();
        try {
            String time = new SimpleDateFormat("HH:mm:ss")
                    .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
            String date = new SimpleDateFormat("dd/MM/yyyy")
                    .format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
            return date + " " + time;
        } catch (ParseException e) {
            logger.error("Error parsing date and time: " + e.getMessage(), e);
            return "";
        }
    }

    private static String parseDateToStringForPayoutSummary(Object value) {
        if (value == null || value.toString().isEmpty()) {
            return "";
        }
        String dateValue = value.toString();
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(dateValue));
        } catch (ParseException e) {
            logger.error("Error parsing date: " + e.getMessage(), e);
            return "";
        }
    }


    // master search Ewallet - shoppepay
    @Override
    public ArrayList<Object> searchSppTransactionByInvoiceIdOrTngTxnID(final PaginationBean<Object> paginationBean,
                                                                       Merchant merchant,
                                                                       List<String> searchValue,
                                                                       String payment_type,
                                                                       String chooseType) {
        int querySize = 0;
        List<Object[]> resultSet = null;
        

        List<String> filteredSearchValue = filterSearchValuesFromUi(searchValue);


        try {

            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(
                            "SELECT " +
                                    "f.MID, " +
                                    "f.AMOUNT, " +
                                    "f.HOST_TYPE, " +
                                    "f.STATUS, " +
                                    "f.MOBI_TXN_ID, " +
                                    "f.TNG_TXN_ID, " +
                                    "f.TIME_STAMP, " +
                                    "f.INVOICE_ID, " +
                                    "f.PAYABLEAMT, " +
                                    "f.MDR_AMT, " +
                                    "f.SETTLED_DATE, " +
                                    "f.TXN_TYPE, " +
                                    "f.EZYSETTLE_AMOUNT, " +
                                    "f.SUB_MERCHANT_MID, " +
                                    "f.MERCHANT_NAME, " +
                                    "f.TID, " +
                                    "m.BUSINESS_NAME, " +
                                    "m.BUSINESS_ADDRESS1, " +
                                    "m.BUSINESS_ADDRESS2, " +
                                    "m.CITY, " +
                                    "m.STATE, " +
                                    "m.POSTCODE, " +
                                    "m.BUSINESS_CONTACT_NUMBER " +
                                    "FROM mobiversa.EWALLET_TXN_DETAILS f " +
                                    "INNER JOIN mobiversa.MID mi ON (f.MID = mi.UM_EZYWAY_MID OR f.MID = mi.UM_MOTO_MID OR f.MID = mi.SHOPPY_MID) " +
                                    "INNER JOIN mobiversa.MERCHANT m ON m.MID_FK = mi.ID "
                    )
                    .append(
                            "WHERE " +
                                    "f.TXN_TYPE = 'SPP' " +
                                    "AND f.MID IN (:shoppyMid, :umEzyWayMid, :umMotoMid) "
                    );
            if (chooseType.equalsIgnoreCase("Reference_No")) {
                queryBuilder.append("AND f.INVOICE_ID IN (:searchValue) ");
            } else if (chooseType.equalsIgnoreCase("Approval_Code")) {
//                queryBuilder.append("AND f.MOBI_TXN_ID IN (:searchValue) ");  // old
                queryBuilder.append("AND f.TNG_TXN_ID IN (:searchValue) ");  // new

            } else {
                logger.info("Not refernce no or approval code for spp");
            }
            queryBuilder.append("ORDER BY f.TIME_STAMP DESC");
            Query sqlQuery = super.getSessionFactory().createSQLQuery(queryBuilder.toString());
            sqlQuery.setString("shoppyMid", merchant.getMid().getShoppyMid());
            sqlQuery.setString("umEzyWayMid", merchant.getMid().getUmEzywayMid());
            sqlQuery.setString("umMotoMid", merchant.getMid().getUmMotoMid());
            sqlQuery.setParameterList("searchValue", filteredSearchValue);
            logger.info("Master search Ewallet-SPP query " + queryBuilder);
            querySize = sqlQuery.list().size();
            paginationBean.setQuerySize(String.valueOf(querySize));
            String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
            int pageSize = Integer.parseInt(DynamicPage);
            int pageNumFromJsp = paginationBean.getCurrPage();
            sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
            sqlQuery.setMaxResults(pageSize);
            resultSet = sqlQuery.list();
            @SuppressWarnings("unchecked")
            ArrayList<Object> fss = new ArrayList<>();
            for (Object[] rec : resultSet) {
            	EwalletSummaryBean fs = new EwalletSummaryBean();
                String createdDateAndTime = getEwalletFormattedDateAndTime(getStringValue(rec[6]),
                        getStringValue(rec[3]));
                fs.setTransactionDate(getEwalletDateOrTime(createdDateAndTime, "Date"));
                fs.setTransactionTime(getEwalletDateOrTime(createdDateAndTime, "Time"));
                fs.setTransactionID(getStringValue(rec[4]));
                fs.setPaymentMethod(getPaymentMethod(getStringValue(rec[10])));
                fs.setTransactionAmount(formatAmount(getStringValue(rec[1])));
                fs.setStatus(ewalletStatus(getStringValue(rec[3]), null));
                fs.setMid(getStringValue(rec[0]));
                fs.setHostType(getStringValue(rec[2]));
                fs.setMdrAmount(getStringValue(rec[9]));
                fs.setSettlementAmount(formatAmount(getStringValue(rec[8])));
                fs.setSettlementDate(getStringValue(getEwalletTngAndSppSettlementDate((String) rec[10])));
                fs.setRrn(getStringValue(rec[5]));
                fs.setApprovalCode(getStringValue(rec[4]));
                fs.setReferanceNo(getStringValue(rec[7]));
                fs.setSubMerchantMID(getStringValue(rec[13]));
                fs.setEzySettleAmt(formatAmount(getStringValue(rec[12])));
                fs.setTid(getStringValue(rec[15]));
                fs.setTimeStamp(getStringValue(rec[6]));
                fs.setMerchantName(getStringValue(rec[16]));
                fs.setContactNumber(getStringValue(rec[22]));
                fs.setFailureReason("Transaction Failed");
                fs = addressFormatter(fs, (String) rec[17], (String) rec[18], (String) rec[19], (String) rec[21], (String) rec[20]);
                fss.add(fs);
            }
            paginationBean.setItemList(fss);
            return fss;
        } catch (Exception e) {

            logger.error("Exception in Master search Spp-Transaction by " +
                    "invoiceId Or tngTxnID with searchValue " + searchValue + " payment_type " + payment_type + " chooseType " + chooseType + " " + e.getMessage(), e);
            paginationBean.setItemList(new ArrayList<>());
            return new ArrayList<>();
        }


    }

    // master search Ewallet - Touch N Go
    @Override
    @Transactional(readOnly = true)
    public ArrayList<Object> searchTngTransactionByInvoiceIdOrTngTxnID(final PaginationBean<Object> paginationBean,
                                                                       Merchant merchant, List<String> searchValue, String payment_type, String chooseType) {


        int querySize = 0;
        List<Object[]> resultSet = null;


        List<String> filteredSearchValue = filterSearchValuesFromUi(searchValue);


        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append(
                    "SELECT " +
                            "f.MID, " +
                            "f.AMOUNT, " +
                            "f.HOST_TYPE, " +
                            "f.STATUS, " +
                            "f.MOBI_TXN_ID, " +
                            "f.TNG_TXN_ID, " +
                            "f.TIME_STAMP, " +
                            "f.INVOICE_ID, " +
                            "f.PAYABLEAMT, " +
                            "f.MDR_AMT, " +
                            "f.SETTLED_DATE, " +
                            "f.TXN_TYPE, " +
                            "f.EZYSETTLE_AMOUNT, " +
                            "f.SUB_MERCHANT_MID, " +
                            "f.MERCHANT_NAME, " +
                            "f.TID, " +
                            "m.BUSINESS_NAME, " +
                            "m.BUSINESS_ADDRESS1, " +
                            "m.BUSINESS_ADDRESS2, " +
                            "m.CITY, " +
                            "m.STATE, " +
                            "m.POSTCODE, " +
                            "m.BUSINESS_CONTACT_NUMBER " +
                            "FROM mobiversa.EWALLET_TXN_DETAILS f " +
                            "INNER JOIN mobiversa.MID mi ON (f.MID = mi.UM_EZYWAY_MID OR f.MID = mi.UM_MOTO_MID OR f.MID = mi.TNG_MID) " +
                            "INNER JOIN mobiversa.MERCHANT m ON m.MID_FK = mi.ID " +
                            "WHERE f.TXN_TYPE = 'TNG' AND " +
                            "f.MID IN (:tngMid, :umEzyWayMid, :umMotoMid) "
            );
            if (chooseType.equalsIgnoreCase("Reference_No")) {
                queryBuilder.append("AND f.INVOICE_ID IN (:searchValue) ");
            } else if (chooseType.equalsIgnoreCase("Approval_Code")) {
                queryBuilder.append("AND f.TNG_TXN_ID IN (:searchValue) ");
            }
            queryBuilder.append("ORDER BY f.TIME_STAMP DESC");
            Query sqlQuery = super.getSessionFactory().createSQLQuery(queryBuilder.toString());
            sqlQuery.setString("tngMid", merchant.getMid().getTngMid());
            sqlQuery.setString("umEzyWayMid", merchant.getMid().getUmEzywayMid());
            sqlQuery.setString("umMotoMid", merchant.getMid().getUmMotoMid());
            sqlQuery.setParameterList("searchValue", filteredSearchValue);
            logger.info("Master search Ewallet-TNG query " + queryBuilder);
            querySize = sqlQuery.list().size();
            paginationBean.setQuerySize(String.valueOf(querySize));
            String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
            int pageSize = Integer.parseInt(DynamicPage);
            int pageNumFromJsp = paginationBean.getCurrPage();
            sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
            sqlQuery.setMaxResults(pageSize);
            resultSet = sqlQuery.list();
            @SuppressWarnings("unchecked")
            ArrayList<Object> fss = new ArrayList<>();
            for (Object[] rec : resultSet) {
                EwalletSummaryBean fs = new EwalletSummaryBean();
                String createdDateAndTime = getEwalletFormattedDateAndTime(getStringValue(rec[6]),
                        getStringValue(rec[3]));
                fs.setTransactionDate(getEwalletDateOrTime(createdDateAndTime, "Date"));
                fs.setTransactionTime(getEwalletDateOrTime(createdDateAndTime, "Time"));
                fs.setTransactionID(getStringValue(rec[4]));
                fs.setHostType(getStringValue(rec[2]));
                fs.setPaymentMethod(getPaymentMethod(getStringValue(rec[10])));
                fs.setTransactionAmount(formatAmount(getStringValue(rec[1])));
                fs.setStatus(ewalletStatus(getStringValue(rec[3]), null));
                fs.setMid(getStringValue(rec[0]));
                fs.setMdrAmount(getStringValue(rec[9]));
                fs.setSettlementAmount(formatAmount(getStringValue(rec[8])));
                fs.setSettlementDate(getStringValue(getEwalletTngAndSppSettlementDate((String) rec[10])));
                fs.setRrn(getStringValue(rec[5]));
                fs.setApprovalCode(getStringValue(rec[4]));
                fs.setReferanceNo(getStringValue(rec[7]));
                fs.setSubMerchantMID(getStringValue(rec[13]));
                fs.setEzySettleAmt(formatAmount(getStringValue(rec[12])));
                fs.setTid(getStringValue(rec[15]));
                fs.setTimeStamp(getStringValue(rec[6]));
                fs.setMerchantName(getStringValue(rec[16]));
                fs.setContactNumber(getStringValue(rec[22]));
                fs.setFailureReason("Transaction Failed");
                fs = addressFormatter(fs, (String) rec[17], (String) rec[18], (String) rec[19], (String) rec[21], (String) rec[20]);
                fss.add(fs);
            }

            paginationBean.setItemList(fss);
            return fss;
        } catch (Exception e) {
            logger.error("Exception in Master search Tng-Transaction by invoiceId Or tngTxnID with searchValue " + searchValue + " payment_type " + payment_type + " chooseType " + chooseType + " " + e.getMessage(), e);
            paginationBean.setItemList(new ArrayList<>());
            return new ArrayList<>();
        }
    }

    // master search Ewallet - Grab Pay
    @Override
    @Transactional(readOnly = true)
    public ArrayList<Object> searchGrabTransactionByReferenceNoOrRrnOrApprovalCode(final PaginationBean<Object> paginationBean,
                                                                                   Merchant merchant, List<String> searchValue, String payment_type, String searchType) {

        int querySize = 0;
        List<Object[]> resultSet = null;
        List<String> filteredSearchValue = filterSearchValuesFromUi(searchValue);

        try {
            ArrayList<Object> fss = new ArrayList<>();
            if (searchValue != null) {
                String conditionalQuery1 = "";
                String conditionalQuery2 = "";
                String grabQuery = "SELECT * FROM (" +
                        " (SELECT " +
                        " a.MID, " +
                        " a.AMOUNT, " +
                        " a.STATUS AS TRANSACTION_STATUS, " +
                        " a.TIME_STAMP, " +
                        " c.BUSINESS_NAME, " +
                        " a.INVOICE_ID, " +
                        " a.TXN_TYPE, " +
                        " a.SUB_MERCHANT_MID, " +
                        " a.AID_RESPONSE, " +
                        " a.AID_RESPONSE AS TXN_ID_ONE, " +
                        " a.MERCHANT_NAME, " +
                        " a.RRN, " +
                        " a.TID, " +
                        " gpf.NETAMT, " +
                        " gpf.MDR, " +
                        " gpf.SETTLEMENT_DATE, " +
                        " gpf.EZYSETTLE_AMOUNT, " +
                        "c.BUSINESS_ADDRESS1, " +
                        "c.BUSINESS_ADDRESS2, " +
                        "c.CITY, " +
                        "c.STATE, " +
                        "c.POSTCODE, " +
                        "c.BUSINESS_CONTACT_NUMBER, " +
                        "gpf.STATUS AS GRABPAY_FILESTATUS " +
                        " FROM " +
                        " MID m " +
                        " INNER JOIN " +
                        " MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK " +
                        " INNER JOIN " +
                        " MERCHANT c ON c.ID = m.MERCHANT_FK " +
                        " INNER JOIN " +
                        " mobiversa.FOR_SETTLEMENT a ON (a.TID = u.GPAY_TID OR a.TID = u.ONLINE_GPAY) " +
                        " INNER JOIN " +
                        " mobiversa.GRABPAY_FILE gpf ON gpf.PARTNERID = a.RRN " +
                        " WHERE " +
                        " m.GRAB_MID = :grabMid " +
                        " AND a.TXN_TYPE = 'GRABPAY' " +
                        " AND a.STATUS IN ('GPS', 'GRF') ";
                if (searchType.equalsIgnoreCase("RRN")) {
                    conditionalQuery1 = "AND a.RRN IN (:searchList) ";
                } else if (searchType.equals("Reference_No")) {
                    conditionalQuery1 = "AND a.INVOICE_ID IN (:searchList) ";
                } else if (searchType.equals("Approval_Code")) {
                    conditionalQuery1 = "AND a.AID_RESPONSE IN (:searchList) ";
                } else {
                    conditionalQuery1 = "AND a.RRN IN (:searchList) ";
                }
                grabQuery += conditionalQuery1 +
                        " ) " +
                        " UNION " +
                        " ( " +
                        " SELECT " +
                        " a.MID, " +
                        " a.AMOUNT, " +
                        " a.STATUS AS TRANSACTION_STATUS, " +
                        " a.TIME_STAMP, " +
                        " c.BUSINESS_NAME, " +
                        " a.INVOICE_ID, " +
                        " a.TXN_TYPE, " +
                        " a.SUB_MERCHANT_MID, " +
                        " a.AID_RESPONSE, " +
                        " a.AID_RESPONSE TXN_ID_TWO, " +
                        " a.MERCHANT_NAME, " +
                        " a.RRN, " +
                        " a.TID, " +
                        " '' AS NETAMT, " +
                        " '' AS MDR, " +
                        " '' AS SETTLEMENT_DATE, " +
                        " '' AS EZYSETTLE_AMOUNT, " +
                        "c.BUSINESS_ADDRESS1, " +
                        "c.BUSINESS_ADDRESS2, " +
                        "c.CITY, " +
                        "c.STATE, " +
                        "c.POSTCODE, " +
                        "c.BUSINESS_CONTACT_NUMBER, " +
                        "'' AS FAILED_STATUS " +
                        " FROM " +
                        " MID m " +
                        " INNER JOIN " +
                        " MOBILE_USER u ON m.MERCHANT_FK = u.MERCHANT_FK " +
                        " INNER JOIN " +
                        " MERCHANT c ON c.ID = m.MERCHANT_FK " +
                        " INNER JOIN " +
                        " mobiversa.FOR_SETTLEMENT a ON (a.TID = u.GPAY_TID OR a.TID = u.ONLINE_GPAY) " +
                        " WHERE " +
                        " m.GRAB_MID = :grabMid " +
                        " AND a.TXN_TYPE = 'GRABPAY' " +
                        " AND a.STATUS NOT IN ('GPS','GRF') ";

                if (searchType.equalsIgnoreCase("RRN")) {
                    conditionalQuery2 = "AND a.RRN IN (:searchList) ";
                } else if (searchType.equals("Reference_No")) {
                    conditionalQuery2 = "AND a.INVOICE_ID IN (:searchList) ";
                } else if (searchType.equals("Approval_Code")) {
                    conditionalQuery2 = "AND a.AID_RESPONSE IN (:searchList) ";
                } else {
                    conditionalQuery2 = "AND a.RRN IN (:searchList) ";
                }
                grabQuery += conditionalQuery2 +

                        " ) " +
                        " ) AS temp " +
                        " ORDER BY " +
                        " TIME_STAMP DESC ";
                Query sqlQuery = super.getSessionFactory().createSQLQuery(grabQuery);
                sqlQuery.setString("grabMid", merchant.getMid().getGrabMid());
                sqlQuery.setParameterList("searchList", filteredSearchValue);
                querySize = sqlQuery.list().size();
                logger.info("Master search Ewallet-Grab query " + grabQuery);
                paginationBean.setQuerySize(String.valueOf(querySize));
                String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
                int pageSize = Integer.parseInt(DynamicPage);
                int pageNumFromJsp = paginationBean.getCurrPage();
                sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
                sqlQuery.setMaxResults(pageSize);
                resultSet = sqlQuery.list();
                for (Object[] rec : resultSet) {
                    EwalletSummaryBean fs = new EwalletSummaryBean();
                    String createdDateAndTime = getEwalletFormattedDateAndTime(getStringValue(rec[3]),
                            getStringValue(rec[2]));
                    fs.setTransactionDate(getEwalletDateOrTime(createdDateAndTime, "Date"));
                    fs.setTransactionTime(getEwalletDateOrTime(createdDateAndTime, "Time"));
                    fs.setTransactionID(getStringValue(rec[9]));
                    fs.setPaymentMethod("Grabpay");
                    fs.setTransactionAmount(formatAmount(getStringValue(rec[1])));
                    fs.setStatus(ewalletStatus(getStringValue(rec[2]), (String) rec[23]));
                    fs.setTimeStamp(getStringValue((rec[3])));
                    fs.setMid(getStringValue(rec[0]));
                    fs.setSubMerchantMID(getStringValue(rec[7]));
                    fs.setRrn(getStringValue(rec[11]));
                    fs.setApprovalCode(getStringValue(rec[8]));
                    fs.setReferanceNo(getStringValue(rec[5]));
                    fs.setSettlementAmount(formatAmount(getStringValue(rec[13])));
                    fs.setTid(getStringValue(rec[12]));
                    fs.setMdrAmount(getStringValue(rec[14]));
                    fs.setSettlementDate(getEwalletSettlementDate(getStringValue(rec[15])));
                    fs.setEzySettleAmt(getStringValue(rec[16]));
                    fs.setMerchantName(getStringValue(rec[4]));
                    fs.setContactNumber(getStringValue(rec[22]));
                    fs.setFailureReason("Transaction Failed");
                    fs = addressFormatter(fs, (String) rec[17], (String) rec[18], (String) rec[19], (String) rec[21], (String) rec[20]);
                    fss.add(fs);
                }
            }
            paginationBean.setItemList(fss);
            return fss;
        } catch (Exception e) {
            logger.error("Exception in Master search GrabPay with with searchValue " + searchValue + " payment_type " + payment_type + " chooseType " + searchType + " " + e.getMessage(), e);
            paginationBean.setItemList(new ArrayList<>());
            return new ArrayList<>();
        }
    }


    public ArrayList<Object> searchBoostTransactionByReferenceNoOrRrnOrApprovalCode(
            final PaginationBean<Object> paginationBean,
            Merchant merchant, List<String> searchValue, String payment_type, String chooseType) {
        int querySize = 0;
        List<Object[]> resultSet = null;

        List<String> filteredSearchValue=filterSearchValuesFromUi(searchValue);


        try {
            String conditionalQuery1 = "";
            String conditionalQuery2 = "";
            String boostQuery = "SELECT * " +
                    "FROM (" +
                    "(" +
                    "SELECT " +
                    "a.MID, " +
                    "a.AMOUNT, " +
                    "a.TID, " +
                    "a.STATUS AS TRANSACTION_STATUS, " +
                    "a.AID_RESPONSE, " +
                    "a.RRN, " +
                    "a.TIME_STAMP, " +
                    "f.BUSINESS_NAME, " +
                    "a.INVOICE_ID, " +
                    "a.SUB_MERCHANT_MID, " +
//                    "b.MDRAMOUNT, " +
                    "(b.MDRAMOUNT + b.MDRREBATEAMOUNT) AS BOOST_MERCHANT_MDR, " +
                    "b.SETTLE_DATE, " +
                    "b.NETAMOUNT, " +
                    "b.EZYSETTLE_AMOUNT, " +
                    "f.BUSINESS_ADDRESS1, " +
                    "f.BUSINESS_ADDRESS2, " +
                    "f.CITY, " +
                    "f.STATE, " +
                    "f.POSTCODE, " +
                    "f.BUSINESS_CONTACT_NUMBER, " +
                    "a.AID_RESPONSE AS TXN_ID_ONE, " +
                    "b.STATUS AS RECON_STATUS " +
                    "FROM " +
                    "FOR_SETTLEMENT a " +
                    "INNER JOIN " +
                    "mobiversa.MID m ON (a.MID = m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.BOOST_MID) " +
                    "INNER JOIN " +
                    "mobiversa.MERCHANT f ON f.MID_FK = m.ID " +
                    "INNER JOIN " +
                    "mobiversa.BOOST_DLY_RECON b ON b.BOOSTTXNID = a.RRN " +
                    "WHERE " +
                    "a.TXN_TYPE = 'BOOST' " +
                    "AND a.STATUS IN ('BPS', 'BPA', 'BPC', 'BPR') " +
                    "AND a.MID IN (:umMotoMid, :umEzyWayMid, :boostMid) ";
            if (chooseType.equalsIgnoreCase("RRN")) {
                conditionalQuery1 = "AND a.RRN IN (:searchList) ";
            } else if (chooseType.equalsIgnoreCase("Reference_No")) {
                conditionalQuery1 = "AND a.INVOICE_ID IN (:searchList) ";
            } else if (chooseType.equalsIgnoreCase("Approval_Code")) {
                conditionalQuery1 = "AND a.AID_RESPONSE IN (:searchList) ";
            } else {
                conditionalQuery1 = "AND a.RRN IN (:searchList) ";
            }
            boostQuery += conditionalQuery1 +
                    ")" +
                    "UNION " +
                    "(" +
                    "SELECT " +
                    "a.MID, " +
                    "a.AMOUNT, " +
                    "a.TID, " +
                    "a.STATUS AS TRANSACTION_STATUS, " +
                    "a.AID_RESPONSE, " +
                    "a.RRN, " +
                    "a.TIME_STAMP, " +
                    "f.BUSINESS_NAME, " +
                    "a.INVOICE_ID, " +
                    "a.SUB_MERCHANT_MID, " +
                    "'' AS MDR, " +
                    "'' AS SETTLE_DATE, " +
                    "'' AS NETAMOUNT, " +
                    "'' AS EZYSETTLE_AMOUNT, " +
                    "f.BUSINESS_ADDRESS1, " +
                    "f.BUSINESS_ADDRESS2, " +
                    "f.CITY, " +
                    "f.STATE, " +
                    "f.POSTCODE, " +
                    "f.BUSINESS_CONTACT_NUMBER, " +
                    "a.AID_RESPONSE AS TXN_ID_TWO, " +
                    "'' AS FAILED_STATUS " +
                    "FROM " +
                    "FOR_SETTLEMENT a " +
                    "INNER JOIN " +
                    "mobiversa.MID m ON (a.MID = m.UM_EZYWAY_MID OR a.MID = m.UM_MOTO_MID OR a.MID = m.BOOST_MID) " +
                    "INNER JOIN " +
                    "mobiversa.MERCHANT f ON f.MID_FK = m.ID " +
                    "WHERE " +
                    "a.TXN_TYPE = 'BOOST' " +
                    "AND a.STATUS NOT IN ('BPS', 'BPA', 'BPC', 'BPR') " +
                    "AND a.MID IN (:umMotoMid, :umEzyWayMid, :boostMid) ";

            if (chooseType.equalsIgnoreCase("RRN")) {
                conditionalQuery2 = "AND a.RRN IN (:searchList) ";
            } else if (chooseType.equalsIgnoreCase("Reference_No")) {
                conditionalQuery2 = "AND a.INVOICE_ID IN (:searchList) ";
            } else if (chooseType.equalsIgnoreCase("Approval_Code")) {
                conditionalQuery2 = "AND a.AID_RESPONSE IN (:searchList) ";
            } else {
                conditionalQuery2 = "AND a.RRN IN (:searchList) ";
            }

            boostQuery += conditionalQuery2 +
                    ")" +
                    ") AS temp " +
                    "ORDER BY " +
                    "TIME_STAMP DESC ";

            Query sqlQuery = super.getSessionFactory().createSQLQuery(boostQuery);
            sqlQuery.setString("umMotoMid", merchant.getMid().getUmMotoMid());
            sqlQuery.setString("umEzyWayMid", merchant.getMid().getUmEzywayMid());
            sqlQuery.setString("boostMid", merchant.getMid().getBoostMid());
            sqlQuery.setString("boostMid", merchant.getMid().getBoostMid());
            sqlQuery.setParameterList("searchList", filteredSearchValue);
            logger.info("Master search Ewallet-Boost query " + boostQuery);
            querySize = sqlQuery.list().size();
            paginationBean.setQuerySize(String.valueOf(querySize));
            String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
            int pageSize = Integer.parseInt(DynamicPage);
            int pageNumFromJsp = paginationBean.getCurrPage();
            sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
            sqlQuery.setMaxResults(pageSize);
            resultSet = sqlQuery.list();
            ArrayList<Object> fss = new ArrayList<>();
            for (Object[] rec : resultSet) {
                EwalletSummaryBean fs = new EwalletSummaryBean();
                String createdDateAndTime = getEwalletFormattedDateAndTime(getStringValue(rec[6]),
                        getStringValue(rec[3]));
                fs.setMid(getStringValue(rec[0]));
                fs.setTransactionAmount(formatAmount(getStringValue(rec[1])));
                fs.setTid(getStringValue(rec[2]));
                fs.setStatus(ewalletStatus(getStringValue(rec[3]), (String) rec[21]));
                fs.setApprovalCode(getStringValue(rec[4]));
                fs.setRrn(getStringValue(rec[5]));
                fs.setTransactionDate(getEwalletDateOrTime(createdDateAndTime, "Date"));
                fs.setTransactionTime(getEwalletDateOrTime(createdDateAndTime, "Time"));
                fs.setReferanceNo(getStringValue(rec[8]));
                fs.setPaymentMethod("Boost");
                fs.setSubMerchantMID(getStringValue(rec[9]));
                fs.setMdrAmount(getStringValue(rec[10]));
                fs.setSettlementDate(getStringValue(ewalletSettledDateFilter((String) rec[11])));
                fs.setSettlementAmount(getStringValue(rec[12]));
                fs.setEzySettleAmt(getStringValue(rec[13]));
                fs.setTimeStamp(getStringValue(rec[6]));
                fs.setMerchantName(getStringValue(rec[7]));
                fs.setContactNumber(getStringValue(rec[19]));
                fs.setTransactionID(getStringValue(rec[20]));
                fs.setFailureReason("Transaction Failed");
                fs = addressFormatter(fs, (String) rec[14], (String) rec[15], (String) rec[16], (String) rec[18], (String) rec[17]);
                fss.add(fs);
            }
            paginationBean.setItemList(fss);
            return fss;

        } catch (Exception e) {
            logger.error("Exception in Master search boost with with searchValue " + searchValue + " payment_type " + payment_type + " chooseType " + chooseType + " " + e.getMessage(), e);
            paginationBean.setItemList(new ArrayList<>());
            return new ArrayList<>();
        }

    }

    private static String getEwalletSettlementDate(String settlementDate) {
        if (settlementDate == null || settlementDate.toString().isEmpty() || settlementDate == null) {
            return "";
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(settlementDate, inputFormatter);
            return dateTime.format(outputFormatter);
        } catch (DateTimeParseException e) {
            return "";
        }
    }

    private static String getEwalletTngAndSppSettlementDate(String settlementDate) {
        if (settlementDate == null || settlementDate.isEmpty()) {
            return "";
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate date = LocalDate.parse(settlementDate, inputFormatter);
            String formattedDate = date.format(outputFormatter);
            return formattedDate;
        } catch (DateTimeParseException e) {
            return "";
        }
    }


    private static String ewalletStatus(String status, String isEzySettled) {

        if (isEzySettled != null && !isEzySettled.trim().isEmpty() && isEzySettled.equals("H")) {
            return "EZYSETTLE";
        }
        switch (status) {

            case "FAILED":
            case "SPSS":
            case "SPF":
            case "TPAR":
            case "TPP":
            case "BPD":
            case "GBC":
            case "GPT":
            case "GPP":
            case "DF":
                return "FAILED";
            case "BPS":
            case "SPS":
            case "GPS":
            case "TPS":
            case "DS":
                return "SETTLED";
            case "SPR":
            case "TPR":
            case "GRF":
            case "GPR":
            case "BPR":
            case "DR":
                return "REFUNDED";
            case "BPC":
            case "GPC":
            case "DC":
                return "VOID";
            case "BP":
            case "SPP":
            case "TP":
            case "DP":
                return "PENDING";
            case "H":
                return "EZYSETTLE";
            case "SPA":
            case "BPA":
            case "TPA":
            case "GPA":
            case "DA":
                return "NOT SETTLED";
            default:
                return "FAILED";
        }
    }


    // search card

    @Override
    public ArrayList<Object> searchCardByReferenceNoOrApprovalCodeOrRrnOrCardNumber(PaginationBean<Object> paginationBean, Merchant merchant, List<String> searchList, String searchType) {
        int querySize = 0;
        List<Object[]> resultSet = null;

        List<String> filteredSearchValue=filterSearchValuesFromUi(searchList);

        try {
            String conditionalQuery1 = "";
            String conditionalQuery2 = "";
            String conditionalQuery3 = "";
            String sql = "SELECT * FROM (" +
                    "(" +
                    "SELECT " +
                    "res.F001_MID," +
                    "res.F005_EXPDATE," +
                    "res.F007_TXNAMT," +
                    "res.F354_TID," +
                    "res.MASKED_PAN," +
                    "res.`STATUS`," +
                    "res.F263_MRN," +
                    "res.H003_TDT," +
                    "res.H004_TTM," +
                    "res.F011_AUTHIDRESP," +
                    "res.F023_RRN," +
                    "res.TIME_STAMP," +
                    "mt.BUSINESS_NAME," +
                    "res.F263_MRN AS TXN_ID_ONE," +
                    "res.F268_CHNAME," +
                    "res.FRAUD_SCORE," +
                    "res.FRAUD_ID," +
                    "st.NET_AMOUNT," +
                    "st.MDR_AMT," +
                    "st.SETTLEMENTDATE," +
                    "res.F350_CRDTYP," +
                    "st.EZYSETTLE_AMOUNT AS EZYSETTLEAMOUNT," +
                    "res.SUB_MERCHANT_MID AS SUB_MERCHANT_MID, " +
                    "res.F259_TXNSTATMSG AS DECLINED_REASON, " +
                    "mt.BUSINESS_ADDRESS1, " +
                    "mt.BUSINESS_ADDRESS2, " +
                    "mt.CITY, " +
                    "mt.STATE, " +
                    "mt.POSTCODE, " +
                    "mt.BUSINESS_CONTACT_NUMBER, " +
                    "mt.AUTH_3DS, " +
                    "st.SETTLEMENTDATE AS DATES, " +
                    "res.F270_ORN AS REF_NO_ONE " +
                    "FROM " +
                    "mobiversa.UM_ECOM_TXNRESPONSE AS res " +
                    "INNER JOIN " +
                    "mobiversa.MID AS m ON res.F001_MID = m.UM_EZYWAY_MID OR res.F001_MID = m.UM_MOTO_MID OR res.F001_MID = m.FIUU_MID " +
                    "INNER JOIN " +
                    "mobiversa.MERCHANT AS mt ON m.MERCHANT_FK = mt.ID " +
                    "LEFT JOIN " +
                    "SETTLEMENT_MDR st ON st.RRN = res.F023_RRN AND st.AID = res.F011_AUTHIDRESP " +
                    "WHERE " +
                    "res.F001_MID IN (:umEzyWayMid,:umMotoMid, :fiuuMid) " +
                    "AND ";
            if (searchType.equalsIgnoreCase("RRN")) {
                conditionalQuery1 = "res.F023_RRN IN (:searchList) ";
            } else if (searchType.equals("CardNumber")) {
                conditionalQuery1 = "res.MASKED_PAN IN (:searchList) ";
            } else if (searchType.equals("ApprovalCode")) {
                conditionalQuery1 = "res.F011_AUTHIDRESP IN (:searchList) ";
            } else if (searchType.equals("ReferenceNo")) {
                conditionalQuery1 = "res.F270_ORN IN (:searchList) ";
            }
            sql += conditionalQuery1 +

                    "AND " +
                    "res.STATUS IN ('S','H','PPA','FR','R','PR','E')" +
                    ") " +
                    "UNION " +
                    "(" +
                    "SELECT " +
                    "res.F001_MID AS MID," +
                    "res.F005_EXPDATE AS EXPDATE," +
                    "res.F007_TXNAMT AS AMOUNT," +
                    "res.F354_TID AS TID," +
                    "res.MASKED_PAN AS MASKED_PAN," +
                    "res.`STATUS` AS STATUS," +
                    "res.F263_MRN AS MRN," +
                    "res.H003_TDT AS TDT," +
                    "res.H004_TTM AS TTM," +
                    "res.F011_AUTHIDRESP AS AID_RESPONSE," +
                    "res.F023_RRN AS RRN," +
                    "res.TIME_STAMP AS TIME_STAMP," +
                    "mt.BUSINESS_NAME AS BUSINESS_NAME," +
                    "res.F263_MRN AS TXN_ID_TWO," +
                    "res.F268_CHNAME AS CARD_HOLDER_NAME," +
                    "res.FRAUD_SCORE AS FRAUD_SCORE," +
                    "res.FRAUD_ID AS FRAUD_ID," +
                    "'' AS NETAMOUNT," +
                    "'' AS MDRAMT," +
                    "'' AS SETTLE_DATE," +
                    "res.F350_CRDTYP AS TXN_TYPE," +
                    "'' AS EZYSETTLEAMOUNT," +
                    "res.SUB_MERCHANT_MID AS SUB_MERCHANT_MID, " +
                    "res.F259_TXNSTATMSG AS DECLINED_REASON, " +
                    "mt.BUSINESS_ADDRESS1, " +
                    "mt.BUSINESS_ADDRESS2, " +
                    "mt.CITY, " +
                    "mt.STATE, " +
                    "mt.POSTCODE, " +
                    "mt.BUSINESS_CONTACT_NUMBER, " +
                    "mt.AUTH_3DS, " +
                    "'' AS SETTLEMENTDATE_FAILED_ONE, " +
                    "res.F270_ORN AS REF_NO_TWO " +
                    "FROM " +
                    "mobiversa.UM_ECOM_TXNRESPONSE AS res " +
                    "INNER JOIN " +
                    "mobiversa.MID AS m ON res.F001_MID = m.UM_EZYWAY_MID OR res.F001_MID = m.UM_MOTO_MID OR res.F001_MID = m.FIUU_MID " +
                    "INNER JOIN " +
                    "mobiversa.MERCHANT AS mt ON m.MERCHANT_FK = mt.ID " +
                    "WHERE " +
                    "res.F001_MID IN (:umEzyWayMid,:umMotoMid, :fiuuMid) " +
                    "AND ";

            if (searchType.equalsIgnoreCase("RRN")) {
                conditionalQuery2 = "res.F023_RRN IN (:searchList) ";
            } else if (searchType.equals("CardNumber")) {
                conditionalQuery2 = "res.MASKED_PAN IN (:searchList) ";
            } else if (searchType.equals("ApprovalCode")) {
                conditionalQuery2 = "res.F011_AUTHIDRESP IN (:searchList) ";
            } else if (searchType.equals("ReferenceNo")) {
                conditionalQuery2 = "res.F270_ORN IN (:searchList) ";
            }
            sql += conditionalQuery2 +

                    "AND " +
                    "res.STATUS IN ('A','C') " +
                    ") " +
                    "UNION" +
                    "(" +
                    "SELECT " +
                    "res.F001_MID AS MID," +
                    "res.F005_EXPDATE AS EXPDATE," +
                    "res.F007_TXNAMT AS AMOUNT," +
                    "res.F354_TID AS TID," +
                    "res.MASKED_PAN AS MASKED_PAN," +
                    "'NULL' AS STATUS," +
                    "res.F263_MRN AS MRN," +
                    "res.H003_TDT AS TDT," +
                    "res.H004_TTM AS TTM," +
                    "res.F011_AUTHIDRESP AS AID_RESPONSE," +
                    "res.F023_RRN AS RRN," +
                    "res.TIME_STAMP AS TIME_STAMP," +
                    "mt.BUSINESS_NAME AS BUSINESS_NAME," +
                    "res.F263_MRN AS TXN_ID_THREE," +
                    "res.F268_CHNAME AS CARD_HOLDER_NAME," +
                    "res.FRAUD_SCORE AS FRAUD_SCORE," +
                    "res.FRAUD_ID AS FRAUD_ID," +
                    "'' AS NETAMOUNT," +
                    "'' AS MDRAMT," +
                    "'' AS SETTLE_DATE," +
                    "res.F350_CRDTYP AS TXN_TYPE," +
                    "'' AS EZYSETTLEAMOUNT," +
                    "res.SUB_MERCHANT_MID AS SUB_MERCHANT_MID, " +
                    "res.F259_TXNSTATMSG AS DECLINED_REASON, " +
                    "mt.BUSINESS_ADDRESS1, " +
                    "mt.BUSINESS_ADDRESS2, " +
                    "mt.CITY, " +
                    "mt.STATE, " +
                    "mt.POSTCODE, " +
                    "mt.BUSINESS_CONTACT_NUMBER, " +
                    "mt.AUTH_3DS, " +
                    "'' AS SETTLEMENTDATE_FAILED_TWO, " +
                    "res.F270_ORN AS REF_NO_THREE " +
                    "FROM " +
                    "mobiversa.UM_ECOM_TXNRESPONSE AS res " +
                    "INNER JOIN " +
                    "mobiversa.MID AS m ON res.F001_MID = m.UM_EZYWAY_MID OR res.F001_MID = m.UM_MOTO_MID OR res.F001_MID = m.FIUU_MID " +
                    "INNER JOIN " +
                    "mobiversa.MERCHANT AS mt ON m.MERCHANT_FK = mt.ID " +
                    "WHERE " +
                    "res.F001_MID IN (:umEzyWayMid,:umMotoMid,:fiuuMid) " +
                    "AND (res.STATUS in ('null') or res.STATUS is null) " +
                    "AND ";

            if (searchType.equalsIgnoreCase("RRN")) {
                conditionalQuery3 = "res.F023_RRN IN (:searchList) ";
            } else if (searchType.equals("CardNumber")) {
                conditionalQuery3 = "res.MASKED_PAN IN (:searchList) ";
            } else if (searchType.equals("ApprovalCode")) {
                conditionalQuery3 = "res.F011_AUTHIDRESP IN (:searchList) ";
            } else if (searchType.equals("ReferenceNo")) {
                conditionalQuery3 = "res.F270_ORN IN (:searchList) ";
            }

            sql += conditionalQuery3 +
                    ")" +
                    ") " +
                    "AS temp " +
                    "ORDER BY " +
                    "TIME_STAMP DESC";

            Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
            sqlQuery.setString("umEzyWayMid", merchant.getMid().getUmEzywayMid());
            sqlQuery.setString("umMotoMid", merchant.getMid().getUmMotoMid());
            sqlQuery.setString("fiuuMid", merchant.getMid().getFiuuMid());
            sqlQuery.setParameterList("searchList", filteredSearchValue);
            logger.info("Master search Card query :" + sql);

            querySize = sqlQuery.list().size();
            paginationBean.setQuerySize(String.valueOf(querySize));
            String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
            int pageSize = Integer.parseInt(DynamicPage);
            int pageNumFromJsp = paginationBean.getCurrPage();
            sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
            sqlQuery.setMaxResults(pageSize);
            resultSet = sqlQuery.list();
            ArrayList<Object> fss = new ArrayList<>();
            for (Object[] rec : resultSet) {
                CardSummaryBean fs = new CardSummaryBean();
                String createdDateAndTime = getEwalletFormattedDateAndTime(getStringValue(rec[11]), "");
                fs.setTransactionDate(getEwalletDateOrTime(createdDateAndTime, "Date"));
                fs.setTransactionTime(getEwalletDateOrTime(createdDateAndTime, "Time"));
                fs.setTransactionId(getStringValue(rec[13]));
                fs.setRefereceNo(getStringValue(rec[32]));
                fs.setCardNumber(getStringValue(cardNumber(String.valueOf(rec[4]))));
                fs.setTransactionAmount(getStringValue(formatAmount((String) rec[2])));
                fs.setStatus(cardStatus(getStringValue(rec[5])));
                fs.setDeclinedReason(getStringValue(capitalizeFirstCharReason((String) rec[23])));
                fs.setCardHolderName(getStringValue(rec[14]));
                fs.setMid(getStringValue(rec[0]));
                fs.setTid(getStringValue(rec[3]));
                fs.setRrn(getStringValue(rec[10]));
                fs.setApprovalCode(getStringValue(rec[9]));
                fs.setNetAmount(getStringValue(rec[17]));
                fs.setMdrAmount(getStringValue(rec[18]));
                fs.setActualPaymentMethod(getStringValue(rec[20]));
                fs = getPaymentMethodAndCardType(fs, getStringValue(rec[20]));
                fs.setCardDetail(getStringValue(rec[20]));
                fs.setTimestamp(getStringValue(rec[11]));
                fs.setMerchantName(getStringValue(rec[12]));
                fs = addressFormatter(fs, String.valueOf(rec[24]), String.valueOf(rec[25]), String.valueOf(rec[26]),
                        String.valueOf(rec[28]), String.valueOf(rec[27]));
                fs.setContactNumber(getStringValue(rec[29]));
                fs.setAuth3Ds(getStringValue(rec[30]));
                fs.setSettlementDateCard(getStringValue(getEwalletSettlementDate((String) rec[31])));
                fss.add(fs);
            }

            paginationBean.setItemList(fss);
            return fss;
        } catch (Exception e) {
            logger.error("Exception in search card with searchList " + searchList + " searchType " + searchType + " " + e.getMessage(), e);
            paginationBean.setItemList(new ArrayList<>());
            return new ArrayList<>();

        }
    }


    // card type split method for visa or master card
    private static CardSummaryBean getPaymentMethodAndCardType(CardSummaryBean cardSummary, String cardDetails) {

        String paymentMethod = "N/A";
        String cardType = "";
        if (cardDetails != null && !cardDetails.trim().isEmpty()) {

            cardDetails = cardDetails.toUpperCase();
            paymentMethod = cardDetails.contains("UNION PAY") ? "UNION PAY" :
                    cardDetails.contains("MASTERCARD") ? "MASTERCARD" :
                            cardDetails.contains("VISA") ? "VISA" : "N/A";

            cardType = cardDetails.contains("CREDIT") ? "Credit" :
                    cardDetails.contains("DEBIT") ? "Debit" : cardDetails;
        }

        cardSummary.setCardType(cardType);   //` credit, debit
        cardSummary.setPaymentMethod(paymentMethod);  // union pay , visa , master card
        return cardSummary;
    }

    // mask card number last 3 digit
    public static String cardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty() || cardNumber.equalsIgnoreCase("null")) {
            logger.info("Card number is null or empty");
            return "";
        }
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < cardNumber.length() - 3; i++) {
            maskedNumber.append("*");
        }
        String lastFourDigits = cardNumber.substring(Math.max(cardNumber.length() - 3, 0));
        maskedNumber.append(lastFourDigits);
        return maskedNumber.toString();
    }

    private static String cardStatus(String status) {
        if (status == null || status.equalsIgnoreCase("null")) {
            return "FAILED";
        }

        switch (status) {
            case "A":
            case "BPA":
            case "TPA":
            case "SPA":
            case "BNA":
                return "NOT SETTLED";
            case "C":
            case "BPC":
            case "GBC":
            case "BNC":
                return "VOIDED";
            case "S":
            case "BPS":
            case "GPS":
            case "GRF":
            case "GPT":
            case "TPS":
            case "SPS":
            case "BNS":
                return "SETTLED";
            case "CB":
                return "CHARGE BACK";
            case "FR":
            case "R":
            case "PR":
                return "REFUNDED";
            case "H":
                return "EZYSETTLE";
            case "PPA":
                return "PAYOUT";
            case "E":
                return "PREAUTH";
            default:
                return "NOT SETTLED";
        }
    }


    // capitalize first char for declined reason
    public static String capitalizeFirstCharReason(String reason) {
        if (reason == null || reason.isEmpty()) {
            return reason;
        }
        return reason.substring(0, 1).toUpperCase() + reason.substring(1);
    }


    private static String getEwalletDateOrTime(String dateAndTime, String dateOrTime) {

        if (dateAndTime == null || dateAndTime.trim().isEmpty()) {
            return "";
        }
        String[] parts = dateAndTime.split(" ");

        if (dateOrTime.equals("Date")) {
            return parts[0];
        } else if (dateOrTime.equals("Time")) {
            return parts[1];
        }
        return "";
    }


    private static String formatAmount(String amountStr) {
        if (amountStr != null && !amountStr.isEmpty()) {
            if (amountStr.contains(".")) {
                double amount = Double.parseDouble(amountStr);
                DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
                return myFormatter.format(amount);
            } else {
                double amount = Double.parseDouble(amountStr) / 100;
                DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
                return myFormatter.format(amount);
            }
        }
        return "";
    }


    // Replace unaccepted char in jsp
    private static String getFilteredValue(Object value) {
        String valueAsString = value != null ? value.toString() : "";
        if (valueAsString.contains("'") || valueAsString.contains("\"") || valueAsString.contains("\\")) {
            return valueAsString.replaceAll("[\"'\\\\]", " ");
        }
        return valueAsString;
    }


    private static String getStringValue(Object value) {
        if (value == null) {
            return "";
        } else {
            String strValue = value.toString();
            return strValue.trim().isEmpty() ? "" : strValue;
        }
    }


    private static String getPaymentMethod(String txnType) {
        switch (txnType.toUpperCase()) {
            case "TNG":
                return "Touch N Go";
            case "SPP":
                return "Shopee pay";
            default:
                return "";
        }
    }

    private static String getEwalletFormattedDateAndTime(String dateStr, String status) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }

        SimpleDateFormat inputFormat = null;
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        if ("S".equals(status)) {
            inputFormat = new SimpleDateFormat("yyyyMMdd");
        } else {
            inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        try {
            Date date = inputFormat.parse(dateStr);
            return outputFormat.format(date);
        } catch (ParseException e) {
            logger.error("ParseException in date formatting: " + e.getMessage(), e);
            return "";
        }
    }


    private static String convertFPxDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String ewalletSettledDateFilter(String settlementDate) {
        if (settlementDate.trim().isEmpty() || settlementDate == null) {
            return "";
        }

        List<String> dateFormats = Arrays.asList(
                "yyyy-MM-dd",
                "dd-MMM-yyyy",
                "yyyyMMdd'T'HHmmss",
                "yyyy-MM-dd HH:mm:ss"
        );

        for (String format : dateFormats) {
            try {
                SimpleDateFormat parser = new SimpleDateFormat(format);
                Date date = parser.parse(settlementDate);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                return formatter.format(date);
            } catch (ParseException e) {
            }
        }
        return settlementDate;
    }


    // address formatter (with dynamic comma remover and adder)
    public static <T> T addressFormatter(T bean, String address1, String address2,
                                         String city, String pincode, String state) {
        if (bean == null) {
            try {
                bean = (T) bean.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String address = "";
        if (address1 != null && !address1.trim().isEmpty()) {
            address += address1;
        } else if (address2 != null && !address2.trim().isEmpty()) {
            address += address2;
        }
        if (city != null && !city.trim().isEmpty()) {
            if (!address.isEmpty()) {
                address += ", " + city;
            } else {
                address += city;
            }
        }
        String validAddress = address.replaceAll("[^a-zA-Z0-9\\s]", "");
        if (validAddress.isEmpty()) {
            address = "";
        }
        String countryPincode = "";
        if (state != null && !state.trim().isEmpty()) {
            countryPincode += state;
            if (pincode != null && !pincode.trim().isEmpty()) {
                countryPincode += " - " + pincode;
            }
        } else {
            if (pincode != null && !pincode.trim().isEmpty()) {
                countryPincode = "";
            }
        }
        String validCountryCode = countryPincode.replaceAll("[^a-zA-Z0-9\\s]", "");

        try {
            //setter via reflection
            Method setAddressMethod = bean.getClass().getMethod("setAddress", String.class);
            boolean shouldAppendComma = (countryPincode != null && !countryPincode.trim().isEmpty()) || !validCountryCode.isEmpty();
            setAddressMethod.invoke(bean, shouldAppendComma && !address.trim().isEmpty() ? address + "," : address);
            //setter via reflection
            Method setStateMethod = bean.getClass().getMethod("setState", String.class);
            setStateMethod.invoke(bean, countryPincode);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }


    private static List<String> filterSearchValuesFromUi(List<String> searchValues) {
        List<String> filteredSearchValue = searchValues.stream()
                .map(s -> s.replaceAll("[\\[\\]\"]", "").split(", ")).flatMap(Arrays::stream)
                .filter(value -> value != null && !value.isEmpty()).distinct().collect(Collectors.toList());
        logger.info("Returned values from filterSearchValuesFromUi method is :" + filteredSearchValue);
        return filteredSearchValue;
    }
    
    
  //master search Ewallet - DuitNow QR

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Object> searchQrTransactionByReferenceNoOrApprovalCode(final PaginationBean<Object> paginationBean,
                                                                       Merchant merchant, List<String> searchValue, String payment_type, String chooseType) {

        int querySize = 0;
        List<Object[]> resultSet = null;


        List<String> filteredSearchValue = filterSearchValuesFromUi(searchValue);


        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT q.CREATED_DATE, q.MERCHANT_FK, q.SUBMERCHANT_MID, ");
            queryBuilder.append("m.BUSINESS_NAME,q.INVOICE_ID, q.TRANSACTION_ID, q.STATUS, ");
            queryBuilder.append("q.AMOUNT, q.HOST_MDR_AMOUNT, q.MOBI_MDR_AMOUNT, ");
            queryBuilder.append("q.MDR_AMOUNT, q.PAYABLE_AMOUNT, q.SETTLE_DATE ,m.BUSINESS_ADDRESS1,m.BUSINESS_ADDRESS2, m.CITY,m.STATE,m.POSTCODE,m.BUSINESS_CONTACT_NUMBER, q.EZYSETTLE_AMOUNT,q.RESPONSE_MESSAGE,q.HOST_TRANSACTION_ID,q.PAID_TIMESTAMP ");
            queryBuilder.append("FROM mobiversa.QRTRANSACTIONS q ");
            queryBuilder.append("INNER JOIN mobiversa.MERCHANT m ON m.ID = q.MERCHANT_FK ");
            queryBuilder.append("WHERE q.MERCHANT_FK = :merchantId ");

            if (chooseType.equalsIgnoreCase("Reference_No")) {
                queryBuilder.append("AND q.INVOICE_ID IN (:searchValue) ");
            } else if (chooseType.equalsIgnoreCase("Approval_Code")) {
                queryBuilder.append("AND q.TRANSACTION_ID IN (:searchValue) ");
            }
            queryBuilder.append("ORDER BY q.CREATED_DATE DESC");
            Query sqlQuery = super.getSessionFactory().createSQLQuery(queryBuilder.toString());

            sqlQuery.setParameterList("searchValue", filteredSearchValue);
            sqlQuery.setParameter("merchantId", merchant.getId());
            logger.info("Master search Ewallet-DuitNow query " + queryBuilder);
            querySize = sqlQuery.list().size();
            paginationBean.setQuerySize(String.valueOf(querySize));
            String DynamicPage = PropertyLoad.getFile().getProperty("masterSearchPaginationCount");
            int pageSize = Integer.parseInt(DynamicPage);
            int pageNumFromJsp = paginationBean.getCurrPage();
            sqlQuery.setFirstResult((pageNumFromJsp * pageSize) - pageSize);
            sqlQuery.setMaxResults(pageSize);
            resultSet = sqlQuery.list();
            @SuppressWarnings("unchecked")
            ArrayList<Object> fss = new ArrayList<>();
            for (Object[] rec : resultSet) {
                EwalletSummaryBean fs = new EwalletSummaryBean();
                String createdDateAndTime = getEwalletFormattedDateAndTime(getStringValue(rec[0]),
                        getStringValue(rec[6]));
                fs.setTransactionDate(getEwalletDateOrTime(createdDateAndTime, "Date"));
                fs.setTransactionTime(getEwalletDateOrTime(createdDateAndTime, "Time"));
                fs.setTransactionID(getStringValue(rec[5]));
              //  fs.setHostType(getStringValue(rec[2]));
                fs.setPaymentMethod("DuitNow QR");
                fs.setTransactionAmount(formatAmount(getStringValue(rec[7])));
                fs.setStatus(ewalletStatus(getStringValue(rec[6]), null));
                fs.setMid(getStringValue(null));
                fs.setMdrAmount(getStringValue(rec[10]));
                fs.setSettlementAmount(formatAmount(getStringValue(rec[11])));
                fs.setSettlementDate(getStringValue(getEwalletTngAndSppSettlementDate((String) rec[12])));
                fs.setRrn(getStringValue(null));
                fs.setApprovalCode(getStringValue(null));
                fs.setReferanceNo(getStringValue(rec[4]));
                fs.setSubMerchantMID(getStringValue(rec[2]));
                fs.setTid(getStringValue(null));
                fs.setTimeStamp(getStringValue(rec[0]));
                fs.setMerchantName(getStringValue(rec[3]));
                fs.setContactNumber(getStringValue(rec[18]));
                fs.setEzySettleAmt(getStringValue(rec[19]));
                fs.setFailureReason(getStringValue(rec[20]));
                fs.setHostTransactionID(getStringValue(rec[21])); //host transaction id
                fs.setPaidTimeStamp(getStringValue(rec[22])); //paid time stamp
                fs = addressFormatter(fs, (String) rec[13], (String) rec[14], (String) rec[15], (String) rec[16], (String) rec[17]);
                fss.add(fs);
            }

            paginationBean.setItemList(fss);
            return fss;
        } catch (Exception e) {
            logger.error("Exception in Master search Duitnow-Transaction by with searchValue " + searchValue + " payment_type " + payment_type + " chooseType " + chooseType + " " + e.getMessage(), e);
            paginationBean.setItemList(new ArrayList<>());
            return new ArrayList<>();
        }
    }
    
    
    
}
