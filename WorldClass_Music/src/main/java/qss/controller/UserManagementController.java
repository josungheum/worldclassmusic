package qss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.CommonImpl;
import qss.impl.UserManagementImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.UserVo;

/**
 * 설정 관리 > 사용자 관리
 * <pre>
 * qss.controller 
 *    |_ UserManagementController.java
 * 
 * </pre>
 * @date : 2019. 1. 15. 오후 4:55:30
 * @version : 
 * @author : 김한기
 */
@Controller
public class UserManagementController {
	@Resource(name = "userManagementService")
	Qss UserManagementService = new UserManagementImpl();

	@Resource(name = "commonService")
	Qss commonService = new CommonImpl();

	private static final Log logger = LogFactory.getLog(UserManagementController.class);
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 조회
	 * 2. 처리내용 : 사용자 조회
	 * </pre>
	 * @Method Name : UserList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "UserManagement/UserList")
	@ResponseBody
	public AjaxResultVO UserList(UserVo userVo, HttpSession session){
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		userVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		try {
			userVo.setCaseString("User_ListUser");
			List<UserVo> list = (List<UserVo>) UserManagementService.SelectListData(userVo);
			result.setData(list);
			
			userVo.setCaseString("User_ListCountUser");
			result.setiTotalDisplayRecords(UserManagementService.DataByCnt(userVo));
			
			result.setResultCode(0);
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 활성화
	 * 2. 처리내용 : 사용자 활성화
	 * </pre>
	 * @Method Name : UserActive
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "UserManagement/UserActive")
	@ResponseBody
	public AjaxResultVO UserActive(UserVo userVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		userVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		userVo.setRegUser((String)session.getAttribute("id"));
		try {
			userVo.setCaseString("User_ActiveUser");
			int cnt = UserManagementService.UpdateData(userVo);

			if (cnt > 0) {
				result.setResultCode(0);
			} else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 비밀번호 변경
	 * 2. 처리내용 : 사용자 비밀번호 변경
	 * </pre>
	 * @Method Name : PwdChange
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "UserManagement/PwdChange")
	@ResponseBody
	public AjaxResultVO PwdChange(UserVo userVo, HttpSession session)
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		userVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		userVo.setRegUser((String)session.getAttribute("id"));
		try {
			userVo.setCaseString("User_PwdChangeUser");
			int cnt = UserManagementService.UpdateData(userVo);
			
			if (cnt > 0) 
				result.setResultCode(0);
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 저장
	 * 2. 처리내용 : 사용자 저장
	 * </pre>
	 * @Method Name : UserCreate
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "UserManagement/UserCreate")
	@ResponseBody
	public AjaxResultVO UserCreate(UserVo userVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		userVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		userVo.setRegUser((String)session.getAttribute("id"));
		try {
			userVo.setCaseString("User_OverlapCountUser");
			
			int cnt = UserManagementService.DataByCnt(userVo);
			if(cnt == 0)
			{
				userVo.setCaseString("User_InsertUser");
				cnt = UserManagementService.InsertData(userVo);
				if (cnt > 0) 
					result.setResultCode(0);
				else 
				{
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else 
			{
				result.setResultCode(1);
				messages.put("title", "사용중인 아이디입니다.");
			}
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 삭제
	 * 2. 처리내용 : 사용자 삭제
	 * </pre>
	 * @Method Name : UserDelete
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "UserManagement/UserDelete")
	@ResponseBody
	public AjaxResultVO UserDelete(UserVo userVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			userVo.setCaseString("User_DeleteUser");
			userVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			userVo.setRegUser((String)session.getAttribute("id"));
			int cnt = UserManagementService.DeleteDataByObjectParam(null, userVo);

			if (cnt > 0) 
				result.setResultCode(0);
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 조회
	 * 2. 처리내용 : 사용자 조회
	 * </pre>
	 * @Method Name : User
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "UserManagement/UserNullTemp")
	public String User(@ModelAttribute("UserVo") UserVo userVo, ModelMap model, HttpSession session) throws Exception
	{
		model.addAttribute(userVo);
		
		return "/UserManagement/User";
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 사용자 등록 화면
	 * 2. 처리내용 : 사용자 등록 화면
	 * </pre>
	 * @Method Name : UserFormNullTemp
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "UserManagement/UserFormNullTemp")
	public String UserFormNullTemp(@ModelAttribute("UserVo") UserVo userVo, ModelMap model, HttpSession session) throws Exception
	{
		model.addAttribute(userVo);
		return "/UserManagement/UserForm";
	}

	/**
	 * <pre>
	 * 1. 개요 : 사용자 메인 화면
	 * 2. 처리내용 : 사용자 메인 화면
	 * </pre>
	 * @Method Name : User
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "UserManagement/Main")
	public String User(@ModelAttribute("UserVo") UserVo userVo, HttpSession session, ModelMap model) throws Exception {
		if ((String) session.getAttribute("id") != null) {
			return "/UserManagement/Main";
		} else {
			return "redirect:/Logout";
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : 사용자 상세 화면
	 * 2. 처리내용 : 사용자 상세 화면
	 * </pre>
	 * @Method Name : UserManagementForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param userVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "UserManagement/UserManagementForm")
	public String UserManagementForm(@ModelAttribute("UserVo") UserVo userVo, ModelMap model, HttpSession session) throws Exception {
		if ((String) session.getAttribute("id") != null) {
			return "/UserManagement/UserManagementForm";
		} else {
			return "redirect:/Logout";
		}
	}


}