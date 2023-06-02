package qss.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import qss.vo.AjaxResultVO;


public class WebUtil {	
	public WebUtil() {}
	
	public static String CheckDelete(int[] arr)
	{
		String deleteIndex = "";
		
		for (int i = 0; i < arr.length; i++) {
			if (i == (arr.length-1)) {
				deleteIndex += String.valueOf(arr[i]);
			}
			else {
				deleteIndex += String.valueOf(arr[i]) + ",";
			}
		}
		return deleteIndex;
	}

	public static String CheckStringArr(String[] arr) 
	{
		// TODO Auto-generated method stub
		String deleteIndex = "";
		
		for (int i = 0; i < arr.length; i++) {
			if (i == (arr.length-1)) {
				deleteIndex += arr[i].trim();
			}
			else {
				deleteIndex += arr[i].trim() + ",";
			}
		}
		return deleteIndex;
	}
	
	public static String EncodingStr(String str) throws UnsupportedEncodingException {

	      /*String[] charset = { "UTF-8", "ksc5601", "iso-8859-1", "8859_1", "ascii", "utf-8", "US-ASCII", "MS949",
	            "Cp970", "EUC-KR", "EUC_KR", "KOI8", "utf8" };

	      for (int i = 0; i < charset.length; i++) {
	         for (int j = 0; j < charset.length; j++) {
	            if (i == j)
	               continue;
	            System.out.print(charset[i] + "->" + charset[j] + ":");
	            System.out.println(new String(xml.getBytes(charset[i]), charset[j]));
	         }
	      }*/
		str = new String(str.getBytes("UTF-8"), "iso-8859-1");
	      
		return str;
	}
	
	public static ArrayList<String> ParsingXML(String xml, String parentTag, String childTag){
		String spTag = "<"+parentTag+">";
		String epTag = "</"+parentTag+">";
		String scTag = "<"+childTag+">";
		String ecTag = "</"+childTag+">";
		String tempXml = "";
	    String str = "";
	    String[] split = xml.split(epTag);
	    ArrayList<String> list = new ArrayList<String>();
	    
	    if (split.length > 0) {
	    	for (int i = 0; i < split.length; i++) {
	    		split[i] = split[i] + epTag;
	    		int start = split[i].indexOf(spTag);
			    if (start != -1) {
			       int end = split[i].indexOf(epTag, start + spTag.length());
			       if (end != -1) {
			    	   tempXml = split[i].substring(start + spTag.length(), end);
			       }
			    }
			    
			    start = tempXml.indexOf(scTag);
			    if (start != -1) {
			       int end = tempXml.indexOf(ecTag, start + scTag.length());
			       if (end != -1) {
			    	   str = String.valueOf(tempXml.substring(start + scTag.length(), end));
			       }
			    }
			    list.add(str);
			}
		}
		return list;
	}
	
	public static AjaxResultVO ExceptionMessages(AjaxResultVO result, int resultCode, Exception e, Log logger) {
		Map<String, Object> messages = new HashMap<String, Object>();
		messages.put("title", "서버오류가 발생하였습니다.");
		messages.put("detail", e.getMessage().replaceAll(";", "\n"));
		result.setMessages(messages);
		logger.error(e.getMessage());
		result.setResultCode(resultCode);
		return result;
	}
}