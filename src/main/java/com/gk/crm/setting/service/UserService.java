package com.gk.crm.setting.service;

import com.gk.crm.exception.LoginException;
import com.gk.crm.setting.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
