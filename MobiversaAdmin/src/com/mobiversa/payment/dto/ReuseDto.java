package com.mobiversa.payment.dto;

public class ReuseDto {

	private String curlecRequestAmount;
	private double afterDeductCurlecFee;
	private double slab;
	public String getCurlecRequestAmount() {
		return curlecRequestAmount;
	}
	public void setCurlecRequestAmount(String curlecRequestAmount) {
		this.curlecRequestAmount = curlecRequestAmount;
	}
	public double getAfterDeductCurlecFee() {
		return afterDeductCurlecFee;
	}
	public void setAfterDeductCurlecFee(double afterDeductCurlecFee) {
		this.afterDeductCurlecFee = afterDeductCurlecFee;
	}
	public double getSlab() {
		return slab;
	}
	public void setSlab(double slab) {
		this.slab = slab;
	}
	
}
