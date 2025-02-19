package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.dto.MerchantGPVData;


public class MerchantGPVForSuperAgentExcel extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Merchant GPV List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("merchantGPVList");
		setExcelRows(excelSheet,txnList);
		
	}

	@SuppressWarnings("static-method")
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Merchant Name");
		excelHeader.createCell(1).setCellValue("Agent Name");		
		excelHeader.createCell(2).setCellValue("Merchant Type");		
		excelHeader.createCell(3).setCellValue("EZYLINK");
		excelHeader.createCell(4).setCellValue("EZYMOTO");
		excelHeader.createCell(5).setCellValue("EZYMOTO-VCC");
		excelHeader.createCell(6).setCellValue("EZYWAY");
		excelHeader.createCell(7).setCellValue("EZYREC");
		excelHeader.createCell(8).setCellValue("RECPLUS");
		excelHeader.createCell(9).setCellValue("EZYWIRE");
		excelHeader.createCell(10).setCellValue("Total");
		
	}
	
	@SuppressWarnings("static-method")
	public void setExcelRows(HSSFSheet excelSheet, List<MerchantGPVData> txnList){
		int record = 1;
		for (MerchantGPVData txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getMerchantName());
			excelRow.createCell(1).setCellValue(txn.getAgentName());			
			excelRow.createCell(2).setCellValue(txn.getMerchantType());
			excelRow.createCell(3).setCellValue(txn.getEzylinkAmt());
			excelRow.createCell(4).setCellValue(txn.getEzymotoAmt());
			excelRow.createCell(5).setCellValue(txn.getEzymotoVccAmt());			
			excelRow.createCell(6).setCellValue(txn.getEzywayAmt());
			excelRow.createCell(7).setCellValue(txn.getEzyrecAmt());
			excelRow.createCell(8).setCellValue(txn.getRecplusAmt());
			excelRow.createCell(9).setCellValue(txn.getEzywireAmt());
			excelRow.createCell(10).setCellValue(txn.getTotalGpv());

			
		}
	}

}
