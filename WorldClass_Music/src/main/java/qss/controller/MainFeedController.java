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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.CommonImpl;
import qss.impl.MainFeedImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.UploadVo;

/**
 * 
 * 메인피드 관리
 * 
 */
@Controller
public class MainFeedController {
	@Resource(name="mainFeedService")
	Qss mainFeedService = new MainFeedImpl();

	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	private static final Log logger = LogFactory.getLog(MainFeedController.class);

	@RequestMapping(value="MainFeed/Main")
    public String brandMain(@ModelAttribute("UploadVo")UploadVo uploadVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			return "/MainFeed/Main";
		} else {
			return "redirect:/Logout";
		}
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "MainFeed/MainFeedList")
	@ResponseBody
	public AjaxResultVO ContentList(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		uploadVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		try {
			uploadVo.setCaseString("MainFeed_MainFeedList");
			List<UploadVo> list = (List<UploadVo>) mainFeedService.SelectListData(uploadVo);
			result.setData(list);

			uploadVo.setCaseString("MainFeed_MainFeedListCnt");
			result.setiTotalDisplayRecords(mainFeedService.DataByCnt(uploadVo));

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

	@RequestMapping(value = "MainFeed/Form")
	public String UrlForm(@ModelAttribute("UploadVo") UploadVo uploadVo, ModelMap model, HttpSession session) throws Exception
	{
		if ((String) session.getAttribute("id") != null) {
			model.addAttribute(uploadVo);
			return "/MainFeed/Form";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping(value = "MainFeed/Create")
	@ResponseBody
	public AjaxResultVO CreateUrl(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			uploadVo.setCaseString("MainFeed_Create");
			uploadVo.setId((String)session.getAttribute("id"));
			uploadVo.setRegUser((String)session.getAttribute("id"));
			uploadVo.setModUser((String)session.getAttribute("id"));

			int resultCode = mainFeedService.InsertData(uploadVo);

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


	@RequestMapping("MainFeed/Delete")
	@ResponseBody
	public AjaxResultVO Delete(UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("MainFeed_Delete");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = mainFeedService.DeleteDataByObjectParam(null, uploadVo);
			
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
	
	@RequestMapping("MainFeed/ActiveYn")
	@ResponseBody
	public AjaxResultVO ActiveYn(UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("MainFeed_ActiveYn");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = mainFeedService.UpdateData(uploadVo);
			
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