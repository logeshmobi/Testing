package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.PreAuthorization;


public class PreauthTxnExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Transaction List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Merchant Name");
		excelHeader.createCell(2).setCellValue("Amount");
		excelHeader.createCell(3).setCellValue("Agent Name");
		excelHeader.createCell(4).setCellValue("Txn Type");
		
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<PreAuthorization> txnList){
		int record = 1;
		for (PreAuthorization txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getMerchantName());		
			excelRow.createCell(2).setCellValue(txn.getAmount());		
			excelRow.createCell(3).setCellValue(txn.getAgentName());		
			excelRow.createCell(4).setCellValue(txn.getTxnType());
			
	
		}
	}

}
