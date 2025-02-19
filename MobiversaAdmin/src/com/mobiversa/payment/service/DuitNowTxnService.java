package com.mobiversa.payment.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.TransactionResult;
import com.mobiversa.payment.dao.DuitnowTxnsDao;
import com.mobiversa.payment.dto.DuitNowTrxResponseDto;
import com.mobiversa.payment.dto.DuitnowTxnDto;
import com.mobiversa.payment.util.PropertyLoader;

@Service
@SuppressWarnings("nls")
public class DuitNowTxnService {

    private static final Logger logger = Logger.getLogger(DuitNowTxnService.class.getName());

    @Autowired
    private DuitnowTxnsDao duitnowTxnsDao;

   
    public DuitNowTrxResponseDto getDuitnowTxnDetails(String fromDate, String toDate, int currPage, String id) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<DuitnowTxnDto> duitnowTxnDtoList= null;
        try {
            String formattedFromDate = formatDate(fromDate, inputFormatter, outputFormatter, true);
            String formattedToDate = formatDate(toDate, inputFormatter, outputFormatter, false);

            logger.info("From Date: " + formattedFromDate);
            logger.info("To Date: " + formattedToDate);

                TransactionResult rawResults = duitnowTxnsDao.fetchDuitNowTxnsDetails(formattedFromDate, formattedToDate, currPage, id);
                duitnowTxnDtoList = mapResultsToDto(rawResults);
                return new DuitNowTrxResponseDto(duitnowTxnDtoList, rawResults.getTotalRecords());


        } catch (Exception e) {
            logger.error("Error occurred while processing DuitNow transactions: ", e);
            throw new RuntimeException("Error retrieving DuitNow transactions", e);
        }
    }
    

    public DuitNowTrxResponseDto getAllDuitnowTxnDetails(String fromDate, String toDate, int currPage) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<DuitnowTxnDto> duitnowTxnDtoList= null;
        try {
            String formattedFromDate = formatDate(fromDate, inputFormatter, outputFormatter, true);
            String formattedToDate = formatDate(toDate, inputFormatter, outputFormatter, false);

            logger.info("From Date: " + formattedFromDate);
            logger.info("To Date: " + formattedToDate);

            TransactionResult rawResults = duitnowTxnsDao.fetchAllDuitNowTxnsDetails(formattedFromDate, formattedToDate, currPage);
            duitnowTxnDtoList = mapResultsToDto(rawResults);
            return new DuitNowTrxResponseDto(duitnowTxnDtoList, rawResults.getTotalRecords());


        } catch (Exception e) {
            logger.error("Error occurred while processing DuitNow transactions: ", e);
            throw new RuntimeException("Error retrieving DuitNow transactions", e);
        }
    }


    public List<DuitnowTxnDto> exportDuitNowTxnDetails(String fromDate, String toDate, String exportType, PaginationBean paginationBean, String id) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<DuitnowTxnDto> duitnowTxnDtoList = new ArrayList<>();
        try {
            // Format the dates
            String formattedFromDate = formatDate(fromDate, inputFormatter, outputFormatter, true);
            String formattedToDate = formatDate(toDate, inputFormatter, outputFormatter, false);

            logger.info(String.format("From Date: %s", formattedFromDate));
            logger.info(String.format("To Date: %s", formattedToDate));

            TransactionResult rawResults = duitnowTxnsDao.exportDuitnowTransactionsData(formattedFromDate, formattedToDate, id);
            duitnowTxnDtoList = mapResultsToDto(rawResults);

            paginationBean.setDateFromBackend(fromDate);
            paginationBean.setDate1FromBackend(toDate);

        } catch (Exception e) {
            logger.error("Error occurred while processing DuitNow transactions for export: ", e);

        }

        return duitnowTxnDtoList;
    }



    public  List<DuitnowTxnDto> exportAllDuitNowTxnDetails(String fromDate, String toDate, String exportType, PaginationBean paginationBean){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<DuitnowTxnDto> duitnowTxnDtoList = new ArrayList<DuitnowTxnDto>();
        try {
            String formattedFromDate = formatDate(fromDate, inputFormatter, outputFormatter, true);
            String formattedToDate = formatDate(toDate, inputFormatter, outputFormatter, false);

            logger.info("From Date: " + formattedFromDate);
            logger.info("To Date: " + formattedToDate);

            TransactionResult rawResults = duitnowTxnsDao.exportAllDuitnowTxnData(formattedFromDate, formattedToDate);
            duitnowTxnDtoList = mapResultsToDto(rawResults);

            paginationBean.setDateFromBackend(fromDate);
            paginationBean.setDate1FromBackend(toDate);

        } catch (Exception e) {
            logger.error("Error occurred while processing DuitNow transactions for export: ", e);
            throw new RuntimeException("Error retrieving DuitNow transactions for export", e);
        }

        return duitnowTxnDtoList;

    }

    public DuitNowTrxResponseDto getFilterData(String searchId, String searchType, String id){
        List<DuitnowTxnDto> txnDtos = null;
        try {

                TransactionResult rawResults = duitnowTxnsDao.fetchSearchedData(searchId, searchType, id);
                txnDtos = mapResultsToDto(rawResults);
                return new DuitNowTrxResponseDto(txnDtos, rawResults.getTotalRecords());
        }catch (Exception e) {
            logger.error("Error occurred while processing DuitNow transactions: ", e);
            return new DuitNowTrxResponseDto();
        }
    }

    public DuitNowTrxResponseDto searchSpecificData(String searchId, String searchType){
        List<DuitnowTxnDto> txnDtos = null;
        try {

            TransactionResult rawResults = duitnowTxnsDao.searchDuitnowTxnData(searchId, searchType);
            txnDtos = mapResultsToDto(rawResults);
            return new DuitNowTrxResponseDto(txnDtos, rawResults.getTotalRecords());
        }catch (Exception e) {
            logger.error("Error occurred while processing DuitNow transactions: ", e);
            throw new RuntimeException("Error retrieving DuitNow transactions", e);
        }
    }


    private static String formatDate(String date, DateTimeFormatter inputFormatter, DateTimeFormatter outputFormatter, boolean isFromDate) {
        if (date != null && !date.isEmpty()) {
            LocalDate localDate = LocalDate.parse(date, inputFormatter);
            LocalDateTime dateTime = isFromDate ? localDate.atStartOfDay() : localDate.atTime(23, 59, 59);
            return dateTime.format(outputFormatter);
        }else {
        	 LocalDate today = LocalDate.now();
             LocalDate fromDate = today.minusDays(5);
             LocalDateTime dateTime = isFromDate ? fromDate.atStartOfDay() : today.atTime(23, 59, 59);
             return dateTime.format(outputFormatter);
        }
//        return date;
    }

    private static List<DuitnowTxnDto> mapResultsToDto(TransactionResult rawResults) {
        List<DuitnowTxnDto> dtoList = new ArrayList<>();
        for (Object[] row : rawResults.getTransactions()) {
            DuitnowTxnDto dto = new DuitnowTxnDto();

            dto.setCreatedDate(isValid(row[0]) ? formatDateFromRow(row[0]) : "");
            dto.setCreatedTime(isValid(row[0]) ? formatTimeFromRow(row[0]) : "");
            dto.setMerchantId(isValid(row[1]) ? row[1].toString() : "");
            dto.setSubMerchantMid(isValid(row[2]) ? row[2].toString() : "");
            dto.setMerchantName(isValid(row[3]) ? row[3].toString() : "");
            dto.setInvoiceId(isValid(row[4]) ? row[4].toString() : "");
            dto.setTransactionId(isValid(row[5]) ? row[5].toString() : "");
            dto.setStatus(isValid(row[6]) ? checkStatus(row[6].toString()) : "");

            dto.setTxnAmount(isValid(row[7]) ? formatAmount(row[7].toString()) : "");
            dto.setHostMdr(isValid(row[8]) ? formatAmount(row[8].toString()) : "");
            dto.setMobiMdr(isValid(row[9]) ? formatAmount(row[9].toString()) : "");
            dto.setMdrAmount(isValid(row[10]) ? formatAmount(row[10].toString()) : "");
            dto.setNetAmount(isValid(row[11]) ? formatAmount(row[11].toString()) : "");
            dto.setSettlementDate(isValid(row[12]) ? formatStringDate(row[12].toString()) : "");
            dto.setEzysettleAmount(isValid(row[13]) ? row[13].toString() : "");
           // dto.setDeclinedReason(isValid(row[14]) ? row[14].toString() : "");
            dto.setDeclinedReason(isValid(row[14]) ? row[14].toString().replaceAll("[^a-zA-Z0-9 ]", "") : "");
            dto.setHostTransactionId(isValid(row[15]) ? row[15].toString() : "");
            dto.setPaidDate(isValid(row[16]) ? formatDateFromRow(row[16]) : "");
            dto.setPaidTime(isValid(row[16]) ? formatTimeFromRow(row[16]) : "");

            dtoList.add(dto);
        }
        return dtoList;
    }

    private static boolean isValid(Object value) {
        return value != null && !value.toString().equals("null");
    }


    private static String formatAmount(String amountStr) {
        amountStr = amountStr.trim().replace(",","");
        if (amountStr != null && !amountStr.isEmpty()) {
            if (amountStr.contains(".")) {
                double amount = Double.parseDouble(amountStr);
                DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
                return myFormatter.format(amount);
            } else {
            	if(amountStr.length() == 12) {
                double amount = Double.parseDouble(amountStr) / 100;
                DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
                return myFormatter.format(amount);
            	}
            	else {
            		double amount = Double.parseDouble(amountStr);
                    DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
                    return myFormatter.format(amount);
            	}
            }
        }
        return "";
    }

    private static String checkStatus(String status) {
        switch (status) {
            case "DS":
                return "SETTLED";
            case "DA":
                return "NOT SETTLED";
            case "DC":
                return "VOIDED";
            case "DP":
                return "PENDING";
            case "DF":
                return "FAILED";
            case "H":
                return "EZYSETTLED";
            default:
                return "";
        }
    }

    private static String formatDateFromRow(Object dateObject) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String createdDate = dateFormat.format(dateObject);
        return createdDate;
    }

    private static String formatTimeFromRow(Object dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String createdTime = timeFormat.format(dateObject);
        return createdTime;
    }

    
    private static String formatStringDate(String dateObject) {
        if (dateObject == null || dateObject.isEmpty()) {
            return "";
        }

        try {
            return new SimpleDateFormat("dd/MM/yyyy")
                    .format(new SimpleDateFormat("yyyy-MM-dd").parse(dateObject));
        } catch (ParseException e) {
            logger.info("Invalid settlement date format: " + dateObject);
            return dateObject;
        }
    }


    
    public String requestVoid(String invoiceID, String amount, String username) {
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = null;

        try {
            URL url = new URL(PropertyLoader.getFileData().getProperty("DUITNOW_VOID_REQUEST_URL"));
            logger.info("URL FOR VOID : "+url);
            con = (HttpURLConnection) url.openConnection();

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");


            JSONObject params = new JSONObject();
            String voidAmount = amount.replace(",", "");
            params.put("invoiceId", invoiceID);
            params.put("amount", voidAmount);
            
            logger.info("params : " + params);

            OutputStream os = con.getOutputStream();
            os.write(params.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

         
            InputStream stream;
            int responseCode = con.getResponseCode();
            logger.info("HTTP Response Code: " + responseCode);

            if (responseCode >= 200 && responseCode < 300) {
                stream = con.getInputStream();  // Success codes (200-299)
            } else {
                stream = con.getErrorStream();  // Error codes (400 and above)
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            logger.info("response for void : " + response);
            
            JSONObject jsonResponse = new JSONObject(response.toString());
            String responseId = jsonResponse.optString("responseCode");
            
            if ("0000".equals(responseId)) {
               boolean updateStatus =  updateUserDetails(invoiceID, username);
               if(updateStatus) {
                logger.info("Successfully update the User Details for invoiceID: " + invoiceID);
               }else {
            	   logger.info("Problem occured while update the User Details for invoiceID: " + invoiceID);
               }
            } else {
               logger.warn("Void request failed with responseCode: " + responseId);
           }
               
        

        } catch (Exception e) {
            logger.info("Exception from void request process: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return response.toString();
    }
    
    //update modified Date and modified by in the table
    
    private boolean updateUserDetails(String invoiceID, String username) {
    	
    	LocalDateTime currentDate = LocalDateTime.now();
    	
    	DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	
    	String formattedCurrentDateWithTime = currentDate.format(format);
    	
    	logger.info("date and username is : "+formattedCurrentDateWithTime+", "+username);
    	
    	return duitnowTxnsDao.UpdateUserData(invoiceID,username,formattedCurrentDateWithTime);
    	
    }



}
