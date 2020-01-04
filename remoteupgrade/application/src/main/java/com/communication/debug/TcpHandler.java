/**
 *
 */
package com.communication.debug;

import com.mina.Methods;
import com.mina.TopiotService;
import com.mina.MinaSys;
import com.server.SpringBeanUtils;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @author XIZHONGHUAI
 *
 */
public class TcpHandler extends IoHandlerAdapter {


	private Logger logger = Logger.getLogger(TcpHandler.class);


	public void messageReceived(IoSession session, Object message) throws Exception {
		// Empty handler
		try {


			if (message.toString().contains("$$") && message.toString().length()>2){


                TopiotService ts = (TopiotService) SpringBeanUtils.getBean("iot");
                if (ts.isDebugflag()) {

                    IoSession eqSSession = MinaSys.regIdToSession(ts.getManagedSessions(), "##");
                    if (eqSSession != null) {
                        eqSSession.write(Methods.encodeGBK(message.toString().substring(2)));
                    } else {
                        session.write("error:"  + "Off-line status of equipment");
                    }
                } else {
                    session.write("error:" + "iot " + "Unopened Debugging Service");
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

					MinaSys.SendMsg(ts.getManagedSessions(), Methods.encodeGBK(data));
					return;
				}

				IoSession eqSSession = MinaSys.regIdToSession(ts.getManagedSessions(), regId);
				if (eqSSession != null) {
					eqSSession.write(Methods.encodeGBK(data));
				} else {
					session.write("error:" + regId + "Off-line status of equipment");
				}
			} else {
				session.write("error:" + serviceId + " Unopened Debugging Service");
			}

		} catch (Exception e) {
			// TODO: handle exception

			session.write("error:" + e.toString());
			logger.info(e.toString());

		}

	}

	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		// Empty handler
	}

}
