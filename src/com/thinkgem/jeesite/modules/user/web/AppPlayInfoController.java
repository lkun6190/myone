package com.thinkgem.jeesite.modules.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.user.service.AppPlayInfoService;

@Controller
@RequestMapping(value = "${adminPath}/playInfo")
public class AppPlayInfoController extends BaseController {

	@Autowired
	private AppPlayInfoService appPlayInfoService;
	
}