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

public class MerchantTxnListPdfView extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<ForSettlement> txnList = (List<ForSettlement>) model.get("txnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.8f, 1.3f, 2.0f, 1.2f, 1.5f, 1.3f,1.4f,1.5f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setSize(20);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(5);
        
	        
	 
		
		table.addCell("Date");
		table.addCell("Time");
		table.addCell("BusinessName");
		table.addCell("Amount");
		table.addCell("Card No");
		table.addCell("Card Type");
		table.addCell("TID");
		table.addCell("TXN-Type");
		
	
		
		 for (ForSettlement forSet : txnList) {
	            table.addCell(forSet.getDate());
	            table.addCell(forSet.getTime());
	            table.addCell(forSet.getMerchantName());
	            table.addCell(forSet.getAmount());
	            table.addCell(forSet.getPan());
	            table.addCell(forSet.getCardType());
	            table.addCell(forSet.getTid());
	            table.addCell(forSet.getTxnType());
	           
	        }

		doc.add(table);
	}
	
	
	
	
	
	
	
}