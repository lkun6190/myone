package com.thinkgem.jeesite.modules.sys.security;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 功能模块【跨站点请求伪造 referer 过滤器】 功能说明 User: xianqinhong Date: 2017-02-04 Time: 11:08
 */
public class CSRFFilter extends HttpServlet implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 从 HTTP 头中取得 Referer 值
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String referer = req.getHeader("Referer");
		// 判断 Referer 是否以 当前主机 开头
		String host = req.getServerName();
		if ((referer == null) || (referer != null) && (referer.trim().indexOf(host) > 0)) {
			if (req.getRequestURI().indexOf(".arc") > 0 || req.getRequestURI().indexOf(".old") > 0
					|| req.getRequestURI().indexOf(".sav") > 0 || req.getRequestURI().indexOf(".$$$") > 0) {
				response.getWriter().print("<html><head><meta charset=\"utf-8\"> <title>错误引用页面</title></head><body>");
				response.getWriter().print("引用归档文件！</body></html>");
				resp.setStatus(500);
			} else {
				resp.addHeader("X-Frame-OPTIONS", "SAMEORIGIN");
				Map params = request.getParameterMap();
				Iterator it = params.keySet().iterator();
				boolean isInject = false;
				while (it.hasNext()) {
					String key = (String) it.next();
					String value = req.getParameter(key);
					if (!"act.taskName".equals(key) && !"password".equals(key) && !"newPassword".equals(key)
							&& !value.startsWith("jericho")) {
						if (com.thinkgem.jeesite.modules.sys.utils.StringUtils.checkInjectWord(value)) {
							isInject = true;
							response.getWriter()
									.print("<html><head><meta charset=\"utf-8\"> <title>错误引用页面</title></head><body>");
							response.getWriter()
									.print("<a onclick='history.go(-1);' href='#'>参数中有非法字符！请重新输入</a></body></html>");
							resp.setStatus(500);
							break;
						}
					}
				}
				if (!isInject) {
					chain.doFilter(request, response);
				}
			}
		} else {

			if (StringUtils.isNotBlank(referer)) {
				if (referer.contains("47.52.151.161") || referer.contains("118.190.155.177")
						|| referer.contains("admin.abacus-pro")) {
					chain.doFilter(request, response);
				} else {
					response.getWriter()
							.print("<html><head><meta charset=\"utf-8\"> <title>错误引用页面</title></head><body>");
					response.getWriter().print("打开页面的来源网站与当前页面不一致，请检查链接地址！</body></html>");
					response.flushBuffer();
				}
			} else {
				response.getWriter().print("<html><head><meta charset=\"utf-8\"> <title>错误引用页面</title></head><body>");
				response.getWriter().print("打开页面的来源网站与当前页面不一致，请检查链接地址！</body></html>");
				response.flushBuffer();
			}

		}
	}
}
