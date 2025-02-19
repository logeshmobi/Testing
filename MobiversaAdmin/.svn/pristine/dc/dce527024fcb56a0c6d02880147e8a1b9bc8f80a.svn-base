package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.SettlementMDR;


public class settlementMDRExcel extends AbstractExcelView {
	
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
		excelHeader.createCell(3).setCellValue("TID");
		excelHeader.createCell(4).setCellValue("TYPE");
		excelHeader.createCell(5).setCellValue("CARD Brand");
		excelHeader.createCell(6).setCellValue("CARD Type");
		
		
		excelHeader.createCell(7).setCellValue("Masked PAN");
		excelHeader.createCell(8).setCellValue("Transaction Amount");
		excelHeader.createCell(9).setCellValue("Host MDR");
		excelHeader.createCell(10).setCellValue("Mobi MDR");
		excelHeader.createCell(11).setCellValue("Merchant MDR");
		excelHeader.createCell(12).setCellValue("Deduction Amount");
		excelHeader.createCell(13).setCellValue("Net Amount");
	}
	
	@SuppressWarnings("static-method")
	public void setExcelRows(HSSFSheet excelSheet, List<SettlementMDR> txnList){
		int record = 1;
		for (SettlementMDR txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getSettlementDate());
			excelRow.createCell(1).setCellValue(txn.getMerchantName());
			
			excelRow.createCell(2).setCellValue(txn.getMid());
			excelRow.createCell(3).setCellValue(txn.getTid());
			excelRow.createCell(4).setCellValue(txn.getTxnType());
			excelRow.createCell(5).setCellValue(txn.getCardBrand());
			
			excelRow.createCell(6).setCellValue(txn.getCardType());
			excelRow.createCell(7).setCellValue(txn.getMaskedPan());
			excelRow.createCell(8).setCellValue(txn.getTxnAmount());
			excelRow.createCell(9).setCellValue(txn.getHostMdrAmt());
			excelRow.createCell(10).setCellValue(txn.getMobiMdrAmt());
			
			excelRow.createCell(11).setCellValue(txn.getMdrAmt());
			excelRow.createCell(12).setCellValue(txn.getExtraDeductAmt());
			excelRow.createCell(13).setCellValue(txn.getNetAmount());
			
		}
	}

}
