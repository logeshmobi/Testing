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

public class AllTxnListExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("All Transaction List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Time");
		/*excelHeader.createCell(2).setCellValue("MID");*/
		excelHeader.createCell(2).setCellValue("Business Name");
		excelHeader.createCell(3).setCellValue("MID");
		excelHeader.createCell(4).setCellValue("Status");
		excelHeader.createCell(5).setCellValue("Amount");
		excelHeader.createCell(6).setCellValue("DeviceHolderName");
		excelHeader.createCell(7).setCellValue("Location");
		excelHeader.createCell(8).setCellValue("TXN-Type");
		excelHeader.createCell(9).setCellValue("CARD Number");
		excelHeader.createCell(10).setCellValue("CARD-TYPE");
		excelHeader.createCell(11).setCellValue("CARD-SCHEME");
		/*excelHeader.createCell(3).setCellValue("State");
		excelHeader.createCell(4).setCellValue("Amount");*/
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<ForSettlement> txnList){
		int record = 1;
		for (ForSettlement txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getTime());
			/*excelRow.createCell(2).setCellValue(txn.getMid());*/
			excelRow.createCell(2).setCellValue(txn.getNumOfSale());
			excelRow.createCell(3).setCellValue(txn.getMid());
			excelRow.createCell(4).setCellValue(txn.getStatus());
			excelRow.createCell(5).setCellValue(txn.getAmount());
			
			excelRow.createCell(6).setCellValue(txn.getMerchantName());
			excelRow.createCell(7).setCellValue(txn.getLocation());
			excelRow.createCell(8).setCellValue(txn.getTxnType());
			excelRow.createCell(9).setCellValue(txn.getPan());
			excelRow.createCell(10).setCellValue(txn.getCardType());
			excelRow.createCell(11).setCellValue(txn.getAidResponse());
			
			/*excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getAgentName());
			excelRow.createCell(2).setCellValue(txn.getMerchantName());
			excelRow.createCell(3).setCellValue(txn.getLocation());
			excelRow.createCell(4).setCellValue(txn.getAmount());*/
			
			
			
		}
	}

}

