package com.mobiversa.payment.dto;

public class HostResponse {

	public static String getHostResponse(String responseNo) {
		
		switch(responseNo) {
		case "00":
			responseNo="Approved";
			break;
		case "01":
			responseNo="Refer To Issuer";
			break;
		case "02":
			responseNo="Refer To Issuer, special condition";
			break;
		case "03":
			responseNo="Invalid Merchant ID";
			break;
		case "04":
			responseNo="Pick Up Card";
			break;
		case "05":
			responseNo="Do Not Honour";
			break;
		case "06":
			responseNo="Error";
			break;
		case "07":
			responseNo="Pick Uo Card,Special condition";
			break;
		case "08":
			responseNo="Check Signature/Id or Honor with ID";
			break;
		case "10":
			responseNo="Partial Approval";
			break;
		case "11":
			responseNo="VIP Approval";
			break;
		case "12":
			responseNo="Invalid Transaction";
			break;
		case "13":
			responseNo="Invalid Amount";
			break;
		case "14":
			responseNo="Invalid Card No";
			break;
		case "15":
			responseNo="Invalid Issuer";
			break;
		case "16":
			responseNo="Approved to update track 2(Reserved)";
			break;
		case "17":
			responseNo="Customer Cancellation (Reversal only)";
			break;
		case "19":
			responseNo="Re-enter Transaction";
			break;
		case "21":
			responseNo="No Transactions";
			break;
		case "22":
			responseNo="Related Transaction Error; Suspected Malfunction";
			break;
		case "24":
			responseNo="Invalid Currency Code";
			break;
		case "25":
			responseNo="Terminated/Inactive card";
			break;
		case "30":
			responseNo="Message Format Error";
			break;
		case "31":
			responseNo="Bank ID Not Found";
			break;
		case "32":
			responseNo="Partial Reversal";
			break;
		case "38":
			responseNo="PIN Try Limit Exceeded";
			break;
		case "41":
			responseNo="Card Reported Lost";
			break;
		case "43":
			responseNo="Stolen Card";
			break;
		case "44":
			responseNo="PIN Change Require";
			break;
		case "45":
			responseNo="Card Not Activated For Use";
			break;
		case "51":
			responseNo="Insufficient Fund";
			break;
		case "52":
			responseNo="No Checking Account";
			break;
		case "53":
			responseNo="No Savings Account";
			break;
		case "54":
			responseNo="Expired Card";
			break;
		case "55":
			responseNo="Invalid PIN";
			break;
		case "56":
			responseNo="Invalid Card";
			break;
		case "57":
			responseNo="Transaction Not Permitted to Cardholder";
			break;
		case "58":
			responseNo="Transaction Not Permitted to Terminal";
			break;
		case "59":
			responseNo="Suspected Fraud";
			break;
		case "61":
			responseNo="Over Limit";
			break;
		case "62":
			responseNo="Restricted Card";
			break;
		case "63":
			responseNo="Security Violation";
			break;
		case "64":
			responseNo="Transaction does not fulfill AML requirement";
			break;
		case "65":
			responseNo="Exceeds Withdrawal Count Limit";
			break;
		case "70":
			responseNo="Contact Card Issuer";
			break;
		case "71":
			responseNo="PIN not changed";
			break;
		case "75":
			responseNo="PIN Tries Exceeded";
			break;
		case "76":
			responseNo="Invalid Description Code";
			break;
		case "77":
			responseNo="Reconcile Error";
			break;
		case "78":
			responseNo="Invalid Trace/TMK Reference No";
			break;
		case "79":
			responseNo="Batch Already Open";
			break;
		case "80":
			responseNo="Invalid Batch No";
			break;
		case "85":
			responseNo="Batch Not Found";
			break;
		case "86":
			responseNo="PIN Validation Not Possible";
			break;
		case "87":
			responseNo="Purchase Amount Only, No Cash Back Allowed";
			break;
		case "88":
			responseNo="cryptographic Failure, Call Issuer";
			break;
		case "89":
			responseNo="Invalid Terminal ID";
			break;
		case "91":
			responseNo="Issuer/Switch Inoperative";
			break;
		case "92":
			responseNo="Destination Cannot Be Found for Routing";
			break;
		case "93":
			responseNo="Transaction Cannot be Completed; Violation of law";
			break;
		case "94":
			responseNo="Duplicate Transaction";
			break;
		case "95":
			responseNo="total Mismatch";
			break;
		case "96":
			responseNo="System Malfunction/Error";
			break;
		case "98":
			responseNo="Issuer Response Not Receive by UnionPay";
			break;
		case "99":
			responseNo="Declined";
			break;
		default:
			responseNo="No Response..";
			break;
			
		}
		
		return responseNo;
		
	}
}
