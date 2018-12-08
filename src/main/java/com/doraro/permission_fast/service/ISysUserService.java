package com.doraro.permission_fast.service;

import com.doraro.permission_fast.model.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
public interface ISysUserService extends IService<SysUser> {

    SysUser selectActiveUserByUsername(String username);

    boolean changePassword(SysUser sysUser, String password, String newPassword, String token);

    List<Long> queryAllModuleId(Long createUserId);
}
