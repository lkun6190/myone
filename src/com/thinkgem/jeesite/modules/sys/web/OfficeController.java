/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * 
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	@ModelAttribute("office")
	public Office get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return officeService.get(id);
		} else {
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "" })
	public String index(Office office, Model model) {
		// model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = { "list" })
	public String list(Office office, Model model, @RequestParam(value = "offAid", required = false) String offAid) {

		try {

			if (StringUtils.isBlank(offAid)) {
				offAid = (String) CacheUtils.get("offAid");
			} else {
				CacheUtils.put("offAid", offAid);
			}

			if (office != null && StringUtils.isBlank(office.getId())) {
				Office o = officeService.get(offAid);
				office.setId(o.getParentId());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		List<Office> list = officeService.findList(office, offAid);
		list.add(officeService.get(offAid));
		for (Office offic : list) {
			if ("4".equals(offic.getGrade())) {
				offic.setTip_tmp("进入机构或学校");
			} else {
				offic.setTip_tmp("添加下级机构");
			}
		}

		String currId = UserUtils.getUser().getOffice().getParentIds();
		int h = currId.split(",").length;

		if (h == 4) {
			model.addAttribute("roo_tmp", 2);
		} else {
			model.addAttribute("roo_tmp", 1);
		}

		model.addAttribute("list", list);

		return "modules/sys/officeList";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model, Integer grade, HttpServletRequest request) {
		String parentId = request.getParameter("parent.id");
		if(StringUtils.isBlank(parentId)&&office!=null){
			
		}else{

		if (grade != null && grade == 4) {

			return "redirect:" + adminPath + "/cla/list?parent.id=" + office.getParentId();
		}

		if (office == null) {
			office = new Office();
		}

		if (StringUtils.isBlank(parentId)) {//添加

			office.setParent(UserUtils.getUser().getOffice());
			if (office.getArea() == null) {
				office.setArea(UserUtils.getUser().getOffice().getArea());
			}

		} else {
			Office op = officeService.get(parentId);
			office.setParent(op);
			if (office.getArea() == null) {
				office.setArea(op.getArea());
			}
		}
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId()) && office.getParent() != null) {//修改
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i = 0; i < list.size(); i++) {
				Office e = list.get(i);
				if (e.getParent() != null && e.getParent().getId() != null
						&& e.getParent().getId().equals(office.getParent().getId())) {
					size++;
				}
			}
			office.setCode(office.getParent().getCode()
					+ StringUtils.leftPad(String.valueOf(size > 0 ? size + 1 : 1), 3, "0"));
		}
		office.setCode(office.getCode().replace("null", "00"));
		model.addAttribute("office", office);
		// model.addAttribute("grade", grade);//1全球,2一级代理,3二级代理,4三级代理

		return "modules/sys/officeForm";
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)) {
			
			return form(office, model, 1, request);
		}
		
		
		
		if(StringUtils.isNotBlank(office.getParentId())&&StringUtils.isNotBlank(office.getName())){
			if (!"true".equals(checkStuNo(office.getOldName(), office.getName(),office))) {
				addMessage(redirectAttributes, "机构代理名存在！");
				return form(office, model, 1, request);
			}
			
		}

		office.setGrade(office.getType());// 默认级别和类型是一样的

		officeService.save(office);

		if (office.getChildDeptList() != null) {
			Office childOffice = null;
			for (String id : office.getChildDeptList()) {
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade()) + 1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}

		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		if (UserUtils.getUser().isAdmin()) {
			return "redirect:" + adminPath + "/sys/office/list?id=0&parentIds=0&offAid=1";
		}
		return "redirect:" + adminPath + "/sys/office/list?id=" + id + "&parentIds=" + office.getParentIds();
	}

	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}

		if (office.getId().equals(UserUtils.getUser().getOffice().getId())) {
			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构,编号空或当前机构");
		} else {
			officeService.delete(office);

			officeService.deleteAllStuByOffice(office);
			officeService.deleteAllClassByOffice(office);
			officeService.deleteAllTeaByOffice(office);

			addMessage(redirectAttributes, "删除机构及以下机构和机构的班级老师学生成功");

		}

		if (UserUtils.getUser().isAdmin()) {
			return "redirect:" + adminPath + "/sys/office/list?id=0&parentIds=0&offAid=1";
		}
		return "redirect:" + adminPath + "/sys/office/list?id=" + office.getParentId() + "&parentIds="
				+ office.getParentIds();
	}

	/**
	 * 获取机构JSON数据。
	 * 
	 * @param extId
	 *            排除的ID
	 * @param type
	 *            类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade
	 *            显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId,
			@RequestParam(required = false) String type, @RequestParam(required = false) Long grade,
			@RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);

		for (int i = 0; i < list.size(); i++) {
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId)
					|| (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)) {
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	public String checkStuNo(String oldName, String name,Office office) {

		if (name != null && name.equals(oldName)) {
			return "true";
		} else if (name != null && officeService.getOfficeByOne(office) == null) {
			return "true";
		}
		return "false";
	}
}
