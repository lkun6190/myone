/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.ResultObject;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * 
 * @author ThinkGem
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController {

	@Autowired
	private SessionDAO sessionDAO;
	
	
	//http://localhost:8888/zxs/admin/ipToArea?type=1
	/**
	 * 管理登录
	 *//*
	@RequestMapping(value = "${adminPath}/ipToArea", method = RequestMethod.GET)
	public String ipToArea(HttpServletRequest request, HttpServletResponse response, Model model) {
		String type=request.getParameter("type");
		
		String loginArr="http://118.190.155.177/zxs/a/login";//国内
		if("2".equals(type)){
		loginArr="http://47.52.151.161/zxs/a/login";//香港
		}
		return "redirect:" + loginArr;
	}*/

	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

		/*
		 * // 默认页签模式 String tabmode = CookieUtils.getCookie(request, "tabmode");
		 * if (tabmode == null) { CookieUtils.setCookie(response, "tabmode",
		 * "1"); }
		 */

		if (logger.isDebugEnabled()) {
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			CookieUtils.setCookie(response, "LOGINED", "false");
		}

		// 如果已经登录，则跳转到管理首页
		if (principal != null && !principal.isMobileLogin()) {
			return "redirect:" + adminPath;
		}

		String m = request.getParameter("mobileLogin") == null ? "n" : request.getParameter("mobileLogin");
		// 是手机登陆进来的
		if ("true".equals(m)) {
			Session session = UserUtils.getSession();
			session.setAttribute("isMobile", true);
			ResultObject resultObject = new ResultObject();

			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("id", UserUtils.getUser().getId());
			obj.put("loginName", UserUtils.getUser().getLoginName());
			obj.put("name", UserUtils.getUser().getName());
			obj.put("mobileLogin", "true");
			obj.put("sessionid", request.getSession().getId());
			obj.put("jeesitesessionid", UserUtils.getSession().getId());
			List<Role> list = UserUtils.getUser().getRoleList();
			obj.put("userType", UserUtils.getUser().getUserType());
			obj.put("userTypeName", DictUtils.getDictLabel(UserUtils.getUser().getUserType(), "sys_user_type", ""));

			resultObject.setData(obj);

			return renderString(response, resultObject);
			// return "modules/app/appLogin";
		} else {
			// 当前会话登录 使之有验证码
			isValidateCodeLogin(UserUtils.getSession().getId().toString(), true, false);
			
			
			if("admin.abacus-pro.com".equals(request.getServerName())||"47.52.151.161".equals(request.getServerName())){
				model.addAttribute("iparea", "2");
			}else{
			
			model.addAttribute("iparea", "1");
			}
			return "modules/sys/sysLogin_temp";
		}
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

		if (logger.isDebugEnabled()) {
			logger.debug("login fail, active session size: {}, message: {}, exception: {}",
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}

		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)) {
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}

		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

		// 如果是手机登录，则返回JSON字符串
		if (mobile) {
			ResultObject res = new ResultObject();
			res.setResult(-1L);
			res.setMsg(message);
			res.setData(model);
			return renderString(response, res);
			/*
			 * Session session = UserUtils.getSession();
			 * session.setAttribute("isMobile",true); return
			 * "modules/app/appLogin";
			 */
		}
		
	
		if("admin.abacus-pro.com".equals(request.getServerName())||"47.52.151.161".equals(request.getServerName())){
			model.addAttribute("iparea", "2");
		}else{
		
		model.addAttribute("iparea", "1");
		}
		
		return "modules/sys/sysLogin_temp";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(UserUtils.getSession().getId().toString(), false, true);

		if (logger.isDebugEnabled()) {
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))) {
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)) {
				CookieUtils.setCookie(response, "LOGINED", "true");
			} else if (StringUtils.equals(logined, "true")) {
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}

		if (principal.isMobileLogin()) {

			ResultObject resultObject = new ResultObject();

			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("id", UserUtils.getUser().getId());
			obj.put("loginName", UserUtils.getUser().getLoginName());
			obj.put("name", UserUtils.getUser().getName());
			obj.put("mobileLogin", "true");
			String cookie = request.getHeader("Cookie");
			String jsessionid = "";
			Cookie[] cookies = request.getCookies();
			if (cookie != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals("JSESSIONID")) {
						jsessionid = c.getValue();
					}
				}
			}
			if (jsessionid.length() == 0) {
				// 生成并写入客户端的Cookie
				jsessionid = IdGen.uuid();
				HttpServletResponse resp = (HttpServletResponse) response;
				Cookie cookie1 = new Cookie("JSESSIONID", jsessionid);
				resp.addCookie(cookie1);
				Cookie cookie2 = new Cookie("jeesite.session.id", (String) UserUtils.getSession().getId());
				resp.addCookie(cookie2);
			}

			obj.put("sessionid", jsessionid);
			obj.put("jeesitesessionid", UserUtils.getSession().getId());

			List<Role> list = UserUtils.getUser().getRoleList();
			obj.put("userType", UserUtils.getUser().getUserType());
			obj.put("userTypeName", DictUtils.getDictLabel(UserUtils.getUser().getUserType(), "sys_user_type", ""));
			obj.put("binded", UserUtils.getUser().getBinded());
			resultObject.setData(obj);
			renderString(response, resultObject);

			return null;

		} else {

			return "modules/sys/sysIndex";
		}
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request,
			HttpServletResponse response) {

		if (StringUtils.isNotBlank(theme)) {
			CookieUtils.setCookie(response, "theme", theme);
		} else {
			theme = CookieUtils.getCookie(request, "theme");
		}

		String url = request.getParameter("url");
		String host = request.getServerName();

		if (url.startsWith(host)) {
			return "redirect:" + request.getParameter("url");
		} else {
			return "redirect:" + adminPath + "/login"; // 登陆的页面
		}

	}

	/**
	 * 是否是验证码登录
	 * 
	 * @param useruame
	 *            用户名
	 * @param isFail
	 *            计数加1
	 * @param clean
	 *            计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
}
