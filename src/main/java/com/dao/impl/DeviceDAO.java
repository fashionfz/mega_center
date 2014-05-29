package com.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dao.IDeviceDAO;
import com.dto.QueryDeviceDTO;
import com.po.Device;


@Repository
public class DeviceDAO implements IDeviceDAO{

    @Autowired
    private SessionFactory sessionFactory;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Device> query(QueryDeviceDTO dto,int page,int rows) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder hql=new StringBuilder();
        Object[] para = new Object[5];
        int i=0;
        hql.append("from Device d where 1=1");
        if(dto.getDvrName()!=null&&!"".equals(dto.getDvrName())){
            hql.append(" and d.dvrName like ?");
            para[i++]="%"+dto.getDvrName()+"%";
        }if(dto.getDvrIp()!=null&&!"".equals(dto.getDvrIp())){
            hql.append(" and d.dvrIp like ?");
            para[i++]="%"+dto.getDvrIp()+"%";
        }if(dto.getDvrUserName()!=null&&!"".equals(dto.getDvrUserName())){
            hql.append(" and d.dvrUserName like ?");
            para[i++]="%"+dto.getDvrUserName()+"%";
        }if(dto.getDvrPassword()!=null&&!"".equals(dto.getDvrPassword())){
            hql.append(" and d.dvrPassword like ?");
            para[i++]="%"+dto.getDvrPassword()+"%";
        }if(dto.getType()!=null&&!"".equals(dto.getType())){
            hql.append(" and d.location = ?");
            para[i++]=dto.getType();
        }
        Query query = session.createQuery(hql.toString());
        for(int x=0;x<i;x++){
            query.setParameter(x, para[x]);
        }
        if(page>0&&rows>0){
            query.setFirstResult((page-1)*rows);
            query.setMaxResults(rows);
        }
        return query.list();
    }

    @Override
    public long getAllCount() {
        Session session = sessionFactory.getCurrentSession();
        String hql="select count(d.id) from Device d";
        Query query = session.createQuery(hql);
        return (Long) query.uniqueResult();
    }

    @Override
    public void create(Device device) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(device);
    }

    @Override
    public void savePlot(String deviceId, String plot) {
        Session session = sessionFactory.getCurrentSession();
        String hql="update Device d set d.recordPlot=? where d.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, plot);
        query.setParameter(1, deviceId);
        query.executeUpdate();
    }

    @Override
    public int deleteDevice(String deviceId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from Device d where d.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, deviceId);
        return query.executeUpdate();
    }

    @Override
    public long queryCount(QueryDeviceDTO dto) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder hql=new StringBuilder();
        Object[] para = new Object[5];
        int i=0;
        hql.append("select count(d.id) from Device d where 1=1");
        if(dto.getDvrName()!=null&&!"".equals(dto.getDvrName())){
            hql.append(" and d.dvrName like ?");
            para[i++]="%"+dto.getDvrName()+"%";
        }if(dto.getDvrIp()!=null&&!"".equals(dto.getDvrIp())){
            hql.append(" and d.dvrIp like ?");
            para[i++]="%"+dto.getDvrIp()+"%";
        }if(dto.getDvrUserName()!=null&&!"".equals(dto.getDvrUserName())){
            hql.append(" and d.dvrUserName like ?");
            para[i++]="%"+dto.getDvrUserName()+"%";
        }if(dto.getDvrPassword()!=null&&!"".equals(dto.getDvrPassword())){
            hql.append(" and d.dvrPassword like ?");
            para[i++]="%"+dto.getDvrPassword()+"%";
        }if(dto.getType()!=null&&!"".equals(dto.getType())){
            hql.append(" and d.location = ?");
            para[i++]=dto.getType();
        }
        Query query = session.createQuery(hql.toString());
        for(int x=0;x<i;x++){
            query.setParameter(x, para[x]);
        }
        return (Long) query.uniqueResult();
    }

    @Override
    public int deleteGroupDevice(String deviceId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="delete from RGroupDevice r where r.vicId=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, deviceId);
        return query.executeUpdate();
    }

    @Override
    public void update(Device device) {
        Session session = sessionFactory.getCurrentSession();
        String hql="update Device d set d.vicName=?,d.dvrName=?,d.dvrIp=?,d.dvrUserName=?,d.dvrPassword=?,d.recordCycle=?,d.channel=?,d.vicType=?,d.recordCycleRemote=?,d.dvrPort=? where d.id=?";
        Query query = session.createQuery(hql);
        query.setParameter(0, device.getVicName());
        query.setParameter(1, device.getDvrName());
        query.setParameter(2, device.getDvrIp());
        query.setParameter(3, device.getDvrUserName());
        query.setParameter(4, device.getDvrPassword());
        query.setParameter(5, device.getRecordCycle());
        query.setParameter(6, device.getChannel());
        query.setParameter(7, device.getVicType());

        query.setParameter(8, device.getRecordCycleRemote());
        query.setParameter(9, device.getDvrPort());
        query.setParameter(10, device.getId());
        query.executeUpdate();
    }

    @Override
    public List<Device> queryCheck(String groupId) {
        Session session = sessionFactory.getCurrentSession();
        String hql="select d from Device d,RGroupDevice r where d.id=r.vicId and r.groupId =?";
        Query query = session.createQuery(hql);
        query.setParameter(0, groupId);
        return query.list();
    }

    @Override
    public Device findById(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (Device) session.load(Device.class, id);
    }

}
