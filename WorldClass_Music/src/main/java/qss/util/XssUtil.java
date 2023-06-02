/*
 * Copyright yysvip.tistory.com.,LTD.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of yysvip.tistory.com.,LTD. ("Confidential Information").
 */
package qss.util;

import org.apache.commons.lang.StringEscapeUtils;

import net.sf.json.JSONArray;

/**
 * <pre>
 * scoservice.util
 *    |_ XssUtil.java
 *
 * </pre>
 * @date : 2019. 5. 31. 오전 10:11:08
 * @version :
 * @author : 이재환
 */
public class XssUtil {

    public static String cleanXSS(String value) {
    	if(value == null || "".equals(value)) {
    		return "";
    	}

  	    value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("\"", "&#34;");
        /*
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("xonmousewheel", "");
        value = value.replaceAll("onactive", "");
        value = value.replaceAll("onfocusout", "");
        value = value.replaceAll("expression", "");
        value = value.replaceAll("link", "");
        value = value.replaceAll("frame", "");
        value = value.replaceAll("iframe", "");
        value = value.replaceAll("cookie", "");
        value = value.replaceAll("style", "");
        value = value.replaceAll("alert", "");
        value = value.replaceAll("void", "");
		*/
        return value;
    }

    public static String displayTag(String value) {
    	if(value == null || "".equals(value)) {
    		return "";
    	}

    	value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        value = value.replaceAll("&#40;", "\\(" ).replaceAll("&#41;", "\\)");
        value = value.replaceAll("&#39;", "'" );
        value = value.replaceAll("&#34;", "\"");
        return value;
    }

    public static String jsonText(String value) {
    	if(value == null || "".equals(value)) {
    		return "";
    	}

    	value = value.replaceAll("\\\"", "\\\\\"");
    	value = value.replaceAll("\\\'", "\\\\\'");

    	return value;
    }

    public static String jsonText(JSONArray json) {
    	String value = null;
    	if(json != null) {
    		value = StringEscapeUtils.escapeJavaScript(json.toString());
    	}
    	return value;
    }

}
