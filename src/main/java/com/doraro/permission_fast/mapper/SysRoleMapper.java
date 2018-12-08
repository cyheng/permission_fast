package com.doraro.permission_fast.mapper;

import com.doraro.permission_fast.model.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Set<String> getRoleNamesByUserId(Long userId);
}
