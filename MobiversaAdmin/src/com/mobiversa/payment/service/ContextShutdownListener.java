package com.mobiversa.payment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextShutdownListener implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private ShutdownHandler shutdownHandler;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        this.shutdownHandler.onShutdown();
    }
}


