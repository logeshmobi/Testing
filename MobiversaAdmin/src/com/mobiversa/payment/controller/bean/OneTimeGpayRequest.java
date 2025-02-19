package com.mobiversa.payment.controller.bean;

public class OneTimeGpayRequest {
	int time_since_epoch;
	String sig;
	public int getTime_since_epoch() {
		return time_since_epoch;
	}
	public void setTime_since_epoch(int time_since_epoch) {
		this.time_since_epoch = time_since_epoch;
	}
	public String getSig() {
		return sig;
	}
	public void setSig(String sig) {
		this.sig = sig;
	}
	
	

}
