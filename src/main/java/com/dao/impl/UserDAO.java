package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.IUserDAO;
import com.po.RUserRole;
import com.po.SysUser;

@Repository
public class UserDAO implements IUserDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SysUser> queryAll(int page,int rows) {
        Session session = sessionFactory.getCurrentSession();
        String hql="from SysUser";
        Query query = session.createQuery(hql);
        if(page>0&&rows>0){
            query.setFirstResult((page-1)*rows);
            query.setMaxResults(rows);
        }
        return query.list();
    }

    @Override
    public void create(SysUser user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public long getAllCount() {
        Session session = sessionFactory.getCurrentSession();
        String hql="select count(d.id) from SysUser d";
        Query query = session.createQuery(hql);
        return (Long) query.uniqueResult();
    }
    @Override
    public void saveUserAnth(RUserRole rur){
        Session session = sessionFactory.getCurrentSession();
        session.persist(rur);
    }

    @Override
    public void delUserAnth(String userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from RUserRole r where r.userId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, userId);
        query.executeUpdate();
        
    }

    @Override
    public int deleteUser(String userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from SysUser u where u.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, userId);
        return query.executeUpdate();
    }

    @Override
    public void updateUser(SysUser user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }

    @Override
    public SysUser findById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (SysUser) session.load(SysUser.class, id);
    }

}
