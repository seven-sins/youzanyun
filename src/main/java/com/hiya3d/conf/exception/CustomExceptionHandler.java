package com.hiya3d.conf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hiya3d.conf.base.Response;

/**
 * 全局异常处理
 * @author seven sins
 * @date 2018年6月2日 下午3:52:36
 */
@RestControllerAdvice
public class CustomExceptionHandler {

	/**
	 * 自定义异常捕获
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HiyaException.class)
	public Response<?> hiyaException(HiyaException e){
		return Response.create(e.getCode(), e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public Response<?> globalException(Exception e){
		return Response.create(400, e.getMessage());
	}
}
