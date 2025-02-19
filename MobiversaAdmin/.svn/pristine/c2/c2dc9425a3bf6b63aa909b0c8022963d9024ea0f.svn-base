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
import com.mobiversa.common.bo.MotoVCDetails;

public class VCPdfView extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<MotoVCDetails> txnList = (List<MotoVCDetails>) model.get("vcTxnList");
		
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
		table.addCell("Name On Card");
		table.addCell("Card No");
		table.addCell("Status");
		table.addCell("Amount");	
		table.addCell("Res Msg");		
		

	/*	for (Map.Entry<String, String> entry : txnList.entrySet()) {
			
			
			table.addCell(entry.getKey());
			table.addCell(entry.getValue());
                }*/
		
		
		 for (MotoVCDetails forSet : txnList) {
	            table.addCell(forSet.getCreatedBy());
	            table.addCell(forSet.getNameOnCard());
	            table.addCell(forSet.getTxnDetails());
	            if(forSet.getStatus().equals("PENDING")){
	            	table.addCell("PROCESSING");
				}else if(forSet.getStatus().equals("SUBMITTED")) {
					table.addCell("SUBMITTED");
				}else if(forSet.getStatus().equals("REJECTED")) {
					table.addCell("REJECTED");
				}else if(forSet.getStatus().equals("APPROVED")) {
					table.addCell("APPROVED");
				}else if(forSet.getStatus().equals("CANCELLED")) {
					table.addCell("CANCELLED");
				}
	            
	            table.addCell(forSet.getAmount());
	            table.addCell(forSet.getRespMsg());
	        
	        }

		doc.add(table);
	}
	
	
	
	
	
	
	
}