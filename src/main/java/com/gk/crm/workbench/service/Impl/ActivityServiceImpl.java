package com.gk.crm.workbench.service.Impl;

import com.gk.crm.setting.dao.UserDao;
import com.gk.crm.setting.domain.User;
import com.gk.crm.utils.SqlSessionUtil;
import com.gk.crm.vo.PaginationVo;
import com.gk.crm.workbench.dao.ActivityDao;
import com.gk.crm.workbench.dao.ActivityRemarkDao;
import com.gk.crm.workbench.domain.Activity;
import com.gk.crm.workbench.domain.ActivityRemark;
import com.gk.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean save(Activity a) {
        boolean flag=true;
        int count=activityDao.save(a);
        if (count!=1){
            return false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        int total=activityDao.getTotalByCondition(map);
        List<Activity> dataList=activityDao.getActivityListByCondition(map);
        PaginationVo<Activity> vo=new PaginationVo<>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] id) {
        boolean flag=true;
        int count1=activityRemarkDao.getCountByAids(id);
        int count2=activityRemarkDao.deleteByAids(id);
        if(count1!=count2){
            flag=false;
        }
        int count3=activityDao.delete(id);
        if(count3!=id.length){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        List<User> uList=userDao.getUserLIst();
        Activity a=activityDao.getById(id);

        Map<String,Object> map=new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);

        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag=true;
        int count=activityDao.update(a);
        if (count!=1){
            return false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity a=activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> arList=activityRemarkDao.getRemarkListByAid(activityId);
        return arList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag=true;
        int count=activityRemarkDao.deleteById(id);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag=true;
        int count=activityRemarkDao.saveRemark(ar);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag=true;
        int count=activityRemarkDao.updateRemark(ar);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> aList=activityDao.getActivityListByClueId(clueId);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        List<Activity> aList=activityDao.getActivityListByNameAndNotByClueId(map);
        return aList;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        List<Activity> aList=activityDao.getActivityListByName(aname);
        return aList;
    }
}
