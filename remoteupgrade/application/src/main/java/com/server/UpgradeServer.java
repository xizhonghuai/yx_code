package com.server;

import com.mina.Methods;
import com.mina.TopiotService;
import com.communication.debug.DebugFilter;
import com.communication.m2m.M2MFilter;
import com.mina.CodecFactory;
import com.communication.push.PushFilter;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UpgradeServer
 * @Description: TODO
 * @Author xizhonghuai
 * @Date 2019/12/30
 * @Version V1.0
 **/
@Component
@ConfigurationProperties(prefix = "upgrade-server")
public class UpgradeServer implements TopiotService {

        private Logger logger = Logger.getLogger(UpgradeServer.class);
        private IoAcceptor ioAcceptor;
        private List<Integer> port;
        private int idle;
        private String decodecharset;
        private String encodecharset;
        private String handler;
        private String serviceId;
        private boolean status = false;
        private boolean debugflag;
        private boolean pushflag;
        private String pushUrl;
        private String toServiceId;

        /**
         * @return the ioAcceptor
         */
        public IoAcceptor getIoAcceptor() {
            return ioAcceptor;
        }

        /**
         * @param ioAcceptor
         *            the ioAcceptor to set
         */
        public void setIoAcceptor(IoAcceptor ioAcceptor) {
            this.ioAcceptor = ioAcceptor;
        }

        /**
         * @return the port
         */
        public List<Integer> getPort() {
            return port;
        }

        /**
         * @param port
         *            the port to set
         */
        public void setPort(List<Integer> port) {
            this.port = port;
        }

        /**
         * @return the idle
         */
        public int getIdle() {
            return idle;
        }

        /**
         * @param idle
         *            the idle to set
         */
        public void setIdle(int idle) {
            this.idle = idle;
        }

        /**
         * @return the decodecharset
         */
        public String getDecodecharset() {
            return decodecharset;
        }

        /**
         * @param decodecharset
         *            the decodecharset to set
         */
        public void setDecodecharset(String decodecharset) {
            this.decodecharset = decodecharset;
        }

        /**
         * @return the encodecharset
         */
        public String getEncodecharset() {
            return encodecharset;
        }

        /**
         * @param encodecharset
         *            the encodecharset to set
         */
        public void setEncodecharset(String encodecharset) {
            this.encodecharset = encodecharset;
        }

        /**
         * @return the handler
         */
        public String getHandler() {
            return handler;
        }

        /**
         * @param handler
         *            the handler to set
         */
        public void setHandler(String handler) {
            this.handler = handler;
        }

        /**
         * @return the serviceId
         */
        public String getServiceId() {
            return serviceId;
        }

        /**
         * @param serviceId
         *            the serviceId to set
         */
        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        /**
         * @return the debugflag
         */
        public boolean isDebugflag() {
            return debugflag;
        }

        /**
         * @param debugflag
         *            the debugflag to set
         */
        public void setDebugflag(boolean debugflag) {
            this.debugflag = debugflag;
        }

        /**
         * @return the pushflag
         */
        public boolean isPushflag() {
            return pushflag;
        }

        /**
         * @param pushflag
         *            the pushflag to set
         */
        public void setPushflag(boolean pushflag) {
            this.pushflag = pushflag;
        }

        /**
         * @return the pushUrl
         */
        public String getPushUrl() {
            return pushUrl;
        }

        /**
         * @param pushUrl
         *            the pushUrl to set
         */
        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        /**
         * @return the toServiceId
         */
        public String getToServiceId() {
            return toServiceId;
        }

        /**
         * @param toServiceId
         *            the toServiceId to set
         */
        public void setToServiceId(String toServiceId) {
            this.toServiceId = toServiceId;
        }

        /*
         * (non-Javadoc)
         *
         * @see com.application.TopiotService#getStatus()
         */
        @Override
        public boolean getStatus() {
            // TODO Auto-generated method stub
            return status;
        }

        /*
         * (non-Javadoc)
         *
         * @see com.application.TopiotService#start()
         */
        @PostConstruct
        @Override
        public boolean start() {
            // TODO Auto-generated method stub

            try {

                ioAcceptor = new NioSocketAcceptor();

                ioAcceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                        new CodecFactory(Charset.forName(decodecharset), Charset.forName(encodecharset))));

                if (debugflag) {
                    ioAcceptor.getFilterChain().addLast("debug", new DebugFilter(serviceId));
                }

                if (pushflag) {
                    ioAcceptor.getFilterChain().addLast("push", new PushFilter(serviceId));
                }

                if (toServiceId != null) {
                    ioAcceptor.getFilterChain().addLast("m2m", new M2MFilter(toServiceId));
                }
            /*设置IoProcessor每次读操作分配的buffer的最小容量，IoProcessor自动调用不能小于这个值*/
                ioAcceptor.getSessionConfig().setMaxReadBufferSize(2048);

            /*设置空闲状态IdleStatus（READER_IDLE, WRITER_IDLE 或 BOTH_IDLE）的空闲时间*/
                ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idle);

                try {

                    ioAcceptor.setHandler((IoHandler) Methods.getObjNewInstance(handler));

                } catch (Exception e) {
                    // TODO: handle exception
                    logger.info("绑定处理器异常" + e.getMessage());
                }


                for (int i = 0; i < port.size(); i++) {
                    ioAcceptor.bind(new InetSocketAddress(port.get(i).intValue()));
                }



                logger.info("tcp服务启动成功，监听端口:" + port.toString());
                status = true;
                return true;

            } catch (Exception e) {
                // TODO: handle exception

                logger.info("tcp服务启动失败:" + e.toString());
                status = false;
                return false;
            }

        }

        /*
         * (non-Javadoc)
         *
         * @see com.application.TopiotService#close()
         */
        @PreDestroy
        @Override
        public void close() {
            // TODO Auto-generated method stub

            ioAcceptor.dispose();
            ioAcceptor = null;
            status = false;
            logger.info("tcp服务关闭" + port.toString() + "");

        }

        /*
         * (non-Javadoc)
         *
         * @see com.application.TopiotService#getManagedSessions()
         */
        @Override
        public Map<Long, IoSession> getManagedSessions() {
            // TODO Auto-generated method stub
            return ioAcceptor.getManagedSessions();
        }

        /*
         * (non-Javadoc)
         *
         * @see com.mina.TopiotService#getSession()
         */
        @Override
        public IoSession getSession() {
            // TODO Auto-generated method stub
            return null;
        }

}
