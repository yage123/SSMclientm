package com.mage.crm.dao;

import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.vo.CustomerDevPlan;

import java.util.List;

public interface CustomerDevPlanDao {
    public List<CustomerDevPlan> queryCusDevPlans(CusDevPlanQuery cusDevPlanQuery);

    public Integer insert(CustomerDevPlan customerDevPlan);

    public Integer update(CustomerDevPlan customerDevPlan);

    public Integer delete(Integer id);

}
