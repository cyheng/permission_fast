package com.doraro.permission_fast.exception;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;


    public static Result error() {
        return error(CodeMsg.SERVER_ERROR);
    }
    public static Result error(String msg) {
        return error(CodeMsg.SERVER_ERROR).put("msg",msg);
    }
    public static Result error(CodeMsg msg) {
        return build(msg);
    }

    private static Result build(CodeMsg msg) {
        Result r = new Result();
        r.put("code", msg.getCode());
        r.put("msg", msg.getMsg());
        return r;
    }

    public static Result ok(String msg) {
        Result r = ok();
        r.put("msg",msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = ok();
        r.putAll(map);
        return r;
    }

    public static Result ok() {
        return build(CodeMsg.SUCCESS);
    }
    public static Result ok(Object data) {
        final Result ok = ok();
        return ok.put("data", data);
    }
    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
