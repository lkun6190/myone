package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.ResultObject;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.security.UsernamePasswordToken;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * @desc: rtedu
 * @author: lkun
 * @createTime: 2017年8月3日 上午10:57:53
 * @history:
 * @version: v1.0
 */
@Controller
@RequestMapping(value = "/app")
public class ALoginController extends BaseController {

	@Autowired
	private SystemService systemService;

	@ResponseBody
	@RequestMapping(value = "login")
	public void login(@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("mobileLogin") String mobileLogin,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		
		UsernamePasswordToken token = new UsernamePasswordToken();
		token.setUsername(username);
		
		User user = systemService.getUserByLoginName(token.getUsername());

		UserUtils.getSession().setAttribute("USER", user);

		Session session = UserUtils.getSession();
		session.setAttribute("isMobile", true);
		ResultObject resultObject = new ResultObject();

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("id", user.getId());
		obj.put("loginName", user.getLoginName());
		obj.put("name", user.getName());
		obj.put("mobileLogin", "true");
		obj.put("sessionid", request.getSession().getId());
		obj.put("jeesitesessionid", UserUtils.getSession().getId());

		List<Role> list = user.getRoleList();
		obj.put("userType", user.getUserType());
		obj.put("userTypeName",
				DictUtils.getDictLabel(user.getUserType(), "sys_user_type", ""));
		resultObject.setData(obj);
		renderString(response, resultObject);

	}

}
