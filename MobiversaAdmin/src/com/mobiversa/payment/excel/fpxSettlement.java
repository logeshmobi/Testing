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

public class fpxSettlement extends AbstractExcelView{

	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		System.out.println("Here");
		
		HSSFSheet excelSheet = workbook.createSheet("Settlement MDR List");
		setExcelHeader(excelSheet);
		
		List txnList = (List)model.get("txnList2");
		setExcelRows(excelSheet,txnList);
		
	}

	@SuppressWarnings("static-method")
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("FPX Txn Id");
		excelHeader.createCell(1).setCellValue("Transaction Amount");
		
		excelHeader.createCell(2).setCellValue("Seller Order No");
        excelHeader.createCell(3).setCellValue("Transaction Date");
        excelHeader.createCell(4).setCellValue("Payable Amount");
        excelHeader.createCell(5).setCellValue("Settlement Date");
		
		
		
		
	}
	
	@SuppressWarnings("static-method")
	public void setExcelRows(HSSFSheet excelSheet, List<FpxTransaction> txnList){
		int record = 1;
		for (FpxTransaction txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getTxnAmount());
			excelRow.createCell(1).setCellValue(txn.getFpxTxnId());
			
			excelRow.createCell(2).setCellValue(txn.getSellerOrderNo());
            excelRow.createCell(3).setCellValue(txn.getTxDate());
            excelRow.createCell(4).setCellValue(txn.getPayableAmt());
            excelRow.createCell(5).setCellValue(txn.getSettledDate());
			
		}
	}



}
