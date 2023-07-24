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
import qss.impl.InstaFeedImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.MainFeedVo;
import qss.vo.UploadVo;

/**
 * 
 * 인스타피드 관리
 * 
 */
@Controller
public class InstaFeedController {
	@Resource(name="instaFeedService")
	Qss instaFeedService = new InstaFeedImpl();

	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	private static final Log logger = LogFactory.getLog(InstaFeedController.class);

	@RequestMapping(value="InstaFeed/Main")
    public String brandMain(@ModelAttribute("UploadVo")UploadVo uploadVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			return "/InstaFeed/Main";
		} else {
			return "redirect:/Logout";
		}
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "InstaFeed/InstaFeedList")
	@ResponseBody
	public AjaxResultVO ContentList(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		uploadVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		try {
			uploadVo.setCaseString("InstaFeed_InstaFeedList");
			List<MainFeedVo> list = (List<MainFeedVo>) instaFeedService.SelectListData(uploadVo);
			result.setData(list);

			uploadVo.setCaseString("InstaFeed_InstaFeedListCnt");
			result.setiTotalDisplayRecords(instaFeedService.DataByCnt(uploadVo));

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

	@RequestMapping(value = "InstaFeed/Form")
	public String UrlForm(@ModelAttribute("UploadVo") UploadVo uploadVo, ModelMap model, HttpSession session) throws Exception
	{
		if ((String) session.getAttribute("id") != null) {
			model.addAttribute(uploadVo);
			return "/InstaFeed/Form";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping(value = "InstaFeed/Create")
	@ResponseBody
	public AjaxResultVO CreateUrl(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			uploadVo.setCaseString("InstaFeed_Create");
			uploadVo.setId((String)session.getAttribute("id"));
			uploadVo.setRegUser((String)session.getAttribute("id"));
			uploadVo.setModUser((String)session.getAttribute("id"));

			int resultCode = instaFeedService.InsertData(uploadVo);

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


	@RequestMapping("InstaFeed/Delete")
	@ResponseBody
	public AjaxResultVO Delete(UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("InstaFeed_Delete");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = instaFeedService.DeleteDataByObjectParam(null, uploadVo);
			
			if (0 == cnt) {
				result.setResultCode(1);
				messages.put("title", "정상적으로 삭제되지 못한 컨텐츠가 존재합니다.");
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

	@RequestMapping(value = "InstaFeed/SortForm")
	public String SortForm(@ModelAttribute("UploadVo") UploadVo uploadVo, ModelMap model, HttpSession session) throws Exception
	{
		if ((String) session.getAttribute("id") != null) {
			model.addAttribute(uploadVo);
			return "/InstaFeed/SortForm";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping(value = "InstaFeed/SortUpdate")
	@ResponseBody
	public AjaxResultVO SortUpdate(@RequestBody UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("InstaFeed_SortUpdate");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = instaFeedService.UpdateData(uploadVo);
			
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