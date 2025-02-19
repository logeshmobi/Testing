package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.payment.dto.MonthlyTxnDetails;

import org.springframework.web.servlet.view.document.AbstractExcelView;

public class MonthlyTxnListExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Monthly Transaction List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Activate Date");
		excelHeader.createCell(1).setCellValue("MerchantName");
		excelHeader.createCell(2).setCellValue("TID");
		excelHeader.createCell(3).setCellValue("MID");
		excelHeader.createCell(4).setCellValue("Amount");
		excelHeader.createCell(5).setCellValue("No of Txns");
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<MonthlyTxnDetails> txnList){
		int record = 1;
		for (MonthlyTxnDetails txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getMerchantName());
			excelRow.createCell(2).setCellValue(txn.getTid());
			excelRow.createCell(3).setCellValue(txn.getMid());
			excelRow.createCell(4).setCellValue(txn.getAmount());
			excelRow.createCell(5).setCellValue(txn.getNoofTxn());
		}
	}

}
