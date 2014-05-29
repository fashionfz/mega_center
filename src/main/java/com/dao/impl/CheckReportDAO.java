package com.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.ICheckReportDAO;
import com.po.CheckLog;
import com.po.CheckReport;


@Repository
public class CheckReportDAO implements ICheckReportDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<CheckReport> queryAll(String mainId,int page,int rows) {
        Session session = sessionFactory.getCurrentSession();
        if(mainId!=null&&!"".equals(mainId)){
            String hql="from CheckReport p where p.mainId=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, mainId);
            if(page>0&&rows>0){
                query.setFirstResult((page-1)*rows);
                query.setMaxResults(rows);
            }
            return query.list();
        }else{
            String sql="select p.* from t_test_report p,t_test_main m where m.id=p.main_id and m.CHECK_TIME=(select max(check_time) from t_test_main)";
            SQLQuery query = session.createSQLQuery(sql).addEntity(CheckReport.class);
            query.setFirstResult((page-1)*rows);
            query.setMaxResults(rows);
            return query.list();
            
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<CheckLog> queryCheckLog(String testId) {
        String hql="from CheckLog l where l.testId=?";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hql);
        query.setParameter(0, testId);
        return query.list();
    }


    @Override
    public void clearLog(Date date) {
        String sql="delete from t_test_log using t_test_report, t_test_log where t_test_log.test_id=t_test_report.id and t_test_report.test_time<?";
        //String sql="delete from t_test_log l where exists(select 1 from t_test_report p where l.test_id=p.id and p.test_time<?) and l.status=1";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter(0, date);
        query.executeUpdate();
    }


    @Override
    public void clearReport(Date date) {
        String sql="delete from t_test_report where t_test_report.test_time<?";
        //String sql="delete from t_test_report p where not exists(select 1 from t_test_log l where p.id=l.test_id)";
        Session session = sessionFactory.getCurrentSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter(0, date);
        query.executeUpdate();
    }


    @Override
    public String save(CheckReport report) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(report);
        return report.getId();
    }


    @Override
    public void save(CheckLog log) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(log);
    }


    @Override
    public long queryCount(String mainId) {
        Session session = sessionFactory.getCurrentSession();
        if(mainId!=null&&!"".equals(mainId)){
            String hql="select count(p.id) from CheckReport p where p.mainId=?";
            Query query = session.createQuery(hql);
            query.setParameter(0, mainId);
            return (Long) query.uniqueResult();
        }else{
            String sql="select count(p.id) from t_test_report p,t_test_main m where m.id=p.main_id and m.CHECK_TIME=(select max(check_time) from t_test_main)";
            SQLQuery query = session.createSQLQuery(sql);
            BigInteger count = (BigInteger) query.uniqueResult();
            return count.longValue();
            
        }
    }

}
