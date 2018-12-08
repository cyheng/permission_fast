package com.doraro.permission_fast.exception;

import lombok.Data;
import lombok.Getter;

import static com.doraro.permission_fast.exception.CodeMsg.SERVER_ERROR;

@Getter
public class ApiGlobalException extends RuntimeException  {
    private CodeMsg codeMsg;

    public ApiGlobalException(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

}
