package com.doraro.permission_fast.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.doraro.permission_fast.exception.Result;
import com.doraro.permission_fast.form.PageForm;
import com.doraro.permission_fast.model.SysLogModel;
import com.doraro.permission_fast.service.ISysLogService;
import com.doraro.permission_fast.util.PageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cyheng
 * @since 2018-12-06
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private ISysLogService sysLogService;

    @GetMapping("/list")
    public Result getSysLogList(PageForm pageForm){
        final IPage<SysLogModel> page = sysLogService.page(PageForm.toQueryPage(pageForm));
        return Result.ok(new PageView(page));
    }
}
