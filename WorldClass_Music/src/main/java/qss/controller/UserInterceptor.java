package qss.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import qss.impl.CommonImpl;
import qss.impl.IndexImpl;
import qss.service.Qss;
import qss.vo.CommonVo;
import qss.vo.IndexVo;
import qss.vo.MenuInfoVo;

/**
 * 인터셉트 관리
 * <pre>
 * qss.controller
 *    |_ UserInterceptor.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:23:17
 * @version :
 * @author : admin
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
	private static final Log logger = LogFactory.getLog(UserInterceptor.class);

	@Value("#{configProp['service.name']}")
	private String serviceName;

	@Value("#{configProp['accesslog.path']}")
	private String accessPath;

	@Value("#{configProp['systemlog.path']}")
	private String systemPath;

	@Resource(name = "commonService")
	Qss commonService = new CommonImpl();

	@Resource(name = "indexService")
	Qss indexService = new IndexImpl();

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("preHandle***********************************************" + request.getRequestURI());
		//NoLoginCheck usingAuth = ((HandlerMethod) handler).getMethodAnnotation(NoLoginCheck.class);
		try {


			Date d = new Date();
			HttpSession session = request.getSession();
			SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String logStr = "DATE: " + yyyymmddhhmmss.format(d) + "|" + "REFERER: " + request.getHeader("REFERER") + "|" + "IP: " + request.getRemoteAddr() + "|"
					+ "PT: " + request.getProtocol() + "|" + "URL: " + request.getRequestURL();

			// 유저 활성화 / 삭제 체크
			if(checkUserInfo(request, response, handler, session) == false) {
				session.removeAttribute("id");
			}

			if (request.getRequestURI().split("/")[1].equals("Logout") || request.getRequestURI().split("/")[1].equals("ws")) {
				return true;
			} else {
				if (request.getHeader("REFERER") != null) {
					if (request.getRequestURI().split("/")[1].equals("Logout") && session.getAttribute("id") != null) {
						FileWrite((String) session.getAttribute("id"), logStr, "access");

						return true;
					} else {
						System.out.println(request.getHeader("referer") + "*****************" + request.getServerName());
						if (request.getHeader("referer").contains(request.getServerName()) && session.getAttribute("id") != null && session.getAttribute(session.getAttribute("id").toString()) != null) {

							CommonVo commonVo = new CommonVo();

							Gson gson = new Gson();
							String reqData = gson.toJson(request.getParameterMap());

							String[] uri = request.getRequestURI().split("/");

							// 사용자 action log 생성
							if (uri.length < 1) {
								commonVo.setCtrlName("Abnormal approach path");
							} else if (uri.length == 2) {
								commonVo.setCtrlName(request.getRequestURI().split("/")[1]);
							} else if (uri.length == 3){
								commonVo.setCtrlName(request.getRequestURI().split("/")[1]);
								commonVo.setActionName(request.getRequestURI().split("/")[2]);
							} else {
								reqData = "";
								for (int i = 3; i < uri.length; i++) {
									reqData = reqData + "/" + uri[i];
								}
							}

							// 사용자 액션이 있을때만(등록/수정/삭제,활성화)
							if(uri.length >= 3) {

								if(commonVo.getActionName() != null && (commonVo.getActionName().toString().toUpperCase().contains("CREATE")
								|| commonVo.getActionName().toString().toUpperCase().contains("UPDATE")
								|| commonVo.getActionName().toString().toUpperCase().contains("DELETE")
								|| commonVo.getActionName().toString().toUpperCase().contains("ACTIVE"))) {
									commonVo.setId(session.getAttribute("id").toString());
									commonVo.setIp(getClientIpAddr(request));
									commonVo.setDetailInfo(reqData);

									commonService.InsertData(commonVo);
								}
							}

							String strUri = request.getRequestURI();
							ArrayList<MenuInfoVo> list = (ArrayList<MenuInfoVo>) session.getAttribute("menuList");
							for(int i=0;i<list.size(); i++) {
							    if(list.get(i).getMenuLinkAddr() != null && !list.get(i).getMenuLinkAddr().isEmpty() && strUri.contains(list.get(i).getMenuLinkAddr())) {
							    	session.setAttribute("MENU_KOR_NAME", list.get(i).getMenuKorName());
							    	session.setAttribute("MENU_ENG_NAME", list.get(i).getMenuEngName());
							    	session.setAttribute("SUB_MENU_NAME", list.get(i).getMenuSubName());
							    	session.setAttribute("PARENT_MENU_ID", list.get(i).getParentMenuId());
							    	session.setAttribute("PARENT_MENU_NM", list.get(i).getParentMenuNm());
							    	session.setAttribute("MENU_ID", list.get(i).getMenuId());

							    	break;
								}
							}

							return true;
						} else {
							response.sendRedirect("/Logout");

							return false;
						}
					}
				} else {
					// 세션이 존재하지 않으면 로그아웃
					if (session.getAttribute("id") == null) {
						response.sendRedirect("/Logout");
						return false;
					}else {
						session.removeAttribute(String.valueOf(session.getAttribute("id")));
						session.removeAttribute(String.valueOf(session.getAttribute("name")));
//						session.removeAttribute(String.valueOf(session.getAttribute("IDX")));
						session.removeAttribute(String.valueOf(session.getAttribute("domainIdx")));
						session.removeAttribute(String.valueOf(session.getAttribute("brandIdx")));
						session.removeAttribute(String.valueOf(session.getAttribute("francIdx")));
						session.removeAttribute(String.valueOf(session.getAttribute("MENU_KOR_NAME")));
						session.removeAttribute(String.valueOf(session.getAttribute("MENU_ENG_NAME")));
						session.removeAttribute(String.valueOf(session.getAttribute("SUB_MENU_NAME")));
						session.removeAttribute(String.valueOf(session.getAttribute("PARENT_MENU_ID")));
						session.removeAttribute(String.valueOf(session.getAttribute("MENU_ID")));
						session.removeAttribute(String.valueOf(session.getAttribute("menuList")));
						response.sendRedirect("/Logout");
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		/*System.out.println("postHandle***********************************************" + request.getRequestURI());*/

		String url = request.getServletPath();
		String[] url_arr =  url.split("/");
		String depth = url_arr[url_arr.length -1];

		// 모바일이면 모바일 메인으로 이동
		if("Main".equals(depth) && !url.contains("/Mobile")) {
			String ua 			= request.getHeader("User-Agent").toUpperCase();
			boolean bMobile 	= StringUtils.contains(ua, "MOBILE");
			boolean bPhone		= StringUtils.contains(ua, "PHONE");
			if(bMobile || bPhone) {
				// 리소스, 상품관리, 주문화면관리, 매출통계 일때만 모바일 페이지 메인 이동여부 체크 추가
				if(url.contains("/Resource/")
				|| url.contains("/OrderProd/")
				|| url.contains("/OrderScreen/")
				|| url.contains("/Report/")){
					response.sendRedirect(serviceName + "/Mobile" + url);
				}
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		System.out.println("afterCompletion***********************************************" + request.getRequestURI());

		Date d = new Date();
		HttpSession session = request.getSession();
		SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String logStr = "DATE: " + yyyymmddhhmmss.format(d) + "|" + "REFERER: " + request.getHeader("REFERER") + "|" + "IP: " + request.getRemoteAddr() + "|"
				+ "PT: " + request.getProtocol() + "|" + "URL: " + request.getRequestURL();
		System.out.println(logStr);

		// 유저 활성화 / 삭제 체크
		if(checkUserInfo(request, response, handler, session) == false) {
			session.removeAttribute("id");
		}

		if (request.getRequestURI().split("/")[1].equals("Logout") || request.getRequestURI().split("/")[1].equals("ws")) {
		} else {
			if (request.getHeader("REFERER") == null) {
				FileWrite(null, logStr, "system");
				response.sendRedirect("/Logout");
			} else {
				if (session.getAttribute("id") == null) {
					if (!request.getRequestURI().split("/")[1].equals("Logout")) {
						response.sendRedirect("/Logout");
					}
				} else {
					FileWrite((String) session.getAttribute("id"), logStr, "access");
				}
			}
		}
	}

	private void FileWrite(String id, String logStr, String standard) {
		String folder = null;

		if (standard.equals("access")) {
			folder = accessPath + "/" + id;
		} else {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM");

			folder = systemPath + "/" + sdf.format(date);
		}

		File saveFolder = new File(folder);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (!saveFolder.exists()) {
				saveFolder.mkdirs();
			}

			String fileName = saveFolder + "/" + sdf.format(date) + ".txt";
			File file = new File(fileName);
			FileWriter fw = new FileWriter(file, true);

			fw.write(logStr + "\r\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


	public boolean checkUserInfo(HttpServletRequest request, HttpServletResponse response, Object handler,  HttpSession session) throws Exception {
		IndexVo indexVo = new IndexVo();
		IndexVo sessionVo = new IndexVo();
		indexVo.setCaseString("Login_GetMember");

		if(session.getAttribute("id") == null) {
			return false;
		}

		indexVo.setId(session.getAttribute("id").toString());

		sessionVo = (IndexVo) indexService.SelectData(indexVo);
		if(sessionVo == null) {
			return false;
		}

		// 비활성화 사용자 제한 추가 20190314
		if(!"Y".equals(sessionVo.getActiveYn())) {
			return false;
		}

		// 브랜드 및 가맹점 비활성화 여부 추가 20190314
		if("ADM0002".equals(sessionVo.getAdminType())) {
			// 브랜드 관리자
			sessionVo.setCaseString("Login_GetBrnActiveCheck");
			IndexVo checkVo = new IndexVo();
			checkVo = (IndexVo) indexService.SelectData(sessionVo);

			if(!"Y".equals(checkVo.getActiveYn())) {
				return false;
			}

		}else if("ADM0003".equals(sessionVo.getAdminType())) {
			// 매장 관리자
			// 브랜드 활성화 여부 확인
			sessionVo.setCaseString("Login_GetBrnActiveCheck");
			IndexVo checkVo = new IndexVo();
			checkVo = (IndexVo) indexService.SelectData(sessionVo);

			if(!"Y".equals(checkVo.getActiveYn())) {
				return false;
			}

			// 매장활성화 여부 확인
			sessionVo.setCaseString("Login_GetFrnActiveCheck");
			checkVo = new IndexVo();
			checkVo = (IndexVo) indexService.SelectData(sessionVo);

			if(!"Y".equals(checkVo.getActiveYn())) {
				return false;
			}
		}

		return true;
	}
}
