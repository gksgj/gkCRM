package com.gk.crm.workbench.dao;

import com.gk.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] id);

    int deleteByAids(String[] id);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
