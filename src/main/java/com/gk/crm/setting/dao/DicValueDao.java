package com.gk.crm.setting.dao;

import com.gk.crm.setting.domain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
