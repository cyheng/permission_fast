package com.doraro.permission_fast.service.impl;

import com.doraro.permission_fast.service.ISysModuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysModuleServiceImplTest {
    @Autowired
    private ISysModuleService sysModuleService;
    @Test
    public void getPermByUserId() {
        System.out.println(sysModuleService.getPermByUserId(2L));
        System.out.println(sysModuleService.getPermByUserId(1L));
    }
}