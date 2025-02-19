package com.mobiversa.payment.connect;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.mobiversa.common.bo.Merchant;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Logger logger = Logger.getLogger(Message.class.getName());
	//private Map<String,String> data;
	private String service;
	private String username;
	private String deviceId;
	private String password;
	private String sessionId;
	private String hostType;
	private String merchantId;
	private String tid;
	
	/*private List<String> registration_ids;
    
    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new LinkedList<String>();
        registration_ids.add(regId);
    }*/
	
	
	public void createData(String name, String device,String pass, Merchant merchant,String tid1){
        /*if(data == null)
            data = new HashMap<String,String>();
*/
        //service= "WEB_SETTLEMENT";
		
		/*logger.info( "merchant:" + ":"+ merchant.getId());
        service= "SETTLEMENT";
        sessionId="0973CE443D95B8846E9E0FC44805A036";
        deviceId= device;
        username=name;
        password=pass;
        hostType = "P";
        merchantId = merchant.getId().toString();*/
		String merchantType;
		if((merchant.getMerchantType() == null) || (merchant.getMerchantType()=="P")) {
			merchantType="P";
		}else if(merchant.getMerchantType() == "FIUU"){
			merchantType = "FIUU";
		}else {
			merchantType="U";
		}
        
        logger.info( "merchant:" + ":"+ merchant.getId());
        service= "SETTLEMENT";
        sessionId="0973CE443D95B8846E9E0FC44805A036";
        tid= tid1;
        hostType = merchantType;
        merchantId = merchant.getId().toString();
        
        
        
        //return data;
    }


}
