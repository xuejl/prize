package com.aoshi.entity;

import org.apache.commons.lang.StringUtils;

/**
 * 接口响应Bean
 * 
 * @author yangyanchao
 * @date 2016年6月28日
 */
public class R {

	private int status;
	private Object msg = "ok";

	public R() {

	}

	public R(int status) {
		this(status, "");
	}

	public R(Object msg) {
		this(0, msg);
	}

	public R(int status, Object msg) {
		this.status = status;
		this.msg = msg;
		if (status != 0 && (msg == null || StringUtils.isBlank(msg.toString()))) {
			msg = "系统繁忙";
		}
	}

	public Object getMsg() {
		return msg;
	}

	public Object setMsg(Object msg) {
		this.msg = msg;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public R setStatus(int status) {
		this.status = status;
		return this;
	}

}
