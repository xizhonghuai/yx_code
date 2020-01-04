package com.protocol;

import com.common.FileUntis;
import com.communication.debug.WsHandler;
import com.initialization.SystemArgsInit;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @ClassName RemoteUpgradeProcess
 * @Description: TODO
 * @Author xizhonghuai
 * @Date 2019/12/31
 * @Version V1.0
 **/
@Component
public class RemoteUpgradeProcess {

    @Autowired
    SystemArgsInit systemArgsInit;

    private Logger logger = Logger.getLogger(RemoteUpgradeProcess.class);

    public void controlMessage(IoSession session, String recMsg) throws Exception {

        char id = recMsg.charAt(0);
        if (id >= 1 && id <= 7) {

            session.write((char) 0x88);
        }


        if (id == 0x88) {
            if (systemArgsInit.getCurFirmware() != null) {
                LinkedList<String> firmware = FileUntis.readTxtFile(systemArgsInit.getCurFirmware());
                session.setAttribute("firmwareCache", firmware);
                session.setAttribute("firmwareSize", firmware.size());
                session.write(firmware.remove(0));
                WsHandler.sendMessage(session.getRemoteAddress()+"_"+(100-firmware.size()*100/((int)session.getAttribute("firmwareSize"))));
            } else {
                session.write("firmware is null");
                logger.info("firmware is null");
            }
        }

        if (id == 0x99) {
            LinkedList<String> firmware = (LinkedList<String>) session.getAttribute("firmwareCache", null);
            if (firmware != null && firmware.size() > 0) {
                session.write(firmware.remove(0));
                WsHandler.sendMessage(session.getRemoteAddress()+"_"+(100-firmware.size()*100/((int)session.getAttribute("firmwareSize"))));
            } else {
                session.write("firmware send done");
                logger.info("firmware send done");
            }


        }


    }
}
