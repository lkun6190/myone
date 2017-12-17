/**
 * Copyright &copy; 2012-2014 <a href="http://www.huayingsoft.com">JGGFrame</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.LogService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 日志Controller
 * 
 * @author ThinkGem
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;

	@Autowired
	private SystemService systemService;

	@RequiresPermissions("sys:log:view")
	@RequestMapping(value = {"index",""})
	public String index(User user, Model model) {
		return "modules/sys/logIndex";
	}
	
	
	@RequiresPermissions("sys:log:view")
	@RequestMapping(value ="list")
	public String list(Log log, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		if (log != null && log.getCreateBy() != null
				&& StringUtils.isNotBlank(log.getCreateBy().getLoginName())) {
			User u = systemService.getUserByLoginName(log.getCreateBy()
					.getLoginName());
			String uid="0";
			if(u!=null){
				uid=u.getId();;
			}
			log.getCreateBy().setId(uid);
				
			log.setOffice(log.getCreateBy().getOffice());	
		}else{
			if(log.getOffice()==null||log.getOffice().getId()==null){
			log.setOffice(UserUtils.getUser().getOffice());
			}
		}
		
		Page<Log> page = logService.findPage(new Page<Log>(request, response),
				log);
		model.addAttribute("page", page);
		return "modules/sys/logList";
	}

}
