package com.doraro.permission_fast.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.doraro.permission_fast.exception.ApiGlobalException;
import com.doraro.permission_fast.exception.CodeMsg;
import com.doraro.permission_fast.model.SysUser;
import com.doraro.permission_fast.mapper.SysUserMapper;
import com.doraro.permission_fast.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.doraro.permission_fast.service.SysTokenService;
import com.doraro.permission_fast.util.EncptyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private SysTokenService tokenService;
    @Override
    public SysUser selectActiveUserByUsername(String username) {
        if(StringUtils.isBlank(username)){
            return null;
        }
        final LambdaQueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>().lambda()
                .eq(SysUser::getUsername, username)
                .eq(SysUser::getStatus, 1);
        return this.getOne(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean changePassword(SysUser sysUser, String oldPassword, String newPassword, String token) {
        if (sysUser == null || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(token)) {
            return false;
        }
        final String oldPwd = EncptyUtils.formPwdToDb(oldPassword, sysUser.getSalt());
        if (!oldPwd.equals(sysUser.getPassword())){
            throw new ApiGlobalException(CodeMsg.INVALID_PWD);
        }
        final String newPwd = EncptyUtils.formPwdToDb(newPassword, sysUser.getSalt());
        final SysUser user = new SysUser().setPassword(newPwd);
        final boolean flag = this.update(user, new UpdateWrapper<SysUser>().lambda()
                .eq(SysUser::getPassword, oldPwd)
                .eq(SysUser::getUserId, sysUser.getUserId()
                ));
        if(!flag){
            throw new ApiGlobalException(CodeMsg.UPDATE_FAIL);
        }
        tokenService.invalidToken(token);
        return true;
    }

    @Override
    public List<Long> queryAllModuleId(Long createUserId) {
       return this.baseMapper.queryAllModuleId(createUserId);
    }
}
