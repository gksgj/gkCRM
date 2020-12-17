package com.gk.crm.workbench.service.Impl;

import com.gk.crm.utils.SqlSessionUtil;
import com.gk.crm.workbench.dao.CustomerDao;
import com.gk.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * Author 北京动力节点
 */
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    public List<String> getCustomerName(String name) {

        List<String> sList = customerDao.getCustomerName(name);

        return sList;
    }
}
















