package com.thinkgem.jeesite.modules.sys.entity;

/**
 * 返回的封装
 * 
 * @desc: rtedu
 * @author: lkun
 * @createTime: 2017年8月28日 下午3:06:52
 * @history:
 * @version: v1.0
 */
public class ResultObject {

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getResult() {
		return result;
	}

	public void setResult(Long result) {
		this.result = result;
	}

	private String msg = "";
	private Long result = 0L;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private Object data;
}
