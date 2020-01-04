/**
 * 
 */
package com.communication.debug;

import com.mina.Methods;
import com.mina.TopiotService;
import com.mina.MinaSys;
import com.server.SpringBeanUtils;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;


/**
 * @author XIZHONGHUAI
 *
 */
public class DebugFilter extends IoFilterAdapter {

	private String serviceId;

	/**
	 * @param serviceId
	 */
	public DebugFilter(String serviceId) {
		super();
		this.serviceId = serviceId;
	}

	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {


		nextFilter.messageReceived(session, message);

		String msg = "";
		
		String regId = (String) session.getAttribute("regId", null);
		
		if (regId == null){
			regId = "no regId";
		}


		if (regId == "##"){

            msg = message.toString();

            TopiotService ts = (TopiotService) SpringBeanUtils.getBean("debugService");

            MinaSys.SendMsg(ts.getManagedSessions(), msg); // ת����tcp�ͻ���

            WsHandler.sendMessage(msg);// ת����web

			return;
        }

		msg = Methods.putSpace(serviceId) + Methods.putSpace(regId) + message.toString();

		TopiotService ts = (TopiotService) SpringBeanUtils.getBean("debugService");

		MinaSys.SendMsg(ts.getManagedSessions(), msg); // ת����tcp�ͻ���

		WsHandler.sendMessage(msg);// web

	}
}
