
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

import qss.impl.ScheduleImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.CalendarVo;
import qss.vo.KioskVo;
import qss.vo.MenuVo;

/**
 * 노출 관리 > 스케줄 편성표 관리
 * <pre>
 * qss.controller
 *    |_ CalendarScheduleController.java
 *
 * </pre>
 * @date : 2018. 12. 25. 오전 12:22:12
 * @version :
 * @author : admin
 */
@Controller
public class CalendarScheduleController {
	private static final Log logger = LogFactory.getLog(CalendarScheduleController.class);

	@Resource(name = "calendarService")
	Qss calendarService = new ScheduleImpl();

	@RequestMapping(value = "Calendar/Main")
	public String Main(@ModelAttribute("MenuVo") MenuVo menuVo, @ModelAttribute("CalendarVo")CalendarVo calendarVo, HttpSession session, ModelMap model) throws Exception {
		if ((String) session.getAttribute("id") != null) {
			return "/Calendar/Main";
		} else {
			return "redirect:/Logout";
		}
	}


	/**
	 * <pre>
	 * 1. 개요 : 스케줄 편성표 리스트 조회
	 * 2. 처리내용 :
	 * </pre>
	 * @Method Name : CalendarList
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
	@RequestMapping(value = "Calendar/CalendarList")
	@ResponseBody
	public AjaxResultVO CalendarList(CalendarVo calendarVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		Map<String, List<?>> list = new HashMap<String, List<?>>();

		try {
			// 관리자 정보 set
			calendarVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
			calendarVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
			calendarVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
			calendarVo.setSessionAdminType((String)session.getAttribute("adminType"));

			// 디바이스 목록
			calendarVo.setCaseString("Calendar_DeviceList");
			List<KioskVo> deviceList = (List<KioskVo>) calendarService.SelectListData(calendarVo);
			list.put("deviceList", deviceList);

			// 갤린더 목록
			calendarVo.setCaseString("Calendar_CalendarList");
			List<CalendarVo> calendarList = (List<CalendarVo>) calendarService.SelectListData(calendarVo);
			list.put("calendarList", calendarList);

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
}