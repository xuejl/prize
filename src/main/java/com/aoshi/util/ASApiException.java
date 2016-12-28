package com.aoshi.util;

/**
 * 通用应用异常
 * 
 * @author yangyanchao
 * @date 2016年7月14日
 */
public class ASApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int status;
	private String errMsg;
	private int errType; // 0.json形式返回错误信息（默认） 1.返回网页的形式显示错误信息

	public int getErrType() {
		return errType;
	}

	public void setErrType(int errType) {
		this.errType = errType;
	}

	public ASApiException(String msg) {
		super(msg);
		errMsg = msg;
		status = 1001;
	}

	public ASApiException(int status, String errMsg) {
		super(errMsg);
		this.status = status;
		this.errMsg = errMsg;
	}

	public ASApiException(int status, String errMsg, int errType) {
		super(errMsg);
		this.status = status;
		this.errMsg = errMsg;
		this.errType = errType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
