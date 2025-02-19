package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.util.UMEzyway;


public class PreauthFeeExcel extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Transaction List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("umTxnList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Time");
		excelHeader.createCell(2).setCellValue("MID");
		excelHeader.createCell(3).setCellValue("TID");
		excelHeader.createCell(4).setCellValue("Merchant Name");
		excelHeader.createCell(5).setCellValue("Amount");
	    excelHeader.createCell(6).setCellValue("Remaining Amt");
		;
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<UMEzyway> txnList){
		int record = 1;
		for (UMEzyway txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getTime());
			excelRow.createCell(2).setCellValue(txn.getF001_MID());
			excelRow.createCell(3).setCellValue(txn.getF354_TID());
			excelRow.createCell(4).setCellValue(txn.getMerchantName());
			excelRow.createCell(5).setCellValue(txn.getF007_TXNAMT());
			excelRow.createCell(6).setCellValue(txn.getAuthremamt());
			
			
			
		}
	}

}
