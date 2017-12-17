/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.ResultObject;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);

		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	public String getMessageParam() {
		return messageParam;
	}
	
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
    	request.setAttribute(getFailureKeyAttribute(), ae.getClass().getName());
		if (ae.getMessage() != null && StringUtils.startsWith(ae.getMessage(), "msg:")){
			String message = StringUtils.replace(ae.getMessage(), "msg:", "");
	        request.setAttribute(getMessageParam(), message);
		}
    }
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		SystemAuthorizingRealm.Principal p = UserUtils.getPrincipal();

		System.out.println(p.isMobileLogin());

		if (p != null && !p.isMobileLogin()){
			 WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
		}else{
			//直接输出成功的信息
			ResultObject resultObject = new ResultObject();
			HttpServletRequest  req = (HttpServletRequest)request;
			String cookie = req.getHeader("Cookie");
			String jsessionid = "";
			Cookie[] cookies = req.getCookies();
			if(cookie!=null) {
				for (Cookie c : cookies) {
					if (c.getName().equals("JSESSIONID")) {
						jsessionid = c.getValue();
					}
				}
			}
			if(jsessionid.length() == 0 ){
				//生成并写入客户端的Cookie
				jsessionid = IdGen.uuid();
				HttpServletResponse resp = (HttpServletResponse)response;
				Cookie cookie1 = new Cookie("JSESSIONID", jsessionid);
				resp.addCookie(cookie1);
				Cookie cookie2 = new Cookie("jeesite.session.id", (String)UserUtils.getSession().getId());
				resp.addCookie(cookie2);
			}
			Map<String, Object> obj = new HashMap<String,Object>();
			User user = UserUtils.getUser();
			obj.put("id", user.getId());
			obj.put("loginName", user.getLoginName());
			obj.put("name",user.getName());
			obj.put("mobileLogin", "true");
			obj.put("sessionid", jsessionid );
			obj.put("jeesitesessionid",UserUtils.getSession().getId());

			List<Role> list = user.getRoleList();
			obj.put("userType", user.getUserType() );
			obj.put("userTypeName", DictUtils.getDictLabel(user.getUserType(), "sys_user_type", ""));
			obj.put("binded", user.getBinded()) ;
			resultObject.setData(obj);

			renderString((HttpServletResponse) response, JsonMapper.toJsonString(resultObject), "application/json");
			//super.issueSuccessRedirect(request, response);
		}
	}

	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
			response.setContentType(type);
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}