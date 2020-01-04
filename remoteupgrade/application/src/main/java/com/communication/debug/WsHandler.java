/**
 *
 */
package com.communication.debug;

import com.mina.Methods;
import com.mina.TopiotService;
import com.mina.MinaSys;
import com.server.SpringBeanUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XIZHONGHUAI
 */
@Component
@ServerEndpoint("/ws")
public class WsHandler {

    private Logger logger = Logger.getLogger(WsHandler.class);

    public static List<Session> websocketSession = new ArrayList<Session>();

    private Session session;

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {

        try {

            if (message.toString().contains("$$") && message.toString().length()>2) {


                TopiotService ts = (TopiotService) SpringBeanUtils.getBean("iot");
                if (ts.isDebugflag()) {
                    IoSession eqSSession = MinaSys.regIdToSession(ts.getManagedSessions(), "##");
                    if (eqSSession != null) {
                        eqSSession.write(Methods.encodeGBK(message.toString().substring(2)));
                    } else {
                       session.getBasicRemote().sendText("error:" + "Off-line status of equipment");
                    }
                } else {
                    session.getBasicRemote().sendText("error:" + "iot " + "Unopened Debugging Service");
                }

                return;
            }


            if (message.toString().length() <= 32) {
                  return;
            }

            String header = message.toString().substring(0, 32);
            String serviceId = header.substring(0, 16).trim();
            String regId = header.substring(16).trim();
            String data = message.toString().substring(32);

            TopiotService ts = (TopiotService) SpringBeanUtils.getBean(serviceId);
            if (ts.isDebugflag()) {

                if ("####".equals(regId)){

                    MinaSys.SendMsg(ts.getManagedSessions(),Methods.encodeGBK(data));
                    return;
                }

                IoSession eqSSession = MinaSys.regIdToSession(ts.getManagedSessions(), regId);
                if (eqSSession != null) {
                    eqSSession.write(Methods.encodeGBK(data));
                } else {
                    session.getBasicRemote().sendText("error:" + regId + "Off-line status of equipment");
                }
            } else {
                session.getBasicRemote().sendText("error:" + serviceId + " Unopened Debugging Service");
            }

        } catch (Exception e) {
            // TODO: handle exception
            session.getBasicRemote().sendText("error:" + e.toString());
            logger.info(e.toString());

        }

    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websocketSession.add(session);
    }

    @OnClose
    public void onClose() {
        websocketSession.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {

        error.printStackTrace();
    }

    /**
     * Ⱥ����Ϣ���������
     *
     * @param message
     * @throws IOException
     */
    public static void sendMessage(String message) throws IOException {
        // Ⱥ����Ϣ
        for (int i = 0; i < websocketSession.size(); i++) {
            websocketSession.get(i).getBasicRemote().sendText(message);

        }
    }

}
