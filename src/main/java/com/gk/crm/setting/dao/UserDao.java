package com.gk.crm.setting.dao;

import com.gk.crm.setting.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    List<User> getUserLIst();

    User login(Map<String,String> map);
}
