/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysHoliday;

/**
 * 节假日DAO接口
 * @author LLR
 * @version 2017-04-26
 */
@MyBatisDao
public interface SysHolidayDao extends CrudDao<SysHoliday> {
	
}