package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.IMenuDAO;
import com.po.SysMenu;

@Repository
public class MenuDAO implements IMenuDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SysMenu> queryAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql="from SysMenu";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysMenu> queryAnth(String roleId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="select m from SysMenu m,RRoleMenu r where m.id=r.menuId and r.roleId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, roleId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SysMenu> queryUnAnth(String roleId) {
        Session session = sessionFactory.getCurrentSession();
        String sql="select m.* from T_SYS_MENU m where not EXISTS(select 1 from R_ROLE_MENU r where m.id=r.MENU_ID and r.ROLE_ID=?)";
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter(0, roleId);
        List<SysMenu> list = query.addEntity(SysMenu.class).list();
        return list;
    }

    @Override
    public void create(SysMenu menu) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(menu);
    }

    @Override
    public void updateMenu(SysMenu menu) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(menu);
    }

    @Override
    public int deleteMenu(String menuId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from SysMenu m where m.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, menuId);
        return query.executeUpdate();
    }

    @Override
    public void clearMenuR(String menuId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from RRoleMenu rrm where rrm.menuId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, menuId);
        query.executeUpdate();
    }

    @Override
    public List<SysMenu> queryByUser(String username) {
        Session session = sessionFactory.getCurrentSession();
        String sql="select distinct m.* from t_sys_menu m,r_role_menu rrm,t_sys_role r,r_user_role rur,t_sys_user u " +
        		"where m.ID=rrm.MENU_ID and rrm.ROLE_ID=r.ID and r.ID=rur.ROLE_ID and rur.USER_ID=u.ID and u.USER_NAME=?";
        SQLQuery query = session.createSQLQuery(sql).addEntity(SysMenu.class);
        query.setParameter(0, username);
        return query.list();
    }

}
