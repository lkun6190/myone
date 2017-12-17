package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;

/**
 * 人员信息DAO接口
 * 
 * @desc: rtedu
 * @author: lkun
 * @createTime: 2017年8月7日 下午4:44:39
 * @history:
 * @version: v1.0
 */
@MyBatisDao
public interface AppPlayInfoDao extends CrudDao<AppPlayInfo> {
	

}