package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.util.SettlementModel;

public class EzySettleListExcel extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
          HttpServletResponse response) throws Exception {
       try {
          logger.info("Inside the generating Excel");
          HSSFSheet excelSheet = workbook.createSheet("EZYSETTLE Transaction List");
          setExcelHeader(excelSheet);

          List txnList = (List) model.get("txnList");
          setExcelRows(excelSheet, txnList);
       }catch (Exception e)
       {
          e.printStackTrace();
          System.out.println("REASON --->"+e.getMessage());
       }

    }

    public void setExcelHeader(HSSFSheet excelSheet) {
       HSSFRow excelHeader = excelSheet.createRow(0);
       excelHeader.createCell(0).setCellValue("Date");
       excelHeader.createCell(1).setCellValue("MID");
       excelHeader.createCell(2).setCellValue("TID");
       excelHeader.createCell(3).setCellValue("Merchant Name");
       excelHeader.createCell(4).setCellValue("Amount(RM)");
       excelHeader.createCell(5).setCellValue("MDR Amount(RM)");
       excelHeader.createCell(6).setCellValue("Net Amount(RM)");
       excelHeader.createCell(7).setCellValue("Status");
       excelHeader.createCell(8).setCellValue("Invoice ID");
       excelHeader.createCell(9).setCellValue("RRN");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<SettlementModel> txnList) {
       int record = 1;
       for (SettlementModel txn : txnList) {
          HSSFRow excelRow = excelSheet.createRow(record++);
          excelRow.createCell(0).setCellValue(txn.getDate());
          excelRow.createCell(1).setCellValue(txn.getMid());
          excelRow.createCell(2).setCellValue(txn.getTid());
          excelRow.createCell(3).setCellValue(txn.getMerchantName());
          excelRow.createCell(4).setCellValue(txn.getNetAmount());
          excelRow.createCell(5).setCellValue(txn.getMdrAmount());
          excelRow.createCell(6).setCellValue(txn.getTxnAmount());
          excelRow.createCell(7).setCellValue(txn.getStatus());
          excelRow.createCell(8).setCellValue(txn.getRrn());
          excelRow.createCell(9).setCellValue(txn.getInvoiceId());

       }
    }

}
 