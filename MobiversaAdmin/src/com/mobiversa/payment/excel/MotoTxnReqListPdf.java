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
import com.mobiversa.common.bo.MotoTxnDetails;

public class MotoTxnReqListPdf extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<MotoTxnDetails> txnList = (List<MotoTxnDetails>) model.get("txnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(7);
	        table.setWidthPercentage(100.0f);
	       // table.setWidths(new float[] {1.7f, 1.0f,1.8f, 2.0f, 1.3f, 1.0f,1.0f,1.7f});
	        table.setWidths(new float[] {1.7f,1.8f, 2.2f, 1.5f, 1.4f,1.2f,1.7f});
	       // table.setWidths(new float[] {2.0f, 2.0f,2.0f, 2.0f, 2.0f, 2.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(1);
	        
	        table.addCell("Date");
	       // table.addCell("Time");
	        table.addCell("Merchant Name");
			table.addCell("MID");
			table.addCell("TID");
			table.addCell("Amount");
			table.addCell("Status");
			table.addCell("Expired Date");
			
			/*table.addCell("Last Trigger Date");
			table.addCell("Next Trigger Date");*/
			

			 for (MotoTxnDetails forSet : txnList) {
				 table.addCell(forSet.getReqDate());
				// table.addCell(forSet.getExpectedDate());
		            table.addCell(forSet.getName());
		            table.addCell(forSet.getMid());
		            table.addCell(forSet.getTid());
		            table.addCell(forSet.getAmount());
		            table.addCell(forSet.getStatus());
		            table.addCell(forSet.getExpDate());
		           
		            /*table.addCell(forSet.getLastTriggerDate());
		            table.addCell(forSet.getNextTriggerDate());*/
		            
		         //   table.addCell(forSet.getMerchantName());
		          
		        }

			doc.add(table);
		}
        
}