/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	public Office findByZbdwId(Office office1);
	public Office getOfficeByOne(Office office1);
	

	public Office findByZbdwName(Office office1);

	public List<Office> findAllOfficeList(Office office);
	
	
	public void deleteAllStuByOffice(Office office);
	public void deleteAllTeaByOffice(Office office);
	public void deleteAllClassByOffice(Office office);
}
