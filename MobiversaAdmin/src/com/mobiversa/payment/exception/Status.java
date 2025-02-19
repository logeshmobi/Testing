package com.mobiversa.payment.exception;

public enum Status {
	SUCCESS("0000", "SUCCESS"),
	FAILURE("0001", "FAILURE"),

	INVALID_TID("1100", "Invalid TID.!"), 
	INVALID_MOTOTID("1101", "Invalid MOTO TID.!"), 
	INVALID_EZYWAYTID("1102", "Invalid EZYWAY TID.!"), 
	INVALID_EZYRECTID("1103", "Invalid EZYREC TID.!"), 
	INVALID_EZYPASSTID("1104", "Invalid EZYPASS TID.!"), 
	
	EXIST_TID("1100", "EXIST TID.!"), 
	EXIST_MOTOTID("1101", "EXIST MOTO TID.!"), 
	EXIST_EZYWAYTID("1102", "EXIST EZYWAY TID.!"), 
	EXIST_EZYRECTID("1103", "EXIST EZYREC TID.!"), 
	EXIST_EZYPASSTID("1104", "EXIST EZYPASS TID.!"), 
	
	EXIST_DEVICEID("1100", "EXIST DeviceID.!"), 
	EXIST_MOTODEVICEID("1100", "EXIST MOTO DeviceID.!"), 
	EXIST_EZYWAYDEVICEID("1100", "EXIST EZYWAY DeviceID.!"), 
	EXIST_EZYRECDEVICEID("1100", "EXIST EZYREC DeviceID.!"), 
	EXIST_EZYPASSDEVICEID("1100", "EXIST EZYPASS DeviceID.!"), 
	
	HOST_DOWN("9900", "Bank host is busy"),
	
	EMPTY_TID("1100", "Empty TID.!"),
	EMPTY_MID("1100", "Empty MID.!"),
	EMPTY_DEVICEID("1100", "Empty DeviceID.!"), 
	INVALID_TID_DETAILS("1100", "INVALID TID"),
	INVALID_DEVICEID_DETAILS("1100", "INVALID DeviceID"),
	EXIST_TID_OR_DEVICEID_DETAILS("1100", "Exist TID/DEVICEID Details"), 
	EMPTY_UPDATE_TYPE("1100","Empty Update Type"),
	EXIST_DATA_BYMOTO("1100","Data Existed - Update using through MOTO"),
	EXIST_DATA_BYEZYWIRE("1100","Data Existed - Update through EZYWIRE"),
	EXIST_DATA_BYEZYREC("1100","Data Existed - Update through EZYREC"),
	EXIST_DATA_BYEZYPASS("1100","Data Existed - Update through EZYPASS"),
	
	INVALID_CAPTCHA("1100","Invalid Captcha"),
	INVALID_CREDENTIALS("1100","Invalid Credentials"),
	EMPTY_DATA("1100","Empty Data"),
	INVALID_PASSWORD("1100","Invalid Password"),
	INVALID_USERNAME("1100","Invalid Username"),
	CONNECTION_TIME_OUT("1100","Connection Time Out"),
	HTML_DATA("1100","HTML Data in request"),
	MOBILE_USER_NOT_EXISTS("1100","Mobile User not found"),
	WITHDRAW_FAILURE("1100","Withdraw Failure"),
	ACCOUNT_ENQUIRY("6666","Rare scenario"),
	SQL_EXCEPTION("1100","SQL EXCEPTION");

	

	// Fraud Error Code ends
	String code;
	String message;

	private Status(final String code, final String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}