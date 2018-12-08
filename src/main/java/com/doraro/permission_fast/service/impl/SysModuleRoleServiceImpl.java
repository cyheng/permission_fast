package com.doraro.permission_fast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.doraro.permission_fast.model.SysModuleRole;
import com.doraro.permission_fast.mapper.SysModuleRoleMapper;
import com.doraro.permission_fast.service.ISysModuleRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-08
 */
@Service
public class SysModuleRoleServiceImpl extends ServiceImpl<SysModuleRoleMapper, SysModuleRole> implements ISysModuleRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateByBatchModuleIds(Long roleId, List<Long> moduleIdList) {
        this.remove(new QueryWrapper<SysModuleRole>().lambda().eq(SysModuleRole::getRoleId,roleId));
        if (moduleIdList == null || moduleIdList.isEmpty()) {
            return ;
        }
        final List<SysModuleRole> collect = moduleIdList.stream().map(it ->
                new SysModuleRole()
                 .setCreateTime(LocalDateTime.now())
                 .setUpdateTime(LocalDateTime.now())
                 .setModuleId(it)
                 .setRoleId(roleId))
                .collect(Collectors.toList());
        this.saveBatch(collect);

    }
}
