package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.MobiProductMDR;


public class UMChargeBackExcel extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Transaction List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("umCBList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("MID");
		
		excelHeader.createCell(2).setCellValue("Status");
		excelHeader.createCell(3).setCellValue("Amount");
		/*excelHeader.createCell(4).setCellValue("DeviceHolderName");
		excelHeader.createCell(5).setCellValue("Location");*/
		
		//excelHeader.createCell(4).setCellValue("Stan");
//		excelHeader.createCell(4).setCellValue("Merchant Name");
//		excelHeader.createCell(5).setCellValue("RRN");
//		excelHeader.createCell(6).setCellValue("Approve Code");
//		excelHeader.createCell(7).setCellValue("Card No");
//		
//		excelHeader.createCell(8).setCellValue("Card Type");
//		excelHeader.createCell(9).setCellValue("Response Message");
		
		
//		excelHeader.createCell(9).setCellValue("Card Brand");
//		excelHeader.createCell(10).setCellValue("Location");
		/*excelHeader.createCell(3).setCellValue("State");
		excelHeader.createCell(4).setCellValue("Amount");*/
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<MobiProductMDR> txnList){
		int record = 1;
		for (MobiProductMDR txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getTimeStamp());
			excelRow.createCell(1).setCellValue(txn.getMid());
			
			excelRow.createCell(2).setCellValue(txn.getStatus().name());
			excelRow.createCell(3).setCellValue(txn.getHostMdr());
			
			//excelRow.createCell(4).setCellValue(txn.getMerchantName());
			//excelRow.createCell(4).setCellValue(txn.getStan());
//			excelRow.createCell(4).setCellValue(txn.getMerchantName());
//			excelRow.createCell(5).setCellValue(txn.getF023_RRN());
//			excelRow.createCell(6).setCellValue(txn.getF011_AUTHIDRESP());
//			
//			excelRow.createCell(7).setCellValue(txn.getPAN());
//			excelRow.createCell(8).setCellValue(txn.getCardType());
//			excelRow.createCell(9).setCellValue(txn.getRespMessage());
//			excelRow.createCell(9).setCellValue(txn.getBatchNo());
//			excelRow.createCell(10).setCellValue(txn.getLocation());
			
			
			/*excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getAgentName());
			excelRow.createCell(2).setCellValue(txn.getMerchantName());
			excelRow.createCell(3).setCellValue(txn.getLocation());
			excelRow.createCell(4).setCellValue(txn.getAmount());*/
			
			
			
		}
	}

}
