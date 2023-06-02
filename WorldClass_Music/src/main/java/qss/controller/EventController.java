package qss.controller;

import java.math.BigInteger;
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
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.EventImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AjaxResultVO;
import qss.vo.EventVo;

@Controller
@RequestMapping(value="/Event")
public class EventController {
	private static final Log logger = LogFactory.getLog(EventController.class);
	
	@Resource(name="eventService")
	Qss eventService = new EventImpl();

	@RequestMapping(value="/Main")
    public String Main(@ModelAttribute("EventVo")EventVo eventVo, HttpSession session, ModelMap model) throws Exception
	{	
		if ((String) session.getAttribute("id") != null) 
			return "/Event/Main";
		else 
			return "/Logout";
    }
	
	@RequestMapping("/ImageList")
	@ResponseBody
	public AjaxResultVO ImageList(EventVo eventVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		eventVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		try {
			eventVo.setCaseString("Event_ImageList");
			List<?> list = eventService.SelectListData(eventVo);
			result.setData(list);
			
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}
	
	@RequestMapping("/List")
	@ResponseBody
	public AjaxResultVO List(EventVo eventVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		// 관리자 정보 set
		eventVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		eventVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		eventVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			eventVo.setCaseString("Event_List");
			List<?> list = eventService.SelectListData(eventVo);
			result.setData(list);
			
			eventVo.setCaseString("Event_ListCnt");
			result.setiTotalDisplayRecords(eventService.DataByCnt(eventVo));
			
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}

	@RequestMapping(value = "/Form")
	public String Form(@ModelAttribute("EventVo") EventVo eventVo, ModelMap model, HttpSession session) throws Exception
	{
		if (eventVo.getEventIdx() != null && !eventVo.getEventIdx().equals(BigInteger.ZERO)) {
			eventVo.setCaseString("Event_Get");
			eventVo = (EventVo)eventService.SelectData(eventVo);
		}
		model.addAttribute("eventVo", eventVo);
		
		return "/Event/Form";
	}
	

	@RequestMapping(value = "/Create")
	@ResponseBody
	public AjaxResultVO Create(EventVo eventVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			eventVo.setRegUser((String)session.getAttribute("id"));
			eventVo.setModUser((String)session.getAttribute("id"));
			
			if (eventVo.getHoriXML() != null && !eventVo.getHoriXML().equals("")) {
				List<EventVo> list = new ArrayList<EventVo>();
				ArrayList<String> fileIdx = WebUtil.ParsingXML(eventVo.getHoriXML(), "Hori", "fileIdx");
				ArrayList<String> orderSeq = WebUtil.ParsingXML(eventVo.getHoriXML(), "Hori", "orderSeq");
				
				for (int i = 0; i < fileIdx.size(); i++) {
					EventVo vo = new EventVo();
					vo.setFileContentIdx(BigInteger.valueOf(Long.valueOf(fileIdx.get(i))));
					vo.setOrderSeq(Integer.valueOf(orderSeq.get(i)));
					list.add(vo);
				}
				
				eventVo.setHoriList(list);
			}
			
			if (eventVo.getVertXML() != null && !eventVo.getVertXML().equals("")) {
				List<EventVo> list = new ArrayList<EventVo>();
				ArrayList<String> fileIdx = WebUtil.ParsingXML(eventVo.getVertXML(), "Vert", "fileIdx");
				ArrayList<String> orderSeq = WebUtil.ParsingXML(eventVo.getVertXML(), "Vert", "orderSeq");
				
				for (int i = 0; i < fileIdx.size(); i++) {
					EventVo vo = new EventVo();
					vo.setFileContentIdx(BigInteger.valueOf(Long.valueOf(fileIdx.get(i))));
					vo.setOrderSeq(Integer.valueOf(orderSeq.get(i)));
					list.add(vo);
				}
				
				eventVo.setVertList(list);
			}
			
			eventVo.setCaseString("Event_Create");
			int idx = eventService.InsertData(eventVo);
			if (idx > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "등록도중 오류가 발생하였습니다.");
				messages.put("detail", "");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/Update")
	@ResponseBody
	public AjaxResultVO Update(EventVo eventVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			eventVo.setModUser((String)session.getAttribute("id"));
			if (eventVo.getHoriXML() != null && !eventVo.getHoriXML().equals("")) {
				List<EventVo> list = new ArrayList<EventVo>();
				ArrayList<String> fileIdx = WebUtil.ParsingXML(eventVo.getHoriXML(), "Hori", "fileIdx");
				ArrayList<String> orderSeq = WebUtil.ParsingXML(eventVo.getHoriXML(), "Hori", "orderSeq");
				
				for (int i = 0; i < fileIdx.size(); i++) {
					EventVo vo = new EventVo();
					vo.setFileContentIdx(BigInteger.valueOf(Long.valueOf(fileIdx.get(i))));
					vo.setOrderSeq(Integer.valueOf(orderSeq.get(i)));
					list.add(vo);
				}
				
				eventVo.setHoriList(list);
			}
			
			if (eventVo.getVertXML() != null && !eventVo.getVertXML().equals("")) {
				List<EventVo> list = new ArrayList<EventVo>();
				ArrayList<String> fileIdx = WebUtil.ParsingXML(eventVo.getVertXML(), "Vert", "fileIdx");
				ArrayList<String> orderSeq = WebUtil.ParsingXML(eventVo.getVertXML(), "Vert", "orderSeq");
				
				for (int i = 0; i < fileIdx.size(); i++) {
					EventVo vo = new EventVo();
					vo.setFileContentIdx(BigInteger.valueOf(Long.valueOf(fileIdx.get(i))));
					vo.setOrderSeq(Integer.valueOf(orderSeq.get(i)));
					list.add(vo);
				}
				
				eventVo.setVertList(list);
			}
			
			eventVo.setCaseString("Event_Update");
			int idx = eventService.UpdateData(eventVo);
			if (idx > 0) {
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "수정도중 오류가 발생하였습니다.");
				messages.put("detail", "");
			}
			result.setMessages(messages);
		}
		catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
		
		return result;
	}
	
	@RequestMapping(value = "/Delete")
	@ResponseBody
	public AjaxResultVO Delete(EventVo eventVo, HttpSession session) 
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		eventVo.setModUser((String)session.getAttribute("id"));
		try {
			eventVo.setCaseString("Event_Delete");
			int cnt = eventService.UpdateData(eventVo);
		
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
	
	@RequestMapping(value = "/ActiveYn")
	@ResponseBody
	public AjaxResultVO ActiveYn(EventVo eventVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			eventVo.setCaseString("Event_ActiveYn");
			eventVo.setModUser((String)session.getAttribute("id"));
			int cnt = eventService.UpdateData(eventVo);

			if (cnt > 0) {
				result.setResultCode(0);
			}
			else{
				result.setResultCode(1);
				messages.put("title", "로직 오류가 발생하였습니다.");
				result.setMessages(messages);
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