package com.thinkgem.jeesite.modules.user.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.StudyDate;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;
import com.thinkgem.jeesite.modules.user.service.ZxsClassService;
import com.thinkgem.jeesite.modules.user.service.ZxsStuService;
import com.thinkgem.jeesite.modules.user.service.ZxsTeaService;

@Controller
@RequestMapping(value = "${adminPath}/cla")
public class ZxsClassController extends BaseController {

	@Autowired
	private ZxsClassService zxsClassService;

	@Autowired
	private ZxsTeaService zxsTeaService;

	@Autowired
	private ZxsStuService zxsStuService;

	@Autowired
	private OfficeService officeService;

	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public ZxsClass get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return zxsClassService.get(id);
		} else {

			return new ZxsClass();
		}
	}

	/**
	 * 班级列表
	 * 
	 * @param zxsClass
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("class:list:view")
	@RequestMapping(value = { "list", "" })
	public String list(ZxsClass zxsClass, HttpServletRequest request, Office office, HttpServletResponse response,
			Model model, HttpSession session) {
		session.setAttribute("classOfficeId", "");

		if (zxsClass == null) {
			zxsClass = new ZxsClass();
		}

		String officeId = "";
		if (office != null && StringUtils.isNotBlank(office.getParentId()) && !"0".equals(office.getParentId())) {
			officeId = office.getParentId();
			CacheUtils.put("classOfficeId", officeId);

		} else {
			officeId = (String) CacheUtils.get("classOfficeId");
		}
		Office ofi = new Office();
		ofi.setId(officeId);
		zxsClass.setOffice(ofi);
		Page<ZxsClass> page = new Page<ZxsClass>(request, response);
		page.setOrderBy("class_no,class_name");
		page = zxsClassService.findPage(page, zxsClass);
		// 获取机构学校的所有老师列表

		/*
		 * ZxsTea zxsTea = new ZxsTea(); zxsTea.setOffice(ofi);
		 */

		List<ZxsTea> teaList = new ArrayList<ZxsTea>();
		if (CacheUtils.get("zxsTeaList" + officeId) == null
				|| StringUtils.isNotBlank(CacheUtils.get("zxsTeaList" + officeId).toString())) {

			teaList = zxsClassService.findTeaList(zxsClass);

			if (teaList == null || teaList.size() == 0 || teaList.get(0) == null) {
				teaList = new ArrayList<ZxsTea>();
			}

			CacheUtils.put("zxsTeaList" + officeId, teaList);
		}
		teaList = (List<ZxsTea>) CacheUtils.get("zxsTeaList" + officeId);

		if (teaList == null) {
			teaList = new ArrayList<ZxsTea>();
		}
		if (teaList.contains(null)) {
			teaList.remove(null);
		}
		model.addAttribute("page", page);
		model.addAttribute("teaList", teaList);
		return "modules/admin/classList";
	}

	/**
	 * 用于根据机构来查找班级
	 * 
	 * @param officeId
	 * @param response
	 * @param session
	 * @throws IOException
	 */
	@RequiresPermissions("class:list:view")
	@RequestMapping(value = "saveId")
	public void saveId(String officeId, HttpServletResponse response, HttpSession session) throws IOException {

		session.setAttribute("classOfficeId", officeId);
		JSONObject obj = new JSONObject();
		try {
			obj.put("result", "ok");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		response.getWriter().print(obj.toString());
	}

	/**
	 * 获取班级列表
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequiresPermissions("class:list:view")
	@ResponseBody
	@RequestMapping(value = "findCla")
	public List<Map<String, Object>> treeData(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String officeId = (String) session.getAttribute("classOfficeId");

		if (StringUtils.isBlank(officeId)) {
			officeId = UserUtils.getUser().getOffice().getId();
		}

		List<Map<String, Object>> mapList = Lists.newArrayList();
		ZxsClass zxsClass = new ZxsClass();
		Office office = new Office();
		office.setId(officeId);
		zxsClass.setOffice(office);
		List<ZxsClass> list = zxsClassService.findList(zxsClass);

		for (int i = 0; i < list.size(); i++) {
			ZxsClass e = list.get(i);

			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", "0");
			map.put("pIds", "0");
			map.put("name", e.getClassNo());

			map.put("isParent", false);

			mapList.add(map);
		}
		return mapList;
	}

	// 个人信息
	@RequiresPermissions("class:edit:view")
	@RequestMapping(value = "form")
	public String rycxForm(@ModelAttribute ZxsClass zxsClass, @RequestParam(value = "id", required = false) String id,
			HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {

		// zxsClass = zxsClassService.get(id);

		model.addAttribute("zxsClass", zxsClass);

		String officeId = (String) CacheUtils.get("classOfficeId");

		ZxsTea zxsTea = new ZxsTea();
		Office ofi = new Office();
		ofi.setId(officeId);
		if (zxsClass == null || zxsClass.getOffice() == null || zxsClass.getOffice().getId() == null) {

			Office office_form = officeService.get(officeId);
			zxsClass.setOffice(office_form);
		} else {
			// 上课时间的列表
			List<String> studyDateIdList = new ArrayList<String>();
			String sd = null;
			if (zxsClass.getStudyDate() != null) {
				sd = zxsClass.getStudyDate().trim();
			}
			if (sd != null && sd.length() > 0) {
				try {

					if (sd.endsWith("&")) {
						sd = sd.substring(0, sd.length() - 1);
					}
					String[] stu_list = sd.split("&");
					for (String s : stu_list) {
						studyDateIdList.add(s);
					}
				} catch (Exception ex) {
				}
			}
			zxsClass.setStudyDateIdList(studyDateIdList);
		}

		zxsTea.setOffice(ofi);
		List<ZxsTea> teaList = zxsTeaService.findList(zxsTea);
		model.addAttribute("teaList", teaList);

		model.addAttribute("allStudyDate", StudyDate.getStudyDateList());
		return "modules/admin/classForm";
	}

	/**
	 * 批量删除班级
	 * 
	 * @param datas
	 * @param response
	 * @throws IOException
	 */
	@RequiresPermissions("class:edit:view")
	@RequestMapping(value = "batchdelete")
	public void batchdelete(@RequestParam(value = "datas[]") String[] datas, HttpServletResponse response)
			throws IOException {
		zxsClassService.batchdelete(datas);

		for (String id : datas) {

			ZxsClass zxsClass = zxsClassService.get(id);

			zxsClassService.delete(zxsClass);
			zxsStuService.deleteByClass(zxsClass);// 班级学生

			updateTea(zxsClass);// 删除班级的负责的老师

		}
		try {
			CacheUtils.remove(AdminUtils.CACHE_ZXSCLASS_LIST);
		} catch (Exception ex) {
		}

		JSONObject obj = new JSONObject();
		try {
			obj.put("result", "ok");
		} catch (JSONException e) {

			e.printStackTrace();

		}
		response.getWriter().print(obj.toString());
	}

	public void updateTea(ZxsClass zxsClass) {
		if (zxsClass.getZxsTea() != null && StringUtils.isNotBlank(zxsClass.getZxsTea().getId())) {
			// 先修改前
			User user = systemService.getUser(zxsClass.getZxsTea().getId());

			String[] sr = user.getClassTemp().split(",");

			if (sr.length == 1) {
				systemService.deleteUser1(user);
				// 删除
			} else {

				sr = ArrayUtils.removeElement(sr, zxsClass.getId());
				String string = StringUtils.join(sr, ",");

				user.setClassTemp(string);

				systemService.updateUserInfo(user);

			}
		}

	}

	@RequiresPermissions("class:edit:view")
	@RequestMapping(value = "delete")
	public String delete(ZxsClass zxsClass, RedirectAttributes redirectAttributes) {

		zxsClassService.delete(zxsClass);

		zxsStuService.deleteByClass(zxsClass);

		updateTea(zxsClass);// 删除班级的负责的老师
		addMessage(redirectAttributes, "删除班级及班级里的学生成功");

		return "redirect:" + adminPath + "/cla/list?repage";
	}

	/**
	 * 导出用户数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("class:edit:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ZxsClass zxsClass, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "班级数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

			String officeId = (String) CacheUtils.get("classOfficeId");
			Office ofi = new Office();
			ofi.setId(officeId);
			zxsClass.setOffice(ofi);

			List<ZxsClass> page = zxsClassService.findList(zxsClass);

			new ExportExcel("班级数据", ZxsClass.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出班级数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/cla/list?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("class:edit:view")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ZxsClass> list = ei.getDataList(ZxsClass.class);

			for (ZxsClass adm : list) {
				try {
					String officeId = (String) CacheUtils.get("classOfficeId");

					if (!officeId.equals(adm.getOffice().getId())) {
						failureMsg.append("机构代理错误或不存在; ");
						failureNum++;
						continue;
					}

					if (zxsClassService.checkNo(adm) != null) {

						failureMsg.append("班级名存在; ");
						failureNum++;
						continue;
					}

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					adm.setStudyTimeStart(
							sdf.format(HSSFDateUtil.getJavaDate(Double.valueOf(adm.getStudyTimeStart()))));
					adm.setStudyTimeEnd(sdf.format(HSSFDateUtil.getJavaDate(Double.valueOf(adm.getStudyTimeEnd()))));
					/*
					 * if (((String) CacheUtils.get("classOfficeId")).equals(adm
					 * .getOffice().getId())) {
					 */
					zxsClassService.save(adm);
					successNum++;
					// }

				} catch (ConstraintViolationException ex) {

					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>导入失败：" + ex.getMessage());
					ex.printStackTrace();
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条班级数据，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条班级数据" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入班级数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/cla/list?repage";
	}

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */

	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "班级数据导入模板.xlsx";
			List<ZxsClass> list = Lists.newArrayList();
			ZxsClass rt = new ZxsClass();
			rt.setClassName("A251600");
			rt.setClassNo("A");
			rt.setStudyDate("二,五");
			rt.setStudyTimeStart("16:00:00");
			rt.setStudyTimeEnd("17:00:00");

			rt.setOffice(UserUtils.getUser().getOffice());

			list.add(rt);
			new ExportExcel("班级数据数据", ZxsClass.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入班级模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/cla/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldStuNo
	 * @param stuNo
	 * @return
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequiresPermissions("class:edit:view")
	 * 
	 * @RequestMapping(value = "checkClassNo")
	 */
	public boolean checkStuNo(ZxsClass zxsClass) {

		if (zxsClass.getClassNo() != null && zxsClass.getClassNo().equals(zxsClass.getOldClassNo())) {
			return true;
		} else if (zxsClass.getClassNo() != null && zxsClassService.checkNo(zxsClass) == null) {
			return true;
		}
		return false;
	}

	@RequiresPermissions("class:edit:view")
	@RequestMapping(value = "save")
	public String abvSave(ZxsClass zxsClass, Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		List<String> sd_list = zxsClass.getStudyDateIdList();

		String sd = "";
		if (sd_list != null && sd_list.size() > 0) {
			for (String s : sd_list) {
				sd += s + "&";
			}
		}

		if (!checkStuNo(zxsClass)) {
			addMessage(model, "保存班级'" + zxsClass.getClassNo() + "'失败，班级名已存在");
			return rycxForm(zxsClass, null, request, response, session, model);
		}

		
		System.out.println(zxsClass.getZxsTea().getId()+"------**************---");
		zxsClass.setStudyDate(sd);

		CacheUtils.put("zxsTeaList" + zxsClass.getOffice().getId(), "");
		try {
			CacheUtils.remove("zxsTeaList" + zxsClass.getOffice().getId());
		} catch (Exception ex) {
		}
		String classId = zxsClassService.saveResultId(zxsClass);

		if (StringUtils.isBlank(zxsClass.getOldTeaId())) { // 旧的不存在
			if (zxsClass.getZxsTea() != null && StringUtils.isNotBlank(zxsClass.getZxsTea().getId())) {
				// 更新 先添加或修改班级 再添加或更新用户
				User user = systemService.getUser(zxsClass.getZxsTea().getId());
				
				if (user == null) {
					// 添加
					user = new User();
					user.setClassTemp(classId);
					user.setId(zxsClass.getZxsTea().getId());
					user = saveOrUpdate(zxsTeaService.get(zxsClass.getZxsTea().getId()), user);
					systemService.saveUser1(user);

				} else {

					user.setClassTemp(
							StringUtils.isBlank(user.getClassTemp()) ? "" : user.getClassTemp() + "," + classId);

					systemService.updateUserInfo(user);

				}
			}
		} else {// 旧的存在

			if (!zxsClass.getOldTeaId().equals(zxsClass.getZxsTea().getId())) {
				// 先修改前
				User user = systemService.getUser(zxsClass.getOldTeaId());

				String[] sr = user.getClassTemp().split(",");
				if (sr.length == 1) {
					systemService.deleteUser1(user);
					// 删除
				} else {

					sr = ArrayUtils.removeElement(sr, classId);
					String string = StringUtils.join(sr, ",");

					user.setClassTemp(string);

					systemService.updateUserInfo(user);

				}
				
				if(StringUtils.isNotBlank(zxsClass.getZxsTea().getId())){
				// 再更新新的
				User newUser = systemService.getUser(zxsClass.getZxsTea().getId());
					
				if (newUser == null) {
					newUser = new User();
					newUser.setClassTemp(classId);
					newUser.setId(zxsClass.getZxsTea().getId());
					newUser = saveOrUpdate(zxsTeaService.get(zxsClass.getZxsTea().getId()), newUser);
					systemService.saveUser1(newUser);
				} else {
					newUser.setClassTemp(
							StringUtils.isBlank(newUser.getClassTemp()) ? "" : newUser.getClassTemp() + "," + classId);

					systemService.updateUserInfo(newUser);
				}
				}

			}
		}

		try {
			CacheUtils.remove(AdminUtils.CACHE_ZXSCLASS_LIST);
		} catch (Exception ex) {
		}
		return "redirect:" + adminPath + "/cla/list?repage";
	}

	public User saveOrUpdate(ZxsTea zxsTea, User user) {

		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = new ArrayList<String>();
		roleIdList.add("d61db7d82dc84a42a4221870b59a91bb");

		for (Role r : systemService.findAllRole()) {
			if (roleIdList.contains(r.getId())) {
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);

		Office office = new Office();
		user.setOffice(zxsTea.getOffice());
		office.setId("1");
		user.setCompany(office);

		user.setDelFlag("0");

		user.setUserType("4");

		user.setIdentfy("教师");

		user.setCreateBy(UserUtils.getUser());
		user.setCreateDate(new Date());
		user.setUpdateBy(UserUtils.getUser());
		user.setUpdateDate(new Date());

		zxsTea.getPwd();

		user.setName(zxsTea.getTeaName());
		user.setLoginName(zxsTea.getTeaNo());
		user.setPassword(zxsTea.getPwd());
		user.setPhone(zxsTea.getPhone());
		user.setAddress(zxsTea.getAddress());
		user.setBirth(zxsTea.getBirth());
		user.setSex(zxsTea.getSex());
		user.setEdu(zxsTea.getEdu());
		user.setNo(zxsTea.getNo());

		if (user.getIsStudied() == null) {
			user.setIsStudied("0");
		}
		return user;
	}

}
