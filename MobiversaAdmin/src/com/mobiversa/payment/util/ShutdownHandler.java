package com.mobiversa.payment.util;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobiversa.payment.service.TransactionService;

@Component
public class ShutdownHandler {
	
	@Autowired
    private TransactionService excelService;

	private static final Logger logger = Logger.getLogger(ShutdownHandler.class);


    @PreDestroy
    public void onShutdown() {
    	logger.info("Application is shutting down... Saving data to Excel."); //$NON-NLS-1$
        if (this.excelService != null) {
            this.excelService.saveIpnTriggerData();
        } else {
        	logger.error("ExcelService is null. Data not saved."); //$NON-NLS-1$
        }
    }
}
