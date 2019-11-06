package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerDao;
import com.mage.crm.dao.DataDicDao;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.DataDic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomerService {
    @Resource
    private CustomerDao customerDao;

    public List<Customer> queryAllCustomers(){
        List<Customer> customerList = customerDao.queryAllCustomers();
        return customerList;
    }

    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery) {
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getRows());
        List<Customer> list = customerDao.queryCustomersByParams(customerQuery);
        PageInfo<Customer> pageInfo = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }


    @Service
    public class DataDicService {
        @Resource
        private DataDicDao dataDicDao;
        public List<DataDic> queryDataDicValueByDataDicName(String dataDicName){
            return dataDicDao.queryDataDicValueByDataDicName(dataDicName);
        }
    }

    public void insert(Customer customer){
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        customer.setState(0);
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerDao.insert(customer)<1,"客户信息添加失败");
    }

    public void checkParams(String customerName,String fr,String phone){
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"联系电话不能为空");

    }

    public void update(Customer customer){
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerDao.update(customer)<1,"客户信息修改失败");
    }

    public void delete(Integer[] ids){
        AssertUtil.isTrue(customerDao.delete(ids)<1,"客户信息删除失败");
    }
}


