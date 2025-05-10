package tn.esprit.services.event;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class ServiceTwilio {

    private static final String ACCOUNT_SID = "ACafb0ba077193789d6d5598c086a0f760";
    private static final String AUTH_TOKEN = "00ed0823615f624d9f22014bff5350bb";
    private static final String TWILIO_PHONE_NUMBER = "+12625928818";

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
