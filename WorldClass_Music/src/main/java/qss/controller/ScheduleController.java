
package qss.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import qss.impl.ScheduleImpl;
import qss.impl.SystemCodeImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.BrandDeviceTypeVo;
import qss.vo.BrandVo;
import qss.vo.MenuVo;
import qss.vo.ScheduleVo;
import qss.vo.ScreenVo;
import qss.vo.SystemCodeVo;
import qss.vo.TreeVo;
import qss.vo.UploadVo;

/**
 * 노출 관리 > 스케줄 관리
 * <pre>
 * qss.controller
 *    |_ ScheduleController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:22:12
 * @version :
 * @author : admin
 */
@Controller
public class ScheduleController {
	private static final Log logger = LogFactory.getLog(ScheduleController.class);

	@Resource(name = "scheduleService")
	Qss scheduleService = new ScheduleImpl();

	@Resource(name="systemCodeService")
	Qss systemCodeService = new SystemCodeImpl();

	@Resource(name="brandManagementService")
	Qss brandManagementService = new BrandManagementImpl();

	@RequestMapping(value = "Schedule/Main")
	public String Main(@ModelAttribute("MenuVo") MenuVo menuVo, @ModelAttribute("ScheduleVo")ScheduleVo scheduleVo, HttpSession session, ModelMap model) throws Exception {
		if ((String) session.getAttribute("id") != null) {
			return "/Schedule/Main";
		} else {
			return "redirect:/Logout";
		}
	}


	/**
	 * <pre>
	 * 1. 개요 : 리스트 조회
	 * 2. 처리내용 : 스케줄 리스트를 조회한다.
	 * </pre>
	 * @Method Name : List
	 * @date : 2019. 1. 8.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 8.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Schedule/List")
	@ResponseBody
	public AjaxResultVO List(ScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		scheduleVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		// 관리자 정보 set
		scheduleVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		scheduleVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		scheduleVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		scheduleVo.setSessionAdminType((String)session.getAttribute("adminType"));

		try {
			scheduleVo.setCaseString("Schedule_List");
			List<ScreenVo> list = (List<ScreenVo>) scheduleService.SelectListData(scheduleVo);
			result.setData(list);


			scheduleVo.setCaseString("Schedule_ListCnt");
			result.setiTotalDisplayRecords(scheduleService.DataByCnt(scheduleVo));

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
	 * @date : 2019. 1. 8.
	 * @author : ksh
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 1. 8.		ksh				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "Schedule/ActiveYn")
	@ResponseBody
	public AjaxResultVO ActiveYn(ScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_SetActiveYn");
			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));
			int cnt = scheduleService.UpdateData(scheduleVo);

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
	@RequestMapping(value = "Schedule/ScheduleDelete")
	@ResponseBody
	public AjaxResultVO ScheduleDelete(ScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_ScheduleDelete");
			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));
			int cnt = scheduleService.DeleteDataByObjectParam(null, scheduleVo);

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
	@RequestMapping(value = "Schedule/ScheduleForm")
	public String scheduleForm(@ModelAttribute("ScheduleVo") ScheduleVo scheduleVo, ModelMap model, HttpSession session) {
		scheduleVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		List<BrandDeviceTypeVo> brandDeviceTypeList = new ArrayList<BrandDeviceTypeVo>();
		try {
			// 해당 매장이 속한 서비스에서 설정한 디바이스 정보 조회
			BrandVo brandVo = new BrandVo();
			brandVo.setDomainIdx(scheduleVo.getDomainIdx());
			brandVo.setBrandIdx((String)scheduleVo.getBrandIdx());
			brandVo.setCaseString("Brand_ListBrandDeviceType");
			brandDeviceTypeList = (List<BrandDeviceTypeVo>) brandManagementService.SelectListData(brandVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		List<SystemCodeVo> deviceResTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVR0000");
		List<SystemCodeVo> deviceSiteTypeList = (List<SystemCodeVo>) systemCodeService.SelectCodeData("DVS0000");
		
		model.addAttribute("brandDeviceTypeList", brandDeviceTypeList);
		model.addAttribute("deviceResTypeList", deviceResTypeList);
		model.addAttribute("deviceSiteTypeList", deviceSiteTypeList);

		if (scheduleVo.getMainScheduleIdx() == null || scheduleVo.getMainScheduleIdx().intValue() == 0) {
			return "/Schedule/ScheduleForm";
		}

		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_ScheduleGet");
			scheduleVo = (ScheduleVo) scheduleService.SelectData(scheduleVo);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		model.addAttribute("ScheduleVo", scheduleVo);

		return "/Schedule/ScheduleForm";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Schedule/ScheduleTree")
	@ResponseBody
	public AjaxResultVO ScheduleTree(ScheduleVo scheduleVo) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_ScheduleTree");
			List<TreeVo> list = (List<TreeVo>) scheduleService.SelectListData(scheduleVo);

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

	@RequestMapping(value = "Schedule/ScreenForm")
	public String ScreenForm(@ModelAttribute("ScheduleVo") ScheduleVo scheduleVo, ModelMap model) {
		return "/Schedule/ScreenForm";
	}

	/**
	 * <pre>
	 * 1. 개요 : 스크린 리스트 조회
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : ScreenList
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
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Schedule/ScreenList")
	@ResponseBody
	public AjaxResultVO ScreenList(ScheduleVo scheduleVo) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_ScreenList");
			List<TreeVo> list = (List<TreeVo>) scheduleService.SelectListData(scheduleVo);

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
	 * 1. 개요 : 운영 리스트 조회
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : OperationList
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
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Schedule/OperationList")
	@ResponseBody
	public AjaxResultVO OperationList(ScheduleVo scheduleVo) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_OperationList");
			List<TreeVo> list = (List<TreeVo>) scheduleService.SelectListData(scheduleVo);

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Schedule/ContentList")
	@ResponseBody
	public AjaxResultVO ContentList(ScheduleVo scheduleVo) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_ContentList");
			List<UploadVo> list = (List<UploadVo>) scheduleService.SelectListData(scheduleVo);

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
	@RequestMapping(value = "Schedule/CreateSchedule")
	@ResponseBody
	public AjaxResultVO CreateSchedule(ScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_CreateSchedule");
			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));

			int resultCode = scheduleService.InsertData(scheduleVo);

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
	@RequestMapping(value = "Schedule/UpdateSchedule", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResultVO UpdateSchedule(ScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_UpdateSchedule");

			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));
			int resultCode = scheduleService.UpdateData(scheduleVo);

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
	

	/**
	 * <pre>
	 * 1. 개요 : 스케줄 중복 체크
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : overlapScreenschedule
	 * @date : 2019. 6. 13.
	 * @author : 김한기
	 * @history :
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 3. 12.		김성환				최초 작성
	 *	-----------------------------------------------------------------------
	 *
	 * @param scheduleVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "Schedule/overlapScreenschedule")
	@ResponseBody
	public AjaxResultVO overlapScreenschedule(ScheduleVo scheduleVo) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_OverlapScreenschedule");
			int cnt = scheduleService.DataByCnt(scheduleVo);

			result.setData(cnt);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public String MapToXML(Map<String, Object> map, String tag) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <" + tag + ">";

		Set<String> keys = map.keySet();

		for (String key : keys) {
			xml = xml + "<" + key + ">";
			if ("java.util.ArrayList".equals(map.get(key).getClass().getName())) {
				List<Object> dataList = (List<Object>) map.get(key);

				for (Object data : dataList) {
					xml = xml + "<value> <![CDATA[" + data + "]]></value>";
				}
			} else {
				xml = xml + "<![CDATA[" + map.get(key) + "]]>";
			}
			xml = xml + "</" + key + ">";
		}

		xml = xml + "</" + tag + ">";

		try {
			xml = new String(xml.getBytes("utf-8"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xml ;
	}


	@RequestMapping("Schedule/SearchKioskTree")
	@ResponseBody
	public AjaxResultVO SearchKioskTree(ScheduleVo scheduleVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_SearchKioskTree");

			scheduleVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
			scheduleVo.setSessionAdminType((String)session.getAttribute("adminType"));
			List<?> list = scheduleService.SelectListData(scheduleVo);
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
	 * 1. 개요 : mqqt시작,종료 처리
	 * 2. 처리내용 : 스케줄에 대한 mqqt시작를 처리한다.
	 * </pre>
	 * @Method Name : mqqt시작
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
	@RequestMapping("Schedule/mqqtYn")
	@ResponseBody
	public AjaxResultVO mqqtYn(ScheduleVo scheduleVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleVo.setCaseString("Schedule_SetMqqtYn");
			scheduleVo.setRegUser((String)session.getAttribute("id"));
			scheduleVo.setModUser((String)session.getAttribute("id"));
			int cnt = scheduleService.UpdateData(scheduleVo);

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
	
	
}