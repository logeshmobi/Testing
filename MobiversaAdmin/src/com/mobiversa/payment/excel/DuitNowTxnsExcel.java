package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.dto.DuitnowTxnDto;

public class DuitNowTxnsExcel extends AbstractExcelView {
    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        logger.info("inside excel export...");
        
        String fileName = "DuitNow_QR_Transactions.xls";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        HSSFSheet excelSheet = workbook.createSheet("Transaction List");
        setExcelHeader(excelSheet);

        List txnList = (List) model.get("duitnowTxnList");
        setExcelRows(excelSheet,txnList);
        logger.info(" DuitNow QR excel file generated..");
    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Date");
        excelHeader.createCell(1).setCellValue("Time");
        excelHeader.createCell(2).setCellValue("Amount (RM)");
        excelHeader.createCell(3).setCellValue("Invoice ID");
        excelHeader.createCell(4).setCellValue("Transaction ID");
        excelHeader.createCell(5).setCellValue("MDR Amount (RM)");
        excelHeader.createCell(6).setCellValue("Net Amount (RM)");
        excelHeader.createCell(7).setCellValue("Status");
        excelHeader.createCell(8).setCellValue("Payment Date");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<DuitnowTxnDto> txnList){
        int record = 1;
        for (DuitnowTxnDto txn : txnList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(txn.getCreatedDate());
            excelRow.createCell(1).setCellValue(txn.getCreatedTime());
            excelRow.createCell(2).setCellValue(txn.getTxnAmount());
            excelRow.createCell(3).setCellValue(txn.getInvoiceId());
            excelRow.createCell(4).setCellValue(txn.getTransactionId());
            excelRow.createCell(5).setCellValue(txn.getMdrAmount());
            excelRow.createCell(6).setCellValue(txn.getNetAmount());
            excelRow.createCell(7).setCellValue(txn.getStatus());
            excelRow.createCell(8).setCellValue(txn.getSettlementDate());

        }
    }
}
