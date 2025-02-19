package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.ForSettlement;


public class PaydeeMerchantMotoExcel extends AbstractExcelView {
	
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
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Time");	
		excelHeader.createCell(2).setCellValue("Mid");
		excelHeader.createCell(3).setCellValue("Tid");
		excelHeader.createCell(4).setCellValue("Amount");
		excelHeader.createCell(5).setCellValue("Name on Card");
		excelHeader.createCell(6).setCellValue("Card No");	
		excelHeader.createCell(7).setCellValue("Reference");
		excelHeader.createCell(8).setCellValue("Approve Code");
		excelHeader.createCell(9).setCellValue("Rrn");
		excelHeader.createCell(10).setCellValue("Stan");
		excelHeader.createCell(11).setCellValue("Txn Type");
		excelHeader.createCell(12).setCellValue("Status");
		

	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<ForSettlement> txnList){
		int record = 1;
		for (ForSettlement txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getTime());
			excelRow.createCell(2).setCellValue(txn.getMid());
			excelRow.createCell(3).setCellValue(txn.getTid());
			excelRow.createCell(4).setCellValue(txn.getAmount());
			excelRow.createCell(5).setCellValue(txn.getNumOfRefund());
			excelRow.createCell(6).setCellValue(txn.getPan());
			excelRow.createCell(7).setCellValue(txn.getInvoiceId());
			excelRow.createCell(8).setCellValue(txn.getAidResponse());
			excelRow.createCell(9).setCellValue(txn.getRrn());
			excelRow.createCell(10).setCellValue(txn.getStan());
			excelRow.createCell(11).setCellValue(txn.getTxnType());
			excelRow.createCell(12).setCellValue(txn.getStatus());
			
		}
	}

}
