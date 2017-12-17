package com.thinkgem.jeesite.modules.user.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;

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
public interface ZxsTeaDao extends CrudDao<ZxsTea> {
	void stop(Integer id);
	
	void stopBatchZxsTea(String id);
	void startBatchZxsTea(String id);

	public ZxsTea getUserByTeaNo(String zxsTea);

	public void stopZxsTea(ZxsTea zxsTea);
	
	
	public void updateToken(ZxsTea zxsTea);
	
	public AppPlayInfo getAppInfo(AppPlayInfo appPlayInfo);
	
	public void insertPlayInfo(ZxsTea zxsTea);
}