package com.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.IDeviceGroupDAO;
import com.dto.GroupDeviceDTO;
import com.po.Device;
import com.po.DeviceGroup;
import com.po.RGroupDevice;

@Repository
public class DeviceGroupDAO implements IDeviceGroupDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<DeviceGroup> queryAll(int page,int rows) {
        Session session = sessionFactory.getCurrentSession();
        String hql="from DeviceGroup";
        Query query = session.createQuery(hql);
        if(page>0&&rows>0){
            query.setFirstResult((page-1)*rows);
            query.setMaxResults(rows);
        }
        return query.list();
    }

    @Override
    public void create(DeviceGroup group) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(group);
    }

    @Override
    public void update(DeviceGroup group) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(group);
    }

    @Override
    public int deleteGroup(String groupId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from DeviceGroup g where g.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, groupId);
        return query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GroupDeviceDTO> queryByGroup(String groupId,int page,int rows) {
        List<GroupDeviceDTO> result = new ArrayList<GroupDeviceDTO>();
        Session session = sessionFactory.getCurrentSession();
        String hql="select d,g from Device d,RGroupDevice g where d.id=g.vicId and g.groupId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, groupId);
        if(page>0&&rows>0){
            query.setFirstResult((page-1)*rows);
            query.setMaxResults(rows);
        }
        List<Object[]> list = query.list();
        for(Object[] objs : list){
            Device d = (Device) objs[0];
            RGroupDevice r = (RGroupDevice) objs[1];
            
            GroupDeviceDTO dto = new GroupDeviceDTO();
            dto.setId(r.getId());
            dto.setGroupId(r.getGroupId());
            dto.setVicId(d.getId());
            dto.setVicName(d.getVicName());
            dto.setRecordType(r.getRecordType());
            result.add(dto);
        }
        return result;
    }

    
    @Override
    public List<Device> queryUnGroup(String groupId,int page,int rows) {
        Session session = sessionFactory.getCurrentSession();
        String sql="select d.* from T_DEVICE d where not EXISTS(select 1 from R_GROUP_DEVICE r where d.id=r.VIC_ID and r.GROUP_ID=?)";
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter(0, groupId);
        if(page>0&&rows>0){
            query.setFirstResult((page-1)*rows);
            query.setMaxResults(rows);
        }
        @SuppressWarnings("unchecked")
        List<Device> list = query.addEntity(Device.class).list();
        return list;
    }

    @Override
    public void saveRGroupDevice(RGroupDevice r) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(r);
    }

    @Override
    public void delRGroupDevice(String groupId, String deviceId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from RGroupDevice r where r.groupId=? and r.vicId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, groupId);
        query.setParameter(1, deviceId);
        query.executeUpdate();
    }

    @Override
    public void updateGroupDevice(String groupId, String deviceId, int type) {
        Session session = sessionFactory.getCurrentSession();
        String hql="update RGroupDevice r set r.recordType=? where r.groupId=? and r.vicId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, type);
        query.setParameter(1, groupId);
        query.setParameter(2, deviceId);
        query.executeUpdate();
    }

    @Override
    public void delRGroupDevice(String groupId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from RGroupDevice r where r.groupId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, groupId);
        query.executeUpdate();
    }

    @Override
    public long queryCount() {
        Session session = sessionFactory.getCurrentSession();
        String hql="select count(g.id) from DeviceGroup g";
        Query query = session.createQuery(hql);
        return (Long) query.uniqueResult();
    }

    @Override
    public long queryByGroup(String groupId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="select count(d.id) from Device d,RGroupDevice g where d.id=g.vicId and g.groupId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, groupId);
        return (Long) query.uniqueResult();
    }

    @Override
    public BigInteger queryUnGroup(String groupId) {
        Session session = sessionFactory.getCurrentSession();
        String sql="select count(d.id) from T_DEVICE d where not EXISTS(select 1 from R_GROUP_DEVICE r where d.id=r.VIC_ID and r.GROUP_ID=?)";
        SQLQuery query = session.createSQLQuery(sql);
        query.setParameter(0, groupId);
        return (BigInteger) query.uniqueResult();
    }

    @Override
    public DeviceGroup findById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (DeviceGroup) session.load(DeviceGroup.class, id);
    }
}
