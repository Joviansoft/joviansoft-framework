package com.joviansoft.framework.core.exceptions;

public enum ValidationErrorCode implements ErrorCode {
	
	VALUE_REQUIRED(201),
	INVALID_FORMAT(202),
	VALUE_TOO_SHORT(203),
	VALUE_TOO_LONGS(204);

	private final int number;

	private ValidationErrorCode(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return number;
	}

}
