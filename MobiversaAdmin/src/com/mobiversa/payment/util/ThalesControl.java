package com.mobiversa.payment.util;

import org.apache.log4j.Logger;

public class ThalesControl {
	private static Logger logger = Logger.getLogger(ThalesControl.class);
	
	private static SockTcpip thalesTcp=new SockTcpip();
	
	private static String thalesHeader = "1234";
	
	
	
	public String panDecrypt(String clearData) {
		logger.info("Pan Decryption Start");
		StringBuilder encryptCmd = new StringBuilder();
		String respCmd = null;
		String resp = null, bdkId = null, bdkKey = null;
		try {
			int clearDataGap = clearData.length() % 8;
//			System.out.println("Data Gap: "+clearDataGap);
			if(clearDataGap != 0)
			clearDataGap = 8 - clearDataGap;
			
			// logger.info("clearDataGap  :: "+clearDataGap);
			for (int i = 0; i < clearDataGap; i++) {
				// logger.info(i);
				clearData = clearData + "0";
			}

			// logger.info("ksn : " + ksn +
			// " encrypted data : "+clearData);
			if (clearData != null) {
				//bdkId = ksn.substring(0, 4);
				//bdkKey = "U1E6157299071FDA49893A6FEC08B0B25";//thalesDao.findBDK2(bdkId);
				//bdkKey = securityDao.getPanKey("DEK");
				//bdkKey = PropertyLoad.getFile().getProperty("DEK_DATA");
				bdkKey = PropertyLoad.getFile().getProperty("TOP_DATA")+PropertyLoad.getFile().getProperty("BOTTOM_DATA");
//				 logger.info("ENCRYPT DEK DATA : "+bdkKey);
				// logger.info(bdkKey);
				if (bdkKey != null) {
					encryptCmd.append(thalesHeader);
					encryptCmd.append("M2011200B");
					encryptCmd.append(bdkKey); // BDK Key
					//encryptCmd.append("906"); // KSN Descriptor
					//encryptCmd.append(ksn); // KSN
					encryptCmd.append("0000000000000000");
					String endMsgLenHex = Integer.toHexString(clearData
							.length());
					for (int i = endMsgLenHex.length(); i < 4; i++)
						endMsgLenHex = "0" + endMsgLenHex;
					// logger.info("length :"+clearData.length()+" : "+
					// endMsgLenHex +"  ::: " + clearData.length() % 8);
					encryptCmd.append(endMsgLenHex.toUpperCase()); // Encrypted
																	// msg len
					encryptCmd.append(clearData.toUpperCase()); // Encrypted msg
																// len
//					 logger.info("Encrypt CMD:"+encryptCmd.toString());

					respCmd = thalesTcp.WriteReadsock(encryptCmd.toString());
//					logger.info("Response from thales : "+respCmd);
					if (respCmd != null) {
						if (respCmd.substring(8, 10).equals("00")) {
							resp = respCmd.substring(30);
						} else {
							resp = null;
						}
						logger.info("Hsm Resp : "
								+ respCmd.substring(8, 10));
					}
				} else
					logger.info("BDK Key Not Found");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Pan Decryption End");
		return resp.replace("A", "");
	}	
}
