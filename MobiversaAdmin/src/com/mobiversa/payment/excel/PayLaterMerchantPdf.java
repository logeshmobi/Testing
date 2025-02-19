package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mobiversa.common.bo.Merchant;

public class PayLaterMerchantPdf extends AbstractPdfView {
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		List<Merchant> txnList = (List<Merchant>) model.get("txnList");
		
		 PdfPTable table = new PdfPTable(11);
	        table.setWidthPercentage(100.5f);
	        table.setWidths(new float[] {3.0f, 3.5f, 3.0f, 3.0f, 3.0f,3.0f,3.0f,3.5f,3.0f,3.0f,3.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(6);
  	        table.addCell("Activation Date");
  	        table.addCell("Business Name");
			table.addCell("Status");
			table.addCell("City");
			table.addCell("EZYLINK MID");
			table.addCell("Amount");
			
			
		 for (Merchant forSet : txnList) {
			 
			  	table.addCell(forSet.getCreatedBy());
	            table.addCell(forSet.getBusinessName());
	            table.addCell(forSet.getState());
	            table.addCell(forSet.getCity());
	            table.addCell(forSet.getReferralId());
	            table.addCell(forSet.getMdr());
	            
	        }

		doc.add(table);
	}
	
	

}
