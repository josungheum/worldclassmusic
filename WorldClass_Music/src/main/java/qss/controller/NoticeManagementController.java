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

import qss.impl.NoticeManagementImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.NoticeVo;
import qss.vo.UserVo;

/**
 * 매장 관리 > 공지사항 관리
 * <pre>
 * qss.controller 
 *    |_ NoticeManagementController.java
 * 
 * </pre>
 * @date : 2018. 12. 25. 오전 12:19:16
 * @version : 
 * @author : admin  
 */
@Controller
public class NoticeManagementController {
	private static final Log logger = LogFactory.getLog(NoticeManagementController.class);
	
	@Resource(name="noticeManagementService")
	Qss noticeManagementService = new NoticeManagementImpl();

	/**
	 * <pre>
	 * 1. 개요 : 공지사항 화면
	 * 2. 처리내용 : 공지사항 화면
	 * </pre>
	 * @Method Name : Notice
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param noticeVo
	 * @param userVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "NoticeManagement/NoticeNullTemp")
	public String Notice(@ModelAttribute("NoticeVo") NoticeVo noticeVo, @ModelAttribute("UserVo") UserVo userVo, ModelMap model, HttpSession session) 
	{
		model.addAttribute("noticeVo",noticeVo);
		return "/NoticeManagement/Notice";
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 브랜드 목록 조회
	 * 2. 처리내용 : 브랜드 목록 조회
	 * </pre>
	 * @Method Name : NoticeList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param noticeVo
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "NoticeManagement/NoticeList")
	@ResponseBody
	public AjaxResultVO NoticeList(NoticeVo noticeVo,HttpSession session) 
	{			
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		noticeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		try {
			noticeVo.setCaseString("Notice_ListNotice");
			List<NoticeVo> list = (List<NoticeVo>) noticeManagementService.SelectListData(noticeVo);
			result.setData(list);
			
			noticeVo.setCaseString("Notice_ListCountNotice");
			result.setiTotalDisplayRecords(noticeManagementService.DataByCnt(noticeVo));
			
			result.setResultCode(0);
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 공지사항 등록/수정 화면
	 * 2. 처리내용 : 공지사항 등록/수정 화면
	 * </pre>
	 * @Method Name : NoticeForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param noticeVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "NoticeManagement/NoticeFormNullTemp")
	public String NoticeForm(@ModelAttribute("NoticeVo") NoticeVo noticeVo, ModelMap model, HttpSession session) 
	{
		noticeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		if(noticeVo.getNoticeIdx() != null && noticeVo.getNoticeIdx().intValue() != 0){
			try {
				noticeVo.setCaseString("Notice_ViewNotice");
				noticeVo = (NoticeVo) noticeManagementService.SelectData(noticeVo);
				noticeVo.setStartDate(noticeVo.getStartDate().substring(0, 10));
				noticeVo.setEndDate(noticeVo.getEndDate().substring(0, 10));
				if(noticeVo.getPopupStartDate() != null && noticeVo.getPopupStartDate().length() <= 10){
					noticeVo.setPopupStartDate(noticeVo.getPopupStartDate().substring(0, 10));
					noticeVo.setPopupEndDate(noticeVo.getPopupEndDate().substring(0, 10));
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
			
		}
		
		model.addAttribute(noticeVo);
		return "/NoticeManagement/NoticeForm";
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 공지사항 글 생성
	 * 2. 처리내용 : 공지사항 글 생성
	 * </pre>
	 * @Method Name : NoticeCreate
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param noticeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "NoticeManagement/NoticeCreate")
	@ResponseBody
	public AjaxResultVO NoticeCreate(NoticeVo noticeVo, HttpSession session) 
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		int cnt = 0;
		
		noticeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		noticeVo.setRegUser((String)session.getAttribute("id"));
		try {
			if(noticeVo.getNoticeIdx() == null || noticeVo.getNoticeIdx().intValue() == 0) 
			{
				noticeVo.setCaseString("Notice_InsertNotice");
				cnt = noticeManagementService.UpdateData(noticeVo);
			}
			else
			{
				noticeVo.setCaseString("Notice_UpdateNotice");
				cnt = noticeManagementService.UpdateData(noticeVo);
			}
			
			if (cnt > 0) {
				result.setResultCode(0);
			}
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
	 * 1. 개요 : 글 삭제
	 * 2. 처리내용 : 글 삭제
	 * </pre>
	 * @Method Name : NoticeDelete
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param noticeVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "NoticeManagement/NoticeDelete")
	@ResponseBody
	public AjaxResultVO NoticeDelete(NoticeVo noticeVo, HttpSession session) 
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		noticeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		noticeVo.setRegUser((String)session.getAttribute("id"));
		try {
			noticeVo.setCaseString("Notice_DeleteNotice");
			int cnt = noticeManagementService.UpdateData(noticeVo);
			
			if (cnt > 0) {
				result.setResultCode(0);
			}
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
	 * 1. 개요 : 글 활성화
	 * 2. 처리내용 : 글 활성화
	 * </pre>
	 * @Method Name : NoticeActive
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @param noticeVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "NoticeManagement/NoticeActive")
	@ResponseBody
	public AjaxResultVO NoticeActive(NoticeVo noticeVo, HttpSession session)  {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		noticeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		noticeVo.setRegUser((String)session.getAttribute("id"));
		try {
			noticeVo.setCaseString("Notice_ActiveNotice");
			int cnt = noticeManagementService.UpdateData(noticeVo);

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
}