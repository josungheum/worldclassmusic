/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package qss.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * scoservice.filter
 *    |_ CrossScriptingFilter.java
 *
 * </pre>
 * @date : 2019. 5. 29. 오전 11:16:13
 * @version :
 * @author : 이재환
 */
public class CrossScriptingFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		String uri = req.getRequestURI();
//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> uri : " + uri   <sfdfdf>);
		/* HTML 태그 또는 XML이 그대로 저장되어야 하는 URL이 있으면 포함시킨다. */
		if(StringUtils.contains(uri, "/Screen/Update") ||
		   StringUtils.contains(uri, "/Screen/Create") ||
		   StringUtils.contains(uri, "/NoticeManagement/NoticeCreate")

				) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(new RequestWrapper((HttpServletRequest)request), response);
		}
	}
}
