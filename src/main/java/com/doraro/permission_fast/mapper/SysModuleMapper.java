package com.doraro.permission_fast.mapper;

import com.doraro.permission_fast.model.SysModule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
public interface SysModuleMapper extends BaseMapper<SysModule> {


    Set<String> getPermByUserId(@Param("userId")Long userId);
}
