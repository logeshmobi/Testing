package com.mobiversa.payment.util;

import org.apache.log4j.Logger;

public class CardType {
	
	private static Logger logger=Logger.getLogger(CardType.class);
	//public static String cardType=null;
/**
 * @param aid
 * @return Card Type
 */
public static String getCardType(String aid ){
	String cardType=null;
	if(aid!=null){
	aid=aid.toUpperCase();
	}
	switch (aid) {
	// VISA CARD TYPES
	case "A000000003000000":
		cardType="(VISA)Card Manager";
		break;
	case "A00000000300037561":
		cardType="Bonuscard";
		break;
	case "A0000000031010":
		cardType="VISA Debit/Credit (Classic)";
		break;
	case "A000000003101001":
		cardType="VISA Credit";
		break;
	case "A000000003101002":
		cardType="VISA Debit";
		break;
	case "A0000000032010":
		cardType="VISA Electron(Debit)";
		break;
	case "A0000000032020":
		cardType="VISA";
		break;
	case "A0000000033010":
		cardType="VISA Interlink";
		break;
	case "A0000000034010":
		cardType="VISA Specific";
		break;
	case "A0000000035010":
		cardType="VISA Specific";
		break;
	case "A000000003534441":
		cardType="Schlumberger Security Domain";
		break;
	case "A0000000035350":
		cardType="Security Domain";
		break;
	case "A000000003535041":
		cardType="Security Domain";
		break;
	case "A0000000036010":
		cardType="Domestic Visa Cash Stored Value";
		break;
	case "A0000000036020":
		cardType="International Visa Cash Stored Value";
		break;
	case "A0000000038002":
		cardType="VISA Auth, VisaRemAuthen EMV-CAP (DPA)";
		break;
	case "A0000000038010":
		cardType="VISA Plus";
		break;
	case "A0000000039010":
		cardType="VISA Loyalty";
		break;
	case "A000000003999910":
		cardType="VISA Proprietary ATM";
		break;
		//MASTER CARD TYPES
	case "A0000000040000":
		cardType="MasterCard Card Manager";
		break;
	case "A00000000401":
		cardType="MasterCard PayPass";
		break;
	case "A0000000041010":
		cardType="MasterCard Credit";
		break;
	case "A00000000410101213":
		cardType="MasterCard";
		break;
	case "A00000000410101215":
		cardType="MasterCard";
		break;
	case "A000000004110101213":
		cardType="MasterCard";
		break;
	case "A0000000042010":
		cardType="MasterCard Specific";
		break;
	case "A0000000042203":
		cardType="MasterCard Specific";
		break;
	case "A0000000043010":
		cardType="MasterCard Specific";
		break;
	case "A0000000043060":
		cardType="Maestro (Debit)";
		break;
	case "A0000000044010":
		cardType="MasterCard Specific";
		break;
	case "A0000000045010":
		cardType="MasterCard Specific";
		break;
	case "A0000000045555":
		cardType="APDULogger";
		break;
	case "A0000000046000":
		cardType="Cirrus";
		break;
	case "A0000000048002":
		cardType="SecureCode Auth EMV-CAP";
		break;
	case "A0000000049999":
		cardType="MasterCard PayPass";
		break;
	case "A0000000050001":
		cardType="Maestro UK";
		break;
	case "A0000000050002":
		cardType="Solo 	UK";
		break;
	case "A0000000090001FF44FF1289":
		cardType="ETSI";
		break;
	case "A0000000101030":
		cardType="Maestro-CH";
		break;
	case "A00000001800":
		cardType="GEMPLUS";
		break;
	case "A0000000181001":
		cardType="GEMPLUS";
		break;
	case "A000000018434D":
		cardType="GEMPLUS";
		break;
	case "A000000018434D00":
		cardType="GEMPLUS";
		break;
	case "A00000002401":
		cardType="Midland Bank Plc";
		break;
		// AMERICAN EXPRESS CARD TYPES
	case "A000000025":
		cardType="American Express";
		break;
	case "A0000000250000":
		cardType="American Express";
		break;
	case "A00000002501":
		cardType="American Express";
		break;
	case "A000000025010104":
		cardType="American Express";
		break;
	case "A000000025010402":
		cardType="American Express";
		break;
	case "A000000025010701":
		cardType="American Express";
		break;
	case "A000000025010801":
		cardType="American Express";
		break;
	case "A0000000291010":
		cardType="American Express";
		break;
	case "A00000002945087510100000":
		cardType="CO-OP";
		break;
	default:
		cardType="Un Available";
		break;
	}
	logger.info("Input AID is  :"+aid+" "+"Output Card Type is  :"+cardType);
	return cardType;
}

public static void main(String arg[]){
	String [] a= {"A0000000031010","A0000000041010","A000000025010402","A000000025010801"};
	
	for (String aid :a){
	
	System.out.println(	 CardType.getCardType(aid));
	}
}

}
