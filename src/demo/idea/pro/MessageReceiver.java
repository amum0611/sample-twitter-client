package demo.idea.pro;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class MessageReceiver implements MoSmsListener {

    @Override
    public void init() {
        //use this for initialization purposes
    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {

        System.out.println(moSmsReq);
        MtSmsReq mtSmsReq = createResponse(moSmsReq);

        try {
            SmsRequestSender requestSender = new SmsRequestSender(new URL("http://localhost:7000/sms/send"));
            requestSender.sendSmsRequest(mtSmsReq);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SdpException e) {
            e.printStackTrace();
        }

    }

    private MtSmsReq createResponse (MoSmsReq moSmsReq) {

        String hashTag = moSmsReq.getMessage().split("twit ")[1];
        String twit;

        TwitterClient client = new TwitterClient();
        try {
            twit = client.search(hashTag);
        } catch (IOException e) {
            twit = "An error occurred. Please try again later";
        }

        MtSmsReq mtSmsReq = new MtSmsReq();
        mtSmsReq.setApplicationId("APP_ID");
        mtSmsReq.setPassword("#$%^$%");
        mtSmsReq.setMessage(twit);
        mtSmsReq.setDestinationAddresses(Arrays.asList(moSmsReq.getSourceAddress()));

        return mtSmsReq;

    }
}
