package qss.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.CommonImpl;
import qss.impl.MainFeedImpl;
import qss.impl.MainVideoImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.UploadVo;

/**
 * 
 * 메인피드 관리
 * 
 */
@Controller
public class MainVideoController {
	@Resource(name="mainVideoService")
	Qss mainVideoService = new MainVideoImpl();

	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	private static final Log logger = LogFactory.getLog(MainVideoController.class);

	@RequestMapping(value="MainVideo/Main")
    public String brandMain(@ModelAttribute("UploadVo")UploadVo uploadVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			return "/MainVideo/Main";
		} else {
			return "redirect:/Logout";
		}
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "MainVideo/MainVideoList")
	@ResponseBody
	public AjaxResultVO ContentList(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		uploadVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		try {
			uploadVo.setCaseString("MainVideo_MainVideoList");
			List<UploadVo> list = (List<UploadVo>) mainVideoService.SelectListData(uploadVo);
			result.setData(list);

			uploadVo.setCaseString("MainVideo_MainVideoListCnt");
			result.setiTotalDisplayRecords(mainVideoService.DataByCnt(uploadVo));

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

	@RequestMapping(value = "MainVideo/Form")
	public String UrlForm(@ModelAttribute("UploadVo") UploadVo uploadVo, ModelMap model, HttpSession session) throws Exception
	{
		if ((String) session.getAttribute("id") != null) {
			model.addAttribute(uploadVo);
			return "/MainVideo/Form";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping(value = "MainVideo/Create")
	@ResponseBody
	public AjaxResultVO CreateUrl(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			uploadVo.setCaseString("MainVideo_Create");
			uploadVo.setId((String)session.getAttribute("id"));
			uploadVo.setRegUser((String)session.getAttribute("id"));
			uploadVo.setModUser((String)session.getAttribute("id"));

			int resultCode = mainVideoService.InsertData(uploadVo);

			if (resultCode > 0)
			{
				result.setResultCode(0);
			}
			else {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
			}
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


	@RequestMapping("MainVideo/Delete")
	@ResponseBody
	public AjaxResultVO Delete(UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("MainVideo_Delete");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = mainVideoService.DeleteDataByObjectParam(null, uploadVo);
			
			if (0 == cnt) {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
				result.setMessages(messages);
			}else {
				result.setResultCode(0);
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
	
	@RequestMapping("MainVideo/ActiveYn")
	@ResponseBody
	public AjaxResultVO ActiveYn(UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("MainVideo_ActiveYn");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = mainVideoService.UpdateData(uploadVo);
			
			if (0 == cnt) {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
				result.setMessages(messages);
			}else {
				result.setResultCode(0);
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
	
	@RequestMapping(value = "MainVideo/SortForm")
	public String SortForm(@ModelAttribute("UploadVo") UploadVo uploadVo, ModelMap model, HttpSession session) throws Exception
	{
		if ((String) session.getAttribute("id") != null) {
			model.addAttribute(uploadVo);
			return "/MainVideo/SortForm";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping(value = "MainVideo/SortUpdate")
	@ResponseBody
	public AjaxResultVO SortUpdate(@RequestBody UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("MainVideo_SortUpdate");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = mainVideoService.UpdateData(uploadVo);
			
			if (0 == cnt) {
				result.setResultCode(1);
				messages.put("title", "처리 되지 않았습니다.");
				result.setMessages(messages);
			}else {
				result.setResultCode(0);
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
}