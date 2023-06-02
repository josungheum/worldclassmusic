package qss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonIgnore;

import qss.impl.CommonImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.CommonVo;
import qss.vo.MenuVo;

/**
 * 공통 관리
 * <pre>
 * qss.controller
 *    |_ CommonController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:19:39
 * @version :
 * @author : admin
 */
@Controller
public class CommonController {
	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	@RequestMapping("Common/SideMenu")
	@ResponseBody
	public List<String> SideMenuAuth(MenuVo menuVo, HttpSession session) throws Exception
	{
		menuVo.setCaseString("Common_SideMenu");
		menuVo.setAS_AUINDEX((Integer)session.getAttribute("A_AUID"));
		List<String> list = commonService.StringList(menuVo);

		return list;
	}

	@RequestMapping("Common/UrlCheck")
	public ModelAndView UrlCheck(MenuVo menuVo, HttpServletResponse response, HttpSession session) throws Exception
	{
		ModelAndView mav = new ModelAndView();

		try {
			if ((String)session.getAttribute("ID") != null) {
				int aAuid = (Integer)session.getAttribute("A_AUID");

				if (aAuid == 0) {
					mav.setViewName("/Logout");

					return mav;
				}

				menuVo.setAS_AUINDEX(aAuid);
				int authResult = commonService.DataByCnt(menuVo);

				if (authResult == 0) {
					return new ModelAndView("redirect:/Logout");
				}
				else {
					mav.addObject("sendUrl", menuVo.getSendUrl());
			    	mav.setViewName("/Common/Redirect");

					return mav;
				}
			}
			else {
				return new ModelAndView("redirect:/Logout");
			}
		}
		catch (Exception ex) {
	    	mav.setViewName("/Logout");

			return mav;
		}
	}

	@RequestMapping("Common/SessionCheck")
	@JsonIgnore
	@ResponseBody
	public AjaxResultVO SessionCheck(HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			if ((String)session.getAttribute("id") == null) {
				result.setResultCode(1);
				messages.put("title", "세션이 만료되었습니다.");
				result.setMessages(messages);
			}
			else {
				result.setResultCode(0);
			}
		}
		catch (Exception e) {
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	@RequestMapping("/Logout")
	public String Logout(HttpServletRequest request, HttpSession session) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			SessionAttrRemove(session);
		}

		return "/Common/Logout";
	}

	public void SessionAttrRemove(HttpSession session)
	{
		session.removeAttribute(String.valueOf(session.getAttribute("id")));
		session.removeAttribute(String.valueOf(session.getAttribute("name")));
		session.removeAttribute(String.valueOf(session.getAttribute("domainIdx")));
		session.removeAttribute(String.valueOf(session.getAttribute("brandIdx")));
		session.removeAttribute(String.valueOf(session.getAttribute("francIdx")));
		session.removeAttribute(String.valueOf(session.getAttribute("MENU_KOR_NAME")));
		session.removeAttribute(String.valueOf(session.getAttribute("MENU_ENG_NAME")));
		session.removeAttribute(String.valueOf(session.getAttribute("SUB_MENU_NAME")));
		session.removeAttribute(String.valueOf(session.getAttribute("PARENT_MENU_ID")));
		session.removeAttribute(String.valueOf(session.getAttribute("MENU_ID")));
		session.removeAttribute(String.valueOf(session.getAttribute("menuList")));

		session.invalidate();
	}

	@RequestMapping("Common/Redirect")
	public String Redirect(HttpServletRequest request, HttpSession session) throws Exception
	{
		return "/Common/Redirect";
	}

	@RequestMapping("Common/Auth")
	public String Auth(HttpServletRequest request, HttpSession session) throws Exception
	{
		return "/Common/Auth";
	}

	@RequestMapping(value = "Common/PreviewForm/{type}")
	public String PreviewForm(@ModelAttribute("commonVo") CommonVo commonVo, ModelMap model, String filepath) throws Exception
	{
		System.out.println("패스ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ"+filepath);
		model.addAttribute(commonVo);
		return "/Common/PreviewForm";
	}


	@RequestMapping(value = "Common/CategoryName",  produces = "application/text; charset=utf8")
	@ResponseBody
	public String CategoryName(CommonVo commonVo) throws Exception
	{
		String str = "";

		commonVo.setCaseString("CategoryName");
		str = commonService.SelectByReturnString(commonVo);

		return str;
	}

	@RequestMapping(value = "Common/UpdateTime")
	@ResponseBody
	public String Update(CommonVo commonVo) throws Exception
	{
		String chkStr = "";

		try {
			commonVo.setCaseString("Common_UpdateTime");
			int cnt = commonService.UpdateData(commonVo);

			if (cnt > 0) {
				chkStr = String.valueOf(cnt);
			}
			else {
				chkStr = "false";
			}
		}
		catch (Exception e) {
			chkStr = "false";
		}

		return chkStr;
	}

	@RequestMapping("Common/SearchTree")
	@ResponseBody
	public AjaxResultVO SearchTree(CommonVo commonVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			commonVo.setCaseString("Common_SearchTree");
			commonVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			commonVo.setBrandIdx((String)session.getAttribute("brandIdx"));
			commonVo.setFrancIdx((String)session.getAttribute("francIdx"));
			//관리자 타입 설정
			commonVo.setSessionAdminType((String)session.getAttribute("adminType"));

			List<?> list = commonService.SelectListData(commonVo);
			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("data", list);

			if (session.getAttribute("brandIdx").equals("")) {
				resultMap.put("target", "DOMAIN-" + (String)session.getAttribute("domainIdx"));
			}else if(session.getAttribute("francIdx").equals("")){
				resultMap.put("target", "BRAND-" + (String)session.getAttribute("brandIdx"));
			}else{
				resultMap.put("target", "FRANCHISEE-" + (String)session.getAttribute("francIdx"));
			}

			result.setData(resultMap);
			result.setResultCode(0);
		} catch (Exception e) {
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
		}
		result.setMessages(messages);

		return result;
	}

	@RequestMapping("Common/Error")
	public String error(HttpServletRequest request, HttpSession session) throws Exception
	{
		return "/Common/Error";
	}


}