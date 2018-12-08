package com.doraro.permission_fast.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StringMapperTest {
    @Autowired
    private SysRoleMapper mapper;

    @Test
    public void getRoleByUserId() {
        final Set<String> roleNamesByUserIds = mapper.getRoleNamesByUserId(2L);
        System.out.println("role = " + roleNamesByUserIds);
        System.out.println("role = " +  mapper.getRoleNamesByUserId(1L));
    }
}