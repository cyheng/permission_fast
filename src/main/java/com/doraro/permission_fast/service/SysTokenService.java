package com.doraro.permission_fast.service;

import com.doraro.permission_fast.model.SysUser;
import com.doraro.permission_fast.util.EncptyUtils;
import com.doraro.permission_fast.util.RedisKeys;
import com.doraro.permission_fast.util.RedisUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysTokenService {
    @Autowired
    private RedisUtils redisUtils;

    public String genToken(SysUser user) {
        if (user == null) {
            return null;
        }
        final String token = EncptyUtils.createUUID();
        redisUtils.set(RedisKeys.SYS_TOKEN.build(token),user);
        return token;
    }

    public void invalidToken(String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }
        redisUtils.delete(RedisKeys.SYS_TOKEN.build(token).getRealKey());

    }
}
