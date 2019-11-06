package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.base.CrmConstant;
import com.mage.crm.base.exceptions.ParamsException;
import com.mage.crm.model.MessageModel;
import com.mage.crm.query.CusDevPlanQuery;
import com.mage.crm.service.CustomerDevPlanService;
import com.mage.crm.service.SaleChanceService;
import com.mage.crm.vo.CustomerDevPlan;
import com.mage.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;


@Controller
@RequestMapping("cus_dev_plan")
public class CustomerDevPlanController extends BaseController {
  @Resource
  public SaleChanceService saleChanceService;

  @Resource
  public CustomerDevPlanService customerDevPlanService;


    @RequestMapping("index")
    public String index(String id, Model model){
      SaleChance saleChance=saleChanceService.querySaleChanceById(id);
      model.addAttribute("saleChance",saleChance);
      return "cus_dev_plan_detail";
  }
    @RequestMapping("queryCusDevPlans")
    @ResponseBody
    public Map<String,Object> queryCusDevPlans(CusDevPlanQuery cusDevPlanQuery){
        return customerDevPlanService.queryCusDevPlans(cusDevPlanQuery);

    }
    @RequestMapping("insert")
    @ResponseBody
    public MessageModel insert(CustomerDevPlan customerDevPlan){
        MessageModel messageModel=new MessageModel();
        try{
            customerDevPlanService.insert(customerDevPlan);
        }catch (ParamsException e){
            e.printStackTrace();
            messageModel.setCode(e.getCode());
            messageModel.setMsg(e.getMsg());
        }catch (Exception e){
            e.printStackTrace();
            messageModel.setCode(CrmConstant.OPS_FAILED_DODE);
            messageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
        }
        return messageModel;
    }

    @RequestMapping("update")
    @ResponseBody
    public MessageModel update(CustomerDevPlan customerDevPlan){
        customerDevPlanService.update(customerDevPlan);
        MessageModel messageModel=new MessageModel();
        return success("更新开发计划成功");
       // return messageModel;
    }

    @RequestMapping("delete")
    @ResponseBody
    public MessageModel delete(Integer id){
        customerDevPlanService.delete(id);
        MessageModel messageModel=new MessageModel();
        //return success("删除开发计划");
        return messageModel;
    }
}
