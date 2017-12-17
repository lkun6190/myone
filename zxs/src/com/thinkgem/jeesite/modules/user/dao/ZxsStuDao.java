/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;

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
public interface ZxsStuDao extends CrudDao<ZxsStu> {
	void stopBatchZxsStu(String id);
	void startBatchZxsStu(String id);
	
	public void stopZxsStu(ZxsStu zxsStu);

	public ZxsStu getUserByStuNo(String stuNo);

	public void updateToken(ZxsStu zxsStu);

	public void deleteByClass(ZxsClass zxsClass);

	public String selectAcc();

	public void updateAcc(String stuAcc);

	public void updateAppInfo(AppPlayInfo appPlayInfo);
	
	public void insertPlayInfo(ZxsStu zxsStu);
	
	
	public List<ZxsStu> orderBy(@Param("id") String id);
	
	
	public void clearOrderBySort();
}