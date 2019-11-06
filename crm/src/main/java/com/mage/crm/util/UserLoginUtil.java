package com.mage.crm.util;

import javax.servlet.http.HttpServletRequest;

public class UserLoginUtil {

    public static String realseUserId(HttpServletRequest request){
        String userId=CookieUtil.getCookieValue(request,"userId");

        return  Base64Util.decode(userId);

    }
}
