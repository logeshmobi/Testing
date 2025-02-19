package com.mobiversa.payment.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ShutdownHandler shutdownHandler;
   
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
        this.shutdownHandler.onShutdown();		
	}
}
