package com.gk.crm.setting.service.impl;

import com.gk.crm.exception.LoginException;
import com.gk.crm.setting.dao.UserDao;
import com.gk.crm.setting.domain.User;
import com.gk.crm.setting.service.UserService;
import com.gk.crm.utils.DateTimeUtil;
import com.gk.crm.utils.SqlSessionUtil;
import javafx.fxml.LoadException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao= SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userDao.login(map);

        if(user==null){
            throw new LoginException("账号密码错误");
        }

        String expireTime=user.getExpireTime();
        String currenTime= DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currenTime)<0){
            throw new LoginException("账号已失效");
        }

        String lockState=user.getLockState();
        if("0".equals(lockState)){
            throw new LoginException("账号已锁定");
        }

        String allowIps=user.getAllowIps();
        if(allowIps!=null || allowIps!=""){
            if(!allowIps.contains(ip)){
                throw new LoginException("IP地址受限");
            }
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> list=userDao.getUserLIst();
        return list;
    }
}
