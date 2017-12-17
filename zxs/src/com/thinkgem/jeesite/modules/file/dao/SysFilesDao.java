/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.file.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.file.entity.SysFiles;

/**
 * 附件表DAO接口
 * @author lijihui
 * @version 2016-11-24
 */
@MyBatisDao
public interface SysFilesDao extends CrudDao<SysFiles> {
	public List<SysFiles> listByAll(SysFiles sysFiles);
}