package com.monkeyframework.web.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class WrapperResponseEntity {

	private static final String SUCCESS_MSG = "操作成功";

	public WrapperResponseEntity() {
	}

	public WrapperResponseEntity(Object data) {
		this.data = data;
	}
	
	public WrapperResponseEntity(Object data, Long count) {
		this.data = data;
		this.count = count;
		this.msg = "";
	}

	public WrapperResponseEntity(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	// 状态
	private int code = 0;

	@JsonInclude(Include.NON_NULL)
	private Long count;

	// 返回信息
	private String msg = SUCCESS_MSG;

	// 响应数据
	@JsonInclude(Include.NON_NULL)
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
