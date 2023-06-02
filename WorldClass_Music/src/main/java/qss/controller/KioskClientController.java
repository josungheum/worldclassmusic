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
import qss.impl.KioskClientImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.KioskClientVo;

/**
 * 설정 관리 > 단말 버전 관리
 * <pre>
 * qss.controller
 *    |_ KioskClientController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:20:16
 * @version :
 * @author : 김한기
 */
@Controller
public class KioskClientController {
	@Resource(name = "kioskClientService")
	Qss kioskClientService = new KioskClientImpl();

	@Resource(name = "commonService")
	Qss commonService = new CommonImpl();

	private static final Log logger = LogFactory.getLog(KioskClientController.class);

	/**
	 * <pre>
	 * 1. 개요 : 단말 화면
	 * 2. 처리내용 : 단말 화면
	 * </pre>
	 * @Method Name : KioskClient
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskClientVo
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "KioskClient/Main")
	public String KioskClient(@ModelAttribute("KioskClientVo") KioskClientVo kioskClientVo, HttpSession session, ModelMap model) {
		if ((String) session.getAttribute("id") != null) {
			return "/KioskClient/Main";
		} else {
			return "redirect:/Logout";
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : 단말 상세 화면
	 * 2. 처리내용 : 단말 상세 화면
	 * </pre>
	 * @Method Name : KioskClientForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskClientVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "KioskClient/KioskClientForm")
	public String KioskClientForm(@ModelAttribute("KioskClientVo") KioskClientVo kioskClientVo, ModelMap model, HttpSession session) {
		if ((String) session.getAttribute("id") != null) {
			//상세 조회
			kioskClientVo.setCaseString("KioskClient_ViewKioskClient");
			kioskClientVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			try {
				kioskClientVo = (KioskClientVo) kioskClientService.SelectData(kioskClientVo);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			model.addAttribute("kioskClientVo", kioskClientVo);
			return "/KioskClient/KioskClientForm";
		} else {
			return "redirect:/Logout";
		}
	}


	/**
	 * <pre>
	 * 1. 개요 : 단말 배포 화면
	 * 2. 처리내용 : 단말 배포 화면
	 * </pre>
	 * @Method Name : KioskDistForm
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskClientVo
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "KioskClient/KioskDistForm")
	public String KioskDistForm(@ModelAttribute("KioskClientVo") KioskClientVo kioskClientVo, ModelMap model, HttpSession session) {
		if ((String) session.getAttribute("id") != null) {

			kioskClientVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			try {
				//상세 조회
				kioskClientVo.setCaseString("KioskClient_ViewKioskClient");
				kioskClientVo = (KioskClientVo) kioskClientService.SelectData(kioskClientVo);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			model.addAttribute("kioskClientVo", kioskClientVo);
			return "/KioskClient/KioskDistForm";
		} else {
			return "redirect:/Logout";
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : 단말 목록 조회
	 * 2. 처리내용 : 단말 목록 조회
	 * </pre>
	 * @Method Name : KioskClientList
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskClientVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "KioskClient/ClientList")
	@ResponseBody
	public AjaxResultVO KioskClientList(KioskClientVo kioskClientVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			kioskClientVo.setCaseString("KioskClient_ListKioskClient");
			List<KioskClientVo> list = (List<KioskClientVo>) kioskClientService.SelectListData(kioskClientVo);
			result.setData(list);

			kioskClientVo.setCaseString("KioskClient_ListCountKioskClient");
			result.setiTotalDisplayRecords(kioskClientService.DataByCnt(kioskClientVo));

			result.setResultCode(0);
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}


	/**
	 * <pre>
	 * 1. 개요 : 단말 버전 삭제
	 * 2. 처리내용 : 단말 버전 삭제
	 * </pre>
	 * @Method Name : ClientDelete
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskClientVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "KioskClient/ClientDelete")
	@ResponseBody
	public AjaxResultVO ClientDelete(KioskClientVo kioskClientVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		kioskClientVo.setRegUser((String)session.getAttribute("id"));
		try {
			kioskClientVo.setCaseString("KioskClient_DeleteKioskClient");
			int cnt = kioskClientService.UpdateData(kioskClientVo);

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
	 * 1. 개요 : 단말 트리 조회
	 * 2. 처리내용 : 단말 트리 조회
	 * </pre>
	 * @Method Name : SearchKioskTree
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskClientVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("KioskClient/SearchKioskTree")
	@ResponseBody
	public AjaxResultVO SearchKioskTree(KioskClientVo kioskClientVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			kioskClientVo.setCaseString("KioskClient_SearchKioskTree");
			List<?> list = kioskClientService.SelectListData(kioskClientVo);
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
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. 개요 : 단말 배포 저장
	 * 2. 처리내용 : 단말 배포 저장
	 * </pre>
	 * @Method Name : DistributeCreate
	 * @date : 2019. 1. 15.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 15.		김한기				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param kioskClientVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "KioskClient/DistributeCreate")
	@ResponseBody
	public AjaxResultVO DistributeCreate(KioskClientVo kioskClientVo, HttpSession session) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		kioskClientVo.setRegUser((String)session.getAttribute("id"));
		try {
			kioskClientVo.setCaseString("KioskClient_InsertDistribute");

			int cnt = kioskClientService.UpdateData(kioskClientVo);

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