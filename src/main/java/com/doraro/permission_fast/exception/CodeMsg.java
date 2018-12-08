package com.doraro.permission_fast.exception;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


/**
 * 封装常用的错误码和错误信息
 * 后三位表示不同模块的异常
 * @author cyheng
 */

@AllArgsConstructor
@Getter
public enum  CodeMsg {

    /**
     * 5001xx 通用的错误码
     * 5002xx 登录模块
     */
    SUCCESS(200,"success"),

    /**
     * 通用的错误码
     */
    BIND_ERROR  (500101, "参数校验异常：%s"),
    SERVER_ERROR  (500100, "未知异常，请联系管理员"),
    NOT_FOUND  (500101, "路径不存在，请检查路径是否正确"),
    DUPLICATE_KEY(500102,"数据库中已存在该记录"),
    UPDATE_FAIL(500103,"数据库中插入失败，请联系管理员"),
    /**
     * 登录模块
     */
     TOKEN_ERROR  (500210, "token不存在或者已经失效"),
    INVALID_USR_OR_PWD(500211, "账号或密码错误"),
    CAPTCHA_ERROR(500212,"验证码不正确"),
    NOT_AUTH(500213, "没有权限，请联系管理员授权"),
    AUTH_FAIL(500214,"认证错误:%s"),
    INVALID_PWD(500215,"原密码错误"),
    /**
     * 角色模块
     */
    PERM_ERROR(500310,"新增角色的权限，已超出你的权限范围")
    ;
    private int code;
    private String msg;
    public CodeMsg fillArgs(Object... args) {
        this.msg = String.format(this.msg, args);
        return this;
    }

}
