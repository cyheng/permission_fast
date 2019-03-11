package com.doraro.permission_fast.oauth2;

import com.doraro.permission_fast.model.SysRole;
import com.doraro.permission_fast.model.SysUser;
import com.doraro.permission_fast.service.ISysModuleService;
import com.doraro.permission_fast.service.ISysRoleService;
import com.doraro.permission_fast.util.RedisKeys;
import com.doraro.permission_fast.util.RedisUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component("authorizer")
public class OauthRealm  extends AuthorizingRealm{
    private final RedisUtils redisUtils;
    private final ISysModuleService moduleService;
    private final ISysRoleService roleService;
    @Autowired
    public OauthRealm(RedisUtils redisUtils, ISysModuleService moduleService, ISysRoleService roleService) {
        this.redisUtils = redisUtils;
        this.moduleService = moduleService;
        this.roleService = roleService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final SysUser user = (SysUser) principals.getPrimaryPrincipal();
        final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(moduleService.getPermByUserId(user.getUserId()));
        info.setRoles(roleService.getRoleNamesByUserId(user.getUserId()));
        return info;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Oauth2Token;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        final Oauth2Token oauth2Token = (Oauth2Token) token;
        final String realToken = oauth2Token.getToken();
        final RedisKeys redisKeys = RedisKeys.SYS_TOKEN.build(realToken);
        final SysUser user = redisUtils.get(redisKeys.getRealKey(), SysUser.class, redisKeys.getExpireTime());
        if (user == null){
            throw new UnsupportedTokenException("token不存在或者已经失效");
        }
        if(user.getStatus() != 1){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        return new SimpleAuthenticationInfo(user, realToken, getName());
    }
}
