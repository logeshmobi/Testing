//package com.mobiversa.payment.excel;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.web.servlet.view.document.AbstractExcelView;
//
//import com.mobiversa.common.bo.FpxTransaction;
//
//public class fpxfailedtxnsummaryExcel extends AbstractExcelView {
//	@Override
//	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		logger.info("FPX Failed Transaction List");
//
//		HSSFSheet excelSheet = workbook.createSheet("FPX Failed Transaction List");
//		setExcelHeader(excelSheet);
//
//		List txnList = (List)model.get("txnList2");
//		setExcelRows(excelSheet,txnList);
//
//	}
//
//	@SuppressWarnings("static-method")
//	public void setExcelHeader(HSSFSheet excelSheet) {
//		HSSFRow excelHeader = excelSheet.createRow(0);
//		excelHeader.createCell(0).setCellValue("TimeStamp");
//		excelHeader.createCell(1).setCellValue("MID");
//		excelHeader.createCell(2).setCellValue("TID");
//		excelHeader.createCell(3).setCellValue("Amount(RM)");
//		excelHeader.createCell(4).setCellValue("SellerOrderNo");
//		excelHeader.createCell(5).setCellValue("FpxTxnId");
//		excelHeader.createCell(6).setCellValue("DebitAuthNo");
//		excelHeader.createCell(7).setCellValue("Response Message");
//
//
//
//
//
//
//	}
//
//	@SuppressWarnings("static-method")
//	public void setExcelRows(HSSFSheet excelSheet, List<FpxTransaction> txnList){
//		int record = 1;
//		for (FpxTransaction txn : txnList) {
//			HSSFRow excelRow = excelSheet.createRow(record++);
//			excelRow.createCell(0).setCellValue(txn.getTimestamp());
//			excelRow.createCell(1).setCellValue(txn.getMid());
//			excelRow.createCell(2).setCellValue(txn.getTid());
//			excelRow.createCell(3).setCellValue(txn.getTxnAmount());
//			excelRow.createCell(4).setCellValue(txn.getSellerOrderNo());
//			excelRow.createCell(5).setCellValue(txn.getFpxTxnId());
//			excelRow.createCell(6).setCellValue(txn.getDebitAuthNo());
//			excelRow.createCell(7).setCellValue(txn.getDebitAuthCodeStr());
//
//
//
//		}
//	}
//
//}
package com.mobiversa.payment.excel;

import static com.mobiversa.payment.excel.ExportCsvColumn.FPX_FAILED_TRANSACTIONS_EXPORT_HEADER;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.mobiversa.common.bo.FpxTransaction;
import com.opencsv.CSVWriter;

public class fpxfailedtxnsummaryExcel extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment");

        List<FpxTransaction> txnList = (List<FpxTransaction>) model.get("txnList2");

        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()))) {
            setCSVHeader(writer);
            setCSVRows(writer, txnList);
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV response", e);
        }
    }

    public void setCSVHeader(CSVWriter writer) {

        writer.writeNext(FPX_FAILED_TRANSACTIONS_EXPORT_HEADER.getColumnNames().toArray(new String[0]));
    }

    public void setCSVRows(CSVWriter writer, List<FpxTransaction> txnList) {
        for (FpxTransaction txn : txnList) {
            String[] row = {
                    txn.getTimestamp(),
                    txn.getMid(),
                    txn.getTid(),
                    txn.getTxnAmount(),
                    txn.getSellerOrderNo(),
                    txn.getFpxTxnId(),
                    txn.getDebitAuthNo(),
                    txn.getDebitAuthCodeStr()
            };
            writer.writeNext(row);
        }
    }
}