package com.thinkgem.jeesite.modules.user.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.AppPlayInfo;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;
import com.thinkgem.jeesite.modules.user.service.ZxsClassService;
import com.thinkgem.jeesite.modules.user.service.ZxsStuService;
import com.thinkgem.jeesite.modules.user.service.ZxsTeaService;

@Controller
@RequestMapping(value = "${adminPath}/stu")
public class ZxsStuController extends BaseController {

	@Autowired
	private ZxsStuService zxsStuService;
	@Autowired
	private ZxsTeaService zxsTeaService;
	@Autowired
	private ZxsClassService zxsClassService;

	@Autowired
	private OfficeService officeService;

	@ModelAttribute("zxsStu")
	public ZxsStu get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return zxsStuService.get(id);
		} else {
			return new ZxsStu();
		}
	}

	@RequiresPermissions("stu:list:view")
	@RequestMapping(value = { "index" })
	public String index(@ModelAttribute("zxsStu") ZxsStu zxsStu, Model model) {
		return "modules/admin/zxsStuIndex";
	}

	/**
	 * 验证登录名是否有效
	 * 
	 * @param oldStuNo
	 * @param stuNo
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "checkStuNo")
	public String checkStuNo(String oldStuNo, String stuNo) {

		if (stuNo != null && stuNo.equals(oldStuNo)) {
			return "true";
		} else if (stuNo != null && zxsStuService.getUserByStuNo(stuNo) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 公共的
	 * 
	 * @param zxsStu
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("stu:list:view")
	@RequestMapping(value = { "list", "" })
	public String list(@ModelAttribute("zxsStu") ZxsStu zxsStu,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (zxsStu == null || zxsStu.getOffice() == null
				|| zxsStu.getOffice().getId() == null) {
			zxsStu.setOffice(UserUtils.getUser().getOffice());
		}
		session.setAttribute("classOfficeId", "");
		Page<ZxsStu> page = new Page<ZxsStu>(request, response);
		page.setOrderBy("o.name, c.class_no,a.zhusuanlv");

		if (StringUtils.isNotBlank(UserUtils.getUser().getClassTemp())) {

			String[] classIds = UserUtils.getUser().getClassTemp().split(",");
			List<ZxsClass> zxsClassList = new ArrayList<ZxsClass>();
			for (String classId : classIds) {

				zxsClassList.add(zxsClassService.get(classId));

			}
			model.addAttribute("zxsClassList", zxsClassList);
			model.addAttribute("temp", 0);

			if (zxsStu == null || zxsStu.getZxsClass() == null
					|| StringUtils.isBlank(zxsStu.getZxsClass().getId())) {

				zxsStu.setZxsClaArr(Arrays.asList(classIds));
				zxsStu.setZxsClaString("ddd");
			}
			page = zxsStuService.findPage(page, zxsStu);

			model.addAttribute("page", page);

		} else {
			model.addAttribute("temp", 1);

			page = zxsStuService.findPage(page, zxsStu);

			model.addAttribute("page", page);
		}
		return "modules/admin/stuList";
	}

	/**
	 * 班级下的学生列表
	 * 
	 * @param zxsStu
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("stu:list:view")
	@RequestMapping(value = "selfList")
	public String selfList(
			@ModelAttribute("zxsStu") ZxsStu zxsStu,
			@RequestParam(value = "classStuId", required = false) String classStuId,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ZxsClass zxsClass = new ZxsClass();
		if (classStuId != null && !"".equals(classStuId)
				&& !"0".equals(classStuId)) {
			CacheUtils.put("classStuId", classStuId);
		} else {
			classStuId = (String) CacheUtils.get("classStuId");
		}
		zxsClass.setId(classStuId);
		if (zxsStu == null) {
			zxsStu = new ZxsStu();
		}
		zxsStu.setZxsClass(zxsClass);
		Page<ZxsStu> page = new Page<ZxsStu>(request, response);
		page.setOrderBy("o.name, c.class_no,a.zhusuanlv");
		page = zxsStuService.findPage(page, zxsStu);
		model.addAttribute("page", page);
		return "modules/admin/selfStuList";
	}

	// 个人信息
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfForm")
	public String selfForm(ZxsStu zxsStu, Model model, HttpSession session) {

		// zxsStu = zxsStuService.get(id);

		if (zxsStu == null || zxsStu.getZxsClass() == null
				|| zxsStu.getZxsClass().getId() == null) {
			String classId = (String) CacheUtils.get("classStuId");
			if (classId != null) {
				ZxsClass zc = zxsClassService.get(classId);
				Office office = zc.getOffice();
				zxsStu.setOffice(office);
				zxsStu.setZxsClass(zc);
			}
		}
		if (zxsStu != null && zxsStu.getAppPlayInfo() != null
				&& zxsStu.getAppPlayInfo().getLessonsHis() != null) {
			String lessionHis = zxsStu.getAppPlayInfo().getLessonsHis();
			if (StringUtils.isNoneBlank(lessionHis) && lessionHis.contains("#")) {
				AppPlayInfo appPlayInfo = zxsStu.getAppPlayInfo();
				appPlayInfo.setLessonsHis(lessionHis.replaceAll("#", "\n")
						.replaceAll("null", ""));
				zxsStu.setAppPlayInfo(appPlayInfo);
			}
		}
		if (zxsStu != null && zxsStu.getAppPlayInfo() != null) {
			if (Integer.parseInt(zxsStu.getAppPlayInfo().getWorksLevel()) >= 100) {
				zxsStu.setAutoLevel("1");
			} else {
				zxsStu.setAutoLevel("0");
			}
		}
		if (zxsStu != null && zxsStu.getOffice() != null
				&& StringUtils.isNotBlank(zxsStu.getOffice().getId())) {
			session.setAttribute("classOfficeId", zxsStu.getOffice().getId());
		}
		model.addAttribute("zxsStu", zxsStu);

		return "modules/admin/selfStuForm";
	}

	// 个人信息
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "stuMsg")
	public String stuMsg(ZxsStu zxsStu, Model model) {

		// zxsStu = zxsStuService.get(id);

		if (zxsStu == null || zxsStu.getZxsClass() == null
				|| zxsStu.getZxsClass().getId() == null) {
			String classId = (String) CacheUtils.get("classStuId");
			if (classId != null) {
				ZxsClass zc = zxsClassService.get(classId);
				Office office = zc.getOffice();
				zxsStu.setOffice(office);
				zxsStu.setZxsClass(zc);
			}
		}
		if (zxsStu != null && zxsStu.getAppPlayInfo() != null
				&& zxsStu.getAppPlayInfo().getLessonsHis() != null) {
			String lessionHis = zxsStu.getAppPlayInfo().getLessonsHis();
			if (StringUtils.isNoneBlank(lessionHis) && lessionHis.contains("-")) {
				AppPlayInfo appPlayInfo = zxsStu.getAppPlayInfo();
				appPlayInfo.setLessonsHis(lessionHis.replaceAll("-", "\n")
						.replaceAll("null", ""));
				zxsStu.setAppPlayInfo(appPlayInfo);
			}
		}
		model.addAttribute("zxsStu", zxsStu);

		return "modules/admin/stuMsg";
	}

	// 个人信息
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = { "form", "" })
	public String rycxForm(ZxsStu zxsStu, HttpSession session, Model model) {

		// zxsStu = zxsStuService.get(id);
		String root_tmp = "true";
		try {
			String currId = UserUtils.getUser().getOffice().getParentIds();
			int h = currId.split(",").length;

			if (h == 4) {
				root_tmp = "false";
			}
		} catch (Exception ex) {
		}
		zxsStu.setRoot_tmp(root_tmp);

		if (zxsStu != null && zxsStu.getAppPlayInfo() != null
				&& zxsStu.getAppPlayInfo().getLessonsHis() != null) {
			String lessionHis = zxsStu.getAppPlayInfo().getLessonsHis();
			if (StringUtils.isNoneBlank(lessionHis) && lessionHis.contains("#")) {
				AppPlayInfo appPlayInfo = zxsStu.getAppPlayInfo();
				appPlayInfo.setLessonsHis(lessionHis.replaceAll("#", "\n")
						.replaceAll("null", ""));
				zxsStu.setAppPlayInfo(appPlayInfo);
			}
		}
		if (zxsStu != null && zxsStu.getAppPlayInfo() != null) {
			if (Integer.parseInt(zxsStu.getAppPlayInfo().getWorksLevel()) >= 100) {
				zxsStu.setAutoLevel("1");
			} else {
				zxsStu.setAutoLevel("0");
			}
		}
		if (zxsStu != null && zxsStu.getOffice() != null
				&& StringUtils.isNotBlank(zxsStu.getOffice().getId())) {
			session.setAttribute("classOfficeId", zxsStu.getOffice().getId());
		}
		model.addAttribute("zxsStu", zxsStu);

		return "modules/admin/stuForm";
	}

	/**
	 * 批量删除
	 * 
	 * @param datas
	 * @param response
	 * @throws IOException
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "batchdelete")
	public void batchdelete(@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws IOException {
		zxsStuService.batchdelete(datas);
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
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(@ModelAttribute("zxsStu") ZxsStu zxsStu,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "学生数据_" + DateUtils.getDate("yyyyMMddHHmmss")
					+ ".xlsx";

			if (zxsStu == null || zxsStu.getOffice() == null
					|| zxsStu.getOffice().getId() == null) {
				zxsStu.setOffice(UserUtils.getUser().getOffice());
			}

			List<ZxsStu> page = zxsStuService.findList(zxsStu);

			new ExportExcel("学生数据", ZxsStu.class).setDataList(page)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学生数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/stu/list?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ZxsStu> list = ei.getDataList(ZxsStu.class);

			for (ZxsStu zxsStu : list) {
				try {

					String stuNo = zxsStuService.selectAcc();
					zxsStu.setStuNo(stuNo);
					String pwd = stuNo;

					Date birth = zxsStu.getBirth();
					if (birth != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
						pwd = sdf.format(birth);
					}
					if (StringUtils.isBlank(zxsStu.getXinsuanlv())) {
						zxsStu.setXinsuanlv("0");
					}
					if (StringUtils.isBlank(zxsStu.getZhusuanlv())) {
						zxsStu.setZhusuanlv("0");
					}

					zxsStu.setPwd(SystemService.entryptPassword(pwd));
					BeanValidators.validateWithException(validator, zxsStu);

					zxsStu.setTel(com.thinkgem.jeesite.common.utils.StringUtils
							.phoneToNumber(zxsStu.getTel()));
					zxsStu.setParentTel(com.thinkgem.jeesite.common.utils.StringUtils
							.phoneToNumber(zxsStu.getParentTel()));
					zxsStuService.saveResult(zxsStu);

					zxsStuService.updateAcc(stuNo);
					successNum++;

				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>学生名" + zxsStu.getStuName()
							+ " 导入失败：");
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>学生名" + zxsStu.getStuName()
							+ " 导入失败：");
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 个学生，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 个学生"
					+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学生数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/stu/list?repage";
	}

	/**
	 * 批量停用
	 * 
	 * @param datas
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "batchstop")
	public void batchstop(@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws Exception {
		zxsStuService.batchStop(datas);
		JSONObject obj = new JSONObject();
		obj.put("result", "ok");
		response.getWriter().print(obj.toString());
	}

	/**
	 * 批量激活
	 * 
	 * @param datas
	 * @param response
	 * @throws Exception
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "batchstart")
	public void batchstart(@RequestParam(value = "datas[]") String[] datas,
			HttpServletResponse response) throws Exception {
		zxsStuService.batchStart(datas);
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
	public String importFileTemplate(ZxsStu zxsStu,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户导入模板.xlsx";
			List<ZxsStu> list = Lists.newArrayList();
			if (zxsStu == null) {
				zxsStu = new ZxsStu();
			}
			zxsStu.setStuName("Alice");
			zxsStu.setStudiedName("珠算宝");
			ZxsClass zxsClass = new ZxsClass();
			zxsClass.setClassName("class1");
			zxsStu.setZxsClass(zxsClass);
			zxsStu.setSex("1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			zxsStu.setBirth(sdf.parse(sdf.format(new Date())));
			zxsStu.setNo("36242120109876");
			zxsStu.setTel("1368888565");
			zxsStu.setEmail("1111222@gmail.com");
			zxsStu.setGold("12");
			zxsStu.setIsStudied(1);
			Office office = new Office();
			office.setName("东方书院");
			zxsStu.setOffice(office);
			zxsStu.setPayment(1);
			zxsStu.setPayDate(sdf.parse(sdf.format(new Date())));
			zxsStu.setZhusuanlv("一级");
			zxsStu.setXinsuanlv("一级");
			zxsStu.setEntryDate(sdf.parse(sdf.format(new Date())));
			zxsStu.setParentName("Join");
			zxsStu.setParentTel("12345678");
			zxsStu.setRex("父亲");
			zxsStu.setAddress("广东广州天河");
			list.add(zxsStu);
			new ExportExcel("学生数据", ZxsStu.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes,
					"导入学生数据模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/stu/list?repage";
	}

	/**
	 * 保存或更改
	 * 
	 * @param zxsClass
	 * @return
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "save")
	public String abvSave(ZxsStu zxsStu, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes,
			HttpSession session) {
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(zxsStu.getNewPassword())) {
			zxsStu.setPwd(SystemService.entryptPassword(zxsStu.getNewPassword()));
		}
		if (!beanValidator(model, zxsStu)) {
			return rycxForm(zxsStu, session, model);
		}
		if (!"true".equals(checkStuNo(zxsStu.getOldStuNo(), zxsStu.getStuNo()))) {
			addMessage(model, "保存学生'" + zxsStu.getStuNo() + "'失败，登录名已存在");
			return rycxForm(zxsStu, session, model);
		}
		if (zxsTeaService.getUserByTeaNo(zxsStu.getStuNo()) != null) {
			addMessage(model, "保存学生'" + zxsStu.getStuNo() + "'失败，登录名已存在");
			return rycxForm(zxsStu, session, model);
		}

		Office office = new Office();
		office.setId(zxsClassService.get(zxsStu.getZxsClass().getId())
				.getOffice().getId());
		zxsStu.setOffice(office);
		if (StringUtils.isBlank(zxsStu.getGold())) {
			zxsStu.setGold("0");
		}
		if (StringUtils.isNotBlank(zxsStu.getId())) {
			if ("1".equals(zxsStu.getAutoLevel())) {

				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("100");
				appPlayInfo.setAdminType("1");
				appPlayInfo.setAdminId(zxsStu.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			} else {
				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("0");
				appPlayInfo.setAdminType("1");
				appPlayInfo.setAdminId(zxsStu.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			}
		}
		zxsStuService.saveResult(zxsStu);

		return "redirect:" + adminPath + "/stu/list?repage&office.id="
				+ zxsStu.getOffice().getId();
	}

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "delete")
	public String delete(ZxsStu zxsStu, RedirectAttributes redirectAttributes) {

		zxsStuService.delete(zxsStu);
		addMessage(redirectAttributes, "删除学生成功");

		return "redirect:" + adminPath + "/stu/list?repage";
	}

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "stop")
	public String stop(ZxsStu zxsStu, String del,
			RedirectAttributes redirectAttributes) {

		String tipStr = "停用";
		if ("0".equals(del)) {// 表示要激活
			tipStr = "激活";
		}
		zxsStu.setDelFlag(del);
		zxsStuService.stop(zxsStu);
		addMessage(redirectAttributes, tipStr + "用户成功");

		return "redirect:" + adminPath + "/stu/list?repage";
	}

	/*******************************************************************************************/

	/**
	 * 导出用户数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfExport", method = RequestMethod.POST)
	public String selfExportFile(@ModelAttribute("zxsStu") ZxsStu zxsStu,
			HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {

			ZxsClass zxsClass = new ZxsClass();

			String classStuId = (String) CacheUtils.get("classStuId");

			zxsClass.setId(classStuId);
			if (zxsStu == null) {
				zxsStu = new ZxsStu();
			}
			zxsStu.setZxsClass(zxsClass);
			String fileName = "学生数据_" + zxsStu.getZxsClass().getClassNo()
					+ ".xlsx";

			List<ZxsStu> page = zxsStuService.findList(zxsStu);
			new ExportExcel("学生数据", ZxsStu.class).setDataList(page)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学生数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/stu/selfList?repage";
	}

	/**
	 * 导入用户数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfImport", method = RequestMethod.POST)
	public String selfImportFile(MultipartFile file,
			RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ZxsStu> list = ei.getDataList(ZxsStu.class);
			for (ZxsStu zxsStu : list) {
				try {

					if (((String) CacheUtils.get("classStuId")).equals(zxsStu
							.getZxsClass().getId())) {
						String stuNo = zxsStuService.selectAcc();
						zxsStu.setStuNo(stuNo);
						String pwd = stuNo;

						Date birth = zxsStu.getBirth();
						if (birth != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
							pwd = sdf.format(birth);
						}
						
						if (StringUtils.isBlank(zxsStu.getXinsuanlv())) {
							zxsStu.setXinsuanlv("0");
						}
						if (StringUtils.isBlank(zxsStu.getZhusuanlv())) {
							zxsStu.setZhusuanlv("0");
						}

						zxsStu.setPwd(SystemService.entryptPassword(pwd));
						BeanValidators.validateWithException(validator, zxsStu);
						zxsStu.setTel(com.thinkgem.jeesite.common.utils.StringUtils
								.phoneToNumber(zxsStu.getTel()));
						zxsStu.setParentTel(com.thinkgem.jeesite.common.utils.StringUtils
								.phoneToNumber(zxsStu.getParentTel()));
						zxsStuService.saveResult(zxsStu);
						zxsStuService.updateAcc(stuNo);
						successNum++;
					}

				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>学生名" + zxsStu.getStuName()
							+ " 导入失败：");
					List<String> messageList = BeanValidators
							.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>学生名" + zxsStu.getStuName()
							+ " 导入失败：");
					ex.printStackTrace();
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 个学生，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 个学生"
					+ failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学生数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/stu/selfList?repage";
	}

	/*
	 * @RequiresPermissions("stu:edit:view")
	 * 
	 * @RequestMapping(value = "batchstop") public void
	 * batchstop(@RequestParam(value = "datas[]") String[] datas,
	 * HttpServletResponse response) throws Exception {
	 * zxsStuService.batchstop(datas); JSONObject obj = new JSONObject();
	 * obj.put("result", "ok"); response.getWriter().print(obj.toString()); }
	 */

	/**
	 * 下载导入用户数据模板
	 * 
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */

	@RequestMapping(value = "selfImport/template")
	public String selfImportFileTemplate(ZxsStu zxsStu,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "学生导入模板.xlsx";
			List<ZxsStu> list = Lists.newArrayList();
			if (zxsStu == null) {
				zxsStu = new ZxsStu();

			}
			zxsStu.setStuName("Alice");
			zxsStu.setStudiedName("珠算宝");
			ZxsClass zxsClass = new ZxsClass();
			zxsClass.setClassName("智信班");
			zxsStu.setZxsClass(zxsClass);
			zxsStu.setSex("1");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			zxsStu.setBirth(sdf.parse(sdf.format(new Date())));
			zxsStu.setNo("36242120109876");
			zxsStu.setTel("1368888565");
			zxsStu.setEmail("1111222@gmail.com");
			zxsStu.setGold("12");
			zxsStu.setIsStudied(1);
			Office office = new Office();
			office.setName("东方书院");
			zxsStu.setOffice(office);
			zxsStu.setPayment(1);
			zxsStu.setPayDate(sdf.parse(sdf.format(new Date())));
			zxsStu.setZhusuanlv("一级");
			zxsStu.setXinsuanlv("一级");
			zxsStu.setEntryDate(sdf.parse(sdf.format(new Date())));
			zxsStu.setParentName("Join");
			zxsStu.setParentTel("12345678");
			zxsStu.setRex("父亲");
			zxsStu.setAddress("广东广州天河");
			list.add(zxsStu);
			new ExportExcel("学生数据", ZxsStu.class, 2).setDataList(list)
					.write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes,
					"导入学生数据模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/stu/selfList?repage";
	}

	/**
	 * 保存或更改
	 * 
	 * @param zxsClass
	 * @return
	 */
	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfSave")
	public String selfSave(ZxsStu zxsStu, HttpServletRequest request,
			Model model, RedirectAttributes redirectAttributes,
			HttpSession session) {
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(zxsStu.getNewPassword())) {
			zxsStu.setPwd(SystemService.entryptPassword(zxsStu.getNewPassword()));
		}
		if (!beanValidator(model, zxsStu)) {
			return selfForm(zxsStu, model, session);
		}
		if (!"true".equals(checkStuNo(zxsStu.getOldStuNo(), zxsStu.getStuNo()))) {
			addMessage(model, "保存学生'" + zxsStu.getStuNo() + "'失败，登录名已存在");
			return selfForm(zxsStu, model, session);
		}
		if (zxsTeaService.getUserByTeaNo(zxsStu.getStuNo()) != null) {
			addMessage(model, "保存学生'" + zxsStu.getStuNo() + "'失败，登录名已存在");
			return selfForm(zxsStu, model, session);
		}

		Office office = new Office();
		office.setId(zxsClassService.get(zxsStu.getZxsClass().getId())
				.getOffice().getId());
		zxsStu.setOffice(office);
		if (StringUtils.isBlank(zxsStu.getGold())) {
			zxsStu.setGold("0");
		}
		if (StringUtils.isNotBlank(zxsStu.getId())) {
			if ("1".equals(zxsStu.getAutoLevel())) {

				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("100");
				appPlayInfo.setAdminType("1");
				appPlayInfo.setAdminId(zxsStu.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			} else {
				AppPlayInfo appPlayInfo = new AppPlayInfo();
				appPlayInfo.setWorksLevel("0");
				appPlayInfo.setAdminType("1");
				appPlayInfo.setAdminId(zxsStu.getId());
				zxsStuService.updateAppInfo(appPlayInfo);
			}
		}
		zxsStuService.saveResult(zxsStu);
		return "redirect:" + adminPath + "/stu/selfList?repage";
	}

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfDelete")
	public String selfDelete(ZxsStu zxsStu,
			RedirectAttributes redirectAttributes) {

		zxsStuService.delete(zxsStu);
		addMessage(redirectAttributes, "删除学生成功");

		return "redirect:" + adminPath + "/stu/selfList?repage";
	}

	@RequiresPermissions("stu:edit:view")
	@RequestMapping(value = "selfStop")
	public String selfStop(ZxsStu zxsStu, String del,
			RedirectAttributes redirectAttributes) {

		String tipStr = "停用";
		if ("0".equals(del)) {// 表示要激活
			tipStr = "激活";
		}
		zxsStu.setDelFlag(del);
		zxsStuService.stop(zxsStu);
		addMessage(redirectAttributes, tipStr + "用户成功");

		return "redirect:" + adminPath + "/stu/selfList?repage";
	}
}