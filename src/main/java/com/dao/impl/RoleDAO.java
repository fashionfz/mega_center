package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.IRoleDAO;
import com.po.RRoleMenu;
import com.po.SysRole;

@Repository
public class RoleDAO implements IRoleDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SysRole> queryAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql="from SysRole";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysRole> queryAnth(String userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="select l from SysRole l,RUserRole r where l.id=r.roleId and r.userId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, userId);
        return query.list();
    }

    @Override
    public List<SysRole> queryUnAnth(String userId) {
        Session session = sessionFactory.getCurrentSession();
        String sql="select l.* from T_SYS_ROLE l where not EXISTS(select 1 from R_USER_ROLE r where l.id=r.role_Id and r.user_Id=?)";
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter(0, userId);
        @SuppressWarnings("unchecked")
        List<SysRole> list = query.addEntity(SysRole.class).list();
        return list;
    }

    @Override
    public void save(SysRole role) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(role);
    }

    @Override
    public void saveRoleAnth(RRoleMenu rrm) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(rrm);
    }

    @Override
    public void delRoleAnth(String roleId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from RRoleMenu r where r.roleId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, roleId);
        query.executeUpdate();
    }

    @Override
    public int deleteRole(String roleId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from SysRole r where r.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, roleId);
        return query.executeUpdate();
    }

    @Override
    public void updateRole(SysRole role) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(role);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> querySecurity() {
        Session session = sessionFactory.getCurrentSession();
        String hql="select r,m from SysRole r,RRoleMenu rrm,SysMenu m where r.id=rrm.roleId and m.id=rrm.menuId";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public void deluserAnth(String roleId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from RUserRole rur where rur.roleId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, roleId);
        query.executeUpdate();
    }

}
