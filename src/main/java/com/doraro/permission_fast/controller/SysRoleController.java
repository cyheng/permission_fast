package com.doraro.permission_fast.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.doraro.permission_fast.annotation.SysLog;
import com.doraro.permission_fast.exception.CodeMsg;
import com.doraro.permission_fast.exception.Result;
import com.doraro.permission_fast.form.PageForm;
import com.doraro.permission_fast.form.RoleAddForm;
import com.doraro.permission_fast.form.RoleUpdateForm;
import com.doraro.permission_fast.form.UserUpdateForm;
import com.doraro.permission_fast.model.SysRole;
import com.doraro.permission_fast.model.SysUser;
import com.doraro.permission_fast.service.ISysModuleService;
import com.doraro.permission_fast.service.ISysRoleService;
import com.doraro.permission_fast.util.Constant;
import com.doraro.permission_fast.util.PageView;
import com.doraro.permission_fast.util.ShiroUserUtil;
import com.doraro.permission_fast.validator.AddGroup;
import com.doraro.permission_fast.validator.ValidatorUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyheng
 * @since 2018-12-02
 */
@RestController
@RequestMapping("/sys-role")
public class SysRoleController {
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysModuleService moduleService;

    @GetMapping("/list")
    @RequiresPermissions("sys_role:view")
    public Result listRole(PageForm pageForm){
        final IPage<SysRole> page = sysRoleService.listRole(PageForm.toQueryPage(pageForm));
        final PageView pageView = new PageView(page);
        return Result.ok(pageView);
    }

    @GetMapping("/info/{id}")
    @RequiresPermissions("sys_role:view")
    public Result getRoleById(@PathVariable("id") Long id) {
        return Result.ok(sysRoleService.getRoleById(id));
    }
    @SysLog("创建角色")
    @PostMapping("/create")
    @RequiresPermissions("sys_role:create")
    public Result createRole(@RequestBody RoleAddForm roleAddForm){
        ValidatorUtils.validate(roleAddForm);
        final SysRole role = new SysRole();
        BeanUtils.copyProperties(roleAddForm,role);
        role.setCreateTime(LocalDateTime.now());
        role.setCreateUserId(ShiroUserUtil.getCurrentUserId());
        sysRoleService.saveRole(role,roleAddForm.getModuleIdList());
        return  Result.ok() ;
    }

    @SysLog("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("sys_role:update")
    public Result updateRole(@RequestBody RoleUpdateForm roleForm){
        ValidatorUtils.validate(roleForm);
        final SysRole role = new SysRole();
        BeanUtils.copyProperties(roleForm,role);
        role.setCreateTime(LocalDateTime.now());
        role.setCreateUserId(ShiroUserUtil.getCurrentUserId());
        sysRoleService.updateRole(role,roleForm.getModuleIdList());
        return  Result.ok() ;
    }
    @SysLog("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys_role:delete")
    public Result deleteRole(@RequestBody Long[] roles){
        if (roles == null || roles.length == 0){
            return Result.error(CodeMsg.BIND_ERROR.fillArgs("role id 不能为空"));
        }
        sysRoleService.deleteRoleByIds(roles);
        return   Result.ok() ;
    }

}
