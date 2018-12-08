package com.doraro.permission_fast.exception;


import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.doraro.permission_fast.exception.CodeMsg.DUPLICATE_KEY;
import static com.doraro.permission_fast.exception.CodeMsg.NOT_AUTH;
import static com.doraro.permission_fast.exception.CodeMsg.NOT_FOUND;

/**
 * 异常处理器
 *
 * @author cyheng
 */
@RestControllerAdvice
public class ApiExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(ApiGlobalException.class)
	public Result handleRRException(ApiGlobalException e){
		return Result.error(e.getCodeMsg());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public Result handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return Result.error(NOT_FOUND);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return Result.error(DUPLICATE_KEY);
	}

	@ExceptionHandler(AuthorizationException.class)
	public Result handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return Result.error(NOT_AUTH);
	}

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e){
		logger.error(e.getMessage(), e);
		return Result.error();
	}
}
