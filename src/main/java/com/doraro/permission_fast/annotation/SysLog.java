package com.doraro.permission_fast.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
