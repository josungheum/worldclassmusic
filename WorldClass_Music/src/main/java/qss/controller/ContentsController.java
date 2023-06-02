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
import qss.impl.ContentsImpl;
import qss.service.Qss;
import qss.vo.AjaxResultVO;
import qss.vo.ScreenVo;
import qss.vo.UploadVo;

/**
 * 컨텐츠 > 컨텐츠 관리
 * <pre>
 * qss.controller
 *    |_ Contentsontroller.java
 *
 * </pre>
 */
@Controller
public class ContentsController {
	@Resource(name="contentsService")
	Qss contentsService = new ContentsImpl();

	@Resource(name="commonService")
	Qss commonService = new CommonImpl();

	private static final Log logger = LogFactory.getLog(ContentsController.class);

	@RequestMapping(value="Contents/Main")
    public String brandMain(@ModelAttribute("UploadVo")UploadVo uploadVo, HttpSession session, ModelMap model, HttpServletRequest request) throws Exception
	{
		if ((String)session.getAttribute("id") != null) {
			return "/Contents/Main";
		} else {
			return "redirect:/Logout";
		}
    }


	@RequestMapping("Contents/SearchTree")
	@ResponseBody
	public AjaxResultVO SearchTree(UploadVo uploadVo, HttpSession session) throws Exception
	{
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			uploadVo.setCaseString("Contents_SearchTree");
			uploadVo.setDomainIdx((String)session.getAttribute("domainIdx"));
			uploadVo.setId((String)session.getAttribute("id"));
			//관리자 타입 설정
			uploadVo.setSessionAdminType((String)session.getAttribute("adminType"));

			List<?> list = contentsService.SelectListData(uploadVo);
			Map<String, Object> resultMap = new HashMap<String, Object>();

			resultMap.put("data", list);
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Contents/GroupList")
	@ResponseBody
	public AjaxResultVO GroupList(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		uploadVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		uploadVo.setId((String)session.getAttribute("id"));

		try {
			uploadVo.setCaseString("Contents_GroupList");
			List<ScreenVo> list = (List<ScreenVo>) contentsService.SelectListData(uploadVo);
			result.setData(list);

			uploadVo.setCaseString("Contents_GroupListCnt");
			result.setiTotalDisplayRecords(contentsService.DataByCnt(uploadVo));

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
	

	@RequestMapping(value = "Contents/GroupForm")
	public String GroupForm(@ModelAttribute("UploadVo") UploadVo uploadVo, ModelMap model, HttpSession session) throws Exception
	{
		if(Integer.valueOf(uploadVo.getGroupIdx()) > 0) {
			uploadVo.setCaseString("Contents_GetGroup");
			uploadVo = (UploadVo) contentsService.SelectData(uploadVo);
		}
		
		model.addAttribute(uploadVo);

		if ((String) session.getAttribute("id") != null) {
			return "/Contents/GroupForm";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping(value = "Contents/CreateGroup")
	@ResponseBody
	public AjaxResultVO CreateGroup(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			uploadVo.setCaseString("Contents_CreateGroup");
			uploadVo.setId((String)session.getAttribute("id"));
			uploadVo.setRegUser((String)session.getAttribute("id"));
			uploadVo.setModUser((String)session.getAttribute("id"));

			int resultCode = contentsService.InsertData(uploadVo);

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

	@RequestMapping(value = "Contents/UpdateGroup")
	@ResponseBody
	public AjaxResultVO UpdateGroup(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			uploadVo.setCaseString("Contents_UpdateGroup");
			uploadVo.setModUser((String)session.getAttribute("id"));

			int resultCode = contentsService.UpdateData(uploadVo);

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
	

	@RequestMapping("Contents/DeleteGroup")
	@ResponseBody
	public AjaxResultVO DeleteGroup(UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("Contents_DeleteGroup");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = contentsService.DeleteDataByObjectParam(null, uploadVo);
			
			if (0 == cnt) {
				result.setResultCode(1);
				messages.put("title", "정상적으로 삭제되지 못한 그룹이 존재합니다.");
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "Contents/ContentList")
	@ResponseBody
	public AjaxResultVO ContentList(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		uploadVo.setDomainIdx((String)session.getAttribute("domainIdx"));

		try {
			uploadVo.setCaseString("Contents_ContentList");
			List<ScreenVo> list = (List<ScreenVo>) contentsService.SelectListData(uploadVo);
			result.setData(list);

			uploadVo.setCaseString("Contents_ContentListCnt");
			result.setiTotalDisplayRecords(contentsService.DataByCnt(uploadVo));

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

	@RequestMapping(value = "Contents/Form")
	public String UrlForm(@ModelAttribute("UploadVo") UploadVo uploadVo, ModelMap model, HttpSession session) throws Exception
	{
		if ((String) session.getAttribute("id") != null) {
			model.addAttribute(uploadVo);
			return "/Contents/Form";
		} else {
			return "/Logout";
		}
	}
	
	@RequestMapping(value = "Contents/Create")
	@ResponseBody
	public AjaxResultVO CreateUrl(UploadVo uploadVo, HttpSession session) throws Exception {
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();

		try {
			uploadVo.setCaseString("Contents_Create");
			uploadVo.setId((String)session.getAttribute("id"));
			uploadVo.setRegUser((String)session.getAttribute("id"));
			uploadVo.setModUser((String)session.getAttribute("id"));

			int resultCode = contentsService.InsertData(uploadVo);

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


	@RequestMapping("Contents/Delete")
	@ResponseBody
	public AjaxResultVO Delete(UploadVo uploadVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		
		try {
			uploadVo.setCaseString("Contents_Delete");
			uploadVo.setModUser((String)session.getAttribute("id"));
			int cnt = contentsService.DeleteDataByObjectParam(null, uploadVo);
			
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
}