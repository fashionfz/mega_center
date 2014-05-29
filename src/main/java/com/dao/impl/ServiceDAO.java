package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.IServiceDAO;
import com.po.SysService;

@Repository
public class ServiceDAO implements IServiceDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public SysService getByCode(String code) {
        Session session = sessionFactory.getCurrentSession();
        String hql="from SysService s where s.code=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, code);
        List<SysService> list = query.list();
        if(list!=null&&list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public List<SysService> queryAll(int page, int rows) {
        Session session = sessionFactory.getCurrentSession();
        String hql="from SysService s";
        Query query = session.createQuery(hql);
        query.setFirstResult((page-1)*rows);
        query.setMaxResults(rows);
        return query.list();
    }

    @Override
    public int create(SysService service) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(service);
        return 0;
    }

    @Override
    public int update(SysService service) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(service);
        return 0;
    }

    @Override
    public long getAllCount() {
        Session session = sessionFactory.getCurrentSession();
        String hql="select count(s.id) from SysService s";
        Query query = session.createQuery(hql);
        return (Long) query.uniqueResult();
    }

    @Override
    public int deleteUser(String serviceId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from SysService s where s.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, serviceId);
        return query.executeUpdate();
    }

}
