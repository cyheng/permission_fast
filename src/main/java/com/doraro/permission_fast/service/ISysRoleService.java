package com.doraro.permission_fast.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doraro.permission_fast.model.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */

public interface ISysRoleService extends IService<SysRole> {

    Set<String> getRoleNamesByUserId(Long userId);

    IPage<SysRole> listRole(Page<SysRole> page);

    SysRole getRoleById(Long id);

    void deleteRoleByIds(Long[] roleIds);

    void saveRole(SysRole role, List<Long> moduleIdList);

    void updateRole(SysRole role, List<Long> moduleIdList);
}
