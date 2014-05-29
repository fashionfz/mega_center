package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.ICheckMainDAO;
import com.po.CheckMain;

@Repository
public class CheckMainDAO implements ICheckMainDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Override
    public List<CheckMain> query(int page,int rows) {
        Session session = sessionFactory.getCurrentSession();
        String hql="from CheckMain m order by m.checkTime desc";
        Query query = session.createQuery(hql);
        if(page>0&&rows>0){
            query.setFirstResult((page-1)*rows);
            query.setMaxResults(rows);
        }
        return query.list();
    }
    
    @Override
    public String save(CheckMain main){
        Session session = sessionFactory.getCurrentSession();
        session.persist(main);
        return main.getId();
    }

    @Override
    public CheckMain queryCheckMain(String mainId) {
        Session session = sessionFactory.getCurrentSession();
        if(mainId!=null&&!"".equals(mainId)){
            return (CheckMain) session.get(CheckMain.class, mainId);
        }else{
            String sql="select m.* from t_test_main m where m.CHECK_TIME=(select max(check_time) from t_test_main)";
            SQLQuery query = session.createSQLQuery(sql).addEntity(CheckMain.class);
            List<CheckMain> list = query.list();
            if(list!=null&&list.size()>0)
                return list.get(0);
            else
                return null;
        }
    }

    @Override
    public void updateCheckMain(String id, int checkCount, int errorCount) {
        Session session = sessionFactory.getCurrentSession();
        String hql="update CheckMain m set m.checkCount=?,m.errorCount=? where m.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, checkCount);
        query.setParameter(1, errorCount);
        query.setParameter(2, id);
        query.executeUpdate();
    }

    @Override
    public long queryCount() {
        Session session = sessionFactory.getCurrentSession();
        String hql="select count(m.id) from CheckMain m";
        Query query = session.createQuery(hql);
        return (Long) query.uniqueResult();
    }

}
