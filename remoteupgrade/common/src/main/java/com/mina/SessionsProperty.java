/**
 * 
 */
package com.mina;

import java.util.Date;

/**
 *@author xizhonghuai
 *@date 2018��7��18��
 *@readme 
 */
public class SessionsProperty {
	
	private String regId;
	private String address;
	private Date activityTime;
	private Date registerTime;
	/**
	 * 
	 */
	public SessionsProperty() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param regId
	 * @param address
	 * @param activityTime
	 * @param registerTime
	 */
	public SessionsProperty(String regId, String address, Date activityTime, Date registerTime) {
		super();
		this.regId = regId;
		this.address = address;
		this.activityTime = activityTime;
		this.registerTime = registerTime;
	}
	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}
	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the activityTime
	 */
	public Date getActivityTime() {
		return activityTime;
	}
	/**
	 * @param activityTime the activityTime to set
	 */
	public void setActivityTime(Date activityTime) {
		this.activityTime = activityTime;
	}
	/**
	 * @return the registerTime
	 */
	public Date getRegisterTime() {
		return registerTime;
	}
	/**
	 * @param registerTime the registerTime to set
	 */
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SessionsProperty [regId=" + regId + ", address=" + address + ", activityTime=" + activityTime
				+ ", registerTime=" + registerTime + "]";
	}
	
	
	
	
	
	
	
	
	
	

}
