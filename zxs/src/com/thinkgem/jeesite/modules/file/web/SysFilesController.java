/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.file.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.file.entity.SysFiles;
import com.thinkgem.jeesite.modules.file.service.SysFilesService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 附件表Controller
 * @author lijihui
 * @version 2016-11-24
 */
@Controller
@RequestMapping(value = "${adminPath}/file/sysFiles")
public class SysFilesController extends BaseController {

	@Autowired
	private SysFilesService sysFilesService;
	
	@ModelAttribute
	public SysFiles get(@RequestParam(required=false) String id) {
		SysFiles entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysFilesService.get(id);
		}
		if (entity == null){
			entity = new SysFiles();
		}
		return entity;
	}
	
	@RequiresPermissions("file:sysFiles:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysFiles sysFiles, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysFiles> page = sysFilesService.findPage(new Page<SysFiles>(request, response), sysFiles); 
		model.addAttribute("page", page);
		return "modules/file/sysFilesList";
	}

	@RequiresPermissions("file:sysFiles:view")
	@RequestMapping(value = "form")
	public String form(SysFiles sysFiles, Model model) {
		model.addAttribute("sysFiles", sysFiles);
		return "modules/file/sysFilesForm";
	}

	@RequiresPermissions("file:sysFiles:edit")
	@RequestMapping(value = "save")
	public String save(SysFiles sysFiles, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysFiles)){
			return form(sysFiles, model);
		}
		sysFilesService.save(sysFiles);
		addMessage(redirectAttributes, "保存附件成功");
		return "redirect:"+Global.getAdminPath()+"/file/sysFiles/?repage";
	}
	
	@RequiresPermissions("file:sysFiles:edit")
	@RequestMapping(value = "delete")
	public String delete(SysFiles sysFiles, RedirectAttributes redirectAttributes) {
		sysFilesService.delete(sysFiles);
		addMessage(redirectAttributes, "删除附件成功");
		return "redirect:"+Global.getAdminPath()+"/file/sysFiles/?repage";
	}

	@RequiresPermissions("file:sysFiles:edit")
    @RequestMapping(value = "batchdelete")
    public void batchdelete(@RequestParam(value = "datas[]")  String[]  datas ,  HttpServletResponse response ) throws IOException {
        sysFilesService.batchdelete(datas , UserUtils.getUser() );
        JSONObject obj = new JSONObject();
        try {
			obj.put("result", "ok");
		} catch (JSONException e) {
			  
			        //TODO  
			      
			        e.printStackTrace();  
			    
		}
        response.getWriter().print(obj.toString() );
    }

}