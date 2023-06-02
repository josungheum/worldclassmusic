package qss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import qss.impl.ScheduleReportImpl;
import qss.util.WebUtil;
import qss.view.ScheduleReportExcelView;
import qss.vo.AjaxResultVO;
import qss.vo.ScheduleReportVo;

@Controller
public class ScheduleReportController {

	@Resource(name="scheduleReportService")
	ScheduleReportImpl scheduleReportService = new ScheduleReportImpl();

	private static final Log logger = LogFactory.getLog(ScheduleReportController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value="ScheduleReport/Main")
    public String Main(@ModelAttribute("ScheduleReportVo")ScheduleReportVo scheduleReportVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			List<Map<String, Object>> serviceList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> siteList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> playerList = new ArrayList<Map<String,Object>>();
			
			scheduleReportVo.setDomainIdx((String)session.getAttribute("domainIdx"));

			try {
				scheduleReportVo.setCaseString("ScheduleReport_ServiceList");
				serviceList = (List<Map<String, Object>>) scheduleReportService.SelectListData(scheduleReportVo);
	 			
				scheduleReportVo.setCaseString("ScheduleReport_SiteList");
				siteList = (List<Map<String, Object>>) scheduleReportService.SelectListData(scheduleReportVo);

				scheduleReportVo.setCaseString("ScheduleReport_PlayerList");
				playerList = (List<Map<String, Object>>) scheduleReportService.SelectListData(scheduleReportVo);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			model.addAttribute("serviceList", serviceList);
			model.addAttribute("siteList", siteList);
			model.addAttribute("playerList", playerList);
			
			return "/ScheduleReport/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }
	
	@RequestMapping("ScheduleReport/List")
	@ResponseBody
	public AjaxResultVO List(ScheduleReportVo scheduleReportVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			scheduleReportVo.setCaseString("ScheduleReport_List");
			List<?> list = scheduleReportService.SelectListData(scheduleReportVo);
			result.setData(list);

			scheduleReportVo.setCaseString("ScheduleReport_ListCnt");
			result.setiTotalDisplayRecords(scheduleReportService.DataByCnt(scheduleReportVo));
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	
	@RequestMapping(value = "ScheduleReport/ExportExcelCnt")
	@ResponseBody
	public AjaxResultVO ExportExcelCnt(ScheduleReportVo scheduleReportVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			scheduleReportVo.setCaseString("ScheduleReport_ListCnt");
			int totalCnt = scheduleReportService.DataByCnt(scheduleReportVo);
			int excelCnt = (int) Math.ceil((double)totalCnt/1000);

			result.setiTotalDisplayRecords(excelCnt);
			result.setResultCode(0);
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
	@RequestMapping(value = "ScheduleReport/ExportExcel/{searchStart}/{searchEnd}/{brandIdx}/{francIdx}/{deviceIdx}/{cntTotal}/{sSortName}/{sSortDir_0}", method = RequestMethod.GET)
	public View ExportExcel(ScheduleReportVo scheduleReportVo, ModelMap model) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		List<ScheduleReportVo> list = new ArrayList<ScheduleReportVo>();

		try {
			scheduleReportVo.setiDisplayStart(scheduleReportVo.getCntTotal()*1000);
			scheduleReportVo.setiDisplayLength((scheduleReportVo.getCntTotal()+1)*1000);
			scheduleReportVo.setCaseString("ScheduleReport_List");
			list = (List<ScheduleReportVo>) scheduleReportService.SelectListData(scheduleReportVo);
			
			model.addAttribute("List", list);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return new ScheduleReportExcelView();
	}
}
