/**
 * 
 */
package com.mina;

import org.apache.mina.core.session.IoSession;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author XIZHONGHUAI
 *
 */
public class MinaSys {

	/**
	 * @param session
	 * @param msg
	 */
	public static void SendMsg(IoSession session, String msg) {
		if (session != null) {
			session.write(msg);
		}
	}

	/**
	 * @param regId
	 * @return
	 */
	public static IoSession regIdToSession(Map<Long, IoSession> mapList, String regId) {
		IoSession session;
		for (Entry<Long, IoSession> entry : mapList.entrySet()) {
			session = entry.getValue();
			if (regId.equals(session.getAttribute("regId", null))) {
				return session;
			}

		}
		return null;
	}

	/**
	 * @param msg
	 */
	public static void SendMsg(Map<Long, IoSession> mapList, String msg) {

		IoSession session;
		for (Entry<Long, IoSession> entry : mapList.entrySet()) {
			session = entry.getValue();

			SendMsg(session, msg);
		}

	}

	public static void SendMsg(Map<Long, IoSession> mapList, String msg, String regId) {
		IoSession session;
		session = regIdToSession(mapList, regId);
		SendMsg(session, msg);
	}

}
