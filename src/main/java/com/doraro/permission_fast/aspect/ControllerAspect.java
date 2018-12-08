package com.doraro.permission_fast.aspect;


import com.doraro.permission_fast.util.HttpContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
@Profile("dev")
public class ControllerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
    @Around("execution(* com.doraro.permission_fast.controller.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        final HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        logger.info("URL : {}" ,request.getRequestURL());
        logger.info("HTTP_METHOD : {}" ,request.getMethod());
        logger.info("IP : {}" ,request.getRemoteAddr());
        logger.info("CLASS_METHOD :{}.{}",point.getSignature().getDeclaringTypeName(),point.getSignature().getName());
        logger.info("args:{}",Arrays.toString(point.getArgs()));
        logger.info("time:{}",time);
        return result;
    }
}
