package com.gk.crm.workbench.web.controller;

import com.gk.crm.setting.domain.User;
import com.gk.crm.setting.service.UserService;
import com.gk.crm.setting.service.impl.UserServiceImpl;
import com.gk.crm.utils.DateTimeUtil;
import com.gk.crm.utils.PrintJson;
import com.gk.crm.utils.ServiceFactory;
import com.gk.crm.utils.UUIDUtil;
import com.gk.crm.workbench.domain.Activity;
import com.gk.crm.workbench.domain.Clue;
import com.gk.crm.workbench.domain.Tran;
import com.gk.crm.workbench.service.ActivityService;
import com.gk.crm.workbench.service.ClueService;
import com.gk.crm.workbench.service.Impl.ActivityServiceImpl;
import com.gk.crm.workbench.service.Impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        }else if ("/workbench/clue/save.do".equals(path)){
            save(request, response);
        }else if ("/workbench/clue/detail.do".equals(path)){
            detail(request, response);
        }else if ("/workbench/clue/getActivityListByClueId.do".equals(path)){
            getActivityListByClueId(request, response);
        }else if ("/workbench/clue/unbund.do".equals(path)){
            unbund(request, response);
        }else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)){
            getActivityListByNameAndNotByClueId(request, response);
        }else if ("/workbench/clue/bund.do".equals(path)){
            bund(request, response);
        }else if ("/workbench/clue/getActivityListByName.do".equals(path)){
            getActivityListByName(request, response);
        }else if ("/workbench/clue/convert.do".equals(path)){
            convert(request, response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("执行线索转换的操作");
        String clueId=request.getParameter("clueId");

        String flag=request.getParameter("flag");
        String  createBy=((User)request.getSession().getAttribute("user")).getName();
        Tran t=null;

        if ("a".equals(flag)){
            t=new Tran();

            String  money=request.getParameter("money");
            String  name=request.getParameter("name");
            String  expectedDate=request.getParameter("expectedDate");
            String  stage=request.getParameter("stage");
            String  activityId=request.getParameter("activityId");
            String  id=UUIDUtil.getUUID();
            String  createTime= DateTimeUtil.getSysTime();

            t.setMoney(money);
            t.setName(name);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setId(id);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
        }
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag1=cs.convert(clueId,t,createBy);
        if(flag1){
            response.sendRedirect(request.getContextPath()+"/workbench/clue/index.jsp");
        }
    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询市场活动列表");
        String aname=request.getParameter("aname");
        System.out.println(aname);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> aList=as.getActivityListByName(aname);
        PrintJson.printJsonObj(response,aList);
    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行关联市场活动");
        String cid=request.getParameter("cid");
        String aids[]=request.getParameterValues("aid");
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=cs.bund(cid,aids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询市场活动+排除已经关联的活动");
        String aname=request.getParameter("aname");
        String clueId=request.getParameter("clueId");

        Map<String,String> map=new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);

        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList=as.getActivityListByNameAndNotByClueId(map);
        PrintJson.printJsonObj(response,aList);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取消市场活动关联");
        String id=request.getParameter("id");
        ClueService cs= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=cs.unbund(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("关联市场活动");
        String clueId=request.getParameter("clueId");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> aList=as.getActivityListByClueId(clueId);
        PrintJson.printJsonObj(response,aList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("线索详细页");
        String id=request.getParameter("id");
        System.out.println(id);
        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        Clue c=clueService.detail(id);
        request.setAttribute("c",c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        String id= UUIDUtil.getUUID();
        String fullname=request.getParameter("fullname");
        String appellation=request.getParameter("appellation");
        String owner=request.getParameter("owner");
        String company=request.getParameter("company");
        String job=request.getParameter("job");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String website=request.getParameter("website");
        String mphone=request.getParameter("mphone");
        String state=request.getParameter("state");
        String source=request.getParameter("source");
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String createTime= DateTimeUtil.getSysTime();
        String description=request.getParameter("description");
        String contactSummary=request.getParameter("contactSummary");
        String nextContactTime=request.getParameter("nextContactTime");
        String address=request.getParameter("address");

        Clue clue=new Clue();
        clue.setId(id);
        clue.setAddress(address);
        clue.setWebsite(website);
        clue.setState(state);
        clue.setSource(source);
        clue.setPhone(phone);
        clue.setOwner(owner);
        clue.setNextContactTime(nextContactTime);
        clue.setMphone(mphone);
        clue.setJob(job);
        clue.setFullname(fullname);
        clue.setEmail(email);
        clue.setDescription(description);
        clue.setCreateTime(createTime);
        clue.setCreateBy(createBy);
        clue.setContactSummary(contactSummary);
        clue.setCompany(company);
        clue.setAppellation(appellation);

        ClueService clueService= (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag=clueService.save(clue);
        PrintJson.printJsonFlag(response,flag);


    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户消息");
        UserService userService= (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> uList=userService.getUserList();
        PrintJson.printJsonObj(response,uList);
    }
}