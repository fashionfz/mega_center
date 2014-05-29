package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.IWorkBookDAO;
import com.po.WorkBook;

@Repository
public class WorkBookDAO implements IWorkBookDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<WorkBook> queryAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql="from WorkBook";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WorkBook> query(String code) {
        Session session = sessionFactory.getCurrentSession();
        String hql="from WorkBook b where b.code=? order by b.sort";
        Query query = session.createQuery(hql);
        query.setParameter(0, code);
        return query.list();
    }

    @Override
    public void create(WorkBook book) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

}
