package com.doraro.permission_fast.service.impl;

import com.doraro.permission_fast.model.SysModule;
import com.doraro.permission_fast.mapper.SysModuleMapper;
import com.doraro.permission_fast.service.ISysModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doraro.permission_fast.service.ISysRoleService;
import com.doraro.permission_fast.util.Constant;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
@Service
public class SysModuleServiceImpl extends ServiceImpl<SysModuleMapper, SysModule> implements ISysModuleService {
    @Autowired
    private ISysRoleService roleService;

    @Override
    public Set<String> getPermByUserId(Long userId) {
        final Set<String> roles = roleService.getRoleNamesByUserId(userId);
        if (roles == null) {
            return Sets.newHashSet();
        }

        if (roles.contains(Constant.SUPER_ROLE_NAME)) {
            return this.list().stream()
                    .map(SysModule::getPerms)
                    .filter(StringUtils::isNoneBlank)
                    .collect(Collectors.toSet());
        }

       return this.baseMapper.getPermByUserId(userId);
    }
}
