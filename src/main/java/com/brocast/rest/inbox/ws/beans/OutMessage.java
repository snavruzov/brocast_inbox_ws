package com.brocast.rest.inbox.ws.beans;

import com.google.gson.Gson;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by root on 7/2/15.
 */
@XmlRootElement
public class OutMessage {
    public long likeCnt;
    public long viewCnt;
    public String dateadded;
    public String username;
    public String avatar;
    public String tkey;
    public Long idUser;
    public String text;
    public Long idMedia;
    public int wsType;

    public OutMessage() {
    }

    public OutMessage(long likeCnt, long viewCnt, String dateadded, String username, String avatar, String tkey, Long idUser, String text, Long idMedia, int wsType) {
        this.likeCnt = likeCnt;
        this.viewCnt = viewCnt;
        this.dateadded = dateadded;
        this.username = username;
        this.avatar = avatar;
        this.tkey = tkey;
        this.idUser = idUser;
        this.text = text;
        this.idMedia = idMedia;
        this.wsType = wsType;
    }

    public int getWsType() {
        return this.wsType;
    }

    public void setWsType(int wsType) {
        this.wsType = wsType;
    }

    public long getLikeCnt() {
        return this.likeCnt;
    }

    public void setLikeCnt(long likeCnt) {
        this.likeCnt = likeCnt;
    }

    public long getViewCnt() {
        return this.viewCnt;
    }

    public void setViewCnt(long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public String getDateadded() {
        return this.dateadded;
    }

    public void setDateadded(String dateadded) {
        this.dateadded = dateadded;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTkey() {
        return this.tkey;
    }

    public void setTkey(String tkey) {
        this.tkey = tkey;
    }

    public Long getIdUser() {
        return this.idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getIdMedia() {
        return this.idMedia;
    }

    public void setIdMedia(Long idMedia) {
        this.idMedia = idMedia;
    }

    public String toString() {
        Gson gson = GsonInsta.getInstance();
        return gson.toJson(this);
    }
}
