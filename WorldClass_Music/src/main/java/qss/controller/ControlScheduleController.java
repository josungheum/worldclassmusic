
package qss.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.BrandManagementImpl;
import qss.impl.ControlScheduleImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.BrandDeviceTypeVo;
import qss.vo.BrandVo;
import qss.vo.ControlScheduleVo;
import qss.vo.MenuVo;
import qss.vo.ScreenVo;
import qss.vo.SystemCodeVo;
import qss.vo.TreeVo;

/**
 * 노출 관리 > 제어 스케줄 관리
 * <pre>
 * qss.controller
 *    |_ ControlScheduleController.java
 *
 * </pre>
 * @date : 2019. 06. 10. 오전 12:22:12
 * @version :
 * @author : gyuin777
 */
@Controller
public class ControlScheduleController {
	private static final Log logger = LogFactory.getLog(ControlScheduleController.class);

	@Resource(name = "controlScheduleService")
	Qss controlScheduleService = new ControlScheduleImpl();

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();

	@Resource(name="brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();


	@RequestMapping(value = "ControlSchedule/Main")
	public String Main(@ModelAttribute("MenuVo") MenuVo menuVo, @ModelAttribute("ControlScheduleVo")ControlScheduleVo controlScheduleVo, HttpSession session, ModelMap model) throws Exception {
		if ((String) session.getAttribute("id") != null) {
			return "/ControlSchedule/Main";
		} else {
			return "redirect:/Logout";
		}
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 리스트 조회
	 * 2. 처리내용 : 제어 스케줄 리스트를 조회한다.
	 * </pre>
	 * @Method Name : List
	 * @date : 2019. 6. 10.
	 * @author : gyuin777
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 10.		gyuin777				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ControlSchedule/List")
	@ResponseBody
	public AjaxResultVO List(ControlScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		scheduleVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		// 관리자 정보 set
		scheduleVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		scheduleVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		scheduleVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		scheduleVo.setSessionAdminType((String)session.getAttribute("adminType"));

		try {
			scheduleVo.setCaseString("ControlSchedule_List");
			List<ScreenVo> list = (List<ScreenVo>) controlScheduleService.SelectListData(scheduleVo);
			result.setData(list);


			scheduleVo.setCaseString("ControlSchedule_ListCnt");
			result.setiTotalDisplayRecords(controlScheduleService.DataByCnt(scheduleVo));

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
	 * 1. 개요 : 활성화 처리
	 * 2. 처리내용 : 스케줄에 대한 활성화 여부를 처리한다.
	 * </pre>
	 * @Method Name : ActiveYn
	 * @date : 2019. 6. 10.
	 * @author : gyuin777
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 6. 10.		gyuin777				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ControlSchedule/ActiveYn")
	@ResponseBody
	public AjaxResultVO ActiveYn(ControlScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("ControlSchedule_SetActiveYn");
			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));
			int cnt = controlScheduleService.UpdateData(scheduleVo);

			if (cnt > 0) {
				result.setResultCode(1);
				messages.put("title", "로직 오류가 발생하였습니다.");
				result.setMessages(messages);
			}else{
				result.setResultCode(0);
			}
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
	 * 1. 개요 : 스케줄 폼
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : scheduleForm
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "ControlSchedule/ScheduleForm")
	public String scheduleForm(@ModelAttribute("ControlScheduleVo") ControlScheduleVo controlScheduleVo, ModelMap model, HttpSession session) {
		controlScheduleVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		List<BrandDeviceTypeVo> brandDeviceTypeList = new ArrayList<BrandDeviceTypeVo>();
		try {
			// 해당 매장이 속한 서비스에서 설정한 디바이스 정보 조회
			BrandVo brandVo = new BrandVo();
			brandVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			brandVo.setBrandIdx((String)controlScheduleVo.getBrandIdx());
			brandVo.setCaseString("Brand_ListBrandDeviceType");
			brandDeviceTypeList = (List<BrandDeviceTypeVo>) brandManagementService.SelectListData(brandVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		List<SystemCodeVo> deviceResTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVR0000");
		List<SystemCodeVo> deviceSiteTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVS0000");
		List<SystemCodeVo> controlCodeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("CTL0000");
		
		model.addAttribute("brandDeviceTypeList", brandDeviceTypeList);
		model.addAttribute("deviceResTypeList", deviceResTypeList);
		model.addAttribute("deviceSiteTypeList", deviceSiteTypeList);
		model.addAttribute("controlCodeList", controlCodeList);

		if (controlScheduleVo.getControlScheduleIdx() == null || controlScheduleVo.getControlScheduleIdx().intValue() == 0) {
			return "/ControlSchedule/ScheduleForm";
		}

		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			controlScheduleVo.setCaseString("ControlSchedule_ScheduleGet");
			controlScheduleVo = (ControlScheduleVo) controlScheduleService.SelectData(controlScheduleVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		model.addAttribute("ControlScheduleVo", controlScheduleVo);

		return "/ControlSchedule/ScheduleForm";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "ControlSchedule/ScheduleTree")
	@ResponseBody
	public AjaxResultVO ScheduleTree(ControlScheduleVo scheduleVo) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("ControlSchedule_ScheduleTree");
			List<TreeVo> list = (List<TreeVo>) controlScheduleService.SelectListData(scheduleVo);

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

	@RequestMapping(value = "ControlSchedule/ScreenForm")
	public String ScreenForm(@ModelAttribute("ControlScheduleVo") ControlScheduleVo scheduleVo, ModelMap model) {
		return "/ControlSchedule/ScreenForm";
	}
	
	/**
	 * <pre>
	 * 1. 개요 : 스케줄 등록
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : CreateSchedule
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ControlSchedule/CreateSchedule")
	@ResponseBody
	public AjaxResultVO CreateSchedule(ControlScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("ControlSchedule_CreateSchedule");
			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));

			int resultCode = controlScheduleService.InsertData(scheduleVo);

			if (resultCode == 1) {
				result.setResultCode(1);
				messages.put("title", "스케줄 등록 중 오류가 발생하였습니다.");
			}
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
	 * 1. 개요 : 스케줄 수정
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : UpdateSchedule
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ControlSchedule/UpdateSchedule", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO UpdateSchedule(ControlScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("ControlSchedule_UpdateSchedule");

			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));
			int resultCode = controlScheduleService.UpdateData(scheduleVo);

			if (resultCode == 1) {
				result.setResultCode(1);
				messages.put("title", "스케줄 변경 중 오류가 발생하였습니다.");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}
	
	@RequestMapping("ControlSchedule/SearchKioskTree")
	@ResponseBody
	public AjaxResultVO SearchKioskTree(ControlScheduleVo scheduleVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("ControlSchedule_SearchKioskTree");
			scheduleVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
			scheduleVo.setSessionAdminType((String)session.getAttribute("adminType"));
			List<?> list = controlScheduleService.SelectListData(scheduleVo);
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
	
	/**
	 * <pre>
	 * 1. 개요 : 스케줄 삭제
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : ScheduleDelete
	 * @date : 2019. 3. 12.
	 * @author : 김성환
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ControlSchedule/ScheduleDelete")
	@ResponseBody
	public AjaxResultVO ScheduleDelete(ControlScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("ControlSchedule_ScheduleDelete");
			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));
			int cnt = controlScheduleService.DeleteDataByObjectParam(null, scheduleVo);

			if (0 == cnt) {
				result.setResultCode(1);
				messages.put("title", "정상적으로 삭제되지 못한 스케줄이 존재합니다.");
				result.setMessages(messages);
			}else {
				result.setResultCode(0);
			}
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