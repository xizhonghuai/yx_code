/**
 * 
 */
package com.communication.push;

import com.mina.Methods;
import com.mina.TopiotService;
import com.server.SpringBeanUtils;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;


/**
 * @author xizhonghuai
 * @date 2018��7��23��
 * @readme
 */
public class PushFilter extends IoFilterAdapter {

	private String serviceId;

	/**
	 * @param serviceId
	 */
	public PushFilter(String serviceId) {
		super();
		this.serviceId = serviceId;
	}

	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		nextFilter.messageReceived(session, message);

		String msg = "";

		String regId = (String) session.getAttribute("regId", null);

		if (regId == null) {
			regId = "no regId";
		}

		msg = Methods.putSpace(serviceId) + Methods.putSpace(regId) + message.toString();

		TopiotService ts = (TopiotService) SpringBeanUtils.getBean(serviceId);

		new PushService(ts.getPushUrl(), msg).start();

	}
}