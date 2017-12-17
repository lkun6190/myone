/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;


import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;
import com.thinkgem.jeesite.modules.user.entity.ZxsVersion;

/**
 * 人员信息DAO接口
 * 
 * @desc: rtedu
 * @author: lkun
 * @createTime: 2017年8月7日 下午4:44:39
 * @history:
 * @version: v1.0
 */
@MyBatisDao
public interface ZxsVersionDao extends CrudDao<ZxsVersion> {
	public ZxsVersion findFirst();
	
}