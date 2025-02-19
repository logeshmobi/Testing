package com.mobiversa.payment.connect;

import java.util.HashMap;

public class ResponseData {

	private String amount;
	private String mid;
	private String status;
	private String transactionDate;
	private String transactionType;
	private String token;
	private String trxId;
	private String txnType;
	private String transactionStatus;
	private String responseDescription;

	private String transactionId;
	private String customerName;
	private String merchantName;
//	private String TransactionId;

	private String bankName;
	private String bankAccNo;
	private String txID;
	private String txStatus;

	private String tid;
	private String requestUrl;
	private String redirectUrl;
	private String bankType;
	private String buyerName;
	private String productDesc;
	private String bank;
	private String service;
	private String mobiLink;
	private String email;
	private String sellerOrderNo;
	private String failureReason;
	
	//added for AE service
	private String netAmount;
	private String fpxTxnId;
	private String mdramt;
	private String settleDate;
	private String validateFPXUpdate;
	
	
	public String getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(String netAmount) {
		this.netAmount = netAmount;
	}

	public String getFpxTxnId() {
		return fpxTxnId;
	}

	public void setFpxTxnId(String fpxTxnId) {
		this.fpxTxnId = fpxTxnId;
	}

	public String getMdramt() {
		return mdramt;
	}

	public void setMdramt(String mdramt) {
		this.mdramt = mdramt;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getValidateFPXUpdate() {
		return validateFPXUpdate;
	}

	public void setValidateFPXUpdate(String validateFPXUpdate) {
		this.validateFPXUpdate = validateFPXUpdate;
	}


	public String getFailureReason() {
			return failureReason;
		}

		public void setFailureReason(String failureReason) {
			this.failureReason = failureReason;
		}


	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTxID() {
		return txID;
	}

	public void setTxID(String txID) {
		this.txID = txID;
	}

	public String getTxStatus() {
		return txStatus;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMobiLink() {
		return mobiLink;
	}

	public void setMobiLink(String mobiLink) {
		this.mobiLink = mobiLink;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSellerOrderNo() {
		return sellerOrderNo;
	}

	public void setSellerOrderNo(String sellerOrderNo) {
		this.sellerOrderNo = sellerOrderNo;
	}

	public void setTxStatus(String txStatus) {
		this.txStatus = txStatus;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
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

	public static void main(String[] args) {
		String ar = "{\"responseCode\":\"0000\",\"responseMessage\":\"SUCCESSFUL\",\"responseDescription\":\"Settlement Done\",\"responseData\":{}}";
		ar = ar.replace("{", "");
		ar = ar.replace("}", "");
		ar = ar.replace("\"", "");
		System.out.println(" Data : " + ar);
		splitData(ar);

	}

	@SuppressWarnings("unchecked")
	public static HashMap<String, String> splitData(String a) {
		a = a.replaceAll("\"", "");
		String[] data1 = a.split(",");
		HashMap<String, String> hm = new HashMap<String, String>();
		System.out.println(data1.length);
		for (String data : data1) {
			System.out.println("Data :" + data);
			String[] data2 = data.split(":");
			System.out.println("Data2 :" + data2.length);
			// for(String dd : data2){
			// System.out.println(" response :"+!(data2[1] == null)+"~~");
			if (data2.length == 2) {
				hm.put(data2[0], data2[1]);
			}
		}
		System.out.println(" hashmap : " + hm);
		System.out.println("Hashmap :" + hm.containsKey("responseMessage") + "   " + hm.values());
		System.out.println("Hashmap :" + hm.containsValue("responseMessage"));
		if (hm.containsKey("responseMessage")) {
			System.out.println(hm.get("responseMessage"));
		}

		return hm;
	}

}
