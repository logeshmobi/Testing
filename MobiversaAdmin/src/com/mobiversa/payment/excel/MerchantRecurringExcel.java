package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.EzyRecurringPayment;


public class MerchantRecurringExcel extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Recurring List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		
		excelHeader.createCell(0).setCellValue("Customer Name");
		excelHeader.createCell(1).setCellValue("Tid");
		excelHeader.createCell(2).setCellValue("Card No");
		//excelHeader.createCell(2).setCellValue("Card Holder Name");
		excelHeader.createCell(3).setCellValue("Amount");
		excelHeader.createCell(4).setCellValue("Frequency");
		excelHeader.createCell(5).setCellValue("No of Payments");
		
		
		excelHeader.createCell(6).setCellValue("Last Trigger Date");
		excelHeader.createCell(7).setCellValue("Next Trigger Date");
		excelHeader.createCell(8).setCellValue("End Date");
		
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<EzyRecurringPayment> txnList){
		int record = 1;
		for (EzyRecurringPayment txn : txnList) {
			//logger.info("merchant recurring excel: ");
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getCustName());
			excelRow.createCell(1).setCellValue(txn.getTid());
			//excelRow.createCell(1).setCellValue(txn.getTime());
			
			excelRow.createCell(2).setCellValue(txn.getMaskedPan());
			//excelRow.createCell(2).setCellValue(txn.getCardHolderName());
			excelRow.createCell(3).setCellValue(txn.getAmount());
			excelRow.createCell(4).setCellValue(txn.getPeriod());
			excelRow.createCell(5).setCellValue(txn.getInstallmentCount());
			
			excelRow.createCell(6).setCellValue(txn.getLastTriggerDate());
			excelRow.createCell(7).setCellValue(txn.getNextTriggerDate());
			excelRow.createCell(8).setCellValue(txn.getEndDate());
			
			/*excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getAgentName());
			excelRow.createCell(2).setCellValue(txn.getMerchantName());
			excelRow.createCell(3).setCellValue(txn.getLocation());
			excelRow.createCell(4).setCellValue(txn.getAmount());*/
			
			
			
		}
	}

}
