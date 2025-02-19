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
import com.mobiversa.common.bo.ForSettlement;

public class BoostAdminTxnPDF extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<ForSettlement> txnList = (List<ForSettlement>) model.get("txnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(6);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 3.0f,2.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(5);
        
	        
	     
		 
		 
		 
		 
		 
      
         
	//List txnList = (List) model.get("txnList"); 
         
         
		// Bhuvana Table table = new Table(6);
		
		//PdfPTable table = new PdfPTable(10);
		
		table.addCell("Date");
		table.addCell("Time");
		table.addCell("Status");
		table.addCell("Merchant Name");
		table.addCell("TID");		
		table.addCell("Amount");

	/*	for (Map.Entry<String, String> entry : txnList.entrySet()) {
			
			
			table.addCell(entry.getKey());
			table.addCell(entry.getValue());
                }*/
		
		
		 for (ForSettlement forSet : txnList) {
	            table.addCell(forSet.getDate());
	            table.addCell(forSet.getTime());
	            table.addCell(forSet.getStatus());
	            table.addCell(forSet.getMerchantName());
	            table.addCell(forSet.getTid());
	            table.addCell(forSet.getAmount());
	        }

		doc.add(table);
	}
	
	
	
	
	
	
	
}