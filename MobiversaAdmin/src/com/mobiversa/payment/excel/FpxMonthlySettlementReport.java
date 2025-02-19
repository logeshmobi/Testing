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

public class FpxMonthlySettlementReport extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		System.out.println("FpxMonthlySettlementReport");
		
		HSSFSheet excelSheet = workbook.createSheet("Settlement List for FPX");
		setExcelHeader(excelSheet);
		
		List txnList = (List)model.get("txnList3");
		setExcelRows(excelSheet,txnList);
		
	}

	@SuppressWarnings("static-method")
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("MID");
		excelHeader.createCell(1).setCellValue("TID");
		excelHeader.createCell(2).setCellValue("TX_DATE");
		excelHeader.createCell(3).setCellValue("TXNAMOUNT");
		excelHeader.createCell(4).setCellValue("MDR_AMT");
		excelHeader.createCell(5).setCellValue("MOBI_MDR_AMT");
		excelHeader.createCell(6).setCellValue("HOST_MDR_AMT");
		excelHeader.createCell(7).setCellValue("PAYABLEAMT");
		excelHeader.createCell(8).setCellValue("SETTLED_DATE");

	}
	
	@SuppressWarnings("static-method")
	public void setExcelRows(HSSFSheet excelSheet, List<FpxTransaction> txnList){
		int record = 1;
		 for (FpxTransaction txn : txnList) {
		        HSSFRow excelRow = excelSheet.createRow(record++);
		        excelRow.createCell(0).setCellValue(txn.getMid()); // MID
		        excelRow.createCell(1).setCellValue(txn.getTid()); // TID
		        excelRow.createCell(2).setCellValue(txn.getTxDate()); // TX_DATE
		        excelRow.createCell(3).setCellValue(txn.getTxnAmount()); // TXNAMOUNT
		        excelRow.createCell(4).setCellValue(txn.getMdrAmt()); // MDR_AMT
		        excelRow.createCell(5).setCellValue(txn.getMobiMdrAmt()); // MOBI_MDR_AMT
		        excelRow.createCell(6).setCellValue(txn.getHostMdrAmt()); // HOST_MDR_AMT
		        excelRow.createCell(7).setCellValue(txn.getPayableAmt()); // PAYABLEAMT
		        excelRow.createCell(8).setCellValue(txn.getSettledDate()); // SETTLED_DATE
		    }
	}


}
