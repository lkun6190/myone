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
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.user.dao.ZxsClassDao;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;

/**
 * 人员信息Service
 * 
 * @author xianqinhong
 * @version 2017-06-04
 */
@Service
@Transactional(readOnly = true)
public class ZxsClassService extends CrudService<ZxsClassDao, ZxsClass> {

	public List<ZxsTea> findTeaList(ZxsClass zxsClass) {
		return dao.findTeaList(zxsClass);
	}

	public ZxsClass checkNo(ZxsClass zxsClass) {
		return dao.checkNo(zxsClass);
	}

	public ZxsClass get(String id) {
		return super.get(id);
	}

	@Transactional(readOnly = false)
	public String saveResultId(ZxsClass zxsClass) {

		String resultId = "";

		if (StringUtils.isBlank(zxsClass.getId())) {
			zxsClass.preInsert();
			dao.insert(zxsClass);

		} else {
			zxsClass.preUpdate();
			dao.update(zxsClass);
		}

		try {
			CacheUtils.remove(AdminUtils.CACHE_ZXSCLASS_LIST);
		} catch (Exception ex) {
		}
		return zxsClass.getId();
	}

	public List<ZxsClass> findList(ZxsClass zxsClass) {
		return super.findList(zxsClass);
	}

	public Page<ZxsClass> findPage(Page<ZxsClass> page, ZxsClass zxsClass) {
		return super.findPage(page, zxsClass);
	}

	@Transactional(readOnly = false)
	public void delete(ZxsClass zxsClass) {
		zxsClass.preUpdate();
		super.delete(zxsClass);
		try {
			CacheUtils.remove(AdminUtils.CACHE_ZXSCLASS_LIST);
		} catch (Exception ex) {
		}
	}

	@Transactional(readOnly = false)
	public void batchdelete(String[] ids) {
		for (String id : ids) {
			ZxsClass zxsClass = super.get(id);
			zxsClass.preUpdate();
			dao.delete(zxsClass);

		}
		try {
			CacheUtils.remove(AdminUtils.CACHE_ZXSCLASS_LIST);
		} catch (Exception ex) {
		}

	}

	public List findListmh(ZxsClass zxsClass) {

		return dao.findList(zxsClass);
	}

	@Transactional(readOnly = false)
	public void batchstop(String[] ids) {
		for (String id : ids) {

			dao.stop(Integer.valueOf(id));
		}
	}

}