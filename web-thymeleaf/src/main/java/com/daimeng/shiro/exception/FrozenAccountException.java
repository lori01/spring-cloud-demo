package com.daimeng.shiro.exception;

import org.apache.shiro.ShiroException;

public class FrozenAccountException extends ShiroException{

	public FrozenAccountException() {
	}

	public FrozenAccountException(String message) {
		super(message);
	}

	public FrozenAccountException(Throwable cause) {
		super(cause);
	}

	public FrozenAccountException(String message, Throwable cause) {
		super(message, cause);
	}
}
