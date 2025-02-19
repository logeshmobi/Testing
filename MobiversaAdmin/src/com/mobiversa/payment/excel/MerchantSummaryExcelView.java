package com.mobiversa.payment.excel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.Merchant;

public class MerchantSummaryExcelView extends AbstractExcelView {
       
       @Override
       protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
                    HttpServletRequest request, HttpServletResponse response)
                    throws Exception {

             HSSFSheet excelSheet = workbook.createSheet("All Merchant Summary List");
             setExcelHeader(excelSheet);
             
             List txnList = (List) model.get("txnList");
             setExcelRows(excelSheet,txnList);
             
       }

       public void setExcelHeader(HSSFSheet excelSheet) {
             HSSFRow excelHeader = excelSheet.createRow(0);
             excelHeader.createCell(0).setCellValue("Activation Date");
             excelHeader.createCell(1).setCellValue("BusinessName");
             excelHeader.createCell(2).setCellValue("Business Reg-Name");
             excelHeader.createCell(3).setCellValue("Business Reg-No");
             excelHeader.createCell(4).setCellValue("Business Type");
             excelHeader.createCell(5).setCellValue("integration platform");
             excelHeader.createCell(6).setCellValue("Email");
             excelHeader.createCell(7).setCellValue("EzywireMid");
             excelHeader.createCell(8).setCellValue("EzyMotoMid");
             excelHeader.createCell(9).setCellValue("EzyRecMid");
             excelHeader.createCell(10).setCellValue("EzyWayMid");
             excelHeader.createCell(11).setCellValue("UM-EzywireMid");
             excelHeader.createCell(12).setCellValue("UM-EzymotoMid");
             excelHeader.createCell(13).setCellValue("UM-EzywayMid");
             excelHeader.createCell(14).setCellValue("UM-EzyrecMid");
             

             excelHeader.createCell(15).setCellValue("Contact Number");
             excelHeader.createCell(16).setCellValue("BusinessAddress");
             excelHeader.createCell(17).setCellValue("City");
             excelHeader.createCell(18).setCellValue("State");
             excelHeader.createCell(19).setCellValue("Postal Code");
             
             excelHeader.createCell(20).setCellValue("Owner Name");
             excelHeader.createCell(21).setCellValue("NRIC/ Passport");
             
             excelHeader.createCell(22).setCellValue("Merchant Type");
             excelHeader.createCell(23).setCellValue("NOB");
             excelHeader.createCell(24).setCellValue("Business Category");
             excelHeader.createCell(25).setCellValue("Bank Name");
             excelHeader.createCell(26).setCellValue("Bank Account No");
             excelHeader.createCell(27).setCellValue("PREAUTH");
             excelHeader.createCell(28).setCellValue("BOOST");
             excelHeader.createCell(29).setCellValue("MOTO");
             excelHeader.createCell(30).setCellValue("OTP");
             excelHeader.createCell(31).setCellValue("Agent Name");
             
             excelHeader.createCell(32).setCellValue("BoostMid");
        excelHeader.createCell(33).setCellValue("GrapMid");
        excelHeader.createCell(34).setCellValue("FpxMid");
        excelHeader.createCell(35).setCellValue("TngMid");
        excelHeader.createCell(36).setCellValue("ShopifyMid");
        excelHeader.createCell(37).setCellValue("BnplMid");
        excelHeader.createCell(38).setCellValue("FiuuMid");
       
             /*excelHeader.createCell(23).setCellValue("Card Type");
             excelHeader.createCell(24).setCellValue("Card Scheme");*/
       
             //excelHeader.createCell(6).setCellValue("Location");
             /*excelHeader.createCell(3).setCellValue("State");
             excelHeader.createCell(4).setCellValue("Amount");*/
       }
       
       public void setExcelRows(HSSFSheet excelSheet, List<Merchant> txnList){
             int record = 1;
             for (Merchant txn : txnList) {
                    HSSFRow excelRow = excelSheet.createRow(record++);
                    
                    String pattern = "dd/MM/yyyy";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                    String date = simpleDateFormat.format(txn.getActivateDate());
                    
                     //System.out.println("DATA : "+date);
                    excelRow.createCell(0).setCellValue(date);
                    if(txn.getBusinessName()!=null) {
                           excelRow.createCell(1).setCellValue(txn.getBusinessName());
                    }
                    if(txn.getBusinessShortName()!=null) {
                           excelRow.createCell(2).setCellValue(txn.getBusinessShortName());
                    }
                    if(txn.getBusinessRegistrationNumber()!=null) {
                       excelRow.createCell(3).setCellValue(txn.getBusinessRegistrationNumber());
                    }
                    if(txn.getBusinessType()!=null) {
                           excelRow.createCell(4).setCellValue(txn.getBusinessType());
                    }
                    excelRow.createCell(5).setCellValue(txn.getIntegrationPlatform());
                    excelRow.createCell(6).setCellValue(txn.getEmail());
                    
                    
                    if (txn.getMid() != null) {
                           excelRow.createCell(7).setCellValue(txn.getMid().getMid());

                           excelRow.createCell(8).setCellValue(txn.getMid().getMotoMid());

                           excelRow.createCell(9).setCellValue(txn.getMid().getEzyrecMid());
                           excelRow.createCell(10).setCellValue(txn.getMid().getEzywayMid());
                           
                           excelRow.createCell(11).setCellValue(txn.getMid().getUmMid());
                           excelRow.createCell(12).setCellValue(txn.getMid().getUmMotoMid());
                           excelRow.createCell(13).setCellValue(txn.getMid().getUmEzywayMid());
                           excelRow.createCell(14).setCellValue(txn.getMid().getUmEzyrecMid());
                           
                           
                           excelRow.createCell(32).setCellValue(txn.getMid().getBoostMid());
                excelRow.createCell(33).setCellValue(txn.getMid().getGrabMid());
                excelRow.createCell(34).setCellValue(txn.getMid().getFpxMid());
                excelRow.createCell(35).setCellValue(txn.getMid().getTngMid());
                excelRow.createCell(36).setCellValue(txn.getMid().getShoppyMid());
                excelRow.createCell(37).setCellValue(txn.getMid().getBnplMid());
                excelRow.createCell(38).setCellValue(txn.getMid().getFiuuMid());
                
                           
                    }else{
                           excelRow.createCell(7).setCellValue("");
                           
                           
                           excelRow.createCell(8).setCellValue("");
                           
                           excelRow.createCell(9).setCellValue("");
                           excelRow.createCell(10).setCellValue("");
                           excelRow.createCell(11).setCellValue("");
                           excelRow.createCell(12).setCellValue("");
                           excelRow.createCell(13).setCellValue("");
                           excelRow.createCell(14).setCellValue("");
                           
                           excelRow.createCell(32).setCellValue("");
                excelRow.createCell(33).setCellValue("");
                excelRow.createCell(34).setCellValue("");
                excelRow.createCell(35).setCellValue("");
                excelRow.createCell(36).setCellValue("");
                excelRow.createCell(37).setCellValue("");
                excelRow.createCell(38).setCellValue("");
                    }
                    
                    //System.out.println("checking data: "+txn.getRole());
             
                    excelRow.createCell(15).setCellValue(txn.getContactPersonPhoneNo());
                    excelRow.createCell(16).setCellValue(txn.getBusinessAddress1());
                    excelRow.createCell(17).setCellValue(txn.getCity());
                    excelRow.createCell(18).setCellValue(txn.getState());
                    excelRow.createCell(19).setCellValue(txn.getPostcode());
                    excelRow.createCell(20).setCellValue(txn.getContactPersonName());
                    excelRow.createCell(21).setCellValue(txn.getOwnerPassportNo());
                    
                    
                    
                    
                    //excelRow.createCell(5).setCellValue(txn.getRole().toString());
                    if(txn.getRole().toString().equals("BANK_MERCHANT"))
                    {
                           excelRow.createCell(22).setCellValue("MERCHANT");
                    }
                    else
                    {
                           excelRow.createCell(22).setCellValue("NON_MERCHANT");
                    }
                    if(txn.getNatureOfBusiness()!=null)
            {
                           excelRow.createCell(23).setCellValue(txn.getNatureOfBusiness());
            }
            else
            {
            excelRow.createCell(23).setCellValue("");
            }
                    
                    excelRow.createCell(24).setCellValue(txn.getCompanyType());
                    
                    if(txn.getBankName() != null){
                           excelRow.createCell(25).setCellValue(txn.getBankName());
                    }else{
                           excelRow.createCell(25).setCellValue("");
                    }
                    
                    if(txn.getBankAcc() != null){
                           excelRow.createCell(26).setCellValue(txn.getBankAcc());
                    }else{
                           excelRow.createCell(26).setCellValue("");
                    }
                    
                    excelRow.createCell(27).setCellValue(txn.getPreAuth());
                    
                    excelRow.createCell(28).setCellValue(txn.getAutoSettled());
                    excelRow.createCell(29).setCellValue(txn.getFaxNo());
                    if(txn.getAuth3DS()!=null) {
                           excelRow.createCell(30).setCellValue(txn.getAuth3DS());
                    }else {
                           excelRow.createCell(30).setCellValue("No");
                    }
                    
                    System.out.println("OTP: "+txn.getAuth3DS());
                    excelRow.createCell(31).setCellValue(txn.getRemarks());
                    /*excelRow.createCell(23).setCellValue(txn.getLastName());
                    excelRow.createCell(24).setCellValue(txn.getFirstName());*/
                    
                    
                    /*excelRow.createCell(6).setCellValue(txn.getEmail());*/
                    
                    /*excelRow.createCell(0).setCellValue(txn.getDate());
                    excelRow.createCell(1).setCellValue(txn.getAgentName());
                    excelRow.createCell(2).setCellValue(txn.getMerchantName());
                    excelRow.createCell(3).setCellValue(txn.getLocation());
                    excelRow.createCell(4).setCellValue(txn.getAmount());*/
                    
                    
                    
             }
       }

}

