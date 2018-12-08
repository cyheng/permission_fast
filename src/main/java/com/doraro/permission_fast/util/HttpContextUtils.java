package com.doraro.permission_fast.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class HttpContextUtils {
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getDomain(){
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin(){
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }

    public static String getAttr(HttpServletRequest request, String key) {
        String token = request.getHeader(key);
        if (StringUtils.isBlank(token)) {
            token  = request.getParameter(key);
            if(StringUtils.isBlank(token)){
                return null;
            }
        }
        return StringUtils.substringAfter(token, Constant.TOKEN_PREFIX);
    }
}
