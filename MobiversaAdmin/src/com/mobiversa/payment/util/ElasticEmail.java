package com.mobiversa.payment.util;

public class ElasticEmail {
	private String fromEmail;
	private String subject;
	private String fromName;
	private String msgto;
	private String cc;
	private String bcc;
	private String attachments;
	private String textbody;
//
//	public ElasticEmail(String fromEmail, String subject, String fromName, String msgto, String cc, String bcc, String attachments, String textbody) 
//	{ 
//		this.fromEmail = fromEmail; 
//		this.subject = subject; 
//		this.fromName = fromName; 
//		this.msgto = msgto; 
//		this.cc = cc; 
//		this.bcc = bcc; 
//		this.attachments = attachments; 
//		this.textbody = textbody; 
//		}
//	
	public ElasticEmail(String fromEmail, String subject, String fromName, String msgto, String cc, String bcc,
			String attachments, String textbody) {
		super();
		this.fromEmail = fromEmail;
		this.subject = subject;
		this.fromName = fromName;
		this.msgto = msgto;
		this.cc = cc;
		this.bcc = bcc;
		this.attachments = attachments;
		this.textbody = textbody;
	}

	

	public String getFromEmail() {
		return fromEmail;
	}

	
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getMsgto() {
		return msgto;
	}

	public void setMsgto(String msgto) {
		this.msgto = msgto;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getAttachment() {
		return attachments;
	}

	public void setAttachment(String attachment) {
		this.attachments = attachment;
	}

	public String getTextbody() {
		return textbody;
	}

	public void setTextbody(String textbody) {
		this.textbody = textbody;
	}

}
