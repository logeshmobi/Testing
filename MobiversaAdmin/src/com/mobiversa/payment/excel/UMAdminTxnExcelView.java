package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.util.UMEzyway;


public class UMAdminTxnExcelView extends AbstractExcelView {
	
	private static final Logger logger = Logger.getLogger(UMAdminTxnExcelView.class); 
	
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
		excelHeader.createCell(2).setCellValue("Txn Type");
		excelHeader.createCell(3).setCellValue("Status");
		excelHeader.createCell(4).setCellValue("Amount");
		excelHeader.createCell(5).setCellValue("Name on Card");
		excelHeader.createCell(6).setCellValue("Reference");
		excelHeader.createCell(7).setCellValue("Merchant Name");
		excelHeader.createCell(8).setCellValue("RRN");
		excelHeader.createCell(9).setCellValue("Approve Code");
		excelHeader.createCell(10).setCellValue("Card No");		
		excelHeader.createCell(11).setCellValue("Card Type");
		excelHeader.createCell(12).setCellValue("MID");
		excelHeader.createCell(13).setCellValue("TID");
		excelHeader.createCell(14).setCellValue("SubMerchantMid");
		excelHeader.createCell(15).setCellValue("Host Type");

	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<UMEzyway> txnList){
		int record = 1;
		for (UMEzyway txn : txnList) {
			logger.info(txn);
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getTime());
			excelRow.createCell(2).setCellValue(txn.getTxnType());
			excelRow.createCell(3).setCellValue(txn.getSTATUS());
			excelRow.createCell(4).setCellValue(txn.getF007_TXNAMT());
			excelRow.createCell(5).setCellValue(txn.getF268_CHNAME());
			excelRow.createCell(6).setCellValue(txn.getF270_ORN());

			excelRow.createCell(7).setCellValue(txn.getMerchantName());
			excelRow.createCell(8).setCellValue(txn.getF023_RRN());
			excelRow.createCell(9).setCellValue(txn.getF011_AUTHIDRESP());
			
			excelRow.createCell(10).setCellValue(txn.getPAN());
			excelRow.createCell(11).setCellValue(txn.getCardType());
			excelRow.createCell(12).setCellValue(txn.getF001_MID());
			excelRow.createCell(13).setCellValue(txn.getF354_TID());
			excelRow.createCell(14).setCellValue(txn.getSubmid());
			excelRow.createCell(15).setCellValue(txn.getServiceId());

		}
	}

}
