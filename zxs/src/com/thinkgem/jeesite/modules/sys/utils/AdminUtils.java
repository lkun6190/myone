/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.AreaDao;
import com.thinkgem.jeesite.modules.sys.dao.MenuDao;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Menu;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.user.dao.ZxsClassDao;
import com.thinkgem.jeesite.modules.user.dao.ZxsTeaDao;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;

/**
 * 用户工具类
 * 
 * @author ThinkGem
 * @version 2013-12-05
 */
public class AdminUtils {

	private static ZxsClassDao zxsClassDao = SpringContextHolder
			.getBean(ZxsClassDao.class);
	private static ZxsTeaDao zxsTeaDao = SpringContextHolder
			.getBean(ZxsTeaDao.class);
	public static final String CACHE_ZXSCLASS_LIST = "ZXSCLASS_LIST";
	public static final String CACHE_ZXSTEA_LIST = "ZXSTEA_LIST";

	/**
	 * 获取当前用户有权限访问的班级列表
	 * 
	 * @return
	 */
	public static List<ZxsTea> getZxsTeaList() {
		@SuppressWarnings("unchecked")
		List<ZxsTea> zxsTeaList = (List<ZxsTea>) getCache(CACHE_ZXSTEA_LIST);
		if (zxsTeaList == null) {
			ZxsTea zxsTea = new ZxsTea();

			zxsTea.setOffice(UserUtils.getUser().getOffice());
			zxsTeaList = zxsTeaDao.findList(zxsTea);
			putCache(CACHE_ZXSTEA_LIST, zxsTeaList);
		}
		return zxsTeaList;
	}

	/**
	 * 获取当前用户有权限访问的班级列表
	 * 
	 * @return
	 */
	public static List<ZxsClass> getZxsClassList() {
		@SuppressWarnings("unchecked")
		List<ZxsClass> zxsClassList = (List<ZxsClass>) getCache(CACHE_ZXSCLASS_LIST);
		if (zxsClassList == null) {
			ZxsClass zxsClass = new ZxsClass();
			zxsClass.setOffice(UserUtils.getUser().getOffice());
			zxsClassList = zxsClassDao.findList(zxsClass);
			putCache(CACHE_ZXSCLASS_LIST, zxsClassList);
		}

		return zxsClassList;
	}

	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		// Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		// getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		// getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static List<Dict> getYearList() {
		List<Dict> list = new ArrayList<Dict>();
		for (int i = 2016; i <= 2030; i++) {
			Dict dict = new Dict();
			dict.setValue(i + "");
			dict.setLabel(i + "年");
			list.add(dict);
		}
		return list;
	}

	public static List<Dict> getMonthList() {
		List<Dict> list = new ArrayList<Dict>();
		for (int i = 1; i <= 12; i++) {
			Dict dict = new Dict();
			dict.setValue(i + "");
			dict.setLabel(i + "月");
			list.add(dict);
		}
		return list;
	}

}
