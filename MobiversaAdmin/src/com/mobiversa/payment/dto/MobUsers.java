package com.mobiversa.payment.dto;


public class MobUsers
{

	private String userName;
	private String activateDate;
//	private String email;
	private String status;
	private String terminalExpDate;
	private String renewalDate;
	private String tid;
	private String merchantName;
	private Long id;
	private String userType;
	
	
	
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getUserName() {

		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getActivateDate() {
	
		return activateDate;
	}
	public void setActivateDate(String activateDate) {
		this.activateDate = activateDate;
	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTerminalExpDate() {
		
		return terminalExpDate;
	}
	public void setTerminalExpDate(String terminalExpDate) {
		this.terminalExpDate = terminalExpDate;
	}
	public String getRenewalDate() {
		
		return renewalDate;
	}
	public void setRenewalDate(String renewalDate) {
		this.renewalDate = renewalDate;
	}
	
	
}
