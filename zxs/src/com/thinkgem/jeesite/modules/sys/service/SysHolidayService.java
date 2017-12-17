/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.entity.SysHoliday;
import com.thinkgem.jeesite.modules.sys.dao.SysHolidayDao;

/**
 * 节假日Service
 * @author LLR
 * @version 2017-04-26
 */
@Service
@Transactional(readOnly = true)
public class SysHolidayService extends CrudService<SysHolidayDao, SysHoliday> {

	public SysHoliday get(String id) {
		return super.get(id);
	}
	
	public List<SysHoliday> findList(SysHoliday sysHoliday) {
		return super.findList(sysHoliday);
	}
	
	public Page<SysHoliday> findPage(Page<SysHoliday> page, SysHoliday sysHoliday) {
		return super.findPage(page, sysHoliday);
	}
	
	@Transactional(readOnly = false)
	public void save(SysHoliday sysHoliday) {
		super.save(sysHoliday);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysHoliday sysHoliday) {
		super.delete(sysHoliday);
	}

	@Transactional(readOnly = false)
	public void batchdelete(String [] ids, User update_by) {
		 for(String id : ids){
			 SysHoliday sysHoliday =super.get(id);
			 sysHoliday.setUpdateBy(update_by);
			 super.delete(sysHoliday);
		 }
	}
	
	 //查询工作日是否为假期还是工作日
	@SuppressWarnings("unused")
	public String findXWorkDay(String beginDate,int offset,String lx){
    	String time="";        //经过处理返回的工作日
    	Date shijina=null;     //开始时间
    	Date pdDate=null;      //延迟后的时间
    	String pdtime="";      //跟数据库匹配时间
    	SysHoliday sysHoliday=new SysHoliday();
    	if(beginDate!=null){
    		shijina=DateUtils.parseDate(beginDate);
    		//经过推算得出的时间
    	    pdDate=getDateAfter(shijina, offset);
    	    pdtime=DateFormatUtils.format(pdDate, "yyyy-MM-dd");
    	    
    	    //查询这段时间内有无节假日
    	    SysHoliday sysHolidayjs=new SysHoliday();
    	    sysHolidayjs.setBeginDate(beginDate);
    	    sysHolidayjs.setHtype("2");
    	    sysHolidayjs.setEndDate(pdtime);
    	    List<SysHoliday> jqlist=dao.findList(sysHolidayjs);
    	    
    	    //得到有几天节假日
    	    int jjrts=jqlist.size();
    	    SysHoliday sysHoliday2=new SysHoliday();
    	    sysHoliday2.setHtype(lx);
    	    //如果存在节假日的时候
    		if(jjrts>0){
    			pdDate=getDateAfter(pdDate, jjrts);
    			pdtime=DateFormatUtils.format(pdDate, "yyyy-MM-dd");
    			
    			SysHoliday sys=new SysHoliday();
    			sys.setHtype(lx);
    			sys.setHdate(pdtime);
    			List<SysHoliday> jjrlist=dao.findList(sys);
    				//如果是工作日
	        		if(jjrlist.size()>0){
	        			sysHoliday=jjrlist.get(0);
	        			time=sysHoliday.getHdate();
	        		}else{
	        			//在延迟的基础上再加一天，推至工作日截止
	        			String[] dateTime=pdtime.split("-");
	        			String year=dateTime[0];
	        			String month=dateTime[1];
	        			
	        			SysHoliday ff=new SysHoliday();
	        			ff.setHyear(year);
	        			ff.setHmonth(month);
	        			List<SysHoliday> yslist=dao.findList(ff);
	        			for(SysHoliday sysholidaylist:yslist){
	        				pdDate=getDateAfter(pdDate, 1);
	            			pdtime=DateFormatUtils.format(pdDate, "yyyy-MM-dd");
	            			sysHoliday2.setHdate(pdtime);
	            			List<SysHoliday> yclist=dao.findList(sysHoliday2);
	            			if(yclist.size()>0){
	            				sysHoliday=yclist.get(0);
	            				if(sysHoliday.getHtype().equals("1")){
	            					time=sysHoliday.getHdate();
	            					break;
	            				}
	            			}
	        			}
	        		}
        		}else{
        			//判断是否是节假日
        			sysHoliday2.setHdate(pdtime);
            		List<SysHoliday> sjlist=dao.findList(sysHoliday2);
        			if(sjlist.size()>0){
            			sysHoliday=sjlist.get(0);
            			time=sysHoliday.getHdate();
            		}else{
    	        			//在延迟的基础上再加一天，推至工作日截止
    	        			String[] dateTime=pdtime.split("-");
    	        			String year=dateTime[0];
    	        			String month=dateTime[1];
    	        			
    	        			SysHoliday ff=new SysHoliday();
    	        			ff.setHyear(year);
    	        			ff.setHmonth(month);
    	        			List<SysHoliday> yslist=dao.findList(ff);
    	        			for(SysHoliday sysholidaylist:yslist){
    	        				pdDate=getDateAfter(pdDate, 1);
    	            			pdtime=DateFormatUtils.format(pdDate, "yyyy-MM-dd");
    	            			sysHoliday2.setHdate(pdtime);
    	            			List<SysHoliday> yclist=dao.findList(sysHoliday2);
    	            			if(yclist.size()>0){
    	            				sysHoliday=yclist.get(0);
    	            				if(sysHoliday.getHtype().equals("1")){
    	            					time=sysHoliday.getHdate();
    	            					break;
    	            				}
    	            			}
    	        			}
    	        		}
            	}
    	  }
    	return time;
    }
	
    /** 
     * 得到几天后的时间 
     *  
     * @param d 
     * @param day 
     * @return 
     */  
    public static Date getDateAfter(Date d, int day) {  
        Calendar now = Calendar.getInstance();  
        now.setTime(d);  
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);  
        return now.getTime();  
    }  
    
}