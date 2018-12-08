package com.doraro.permission_fast.oauth2;

import com.doraro.permission_fast.exception.CodeMsg;
import com.doraro.permission_fast.exception.Result;
import com.doraro.permission_fast.util.HttpContextUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.doraro.permission_fast.util.Constant.TOKEN_KEY;

public class OAuth2Filter extends AuthenticatingFilter{



    /**
     * 提取header或param中的token值
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String token  = HttpContextUtils.getAttr((HttpServletRequest)request, TOKEN_KEY);
        if(StringUtils.isBlank(token)){
            return null;
        }
        return new Oauth2Token(token);
    }


    /**
     * 允许OPTIONS方法访问
     * 详情：https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Access_control_CORS#%E9%A2%84%E6%A3%80%E8%AF%B7%E6%B1%82
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        final String method = ((HttpServletRequest) request).getMethod();
        return method.equals(RequestMethod.OPTIONS.name());
    }

    /**
     * 获取请求token，如果token不存在，直接返回
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token = HttpContextUtils.getAttr((HttpServletRequest) request,TOKEN_KEY);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            setHeader(httpResponse);
            String json = new Gson().toJson(Result.error(CodeMsg.TOKEN_ERROR));
            httpResponse.getWriter().print(json);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 捕获Relam中doGetAuthenticationInfo抛出的异常并返回到客户端
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        setHeader(httpResponse);
        Result r = Result.error(CodeMsg.AUTH_FAIL.fillArgs(e.getMessage()));
        String json = new Gson().toJson(r);
        try {
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private void setHeader(HttpServletResponse httpResponse) {
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
    }




}
