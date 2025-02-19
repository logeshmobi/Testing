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


public class EzySettleListMerchant extends AbstractExcelView
{
	@Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        try {
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
        try {
            HSSFRow excelHeader = excelSheet.createRow(0);
            excelHeader.createCell(0).setCellValue("Date");
            excelHeader.createCell(1).setCellValue("MID");
            excelHeader.createCell(2).setCellValue("TID");
            excelHeader.createCell(3).setCellValue("Amount(RM)");
            excelHeader.createCell(4).setCellValue("MDR Amount(RM)");
            excelHeader.createCell(5).setCellValue("Net Amount(RM)");
            excelHeader.createCell(6).setCellValue("Status");
            excelHeader.createCell(7).setCellValue("Invoice ID");
        }catch(Exception e)
        {
            e.printStackTrace();
            logger.error("Message is "+e.getMessage());
        }
    }

    public void setExcelRows(HSSFSheet excelSheet, List<SettlementModel> txnList) {
        try {
            int record = 1;
            for (SettlementModel txn : txnList) {
                HSSFRow excelRow = excelSheet.createRow(record++);
                excelRow.createCell(0).setCellValue(txn.getDate());
                excelRow.createCell(1).setCellValue(txn.getMid());
                excelRow.createCell(2).setCellValue(txn.getTid());
                excelRow.createCell(3).setCellValue(txn.getNetAmount());
                excelRow.createCell(4).setCellValue(txn.getMdrAmount());
                excelRow.createCell(5).setCellValue(txn.getTxnAmount());
                excelRow.createCell(6).setCellValue(txn.getStatus());
                excelRow.createCell(7).setCellValue(txn.getInvoiceId());

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Message is " + e.getMessage());
        }
    }

}
