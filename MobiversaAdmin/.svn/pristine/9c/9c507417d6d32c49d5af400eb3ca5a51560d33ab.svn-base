package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.BizAppSettlement;


public class bizappMerchantSummaryExcel extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Settlemrnt MDR List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
	}

	@SuppressWarnings("static-method")
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Settlement Date");
		excelHeader.createCell(1).setCellValue("Merchant Name");	
		excelHeader.createCell(2).setCellValue("Transaction Amount");
		excelHeader.createCell(3).setCellValue("Host MDR");
		excelHeader.createCell(4).setCellValue("Mobi MDR");
		excelHeader.createCell(5).setCellValue("Merchant MDR");
		excelHeader.createCell(6).setCellValue("Deduction Amount");
		excelHeader.createCell(7).setCellValue("Net Amount");
		excelHeader.createCell(8).setCellValue("Trade Name");
		excelHeader.createCell(9).setCellValue("Reference");
		excelHeader.createCell(10).setCellValue("Status");
		excelHeader.createCell(11).setCellValue("Bank Name");
		excelHeader.createCell(12).setCellValue("Email");
		excelHeader.createCell(13).setCellValue("Account Number");
	}
	
	@SuppressWarnings("static-method")
	public void setExcelRows(HSSFSheet excelSheet, List<BizAppSettlement> txnList){
		int record = 1;
		for (BizAppSettlement txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getSettlementDate());
			excelRow.createCell(1).setCellValue(txn.getMerchantName());
		
			excelRow.createCell(2).setCellValue(txn.getGrossAmt());
			excelRow.createCell(3).setCellValue(txn.getHostMdrAmt());
			excelRow.createCell(4).setCellValue(txn.getMobiMdrAmt());
			
			excelRow.createCell(5).setCellValue(txn.getMdrAmt());
			excelRow.createCell(6).setCellValue(txn.getDetectionAmt());
			excelRow.createCell(7).setCellValue(txn.getNetAmt());
			excelRow.createCell(8).setCellValue(txn.getTradeName());
			excelRow.createCell(9).setCellValue(txn.getReference());
			excelRow.createCell(10).setCellValue(txn.getStatus());
			excelRow.createCell(11).setCellValue(txn.getBankName());
			excelRow.createCell(12).setCellValue(txn.getEmail());
			excelRow.createCell(13).setCellValue(txn.getAccountNo());
			
		}
	}

}
