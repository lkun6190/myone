/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.AppPlayInfoDao;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;

/**
 * 
 * 
 * @author 
 * @version 2017-10-04
 */
@Service
@Transactional(readOnly = true)
public class AppPlayInfoService extends CrudService<AppPlayInfoDao, AppPlayInfo> {

	

	
	@Transactional(readOnly = false)
	public int saveResultId(AppPlayInfo appPlayInfo) {

		int adminId = dao.insert(appPlayInfo);

		return adminId;
	}

	public List<AppPlayInfo> findList(AppPlayInfo appPlayInfo) {
		return super.findList(appPlayInfo);
	}

	public Page<AppPlayInfo> findPage(Page<AppPlayInfo> page, AppPlayInfo appPlayInfo) {
		return super.findPage(page, appPlayInfo);
	}

	@Transactional(readOnly = false)
	public void delete(AppPlayInfo appPlayInfo) {
		super.delete(appPlayInfo);
	}

	@Transactional(readOnly = false)
	public void batchdelete(String[] ids) {
		for (String id : ids) {
			AppPlayInfo admin = super.get(id);
			dao.delete(admin);

		}
	}

	public List findListmh(AppPlayInfo appPlayInfo) {

		return dao.findList(appPlayInfo);
	}

}