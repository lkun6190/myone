/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
	public int countZslx();

	public int countXxsz();
	public int countLabel(String label);
	public Dict findByLabel(String label);

	public List<Dict> findListByPId(String pId);
	
/*	public void update();*/
}
