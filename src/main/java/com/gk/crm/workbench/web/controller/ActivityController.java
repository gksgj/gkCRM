package com.gk.crm.workbench.web.controller;

import com.gk.crm.setting.domain.User;
import com.gk.crm.setting.service.UserService;
import com.gk.crm.setting.service.impl.UserServiceImpl;
import com.gk.crm.utils.*;
import com.gk.crm.vo.PaginationVo;
import com.gk.crm.workbench.domain.Activity;
import com.gk.crm.workbench.domain.ActivityRemark;
import com.gk.crm.workbench.service.ActivityService;
import com.gk.crm.workbench.service.Impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动控制器");
        String path=request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if ("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }else if ("/workbench/activity/update.do".equals(path)){
            update(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/activity/getRemarkListByAid.do".equals(path)){
            getRemarkListByAid(request,response);
        }else if ("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if ("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if ("/workbench/activity/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }

    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行修改备注的操作");
        String id=request.getParameter("id");
        String noteContent=request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy =((User)request.getSession().getAttribute("user")).getName();
        String editflag="1";

        ActivityRemark ar=new ActivityRemark();

        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setEditFlag(editflag);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag=as.updateRemark(ar);

        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);

        PrintJson.printJsonObj(response,map);

    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行添加备注操作");

        String noteContent=request.getParameter("noteContent");
        String activityId=request.getParameter("activityId");
        String id=UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy =((User)request.getSession().getAttribute("user")).getName();
        String editflag="0";

        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(noteContent);
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editflag);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag=as.saveRemark(ar);

        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("ar",ar);

        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("删除备注操作");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据市场活动id，取得备注信息列表");
        String activityId=request.getParameter("activityId");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> arList=as.getRemarkListByAid(activityId);
        PrintJson.printJsonObj(response,arList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到跳转到详细信息页的操作");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        Activity a=as.detail(id);
        request.setAttribute("a",a);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动修改操作");
        String id = request.getParameter("id");
        String owner =request.getParameter("owner");
        String name =request.getParameter("name");
        String startDate =request.getParameter("startDate");
        String endDate =request.getParameter("endDate");
        String cost =request.getParameter("cost");
        String description =request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy =((User)request.getSession().getAttribute("user")).getName();

        Activity a=new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setEditBy(editBy);
        a.setEditTime(editTime);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag=as.update(a);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询用户信息列表和根据市场活动id查询单条记录的操作");
        String id=request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Map<String,Object> map=as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的删除操作");
        String id[]=request.getParameterValues("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag=as.delete(id);
        PrintJson.printJsonFlag(response,flag);

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");
        String name=request.getParameter("name");
        String owner=request.getParameter("owner");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String pageNo=request.getParameter("pageNo");
        int paNo=Integer.valueOf(pageNo);
        String pageSize=request.getParameter("pageSize");
        int paSi=Integer.valueOf(pageSize);
        int skipcount=(paNo-1)*paSi;

        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipcount",skipcount);
        map.put("paSi",paSi);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVo<Activity> vo= as.pageList(map);

        PrintJson.printJsonObj(response,vo);


    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动的添加操作");
        String id = UUIDUtil.getUUID();
        String owner =request.getParameter("owner");
        String name =request.getParameter("name");
        String startDate =request.getParameter("startDate");
        String endDate =request.getParameter("endDate");
        String cost =request.getParameter("cost");
        String description =request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy =((User)request.getSession().getAttribute("user")).getName();

        Activity a=new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setOwner(owner);
        a.setName(name);
        a.setEndDate(endDate);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag=as.save(a);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService us= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList=us.getUserList();
        PrintJson.printJsonObj(response,userList);
    }


}
