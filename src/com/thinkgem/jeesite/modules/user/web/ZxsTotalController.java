package com.thinkgem.jeesite.modules.user.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.utils.AdminUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.user.entity.ZxsClass;
import com.thinkgem.jeesite.modules.user.entity.ZxsStu;
import com.thinkgem.jeesite.modules.user.entity.ZxsTotal;
import com.thinkgem.jeesite.modules.user.service.ZxsClassService;
import com.thinkgem.jeesite.modules.user.service.ZxsTotalService;

@Controller
@RequestMapping(value = "${adminPath}/total")
public class ZxsTotalController extends BaseController {

	@Autowired
	private ZxsTotalService zxsTotalService;
	@Autowired
	private OfficeService officeService;

	@ModelAttribute("zxsTotal")
	public ZxsTotal get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return zxsTotalService.get(id);
		} else {
			return new ZxsTotal();
		}
	}

	@RequiresPermissions("total:list:view")
	@RequestMapping(value = { "totalIndex" })
	public String totalIndex(@ModelAttribute("zxsTotal") ZxsTotal zxsTotal,Model model) {
		
		return "modules/admin/totalIndex";
	}

	@RequiresPermissions("total:list:view")
	@RequestMapping(value = { "onlineIndex" })
	public String onlineIndex(Model model) {
		
		return "modules/admin/onlineIndex";
	}

	/**
	 * 统计记录
	 * 
	 * @author: lkun
	 * @createTime: 2017年9月28日 下午5:20:33
	 * @history:
	 * @param zxsClass
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequiresPermissions("total:list:view")
	@RequestMapping(value = { "list", "" })
	public String list(@ModelAttribute("zxsTotal") ZxsTotal zxsTotal, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (zxsTotal == null || zxsTotal.getOffice() == null
				|| StringUtils.isBlank(zxsTotal.getOffice().getId())) {
			zxsTotal.setOffice(UserUtils.getUser().getOffice());
		}

		Page<ZxsTotal> page = zxsTotalService.findPage(new Page<ZxsTotal>(
				request, response), zxsTotal);
		
		Office o=new Office();
		o.setId(zxsTotal.getOffice().getId());
		o=officeService.get(o);
		model.addAttribute("hLength", o.getParentIds().split(",").length);
		
		
		model.addAttribute("monthList", AdminUtils.getMonthList());
		model.addAttribute("yearList", AdminUtils.getYearList());
		model.addAttribute("page", page);
		return "modules/admin/totalList";
		
	}

	/**
	 * 实时统计
	 * 
	 * @author: lkun
	 * @createTime: 2017年9月28日 下午5:20:33
	 * @history:
	 * @param zxsClass
	 * @param request
	 * @param response
	 * @param model
	 * @return String
	 */
	@RequiresPermissions("total:list:view")
	@RequestMapping(value = { "online", "" })
	public String online(@ModelAttribute("zxsTotal") ZxsTotal zxsTotal, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (zxsTotal.getOffice() == null
				|| zxsTotal.getOffice().getId() == null) {
			zxsTotal.setOffice(UserUtils.getUser().getOffice());
		}
		zxsTotal = zxsTotalService.get(zxsTotal);
		zxsTotal.setOffice(officeService.get(zxsTotal.getOffice().getId()));
		model.addAttribute("zxsTotal", zxsTotal);
		return "modules/admin/totalOnline";
	}

}
