package com.listener;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtil {

	private static ApplicationContext context;

	public static ApplicationContext getContext()
	{
		ServletContext servletContext = MyListener.getContext();
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return context;
	}

	public static void setContext(ApplicationContext context)
	{
		SpringUtil.context = context;
	}
	
	public static Object getBean(String beanName){
		return SpringUtil.getContext().getBean(beanName);		
	}
}
