package com.doraro.permission_fast.util;

import com.doraro.permission_fast.model.SysUser;
import org.apache.shiro.SecurityUtils;

public class ShiroUserUtil {
    public static SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }
    public static Long getCurrentUserId(){
        return getUser().getUserId();
    }
    public static boolean isSuperUser(){
        return  SecurityUtils.getSubject().hasRole(Constant.SUPER_ROLE_NAME);
    }
}
