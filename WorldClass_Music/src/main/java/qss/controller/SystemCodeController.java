
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

import net.sf.json.JSONArray;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.util.XssUtil;
import qss.vo.AjaxResultVO;
import qss.vo.SystemCodeVo;

/**
 * 공통 코드
 * <pre>
 * qss.controller
 *    |_ SystemCodeController.java
 *
 * </pre>
 * @date : 2019. 1. 7. 오후 9:25:30
 * @version :
 * @author : admin
 */
@Controller
public class SystemCodeController {
	private static final Log logger = LogFactory.getLog(SystemCodeController.class);

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();

	@RequestMapping(value="SystemCode/Main")
    public String SystemMain(@ModelAttribute("SystemCodeVo")SystemCodeVo systemCodeVo, HttpSession session, ModelMap model)
	{
		if ((String)session.getAttribute("id") != null) {
			try {
				systemCodeVo.setCaseString("SystemCode_SystemGroupCode");
				systemCodeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
				model.addAttribute("SystemCodeList", XssUtil.jsonText(JSONArray.fromObject(systemCodeService.SelectListData(systemCodeVo))));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			return "/SystemCode/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }

	@ResponseBody
	@RequestMapping(value="SystemCode/GroupCode")
	public String GroupCode() throws Exception
	{
		SystemCodeVo systemCodeVo = new SystemCodeVo();
		systemCodeVo.setCaseString("SystemCode_SystemGroupCode");
//		return XssUtil.jsonText(JSONArray.fromObject(systemCodeService.SelectListData(systemCodeVo)));
		return JSONArray.fromObject(systemCodeService.SelectListData(systemCodeVo)).toString();
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 공통코드 등록/상세
	 * 2. 처리내용 : 공통코드 등록/상세 폼 표시
	 * </pre>
	 * @Method Name : NoticeForm
	 * @date : 2019. 6. 5.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 5.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param systemCodeVo
	 * @param model
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "SystemCode/FormNullTemp")
	public String FormNullTemp(@ModelAttribute("SystemCodeVo") SystemCodeVo systemCodeVo, ModelMap model, HttpSession session)
	{
		try {
			// 등록 시 CodeIdx : 0, 상세 시 CodeIdx 들어옴.
			if(systemCodeVo.getCodeIdx() != null && systemCodeVo.getCodeIdx().intValue() > 0 ){
				// 상세 Attribute
				systemCodeVo.setCaseString("SystemCode_GetSystemCode");
				systemCodeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
				model.addAttribute("SystemCodeVo",(SystemCodeVo) systemCodeService.SelectData(systemCodeVo));
			} else {
				// 등록 Attribute
				systemCodeVo.setCodeIdx(null);
				model.addAttribute("SystemCodeVo",systemCodeVo);
			}
			systemCodeVo.setCaseString("SystemCode_SystemGroupCode");
			systemCodeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			List<SystemCodeVo> list = (List<SystemCodeVo>) systemCodeService.SelectListData(systemCodeVo);
			model.addAttribute("groupList", list);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/SystemCode/Form";
	}

	/**
	 * 공통 코드 목록 조회
	 * <pre>
	 * 1. 개요 :
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : SystemCodeList
	 * @date : 2019. 1. 7.
	 * @author : admin
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 7.		admin				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param systemCodeVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "SystemCode/SystemCodeList")
	@ResponseBody
	public AjaxResultVO SystemCodeList(SystemCodeVo systemCodeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			systemCodeVo.setCaseString("SystemCode_SystemCodeList");
			systemCodeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			List<SystemCodeVo> list = (List<SystemCodeVo>) systemCodeService.SelectListData(systemCodeVo);
			result.setData(list);

			systemCodeVo.setCaseString("SystemCode_SystemCodeCount");
			result.setiTotalDisplayRecords(systemCodeService.DataByCnt(systemCodeVo));
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 *
	 * <pre>
	 * 1. 개요 : 코드 / 코드 분류 등록
	 * 2. 처리내용 : 코드 / 코드 분류 등록
	 * </pre>
	 * @Method Name : SystemCodeCreate
	 * @date : 2019. 6. 5.
	 * @author : 이재환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 5.		이재환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param systemCodeVo
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "SystemCode/SystemCodeCreate")
	@ResponseBody
	public AjaxResultVO SystemCodeCreate(SystemCodeVo systemCodeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		int cnt = 0;
		try {
			//중복 코드 확인
			systemCodeVo.setCaseString("SystemCode_SystemCodeOverlap");
			if(systemCodeService.DataByCnt(systemCodeVo) == 0){
				systemCodeVo.setDomainIdx((String)session.getAttribute("domainIdx"));
				systemCodeVo.setRegUser((String)session.getAttribute("id"));
				if(systemCodeVo.getCodeIdx() != null && systemCodeVo.getCodeIdx().intValue() > 0 ) {
					systemCodeVo.setCaseString("SystemCode_SystemCodeUpdate");
				} else {
					systemCodeVo.setCaseString("SystemCode_SystemCodeCreate");
				}

				cnt = systemCodeService.UpdateData(systemCodeVo);
				if (cnt > 0) {
					result.setResultCode(0);
				} else {
					result.setResultCode(1);
					messages.put("title", "처리 되지 않았습니다.");
				}
			}else {
				result.setResultCode(1);
				messages.put("title", "중복되는 코드입니다.");
			}

			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	@RequestMapping(value = "SystemCode/SystemCodeActive")
	@ResponseBody
	public AjaxResultVO SystemCodeActive(SystemCodeVo systemCodeVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			systemCodeVo.setCaseString("SystemCode_SystemCodeActive");
			systemCodeVo.setRegUser((String)session.getAttribute("id"));
			int cnt = systemCodeService.UpdateData(systemCodeVo);

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

	@RequestMapping(value = "SystemCode/SystemCodeDelete")
	@ResponseBody
	public AjaxResultVO SystemCodeDelete(SystemCodeVo systemCodeVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			systemCodeVo.setCaseString("SystemCode_SystemCodeDelete");
			systemCodeVo.setRegUser((String)session.getAttribute("id"));
			int cnt = systemCodeService.UpdateData(systemCodeVo);

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
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
		}
		return result;
	}
}