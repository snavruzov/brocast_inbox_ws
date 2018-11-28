package com.brocast.rest.inbox.ws.service;

import com.brocast.rest.inbox.ws.beans.BasicMessageBody;
import com.brocast.rest.inbox.ws.beans.InMessage;
import com.dgtz.mcache.api.factory.Constants;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;


/**
 * Created by root on 7/2/15.
 */
public final class SendWSMessage {
    private static final Logger log = LoggerFactory.getLogger(SendWSMessage.class);
    private static final String PUB_MSG = "room/publish";

    public SendWSMessage() {
    }

    public  static void sendToInboxAPI(InMessage m) throws Exception {

        BasicMessageBody messageBody = new BasicMessageBody(m.hash, m.msg_type, m.body, m.id_room, m.id_msg, m.duration);

        Future<HttpResponse<JsonNode>> rStatus = Unirest.post(Constants.INBOX_URL + PUB_MSG)
                .header("Host", "inbox.api.brocast.com")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(messageBody)
                .asJsonAsync(new Callback<JsonNode>() {
                    public void failed(UnirestException e) {
                        log.debug("The request has failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        JSONObject code = response.getBody().getObject();
                        log.debug("Completed {}", code.toString());
                    }

                    public void cancelled() {
                        log.debug("The request has been cancelled");
                    }

                });
    }
}

