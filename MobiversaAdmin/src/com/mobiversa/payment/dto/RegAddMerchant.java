package com.mobiversa.payment.dto;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;

import org.springframework.web.multipart.MultipartFile;

public class RegAddMerchant {

	public String id;
	public String mid;
	public String midType;
	public String ezymotomid;
	public String ezypassmid;
	public String ezywaymid;
	public String ezyrecmid;

	public String umMid;
	public String umMotoMid;
	public String umEzypassMid;
	public String umEzywayMid;
	public String umEzyrecMid;

	public String fpxMid;
	public String boostMid;
	
	public String fiuuMid;

	public String agentName;
	public String registeredName;
	public String businessName;
	public String businessRegNo;
	public String registeredAddress;
	public String businessAddress;
	public String mailingAddress;
	public String name;
	public String contactNo;
	public String email;
	public String salutation;
	public String ownerSalutation1;
	public String ownerName1;

	public String passportNo1;
	public String residentialAddress1;
	public String ownerContactNo1;
	public String ownerSalutation2;
	public String ownerName2;
	public String passportNo2;
	public String residentialAddress2;
	public String ownerContactNo2;
	public String ownerSalutation3;
	public String ownerName3;
	public String passportNo3;
	public String residentialAddress3;
	public String ownerContactNo3;
	public String ownerSalutation4;
	public String ownerName4;
	public String passportNo4;
	public String residentialAddress4;
	public String ownerContactNo4;
	public String ownerSalutation5;
	public String foreignCard;

	public String ezysettle;

	// Async Payout Changes
	public String isAsyncPayoutEnabled;
	public String payoutIpnUrl;
	public String asyncEnableReason;
	public String quickPayoutUrl;

	public String getQuickPayoutUrl() {
		return quickPayoutUrl;
	}

	public void setQuickPayoutUrl(String quickPayoutUrl) {
		this.quickPayoutUrl = quickPayoutUrl;
	}

	//Enable Acc Enq & Quick Payout
	public String isAccountEnquiryEnabled;
	public String isQuickPayoutEnabled;

	public String getIsAccountEnquiryEnabled() {
		return isAccountEnquiryEnabled;
	}

	public void setIsAccountEnquiryEnabled(String isAccountEnquiryEnabled) {
		this.isAccountEnquiryEnabled = isAccountEnquiryEnabled;
	}

	public String getIsQuickPayoutEnabled() {
		return isQuickPayoutEnabled;
	}

	public void setIsQuickPayoutEnabled(String isQuickPayoutEnabled) {
		this.isQuickPayoutEnabled = isQuickPayoutEnabled;
	}

	// Max Payout Limit transactions changes
	public String maxPayoutTxnLimit;

	public String getEzysettle() {
		return ezysettle;
	}

	public void setEzysettle(String ezysettle) {
		this.ezysettle = ezysettle;
	}

	public String getForeignCard() {
		return foreignCard;
	}

	public void setForeignCard(String foreignCard) {
		this.foreignCard = foreignCard;
	}

	public String ownerName5;
	public String passportNo5;
	public String residentialAddress5;
	public String ownerContactNo5;

	public String website;
	public String officeNo;
	public String faxNo;
	public String businessType;
	// public String businessCategory;
	public String companyType;

	public String natureOfBusiness;
	public String bankName;
	public String bankAccNo;
	public String documents;
	public String referralId;
	public String tradingName;
	public String noOfReaders;
	public String signedPackage;
	public String yearIncorporated;

	public String wavierMonth;
	public String businessState;
	public String businessCity;
	public String businessPostCode;

	public String officeEmail;

	private String merchantImgFilePath;
	public MultipartFile imageFile;
	private String merchantProfile;
	private String merchantLogo;
	private String auth3DS;
	private String merchantType;

	public String fileId;

	public String ownerCount;

	public String status;

	public String statusRemarks;
	public String preAuth;
	public String mdr;

	public MultipartFile formFile;
	public MultipartFile docFile;
	public MultipartFile payFile;

	public String formFName;
	public String docFName;
	public String payFName;

	public String vcc;

	public String accType;

	public String contactIc;
	public String subMid;

	public String mmid;
	public String businessCountry;

	public String username;

	private String secoundaryEmail;

	public String getSecoundaryEmail() {
		return secoundaryEmail;
	}

	public void setSecoundaryEmail(String secoundaryEmail) {
		this.secoundaryEmail = secoundaryEmail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBusinessCountry() {
		return businessCountry;
	}

	public void setBusinessCountry(String businessCountry) {
		this.businessCountry = businessCountry;
	}

	public String getContactIc() {
		return contactIc;
	}

	public void setContactIc(String contactIc) {
		this.contactIc = contactIc;
	}

	public String getSubMid() {
		return subMid;
	}

	public void setSubMid(String subMid) {
		this.subMid = subMid;
	}

	public String getMmid() {
		return mmid;
	}

	public void setMmid(String mmid) {
		this.mmid = mmid;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getVcc() {
		return vcc;
	}

	public void setVcc(String vcc) {
		this.vcc = vcc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getUmMid() {
		return umMid;
	}

	public void setUmMid(String umMid) {
		this.umMid = umMid;
	}

	public String getUmMotoMid() {
		return umMotoMid;
	}

	public void setUmMotoMid(String umMotoMid) {
		this.umMotoMid = umMotoMid;
	}

	public String getUmEzypassMid() {
		return umEzypassMid;
	}

	public void setUmEzypassMid(String umEzypassMid) {
		this.umEzypassMid = umEzypassMid;
	}

	public String getUmEzywayMid() {
		return umEzywayMid;
	}

	public void setUmEzywayMid(String umEzywayMid) {
		this.umEzywayMid = umEzywayMid;
	}

	public String getUmEzyrecMid() {
		return umEzyrecMid;
	}

	public void setUmEzyrecMid(String umEzyrecMid) {
		this.umEzyrecMid = umEzyrecMid;
	}

	public String getEzywaymid() {
		return ezywaymid;
	}

	public void setEzywaymid(String ezywaymid) {
		this.ezywaymid = ezywaymid;
	}

	public String getEzyrecmid() {
		return ezyrecmid;
	}

	public void setEzyrecmid(String ezyrecmid) {
		this.ezyrecmid = ezyrecmid;
	}

	public String getAuth3DS() {
		return auth3DS;
	}

	public void setAuth3DS(String auth3ds) {
		auth3DS = auth3ds;
	}

	public String getMidType() {
		return midType;
	}

	public void setMidType(String midType) {
		this.midType = midType;
	}

	public String getEzymotomid() {
		return ezymotomid;
	}

	public void setEzymotomid(String ezymotomid) {
		this.ezymotomid = ezymotomid;
	}

	public String getEzypassmid() {
		return ezypassmid;
	}

	public void setEzypassmid(String ezypassmid) {
		this.ezypassmid = ezypassmid;
	}

	public String getMerchantLogo() {
		return merchantLogo;
	}

	public void setMerchantLogo(String merchantLogo) {
		this.merchantLogo = merchantLogo;
	}

	@Basic(fetch = FetchType.LAZY)
	@Column(length = Integer.MAX_VALUE)
	public byte[] merchantImage1;

	// new field added 23062016

	public byte[] getMerchantImage1() {
		return merchantImage1;
	}

	public void setMerchantImage1(byte[] merchantImage1) {
		this.merchantImage1 = merchantImage1;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public String getMerchantImgFilePath() {
		return merchantImgFilePath;
	}

	public void setMerchantImgFilePath(String merchantImgFilePath) {
		this.merchantImgFilePath = merchantImgFilePath;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getMerchantProfile() {
		return merchantProfile;
	}

	public void setMerchantProfile(String merchantProfile) {
		this.merchantProfile = merchantProfile;
	}

	public String getFormFName() {
		return formFName;
	}

	public void setFormFName(String formFName) {
		this.formFName = formFName;
	}

	public String getDocFName() {
		return docFName;
	}

	public void setDocFName(String docFName) {
		this.docFName = docFName;
	}

	public String getPayFName() {
		return payFName;
	}

	public void setPayFName(String payFName) {
		this.payFName = payFName;
	}

	/*
	 * //new field 28/06/2016 public String otherSalutation;
	 * 
	 * public String otherSalutation1;
	 * 
	 * public String OtherSalutation2;
	 * 
	 * public String otherSalutation3;
	 * 
	 * public String otherSalutation4;
	 * 
	 * public String otherSalutation5;
	 */

	// new field added 01/07/2016

	public MultipartFile getFormFile() {
		return formFile;
	}

	public void setFormFile(MultipartFile formFile) {
		this.formFile = formFile;
	}

	public MultipartFile getDocFile() {
		return docFile;
	}

	public void setDocFile(MultipartFile docFile) {
		this.docFile = docFile;
	}

	public MultipartFile getPayFile() {
		return payFile;
	}

	public void setPayFile(MultipartFile payFile) {
		this.payFile = payFile;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String autoSettled;

	public String getAutoSettled() {
		return autoSettled;
	}

	public void setAutoSettled(String autoSettled) {
		this.autoSettled = autoSettled;
	}

	public String getMdr() {
		return mdr;
	}

	public void setMdr(String mdr) {
		this.mdr = mdr;
	}
	/*
	 * public String otherSignedPackage;
	 * 
	 * public String otherDocumnets;
	 */

	/*
	 * public String getOtherSalutation() { return otherSalutation; } public void
	 * setOtherSalutation(String otherSalutation) { this.otherSalutation =
	 * otherSalutation; } public String getOtherSalutation1() { return
	 * otherSalutation1; } public void setOtherSalutation1(String otherSalutation1)
	 * { this.otherSalutation1 = otherSalutation1; } public String
	 * getOtherSalutation2() { return OtherSalutation2; } public void
	 * setOtherSalutation2(String otherSalutation2) { OtherSalutation2 =
	 * otherSalutation2; } public String getOtherSalutation3() { return
	 * otherSalutation3; } public void setOtherSalutation3(String otherSalutation3)
	 * { this.otherSalutation3 = otherSalutation3; } public String
	 * getOtherSalutation4() { return otherSalutation4; } public void
	 * setOtherSalutation4(String otherSalutation4) { this.otherSalutation4 =
	 * otherSalutation4; } public String getOtherSalutation5() { return
	 * otherSalutation5; } public void setOtherSalutation5(String otherSalutation5)
	 * { this.otherSalutation5 = otherSalutation5; }
	 */
	/*
	 * public String getOtherSignedPackage() { return otherSignedPackage; } public
	 * void setOtherSignedPackage(String otherSignedPackage) {
	 * this.otherSignedPackage = otherSignedPackage; } public String
	 * getOtherDocumnets() { return otherDocumnets; } public void
	 * setOtherDocumnets(String otherDocumnets) { this.otherDocumnets =
	 * otherDocumnets; }
	 */
	public String getPreAuth() {
		return preAuth;
	}

	public void setPreAuth(String preAuth) {
		this.preAuth = preAuth;
	}

	public String getStatusRemarks() {
		return statusRemarks;
	}

	public void setStatusRemarks(String statusRemarks) {
		this.statusRemarks = statusRemarks;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getRegisteredName() {
		return registeredName;
	}

	public void setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessRegNo() {
		return businessRegNo;
	}

	public void setBusinessRegNo(String businessRegNo) {
		this.businessRegNo = businessRegNo;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getOwnerName1() {
		return ownerName1;
	}

	public void setOwnerName1(String ownerName1) {
		this.ownerName1 = ownerName1;
	}

	public String getPassportNo1() {
		return passportNo1;
	}

	public void setPassportNo1(String passportNo1) {
		this.passportNo1 = passportNo1;
	}

	public String getResidentialAddress1() {
		return residentialAddress1;
	}

	public void setResidentialAddress1(String residentialAddress1) {
		this.residentialAddress1 = residentialAddress1;
	}

	public String getOwnerContactNo1() {
		return ownerContactNo1;
	}

	public void setOwnerContactNo1(String ownerContactNo1) {
		this.ownerContactNo1 = ownerContactNo1;
	}

	public String getOwnerSalutation1() {
		return ownerSalutation1;
	}

	public void setOwnerSalutation1(String ownerSalutation1) {
		this.ownerSalutation1 = ownerSalutation1;
	}

	public String getOwnerName2() {
		return ownerName2;
	}

	public void setOwnerName2(String ownerName2) {
		this.ownerName2 = ownerName2;
	}

	public String getPassportNo2() {
		return passportNo2;
	}

	public void setPassportNo2(String passportNo2) {
		this.passportNo2 = passportNo2;
	}

	public String getResidentialAddress2() {
		return residentialAddress2;
	}

	public void setResidentialAddress2(String residentialAddress2) {
		this.residentialAddress2 = residentialAddress2;
	}

	public String getOwnerContactNo2() {
		return ownerContactNo2;
	}

	public void setOwnerContactNo2(String ownerContactNo2) {
		this.ownerContactNo2 = ownerContactNo2;
	}

	public String getOwnerSalutation2() {
		return ownerSalutation2;
	}

	public void setOwnerSalutation2(String ownerSalutation2) {
		this.ownerSalutation2 = ownerSalutation2;
	}

	public String getOwnerName3() {
		return ownerName3;
	}

	public void setOwnerName3(String ownerName3) {
		this.ownerName3 = ownerName3;
	}

	public String getPassportNo3() {
		return passportNo3;
	}

	public void setPassportNo3(String passportNo3) {
		this.passportNo3 = passportNo3;
	}

	public String getResidentialAddress3() {
		return residentialAddress3;
	}

	public void setResidentialAddress3(String residentialAddress3) {
		this.residentialAddress3 = residentialAddress3;
	}

	public String getOwnerContactNo3() {
		return ownerContactNo3;
	}

	public void setOwnerContactNo3(String ownerContactNo3) {
		this.ownerContactNo3 = ownerContactNo3;
	}

	public String getOwnerName4() {
		return ownerName4;
	}

	public String getBusinessState() {
		return businessState;
	}

	public void setBusinessState(String businessState) {
		this.businessState = businessState;
	}

	public String getBusinessCity() {
		return businessCity;
	}

	public void setBusinessCity(String businessCity) {
		this.businessCity = businessCity;
	}

	public String getBusinessPostCode() {
		return businessPostCode;
	}

	public void setBusinessPostCode(String businessPostCode) {
		this.businessPostCode = businessPostCode;
	}

	public String getOwnerCount() {
		return ownerCount;
	}

	public void setOwnerCount(String ownerCount) {
		this.ownerCount = ownerCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOwnerName4(String ownerName4) {
		this.ownerName4 = ownerName4;
	}

	public String getPassportNo4() {
		return passportNo4;
	}

	public void setPassportNo4(String passportNo4) {
		this.passportNo4 = passportNo4;
	}

	public String getResidentialAddress4() {
		return residentialAddress4;
	}

	public void setResidentialAddress4(String residentialAddress4) {
		this.residentialAddress4 = residentialAddress4;
	}

	public String getOwnerContactNo4() {
		return ownerContactNo4;
	}

	public void setOwnerContactNo4(String ownerContactNo4) {
		this.ownerContactNo4 = ownerContactNo4;
	}

	public String getOwnerName5() {
		return ownerName5;
	}

	public void setOwnerName5(String ownerName5) {
		this.ownerName5 = ownerName5;
	}

	public String getPassportNo5() {
		return passportNo5;
	}

	public void setPassportNo5(String passportNo5) {
		this.passportNo5 = passportNo5;
	}

	public String getResidentialAddress5() {
		return residentialAddress5;
	}

	public void setResidentialAddress5(String residentialAddress5) {
		this.residentialAddress5 = residentialAddress5;
	}

	public String getOwnerContactNo5() {
		return ownerContactNo5;
	}

	public void setOwnerContactNo5(String ownerContactNo5) {
		this.ownerContactNo5 = ownerContactNo5;
	}

	public String getOfficeEmail() {
		return officeEmail;
	}

	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getOfficeNo() {
		return officeNo;
	}

	public void setOfficeNo(String officeNo) {
		this.officeNo = officeNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/*
	 * public String getBusinessCategory() { return businessCategory; } public void
	 * setBusinessCategory(String businessCategory) { this.businessCategory =
	 * businessCategory; }
	 */
	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getDocuments() {
		return documents;
	}

	public void setDocuments(String documents) {
		this.documents = documents;
	}

	public String getReferralId() {
		return referralId;
	}

	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getNoOfReaders() {
		return noOfReaders;
	}

	public void setNoOfReaders(String noOfReaders) {
		this.noOfReaders = noOfReaders;
	}

	public String getSignedPackage() {
		return signedPackage;
	}

	public void setSignedPackage(String signedPackage) {
		this.signedPackage = signedPackage;
	}

	public String getYearIncorporated() {
		return yearIncorporated;
	}

	public void setYearIncorporated(String yearIncorporated) {
		this.yearIncorporated = yearIncorporated;
	}

	public String getWavierMonth() {
		return wavierMonth;
	}

	public void setWavierMonth(String wavierMonth) {
		this.wavierMonth = wavierMonth;
	}

	public String getOwnerSalutation3() {
		return ownerSalutation3;
	}

	public void setOwnerSalutation3(String ownerSalutation3) {
		this.ownerSalutation3 = ownerSalutation3;
	}

	public String getOwnerSalutation4() {
		return ownerSalutation4;
	}

	public void setOwnerSalutation4(String ownerSalutation4) {
		this.ownerSalutation4 = ownerSalutation4;
	}

	public String getOwnerSalutation5() {
		return ownerSalutation5;
	}

	public void setOwnerSalutation5(String ownerSalutation5) {
		this.ownerSalutation5 = ownerSalutation5;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getFpxMid() {
		return fpxMid;
	}

	public void setFpxMid(String fpxMid) {
		this.fpxMid = fpxMid;
	}

	public String getBoostMid() {
		return boostMid;
	}

	public void setBoostMid(String boostMid) {
		this.boostMid = boostMid;
	}

	public String getIsAsyncPayoutEnabled() {
		return isAsyncPayoutEnabled;
	}

	public void setIsAsyncPayoutEnabled(String isAsyncPayoutEnabled) {
		this.isAsyncPayoutEnabled = isAsyncPayoutEnabled;
	}

	public String getPayoutIpnUrl() {
		return payoutIpnUrl;
	}

	public void setPayoutIpnUrl(String payoutIpnUrl) {
		this.payoutIpnUrl = payoutIpnUrl;
	}

	public String getAsyncEnableReason() {
		return asyncEnableReason;
	}

	public void setAsyncEnableReason(String asyncEnableReason) {
		this.asyncEnableReason = asyncEnableReason;
	}

	public String getMaxPayoutTxnLimit() {
		return maxPayoutTxnLimit;
	}

	public void setMaxPayoutTxnLimit(String bigDecimal) {
		this.maxPayoutTxnLimit = bigDecimal;
	}

	public String getFiuuMid() {
		return fiuuMid;
	}

	public void setFiuuMid(String fiuuMid) {
		this.fiuuMid = fiuuMid;
	}
	
	

}
