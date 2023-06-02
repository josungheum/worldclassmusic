/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package qss.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import qss.util.XssUtil;

/**
 * <pre>
 * scoservice.filter
 *    |_ RequestWrapper.java
 *
 * </pre>
 * @date : 2019. 5. 29. 오전 11:10:08
 * @version :
 * @author : 이재환
 */
public final class RequestWrapper extends HttpServletRequestWrapper {

	public RequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

    public String[] getParameterValues(String parameter) {

        String[] values = super.getParameterValues(parameter);
        if (values==null)  {
                    return null;
            }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
                   encodedValues[i] = XssUtil.cleanXSS(values[i]);
         }
        return encodedValues;
      }

      public String getParameter(String parameter) {
            String value = super.getParameter(parameter);
            if (value == null) {
                   return null;
                    }
            return XssUtil.cleanXSS(value);
      }

      public String getHeader(String name) {
          String value = super.getHeader(name);
          if (value == null)
              return null;
          return XssUtil.cleanXSS(value);

      }



}
