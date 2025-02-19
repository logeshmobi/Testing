package com.mobiversa.payment.exception;

public class MobiSuccess extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Status status;

	public MobiSuccess(Status status) {
		super(status.getMessage());
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

}