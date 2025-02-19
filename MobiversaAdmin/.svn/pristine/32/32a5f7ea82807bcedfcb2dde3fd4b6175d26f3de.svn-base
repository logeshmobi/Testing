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
import com.mobiversa.common.bo.PreAuthorization;

public class PreauthMerchantTxnPDF extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<PreAuthorization> txnList = (List<PreAuthorization>) model.get("txnList");
		
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
		table.addCell("Amount");
		table.addCell("Status");
		table.addCell("TID");	
		table.addCell("Card No");	
		table.addCell("Approval code");	
		table.addCell("Reference");	
		

	/*	for (Map.Entry<String, String> entry : txnList.entrySet()) {
			
			
			table.addCell(entry.getKey());
			table.addCell(entry.getValue());
                }*/
		
		
		 for (PreAuthorization forSet : txnList) {
	            table.addCell(forSet.getDate());
	            table.addCell(forSet.getTime());
	            table.addCell(forSet.getAmount());
	            table.addCell(forSet.getStatus());
	            table.addCell(forSet.getTid());
	            table.addCell(forSet.getNumOfSale());
	            table.addCell(forSet.getAidResponse());
	            table.addCell(forSet.getInvoiceId());
	        
	        }

		doc.add(table);
	}
	
	
	
	
	
	
	
}