package com.jiniguez.school.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ErrorDTO implements Serializable{
	private static final long serialVersionUID = -516841391570240331L;
	private Integer code;
	private String msg;
	
	public ErrorDTO(Integer c, String m) {
		super();
		code = c; msg = m;
	}
	
}
