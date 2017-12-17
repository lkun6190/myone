/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.ZxsTotalDao;
import com.thinkgem.jeesite.modules.user.entity.ZxsTotal;

/**
 * 人员信息Service
 * 
 * @author xianqinhong
 * @version 2017-06-04
 */
@Service
@Transactional(readOnly = true)
public class ZxsTotalService extends CrudService<ZxsTotalDao, ZxsTotal> {
	
	@Transactional(readOnly = false)
	public void batchSave(List<ZxsTotal> list){
		dao.batchSave(list);
	}

	public ZxsTotal get(String id) {
		return super.get(id);
	}
	
	public ZxsTotal getMonth(ZxsTotal zxsTotal){
		return dao.getMonth(zxsTotal);
	}
	
	
	@Transactional(readOnly = false)
	public int saveResultId(ZxsTotal zxsTotal) {
		
		int adminId = dao.insert(zxsTotal);
		
		return adminId;
	}

	public List<ZxsTotal> findList(ZxsTotal zxsTotal) {
		return super.findList(zxsTotal);
	}

	public Page<ZxsTotal> findPage(Page<ZxsTotal> page, ZxsTotal zxsTotal) {
		return super.findPage(page, zxsTotal);
	}


	@Transactional(readOnly = false)
	public void delete(ZxsTotal zxsTotal) {
		
		super.delete(zxsTotal);
		
	}

	@Transactional(readOnly = false)
	public void batchdelete(String[] ids) {
		for (String id : ids) {
			ZxsTotal zxsTotal = super.get(id);
			dao.delete(zxsTotal);
			
		}
		
	}
	public List findListmh(ZxsTotal zxsTotal) {

		return dao.findList(zxsTotal);
	}
}