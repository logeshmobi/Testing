package com.mobiversa.payment.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;

import org.springframework.web.multipart.MultipartFile;

public class MerchtCustMail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String mid;
	private String motoMid;
	private String ezypassMid;
	private String merchantId;
	private String merchantName;
	private String businessName;

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	private String logoFilePath;

	private String mFilepath;

	private String merchantAddress;
	private String contactNo;
	private String Email;
	private String mLogo;
	private MultipartFile mailFile;
	private MultipartFile businessAddress1;
	
	
	
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

	public MultipartFile getBusinessAddress1() {
		return businessAddress1;
	}

	public void setBusinessAddress1(MultipartFile businessAddress1) {
		this.businessAddress1 = businessAddress1;
	}

	public String getEmailText() {
		return emailText;
	}

	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	private String custMailList;
	private String emailText;

	private MultipartFile imageFile;
	@Basic(fetch = FetchType.LAZY)
	@Column(length = Integer.MAX_VALUE)
	public byte[] merchantLogo;

	public String getLogoFilePath() {
		return logoFilePath;
	}

	public void setLogoFilePath(String logoFilePath) {
		this.logoFilePath = logoFilePath;
	}

	public byte[] getMerchantLogo() {
		return merchantLogo;
	}

	public String getmLogo() {
		return mLogo;
	}

	public void setmLogo(String mLogo) {
		this.mLogo = mLogo;
	}

	public void setMerchantLogo(byte[] merchantLogo) {
		this.merchantLogo = merchantLogo;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getmFilepath() {
		return mFilepath;
	}

	public void setmFilepath(String mFilepath) {
		this.mFilepath = mFilepath;
	}

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public MultipartFile getMailFile() {
		return mailFile;
	}

	public void setMailFile(MultipartFile mailFile) {
		this.mailFile = mailFile;
	}

	public String getCustMailList() {
		return custMailList;
	}

	public void setCustMailList(String custMailList) {
		this.custMailList = custMailList;
	}

}
