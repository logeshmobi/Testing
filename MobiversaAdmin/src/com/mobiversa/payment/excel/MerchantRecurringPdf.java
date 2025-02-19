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

public class MerchantRecurringPdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<EzyRecurringPayment> txnList = (List<EzyRecurringPayment>) model.get("txnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(7);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {2.5f, 1.5f, 1.5f, 1.5f, 1.5f,2.0f,2.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(1);
	        
	        table.addCell("Customer Name");
			table.addCell("Card No");
			//table.addCell("Card Holder Name");
			table.addCell("Amount");
			table.addCell("No Of Payments");
			table.addCell("Frequency");
			
			/*table.addCell("Last Trigger Date");*/
			table.addCell("Next Trigger Date");
			table.addCell("End Date");

			 for (EzyRecurringPayment forSet : txnList) {
		            table.addCell(forSet.getCustName());
		            table.addCell(forSet.getMaskedPan());
		           // table.addCell(forSet.getCardHolderName());
		            table.addCell(forSet.getAmount());
		            table.addCell(forSet.getInstallmentCount());
		            table.addCell(forSet.getPeriod());
		           
		            //table.addCell(forSet.getLastTriggerDate());
		            table.addCell(forSet.getNextTriggerDate());
		            table.addCell(forSet.getEndDate());
		            
		         //   table.addCell(forSet.getMerchantName());
		          
		        }

			doc.add(table);
		}
        
}