package com.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class MyInvocationSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public MyInvocationSecurityMetadataSource() {
		loadResourceDefine();
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (pathMatchesUrl(resURL, url)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Cacheable(value = "andCache", key = "role_menu")
	private void loadResourceDefine() {
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		SessionFactory sessionFactory = (SessionFactory) context
				.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		String sql = "";
		sql="select URL from T_SYS_MENU";
		@SuppressWarnings("unchecked")
		List<String> urls = session.createSQLQuery(sql).list();
		for (String url : urls) {
		    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			sql = "select r.name from T_SYS_MENU m,R_ROLE_MENU rm,T_SYS_ROLE r where m.id=rm.menu_id and r.id=rm.role_id and m.url='"
			+ url + "'";
			@SuppressWarnings("unchecked")
			List<String> roles = session.createSQLQuery(sql).list();
			for (String role : roles) {
				ConfigAttribute ca = new SecurityConfig(role);
				atts.add(ca);
			}
			resourceMap.put(url, atts);
		}
		
		

		session.close();
	}

	public boolean pathMatchesUrl(String resUrl, String url) {
		if (resUrl.equals(url)||url.equals("/"+resUrl))
			return true;
		else
			return false;
	}

    public static Map<String, Collection<ConfigAttribute>> getResourceMap() {
        return resourceMap;
    }

    public static void setResourceMap(
            Map<String, Collection<ConfigAttribute>> resourceMap) {
        MyInvocationSecurityMetadataSource.resourceMap = resourceMap;
    }
	
	
}
