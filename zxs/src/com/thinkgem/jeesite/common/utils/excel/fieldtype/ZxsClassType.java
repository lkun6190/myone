/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel.fieldtype;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;

/**
 * 字段类型转换
 * 
 * @author ThinkGem
 * @version 2013-03-10
 */
public class ZxsClassType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		
		for (ZxsClass e : AdminUtils.getZxsClassList()) {
			
			if (StringUtils.trimToEmpty(val).equals(e.getClassNo())) {
				return e;
			}
		}
	
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((ZxsClass) val).getClassNo() != null) {
			return ((ZxsClass) val).getClassNo();
		}
		return "";
	}
}
