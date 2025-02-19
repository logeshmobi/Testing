package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.MotoVCDetails;


public class VCExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("VC Summary");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("vcTxnList");
		setExcelRows(excelSheet,txnList);
		
	}
	
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Name On Card");
		
		excelHeader.createCell(2).setCellValue("Card No");
		excelHeader.createCell(3).setCellValue("Status");
		/*excelHeader.createCell(4).setCellValue("DeviceHolderName");
		excelHeader.createCell(5).setCellValue("Location");*/
		
		//excelHeader.createCell(4).setCellValue("Stan");
		excelHeader.createCell(4).setCellValue("Amount");
		excelHeader.createCell(5).setCellValue("RES MSG");
		
//		excelHeader.createCell(9).setCellValue("Card Brand");
//		excelHeader.createCell(10).setCellValue("Location");
		/*excelHeader.createCell(3).setCellValue("State");
		excelHeader.createCell(4).setCellValue("Amount");*/
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<MotoVCDetails> txnList){
		int record = 1;
		for (MotoVCDetails txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getCreatedBy());
			excelRow.createCell(1).setCellValue(txn.getNameOnCard());
			
			excelRow.createCell(2).setCellValue(txn.getTxnDetails());
			if(txn.getStatus().equals("PENDING")){
				excelRow.createCell(3).setCellValue("PROCESSING");
			}else if(txn.getStatus().equals("SUBMITTED")) {
				excelRow.createCell(3).setCellValue("SUBMITTED");
			}else if(txn.getStatus().equals("REJECTED")) {
				excelRow.createCell(3).setCellValue("REJECTED");
			}else if(txn.getStatus().equals("APPROVED")) {
				excelRow.createCell(3).setCellValue("APPROVED");
			}else if(txn.getStatus().equals("CANCELLED")) {
				excelRow.createCell(3).setCellValue("CANCELLED");
			}
			
			
			//excelRow.createCell(4).setCellValue(txn.getMerchantName());
			//excelRow.createCell(4).setCellValue(txn.getStan());
			excelRow.createCell(4).setCellValue(txn.getAmount());
			excelRow.createCell(5).setCellValue(txn.getRespMsg());
		/*	excelRow.createCell(6).setCellValue(txn.getF011_AUTHIDRESP());
			
			excelRow.createCell(7).setCellValue(txn.getPAN());
			excelRow.createCell(8).setCellValue(txn.getCardType());
//			excelRow.createCell(9).setCellValue(txn.getBatchNo());
//			excelRow.createCell(10).setCellValue(txn.getLocation());
			
			
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getAgentName());
			excelRow.createCell(2).setCellValue(txn.getMerchantName());
			excelRow.createCell(3).setCellValue(txn.getLocation());
			excelRow.createCell(4).setCellValue(txn.getAmount());*/
			
			
			
		}
	}

}
