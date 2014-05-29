package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.dao.ICheckConfigDAO;
import com.po.CheckConfig;
@Repository
public class CheckConfigDAO implements ICheckConfigDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Override
    public void create(CheckConfig config) {
        Session session =sessionFactory.getCurrentSession();
        session.persist(config);
    }

    @Override
    public int update(CheckConfig config) {
        Session session =sessionFactory.getCurrentSession();
        String hql="update CheckConfig c set c.itemValue=? where c.itemCode=? and c.itemValue <> ?";
        Query query = session.createQuery(hql);
        query.setParameter(0, config.getItemValue());
        query.setParameter(1, config.getItemCode());
        query.setParameter(2, config.getItemValue());
        return query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CheckConfig> queryAll() {
        Session session =sessionFactory.getCurrentSession();
        String hql="from CheckConfig";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public long checkExits(CheckConfig config) {
        Session session =sessionFactory.getCurrentSession();
        String hql="select count(c.id) from CheckConfig c where c.itemCode=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, config.getItemCode());
        return (Long) query.uniqueResult();
    }

    @Override
    public CheckConfig query(String code) {
        Session session =sessionFactory.getCurrentSession();
        String hql="from CheckConfig c where c.itemCode=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, code);
        @SuppressWarnings("unchecked")
        List<CheckConfig> list = query.list();
        if(list.size()>0)
            return list.get(0);
        return null;
    }

}
