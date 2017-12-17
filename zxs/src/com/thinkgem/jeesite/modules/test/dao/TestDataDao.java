/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.test.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.test.entity.TestData;

/**
 * 主务主表DAO接口
 * @author xianqinhong
 * @version 2016-11-25
 */
@MyBatisDao
public interface TestDataDao extends CrudDao<TestData> {
	
}