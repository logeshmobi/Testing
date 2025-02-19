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
import com.mobiversa.payment.util.UMEzyway;

public class PreAuthFeePdf extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<UMEzyway> txnList = (List<UMEzyway>) model.get("umTxnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(7);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.5f, 1.5f, 2.5f, 2.0f, 2.0f,1.0f,1.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(1);
	        
	        table.addCell("Transaction Date");
	        table.addCell("Time");
			table.addCell("MID");
			table.addCell("TID");
			table.addCell("Merchant Name");
			table.addCell("Amount");
			table.addCell("Remaining Amt");
			
			

			 for (UMEzyway forSet : txnList) {
		            table.addCell(forSet.getDate());	           
		            table.addCell(forSet.getTime());
		            table.addCell(forSet.getF001_MID());
		            table.addCell(forSet.getF354_TID());
		            table.addCell(forSet.getMerchantName());
		            table.addCell(forSet.getF007_TXNAMT());
		            table.addCell(forSet.getAuthremamt());		           
		            
		           
		         
		          
		        }

			doc.add(table);
		}
        
}