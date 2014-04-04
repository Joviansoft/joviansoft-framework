package com.joviansoft.framework.core.exceptions;

public enum ServiceErrorCode implements ErrorCode {
	SERVICE_UNAVAILABLEE(1), //服务不存在
	SERVICE_TIMEOUT(2), //服务超时
    INVALID_APPKEY(3), //不合法的appkey 和应用校验码
    INVALID_SESSION(4), //session不存在或已过期
    INVALID_SIGN(5),//错误的签名
    INVALID_ARGUMENT(6), //参数不合法
    INVALID_TIMESTAMP(7); //时间戳错误

	private final int number;

	private ServiceErrorCode(int number) {
		this.number = number;
	}
	
	@Override
	public int getNumber() {
		return number;
	}

}
