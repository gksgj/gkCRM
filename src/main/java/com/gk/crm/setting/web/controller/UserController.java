package com.gk.crm.setting.web.controller;

import com.gk.crm.setting.domain.User;
import com.gk.crm.setting.service.UserService;
import com.gk.crm.setting.service.impl.UserServiceImpl;
import com.gk.crm.utils.MD5Util;
import com.gk.crm.utils.PrintJson;
import com.gk.crm.utils.ServiceFactory;
import com.gk.crm.utils.SqlSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到用户控制器");
        String path=request.getServletPath();
        if("/settings/user/login.do".equals(path)){
            login(request,response);

        }else if("/settings/user/xxx.do".equals(path)){

        }

    }

    private void login(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到验证登录操作");
        String loginAct=request.getParameter("loginAct");
        String loginPwd=request.getParameter("loginPwd");
        System.out.println(loginAct);
        System.out.println(loginPwd);
        loginPwd= MD5Util.getMD5(loginPwd);
        String ip=request.getRemoteAddr();
        System.out.println("--------ip:"+ip);

        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());

        try{
            User user=userService.login(loginAct,loginPwd,ip);

            request.getSession().setAttribute("user",user);

            PrintJson.printJsonFlag(response,true);
        } catch (Exception e) {
            e.printStackTrace();
            String msg=e.getMessage();
            Map<String,Object> map=new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }
}
