package tn.esprit.services.event;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class ServiceTwilio {

    private static final String ACCOUNT_SID = "ACdc65e140415ce4a2d89d3df0708ef0cc";
    private static final String AUTH_TOKEN = "f1e582d63154d007d67eee55f05ac5df";
    private static final String TWILIO_PHONE_NUMBER = "+14178553121";

    public void sendSMS(String recipientNumber, String messageSent) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                messageSent
        ).create();

        System.out.println("Message envoyé à : " + recipientNumber);
    }
}
