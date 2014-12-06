package com.common;

import com.google.gson.Gson;

public class WebResponse {
	private boolean success = true;
	private String msg = "";
	private String dataJsonStr = "";
	private Object retData = null;
	
	public WebResponse() {
		super();
	}

	public WebResponse(boolean success, String msg, Object retData) {
		super();
		this.success = success;
		this.msg = msg;
		this.retData = retData;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getRetData() {
		return retData;
	}

	public void setRetData(Object retData) {
		this.retData = retData;
	}

	public String getDataJsonStr() {
		return dataJsonStr;
	}

	public void setDataJsonStr(String dataJsonStr) {
		this.dataJsonStr = dataJsonStr;
	}
}
