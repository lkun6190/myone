/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.file.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.file.entity.SysFiles;
import com.thinkgem.jeesite.modules.file.dao.SysFilesDao;

/**
 * 附件表Service
 * @author lijihui
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class SysFilesService extends CrudService<SysFilesDao, SysFiles> {

	public SysFiles get(String id) {
		return super.get(id);
	}
	
	public List<SysFiles> findList(SysFiles sysFiles) {
		return super.findList(sysFiles);
	}
	
	public Page<SysFiles> findPage(Page<SysFiles> page, SysFiles sysFiles) {
		return super.findPage(page, sysFiles);
	}
	
	@Transactional(readOnly = false)
	public void save(SysFiles sysFiles) {
		super.save(sysFiles);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysFiles sysFiles) {
		super.delete(sysFiles);
	}

	@Transactional(readOnly = false)
	public void batchdelete(String [] ids, User update_by) {
		 for(String id : ids){
			 SysFiles sysFiles =super.get(id);
			 sysFiles.setUpdateBy(update_by);
			 super.delete(sysFiles);
		 }
	}
	
	@Transactional(readOnly = false)
	public Page<SysFiles> listByAll(Page<SysFiles> page, SysFiles sysFiles) {
		sysFiles.setPage(page);
		page.setList(dao.listByAll(sysFiles));
		return page;
	}
	
}