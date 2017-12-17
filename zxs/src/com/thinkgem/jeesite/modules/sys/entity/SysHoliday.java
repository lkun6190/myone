/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 节假日Entity
 * @author LLR
 * @version 2017-04-26
 */
public class SysHoliday extends DataEntity<SysHoliday> {
	
	private static final long serialVersionUID = 1L;
	private String hid;		// hid
	private String hyear;		// hyear
	private String hmonth;		// hmonth
	private String hday;		// hday
	private String hdate;		// hdate
	private String htype;		// 1工作日、2节假日
	
	
	private String beginDate;   //开始时间
	private String endDate;     //结束时间
	
	public SysHoliday() {
		super();
	}

	public SysHoliday(String id){
		super(id);
	}

	@Length(min=1, max=64, message="hid长度必须介于 1 和 64 之间")
	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}
	
	@Length(min=0, max=20, message="hyear长度必须介于 0 和 20 之间")
	public String getHyear() {
		return hyear;
	}

	public void setHyear(String hyear) {
		this.hyear = hyear;
	}
	
	@Length(min=0, max=20, message="hmonth长度必须介于 0 和 20 之间")
	public String getHmonth() {
		return hmonth;
	}

	public void setHmonth(String hmonth) {
		this.hmonth = hmonth;
	}
	
	@Length(min=0, max=20, message="hday长度必须介于 0 和 20 之间")
	public String getHday() {
		return hday;
	}

	public void setHday(String hday) {
		this.hday = hday;
	}
	
	@Length(min=0, max=50, message="hdate长度必须介于 0 和 50 之间")
	public String getHdate() {
		return hdate;
	}

	public void setHdate(String hdate) {
		this.hdate = hdate;
	}
	
	@Length(min=0, max=1, message="1工作日、2节假日长度必须介于 0 和 1 之间")
	public String getHtype() {
		return htype;
	}

	public void setHtype(String htype) {
		this.htype = htype;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}