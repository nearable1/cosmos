package com.inbody.crm.common.utils;

import java.lang.reflect.InvocationTargetException;

public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {

	public void copy(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException{
		org.apache.commons.beanutils.BeanUtils.copyProperties(dest, orig);
	}
	
}
