package com.postmark.java;

public class EmailTemplet1 {
	
	public static String sentTempletContent(TempletFields templetFields) {
	
		return "<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\"><title></title>"
				+ "</head><body style=\"padding:5px;text-align:left;font-size:16px;display:block;font-family:Lao UI;\"><h5>Dear "
				+ templetFields.getFirstName() + " "+ templetFields.getLastName()
				+ "</h5><div style=\"padding:5px;text-align:left;font-size:16px;display:block;font-family:Lao UI;\">"
				+ "<p>Your agent login has been created on Mobiversa."
				/*+ "<a href=\"www.ezywire.com\">www.ezywire.com </a>"*/
				+ "<a href=\"portal.mobiversa.com\">portal.mobiversa.com </a>"
			
				+ "<br>Your login id and temporary password has been created as below:<br><br>USER ID :<B>"
				+ templetFields.getUserName() + "</B><BR> PASSWORD : <B>"
				+ templetFields.getPassword()
				+ "</B><br>Please contact in case of any clarification :"
				+ "<a href=\"ethan@mobiversa.com\">info@gomobi.io </a></p></div></body></html>";
	}

}
