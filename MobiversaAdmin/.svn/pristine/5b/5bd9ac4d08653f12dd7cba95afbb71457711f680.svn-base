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
import com.mobiversa.common.bo.EzyRecurringPayment;

public class RecurringTxnListPdf extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<EzyRecurringPayment> txnList = (List<EzyRecurringPayment>) model.get("txnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.5f, 2.0f,1.0f, 1.0f, 1.0f, 1.0f,2.0f,2.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(1);
	        
	        table.addCell("Merchant Name");
			table.addCell("MID");
			table.addCell("TID");
			table.addCell("Amount");
			table.addCell("Frequency");
			table.addCell("No Of Payments");
			
			table.addCell("Last Trigger Date");
			table.addCell("Next Trigger Date");
			

			 for (EzyRecurringPayment forSet : txnList) {
		            table.addCell(forSet.getMerchantName());
		            table.addCell(forSet.getMid());
		            table.addCell(forSet.getTid());
		            table.addCell(forSet.getAmount());
		            table.addCell(forSet.getPeriod());
		            table.addCell(forSet.getInstallmentCount());
		           
		            table.addCell(forSet.getLastTriggerDate());
		            table.addCell(forSet.getNextTriggerDate());
		            
		         //   table.addCell(forSet.getMerchantName());
		          
		        }

			doc.add(table);
		}
        
}