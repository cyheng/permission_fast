package com.doraro.permission_fast.util;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 管理redis前缀和生存周期（单位秒）
 * @author cyheng
 */
@Getter
public class    RedisKeys {
    /**
     * 系统登录验证码,5分钟
     */
    public static final RedisKeys SYS_CAP = new RedisKeys("sys:cap:",300);
    /**
     * 登录token,1天
     */
    public static final RedisKeys SYS_TOKEN = new RedisKeys("sys:token:",60*60*24);
    private final String prefix;
    private String realKey;
    private long expireTime;
    private RedisKeys(String prefix,long expireTime) {
        this.prefix = prefix;
        this.expireTime = expireTime;
    }
    private RedisKeys(String prefix,String realKey,long expireTime){
        this.prefix = prefix;
        this.expireTime = expireTime;
        this.realKey = realKey;
    }

    public RedisKeys build(String... keys) {
        return new RedisKeys(this.prefix,this.prefix + StringUtils.join(keys,":"),this.expireTime);
    }


}
