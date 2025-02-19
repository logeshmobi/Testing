package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.BoostDailyRecon;


public class BoostSettlementsumMerchantExcel extends AbstractExcelView {
	
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
		excelHeader.createCell(0).setCellValue("TxnDate");
		excelHeader.createCell(1).setCellValue("Mid");
		excelHeader.createCell(2).setCellValue("Tid");
		excelHeader.createCell(3).setCellValue("TxnAmount");
		excelHeader.createCell(4).setCellValue("NetAmount");
		excelHeader.createCell(5).setCellValue("MdrAmount");	
		excelHeader.createCell(6).setCellValue("SettlementDate");

		}
	
	public void setExcelRows(HSSFSheet excelSheet, List<BoostDailyRecon> txnList){
		int record = 1;
		for (BoostDailyRecon txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getMid());
			excelRow.createCell(2).setCellValue(txn.getTid());
			excelRow.createCell(3).setCellValue(txn.getTxnAmount());
			excelRow.createCell(4).setCellValue(txn.getNetAmount());			
			excelRow.createCell(5).setCellValue(txn.getMdrRebateAmount());
			excelRow.createCell(6).setCellValue(txn.getTimeStamp());
			
			
		}
	}

}
