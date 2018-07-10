package com.qqlei.cloud.auth.security.integration.authenticator.sms;

import org.springframework.context.ApplicationEvent;


public class SmsAuthenticateBeforeEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SmsAuthenticateBeforeEvent(Object source) {
        super(source);
        System.out.println("SmsAuthenticateBeforeEvent");
    }
}
