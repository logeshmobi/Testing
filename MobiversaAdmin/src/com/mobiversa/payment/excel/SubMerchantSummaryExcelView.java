package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.Merchant;

public class SubMerchantSummaryExcelView extends AbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Submerchant Summary List");
		setExcelHeader(excelSheet);
		
		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet,txnList);
		
	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Activation Date");
		excelHeader.createCell(1).setCellValue("BusinessName");
		excelHeader.createCell(2).setCellValue("Email");
		excelHeader.createCell(3).setCellValue("City");
		excelHeader.createCell(4).setCellValue("State");
		excelHeader.createCell(5).setCellValue("Mid");
		excelHeader.createCell(6).setCellValue("Main_Merchant");
		
		
		
	
		//excelHeader.createCell(6).setCellValue("Location");
		/*excelHeader.createCell(3).setCellValue("State");
		excelHeader.createCell(4).setCellValue("Amount");*/
	}
	
	
	public void setExcelRows(HSSFSheet excelSheet, List<Merchant> txnList){
		int record = 1;
		for (Merchant txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			
//			String pattern = "dd-MMM-yyyy";
//			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//			 String date = simpleDateFormat.format(txn.getCreatedBy());
//			 
//			 //System.out.println("DATA : "+date);
//			excelRow.createCell(0).setCellValue(date);
			
			excelRow.createCell(0).setCellValue(txn.getCreatedBy());
			if(txn.getBusinessName()!=null) {
				excelRow.createCell(1).setCellValue(txn.getBusinessName());
			}
			if(txn.getEmail()!=null) {
				excelRow.createCell(2).setCellValue(txn.getEmail());
			}
			
			if(txn.getCity()!=null) {
				excelRow.createCell(3).setCellValue(txn.getCity());
			}
			if(txn.getState()!=null) {
				excelRow.createCell(4).setCellValue(txn.getState());
			
			}
			
			
			if (txn.getMobiId() != null) {
				excelRow.createCell(5).setCellValue(txn.getMobiId());

			
			}else{
				excelRow.createCell(5).setCellValue("");
				
				}
			
			 if (txn.getMmId() != null) {
	                excelRow.createCell(6).setCellValue(txn.getMmId());
	           
	            }else{
	                excelRow.createCell(6).setCellValue("");
	                
	                }
			
			//System.out.println("checking data: "+txn.getRole());
		
//			excelRow.createCell(14).setCellValue(txn.getContactPersonPhoneNo());
//			excelRow.createCell(15).setCellValue(txn.getBusinessAddress1());
//			excelRow.createCell(16).setCellValue(txn.getCity());
//			excelRow.createCell(17).setCellValue(txn.getState());
//			excelRow.createCell(18).setCellValue(txn.getPostcode());
//			excelRow.createCell(19).setCellValue(txn.getContactPersonName());
//			excelRow.createCell(20).setCellValue(txn.getOwnerPassportNo());
//			
//			
//			
//			//excelRow.createCell(5).setCellValue(txn.getRole().toString());
//			if(txn.getRole().toString().equals("BANK_MERCHANT"))
//			{
//				excelRow.createCell(21).setCellValue("MERCHANT");
//			}
//			else
//			{
//				excelRow.createCell(21).setCellValue("NON_MERCHANT");
//			}
//			if(txn.getNatureOfBusiness()!=null)
//            {
//				excelRow.createCell(22).setCellValue(txn.getNatureOfBusiness());
//            }
//            else
//            {
//            	excelRow.createCell(22).setCellValue("");
//            }
//			
//			excelRow.createCell(23).setCellValue(txn.getCompanyType());
//			
//			if(txn.getBankName() != null){
//				excelRow.createCell(24).setCellValue(txn.getBankName());
//			}else{
//				excelRow.createCell(24).setCellValue("");
//			}
//			
//			if(txn.getBankAcc() != null){
//				excelRow.createCell(25).setCellValue(txn.getBankAcc());
//			}else{
//				excelRow.createCell(25).setCellValue("");
//			}
//			
//			excelRow.createCell(26).setCellValue(txn.getPreAuth());
//			
//			excelRow.createCell(27).setCellValue(txn.getAutoSettled());
//			excelRow.createCell(28).setCellValue(txn.getFaxNo());
//			if(txn.getAuth3DS()!=null) {
//				excelRow.createCell(29).setCellValue(txn.getAuth3DS());
//			}else {
//				excelRow.createCell(29).setCellValue("No");
//			}
//			
//			System.out.println("OTP: "+txn.getAuth3DS());
//			excelRow.createCell(30).setCellValue(txn.getRemarks());
			/*excelRow.createCell(23).setCellValue(txn.getLastName());
			excelRow.createCell(24).setCellValue(txn.getFirstName());*/
			
			
			/*excelRow.createCell(6).setCellValue(txn.getEmail());*/
			
			/*excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getAgentName());
			excelRow.createCell(2).setCellValue(txn.getMerchantName());
			excelRow.createCell(3).setCellValue(txn.getLocation());
			excelRow.createCell(4).setCellValue(txn.getAmount());*/
			
			
			
		}
	}

}

