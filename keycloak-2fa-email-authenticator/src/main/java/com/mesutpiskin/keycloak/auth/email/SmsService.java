package com.mesutpiskin.keycloak.auth.email;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsService {
    private static final String ACCOUNT_SID=System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN=System.getenv("TWILIO_AUTH_TOKEN");
    private static final String TWILIO_NUMBER=System.getenv("TWILIO_PHONE_NUMBER");

    public static void sendSmsOtp(String toPhone, String messageText) {
        if (ACCOUNT_SID == null || AUTH_TOKEN == null || TWILIO_NUMBER == null) {
            throw new IllegalStateException("Missing Twilio configuration from environment variables.");
        }

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(toPhone),
                new PhoneNumber(TWILIO_NUMBER),
                messageText
        ).create();
        System.out.println("otp sent: " + message);
    }
}
