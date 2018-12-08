package com.doraro.permission_fast.service;

import com.doraro.permission_fast.model.SysModuleRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-08
 */
public interface ISysModuleRoleService extends IService<SysModuleRole> {

    void saveOrUpdateByBatchModuleIds(Long roleId, List<Long> moduleIdList);
}
