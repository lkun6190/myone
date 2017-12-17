/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.user.dao.ZxsTeaDao;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;

/**
 * 人员信息Service
 * 
 * @author xianqinhong
 * @version 2017-06-04
 */
@Service
@Transactional(readOnly = true)
public class ZxsTeaService extends CrudService<ZxsTeaDao, ZxsTea> {
	
	@Autowired
	ZxsStuService zxsStuService;
	
	@Transactional(readOnly = false)
	public void saveResult(ZxsTea zxsTea){
		if (zxsTea.getIsNewRecord()) {
			zxsTea.preInsert();
			dao.insert(zxsTea);
			
			dao.insertPlayInfo(zxsTea);
			
		} else {
			zxsTea.preUpdate();
			dao.update(zxsTea);
		}
	}
	
	public AppPlayInfo getAppInfo(AppPlayInfo appPlayInfo) {
		return dao.getAppInfo(appPlayInfo);
	}
	
	@Transactional(readOnly = false)
	public void updateToken(ZxsTea zxsTea) {
		dao.updateToken(zxsTea);
	}

	
	public ZxsTea getUserByTeaNo(String teaNo) {
		return dao.getUserByTeaNo(teaNo);
	}
	
	public ZxsTea get(String id) {
		return super.get(id);
	}
	
	@Transactional(readOnly = false)
	public int saveResultId(ZxsTea ZxsTea) {
		
		int adminId = dao.insert(ZxsTea);
		try{
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		}catch(Exception ex){}
		return adminId;
	}

	public List<ZxsTea> findList(ZxsTea ZxsTea) {
		return super.findList(ZxsTea);
	}

	public Page<ZxsTea> findPage(Page<ZxsTea> page, ZxsTea tsAdmin) {
		return super.findPage(page, tsAdmin);
	}


	@Transactional(readOnly = false)
	public void delete(ZxsTea tsAdmin) {
		tsAdmin.preUpdate();
		super.delete(tsAdmin);
		try{
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		}catch(Exception ex){}
	}

	@Transactional(readOnly = false)
	public void batchdelete(String[] ids) {
		for (String id : ids) {
			ZxsTea admin = super.get(id);
			admin.preUpdate();
			dao.delete(admin);
			
			
		}
		try{
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		}catch(Exception ex){}
	}

	public List findListmh(ZxsTea tsAdmin) {

		return dao.findList(tsAdmin);
	}

	@Transactional(readOnly = false)
	public void batchstop(String[] ids) {
		for (String id : ids) {
			dao.stopBatchZxsTea(id);
		}
		try{
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		}catch(Exception ex){}
	}
	
	@Transactional(readOnly = false)
	public void batchstart(String[] ids) {
		for (String id : ids) {
			dao.startBatchZxsTea(id);
		}
		try{
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		}catch(Exception ex){}
	}
	
	@Transactional(readOnly = false)
	public void stop(ZxsTea zxsTea){
		dao.stopZxsTea(zxsTea);
		try{
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		}catch(Exception ex){}
	}
}