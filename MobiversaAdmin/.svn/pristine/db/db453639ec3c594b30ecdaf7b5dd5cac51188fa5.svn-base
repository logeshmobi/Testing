package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.FpxTransaction;


public class fpxSettlementMDRExcel extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Settlemrnt MDR List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("settlementMDRList");
		setExcelRows(excelSheet,txnList);
		
	}

	@SuppressWarnings("static-method")
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("SettleDate");
		excelHeader.createCell(1).setCellValue("Merchant Name");		
		excelHeader.createCell(2).setCellValue("MID");		
		excelHeader.createCell(3).setCellValue("Transaction Amount");
		excelHeader.createCell(4).setCellValue("Host MDR");
		excelHeader.createCell(5).setCellValue("Mobi MDR");
		excelHeader.createCell(6).setCellValue("Merchant MDR");
		excelHeader.createCell(7).setCellValue("Buyer Bank Id");
		excelHeader.createCell(8).setCellValue("Seller Order No");
		
	}
	
	@SuppressWarnings("static-method")
	public void setExcelRows(HSSFSheet excelSheet, List<FpxTransaction> txnList){
		int record = 1;
		for (FpxTransaction txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getSettledDate());
			excelRow.createCell(1).setCellValue(txn.getMakerName());			
			excelRow.createCell(2).setCellValue(txn.getMid());
			excelRow.createCell(3).setCellValue(txn.getTxnAmount());
			excelRow.createCell(4).setCellValue(txn.getHostMdrAmt());
			excelRow.createCell(5).setCellValue(txn.getMobiMdrAmt());			
			excelRow.createCell(6).setCellValue(txn.getMdrAmt());
			excelRow.createCell(7).setCellValue(txn.getBuyerBankId());
			excelRow.createCell(8).setCellValue(txn.getSellerExOrderNo());

			
		}
	}

}
