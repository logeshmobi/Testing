package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.dto.MerchantDTO;

public class MerchantSummaryAgentExcelView extends AbstractExcelView {
       
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
             excelHeader.createCell(2).setCellValue("Contact Number");
             excelHeader.createCell(3).setCellValue("City");
             excelHeader.createCell(4).setCellValue("Email");
             excelHeader.createCell(5).setCellValue("Auth 3DS");
             excelHeader.createCell(6).setCellValue("Um_Mid");
             excelHeader.createCell(7).setCellValue("Um_Moto_Mid");
             excelHeader.createCell(8).setCellValue("Um_Ezyway_Mid");
             excelHeader.createCell(9).setCellValue("Boost Mid");
             excelHeader.createCell(10).setCellValue("Grab Mid");
             excelHeader.createCell(11).setCellValue("Tng Mid");
             excelHeader.createCell(12).setCellValue("Shopee pay Mid");
            
             
       }
       
       public void setExcelRows(HSSFSheet excelSheet, List<MerchantDTO> txnList){
             int record = 1;
             for (MerchantDTO txn : txnList) {
                    HSSFRow excelRow = excelSheet.createRow(record++);
                    
                    excelRow.createCell(0).setCellValue(txn.getCreatedBy() != null ? txn.getCreatedBy() : "");
                    excelRow.createCell(1).setCellValue(txn.getBusinessName() != null ? txn.getBusinessName() : "");
                    excelRow.createCell(2).setCellValue(txn.getBusinessContactNo() != null ? txn.getBusinessContactNo() : "");
                    excelRow.createCell(3).setCellValue(txn.getCity() != null ? txn.getCity() : "");
                    excelRow.createCell(4).setCellValue(txn.getEmail() != null ? txn.getEmail() : "");
                    excelRow.createCell(5).setCellValue(txn.getAuth3DS() != null ? txn.getAuth3DS() : "");
                    excelRow.createCell(6).setCellValue(txn.getUmMid() != null ? txn.getUmMid() : "");
                    excelRow.createCell(7).setCellValue(txn.getUmMotoMid() != null ? txn.getUmMotoMid() : "");
                    excelRow.createCell(8).setCellValue(txn.getUmEzywayMid() != null ? txn.getUmEzywayMid() : "");
                    excelRow.createCell(9).setCellValue(txn.getBoostMid() != null ? txn.getBoostMid() : "");
                    excelRow.createCell(10).setCellValue(txn.getGrabMid() != null ? txn.getGrabMid() : "");
                    excelRow.createCell(11).setCellValue(txn.getTngMid() != null ? txn.getTngMid() : "");
                    excelRow.createCell(12).setCellValue(txn.getShoppyMid() != null ? txn.getShoppyMid() : "");

             }
       }

}

