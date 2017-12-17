package com.thinkgem.jeesite.modules.user.appWeb;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.ResultObject;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;
import com.thinkgem.jeesite.modules.user.entity.ZxsVersion;
import com.thinkgem.jeesite.modules.user.service.ZxsStuService;
import com.thinkgem.jeesite.modules.user.service.ZxsTeaService;
import com.thinkgem.jeesite.modules.user.service.ZxsVersionService;

@Controller
@RequestMapping(value = "${frontPath}/admin")
public class AppZxsStuController extends BaseController {

	@Autowired
	private ZxsStuService zxsStuService;

	@Autowired
	private ZxsTeaService zxsTeaService;

	@Autowired
	private ZxsVersionService zxsVersionService;

	@ModelAttribute
	public ZxsStu get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return zxsStuService.get(id);
		} else {
			return new ZxsStu();
		}
	}

	/**
	 * 用户登陆(用户账号和密码登录)
	 * 
	 * @author: lkun
	 * @createTime: 2017年9月1日 下午4:47:06
	 * @history:
	 * @param request
	 * @param response
	 * @param model
	 *            void
	 */
	@ResponseBody
	@RequestMapping("login")
	public void login(HttpServletRequest request, HttpServletResponse response, Model model) {
		ResultObject res = new ResultObject();
		String acc = request.getParameter("acc");
		String pwd = request.getParameter("pwd");
		// String type = request.getParameter("version");// 1学生2老师 版本

		ZxsTea zxsTea = zxsTeaService.getUserByTeaNo(acc);
		ZxsStu zxsStu = zxsStuService.getUserByStuNo(acc);
		
		
		
		int type = 1;
		if (zxsTea == null && zxsStu == null) {
			res.setResult(202L);
			res.setMsg("not the acc");
			renderString(response, res);
			return;
		} else if (zxsTea != null && zxsStu == null) {
			type = 2;// 老师
		} else if (zxsTea == null && zxsStu != null) {
			type = 1;// 学生
		}

		if (type == 2) {// 学生以外,老师用户

			// zxsTea = zxsTeaService.getUserByTeaNo(acc);

			if (zxsTea != null) {
				if (new SystemService().validatePassword(pwd, zxsTea.getPwd())) {
					if ("2".equals(zxsTea.getDelFlag())) {
						res.setResult(202L);
						res.setMsg("the acc stop");
					} else {

						res.setResult(200L);
						res.setMsg("2");
						String token = IdGen.uuid().substring(0, 5) + System.currentTimeMillis();

						zxsTea.setToken(token);
						zxsTea.setStatu("1");
						zxsTea.setLastLoginDate(new Date());
						zxsTeaService.updateToken(zxsTea);
						
						zxsTea.setZhusuanlv(DictUtils.getDictLabel(zxsTea.getZhusuanlv(), "zhusuanlv", "0"));
						zxsTea.setXinsuanlv(DictUtils.getDictLabel(zxsTea.getXinsuanlv(), "zhusuanlv", "0"));

						res.setData(zxsTea);
					}
				} else {
					res.setResult(201L);
					res.setMsg("pwd fail");
				}
			} else {
				res.setResult(202L);
				res.setMsg("not the acc");
			}
		}

		else {// 学生账号
				// ZxsStu zxsStu = zxsStuService.getUserByStuNo(acc);
			if (zxsStu != null) {
				if (new SystemService().validatePassword(pwd, zxsStu.getPwd())) {
					if ("2".equals(zxsStu.getDelFlag())) {
						res.setResult(202L);
						res.setMsg("the acc stop");
					} else {
						res.setResult(200L);
						res.setMsg("1");

						String token = IdGen.uuid().substring(0, 5) + System.currentTimeMillis();

						zxsStu.setToken(token);
						zxsStu.setStatu("1");
						zxsStu.setLastLoginDate(new Date());
						zxsStuService.updateToken(zxsStu);
						zxsStu.setZhusuanlv(DictUtils.getDictLabel(zxsStu.getZhusuanlv(), "zhusuanlv", "0"));
						zxsStu.setXinsuanlv(DictUtils.getDictLabel(zxsStu.getXinsuanlv(), "zhusuanlv", "0"));
						res.setData(zxsStu);
					}
				} else {
					res.setResult(201L);
					res.setMsg("pwd fail");
				}

			} else {
				res.setResult(202L);
				res.setMsg("not the acc");
			}
		}

		renderString(response, res);
	}

	/**
	 * 登出
	 * 
	 * @author: lkun
	 * @createTime: 2017年9月28日 下午3:49:22
	 * @history:
	 * @param request
	 * @param response
	 *            void
	 */
	@ResponseBody
	@RequestMapping(value = "logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {

		String adminId = request.getParameter("adminId");
		String acc = request.getParameter("acc");
		String type = request.getParameter("type");
		if ("2".equals(type)) {
			ZxsTea zxsTea = new ZxsTea();
			zxsTea.setId(adminId);

			zxsTea.setStatu("0");
			zxsTeaService.updateToken(zxsTea);
		} else {
			ZxsStu zxsStu = new ZxsStu();
			zxsStu.setId(adminId);

			zxsStu.setStatu("0");
			zxsStuService.updateToken(zxsStu);
		}

		ResultObject res = new ResultObject();
		res.setMsg("OK");
		res.setResult(200L);
		renderString(response, res);
	}

	@ResponseBody
	@RequestMapping(value = "cktoken")
	public void cktoken(HttpServletRequest request, HttpServletResponse response) {
		ResultObject res = new ResultObject();
		String token = request.getParameter("token");
		String acc = request.getParameter("acc");
		String type = request.getParameter("type");
		if ("2".equals(type)) {// 老师账号
			ZxsTea zxsTea = zxsTeaService.getUserByTeaNo(acc);
			if (zxsTea != null) {
				if (token.equals(zxsTea.getToken())) {
					res.setResult(200L);
				} else {
					res.setResult(201L);
				}
			}
		} else {// 学生账号
			ZxsStu zxsStu = zxsStuService.getUserByStuNo(acc);
			if (zxsStu != null) {
				if (token.equals(zxsStu.getToken())) {

					res.setResult(200L);

				} else {
					res.setResult(201L);

				}
			}
		}
		renderString(response, res);
	}

	@RequestMapping(value = "info")
	@ResponseBody
	public void appInfo(@ModelAttribute("appPlayInfo") AppPlayInfo appPlayInfo, HttpServletRequest request,
			HttpServletResponse response) {
		ResultObject res = new ResultObject();

		String type = appPlayInfo.getAdminType();
		String token = appPlayInfo.getToken();

		if ("2".equals(type)) {// 老师账号
			ZxsTea zxsTea = zxsTeaService.get(appPlayInfo.getAdminId());
			if (zxsTea != null) {
				if (!token.equals(zxsTea.getToken())) {
					res.setResult(201L);
					res.setMsg("Login elsewhere");
					renderString(response, res);
					return;
				}
			}
		} else {// 学生账号
			ZxsStu zxsStu = zxsStuService.get(appPlayInfo.getAdminId());
			if (zxsStu != null) {
				if (!token.equals(zxsStu.getToken())) {

					res.setResult(201L);
					res.setMsg("Login elsewhere");
					renderString(response, res);
					return;
				}
			}
		}

		try {

			zxsStuService.updateAppInfo(appPlayInfo);

			res.setResult(200L);
			res.setMsg("ok");
		} catch (Exception ex) {
			res.setResult(201L);
			res.setMsg("fail");
			ex.printStackTrace();
		}
		renderString(response, res);
	}

	@RequestMapping(value = "checkVersion")
	@ResponseBody
	public void checkVersion(HttpServletRequest request, HttpServletResponse response) {
		ResultObject res = new ResultObject();
		ZxsVersion zxsVersion = zxsVersionService.findFirst();
		res.setResult(200L);
		res.setData(zxsVersion);
		res.setMsg("ok");
		renderString(response, res);

	}
	////flashAm   flashMulti   flashDivis   tsAm   tsMulti    tsDivis
	
	@RequestMapping(value = "orderBySort")
	@ResponseBody
	public void orderBySort(HttpServletRequest request, HttpServletResponse response) {
		String temp=request.getParameter("temp");
	
		ResultObject res = new ResultObject();
		List<ZxsStu> map=zxsStuService.orderBy(temp);
		res.setResult(200L);
		res.setData(map);
		res.setMsg("ok");
		renderString(response, res);
	}
	
	@RequestMapping(value = "ipToArea")
	public String ipToArea(HttpServletRequest request, HttpServletResponse response) {
		String type=request.getParameter("type");
		if("2".equals(type)){
			return "redirect:http://admin.abacus-pro.com/zxs";
		
		}else{
			return "redirect:http://118.190.155.177/zxs";
		
		}
	}
}
