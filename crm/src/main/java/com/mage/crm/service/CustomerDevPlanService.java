package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.CustomerDevPlanDao;
import com.mage.crm.dao.SaleChanceDao;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.vo.CustomerDevPlan;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerDevPlanService {
    @Resource
    public SaleChanceDao saleChanceDao;
    @Resource
    public CustomerDevPlanDao customerDevPlanDao;

    public Map<String,Object> queryCusDevPlans(CusDevPlanQuery cusDevPlanQuery){
        AssertUtil.isTrue(null==saleChanceDao.querySaleChanceById(cusDevPlanQuery.saleChanceId+"")," 营销机会不存在");
                PageHelper.startPage(cusDevPlanQuery.getPage(),cusDevPlanQuery.getRows());
        List<CustomerDevPlan> list =
                customerDevPlanDao.queryCusDevPlans(cusDevPlanQuery);
        PageInfo<CustomerDevPlan> pageInfo = new PageInfo<>(list);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",pageInfo.getList());
        map.put("total",pageInfo.getTotal());
        return map;
    }
    public void insert(CustomerDevPlan customerDevPlan) {
        AssertUtil.isTrue(null==saleChanceDao.querySaleChanceById(customerDevPlan.getSaleChanceId()+ ""),"营销机会不存在");
        customerDevPlan.setCreateDate(new Date());
        customerDevPlan.setUpdateDate(new Date());
        customerDevPlan.setIsValid(1);
        AssertUtil.isTrue(customerDevPlanDao.insert(customerDevPlan)<1,"插入开发计划失败");
    }

    public void update(CustomerDevPlan customerDevPlan){
        AssertUtil.isTrue(null==saleChanceDao.querySaleChanceById(customerDevPlan.getSaleChanceId()+ ""),"营销机会不存在");
        customerDevPlan.setCreateDate(new Date());
        customerDevPlan.setUpdateDate(new Date());
        customerDevPlan.setIsValid(1);
        AssertUtil.isTrue(customerDevPlanDao.update(customerDevPlan)<1,"修改开发计划失败");
        AssertUtil.isTrue(saleChanceDao.updateSaleChanceDevResult(1,customerDevPlan.getSaleChanceId(
        ))<1,"修改开发结果失败");
    }

    public void delete(Integer id){
        AssertUtil.isTrue(customerDevPlanDao.delete(id)<1,"删除开发计划失败");
    }
}
