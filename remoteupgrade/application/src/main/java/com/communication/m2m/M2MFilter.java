/**
 * 
 */
package com.communication.m2m;
import com.mina.TopiotService;
import com.mina.MinaSys;
import com.server.SpringBeanUtils;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @author xizhonghuai
 * @date 2018��7��24��
 * @readme
 */
public class M2MFilter extends IoFilterAdapter {

	private String toServiceId;

	/**
	 * @param toServiceId
	 */
	public M2MFilter(String toServiceId) {
		super();
		this.toServiceId = toServiceId;
	}

	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		nextFilter.messageReceived(session, message);

		try {
			if (message.toString().length() <= 19) {
				return;
			}

			if (!message.toString().subSequence(0, 3).equals("M2M")) {
				return;
			}

			String toMachineMsg = message.toString().substring(3);

			String regId = toMachineMsg.substring(0, 16).trim();
			String data = toMachineMsg.substring(16);

			TopiotService ts = (TopiotService) SpringBeanUtils.getBean(toServiceId);
			if (ts.isDebugflag()) {
				MinaSys.SendMsg(ts.getManagedSessions(), data, regId);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}
}
