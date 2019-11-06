package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CustomerQuery;
import com.mage.crm.service.CustomerService;
import com.mage.crm.service.UserService;
import com.mage.crm.vo.Customer;
import com.mage.crm.vo.DataDic;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {
    @Resource
    private CustomerService customerService;

    @RequestMapping("queryAllCustomers")
    @ResponseBody
    public List<Customer>queryAllCustomers(){
        List<Customer> customers = customerService.queryAllCustomers();
        return customers;
    }


    @RequestMapping("index")
    public String index(){
        return "customer";
    }

    @ResponseBody
    @RequestMapping("queryCustomersByParams")
    public Map<String,Object> queryCustomersByParams(CustomerQuery customerQuery){
        return customerService.queryCustomersByParams(customerQuery);
    }

    @Controller
    @RequestMapping("data_dic")
    public class DataDicController extends BaseController{
        @Resource
        private CustomerService.DataDicService dataDicService;
        @ResponseBody
        @RequestMapping("queryDataDicValueByDataDicName")
        public List<DataDic> queryDataDicValueByDataDicName(String dataDicName){
            return dataDicService.queryDataDicValueByDataDicName(dataDicName);
        }
    }


    @ResponseBody
    @RequestMapping("insert")
    public MessageModel insert(Customer customer){
        customerService.insert(customer);
        return success("客户信息添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    public MessageModel update(Customer customer) {
        customerService.update(customer);
        return success("客户信息修改成功");
    }

    @RequestMapping("delete")
    @ResponseBody
    public MessageModel delete(Integer[] ids) {
        customerService.delete(ids);
        return success("客户信息删除成功");
    }
}


