package com.mobiversa.payment.dto;
public class Response {
	private String status;
	private Object data;
	private String txtCompare1;
	private String txtCompare2;
	public String getTxtCompare1() {
		return txtCompare1;
	}
	public void setTxtCompare1(String txtCompare1) {
		this.txtCompare1 = txtCompare1;
	}
	public String getTxtCompare2() {
		return txtCompare2;
	}
	public void setTxtCompare2(String txtCompare2) {
		this.txtCompare2 = txtCompare2;
	}
	public Response(String status)
	{
		this.status = status;
	}
	
	public Response(String status, Object data){
		this.status = status;
		this.data = data;
	}
 
	public Response(String status, String data, String captcha1,
			String captcha2) {
		this.status = status;
		this.data = data;
		this.txtCompare1 = captcha1;
		this.txtCompare2 = captcha2;
		
	}
	public String getStatus() {
		return status;
	}
 
	public void setStatus(String status) {
		this.status = status;
	}
 
	public Object getData() {
		return data;
	}
 
	public void setData(Object data) {
		this.data = data;
	}
}