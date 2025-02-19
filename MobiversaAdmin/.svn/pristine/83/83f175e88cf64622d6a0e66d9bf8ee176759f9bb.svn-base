package com.mobiversa.payment.excel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.MobileUser;

public class MobileUserExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("All MobileUser List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Activation Date");
		excelHeader.createCell(1).setCellValue("UserName");
		excelHeader.createCell(2).setCellValue("MerchantName");
		excelHeader.createCell(3).setCellValue("MID");
		excelHeader.createCell(4).setCellValue("TID");
		excelHeader.createCell(5).setCellValue("DeviceId");
		excelHeader.createCell(6).setCellValue("Device Type");
		excelHeader.createCell(7).setCellValue("Expiry Date");
		//excelHeader.createCell(1).setCellValue("UserName");
		//excelHeader.createCell(6).setCellValue("Location");
		/*excelHeader.createCell(3).setCellValue("State");
		excelHeader.createCell(4).setCellValue("Amount");*/
	}
	
	public void setExcelRows(HSSFSheet excelSheet, List<MobileUser> txnList){
		int record = 1;
		for (MobileUser txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			
			 String pattern = "dd-MMM-yyyy";
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			 String date = simpleDateFormat.format(txn.getActivateDate());
			 
			 System.out.println("DATA : "+date);
			excelRow.createCell(0).setCellValue(date);
			
			excelRow.createCell(1).setCellValue(txn.getUsername());

			excelRow.createCell(2).setCellValue(txn.getCreatedBy());
			excelRow.createCell(3).setCellValue(txn.getConnectType());
			excelRow.createCell(4).setCellValue(txn.getTid());
			excelRow.createCell(5).setCellValue(txn.getDeviceId());
			excelRow.createCell(6).setCellValue(txn.getDeviceType());
			if(txn.getSuspendDate() != null){
				excelRow.createCell(7).setCellValue(simpleDateFormat.format(txn.getSuspendDate()));
			}else{
				excelRow.createCell(7).setCellValue("");
			}
			//excelRow.createCell(4).setCellValue(txn.getAmount());
			
			//excelRow.createCell(5).setCellValue(txn.username());
			//excelRow.createCell(6).setCellValue(txn.getLocation());
			
			/*excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getAgentName());
			excelRow.createCell(2).setCellValue(txn.getMerchantName());
			excelRow.createCell(3).setCellValue(txn.getLocation());
			excelRow.createCell(4).setCellValue(txn.getAmount());*/
			
			
			
		}
	}

}

