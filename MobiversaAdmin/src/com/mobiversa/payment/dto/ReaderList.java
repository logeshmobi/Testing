package com.mobiversa.payment.dto;

public class ReaderList {
	
	
	/*private List<String> deviceId1;
	private List<String> tid1;
	private List<String> contactName;
	
	private List<String> activateDate;
	private List<String> userName;
	//private List<String> count;
	
	*/
	public String deviceId;
	public String mobileUserId;
	public String deviceType;
	public String mid;
	public String refno;
	
	
	public String data;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getRefno() {
		return refno;
	}

	public void setRefno(String refno) {
		this.refno = refno;
	}

	public String motoapikey;
	/*public List<String> getDeviceId1() {
		return deviceId1;
	}

	public void setDeviceId1(List<String> deviceId1) {
		this.deviceId1 = deviceId1;
	}

	public List<String> getTid1() {
		return tid1;
	}

	public void setTid1(List<String> tid1) {
		this.tid1 = tid1;
	}

	public List<String> getContactName() {
		return contactName;
	}

	public void setContactName(List<String> contactName) {
		this.contactName = contactName;
	}

	public List<String> getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(List<String> activateDate) {
		this.activateDate = activateDate;
	}

	public List<String> getUserName() {
		return userName;
	}

	public void setUserName(List<String> userName) {
		this.userName = userName;
	}*/

	public String getMotoapikey() {
		return motoapikey;
	}

	public void setMotoapikey(String motoapikey) {
		this.motoapikey = motoapikey;
	}

	public String getMobileUserId() {
		return mobileUserId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public void setMobileUserId(String mobileUserId) {
		this.mobileUserId = mobileUserId;
	}

	public String tid;
	public String deviceHolderName;
	public String status;
	public String activationDate;
	public String expiryDate;
	
	
	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String mobileUserName;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getDeviceHolderName() {
		return deviceHolderName;
	}

	public void setDeviceHolderName(String deviceHolderName) {
		this.deviceHolderName = deviceHolderName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}

	public String getMobileUserName() {
		return mobileUserName;
	}

	public void setMobileUserName(String mobileUserName) {
		this.mobileUserName = mobileUserName;
	}
	
	

}
