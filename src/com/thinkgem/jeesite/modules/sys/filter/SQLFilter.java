/**
 * $RCSfile: SQLFilter.java,v $
 * $Revision: 1.7 $
 * $Date: 2011/12/16 10:24:27 $
 *
 * Copyright (C) 2003 ICSS, Inc. All rights reserved.
 *
 * This software is the proprietary information of ICSS, Inc.
 * Use is subject to license terms.
 */
package com.thinkgem.jeesite.modules.sys.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Title: 信息发布系统2.0（CMS2.0）
 * 
 * Description: 防SQL注入Filter
 * 
 * Copyright: Copyright (c) 2002-2007
 * 
 * Company: 中软国际
 * 
 * @version 1.0
 * @author 张亮(Felankia)
 */
public class SQLFilter implements Filter {
	
	protected static final Logger logger=Logger.getLogger(SQLFilter.class);
	
	protected String encoding = null;

	protected FilterConfig filterConfig = null;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	
	
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest re=(HttpServletRequest)request;
		HttpServletResponse hres = (HttpServletResponse) response;
		String uri=re.getRequestURI();
		if(uri.toLowerCase().indexOf("script%3e")!=-1 || uri.toLowerCase().indexOf("script>")!=-1){
			hres.sendRedirect("modules/views/error/500.jsp");
		}
		
	
		
		Enumeration tmp = request.getParameterNames();
		boolean hasSQLKeyWord = false;
		while (tmp.hasMoreElements()) {
			Object t = tmp.nextElement();
			if (((String) t).equals("CONTENT")
					|| ((String) t).equals("REPLY_CONTENT")) {
				continue;
			}
			if(((String) t).toLowerCase().contains("script>")||((String) t).toLowerCase().contains("alert")){
				hasSQLKeyWord = true;
				break;
			}
			String str = request.getParameter((String) t);
			if (sql_inj(str)) {
				hasSQLKeyWord = true;
				break;
			}
		}
		if (hasSQLKeyWord) {
			hres.sendRedirect("modules/views/error/500.jsp");
		} else {
			chain.doFilter(request, response);
		}
	}
	


	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	protected String selectEncoding(ServletRequest request) {
		return this.encoding;
	}
	
	
	

	public static boolean sql_inj(String str) {
		  String inj_str = " and @exec @insert @select @delete @update @declare @ count@ chr@ mid@ truncate@ char@ or @'or @')or";
  		
	    String inj_stra[] = inj_str.split("@");
	    if(str!=null){
		    for (int i=0 ; i<inj_stra.length ; i++ ){		    
		        if(inj_stra[i]!=null&&!"".equals(inj_stra[i].trim())){
			    	if (str.toLowerCase().contains(inj_stra[i])){
			        
			            return true;
			        }
		        }
		    }
	    }
	    return false;
	}
	
	
	

}