package com.gk.crm.web.listener;

import com.gk.crm.setting.domain.DicValue;
import com.gk.crm.setting.service.DicService;
import com.gk.crm.setting.service.impl.DicServiceImpl;
import com.gk.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("数据字典启动");
        ServletContext application=event.getServletContext();
        DicService dicService= (DicService) ServiceFactory.getService(new DicServiceImpl());
        Map<String, List<DicValue>> map=dicService.getAll();
        Set<String> set=map.keySet();
        for (String key:set){
            application.setAttribute(key,map.get(key));
        }
        System.out.println("数据字典结束");


        Map<String,String> pMap = new HashMap<>();

        ResourceBundle rb = ResourceBundle.getBundle("Stage2Possibility");

        Enumeration<String> e = rb.getKeys();

        while (e.hasMoreElements()){

            //阶段
            String key = e.nextElement();
            //可能性
            String value = rb.getString(key);

            pMap.put(key, value);
    }
        application.setAttribute("pMap", pMap);
  }
}
