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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qss.impl.AdditionImageListImpl;
import qss.service.Qss;
import qss.util.WebUtil;
import qss.vo.AdditionImageListVo;
import qss.vo.AjaxResultVO;

@Controller
@RequestMapping(value="/Addition")
public class AdditionImageListController {
	private static final Log logger = LogFactory.getLog(AdditionImageListController.class);
	
	@Resource(name="additionImageListService")
	Qss additionImageListService = new AdditionImageListImpl();

	@RequestMapping(value="/Main")
    public String Main(@ModelAttribute("AdditionImageListVo")AdditionImageListVo additionImageListVo, HttpSession session, ModelMap model) throws Exception
	{	
		if ((String) session.getAttribute("id") != null) 
			return "/AdditionImageList/Main";
		else 
			return "/Logout";
    }
	
	@RequestMapping("/List")
	@ResponseBody
	public AjaxResultVO List(AdditionImageListVo additionImageListVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		additionImageListVo.setDomainIdx((String)session.getAttribute("domainIdx"));
		
		// 관리자 정보 set
		additionImageListVo.setSessionDomainIdx((String)session.getAttribute("domainIdx"));
		additionImageListVo.setSessionBrandIdx((String)session.getAttribute("brandIdx"));
		additionImageListVo.setSessionFrancIdx((String)session.getAttribute("francIdx"));
		additionImageListVo.setSessionAdminType((String)session.getAttribute("adminType"));
		try {
			additionImageListVo.setCaseString("AdditionImageList_List");
			List<?> list = additionImageListService.SelectListData(additionImageListVo);
			result.setData(list);
			
			result.setMessages(messages);
		} catch (Exception e) {
			result = WebUtil.ExceptionMessages(result, 2, e, logger);
		}
			
		return result;
	}

	@RequestMapping(value = "/Create")
	@ResponseBody
	public AjaxResultVO Create(AdditionImageListVo additionImageListVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			ArrayList<String> fileIdx = WebUtil.ParsingXML(additionImageListVo.getAdditionXML(), "Addition", "fileIdx");
			ArrayList<String> orderSeq = WebUtil.ParsingXML(additionImageListVo.getAdditionXML(), "Addition", "orderSeq");
			ArrayList<String> type = WebUtil.ParsingXML(additionImageListVo.getAdditionXML(), "Addition", "type");
			
			List<AdditionImageListVo> list = new ArrayList<AdditionImageListVo>();
			
			for (int i = 0; i < fileIdx.size(); i++) {
				AdditionImageListVo vo = new AdditionImageListVo();
				vo.setFileContentIdx(Integer.valueOf(fileIdx.get(i)));
				vo.setOrderSeq(Integer.valueOf(orderSeq.get(i)));
				vo.setAdditionType(type.get(i));
				vo.setDomainIdx(additionImageListVo.getDomainIdx());
				vo.setBrandIdx(additionImageListVo.getBrandIdx());
				list.add(i, vo);
			}
			additionImageListVo.setAdditionList(list);
			
			additionImageListVo.setCaseString("AdditionImageList_Create");
			int idx = additionImageListService.InsertData(additionImageListVo);
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
	
	@RequestMapping(value = "/Delete")
	@ResponseBody
	public AjaxResultVO Delete(AdditionImageListVo additionImageListVo, HttpSession session) throws Exception
	{	
		AjaxResultVO result = new AjaxResultVO();
		Map<String, Object> messages = new HashMap<String, Object>();
		try {
			additionImageListVo.setCaseString("AdditionImageList_Delete");
			int idx = additionImageListService.UpdateData(additionImageListVo);
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
}