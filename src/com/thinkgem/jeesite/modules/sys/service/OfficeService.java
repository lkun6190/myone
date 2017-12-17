/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * 
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	
	@Transactional(readOnly = false)
	public Office getOfficeByOne(Office office) {
		return dao.getOfficeByOne(office);
	}
	
	@Transactional(readOnly = false)
	public void deleteAllStuByOffice(Office office) {
		dao.deleteAllStuByOffice(office);
	}
	@Transactional(readOnly = false)
	public void deleteAllTeaByOffice(Office office) {
		dao.deleteAllTeaByOffice(office);
	}
	@Transactional(readOnly = false)
	public void deleteAllClassByOffice(Office office) {
		dao.deleteAllClassByOffice(office);
	}

	public List<Office> findAll() {
		return UserUtils.getOfficeList();
	}

	public List<Office> findAllOfficeList(Office office) {
		return dao.findAllOfficeList(office);
	}

	public List<Office> findList(Boolean isAll) {

		if (isAll != null && isAll) {
			return UserUtils.getOfficeAllList();
		} else {
			return UserUtils.getOfficeList();
		}
	}

	@Transactional(readOnly = true)
	public List<Office> findList(Office office, String offAid) {

		if (office == null) {
			office = UserUtils.getUser().getOffice();
		}
		String ids = office.getParentIds();
		office.setParentIds(ids + offAid + "," + "%");
		List<Office> list = dao.findByParentIdsLike(office);
		office.setParentIds(ids);
		return list;
	}

	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public Office findByZbdwId(Office office1) {
		// TODO Auto-generated method stub
		return dao.findByZbdwId(office1);
	}

	@Transactional(readOnly = false)
	public Office findByZbdwName(Office office1) {
		// TODO Auto-generated method stub
		return dao.findByZbdwName(office1);
	}
}
