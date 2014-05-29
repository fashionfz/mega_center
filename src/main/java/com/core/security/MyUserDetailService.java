package com.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.po.SysRole;
import com.po.SysUser;


public class MyUserDetailService implements UserDetailsService {

	private SessionFactory sessionFactory;
	
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if(username==null)
			return null;
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Session session = sessionFactory.getCurrentSession();
		String hql="select r from SysRole r,RUserRole ur,SysUser u where u.id=ur.userId and r.id=ur.roleId and u.name=?";
		Query query = session.createQuery(hql);
		query.setParameter(0, username);
		@SuppressWarnings("unchecked")
		List<SysRole> list = query.list();
		for(SysRole r : list){
			SimpleGrantedAuthority auth = new SimpleGrantedAuthority(r.getName());
			auths.add(auth);
		}
		hql="select u from SysUser u where u.name=?";
		query = session.createQuery(hql);
		query.setParameter(0, username);
		SysUser user = (SysUser) query.uniqueResult();
		return new User(username, user.getPassword(), true, true, true, true, auths);
	}
	

}