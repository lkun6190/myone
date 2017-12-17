/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.user.dao.ZxsVersionDao;
import com.thinkgem.jeesite.modules.user.entity.ZxsVersion;

/**
 * 人员信息Service
 * 
 * @author xianqinhong
 * @version 2017-06-04
 */
@Service
@Transactional(readOnly = true)
public class ZxsVersionService extends CrudService<ZxsVersionDao, ZxsVersion> {
	public ZxsVersion findFirst() {
		return dao.findFirst();
	}

}