package com.jiniguez.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.jiniguez.school.dto.ErrorDTO;
import com.jiniguez.school.exception.NotFoundException;


@ControllerAdvice(basePackages = { "com.jiniguez.school" })
public class ErrorController {

	@ResponseBody
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorDTO error(NotFoundException e) {
		return new ErrorDTO(404, e.getMessage());
	}
}