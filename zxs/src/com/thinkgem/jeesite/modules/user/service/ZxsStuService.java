/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.ZxsStuDao;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;

/**
 * 人员信息Service
 * 
 * @author xianqinhong
 * @version 2017-06-04
 */
@Service
@Transactional(readOnly = true)
public class ZxsStuService extends CrudService<ZxsStuDao, ZxsStu> {
	@Transactional(readOnly = false)
	public void clearOrderBySort(){
		dao.clearOrderBySort();
	}
	
	public List<ZxsStu> orderBy(String id) {
		return dao.orderBy(id);
	}

	@Transactional(readOnly = false)
	public void updateAppInfo(AppPlayInfo appPlayInfo) {
		dao.updateAppInfo(appPlayInfo);
	}

	@Transactional(readOnly = false)
	public void deleteByClass(ZxsClass zxsClass) {
		
		zxsClass.preUpdate();
		dao.deleteByClass(zxsClass);
	}

	@Transactional(readOnly = false)
	public void updateToken(ZxsStu zxsStu) {
		dao.updateToken(zxsStu);
	}

	@Transactional(readOnly = false)
	public void stop(ZxsStu zxsStu) {
		dao.stopZxsStu(zxsStu);
	}

	public ZxsStu get(String id) {
		return super.get(id);
	}

	public ZxsStu getUserByStuNo(String stuNo) {
		return dao.getUserByStuNo(stuNo);
	}

	@Transactional(readOnly = false)
	public int saveResultId(ZxsStu ZxsStu) {

		int adminId = dao.insert(ZxsStu);

		return adminId;
	}

	@Transactional(readOnly = false)
	public void saveResult(ZxsStu zxsStu) {
		if (zxsStu.getIsNewRecord()) {
			zxsStu.preInsert();
			dao.insert(zxsStu);
			dao.insertPlayInfo(zxsStu);
		} else {
			zxsStu.preUpdate();
			dao.update(zxsStu);
		}
	}

	public List<ZxsStu> findList(ZxsStu ZxsStu) {
		return super.findList(ZxsStu);
	}

	public Page<ZxsStu> findPage(Page<ZxsStu> page, ZxsStu tsAdmin) {
		return super.findPage(page, tsAdmin);
	}

	@Transactional(readOnly = false)
	public void delete(ZxsStu tsAdmin) {
		tsAdmin.preUpdate();
		super.delete(tsAdmin);
	}

	@Transactional(readOnly = false)
	public void batchdelete(String[] ids) {
		for (String id : ids) {
			ZxsStu admin = super.get(id);
			admin.preUpdate();
			dao.delete(admin);

		}
	}

	@Transactional(readOnly = false)
	public void batchStop(String[] ids) {
		for (String id : ids) {
			//ZxsStu admin = super.get(id);
			dao.stopBatchZxsStu(id);
		}
	}
	
	@Transactional(readOnly = false)
	public void batchStart(String[] ids) {
		for (String id : ids) {
			//ZxsStu admin = super.get(id);
			dao.startBatchZxsStu(id);
		}
	}

	public List findListmh(ZxsStu tsAdmin) {

		return dao.findList(tsAdmin);
	}

	public String selectAcc() {
		return dao.selectAcc();
	}

	@Transactional(readOnly = false)
	public void updateAcc(String stuAcc) {
		dao.updateAcc(stuAcc);
	}
}