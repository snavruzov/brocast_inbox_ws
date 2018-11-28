package com.brocast.rest.inbox.ws.beans;

/**
 * BroCast.
 * Copyright: Sardor Navruzov
 * 2013-2016.
 */
public class BasicMessageBody {
    public String hash;
    public int msg_type;
    public String body;
    public String id_room;
    public String id_msg;
    public Integer duration;

    public BasicMessageBody() {
    }

    public BasicMessageBody(String hash, int msg_type, String body, String id_room, String id_msg, Integer duration) {
        this.hash = hash;
        this.msg_type = msg_type;
        this.body = body;
        this.id_room = id_room;
        this.id_msg = id_msg;
        this.duration = duration;
    }

    public String getId_msg() {
        return id_msg;
    }

    public void setId_msg(String id_msg) {
        this.id_msg = id_msg;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(int msg_type) {
        this.msg_type = msg_type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId_room() {
        return id_room;
    }

    public void setId_room(String id_room) {
        this.id_room = id_room;
    }

    @Override
    public String toString() {
        return "BasicMessageBody{" +
                "hash='" + hash + '\'' +
                ", msg_type=" + msg_type +
                ", body='" + body + '\'' +
                ", id_room='" + id_room + '\'' +
                '}';
    }
}
