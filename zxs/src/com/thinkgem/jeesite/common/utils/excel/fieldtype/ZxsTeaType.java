/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.excel.fieldtype;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;

/**
 * 字段类型转换
 * 
 * @author ThinkGem
 * @version 2013-03-10
 */
public class ZxsTeaType {

	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		
		for (ZxsTea e : AdminUtils.getZxsTeaList()) {
			
			if (StringUtils.trimToEmpty(val).equals(e.getTeaName())) {
				return e;
			}
		}
	
		return null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null && ((ZxsTea) val).getTeaName() != null) {
			
			return ((ZxsTea) val).getTeaName();
		}
		return "";
	}
}
