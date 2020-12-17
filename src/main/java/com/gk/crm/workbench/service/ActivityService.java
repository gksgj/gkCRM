package com.gk.crm.workbench.service;

import com.gk.crm.utils.SqlSessionUtil;
import com.gk.crm.vo.PaginationVo;
import com.gk.crm.workbench.dao.ActivityDao;
import com.gk.crm.workbench.domain.Activity;
import com.gk.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {


    boolean save(Activity a);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] id);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}
