package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.Merchant;

public class PayLaterMerchantExcel extends AbstractExcelView {
	
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
		excelHeader.createCell(2).setCellValue("Status");
		excelHeader.createCell(3).setCellValue("City");
		excelHeader.createCell(4).setCellValue("EZYLINK MID");
		excelHeader.createCell(5).setCellValue("Amount");

	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<Merchant> txnList){
		int record = 1;
		for (Merchant txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);

			excelRow.createCell(0).setCellValue(txn.getCreatedBy());
			excelRow.createCell(1).setCellValue(txn.getBusinessName());
			excelRow.createCell(2).setCellValue(txn.getState());
			excelRow.createCell(3).setCellValue(txn.getCity());
			excelRow.createCell(4).setCellValue(txn.getReferralId());
			excelRow.createCell(5).setCellValue(txn.getMdr());
			
			
			
			
		}
	}

}

