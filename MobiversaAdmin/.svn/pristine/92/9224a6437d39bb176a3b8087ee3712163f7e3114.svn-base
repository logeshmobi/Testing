package com.mobiversa.payment.notification;

import org.apache.log4j.Logger;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.ApnsServiceBuilder;

public class IosPushNotifications {
	
	private static Logger logger = Logger.getLogger(IosPushNotifications.class.getName());
	
	public void sendNotification(String token,String count,String code,String name){
		
		logger.info("Sending an iOS push notification...");
		
		 //String token = "ab19037712682c87198c845a4fc833484acf456fe295fa2a110430905d3f9d8f";
	     String type = "dev";
	     String message = "Your Ezywire Promotion has been approved ";
	     
	     logger.info("The target token is "+token);

	        ApnsServiceBuilder serviceBuilder = APNS.newService();

	        if (type.equals("prod")) {
	        	logger.info("using prod API");
	            /*String certPath = PushNotifications.class.getResource("Certificates.p12").getPath();
	            serviceBuilder.withCert(certPath, "password")
	                    .withProductionDestination();*/
	        } else if (type.equals("dev")) {
	        	logger.info("using dev API");
	            //String certPath = PushNotifications.class.getResource("Certificates.p12").getPath();
	            //String certPath = "C:\\iOS_cert\\iosPushNotification.p12";
	            
	        	 String certPath = "C:\\iOS_cert\\PushNotification_Live.p12";
	            serviceBuilder.withCert(certPath, "Mobi_Ezywire_iOS")
	                    .withSandboxDestination();
	        } else {
	        	logger.info("unknown API type "+type);
	            return;
	        }

	        ApnsService service = serviceBuilder.build();


	        //Payload with custom fields
	        String payload = APNS.newPayload()
	                .alertBody(message+code+" : "+name)
	                .alertTitle("Promotion Approved")
	                .badge(Integer.parseInt(count))
	                .sound("default")
	                .customField("custom", "Chumma")
	                .build();

	      
	        logger.info("payload: "+payload);
	        service.push(token, payload);

	        logger.info("The message has been hopefully sent...");
		
	}

}
