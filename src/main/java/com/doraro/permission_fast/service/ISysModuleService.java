package com.doraro.permission_fast.service;

import com.doraro.permission_fast.model.SysModule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
public interface ISysModuleService extends IService<SysModule> {

    Set<String> getPermByUserId(Long userId);
}
