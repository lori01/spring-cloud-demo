package com.daimeng.shiro.exception;

import org.apache.shiro.ShiroException;

public class NullAccountException extends ShiroException{

	public NullAccountException() {
	}

	public NullAccountException(String message) {
		super(message);
	}

	public NullAccountException(Throwable cause) {
		super(cause);
	}

	public NullAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
