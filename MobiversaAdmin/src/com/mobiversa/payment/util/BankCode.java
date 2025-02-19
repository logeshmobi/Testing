package com.mobiversa.payment.util;

public class BankCode {

	public static String getBankBicCode(String bankName) {
		if (bankName!=null) {
			String code = null;
			bankName = bankName.toUpperCase();
			System.out.println(" Name : Upper :: " + bankName);
			if (bankName.contains("AFFIN") || bankName.contains("AFFIN BANK") || bankName.contains("AFFIN BANK BERHAD")
					|| bankName.contains("ABB")) {
				code = "PHBMMYKL";
			} else if (bankName.contains("ALLIANCE BANK MALAYSIA BERHAD") || bankName.contains("ABMB")
					|| bankName.contains("ALLIANCE") || bankName.contains("ALLIANCE BANK")) {
				code = "MFBBMYKL";
			} else if (bankName.contains("AL-RAJHI") || bankName.contains("AL-RAJHI BANK") || bankName.contains("ARM")
					|| bankName.contains("ALRAJHI")) {
				code = "RJHIMYKL";
			} else if (bankName.contains("AMBANK MALAYSIA BERHAD") || bankName.contains("AMBANK BERHAD")
					|| bankName.contains("AMFB") || bankName.contains("AMBANK")) {
				code = "ARBKMYKL";
			} else if (bankName.contains("BANK ISLAM MALAYSIA BERHAD") || bankName.contains("BANK ISLAM BERHAD")
					|| bankName.contains("BIMB") || bankName.contains("BANK ISLAM")) {
				code = "BIMBMYKL";
			} else if (bankName.contains("BANK KERJASAMA RAKYAT MALAYSIA BERHAD") || bankName.contains("KERJASAMA")
					|| bankName.contains("KERJASAMA RAKYAT") || bankName.contains("BIMB")) {
				code = "BKRMMYKL";
			} else if (bankName.contains("BANK MUAMALAT MALAYSIA BHD") || bankName.contains("MUAMALAT")
					|| bankName.contains("BANK MUAMALAT") || bankName.contains("BMMB")) {
				code = "BMMBMYKL";
			} else if (bankName.contains("BANK OF AMERICA(M) BERHAD") || bankName.contains("BANK OF AMERICA")
					|| bankName.contains("BOA")) {
				code = "BOFAMY2X";
			} else if (bankName.contains("BANK OF CHINA(M) BERHAD") || bankName.contains("BANKOFCHINA")
					|| bankName.contains("BOCM") || bankName.contains("BANK OF CHINA")) {
				code = "BKCHMYKL";
			} else if (bankName.contains("AGRO") || bankName.contains("AGROBANK") || bankName.contains("BPM")) {
				code = "AGOBMYKL";
			} else if (bankName.contains("BANK SIMPANAN NASIONAL BERHAD") || bankName.contains("BANK SIMPANAN NASIONAL")
					|| bankName.contains("SIMPANAN NASIONAL") || bankName.contains("BSNB")) {
				code = "BSNAMYK1";
			} else if (bankName.contains("BANK OF TOKYO-MITSUBISHI UFJ (M) BERHAD")
					|| bankName.contains("BANK OF TOKYO-MITSUBISHI") || bankName.contains("BANK OF TOKYO-MITSUBISHI")
					|| bankName.contains("BOTM") || bankName.contains("TOKYO-MITSUBISHI UFJ")) {
				code = "BOTKMYKX";
			} else if (bankName.contains("BNP PARIBAS MALASYIA BERHAD") || bankName.contains("BNP PARIBAS")
					|| bankName.contains("BNP PARIBAS MALASYIA") || bankName.contains("BNP")
					|| bankName.contains("PARIBAS MALASYIA")) {
				code = "BNPAMYKL";
			} else if (bankName.contains("OCBC BANK (MALAYSIA) BERHAD") || bankName.contains("OCBC BANK BERHAD")
					|| bankName.contains("OCBC")) {
				code = "OCBCMYKL";
			} else if (bankName.contains("CIMB BANK BERHAD") || bankName.contains("CIMB") || bankName.contains("CIMB BANK")
					|| bankName.contains("BCBB")) {
				code = "CIBBMYKL";
			} else if (bankName.contains("CITIBANK BERHAD") || bankName.contains("CITIBANK") || bankName.contains("CITI")) {
				code = "CITIMYKL";
			} else if (bankName.contains("DEUTSCHE BANK BERHAD") || bankName.contains("DEUTSCHE BANK")
					|| bankName.contains("DEUTSCHE") || bankName.contains("DBB") || bankName.contains("DB")) {
				code = "DEUTMYKL";
			} else if (bankName.contains("HONG LEONG BANK BERHAD") || bankName.contains("HONG LEONG BANK")
					|| bankName.contains("HONG LEONG") || bankName.contains("HONG") || bankName.contains("HLB")) {
				code = "HLBBMYKL";
			} else if (bankName.contains("HSBC BANK MALAYSIA BERHAD") || bankName.contains("HSBC BANK BERHAD")
					|| bankName.contains("HSBC")) {
				code = "HBMBMYKL";
			} else if (bankName.contains("INDUSTRIAL AND COMMERCIAL BANK OF CHINA (MALAYSIA) BERHAD")
					|| bankName.contains("INDUSTRIAL AND COMMERCIAL BANK") || bankName.contains("INDUSTRIAL AND COMMERCIAL")
					|| bankName.contains("ICBK")) {
				code = "ICBKMYKL";
			} else if (bankName.contains("J P MORGAN CHASE BANK BERHAD") || bankName.contains("MORGAN")
					|| bankName.contains("J P MORGAN CHASE BANK") || bankName.contains("JPMCBB")) {
				code = "CHASMYKX";
			} else if (bankName.contains("KUWAIT FINANCE HOUSE (M) BERHAD")
					|| bankName.contains("KUWAIT FINANCE HOUSE BERHAD") || bankName.contains("KUWAIT FINANCE HOUSE")
					|| bankName.contains("KUWAIT") || bankName.contains("KFH")) {
				code = "KFHOMYKL";
			} else if (bankName.contains("MALAYAN BANKING BERHAD") || bankName.contains("MALAYAN BANK BERHAD")
					|| bankName.contains("MALAYAN") || bankName.contains("MAYBANK")) {
				code = "MBBEMYKL";
			} else if (bankName.contains("MIZUHO CORPORATE BANK (MALAYSIA) BERHAD")
					|| bankName.contains("MIZUHO CORPORATE BANK BERHAD") || bankName.contains("MIZUHO CORPORATE")
					|| bankName.contains("MIZUHO")) {
				code = "MHCBMYKA";
			} else if (bankName.contains("OCBC BANK (MALAYSIA) BERHAD") || bankName.contains("OCBC BANK BERHAD")
					|| bankName.contains("OCBC")) {
				code = "OCBCMYKL";
			} else if (bankName.contains("PUBLIC BANK BERHAD") || bankName.contains("PUBLIC BANK")
					|| bankName.contains("PUBLIC") || bankName.contains("PBB")) {
				code = "PBBEMYKL";
			} else if (bankName.contains("RHB BANK BERHAD") || bankName.contains("RHB BANK") || bankName.contains("RHB")) {
				code = "RHBBMYKL";
			} else if (bankName.contains("STANDARD CHARTERED BANK MALAYSIA BERHAD") || bankName.contains("CHARTERED")
					|| bankName.contains("STAN") || bankName.contains("SCB")) {
				code = "SCBLMYKX";
			} else if (bankName.contains("SUMITOMO MITSUI BANKING CORPORATION MALAYSIA BERHAD")
					|| bankName.contains("SUMITOMO MITSUI BANK BERHAD") || bankName.contains("SUMITOMO MITSUI")
					|| bankName.contains("SMBC")) {
				code = "SMBCMYKL";
			} else if (bankName.contains("UNITED OVERSEAS BANK (MALAYSIA) BERHAD")
					|| bankName.contains("UNITED OVERSEAS BANK BERHAD") || bankName.contains("UNITED OVERSEAS BANK")
					|| bankName.contains("UNITED") || bankName.contains("UOB")) {
				code = "UOVBMYKL";
			} else if (bankName.contains("MBSB BANK BERHAD") || bankName.contains("MBSB") || bankName.contains("MBSB BANK")
					|| bankName.contains("MBSB")) {
				code = "AFBQMYKL";
			} else if (bankName.contains("FINEXUS CARDS SDN.BHD") || bankName.contains("FINEXUS")
					|| bankName.contains("FINEXUS CARDS") || bankName.contains("FCSB")) {
				code = "FNXSMYNB";
			} else if (bankName.contains("TOUCH 'N GO EWALLET") || bankName.contains("TOUCHNGO") || bankName.contains("TNG")
					|| bankName.contains("TNGE")) {
				code = "TNGDMYNB";
			} else if (bankName.contains("CHINA CONSTRUCTION BANK(M) BERHAD")
					|| bankName.contains("CHINA CONSTRUCTION BANK") || bankName.contains("CCBB")
					|| bankName.contains("CHINA CONSTRUCTION")) {
				code = "PCBCMYKL";
			} else {
				code = "XXX";
			}
			return code;
		}else {
			return null;
		}
	}
}
