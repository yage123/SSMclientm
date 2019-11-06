package com.mage.crm.base.exceptions;

import com.alibaba.fastjson.JSON;
import com.mage.crm.base.CrmConstant;
import com.mage.crm.model.MessageModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    /**
     * 全局异常
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @param ex
     * @return
     */
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {
        ModelAndView modelAndView = createDefaultModelAndView(httpServletRequest);
        /**
         * view异常
         * json异常
         */
        if(handler instanceof  HandlerMethod){
            if(ex instanceof  ParamsException){
                ParamsException exception=(ParamsException)ex;
                /**q
                 * 处理用户未登录
                 */
                if(exception.getCode().equals(CrmConstant.OPS_FAILED_DODE) ){
                    modelAndView.addObject("msg","用户未登录");
                    modelAndView.addObject("code", CrmConstant.LOGIN_FAILED_DODE);
                    return modelAndView;
                }

            }
            HandlerMethod handlerMethod=(HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
            if(responseBody!=null){
                MessageModel messageModel = new MessageModel();
                messageModel.setMsg(CrmConstant.OPS_FAILED_MSG);
                messageModel.setCode(CrmConstant.LOGIN_FAILED_DODE);

                if(ex instanceof ParamsException){
                    ParamsException exception = (ParamsException)ex;
                    messageModel.setMsg(exception.getMsg());
                    messageModel.setCode(exception.getCode());
                }
                httpServletResponse.setContentType("application/json;charset=uft-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                PrintWriter printWriter = null;
                try {
                    printWriter = httpServletResponse.getWriter();

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(printWriter!=null){
                        printWriter.write(JSON.toJSONString(messageModel));
                        printWriter.flush();
                        printWriter.close();
                    }
                }
                return  null;
            }else {
                if(ex instanceof ParamsException) {
                    ParamsException exception=(ParamsException)ex;
                    modelAndView.addObject("msg", exception.getMsg());
                    modelAndView.addObject("code", exception.getCode());
                    return modelAndView;
                }
            }


        }

        return modelAndView;
    }

    private ModelAndView createDefaultModelAndView(HttpServletRequest httpServletRequest){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("code",CrmConstant.OPS_FAILED_DODE);
        modelAndView.addObject("msg", CrmConstant.OPS_FAILED_MSG);
        modelAndView.addObject("uri",httpServletRequest.getRequestURI());
        modelAndView.addObject("ctx",httpServletRequest.getContextPath());
        return modelAndView;
    }

}
