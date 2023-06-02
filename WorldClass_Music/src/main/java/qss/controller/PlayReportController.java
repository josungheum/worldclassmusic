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

import qss.impl.PlayReportImpl;
import qss.util.WebUtil;
import qss.view.PlayReportExcelView;
import qss.vo.AjaxResultVO;
import qss.vo.PlayReportVo;

@Controller
public class PlayReportController {

	@Resource(name="playReportService")
	PlayReportImpl playReportService = new PlayReportImpl();

	private static final Log logger = LogFactory.getLog(PlayReportController.class);

	@SuppressWarnings("unchecked")
	@RequestMapping(value="PlayReport/Main")
    public String Main(@ModelAttribute("PlayReportVo")PlayReportVo playReportVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{

		if ((String)session.getAttribute("id") != null) {
			List<Map<String, Object>> serviceList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> siteList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> playerList = new ArrayList<Map<String,Object>>();
			
			playReportVo.setDomainIdx((String)session.getAttribute("domainIdx"));

			try {
				playReportVo.setCaseString("PlayReport_ServiceList");
				serviceList = (List<Map<String, Object>>) playReportService.SelectListData(playReportVo);
	 			
				playReportVo.setCaseString("PlayReport_SiteList");
				siteList = (List<Map<String, Object>>) playReportService.SelectListData(playReportVo);

				playReportVo.setCaseString("PlayReport_PlayerList");
				playerList = (List<Map<String, Object>>) playReportService.SelectListData(playReportVo);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			model.addAttribute("serviceList", serviceList);
			model.addAttribute("siteList", siteList);
			model.addAttribute("playerList", playerList);
			
			return "/PlayReport/Main";
		}
		else {
			return "redirect:/Logout";
		}
    }
	
	@RequestMapping("PlayReport/List")
	@ResponseBody
	public AjaxResultVO List(PlayReportVo playReportVo, HttpSession session)
	{
		AjaxResultVO result = new AjaxResultVO();
		try {
			playReportVo.setCaseString("PlayReport_List");
			List<?> list = playReportService.SelectListData(playReportVo);
			result.setData(list);

			playReportVo.setCaseString("PlayReport_ListCnt");
			result.setiTotalDisplayRecords(playReportService.DataByCnt(playReportVo));
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		return result;
	}
	
	
	@RequestMapping(value = "PlayReport/ExportExcelCnt")
	@ResponseBody
	public AjaxResultVO ExportExcelCnt(PlayReportVo playReportVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			playReportVo.setCaseString("PlayReport_ListCnt");
			int totalCnt = playReportService.DataByCnt(playReportVo);
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
	@RequestMapping(value = "PlayReport/ExportExcel/{plPlaydate}/{brandIdx}/{francIdx}/{deviceIdx}/{screenName}/{cntTotal}/{sSortName}/{sSortDir_0}", method = RequestMethod.GET)
	public View ExportExcel(PlayReportVo playReportVo, ModelMap model) {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		List<PlayReportVo> list = new ArrayList<PlayReportVo>();

		try {
			playReportVo.setiDisplayStart(playReportVo.getCntTotal()*1000);
			playReportVo.setiDisplayLength((playReportVo.getCntTotal()+1)*1000);
			playReportVo.setCaseString("PlayReport_List");
			list = (List<PlayReportVo>) playReportService.SelectListData(playReportVo);
			
			model.addAttribute("List", list);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			result.setResultCode(2);
			messages.put("title", "서버오류가 발생하였습니다.");
			messages.put("detail", e.getMessage().replaceAll(";", "\n"));
			result.setMessages(messages);
		}

		return new PlayReportExcelView();
	}
}
