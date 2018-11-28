package com.brocast.rest.inbox.ws.runtime;

import com.brocast.rest.inbox.ws.beans.InMessage;
import com.brocast.rest.inbox.ws.service.SendWSMessage;
import com.brocast.riak.api.beans.DcMediaEntity;
import com.dgtz.mcache.api.factory.Constants;
import com.dgtz.mcache.api.factory.RMemoryAPI;
import com.dgtz.mcache.api.utils.MD5;
import org.atmosphere.annotation.Broadcast;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.config.service.AtmosphereService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.JerseyBroadcaster;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Sardor Navruzov on 7/2/15.
 * Copyrights Digitizen Co.
 */
@Path("/{id}")

@AtmosphereService(broadcaster = JerseyBroadcaster.class)
public class WSResource {
    private static final Logger logger = LoggerFactory.getLogger(WSResource.class);

    @Context
    private HttpServletRequest request;

    @PathParam("id")
    private Broadcaster broadcaster;

    public WSResource() {
    }

    @Suspend(listeners = {EventLogger.class})
    @GET
    public String suspend(@PathParam("id") String idInbox) {
        return "";
    }

    @Broadcast(writeEntity = false)
    @POST
    @Produces({"application/json;charset=UTF-8"})
    public void broadCast(InMessage message) {

        logger.debug("Broadcast idhash {}", message.hash);
        this.broadcast(message);
    }

    /*{"is_deleted":false,"":null}*/
    protected void broadcast(InMessage m) {
        JSONObject om = new JSONObject();

        try {
            String idUser = RMemoryAPI.getInstance().pullElemFromMemory(Constants.USER_HASH + m.hash);
            if (idUser != null) {
                Boolean userlist = RMemoryAPI.getInstance()
                        .pullIfSetElem(Constants.USER_KEY + "room:users:" + m.id_room, idUser);
                if (userlist) {

                    switch (m.msg_type){
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:{
                            if (m.id_msg == null || m.id_msg.isEmpty()) {
                                m.id_msg = MD5.hash(System.currentTimeMillis() + "mid");
                            }

                            String msg = new String(m.body.getBytes("UTF-8"));
                            String vurl = "";
                            String author_name = "";
                            String method = "";
                            String thumb = "";
                            long idAuthor = 0;
                            if (m.msg_type == 5 || m.msg_type == 4) {
                                vurl = Constants.STATIC_URL + m.url;
                            } else if (m.msg_type == 3) {
                                vurl = Constants.VIDEO_URL + m.url;
                            } else if (m.msg_type == 2) {
                                DcMediaEntity media = RMemoryAPI.getInstance()
                                        .pullHashFromMemory(Constants.MEDIA_KEY + m.idMedia, "detail", DcMediaEntity.class);
                                if (media != null) {
                                    author_name = RMemoryAPI.getInstance().pullHashFromMemory(Constants.USER_KEY + media.idUser, "username");
                                    idAuthor = media.idUser;
                                    method = media.method;
                                    thumb = Constants.encryptAmazonURL(media.getIdUser(), media.getIdMedia(), "jpg", "thumb", Constants.STATIC_URL);
                                }
                            }

                            String username = RMemoryAPI.getInstance()
                                    .pullHashFromMemory(Constants.USER_KEY + idUser, "username");

                            String aval = RMemoryAPI.getInstance()
                                    .pullHashFromMemory(Constants.USER_KEY + idUser, "avatar");
                            String avatar = Constants.STATIC_URL + idUser + "/image" + aval + "M.jpg";
                            om.put("avatar", avatar);
                            om.put("dateadded", System.currentTimeMillis());
                            om.put("username", username);
                            om.put("body", msg);
                            om.put("url", vurl);
                            om.put("duration", m.duration);
                            om.put("id_msg", m.id_msg);
                            om.put("msg_type", m.msg_type);
                            om.put("is_read", false);
                            om.put("id_room", m.id_room);
                            om.put("is_deleted", false);
                            om.put("idMedia", m.idMedia);
                            om.put("idUser", m.idUser);
                            om.put("idChannel", m.idChannel);
                            om.put("thumb", thumb);
                            om.put("status", "valid");
                            om.put("author_name", author_name);
                            om.put("idAuthor", idAuthor);
                            om.put("method", method);

                            break;
                        }
                        case 9:{
                            List<String> ids = RMemoryAPI.getInstance()
                                    .pullListElemFromMemory(Constants.USER_KEY + "inbox:new:" + idUser+"_"+m.id_room, 0, -1);
                            RMemoryAPI.getInstance().delFromMemory(Constants.USER_KEY + "inbox:new:" + idUser+"_"+m.id_room);
                            om.put("id_room", m.id_room);
                            om.put("is_read", true);
                            om.put("id_msgs", ids);
                            break;
                        }
                    }

                    //SendWSMessage.sendToInboxAPI(m);
                }
            }


        } catch (Exception e) {
            logger.error("Error in WS", e);
        }

        logger.debug("RESPONSE JSON FORMAT {}", om.toString());

        broadcaster.broadcast(om.toString());
    }

    public static final class EventLogger extends AtmosphereResourceEventListenerAdapter {
        private final Logger logger = LoggerFactory.getLogger(EventLogger.class);

        public EventLogger() {
        }

        @Override
        public void onClose(AtmosphereResourceEvent event) {
            this.logger.info("ON_CLOSE called for {} ", event.getResource().uuid());
        }

        @Override
        public void onSuspend(AtmosphereResourceEvent event) {
            AtmosphereResource r = event.getResource();
            r.getResponse().setCharacterEncoding("UTF-8");
            try {
                r.getRequest().setCharacterEncoding("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            event.broadcaster().addAtmosphereResource(r);

            if (r.transport() == AtmosphereResource.TRANSPORT.LONG_POLLING) {
                logger.debug("TRANSPORT: LONG-POLLING");
                event.getResource().resumeOnBroadcast(true).suspend();
            } else if (r.transport() == AtmosphereResource.TRANSPORT.WEBSOCKET) {
                logger.debug("Path value {}", event.broadcaster().getID());
            }

        }

        @Override
        public void onDisconnect(AtmosphereResourceEvent event) {
            {
                this.logger.info("Socket {} closed", event.getResource().uuid());
            }
        }
    }
}
