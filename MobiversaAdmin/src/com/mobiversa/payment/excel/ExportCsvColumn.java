package com.mobiversa.payment.excel;


import java.util.Arrays;
import java.util.List;

public enum ExportCsvColumn {
	PAYOUTLOGIN_EXPORT_HEADER(Arrays.asList("Time Stamp", "Merchant Name", "Customer Name", "BRN/IC", "Account No", "Bank Name", "Transaction_ID", "Amount", "Payout Fee", "Status", "Date", "Paid Time", "Paid Date", "Submerchant Mid", "Submerchant Name", "Payout Id", "Decline Reason", "Payout Type","Host Transaction ID")),
	PAYOUT_EXPORT_HEADER(Arrays.asList("Time Stamp", "Merchant Name", "Customer Name", "BRN/IC", "Account No", "Bank Name", "Transaction_ID", "Amount", "Payout Fee", "Status", "Date", "Paid Time", "Paid Date", "Submerchant Mid", "Submerchant Name", "Payout Id", "Decline Reason", "Payout Type")),
	PREAUTH_EXPORT_HEADER(Arrays.asList("Date", "Time", "Amount(RM)", "Card No", "TID", "Status", "Approval Code", "Reference")),
	BOOST_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Name on Card", "Card Number", "Reference", "Approval Code", "RRN", "Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount", "Fraud Score", "Fraud ID", "Sub Merchant MID")),
	BOOST_EXPORT_HEADER_WITHOUT_FPX(Arrays.asList("Date", "Time", "MID", "TID", "Amount","Invoice ID","Transaction ID","Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount","Sub Merchant MID")),
	//FPX_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Invoice ID", "Transaction ID","Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount", "Fraud Score", "Fraud ID", "Sub Merchant MID")),
	GRAB_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount","Invoice ID","Transaction ID","Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount","Sub Merchant MID")),
	TNG_SPP_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount","Invoice ID","Transaction ID","Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount","Sub Merchant MID")),
	PAYDEE_TRANSACTIONS_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Name on Card", "Card Number", "Reference", "AID Response", "RRN", "Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date")),
	UMOBILE_EZYWAY_TRANSACTIONS_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Name on Card", "Card Number", "Reference", "Approval Code", "RRN", "Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount", "Fraud Score", "Fraud ID", "Sub Merchant MID")),
	UMOBILE_EZYLINK_TRANSACTIONS_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Name on Card", "Card Number", "Reference", "Approval Code", "RRN", "Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount", "PREAUTH Fee", "SUB MERCHANT MID")),
	UMOBILE_EZYAUTH_TRANSACTIONS_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Name on Card", "Card Number", "Reference", "Approval Code", "RRN", "Status")),
	BNPL_TRANSACTIONS_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Name on Card", "Card Number", "Reference", "Approval Code", "RRN", "Status", "Payment Method", "MDR Amount", "Net Amount", "Payment Date", "EZYSETTLE Amount", "Fraud Score", "Fraud ID", "Sub Merchant MID")),
	FPX_FAILED_EZYWAY_TRANSACTIONS_EXPORT_HEADER(Arrays.asList("Date", "Time", "MID", "TID", "Amount", "Name on Card", "Card Number", "Reference", "Approval Code", "RRN", "MRN", "Status", "Payment Method", "Response Message")),
	FPX_FAILED_TRANSACTIONS_EXPORT_HEADER(Arrays.asList("TimeStamp", "MID", "TID", "Amount(RM)", "SellerOrderNo", "FpxTxnId", "DebitAuthNo", "Response Message")),
	RECURRING_SUMMARY_EXPORT_HEADER(Arrays.asList("Customer Name", "Tid", "Card No", "Amount", "Frequency", "No of Payments", "Last Trigger Date", "Next Trigger Date", "End Date")),
	CURRENT_DAY_BALANCE_HEADER(Arrays.asList("Business Name","Merchant Id", "Previous Balance(RM)", "Settlement Amount(RM)", "Settlement Date", "Current Balance(RM)"));

	private final List<String> columnNames;

	private ExportCsvColumn(List columnNames) {
		this.columnNames = columnNames;
	}

	public List<String> getColumnNames() {
		return this.columnNames;
	}
}