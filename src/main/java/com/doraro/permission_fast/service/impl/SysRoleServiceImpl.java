package com.doraro.permission_fast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.doraro.permission_fast.exception.ApiGlobalException;
import com.doraro.permission_fast.exception.CodeMsg;
import com.doraro.permission_fast.model.SysRole;
import com.doraro.permission_fast.mapper.SysRoleMapper;
import com.doraro.permission_fast.service.ISysModuleRoleService;
import com.doraro.permission_fast.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doraro.permission_fast.service.ISysUserService;
import com.doraro.permission_fast.util.Constant;
import com.doraro.permission_fast.util.ShiroUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysModuleRoleService moduleRoleService;
    @Override
    public Set<String> getRoleNamesByUserId(Long userId) {
        final Set<String> result = this.baseMapper.getRoleNamesByUserId(userId);
        result.remove(null);
        return result;
    }

    @Override
    public IPage<SysRole> listRole(Page<SysRole> page) {
        final LambdaQueryWrapper<SysRole> wrapper = notSuperUserCondition();
        return this.page(page,wrapper);
    }

    @Override
    public SysRole getRoleById(Long id) {
        final LambdaQueryWrapper<SysRole> wrapper = notSuperUserCondition().eq(SysRole::getRoleId, id);
        return this.getOne(wrapper);
    }

    @Override
    public void deleteRoleByIds(Long[] roleIds) {

        final LambdaQueryWrapper<SysRole> wrapper = notSuperUserCondition()
                .ne(SysRole::getRoleName, Constant.SUPER_ROLE_NAME)
                .in(SysRole::getRoleId, Arrays.asList(roleIds));
        this.remove(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveRole(SysRole role, List<Long> moduleIdList) {
        checkPerm(role,moduleIdList);
        this.save(role);
        moduleRoleService.saveOrUpdateByBatchModuleIds(role.getRoleId(),moduleIdList);
    }

    @Override
    public void updateRole(SysRole role, List<Long> moduleIdList) {
        checkPerm(role,moduleIdList);
        this.updateById(role);
        moduleRoleService.saveOrUpdateByBatchModuleIds(role.getRoleId(),moduleIdList);
    }

    private void checkPerm(SysRole role, List<Long> moduleIdList) {
        if(ShiroUserUtil.isSuperUser()){
            return;
        }
        List<Long> ids =  userService.queryAllModuleId(role.getCreateUserId());
        if(!ids.containsAll(moduleIdList)){
            throw new ApiGlobalException(CodeMsg.PERM_ERROR);
        }
    }

    /**
     *  超级用户才能看到所有角色,否则只能看到当前用户创建的角色
     * @return wrapper
     */
    private LambdaQueryWrapper<SysRole> notSuperUserCondition() {
        final boolean isSuperUser = ShiroUserUtil.isSuperUser();
        return new QueryWrapper<SysRole>()
                .lambda()
                .eq(!isSuperUser, SysRole::getCreateUserId, ShiroUserUtil.getCurrentUserId());
    }

}
