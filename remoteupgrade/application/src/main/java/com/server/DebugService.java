/**
 *
 */
package com.server;

import com.communication.debug.TcpHandler;
import com.mina.TopiotService;
import com.mina.CodecFactory;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


/**
 * @author XIZHONGHUAI
 *
 */
@Component
@ConfigurationProperties(prefix = "debug-service")
public class DebugService implements TopiotService {

    private Logger logger = Logger.getLogger(DebugService.class);

    private IoAcceptor ioAcceptor = null;
    private List<Integer> port; // ???????


    /**
     * @return the ioAcceptor
     */
    public IoAcceptor getIoAcceptor() {
        return ioAcceptor;
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


    /*
     * (non-Javadoc)
     *
     * @see com.application.TopiotService#getStatus()
     */
    @Override
    public boolean getStatus() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.application.TopiotService#isTranspondflag()
     */
    @Override
    public boolean isDebugflag() {
        // TODO Auto-generated method stub
        return false;
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

        if (ioAcceptor != null) {
            return true;
        }

        try {

            ioAcceptor = new NioSocketAcceptor();


            ioAcceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
                    new CodecFactory(Charset.forName("ISO-8859-1"), Charset.forName("ISO-8859-1"))));

            ioAcceptor.getSessionConfig().setMaxReadBufferSize(2048);
            ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 3600);

            ioAcceptor.setHandler(new TcpHandler());


            for (int i = 0; i < port.size(); i++) {

                ioAcceptor.bind(new InetSocketAddress(port.get(i).intValue()));
            }

            logger.info("调试服务启动成功！");
            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info("调试服务启动异常:" + e.getMessage());
            return false;
        }

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
     * @see com.application.TopiotService#close()
     */
    @PreDestroy
    @Override
    public void close() {
        // TODO Auto-generated method stub

        ioAcceptor.dispose();
        ioAcceptor = null;

    }

    /* (non-Javadoc)
     * @see com.mina.TopiotService#isPushflag()
     */
    @Override
    public boolean isPushflag() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see com.mina.TopiotService#getPushUrl()
     */
    @Override
    public String getPushUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.mina.TopiotService#getSession()
     */
    @Override
    public IoSession getSession() {
        // TODO Auto-generated method stub
        return null;
    }

}
