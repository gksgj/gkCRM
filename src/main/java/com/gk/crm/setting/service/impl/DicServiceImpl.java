package com.gk.crm.setting.service.impl;

import com.gk.crm.setting.dao.DicTypeDao;
import com.gk.crm.setting.dao.DicValueDao;
import com.gk.crm.setting.dao.UserDao;
import com.gk.crm.setting.domain.DicType;
import com.gk.crm.setting.domain.DicValue;
import com.gk.crm.setting.service.DicService;
import com.gk.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao= SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao= SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map=new HashMap<>();
        List<DicType> dlist=dicTypeDao.getTypeList();
        for(DicType dt:dlist){
            String code=dt.getCode();
            List<DicValue> dvList=dicValueDao.getListByCode(code);
            map.put(code+"List",dvList);
        }
        return map;
    }
}
