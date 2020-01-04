/**
 * 
 */
package com.server;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * @author XIZHONGHUAI
 *
 */
@Component("springBeanUtils")
public class SpringBeanUtils implements BeanFactoryAware {

	private static BeanFactory beanFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.
	 * springframework.beans.factory.BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		// TODO Auto-generated method stub
		SpringBeanUtils.beanFactory = beanFactory;
	}

	/**
	 * ����bean�����ƻ�ȡ��Ӧ���͵Ķ���
	 *
	 * @param id
	 * 
	 * @return Object���͵Ķ���
	 */
	public static Object getBean(String id) {
		return beanFactory.getBean(id);
	}

}
