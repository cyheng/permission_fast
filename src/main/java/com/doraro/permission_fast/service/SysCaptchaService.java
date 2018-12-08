package com.doraro.permission_fast.service;

import com.doraro.permission_fast.exception.ApiGlobalException;

import com.doraro.permission_fast.util.RedisKeys;
import com.doraro.permission_fast.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.doraro.permission_fast.exception.CodeMsg.BIND_ERROR;

@Service
public class SysCaptchaService {
    @Autowired
    private RedisUtils redisUtils;
    public void saveCaptcha(String uuid, String code) {
        if (StringUtils.isBlank(uuid)){
            throw new ApiGlobalException(BIND_ERROR.fillArgs("uuid不能为空"));
        }
        redisUtils.set(RedisKeys.SYS_CAP.build(uuid),code);

    }

    public boolean checkCaptcha(String uuid,String captcha) {
        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(captcha)){
            return false;
        }
        final RedisKeys keys = RedisKeys.SYS_CAP.build(uuid);
        final String code = redisUtils.get(keys.getRealKey());
        if(code == null || !code.equals(captcha)){
            return false;
        }
        redisUtils.delete(keys.getRealKey());
        return true;
    }
}
