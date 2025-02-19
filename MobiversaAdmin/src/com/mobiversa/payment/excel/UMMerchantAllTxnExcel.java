package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.util.UMEzyway;

public class UMMerchantAllTxnExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Transaction List");
		setExcelHeader(excelSheet);

		List txnList = (List) model.get("umTxnList");
		setExcelRows(excelSheet, txnList);

	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Time");
		excelHeader.createCell(2).setCellValue("MID");
		excelHeader.createCell(3).setCellValue("TID");
		excelHeader.createCell(4).setCellValue("Amount");
		excelHeader.createCell(5).setCellValue("Name on Card");
		excelHeader.createCell(6).setCellValue("Card Number");
		excelHeader.createCell(7).setCellValue("Reference");
		excelHeader.createCell(8).setCellValue("Approval Code");
		excelHeader.createCell(9).setCellValue("RRN");
		excelHeader.createCell(10).setCellValue("Status");
		excelHeader.createCell(11).setCellValue("Payment Method");
		excelHeader.createCell(12).setCellValue("MDR Amount");
		excelHeader.createCell(13).setCellValue("Net Amount");
		excelHeader.createCell(14).setCellValue("Payment Date");
		excelHeader.createCell(15).setCellValue("EZYSETTLE Amount");
		excelHeader.createCell(16).setCellValue("PREAUTH Fee");
		excelHeader.createCell(17).setCellValue("SUB MERCHANT MID");

	}

	public void setExcelRows(HSSFSheet excelSheet, List<UMEzyway> txnList) {
		int record = 1;
		for (UMEzyway txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getTime());
			excelRow.createCell(2).setCellValue(txn.getF001_MID());
			excelRow.createCell(3).setCellValue(txn.getF354_TID());
			excelRow.createCell(4).setCellValue(txn.getF007_TXNAMT());
			excelRow.createCell(5).setCellValue(txn.getF268_CHNAME());
			excelRow.createCell(6).setCellValue(txn.getPAN());
			excelRow.createCell(7).setCellValue(txn.getF270_ORN());
			excelRow.createCell(8).setCellValue(txn.getF011_AUTHIDRESP());
			excelRow.createCell(9).setCellValue(txn.getF023_RRN());
			excelRow.createCell(10).setCellValue(txn.getSTATUS());
			excelRow.createCell(11).setCellValue(txn.getCardType());
			excelRow.createCell(12).setCellValue(txn.getMdrAmt());
			excelRow.createCell(13).setCellValue(txn.getNetAmount());
			excelRow.createCell(14).setCellValue(txn.getSettlementDate());
			excelRow.createCell(15).setCellValue(txn.getEzysettleAmt());
			excelRow.createCell(16).setCellValue(txn.getPreauthfee());
			excelRow.createCell(17).setCellValue(txn.getSubmerchantmid());
		}
	}

}
