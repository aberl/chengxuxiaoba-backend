package com.chengxuxiaoba.video.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class HttpServletRequestUtil {
	public static HttpServletRequest getHttpServletRequest() {
		try {
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			if (servletRequestAttributes == null)
				return null;

			HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
			return httpServletRequest;

		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T getInstance(String key, Class<T> clazz)
	{
		HttpServletRequest request=getHttpServletRequest();

		Object instanceObj = request.getAttribute(key);

		if(instanceObj == null)
			return null;

		T instance = (T)instanceObj;

		return instance;
	}
}
