package com.jiniguez.school.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -4729802641454161857L;

	private static final String MSG = "La entidad buscada no existe";

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException() {
		super(MSG);
	}
}
