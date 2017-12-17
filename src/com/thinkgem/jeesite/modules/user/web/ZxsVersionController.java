package com.thinkgem.jeesite.modules.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.entity.ZxsVersion;
import com.thinkgem.jeesite.modules.user.service.ZxsVersionService;

@Controller
@RequestMapping(value = "${adminPath}/version")
public class ZxsVersionController extends BaseController {

	@Autowired
	private ZxsVersionService zxsVersionService;

	@ModelAttribute
	public ZxsVersion get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return zxsVersionService.get(id);
		} else {

			return new ZxsVersion();
		}
	}
}
