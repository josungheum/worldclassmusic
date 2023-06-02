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

import qss.impl.ScreenImpl;
import qss.impl.TemplateImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.ScreenVo;
import qss.vo.TemplateVo;

/**
 * 노출 관리 > 스크린 관리
 * <pre>
 * qss.controller
 *    |_ ScreenController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:22:35
 * @version :
 * @author : admin
 */
@Controller
public class ScreenController {
	private static final Log logger = LogFactory.getLog(ScreenController.class);

	@Resource(name="screenService")
	Qss screenService = new ScreenImpl();
	
	@Resource(name = "templateService")
    Qss templateService = new TemplateImpl();

	@RequestMapping(value="Screen/Main")
    public String Screen(@ModelAttribute("ScreenVo")ScreenVo screenVo, HttpSession session, ModelMap model) throws Exception
	{
		if ((String) session.getAttribute("id") != null) {
			return "/Screen/Main";
		} else {
			return "/Logout";
		}
    }

	@RequestMapping(value = "Screen/Form")
	public String Screen(@ModelAttribute("ScreenVo") ScreenVo screenVo, ModelMap model, HttpSession session) throws Exception
	{
		model.addAttribute(screenVo);

		if ((String) session.getAttribute("id") != null) {
			return "/Screen/Form";
		} else {
			return "/Logout";
		}
	}


	/**
	 * <pre>
	 * 1. 개요 : 스크린조회
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : List
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("Screen/List")
	@ResponseBody
	public AjaxResultVO List(ScreenVo screenVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		screenVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		// 관리자 정보 set
		screenVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		screenVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		screenVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		screenVo.setSessionAdminType((String)session.getAttribute("adminType"));

		try {
			screenVo.setCaseString("Screen_ScreenList");
			List<ScreenVo> list = (List<ScreenVo>) screenService.SelectListData(screenVo);
			result.setData(list);


			screenVo.setCaseString("Screen_ScreenListCnt");
			result.setiTotalDisplayRecords(screenService.DataByCnt(screenVo));

			result.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 스크린상세
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : ScreenFormDetail
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Screen/Get")
	@ResponseBody
	public AjaxResultVO ScreenFormDetail(ScreenVo screenVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		screenVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		try {
			screenVo.setCaseString("Screen_ScreenGet");
			List<?> list = screenService.SelectListData(screenVo);

			result.setData(list);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 스크린 등록
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : CreateScreen
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Screen/Create")
	@ResponseBody
	public AjaxResultVO CreateScreen(ScreenVo screenVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			screenVo.setCaseString("Screen_Create");
			screenVo.setRegUser((String)session.getAttribute("id"));
			screenVo.setModUser((String)session.getAttribute("id"));
			int cnt = screenService.InsertData(screenVo);

			if (cnt > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리되지 않았습니다.");
				messages.put("detail", "");
				result.setMessages(messages);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 스크린 수정
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : UpdateScreen
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Screen/Update")
	@ResponseBody
	public AjaxResultVO UpdateScreen(ScreenVo screenVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			screenVo.setCaseString("Screen_Update");
			screenVo.setRegUser((String)session.getAttribute("id"));
			screenVo.setModUser((String)session.getAttribute("id"));
			int cnt = screenService.UpdateData(screenVo);

			if (cnt > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리되지 않았습니다.");
				result.setMessages(messages);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 스크린 삭제
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : DeleteScreen
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param screenVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("Screen/Delete")
	@ResponseBody
	public AjaxResultVO DeleteScreen(ScreenVo screenVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			screenVo.setCaseString("Screen_Delete");
			screenVo.setRegUser((String)session.getAttribute("id"));
			screenVo.setModUser((String)session.getAttribute("id"));
			int cnt = screenService.DeleteDataByObjectParam(null, screenVo);

			if (cnt > 0) {
				if (cnt == 1) {
					result.setResultCode(0);
				}
				else {
					result.setResultCode(1);
					messages.put("title", "현재 스크린을 적용한 스케줄이 존재합니다.");
					messages.put("detail", "Schedule에 적용된 Screen이 존재합니다.");
					result.setMessages(messages);
				}
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리되지 않았습니다.");
				result.setMessages(messages);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}
	
	@RequestMapping(value = "Screen/ContentForm")
	public String ScreenForm(@ModelAttribute("ScreenVo") ScreenVo screenVo, ModelMap model) {
		return "/Screen/ContentForm";
	}
	
    // 템플릿 불러오기 - Form
    @RequestMapping(value = "Screen/TemplateAddForm")
    public String templateAddForm(@ModelAttribute("ScreenVo") ScreenVo screenVo, ModelMap model, HttpSession session) {
        if ((String) session.getAttribute("id") != null) {
            try {
            } catch (Exception e) {
                logger.error("##### Screen TemplateAddForm Exception : " + e.getMessage());
            }
            return "/Screen/TemplateAddForm";
        } else {
            return "redirect:/Logout";
        }
    }
    
    // 템플릿 리스트
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "Screen/TemplateList")
    @ResponseBody
    public AjaxResultVO getTemplateList(@ModelAttribute("TemplateVo") TemplateVo templateVo, ModelMap model, HttpSession session) {
        AjaxResultVO result = new AjaxResultVO();
        Map<String, Object> messages = new HashMap<String, Object>();

        templateVo.setCaseString("Template_List");
        templateVo.setDomainIdx((String) session.getAttribute("domainIdx"));
        
        // 관리자 정보 set
        templateVo.setSessionDomainIdx((String) session.getAttribute("domainIdx"));
        templateVo.setSessionBrandIdx((String) session.getAttribute("brandIdx"));
        templateVo.setSessionFrancIdx((String) session.getAttribute("francIdx"));
        templateVo.setSessionAdminType((String) session.getAttribute("adminType"));

        try {
            templateVo.setCaseString("Template_TemplateList");
            List<TemplateVo> list = (List<TemplateVo>) templateService.SelectListData(templateVo);
            result.setData(list);

            templateVo.setCaseString("Template_TemplateListCnt");
            result.setiTotalDisplayRecords(templateService.DataByCnt(templateVo));

            result.setResultCode(0);
            result.setMessages(messages);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setResultCode(2);
            messages.put("title", "서버오류가 발생하였습니다.");
            messages.put("detail", e.getMessage().replaceAll(";", "\n"));
            result.setMessages(messages);
        }

        return result;
    }

}