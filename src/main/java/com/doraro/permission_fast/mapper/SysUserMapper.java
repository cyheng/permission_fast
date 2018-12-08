package com.doraro.permission_fast.mapper;

import com.doraro.permission_fast.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<Long> queryAllModuleId(@Param("userId") Long userId);
}
