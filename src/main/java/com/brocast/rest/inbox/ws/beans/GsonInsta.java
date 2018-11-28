package com.brocast.rest.inbox.ws.beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by root on 7/2/15.
 */
public class GsonInsta {
    private static Gson instance = null;

    public static synchronized Gson getInstance() {
        if(instance == null) {
            instance = (new GsonBuilder()).serializeNulls().create();
        }

        return instance;
    }

    private GsonInsta() {
    }
}
