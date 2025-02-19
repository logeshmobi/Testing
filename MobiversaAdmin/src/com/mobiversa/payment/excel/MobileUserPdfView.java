package com.mobiversa.payment.excel;

import java.text.SimpleDateFormat;
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
import com.mobiversa.common.bo.MobileUser;

public class MobileUserPdfView extends AbstractPdfView {
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		List<MobileUser> txnList = (List<MobileUser>) model.get("txnList");
		
		 PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.0f, 1.5f, 1.0f, 1.5f, 1.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(5);
  	        table.addCell("Activation Date");
			table.addCell("UserName");
			table.addCell("TID");
			table.addCell("DeviceId");
			table.addCell("Expiry Date");
			//.addCell("Amount");
			//table.addCell("DeviceHolder Name");
			//.addCell("UserName");

			
		 for (MobileUser forSet : txnList) {
			 
			 String pattern = "dd-MMM-yyyy";
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			 String date = simpleDateFormat.format(forSet.getActivateDate());
			 
			 System.out.println("DATA : "+date);
	            table.addCell(date);
	            table.addCell(forSet.getUsername());
	            table.addCell(forSet.getTid());
	            table.addCell(forSet.getDeviceId());
	            if(forSet.getSuspendDate() != null){
	            	table.addCell(simpleDateFormat.format(forSet.getSuspendDate()));
	            }else{
	            	table.addCell("");
	            }
	           // table.addCell(forSet.getAmount());
	           // table.addCell(forSet.getMerchantName());
	           // table.addCell(forSet.getLocation());
	        }

		doc.add(table);
	}
	
	

}
