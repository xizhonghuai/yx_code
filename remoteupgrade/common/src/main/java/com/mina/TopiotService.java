/**
 * 
 */
package com.mina;

import org.apache.mina.core.session.IoSession;

import java.util.Map;

/**
 * @author XIZHONGHUAI
 *
 */
public interface TopiotService {

	boolean getStatus();
	
	boolean isDebugflag();

	boolean start();	
	
	boolean isPushflag();
	
	IoSession getSession();
	
	String getPushUrl();
    
	Map<Long, IoSession> getManagedSessions();

	void close();

}
