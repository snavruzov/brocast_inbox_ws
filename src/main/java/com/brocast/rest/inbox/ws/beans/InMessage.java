package com.brocast.rest.inbox.ws.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Sardor Navruzov on 7/2/15.
 * Copyrights Digitizen Co.
 */
@XmlRootElement
public class InMessage {
    public String hash;
    public int msg_type; //1-text, 2-share, 3-video, 4-picture, 5-voice, 6-system, 7-channel, 8-debate, 9-is_read
    public String body;
    public String id_room;
    public String id_msg;
    public Integer duration;
    public String url;
    public Long idMedia;
    public Long idChannel;
    public Long idUser;
    public String idInbox;

    public InMessage() {
    }

    public InMessage(String hash, int msg_type, String body, String id_room, String id_msg, Integer duration, String url) {
        this.hash = hash;
        this.msg_type = msg_type;
        this.body = body;
        this.id_room = id_room;
        this.id_msg = id_msg;
        this.duration = duration;
        this.url = url;
    }
}

