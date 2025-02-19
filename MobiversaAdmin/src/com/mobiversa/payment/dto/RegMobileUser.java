package com.mobiversa.payment.dto;

import java.util.List;

public class RegMobileUser {
	
	private String status;
	
	private String firstName;
	private String lastName;
	private String contactNo;
	private String emailId;

	private String salutation;
	private Long id;
	private String activateDate;
	private String connectType;
	private String deviceType;
	
	private String expiryDate;
	private String renewalPeriod;
	private String remarks;
	
	private String username;
	private String merchantusername;
	private String businessName;
	private String ezywiremobusername;
	private String motousername;
	private String ezypassusername;
	private String ezywayusername;
	private String ezyrecusername;
	private String umusername;
	
	//padee terminal details
	private String tid;
	private String mid;
	private String deviceId;
	private String referenceNo;
	
	private String motodeviceId;
	private String motorefNo;
	private String motoTid;
	private String motoMid;
	
	private String ezypassdeviceId;
	private String ezypassrefNo;
	private String ezypassTid;
	private String ezypassMid;
	
	private String ezywaydeviceId;
	private String ezywayrefNo;
	private String ezywayTid;
	private String ezywayMid;
	
	private String ezyrecdeviceId;
	private String ezyrecrefNo;
	private String ezyrecTid;
	private String ezyrecMid;
	
	private String gPaydeviceId;
	private String gPayrefNo;
	private String gPayTid;
	private String gPayMid;
	private String gPayPreAuth;
	
	private List<String> ezywiretidList;
	private List<String> mototidList;
	private List<String> ezypasstidList;
	private String updateType;
	private String ezypod;
	
	private List<String> ezywaytidList;
	private List<String> ezyrectidList;
	
	

	private String activationCode;
	
	private String rateId;
	
	
	private String merchantID;
	private String mobileId;
	
	private String domCreditMerchantMDR;
	private String domCreditHostMDR;
	private String domDebitMerchantMDR;
	private String domDebitHostMDR;
	
	private String foCreditMerchantMDR;
	private String foCreditHostMDR;
	private String foDebitMerchantMDR;
	private String foDebitHostMDR;
	
	//UMobile terminal details
	private String um_tid;
	private String um_mid;
	private String um_deviceId;
	private String um_refNo;
	
	private String um_motodeviceId;
	private String um_motorefNo;
	private String um_motoTid;
	private String um_motoMid;
	
	private String um_ezypassdeviceId;
	private String um_ezypassrefNo;
	private String um_ezypassTid;
	private String um_ezypassMid;
	
	private String um_ezywaydeviceId;
	private String um_ezywayrefNo;
	private String um_ezywayTid;
	private String um_ezywayMid;
	
	private String um_ezyrecdeviceId;
	private String um_ezyrecrefNo;
	private String um_ezyrecTid;
	private String um_ezyrecMid;


	private List<String> um_tidList;
	private List<String> um_mototidList;
	private List<String> um_ezypasstidList;
	private List<String> um_ezywaytidList;
	private List<String> um_ezyrectidList;
	
	private String um_updateType;
	private String um_activationCode;
	private String um_preAuth;
	private String merchantType;
	
	private String Brand;
	
	private String hashkey;
	
	private String DTL;
	
	private String hashkey1;
	
	private String DTL1;
	
	private String recHashkey;
	
	private String recDTL;
	
	private String ch_amount;
	private String domainUrl;
	
	private String vcc;
	
	private String settlePeriod;
	private String payLater;
	private String amount;
	private String installment;
	
	private String repayableAmt;
	
	
	
	public String getSettlePeriod() {
		return settlePeriod;
	}
	public void setSettlePeriod(String settlePeriod) {
		this.settlePeriod = settlePeriod;
	}
	public String getPayLater() {
		return payLater;
	}
	public void setPayLater(String payLater) {
		this.payLater = payLater;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getInstallment() {
		return installment;
	}
	public void setInstallment(String installment) {
		this.installment = installment;
	}
	public String getHashkey1() {
		return hashkey1;
	}
	public void setHashkey1(String hashkey1) {
		this.hashkey1 = hashkey1;
	}
	public String getDTL1() {
		return DTL1;
	}
	public void setDTL1(String dTL1) {
		DTL1 = dTL1;
	}
	public String getVcc() {
		return vcc;
	}
	public void setVcc(String vcc) {
		this.vcc = vcc;
	}
	public String getCh_amount() {
		return ch_amount;
	}
	public void setCh_amount(String ch_amount) {
		this.ch_amount = ch_amount;
	}
	public String getUmusername() {
		return umusername;
	}
	public void setUmusername(String umusername) {
		this.umusername = umusername;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public String getUm_motorefNo() {
		return um_motorefNo;
	}
	public void setUm_motorefNo(String um_motorefNo) {
		this.um_motorefNo = um_motorefNo;
	}
	public String getUm_preAuth() {
		return um_preAuth;
	}
	public void setUm_preAuth(String um_preAuth) {
		this.um_preAuth = um_preAuth;
	}
	public String getUm_tid() {
		return um_tid;
	}
	public void setUm_tid(String um_tid) {
		this.um_tid = um_tid;
	}
	public String getUm_mid() {
		return um_mid;
	}
	public void setUm_mid(String um_mid) {
		this.um_mid = um_mid;
	}
	public String getUm_deviceId() {
		return um_deviceId;
	}
	public void setUm_deviceId(String um_deviceId) {
		this.um_deviceId = um_deviceId;
	}
	public String getUm_refNo() {
		return um_refNo;
	}
	public void setUm_refNo(String um_refNo) {
		this.um_refNo = um_refNo;
	}
	public String getUm_motodeviceId() {
		return um_motodeviceId;
	}
	public void setUm_motodeviceId(String um_motodeviceId) {
		this.um_motodeviceId = um_motodeviceId;
	}
	public String getUm_motoTid() {
		return um_motoTid;
	}
	public void setUm_motoTid(String um_motoTid) {
		this.um_motoTid = um_motoTid;
	}
	public String getUm_motoMid() {
		return um_motoMid;
	}
	public void setUm_motoMid(String um_motoMid) {
		this.um_motoMid = um_motoMid;
	}
	public String getUm_ezypassdeviceId() {
		return um_ezypassdeviceId;
	}
	public void setUm_ezypassdeviceId(String um_ezypassdeviceId) {
		this.um_ezypassdeviceId = um_ezypassdeviceId;
	}
	public String getUm_ezypassrefNo() {
		return um_ezypassrefNo;
	}
	public void setUm_ezypassrefNo(String um_ezypassrefNo) {
		this.um_ezypassrefNo = um_ezypassrefNo;
	}
	public String getUm_ezypassTid() {
		return um_ezypassTid;
	}
	public void setUm_ezypassTid(String um_ezypassTid) {
		this.um_ezypassTid = um_ezypassTid;
	}
	public String getUm_ezypassMid() {
		return um_ezypassMid;
	}
	public void setUm_ezypassMid(String um_ezypassMid) {
		this.um_ezypassMid = um_ezypassMid;
	}
	public String getUm_ezywaydeviceId() {
		return um_ezywaydeviceId;
	}
	public void setUm_ezywaydeviceId(String um_ezywaydeviceId) {
		this.um_ezywaydeviceId = um_ezywaydeviceId;
	}
	public String getUm_ezywayrefNo() {
		return um_ezywayrefNo;
	}
	public void setUm_ezywayrefNo(String um_ezywayrefNo) {
		this.um_ezywayrefNo = um_ezywayrefNo;
	}
	public String getUm_ezywayTid() {
		return um_ezywayTid;
	}
	public void setUm_ezywayTid(String um_ezywayTid) {
		this.um_ezywayTid = um_ezywayTid;
	}
	public String getUm_ezywayMid() {
		return um_ezywayMid;
	}
	public void setUm_ezywayMid(String um_ezywayMid) {
		this.um_ezywayMid = um_ezywayMid;
	}
	public String getUm_ezyrecdeviceId() {
		return um_ezyrecdeviceId;
	}
	public void setUm_ezyrecdeviceId(String um_ezyrecdeviceId) {
		this.um_ezyrecdeviceId = um_ezyrecdeviceId;
	}
	public String getUm_ezyrecrefNo() {
		return um_ezyrecrefNo;
	}
	public void setUm_ezyrecrefNo(String um_ezyrecrefNo) {
		this.um_ezyrecrefNo = um_ezyrecrefNo;
	}
	public String getUm_ezyrecTid() {
		return um_ezyrecTid;
	}
	public void setUm_ezyrecTid(String um_ezyrecTid) {
		this.um_ezyrecTid = um_ezyrecTid;
	}
	public String getUm_ezyrecMid() {
		return um_ezyrecMid;
	}
	public void setUm_ezyrecMid(String um_ezyrecMid) {
		this.um_ezyrecMid = um_ezyrecMid;
	}
	
	
	public List<String> getUm_tidList() {
		return um_tidList;
	}
	public void setUm_tidList(List<String> um_tidList) {
		this.um_tidList = um_tidList;
	}
	public List<String> getUm_mototidList() {
		return um_mototidList;
	}
	public void setUm_mototidList(List<String> um_mototidList) {
		this.um_mototidList = um_mototidList;
	}
	public List<String> getUm_ezypasstidList() {
		return um_ezypasstidList;
	}
	public void setUm_ezypasstidList(List<String> um_ezypasstidList) {
		this.um_ezypasstidList = um_ezypasstidList;
	}
	public List<String> getUm_ezywaytidList() {
		return um_ezywaytidList;
	}
	public void setUm_ezywaytidList(List<String> um_ezywaytidList) {
		this.um_ezywaytidList = um_ezywaytidList;
	}
	public List<String> getUm_ezyrectidList() {
		return um_ezyrectidList;
	}
	public void setUm_ezyrectidList(List<String> um_ezyrectidList) {
		this.um_ezyrectidList = um_ezyrectidList;
	}
	public String getUm_updateType() {
		return um_updateType;
	}
	public void setUm_updateType(String um_updateType) {
		this.um_updateType = um_updateType;
	}
	public String getUm_activationCode() {
		return um_activationCode;
	}
	public void setUm_activationCode(String um_activationCode) {
		this.um_activationCode = um_activationCode;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getEzypod() {
		return ezypod;
	}
	public void setEzypod(String ezypod) {
		this.ezypod = ezypod;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEzywayusername() {
		return ezywayusername;
	}
	public void setEzywayusername(String ezywayusername) {
		this.ezywayusername = ezywayusername;
	}
	public String getEzyrecusername() {
		return ezyrecusername;
	}
	public void setEzyrecusername(String ezyrecusername) {
		this.ezyrecusername = ezyrecusername;
	}
	public String getEzywaydeviceId() {
		return ezywaydeviceId;
	}
	public void setEzywaydeviceId(String ezywaydeviceId) {
		this.ezywaydeviceId = ezywaydeviceId;
	}
	public String getEzywayrefNo() {
		return ezywayrefNo;
	}
	public void setEzywayrefNo(String ezywayrefNo) {
		this.ezywayrefNo = ezywayrefNo;
	}
	public String getEzywayTid() {
		return ezywayTid;
	}
	public void setEzywayTid(String ezywayTid) {
		this.ezywayTid = ezywayTid;
	}
	public String getEzywayMid() {
		return ezywayMid;
	}
	public void setEzywayMid(String ezywayMid) {
		this.ezywayMid = ezywayMid;
	}
	public String getEzyrecdeviceId() {
		return ezyrecdeviceId;
	}
	public void setEzyrecdeviceId(String ezyrecdeviceId) {
		this.ezyrecdeviceId = ezyrecdeviceId;
	}
	public String getEzyrecrefNo() {
		return ezyrecrefNo;
	}
	public void setEzyrecrefNo(String ezyrecrefNo) {
		this.ezyrecrefNo = ezyrecrefNo;
	}
	public String getEzyrecTid() {
		return ezyrecTid;
	}
	public void setEzyrecTid(String ezyrecTid) {
		this.ezyrecTid = ezyrecTid;
	}
	public String getEzyrecMid() {
		return ezyrecMid;
	}
	public void setEzyrecMid(String ezyrecMid) {
		this.ezyrecMid = ezyrecMid;
	}
	public List<String> getEzywaytidList() {
		return ezywaytidList;
	}
	public void setEzywaytidList(List<String> ezywaytidList) {
		this.ezywaytidList = ezywaytidList;
	}
	public List<String> getEzyrectidList() {
		return ezyrectidList;
	}
	public void setEzyrectidList(List<String> ezyrectidList) {
		this.ezyrectidList = ezyrectidList;
	}
	public String getEzywiremobusername() {
		return ezywiremobusername;
	}
	public void setEzywiremobusername(String ezywiremobusername) {
		this.ezywiremobusername = ezywiremobusername;
	}
	public String getMotousername() {
		return motousername;
	}
	public void setMotousername(String motousername) {
		this.motousername = motousername;
	}
	public String getEzypassusername() {
		return ezypassusername;
	}
	public void setEzypassusername(String ezypassusername) {
		this.ezypassusername = ezypassusername;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}
	public String getMerchantusername() {
		return merchantusername;
	}
	public void setMerchantusername(String merchantusername) {
		this.merchantusername = merchantusername;
	}
	public List<String> getEzywiretidList() {
		return ezywiretidList;
	}
	public void setEzywiretidList(List<String> ezywiretidList) {
		this.ezywiretidList = ezywiretidList;
	}
	public List<String> getMototidList() {
		return mototidList;
	}
	public void setMototidList(List<String> mototidList) {
		this.mototidList = mototidList;
	}
	
	
	
	public List<String> getEzypasstidList() {
		return ezypasstidList;
	}
	public void setEzypasstidList(List<String> ezypasstidList) {
		this.ezypasstidList = ezypasstidList;
	}
	public String getMotodeviceId() {
		return motodeviceId;
	}
	public void setMotodeviceId(String motodeviceId) {
		this.motodeviceId = motodeviceId;
	}
	
	
	public String getMotorefNo() {
		return motorefNo;
	}
	public void setMotorefNo(String motorefNo) {
		this.motorefNo = motorefNo;
	}
	public String getMotoMid() {
		return motoMid;
	}
	public void setMotoMid(String motoMid) {
		this.motoMid = motoMid;
	}
	public String getEzypassdeviceId() {
		return ezypassdeviceId;
	}
	public void setEzypassdeviceId(String ezypassdeviceId) {
		this.ezypassdeviceId = ezypassdeviceId;
	}
	
	
	public String getMotoTid() {
		return motoTid;
	}
	public void setMotoTid(String motoTid) {
		this.motoTid = motoTid;
	}
	public String getEzypassrefNo() {
		return ezypassrefNo;
	}
	public void setEzypassrefNo(String ezypassrefNo) {
		this.ezypassrefNo = ezypassrefNo;
	}
	public String getEzypassTid() {
		return ezypassTid;
	}
	public void setEzypassTid(String ezypassTid) {
		this.ezypassTid = ezypassTid;
	}
	public String getEzypassMid() {
		return ezypassMid;
	}
	public void setEzypassMid(String ezypassMid) {
		this.ezypassMid = ezypassMid;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getRenewalPeriod() {
		return renewalPeriod;
	}
	public void setRenewalPeriod(String renewalPeriod) {
		this.renewalPeriod = renewalPeriod;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getActivateDate() {
		return activateDate;
	}
	public void setActivateDate(String activateDate) {
		this.activateDate = activateDate;
	}

	private String contactName;
	
	private String preAuth;
	private String motopreAuth;
	private String ezyPassPreAuth;
	
	
	
	public String getEzyPassPreAuth() {
		return ezyPassPreAuth;
	}
	public void setEzyPassPreAuth(String ezyPassPreAuth) {
		this.ezyPassPreAuth = ezyPassPreAuth;
	}
	public String getMotopreAuth() {
		return motopreAuth;
	}
	public void setMotopreAuth(String motopreAuth) {
		this.motopreAuth = motopreAuth;
	}
	public String getPreAuth() {
		return preAuth;
	}
	public void setPreAuth(String preAuth) {
		this.preAuth = preAuth;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	private String merchantName;

	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getConnectType() {
		return connectType;
	}
	public void setConnectType(String connectType) {
		this.connectType = connectType;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	
	private boolean resTid=true;
	private boolean resMotoTid=true;
	private boolean resEzypassTid=true;
	private boolean resEzywayTid=true;
	private boolean resEzyrecTid=true;
	
	private boolean resDeviceID=true;
	private boolean resMotoDeviceID=true;
	private boolean resEzypassDeviceID=true;
	private boolean resEzywayDeviceID=true;
	private boolean resEzyrecDeviceID=true;
	
	private boolean resUTid=true;
	private boolean resUMotoTid=true;
	private boolean resUEzypassTid=true;
	private boolean resUEzywayTid=true;
	private boolean resUEzyrecTid=true;
	
	private boolean resUDeviceID=true;
	private boolean resUMotoDeviceID=true;
	private boolean resUEzypassDeviceID=true;
	private boolean resUEzywayDeviceID=true;
	private boolean resUEzyrecDeviceID=true;

	private String respTid;
	private String respMotoTid;
	private String respEzypassTid;
	private String respEzywayTid;
	private String respEzyrecTid;
	
	private String respDeviceID;
	private String respMotoDeviceID;
	private String respEzypassDeviceID;
	private String respEzywayDeviceID;
	private String respEzyrecDeviceID;
	
	private String respUTid;
	private String respUMotoTid;
	private String respUEzypassTid;
	private String respUEzywayTid;
	private String respUEzyrecTid;
	
	private String respUDeviceID;
	private String respUMotoDeviceID;
	private String respUEzypassDeviceID;
	private String respUEzywayDeviceID;
	private String respUEzyrecDeviceID;










	public boolean isResTid() {
		return resTid;
	}
	public void setResTid(boolean resTid) {
		this.resTid = resTid;
	}
	public boolean isResMotoTid() {
		return resMotoTid;
	}
	public void setResMotoTid(boolean resMotoTid) {
		this.resMotoTid = resMotoTid;
	}
	public boolean isResEzypassTid() {
		return resEzypassTid;
	}
	public void setResEzypassTid(boolean resEzypassTid) {
		this.resEzypassTid = resEzypassTid;
	}
	public boolean isResEzywayTid() {
		return resEzywayTid;
	}
	public void setResEzywayTid(boolean resEzywayTid) {
		this.resEzywayTid = resEzywayTid;
	}
	public boolean isResEzyrecTid() {
		return resEzyrecTid;
	}
	public void setResEzyrecTid(boolean resEzyrecTid) {
		this.resEzyrecTid = resEzyrecTid;
	}
	public boolean isResDeviceID() {
		return resDeviceID;
	}
	public void setResDeviceID(boolean resDeviceID) {
		this.resDeviceID = resDeviceID;
	}
	public boolean isResMotoDeviceID() {
		return resMotoDeviceID;
	}
	public void setResMotoDeviceID(boolean resMotoDeviceID) {
		this.resMotoDeviceID = resMotoDeviceID;
	}
	public boolean isResEzypassDeviceID() {
		return resEzypassDeviceID;
	}
	public void setResEzypassDeviceID(boolean resEzypassDeviceID) {
		this.resEzypassDeviceID = resEzypassDeviceID;
	}
	public boolean isResEzywayDeviceID() {
		return resEzywayDeviceID;
	}
	public void setResEzywayDeviceID(boolean resEzywayDeviceID) {
		this.resEzywayDeviceID = resEzywayDeviceID;
	}
	public boolean isResEzyrecDeviceID() {
		return resEzyrecDeviceID;
	}
	public void setResEzyrecDeviceID(boolean resEzyrecDeviceID) {
		this.resEzyrecDeviceID = resEzyrecDeviceID;
	}
	public boolean isResUTid() {
		return resUTid;
	}
	public void setResUTid(boolean resUTid) {
		this.resUTid = resUTid;
	}
	public boolean isResUMotoTid() {
		return resUMotoTid;
	}
	public void setResUMotoTid(boolean resUMotoTid) {
		this.resUMotoTid = resUMotoTid;
	}
	public boolean isResUEzypassTid() {
		return resUEzypassTid;
	}
	public void setResUEzypassTid(boolean resUEzypassTid) {
		this.resUEzypassTid = resUEzypassTid;
	}
	public boolean isResUEzywayTid() {
		return resUEzywayTid;
	}
	public void setResUEzywayTid(boolean resUEzywayTid) {
		this.resUEzywayTid = resUEzywayTid;
	}
	public boolean isResUEzyrecTid() {
		return resUEzyrecTid;
	}
	public void setResUEzyrecTid(boolean resUEzyrecTid) {
		this.resUEzyrecTid = resUEzyrecTid;
	}
	public boolean isResUDeviceID() {
		return resUDeviceID;
	}
	public void setResUDeviceID(boolean resUDeviceID) {
		this.resUDeviceID = resUDeviceID;
	}
	public boolean isResUMotoDeviceID() {
		return resUMotoDeviceID;
	}
	public void setResUMotoDeviceID(boolean resUMotoDeviceID) {
		this.resUMotoDeviceID = resUMotoDeviceID;
	}
	public boolean isResUEzypassDeviceID() {
		return resUEzypassDeviceID;
	}
	public void setResUEzypassDeviceID(boolean resUEzypassDeviceID) {
		this.resUEzypassDeviceID = resUEzypassDeviceID;
	}
	public boolean isResUEzywayDeviceID() {
		return resUEzywayDeviceID;
	}
	public void setResUEzywayDeviceID(boolean resUEzywayDeviceID) {
		this.resUEzywayDeviceID = resUEzywayDeviceID;
	}
	public boolean isResUEzyrecDeviceID() {
		return resUEzyrecDeviceID;
	}
	public void setResUEzyrecDeviceID(boolean resUEzyrecDeviceID) {
		this.resUEzyrecDeviceID = resUEzyrecDeviceID;
	}
	public String getRespTid() {
		return respTid;
	}
	public void setRespTid(String respTid) {
		this.respTid = respTid;
	}
	public String getRespMotoTid() {
		return respMotoTid;
	}
	public void setRespMotoTid(String respMotoTid) {
		this.respMotoTid = respMotoTid;
	}
	public String getRespEzypassTid() {
		return respEzypassTid;
	}
	public void setRespEzypassTid(String respEzypassTid) {
		this.respEzypassTid = respEzypassTid;
	}
	public String getRespEzywayTid() {
		return respEzywayTid;
	}
	public void setRespEzywayTid(String respEzywayTid) {
		this.respEzywayTid = respEzywayTid;
	}
	public String getRespEzyrecTid() {
		return respEzyrecTid;
	}
	public void setRespEzyrecTid(String respEzyrecTid) {
		this.respEzyrecTid = respEzyrecTid;
	}
	public String getRespDeviceID() {
		return respDeviceID;
	}
	public void setRespDeviceID(String respDeviceID) {
		this.respDeviceID = respDeviceID;
	}
	public String getRespMotoDeviceID() {
		return respMotoDeviceID;
	}
	public void setRespMotoDeviceID(String respMotoDeviceID) {
		this.respMotoDeviceID = respMotoDeviceID;
	}
	public String getRespEzypassDeviceID() {
		return respEzypassDeviceID;
	}
	public void setRespEzypassDeviceID(String respEzypassDeviceID) {
		this.respEzypassDeviceID = respEzypassDeviceID;
	}
	public String getRespEzywayDeviceID() {
		return respEzywayDeviceID;
	}
	public void setRespEzywayDeviceID(String respEzywayDeviceID) {
		this.respEzywayDeviceID = respEzywayDeviceID;
	}
	public String getRespEzyrecDeviceID() {
		return respEzyrecDeviceID;
	}
	public void setRespEzyrecDeviceID(String respEzyrecDeviceID) {
		this.respEzyrecDeviceID = respEzyrecDeviceID;
	}
	public String getRespUTid() {
		return respUTid;
	}
	public void setRespUTid(String respUTid) {
		this.respUTid = respUTid;
	}
	public String getRespUMotoTid() {
		return respUMotoTid;
	}
	public void setRespUMotoTid(String respUMotoTid) {
		this.respUMotoTid = respUMotoTid;
	}
	public String getRespUEzypassTid() {
		return respUEzypassTid;
	}
	public void setRespUEzypassTid(String respUEzypassTid) {
		this.respUEzypassTid = respUEzypassTid;
	}
	public String getRespUEzywayTid() {
		return respUEzywayTid;
	}
	public void setRespUEzywayTid(String respUEzywayTid) {
		this.respUEzywayTid = respUEzywayTid;
	}
	public String getRespUEzyrecTid() {
		return respUEzyrecTid;
	}
	public void setRespUEzyrecTid(String respUEzyrecTid) {
		this.respUEzyrecTid = respUEzyrecTid;
	}
	public String getRespUDeviceID() {
		return respUDeviceID;
	}
	public void setRespUDeviceID(String respUDeviceID) {
		this.respUDeviceID = respUDeviceID;
	}
	public String getRespUMotoDeviceID() {
		return respUMotoDeviceID;
	}
	public void setRespUMotoDeviceID(String respUMotoDeviceID) {
		this.respUMotoDeviceID = respUMotoDeviceID;
	}
	public String getRespUEzypassDeviceID() {
		return respUEzypassDeviceID;
	}
	public void setRespUEzypassDeviceID(String respUEzypassDeviceID) {
		this.respUEzypassDeviceID = respUEzypassDeviceID;
	}
	public String getRespUEzywayDeviceID() {
		return respUEzywayDeviceID;
	}
	public void setRespUEzywayDeviceID(String respUEzywayDeviceID) {
		this.respUEzywayDeviceID = respUEzywayDeviceID;
	}
	public String getRespUEzyrecDeviceID() {
		return respUEzyrecDeviceID;
	}
	public void setRespUEzyrecDeviceID(String respUEzyrecDeviceID) {
		this.respUEzyrecDeviceID = respUEzyrecDeviceID;
	}
	
		public String getDomCreditMerchantMDR() {
		return domCreditMerchantMDR;
	}
	public void setDomCreditMerchantMDR(String domCreditMerchantMDR) {
		this.domCreditMerchantMDR = domCreditMerchantMDR;
	}
	public String getDomCreditHostMDR() {
		return domCreditHostMDR;
	}
	public void setDomCreditHostMDR(String domCreditHostMDR) {
		this.domCreditHostMDR = domCreditHostMDR;
	}
	public String getDomDebitMerchantMDR() {
		return domDebitMerchantMDR;
	}
	public void setDomDebitMerchantMDR(String domDebitMerchantMDR) {
		this.domDebitMerchantMDR = domDebitMerchantMDR;
	}
	public String getDomDebitHostMDR() {
		return domDebitHostMDR;
	}
	public void setDomDebitHostMDR(String domDebitHostMDR) {
		this.domDebitHostMDR = domDebitHostMDR;
	}
	public String getFoCreditMerchantMDR() {
		return foCreditMerchantMDR;
	}
	public void setFoCreditMerchantMDR(String foCreditMerchantMDR) {
		this.foCreditMerchantMDR = foCreditMerchantMDR;
	}
	public String getFoCreditHostMDR() {
		return foCreditHostMDR;
	}
	public void setFoCreditHostMDR(String foCreditHostMDR) {
		this.foCreditHostMDR = foCreditHostMDR;
	}
	public String getFoDebitMerchantMDR() {
		return foDebitMerchantMDR;
	}
	public void setFoDebitMerchantMDR(String foDebitMerchantMDR) {
		this.foDebitMerchantMDR = foDebitMerchantMDR;
	}
	public String getFoDebitHostMDR() {
		return foDebitHostMDR;
	}
	public void setFoDebitHostMDR(String foDebitHostMDR) {
		this.foDebitHostMDR = foDebitHostMDR;
	}
	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		Brand = brand;
	}
	
	public String getgPaydeviceId() {
		return gPaydeviceId;
	}
	public void setgPaydeviceId(String gPaydeviceId) {
		this.gPaydeviceId = gPaydeviceId;
	}
	public String getgPayrefNo() {
		return gPayrefNo;
	}
	public void setgPayrefNo(String gPayrefNo) {
		this.gPayrefNo = gPayrefNo;
	}
	
		public String getMobileId() {
		return mobileId;
	}
	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}
	public String getgPayPreAuth() {
		return gPayPreAuth;
	}
	public void setgPayPreAuth(String gPayPreAuth) {
		this.gPayPreAuth = gPayPreAuth;
	}
	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getgPayTid() {
		return gPayTid;
	}
	public void setgPayTid(String gPayTid) {
		this.gPayTid = gPayTid;
	}
	public String getgPayMid() {
		return gPayMid;
	}
	public void setgPayMid(String gPayMid) {
		this.gPayMid = gPayMid;
	}
	public String getHashkey() {
		return hashkey;
	}
	public void setHashkey(String hashkey) {
		this.hashkey = hashkey;
	}
	public String getDTL() {
		return DTL;
	}
	public void setDTL(String dTL) {
		DTL = dTL;
	}
	public String getDomainUrl() {
		return domainUrl;
	}
	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}
	public String getRepayableAmt() {
		return repayableAmt;
	}
	public void setRepayableAmt(String repayableAmt) {
		this.repayableAmt = repayableAmt;
	}
	public String getRecHashkey() {
		return recHashkey;
	}
	public void setRecHashkey(String recHashkey) {
		this.recHashkey = recHashkey;
	}
	public String getRecDTL() {
		return recDTL;
	}
	public void setRecDTL(String recDTL) {
		this.recDTL = recDTL;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	private String domCreditMerchantMDR1;
    private String domCreditHostMDR1;
    private String domDebitMerchantMDR1;
    private String domDebitHostMDR1;
    
    private String foCreditMerchantMDR1;
    private String foCreditHostMDR1;
    private String foDebitMerchantMDR1;
    private String foDebitHostMDR1;
    
    
    public String getDomCreditMerchantMDR1() {
        return domCreditMerchantMDR1;
    }
    public void setDomCreditMerchantMDR1(String domCreditMerchantMDR1) {
        this.domCreditMerchantMDR1 = domCreditMerchantMDR1;
    }
    public String getDomCreditHostMDR1() {
        return domCreditHostMDR1;
    }
    public void setDomCreditHostMDR1(String domCreditHostMDR1) {
        this.domCreditHostMDR1 = domCreditHostMDR1;
    }
    public String getDomDebitMerchantMDR1() {
        return domDebitMerchantMDR1;
    }
    public void setDomDebitMerchantMDR1(String domDebitMerchantMDR1) {
        this.domDebitMerchantMDR1 = domDebitMerchantMDR1;
    }
    public String getDomDebitHostMDR1() {
        return domDebitHostMDR1;
    }
    public void setDomDebitHostMDR1(String domDebitHostMDR1) {
        this.domDebitHostMDR1 = domDebitHostMDR1;
    }
    public String getFoCreditMerchantMDR1() {
        return foCreditMerchantMDR1;
    }
    public void setFoCreditMerchantMDR1(String foCreditMerchantMDR1) {
        this.foCreditMerchantMDR1 = foCreditMerchantMDR1;
    }
    public String getFoCreditHostMDR1() {
        return foCreditHostMDR1;
    }
    public void setFoCreditHostMDR1(String foCreditHostMDR1) {
        this.foCreditHostMDR1 = foCreditHostMDR1;
    }
    public String getFoDebitMerchantMDR1() {
        return foDebitMerchantMDR1;
    }
    public void setFoDebitMerchantMDR1(String foDebitMerchantMDR1) {
        this.foDebitMerchantMDR1 = foDebitMerchantMDR1;
    }
    public String getFoDebitHostMDR1() {
        return foDebitHostMDR1;
    }
    public void setFoDebitHostMDR1(String foDebitHostMDR1) {
        this.foDebitHostMDR1 = foDebitHostMDR1;
    }
    
    private String BoostEcomMerchantMDR;
    private String BoostEcomHostMDR;
    private String BoostQrMerchantMDR;
    
    private String BoostQrHostMDR;
    private String GrabEcomMerchantMDR;
    private String GrabEcomHostMDR;




    private String GrabQrMerchantMDR;
    private String GrabQrHostMDR;
    private String FpxMerchantMDR;
    private String FpxHostMDR;
    private String TngEcomMerchantMDR;
    private String TngEcomHostMDR;
    private String TngQrMerchantMDR;
    private String TngQrHostMDR;
    public String getBoostEcomMerchantMDR() {
        return BoostEcomMerchantMDR;
    }
    public void setBoostEcomMerchantMDR(String boostEcomMerchantMDR) {
        BoostEcomMerchantMDR = boostEcomMerchantMDR;
    }
    public String getBoostEcomHostMDR() {
        return BoostEcomHostMDR;
    }
    public void setBoostEcomHostMDR(String boostEcomHostMDR) {
        BoostEcomHostMDR = boostEcomHostMDR;
    }
    public String getBoostQrMerchantMDR() {
        return BoostQrMerchantMDR;
    }
    public void setBoostQrMerchantMDR(String boostQrMerchantMDR) {
        BoostQrMerchantMDR = boostQrMerchantMDR;
    }
    public String getBoostQrHostMDR() {
        return BoostQrHostMDR;
    }
    public void setBoostQrHostMDR(String boostQrHostMDR) {
        BoostQrHostMDR = boostQrHostMDR;
    }
    public String getGrabEcomMerchantMDR() {
        return GrabEcomMerchantMDR;
    }
    public void setGrabEcomMerchantMDR(String grabEcomMerchantMDR) {
        GrabEcomMerchantMDR = grabEcomMerchantMDR;
    }
    public String getGrabEcomHostMDR() {
        return GrabEcomHostMDR;
    }
    public void setGrabEcomHostMDR(String grabEcomHostMDR) {
        GrabEcomHostMDR = grabEcomHostMDR;
    }
    public String getGrabQrMerchantMDR() {
        return GrabQrMerchantMDR;
    }
    public void setGrabQrMerchantMDR(String grabQrMerchantMDR) {
        GrabQrMerchantMDR = grabQrMerchantMDR;
    }
    public String getGrabQrHostMDR() {
        return GrabQrHostMDR;
    }
    public void setGrabQrHostMDR(String grabQrHostMDR) {
        GrabQrHostMDR = grabQrHostMDR;
    }
    public String getFpxMerchantMDR() {
        return FpxMerchantMDR;
    }
    public void setFpxMerchantMDR(String fpxMerchantMDR) {
        FpxMerchantMDR = fpxMerchantMDR;
    }
    public String getFpxHostMDR() {
        return FpxHostMDR;
    }
    public void setFpxHostMDR(String fpxHostMDR) {
        FpxHostMDR = fpxHostMDR;
    }
    public String getTngEcomMerchantMDR() {
        return TngEcomMerchantMDR;
    }
    public void setTngEcomMerchantMDR(String tngEcomMerchantMDR) {
        TngEcomMerchantMDR = tngEcomMerchantMDR;
    }
    public String getTngEcomHostMDR() {
        return TngEcomHostMDR;
    }
    public void setTngEcomHostMDR(String tngEcomHostMDR) {
        TngEcomHostMDR = tngEcomHostMDR;
    }
    public String getTngQrMerchantMDR() {
        return TngQrMerchantMDR;
    }
    public void setTngQrMerchantMDR(String tngQrMerchantMDR) {
        TngQrMerchantMDR = tngQrMerchantMDR;
    }
    public String getTngQrHostMDR() {
        return TngQrHostMDR;
    }
    public void setTngQrHostMDR(String tngQrHostMDR) {
        TngQrHostMDR = tngQrHostMDR;
    }
	
}
