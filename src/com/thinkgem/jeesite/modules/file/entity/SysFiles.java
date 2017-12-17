/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.file.entity;

import com.thinkgem.jeesite.modules.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 附件表Entity
 * @author lijihui
 * @version 2016-11-24
 */
public class SysFiles extends DataEntity<SysFiles> {
	
	private static final long serialVersionUID = 1L;
	private String wjlj;		// 文件路径
	private String wjbt;		// 文件标题
	private String wjlx;		// 文件类型
	private String zbid;		// 附件归属表主键ID
	
	public SysFiles() {
		super();
	}

	public SysFiles(String id){
		super(id);
	}

	@Length(min=0, max=255, message="文件路径长度必须介于 0 和 255 之间")
	public String getWjlj() {
		return wjlj;
	}

	public void setWjlj(String wjlj) {
		this.wjlj = wjlj;
	}
	
	@Length(min=0, max=255, message="文件标题长度必须介于 0 和 255 之间")
	public String getWjbt() {
		return wjbt;
	}

	public void setWjbt(String wjbt) {
		this.wjbt = wjbt;
	}
	
	@Length(min=0, max=64, message="文件类型长度必须介于 0 和 64 之间")
	public String getWjlx() {
		return wjlx;
	}

	public void setWjlx(String wjlx) {
		this.wjlx = wjlx;
	}
	
	@Length(min=0, max=64, message="附件归属表主键ID长度必须介于 0 和 64 之间")
	public String getZbid() {
		return zbid;
	}

	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
	
}