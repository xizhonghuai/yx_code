package com.protocol;

import com.mina.Methods;
import com.server.SpringBeanUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @ClassName RemoteUpgradeHandler
 * @Description: TODO
 * @Author xizhonghuai
 * @Date 2019/12/31
 * @Version V1.0
 **/
public class RemoteUpgradeHandler extends IoHandlerAdapter {
    private Logger logger = Logger.getLogger(RemoteUpgradeHandler.class);

    public void messageReceived(IoSession session, Object message) {
        String recMsg = message.toString();
        logger.info("收到报文:" + Methods.toHexString(recMsg));


        try {
            RemoteUpgradeProcess process = (RemoteUpgradeProcess) (SpringBeanUtils.getBean("remoteUpgradeProcess"));
            process.controlMessage(session, recMsg);
        } catch (Exception e) {
            logger.info(e.toString());
        }


//        if ((recMsg.charAt(0) == 0x28) && (recMsg.charAt(recMsg.length() - 1) == 0x29)) {
//            try {
//                RemoteUpgradeProcess process = (RemoteUpgradeProcess) (SpringBeanUtils.getBean("remoteUpgradeProcess"));
//                process.controlMessage(session, recMsg);
//            } catch (Exception e) {
//                logger.info(e.toString());
//            }
//        }


    }


    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        // Empty handler

        if (session.getAttribute("regId", null) != null) {

            logger.info("设备" + session.getAttribute("regId", null).toString() + session.getRemoteAddress()
                    + "---------长时间无心跳信息，服务端释放连接");
        }

        session.close(true);
    }


    public void sessionOpened(IoSession session) {
        // Empty handler
        logger.info(session.getRemoteAddress() + "连接");
    }

    public void sessionClosed(IoSession session) {
        if (session.getAttribute("regId", null) != null) {
            logger.info(
                    "设备" + session.getAttribute("regId", null).toString() + session.getRemoteAddress() + "---------注销");
        }
        logger.info(session.getRemoteAddress() + "断开");
    }

}