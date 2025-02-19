package com.mobiversa.payment.dto;

public class MerchantPaymentDetailsDto {
	
	private String id;
	private String enablePaymentMethod;
	private String hostMdr;
	private String minRate;
	private String mobiMdr;
	private String paymentMethod;
	private String merchantFk;
	private String hostType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnablePaymentMethod() {
		return enablePaymentMethod;
	}
	public void setEnablePaymentMethod(String enablePaymentMethod) {
		this.enablePaymentMethod = enablePaymentMethod;
	}
	public String getHostMdr() {
		return hostMdr;
	}
	public void setHostMdr(String hostMdr) {
		this.hostMdr = hostMdr;
	}
	public String getMinRate() {
		return minRate;
	}
	public void setMinRate(String minRate) {
		this.minRate = minRate;
	}
	public String getMobiMdr() {
		return mobiMdr;
	}
	public void setMobiMdr(String mobiMdr) {
		this.mobiMdr = mobiMdr;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getMerchantFk() {
		return merchantFk;
	}
	public void setMerchantFk(String merchantFk) {
		this.merchantFk = merchantFk;
	}
	public String getHostType() {
		return hostType;
	}
	public void setHostType(String hostType) {
		this.hostType = hostType;
	}
	
	@Override
	public String toString() {
		return "MerchantPaymentDetailsDto [id=" + id + ", enablePaymentMethod=" + enablePaymentMethod + ", hostMdr="
				+ hostMdr + ", minRate=" + minRate + ", mobiMdr=" + mobiMdr + ", paymentMethod=" + paymentMethod
				+ ", merchantFk=" + merchantFk + ", hostType=" + hostType + "]";
	}
	
	

}
