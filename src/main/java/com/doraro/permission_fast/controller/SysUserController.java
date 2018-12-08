package com.doraro.permission_fast.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.doraro.permission_fast.annotation.SysLog;
import com.doraro.permission_fast.exception.Result;
import com.doraro.permission_fast.form.PageForm;
import com.doraro.permission_fast.form.PasswordForm;
import com.doraro.permission_fast.form.UserAddForm;
import com.doraro.permission_fast.form.UserUpdateForm;
import com.doraro.permission_fast.model.SysUser;
import com.doraro.permission_fast.service.ISysUserService;
import com.doraro.permission_fast.util.EncptyUtils;
import com.doraro.permission_fast.util.HttpContextUtils;
import com.doraro.permission_fast.util.PageView;
import com.doraro.permission_fast.validator.AddGroup;
import com.doraro.permission_fast.validator.ValidatorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.doraro.permission_fast.util.Constant.TOKEN_KEY;
import static com.doraro.permission_fast.util.ShiroUserUtil.getCurrentUserId;
import static com.doraro.permission_fast.util.ShiroUserUtil.getUser;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController  {
    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("/list")
    @RequiresPermissions("sys_user:view")
    public Result getAllUser(PageForm param) {
        final IPage<SysUser> result = sysUserService.page(PageForm.toQueryPage(param));
        final PageView pageView = new PageView(result);
        return Result.ok(pageView);
    }


    @GetMapping("/info")
    @RequiresPermissions("sys_user:view")
    public Result getCurrentUser() {
        return Result.ok(getUser());
    }

    @GetMapping("/info/{id}")
    @RequiresPermissions("sys_user:view")
    public Result getUserById(@PathVariable("id") Long id) {
        return Result.ok(sysUserService.getById(id));
    }

    @PostMapping("/password")
    @SysLog("修改密码")
    @RequiresPermissions("sys_user:update")
    public Result changePassword(@RequestBody PasswordForm passwordForm, HttpServletRequest request) {
        ValidatorUtils.validate(passwordForm);
        final SysUser sysUser =  getUser();
        String token = HttpContextUtils.getAttr(request, TOKEN_KEY);
        boolean flag = sysUserService.changePassword(sysUser, passwordForm.getPassword(), passwordForm.getNewPassword(), token);
        if (!flag) {
            return Result.error("修改密码失败!");
        }
        return Result.ok();
    }

    @PostMapping("/create")
    @RequiresPermissions("sys_user:create")
    public Result createUser(@RequestBody UserAddForm userForm){
        ValidatorUtils.validate(userForm, AddGroup.class);
        String salt = EncptyUtils.createSalt(20);
        final SysUser user = new SysUser()
                .setPassword(EncptyUtils.formPwdToDb(userForm.getPassword(), salt))
                .setSalt(salt)
                .setUsername(userForm.getUsername())
                .setMail(userForm.getMail())
                .setCreateUserId(getCurrentUserId())
                .setStatus(0)
                .setCreateTime(LocalDateTime.now());
        final boolean ok = sysUserService.save(user);
        return ok?  Result.ok():Result.error();
    }

    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys_user:update")
    public Result updateUser(@RequestBody UserUpdateForm userForm){
        final SysUser user = new SysUser();
        BeanUtils.copyProperties(userForm,user);
        final boolean ok = sysUserService.updateById(user);
        return ok?  Result.ok():Result.error();
    }
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys_user:delete")
    public Result createUser(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds, getCurrentUserId())){
            return Result.error("当前用户不能删除");
        }
        if (sysUserService.count() - userIds.length < 1) {
            return Result.error("需要存在至少1个用户");
        }
        final boolean ok = sysUserService.removeByIds(Arrays.asList(userIds));
        return ok?  Result.ok():Result.error();
    }

}
