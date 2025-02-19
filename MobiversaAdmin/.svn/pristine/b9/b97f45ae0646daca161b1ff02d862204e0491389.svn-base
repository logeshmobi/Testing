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
import com.mobiversa.common.bo.Merchant;

public class MerchantSummaryPdfView extends AbstractPdfView {
	
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
			table.addCell("Email");
			table.addCell("Mid");
			table.addCell("Contact Number");
			table.addCell("Merchant Type");
			table.addCell("NOB");
			
			table.addCell("Business Category");
			table.addCell("PREAUTH");
			table.addCell("BOOST");
			table.addCell("MOTO");
			
			//.addCell("Amount");
			
			//.addCell("UserName");

			
		 for (Merchant forSet : txnList) {
			 
			 String pattern = "dd-MMM-yyyy";
			 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			 String date = simpleDateFormat.format(forSet.getActivateDate());
			 
			// System.out.println("DATA : "+date);
			 //String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(forSet.getActivateDate().toString()));
			 /*String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.parse(forSet.getActivateDate().toString())*/
			 
	            table.addCell(date);
	            table.addCell(forSet.getBusinessName());
	            table.addCell(forSet.getUsername());
	            table.addCell(forSet.getMid().getMid());
	            table.addCell(forSet.getContactPersonPhoneNo());
	            if(forSet.getRole().toString().equals("BANK_MERCHANT"))
				{
	            	table.addCell("MERCHANT");
				}
				else
				{
					table.addCell("NON_MERCHANT");
				}
	            if(forSet.getNatureOfBusiness()!=null)
	            {
	            table.addCell(forSet.getNatureOfBusiness());
	            }
	            else
	            {
	            	table.addCell("");
	            }
	            table.addCell(forSet.getCompanyType());
	            table.addCell(forSet.getPreAuth());
	            table.addCell(forSet.getAutoSettled());
	            table.addCell(forSet.getFaxNo());
	           // table.addCell(forSet.getMerchantName());
	           // table.addCell(forSet.getLocation());
	        }

		doc.add(table);
	}
	
	

}
