package com.joviansoft.framework.core.exceptions;

public enum BusinessErrorCode implements ErrorCode {

	VALUE_REQUIRED(1),
	INVALID_FORMAT(8),
	VALUE_TOO_SHORT(203),
	VALUE_TOO_LONGS(204);

	private final int number;

	private BusinessErrorCode(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return number;
	}

}
