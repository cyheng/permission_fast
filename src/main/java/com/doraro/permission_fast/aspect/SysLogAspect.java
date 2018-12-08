package com.doraro.permission_fast.aspect;


import com.doraro.permission_fast.annotation.SysLog;
import com.doraro.permission_fast.model.SysLogModel;
import com.doraro.permission_fast.service.ISysLogService;
import com.doraro.permission_fast.util.HttpContextUtils;
import com.doraro.permission_fast.util.ShiroUserUtil;
import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

import java.time.LocalDateTime;

/**
 * 系统关键操作日志保存
 * @author cyheng
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private ISysLogService sysLogService;


    @Pointcut("@annotation(com.doraro.permission_fast.annotation.SysLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        final SysLogModel sysLog = new SysLogModel();
        SysLog syslog = method.getAnnotation(SysLog.class);
        if(syslog != null){
            //注解上的描述
            sysLog.setOperation(syslog.value());
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try{
            String params = new Gson().toJson(args);
            sysLog.setParams(params);
        }catch (Exception e){

        }

        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(request.getRemoteAddr());

        //用户名
        String username = ShiroUserUtil.getUser().getUsername();
        sysLog.setUsername(username);

        sysLog.setTime(time);
        sysLog.setCreateTime(LocalDateTime.now());
        //保存系统日志
        sysLogService.save(sysLog);
    }

}
