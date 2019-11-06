package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.base.CrmConstant;
import com.mage.crm.base.exceptions.ParamsException;
import com.mage.crm.model.MessageModel;
import com.mage.crm.model.UserModel;
import com.mage.crm.service.UserService;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.util.UserLoginUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {


    @Resource
    private UserService userService;

    @RequestMapping("userLogin")
    @ResponseBody
    public MessageModel userLogin(String userName, String userPwd) {
        MessageModel messageModel = new MessageModel();
        try {
            UserModel userModel = userService.userLogin(userName, userPwd);
            messageModel.setResult(userModel);
        } catch (ParamsException e) {
            e.printStackTrace();
            messageModel.setCode(CrmConstant.LOGIN_FAILED_DODE);
            messageModel.setMsg(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            messageModel.setCode(CrmConstant.OPS_FAILED_DODE);
            messageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
        }
        return messageModel;
    }



    @RequestMapping("updatePwd")
    @ResponseBody
    public MessageModel updatePwd(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        MessageModel messageModel=new MessageModel();
        String userId= UserLoginUtil.realseUserId(request);
        try{
            userService.updatePwd(userId,oldPassword,newPassword,confirmPassword);
        }catch(ParamsException e){
            e.printStackTrace();
            messageModel.setCode(e.getCode());
            messageModel.setMsg(e.getMsg());
        }catch(Exception e){
            e.printStackTrace();
            messageModel.setCode(CrmConstant.OPS_FAILED_DODE);
            messageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
        }
        return messageModel;
    }




    @RequestMapping("main")
    public String main(HttpServletRequest request) throws UnsupportedEncodingException {
        /**
         * 从request中，获取cookie 得到userName,和trueName
         */
        /*Cookie[]  cookies = request.getCookies();
        for (int i = 0; i <cookies.length ; i++) {
            Cookie cookie = cookies[i];
            if("userName".equals(cookie.getName())){
                request.setAttribute("userName",cookie.getValue());
            }else if("trueName".equals(cookie.getName())){
                request.setAttribute("trueName", URLDecoder.decode(cookie.getValue(),"utf-8"));
            }
        }*/
        request.setAttribute("userName", CookieUtil.getCookieValue(request, "userName"));
        request.setAttribute("trueName", CookieUtil.getCookieValue(request, "trueName"));
        return "main";
    }


    @RequestMapping("queryAllCustomerManager")
    @ResponseBody
    public List<User> queryAllCustomerManager() {
        List<User> users = userService.queryAllCustomerManager();
        return users;
    }
}







