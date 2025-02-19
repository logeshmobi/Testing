package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.TransactionRequest;

public class EnquiryListExcelView  extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Transaction Expiry List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
		System.out.println("innnputtt");
		
	}

	@SuppressWarnings("static-method")
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Time");
		excelHeader.createCell(2).setCellValue("MID");
		excelHeader.createCell(3).setCellValue("Business Name");
		/*excelHeader.createCell(3).setCellValue("MID");*/
		excelHeader.createCell(4).setCellValue("CardNo");
		excelHeader.createCell(5).setCellValue("Amount(RM)");
		excelHeader.createCell(6).setCellValue("Status");
		excelHeader.createCell(7).setCellValue("Stan");
		excelHeader.createCell(8).setCellValue("TID");
		excelHeader.createCell(9).setCellValue("Approve Code");
		/*excelHeader.createCell(10).setCellValue("CARD-TYPE");
		excelHeader.createCell(11).setCellValue("CARD-SCHEME");*/
		/*excelHeader.createCell(3).setCellValue("State");
		excelHeader.createCell(4).setCellValue("Amount");*/
	}
	
	@SuppressWarnings("static-method")
	public void setExcelRows(HSSFSheet excelSheet, List<TransactionRequest> txnList){
		int record = 1;
		for (TransactionRequest txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getTransactionDate());
			excelRow.createCell(1).setCellValue(txn.getTransactionTime());
			excelRow.createCell(2).setCellValue(txn.getMid());
			excelRow.createCell(3).setCellValue(txn.getCardHolderName());
			/*excelRow.createCell(3).setCellValue(txn.getMid());*/
			excelRow.createCell(4).setCellValue(txn.getPan());
			excelRow.createCell(5).setCellValue(txn.getAmount());
			
			excelRow.createCell(6).setCellValue(txn.getPosConditionCode());
			excelRow.createCell(7).setCellValue(txn.getStan());
			excelRow.createCell(8).setCellValue(txn.getTid());
			excelRow.createCell(9).setCellValue(txn.getAid());
			/*excelRow.createCell(10).setCellValue(txn.getCardType());
			excelRow.createCell(11).setCellValue(txn.getPosConditionCode());*/
			
			/*excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getAgentName());
			excelRow.createCell(2).setCellValue(txn.getMerchantName());
			excelRow.createCell(3).setCellValue(txn.getLocation());
			excelRow.createCell(4).setCellValue(txn.getAmount());*/
			
			
			
		}
	}

}
