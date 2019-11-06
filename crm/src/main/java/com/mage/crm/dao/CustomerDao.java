package com.mage.crm.dao;

import com.mage.crm.query.CustomerQuery;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.DataDic;
import com.mage.crm.vo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;




public interface CustomerDao {
    @Select("select id,name from t_customer where is_valid=1")
    public List<Customer> queryAllCustomers();

    public List<Customer> queryCustomersByParams(CustomerQuery customerQuery);

    public Integer insert(Customer customer);

    public Integer update(Customer customer);

    public Integer delete(Integer[] ids);
}


//public Integer insert(Customer customer);
//public Integer update(Customer customer);





