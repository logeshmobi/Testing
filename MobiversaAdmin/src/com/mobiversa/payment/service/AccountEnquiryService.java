package com.mobiversa.payment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiversa.common.bo.PayoutHoldTxn;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.AccountEnquiryDao;
import com.mobiversa.payment.dto.PayoutAccountEnquiryDto;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.util.ElasticEmail;
import com.mobiversa.payment.util.ElasticEmailClient;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


@Service
public class AccountEnquiryService {
    @Autowired
    private  AccountEnquiryDao accountEnquiryDao;

    private static final Logger logger=Logger.getLogger(AccountEnquiryService.class.getName());

    public void listApprovalTransactions(Model model, final int currentPage, String merchantName,String invoiceId,boolean rareScenario,String action) throws MobiException {
        PageBean pageBean = new PageBean("Payout Account Enquiry", "merchantweb/transaction/AccountEnquiryPayout", PageBean.Module.TRANSACTION,
                "transaction/sideMenuTransaction");
        PaginationBean<PayoutAccountEnquiryDto> paginationBean = new PaginationBean<>();
        try {
            Long merchantID = accountEnquiryDao.getMerchantIdFromUserName(merchantName);
            int totalTransactionSize = accountEnquiryDao.sizeOfApprovalTransactions(merchantID,invoiceId);

            int transactionsPerPage = Integer.parseInt(PropertyLoad.getFile().getProperty("paginationCount"));
            int requiredPages = totalTransactionSize / transactionsPerPage;
            if (totalTransactionSize % transactionsPerPage != 0)
                requiredPages++;
            paginationBean.setQuerySize(Integer.toString(requiredPages));
            int firstRecord = (currentPage - 1) * transactionsPerPage;

            logger.info("Total number of transactions : "+totalTransactionSize+"  and the pages required is "+requiredPages+ "The current page is "+currentPage+ "and the first record for the transaction is "+firstRecord);


            List<PayoutAccountEnquiryDto> enquiryDtos = accountEnquiryDao.listApprovalTransactions(merchantID,firstRecord,transactionsPerPage,invoiceId);
            logger.info(enquiryDtos != null && !enquiryDtos.isEmpty());
            if (enquiryDtos != null && !enquiryDtos.isEmpty()) {
                paginationBean.setItemList(enquiryDtos);
                logger.info("the objects setted in the list "+paginationBean.getItemList() +"and its size "+paginationBean.getItemList().size());
            }else
            {
                model.addAttribute("responseData" , "No Records Found");
                logger.info("No records found ");
            }


        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info(" Error message : "+e);
        }

            if (rareScenario) {
                PayoutHoldTxn payoutHoldTxn = accountEnquiryDao.findPayoutByInvoiceId(invoiceId);
                PayoutAccountEnquiryDto payoutAccountEnquiryDto = new PayoutAccountEnquiryDto(payoutHoldTxn);
                paginationBean.setPayoutAccountEnquiryDto(payoutAccountEnquiryDto);
                model.addAttribute("rareScenarioAction", payoutHoldTxn.getStatus());
            }
            else {
                model.addAttribute("action",action);
            }

        model.addAttribute("pageBean", pageBean);
        model.addAttribute("paginationBean", paginationBean);
        model.addAttribute("currentPageNumber", currentPage);
        model.addAttribute("rareScenario",rareScenario ? "Yes" : "no" );

    }


    public void contactExternalApi(PayoutHoldTxn payoutHoldTxn,String action,String rejectReason) throws MobiException, IOException {
        try {

            Map<String, String> requestData = generateRequestData(payoutHoldTxn,action,rejectReason);

            // Convert request data to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(requestData);
            String apiKey = PropertyLoad.getFile().getProperty("KeyForAccountEnquiry");
            logger.info("api Key : "+apiKey);
            String encryptedRequestBody = encryptBeforeExternalApi(apiKey,requestBody);
            logger.info("Encrypted Request : "+encryptedRequestBody);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("payoutHldencreq",encryptedRequestBody);



            logger.info(" Request data : "+requestBody);


            String payoutUrl =  PropertyLoad.getFile().getProperty("payoutUrl");
            logger.info("Payout url : "+payoutUrl);
            // Create the URL and connection
            URL url = new URL(payoutUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get the response
            int responseCode = connection.getResponseCode();
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (Exception e) {
                logger.info("Exception Ocuured while accepting the Payout for the invoice Id : " + payoutHoldTxn.getInvoiceIdProof());
                logger.error(e);
                e.printStackTrace();
            }

            // Print the response
            logger.info("Response code: " + responseCode);
            logger.info("Response body: " + response.toString());

//           Map responseJson = objectMapper.readValue(response.toString(),Map.class);
//           return responseJson;
        }
        catch (Exception e) {
            logger.error(e);
            logger.info("Exception occured while approving the Payout transaction for this invoice id : "+payoutHoldTxn.getInvoiceIdProof());
            logger.info("error message "+e.getMessage());
            e.printStackTrace();

        }

    }

    private static Map<String, String> generateRequestData(PayoutHoldTxn payoutHoldTxn,String action,String rejectReason) throws MobiException {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("service", "PAYOUT_TXN_REQ");
        requestData.put("mobiApiKey", getValue(payoutHoldTxn.getMobiApiKey()));
        requestData.put("businessRegNo", getValue(payoutHoldTxn.getBusinessRegNo()));
        requestData.put("bankName", getValue(payoutHoldTxn.getBankName()));
        requestData.put("bankAccNo", getValue(payoutHoldTxn.getBankAccNo()));
        requestData.put("payoutType", getValue(payoutHoldTxn.getPayoutType()));
        requestData.put("customerName", getValue(payoutHoldTxn.getCustomerName()));
        requestData.put("amount", getValue(payoutHoldTxn.getAmount()));
        requestData.put("subMID", getValue(payoutHoldTxn.getSubMID()));
        requestData.put("payoutid", getValue(payoutHoldTxn.getPayoutId()));
        requestData.put("bicCode", getValue(payoutHoldTxn.getBicCode()));
        requestData.put("remarks", getValue(payoutHoldTxn.getRemarks()));
        requestData.put("invoiceIdProof", getValue(payoutHoldTxn.getInvoiceIdProof()));
        requestData.put("onHoldPayout", action);
        requestData.put("payoutRejectReason", rejectReason);

        logger.info(requestData.toString());
        return requestData;

    }
    private static String getValue(String mayBeStringNull)
    {
        return "null".equalsIgnoreCase(mayBeStringNull) ? null : mayBeStringNull;
    }

    public PayoutHoldTxn rejectPayout(String invoiceId,String reason,Model model) throws Exception {
        PayoutHoldTxn payoutHoldTxn = accountEnquiryDao.findPayoutByInvoiceId(invoiceId);
        if(!"On Hold".equalsIgnoreCase(payoutHoldTxn.getStatus())) {
            throw new MobiException(Status.ACCOUNT_ENQUIRY);
        }
        accountEnquiryDao.updateOnHold(invoiceId,"Rejected");
        payoutHoldTxn.setStatus("Reject");
        return payoutHoldTxn;
    }


    public void listApprovalTransactionsForPayoutUser(Model model, int currentPage, String name, Object o) {

        PageBean pageBean = new PageBean("Payout Account Enquiry", "PayoutUser/AccountEnquiryPayout", PageBean.Module.TRANSACTION,
                "transaction/sideMenuTransaction");
        PaginationBean<PayoutAccountEnquiryDto> paginationBean = new PaginationBean<>();
        try {
            int totalTransactionSize = accountEnquiryDao.sizeOfApprovalTransactionsForPayoutUser();
            int transactionsPerPage = Integer.parseInt(PropertyLoad.getFile().getProperty("paginationCount"));
            int requiredPages = totalTransactionSize / transactionsPerPage;
            if (totalTransactionSize % transactionsPerPage != 0)
                requiredPages++;
            paginationBean.setQuerySize(Integer.toString(requiredPages));
            int firstRecord = (currentPage - 1) * transactionsPerPage;

            logger.info("Total number of transactions : "+totalTransactionSize+"  and the pages required is "+requiredPages+ "The current page is "+currentPage+ "and the first record for the transaction is "+firstRecord);

            if(totalTransactionSize > 0) {
                List<PayoutAccountEnquiryDto> enquiryDtos = accountEnquiryDao.listApprovalTransactionsForPayoutUser(firstRecord, transactionsPerPage);
                logger.info(enquiryDtos != null && !enquiryDtos.isEmpty());
                if (enquiryDtos != null && !enquiryDtos.isEmpty()) {
                    paginationBean.setItemList(enquiryDtos);
                    logger.info("the objects setted in the list " + paginationBean.getItemList() + "and its size " + paginationBean.getItemList().size());
                }
            }else
            {
                model.addAttribute("responseData" , "No Records Found");
                logger.info("No records found ");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info(" Error message : "+e);
        }
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("currentPageNumber", currentPage);
        model.addAttribute("paginationBean", paginationBean);
    }


    public void approveOrRejectEmail(PayoutHoldTxn payoutHoldTxn, String reason,String merchantName) throws MobiException {
        try {

            logger.info("Inside  email method : "+payoutHoldTxn.getInvoiceIdProof() + " reason : "+reason + " name : "+merchantName);

            String response = null;
            StringBuilder subjectBuilder = new StringBuilder();
            subjectBuilder.append("Confirmation: ");
            String status = "Approved " ;
            if(reason != null)
                status = "Rejected ";
            subjectBuilder.append(status);
            subjectBuilder.append(payoutHoldTxn.getPayoutId()!=null || "null".equalsIgnoreCase(payoutHoldTxn.getPayoutId()) ? payoutHoldTxn.getPayoutId():" ");
            subjectBuilder.append(" ").append(payoutHoldTxn.getCustomerName());

            String subject = subjectBuilder.toString();
            String toAddress = accountEnquiryDao.getMerchantEmailFromUserName(merchantName);
            String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
            String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_ACCOUNT_ENQUIRY_MAIL_CC");
//
            String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

            String actionTime = Utils.getFormattedDateTime("HH:mm:ss");

            logger.info("Email from :" + fromAddress);
            logger.info("Sending Email to :" + toAddress);
            logger.info("Email subject :" + subject);

            logger.info("Email From Name :" + fromName);
            logger.info("Sending Email to :" + toAddress);
            logger.info("Sending Email to :" + toAddress);

            String payoutId = "null".equalsIgnoreCase(payoutHoldTxn.getPayoutId()) ? " Transaction ID : "+payoutHoldTxn.getInvoiceIdProof() : " Payout ID : "+payoutHoldTxn.getPayoutId();

            StringBuilder emailContent = new StringBuilder();

            emailContent.append("Dear Merchant,\n\n").append("Please find below the confirmation of the action taken for Review").append(payoutId).append("\n\n")
                    .append("Details to Review:\n")
                    .append("    Payout Request Date: ").append(payoutHoldTxn.getCreatedDate()).append("\n")
                    .append("    Action Time: ").append(actionTime).append("\n")
                    .append("    Customer Name: ").append(payoutHoldTxn.getCustomerName()).append("\n")
                    .append("    Bank Account Name: ").append(payoutHoldTxn.getNameInBank()).append("\n")
                    .append("    Bank Account Number : ").append(payoutHoldTxn.getBankAccNo()).append("\n")
                    .append("    Transaction Amount: ").append(payoutHoldTxn.getAmount()).append("\n")
                    .append("    Status: ").append(status).append("\n")
                    .append("    Transaction ID: ").append(payoutHoldTxn.getInvoiceIdProof()).append("\n\n").append("The payout request has been ").append(status).append(" This transaction will now appear in your payout summary.\n\n")
                    .append("Thank you.\n\n")
                    .append("Regards,\n")
                    .append("Team Mobi\n")
                    .append("Portal Link : https://portal.gomobi.io/MobiversaAdmin");

            String emailBody = emailContent.toString();

            logger.info("Email message : "+emailBody);

            ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress, null,
                    emailBody);
            ElasticEmailClient client = new ElasticEmailClient();

            try {
                response = client.sendMessage1(message);
                logger.info("Email Sent Successfully to " + toAddress);
                logger.info("output Response : " + response);
            }catch (Exception e) {
                response = "400";
                logger.info("Invalid Signature Base64 String");
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("error reason "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public PayoutHoldTxn statusUpdate(String invoiceID) throws Exception {
        PayoutHoldTxn payoutHoldTxn = accountEnquiryDao.findPayoutByInvoiceId(invoiceID);
        if(!"On Hold".equalsIgnoreCase(payoutHoldTxn.getStatus()))
        {
            throw new MobiException(Status.ACCOUNT_ENQUIRY);
        }

        accountEnquiryDao.updateOnHold(invoiceID,"Approved");//updating to approve
        return payoutHoldTxn;
    }
    public String getEnableAccountEnquiry(long merchantId)
    {
        return accountEnquiryDao.getEnableAccountEnquiry(merchantId);
    }

    public void findByID(String type,String id,String merchantName,Model model)
    {
        logger.info("Searching for "+type+" and its id is "+id);
        List<PayoutAccountEnquiryDto> enquiryDtos = new ArrayList<>();

        PageBean pageBean = new PageBean("Payout Account Enquiry", "merchantweb/transaction/AccountEnquiryPayout", PageBean.Module.TRANSACTION,
                "transaction/sideMenuTransaction");
        PaginationBean<PayoutAccountEnquiryDto> paginationBean = new PaginationBean<>();
        String txnType =  null;
        try {
                Long merchantID = accountEnquiryDao.getMerchantIdFromUserName(merchantName);
                txnType = type.equalsIgnoreCase("Transaction ID") ? "INVOICE_ID_PROOF" : "PAYOUT_ID";
                enquiryDtos = accountEnquiryDao.findById(txnType, id, merchantID);
                logger.info(enquiryDtos != null && !enquiryDtos.isEmpty());
            if (enquiryDtos != null && !enquiryDtos.isEmpty()) {
                paginationBean.setItemList(enquiryDtos);
                logger.info("the object result for search query "+paginationBean.getItemList());
            }else
            {
                model.addAttribute("responseData" , "No Records Found");
                logger.info("No records found ");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info(" Error message : "+e);
        }

        model.addAttribute("pageBean", pageBean);
        model.addAttribute("paginationBean", paginationBean);
    }

    public void findByIDForPayoutUser(String type,String id,String merchantName,Model model)
    {
        logger.info("Searching for "+type+" and its id is "+id);
        List<PayoutAccountEnquiryDto> enquiryDtos = new ArrayList<>();

        PageBean pageBean = new PageBean("Payout Account Enquiry", "PayoutUser/AccountEnquiryPayout", PageBean.Module.TRANSACTION,
                "transaction/sideMenuTransaction");
        PaginationBean<PayoutAccountEnquiryDto> paginationBean = new PaginationBean<>();
        String txnType =  null;
        try {
            Long merchantID = accountEnquiryDao.getMerchantIdFromUserName(merchantName);
            txnType = type.equalsIgnoreCase("Transaction ID") ? "INVOICE_ID_PROOF" : "PAYOUT_ID";
            enquiryDtos = accountEnquiryDao.findById(txnType, id);
            logger.info(enquiryDtos != null && !enquiryDtos.isEmpty());
            if (enquiryDtos != null && !enquiryDtos.isEmpty()) {
                paginationBean.setItemList(enquiryDtos);
                logger.info("the object result for search query "+paginationBean.getItemList());
            }else
            {
                model.addAttribute("responseData" , "No Records Found");
                logger.info("No records found ");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info(" Error message : "+e);
        }

        model.addAttribute("pageBean", pageBean);
        model.addAttribute("paginationBean", paginationBean);
    }
    public static String encryptBeforeExternalApi(String apikey, String requestData) throws Exception {
        try {

            DESedeKeySpec dks = new DESedeKeySpec(apikey.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byte[] b = cipher.doFinal(requestData.getBytes());
            String encryptedValue = Base64.getEncoder().encodeToString(b);
            return new String(encryptedValue).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}