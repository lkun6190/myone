package com.thinkgem.jeesite.modules.user.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.ZxsTea;
import com.thinkgem.jeesite.modules.user.service.ZxsStuService;
import com.thinkgem.jeesite.modules.user.service.ZxsTeaService;

@Controller
@RequestMapping(value = "${adminPath}/tea")
public class ZxsTeaController extends BaseController {

	@Autowired
	private ZxsTeaService zxsTeaService;

	@Autowired
	private ZxsStuService zxsStuService;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute("zxsTea")
	public ZxsTea get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return zxsTeaService.get(id);
		} else {
			return new ZxsTea();
		}
	}

	@RequiresPermissions("tea:list:view")
	@RequestMapping(value = { "index" })
	public String index(@ModelAttribute("zxsTea") ZxsTea zxsTea, Model model) {
		return "modules/admin/zxsTeaIndex";
	}

	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldStuNo
	 * @param stuNo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "checkTeaNo")
	public String checkTeaNo(String oldTeaNo, String teaNo) {
		if (teaNo != null && teaNo.equals(oldTeaNo)) {
			return "true";
		} else if (teaNo != null && zxsTeaService.getUserByTeaNo(teaNo) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 总列表
	 * 
	 * @param zxsTea
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("tea:list:view")
	@RequestMapping(value = { "list", "" })
	public String list(@ModelAttribute("zxsTea") ZxsTea zxsTea,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (zxsTea == null || zxsTea.getOffice() == null
				|| zxsTea.getOffice().getId() == null) {
			zxsTea.setOffice(UserUtils.getUser().getOffice());
		}
		Page<ZxsTea> page = new Page<ZxsTea>(request, response);

		page.setOrderBy("o.name,a.zhusuanlv");
		page = zxsTeaService.findPage(page, zxsTea);
		model.addAttribute("page", page);
		return "modules/admin/teaList";
	}

	@RequiresPermissions("tea:list:view")
	@RequestMapping(value = { "selfList", "" })
	public String selfList(@ModelAttribute("zxsTea") ZxsTea zxsTea,
			@RequestParam(value = "id", required = false) String id,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (zxsTea == null || zxsTea.getOffice() == null
				|| zxsTea.getOffice().getId() == null) {
			String office_id = (String) CacheUtils.get("classOfficeId");
			zxsTea.setOffice(officeService.get(office_id));
		}
		Page<ZxsTea> page = new Page<ZxsTea>(request, response);
		page.setOrderBy("o.name,a.zhusuanlv");
		page = zxsTeaService.findPage(page, zxsTea);
		model.addAttribute("page", page);
		return "modules/admin/selfTeaList";
	}

	// 个人信息
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "selfForm")
	public String selfForm(@ModelAttribute ZxsTea zxsTea,

	Model model) {

		if (zxsTea == null || zxsTea.getOffice() == null
				|| zxsTea.getOffice().getId() == null) {
			String office_id = (String) CacheUtils.get("classOfficeId");
			zxsTea.setOffice(officeService.get(office_id));
		}
		// zxsTea = zxsTeaService.get(id);
		if (zxsTea != null && zxsTea.getAppPlayInfo() != null
				&& zxsTea.getAppPlayInfo().getLessonsHis() != null) {
			String lessionHis = zxsTea.getAppPlayInfo().getLessonsHis();
			if (StringUtils.isNoneBlank(lessionHis) && lessionHis.contains("#")) {
				AppPlayInfo appPlayInfo = zxsTea.getAppPlayInfo();
				appPlayInfo.setLessonsHis(lessionHis.replaceAll("#", "\n")
						.replaceAll("null", ""));
				zxsTea.setAppPlayInfo(appPlayInfo);
			}
		}
		if (zxsTea != null && zxsTea.getAppPlayInfo() != null) {
			if (Integer.parseInt(zxsTea.getAppPlayInfo().getWorksLevel()) >= 100) {
				zxsTea.setAutoLevel("1");
			} else {
				zxsTea.setAutoLevel("0");
			}
		}
		model.addAttribute("zxsTea", zxsTea);
		return "modules/admin/selfTeaForm";
	}

	// 个人信息
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = { "form", "" })
	public String rycxForm(@ModelAttribute ZxsTea zxsTea, Model model) {

		// zxsTea = zxsTeaService.get(id);
		String root_tmp = "true";
		try {
			String currId = UserUtils.getUser().getOffice().getParentIds();
			int h = currId.split(",").length;

			if (h == 4) {
				root_tmp = "false";
			}
		} catch (Exception ex) {
		}
		zxsTea.setRoot_tmp(root_tmp);

		if (zxsTea != null && zxsTea.getAppPlayInfo() != null
				&& zxsTea.getAppPlayInfo().getLessonsHis() != null) {
			String lessionHis = zxsTea.getAppPlayInfo().getLessonsHis();
			if (StringUtils.isNoneBlank(lessionHis) && lessionHis.contains("#")) {
				AppPlayInfo appPlayInfo = zxsTea.getAppPlayInfo();
				appPlayInfo.setLessonsHis(lessionHis.replaceAll("#", "\n")
						.replaceAll("null", ""));
				zxsTea.setAppPlayInfo(appPlayInfo);
			}
		}

		if (zxsTea != null && zxsTea.getAppPlayInfo() != null) {
			if (Integer.parseInt(zxsTea.getAppPlayInfo().getWorksLevel()) >= 100) {
				zxsTea.setAutoLevel("1");
			} else {
				zxsTea.setAutoLevel("0");
			}
		}

		model.addAttribute("zxsTea", zxsTea);
		return "modules/admin/teaForm";
	}

	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "batchdelete")
	public void batchdelete(@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws IOException {
		zxsTeaService.batchdelete(datas);
		JSONObject obj = new JSONObject();
		try {
			obj.put("result", "ok");
		} catch (JSONException e) {

			e.printStackTrace();

		}

		for (String data : datas) {
			try {
				User user = systemService.getUser(data);
				if (user != null) {
					systemService.deleteUser1(user);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		response.getWriter().print(obj.toString());
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
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(@ModelAttribute("zxsTea") ZxsTea zxsTea,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "老师数据_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";

			if (zxsTea == null || zxsTea.getOffice() == null
					|| zxsTea.getOffice().getId() == null) {
				zxsTea.setOffice(UserUtils.getUser().getOffice());
			}

			List<ZxsTea> page = zxsTeaService.findList(zxsTea);

			new ExportExcel("老师数据", ZxsTea.class).setDataList(page)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出老师数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/tea/list?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ZxsTea> list = ei.getDataList(ZxsTea.class);

			for (ZxsTea adm : list) {
				try {
					if (StringUtils.isBlank(adm.getTeaNo())) {
						adm.setTeaNo(adm.getTeaName());
					}

					String re = "[0-9A-Za-z] {0,6}";
					if (adm.getTeaNo().matches(re)) {
						failureMsg.append("<br/>导入失败：" + adm.getTeaNo()
								+ "登录名格式不符合规则");
						failureNum++;
						continue;
					}

					if (zxsTeaService.getUserByTeaNo(adm.getTeaNo()) != null
							|| zxsStuService.getUserByStuNo(adm.getTeaNo()) != null) {
						failureMsg
								.append("<br/>导入失败：" + adm.getTeaNo() + "已存在");
						failureNum++;
						continue;
					}
					if (StringUtils.isBlank(adm.getXinsuanlv())) {
						adm.setXinsuanlv("0");
					}
					if (StringUtils.isBlank(adm.getZhusuanlv())) {
						adm.setZhusuanlv("0");
					}
					
					adm.setPwd(SystemService.entryptPassword(StringUtils
							.isBlank(adm.getPhone()) ? adm.getTeaNo() : adm
							.getPhone()));

					adm.setTel(com.thinkgem.jeesite.common.utils.StringUtils
							.phoneToNumber(adm.getTel()));
					zxsTeaService.saveResult(adm);
					successNum++;

				} catch (ConstraintViolationException ex) {

					failureMsg.append("<br/>老师名" + adm.getTeaName() + " 导入失败：");
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>老师名" + adm.getTeaName() + " 导入失败：");
					failureNum++;
					ex.printStackTrace();
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条老师数据，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条老师数据"
					+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入老师数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/tea/list?repage";
	}

	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "batchstop")
	public void batchstop(@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws Exception {
		zxsTeaService.batchstop(datas);
		JSONObject obj = new JSONObject();
		obj.put("result", "ok");
		response.getWriter().print(obj.toString());
	}

	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "batchstart")
	public void batchstart(@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws Exception {
		zxsTeaService.batchstart(datas);
		JSONObject obj = new JSONObject();
		obj.put("result", "ok");
		response.getWriter().print(obj.toString());
	}

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */

	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "老师数据导入模板.xlsx";
			List<ZxsTea> list = Lists.newArrayList();
			ZxsTea rt = new ZxsTea();
			rt.setTeaName("Alice");
			rt.setStudiedName("珠算宝");
			rt.setTeaNo("alice");

			rt.setSex("1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			rt.setBirth(sdf.parse(sdf.format(new Date())));
			rt.setNo("36242120109876");
			rt.setTel("1368888565");
			rt.setEmail("1111222@gmail.com");

			rt.setIsStudied(1);
			Office office = new Office();
			office.setName("东方书院");
			rt.setOffice(office);

			rt.setEntryDate(sdf.parse(sdf.format(new Date())));
			rt.setEdu("中山大学本科");
			rt.setEx("5年珠算老师");

			rt.setZhusuanlv("一级");
			rt.setXinsuanlv("一级");

			rt.setAddress("广东广州天河");
			list.add(rt);

			new ExportExcel("用户数据", ZxsTea.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes,
					"导入老师数据模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/tea/list?repage";
	}

	/**
	 * 保存或更改
	 * 
	 * @param zxsClass
	 * @return
	 */
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "save")
	public String abvSave(ZxsTea zxsTea, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(zxsTea.getNewPassword())) {
			zxsTea.setPwd(SystemService.entryptPassword(zxsTea.getNewPassword()));
		}
		if (!beanValidator(model, zxsTea)) {
			return rycxForm(zxsTea, model);
		}
		if (!"true".equals(checkTeaNo(zxsTea.getOldTeaNo(), zxsTea.getTeaNo()))) {
			addMessage(model, "保存老师'" + zxsTea.getTeaNo() + "'失败，登录名已存在");
			return rycxForm(zxsTea, model);
		}
		if (zxsStuService.getUserByStuNo(zxsTea.getTeaNo()) != null) {
			addMessage(model, "保存老师'" + zxsTea.getTeaNo() + "'失败，登录名已存在");
			return rycxForm(zxsTea, model);
		}

		if (StringUtils.isNotBlank(zxsTea.getId())) {
			if ("1".equals(zxsTea.getAutoLevel())) {

				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("100");
				appPlayInfo.setAdminType("2");
				appPlayInfo.setAdminId(zxsTea.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			} else {
				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("0");
				appPlayInfo.setAdminType("2");
				appPlayInfo.setAdminId(zxsTea.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			}
		}

		zxsTeaService.saveResult(zxsTea);

		try {
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		} catch (Exception ex) {
		}

		return "redirect:" + adminPath + "/tea/list?repage&office.id="+zxsTea.getOffice().getId();
	}

	@Autowired
	SystemService systemService;

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "delete")
	public String delete(ZxsTea zxsTea, RedirectAttributes redirectAttributes) {

		zxsTeaService.delete(zxsTea);
		addMessage(redirectAttributes, "删除老师成功");

		try {
			User user = systemService.getUser(zxsTea.getId());
			if (user != null) {
				systemService.deleteUser1(user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return "redirect:" + adminPath + "/tea/list?repage";
	}

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "stop")
	public String stop(ZxsTea zxsTea, String del,
			RedirectAttributes redirectAttributes) {

		String tipStr = "停用";
		if ("0".equals(del)) {// 表示要激活
			tipStr = "激活";
		}
		zxsTea.setDelFlag(del);
		zxsTeaService.stop(zxsTea);
		addMessage(redirectAttributes, tipStr + "老师用户成功");

		return "redirect:" + adminPath + "/tea/list?repage";
	}

	/****************************************************************************************************/

	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "selfBatchdelete")
	public void selfBatchdelete(
			@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws IOException {
		zxsTeaService.batchdelete(datas);

		for (String data : datas) {
			try {
				User user = systemService.getUser(data);
				if (user != null) {
					systemService.deleteUser1(user);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		JSONObject obj = new JSONObject();
		try {
			obj.put("result", "ok");
		} catch (JSONException e) {

			e.printStackTrace();

		}
		response.getWriter().print(obj.toString());
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
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "selfExport", method = RequestMethod.POST)
	public String selfExportFile(@ModelAttribute("zxsTea") ZxsTea zxsTea,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {

			if (zxsTea == null || zxsTea.getOffice() == null
					|| zxsTea.getOffice().getId() == null) {
				String office_id = (String) CacheUtils.get("classOfficeId");
				zxsTea.setOffice(officeService.get(office_id));
			}
			String fileName = "老师数据_" + zxsTea.getOffice().getName() + ".xlsx";
			List<ZxsTea> page = zxsTeaService.findList(zxsTea);

			new ExportExcel("老师数据", ZxsTea.class).setDataList(page)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出老师数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/tea/selfList?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "selfImport", method = RequestMethod.POST)
	public String selfImportFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ZxsTea> list = ei.getDataList(ZxsTea.class);

			for (ZxsTea adm : list) {
				try {
					if (StringUtils.isBlank(adm.getTeaNo())) {
						adm.setTeaNo(adm.getTeaName());
					}
					String re = "[0-9A-Za-z] {0,6}";
					if (adm.getTeaNo().matches(re)) {
						failureMsg.append("<br/>导入失败：" + adm.getTeaNo()
								+ "登录名格式不符合规则");
						failureNum++;
						continue;
					}
					if (((String) CacheUtils.get("classOfficeId")).equals(adm
							.getOffice().getId())) {
						if (zxsTeaService.getUserByTeaNo(adm.getTeaNo()) != null
								|| zxsStuService.getUserByStuNo(adm.getTeaNo()) != null) {
							failureMsg.append("<br/>导入失败：" + adm.getTeaNo()
									+ "已存在");
							failureNum++;
							continue;
						}
						
						if (StringUtils.isBlank(adm.getXinsuanlv())) {
							adm.setXinsuanlv("0");
						}
						if (StringUtils.isBlank(adm.getZhusuanlv())) {
							adm.setZhusuanlv("0");
						}
						
						adm.setPwd(SystemService.entryptPassword(StringUtils
								.isBlank(adm.getPhone()) ? adm.getTeaNo() : adm
								.getPhone()));
						adm.setTel(com.thinkgem.jeesite.common.utils.StringUtils
								.phoneToNumber(adm.getTel()));
						zxsTeaService.saveResult(adm);
						successNum++;
					}

				} catch (ConstraintViolationException ex) {

					failureMsg.append("<br/>老师名" + adm.getTeaName() + " 导入失败：");
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>老师名" + adm.getTeaName() + " 导入失败：");
					failureNum++;
					ex.printStackTrace();
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条老师数据，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条老师数据"
					+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入老师数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/tea/selfList?repage";
	}

	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "selfBatchstop")
	public void selfBatchstop(@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws Exception {
		zxsTeaService.batchstop(datas);
		JSONObject obj = new JSONObject();
		obj.put("result", "ok");
		response.getWriter().print(obj.toString());
	}

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */

	@RequestMapping(value = "selfImport/template")
	public String selfImportFileTemplate(HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "老师数据导入模板.xlsx";
			List<ZxsTea> list = Lists.newArrayList();
			ZxsTea rt = new ZxsTea();
			rt.setTeaName("Alice");
			rt.setStudiedName("珠算宝");
			rt.setTeaNo("alice");

			rt.setSex("1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			rt.setBirth(sdf.parse(sdf.format(new Date())));
			rt.setNo("36242120109876");
			rt.setTel("1368888565");
			rt.setEmail("1111222@gmail.com");

			rt.setIsStudied(1);
			Office office = new Office();
			office.setName("东方书院");
			rt.setOffice(office);

			rt.setEntryDate(sdf.parse(sdf.format(new Date())));
			rt.setEdu("中山大学本科");
			rt.setEx("5年珠算老师");

			rt.setZhusuanlv("一级");
			rt.setXinsuanlv("一级");

			rt.setAddress("广东广州天河");

			list.add(rt);

			new ExportExcel("用户数据", ZxsTea.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes,
					"导入老师数据模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/tea/selfList?repage";
	}

	/**
	 * 保存或更改
	 * 
	 * @param zxsClass
	 * @return
	 */
	@RequiresPermissions("tea:edit:view")
	@RequestMapping(value = "selfSave")
	public String selfSave(ZxsTea zxsTea, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes) {
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(zxsTea.getNewPassword())) {
			zxsTea.setPwd(SystemService.entryptPassword(zxsTea.getNewPassword()));
		}
		if (!beanValidator(model, zxsTea)) {
			return selfForm(zxsTea, model);
		}
		if (!"true".equals(checkTeaNo(zxsTea.getOldTeaNo(), zxsTea.getTeaNo()))) {
			addMessage(model, "保存老师'" + zxsTea.getTeaNo() + "'失败，登录名已存在");
			return selfForm(zxsTea, model);
		}
		if (zxsStuService.getUserByStuNo(zxsTea.getTeaNo()) != null) {
			addMessage(model, "保存老师'" + zxsTea.getTeaNo() + "'失败，登录名已存在");
			return selfForm(zxsTea, model);
		}

		if (StringUtils.isNotBlank(zxsTea.getId())) {
			if ("1".equals(zxsTea.getAutoLevel())) {

				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("100");
				appPlayInfo.setAdminType("2");
				appPlayInfo.setAdminId(zxsTea.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			} else {
				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("0");
				appPlayInfo.setAdminType("2");
				appPlayInfo.setAdminId(zxsTea.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			}
		}
		zxsTeaService.saveResult(zxsTea);
		try {
			CacheUtils.remove(AdminUtils.CACHE_ZXSTEA_LIST);
		} catch (Exception ex) {
		}
		return "redirect:" + adminPath + "/tea/selfList?repage";
	}

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfDelete")
	public String selfDelete(ZxsTea zxsTea,
			RedirectAttributes redirectAttributes) {

		zxsTeaService.delete(zxsTea);
		addMessage(redirectAttributes, "删除老师成功");

		try {
			User user = systemService.getUser(zxsTea.getId());
			if (user != null) {
				systemService.deleteUser1(user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:" + adminPath + "/tea/selfList?repage";
	}

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfStop")
	public String selfStop(ZxsTea zxsTea, String del,
			RedirectAttributes redirectAttributes) {

		String tipStr = "停用";
		if ("0".equals(del)) {// 表示要激活
			tipStr = "激活";
		}
		zxsTea.setDelFlag(del);
		zxsTeaService.stop(zxsTea);
		addMessage(redirectAttributes, tipStr + "老师用户成功");

		return "redirect:" + adminPath + "/tea/selfList?repage";
	}

}
