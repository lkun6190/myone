package com.thinkgem.jeesite.modules.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.OfficeDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.user.entity.ZxsTotal;
import com.thinkgem.jeesite.modules.user.service.ZxsStuService;
import com.thinkgem.jeesite.modules.user.service.ZxsTotalService;

/**
 * 查询总账定时任务
 */

@Component("insertTotalTask")
public class InsertTotalTask {
	@Autowired
	ZxsTotalService zxsTotalService;
	
	@Autowired
	ZxsStuService zxsStuService;
	
	
	@Autowired
	OfficeService officeService;

	// private OfficeDao officeDao =
	// SpringContextHolder.getBean(OfficeDao.class);

	public void insertTotal() {
		try {

			ZxsTotal zxsTotal = new ZxsTotal();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			List<ZxsTotal> total_list = new ArrayList<ZxsTotal>();

			List<Office> off_list = officeService
					.findAllOfficeList(new Office());
			for (Office off : off_list) {				
				
				zxsTotal.setOffice(off);
				ZxsTotal zt= zxsTotalService.getMonth(zxsTotal);
				
				//zxsTotal.setOffice(zt.getOffice());
				zt.setYear(year);
				zt.setMonth(month);
				zt.setId(IdGen.uuid());
				total_list.add(zt);
			}
			zxsTotalService.batchSave(total_list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void clearOrderBySort(){
		zxsStuService.clearOrderBySort();
	}

}
