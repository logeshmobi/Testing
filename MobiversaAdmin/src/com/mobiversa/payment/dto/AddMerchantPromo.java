package com.mobiversa.payment.dto;


import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;

import org.springframework.web.multipart.MultipartFile;

public class AddMerchantPromo {
	
	
	public String id;
	
	
	public String merchantId;
	
	public String mid;
	public String motoMid;
	public String ezypassMid;
	public String ezyrecMid;
	 public String merchantName;
	 public String pCode;
	 public String merchantType;
	 
	 
	
	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	
	public String getEzyrecMid() {
		return ezyrecMid;
	}

	public void setEzyrecMid(String ezyrecMid) {
		this.ezyrecMid = ezyrecMid;
	}

	public String getMotoMid() {
		return motoMid;
	}

	public void setMotoMid(String motoMid) {
		this.motoMid = motoMid;
	}

	public String getEzypassMid() {
		return ezypassMid;
	}

	public void setEzypassMid(String ezypassMid) {
		this.ezypassMid = ezypassMid;
	}

	public String username;
	public String deviceId;
	 public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String promoName;
	 
	 @Basic(fetch = FetchType.LAZY)
		@Column(length = Integer.MAX_VALUE)
	 	public byte[] promoImage1;
	 	@Basic(fetch = FetchType.LAZY)
		@Column(length = Integer.MAX_VALUE)
	 	public byte[] promoImage2;
	 
	public String status;
	
	public String promoDesc;
	
	public String sendType;
	

	public String emailList;
	public MultipartFile imageFile1;
	public String custMailList;
	public String points;
	public String remarks;
	private Date mailDate;
	@Basic(fetch = FetchType.LAZY)
	@Column(length = Integer.MAX_VALUE)
	private String merchantLogo;
	private String mLogo;
	private String promoLogo1;
	private String promoLogo2;
	private String mFilepath;

	private String promoImgFilePath1;
	private String promoImgFilePath2;
	public MultipartFile imageFile;	
	public String fileId;
    public String msgFlag;
    public String msgCount;
    
    

	public String tid;
	public String motoTid;
	public String ezypassTid;
	public String ezyrecTid;
    public String validityDate;
	
    public String merchantEmail;
    
    
    public String emailPreview;
    
    public String validFrom;
    
    public String validTo;
    
    
    
    
    public String getEzyrecTid() {
		return ezyrecTid;
	}

	public void setEzyrecTid(String ezyrecTid) {
		this.ezyrecTid = ezyrecTid;
	}

	public String getMotoTid() {
		return motoTid;
	}

	public void setMotoTid(String motoTid) {
		this.motoTid = motoTid;
	}

	public String getEzypassTid() {
		return ezypassTid;
	}

	public void setEzypassTid(String ezypassTid) {
		this.ezypassTid = ezypassTid;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public String getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
    public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	
    
	 public String getEmailPreview() {
		return emailPreview;
	}

	public void setEmailPreview(String emailPreview) {
		this.emailPreview = emailPreview;
	}

	public String getMerchantEmail() {
		return merchantEmail;
	}

	public void setMerchantEmail(String merchantEmail) {
		this.merchantEmail = merchantEmail;
	}

	public byte[] getPromoImage1() {
		return promoImage1;
	}

	public void setPromoImage1(byte[] promoImage1) {
		this.promoImage1 = promoImage1;
	}
    
	public String getPromoLogo1() {
		return promoLogo1;
	}

	public void setPromoLogo1(String promoLogo1) {
		this.promoLogo1 = promoLogo1;
	}

	public String getPromoLogo2() {
		return promoLogo2;
	}

	public void setPromoLogo2(String promoLogo2) {
		this.promoLogo2 = promoLogo2;
	}

	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    

	public String getPromoImgFilePath1() {
		return promoImgFilePath1;
	}

	public void setPromoImgFilePath1(String promoImgFilePath1) {
		this.promoImgFilePath1 = promoImgFilePath1;
	}

	public String getPromoImgFilePath2() {
		return promoImgFilePath2;
	}

	public void setPromoImgFilePath2(String promoImgFilePath2) {
		this.promoImgFilePath2 = promoImgFilePath2;
	}
    
    
    public byte[] getPromoImage2() {
		return promoImage2;
	}

	public void setPromoImage2(byte[] promoImage2) {
		this.promoImage2 = promoImage2;
	}
	
    public String getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(String msgCount) {
		this.msgCount = msgCount;
	}

	public String getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
	
	public MultipartFile getImageFile1() {
		return imageFile1;
	}

	public void setImageFile1(MultipartFile imageFile1) {
		this.imageFile1 = imageFile1;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

 

   public String getmLogo() {
		return mLogo;
	}

	public String getMerchantLogo() {
	return merchantLogo;
}

  public void setMerchantLogo(String merchantLogo) {
	this.merchantLogo = merchantLogo;
}

	public void setmLogo(String mLogo) {
		this.mLogo = mLogo;
	}

	public Date getMailDate() {
		return mailDate;
	}

	public void setMailDate(Date mailDate) {
		//this.mailDate = mailDate;
		this.mailDate = new Date();
		this.mailDate = mailDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	
	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getmFilepath() {
		return mFilepath;
	}

	public void setmFilepath(String mFilepath) {
		this.mFilepath = mFilepath;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCustMailList() {
		return custMailList;
	}

	public void setCustMailList(String custMailList) {
		this.custMailList = custMailList;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getPromoName() {
		return promoName;
	}

	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPromoDesc() {
		return promoDesc;
	}

	public void setPromoDesc(String promoDesc) {
		this.promoDesc = promoDesc;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}

	public String getEmailList() {
		return emailList;
	}

	public void setEmailList(String emailList) {
		this.emailList = emailList;
	}

	

}
