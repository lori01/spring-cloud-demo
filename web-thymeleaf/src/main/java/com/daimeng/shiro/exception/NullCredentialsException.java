package com.daimeng.shiro.exception;

import org.apache.shiro.ShiroException;

public class NullCredentialsException extends ShiroException{

	public NullCredentialsException() {
	}

	public NullCredentialsException(String message) {
		super(message);
	}

	public NullCredentialsException(Throwable cause) {
		super(cause);
	}

	public NullCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}
}
